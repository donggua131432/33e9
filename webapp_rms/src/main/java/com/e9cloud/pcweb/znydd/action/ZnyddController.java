package com.e9cloud.pcweb.znydd.action;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.common.ID;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.RelayService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.znydd.biz.UAZnyddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 智能云调度
 * Created by Administrator on 2016/3/2.
 */
@Controller
@RequestMapping(value = "znydd")
public class ZnyddController extends BaseController{

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private UAZnyddService uaZnyddService;

    @Autowired
    AppConfig appConfig;

    // 中继 Service
    @Autowired
    private RelayService relayService;

    // 创建账号页面
    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public String toAdd() {
        return "znydd/znydd_add";
    }

    // 应用列表
    @RequestMapping(value = "toAppList", method = RequestMethod.GET)
    public String toAppList() {
        return "znydd/znydd_app_list";
    }

    // 创建应用页面
    @RequestMapping(value = "toAddApp", method = RequestMethod.GET)
    public String toAddApp() {
        return "znydd/znydd_app_add";
    }

    // 显示应用信息
    @RequestMapping(value = "toShowAppInfo", method = RequestMethod.GET)
    public String toShowAppInfo(String appId, Model model) {

        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);

        return "znydd/znydd_app_show";
    }

    // 编辑应用页面
    @RequestMapping(value = "toEditAppInfo", method = RequestMethod.GET)
    public String toEditAppInfo(String appId, Model model) {

        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);

        return "znydd/znydd_app_edit";
    }

    // 为只能云调度用户添加一个应用
    @RequestMapping(value = "addApp", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage addApp(AppInfo appInfo) throws Exception{

        logger.info("-------------ZnyddController addApp start-------------");

        appInfo.setStatus("00");
        appInfo.setCreateDate(new Date());
        appInfo.setAppid(ID.randomUUID());
        appInfo.setBusType("01");

        appInfoService.saveAppInfo(appInfo);

        logger.info("-------------ZnyddController addApp start-------------");

        return new JSonMessage("ok", "");
    }

    // 为只能云调度用户修改一个应用信息
    @RequestMapping(value = "editApp", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage editApp(AppInfo appInfo) throws Exception{

        logger.info("-------------ZnyddController editApp start-------------");

        if(Tools.isNotNullStr(appInfo.getAnswerTrunk())){
            SipBasic sipBasic = relayService.getLimitStatusByRelayNum(appInfo.getAnswerTrunk());
            if (sipBasic == null) {
                return new JSonMessage(R.ERROR, "中继不存在");
            }
            if (!"00".equals(sipBasic.getUseType()) || !"91".equals(sipBasic.getBusType())){
                return new JSonMessage(R.ERROR, "中继类型不正确");
            }
        }

        appInfo.setUpdateDate(new Date());

        appInfoService.updateAppInfo(appInfo);

        logger.info("-------------ZnyddController editApp start-------------");

        return new JSonMessage("ok", "修改成功");
    }

    // 改变一个应用的状态
    @RequestMapping(value = "updateAppStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateAppStatus(AppInfo appInfo,String appid,String companyName,String sid,String appName) {
        Map<String,String> map = new HashMap<String, String>();
        logger.info("-------------ZnyddController updateAppStatus start-------------");

        String count = appInfoService.countBusinessType(appInfo.getAppid());
        if("0".equals(count)){
            map.put("code","01");
        }else {
            appInfoService.updateAppStatus(appInfo);
            map.put("code","00");
        }

        String status = appInfo.getStatus();
        if("00".equals(status)){
            LogUtil.log("启用应用", "accountID：" +sid+" , 客户名称: "+companyName + " , AppID: "+appid +" , 应用名称："+ appName+ " 。 启用应用，恢复正常！" , LogType.UPDATE);

        }
        if("01".equals(status)){
            LogUtil.log("冻结应用", "accountID：" +sid+" , 客户名称: "+companyName + " , AppID: "+appid +" , 应用名称："+ appName+ "  。 应用冻结！" , LogType.UPDATE);
        }
        if("02".equals(status)){
            LogUtil.log("禁用应用", "accountID：" +sid+" , 客户名称: "+companyName + " , AppID: "+appid +" , 应用名称："+ appName+ " 。 应用禁用！" , LogType.UPDATE);
        }

        return map;
    }

    // 分页查询应用列表
    @RequestMapping(value = "pageAppList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageAppList(Page page) {
//        page.addParams("ubustype","2");
        page.addParams("abustype","01");
        return appInfoService.pageAppListForZnydd(page);
    }

    // 创建并保存用户的信息
    @RequestMapping(value="addZnyddUser", method = RequestMethod.POST)
    public String addZnyddUser(HttpServletRequest request, HttpServletResponse response, UserAdmin userAdmin, AuthenCompany authenCompany,AppInfo appInfo, UserExternInfo userExternInfo,
                               @RequestParam(required = false, value = "taxRegFile") MultipartFile taxRegPic,
                               @RequestParam(required = false, value = "businessLicenseFile") MultipartFile businessLicensePic,Model model){
        logger.info("-------------ZnyddController addZnyddUser start-------------");
        String result = null;
        String authAddress = request.getParameter("authAddress");
        String companyName = request.getParameter("companyName");
        String busType = request.getParameter("busType");
        authenCompany.setName(companyName);
        authenCompany.setAddress(authAddress);
        try {
            result =  uaZnyddService.saveZnyddUser(userAdmin,authenCompany,userExternInfo,taxRegPic,businessLicensePic,busType,appInfo);
        } catch (Exception e) {
            logger.info("-------------ZnyddController error-------------",e);
            model.addAttribute("result", "error");
        }
        //操作日志工具类
        LogUtil.log("创建并保存用户的信息", "客户名称："+companyName+",  创建新用户并认证通过。", LogType.UPDATE);
        logger.info("-------------ZnyddController addZnyddUser end-------------");
        model.addAttribute("result", result);
        return "znydd/znydd_add";
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadAppListReport")
    public ModelAndView downloadAppListReport(Page page) {
        logger.info("=====================================ZnyddController downloadAppListReport Execute=====================================");
//        page.addParams("ubustype","2");
        page.addParams("abustype","01");
        List<Map<String, Object>> totals = appInfoService.downloadAppInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> appMap = new HashMap<String, Object>();
            appMap.put("rowNO",(int)Float.parseFloat(map.get("rowNO").toString()));
            appMap.put("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("createDate")));
            appMap.put("companyName", map.get("companyName"));
            appMap.put("sid", map.get("sid"));
            appMap.put("appName", map.get("appName"));
            appMap.put("appId", map.get("appId"));
            if("00".equals(map.get("status"))){
                appMap.put("status", "正常");
            }else if("01".equals(map.get("status"))){
                appMap.put("status", "冻结");
            }else if("02".equals(map.get("status"))){
                appMap.put("status", "禁用");
            }
            if( map.get("callNo")==null){
                appMap.put("callNo", "");
            }else{
                appMap.put("callNo", map.get("callNo"));
            }

            list.add(appMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("id");
        titles.add("创建时间");
        titles.add("客户名称");
        titles.add("Account ID");
        titles.add("应用名称");
        titles.add("APP ID");
        titles.add("应用号码");
        titles.add("应用状态");


        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("createDate");
        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appId");
        columns.add("callNo");
        columns.add("status");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "应用信息列表");
        map.put("excelName","应用信息列表");
        return new ModelAndView(new POIXlsView(), map);
    }

    @RequestMapping(value = "toEditAppRelayRoute", method = RequestMethod.GET)
    public String toEditAppRouteInfo(String appId, Model model, String managerType) {
        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);
        return "znydd/znydd_appRoute_edit";
    }

    /**
     * 使用专用路由
     * @return
     */
    @RequestMapping("/updateRoute")
    @ResponseBody
    public JSonMessageSubmit updateRoute(HttpServletRequest request) {
        logger.info("--------------------------------ZnyddController updateRoute start--------------------------------");
        String isRoute = request.getParameter("isRoute") == null ? "" : request.getParameter("isRoute").trim();
        String routeCode = request.getParameter("routeCode") == null ? "" : request.getParameter("routeCode").trim();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id").trim();
        AppInfo appInfo = new AppInfo();
        appInfo.setId(Integer.parseInt(id));
        appInfo.setIsRoute(isRoute);
        if (routeCode.length() > 0 && routeCode.length() < 4) {
            routeCode = "0000".substring(0, 3 - routeCode.length()) + routeCode;
        }
        appInfo.setRouteCode(routeCode);

        try {
            appInfoService.updateAppInfo(appInfo);
            if (routeCode.length() > 0) {
                return new JSonMessageSubmit("2", "路由编码设置成功！");
            }
            if (routeCode.length() == 0) {
                return new JSonMessageSubmit("", "路由编码未设置！");
            }
            return new JSonMessageSubmit("1", "修改成功！");
        } catch (Exception e) {
            return new JSonMessageSubmit("-1", "修改失败！");
        }
    }

}
