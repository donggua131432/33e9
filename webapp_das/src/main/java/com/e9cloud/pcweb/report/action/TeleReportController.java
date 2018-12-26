package com.e9cloud.pcweb.report.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mybatis.service.TelephoneService;
import com.e9cloud.pcweb.BaseController;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
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
 * 话单
 * Created by lixin on 2016/12/4.
 */
@Controller
@RequestMapping("/tele")
public class TeleReportController extends BaseController{

    @Autowired
    private MongoDBDao mongoDBDao;

    @Autowired
    private TelephoneService telephoneService;
    //////////////////////////////////////////// rest start //////////////////////////////////////////////

    // 跳转到 专线语音 的话务报表页面
    @RequestMapping("/toTeleList")
    public String toTeleList() {
        return "report/telereportlist";
    }

    /**
     * 获取话务报表列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageTeleList")
    @ResponseBody
    public PageWrapper pageTeleRecord( Page page){
        logger.info("------------------- TeleReportController pageTeleRecord START -----------------------------");
        page.getParams().get("reportType");
        if ("3".equals(page.getParams().get("reportType"))){
            PageWrapper pageWrapper = telephoneService.pageHourTeleList(page);
            return pageWrapper;
        }else {
            PageWrapper pageWrapper = telephoneService.pageTeleList(page);
            return pageWrapper;
        }
    }


    /**
     * 获取话务报表列表（小时报表）
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageHourTeleList")
    @ResponseBody
    public PageWrapper pageHourTeleList( Page page){
        logger.info("------------------- TeleReportController pageHourTeleList START -----------------------------");
        PageWrapper pageWrapper = telephoneService.pageHourTeleList(page);
        return pageWrapper;
    }


    /**
     * 获取服务用户数
//     * @param page 分页信息
//     * @return pageWrapper
     */
    @RequestMapping("/getServiceNum")
    @ResponseBody
    public Map<String, Integer> getServiceNum(Page page){
        logger.info("------------------- TeleReportController getServiceNum START -----------------------------");
        DBObject query = new BasicDBObject();
        Set<Map.Entry<String, Object>> set = page.getParams().entrySet();
        for (Map.Entry<String, Object> entry : set) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if(null != value && !value.equals("")){
                if(key.equals("sid")){
                    query.put(key,value);
                }
            }
        }

        if(page.getDatemax() != null && page.getDatemin() != null){
            if(page.getParams()!= null&&page.getParams().size() != 0) {
                if (page.getParams().get("reportType").equals("2")) {
                    Calendar calendar_i = new GregorianCalendar();
                    calendar_i.setTime(page.getDatemin());
                    calendar_i.set(Calendar.DAY_OF_MONTH,1);
                    calendar_i.getTime();

                    Calendar calendar_a = new GregorianCalendar();
                    calendar_a.setTime(page.getDatemax());
                    calendar_a.add(Calendar.MONTH,1);
                    calendar_a.set(Calendar.DAY_OF_MONTH,1);
                    calendar_a.getTime();
                    query.put("closureTime", "{$lte:" + calendar_a.getTime() + "," + "$gt:" + calendar_i.getTime() + "}");
                }else {
                    Calendar calendar_a = new GregorianCalendar();
                    calendar_a.setTime(page.getDatemax());
                    calendar_a.add(Calendar.DAY_OF_MONTH,1);
//                    calendar_a.set(Calendar.DAY_OF_MONTH,1);
                    calendar_a.getTime();
                    query.put("closureTime","{$lte:"+calendar_a.getTime()+","+"$gt:" +page.getDatemin()+"}");
                }
            }
        }
        List aTelno = mongoDBDao.distinct("restRecord", "aTelno", query);
        List bTelno = mongoDBDao.distinct("restRecord", "bTelno", query);

        Set telno = new HashSet<>();
        telno.addAll(aTelno);
        telno.addAll(bTelno);

        Map<String, Integer> map = new HashMap<>();
        map.put("onlineNum", telno.size());

        return map;

    }


    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadTeleReport")
    public ModelAndView downloadTeleReport(Page page) {
        logger.info("=====================================TeleReportController downloadTeleReport Execute=====================================");
        if ("3".equals(page.getParams().get("reportType"))){
            List<Map<String, Object>> totals = telephoneService.downloadHourTeleReport(page);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            totals.forEach((map) -> {
                Map<String, Object> teleReportMap = new HashMap<String, Object>();
                teleReportMap.put("stafday", map.get("stathour"));
                teleReportMap.put("businessName", map.get("businessName"));
                teleReportMap.put("sid", map.get("sid"));
                teleReportMap.put("companyName", map.get("companyName").toString());
                teleReportMap.put("abline", map.get("abline").toString());
                teleReportMap.put("callcnt", map.get("callcnt").toString());
                teleReportMap.put("callCompletingRate", map.get("callCompletingRate"));
                teleReportMap.put("answerRate", map.get("answerRate"));
                teleReportMap.put("thscsum", map.get("thscsum"));
                teleReportMap.put("jfscsum", map.get("jfscsum"));
                teleReportMap.put("pjsc", map.get("pjsc"));
                teleReportMap.put("rcd_time", map.get("rcd_time"));
                teleReportMap.put("jflyscsum", map.get("jflyscsum"));
                teleReportMap.put("fee", map.get("fee").toString());
                teleReportMap.put("recordingfee", map.get("recordingfee").toString());
                list.add(teleReportMap);
            });
            List<String> titles = new ArrayList<String>();
            titles.add("日期");
            titles.add("业务名称");
            titles.add("accountID");
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
            titles.add("通话费用");
            titles.add("录音费用");
            List<String> columns = new ArrayList<String>();
            columns.add("stafday");
            columns.add("businessName");
            columns.add("sid");
            columns.add("companyName");
            columns.add("abline");
            columns.add("callcnt");
            columns.add("callCompletingRate");
            columns.add("answerRate");
            columns.add("thscsum");
            columns.add("jfscsum");
            columns.add("pjsc");
            columns.add("rcd_time");
            columns.add("jflyscsum");
            columns.add("fee");
            columns.add("recordingfee");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("titles", titles);
            map.put("columns", columns);
            map.put("list", list);
            map.put("title", "开放接口话务报表信息");
            map.put("excelName","开放接口话务报表信息");
            return new ModelAndView(new POIXlsView(), map);
        }else {
        List<Map<String, Object>> totals = telephoneService.downloadTeleReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> teleReportMap = new HashMap<String, Object>();
            if ("2".equals(page.getParams().get("reportType"))){
                teleReportMap.put("stafday", map.get("stafday").toString().substring(0,7));
            }else {
                teleReportMap.put("stafday", map.get("stafday"));
            }
            teleReportMap.put("businessName", map.get("businessName"));
            teleReportMap.put("sid", map.get("sid"));
            teleReportMap.put("companyName", map.get("companyName").toString());
            teleReportMap.put("abline", map.get("abline").toString());
            teleReportMap.put("callcnt", map.get("callcnt").toString());
            teleReportMap.put("callCompletingRate", map.get("callCompletingRate"));
            teleReportMap.put("answerRate", map.get("answerRate"));
            teleReportMap.put("thscsum", map.get("thscsum"));
            teleReportMap.put("jfscsum", map.get("jfscsum"));
            teleReportMap.put("pjsc", map.get("pjsc"));
            teleReportMap.put("rcd_time", map.get("rcd_time"));
            teleReportMap.put("jflyscsum", map.get("jflyscsum"));
            teleReportMap.put("fee", map.get("fee").toString());
            teleReportMap.put("recordingfee", map.get("recordingfee").toString());
            list.add(teleReportMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("日期");
        titles.add("业务名称");
        titles.add("accountID");
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
        titles.add("通话费用");
        titles.add("录音费用");
        List<String> columns = new ArrayList<String>();
        columns.add("stafday");
        columns.add("businessName");
        columns.add("sid");
        columns.add("companyName");
        columns.add("abline");
        columns.add("callcnt");
        columns.add("callCompletingRate");
        columns.add("answerRate");
        columns.add("thscsum");
        columns.add("jfscsum");
        columns.add("pjsc");
        columns.add("rcd_time");
        columns.add("jflyscsum");
        columns.add("fee");
        columns.add("recordingfee");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "开放接口话务报表信息");
        map.put("excelName","开放接口话务报表信息");
        return new ModelAndView(new POIXlsView(), map);
        }
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
        //true代表允许输入值为空
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
