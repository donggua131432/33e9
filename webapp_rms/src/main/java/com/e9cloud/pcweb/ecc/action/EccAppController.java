package com.e9cloud.pcweb.ecc.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * ecc 云总机业务
 * Created by dukai on 2017/2/10.
 */
@Controller
@RequestMapping("/ecc")
public class EccAppController extends BaseController {

    // 应用相关 Service
    @Autowired
    private AppInfoService appInfoService;

    // 应用列表
    @RequestMapping(value = "/toAppList", method = RequestMethod.GET)
    public String toAppList() {
        logger.info("----EccAppController toAppList start-------");
        return "ecc/ecc_appList";
    }

    // 分页查询应用列表
    @RequestMapping(value = "/pageAppList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageAppList(Page page) {
        logger.info("----EccAppController pageAppList start-------");
        page.addParams("abustype", Constants.APP_BUS_TYPE_SWITCHBOARD);
        page.setSidx("ai.create_date");
        page.setSord("DESC");
        return appInfoService.pageAppListForSipPhone(page);
    }


    /**
     * 跳转到添加用户界面
     */
    @RequestMapping(value = "/toAddApp", method = RequestMethod.GET)
    public String toAddApp () {
        return "ecc/ecc_add";
    }
    /**
     * app应用编辑
     */
    @RequestMapping(value = "/addApp", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage appSave(AppInfo app, AppInfoExtra appInfoExtra, int[] recordingTypes) throws Exception {
        logger.info("----EccAppController appSave start-------");
        try{
            app.setAppid(ID.randomUUID());
            app.setCreateDate(new Date());
            app.setBusType(Constants.APP_BUS_TYPE_SWITCHBOARD);
            appInfoService.saveAppInfo(app);

            appInfoExtra.setAppid(app.getAppid());
            logger.info("-------------recordingTypes:{}-------------", recordingTypes);
            appInfoExtra.setAppid(app.getAppid());
            int recordingType = Tools.bitToDecimal(recordingTypes);

            appInfoExtra.setRecordingType(recordingType);
            appInfoService.saveAppExtra(appInfoExtra);
            return new JSonMessage(R.OK, "创建成功！");
        }catch (Exception e){
            logger.info("--------appSave error----：{}", e);
            return new JSonMessage(R.ERROR, "创建失败！");
        }
    }


    /**
     * 导出报表
     */
    @RequestMapping(value = "/downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(Page page) {
        logger.info("----EccAppController downloadReport start-------");
        page.addParams("abustype", Constants.APP_BUS_TYPE_SWITCHBOARD);
        List<Map<String, Object>> totals =  appInfoService.downloadAppInfoForSipphone(page);
        List<Map<String, Object>> list = new ArrayList<>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<>();
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
                if("00".equals(total.get("status"))) map.put("status","正常");
                if("01".equals(total.get("status"))) map.put("status","冻结");
                if("02".equals(total.get("status"))) map.put("status","禁用");
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<>();

        titles.add("注册账号");
        titles.add("accountID");
        titles.add("客户名称");
        titles.add("APP ID");
        titles.add("应用名称");
        titles.add("应用状态");
        titles.add("创建时间");

        List<String> columns = new ArrayList<>();

        columns.add("email");
        columns.add("sid");
        columns.add("companyName");
        columns.add("appId");
        columns.add("appName");
        columns.add("status");
        columns.add("createDate");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "云总机应用列表");
        map.put("excelName","云总机应用列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("云总机应用列表", "云总机应用列表导出一条记录。导出内容的查询条件为：" + " account ID： "
                + params.get("sid") + "，客户名称：" + params.get("companyName") + "，appid：" + params.get("appid") + "，应用名称："
                + params.get("appName") + "，客户账号：" + params.get("email"), LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }
}
