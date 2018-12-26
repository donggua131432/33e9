package com.e9cloud.pcweb.report.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.CcphoneService;
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
 * 智能云调度话单
 * Created by hzd on 2016/12/1.
 */
@Controller
@RequestMapping("/cc")
public class CcReportController extends BaseController{

    @Autowired
    private CcphoneService ccphoneService;
    /////////////////////////////////v/////////// cc start //////////////////////////////////////////////

    // 跳转到 语音通知 的话务报表页面
    @RequestMapping("/toCcList")
    public String toCcList() {
        return "report/ccreportlist";
    }

    /**
     * 获取话务报表列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageCcList")
    @ResponseBody
    public PageWrapper pageCcRecord( Page page){
        logger.info("------------------- TeleReportController pageTeleRecord START -----------------------------");
        page.getParams().get("reportType");
        logger.info("================"+page.getParams().get("reportType"));
        PageWrapper pageWrapper = ccphoneService.pageCcList(page);
        return pageWrapper;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadCcReport")
    public ModelAndView downloadCcReport(Page page) {
        logger.info("=====================================CcReportController downloadCcReport Execute=====================================");

        logger.info("=========================="+page.getParams().get("reportType"));
        List<Map<String, Object>> totals = ccphoneService.downloadCcReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> ccReportMap = new HashMap<String, Object>();
            if(map.get("statday")!=null){
                if ("2".equals(page.getParams().get("reportType"))){
                    ccReportMap.put("statday", map.get("statday").toString().substring(0,7));
                }else {
                    ccReportMap.put("statday", map.get("statday"));
                }
            }else {
                ccReportMap.put("statday", "");
            }
            if(map.get("sid")!=null){
                ccReportMap.put("sid", map.get("sid"));
            }else {
                ccReportMap.put("sid", "");
            }
            if(map.get("companyName")!=null){
                ccReportMap.put("companyName", map.get("companyName").toString());
            }else {
                ccReportMap.put("companyName", "");
            }
            if(map.get("abline")!=null){
                if("I".equals(map.get("abline").toString())){
                    ccReportMap.put("abline", "呼入");
                }else if("O".equals(map.get("abline").toString())){
                    ccReportMap.put("abline", "呼出");
                }
            }else {
                ccReportMap.put("abline", "");
            }

            ccReportMap.put("subid", map.get("subid"));
            if(map.get("ccname")!=null&&map.get("ccname")!=""){
                ccReportMap.put("ccname", map.get("ccname").toString());
            }else {
                ccReportMap.put("ccname", "");
            }

            ccReportMap.put("operator",Tools.decode(map.get("operator"),"00","移动","01","联通","02","电信","其他"));

            /*if(map.get("operator")!=null){
                if("00".equals(map.get("operator").toString())){
                    ccReportMap.put("operator", "中国移动");
                }else if("01".equals(map.get("operator").toString())){
                    ccReportMap.put("operator", "中国联通");
                }else if("02".equals(map.get("operator").toString())){
                    ccReportMap.put("operator", "中国电信");
                }else if("03".equals(map.get("operator").toString())){
                    ccReportMap.put("operator", "广电");
                }else{
                    ccReportMap.put("operator", "虚拟运营商");
                }
            }else {
                ccReportMap.put("operator", "");
            }*/

            if(map.get("callcnt")!=null){
                ccReportMap.put("callcnt", map.get("callcnt").toString());
            }else {
                ccReportMap.put("callcnt", "");
            }

            if(map.get("callCompletingRate")!=null){
                ccReportMap.put("callCompletingRate", map.get("callCompletingRate"));
            }else{
                ccReportMap.put("callCompletingRate", "0.00%");
            }
            if(map.get("callAnswerRate")!=null){
                ccReportMap.put("callAnswerRate", map.get("callAnswerRate"));
            }else{
                ccReportMap.put("callAnswerRate", "0.00%");
            }
            ccReportMap.put("thscsum", map.get("thscsum"));
            ccReportMap.put("jfscsum", map.get("jfscsum"));
            ccReportMap.put("pjsc", map.get("pjsc"));
            if(map.get("fee")!=null){
                ccReportMap.put("fee", map.get("fee").toString());
            }else {
                ccReportMap.put("fee", "");
            }

            list.add(ccReportMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("日期");
        titles.add("Account ID");
        titles.add("客户名称");
        titles.add("呼叫类型");
        titles.add("Sub ID");
        titles.add("呼叫中心名称");
        titles.add("运营商");
        titles.add("呼叫总数");
        titles.add("接通率");
        titles.add("应答率");
        titles.add("累计通话时长(秒)");
        titles.add("计费通话时长(分)");
        titles.add("平均通话时长(秒)");
        titles.add("通话费用(元)");
        List<String> columns = new ArrayList<String>();
        columns.add("statday");
        columns.add("sid");
        columns.add("companyName");
        columns.add("abline");
        columns.add("subid");
        columns.add("ccname");
        columns.add("operator");
        columns.add("callcnt");
        columns.add("callCompletingRate");
        columns.add("callAnswerRate");
        columns.add("thscsum");
        columns.add("jfscsum");
        columns.add("pjsc");
        columns.add("fee");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "智能云调度话务报表信息");
        map.put("excelName","智能云调度话务报表信息");
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
