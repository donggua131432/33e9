package com.e9cloud.pcweb.record.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.record.action.biz.DownloadRecordManager;
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
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 话单
 * Created by Administrator on 2016/8/4.
 */
@Controller
@RequestMapping("/recordlist")
public class CallRecordListController extends BaseController {

    @Autowired
    private MongoDBDao mongoDBDao;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private SipManagerService sipManagerService;

    @Autowired
    private AppService appService;

    @Autowired
    private CallCenterService callCenterService;

    @Autowired
    private CommonService commonService;

    //////////////////////////////////////////// rest start //////////////////////////////////////////////

    // 跳转到 专线语音 的通话记录页面
    @RequestMapping("/rest")
    public String toRest(HttpServletRequest request, Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        Account account = (Account) request.getSession().getAttribute("userInfo");
//        String md5Str = MD5Util.string32MD5(account.getSid()+":"+account.getToken());
//        model.addAttribute("auth",md5Str);
        return "recordlist/restrecordlist";
    }
    // 跳转到 临时文件下载界面
    @RequestMapping("/fileDownLoad")
    public String fileDownLoad(HttpServletRequest request, Model model) {
        return "recordlist/downFileList";
    }

    /**
     * 分页临时下载文件信息
     * @param page
     * @return
     */
    @RequestMapping("/pageFileList")
    @ResponseBody
    public PageWrapper pageFileList(Page page){
        logger.info("=====================================CallRecordListController pageFileList Execute=====================================");
        return commonService.findNotDelList(page);
    }
    /**
     * 获取话单记录列表
     * @param feeid 专线语音通话记录
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageRestCallRecord")
    @ResponseBody
    public PageWrapper pageRestCallRecord(String feeid, Page page, String condstr, String sid){
        logger.info("------------------- CallRecordListController pageMaskCallRecord START -----------------------------");

        if(Tools.isNotNullStr(condstr)){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, RestRecord.class, "restRecord");
    }

    //////////////////////////////////////////// rest end //////////////////////////////////////////////

    //////////////////////////////////////////// mask start //////////////////////////////////////////////

    // 跳转到 专线语音 的通话记录页面
    @RequestMapping("/mask")
    public String toMask(Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        List<CityAreaCode> cityList = callCenterService.getAllCity(new CityAreaCode());
        model.addAttribute("cityList", cityList);
        return "recordlist/maskrecordlist";
    }


    /**
     * 专线语音通话记录
     * @param feeid 计费id
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageMaskCallRecord")
    @ResponseBody
    public PageWrapper pageMaskCallRecord(String feeid, Page page, String condstr, String sid){
        logger.info("------------------- CallRecordListController pageMaskCallRecord START -----------------------------");

        if(Tools.isNotNullStr(condstr)){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, MaskRecord.class, "maskRecord");
    }

    //////////////////////////////////////////// mask end //////////////////////////////////////////////
    //////////////////////////////////////////// sip start //////////////////////////////////////////////

    // 跳转到 专线语音 的通话记录页面
    @RequestMapping("/sip")
    public String toSip(Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        return "recordlist/siprecordlist";
    }

    /**
     * 获取话单记录列表
     * @param feeid 专线语音通话记录
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageSipCallRecord")
    @ResponseBody
    public PageWrapper pageSipCallRecord(String feeid, Page page, String condstr,String sid){
        logger.info("------------------- CallRecordListController pageMaskCallRecord START -----------------------------");

        if(condstr!=null&&!condstr.equals("")){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, SipRecord.class, "sipRecord");
    }

    //////////////////////////////////////////// sip end //////////////////////////////////////////////



    //////////////////////////////////////////// cc start //////////////////////////////////////////////

    // 跳转到 智能云调度 的通话记录页面
    @RequestMapping("/cc")
    public String toCc(Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("provice", callCenterService.getAllProvice(null));
        model.addAttribute("callCenter", callCenterService.getAllCallCenterInfo1(null));
        return "recordlist/ccrecordlist";
    }



    @RequestMapping(value="/getAllCcInfo1", method=RequestMethod.POST)
    @ResponseBody
    public List<CcInfo> getAllCcInfo1(HttpServletRequest request){
        List<CcInfo> getAllCCList = callCenterService.getAllCallCenterInfo1(null);
        return getAllCCList;
    }



    /**
     * 获取话单记录列表
     * @param feeid 智能云调度通话记录
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageCcCallRecord")
    @ResponseBody
    public PageWrapper pageCcCallRecord(String feeid, Page page, String condstr, String sid){
        logger.info("------------------- CallRecordListController pageRestCallRecord START -----------------------------");

        logger.info(feeid + "-----------" + condstr);

        if(Tools.isNotNullStr(condstr)){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, CcRecord.class, "ccenterRecord");
    }

    //////////////////////////////////////////// cc end //////////////////////////////////////////////

    //////////////////////////////////////////// sipphone start //////////////////////////////////////////////

    // 跳转到 云话机 的通话记录页面
    @RequestMapping("/sp")
    public String toSp(Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        return "recordlist/sprecordlist";
    }

    /**
     * 获取话单记录列表
     * @param feeid
     * @return
     * @throws Exception
     */
    @RequestMapping("/pageSpCallRecord")
    @ResponseBody
    public PageWrapper pageSpCallRecord(String feeid, Page page, String condstr, String sid){
        logger.info("------------------- CallRecordListController pageSpCallRecord START -----------------------------");

        logger.info(feeid+"-----------"+condstr);

        if(Tools.isNotNullStr(condstr)){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, SipPhoneRecord.class, "sipphoneRecord");
    }

    /**
     * 得到用户信息及app 列表
     * @param userAdmin 用户信息
     * @param busType app的业务业务类型
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadApp")
    @ResponseBody
    public JSonMessage loadApp(UserAdmin userAdmin, String busType) throws Exception {
        logger.info("---- loadApp start -------");
        JSonMessage jSonMessage = new JSonMessage();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        if(Tools.isNotNullStr(userAdmin.getSid())){
            map1.put("busType",busType);
            map1.put("sid",userAdmin.getSid());
            UserAdmin userAdminResult = userAdminService.getUserAdminWithCompany(userAdmin);
            if(userAdminResult != null){
                data.put("userAdmin", userAdminResult);
                List<AppInfo> appInfos = appService.getAllSpInfo(map1);
                data.put("appInfos", appInfos);
                jSonMessage.setCode(R.OK);
                jSonMessage.setData(data);
            }else{
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg("用户不存在");
            }
        }else{
            Map<String, Object> map2 = new HashMap<>();
            map2.put("busType",busType);
            List<AppInfo> appInfos = appService.getAllSpInfo(map2);
            data.put("appInfos", appInfos);
            List<UserAdmin> userAdminResults = userAdminService.getUserAdminWithCompanyList(userAdmin);
            data.put("userAdmins", userAdminResults);
            jSonMessage.setCode(R.OK);
            jSonMessage.setData(data);
        }
        return jSonMessage;
    }

    //////////////////////////////////////////// sipphone end //////////////////////////////////////////////

    //导出报表
    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReportSip", method = RequestMethod.GET)
    public ModelAndView downloadReport(UserAdmin userAdmin, String feeid, Page page, String condstr) throws Exception {
        UserAdmin uAdmin = null;
        Map<String, Object> subAppMap;
        Map<String, Object> userAdminMap = null;

        if (Tools.isNotNullStr(condstr)) {
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        List<SipRecord> records = mongoDBDao.download(page, SipRecord.class, "sipRecord");

        List<Map<String, Object>> list = new ArrayList<>();
        if(records.size() > 0) {
            if(Tools.isNotNullStr(userAdmin.getSid())){
                uAdmin = userAdminService.getUserAdminWithCompany(userAdmin);
                List<SipRelayInfo> subApps = sipManagerService.getSipRelayInfoListBySid(uAdmin.getSid());
                subAppMap = Tools.listToMap(subApps, "subid", "subName", SipRelayInfo.class);
            }else {
                List<SipRelayInfo> subApps = sipManagerService.getSipRelayInfoListBySid(userAdmin.getSid());
                subAppMap = Tools.listToMap(subApps, "subid", "subName", SipRelayInfo.class);

                List<UserAdmin> userAdminList = userAdminService.getUserAdminWithCompanyList(null);
                userAdminMap = Tools.listToMap(userAdminList, "sid", "authenCompany", UserAdmin.class);
            }



            for(SipRecord sipRecord : records) {
                Map<String, Object> map = new HashMap<String, Object>();
                /*map.put("sid", uAdmin.getAuthenCompany().getName());
                map.put("subName", subAppMap.get(sipRecord.getSubid()));*/

                if(Tools.isNotNullStr(userAdmin.getSid())){
                    map.put("sid", uAdmin.getAuthenCompany().getName());
                }else {
                    map.put("sid", Tools.isNull(userAdminMap.get(sipRecord.getSid()))?"":((AuthenCompany)userAdminMap.get(sipRecord.getSid())).getName());
                }
                map.put("subName", Tools.isNull(subAppMap.get(sipRecord.getSubid()))?"":subAppMap.get(sipRecord.getSubid()));


                map.put("closureTime", Tools.formatDate(sipRecord.getClosureTime(), "yyyy-MM-dd"));
                map.put("subid", sipRecord.getSubid());
                map.put("aTelno", sipRecord.getaTelno());
                map.put("bTelno", sipRecord.getbTelno());
                if (sipRecord.getConnectSucc() == 1) {
                    map.put("connectSucc", "接通");
                } else if (sipRecord.getConnectSucc() == 0) {
                    map.put("connectSucc", "未接通");
                } else {
                    map.put("connectSucc", "");
                }

                map.put("qssj", Tools.formatDate(sipRecord.getQssj()));
                map.put("jssj", Tools.formatDate(sipRecord.getJssj()));
                map.put("thsc", sipRecord.getThsc());
                map.put("fee", sipRecord.getFee());

                list.add(map);
            }
        }

        List<String> titles = new ArrayList<String>();

        titles.add("客户名称");
        titles.add("日期");
        titles.add("子账号名称");
        titles.add("子账号 ID");
        titles.add("主叫号码");
        titles.add("被叫号码");
        titles.add("接通状态");
        titles.add("开始时间");
        titles.add("结束时间");
        titles.add("通话时长");
        titles.add("费用(元)");

        List<String> columns = new ArrayList<String>();

        columns.add("sid");
        columns.add("closureTime");
        columns.add("subName");
        columns.add("subid");
        columns.add("aTelno");
        columns.add("bTelno");
        columns.add("connectSucc");

        columns.add("qssj");
        columns.add("jssj");
        columns.add("thsc");
        columns.add("fee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "Sip通话记录");
        map.put("excelName","Sip通话记录");


        return new ModelAndView(new POIXlsView(), map);
    }


    //导出报表
    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReportRest", method = RequestMethod.GET)
    public ModelAndView downloadReportRest(UserAdmin userAdmin, String feeid, Page page, String condstr) throws Exception {
        /*if (Tools.isNullStr(feeid)) {
            return null;
        }*/
        UserAdmin uAdmin = null;
        Map<String, Object> subAppMap;
        Map<String, Object> userAdminMap = null;
        if (Tools.isNotNullStr(condstr)) {
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        List<RestRecord> records =  mongoDBDao.download(page, RestRecord.class, "restRecord");

        List<Map<String, Object>> list = new ArrayList<>();
        if(records.size() > 0) {
            if(Tools.isNotNullStr(userAdmin.getSid())){
                uAdmin = userAdminService.getUserAdminWithCompany(userAdmin);
                List<AppInfo> appInfos = appService.findALLAppInfo(uAdmin.getSid());
                subAppMap = Tools.listToMap(appInfos, "appid", "appName", AppInfo.class);
            }else {
                List<AppInfo> appInfos = appService.findALLAppInfo(null);
                subAppMap = Tools.listToMap(appInfos, "appid", "appName", AppInfo.class);
                List<UserAdmin> userAdminList = userAdminService.getUserAdminWithCompanyList(null);
                userAdminMap = Tools.listToMap(userAdminList, "sid", "authenCompany", UserAdmin.class);
            }
            for(RestRecord restRecord : records) {
                Map<String, Object> map = new HashMap<String, Object>();
                if(Tools.isNotNullStr(userAdmin.getSid())){
                    map.put("sid", uAdmin.getAuthenCompany().getName());
                }else {
                    map.put("sid", Tools.isNull(userAdminMap.get(restRecord.getSid()))?"":((AuthenCompany)userAdminMap.get(restRecord.getSid())).getName());
                }
                map.put("appid", Tools.isNull(subAppMap.get(restRecord.getAppid()))?"":subAppMap.get(restRecord.getAppid()));


                if ("A".equals(restRecord.getAbLine())) {
                    map.put("abLine", "A路");
                } else if ("B".equals(restRecord.getAbLine())) {
                    map.put("abLine", "B路");
                } else if ("C".equals(restRecord.getAbLine())) {
                    map.put("abLine", "回呼");
                } else {
                    map.put("abLine", "");
                }
                map.put("callSid", restRecord.getCallSid());
                map.put("aTelno", restRecord.getaTelno());
                map.put("bTelno", restRecord.getbTelno());

                if(restRecord.getConnectSucc()!=null){
                    if (restRecord.getConnectSucc() == 1) {
                        map.put("connectSucc", "接通");
                    } else if (restRecord.getConnectSucc() == 0) {
                        map.put("connectSucc", "未接通");
                    } else {
                        map.put("connectSucc", "");
                    }
                }
                map.put("qssj", Tools.formatDate(restRecord.getQssj()));
                map.put("jssj", Tools.formatDate(restRecord.getJssj()));
                map.put("thsc", restRecord.getThsc());
                map.put("fee", restRecord.getFee());
                map.put("recordingFee", restRecord.getRecordingFee());
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("客户名称");
        titles.add("应用名称");
        titles.add("类型");
        titles.add("Call ID");
        titles.add("主叫号码");
        titles.add("被叫号码");
        titles.add("接通状态");
        titles.add("开始时间");
        titles.add("结束时间");
        titles.add("通话时长");
        titles.add("通话费用");
        titles.add("录音费用");

        List<String> columns = new ArrayList<String>();

        columns.add("sid");
        columns.add("appid");
        columns.add("abLine");
        columns.add("callSid");
        columns.add("aTelno");
        columns.add("bTelno");
        columns.add("connectSucc");

        columns.add("qssj");
        columns.add("jssj");
        columns.add("thsc");
        columns.add("fee");
        columns.add("recordingFee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "专线语音通话记录");
        map.put("excelName","专线语音通话记录");

        return new ModelAndView(new POIXlsView(), map);
    }

    //导出报表
    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReportMask", method = RequestMethod.GET)
    public ModelAndView downloadReportMask(UserAdmin userAdmin, String feeid, Page page, String condstr) throws Exception {
        /*if (Tools.isNullStr(feeid)) {
            return null;
        }*/
        UserAdmin uAdmin = null;
        Map<String, Object> subAppMap;
        Map<String, Object> userAdminMap = null;

        if (Tools.isNotNullStr(condstr)) {
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        List<MaskRecord> records = mongoDBDao.download(page, MaskRecord.class, "maskRecord");

        List<Map<String, Object>> list = new ArrayList<>();
        if(records.size() > 0) {
            if(Tools.isNotNullStr(userAdmin.getSid())){
                uAdmin = userAdminService.getUserAdminWithCompany(userAdmin);
                List<AppInfo> appInfos = appService.findALLAppInfo(uAdmin.getSid());
                subAppMap = Tools.listToMap(appInfos, "appid", "appName", AppInfo.class);
            }else {
                List<AppInfo> appInfos = appService.findALLAppInfo(null);
                subAppMap = Tools.listToMap(appInfos, "appid", "appName", AppInfo.class);
                List<UserAdmin> userAdminList = userAdminService.getUserAdminWithCompanyList(null);
                userAdminMap = Tools.listToMap(userAdminList, "sid", "authenCompany", UserAdmin.class);
            }
            for(MaskRecord maskRecord : records) {
                Map<String, Object> map = new HashMap<String, Object>();
                if(Tools.isNotNullStr(userAdmin.getSid())){
                    map.put("sid", uAdmin.getAuthenCompany().getName());
                }else {
                    map.put("sid", Tools.isNull(userAdminMap.get(maskRecord.getSid()))?"":((AuthenCompany)userAdminMap.get(maskRecord.getSid())).getName());
                }
                map.put("appid", Tools.isNull(subAppMap.get(maskRecord.getAppid()))?"":subAppMap.get(maskRecord.getAppid()));

                map.put("callSid", maskRecord.getCallSid());
                map.put("displayPoolCity", maskRecord.getDisplayPoolCity());

                if ("A".equals(maskRecord.getAbLine())) {
                    map.put("abLineB", "回拨");
                    map.put("abLineT", "主叫");
                } else if ("B".equals(maskRecord.getAbLine())) {
                    map.put("abLineB", "回拨");
                    map.put("abLineT", "被叫");
                } else if ("C".equals(maskRecord.getAbLine())) {
                    map.put("abLineB", "回呼");
                    map.put("abLineT", "");
                } else {
                    map.put("abLineB", "");
                    map.put("abLineT", "");
                }

                map.put("aTelno", maskRecord.getaTelno());
                map.put("bTelno", maskRecord.getbTelno());
                map.put("display", maskRecord.getDisplay());

                if (maskRecord.getConnectSucc() == 1) {
                    map.put("connectSucc", "接通");
                } else if (maskRecord.getConnectSucc() == 0) {
                    map.put("connectSucc", "未接通");
                } else {
                    map.put("connectSucc", "");
                }

                map.put("qssj", Tools.formatDate(maskRecord.getQssj()));
                map.put("jssj", Tools.formatDate(maskRecord.getJssj()));
                map.put("thsc", maskRecord.getThsc());
                map.put("jfsc", maskRecord.getJfsc());
                map.put("fee", maskRecord.getFee());
                map.put("recordingFee", maskRecord.getRecordingFee());

                list.add(map);
            }
        }

        List<String> titles = new ArrayList<String>();

        titles.add("客户名称");
        titles.add("应用名称");
        titles.add("Call ID");
        titles.add("城市");
        titles.add("业务类型");
        titles.add("呼叫类型");

        titles.add("主叫");
        titles.add("被叫");
        titles.add("隐私号");

        titles.add("接通状态");
        titles.add("开始时间");
        titles.add("结束时间");
        titles.add("通话时长（秒）");
        titles.add("计费时长（分）");
        titles.add("通话费用");
        titles.add("录音费用");

        List<String> columns = new ArrayList<String>();

        columns.add("sid");
        columns.add("appid");
        columns.add("callSid");
        columns.add("displayPoolCity");
        columns.add("abLineB");
        columns.add("abLineT");
        columns.add("aTelno");
        columns.add("bTelno");
        columns.add("display");
        columns.add("connectSucc");

        columns.add("qssj");
        columns.add("jssj");
        columns.add("thsc");
        columns.add("jfsc");
        columns.add("fee");
        columns.add("recordingFee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "专号通通话记录");
        map.put("excelName","专号通通话记录");

        return new ModelAndView(new POIXlsView(), map);
    }

    //导出报表
    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReportCC", method = RequestMethod.GET)
    public ModelAndView downloadReportCC(UserAdmin userAdmin, String feeid, Page page, String condstr) throws Exception {

        UserAdmin uAdmin = null;
        Map<String, Object> userAdminMap = null;

        if (Tools.isNotNullStr(condstr)) {
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        List<CcRecord> records = mongoDBDao.download(page, CcRecord.class, "ccenterRecord");

        List<Map<String, Object>> list = new ArrayList<>();
        if(records.size() > 0) {
            if(Tools.isNotNullStr(userAdmin.getSid())){
                uAdmin = userAdminService.getUserAdminWithCompany(userAdmin);
            }else {
                List<UserAdmin> userAdminList = userAdminService.getUserAdminWithCompanyList(null);
                userAdminMap = Tools.listToMap(userAdminList, "sid", "authenCompany", UserAdmin.class);
            }
            for(CcRecord ccRecord : records) {
                Map<String, Object> map = new HashMap<String, Object>();
                if(Tools.isNotNullStr(userAdmin.getSid())){
                    map.put("sid", uAdmin.getAuthenCompany().getName());
                }else {
                    map.put("sid", Tools.isNull(userAdminMap.get(ccRecord.getSid()))?"":((AuthenCompany)userAdminMap.get(ccRecord.getSid())).getName());
                }

                map.put("pname", ccRecord.getPname());
                map.put("cname", ccRecord.getCname());


                CcInfo ccInfo = callCenterService.getCcInfoByDefault(ccRecord.getSubid());
                map.put("ccname", ccInfo.getCcname());
                map.put("zj", ccRecord.getZj());
                map.put("bj", ccRecord.getBj());

                if ("I".equals(ccRecord.getAbLine())) {
                    map.put("abLine", "呼入");
                } else if ("O".equals(ccRecord.getAbLine())) {
                    map.put("abLine", "呼出");
                } else {
                    map.put("abLine", "");
                }
                map.put("operator", ccRecord.getOperator());
                map.put("qssj", Tools.formatDate(ccRecord.getQssj()));
                map.put("jssj", Tools.formatDate(ccRecord.getJssj()));
                map.put("thsc", ccRecord.getThsc());
                if (ccRecord.getConnectSucc() == 1) {
                    map.put("connectSucc", "接通");
                } else if (ccRecord.getConnectSucc() == 0) {
                    map.put("connectSucc", "未接通");
                } else {
                    map.put("connectSucc", "");
                }

                map.put("fee", ccRecord.getFee());

                list.add(map);
            }
        }

        List<String> titles = new ArrayList<String>();

        titles.add("客户名称");
        titles.add("省份");
        titles.add("城市");
        titles.add("呼叫中心");
        titles.add("主叫号码");
        titles.add("被叫号码");
        titles.add("呼叫类型");
        titles.add("所属运营商");
        titles.add("开始时间");
        titles.add("结束时间");
        titles.add("通话时长");
        titles.add("接通状态");
        titles.add("费用(元)");

        List<String> columns = new ArrayList<String>();

        columns.add("sid");
        columns.add("pname");
        columns.add("cname");
        columns.add("ccname");
        columns.add("zj");
        columns.add("bj");
        columns.add("abLine");

        columns.add("operator");
        columns.add("qssj");
        columns.add("jssj");
        columns.add("thsc");
        columns.add("connectSucc");
        columns.add("fee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "智能云调度通话记录");
        map.put("excelName","智能云调度");

        return new ModelAndView(new POIXlsView(), map);
    }


    //////////////////////////////////////////// voiceNotice start //////////////////////////////////////////////

    // 跳转到 专线语音 的通话记录页面
    @RequestMapping("/voiceNotice")
    public String tovoiceNotice(Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        return "recordlist/voicerecordlist";
    }


    /**
     * 专线语音通话记录
     * @param feeid 计费id
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageVoiceNoticeRecord")
    @ResponseBody
    public PageWrapper pageVoiceNoticeRecord(String feeid, Page page, String condstr,String sid){
        logger.info("------------------- CallRecordListController pageVoiceNoticeRecord START -----------------------------");

        if(Tools.isNotNullStr(condstr)){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, VoiceRecord.class, "voiceRecord");
    }

    //////////////////////////////////////////// voiceNotice end //////////////////////////////////////////////


    //////////////////////////////////////////// voiceVerifyNotice start //////////////////////////////////////////////

    // 跳转到 专线语音 的通话记录页面
    @RequestMapping("/voiceVerifyNotice")
    public String tovoiceVerifyNotice(Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        return "recordlist/voiceverifyrecordlist";
    }


    /**
     * 语音验证码通话记录
     * @param feeid 计费id
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageVoiceVerifyNoticeRecord")
    @ResponseBody
    public PageWrapper pageVoiceVerifyNoticeRecord(String feeid, Page page, String condstr){
        logger.info("------------------- CallRecordListController pageVoiceVerifyNoticeRecord START -----------------------------");

        if(Tools.isNotNullStr(condstr)){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, VoiceVerifyRecord.class, "voiceVerifyRecord");
    }

    //////////////////////////////////////////// voiceVerifyNotice end //////////////////////////////////////////////

    //////////////////////////////////////////// ecc start //////////////////////////////////////////////
    // 跳转到 专线语音 的通话记录页面
    @RequestMapping("/ecc")
    public String toEcc(HttpServletRequest request, Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        return "recordlist/ivrrecordlist";
    }

    /**
     * 获取话单记录列表
     * @param feeid 云总机通话记录
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageIvrCallRecord")
    @ResponseBody
    public PageWrapper pageIvrCallRecord(String feeid, Page page, String condstr,String sid){
        logger.info("------------------- CallRecordListController pageIvrCallRecord START -----------------------------");

        if(Tools.isNotNullStr(condstr)){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, IvrRecord.class, "ivrRecord");
    }
    //////////////////////////////////////////// ecc end //////////////////////////////////////////////

    //////////////////////////////////////////// maskB start //////////////////////////////////////////////
    // 跳转到 虚拟小号 的通话记录页面
    @RequestMapping("/maskB")
    public String toMaskB(HttpServletRequest request, Model model) {
        String currentDate = new SimpleDateFormat("yyyy年MM月").format(DateUtils.addMonths(new Date(), -1));
        model.addAttribute("currentDate", currentDate);
        return "recordlist/maskBrecordlist";
    }

    /**
     * 获取话单记录列表
     * @param feeid 虚拟小号通话记录
     * @param page 分页信息
     * @return pageWrapper
     */
    @RequestMapping("/pageMaskBCallRecord")
    @ResponseBody
    public PageWrapper pageMaskBCallRecord(String feeid, Page page, String condstr,String sid){
        logger.info("------------------- CallRecordListController pageMaskBCallRecord START -----------------------------");

        if(Tools.isNotNullStr(condstr)){
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        return mongoDBDao.page(page, MaskBRecord.class, "maskBRecord");
    }
    //////////////////////////////////////////// maskB end //////////////////////////////////////////////

    //导出报表
    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReportVoice")
    public ModelAndView downloadReportVoice(UserAdmin userAdmin, String feeid, Page page, String condstr) throws Exception {

        UserAdmin uAdmin = null;
        Map<String, Object> subAppMap;
        Map<String, Object> userAdminMap = null;

        if (Tools.isNotNullStr(condstr)) {
            Condition[] conditions = JSonUtils.readValue(condstr, Condition[].class);
            if (Tools.isNotNullArray(conditions)) {
                page.setConds(Arrays.asList(conditions));
            }
        }

        if (Tools.isNotNullStr(feeid)) {
            page.addCond(new Condition("feeid", feeid));
        }

        List<VoiceRecord> records =  mongoDBDao.download(page, VoiceRecord.class, "voiceRecord");

        List<Map<String, Object>> list = new ArrayList<>();
        if(records.size() > 0) {
            if(Tools.isNotNullStr(userAdmin.getSid())){
                uAdmin = userAdminService.getUserAdminWithCompany(userAdmin);
                List<AppInfo> appInfos = appService.findALLAppInfo(uAdmin.getSid());
                subAppMap = Tools.listToMap(appInfos, "appid", "appName", AppInfo.class);
            }else {
                List<AppInfo> appInfos = appService.findALLAppInfo(null);
                subAppMap = Tools.listToMap(appInfos, "appid", "appName", AppInfo.class);
                List<UserAdmin> userAdminList = userAdminService.getUserAdminWithCompanyList(null);
                userAdminMap = Tools.listToMap(userAdminList, "sid", "authenCompany", UserAdmin.class);
            }
            for(VoiceRecord voiceRecord : records) {
                Map<String, Object> map = new HashMap<String, Object>();
                if(Tools.isNotNullStr(userAdmin.getSid())){
                    map.put("companyName", uAdmin.getAuthenCompany().getName());
                }else {
                    map.put("companyName", Tools.isNull(userAdminMap.get(voiceRecord.getSid()))?"":((AuthenCompany)userAdminMap.get(voiceRecord.getSid())).getName());
                }
                map.put("appName", Tools.isNull(subAppMap.get(voiceRecord.getAppid()))?"":subAppMap.get(voiceRecord.getAppid()));
                map.put("appid", voiceRecord.getAppid());
                map.put("sid", voiceRecord.getSid());
                map.put("templateId", voiceRecord.getTemplateId());
                map.put("requestId", voiceRecord.getRequestId());

                if (voiceRecord.getConnectStatus()) {
                    map.put("connectStatus", "接通");
                } else if (!voiceRecord.getConnectStatus()) {
                    map.put("connectStatus", "未接通");
                } else {
                    map.put("connectStatus", "");
                }
                map.put("bj", voiceRecord.getBj());
                map.put("display", voiceRecord.getDisplay());
                map.put("thsc", voiceRecord.getThsc());
                map.put("dtmf", voiceRecord.getDtmf());
                list.add(map);
            }
        }

        List<String> titles = new ArrayList<String>();

        titles.add("客户名称");
        titles.add("account Sid");
        titles.add("应用名称");
        titles.add("App ID");
        titles.add("模板 ID");
        titles.add("request ID");
        titles.add("被叫号码");
        titles.add("外显号码");
        titles.add("DTMF反馈");
        titles.add("通话时长");
        titles.add("呼叫状态");
        List<String> columns = new ArrayList<String>();

        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appid");
        columns.add("templateId");
        columns.add("requestId");
        columns.add("bj");
        columns.add("display");
        columns.add("dtmf");
        columns.add("thsc");
        columns.add("connectStatus");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "语音通知通话记录");
        map.put("excelName","语音通知通话记录");

        return new ModelAndView(new POIXlsView(), map);
    }

    // 下载页面
    @RequestMapping(value = "toShowDownPage", method = RequestMethod.GET)
    public String toShowAppInfo(String code,String feeid,int pageCount,String conds, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("conds", conds);
        model.addAttribute("feeid", feeid);
        return "recordlist/record_download";
    }

    // 下载页面
    @RequestMapping(value = "download", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage download(DownLoadWrapper wrapper) {
        wrapper.setCreateTime(new Date());
        wrapper.setStatus("00");
        wrapper.setUserId(UserUtil.getCurrentUser().getUsername());
        try{
            commonService.saveDownLoadInfo(wrapper);
            DownloadRecordManager.getInstance().addQueue(wrapper);
        }catch (Exception e){
            logger.error("download find error",e);
            return new JSonMessage(R.ERROR,"添加下载任务失败");
        }
        return new JSonMessage(R.OK,"添加下载任务成功,生成文件大约需要几分钟，请稍后至临时文件夹下载进行下载！");
    }

    // 检查文件名是否输入过
    @RequestMapping(value = "checkFileName", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage checkFileName(String fileName) {
        boolean flag;
        try{
            flag =  commonService.checkFileName(fileName);
        }catch (Exception e){
            logger.error("download find error",e);
            return new JSonMessage(R.ERROR,"");
        }
        if(flag) {
            return new JSonMessage(R.OK, "");
        }else{
            return new JSonMessage(R.ERROR, "");
        }

    }

    // 检查文件名是否输入过
    @RequestMapping(value = "delFile", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage delFile(DownLoadWrapper wrapper) {
        try{
            wrapper.setStatus("02");
            commonService.updateDownLoadInfo(wrapper);
        }catch (Exception e){
            logger.error("download find error", e);
            return new JSonMessage(R.ERROR, "文件删除失败");
        }
        return new JSonMessage(R.OK, "文件删除成功");

    }

    @RequestMapping(value = "downLoadReprot", method = RequestMethod.GET)
    public void download(String filePath, HttpServletResponse response,HttpServletRequest request) {
        try {
            // path是指欲下载的文件的路径。
            String newPath = appConfig.getTempRecordFilePath() + filePath;
            File file = new File(newPath);

            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(newPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            String agent = (String)request.getHeader("USER-AGENT");
            if(agent != null && agent.indexOf("MSIE") == -1) {// FF
                filename = "=?UTF-8?B?" + (new String(Tools.encode(filename.getBytes("UTF-8")))) + "?=";
                response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            } else {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            }

            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
