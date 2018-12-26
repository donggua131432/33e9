package com.e9cloud.pcweb.bill.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.CcRecord;
import com.e9cloud.mongodb.domain.SipRecord;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.CcInfo;
import com.e9cloud.mybatis.domain.Province;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.service.CallCenterService;
import com.e9cloud.mybatis.service.ZnyddService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 通话记录
 *
 * Created by Administrator on 2016/1/26.
 */
@Controller
@RequestMapping("/znydd/bill/recentCall")
public class RecentCallController extends BaseController {

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    @Autowired
    private CallCenterService callCenterService;

    @Autowired
    private ZnyddService znyddService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        model.addAttribute("provice", callCenterService.getAllProvice(null));
//        model.addAttribute("callCenter", callCenterService.getAllCallCenterInfo(account.getSid()));
        model.addAttribute("months", Tools.getMonthsForOneYear());

        model.addAttribute("callCenter", callCenterService.getAllCallCenterInfo(account.getSid()));

        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);

        return "bill/recentCall";
    }

    @RequestMapping(value="/getAllCcInfo", method=RequestMethod.POST)
    @ResponseBody
    public List<CcInfo> getAllCcInfo(HttpServletRequest request){
        //获取当前登录的用信息
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        List<CcInfo> getAllProviceList = callCenterService.getAllCallCenterInfo(account.getSid());
        return getAllProviceList;
    }

    @RequestMapping(value="/getAllCcInfo1", method=RequestMethod.POST)
    @ResponseBody
    public List<CcInfo> getAllCcInfo1(HttpServletRequest request){
        //获取当前登录的用信息
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        List<CcInfo> getAllProviceList = callCenterService.getAllCallCenterInfo1(account.getSid());
        return getAllProviceList;
    }

    @RequestMapping(value="/getAllCallCenterInfoWithDelete", method=RequestMethod.POST)
    @ResponseBody
    public List<CcInfo> getAllCallCenterInfoWithDelete(HttpServletRequest request){
        //获取当前登录的用信息
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        List<CcInfo> getAllProviceList = callCenterService.getAllCallCenterInfoWithDelete(account.getSid());
        return getAllProviceList;
    }


    /**
     * 分页查询通话记录
     * @param page
     * @return
     */
    @RequestMapping(value = "pageRecentCall", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageRecentCall(Page page) {

        PageWrapper wrapper = znyddService.pageRecentCall(page);

        return wrapper;
    }


    /**
     * 获取智能云调度话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getZnyCallRecordList")
    @ResponseBody
    public PageWrapper getZnyCallRecordList(HttpServletRequest request, CcRecord ccRecord, Page page){
        logger.info("------------------------------------------------GET SipCallRecordController getSipCallRecordList START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        //获取通话记录总数量
        Query queryList = new Query();
        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
        queryList.addCriteria(Criteria.where("sid").is(account.getSid()));


        if(ccRecord.getClosureTime() !=null && ccRecord.getClosureTime1() !=null){
            queryList.addCriteria(Criteria.where("closureTime").gte(ccRecord.getClosureTime()).lte(ccRecord.getClosureTime1()));
        }else{
            //获取通话记录总数量
            if(ccRecord.getClosureTime() !=null){
                queryList.addCriteria(Criteria.where("closureTime").gte(ccRecord.getClosureTime()));
            }else if(ccRecord.getClosureTime1() !=null){
                queryList.addCriteria(Criteria.where("closureTime").lte(ccRecord.getClosureTime1()));
            } else{
                queryList.addCriteria(Criteria.where("closureTime").is(""));
            }
        }

        if(!"".equals(ccRecord.getSubid()) && ccRecord.getSubid() !=null){
            queryList.addCriteria(Criteria.where("subid").is(ccRecord.getSubid()));
        }

        if(!"".equals(ccRecord.getPname()) && ccRecord.getPname() !=null){
            queryList.addCriteria(Criteria.where("pname").is(ccRecord.getPname()));
        }


        if(!"".equals(ccRecord.getOperator()) && ccRecord.getOperator() !=null){
            if ("06".equals(ccRecord.getOperator())) {
                queryList.addCriteria(Criteria.where("operator").in(ccRecord.getOperator(), null));
            } else {
                queryList.addCriteria(Criteria.where("operator").is(ccRecord.getOperator()));
            }
        }
        if(ccRecord.getConnectSucc() != null){
            queryList.addCriteria(Criteria.where("connectSucc").is(ccRecord.getConnectSucc()));
        }

        if(!"".equals(ccRecord.getAbLine()) && ccRecord.getAbLine() !=null){
            queryList.addCriteria(Criteria.where("abLine").is(ccRecord.getAbLine()));
        }

        if(!"".equals(ccRecord.getZj()) && ccRecord.getZj() !=null){
            queryList.addCriteria(Criteria.where("zj").is(ccRecord.getZj()));
        }

        if(!"".equals(ccRecord.getBj()) && ccRecord.getBj() !=null){
            queryList.addCriteria(Criteria.where("bj").is(ccRecord.getBj()));
        }
        int count =(int)(mongoDBDao.count(queryList, CcRecord.class,"ccenterRecord"));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "closureTime")));
        List<CcRecord> ccRecordList = mongoDBDao.find(queryList, CcRecord.class, "ccenterRecord");
        pageWrapper.setRows(ccRecordList);
        return  pageWrapper;
    }


}
