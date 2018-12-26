package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.MD5Util;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.e9cloud.mongodb.domain.MaskBRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by DELL on 2017/4/18.
 */
@Controller
@RequestMapping("/maskBRecord")
public class MaskBRecordController extends BaseController {

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    /**
     * 通话记录
     */
    @RequestMapping(value = "/callRecord")
    public String callRecord(HttpServletRequest request, Model model) {
        logger.info("------------------------------------------------GET MaskBRecordController callRecord START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute("userInfo");
        String md5Str = MD5Util.string32MD5(account.getSid()+":"+account.getToken());
        model.addAttribute("auth",md5Str);

        return "app/callRecordListMaskB";
    }

    /**
     * 获取话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCallRecordList")
    @ResponseBody
    public PageWrapper getCallRecordList(HttpServletRequest request, MaskBRecord maskBRecord, Page page){
        logger.info("------------------------------------------------GET MaskBRecordController getCallRecordList START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = this.getCurrUser(request);
        //获取通话记录总数量
        Query queryList = new Query();
        if(!"".equals(maskBRecord.getAppid()) && maskBRecord.getAppid() != null){
            queryList.addCriteria(Criteria.where("appid").is(maskBRecord.getAppid()));
        }else{
            queryList.addCriteria(Criteria.where("appid").is(""));
        }


        if(!"".equals(maskBRecord.getCallid()) && maskBRecord.getCallid() != null){
            queryList.addCriteria(Criteria.where("callid").is(maskBRecord.getCallid()));
        }


        if(!"".equals(maskBRecord.getTelX()) && maskBRecord.getTelX() != null){
            queryList.addCriteria(Criteria.where("telX").is(maskBRecord.getTelX()));
        }


        if(!"".equals(maskBRecord.getTelA()) && maskBRecord.getTelA() != null){
//            Criteria cr = new Criteria();
//            queryList.addCriteria(cr.orOperator(cr.andOperator(Criteria.where("telA").is(maskBRecord.getTelX()),Criteria.where("calltype").is("10")),cr.andOperator(Criteria.where("telB").is(maskBRecord.getTelX()),Criteria.where("calltype").is("11"))));
            queryList.addCriteria(Criteria.where("telA").is(maskBRecord.getTelA()));
        }
        if(!"".equals(maskBRecord.getTelB()) && maskBRecord.getTelB() != null){
//            Criteria cr = new Criteria();
//            queryList.addCriteria(cr.orOperator(cr.andOperator(Criteria.where("telA").is(maskBRecord.getTelX()),Criteria.where("calltype").is("11")),cr.andOperator(Criteria.where("telB").is(maskBRecord.getTelX()),Criteria.where("calltype").is("10"))));
            queryList.addCriteria(Criteria.where("telB").is(maskBRecord.getTelB()));
        }
        if(!"".equals(maskBRecord.getConnectSucc()) && maskBRecord.getConnectSucc() != null){
            queryList.addCriteria(Criteria.where("connectSucc").is(maskBRecord.getConnectSucc()));
        }

        if(!"".equals(maskBRecord.getReleasedir()) && maskBRecord.getReleasedir() != null){
            queryList.addCriteria(Criteria.where("releasedir").is(maskBRecord.getReleasedir()));
        }

        if(maskBRecord.getCalltime() !=null && maskBRecord.getCalltime1() !=null){
            queryList.addCriteria(Criteria.where("calltime").gte(maskBRecord.getCalltime()).lte(maskBRecord.getCalltime1()));
        }else{
            if(maskBRecord.getCalltime() !=null){
                queryList.addCriteria(Criteria.where("calltime").gte(maskBRecord.getCalltime()));
            }else if(maskBRecord.getCalltime1() !=null){
                queryList.addCriteria(Criteria.where("calltime").lte(maskBRecord.getCalltime1()));
            }else{
                queryList.addCriteria(Criteria.where("calltime").is(""));
            }
        }

        queryList.addCriteria(Criteria.where("feeid").is(account.getFeeid()));

        int count =(int)(mongoDBDao.count(queryList, MaskBRecord.class,"maskBRecord"));
        //int count =(int)(mongoDBDao.count(queryList, RestRecord.class));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "qssj")));
        List<MaskBRecord> maskBRecordList = mongoDBDao.find(queryList, MaskBRecord.class, "maskBRecord");
        //List<MaskRecord>  maskRecordList = mongoDBDao.find(queryList, MaskRecord.class);
        pageWrapper.setRows(maskBRecordList);
        return  pageWrapper;
    }
}
