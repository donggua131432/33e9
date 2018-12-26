package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.VoiceVerifyRecord;
import com.e9cloud.mybatis.domain.VoiceverifyTemp;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hzd on 2017/5/4.
 */
@Controller
@RequestMapping("/voiceVerifyRecord")
public class VoiceVerifyRecordController extends BaseController {

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    /**
     * 通话记录列表
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        return "app/voiceVerifyRecordList";
    }



    /**
     * 获取话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCallRecordList")
    @ResponseBody
    public PageWrapper getCallRecordList(HttpServletRequest request, VoiceVerifyRecord voiceVerifyRecord, Page page){
        logger.info("------------------------------------------------GET VoiceVerifyRecordController getCallRecordList START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);

        VoiceverifyTemp voiceverifyTemp = new VoiceverifyTemp();
        voiceverifyTemp.setAppid(voiceVerifyRecord.getAppid());


        //获取通话记录总数量
        Query queryList = new Query();
//        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));

        if(!"".equals(voiceVerifyRecord.getAppid()) && voiceVerifyRecord.getAppid() !=null){
            queryList.addCriteria(Criteria.where("appid").is(voiceVerifyRecord.getAppid()));
        }else{
            queryList.addCriteria(Criteria.where("appid").is(""));
        }

        if(!"".equals(voiceVerifyRecord.getCallSid()) && voiceVerifyRecord.getCallSid() !=null){
            queryList.addCriteria(Criteria.where("callSid").is(voiceVerifyRecord.getCallSid()));
        }

        if(!"".equals(voiceVerifyRecord.getTemplateId()) && voiceVerifyRecord.getTemplateId() !=null){
            queryList.addCriteria(Criteria.where("templateId").is(voiceVerifyRecord.getTemplateId()));
        }

        if(!"".equals(voiceVerifyRecord.getDisplay()) && voiceVerifyRecord.getDisplay() !=null){
            queryList.addCriteria(Criteria.where("display").is(voiceVerifyRecord.getDisplay()));
        }
        if(!"".equals(voiceVerifyRecord.getBj()) && voiceVerifyRecord.getBj() !=null){
            queryList.addCriteria(Criteria.where("bj").is(voiceVerifyRecord.getBj()));
        }

        if(voiceVerifyRecord.getCallTime() !=null && voiceVerifyRecord.getCallTime1() !=null){
            queryList.addCriteria(Criteria.where("closureTime").gte(voiceVerifyRecord.getCallTime()).lte(voiceVerifyRecord.getCallTime1()));
        }else{
            if(voiceVerifyRecord.getCallTime() !=null){
                queryList.addCriteria(Criteria.where("closureTime").gte(voiceVerifyRecord.getCallTime()));
            }else if(voiceVerifyRecord.getCallTime1() !=null){
                queryList.addCriteria(Criteria.where("closureTime").lte(voiceVerifyRecord.getCallTime1()));
            }else{
                queryList.addCriteria(Criteria.where("closureTime").is(""));
            }
        }

        if(voiceVerifyRecord.getConnectStatus() !=null){
            queryList.addCriteria(Criteria.where("connectStatus").is(voiceVerifyRecord.getConnectStatus().booleanValue()));
        }

        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
        int count =(int)(mongoDBDao.count(queryList, VoiceVerifyRecord.class,"voiceVerifyRecord"));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "qssj")));
        List<VoiceVerifyRecord> voiceVerifyRecordList = mongoDBDao.find(queryList, VoiceVerifyRecord.class, "voiceVerifyRecord");
        pageWrapper.setRows(voiceVerifyRecordList);
        return  pageWrapper;
    }

}
