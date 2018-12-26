package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.RestStafDayRecord;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Dukai on 2016/3/9.
 */
@Controller
@RequestMapping("/dataAnalysis")
public class DataAnalysisController extends BaseController {
    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    @Autowired
    private AppService appService;

    /**
     * 数据日报页面
     */
    @RequestMapping(value = "/dataDailyPage")
    public String callRecord() {
        return "app/dataDaily";
    }


    /**
     * 获取主叫通话记录统计(mongo)
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/getCallStatisticsList")
    @ResponseBody
    public PageWrapper getCallStatisticsList(HttpServletRequest request,Page page, RestStafDayRecord restStafDayRecord) throws Exception{
        logger.info("------------------------------------------------GET DataAnalysisController getCallStatisticsList START----------------------------------------------------------------");
        //获取当前登录用户信息
        Account account = (Account)request.getSession().getAttribute(JSession.USER_INFO);
        //查询条件
        Query querylist = new Query();
        querylist.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
        querylist.addCriteria(Criteria.where("abLine").is(page.getParams().get("abLine")));

        /*if(restStafDayRecord.getStafDay() != null){
            querylist.addCriteria(Criteria.where("stafDay").is(restStafDayRecord.getStafDay()));
        }else{
            querylist.addCriteria(Criteria.where("stafDay").is(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(page.getParams().get("stafDay")))));
        }*/

        if(restStafDayRecord.getStafDay() !=null && restStafDayRecord.getStafDay1() !=null){
            querylist.addCriteria(Criteria.where("stafDay").gte(restStafDayRecord.getStafDay()).lte(restStafDayRecord.getStafDay1()));
        }else{
            if(restStafDayRecord.getStafDay() !=null){
                querylist.addCriteria(Criteria.where("stafDay").gte(restStafDayRecord.getStafDay()));
            }else if(restStafDayRecord.getStafDay1() !=null){
                querylist.addCriteria(Criteria.where("stafDay").lte(restStafDayRecord.getStafDay1()));
            }else{
                querylist.addCriteria(Criteria.where("stafDay").is(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(page.getParams().get("stafDay")))));
            }
        }
        if(restStafDayRecord.getAppid() != null && !"".equals(restStafDayRecord.getAppid())){
            querylist.addCriteria(Criteria.where("appid").is(restStafDayRecord.getAppid()));
        }
        //获取主叫通话记录总数量
        int count =(int)(mongoDBDao.count(querylist, RestStafDayRecord.class));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        querylist.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        querylist.with(new Sort(new Sort.Order(Sort.Direction.DESC, "stafDay")));

        List<RestStafDayRecord> restRecordList = mongoDBDao.find(querylist, RestStafDayRecord.class);
        pageWrapper.setRows(restRecordList);
        return pageWrapper;
    }

    /**
     * 下载报表
     * @return
     */
    @RequestMapping(value = "/downLoadDataDaily", method = RequestMethod.GET)
    public ModelAndView downLoadDataDaily(HttpServletRequest request, Page page, RestStafDayRecord restStafDayRecord){
        logger.info("------------------------------------------------GET DataAnalysisController downLoadDataDaily START----------------------------------------------------------------");
        //获取当前登录用户信息
        Account account = (Account)request.getSession().getAttribute(JSession.USER_INFO);

        //获取AppInfo集合信息
        List<AppInfo> appInfoList = appService.findALLAppInfo(account.getSid());
        Map<String,String> appInfoMap = new HashMap<>();
        for (AppInfo appInfo:appInfoList){
            appInfoMap.put(appInfo.getAppid(),appInfo.getAppName());
        }

        //查询条件
        Query querylist = new Query();
        querylist.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
        querylist.addCriteria(Criteria.where("abLine").is(restStafDayRecord.getAbLine()));


        /*if(restStafDayRecord.getStafDay() != null){
            querylist.addCriteria(Criteria.where("stafDay").is(restStafDayRecord.getStafDay()));
        }*/
        if(restStafDayRecord.getStafDay() !=null && restStafDayRecord.getStafDay1() !=null){
            querylist.addCriteria(Criteria.where("stafDay").gte(restStafDayRecord.getStafDay()).lte(restStafDayRecord.getStafDay1()));
        }else{
            if(restStafDayRecord.getStafDay() !=null){
                querylist.addCriteria(Criteria.where("stafDay").gte(restStafDayRecord.getStafDay()));
            }else if(restStafDayRecord.getStafDay1() !=null){
                querylist.addCriteria(Criteria.where("stafDay").lte(restStafDayRecord.getStafDay1()));
            }
        }

        if(restStafDayRecord.getAppid() != null && !"".equals(restStafDayRecord.getAppid())){
            querylist.addCriteria(Criteria.where("appid").is(restStafDayRecord.getAppid()));
        }
        //排序
        querylist.with(new Sort(new Sort.Order(Sort.Direction.DESC, "stafDay")));
        //获取呼叫日报列表数据
        List<RestStafDayRecord> restRecordList = mongoDBDao.find(querylist, RestStafDayRecord.class);
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(restRecordList != null && restRecordList.size() > 0) {
            for(RestStafDayRecord restStafDayRecordResult : restRecordList) {
                Map<String, Object> map = new HashMap<>();
                map.put("stafDay",  new SimpleDateFormat("yyyy-MM-dd").format(restStafDayRecordResult.getStafDay()));
                map.put("appid", restStafDayRecordResult.getAppid());
                map.put("appName", appInfoMap.get(restStafDayRecordResult.getAppid()));
                map.put("callCnt", String.valueOf(restStafDayRecordResult.getCallCnt()));
                map.put("succCnt", String.valueOf(restStafDayRecordResult.getSuccCnt()));
                map.put("callRate", String.format("%.2f",new Float(restStafDayRecordResult.getSuccCnt()/restStafDayRecordResult.getCallCnt())*100));
                map.put("thscSum", String.valueOf(restStafDayRecordResult.getThscSum()));
                if(restStafDayRecordResult.getSuccCnt() == 0){
                    map.put("callTimeLengthAvg", "0");
                }else{
                    map.put("callTimeLengthAvg", String.format("%.2f",new Float(restStafDayRecordResult.getThscSum()/restStafDayRecordResult.getSuccCnt())*100));
                }
                map.put("jfscSum", String.valueOf(restStafDayRecordResult.getJfscSum()));
                map.put("fee", String.valueOf(restStafDayRecordResult.getFee()));
                map.put("recordingFee", String.valueOf(restStafDayRecordResult.getRecordingFee()));
                mapList.add(map);
            }
        }

        List<String> titles = new ArrayList<>();
        titles.add("日期");
        titles.add("应用ID");
        titles.add("应用名称");
        titles.add("呼叫总量");
        titles.add("接通量");
        titles.add("接通率(%)");
        titles.add("总通话时长");
        titles.add("平均通话长");
        titles.add("计费通话时长");
        titles.add("通话费用");
        titles.add("录音费用");

        List<String> columns = new ArrayList<>();
        columns.add("stafDay");
        columns.add("appid");
        columns.add("appName");
        columns.add("callCnt");
        columns.add("succCnt");
        columns.add("callRate");
        columns.add("thscSum");
        columns.add("callTimeLengthAvg");
        columns.add("jfscSum");
        columns.add("fee");
        columns.add("recordingFee");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", mapList);
        if(restStafDayRecord.getAbLine().equals("A")){
            map.put("title", "主叫数据日报");
            map.put("excelName", "主叫数据日报");
        }else if (restStafDayRecord.getAbLine().equals("B")){
            map.put("title", "被叫数据日报");
            map.put("excelName", "被叫数据日报");
        }

        return new ModelAndView(new POIXlsView(), map);
    }

}
