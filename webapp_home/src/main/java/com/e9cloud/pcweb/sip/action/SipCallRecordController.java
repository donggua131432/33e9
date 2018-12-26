package com.e9cloud.pcweb.sip.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.SipRecord;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.sip.biz.SipCommonService;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Dukai on 2016/7/12.
 */
@Controller
@RequestMapping("/sipCallRecord")
public class SipCallRecordController extends BaseController{

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    @Autowired
    private SipCommonService sipCommonService;

    @RequestMapping("/index")
    public String index(){
        return "sip/sipCallRecord";
    }

    /**
     * 获取SIP话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getSipCallRecordList")
    @ResponseBody
    public PageWrapper getCallRecordList(HttpServletRequest request, SipRecord sipRecord, Page page){
        logger.info("------------------------------------------------GET SipCallRecordController getSipCallRecordList START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        //获取通话记录总数量
        Query queryList = new Query();
        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
        queryList.addCriteria(Criteria.where("sid").is(account.getSid()));
        queryList.addCriteria(Criteria.where("appid").is(sipCommonService.getSipAppInfo(request).getAppid()));

        /*if(!"".equals(sipRecord.getSid()) && sipRecord.getSubid() !=null){
            queryList.addCriteria(Criteria.where("subid").is(sipRecord.getSubid()));
        }else{
            queryList.addCriteria(Criteria.where("subid").is(""));
        }*/

        if(!"".equals(sipRecord.getSubid()) && sipRecord.getSubid() !=null){
            queryList.addCriteria(Criteria.where("subid").is(sipRecord.getSubid()));
        }

        if(!"".equals(sipRecord.getaTelno()) && sipRecord.getaTelno() !=null){
            queryList.addCriteria(Criteria.where("aTelno").is(sipRecord.getaTelno()));
        }

        if(!"".equals(sipRecord.getbTelno()) && sipRecord.getbTelno() !=null){
            queryList.addCriteria(Criteria.where("bTelno").is(sipRecord.getbTelno()));
        }

        if(sipRecord.getClosureTime() !=null && sipRecord.getClosureTime1() !=null){
            queryList.addCriteria(Criteria.where("closureTime").gte(sipRecord.getClosureTime()).lte(sipRecord.getClosureTime1()));
        }else{
            if(sipRecord.getClosureTime() !=null){
                queryList.addCriteria(Criteria.where("closureTime").gte(sipRecord.getClosureTime()));
            }else if(sipRecord.getClosureTime1() !=null){
                queryList.addCriteria(Criteria.where("closureTime").lte(sipRecord.getClosureTime1()));
            }else{
                queryList.addCriteria(Criteria.where("closureTime").is(""));
            }
        }


        if(sipRecord.getConnectSucc() !=null){
            queryList.addCriteria(Criteria.where("connectSucc").is(sipRecord.getConnectSucc()));
        }
        int count =(int)(mongoDBDao.count(queryList, SipRecord.class,"sipRecord"));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "closureTime")));
        List<SipRecord> sipRecordList = mongoDBDao.find(queryList, SipRecord.class, "sipRecord");
        pageWrapper.setRows(sipRecordList);
        return  pageWrapper;
    }

}
