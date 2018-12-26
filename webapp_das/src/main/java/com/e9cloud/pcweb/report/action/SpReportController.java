package com.e9cloud.pcweb.report.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.service.SpphoneService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 云话机话单
 * Created by hzd on 2016/12/1.
 */
@Controller
@RequestMapping("/sp")
public class SpReportController extends BaseController{

    @Autowired
    private SpphoneService SpphoneService;
    /////////////////////////////////v/////////// sp start //////////////////////////////////////////////

    // 跳转到 语音通知 的话务报表页面
    @RequestMapping("/toSpList")
    public String toSpList() {
        return "report/spreportlist";
    }

    /**
     * 获取话务报表列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageSpList")
    @ResponseBody
    public PageWrapper pageSpRecord( Page page){
        logger.info("------------------- SpReportController pageSpRecord START -----------------------------");
        page.getParams().get("reportType");
        logger.info("================"+page.getParams().get("reportType"));
        PageWrapper pageWrapper = SpphoneService.pageSpList(page);
        return pageWrapper;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadSpReport")
    public ModelAndView downloadSpReport(Page page) {
        logger.info("=====================================SpReportController downloadSpReport Execute=====================================");

        logger.info("=========================="+page.getParams().get("reportType"));
        List<Map<String, Object>> totals = SpphoneService.downloadSpReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> SpReportMap = new HashMap<String, Object>();
            if(map.get("statday")!=null){
                if ("2".equals(page.getParams().get("reportType"))){
                    SpReportMap.put("statday", map.get("statday").toString().substring(0,7));
                }else {
                    SpReportMap.put("statday", map.get("statday"));
                }
            }else {
                SpReportMap.put("statday", "");
            }
            SpReportMap.put("sid", map.get("sid"));
            if(map.get("companyName")!=null){
                SpReportMap.put("companyName", map.get("companyName").toString());
            }else {
                SpReportMap.put("companyName", "");
            }
            if(map.get("abline")!=null){
                if("1".equals(map.get("abline").toString())){
                    SpReportMap.put("abline", "回拨A路");
                }else if("2".equals(map.get("abline").toString())){
                    SpReportMap.put("abline", "回拨B路");
                }else if("3".equals(map.get("abline").toString())){
                    SpReportMap.put("abline", "SIP PHONE间回拨A路");
                }else if("4".equals(map.get("abline").toString())){
                    SpReportMap.put("abline", "SIP PHONE间回拨B路");
                }else if("5".equals(map.get("abline").toString())){
                    SpReportMap.put("abline", "直拨");
                }else if("6".equals(map.get("abline").toString())){
                    SpReportMap.put("abline", "SIP PHONE间直拨");
                }else if("7".equals(map.get("abline").toString())){
                    SpReportMap.put("abline", "回呼");
                }
            }else {
                SpReportMap.put("abline", "");
            }

            if(map.get("callcnt")!=null){
                SpReportMap.put("callcnt", map.get("callcnt").toString());
            }else {
                SpReportMap.put("callcnt", "");
            }

            if(map.get("callCompletingRate")!=null){
                SpReportMap.put("callCompletingRate", map.get("callCompletingRate"));
            }else{
                SpReportMap.put("callCompletingRate", "0.00%");
            }
            if(map.get("callAnswerRate")!=null){
                SpReportMap.put("callAnswerRate", map.get("callAnswerRate"));
            }else{
                SpReportMap.put("callAnswerRate", "0.00%");
            }
            SpReportMap.put("thscsum", map.get("thscsum"));
            SpReportMap.put("jfscsum", map.get("jfscsum"));
            SpReportMap.put("pjsc", map.get("pjsc"));
            SpReportMap.put("lyscsum", map.get("lyscsum"));
            SpReportMap.put("jflyscsum", map.get("jflyscsum"));
            if(map.get("fee")!=null){
                SpReportMap.put("fee", map.get("fee").toString());
            }else {
                SpReportMap.put("fee", "");
            }
            SpReportMap.put("recordingfee", map.get("recordingfee"));
            list.add(SpReportMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("日期");
        titles.add("Account ID");
        titles.add("客户名称");
        titles.add("呼叫类型");
        titles.add("呼叫总数");
        titles.add("接通率");
        titles.add("应答率");
        titles.add("累计通话时长(秒)");
        titles.add("计费通话时长(分)");
        titles.add("平均通话时长(秒)");
        titles.add("录音时长(秒)");
        titles.add("计费录音时长(分)");
        titles.add("通话费用(元)");
        titles.add("录音费用(元)");
        List<String> columns = new ArrayList<String>();
        columns.add("statday");
        columns.add("sid");
        columns.add("companyName");
        columns.add("abline");
        columns.add("callcnt");
        columns.add("callCompletingRate");
        columns.add("callAnswerRate");
        columns.add("thscsum");
        columns.add("jfscsum");
        columns.add("pjsc");
        columns.add("lyscsum");
        columns.add("jflyscsum");
        columns.add("fee");
        columns.add("recordingfee");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "云话机话务报表信息");
        map.put("excelName","云话机话务报表信息");
        return new ModelAndView(new POIXlsView(), map);
    }



    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        // <3> 定义转换格式
       String dateStr =  request.getParameter("datemin");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(dateStr!=null){
            if(dateStr.length()==7) {
                dateFormat = new SimpleDateFormat("yyyy-MM");
            }
        }
//        Map temp =  request.getParameterMap();
        //true代表允许输入值为空
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
