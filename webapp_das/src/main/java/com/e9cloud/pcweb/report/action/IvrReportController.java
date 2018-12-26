package com.e9cloud.pcweb.report.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.service.IvrDayRecordService;
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
 * 云总机话单
 * Created by hzd on 2016/12/1.
 */
@Controller
@RequestMapping("/ivr")
public class IvrReportController extends BaseController{

    @Autowired
    private IvrDayRecordService ivrDayRecordService;
    /////////////////////////////////v/////////// sp start //////////////////////////////////////////////

    // 跳转到云总机的话务报表页面
    @RequestMapping("/toIvrList")
    public String toSpList() {
        return "report/ivrreportlist";
    }

    /**
     * 获取话务报表列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageIvrList")
    @ResponseBody
    public PageWrapper pageIvrRecord( Page page){
        logger.info("------------------- IvrReportController pageIvrRecord START -----------------------------");
        page.getParams().get("reportType");
        logger.info("================"+page.getParams().get("reportType"));
        PageWrapper pageWrapper = ivrDayRecordService.pageIvrList(page);
        return pageWrapper;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadIvrReport")
    public ModelAndView downloadIvrReport(Page page) {
        logger.info("=====================================IvrReportController downloadIvrReport Execute=====================================");

        logger.info("=========================="+page.getParams().get("reportType"));
        List<Map<String, Object>> totals = ivrDayRecordService.downloadIvrReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> IvrDayRecordReportMap = new HashMap<String, Object>();
            if(map.get("statday")!=null){
                if ("2".equals(page.getParams().get("reportType"))){
                    IvrDayRecordReportMap.put("statday", map.get("statday").toString().substring(0,7));
                }else {
                    IvrDayRecordReportMap.put("statday", map.get("statday"));
                }
            }else {
                IvrDayRecordReportMap.put("statday", "");
            }
            IvrDayRecordReportMap.put("sid", map.get("sid"));
            if(map.get("companyName")!=null){
                IvrDayRecordReportMap.put("companyName", map.get("companyName").toString());
            }else {
                IvrDayRecordReportMap.put("companyName", "");
            }
            if(map.get("rcdtype")!=null){
                if("CallInSip".equals(map.get("rcdtype").toString())){
                    IvrDayRecordReportMap.put("rcdtype", "呼入总机-SIP");
                }else if("CallInNonSip".equals(map.get("rcdtype").toString())){
                    IvrDayRecordReportMap.put("rcdtype", "呼入总机-非SIP");
                }else if("CallInDirect".equals(map.get("rcdtype").toString())){
                    IvrDayRecordReportMap.put("rcdtype", "呼入-直呼");
                }else if("CallOutLocal".equals(map.get("rcdtype").toString())){
                    IvrDayRecordReportMap.put("rcdtype", "呼出市话");
                }else if("CallOutNonLocal".equals(map.get("rcdtype").toString())){
                    IvrDayRecordReportMap.put("rcdtype", "呼出长途");
                }
            }else {
                IvrDayRecordReportMap.put("rcdtype", "");
            }

            if(map.get("callcnt")!=null){
                IvrDayRecordReportMap.put("callcnt", map.get("callcnt").toString());
            }else {
                IvrDayRecordReportMap.put("callcnt", "");
            }

            if(map.get("callRate")!=null){
                IvrDayRecordReportMap.put("callRate", map.get("callRate"));
            }else{
                IvrDayRecordReportMap.put("callRate", "0.00%");
            }
            if(map.get("callAnswerRate")!=null){
                IvrDayRecordReportMap.put("callAnswerRate", map.get("callAnswerRate"));
            }else{
                IvrDayRecordReportMap.put("callAnswerRate", "0.00%");
            }
            IvrDayRecordReportMap.put("thscsum", map.get("thscsum"));
            IvrDayRecordReportMap.put("jfscsum", map.get("jfscsum"));
            IvrDayRecordReportMap.put("pjsc", map.get("pjsc"));
            IvrDayRecordReportMap.put("lyscsum", map.get("lyscsum"));
            IvrDayRecordReportMap.put("jflyscsum", map.get("jflyscsum"));
            if(map.get("fee")!=null){
                IvrDayRecordReportMap.put("fee", map.get("fee").toString());
            }else {
                IvrDayRecordReportMap.put("fee", "");
            }
            IvrDayRecordReportMap.put("recordingfee", map.get("recordingfee"));
            list.add(IvrDayRecordReportMap);
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
        columns.add("rcdtype");
        columns.add("callcnt");
        columns.add("callRate");
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
        map.put("title", "云总机话务报表信息");
        map.put("excelName","云总机话务报表信息");
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
