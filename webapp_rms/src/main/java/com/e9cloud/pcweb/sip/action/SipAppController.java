package com.e9cloud.pcweb.sip.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
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

/**
 * sip 业务相关的控制器
 * Created by lixin on 2016/7/12.
 */
@Controller
@RequestMapping("sip")
public class SipAppController extends BaseController {

    // 客户信息相关操作
    @Autowired
    private UserAdminService userAdminService;

    // 应用相关 Service
    @Autowired
    private AppInfoService appInfoService;

    // 中继费率 Service
    @Autowired
    private SipRateService sipRateService;

    // 中继-子账号 Service
    @Autowired
    private RelayInfoService relayInfoService;

    // 中继 Service
    @Autowired
    private RelayService relayService;

    // 应用列表
    @RequestMapping(value = "toAppList", method = RequestMethod.GET)
    public String toAppList() {
        return "sip/sip_applist";
    }

    // 分页查询应用列表
    @RequestMapping(value = "pageAppList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageAppList(Page page) {
        page.addParams("ubustype","3");
        page.addParams("abustype","03");
        return appInfoService.pageAppListForSip(page);
    }

    // 查看sip应用
    @RequestMapping(value = "toShowAppInfo", method = RequestMethod.GET)
    public String toShowAppInfo(String appId, Model model, String managerType) {

        AppInfo appInfo = appInfoService.getSipAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);

        SipRate sipRate = sipRateService.getStandardSipReta();
        model.addAttribute("sipRate", sipRate);

        model.addAttribute("managerType", managerType);

        return "sip/sip_app_show";
    }


    // 编辑sip应用
    @RequestMapping(value = "toEditAppInfo", method = RequestMethod.GET)
    public String toEditAppInfo(String appId, Model model, String managerType) {

        AppInfo appInfo = appInfoService.getSipAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);

        SipRate sipRate = sipRateService.getStandardSipReta();
        model.addAttribute("sipRate", sipRate);

        model.addAttribute("managerType", managerType);
        return "sip/sip_app_edit";
    }


    // 修改sip应用名称 并发数等信息
    @RequestMapping(value = "updateApp", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateApp(AppInfo appInfo) throws Exception {

        if (appInfo.getMaxConcurrent() == null){
            appInfo.setMaxConcurrent(0);
        }
        appInfo.setUpdateDate(new Date());
        appInfoService.updateAppInfo(appInfo);
        return new JSonMessage("ok", "修改成功！");
    }

    /**
     * 分页查询子应用列表
     * @param page
     * @return
     */
    @RequestMapping(value = "pageSubList")
    @ResponseBody
    public PageWrapper pageSubList(Page page) {
        return relayInfoService.pageRelayInfo(page);
    }

    // 添加子账号
    @RequestMapping(value = "toAddSub")
    public String toAddSub(Model model, String appid, String sid){
        logger.info("sipAppController toAddsub for appid:{} start" , appid);

        model.addAttribute("sipRate", sipRateService.getStandardSipReta());
        model.addAttribute("relays", relayService.getRelays(null));
        model.addAttribute("appid", appid);
        model.addAttribute("sid", sid);

        logger.info("sipAppController toAddsub for appid:{} end" , appid);

        return "sip/sip_sub_add";
    }

    // 修改子账号基本信息
    @RequestMapping(value = "toEditSub")
    public String toEditSub(Model model, String appid, String subid){
        logger.info("sipAppController toEditSub start");

        SipRelayInfo relayInfo = relayInfoService.getRelayInfoBySubid(subid);
        SipRate rate = sipRateService.getSipRetaBySubid(subid);

        SipBasic sipBasic =  relayService.getRelayInfoByRelayNum(relayInfo.getRelayNum());
        model.addAttribute("sipBasic", sipBasic);

        Map<String, Object> params = new HashMap<>();
        params.put("subid", subid);
        model.addAttribute("sipRate", sipRateService.getStandardSipReta());
        model.addAttribute("relays", relayService.getRelays(params));
        model.addAttribute("appid", appid);
        model.addAttribute("relayInfo", relayInfo);
        model.addAttribute("rate", rate);
        model.addAttribute("calledTypes", Tools.decimalToBitStr(relayInfo.getCalledType()));
        model.addAttribute("calledLimits", Tools.decimalToBitStr(relayInfo.getCalledLimit()));
        logger.info("sipAppController toEditSub end");

        return "sip/sip_sub_edit";
    }

    // 查看子账号基本信息
    @RequestMapping(value = "toShowSub")
    public String toShowSub(Model model, String appid, String subid){
        logger.info("sipAppController toShowSub for appid:{} start" , appid);

        SipRelayInfo relayInfo = relayInfoService.getRelayInfoBySubid(subid);
        SipRate rate = sipRateService.getSipRetaBySubid(subid);
        model.addAttribute("sipRate", sipRateService.getStandardSipReta());

        SipBasic sipBasic =  relayService.getRelayInfoByRelayNum(relayInfo.getRelayNum());
        model.addAttribute("sipBasic", sipBasic);

        Map<String, Object> params = new HashMap<>();
        params.put("subid", subid);
        model.addAttribute("relays", relayService.getRelays(params));
        model.addAttribute("appid", appid);
        model.addAttribute("relayInfo", relayInfo);
        model.addAttribute("rate", rate);
        model.addAttribute("calledTypes", Tools.decimalToBits(relayInfo.getCalledType()));
        model.addAttribute("calledLimits", Tools.decimalToBits(relayInfo.getCalledLimit()));
        logger.info("sipAppController toShowSub for appid:{} end" , appid);

        return "sip/sip_sub_show";
    }


    // 查看SipBasic基本信息
    @RequestMapping(value = "getSipBasic")
    @ResponseBody
    public Map<String,Object> getSipBasic(Model model, String relayNum){
//        logger.info("sipAppController toShowSub for appid:{} start" , appid);
        Map<String,Object> map = new HashMap<String,Object>();
        SipBasic sipBasic =  relayService.getRelayInfoByRelayNum(relayNum);
        if(sipBasic != null){
            map.put("sipBasic", sipBasic);
        }else {
            map.put("sipBasic", "");
        }
        return  map;
    }
    /**
     * 添加子账号
     * @param model
     * @param relayInfo 子账号-中继信息
     * @param sipRate 子账号费率
     * @return
     */
    @RequestMapping(value = "addSub")
    @ResponseBody
    public JSonMessage addSub(Model model, SipRelayInfo relayInfo, SipRate sipRate, String sid, int[] calledType, int[] calledLimit,String discount){
        logger.info("sipAppController addsub start");

        JSonMessage jSonMessage = new JSonMessage();

        SipBasic sipBasic = relayService.getLimitStatusByRelayNum(relayInfo.getRelayNum());

        if ("00".equals(sipBasic.getLimitStatus()) && "03".equals(sipBasic.getBusType())) { // 中继可用 并且 为sip中继
            UserAdmin userAdmin = userAdminService.findUserAdminSID(sid);
            String subId = ID.randomUUID();

            relayInfo.setCreateTime(new Date());
            relayInfo.setSubid(subId);
            relayInfo.setCalledType(Tools.bitToDecimal(calledType));

            relayInfo.setCalledLimit(Tools.bitToDecimal(calledLimit));

            sipRate.setSubid(subId);
            sipRate.setFeeid(userAdmin.getFeeid());
            sipRate.setAddtime(new Date());
            int dis = Double.valueOf(Double.valueOf(discount) * 10).intValue();
            if (sipRate.getCycle() == 6) {
                sipRate.setPer6Discount(dis);
            }
            if (sipRate.getCycle() == 60) {
                sipRate.setPer60Discount(dis);
            }

            relayInfoService.saveRelayInfoAndRate(relayInfo, sipRate);
            jSonMessage.setCode(R.OK);
            jSonMessage.setMsg("添加成功!");
        } else { // 中继不可用
            jSonMessage.setCode(R.ERROR);
            jSonMessage.setMsg("中继不可用!");
        }

        logger.info("sipAppController addsub end");

        return jSonMessage;
    }

    @RequestMapping(value = "editSub")
    @ResponseBody
    public JSonMessage editSub(SipRelayInfo relayInfo, SipRate sipRate, String sid, int[] calledType,  int[] calledLimit, String discount, String oldRelayNum) {
        logger.info("sipAppController addsub start");

        boolean relayChanged = false;

        JSonMessage jSonMessage = new JSonMessage();

        SipBasic sipBasic = new SipBasic();

        if (Tools.isNotNullStr(oldRelayNum) && !oldRelayNum.equals(relayInfo.getRelayNum())) {
            sipBasic = relayService.getLimitStatusByRelayNum(relayInfo.getRelayNum());
            relayChanged = true;
        }

        if (!relayChanged || ("00".equals(sipBasic.getLimitStatus()) && "03".equals(sipBasic.getBusType()))) { // 中继可用 并且 为sip中继
            relayInfo.setCalledType(Tools.bitToDecimal(calledType));

            relayInfo.setCalledLimit(Tools.bitToDecimal(calledLimit));
            int dis = Double.valueOf(Double.valueOf(discount) * 10).intValue();
            if (sipRate.getCycle() == 6) {
                sipRate.setPer6Discount(dis);
            }
            if (sipRate.getCycle() == 60) {
                sipRate.setPer60Discount(dis);
            }
            //add by lixin   输入为空时设置为默认值 ----start-----
            if (relayInfo.getMaxConcurrent() == null){
                relayInfo.setMaxConcurrent(0);
            }
            //add by lixin   ----end-----
            relayInfoService.updateRelayInfoAndRate(relayInfo, sipRate);
            jSonMessage.setCode(R.OK);
            jSonMessage.setMsg("修改成功!");
        } else {// 中继不可用
            jSonMessage.setCode(R.ERROR);
            jSonMessage.setMsg("中继不可用!");
        }

        logger.info("sipAppController addsub end");

        return jSonMessage;
    }

    // 删除子账号
    @RequestMapping(value = "deleteSub")
    @ResponseBody
    public JSonMessage deleteSub(String subid){
        logger.info("deleteSub subid:{} start", subid);
        relayInfoService.deleteSubBySubid(subid);
        logger.info("deleteSub subid:{} end", subid);
        return new JSonMessage(R.OK, "删除成功");
    }

    // 改变sip应用的状态
    @RequestMapping(value = "updateAppStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateAppStatus(AppInfo appInfo, String appid, String companyName, String sid, String appName) {

        appInfoService.updateAppStatus(appInfo);
        String status = appInfo.getStatus();
        logger.info("-------------updateAppStatus start-------------");
        if("00".equals(status)){
            LogUtil.log("启用应用 ", "accountID：" +sid+" , 客户名称: "+companyName + " , AppID: "+appid +" , 应用名称："+ appName+ " 。 启用应用，恢复正常！" , LogType.UPDATE);
        }
        if("01".equals(status)){
            LogUtil.log("冻结应用", "accountID：" +sid+" , 客户名称: "+companyName + " , AppID: "+appid +" , 应用名称："+ appName+ "  。 应用冻结！" , LogType.UPDATE);
        }
        if("02".equals(status)){
            LogUtil.log("禁用应用", "accountID：" +sid+" , 客户名称: "+companyName + " , AppID: "+appid +" , 应用名称："+ appName+ " 。 应用禁用！" , LogType.UPDATE);
        }
        return new JSonMessage("ok", "修改成功");
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, Model model,Page page) {
        List<Map<String, Object>> totals =  appInfoService.getpageAppListForSip(page);
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
        map.put("title", "Sip业务应用列表");
        map.put("excelName","Sip业务应用列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("Sip业务应用列表", "Sip业务应用列表导出一条记录。导出内容的查询条件为：" + " account ID： "
                + params.get("sid") + "，客户名称：" + params.get("companyName") + "，appid：" + params.get("appid") + "，应用名称："
                + params.get("appName") + "，客户账号：" + params.get("email"), LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 导出子账号-中继报表
     *
     * @return
     */
    @RequestMapping(value = "downloadSubReport", method = RequestMethod.GET)
    public ModelAndView downloadSubReport(HttpServletRequest request, Model model, Page page) {

        List<Map<String, Object>> totals = relayInfoService.downloadSubReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if(totals != null && totals.size() > 0) {
            // 标准价
            SipRate rate = sipRateService.getStandardSipReta();

            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO",String.valueOf(total.get("rowNO")));
                if("".equals(total.get("createTime")) || null == total.get("createTime")){
                    map.put("createTime", "");
                } else {
                    map.put("createTime", String.valueOf(total.get("createTime").toString().substring(0,19)));
                }
                map.put("subName", String.valueOf(total.get("subName")));
                map.put("subid", String.valueOf(total.get("subid")));
                map.put("relayName", String.valueOf(total.get("relayName")));
                map.put("relayNum", String.valueOf(total.get("relayNum")));

                if("00".equals(total.get("numFlag"))){
                    map.put("numFlag", "关闭");
                } else if("01".equals(total.get("numFlag"))){
                    map.put("numFlag", "打开");
                }

                String cycle = String.valueOf(total.get("cycle"));

                if ("6".equals(cycle)) { // 6秒计费
                    Integer per6Discount = Integer.parseInt(String.valueOf(total.get("per6Discount")));

                    map.put("per", rate.getPer6());
                    map.put("discount", String.valueOf(per6Discount));
                    map.put("aper", String.valueOf(rate.getPer6().multiply(BigDecimal.valueOf(per6Discount))));
                    map.put("cycle", "6秒");
                } else if ("60".equals(cycle)) { // 60秒（分钟）计费
                    Integer per60Discount = Integer.parseInt(String.valueOf(total.get("per60Discount")));

                    map.put("per", rate.getPer60());
                    map.put("discount", String.valueOf(String.valueOf(per60Discount)));
                    map.put("aper", String.valueOf(rate.getPer60().multiply(BigDecimal.valueOf(per60Discount))));
                    map.put("cycle", "分钟");
                }

                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("序号");
        titles.add("创建时间");
        titles.add("子账号名称");
        titles.add("子账号ID");
        titles.add("中继名称");
        titles.add("中继编号");
        titles.add("随机选号");
        titles.add("标准价");
        titles.add("折扣率");
        titles.add("折后价");
        titles.add("计费单位");

        List<String> columns = new ArrayList<String>();

        columns.add("rowNO");
        columns.add("createTime");
        columns.add("subName");
        columns.add("subid");
        columns.add("relayName");
        columns.add("relayNum");
        columns.add("numFlag");
        columns.add("per");
        columns.add("discount");
        columns.add("aper");
        columns.add("cycle");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "Sip子账号列表");
        map.put("excelName","Sip子账号列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("Sip子账号列表", "Sip子账号列表导出一条记录。导出内容的查询条件为：" + " 中继名称： "
                + params.get("relayName") + "，中继编号：" + params.get("relayNum") + "，开始时间："
                + Tools.formatDate(page.getTimemax()) + "，结束时间：" + Tools.formatDate(page.getTimemin())
                , LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }



    @RequestMapping(value = "toEditAppRoute", method = RequestMethod.GET)
    public String toEditAppRouteInfo(String appId, Model model, String managerType) {
        AppInfo appInfo = appInfoService.getSipAppInfoByAppId(appId);
        model.addAttribute("appInfo", appInfo);

        SipRate sipRate = sipRateService.getStandardSipReta();
        model.addAttribute("sipRate", sipRate);

        model.addAttribute("managerType", managerType);
        return "sip/sip_appRoute_edit";
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
