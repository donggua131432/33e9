package com.e9cloud.pcweb.traffic.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.SmartCloudTrafficMgrService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 话务调度
 * Created by dukai on 2016/8/6.
 */
@Controller
@RequestMapping("/smartCloud/traffic/dispatch")
public class TrafficDispatchController extends BaseController{

    @Autowired
    private SmartCloudTrafficMgrService smartCloudTrafficMgrService;

    // 返回调度页面
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request,Model model) {
        logger.info("------------------------------------------------GET TrafficDispatchController index START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        model.addAttribute("ccAreaList", smartCloudTrafficMgrService.findCcAreaList(account.getSid()));
        model.addAttribute("sid", account.getSid());
        return "traffic/traffic_dispatch";
    }


    /**
     * 添加话务调度信息
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/addDispatchInfo",method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage addDispatchInfo(HttpServletRequest request,String ccStr,String dispatchListStr){
        logger.info("------------------------------------------------GET TrafficDispatchController addDispatchInfo START----------------------------------------------------------------");
        List<CcDispatchInfo> ccDispatchInfoList = JSonUtils.readValue(dispatchListStr, List.class,CcDispatchInfo.class);
        CcDispatch ccDispatch = new Gson().fromJson(ccStr,CcDispatch.class);
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        ccDispatch.setSid(account.getSid());
        long l = smartCloudTrafficMgrService.countDispatchBySidAndDispatchName(ccDispatch);
        if(l > 0){
            return new JSonMessage("1", "该调度名称已存在!");
        }else{
            //自动生成dispatchId
            String dispatchId = ID.randomUUID();
            ccDispatch.setDispatchId(dispatchId);
            ccDispatch.setStatus("00");
            for (CcDispatchInfo ccDispatchInfo:ccDispatchInfoList){
                ccDispatchInfo.setDispatchId(dispatchId);
            }
            for(CcDispatchInfo ccDispatchInfo:ccDispatchInfoList){
                long ccl = smartCloudTrafficMgrService.countCallCenterBySubId(ccDispatchInfo.getSubid());
                if(ccl>0){
                    smartCloudTrafficMgrService.addDispatchInfo(ccDispatch,ccDispatchInfoList);
                    return new JSonMessage("0", "新增话务调度完成!");
                }else {
                    List<CcInfo> ccInfoList = smartCloudTrafficMgrService.findCcInfoListBySid(account.getSid());
                    return new JSonMessage("2", "提交了已删除的呼叫中心，请重新添加！",ccInfoList);
                }
            }

            logger.info("-------------CcDispatchController add end-------------");
            return new JSonMessage("0", "新增话务调度完成!");
        }
    }

    /**
     * 修改话务调度信息
     * @param
     * @return
     */
    @RequestMapping("/updateDispatchInfo")
    @ResponseBody
    public JSonMessage updateDispatchInfo(String dispatchListStr,String ccStr){
        logger.info("------------------------------------------------GET TrafficDispatchController updateDispatchInfo START----------------------------------------------------------------");
        CcDispatch ccDispatch = new Gson().fromJson(ccStr,CcDispatch.class);
        CcDispatch ccDispatchResult = smartCloudTrafficMgrService.findByDispatchId(ccDispatch.getDispatchId());
        if(ccDispatchResult != null){
            long l = smartCloudTrafficMgrService.countDispatchBySidAndDispatchName(ccDispatch);
            if(l > 0){
                return new JSonMessage("1", "该调度名称已存在!");
            }else {
                //删除配置的呼叫中心
                smartCloudTrafficMgrService.deleteCcDispatchInfoByDispatchId(ccDispatch.getDispatchId());
                //配置呼叫中心
                List<CcDispatchInfo> ccDispatchInfoList = JSonUtils.readValue(dispatchListStr, List.class,CcDispatchInfo.class);
                for (CcDispatchInfo ccDispatchInfo:ccDispatchInfoList){
                    ccDispatchInfo.setDispatchId(ccDispatch.getDispatchId());
                }
                smartCloudTrafficMgrService.insertCcDispatchInfoList(ccDispatchInfoList);
                //添加调度
                smartCloudTrafficMgrService.updateDispatchInfo(ccDispatch);
                return new JSonMessage("0","修改话务调度完成!");
            }
        }else {
            return new JSonMessage("2","修改话务调度失败！");
        }
    }


    /**
     * 获取话务调度信息
     * @param dispatchId
     * @return
     */
    @RequestMapping("/deleteDispatch")
    @ResponseBody
    public JSonMessage deleteDispatch(String dispatchId){
        logger.info("------------------------------------------------GET TrafficDispatchController deleteDispatch START----------------------------------------------------------------");
        if(Tools.isNotNullStr(dispatchId)){
            //删除调度信息
            smartCloudTrafficMgrService.deleteCcDispatchByDispatchId(dispatchId);
            //删除相关的呼叫中心配置信息
            smartCloudTrafficMgrService.deleteCcDispatchInfoByDispatchId(dispatchId);

            return new JSonMessage("0","信息删除成功！");
        }else {
            return new JSonMessage("1","信息删除失败！");
        }

    }

    /**
     * 获取话务调度信息
     * @param dispatchId
     * @return
     */
    @RequestMapping("/getDispatchInfo")
    @ResponseBody
    public CcDispatch getDispatchInfo(String dispatchId){
        logger.info("------------------------------------------------GET TrafficDispatchController getDispatchInfo START----------------------------------------------------------------");
        return smartCloudTrafficMgrService.findByDispatchId(dispatchId);
    }


    /**
     * 获取话务调度分页信息
     * @param page
     * @return
     */
    @RequestMapping("/getDispatchPageList")
    @ResponseBody
    public PageWrapper getDispatchPageList(HttpServletRequest request, Page page){
        logger.info("------------------------------------------------GET TrafficDispatchController getDispatchPageList START----------------------------------------------------------------");
        Account account = (Account)request.getSession().getAttribute(JSession.USER_INFO);
        page.addParams("sid", account.getSid());

        return smartCloudTrafficMgrService.pageDispatchList(page);
    }

    /**
     * 获取配置的呼叫中心
     * @param dispatchId
     * @return
     */
    @RequestMapping("/getCcDispatchInfoList")
    @ResponseBody
    public JSonMessage getCcDispatchInfoList(HttpServletRequest request, String dispatchId){
        logger.info("------------------------------------------------GET TrafficDispatchController getCcDispatchInfoList START----------------------------------------------------------------");
        List<CcInfo> ccInfoList = smartCloudTrafficMgrService.findCcInfoListBySid(this.getCurrUserSid(request));
        List<CcDispatchInfo> ccDispatchInfoList = smartCloudTrafficMgrService.findCcDispatchInfoList(dispatchId);

        Map<String, Object> data = new HashMap<>();
        data.put("ccInfoList", ccInfoList);

        if(ccDispatchInfoList.size() > 0){
            data.put("ccDispatchInfoList", ccDispatchInfoList);
            return new JSonMessage("0","数据请求成功！", data);
        }else{
            data.put("ccDispatchInfoList", "");
            return new JSonMessage("1","该调度没有配置呼叫中心！",data);
        }
    }

    /**
     * 下载话务统计分页信息
     * @param page
     * @return
     */
    @RequestMapping("/downloadCcDispatch")
    public ModelAndView downloadCcDispatch(Page page, HttpServletRequest request){
        logger.info("------------------------------------------------GET TrafficDispatchController downloadCcdispatch START------------------------------------------------");
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        List<CcArea> ccAreaList = smartCloudTrafficMgrService.findCcAreaList(account.getSid());
        Map<String, String> ccAreaMap = new HashMap<>();
        for(CcArea ccArea:ccAreaList){
            ccAreaMap.put(ccArea.getAreaId(), ccArea.getAname());
        }

        page.addParams("sid",account.getSid());
        List<Map<String, Object>> totals = smartCloudTrafficMgrService.downloadDispatchList(page);
        List<Map<String, Object>> list = new ArrayList<>();

        totals.forEach((map) -> {
            Map<String, Object> numMap = new HashMap<>();
            if(!"".equals(map.get("dispatch_name"))&&map.get("dispatch_name")!=null){
                numMap.put("dispatchName", map.get("dispatch_name").toString());
            }else{
                numMap.put("dispatchName", "");
            }
            String timeStart = Tools.padLeft(map.get("time_start"), 4, "0");
            String timeEnd = Tools.padLeft(map.get("time_end"), 4, "0");
            timeStart = timeStart.substring(0,2) + "：" + timeStart.substring(2);
            timeEnd = timeEnd.substring(0,2) + "：" + timeEnd.substring(2);

            numMap.put("areaName", ccAreaMap.get(map.get("area_id").toString()));
            numMap.put("timeStart", timeStart + "：00");
            numMap.put("timeEnd", timeEnd + "：00");
            numMap.put("weekday",getWeekDay(map.get("weekday").toString()));
            numMap.put("validDate", new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(map.get("valid_date")));
            if(!"".equals(map.get("ccname"))&&map.get("ccname")!=null){
                numMap.put("ccname", map.get("ccname").toString());
            }else {
                numMap.put("ccname", "");
            }

            list.add(numMap);
        });

        List<String> titles = new ArrayList<>();
        titles.add("调度名称");
        titles.add("调度区域");
        titles.add("开始时间");
        titles.add("结束时间");
        titles.add("日期类型");
        titles.add("生效时间");
        titles.add("呼叫中心");

        List<String> columns = new ArrayList<>();
        columns.add("dispatchName");
        columns.add("areaName");
        columns.add("timeStart");
        columns.add("timeEnd");
        columns.add("weekday");
        columns.add("validDate");
        columns.add("ccname");

        Map<String, Object> map = new HashMap<>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "话务调度列表");
        map.put("excelName","话务调度列表");

        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 获取星期
     * @param weekStr
     * @return
     */
    public String getWeekDay(String weekStr){
        String[] weekArray =  Integer.toBinaryString(Integer.parseInt(weekStr)).split("");
        String weekdayStr = "";
        for(int i=0; i<weekArray.length; i++){
            int  weekday  = Integer.parseInt(weekArray[weekArray.length-1-i]);
            if(weekday == 1){
                if(i == 0){
                    weekdayStr += "周一,";
                }
                if(i == 1){
                    weekdayStr += "周二,";
                }
                if(i == 2){
                    weekdayStr += "周三,";
                }
                if(i == 3){
                    weekdayStr += "周四,";
                }
                if(i == 4){
                    weekdayStr += "周五,";
                }
                if(i == 5){
                    weekdayStr += "周六,";
                }
                if(i == 6){
                    weekdayStr += "周日,";
                }
            }
        }
        return weekdayStr.substring(0,weekdayStr.length()-1);
    }
}
