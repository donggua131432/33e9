package com.e9cloud.pcweb.traffic.action;

import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.RestStatMinuteRecordService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 话务监控
 * Created by Administrator on 2016/10/10.
 */
@Controller
@RequestMapping("/monitor")
public class TrafficMonitorController extends BaseController {

    @Autowired
    private RestStatMinuteRecordService restStatMinuteRecordService;


    // 专线语音--话务监控
    @RequestMapping("rest")
    public String rest(Model model) {
        model.addAttribute("yestoday", Tools.formatDate(Tools.addDay(new Date(), -1), "yyyy-MM-dd"));
        return "traffic/restmoni";
    }

    // 总览数据(专线语音)
    @RequestMapping("restscan")
    @ResponseBody
    private Map<String, Object> restscan(Map<String, Object> params, String stype, String day) {

        Map<String, Object> map = new HashMap<>();

        if ("n".equals(stype)) { // 实时
            params.put("startDay", Tools.getDate(0));
            params.put("endDay", Tools.getDate(1));

        } else { // 昨天
            Date date = Tools.parseDate(day, "yyyy-MM-dd");
            params.put("startDay", date);
            params.put("endDay", Tools.addDay(date, 1));
        }

        map.put("monitor", restStatMinuteRecordService.countRestScanByMin(params));
        map.put("response", restStatMinuteRecordService.countRestResponseByMin(params));

        return map;
    }

}
