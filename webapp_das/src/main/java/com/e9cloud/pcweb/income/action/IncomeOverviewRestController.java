package com.e9cloud.pcweb.income.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.mybatis.domain.RestRate;
import com.e9cloud.mybatis.domain.RestStatDayRecord;
import com.e9cloud.mybatis.service.RateService;
import com.e9cloud.mybatis.service.RestStatRecordService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.income.biz.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dukai on 2016/8/22.
 */
@Controller
@RequestMapping("/incomeOverviewRest")
public class IncomeOverviewRestController extends BaseController {

    @Autowired
    private RestStatRecordService restStatRecordService;

    @Autowired
    private RateService rateService;

    @Autowired
    private IncomeService incomeService;

    @RequestMapping
    public String index(Model model){
        logger.info("------------------------------------------------GET IncomeOverviewRestController index START----------------------------------------------------------------");
        model.addAttribute("maxMonth",new SimpleDateFormat("yyyy-MM").format(incomeService.getDayByType("beforeMonth1")));
        model.addAttribute("minMonth",new SimpleDateFormat("yyyy-MM").format(incomeService.getDayByType("beforeMonth12")));
        return "income/incomeOverviewRest";
    }

    /**
     *获取月统计的概览数据
     * @return
     */
    @RequestMapping("/getMonthRestOverview")
    @ResponseBody
    public JSonMessage getMonthRestOverview(HttpServletRequest request) throws Exception{
        logger.info("------------------------------------------------GET IncomeOverviewRestController getMonthRestOverview START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();
        map.put("stafDay", new SimpleDateFormat("yyyy-MM").parse(request.getParameter("stafDay")));
        RestStatDayRecord restStatDayRecord = restStatRecordService.getMonthRestOverview(map);
        RestRate restRate = rateService.getStandardRestRate("0");
        if(restStatDayRecord != null){
            map.put("receivableFee",restStatDayRecord.getTotalFee());
            map.put("callSumFee",restStatDayRecord.getFee());
            map.put("timeLengthFee",restStatDayRecord.getJfscSum());
            map.put("standardFee",restRate.getResta());
            return  new JSonMessage("0","信息查询成功！",map);
        }
        return new JSonMessage("1","信息不存在！",map);
    }


    /**
     * 根据日期范围获取消费概况
     * @param request
     * @return
     */
    @RequestMapping("/getRestDayRangeRecordInfo")
    @ResponseBody
    public JSonMessage getRestDayRangeRecordInfo(HttpServletRequest request){
        logger.info("------------------------------------------------GET IncomeOverviewRestController getRestDayRangeRecordInfo START----------------------------------------------------------------");
        String dayRange = request.getParameter("dayRange");
        Map<String, Object> map = new HashMap<>();
        if("yesterday".equals(dayRange)){
            map.put("statDay",incomeService.getDayByType("yesterday"));
            map.put("statDay1",incomeService.getDayByType("yesterday"));
        }else if("7day".equals(dayRange)){
            map.put("statDay",incomeService.getDayByType("7day"));
            map.put("statDay1",new Date());
        }else if("30day".equals(dayRange)){
            map.put("statDay",incomeService.getDayByType("30day"));
            map.put("statDay1",new Date());
        }
        RestStatDayRecord restStatDayRecord = restStatRecordService.getRestDayRangeRecordInfo(map);
        if(restStatDayRecord != null){
            return new JSonMessage("0","信息查询成功！",restStatDayRecord);
        }
        return new JSonMessage("1","信息不存在！");
    }

    /**
     * 根据日期范围获取客户消费Top10
     * @param page
     * @return
     */
    @RequestMapping("/getRestDayRangeRecordTopTen")
    @ResponseBody
    public PageWrapper getRestDayRangeRecordTopTen(Page page){
        logger.info("------------------------------------------------GET IncomeOverviewRestController getRestDayRangeRecordTopTen START----------------------------------------------------------------");
        String dayRange = page.getParams().get("dayRange").toString();
        Map<String, Object> map = new HashMap<>();
        if("yesterday".equals(dayRange)){
            map.put("statDay",incomeService.getDayByType("yesterday"));
            map.put("statDay1",incomeService.getDayByType("yesterday"));
            page.setParams(map);
        }else if("7day".equals(dayRange)){
            map.put("statDay",incomeService.getDayByType("7day"));
            map.put("statDay1",new Date());
            page.setParams(map);
        }else if("30day".equals(dayRange)){
            map.put("statDay",incomeService.getDayByType("30day"));
            map.put("statDay1",new Date());
            page.setParams(map);
        }
        return restStatRecordService.getRestDayRangeRecordTopTen(page);
    }
}
