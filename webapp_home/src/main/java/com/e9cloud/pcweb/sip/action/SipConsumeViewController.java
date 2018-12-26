package com.e9cloud.pcweb.sip.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.service.SipManagerService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.app.biz.AppCommonService;
import com.e9cloud.pcweb.sip.biz.SipCommonService;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Dukai on 2016/7/12.
 */
@Controller
@RequestMapping("/sipConsumeView")
public class SipConsumeViewController extends BaseController{

    @Autowired
    private SipManagerService sipManagerService;

    @Autowired
    private SipCommonService sipCommonService;

    @Autowired
    private AppCommonService appCommonService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model){
        logger.info("------------------------------------------------GET SipConsumeViewController index START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);

        //获取时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date currentDate = appCommonService.getDayByType("");
        Date beforeMonth12 = appCommonService.getDayByType("beforeMonth12");
        logger.info("=============================================================================================");
        logger.info("当前年月："+sdf.format(currentDate)+" > "+currentDate);
        logger.info("12个月前年月："+sdf.format(beforeMonth12)+" > "+beforeMonth12);
        logger.info("=============================================================================================");

        //当前年月
        model.addAttribute("currentDate",sdf.format(currentDate));
        //12个月前年月
        model.addAttribute("beforeMonth",sdf.format(beforeMonth12));

      /*  double sipYesterdayFee = appCommonService.getCallRecordSumFee(account, "SipRecord_"+account.getFeeid() ,"yesterday");
        double sipMonthFee = appCommonService.getCallRecordSumFee(account, "SipRecord_"+account.getFeeid(), "month");
        //sip昨天消费信息
        model.addAttribute("sipYesterdayFee", String.format("%.2f",sipYesterdayFee));
        //sip本月消费信息
        model.addAttribute("sipMonthFee", String.format("%.2f",sipMonthFee));*/
        Map<String, Object> map = new HashMap<>();
        map.put("feeid",account.getFeeid());
        //sip昨天消费信息
        model.addAttribute("sipYesterdayFee", appCommonService.getSipYesterDaySpendSumFee(map));
        //sip本月消费信息
        model.addAttribute("sipMonthFee", appCommonService.getSipMonthSpendSumFee(map));
        return "sip/sipConsumeView";
    }

    /**
     * sip消费报表（日报）
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/getSipDayReportList")
    @ResponseBody
    public PageWrapper getSipDayReportList(HttpServletRequest request,Page page){
        logger.info("------------------------------------------------GET SipConsumeViewController getSipDayReportList START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        page.addParams("feeid",account.getFeeid());
        page.addParams("appid",sipCommonService.getSipAppInfo(request).getAppid());
        return sipManagerService.getSipDayReportList(page);
    }

    /**
     * 导出sip消费报表（日报）
     * @param page
     * @return
     */
    @RequestMapping("/downloadSipDayReportList")
    public ModelAndView downloadSipDayReportList(Page page, HttpServletRequest request) {
        logger.info("------------------------------------------------GET SipConsumeViewController downloadSipDayReportList START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        List<SipRelayInfo> sipRelayInfoList = sipManagerService.getSipRelayInfoListBySid(account.getSid());
        Map<String,String> sipAppInfoMap = new HashMap<>();
        for (SipRelayInfo sipRelayInfo: sipRelayInfoList) {
            sipAppInfoMap.put(sipRelayInfo.getSubid(), sipRelayInfo.getSubName());
        }


        page.addParams("feeid",account.getFeeid());
        page.addParams("appid",sipCommonService.getSipAppInfo(request).getAppid());
        List<Map<String, Object>> totals = sipManagerService.downloadSipDayReportList(page);
        List<Map<String, Object>> list = new ArrayList<>();
        totals.forEach((map) -> {
            Map<String, Object> reportMap = new HashMap<>();
            reportMap.put("statDay", map.get("statday").toString());
            reportMap.put("subName", sipAppInfoMap.get(map.get("subid").toString()));
            reportMap.put("subId",map.get("subid").toString());
            reportMap.put("fee", map.get("fee").toString());
            list.add(reportMap);
        });

        List<String> titles = new ArrayList<>();
        titles.add("日期");
        titles.add("子账号名称");
        titles.add("子账号 ID");
        titles.add("消费金额");

        List<String> columns = new ArrayList<>();
        columns.add("statDay");
        columns.add("subName");
        columns.add("subId");
        columns.add("fee");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "SIP消费报表（日报）");
        map.put("excelName","SIP消费报表（日报）");

        return new ModelAndView(new POIXlsView(), map);
    }


    /**
     * sip消费报表（月报）
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/getSipMonthReportList")
    @ResponseBody
    public PageWrapper getSipMonthReportList(HttpServletRequest request,Page page){
        logger.info("------------------------------------------------GET SipConsumeViewController getSipMonthReportList START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        page.addParams("feeid",account.getFeeid());
        page.addParams("appid",sipCommonService.getSipAppInfo(request).getAppid());
        return sipManagerService.getSipMonthReportList(page);
    }


    /**
     * 导出sip消费报表（月报）
     * @param page
     * @return
     */
    @RequestMapping("/downloadSipMonthReportList")
    public ModelAndView downloadSipMonthReportList(Page page, HttpServletRequest request) {
        logger.info("------------------------------------------------GET SipConsumeViewController downloadSipMonthReportList START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        List<SipRelayInfo> sipRelayInfoList = sipManagerService.getSipRelayInfoListBySid(account.getSid());
        Map<String,String> sipAppInfoMap = new HashMap<>();
        for (SipRelayInfo sipRelayInfo: sipRelayInfoList) {
            sipAppInfoMap.put(sipRelayInfo.getSubid(), sipRelayInfo.getSubName());
        }

        page.addParams("feeid",account.getFeeid());
        page.addParams("appid",sipCommonService.getSipAppInfo(request).getAppid());
        List<Map<String, Object>> totals  = sipManagerService.downloadSipMonthReportList(page);
        List<Map<String, Object>> list = new ArrayList<>();
        totals.forEach((map) -> {
            Map<String, Object> reportMap = new HashMap<>();
            reportMap.put("statDay",map.get("statday").toString());
            reportMap.put("subName", sipAppInfoMap.get(map.get("subid").toString()));
            reportMap.put("subId",map.get("subid").toString());
            reportMap.put("fee", map.get("fee").toString());
            list.add(reportMap);
        });

        List<String> titles = new ArrayList<>();
        titles.add("日期");
        titles.add("子账号名称");
        titles.add("子账号 ID");
        titles.add("消费金额");

        List<String> columns = new ArrayList<>();
        columns.add("statDay");
        columns.add("subName");
        columns.add("subId");
        columns.add("fee");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "SIP消费报表（月报）");
        map.put("excelName","SIP消费报表（月报）");

        return new ModelAndView(new POIXlsView(), map);
    }
}
