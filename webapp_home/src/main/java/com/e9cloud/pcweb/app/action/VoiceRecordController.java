package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mongodb.domain.RestRecord;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.VoiceRecord;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.TempVoice;
import com.e9cloud.mybatis.service.MouldVoiceService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lixin on 2016/9/21.
 */
@Controller
@RequestMapping("/voiceRecord")
public class VoiceRecordController extends BaseController {

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    @Autowired
    private MouldVoiceService mouldVoiceService;

    /**
     * 通话记录列表
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        return "app/voiceRecordList";
    }



    /**
     * 获取话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCallRecordList")
    @ResponseBody
    public PageWrapper getCallRecordList(HttpServletRequest request, VoiceRecord voiceRecord,Page page){
        logger.info("------------------------------------------------GET VoiceRecordController getCallRecordList START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);

        TempVoice voice = new TempVoice();
        voice.setAppid(voiceRecord.getAppid());
        voice.setName(voiceRecord.getName());


        //获取通话记录总数量
        Query queryList = new Query();
//        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));

        if(!"".equals(voiceRecord.getAppid()) && voiceRecord.getAppid() !=null){
            queryList.addCriteria(Criteria.where("appid").is(voiceRecord.getAppid()));
        }else{
            queryList.addCriteria(Criteria.where("appid").is(""));
        }

        if (Tools.isNullStr(voiceRecord.getName())&&Tools.isNullStr(voiceRecord.getTemplateId())){
//            TempVoice a = mouldVoiceService.getTempVoice(voice);
        }else if(Tools.isNullStr(voiceRecord.getName())&&Tools.isNotNullStr(voiceRecord.getTemplateId())){
            queryList.addCriteria(Criteria.where("templateId").is(voiceRecord.getTemplateId()));
        }else if(Tools.isNotNullStr(voiceRecord.getName())&&Tools.isNullStr(voiceRecord.getTemplateId())){
            TempVoice a = mouldVoiceService.getTempVoice(voice);
            if( a !=null && a.getId() != null) {
                    queryList.addCriteria(Criteria.where("templateId").is(a.getId().toString()));
            }
            if (a == null || a.getId() == null){
                queryList.addCriteria(Criteria.where("feeid").exists(false));
            }
        }else if(Tools.isNotNullStr(voiceRecord.getName())&&Tools.isNotNullStr(voiceRecord.getTemplateId())){
            TempVoice a = mouldVoiceService.getTempVoice(voice);
            if( a!=null && voiceRecord.getTemplateId().equals(String.valueOf(a.getId()))) {
                queryList.addCriteria(Criteria.where("templateId").is(voiceRecord.getTemplateId()));
            }else {
                queryList.addCriteria(Criteria.where("feeid").exists(false));
            }
        }

        if(!"".equals(voiceRecord.getDisplay()) && voiceRecord.getDisplay() !=null){
            queryList.addCriteria(Criteria.where("display").is(voiceRecord.getDisplay()));
        }
        if(!"".equals(voiceRecord.getBj()) && voiceRecord.getBj() !=null){
            queryList.addCriteria(Criteria.where("bj").is(voiceRecord.getBj()));
        }

        if(voiceRecord.getCallTime() !=null && voiceRecord.getCallTime1() !=null){
            queryList.addCriteria(Criteria.where("closureTime").gte(voiceRecord.getCallTime()).lte(voiceRecord.getCallTime1()));
        }
        else{

            if(voiceRecord.getCallTime() !=null){
                queryList.addCriteria(Criteria.where("closureTime").gte(voiceRecord.getCallTime()));
            }else if(voiceRecord.getCallTime1() !=null){
                queryList.addCriteria(Criteria.where("closureTime").lte(voiceRecord.getCallTime1()));
            }else{
                queryList.addCriteria(Criteria.where("closureTime").is(""));
            }
        }

        if(voiceRecord.getConnectStatus() !=null){
            queryList.addCriteria(Criteria.where("connectStatus").is(voiceRecord.getConnectStatus().booleanValue()));
        }

        queryList.addCriteria(Criteria.where("valid").is(true));
        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
        int count =(int)(mongoDBDao.count(queryList, VoiceRecord.class,"voiceRecord"));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "qssj")));
        List<VoiceRecord> voiceRecordList = mongoDBDao.find(queryList, VoiceRecord.class, "voiceRecord");
        pageWrapper.setRows(voiceRecordList);
        return  pageWrapper;
    }

}
