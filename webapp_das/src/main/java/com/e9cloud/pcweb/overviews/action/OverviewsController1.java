package com.e9cloud.pcweb.overviews.action;

import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.OverviewsService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 话单
 * Created by pengchunchen on 2016/8/23.
 */
@Controller
@RequestMapping(value = "/overviews1")
public class OverviewsController1 extends BaseController {

    @Autowired
    private OverviewsService overviewsService;

    // 跳转到 话务总览页面
    @RequestMapping(value = "/toTel")
    public String toTel(HttpServletRequest request, Model model) {
        model.addAttribute("maxMonth",new SimpleDateFormat("yyyy-MM").format(Tools.addMonth(new Date(),-1)));
        return "count/oldoverviewsTel";
    }

    // 跳转到 营收总览页面
    @RequestMapping(value = "/toRev")
    public String toRev(HttpServletRequest request, Model model) {
        //根据当前月计算最小（前36月）、最大日期(上月)
        String minMonth = Tools.formatDate(Tools.addMonth(new Date(),-36),"yyyy-MM");
        String maxMonth = Tools.formatDate(Tools.addMonth(new Date(),-1),"yyyy-MM");
        //根据当前日期计算最小（前365天）、最大日期(前1天)
        String minDay = Tools.formatDate(Tools.addDay(new Date(),-365),"yyyy-MM-dd");
        String maxDay = Tools.formatDate(Tools.addDay(new Date(),-1),"yyyy-MM-dd");

        model.addAttribute("minMonth",minMonth);
        model.addAttribute("maxMonth",maxMonth);

        model.addAttribute("minDay",minDay);
        model.addAttribute("maxDay",maxDay);
        return "count/overviewsRev";
    }

    @RequestMapping(value = "tel", method = RequestMethod.POST)
    @ResponseBody
    public Map tel(Integer dateNum){
        logger.info("-------------OverviewsController tel start-------------");

        //根据dateNum计算最小、最大日期(昨日)
        String timemin = Tools.formatDate(Tools.addDay(new Date(),dateNum),"yyyy-MM-dd");
        String timemax = Tools.formatDate(Tools.addDay(new Date(),-1),"yyyy-MM-dd");

        Map map=new HashMap<>();
        map.put("dateNum",Math.abs(dateNum));//取正数
        map.put("timemin",timemin);
        map.put("timemax",timemax);
        //统计所有业务的总览情况-总收入、累计通话时长、计费通话时长
        Map mapTmp=overviewsService.selectOverviews(map);
        //统计消费用户数-每日累计消费金额大于等于300元的用户
        Integer countUser=overviewsService.CountOverviewsUser(map);
        //统计各业务消费占比：专线语音、专号通、SIP、智能云调度
        List<Map> typeList=overviewsService.selectTypePercent(map);
        //统计消费前10客户
        List<Map> topList=overviewsService.selectOverviewsTop(map);

        Map mapData = new HashMap<>();
        mapData.put("thscsum",0);
        mapData.put("jfscsum",0);
        mapData.put("fee",0);
        if(Tools.isNotNull(mapTmp)){
            mapData.put("thscsum",mapTmp.get("thscsum"));
            mapData.put("jfscsum",mapTmp.get("jfscsum"));
            mapData.put("fee",mapTmp.get("fee"));
        }
        mapData.put("countUser",countUser);
        mapData.put("typeList",typeList);
        mapData.put("topList",topList);

        return mapData;
    }


    @RequestMapping(value = "telCount", method = RequestMethod.POST)
    @ResponseBody
    public Map telCount(String statDay,String rType){
        logger.info("-------------OverviewsController telCount start-------------");

        String a = statDay;
        String b = rType;

        Map<String,Object> map=new HashMap<>();
        map.put("statday",statDay);
        map.put("rType",rType);

        //统计所有业务的总览情况-总收入、累计通话时长、计费通话时长
        Map mapTmp=overviewsService.selectOverviews(map);
        //统计消费用户数-每日累计消费金额大于等于300元的用户
        Integer countUser=overviewsService.CountOverviewsUser(map);
        //统计各业务消费占比：专线语音、专号通、SIP、智能云调度
        List<Map> typeList=overviewsService.selectTypePercent(map);
        //统计消费前10客户
        List<Map> topList=overviewsService.selectOverviewsTop(map);

        Map mapData = new HashMap<>();
        mapData.put("thscsum",0);
        mapData.put("jfscsum",0);
        mapData.put("thscsum",0);
        mapData.put("fee",0);
        if(Tools.isNotNull(mapTmp)){
            mapData.put("thscsum",mapTmp.get("thscsum"));
            mapData.put("jfscsum",mapTmp.get("jfscsum"));
            mapData.put("fee",mapTmp.get("fee"));
        }
        mapData.put("countUser",countUser);
        mapData.put("typeList",typeList);
        mapData.put("topList",topList);

        return mapData;
    }

    @RequestMapping(value = "rev", method = RequestMethod.POST)
    @ResponseBody
    public Map rev(String statDay,String rType){
        logger.info("-------------OverviewsController rev start-------------");

        Map map=new HashMap<>();
        map.put("statday",statDay);
        map.put("rType",rType);
        //应收账款
        Map mapTmp=overviewsService.selectOverviewsRev(map);
        //充值金额
        Map mapMon=overviewsService.selectOverviewsMon(map);
        //注册用户数
        String mapC=overviewsService.selectOverviewsCon(null);
        //新增用户数
        String ConNew=overviewsService.selectOverviewsConNew(map);
        //活跃用户数
        String ConActive=overviewsService.selectOverviewsConActive(map);
        //统计各业务消费占比：专线语音、专号通、SIP、智能云调度、云话机、语音通知
        List<Map> typeList=overviewsService.selectTypePercent(map);
        //统计消费前10客户
        List<Map> topList=overviewsService.selectOverviewsTopRev(map);

        //开放接口
        List<Map> restFee=overviewsService.selectRestFeeSum(map);
        List<Map> restAbline=overviewsService.selectRestAblineSum(map);
        String restCount=overviewsService.selectRestCount(map);

        //专号通
        List<Map> maskFee=overviewsService.selectMaskFeeSum(map);
        List<Map> maskAbline=overviewsService.selectMaskAblineSum(map);
        String maskCount=overviewsService.selectMaskCount(map);

        //语音通知
        List<Map> voiceFee=overviewsService.selectVoiceFeeSum(map);
        String voiceCount=overviewsService.selectVoiceCount(map);

        //SIP业务
        List<Map> sipFee=overviewsService.selectSipFeeSum(map);
        List<Map> sipAbline=overviewsService.selectSipAblineSum(map);
        String sipCount=overviewsService.selectSipCount(map);

        //智能云调度业务
        List<Map> ccFee=overviewsService.selectCcFeeSum(map);
        List<Map> ccAbline=overviewsService.selectCcAblineSum(map);
        String ccCount=overviewsService.selectCcCount(map);

        //云话机调度业务
//        List<Map> spFee=overviewsService.selectSpFeeSum(map);
//        List<Map> spAbline=overviewsService.selectSpAblineSum(map);
//        String spCount=overviewsService.selectSpCount(map);



        Map<String,Object> mapData = new HashMap<>();
        mapData.put("fee",0);
        mapData.put("money",0);
        if(Tools.isNotNull(mapTmp)){
            mapData.put("fee",mapTmp.get("fee"));
        }
        if(Tools.isNotNull(mapMon)){
            mapData.put("money",mapMon.get("money"));
        }
        mapData.put("mapC",mapC);
        mapData.put("ConNew",ConNew);
        mapData.put("ConActive",ConActive);
        mapData.put("topList",topList);
        mapData.put("typeList",typeList);
        //rest相关统计
        mapData.put("restFee",restFee);
        mapData.put("restAbline",restAbline);
        mapData.put("restCount",restCount);

        //mask相关统计
        mapData.put("maskFee",maskFee);
        mapData.put("maskAbline",maskAbline);
        mapData.put("maskCount",maskCount);

        //voice相关统计
        mapData.put("voiceFee",voiceFee);
        mapData.put("voiceCount",voiceCount);

        //sip相关统计
        mapData.put("sipFee",sipFee);
        mapData.put("sipAbline",sipAbline);
        mapData.put("sipCount",sipCount);

        //智能云调度相关统计
        mapData.put("ccFee",ccFee);
        mapData.put("ccAbline",ccAbline);
        mapData.put("ccCount",ccCount);

        //云话机相关统计
//        mapData.put("spFee",spFee);
//        mapData.put("spAbline",spAbline);
//        mapData.put("spCount",spCount);

        return mapData;
    }

}
