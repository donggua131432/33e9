package com.e9cloud.pcweb.ecc.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.MD5Util;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.IvrRecord;
import com.e9cloud.mongodb.domain.SipPhoneRecord;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/eccRecord")
public class EccRecordController extends BaseController{

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;


    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model){
        Account account = this.getCurrUser(request);
        String md5Str = MD5Util.string32MD5(account.getSid()+":"+account.getToken());
        model.addAttribute("auth",md5Str);
        return "ecc/eccCallRecord";
    }

    /**
     * 获取ECC话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getEccRecordList")
    @ResponseBody
    public PageWrapper getEccRecordList(HttpServletRequest request,Page page){
        logger.info("-----GET getEccRecordList START-----------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        Query queryList = new Query();
        queryList.addCriteria(Criteria.where("abLine").is("B"));
        if(!"".equals(page.getParams().get("appid")) && page.getParams().get("appid") != null){
            queryList.addCriteria(Criteria.where("appid").is(page.getParams().get("appid")));
        }else{
            queryList.addCriteria(Criteria.where("appid").is(""));
        }
        if(!"".equals(page.getParams().get("connectSucc"))&&page.getParams().get("connectSucc") != null){
            queryList.addCriteria(Criteria.where("connectSucc").is(Integer.valueOf(page.getParams().get("connectSucc").toString())));
//            queryList.addCriteria(Criteria.where("connectSucc").is(page.getParams().get("connectSucc").toString()));
        }
        if(!"".equals(page.getParams().get("callSid")) && page.getParams().get("callSid") != null){
            queryList.addCriteria(Criteria.where("callSid").is(page.getParams().get("callSid")));
        }
        if(!"".equals(page.getParams().get("zj")) && page.getParams().get("zj") != null){
            queryList.addCriteria(Criteria.where("zj").is(page.getParams().get("zj")));
        }

        if(!"".equals(page.getParams().get("bj")) && page.getParams().get("bj") != null){
            queryList.addCriteria(Criteria.where("bj").is(page.getParams().get("bj")));
        }
        if(!"".equals(page.getParams().get("display")) && page.getParams().get("display") != null){
            queryList.addCriteria(Criteria.where("display").is(page.getParams().get("display")));
        }
        if(!"".equals(page.getParams().get("rcdType")) && page.getParams().get("rcdType") != null){
            queryList.addCriteria(Criteria.where("rcdType").is(page.getParams().get("rcdType")));
        }
        if(page.getsTime() !=null && page.geteTime() !=null){
            queryList.addCriteria(Criteria.where("closureTime").gte(page.getsTime()).lte(page.geteTime()));
        }else{
            if(page.getsTime() !=null){
                queryList.addCriteria(Criteria.where("closureTime").gte(page.getsTime()));
            }else if(page.geteTime() !=null){
                queryList.addCriteria(Criteria.where("closureTime").lte(page.geteTime() ));
            }else{
                queryList.addCriteria(Criteria.where("closureTime").is(""));
            }
        }
        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
//        int count =(int)(mongoDBDao.count(queryList, SipPhoneRecord.class,"SipphoneRecord_3af915c69f8f466296d82b4c7572ab6e"));
        int count =(int)(mongoDBDao.count(queryList, IvrRecord.class,"ivrRecord"));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "qssj")));
        List<IvrRecord>  ivrRecordList = mongoDBDao.find(queryList, IvrRecord.class, "ivrRecord");
        pageWrapper.setRows(ivrRecordList);
        return  pageWrapper;
    }

}
