package com.e9cloud.pcweb.sip.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.SipManagerService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.sip.biz.SipCommonService;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/7/12.
 */
@Controller
@RequestMapping("/sipRepeaterList")
public class SipRepeaterListController extends BaseController{

    @Autowired
    private SipManagerService sipManagerService;

    @Autowired
    private SipCommonService sipCommonService;

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request){
        logger.info("------------------------------------------------GET SipRepeaterListController index START----------------------------------------------------------------");
        //获取标准费率
        SipRelayInfo sipStandardRate = sipManagerService.getSipRateByFeeid("0");
        model.addAttribute("sipStandardRate", sipStandardRate);
        //获取当前登录的用信息
        model.addAttribute("appid", sipCommonService.getSipAppInfo(request).getAppid());
        return "sip/sipRepeaterList";
    }

    /**
     * 获取中继配置信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/sipRepeaterConfig")
    public String sipRepeaterConfig(HttpServletRequest request, Model model){
        logger.info("------------------------------------------------GET SipRepeaterListController sipRepeaterConfig START----------------------------------------------------------------");
        //获取标准费率
        SipRelayInfo sipStandardRate = sipManagerService.getSipRateByFeeid("0");
        model.addAttribute("sipStandardRate", sipStandardRate);
        //获取sipRelayInfo信息
        SipRelayInfo sipRelayInfo = sipManagerService.getSipRelayUnionRateBySubid(request.getParameter("subid"));
        model.addAttribute("sipRelayInfo", sipRelayInfo);
        //获取sip基本信息
        //SipBasic sipBasic = sipManagerService.getSipBasicByRelayNum(sipRelayInfo.getRelayNum());
        //model.addAttribute("sipBasic", sipBasic);
        return "sip/sipRepeaterConfig";
    }

    /**
     * 获取中继列表信息
     * @param page
     * @return
     */
    @RequestMapping("/getSipRepeaterList")
    @ResponseBody
    public PageWrapper getSipRepeaterList(Page page){
        logger.info("------------------------------------------------GET SipRepeaterListController getSipRepeaterList START----------------------------------------------------------------");
        return sipManagerService.getSipRelayInfoList(page);
    }

    /**
     * 获取中继号码池
     * @param page
     * @return
     */
    @RequestMapping("/getSipRepeaterNumPool")
    @ResponseBody
    public PageWrapper getSipRepeaterNumPool(Page page){
        logger.info("------------------------------------------------GET SipRepeaterListController getSipRepeaterNumPool START----------------------------------------------------------------");
        return sipManagerService.getSipRelayNumberPool(page);
    }

    /**
     * 导出中继号码池报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadSipRepeaterNum")
    public ModelAndView downloadSipRepeaterNum(Page page) {
        logger.info("------------------------------------------------GET SipRepeaterListController downloadSipRepeaterNum START------------------------------------------------");
        List<Map<String, Object>> totals = sipManagerService.downloadSipRelayNumberPool(page);
        List<Map<String, Object>> list = new ArrayList<>();

        totals.forEach((map) -> {
            Map<String, Object> numMap = new HashMap<>();
            numMap.put("number", map.get("number").toString());
            numMap.put("createTime", new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(map.get("create_time")));
            list.add(numMap);
        });

        List<String> titles = new ArrayList<>();
        titles.add("号码");
        titles.add("创建时间");

        List<String> columns = new ArrayList<>();
        columns.add("number");
        columns.add("createTime");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "中继号码池列表");
        map.put("excelName","中继号码池列表");

        return new ModelAndView(new POIXlsView(), map);
    }
}
