package com.e9cloud.pcweb.traffic.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.*;

/**
 * 话务中心--话务走势
 * Created by Administrator on 2016/8/23.
 */
@Controller
@RequestMapping("/traffic")
public class TrafficScanController extends BaseController {

    // 专线语音 -- 天
    @Autowired
    private TrafficScanService trafficScanService;

    // 专线语音 -- 小时
    @Autowired
    private RestHourRecordService restHourRecordService;

    // 专线语音 -- 分钟
    @Autowired
    private RestStatMinuteRecordService restStatMinuteRecordService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private SipManagerService sipManagerService;

    @Autowired
    private CallCenterService callCenterService;

    /* -------------------- 专线语音 start ----------------------- */

    // 专线语音
    @RequestMapping("rest")
    private String rest(Model model) {
        model.addAttribute("bdays", Tools.arrayJoinWith(Tools.getBeforeDays(-30), ","));
        model.addAttribute("sdays", Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        return "traffic/restscan";
    }

    // 话务走势(专线语音)
    @RequestMapping("restscan")
    @ResponseBody
    private List<Map<String, Object>> restscan(Map<String, Object> params, String stype) {
        List<Map<String, Object>> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
            list = restHourRecordService.countRestScanByDay(params);
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countRestScanByDay(params);
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
            list = restStatMinuteRecordService.countRestScanByMin(params);
        }

        return list;
    }

    /* -------------------- 专线语音 end ----------------------- */

    /* -------------------- 专号通 start ----------------------- */

    // 专号通
    @RequestMapping("mask")
    private String mask(Model model) {
        model.addAttribute("bdays", Tools.arrayJoinWith(Tools.getBeforeDays(-30), ","));
        model.addAttribute("sdays", Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        return "traffic/maskscan";
    }

    // 话务走势(专号通)
    @RequestMapping("maskscan")
    @ResponseBody
    private List<Map<String, Object>> maskscan(Map<String, Object> params, String stype) {
        List<Map<String, Object>> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countMaskScanByHour(params);
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countMaskScanByDay(params);
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countMaskScanByMin(params);
        }

        return list;
    }

    /* -------------------- 专号通 end ----------------------- */

    /* -------------------- sip接口 start ----------------------- */

    // sip接口
    @RequestMapping("sip")
    private String sip(Model model) {
        model.addAttribute("bdays", Tools.arrayJoinWith(Tools.getBeforeDays(-30), ","));
        model.addAttribute("sdays", Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        return "traffic/sipscan";
    }

    // 话务走势(sip接口)
    @RequestMapping("sipscan")
    @ResponseBody
    private List<Map<String, Object>> sipscan(Map<String, Object> params, String stype, String sid, String subid) {
        params.put("sid", sid);
        params.put("subid", subid);

        logger.info("sipscan params:{}", JSonUtils.toJSon(params));

        List<Map<String, Object>> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countSipScanByHour(params);
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countSipScanByDay(params);
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countSipScanByMin(params);
        }

        return list;
    }

    /* -------------------- sip接口 end ----------------------- */

    /* -------------------- 语音通知 start ----------------------- */

    // 语音通知
    @RequestMapping("voice")
    private String voice(Model model) {
        model.addAttribute("bdays", Tools.arrayJoinWith(Tools.getBeforeDays(-30), ","));
        model.addAttribute("sdays", Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        return "traffic/voicescan";
    }

    // 话务走势(语音通知)
    @RequestMapping("voicescan")
    @ResponseBody
    private List<Map<String, Object>> voicescan(Map<String, Object> params, String stype) {
        List<Map<String, Object>> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countVoiceScanByHour(params);
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countVoiceScanByDay(params);
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countVoiceScanByMin(params);
        }

        return list;
    }

    /* -------------------- 语音通知 end ----------------------- */

    /* -------------------- 智能云调度 start ----------------------- */

    // 智能云调度
    @RequestMapping("cc")
    private String cc(Model model) {
        model.addAttribute("bdays", Tools.arrayJoinWith(Tools.getBeforeDays(-30), ","));
        model.addAttribute("sdays", Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        return "traffic/ccscan";
    }

    // 话务走势(智能云调度)
    @RequestMapping("ccscan")
    @ResponseBody
    private List<Map<String, Object>> ccscan(Map<String, Object> params, String stype, String sid, String subid) {
        params.put("sid", sid);
        params.put("subid", subid);

        List<Map<String, Object>> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countCcScanByHour(params);
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countCcScanByDay(params);
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countCcScanByMin(params);
        }

        return list;
    }

    // 话务走势(智能云调度)
    @RequestMapping("ccOperator")
    @ResponseBody
    private Map<String, Object> ccOperator(Map<String, Object> params, String stype, String sid, String subid) {
        params.put("sid", sid);
        params.put("subid", subid);

        Map<String, Object> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
        }

        list = trafficScanService.countCcScanByOperator(params);

        return list;
    }

    /* -------------------- 智能云调度 end ----------------------- */

    /* -------------------- 云话机 start ----------------------- */

    // 云话机
    @RequestMapping("sp")
    private String sp(Model model) {
        model.addAttribute("bdays", Tools.arrayJoinWith(Tools.getBeforeDays(-30), ","));
        model.addAttribute("sdays", Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        return "traffic/spscan";
    }

    // 话务走势(云话机)
    @RequestMapping("spscan")
    @ResponseBody
    private List<Map<String, Object>> spscan(Map<String, Object> params, String stype) {
        List<Map<String, Object>> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countSpScanByHour(params);
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countSpScanByDay(params);
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countSpScanByMin(params);
        }

        return list;
    }

    /* -------------------- 云话机 end ----------------------- */

    /* -------------------- 云总机 start ----------------------- */

    // 云总机
    @RequestMapping("ecc")
    private String ecc(Model model) {
        model.addAttribute("bdays", Tools.arrayJoinWith(Tools.getBeforeDays(-30), ","));
        model.addAttribute("sdays", Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        return "traffic/eccscan";
    }

    // 话务走势(云总机)
    @RequestMapping("eccscan")
    @ResponseBody
    private List<Map<String, Object>> eccscan(Map<String, Object> params, String stype) {
        List<Map<String, Object>> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countEccScanByHour(params);
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countEccScanByDay(params);
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countEccScanByMin(params);
        }

        return list;
    }

    /* -------------------- 云总机 end ----------------------- */

    @RequestMapping("getCompanyNameAndSidForSelect2")
    @ResponseBody
    public PageWrapper getCompanyNameAndSidForSelect2(Page page){

        logger.info("-------------TrafficScanController getCompanyNameAndSidForSelect2 start-------------");

        String stype = Tools.toStr(page.getParams().get("stype"));
        if (Tools.isNotNullStr(stype)) {
            page.setTimemax(Tools.getDate(0));
            if ("7".equals(stype)) { // 近7日
                page.setTimemin(Tools.getDate(-7));
            } else if ("30".equals(stype)) { // 近30日
                page.setTimemin(Tools.getDate(-30));
            } else { // 昨天
                page.setTimemin(Tools.getDate(-1));
            }
        }

        return userAdminService.getCompanyNameAndSidForSelect2(page);
    }

    @RequestMapping("/getSipRelayInfo")
    @ResponseBody
    private Map<String, Object> getSipRelayInfo(Map<String, Object> params, String stype, String day, String sid) throws ParseException {
        logger.info("------------------------------------------------GET SipMonitorController getSipRelayInfo START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
        }

        params.put("sid",sid);
        map.put("sipRelayInfoList", sipManagerService.getSipRelayInfoByMap(params));
        return map;
    }

    @RequestMapping("/getCcInfo")
    @ResponseBody
    private Map<String, Object> getCcInfo(Map<String, Object> params, String stype, String day, String sid) throws ParseException {
        logger.info("------------------------------------------------GET CloudMonitorController getCcInfo START----------------------------------------------------------------");
        Map<String, Object> map = new HashMap<>();

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
        }

        params.put("sid",sid);
        map.put("ccInfoList", callCenterService.getCcInfoByMap(params));
        return map;
    }

        /* -------------------- 语音验证码 start ----------------------- */

    // 语音验证码
    @RequestMapping("voiceverify")
    private String voiceverify(Model model) {
        model.addAttribute("bdays", Tools.arrayJoinWith(Tools.getBeforeDays(-30), ","));
        model.addAttribute("sdays", Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        return "traffic/voiceverifyscan";
    }

    // 话务走势(语音验证码)
    @RequestMapping("voiceverifyscan")
    @ResponseBody
    private List<Map<String, Object>> voiceverifyscan(Map<String, Object> params, String stype) {
        List<Map<String, Object>> list;

        if ("7".equals(stype)) { // 近7日
            params.put("startDay", Tools.getDate(-7));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countVoiceverifyScanByHour(params);
        } else if ("30".equals(stype)) { // 近30日
            params.put("startDay", Tools.getDate(-30));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countVoiceverifyScanByDay(params);
        } else { // 昨天
            params.put("startDay", Tools.getDate(-1));
            params.put("endDay", Tools.getDate(0));
            list = trafficScanService.countVoiceverifyScanByMin(params);
        }

        return list;
    }

    /* -------------------- 语音验证码 end ----------------------- */

}
