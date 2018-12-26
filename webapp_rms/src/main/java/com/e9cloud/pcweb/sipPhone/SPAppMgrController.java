package com.e9cloud.pcweb.sipPhone;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.SPApplyService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import com.e9cloud.core.common.ID;

/**
 * 云话机--应用管理
 * Created by Administrator on 2016/10/28.
 */
@Controller
@RequestMapping("/sp")
public class SPAppMgrController extends BaseController {

    @Autowired
    private AppInfoService appInfoService;

    // 应用管理列表页面
    @RequestMapping("appIndex")
    public String toAppIndex(){
        return "SIPPhone/app_index";
    }

    /**
     * 获取云话机应用列表
     * @param page 分页参数
     * @return 分页信息
     */
    @RequestMapping("pageAppList")
    @ResponseBody
    public PageWrapper pageAppList(Page page) {
        logger.info("=========== SPAppMgrController pageAppList start ===========");
        page.addParams("abustype", Constants.APP_BUS_TYPE_SIP_PHONE);
        return appInfoService.pageAppListForSipPhone(page);
    }

    /**
     * 查看应用信息
     * @return 查看信息的页面
     */
    @RequestMapping(value = "toShowAppInfo", method = RequestMethod.GET)
    public String toShowAppInfo(String appId, Model model, String managerType) {

        AppInfo appInfo = appInfoService.getSipPhoneAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);
        model.addAttribute("managerType", managerType);

        return "SIPPhone/app_show";
    }

    /**
     * 编辑应用
     * @return 编辑应用的页面
     */
    @RequestMapping(value = "toEditAppInfo", method = RequestMethod.GET)
    public String toEditAppInfo(String appId, Model model, String managerType) {

        AppInfo appInfo = appInfoService.getSipPhoneAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);
        model.addAttribute("managerType", managerType);

        return "SIPPhone/app_edit";
    }

    // 修改sip应用名称 并发数等信息
    @RequestMapping(value = "updateApp", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateApp(AppInfo appInfo, AppInfoExtra appInfoExtra, int[] valueAddeds, int[] recordingTypes, BigDecimal quota1, String limitStatus) throws Exception{

        logger.info("-------------valueAddeds:{},recordingTypes:{}-------------", valueAddeds, recordingTypes);

        if (quota1 != null && !quota1.equals(new BigDecimal(0))) {
            BigDecimal bigDecimal = appInfo.getQuota();
            if (bigDecimal == null) {
                bigDecimal = new BigDecimal(0);
            }
            if (Tools.eqStr("00", limitStatus)) {
                appInfo.setQuota(bigDecimal.add(quota1));
            } else if (Tools.eqStr("01", limitStatus)){
                appInfo.setQuota(bigDecimal.subtract(quota1));
            }
        }

        int valueAdded = Tools.bitToDecimal(valueAddeds);
        int recordingType = Tools.bitToDecimal(recordingTypes);

        appInfoExtra.setValueAdded(valueAdded);
        appInfoExtra.setRecordingType(recordingType);
        appInfo.setUpdateDate(new Date());
        appInfoService.updateAppInfoAndExtra(appInfo, appInfoExtra);

        appInfo.setAppInfoExtra(appInfoExtra);
        return new JSonMessage("ok", "修改成功！", appInfo);
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, Model model, Page page) {
        page.addParams("abustype", Constants.APP_BUS_TYPE_SIP_PHONE);
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
        map.put("title", "云话机应用列表");
        map.put("excelName","云话机应用列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("云话机应用列表", "云话机应用列表导出一条记录。导出内容的查询条件为：" + " account ID： "
                + params.get("sid") + "，客户名称：" + params.get("companyName") + "，appid：" + params.get("appid") + "，应用名称："
                + params.get("appName") + "，客户账号：" + params.get("email"), LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 跳转到添加用户界面
     */
    @RequestMapping(value = "toAddApp", method = RequestMethod.GET)
    public String toAddApp () {
        return "SIPPhone/app_add";
    }

    /**
     * app应用编辑
     */
    @RequestMapping(value = "addApp", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage appSave( AppInfo app, AppInfoExtra appInfoExtra, int[] valueAddeds,int[] recordingTypes) throws Exception {
        try{
//            Account account =  (Account) request.getSession().getAttribute("userInfo");
//            String sid = account.getSid();
//            app.setSid(sid);
            app.setAppid(ID.randomUUID());
            app.setCreateDate(new Date());
            app.setBusType(Constants.APP_BUS_TYPE_SIP_PHONE);
            appInfoService.saveAppInfo(app);
            appInfoExtra.setAppid(app.getAppid());
            logger.info("-------------recordingTypes:{}-------------", recordingTypes);
            appInfoExtra.setAppid(app.getAppid());
//            appInfoExtra.setVoiceCode("G729");
            int recordingType = Tools.bitToDecimal(recordingTypes);
            int valueAdded = Tools.bitToDecimal(valueAddeds);


            appInfoExtra.setValueAdded(valueAdded);
            appInfoExtra.setRecordingType(recordingType);
            appInfoService.saveAppExtra(appInfoExtra);
            return new JSonMessage(R.OK, "创建成功！");

        }catch (Exception e){
            logger.info("--------appSave error----：{}", e);
            return new JSonMessage(R.ERROR, "创建失败！");
        }
    }



}
