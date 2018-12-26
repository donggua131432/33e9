package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.MD5Util;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.RestRecord;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.RestStafDayRecord;
import com.e9cloud.mybatis.service.RestStafDayRecordService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Collections.singletonList;

/**
 * Created by Dukai on 2016/2/29.
 */
@Controller
@RequestMapping("/monthlySta")
public class MonthlyStatementController extends BaseController {

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    @Autowired
    private RestStafDayRecordService restStafDayRecordService;

    /**
     * 获取消费总览的信息
     * @param request
     * @param model
     * @param restStafDayRecord
     * @return
     */
    @RequestMapping("/getConsumeView")
    public String getConsumeView(HttpServletRequest request, Model model, RestStafDayRecord restStafDayRecord){
        logger.info("------------------------------------------------GET MonthlyStatementController getConsumeView START----------------------------------------------------------------");

        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        restStafDayRecord.setFeeid(account.getFeeid());

        //获取时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //获取上个月时间
        cal.add(Calendar.MONTH, -1);
        restStafDayRecord.setStafDay(cal.getTime());
        model.addAttribute("currentDate",sdf.format(cal.getTime()));
        //获取三个月前时间
        cal.add(Calendar.MONTH, -2);
        model.addAttribute("beforThreeMonth",sdf.format(cal.getTime()));

        //获取消费总额
        RestStafDayRecord consumeTotal = restStafDayRecordService.getMonthConsumeTotal(restStafDayRecord);
        if(consumeTotal == null) {
            consumeTotal = new RestStafDayRecord();
            consumeTotal.setTotalFee(new BigDecimal(0));
        }
        model.addAttribute("consumeTotal", consumeTotal);
        //获取近期消费走势信息
        List<RestStafDayRecord > consumeTrendList = restStafDayRecordService.getConsumeTrendView(restStafDayRecord);
        model.addAttribute("consumeTrendList", JSonUtils.toJSon(consumeTrendList));
        //获取每月应用消费概况信息
        List<RestStafDayRecord > consumeSurveyList = restStafDayRecordService.getConsumeSurveyView(restStafDayRecord);
        model.addAttribute("consumeSurveyList",JSonUtils.toJSon(consumeSurveyList));

        return "app/consumeView";
    }

    /**
     * 获取消费总览、应用概况的实时信息
     * @param request
     * @param restStafDayRecord
     * @return
     * @throws Exception
     */
    @RequestMapping("/getConsumeAlways")
    @ResponseBody
    public Map<String, Object> getConsumeAlways(HttpServletRequest request, RestStafDayRecord restStafDayRecord) throws Exception{
        logger.info("------------------------------------------------GET MonthlyStatementController getConsumeAlways START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        restStafDayRecord.setFeeid(account.getFeeid());

        //获取消费总额
        RestStafDayRecord consumeTotal = restStafDayRecordService.getMonthConsumeTotal(restStafDayRecord);
        //获取近期消费走势信息
        List<RestStafDayRecord > consumeTrendList = restStafDayRecordService.getConsumeTrendView(restStafDayRecord);
        //获取每月应用消费概况信息
        List<RestStafDayRecord > consumeSurveyList = restStafDayRecordService.getConsumeSurveyView(restStafDayRecord);
        if(consumeTrendList != null && consumeSurveyList != null){
            map.put("status","0");
            map.put("info","数据请求成功！");
            if(consumeTotal == null) {
               consumeTotal = new RestStafDayRecord();
               consumeTotal.setTotalFee(new BigDecimal(0));
            }
            //消费总额
            map.put("consumeTotal", consumeTotal);
            //近期消费走势信息
            map.put("consumeTrendList", JSonUtils.toJSon(consumeTrendList));
            //每月应用消费概况信息
            map.put("consumeSurveyList", JSonUtils.toJSon(consumeSurveyList));
            //获取日期进行转换
            map.put("currentDate",new SimpleDateFormat("yyyy年MM月").format(new SimpleDateFormat("yyyy-MM").parse(restStafDayRecord.getStafDay())));
        }else{
            map.put("status","1");
            map.put("info","数据请求失败！");
        }
        return map;
    }


    /**
     * 通话记录
     */
    @RequestMapping(value = "/callRecord")
    public String callRecord(HttpServletRequest request, Model model) {
        logger.info("------------------------------------------------GET MonthlyStatementController callRecord START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute("userInfo");
        String md5Str = MD5Util.string32MD5(account.getSid()+":"+account.getToken());
        model.addAttribute("auth",md5Str);
        return "app/callRecordList";
    }

    /**
     * 获取话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCallRecordList")
    @ResponseBody
    public PageWrapper getCallRecordList(HttpServletRequest request, RestRecord restRecord, Page page){
        logger.info("------------------------------------------------GET MonthlyStatementController getCallRecordList START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        //获取通话记录总数量
        Query queryList = new Query();
        /*queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));*/
        if(!"".equals(restRecord.getAppid()) && restRecord.getAppid() !=null){
            queryList.addCriteria(Criteria.where("appid").is(restRecord.getAppid()));
        }else{
            queryList.addCriteria(Criteria.where("appid").is(""));
        }
        if(!"".equals(restRecord.getSubid()) && restRecord.getSubid() !=null){
            queryList.addCriteria(Criteria.where("subid").is(restRecord.getSubid()));
        }
        if(!"".equals(restRecord.getDisplay()) && restRecord.getDisplay() !=null){
            queryList.addCriteria(Criteria.where("display").is(restRecord.getDisplay()));
        }
        if(!"".equals(restRecord.getCallSid()) && restRecord.getCallSid() !=null){
            queryList.addCriteria(Criteria.where("callSid").is(restRecord.getCallSid()));
        }

        if(!"".equals(restRecord.getaTelno()) && restRecord.getaTelno() !=null){
            queryList.addCriteria(Criteria.where("aTelno").is(restRecord.getaTelno()));
        }

        if(!"".equals(restRecord.getbTelno()) && restRecord.getbTelno() !=null){
            queryList.addCriteria(Criteria.where("bTelno").is(restRecord.getbTelno()));
        }
        /*if(restRecord.getJssj() !=null && restRecord.getJssj1() !=null){
            queryList.addCriteria(Criteria.where("jssj").gte(restRecord.getJssj()).lte(restRecord.getJssj1()));
        }else{
            if(restRecord.getJssj() !=null){
                queryList.addCriteria(Criteria.where("jssj").gte(restRecord.getJssj()));
            }

            if(restRecord.getJssj1() !=null){
                queryList.addCriteria(Criteria.where("jssj").lte(restRecord.getJssj1()));
            }
        }*/

        if(restRecord.getClosureTime() !=null && restRecord.getClosureTime1() !=null){
            queryList.addCriteria(Criteria.where("closureTime").gte(restRecord.getClosureTime()).lte(restRecord.getClosureTime1()));
        }else{
            if(restRecord.getClosureTime() !=null){
                queryList.addCriteria(Criteria.where("closureTime").gte(restRecord.getClosureTime()));
            }else if(restRecord.getClosureTime1() !=null){
                queryList.addCriteria(Criteria.where("closureTime").lte(restRecord.getClosureTime1()));
            }else{
                queryList.addCriteria(Criteria.where("closureTime").is(""));
            }
        }


        if(restRecord.getThsc() !=null && restRecord.getThsc1() !=null){
            queryList.addCriteria(Criteria.where("thsc").gte(restRecord.getThsc()).lte(restRecord.getThsc1()));
        }else{
            if(restRecord.getThsc() !=null){
                queryList.addCriteria(Criteria.where("thsc").gte(restRecord.getThsc()));
            }

            if(restRecord.getThsc1() !=null){
                queryList.addCriteria(Criteria.where("thsc").lte(restRecord.getThsc1()));
            }
        }

        if(restRecord.getConnectSucc() !=null){
            queryList.addCriteria(Criteria.where("connectSucc").is(restRecord.getConnectSucc()));
        }
        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
        int count =(int)(mongoDBDao.count(queryList, RestRecord.class,"restRecord"));
        //int count =(int)(mongoDBDao.count(queryList, RestRecord.class));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "qssj")));
        List<RestRecord>  restRecordList = mongoDBDao.find(queryList, RestRecord.class, "restRecord");
        //List<RestRecord>  restRecordList = mongoDBDao.find(queryList, RestRecord.class);
        pageWrapper.setRows(restRecordList);
        return  pageWrapper;
    }
}
