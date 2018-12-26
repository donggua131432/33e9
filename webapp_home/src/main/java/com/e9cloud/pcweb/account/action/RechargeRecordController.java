package com.e9cloud.pcweb.account.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.RechargeRecord;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.RechargeRecordService;
import com.e9cloud.pcweb.BaseController;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2016/2/19.
 */
@Controller
@RequestMapping("/rechargeRecord")
public class RechargeRecordController extends BaseController{

    private final static String RECHARGE_LOG= "acc/rechargeLog";//add  by  li.xin

    @Autowired
    private RechargeRecordService rechargeRecordService;

    /**
     * 查询充值记录
     */
    @RequestMapping("/query")
    public String queryrechargelog() throws Exception {
        return RECHARGE_LOG;
    }

    /**
     * 分页查询充值记录
     */
    @RequestMapping("/pageRechargeRecord")
    @ResponseBody
    public PageWrapper pageRechargeRecord(HttpServletRequest request, Page page) throws Exception {
        logger.info("pageRechargeRecord start");
        // TODO: 2016/2/19 page

        Account account = this.getCurrUser(request);

        Date date = page.getEndTime();
        if (date != null) {
            date = DateUtils.addDays(date, 1);
            page.setEndTime(date);
        }
        page.getParams().put("sid", account.getSid());

        return rechargeRecordService.pageRechargeRecord(page);
    }



    /**
     * 下载报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request,String type, Model model, Page page) {
        Account account = this.getCurrUser(request);
        Date date = page.getEndTime();
        if (date != null) {
            date = DateUtils.addDays(date, 1);
            page.setEndTime(date);
        }
        page.getParams().put("sid", account.getSid());

        List<RechargeRecord> totals = rechargeRecordService.getRechargeRecordList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(RechargeRecord total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("RechargeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(total.getRechargeTime()));
                map.put("Money", String.valueOf(total.getMoney()));
                map.put("Status", String.valueOf(total.getStatus()));
                if(total.getStatus()=='0'){
                    map.put("Status","已支付");
                }
                else{
                    map.put("Status"  ,"已支付");
                }
                map.put("Comment", String.valueOf(total.getComment()));
                if(total.getComment()== null){
                    map.put("Comment","线下充值");
                }
                else{
                    map.put("Comment","线下充值");
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("充值时间");
        titles.add("充值金额(元)");
        titles.add("支付状态");
        titles.add("充值类型");

        List<String> columns = new ArrayList<String>();

        columns.add("RechargeTime");
        columns.add("Money");
        columns.add("Status");
        columns.add("Comment");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "充值记录列表");
        map.put("excelName","充值记录报表");
        return new ModelAndView(new POIXlsView(), map);
    }

}
