package com.e9cloud.pcweb.monitor.action;

import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.RestStatMinuteRecordService;
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
 * Rest接口错误码监控
 * Created by dukai on 2016/12/07.
 */
@Controller
@RequestMapping("/monitor")
public class RestApiMonitorController extends BaseController{

    @Autowired
    private TrafficMonitorService trafficMonitorService;

    @Autowired
    private SysAavrService sysAavrService;

    @RequestMapping("/restApiMonitor")
    public String restApiMonitor(Model model) {
        model.addAttribute("yesterday", Tools.formatDate(Tools.addDay(new Date(), -1), "yyyy-MM-dd"));
        model.addAttribute("sysAvar",sysAavrService.getSysAvarByVar("STAT_RESPONSE_REST"));
        return "monitor/restApiMonitor";
    }


    //Rest接口错误码 曲线图统计 饼状图
    @RequestMapping("/restApiMonitorData")
    @ResponseBody
    private Map<String, Object> restApiMonitorData(Map<String, Object> params, String sType, String day) throws ParseException {
        logger.info("------------------------------------------------GET RestApiMonitorController restApiMonitorData START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();

        if ("now".equals(sType)) { // 实时
            params.put("startDay", Tools.getDate(0));
            params.put("endDay", new Date());
        } else {
            Date date = Tools.parseDate(day, "yyyy-MM-dd");
            params.put("startDay", date);
            params.put("endDay", Tools.addDay(date,1));
        }

        map.put("monitor", trafficMonitorService.countRestApiScanByMin(params));
        map.put("monitorErrorCode", trafficMonitorService.countRestApiScanByCode(params));
        map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        return map;
    }
}
