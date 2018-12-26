package com.e9cloud.pcweb.sip.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.CalculateUtil;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.SipDayRecord;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.service.SipManagerService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.sip.biz.SipCommonService;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Dukai on 2016/7/12.
 */
@Controller
@RequestMapping("/sipTrafficStatistics")
public class SipTrafficStatisticsController extends BaseController{

    @Autowired
    private SipManagerService sipManagerService;

    @Autowired
    private SipCommonService sipCommonService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model){
        logger.info("------------------------------------------------GET SipTrafficStatisticsController index START------------------------------------------------");
        model.addAttribute("appInfo", sipCommonService.getSipAppInfo(request));
        return "sip/sipTrafficStatistics";
    }

    @RequestMapping(value="/getSipRelayInfo", method=RequestMethod.POST)
    @ResponseBody
    public List<SipRelayInfo> getSipRelayInfo(HttpServletRequest request){
        logger.info("------------------------------------------------GET SipTrafficStatisticsController getSipRelayInfo START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        List<SipRelayInfo> sipRelayInfoList = sipManagerService.getSipRelayInfoListBySid(account.getSid());
        return sipRelayInfoList;
    }

    /**
     * 获取话务统计分页信息集合
     * @param page
     * @return
     */
    @RequestMapping("/getSipTrafficListPage")
    @ResponseBody
    public PageWrapper getSipTrafficListPage(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------GET SipTrafficStatisticsController getSipTrafficListPage START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        page.addParams("feeid",account.getFeeid());
        return sipManagerService.getSipTrafficListPage(page);
    }


    /**
     * 下载话务统计分页信息
     * @param page
     * @return
     */
    @RequestMapping("/downloadSipTrafficList")
    public ModelAndView downloadSipTrafficList(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------GET SipTrafficStatisticsController downloadSipTrafficList START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        List<SipRelayInfo> sipRelayInfoList = sipManagerService.getSipRelayInfoListBySid(account.getSid());
        Map<String,String> sipAppInfoMap = new HashMap<>();
        for (SipRelayInfo sipRelayInfo: sipRelayInfoList) {
            sipAppInfoMap.put(sipRelayInfo.getSubid(), sipRelayInfo.getSubName());
        }

        page.addParams("feeid",account.getFeeid());
        List<SipDayRecord> sipDayRecordList = sipManagerService.downloadSipTrafficList(page);
        List<Map<String, Object>> list = new ArrayList<>();

        for (SipDayRecord sipDayRecord:sipDayRecordList) {
            Map<String, Object> numMap = new HashMap<>();
            numMap.put("statday", new SimpleDateFormat("yyy-MM-dd").format(sipDayRecord.getStatday()));
            numMap.put("subName", sipAppInfoMap.get(sipDayRecord.getSubid()));
            numMap.put("subid",sipDayRecord.getSubid());

            //平均通话时长(总通话时长/呼叫成功次数)
            String callTimeAvg = "0";
            //接通率(呼叫成功次数/呼叫总数)
            String connectRate = "0.00";
            //应答率(应答数/呼叫成功次数)
            String answerRate = "0.00";

            Integer thscsum = sipDayRecord.getThscsum() == null ? 0:sipDayRecord.getThscsum();
            Integer succcnt = sipDayRecord.getSucccnt() == null ? 0:sipDayRecord.getSucccnt();
            Integer answercnt = sipDayRecord.getAnswercnt() == null ? 0:sipDayRecord.getAnswercnt();
            Integer callcnt = sipDayRecord.getCallcnt() == null ? 0:sipDayRecord.getCallcnt();

            DecimalFormat df = new DecimalFormat("#0.00");
            if(succcnt != 0){
                callTimeAvg = df.format(CalculateUtil.div(Double.valueOf(thscsum), Double.valueOf(succcnt), 2));
                answerRate = df.format(CalculateUtil.div(Double.valueOf(answercnt), Double.valueOf(succcnt), 4)*100);
            }
            if(callcnt != 0){
                connectRate = df.format(CalculateUtil.div(Double.valueOf(succcnt), Double.valueOf(callcnt), 4)*100);
            }

            numMap.put("callcnt", callcnt.toString());
            numMap.put("callTimeAvg", callTimeAvg);
            numMap.put("connectRate", connectRate);
            numMap.put("answerRate", answerRate);
            numMap.put("fee", sipDayRecord.getFee().toString());
            list.add(numMap);
        }



        List<String> titles = new ArrayList<>();
        titles.add("统计日期");
        titles.add("子账号名称");
        titles.add("子账号 ID");
        titles.add("呼叫总量");
        titles.add("平均通话时长");
        titles.add("接通率(%)");
        titles.add("应答率(%)");
        titles.add("通话费用(元)");

        List<String> columns = new ArrayList<>();
        columns.add("statday");
        columns.add("subName");
        columns.add("subid");
        columns.add("callcnt");
        columns.add("callTimeAvg");
        columns.add("connectRate");
        columns.add("answerRate");
        columns.add("fee");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "话务统计列表列表");
        map.put("excelName","话务统计列表列表");

        return new ModelAndView(new POIXlsView(), map);
    }

}
