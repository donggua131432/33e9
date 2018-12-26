package com.e9cloud.pcweb.traffic.action;

import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.CcMonitor;
import com.e9cloud.mybatis.service.SmartCloudTrafficMgrService;
import com.e9cloud.mybatis.service.SysAavrService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.app.biz.AppCommonService;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 话务监控
 * Created by dukai on 2016/08/01.
 */
@Controller
@RequestMapping("/smartCloud/traffic/monitor")
public class TrafficMonitorController extends BaseController{

    @Autowired
    private SmartCloudTrafficMgrService smartCloudTrafficMgrService;

    @Autowired
    private AppCommonService appCommonService;

    @Autowired
    private SysAavrService sysAavrService;

    //跳转至话务监控页面
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        logger.info("------------------------------------------------GET TrafficMonitorController index START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        model.addAttribute("ccInfoList", smartCloudTrafficMgrService.findCcInfoListBySid(account.getSid()));
        model.addAttribute("sysAvar",sysAavrService.getSysAvarByVar("STAT_MONITOR_CC"));
        return "traffic/traffic_monitor";
    }

    /**
     * 获取监控信息列表
     * @param request
     * @param params
     * @param subid
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getCcMonitorData")
    @ResponseBody
    private Map<String, Object> getCcMonitorData(HttpServletRequest request, Map<String, Object> params, String subid) throws ParseException {
        logger.info("------------------------------------------------GET TrafficMonitorController getCcMonitorData START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();

        params.put("startDay", appCommonService.getDayByType("beforeHour3"));
        params.put("endDay", new Date());
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        params.put("sid",account.getSid());
        params.put("subid",subid);

        map.put("ccMonitor", smartCloudTrafficMgrService.selectCcMinuteList(params));
        map.put("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        return map;
    }
}
