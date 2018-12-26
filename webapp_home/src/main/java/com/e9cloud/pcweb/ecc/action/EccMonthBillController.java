package com.e9cloud.pcweb.ecc.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.UserCompany;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.AuthenService;
import com.e9cloud.mybatis.service.EccMonthBillService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hzd on 2017/2/14.
 */
@Controller
@RequestMapping("/eccMonthBill")
public class EccMonthBillController extends BaseController {
    @Autowired
    private AppService appService;
    @Autowired
    private EccMonthBillService eccMonthBillService;
    @Autowired
    private AuthenService authenService;

    /**
     * 消费统计列表
     */
    @RequestMapping(value = "/toMonthEccBill")
    public String toMonthEccBill(HttpServletRequest request, Model model,String ym) {
        ym = Tools.isNotNullStr(ym) ? ym : new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -1));
        String ym2 = new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -1));
        String beforcurrentDate = new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -12));
        model.addAttribute("ym", ym);
        model.addAttribute("ym2", ym2);
        model.addAttribute("beforcurrentDate", beforcurrentDate);
        return "ecc/monthEccBill";
    }

    /**
     * 获取消费统计月报列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageMonthEccBill")
    @ResponseBody
    public PageWrapper pageMonthEccBill(Page page, HttpServletRequest request){
        logger.info("------------------- EccMonthBillController pageMonthEccBill START -----------------------------");
        page.addParams("feeid", this.getCurrUser(request).getFeeid());
        PageWrapper pageWrapper = eccMonthBillService.pageMonthEccBill(page);

        return pageWrapper;
    }

    /**
     * 下载报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, String time, Model model, String appId) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");

        time = Tools.isNotNullStr(time) ? time : new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -1));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("time", time);
        map.put("appId", appId);
        map.put("feeid",account.getFeeid());
        List<Map<String, Object>> totals = eccMonthBillService.downloadEccMonthBill(map);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map1 = new HashMap<String, Object>();
                float ivrFee_=0;
                float directFee_=0;
                float localFee_=0;
                float nonlocalFee_=0;
                float inRecordingfee_=0;
                float outRecordingfee_=0;
                float costFee_=0;
                float sipnumRent_=0;
                float minConsume_=0;
                map1.put("appName", Tools.isNullStr(total.get("appName").toString()) ? "" : total.get("appName").toString());

                if(!"".equals(total.get("ivrFee").toString())&&total.get("ivrFee").toString()!=null){
                    ivrFee_= Float.parseFloat(total.get("ivrFee").toString());
                    map1.put("ivrFee", String.format("%.2f",ivrFee_));
                }else {
                    map1.put("ivrFee", "0.00");
                }
                if(!"".equals(total.get("directFee").toString())&&total.get("directFee").toString()!=null){
                    directFee_= Float.parseFloat(total.get("directFee").toString());
                    map1.put("directFee", String.format("%.2f",directFee_));
                }else {
                    map1.put("directFee", "0.00");
                }
                if(!"".equals(total.get("localFee").toString())&&total.get("localFee").toString()!=null){
                    localFee_= Float.parseFloat(total.get("localFee").toString());
                    map1.put("localFee", String.format("%.2f",localFee_));
                }else {
                    map1.put("localFee", "0.00");
                }
                if(!"".equals(total.get("nonlocalFee").toString())&&total.get("nonlocalFee").toString()!=null){
                    nonlocalFee_= Float.parseFloat(total.get("nonlocalFee").toString());
                    map1.put("nonlocalFee", String.format("%.2f",nonlocalFee_));
                }else {
                    map1.put("nonlocalFee", "0.00");
                }
                if(!"".equals(total.get("inRecordingfee").toString())&&total.get("inRecordingfee").toString()!=null){
                    inRecordingfee_= Float.parseFloat(total.get("inRecordingfee").toString());
                    map1.put("inRecordingfee", String.format("%.2f",inRecordingfee_));
                }else {
                    map1.put("inRecordingfee", "0.00");
                }
                if(!"".equals(total.get("outRecordingfee").toString())&&total.get("outRecordingfee").toString()!=null){
                    outRecordingfee_= Float.parseFloat(total.get("outRecordingfee").toString());
                    map1.put("outRecordingfee", String.format("%.2f",outRecordingfee_));
                }else {
                    map1.put("outRecordingfee", "0.00");
                }
                if(!"".equals(total.get("costFee").toString())&&total.get("costFee").toString()!=null){
                    costFee_= Float.parseFloat(total.get("costFee").toString());
                    map1.put("costFee", String.format("%.2f",costFee_));
                }else {
                    map1.put("costFee", "0.00");
                }
                if(!"".equals(total.get("sipnumRent").toString())&&total.get("sipnumRent").toString()!=null){
                    sipnumRent_= Float.parseFloat(total.get("sipnumRent").toString());
                    map1.put("sipnumRent", String.format("%.2f",sipnumRent_));
                }else {
                    map1.put("sipnumRent", "0.00");
                }
                if(!"".equals(total.get("minConsume").toString())&&total.get("minConsume").toString()!=null){
                    minConsume_= Float.parseFloat(total.get("minConsume").toString());
                    map1.put("minConsume", String.format("%.2f",minConsume_));
                }else {
                    map1.put("minConsume", "0.00");
                }

                map1.put("totalFee",String.valueOf(String.format("%.2f",ivrFee_+directFee_+localFee_+nonlocalFee_+inRecordingfee_+outRecordingfee_+costFee_+sipnumRent_+minConsume_)));

                list.add(map1);
            }
        }

        List<String> titles = new ArrayList<String>();
        titles.add("应用名称");
        titles.add("呼入总机(元)");
        titles.add("呼入直拨(元)");
        titles.add("呼出市话(元)");
        titles.add("呼出长途(元)");
        titles.add("呼入录音(元)");
        titles.add("呼出录音(元)");
        titles.add("总机月租(元)");
        titles.add("SIP号码月租(元)");
        titles.add("补扣最低消费(元)");
        titles.add("费用总计(元)");

        List<String> columns = new ArrayList<String>();
        columns.add("appName");
        columns.add("ivrFee");
        columns.add("directFee");
        columns.add("localFee");
        columns.add("nonlocalFee");
        columns.add("inRecordingfee");
        columns.add("outRecordingfee");
        columns.add("costFee");
        columns.add("sipnumRent");
        columns.add("minConsume");
        columns.add("totalFee");

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("titles", titles);
        map2.put("columns", columns);
        map2.put("list", list);
        map2.put("title", "月结账单信息列表");
        map2.put("excelName","月结账单信息列表");
        return new ModelAndView(new POIXlsView(), map2);
    }

    /**
     * 导出消费统计月报明细表
     * @return
     */
    @RequestMapping(value = "/exportEccMonthBillRecord", method = RequestMethod.GET)
    public ModelAndView exportEccMonthBillRecord(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------EccMonthBillController exportEccMonthBillRecord START----------------------------------------------------------------");
        page.addParams("feeid", this.getCurrUser(request).getFeeid());
        List<Map<String, Object>> totals = eccMonthBillService.downloadEccMonthBillRecordList(page);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for(Map<String, Object> map :totals) {
            Map<String, Object> DSmap = new HashMap<String, Object>();
            if(map.get("subphone")!=null && !"".equals(map.get("subphone"))){
                DSmap.put("subphone",map.get("subphone").toString());
            }else {
                DSmap.put("subphone","");
            }
            if(map.get("subphone_type") == null || "".equals(map.get("subphone_type"))){
                DSmap.put("subphoneType","");
            }else {
                if("01".equals(map.get("subphone_type").toString())){
                    DSmap.put("subphoneType","sip");
                }else if("02".equals(map.get("subphone_type").toString())){
                    DSmap.put("subphoneType","手机");
                }else{
                    DSmap.put("subphoneType","固话");
                }
            }
            float ivrFee_=0;
            float directFee_=0;
            float localFee_=0;
            float nonlocalFee_=0;
            float inRecordingfee_=0;
            float outRecordingfee_=0;
            float sipnumRent_=0;
            float minConsume_=0;
            if(!"".equals(map.get("ivrFee").toString())&&map.get("ivrFee").toString()!=null){
                ivrFee_= Float.parseFloat(map.get("ivrFee").toString());
                DSmap.put("ivrFee", String.format("%.2f",ivrFee_));
            }else {
                DSmap.put("ivrFee", "0.00");
            }
            if(!"".equals(map.get("directFee").toString())&&map.get("directFee").toString()!=null){
                directFee_= Float.parseFloat(map.get("directFee").toString());
                DSmap.put("directFee", String.format("%.2f",directFee_));
            }else {
                DSmap.put("directFee", "0.00");
            }
            if(!"".equals(map.get("localFee").toString())&&map.get("localFee").toString()!=null){
                localFee_= Float.parseFloat(map.get("localFee").toString());
                DSmap.put("localFee", String.format("%.2f",localFee_));
            }else {
                DSmap.put("localFee", "0.00");
            }
            if(!"".equals(map.get("nonlocalFee").toString())&&map.get("nonlocalFee").toString()!=null){
                nonlocalFee_= Float.parseFloat(map.get("nonlocalFee").toString());
                DSmap.put("nonlocalFee", String.format("%.2f",nonlocalFee_));
            }else {
                DSmap.put("nonlocalFee", "0.00");
            }
            if(!"".equals(map.get("inRecordingfee").toString())&&map.get("inRecordingfee").toString()!=null){
                inRecordingfee_= Float.parseFloat(map.get("inRecordingfee").toString());
                DSmap.put("inRecordingfee", String.format("%.2f",inRecordingfee_));
            }else {
                DSmap.put("inRecordingfee", "0.00");
            }
            if(!"".equals(map.get("outRecordingfee").toString())&&map.get("outRecordingfee").toString()!=null){
                outRecordingfee_= Float.parseFloat(map.get("outRecordingfee").toString());
                DSmap.put("outRecordingfee", String.format("%.2f",outRecordingfee_));
            }else {
                DSmap.put("outRecordingfee", "0.00");
            }
            if(!"".equals(map.get("sipnumRent").toString())&&map.get("sipnumRent").toString()!=null){
                sipnumRent_= Float.parseFloat(map.get("sipnumRent").toString());
                DSmap.put("sipnumRent", String.format("%.2f",sipnumRent_));
            }else {
                DSmap.put("sipnumRent", "0.00");
            }
            if(!"".equals(map.get("minConsume").toString())&&map.get("minConsume").toString()!=null){
                minConsume_= Float.parseFloat(map.get("minConsume").toString());
                DSmap.put("minConsume", String.format("%.2f",minConsume_));
            }else {
                DSmap.put("minConsume", "0.00");
            }
            DSmap.put("totalFee",String.valueOf(ivrFee_+directFee_+localFee_+nonlocalFee_+inRecordingfee_+outRecordingfee_+sipnumRent_+minConsume_));
            mapList.add(DSmap);
        }
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String uid = account.getUid();
        UserCompany company = authenService.findAuthInfoByUid(uid);
        String customerName = company.getName();
        String appName = String.valueOf(page.getParams().get("appName"));
        String smonth = String.valueOf(page.getParams().get("smonth"));
        String totalFee = String.valueOf(page.getParams().get("totalFee"));
        String callinFee = String.valueOf(page.getParams().get("callinFee"));
        String calloutFee = String.valueOf(page.getParams().get("calloutFee"));
        String costFee = String.valueOf(page.getParams().get("costFee"));
        String minConsume = String.valueOf(page.getParams().get("minConsume"));

        StringBuffer sb = new StringBuffer();
        sb.append("统计月份："+smonth+",客户名称:"+customerName+",应用名称:"+appName+",总费用:"+totalFee+"元,呼入金额:"+callinFee+"元,呼出金额:"
                +calloutFee+"元,总机月租:"+costFee+"元,抵扣低消:"+minConsume+"元");

        List<String> titles = new ArrayList<String>();
        titles.add("号码");
        titles.add("号码类型");
        titles.add("呼入总机(元)");
        titles.add("呼入直呼(元)");
        titles.add("呼出市话(元)");
        titles.add("呼出长途(元)");
        titles.add("呼入录音(元)");
        titles.add("呼出录音(元)");
        titles.add("SIP号码月租(元)");
        titles.add("SIP号码低消补差(元)");
        titles.add("费用总计(元)");

        List<String> columns = new ArrayList<String>();

        columns.add("subphone");
        columns.add("subphoneType");
        columns.add("ivrFee");
        columns.add("directFee");
        columns.add("localFee");
        columns.add("nonlocalFee");
        columns.add("inRecordingfee");
        columns.add("outRecordingfee");
        columns.add("sipnumRent");
        columns.add("minConsume");
        columns.add("totalFee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", mapList);
        map.put("title", sb.toString());
        map.put("excelName", "消费统计明细");
        return new ModelAndView(new POIXlsView(), map);
    }

    @RequestMapping(value = "/getALLEccAppInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<AppInfo> getALLEccAppInfo(HttpServletRequest request){
        logger.info("------------------------------------------------GET EccMonthBillController getALLEccAppInfo START----------------------------------------------------------------");
        Map<String, Object> params = new HashMap<>();
        params.put("sid", this.getCurrUserSid(request));
        params.put("busType", "06");
        List<AppInfo> appInfoList = appService.selectAppInfoListByMap(params);
        return appInfoList;
    }

}
