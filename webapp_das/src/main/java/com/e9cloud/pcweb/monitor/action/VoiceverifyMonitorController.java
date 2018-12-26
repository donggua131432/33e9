package com.e9cloud.pcweb.monitor.action;

import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.SysAavrService;
import com.e9cloud.mybatis.service.TrafficScanService;
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
 * 语音验证码话务监控
 * Created by dukai on 2017/05/19.
 */
@Controller
@RequestMapping("/monitor")
public class VoiceverifyMonitorController extends BaseController{

    @Autowired
    private TrafficScanService trafficScanService;

    @Autowired
    private SysAavrService sysAavrService;

    @RequestMapping("/voiceverifyMonitor")
    public String voiceverifyMonitor(Model model) {
        model.addAttribute("yesterday", Tools.formatDate(Tools.addDay(new Date(), -1), "yyyy-MM-dd"));
        model.addAttribute("sysAvar",sysAavrService.getSysAvarByVar("STAT_MONITOR_REST"));
        return "monitor/voiceverifyMonitor";
    }


    // 总览数据(语音验证码)
    @RequestMapping("/voiceverifyMonitorData")
    @ResponseBody
    private Map<String, Object> voiceverifyMonitorData(Map<String, Object> params, String sType, String day) throws ParseException {
        logger.info("------------------------------------------------GET VoiceverifyMonitorController voiceverifyMonitorData START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();

        if ("now".equals(sType)) { // 实时
            params.put("startDay", Tools.getDate(0));
            params.put("endDay", new Date());
        } else {
            Date date = Tools.parseDate(day, "yyyy-MM-dd");
            params.put("startDay", date);
            params.put("endDay", Tools.addDay(date,1));
        }

        map.put("monitor", trafficScanService.countVoiceverifyScanByMin(params));
        map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        return map;
    }

}
