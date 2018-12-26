package com.e9cloud.pcweb.financial.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.PaymentRecords;
import com.e9cloud.mybatis.domain.RechargeRecords;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.domain.UserAdminHistory;
import com.e9cloud.mybatis.service.PaymentRecordsService;
import com.e9cloud.mybatis.service.RechargeRecordsService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
import com.e9cloud.thirdparty.sms.SmsUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *FinancialController负责财务管理的业务控制
 *
 * Created by wujiang on 2016/2/18.
 *
 */
@Controller
@RequestMapping(value = "/financial")
public class FinancialController extends BaseController {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private RechargeRecordsService rechargeRecordsService;

    @Autowired
    private PaymentRecordsService paymentRecordsService;

    /**
     * 充值入库
     * @return
     */
    @RequestMapping("/showRecharge")
    public String showRecharge(){
        return "financialMgr/recharge";
    }

    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage recharge(RechargeRecords rechargeRecords) throws Exception{
        logger.info("充值开始--------");
        UserAdmin userAdmin = userAdminService.findUserAdminSID(rechargeRecords.getSid());
        BigDecimal a = userAdmin.getFee();//获得充值前的账户余额
        BigDecimal bigDecimal = rechargeRecords.getMoney();
        userAdmin.setFee(bigDecimal.add(userAdmin.getFee()));
        String mobile = userAdmin.getMobile();
        String inMoney = bigDecimal.toString();//充值金额
        userAdminService.updateUserAdminWithFee(userAdmin);
        BigDecimal b = userAdmin.getFee();//获得充值之后的账户余额

        if (b.compareTo(userAdmin.getRemindFee()) == 1 ){
           userAdmin.setSmsStatus("00");
           userAdminService.updateUserAdminWithFee(userAdmin);
        }

        rechargeRecords.setStatus(1);//1.是成功 2.是失败
        String custom_name = userAdminService.getUserAdminWithCompany(userAdmin).getAuthenCompany().getName();
        rechargeRecords.setCustomerName(custom_name);//客户名称
        String login_user = UserUtil.getCurrentUser().getUsername();
        rechargeRecords.setOperator(login_user);
        logger.info("collectionDate========================="+rechargeRecords.getCollectionTime());
        Date date = new Date();
        rechargeRecords.setRechargeTime(date);
        rechargeRecords.setRechargeType(0);
        rechargeRecordsService.insertRechargeRecords(rechargeRecords);

        Map smsMap = new HashMap<String,String>();
        smsMap.put("inMoney",inMoney);
        String content = SmsUtils.genSmsContent(Constants.SEND_SMS_IN_MONEY,smsMap);
        SmsUtils.sendSms(mobile,content);
        logger.info("-----sendMobileCode-the mobile:"+mobile);
        logger.info("-----sendMobileCode-the content:"+content);

        Sender.sendMessage(userAdmin, TempCode.SEND_SMS_IN_MONEY, smsMap);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CollectionTime =format.format(rechargeRecords.getCollectionTime());
        //操作日志工具类
        LogUtil.log("账户充值", "accountID:"+rechargeRecords.getSid()+" , 客户名称："+custom_name+" , 收款时间："+CollectionTime+"  , 充值金额："+inMoney+"元 , 充值前金额"+a+"元 , 充值后金额"+b+"元", LogType.INSERT);

        return new JSonMessage("ok","");
    }

    /**
     * 充值列表
     * @return
     */
    @RequestMapping("/rechargeList")
    public String rechargeList(){
        return "financialMgr/rechargeList";
    }

    @RequestMapping("/selectRechargeRecordsPage")
    @ResponseBody
    public PageWrapper selectRechargeRecordsPage(Page page) throws Exception{
        page.setDatemax(Tools.addDay(page.getDatemax(), 1));
        return rechargeRecordsService.selectRechargeRecordsPage(page);
    }

    @RequestMapping("/showPayment")
    public String showPayment(){
        return "financialMgr/payment";
    }

    @RequestMapping(value = "payment", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage payment(PaymentRecords paymentRecords) throws Exception{
        logger.info("扣款开始--------");
        UserAdmin userAdmin = userAdminService.findUserAdminSID(paymentRecords.getSid());
        BigDecimal a = userAdmin.getFee();//获得扣款前的账户余额
        BigDecimal bigDecimal = paymentRecords.getMoney();
        BigDecimal balance = userAdmin.getFee();
        userAdmin.setFee(balance.subtract(bigDecimal));
        String mobile = userAdmin.getMobile();
        String outMoney = bigDecimal.toString();
        userAdminService.updateUserAdminWithFee(userAdmin);
        BigDecimal b = userAdmin.getFee();//获得扣款后的账户余额
        paymentRecords.setStatus(1);
        String custom_name = userAdminService.getUserAdminWithCompany(userAdmin).getAuthenCompany().getName();
        paymentRecords.setCustomerName(custom_name);
        String login_user = UserUtil.getCurrentUser().getUsername();
        paymentRecords.setOperator(login_user);
        Date date = new Date();
        paymentRecords.setPaymentTime(date);
        paymentRecordsService.insertPaymentRecords(paymentRecords);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String outTimes =format.format(date);

        Map smsMap = new HashMap<String,String>();
        smsMap.put("outMoney",outMoney);
        smsMap.put("outTimes",outTimes);
        String content = SmsUtils.genSmsContent(Constants.SEND_SMS_OUT_MONEY,smsMap);
        SmsUtils.sendSms(mobile,content);
        logger.info("-----sendMobileCode-the mobile:"+mobile);
        logger.info("-----sendMobileCode-the content:"+content);

        Sender.sendMessage(userAdmin, TempCode.SEND_SMS_OUT_MONEY, smsMap);

        //操作日志工具类
        LogUtil.log("费用扣款", "accountID:"+paymentRecords.getSid()+" , 客户名称："+custom_name+" , 扣款时间："+outTimes+"  , 扣款金额："+outMoney+"元 , 扣款前金额"+a+"元 , 扣款后金额"+b+"元", LogType.UPDATE);

        return new JSonMessage("ok","");
    }

    /**
     * 扣款列表
     * @return
     */
    @RequestMapping("/paymentList")
    public String paymentList(){
        return "financialMgr/paymentList";
    }

    @RequestMapping("/selectPaymentRecordsPage")
    @ResponseBody
    public PageWrapper selectPaymentRecordsPage(Page page) throws Exception{
        page.setDatemax(Tools.addDay(page.getDatemax(), 1));
        return paymentRecordsService.selectPaymentRecordsPage(page);
    }

    /**
     * 用户余额列表
     */
    @RequestMapping("/balanceList")
    public String balanceList() {
        return "financialMgr/balanceList";
    }

    // 分页查询历史余额列表
    @RequestMapping("/selectBalanceListPage")
    @ResponseBody
    public PageWrapper selectBalanceListPage(Page page) throws Exception{
        return userAdminService.selectBalanceListPage(page);
    }

    /**
     * 历史余额列表
     */
    @RequestMapping("/balanceHistoryList")
    public String balanceHistoryList() {
        return "financialMgr/balanceHistoryList";
    }

    // 分页查询余额列表
    @RequestMapping("/selectBalanceHistoryListPage")
    @ResponseBody
    public PageWrapper selectBalanceHistoryListPage(Page page) throws Exception{
        if (page.getDatemax() != null) {
            page.setDatemax(DateUtils.addDays(page.getDatemax(), 1));
        }
        return userAdminService.selectBalanceHistoryListPage(page);
    }

    /**
     * 下载报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(String type, Model model, Page page) {
        page.setDatemax(Tools.addDay(page.getDatemax(), 1));
        if(type.equals("balanceList")){
            List<UserAdmin> totals= userAdminService.selectBalanceLisDownload(page);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if(totals != null && totals.size() > 0) {
                for(UserAdmin total : totals) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", total.getName());
                    map.put("sid", total.getSid());
                    map.put("fee", String.valueOf(total.getFee()));
                    list.add(map);
                }
            }
            List<String> titles = new ArrayList<String>();

            titles.add("客户名称");
            titles.add("accountID");
            titles.add("可用余额");

            List<String> columns = new ArrayList<String>();

            columns.add("name");
            columns.add("sid");
            columns.add("fee");

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("titles", titles);
            map.put("columns", columns);
            map.put("list", list);
            map.put("title", "余额列表");
            map.put("excelName","余额列表");

            Object customer_name = page.getParams().get("customer_name");
            Object sid = page.getParams().get("sid");
            Object price_min = page.getParams().get("price_min");
            Object price_max = page.getParams().get("price_max");
            LogUtil.log("导出余额列表", "余额列表导出一条记录。导出内容的查询条件为："+" 余额区间： " +price_min+" -- "+price_max+"元 ， accountID："+sid+" ，客户名称："+customer_name, LogType.UPDATE);

            return new ModelAndView(new POIXlsView(), map);
        }else if(type.equals("rechargeList")){
            List<RechargeRecords> totals= rechargeRecordsService.selectRechargeRecordsDownload(page);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if(totals != null && totals.size() > 0) {
                for(RechargeRecords total : totals) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("recharge_time",Tools.formatDate(total.getRechargeTime(),"yyyy-MM-dd" ));
                    map.put("sid", total.getSid());
                    map.put("customer_name", total.getCustomerName());
                    map.put("money", String.valueOf(total.getMoney()));

                    if ("00".equals(total.getType())){
                        map.put("type", "测试金");
                    }else if ("01".equals(total.getType())){
                        map.put("type", "授信额度");
                    }else if ("02".equals(total.getType())){
                        map.put("type", "充值");
                    }else if ("03".equals(total.getType())){
                        map.put("type", "补退款");
                    }else {
                        map.put("type", "其他");
                    }

                    if ("0".equals(String.valueOf(total.getRechargeType()))){
                        map.put("RechargeType", "线下充值");
                    }else{
                        map.put("RechargeType", "线上充值");
                    }

                    map.put("operator", total.getOperator());
                    map.put("comment", total.getComment());
                    list.add(map);
                }
            }
            List<String> titles = new ArrayList<String>();

            titles.add("时间");
            titles.add("accountID");
            titles.add("客户名称");
            titles.add("充值金额");
            titles.add("充值类型");
            titles.add("充值来源");
            titles.add("操作人");
            titles.add("备注");

            List<String> columns = new ArrayList<String>();

            columns.add("recharge_time");
            columns.add("sid");
            columns.add("customer_name");
            columns.add("money");
            columns.add("type");
            columns.add("RechargeType");
            columns.add("operator");
            columns.add("comment");


            Map<String, Object> map = new HashMap<String, Object>();
            map.put("titles", titles);
            map.put("columns", columns);
            map.put("list", list);
            map.put("title", "充值列表");
            map.put("excelName","充值列表");

            Object customer_name = page.getParams().get("customer_name");
            Object sid = page.getParams().get("sid");
            Object datemin = Tools.formatDate(page.getDatemin(),"yyyy-MM-dd" );
            Object datemax = Tools.formatDate(page.getDatemax(),"yyyy-MM-dd" );

            LogUtil.log("导出充值列表", "充值列表导出一条记录。导出内容的查询条件为："+" 时间范围： " +datemin+" 至 "+datemax+" ， accountID："+sid+" ，客户名称："+customer_name, LogType.UPDATE);

            return new ModelAndView(new POIXlsView(), map);

        }else if(type.equals("paymentList")){

            List<PaymentRecords> totals= paymentRecordsService.selectPaymentRecordsDownload(page);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if(totals != null && totals.size() > 0) {
                for(PaymentRecords total : totals) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("payment_time", new SimpleDateFormat().format(total.getPaymentTime()));
                    map.put("sid", total.getSid());
                    map.put("customer_name", total.getCustomerName());
                    map.put("money", String.valueOf(total.getMoney()));
                    map.put("operator", total.getOperator());
                    map.put("comment", total.getComment());
                    list.add(map);
                }
            }
            List<String> titles = new ArrayList<String>();
            titles.add("时间");
            titles.add("accountID");
            titles.add("客户名称");
            titles.add("扣款金额");
            titles.add("操作人");
            titles.add("扣款原因");

            List<String> columns = new ArrayList<String>();

            columns.add("payment_time");
            columns.add("sid");
            columns.add("customer_name");
            columns.add("money");
            columns.add("operator");
            columns.add("comment");


            Map<String, Object> map = new HashMap<String, Object>();
            map.put("titles", titles);
            map.put("columns", columns);
            map.put("list", list);
            map.put("title", "扣费列表");
            map.put("excelName","扣费列表");
            Object datemin = Tools.formatDate(page.getDatemin(),"yyyy-MM-dd" );
            Object datemax = Tools.formatDate(page.getDatemax(),"yyyy-MM-dd" );
            LogUtil.log("导出扣款记录", "费用扣款导出一条时间范围为： " +datemin+" 至 "+datemax + " 的扣款记录。" , LogType.UPDATE);


            return new ModelAndView(new POIXlsView(), map);

        } else if (type.equals("historyBalanceList")){
            if (page.getDatemax() != null) {
                page.setDatemax(DateUtils.addDays(page.getDatemax(), 1));
            }
            List<UserAdminHistory> totals= userAdminService.selectBalanceHistoryLisDownload(page);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if(totals != null && totals.size() > 0) {
                for(UserAdminHistory total : totals) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("cday", total.getCday());
                    map.put("name", total.getName());
                    map.put("sid", total.getSid());
                    map.put("fee", String.valueOf(total.getFee()));
                    list.add(map);
                }
            }
            List<String> titles = new ArrayList<String>();

            titles.add("日期");
            titles.add("客户名称");
            titles.add("accountID");
            titles.add("可用余额");

            List<String> columns = new ArrayList<String>();

            columns.add("cday");
            columns.add("name");
            columns.add("sid");
            columns.add("fee");

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("titles", titles);
            map.put("columns", columns);
            map.put("list", list);
            map.put("title", "历史余额列表");
            map.put("excelName","历史余额列表");

            return new ModelAndView(new POIXlsView(), map);
        }
        return null;
    }

}
