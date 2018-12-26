package com.e9cloud.pcweb.report.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.service.SipReportService;
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
 * Created by lixin on 2016/12/6.
 */
@Controller
@RequestMapping("/toSip")
public class SipReportController extends BaseController{
    @Autowired
    private SipReportService sipReportService;

    // 跳转到 sip接口业务 的话务报表页面  SipReportlist
    @RequestMapping("/toSipList")
    public String toSipList() {
        return "report/sipReportList";
    }

    /**
     * 获取话务报表列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageSipList")
    @ResponseBody
    public PageWrapper pageSipList(Page page){
        logger.info("------------------- SipReportController pageSipList START -----------------------------");
        page.getParams().get("reportType");
        PageWrapper pageWrapper = sipReportService.pageSipList(page);
        return pageWrapper;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadSipReport")
    public ModelAndView downloadSipReport(Page page) {
        logger.info("=====================================SipReportController downloadSipReport Execute=====================================");
        List<Map<String, Object>> totals = sipReportService.downloadSipReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> teleReportMap = new HashMap<String, Object>();
            if ("2".equals(page.getParams().get("reportType"))){
                teleReportMap.put("stafday", map.get("statday").toString().substring(0,7));
            }else {
                teleReportMap.put("stafday", map.get("statday"));
            }
            teleReportMap.put("businessName", map.get("businessName"));
            teleReportMap.put("sid", map.get("sid"));
            teleReportMap.put("companyName", map.get("companyName").toString());
            teleReportMap.put("subid", map.get("subid"));
            teleReportMap.put("sub_name", map.get("sub_name"));
            /*teleReportMap.put("relay_name", map.get("relay_name"));*/
            teleReportMap.put("callcnt", map.get("callcnt"));
            teleReportMap.put("callCompletingRate", map.get("callCompletingRate"));
            teleReportMap.put("answerRate", map.get("answerRate"));
            teleReportMap.put("thscsum", map.get("thscsum"));
            teleReportMap.put("jfscsum", map.get("jfscsum"));
            teleReportMap.put("pjsc", map.get("pjsc"));
            teleReportMap.put("fee", map.get("fee").toString());
            list.add(teleReportMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("日期");
        titles.add("业务名称");
        titles.add("accountID");
        titles.add("客户名称");
        titles.add("subid");
        titles.add("子账号名称");
        /*titles.add("中继名称");*/
        titles.add("呼叫总数");
        titles.add("接通率");
        titles.add("应答率");
        titles.add("累计通话时长(秒)");
        titles.add("计费通话时长(分)");
        titles.add("平均通话时长(秒)");
        titles.add("通话费用");
        List<String> columns = new ArrayList<String>();
        columns.add("stafday");
        columns.add("businessName");
        columns.add("sid");
        columns.add("companyName");
        columns.add("subid");
        columns.add("sub_name");
        /*columns.add("relay_name");*/
        columns.add("callcnt");
        columns.add("callCompletingRate");
        columns.add("answerRate");
        columns.add("thscsum");
        columns.add("jfscsum");
        columns.add("pjsc");
        columns.add("fee");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "SIP接口话务报表信息");
        map.put("excelName","SIP接口话务报表信息");
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
