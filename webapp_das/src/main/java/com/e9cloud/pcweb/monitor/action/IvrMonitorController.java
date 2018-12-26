package com.e9cloud.pcweb.monitor.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.SipManagerService;
import com.e9cloud.mybatis.service.SysAavrService;
import com.e9cloud.mybatis.service.TrafficMonitorService;
import com.e9cloud.mybatis.service.UserAdminService;
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
 * 云总机话务监控
 * Created by Dell on 2017/3/1.
 */
@Controller
@RequestMapping("/monitor")
public class IvrMonitorController extends BaseController{

    @Autowired
    private TrafficMonitorService trafficMonitorService;

    @Autowired
    private SysAavrService sysAavrService;

//    @Autowired
//    private UserAdminService userAdminService;
//
//    @Autowired
//    private SipManagerService sipManagerService;

    @RequestMapping("/ivrMonitor")
    public String sipMonitor(Model model) {
        model.addAttribute("yesterday", Tools.formatDate(Tools.addDay(new Date(), -1), "yyyy-MM-dd"));
        model.addAttribute("sysAvar",sysAavrService.getSysAvarByVar("STAT_MONITOR_SIP"));
        return "monitor/ivrMonitor";
    }

    // 总览数据(云总机)
    @RequestMapping("/ivrMonitorData")
    @ResponseBody
    private Map<String, Object> ivrMonitorData(Map<String, Object> params, String sType, String day) throws ParseException {
        logger.info("------------------------------------------------GET IvrMonitorController ivrMonitorData START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();
        if ("now".equals(sType)) { // 实时
            params.put("startDay", Tools.getDate(0));
            params.put("endDay", new Date());
        } else {
            Date date = Tools.parseDate(day, "yyyy-MM-dd");
            params.put("startDay", date);
            params.put("endDay", Tools.addDay(date,1));
        }
        map.put("monitor", trafficMonitorService.countEccScanByMin(params));
        map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        return map;
    }

}
