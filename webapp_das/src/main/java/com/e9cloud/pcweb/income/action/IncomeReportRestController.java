package com.e9cloud.pcweb.income.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.mybatis.service.RestStatRecordService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.income.biz.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dukai on 2016/8/22.
 */
@Controller
@RequestMapping("/incomeReportRest")
public class IncomeReportRestController extends BaseController {

    @Autowired
    private RestStatRecordService restStatRecordService;

    @Autowired
    private IncomeService incomeService;

    @RequestMapping
    public String index(Model model){
        logger.info("------------------------------------------------GET IncomeReportRestController index START----------------------------------------------------------------");
        model.addAttribute("maxMonth",new SimpleDateFormat("yyyy-MM").format(new Date()));
        model.addAttribute("minMonth",new SimpleDateFormat("yyyy-MM").format(incomeService.getDayByType("beforeMonth12")));
        return "income/incomeReportRest";
    }


    /**
     * 获取专线语音报表信息
     * @param page
     * @return
     */
    @RequestMapping("/getRestDayMonthReportList")
    @ResponseBody
    public PageWrapper getRestDayReportList(Page page){
        logger.info("------------------------------------------------GET IncomeReportRestController getRestDayMonthReportList START----------------------------------------------------------------");
        String reportType = page.getParams().get("reportType").toString();
        if("1".equals(reportType)){
            return restStatRecordService.getRestDayReportList(page);
        }else{
            return restStatRecordService.getRestMonthReportList(page);
        }
    }

    /**
     * 获取专线语音消费总额
     * @param page
     * @return
     */
    @RequestMapping("/getRestDayMonthTotalFee")
    @ResponseBody
    public JSonMessage getRestDayMonthTotalFee(Page page){
        logger.info("------------------------------------------------GET IncomeReportRestController getRestDayMonthTotalFee START----------------------------------------------------------------");
        String reportType = page.getParams().get("reportType").toString();
        if("1".equals(reportType)){
            return new JSonMessage("0","信息查询成功！", restStatRecordService.getRestDayTotalFee(page));
        }else{
            return  new JSonMessage("0","信息查询成功！",restStatRecordService.getRestMonthTotalFee(page));
        }
    }

    /**
     * 导出专线语音报表信息
     * @param page
     * @return
     */
    @RequestMapping("/downloadRestReport")
    public ModelAndView downloadRestReport(Page page) {
        logger.info("------------------------------------------------GET IncomeReportRestController downloadRestReport START----------------------------------------------------------------");
        String reportType = page.getParams().get("reportType").toString();
        List<Map<String, Object>> totals;
        if("1".equals(reportType)){
            totals = restStatRecordService.downloadDayRestReport(page);
        }else{
            totals = restStatRecordService.downloadMonthRestReport(page);
        }
        List<Map<String, Object>> list = new ArrayList<>();

        totals.forEach((map) -> {
            Map<String, Object> m = new HashMap<>();
            if("1".equals(reportType)){
                m.put("stafday", map.get("stafday").toString().substring(0,10));
            }else{
                m.put("stafday", map.get("stafday").toString().substring(0,7));
            }
            m.put("businessName", "专线语音");
            m.put("sid", map.get("sid"));
            m.put("name", map.get("name"));
            m.put("totalFee", map.get("total_fee"));
            m.put("accountFee", map.get("account_fee"));
            m.put("grossProfit", "");
            m.put("grossProfitRate", "");
            list.add(m);
        });

        List<String> titles = new ArrayList<>();

        titles.add("日期");
        titles.add("业务名称");
        titles.add("account ID");
        titles.add("客户名称");
        titles.add("应收账款(元)");
        titles.add("账户余额(元)");
        titles.add("毛利(元)");
        titles.add("毛利率(%)");

        List<String> columns = new ArrayList<>();

        columns.add("stafday");
        columns.add("businessName");
        columns.add("sid");
        columns.add("name");
        columns.add("totalFee");
        columns.add("accountFee");
        columns.add("grossProfit");
        columns.add("grossProfitRate");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "专线语音收入报表");
        map.put("excelName","专线语音收入报表");

        return new ModelAndView(new POIXlsView(), map);
    }
}
