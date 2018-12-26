package com.e9cloud.pcweb.report.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.service.VoicephoneService;
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
 * 语音通知话单
 * Created by hzd on 2016/12/1.
 */
@Controller
@RequestMapping("/voice")
public class VoiceReportController extends BaseController{

    @Autowired
    private VoicephoneService voicephoneService;
    /////////////////////////////////v/////////// Voice start //////////////////////////////////////////////

    // 跳转到 语音通知 的话务报表页面
    @RequestMapping("/toVoiceList")
    public String toVoiceList() {
        return "report/voicereportlist";
    }

    /**
     * 获取话务报表列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageVoiceList")
    @ResponseBody
    public PageWrapper pageVoiceRecord( Page page){
        logger.info("------------------- VoiceReportController pageVoiceRecord START -----------------------------");
        page.getParams().get("reportType");
        logger.info("================"+page.getParams().get("reportType"));
        PageWrapper pageWrapper = voicephoneService.pageVoiceList(page);
        return pageWrapper;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadVoiceReport")
    public ModelAndView downloadVoiceReport(Page page) {
        logger.info("=====================================VoiceReportController downloadVoiceReport Execute=====================================");

        logger.info("=========================="+page.getParams().get("reportType"));
        List<Map<String, Object>> totals = voicephoneService.downloadVoiceReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> voiceReportMap = new HashMap<String, Object>();
            if(map.get("statday")!=null){
                if ("2".equals(page.getParams().get("reportType"))){
                    voiceReportMap.put("statday", map.get("statday").toString().substring(0,7));
                }else {
                    voiceReportMap.put("statday", map.get("statday"));
                }
            }else {
                voiceReportMap.put("statday", "");
            }

            voiceReportMap.put("sid", map.get("sid"));
            if(map.get("companyName")!=null){
                voiceReportMap.put("companyName", map.get("companyName").toString());
            }else {
                voiceReportMap.put("companyName", "");
            }
            if(map.get("callcnt")!=null){
                voiceReportMap.put("callcnt", map.get("callcnt").toString());
            }else {
                voiceReportMap.put("callcnt", "");
            }
            if(map.get("callCompletingRate")!=null){
                voiceReportMap.put("callCompletingRate", map.get("callCompletingRate"));
            }else{
                voiceReportMap.put("callCompletingRate", "0.00%");
            }
            if(map.get("callAnswerRate")!=null){
                voiceReportMap.put("callAnswerRate", map.get("callAnswerRate"));
            }else{
                voiceReportMap.put("callAnswerRate", "0.00%");
            }
            voiceReportMap.put("thscsum", map.get("thscsum"));
            voiceReportMap.put("jfscsum", map.get("jfscsum"));
            voiceReportMap.put("pjsc", map.get("pjsc"));
            voiceReportMap.put("pjcs", map.get("pjcs"));
            if(map.get("fee")!=null){
                voiceReportMap.put("fee", map.get("fee").toString());
            }else {
                voiceReportMap.put("fee", "");
            }
            list.add(voiceReportMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("日期");
        titles.add("Account ID");
        titles.add("客户名称");
        titles.add("呼叫总数");
        titles.add("接通率");
        titles.add("应答率");
        titles.add("累计通话时长(秒)");
        titles.add("计费通话时长(分)");
        titles.add("平均通话时长(秒)");
        titles.add("平均试呼次数");
        titles.add("通话费用(元)");
        List<String> columns = new ArrayList<String>();
        columns.add("statday");
        columns.add("sid");
        columns.add("companyName");
        columns.add("callcnt");
        columns.add("callCompletingRate");
        columns.add("callAnswerRate");
        columns.add("thscsum");
        columns.add("jfscsum");
        columns.add("pjsc");
        columns.add("pjcs");
        columns.add("fee");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "语音通知话务报表信息");
        map.put("excelName","语音通知话务报表信息");
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
