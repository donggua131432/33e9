package com.e9cloud.pcweb.openinterface.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppRelay;
import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.AppRelayService;
import com.e9cloud.mybatis.service.IDicDataService;
import com.e9cloud.mybatis.service.RelayService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/12.
 */
@Controller
@RequestMapping("openi")
public class OpenInterfaceController extends BaseController{

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private IDicDataService iDicDataService;

    // 中继 Service
    @Autowired
    private RelayService relayService;

    //应用中继
    @Autowired
    private AppRelayService appRelayService;

    // 应用列表
    @RequestMapping(value = "toAppList", method = RequestMethod.GET)
    public String toAppList(Model model) {

        List<DicData> dicDatas = iDicDataService.findDicListByType("appType");

        model.addAttribute("dicDatas", dicDatas);
        model.addAttribute("dds", JSonUtils.toJSon(dicDatas));

        return "openI/app_list";
    }

    // 分页查询应用列表
    @RequestMapping(value = "pageAppList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageAppList(Page page) {
        page.addParams("ubustype","1");
        page.addParams("abustype","02");
        return appInfoService.pageAppListForZnydd(page);
    }

    // 分页查询中继方向为为（10 出中继 11双向中继）类型的中继群 且正常中继列表
    @RequestMapping(value = "pageRelayList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageRelayList(Page page, String appid) {
        page.addParams("appid", appid);

        PageWrapper pageWrapper = relayService.getRelaysByRelayType(page);

        /*List<SipBasic> sipBasicList = relayService.getRelays();
        List<SipBasic> newSipBasicList = new ArrayList<SipBasic>();

        AppRelay appRelayInTbale = new AppRelay();
        AppRelay appRelay =new AppRelay();
        SipBasic sipBasic = null;

        for(int i=0; i<sipBasicList.size(); i++){
            sipBasic = sipBasicList.get(i);
            appRelay.setAppid(appid);
            appRelay.setRelayNum(sipBasic.getRelayNum());
            appRelayInTbale = appRelayService.getAppRelayByObj(appRelay);
            if(appRelayInTbale!=null){
                sipBasic.setIsChecked("1");
            }else{
                sipBasic.setIsChecked("0");
            }
            sipBasic.setId(i+1);
            newSipBasicList.add(sipBasic);
        }
        pageWrapper.setRows(newSipBasicList);*/

        return pageWrapper;
        //return relayService.getRelaysByRelayType(page);
    }

    // 编辑sip应用
    @RequestMapping(value = "toEditAppRelay", method = RequestMethod.GET)
    public String toEditAppInfo(String appId, Model model, String managerType) {

        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);


        return "openI/app_relay_edit";
    }

    // 改变一个应用的状态
    @RequestMapping(value = "updateAppStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateAppStatus(AppInfo appInfo,String appid,String companyName,String sid,String appName) {
        Map<String,String> map = new HashMap<String, String>();
        logger.info("-------------OpenInterfaceController updateAppStatus start-------------");

        appInfoService.updateAppStatus(appInfo);
        map.put("code","00");

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

    // 编辑应用的专用路由
    @RequestMapping(value = "toEditAppRelayRoute", method = RequestMethod.GET)
    public String toEditAppRouteInfo(String appId, Model model, String managerType) {
        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);
        return "openI/openI_appRoute_edit";
    }

    // 选择系统默认中继号码池时将app_info表字段isDefined置为0
    @RequestMapping(value = "useSysNum")
    @ResponseBody
    public JSonMessage useSysNum(String appid) throws Exception{
        AppInfo appInfoParma = new AppInfo();
        appInfoParma.setAppid(appid);
        AppInfo appInfo = appInfoService.getAppInfoByObj(appInfoParma);
        List<AppRelay> appRelayList = new ArrayList<AppRelay>();
        if(appInfo!=null){
            //app应用里字段置为系统默认强显号码池
            appInfo.setIsDefined("0");
            appInfoService.updateAppInfo(appInfo);

            //选择系统默认中继时，清空自定义中继
            appRelayList = appRelayService.findAppRelayListByAppid(appid);
            for(AppRelay appRelay:appRelayList){
                appRelayService.deleteAppRelay(appRelay);
            }
        }else {
            return new JSonMessage(R.ERROR, "找不到该应用!");
        }

        return new JSonMessage(R.OK, "选择成功");
    }

    // 中继应用相关
    @RequestMapping(value = "addAppRelay", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage addAppRelay(AppRelay appRelay)throws Exception{
        AppRelay appRelayInTbale = appRelayService.getAppRelayByObj(appRelay);
        if(appRelayInTbale==null){
            //库里没相关数据，进行插入操作
            AppInfo appInfoParma = new AppInfo();
            appInfoParma.setAppid(appRelay.getAppid());
            AppInfo appInfo = appInfoService.getAppInfoByObj(appInfoParma);
            if(appInfo!=null){
                //app应用里字段置为自定义强显号码池
                appInfo.setIsDefined("1");
                appInfoService.updateAppInfo(appInfo);
                appRelayService.saveAppRelay(appRelay);
            }else {
                return new JSonMessage(R.ERROR, "找不到该应用！");
            }
            return new JSonMessage(R.OK, "选择成功！");

        }else{
            //库里已有相关数据，进行删除操作
            return new JSonMessage(R.ERROR, "已有该中继！");
        }

    }

    // 中继应用相关
    @RequestMapping(value = "deleteAppRelay", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage deleteAppRelay(AppRelay appRelay){
        AppRelay appRelayInTbale = appRelayService.getAppRelayByObj(appRelay);
        if(appRelayInTbale==null){
            return new JSonMessage(R.ERROR, "删除中继失败！");

        }else{

            appRelayService.deleteAppRelay(appRelayInTbale);
            return new JSonMessage(R.OK, "删除成功！");
        }

    }

    // 显示应用信息
    @RequestMapping(value = "toShowAppInfo", method = RequestMethod.GET)
    public String toShowAppInfo(String appId, Model model) {

        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);

        return "znydd/znydd_app_show";
    }

    /**
     * 使用专用路由
     * @return
     */
    @RequestMapping("/updateRoute")
    @ResponseBody
    public JSonMessageSubmit updateRoute(HttpServletRequest request) {
        logger.info("--------------------------------OpenInterfaceController updateRoute start-------------------------------");
        String isRoute = request.getParameter("isRoute") == null ? "" : request.getParameter("isRoute").trim();
        String routeCode = request.getParameter("routeCode") == null ? "" : request.getParameter("routeCode").trim();
        String id = request.getParameter("id") == null ? "" : request.getParameter("id").trim();
        AppInfo appInfo = new AppInfo();
        appInfo.setId(Integer.parseInt(id));
        appInfo.setIsRoute(isRoute);
        if (routeCode.length() > 0 && routeCode.length() < 4) {
            routeCode = "0000".substring(0, 4 - routeCode.length()) + routeCode;
        }
        appInfo.setRouteCode(routeCode);
        try {
            appInfoService.updateAppInfo(appInfo);
            if (routeCode.length() > 0) {
                return new JSonMessageSubmit("1", "路由编码设置成功！");
            }
            if ( routeCode.length() == 0) {
                return new JSonMessageSubmit("0", "路由编码未设置！");
            }
            return new JSonMessageSubmit("1", "修改成功！");
        } catch (Exception e) {
            return new JSonMessageSubmit("-1", "修改失败！");
        }
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, Model model, Page page) {
        page.addParams("abustype", Constants.APP_BUS_TYPE_REST);
        List<Map<String, Object>> totals =  appInfoService.downloadAppInfoForSipphone(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sid",String.valueOf(total.get("sid")));
                map.put("email", String.valueOf(total.get("email")));
                map.put("companyName", String.valueOf(total.get("companyName")));
                map.put("appId",String.valueOf(total.get("appId")));
                map.put("appName", String.valueOf(total.get("appName")));

                if("".equals(total.get("createDate")) || null == total.get("createDate")){
                    map.put("createDate", "");
                }
                else {
                    map.put("createDate", String.valueOf(total.get("createDate").toString().substring(0,19)));
                }

                map.put("status", String.valueOf(total.get("status")));

                if("00".equals(total.get("status"))){
                    map.put("status","正常");

                }
                if("01".equals(total.get("status"))){
                    map.put("status","冻结");
                }
                if("02".equals(total.get("status"))){
                    map.put("status","禁用");
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("注册账号");
        titles.add("accountID");
        titles.add("客户名称");
        titles.add("APP ID");
        titles.add("应用名称");
        titles.add("应用状态");
        titles.add("创建时间");

        List<String> columns = new ArrayList<String>();

        columns.add("email");
        columns.add("sid");
        columns.add("companyName");
        columns.add("appId");
        columns.add("appName");
        columns.add("status");
        columns.add("createDate");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "专线语音应用列表");
        map.put("excelName","专线语音应用列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("专线语音应用列表", "专线语音应用列表导出一条记录。导出内容的查询条件为：" + " account ID： "
                + params.get("sid") + "，客户名称：" + params.get("companyName") + "，appid：" + params.get("appid") + "，应用名称："
                + params.get("appName") + "，客户账号：" + params.get("email"), LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }
}
