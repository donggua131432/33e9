package com.e9cloud.pcweb.monitor.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SIP话务监控
 * Created by dukai on 2016/12/2.
 */
@Controller
@RequestMapping("/monitor")
public class SipMonitorController extends BaseController{

    @Autowired
    private TrafficMonitorService trafficMonitorService;

    @Autowired
    private SysAavrService sysAavrService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private SipManagerService sipManagerService;

    @RequestMapping("/sipMonitor")
    public String sipMonitor(Model model) {
        model.addAttribute("yesterday", Tools.formatDate(Tools.addDay(new Date(), -1), "yyyy-MM-dd"));
        model.addAttribute("sysAvar",sysAavrService.getSysAvarByVar("STAT_MONITOR_SIP"));
        return "monitor/sipMonitor";
    }


    // 总览数据(专线语音)
    @RequestMapping("/sipMonitorData")
    @ResponseBody
    private Map<String, Object> sipMonitorData(Map<String, Object> params, String sType, String day, String appid, String subid) throws ParseException {
        logger.info("------------------------------------------------GET SipMonitorController sipMonitorData START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();

        if ("now".equals(sType)) { // 实时
            params.put("startDay", Tools.getDate(0));
            params.put("endDay", new Date());
        } else {
            Date date = Tools.parseDate(day, "yyyy-MM-dd");
            params.put("startDay", date);
            params.put("endDay", Tools.addDay(date,1));
        }

        params.put("appid",appid);
        params.put("subid",subid);

        map.put("monitor", trafficMonitorService.countSipScanByMin(params));
        map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        return map;
    }


    @RequestMapping("/getCompanyNameAndSidForSelect2")
    @ResponseBody
    public PageWrapper getCompanyNameAndSidForSelect2(Page page){
        logger.info("-------------SipMonitorController getCompanyNameAndSidForSelect2 start-------------");
        String sType = Tools.toStr(page.getParams().get("sType"));
        if (Tools.isNotNullStr(sType)) {
            if ("now".equals(sType)) { // 实时
                page.setTimemin(Tools.getDate(0));
                page.setTimemax(new Date());
            } else {
                Date date = Tools.parseDate(Tools.toStr(page.getParams().get("day")), "yyyy-MM-dd");
                page.setTimemin(date);
                page.setTimemax(Tools.addDay(date,1));
            }
        }
        return userAdminService.getCompanyNameAndSidForSelect2(page);
    }


    @RequestMapping("/getSipRelayInfo")
    @ResponseBody
    private Map<String, Object> getSipRelayInfo(Map<String, Object> params, String sType, String day, String sid) throws ParseException {
        logger.info("------------------------------------------------GET SipMonitorController getSipRelayInfo START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();

        if ("now".equals(sType)) { // 实时
            params.put("startDay", Tools.getDate(0));
            params.put("endDay", new Date());
        } else {
            Date date = Tools.parseDate(day, "yyyy-MM-dd");
            params.put("startDay", date);
            params.put("endDay", Tools.addDay(date,1));
        }
        params.put("sid",sid);
        map.put("sipRelayInfoList", sipManagerService.getSipRelayInfoByMap(params));
        return map;
    }


}
