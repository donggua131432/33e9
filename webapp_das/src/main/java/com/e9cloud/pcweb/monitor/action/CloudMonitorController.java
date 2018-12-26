package com.e9cloud.pcweb.monitor.action;

import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.CallCenterService;
import com.e9cloud.mybatis.service.SysAavrService;
import com.e9cloud.mybatis.service.TrafficMonitorService;
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
 * 智能云调度话务监控
 * Created by dukai on 2016/12/07.
 */
@Controller
@RequestMapping("/monitor")
public class CloudMonitorController extends BaseController{

    @Autowired
    private TrafficMonitorService trafficMonitorService;

    @Autowired
    private SysAavrService sysAavrService;

    @Autowired
    private CallCenterService callCenterService;

    @RequestMapping("/cloudMonitor")
    public String cloudMonitor(Model model) {
        model.addAttribute("yesterday", Tools.formatDate(Tools.addDay(new Date(), -1), "yyyy-MM-dd"));
        model.addAttribute("sysAvar",sysAavrService.getSysAvarByVar("STAT_MONITOR_CC"));
        return "monitor/cloudMonitor";
    }


    //智能云调度
    @RequestMapping("/cloudMonitorData")
    @ResponseBody
    private Map<String, Object> cloudMonitorData(Map<String, Object> params, String sType, String day, String appid, String subid) throws ParseException {
        logger.info("------------------------------------------------GET CloudMonitorController cloudMonitorData START----------------------------------------------------------------");
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

        map.put("monitor", trafficMonitorService.countCloudScanByMin(params));
        map.put("monitorProv", trafficMonitorService.countCloudScanByProv(params));
        map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        return map;
    }


    @RequestMapping("/getCcInfo")
    @ResponseBody
    private Map<String, Object> getCcInfo(Map<String, Object> params, String sType, String day, String sid) throws ParseException {
        logger.info("------------------------------------------------GET CloudMonitorController getCcInfo START----------------------------------------------------------------");
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
        map.put("ccInfoList", callCenterService.getCcInfoByMap(params));
        return map;
    }
}
