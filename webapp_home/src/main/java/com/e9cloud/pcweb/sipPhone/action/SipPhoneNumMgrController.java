package com.e9cloud.pcweb.sipPhone.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.MD5Util;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.SipPhoneRecord;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.SipPhoneApply;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.ApplySipPhoneService;
import com.e9cloud.mybatis.service.CallCenterService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 应用管理
 * Created by Administrator on 2016/2/2.
 */
@Controller
@RequestMapping("/sipPhoneAppMgr")
public class SipPhoneNumMgrController extends BaseController {

    @Autowired
    private AppService appService;

    @Autowired
    private CallCenterService callCenterService;

    @Autowired
    private ApplySipPhoneService applySipPhoneService;

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    /**
     * app应用列表
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "sipPhone/sipPhoneAppList";
    }

    /**
     * app应用列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper findAppList(HttpServletRequest request, HttpServletResponse response, AppInfo app, Page page) {

        int count = 0;
        List<AppInfo> appList = new ArrayList<AppInfo>();
        PageWrapper pageWrapper = null;
        Map<String, Object> param = new HashMap<>();
        param.put("sid", this.getCurrUserSid(request));

        try{
            param.put("appName",app.getAppName());
            param.put("appType",app.getAppType());
            param.put("busType", "05");
            count = appService.findAppListCountByMap(param);
            pageWrapper = new PageWrapper(page.getPage(), page.getPageSize(), count, null);

            param.put("start",pageWrapper.getFromIndex());
            param.put("pageSize",page.getPageSize());
            appList = appService.findAppListByMap(param);
            pageWrapper.setRows(appList);
        }catch (Exception e){
            logger.info("--------findAppList error----",e);
        }

        return  pageWrapper;
    }

    @RequestMapping("/sipPhoneNumList")
    public String index(HttpServletRequest request, Model model){
        logger.info("------------------------------------------------GET SipAppDetailController index START------------------------------------------------");
        model.addAttribute("appInfo", appService.findAppInfoByAppId(request.getParameter("appid")));
        model.addAttribute("provice", callCenterService.getAllProvice(null));
        return "sipPhone/sipPhoneNumList";
    }

    /**
     * 申请记录
     */
    @RequestMapping(value = "/applySipPhoneRecord")
    public String applySipPhoneRecord(HttpServletRequest request, Model model) {
        model.addAttribute("appInfo", appService.findAppInfoByAppId(request.getParameter("appid")));
        return "sipPhone/applySipPhoneRecord";
    }

    /**
     * 获取申请记录列表
     * @param page
     * @return
     */
    @RequestMapping("/getApplayRecordList")
    @ResponseBody
    public PageWrapper getApplayRecordList(HttpServletRequest request, Page page){
        logger.info("------------------------------------------------GET sipPhoneNumMgrController getApplayRecordList START----------------------------------------------------------------");
        String appid = request.getParameter("appid");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid",appid);
        page.setParams(paramMap);
        return applySipPhoneService.getAllApplyRecord(page);
    }

    /**
     * 申请记录
     */
    @RequestMapping(value = "/applyRecordDetail")
    public String applyRecordDetail(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        SipPhoneApply sipPhoneApply = new SipPhoneApply();
        sipPhoneApply = applySipPhoneService.getApplyRecordById(id);
        model.addAttribute("sipPhoneApply", sipPhoneApply);
        model.addAttribute("appInfo", appService.findAppInfoByAppId(sipPhoneApply.getAppid()));
        return "sipPhone/applyRecordDetail";
    }

    /**
     * 删除申请记录
     */
    @RequestMapping(value = "delApplyRecord", method = RequestMethod.POST)
    @ResponseBody
    public boolean delApplyRecord(HttpServletRequest request, HttpServletResponse response, SipPhoneApply sipPhoneApply) {
        try{
            applySipPhoneService.delApplyRecord(sipPhoneApply.getId());
            return true;
        }catch (Exception e){
            logger.info("--------delApplyRecord error----",e);
            return false;
        }
    }

    /**
     * 申请号码
     */
    @RequestMapping(value = "/applyForSipPhone")
    public String applyForSipPhone(HttpServletRequest request, Model model) {
        model.addAttribute("appInfo", appService.findAppInfoByAppId(request.getParameter("appid")));
        return "sipPhone/applySipPhoneNum";
    }

    /**
     * 获取应用号码列表
     * @param page
     * @return
     */
    @RequestMapping("/getSipPhoneNumList")
    @ResponseBody
    public PageWrapper getSipPhoneNumList(HttpServletRequest request, Page page){
        logger.info("------------------------------------------------GET sipPhoneNumMgrController getSipPhoneNumList START----------------------------------------------------------------");
        return applySipPhoneService.getSipPhoneNumList(page);
    }

    // 开启/关闭长途，开启/禁用号码
    @RequestMapping("updateSipstatus")
    @ResponseBody
    public JSonMessage updateSipstatus(SpApplyNum applyNum) {
        logger.info("applyNum:{}", JSonUtils.toJSon(applyNum));
        try {
            applySipPhoneService.updateSipstatus(applyNum);
        } catch (Exception e) {
            logger.info("updateSipstatus error : {}", e);
            e.printStackTrace();
            return new JSonMessage("error", "操作失败");
        }

        return new JSonMessage("ok", "操作成功");
    }

    //批量删除消息
    @RequestMapping("deleteSpApplyNum")
    @ResponseBody
    public Map<String,String> deleteSpApplyNum (HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        String strId = request.getParameter("strId");
        if (StringUtils.isEmpty(strId)){
            map.put("code","99");
            return map;
        }
        String[] str1 = strId.split(",");
        applySipPhoneService.deleteSpApplyNum(str1);
        map.put("code","00");
        return map;
    }
    /**
     * 显号编辑
     */
    @RequestMapping(value = "/toShowNumEdit", method = RequestMethod.POST)
    @ResponseBody
    public SpApplyNum toShowNumEdit(HttpServletRequest request, Model model ) {
        String id = request.getParameter("id");
        long l = new Long(id);
        SpApplyNum spApplyNum = applySipPhoneService.getSpApplyNumById(l);
        model.addAttribute("spApplyNum", spApplyNum);
        return spApplyNum;
    }

    /**
     * 模板创建
     */
    @RequestMapping(value = "showNumEdit", method = RequestMethod.POST)
    public String showNumEdit(Model model, HttpServletRequest request, SpApplyNum spApplyNum) {
        logger.info("===========================edit===========================start");
        applySipPhoneService.saveShowNumToAudio(spApplyNum);
//        applySipPhoneService.updateShowNum(spApplyNum);
        model.addAttribute("appInfo", appService.findAppInfoByAppId(request.getParameter("appid")));
        return "sipPhone/sipPhoneNumList";
    }

    /**
     * 导出号码列表
     *
     * @return
     */
    @RequestMapping(value = "downloadSipNumReport", method = RequestMethod.GET)
    public ModelAndView downloadSubReport(HttpServletRequest request, Model model, Page page) {
        Map<String, Object> paramMap = new HashMap<>();
        String sipphone = request.getParameter("sipphone");
        String fixphone = request.getParameter("fixphone");
        String showNum = request.getParameter("showNum1");
        String pcode = request.getParameter("pcode");
        String ccode = request.getParameter("ccode");
        String appid = request.getParameter("appid");
        paramMap.put("sipphone",sipphone);
        page.setParams(paramMap);
        paramMap.put("fixphone",fixphone);
        page.setParams(paramMap);
        paramMap.put("showNum",showNum);
        page.setParams(paramMap);
        paramMap.put("pcode",pcode);
        page.setParams(paramMap);
        paramMap.put("ccode",ccode);
        page.setParams(paramMap);
        paramMap.put("appid",appid);
        page.setParams(paramMap);

        List<Map<String, Object>> totals = applySipPhoneService.downloadSipNumReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                if("".equals(total.get("atime")) || null == total.get("atime")){
                    map.put("atime", "");
                } else {
                    map.put("atime", String.valueOf(total.get("atime").toString().substring(0,19)));
                }
                if("".equals(total.get("sip_phone")) || null == total.get("sip_phone")){
                    map.put("sip_phone", "");
                } else {
                    map.put("sip_phone", String.valueOf(total.get("sip_phone")));
                }
                if("".equals(total.get("number")) || null == total.get("number")){
                    map.put("number", "");
                } else {
                    map.put("number", String.valueOf(total.get("number")));
                }
                if("02".equals(total.get("audit_status"))){
                    map.put("show_num", String.valueOf(total.get("showNum")));
                }else{
                    map.put("show_num", String.valueOf(total.get("show_num")));
                }

                if("00".equals(total.get("audit_status"))){
                    map.put("audit_status", "审核中");
                } else if("01".equals(total.get("audit_status"))){
                    map.put("audit_status", "通过");
                }else if("02".equals(total.get("audit_status"))){
                    map.put("audit_status", "不通过");
                }

                map.put("pname", String.valueOf(total.get("pname")));
                map.put("cname", String.valueOf(total.get("cname")));
                if("".equals(total.get("pwd")) || null == total.get("pwd")){
                    map.put("pwd", "");
                } else {
                    map.put("pwd", String.valueOf(total.get("pwd")));
                }
                if("".equals(total.get("sip_realm")) || null == total.get("sip_realm")){
                    map.put("sip_realm", "");
                } else {
                    map.put("sip_realm", String.valueOf(total.get("sip_realm")));
                }
                if("".equals(total.get("ip_port")) || null == total.get("ip_port")){
                    map.put("ip_port", "");
                } else {
                    map.put("ip_port", String.valueOf(total.get("ip_port")));
                }

                map.put("longDistanceFlag", Tools.decode(total.get("longDistanceFlag"),"00","开启","01","关闭"));
                map.put("callSwitchFlag", Tools.decode(total.get("callSwitchFlag"),"00","开启","01","禁用"));

                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("SIP号码");
        titles.add("绑定固话号码");
        titles.add("外显号码");
        titles.add("外显号码状态");
        titles.add("省份");
        titles.add("城市");
        titles.add("认证密码");
        titles.add("SIP REALM");
        titles.add("IP:PORT");
        titles.add("生成时间");
        titles.add("长途状态");
        titles.add("号码状态");

        List<String> columns = new ArrayList<String>();

        columns.add("sip_phone");
        columns.add("number");
        columns.add("show_num");
        columns.add("audit_status");
        columns.add("pname");
        columns.add("cname");
        columns.add("pwd");
        columns.add("sip_realm");
        columns.add("ip_port");
        columns.add("atime");
        columns.add("longDistanceFlag");
        columns.add("callSwitchFlag");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "应用号码列表");
        map.put("excelName","应用号码列表");

        Map<String, Object> params = page.getParams();
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 申请号码
     */
    @RequestMapping(value = "applyPhoneSave", method = RequestMethod.POST)
    public String applyPhoneSave(HttpServletRequest request, HttpServletResponse response,Model model, SipPhoneApply sipPhoneApply) throws Exception {
        try{
            String appid = request.getParameter("appid");
            sipPhoneApply.setAppid(appid);
            applySipPhoneService.saveSipPhoneApply(sipPhoneApply);
            model.addAttribute("appInfo", appService.findAppInfoByAppId(appid));
            return "sipPhone/applySipPhoneRecord";
        }catch (Exception e){
            logger.info("--------appSave error----",e);
            throw new Exception(e);
        }
    }

    /**
     * 通话记录列表
     */
    @RequestMapping(value = "/callRecord")
    public String callRecord(HttpServletRequest request, Model model) {
        Map<String, Object> params = new HashMap<>();
        params.put("sid", this.getCurrUserSid(request));
        params.put("busType", "05");
        List<AppInfo> appInfos = appService.selectAppInfoListByMap(params);
        model.addAttribute("appInfos", appInfos);
        model.addAttribute("currTime", System.currentTimeMillis());

        Account account = this.getCurrUser(request);
        String md5Str = MD5Util.string32MD5(account.getSid()+":"+account.getToken());
        model.addAttribute("auth",md5Str);

        return "sipPhone/callRecord";
    }

    /**
     * 数据统计列表
     */
    @RequestMapping(value = "/dataStatistics")
    public String dataStatistics(HttpServletRequest request, Model model) {
        Map<String, Object> params = new HashMap<>();
        params.put("sid", this.getCurrUserSid(request));
        params.put("busType", "05");
        List<AppInfo> appInfos = appService.selectAppInfoListByMap(params);
        model.addAttribute("appInfos", appInfos);
        return "sipPhone/dataStatistics";
    }


    /**
     * 获取话单记录列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCallRecordList")
    @ResponseBody
    public PageWrapper getCallRecordList(HttpServletRequest request, SipPhoneRecord sipPhoneRecord, Page page){
        logger.info("------------------------------------------------GET SipPhoneNumMgrController getCallRecordList START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        Query queryList = new Query();
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
        int count =(int)(mongoDBDao.count(queryList, SipPhoneRecord.class,"sipphoneRecord"));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "qssj")));
//        List<SipPhoneRecord>  SipPhoneRecordList = mongoDBDao.find(queryList, SipPhoneRecord.class, "SipphoneRecord_3af915c69f8f466296d82b4c7572ab6e");
        List<SipPhoneRecord>  SipPhoneRecordList = mongoDBDao.find(queryList, SipPhoneRecord.class, "sipphoneRecord");
        pageWrapper.setRows(SipPhoneRecordList);
        return  pageWrapper;
    }

    /**
     * 下载报表
     * @return
     */
    @RequestMapping(value = "/exportCallRecord", method = RequestMethod.GET)
    public ModelAndView downLoadCallRecord(HttpServletRequest request, Page page){
        logger.info("------------------------------------------------ SipPhoneNumMgrController downLoadCallRecord START----------------------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        //获取AppInfo集合信息
        List<AppInfo> appInfoList = appService.findALLAppInfo(account.getSid());
        Map<String,String> appInfoMap = new HashMap<String,String>();
        for (AppInfo appInfo:appInfoList){
            appInfoMap.put(appInfo.getAppid(),appInfo.getAppName());
        }
        Query queryList = new Query();
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
//        int count =(int)(mongoDBDao.count(queryList, SipPhoneRecord.class,"SipphoneRecord_502aaf1191254c46890802a5cccaefe3"));
        int count =(int)(mongoDBDao.count(queryList, SipPhoneRecord.class,"sipphoneRecord"));
        //获取分页信息
        PageWrapper pageWrapper =  new PageWrapper(page.getPage(), page.getPageSize(), count, null);
        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(pageWrapper.getFromIndex());
        //排序
        queryList.with(new Sort(new Sort.Order(Sort.Direction.DESC, "qssj")));
//        List<SipPhoneRecord>  SipPhoneRecordList = mongoDBDao.find(queryList, SipPhoneRecord.class, "SipphoneRecord_502aaf1191254c46890802a5cccaefe3");
        List<SipPhoneRecord>  SipPhoneRecordList = mongoDBDao.find(queryList, SipPhoneRecord.class, "sipphoneRecord");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(SipPhoneRecordList != null && SipPhoneRecordList.size() > 0) {
            for(SipPhoneRecord sipPhoneRecord : SipPhoneRecordList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("appName", Tools.isNullStr(appInfoMap.get(sipPhoneRecord.getAppid()).toString())?"":appInfoMap.get(sipPhoneRecord.getAppid()));
                // 呼叫类型(回拨 : sipprest, 直拨 : sippout, 回呼 : sippin)
                if (Tools.isNotNullStr((String.valueOf(sipPhoneRecord.getRcdType())))){
                    if(String.valueOf(sipPhoneRecord.getRcdType()).equals("sipprest")){
                        map.put("rcdType","回拨");
                    }else if(String.valueOf(sipPhoneRecord.getRcdType()).equals("sippout")){
                        map.put("rcdType","直拨");
                    }else if(String.valueOf(sipPhoneRecord.getRcdType()).equals("sippin")){
                        map.put("rcdType","回呼");
                    }
                }else{
                    map.put("rcdType","");
                }
               if (Tools.isNotNullStr(String.valueOf(sipPhoneRecord.getConnectSucc()))){
                   if(String.valueOf(sipPhoneRecord.getConnectSucc()).equals("0")){
                       map.put("connectSucc","未接通");
                   }else {
                       map.put("connectSucc","接通");
                   }
               }else {
                   map.put("connectSucc","");
               }
                map.put("abLine", Tools.isNullStr(String.valueOf(sipPhoneRecord.getAbLine()))?"":String.valueOf(sipPhoneRecord.getAbLine()));
                map.put("callSid",  Tools.isNullStr(String.valueOf(sipPhoneRecord.getCallSid()))?"":String.valueOf(sipPhoneRecord.getCallSid()));
                map.put("zj", Tools.isNullStr(String.valueOf(sipPhoneRecord.getZj()))?"":String.valueOf(sipPhoneRecord.getZj()));
                map.put("bj", Tools.isNullStr(String.valueOf(sipPhoneRecord.getBj()))?"":String.valueOf(sipPhoneRecord.getBj()));
                map.put("display", Tools.isNullStr(String.valueOf(sipPhoneRecord.getDisplay()))?"":String.valueOf(sipPhoneRecord.getDisplay()));
                map.put("qssj", Tools.isNullStr(String.valueOf(sipPhoneRecord.getQssj()))?"":String.valueOf(sipPhoneRecord.getQssj()));
                map.put("jssj", Tools.isNullStr(String.valueOf(sipPhoneRecord.getJssj()))?"":String.valueOf(sipPhoneRecord.getJssj()));
                map.put("jssj", Tools.isNullStr(String.valueOf(sipPhoneRecord.getThsc()))?"":String.valueOf(sipPhoneRecord.getThsc()));
                map.put("fee", Tools.isNullStr(String.valueOf(sipPhoneRecord.getRecordingFee()+sipPhoneRecord.getFee()))?"":String.valueOf(sipPhoneRecord.getRecordingFee()+sipPhoneRecord.getFee()));
                   /*if(restStafDayRecordResult.getSuccCnt() == 0){
                    map.put("callTimeLengthAvg", "0");
                }else{
                    map.put("callTimeLengthAvg", String.format("%.2f",new Float(restStafDayRecordResult.getThscSum()/restStafDayRecordResult.getSuccCnt())*100));
                }*/
                mapList.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("应用名称");
        titles.add("呼叫类型");
        titles.add("类型");
        titles.add("Call ID");
        titles.add("主叫号码");
        titles.add("被叫号码");
        titles.add("主叫绑定固话");
        titles.add("开始时间");
        titles.add("结束时间");
        titles.add("通话状态");

        titles.add("通话时长");
        titles.add("费用(通话+录音)");

        List<String> columns = new ArrayList<>();
        columns.add("appName");
        columns.add("rcdType");
        columns.add("abLine");
        columns.add("callSid");
        columns.add("zj");
        columns.add("bj");
        columns.add("display");
        columns.add("qssj");
        columns.add("jssj");
        columns.add("connectSucc");
        columns.add("thsc");
        columns.add("fee");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", mapList);
        map.put("title", "通话记录");
        map.put("excelName", "通话记录");

        return new ModelAndView(new POIXlsView(), map);
    }



    /**
     * 获取数据统计列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageDataStatistics")
    @ResponseBody
    public PageWrapper pageDataStatistics(Page page, HttpServletRequest request){
        logger.info("------------------- SipPhoneNumMgrController pageDataStatistics START -----------------------------");
        page.addParams("sid", this.getCurrUserSid(request));
        PageWrapper pageWrapper = applySipPhoneService.pageDataStatistics(page);

        return pageWrapper;
    }


    /**
     * 下载报表
     * @return
     */
    @RequestMapping(value = "/exportDataStatistics", method = RequestMethod.GET)
    public ModelAndView downLoadDataStatistics(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------SipPhoneNumMgrController downLoadDataStatistics START----------------------------------------------------------------");
        page.addParams("sid", this.getCurrUserSid(request));
        List<Map<String, Object>> totals = applySipPhoneService.downloadDataStatisticsReport(page);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String flag = "";
        for(Map<String, Object> map :totals) {
            Map<String, Object> DSmap = new HashMap<String, Object>();
            // 呼叫类型(回拨 : sipprest, 直拨 : sippout, 回呼 : sippin)
            if(Tools.isNullStr(String.valueOf(map.get("rcdType")))){
                DSmap.put("rcdType","");
            }else {
                if(String.valueOf(map.get("rcdType")).equals("sipprest")){
                    DSmap.put("rcdType","回拨");
                    flag = "sipprest";
                }else if(String.valueOf(map.get("rcdType")).equals("sippout")){
                    DSmap.put("rcdType","直拨");
                    flag = "sippout";
                }else if(String.valueOf(map.get("rcdType")).equals("sippin")){
                    DSmap.put("rcdType","回呼");
                    flag = "sippin";
                }
            }
            if(flag.equals("sipprest")){
                if (Tools.isNotNullStr(String.valueOf(map.get("abLine")))){
                    if(String.valueOf(map.get("abLine")).equals("A")){
                        DSmap.put("abLine","A路");
                    }else {
                        DSmap.put("abLine","B路");
                    }
                }else {
                    DSmap.put("abLine","");
                }
            }
            DSmap.put("appName",Tools.isNullStr(map.get("appName").toString())?"": map.get("appName").toString());
            DSmap.put("statDay", Tools.isNullStr(map.get("statDay").toString())?"":map.get("statDay").toString());
            DSmap.put("callCnt", Tools.isNullStr(map.get("callCnt").toString())?"":map.get("callCnt").toString());
            DSmap.put("callRate",Tools.isNullStr(map.get("callRate").toString())?"": map.get("callRate"));
            DSmap.put("thscSum", Tools.isNullStr(map.get("thscSum").toString())?"":map.get("thscSum").toString());
            DSmap.put("jfscSum",Tools.isNullStr(map.get("jfscSum").toString())?"": map.get("jfscSum").toString());
            DSmap.put("pjthsc", Tools.isNullStr(map.get("pjthsc").toString())?"":map.get("pjthsc").toString());
            DSmap.put("pjthsc", Tools.isNullStr(map.get("pjthsc").toString())?"":map.get("pjthsc").toString());
            DSmap.put("lyscsum", Tools.isNullStr(map.get("lyscsum").toString())?"":map.get("lyscsum").toString());
            DSmap.put("fee", Tools.isNullStr(map.get("fee").toString())?"": String.format("%.2f",Float.parseFloat(map.get("fee").toString())));
            DSmap.put("recordingFee", Tools.isNullStr(map.get("recordingFee").toString())?"":String.format("%.2f",Float.parseFloat(map.get("recordingFee").toString())));
            mapList.add(DSmap);
        }

        List<String> titles = new ArrayList<String>();
//        titles.add("报表类型");
        titles.add("业务类型");
        if(flag.equals("sipprest")){
            titles.add("通话类型");
        }
        titles.add("应用名称");
        titles.add("时间");
        titles.add("接通总数");
        titles.add("接通率(%)");
        titles.add("通话总时长(s)");
        titles.add("计费通话时长(min)");
        titles.add("平均通话时长(s)");
        titles.add("平均计费通话时长(s)");
        titles.add("录音总时长(s)");
        titles.add("通话总费用(元)");
        titles.add("录音总费用(元)");
        List<String> columns = new ArrayList<String>();
        columns.add("rcdType");
        if(flag.equals("sipprest")){
            columns.add("abLine");
        }

        columns.add("appName");
        columns.add("statDay");
        columns.add("callCnt");
        columns.add("callRate");
        columns.add("thscSum");
        columns.add("jfscSum");
        columns.add("pjthsc");
        columns.add("pjthsc");
        columns.add("lyscsum");
        columns.add("fee");
        columns.add("recordingFee");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", mapList);
        map.put("title", "数据统计");
        map.put("excelName", "数据统计");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 消费统计列表
     */
    @RequestMapping(value = "/consumptionStatistics")
    public String consumptionStatistics(HttpServletRequest request, Model model) {
        return "sipPhone/consumptionStatistics";
    }

    /**
     * 获取消费统计日报列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageConsumptionStatistics")
    @ResponseBody
    public PageWrapper pageConsumptionStatistics(Page page, HttpServletRequest request){
        logger.info("------------------- SipPhoneNumMgrController pageConsumptionStatistics START -----------------------------");
        page.addParams("feeid", this.getCurrUser(request).getFeeid());
        PageWrapper pageWrapper = applySipPhoneService.pageConsumptionStatistics(page);

        return pageWrapper;
    }

    /**
     * 导出消费统计日报表
     * @return
     */
    @RequestMapping(value = "/exportConsumptionStatistics", method = RequestMethod.GET)
    public ModelAndView exportConsumptionStatistics(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------SipPhoneNumMgrController exportConsumptionStatistics START----------------------------------------------------------------");
        page.addParams("feeid", this.getCurrUser(request).getFeeid());
        List<Map<String, Object>> totals = applySipPhoneService.downloadConsumptionStatisticsList(page);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for(Map<String, Object> map :totals) {
            Map<String, Object> DSmap = new HashMap<String, Object>();
            DSmap.put("reportType", "日报");
            DSmap.put("statDay", Tools.isNullStr(map.get("statDay").toString()) ? "" : map.get("statDay").toString());
            DSmap.put("appName", Tools.isNullStr(map.get("appName").toString()) ? "" : map.get("appName").toString());
            DSmap.put("fee", Tools.isNullStr(map.get("fee").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("fee").toString())));
            DSmap.put("recordingFee", Tools.isNullStr(map.get("recordingFee").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("recordingFee").toString())));

            Float fee = 0f;
            Float recordingFee = 0f;

            if(!Tools.isNullStr(map.get("fee").toString())){
                fee=Float.parseFloat(map.get("fee").toString());
            }
            if(!Tools.isNullStr(map.get("recordingFee").toString())){
                recordingFee=Float.parseFloat(map.get("recordingFee").toString());
            }

            Float totalFee = fee + recordingFee;
            DSmap.put("totalFee", String.format("%.2f", totalFee));

            mapList.add(DSmap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("报表类型");
        titles.add("时间");
        titles.add("应用名称");
        titles.add("通话总费用");
        titles.add("录音总费用");
        titles.add("合计费用");

        List<String> columns = new ArrayList<String>();

        columns.add("reportType");
        columns.add("statDay");
        columns.add("appName");
        columns.add("fee");
        columns.add("recordingFee");
        columns.add("totalFee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", mapList);
        map.put("title", "消费统计");
        map.put("excelName", "消费统计");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 获取消费统计月报列表
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageMonthConsumptionStatistics")
    @ResponseBody
    public PageWrapper pageMonthConsumptionStatistics(Page page, HttpServletRequest request){
        logger.info("------------------- SipPhoneNumMgrController pageMonthConsumptionStatistics START -----------------------------");
        page.addParams("feeid", this.getCurrUser(request).getFeeid());
        PageWrapper pageWrapper = applySipPhoneService.pageMonthConsumptionStatistics(page);

        return pageWrapper;
    }

    /**
     * 导出消费统计月报表
     * @return
     */
    @RequestMapping(value = "/exportMonthConsumptionStatistics", method = RequestMethod.GET)
    public ModelAndView exportMonthConsumptionStatistics(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------SipPhoneNumMgrController exportConsumptionStatistics START----------------------------------------------------------------");
        page.addParams("feeid", this.getCurrUser(request).getFeeid());
        List<Map<String, Object>> totals = applySipPhoneService.downloadMonthConsumStatisticsList(page);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for(Map<String, Object> map :totals) {
            Map<String, Object> DSmap = new HashMap<String, Object>();
            DSmap.put("reportType", "月报");
            DSmap.put("smonth", Tools.isNullStr(map.get("smonth").toString()) ? "" : map.get("smonth").toString());
            DSmap.put("appName", Tools.isNullStr(map.get("appName").toString()) ? "" : map.get("appName").toString());
            DSmap.put("fee", Tools.isNullStr(map.get("fee").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("fee").toString())));
            DSmap.put("recordingFee", Tools.isNullStr(map.get("recordingFee").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("recordingFee").toString())));
            DSmap.put("sipNum", Tools.isNullStr(map.get("sipNum").toString()) ? "" : map.get("sipNum").toString());
            DSmap.put("rent", Tools.isNullStr(map.get("rent").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("rent").toString())));
            DSmap.put("costMin", Tools.isNullStr(map.get("costMin").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("costMin").toString())));

            Float fee = 0f;
            Float recordingFee = 0f;
            Float rent = 0f;
            Float costMin = 0f;
            if(!Tools.isNullStr(map.get("fee").toString())){
                fee=Float.parseFloat(map.get("fee").toString());
            }
            if(!Tools.isNullStr(map.get("recordingFee").toString())){
                recordingFee=Float.parseFloat(map.get("recordingFee").toString());
            }
            if(!Tools.isNullStr(map.get("rent").toString())){
                rent=Float.parseFloat(map.get("rent").toString());
            }
            if(!Tools.isNullStr(map.get("costMin").toString())){
                costMin=Float.parseFloat(map.get("costMin").toString());
            }
            Float totalFee = fee + recordingFee + rent + costMin;
            DSmap.put("totalFee", String.format("%.2f", totalFee));

            mapList.add(DSmap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("报表类型");
        titles.add("时间");
        titles.add("应用名称");
        titles.add("通话总费用");
        titles.add("录音总费用");
        titles.add("号码个数");
        titles.add("号码月租");
        titles.add("低消");
        titles.add("合计费用");

        List<String> columns = new ArrayList<String>();

        columns.add("reportType");
        columns.add("smonth");
        columns.add("appName");
        columns.add("fee");
        columns.add("recordingFee");
        columns.add("sipNum");
        columns.add("rent");
        columns.add("costMin");
        columns.add("totalFee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", mapList);
        map.put("title", "消费统计");
        map.put("excelName", "消费统计");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 导出消费统计月报明细表
     * @return
     */
    @RequestMapping(value = "/exportMonthConsumptionRecord", method = RequestMethod.GET)
    public ModelAndView exportMonthConsumptionRecord(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------SipPhoneNumMgrController exportMonthConsumptionRecord START----------------------------------------------------------------");
        page.addParams("feeid", this.getCurrUser(request).getFeeid());
        List<Map<String, Object>> totals = applySipPhoneService.downloadStatSipphoneConsumeRecordList(page);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for(Map<String, Object> map :totals) {
            Map<String, Object> DSmap = new HashMap<String, Object>();
            DSmap.put("smonth", Tools.isNullStr(map.get("smonth").toString()) ? "" : map.get("smonth").toString());
            DSmap.put("appName", Tools.isNullStr(map.get("appName").toString()) ? "" : map.get("appName").toString());
            DSmap.put("sipphone", Tools.isNullStr(map.get("sipphone").toString()) ? "" : map.get("sipphone").toString());
            DSmap.put("usedDays", Tools.isNullStr(map.get("usedDays").toString()) ? "" : map.get("usedDays").toString());

            DSmap.put("fee", Tools.isNullStr(map.get("fee").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("fee").toString())));
            DSmap.put("recordingFee", Tools.isNullStr(map.get("recordingFee").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("recordingFee").toString())));
            DSmap.put("rent", Tools.isNullStr(map.get("rent").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("rent").toString())));
            DSmap.put("minConsume", Tools.isNullStr(map.get("minConsume").toString()) ? "" : String.format("%.2f", Float.parseFloat(map.get("minConsume").toString())));


            Float fee = 0f;
            Float recordingFee = 0f;
            Float rent = 0f;
            Float minConsume = 0f;
            if(!Tools.isNullStr(map.get("fee").toString())){
                fee=Float.parseFloat(map.get("fee").toString());
            }
            if(!Tools.isNullStr(map.get("recordingFee").toString())){
                recordingFee=Float.parseFloat(map.get("recordingFee").toString());
            }
            if(!Tools.isNullStr(map.get("rent").toString())){
                rent=Float.parseFloat(map.get("rent").toString());
            }
            if(!Tools.isNullStr(map.get("minConsume").toString())){
                minConsume=Float.parseFloat(map.get("minConsume").toString());
            }
            Float totalFee = fee + recordingFee + rent + minConsume;
            DSmap.put("totalFee", String.format("%.2f", totalFee));

            String status = Tools.isNullStr(map.get("status").toString()) ? "" : map.get("status").toString();
            if("00".equals(status)){
                DSmap.put("status","正常");
            }else{
                DSmap.put("status","已停用");
            }
            mapList.add(DSmap);
        }

        List<String> titles = new ArrayList<String>();

        titles.add("时间");
        titles.add("应用名称");
        titles.add("号码");
        titles.add("使用天数");
        titles.add("通话总费用");
        titles.add("录音总费用");
        titles.add("月租");
        titles.add("低消");
        titles.add("合计费用");
        titles.add("号码状态");

        List<String> columns = new ArrayList<String>();

        columns.add("smonth");
        columns.add("appName");
        columns.add("sipphone");
        columns.add("usedDays");
        columns.add("fee");

        columns.add("recordingFee");
        columns.add("rent");
        columns.add("minConsume");
        columns.add("totalFee");
        columns.add("status");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", mapList);
        map.put("title", "消费统计明细");
        map.put("excelName", "消费统计明细");
        return new ModelAndView(new POIXlsView(), map);
    }

}
