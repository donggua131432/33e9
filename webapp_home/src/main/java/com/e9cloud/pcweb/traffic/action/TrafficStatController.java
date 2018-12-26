package com.e9cloud.pcweb.traffic.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.CallCenterService;
import com.e9cloud.mybatis.service.SmartCloudTrafficMgrService;
import com.e9cloud.mybatis.service.TelnoOperatorService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.app.biz.AppCommonService;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 话务统计
 *
 * Created by Administrator on 2016/8/10.
 */
@Controller
@RequestMapping("/smartCloud/traffic/stat")
public class TrafficStatController extends BaseController{

    @Autowired
    private TelnoOperatorService telnoOperatorService;

    @Autowired
    private SmartCloudTrafficMgrService smartCloudTrafficMgrService;

    @Autowired
    private CallCenterService callCenterService;

    @Autowired
    private AppCommonService appCommonService;

    /**
     *  跳转到话务统计页面
     *
     * @return 返回话务统计页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String trafficStat(HttpServletRequest request, Model model) {
        logger.info("------------------------------------------------GET TrafficStatController trafficStat START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        model.addAttribute("currentDate",new SimpleDateFormat("yyyy-MM").format(new Date()));
        model.addAttribute("beforeMonth12",new SimpleDateFormat("yyyy-MM").format(appCommonService.getDayByType("beforeMonth12")));
        model.addAttribute("ccInfoList", smartCloudTrafficMgrService.findCcInfoListBySid(account.getSid()));
        model.addAttribute("telnoOperatorList", telnoOperatorService.getTelnoOperatorList(new TelnoOperator()));
        return "traffic/traffic_stat";
    }

    /**
     * 返回话务统计分页信息列表
     * @param request
     * @param page
     * @return
     */
    @RequestMapping(value = "pageStatCcRecordList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageStatCcRecordList(HttpServletRequest request, Page page) {
        logger.info("------------------------------------------------GET TrafficStatController pageStatCcRecordList START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        page.addParams("feeid",account.getFeeid());
        if(page.getParams().get("reportType") == null || "".equals(page.getParams().get("reportType"))) page.getParams().put("reportType","1");
        if("1".equals(page.getParams().get("reportType").toString())){
            logger.info("-----------------------------------------------查询话务统计时报----------------------------------------------------------------");
            if(page.getParams().get("stathour") == null || "".equals(page.getParams().get("stathour"))) page.getParams().put("stathour",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(page.getParams().get("stathour1") == null || "".equals(page.getParams().get("stathour1"))) page.getParams().put("stathour1",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return smartCloudTrafficMgrService.pageCcHourRecordList(page);
        }else if("2".equals(page.getParams().get("reportType").toString())){
            logger.info("-----------------------------------------------查询话务统计日报----------------------------------------------------------------");
            if(page.getParams().get("statday") == null || "".equals(page.getParams().get("statday"))) page.getParams().put("statday",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(page.getParams().get("statday1") == null || "".equals(page.getParams().get("statday1"))) page.getParams().put("statday1",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return smartCloudTrafficMgrService.pageCcDayRecordList(page);
        }else{
            logger.info("-----------------------------------------------查询话务统计月报----------------------------------------------------------------");
            if(page.getParams().get("statday") == null || "".equals(page.getParams().get("statday"))) page.getParams().put("statday",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return smartCloudTrafficMgrService.pageCcMonthRecordList(page);
        }
    }


    /**
     * 下载话务统计分页信息
     * @param page
     * @return
     */
    @RequestMapping("/downloadCcRecordReport")
    public ModelAndView downloadCcRecordReport(HttpServletRequest request, Page page){
        logger.info("------------------------------------------------GET TrafficStatController downloadCcRecordReport START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        //获取呼叫中心map集合
        List<CcInfo> ccInfoList = callCenterService.getAllCallCenterInfoWithDelete(account.getSid());
        Map<String, String> ccInfoMap = new HashMap<>();
        for(CcInfo ccInfo:ccInfoList){
            ccInfoMap.put(ccInfo.getSubid(), ccInfo.getCcname());
        }
        //获取统计方式map集合
        List<TelnoOperator> telnoOperatorList = telnoOperatorService.getTelnoOperatorList(new TelnoOperator());
        Map<String, String> telnoOperatorMap = new HashMap<>();
        for(TelnoOperator telnoOperator:telnoOperatorList){
            telnoOperatorMap.put(telnoOperator.getOcode(), telnoOperator.getOname());
        }
        //呼入呼出
        Map<String, String> abLineMap = new HashMap<>();
        abLineMap.put("I","呼入");
        abLineMap.put("O","呼出");

        page.addParams("feeid",account.getFeeid());
        List<Map<String, Object>> totals;
        if("1".equals(page.getParams().get("reportType").toString())){
            logger.info("-----------------------------------------------下载话务统计时报----------------------------------------------------------------");
            if(page.getParams().get("stathour") == null || "".equals(page.getParams().get("stathour"))) page.getParams().put("stathour",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(page.getParams().get("stathour1") == null || "".equals(page.getParams().get("stathour1"))) page.getParams().put("stathour1",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            totals = smartCloudTrafficMgrService.downloadCcHourRecordList(page);
        }else if("2".equals(page.getParams().get("reportType").toString())){
            logger.info("-----------------------------------------------下载话务统计日报----------------------------------------------------------------");
            if(page.getParams().get("statday") == null || "".equals(page.getParams().get("statday"))) page.addParams("statday",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(page.getParams().get("statday1") == null || "".equals(page.getParams().get("statday1"))) page.addParams("statday1",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            totals = smartCloudTrafficMgrService.downloadCcDayRecordList(page);
        }else{
            logger.info("-----------------------------------------------下载话务统计月报----------------------------------------------------------------");
            if(page.getParams().get("statday") == null || "".equals(page.getParams().get("statday"))) page.getParams().put("statday",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            totals = smartCloudTrafficMgrService.downloadCcMonthRecordList(page);
        }

        List<Map<String, Object>> list = new ArrayList<>();
        totals.forEach((map) -> {
            Map<String, Object> ccRecordMap = new HashMap<>();
            if("1".equals(page.getParams().get("reportType").toString())){
                ccRecordMap.put("dayHour", map.get("stathour").toString());
            }else if("2".equals(page.getParams().get("reportType").toString())){
                String statday = map.get("statday").toString();
                ccRecordMap.put("dayHour", statday.substring(0,10));
            }else if("3".equals(page.getParams().get("reportType").toString())){
                String statday = map.get("statday").toString();
                ccRecordMap.put("dayHour", statday.substring(0,7));
            }
            ccRecordMap.put("pname", Tools.toStr(map.get("pname")));
            ccRecordMap.put("ccname", ccInfoMap.get(Tools.toStr(map.get("subid"))));
            ccRecordMap.put("operator", telnoOperatorMap.get(Tools.toStr(map.get("operator"))));
            ccRecordMap.put("abline", abLineMap.get(Tools.toStr(map.get("abline"))));
            ccRecordMap.put("succcnt", Tools.toStr(map.get("succcnt")));
            ccRecordMap.put("thscsum", Tools.toStr(map.get("thscsum")));
            ccRecordMap.put("jfscsum", Tools.toStr(map.get("jfscsum")));
            list.add(ccRecordMap);
        });

        List<String> titles = new ArrayList<>();
        titles.add("日期");
        titles.add("省份");
        titles.add("呼叫中心");
        titles.add("运营商");
        titles.add("呼叫类型");
        titles.add("接通数");
        titles.add("总通话时长(秒)");
        titles.add("计费通话时长(分钟)");

        List<String> columns = new ArrayList<>();
        columns.add("dayHour");
        columns.add("pname");
        columns.add("ccname");
        columns.add("operator");
        columns.add("abline");
        columns.add("succcnt");
        columns.add("thscsum");
        columns.add("jfscsum");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        if("1".equals(page.getParams().get("reportType").toString())){
            map.put("title", "话务统计报表（时报）");
            map.put("excelName","话务统计报表（时报）");
        }else if("2".equals(page.getParams().get("reportType").toString())){
            map.put("title", "话务统计报表（日报）");
            map.put("excelName","话务统计报表（日报）");
        }else if("3".equals(page.getParams().get("reportType").toString())){
            map.put("title", "话务统计报表（月报）");
            map.put("excelName","话务统计报表（月报）");
        }

        return new ModelAndView(new POIXlsView(), map);
    }
}
