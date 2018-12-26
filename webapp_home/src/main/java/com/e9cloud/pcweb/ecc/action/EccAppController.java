package com.e9cloud.pcweb.ecc.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.ApplySipPhoneService;
import com.e9cloud.mybatis.service.EccDataService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.app.biz.AppCommonService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 云总机  应用管理
 * Created by lixin on 2017/2/10.
 */

@Controller
@RequestMapping("/eccApp")
public class EccAppController extends BaseController{

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppService appService;

    @Autowired
    private ApplySipPhoneService applySipPhoneService;

    @Autowired
    private EccDataService eccDataService;

    @Autowired
    private AppCommonService appCommonService;
    /**
     * app应用列表
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "ecc/appEccList";
    }

    /**
     * app应用列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper findAppList(HttpServletRequest request, HttpServletResponse response, AppInfo app, Page page) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        int count = 0;
        List<AppInfo> appList = new ArrayList<AppInfo>();
        PageWrapper pageWrapper = null;
        Map param = new HashMap<String,String>();
        param.put("sid",account.getSid());
        logger.info("----findAppList start----");
        try{
            param.put("appName",app.getAppName());
            param.put("appType",app.getAppType());
            param.put("busType", "06");
            count = appService.findAppListCountByMap(param);
            pageWrapper = new PageWrapper(page.getPage(), page.getPageSize(), count, null);
            param.put("start",pageWrapper.getFromIndex());
            param.put("pageSize",page.getPageSize());
            appList = appService.findAppListByMap(param);
            logger.info("");
            pageWrapper.setRows(appList);
        }catch (Exception e){
            logger.info("--------findAppList error----",e);
        }
        return  pageWrapper;
    }

    /**
     * app应用列表----查看
     */
    @RequestMapping("appInfoView")
    public String appInfoView(AppInfo appInfo, Model model){
        model.addAttribute("appInfo",appService.findAppInfoByAppId(appInfo.getAppid()));
        model.addAttribute("appExtra",appService.findAppExtraByAppId(appInfo.getAppid()));
        model.addAttribute("appEcc",appService.findAppEccByAppId(appInfo.getAppid()));

        Map<String, Object> totals = appService.findCityEccByAppId(appInfo.getAppid());

        if (totals == null){
        model.addAttribute("number","");
        model.addAttribute("pname","");
        model.addAttribute("cname","");
        }else {
            model.addAttribute("number",Tools.isNullObject(totals.get("number").toString())?"": totals.get("number").toString());
            model.addAttribute("pname",Tools.isNullObject(totals.get("pname").toString())?"": totals.get("pname").toString());
            model.addAttribute("cname",Tools.isNullObject(totals.get("cname").toString())?"": totals.get("cname").toString());
        }
        return "ecc/appEccView";
    }




    /**
     * 开通SipPhone
     * @return
     */
    @RequestMapping("/updatelimitFlag")
    @ResponseBody

    public Map<String,String> openEcc(HttpServletRequest request,HttpServletResponse response,AppInfo app,String appid,String limitFlag,String limitStatus,String quota11,String quota1_,String quota2,String id) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        try{
            String a = quota11;
            String b = quota2;
            String c = quota1_;

            BigDecimal fee = null;
            if (!"".equals(a) && !"".equals(b)){
                BigDecimal quota1 = new BigDecimal(a);
                BigDecimal quota =new BigDecimal(b);
                if("00".equals(limitStatus)){
                    fee = quota.add(quota1);
                }
                if("01".equals(limitStatus)){
                    fee = quota.subtract(quota1);
                }
                app.setQuota(fee);
            }
            if ((a == null||"".equals(a)) && "".equals(b)&& !"".equals(c)){
                BigDecimal quota =new BigDecimal(c);
                app.setQuota(quota);
            }
            app.setUpdateDate(new Date());
            app.setId(Integer.parseInt(id));
            app.setAppid(appid);
            app.setLimitFlag(limitFlag);
            appService.updateApp(app);
            map.put("code","00");

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        return map;
    }


    /**
     * 下载IVR提示音文件
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(String url, HttpServletResponse response) {
        FileUtil.downloadIvr(url, response);
    }

    /**
     * 分页查询分机号码信息
     */
    @RequestMapping("/pageEccNum")
    @ResponseBody
    public PageWrapper pageEccNumRecord(HttpServletRequest request,AppInfo appInfo, Page page) throws Exception {
        Account account = this.getCurrUser(request);
        page.getParams().put("sid", account.getSid());
        logger.info(appInfo.getAppid());
        return appService.pageEccNum(page);
    }


    // 开启/关闭长途，开启/禁用号码
    @RequestMapping("updateEccstatus")
    @ResponseBody
    public JSonMessage updateEccstatus(EccExtention eccExtention) {
        logger.info("applyNum:{}", JSonUtils.toJSon(eccExtention));
        try {
            applySipPhoneService.updateEccstatus(eccExtention);
        } catch (Exception e) {
            logger.info("updateEccstatus error : {}", e);
            e.printStackTrace();
            return new JSonMessage("error", "操作失败");
        }

        return new JSonMessage("ok", "操作成功");
    }



    /**
     * 下载报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request,String appType, String appName,Page page) {

        Account account = this.getCurrUser(request);
        Date date = page.getEndTime();
        if (date != null) {
            date = DateUtils.addDays(date, 1);
            page.setEndTime(date);
        }
        page.addParams("appType", appType);
        page.addParams("appName", appName);
        page.addParams("sid", account.getSid());
        page.addParams("busType", "06");
        List<Map<String, Object>> totals  = appService.getAppInfoList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();

                if("".equals(total.get("create_date")) || null == total.get("create_date")){
                    map.put("create_date", "");
                }
                else {
                    map.put("create_date", String.valueOf(total.get("create_date").toString().substring(0,19)));
                }
                map.put("app_name", String.valueOf(total.get("app_name").toString()));
                map.put("app_type", String.valueOf(total.get("app_type").toString()));
                map.put("appid", String.valueOf(total.get("appid").toString()));
                map.put("status", Tools.decode(total.get("status"),"00","正常","01","冻结","禁用"));
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("应用名称");
        titles.add("应用类型");
        titles.add("创建时间");
        titles.add("APP  ID");
        titles.add("状态");
        List<String> columns = new ArrayList<String>();

        columns.add("app_name");
        columns.add("app_type");
        columns.add("create_date");
        columns.add("appid");
        columns.add("status");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "ECC应用信息列表");
        map.put("excelName","ECC应用信息列表");
        return new ModelAndView(new POIXlsView(), map);
    }



    /**
     * 话务列表
     */
    @RequestMapping(value = "/dataStatistics")
    public String dataStatistics(HttpServletRequest request, Model model) {

        model.addAttribute("currentDay",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        model.addAttribute("beforeDay3",new SimpleDateFormat("yyyy-MM-dd").format(appCommonService.getDayByType("90day")));


        model.addAttribute("currentMonth",new SimpleDateFormat("yyyy-MM").format(new Date()));
        model.addAttribute("beforeMonth12",new SimpleDateFormat("yyyy-MM").format(appCommonService.getDayByType("beforeMonth12")));


        return "ecc/dataStatisticsEcc";
    }

    /**
     * 得到ECC应用列表的序列
     */
    @RequestMapping(value = "/getALLAppInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<AppInfo> getALLSpAppInfo(HttpServletRequest request){
        Map<String, Object> params = new HashMap<>();
        params.put("sid", this.getCurrUserSid(request));
        params.put("busType", "06");
        logger.info("-----GET EccAppController getALLAppInfo START------");
        List<AppInfo> appInfoList = appService.selectAppInfoListByMap(params);
        return appInfoList;
    }


    /**
     * 获取话务统计列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageDataStatistics")
    @ResponseBody
    public PageWrapper pageDataStatistics(Page page, HttpServletRequest request){
        logger.info("------------------- EccAppController pageDataStatistics START -----------------------------");
        page.addParams("sid", this.getCurrUserSid(request));
        page.addParams("date", new SimpleDateFormat("yyyy-MM-dd").format(appCommonService.getDayByType("yesterday")));
        PageWrapper pageWrapper = eccDataService.pageDataStatistics(page);

        return pageWrapper;
    }


    /**
     * 下载报表---话务统计
     * @return
     */
    @RequestMapping(value = "/exportDataStatistics", method = RequestMethod.GET)
    public ModelAndView downLoadDataStatistics(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------ downLoadDataStatistics START----------------------------------------------------------------");
        page.addParams("sid", this.getCurrUserSid(request));
        List<Map<String, Object>> totals = eccDataService.downloadDataStatisticsReport(page);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        Object flag = page.getParams().get("reportType");

        for(Map<String, Object> map :totals) {

            Map<String, Object> DSmap = new HashMap<String, Object>();

            if(flag.equals("1")){
                DSmap.put("statDay", Tools.isNullStr(map.get("statDay").toString())?"":map.get("statDay").toString());
                DSmap.put("type","日报");

            }else {
                DSmap.put("statDay", Tools.isNullStr(map.get("statDay").toString())?"":map.get("statDay").toString().substring(0,7));
                DSmap.put("type","月报");
            }
            DSmap.put("appName",Tools.isNullStr(map.get("appName").toString())?"": map.get("appName").toString());

            // 通话类型
            if(Tools.isNullStr(String.valueOf(map.get("rcdType")))){
                DSmap.put("rcdType","");
            }else {
                if(String.valueOf(map.get("rcdType")).equals("CallInSip")){
                    DSmap.put("rcdType","呼入总机SIP");
                }else if(String.valueOf(map.get("rcdType")).equals("CallInNonSip")){
                    DSmap.put("rcdType","呼入总机非SIP");
                }else if(String.valueOf(map.get("rcdType")).equals("CallInDirect")){
                    DSmap.put("rcdType","呼入总机直呼");
                }else if(String.valueOf(map.get("rcdType")).equals("CallOutLocal")){
                    DSmap.put("rcdType","呼出市话");
                }else if(String.valueOf(map.get("rcdType")).equals("CallOutNonLocal")){
                    DSmap.put("rcdType","呼出长途");
                }
            }
            DSmap.put("callCnt", Tools.isNullStr(map.get("callCnt").toString())?"":map.get("callCnt").toString());
            DSmap.put("callRate",Tools.isNullStr(map.get("callRate").toString())?"": map.get("callRate"));
            DSmap.put("thscSum", Tools.isNullStr(map.get("thscSum").toString())?"":map.get("thscSum").toString());
            DSmap.put("jfscSum",Tools.isNullStr(map.get("jfscSum").toString())?"": map.get("jfscSum").toString());
            DSmap.put("pjthsc", Tools.isNullStr(map.get("pjthsc").toString())?"":map.get("pjthsc").toString());
            DSmap.put("lyscsum", Tools.isNullStr(map.get("lyscsum").toString())?"":map.get("lyscsum").toString());
            DSmap.put("fee", Tools.isNullStr(map.get("fee").toString())?"": String.format("%.2f",Float.parseFloat(map.get("fee").toString())));
            DSmap.put("recordingFee", Tools.isNullStr(map.get("recordingFee").toString())?"":String.format("%.2f",Float.parseFloat(map.get("recordingFee").toString())));
            DSmap.put("feesum", Tools.isNullStr(map.get("feesum").toString())?"": String.format("%.2f",Float.parseFloat(map.get("feesum").toString())));


            mapList.add(DSmap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("报表类型");
        titles.add("时间");
        titles.add("应用名称");
        titles.add("通话类型");
        titles.add("接通总数");
        titles.add("接通率(%)");
        titles.add("通话总时长(s)");
        titles.add("计费通话时长(min)");
        titles.add("平均通话时长(s)");
        titles.add("录音总时长(s)");
        titles.add("通话总费用(元)");
        titles.add("录音总费用(元)");
        titles.add("合计费用(元)");

        List<String> columns = new ArrayList<String>();
        columns.add("type");
        columns.add("statDay");
        columns.add("appName");
        columns.add("rcdType");
        columns.add("callCnt");
        columns.add("callRate");
        columns.add("thscSum");
        columns.add("jfscSum");
        columns.add("pjthsc");
        columns.add("lyscsum");
        columns.add("fee");
        columns.add("recordingFee");
        columns.add("feesum");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", mapList);
        map.put("title", "云总机话务数据统计");
        map.put("excelName", "云总机话务数据统计");
        return new ModelAndView(new POIXlsView(), map);
    }

}
