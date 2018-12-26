package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.MD5Util;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.MaskRecord;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.CityAreaCode;
import com.e9cloud.mybatis.service.CityAreaCodeService;
import com.e9cloud.pcweb.BaseController;
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
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Dukai on 2016/2/29.
 */
@Controller
@RequestMapping("/maskRecord")
public class MaskRecordController extends BaseController {

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    @Autowired
    private CityAreaCodeService cityAreaCodeService;



    /**
     * 通话记录
     */
    @RequestMapping(value = "/callRecord")
    public String callRecord(HttpServletRequest request, Model model) {
        logger.info("------------------------------------------------GET MaskRecordController callRecord START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute("userInfo");
        String md5Str = MD5Util.string32MD5(account.getSid()+":"+account.getToken());
        model.addAttribute("auth",md5Str);

        List<CityAreaCode> cityList = cityAreaCodeService.getCityAreaCodeList(new CityAreaCode());
        model.addAttribute("cityList", cityList);

        return "app/callRecordListMask";
    }

    /**
     * 获取话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCallRecordList")
    @ResponseBody
    public PageWrapper getCallRecordList(HttpServletRequest request, MaskRecord maskRecord, Page page){
        logger.info("------------------------------------------------GET MaskRecordController getCallRecordList START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = this.getCurrUser(request);
        //获取通话记录总数量
        Query queryList = new Query();
        if(!"".equals(maskRecord.getAppid()) && maskRecord.getAppid() != null){
            queryList.addCriteria(Criteria.where("appid").is(maskRecord.getAppid()));
        }else{
            queryList.addCriteria(Criteria.where("appid").is(""));
        }
        if(!"".equals(maskRecord.getSubid()) && maskRecord.getSubid() != null){
            queryList.addCriteria(Criteria.where("subid").is(maskRecord.getSubid()));
        }

        if(!"".equals(maskRecord.getCallSid()) && maskRecord.getCallSid() != null){
            queryList.addCriteria(Criteria.where("callSid").is(maskRecord.getCallSid()));
        }

        if(!"".equals(maskRecord.getDisplayPoolCity()) && maskRecord.getDisplayPoolCity() != null){
            queryList.addCriteria(Criteria.where("displayPoolCity").is(maskRecord.getDisplayPoolCity()));
        }

        if(!"".equals(maskRecord.getAbLine()) && maskRecord.getAbLine() != null){
            if("AB".equals(maskRecord.getAbLine())){
                queryList.addCriteria(Criteria.where("abLine").in("A","B"));
            }else{
                queryList.addCriteria(Criteria.where("abLine").is(maskRecord.getAbLine()));
            }

        }

        if(!"".equals(maskRecord.getDisplay()) && maskRecord.getDisplay() !=null){
            queryList.addCriteria(new Criteria().orOperator(Criteria.where("bTelno").is(maskRecord.getDisplay()),Criteria.where("aTelno").is(maskRecord.getDisplay()),Criteria.where("display").is(maskRecord.getDisplay())));
        }

        if(maskRecord.getClosureTime() !=null && maskRecord.getClosureTime1() !=null){
            queryList.addCriteria(Criteria.where("closureTime").gte(maskRecord.getClosureTime()).lte(maskRecord.getClosureTime1()));
        }else{
            if(maskRecord.getClosureTime() !=null){
                queryList.addCriteria(Criteria.where("closureTime").gte(maskRecord.getClosureTime()));
            }else if(maskRecord.getClosureTime1() !=null){
                queryList.addCriteria(Criteria.where("closureTime").lte(maskRecord.getClosureTime1()));
            }else{
                queryList.addCriteria(Criteria.where("closureTime").is(""));
            }
        }

        if(maskRecord.getConnectSucc() !=null){
            queryList.addCriteria(Criteria.where("connectSucc").is(maskRecord.getConnectSucc()));
        }
        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));

        int count =(int)(mongoDBDao.count(queryList, MaskRecord.class,"maskRecord"));
        //int count =(int)(mongoDBDao.count(queryList, RestRecord.class));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "qssj")));
        List<MaskRecord>  maskRecordList = mongoDBDao.find(queryList, MaskRecord.class, "maskRecord");
        //List<MaskRecord>  maskRecordList = mongoDBDao.find(queryList, MaskRecord.class);
        pageWrapper.setRows(maskRecordList);
        return  pageWrapper;
    }
}
