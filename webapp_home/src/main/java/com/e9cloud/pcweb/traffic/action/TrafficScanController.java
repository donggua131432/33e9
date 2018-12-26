package com.e9cloud.pcweb.traffic.action;

import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.StatCcDayRecord;
import com.e9cloud.mybatis.service.SmartCloudTrafficMgrService;
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
import java.util.Date;
import java.util.List;

/**
 * 话务总览
 *
 * Created by Administrator on 2016/8/10.
 */
@Controller
@RequestMapping("/smartCloud/traffic/scan")
public class TrafficScanController extends BaseController{

    @Autowired
    private SmartCloudTrafficMgrService smartCloudTrafficMgrService;

    @Autowired
    private AppCommonService appCommonService;

    /**
     *  跳转到话务总览页面
     *
     * @return 返回话务总览页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String trafficScan(HttpServletRequest request, Model model) {
        logger.info("------------------------------------------------GET TrafficScanController trafficScan START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        model.addAttribute("ccInfoList", smartCloudTrafficMgrService.findCcInfoListBySid(account.getSid()));
        return "traffic/traffic_scan";
    }

    @RequestMapping("/getCcCallInRecordList")
    @ResponseBody
    public List<StatCcDayRecord> getCcCallInRecordList(HttpServletRequest request){
        logger.info("------------------------------------------------GET TrafficScanController getCcCallInRecordList START----------------------------------------------------------------");
        //获取当前登录用户信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        StatCcDayRecord statCcDayRecord = new StatCcDayRecord();
        statCcDayRecord.setFeeid(account.getFeeid());
        statCcDayRecord.setSubid(request.getParameter("subid"));
        statCcDayRecord.setAbline(request.getParameter("abline"));
        String timeRange = request.getParameter("timeRange");
        if("yesterday".equals(timeRange)){
            statCcDayRecord.setStatday(appCommonService.getDayByType("yesterday"));
            statCcDayRecord.setStatday1(appCommonService.getDayByType("yesterday"));
        }else if("7day".equals(timeRange)){
            statCcDayRecord.setStatday(appCommonService.getDayByType("7day"));
            statCcDayRecord.setStatday1(new Date());
        }else if("30day".equals(timeRange)){
            statCcDayRecord.setStatday(appCommonService.getDayByType("30day"));
            statCcDayRecord.setStatday1(new Date());
        }
        return smartCloudTrafficMgrService.findCcCallInRecordList(statCcDayRecord);
    }

}
