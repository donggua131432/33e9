package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.RestStafDayRecord;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.RestStafDayRecordService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/5/26.
 */
@Controller
@RequestMapping("/restStafDayRecord")
public class RestStafDayRecordController extends BaseController {
    @Autowired
    private RestStafDayRecordService restStafDayRecordService;

    @Autowired
    private AppService appService;

    /**
     * 获取主叫通话记录统计(mysql)
     * @param request
     * @param page
     * @param restStafDayRecord
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCallStatisticsList")
    @ResponseBody
    public PageWrapper getCallStatisticsList(HttpServletRequest request, Page page, RestStafDayRecord restStafDayRecord) throws Exception{
        logger.info("------------------------------------------------GET restStafDayRecord getCallStatisticsList START----------------------------------------------------------------");
        //获取当前登录用户信息
        Account account = (Account)request.getSession().getAttribute(JSession.USER_INFO);
        //logger.info("=======================================:"+account.getFeeid()+"----------"+page.getParams().get("abLine"));
        //获取主叫通话记录总数量
        Map<String, Object> map = new HashMap<>();
        map.put("feeid",account.getFeeid());
        map.put("appid",restStafDayRecord.getAppid());
        map.put("abLine",page.getParams().get("abLine"));
        map.put("stafDay",restStafDayRecord.getStafDay());
        map.put("stafDay1",restStafDayRecord.getStafDay1());
        int count = restStafDayRecordService.getDataAnalysisCount(map);
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        map.put("start",pageWrapper.getFromIndex());
        map.put("pageSize",page.getPageSize());
        List<RestStafDayRecord> restRecordList = restStafDayRecordService.getDataAnalysisList(map);
        pageWrapper.setRows(restRecordList);
        return pageWrapper;
    }



    /**
     * 下载报表
     * @return
     */
    @RequestMapping(value = "/downLoadDataDaily", method = RequestMethod.GET)
    public ModelAndView downLoadDataDaily(HttpServletRequest request, Page page, com.e9cloud.mongodb.domain.RestStafDayRecord restStafDayRecord){
        logger.info("------------------------------------------------GET DataAnalysisController downLoadDataDaily START----------------------------------------------------------------");
        //获取当前登录用户信息
        //获取当前登录用户信息
        Account account = (Account)request.getSession().getAttribute(JSession.USER_INFO);

        //获取AppInfo集合信息
        List<AppInfo> appInfoList = appService.findALLAppInfo(account.getSid());
        Map appInfoMap = new HashMap<String,String>();
        for (AppInfo appInfo:appInfoList){
            appInfoMap.put(appInfo.getAppid(),appInfo.getAppName());
        }


        //获取主叫通话记录总数量
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("feeid",account.getFeeid());
        paramMap.put("appid",restStafDayRecord.getAppid());
        paramMap.put("abLine",page.getParams().get("abLine"));
        paramMap.put("stafDay",restStafDayRecord.getStafDay());
        paramMap.put("stafDay1",restStafDayRecord.getStafDay1());
        int count = restStafDayRecordService.getDataAnalysisCount(paramMap);
        paramMap.put("start",0);
        paramMap.put("pageSize",count);
        List<RestStafDayRecord> restRecordList = restStafDayRecordService.getDataAnalysisList(paramMap);

        List mapList = new ArrayList<Map<String, Object>>();
        if(restRecordList != null && restRecordList.size() > 0) {
            for(RestStafDayRecord restStafDayRecordResult : restRecordList) {
                Map map = new HashMap<String, Object>();
                map.put("stafDay", restStafDayRecordResult.getStafDay());
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

        List titles = new ArrayList<String>();
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

        List columns = new ArrayList<String>();
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

        Map map = new HashMap<String, Object>();
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
