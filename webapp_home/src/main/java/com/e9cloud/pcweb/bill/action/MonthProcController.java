package com.e9cloud.pcweb.bill.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.CallRate;
import com.e9cloud.mybatis.service.CallCenterService;
import com.e9cloud.mybatis.service.FeeListService;
import com.e9cloud.mybatis.service.ZnyddService;
import com.e9cloud.pcweb.BaseController;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;

/**
 * 月结账单
 *
 * Created by Administrator on 2016/1/26.
 */
@Controller
@RequestMapping("/znydd/bill/monthProc")
public class MonthProcController extends BaseController {
    @Autowired
    private FeeListService feeListService;
    @Autowired
    private CallCenterService callCenterService;

    @Autowired
    private ZnyddService znyddService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model, String ym, String cc) {

        Account account =  (Account) request.getSession().getAttribute("userInfo");

        ym = Tools.isNotNullStr(ym) ? ym : new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -1));

        String ym2 = new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -1));
        //获取12个月前时间
        String beforcurrentDate = new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -12));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ym", ym);
        map.put("cc", cc);
        map.put("feeid",account.getFeeid());

        model.addAttribute("callCenter", callCenterService.getAllCallCenterInfo(account.getSid()));
        model.addAttribute("months", Tools.getMonthsForOneYear());
        model.addAttribute("srs", znyddService.procMonth(map));
        CallRate callRate = feeListService.findCallRateByFeeid(account.getFeeid());
        model.addAttribute("callRate", callRate);
        model.addAttribute("ym2", ym2);
        model.addAttribute("ym", ym);
        model.addAttribute("beforcurrentDate", beforcurrentDate);
        model.addAttribute("ccid", cc);


        return "bill/monthProc";
    }


    /**
     * 下载报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, String ym, Model model, String cc) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");

        ym = Tools.isNotNullStr(ym) ? ym : new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -1));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ym", ym);
        map.put("cc", cc);
        map.put("feeid",account.getFeeid());
        List<Map<String, Object>> totals = znyddService.downloadProcMonth(map);
        CallRate callRate = feeListService.findCallRateByFeeid(account.getFeeid());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map1 = new HashMap<String, Object>();

                map1.put("ym", ym);
                if ("".equals(cc) || cc == null){
                    map1.put("ccname", "全部");
                }else{
                    map1.put("ccname", String.valueOf(total.get("ccname")));
                }

                map1.put("abline", String.valueOf(total.get("abline")));

                BigDecimal result_in= callRate.getCallin().multiply(new BigDecimal(callRate.getCallinDiscount()));
                BigDecimal result_out= callRate.getCallout().multiply(new BigDecimal(callRate.getCalloutDiscount()));
                BigDecimal b2 =  new BigDecimal("1000");;
                BigDecimal b3 = result_in.divide(b2,3,BigDecimal.ROUND_HALF_EVEN);
                BigDecimal b4 = result_out.divide(b2,3,BigDecimal.ROUND_HALF_EVEN);

                if("I".equals(String.valueOf(total.get("abline")))){
                    map1.put("abline","呼入");
                    map1.put("price", String.valueOf(b3));
                }
                else{
                    map1.put("abline"  ,"呼出");
                    map1.put("price", String.valueOf(b4));
                }

                if("00".equals(String.valueOf(total.get("operator")))){
                    map1.put("operator","移动");
                }
                else if ("01".equals(String.valueOf(total.get("operator")))){
                    map1.put("operator"  ,"联通");
                }
                else if ("02".equals(String.valueOf(total.get("operator")))){
                    map1.put("operator"  ,"电信");
                }else{
                    map1.put("operator"  ,"其他");
                }

                map1.put("succcnt", String.valueOf(total.get("succcnt")));
                map1.put("fee", String.valueOf(total.get("fee")));
                map1.put("thscsum", String.valueOf(total.get("thscsum")));

                map1.put("jfscsum", String.valueOf(Tools.toSetScale(Float.parseFloat(total.get("jfscsum").toString()))));
                map1.put("sumfee", String.valueOf(total.get("fee")));
//                BigDecimal a =BigDecimal.valueOf(Double.parseDouble(total.get("fee").toString()));
//                BigDecimal.valueOf(Double.parseDouble(total.get("fee").toString()));
//                map1.put("sumfee", String.valueOf(callRate.getRelayRent().add(a)));
                list.add(map1);
            }
        }

        List<String> titles = new ArrayList<String>();
        titles.add("日期");
        titles.add("呼叫中心名称");
        titles.add("呼叫方式");
        titles.add("运营商");
        titles.add("接通数");
        titles.add("总通话时长(秒)");
        titles.add("计费通话时长(分钟)");
        titles.add("单价");
        titles.add("通话费用");
        titles.add("总消费金额");


        List<String> columns = new ArrayList<String>();
        columns.add("ym");
        columns.add("ccname");
        columns.add("abline");
        columns.add("operator");
        columns.add("succcnt");
        columns.add("thscsum");
        columns.add("jfscsum");
        columns.add("price");
        columns.add("fee");
        columns.add("sumfee");


        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("titles", titles);
        map2.put("columns", columns);
        map2.put("list", list);
        map2.put("title", "月结账单信息列表");
        map2.put("excelName","月结账单信息列表");
        return new ModelAndView(new POIXlsView(), map2);
    }

}
