package com.e9cloud.pcweb.znydd.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.ReflectionUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.CcDispatchInfoService;
import com.e9cloud.mybatis.service.CcInfoService;
import com.e9cloud.mybatis.service.RelayService;
import com.e9cloud.pcweb.BaseController;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 呼叫中心配置
 * Created by pengchunchen on 2016/8/5.
 */
@Controller
@RequestMapping(value = "/ccinfo")
public class CcInfoController extends BaseController{

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private CcInfoService ccInfoService;

    // 中继 Service
    @Autowired
    private RelayService relayService;

    @Autowired
    private CcDispatchInfoService ccDispatchInfoService;

    // 呼叫中心列表
    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String toList() {
        return "znydd/ccinfo_list";
    }

    // 创建呼叫中心页面
    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public String toAdd(Model model) {
        //查询未占用中继
        model.addAttribute("relays1", relayService.getRelaysByLimit());
        model.addAttribute("relays2", relayService.getRelaysByLimit());
        return "znydd/ccinfo_add";
    }

    // 显示呼叫中心信息
    @RequestMapping(value = "toShow", method = RequestMethod.GET)
    public String toShow(String id, Model model) {
        CcInfo ccInfo = ccInfoService.queryCcInfoById(id);
        model.addAttribute("ccInfo", ccInfo);

        return "znydd/ccinfo_show";
    }

    // 编辑呼叫中心页面
    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    public String toEdit(String id, Model model) {
        CcInfo ccInfo = ccInfoService.queryCcInfoById(id);
        model.addAttribute("ccInfo", ccInfo);
        List<Map> list = this.getRelayWeight(ccInfo);
        model.addAttribute("relayWeigths", list);
        model.addAttribute("relayStr",new Gson().toJson(list));
        model.addAttribute("relays1", relayService.getRelaysByLimit());
        return "znydd/ccinfo_edit";
    }

    // 为只能云调度用户添加一个呼叫中心
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage add(CcInfo ccInfo,String relays,String weights) {
        logger.info("-------------CcInfoController add start-------------");
        if(!StringUtils.isEmpty(relays)&&!StringUtils.isEmpty(weights)){
            String[] relayStr = relays.split("-");
            String[] weightStr = weights.split("-");
                for (int i=0;i<relayStr.length;i++){
                    ReflectionUtils.invokeSetterMethod(ccInfo,"relay"+(i+1),relayStr[i]);
                    ReflectionUtils.invokeSetterMethod(ccInfo,"weight"+(i+1),Integer.valueOf(weightStr[i]));
                }
        }
        //自动生成subid
        String subId = ID.randomUUID();
        ccInfo.setSubid(subId);
        //设置状态 00 正常
        ccInfo.setStatus("00");
        ccInfoService.saveCcInfo(ccInfo);
        //如果当前操作选择缺省 是时，变更之前的默认缺省为否
        if(1==ccInfo.getDefaultCc()){
            ccInfoService.updateCcInfodefaultCc(ccInfo);
        }
        logger.info("-------------CcInfoController add end-------------");

        return new JSonMessage(R.OK, "新增呼叫中心完成");
    }

    // 为只能云调度用户修改一个呼叫中心信息
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage edit(CcInfo ccInfo,String relays,String weights) {
        logger.info("-------------CcInfoController edit start-------------");
        if(!StringUtils.isEmpty(relays)&&!StringUtils.isEmpty(weights)){
            String[] relayStr = relays.split("-");
            String[] weightStr = weights.split("-");
            for (int i=0;i<relayStr.length;i++){
                ReflectionUtils.invokeSetterMethod(ccInfo,"relay"+(i+1),relayStr[i]);
                ReflectionUtils.invokeSetterMethod(ccInfo,"weight"+(i+1),Integer.valueOf(weightStr[i]));
            }
        }
        setRelayNull(ccInfo);
        ccInfoService.updateCcInfo(ccInfo);
        //如果当前操作选择缺省 是时，变更之前的默认缺省为否
        if(1==ccInfo.getDefaultCc()){
            ccInfoService.updateCcInfodefaultCc(ccInfo);
        }
        logger.info("-------------CcInfoController edit end-------------");

        return new JSonMessage("ok", "编辑呼叫中心完成");
    }

    // 删除一个呼叫中心
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage delete(CcInfo ccInfo) {
        logger.info("-------------CcInfoController delete start-------------");
        ccInfoService.delCcInfo(ccInfo);
        logger.info("-------------CcInfoController delete end-------------");

        return new JSonMessage("ok", "删除呼叫中心完成");
    }

    // 改变一个呼叫中心的状态
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateStatus(CcInfo ccInfo) {
        logger.info("-------------CcInfoController updateStatus start-------------");
        //有话务调度任务的呼叫中心，不能禁用
        CcDispatchInfo ccDispatchInfo = new CcDispatchInfo();
        ccDispatchInfo.setSubid(ccInfo.getSubid());
//        Integer count=ccDispatchInfoService.countCcDispatchInfo(ccDispatchInfo);
        List<CcDispatch> ccDispatchList = ccDispatchInfoService.getDispatchBySubId(ccInfo.getSubid());
        StringBuilder dispatchName = new StringBuilder();

        if(ccDispatchList!=null&&ccDispatchList.size()>0){
            for (CcDispatch ccDispatch:ccDispatchList){
                if (ccDispatch!=null){
                    dispatchName.append(ccDispatch.getDispatchName()).append(",");
                }
            }
            return new JSonMessage("no", "呼叫中心已被:("+dispatchName.substring(0,dispatchName.length()-1)+")调度使用，不能删除！");
        }else{
            ccInfoService.updateCcInfoStatus(ccInfo);
            logger.info("-------------CcInfoController updateStatus end-------------");

            return new JSonMessage("ok", "编辑呼叫中心状态完成");
        }
    }

    // 分页查询呼叫中心列表
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageList(Page page) {
        logger.info("-------------CcInfoController pageList end-------------");
//        page.addParams("ubustype","2");
//        page.addParams("abustype","01");
        PageWrapper pageWrapper=ccInfoService.pageCcInfoList(page);
        return pageWrapper;
    }

    // 校验唯一性
    @RequestMapping(value = "checkCcnameUnique", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage checkCcnameUnique(CcInfo ccInfo){
        JSonMessage jSonMessage = new JSonMessage();

        logger.info("-------------CcInfoController checkCcnameUnique start-------------");

        Integer count = ccInfoService.countCcInfo(ccInfo);
        if (count == 0) {
            jSonMessage.setCode("ok");
        }

        logger.info("-------------CcInfoController checkCcnameUnique start-------------");

        return jSonMessage;
    }

    // 校验唯一性
    @RequestMapping(value = "getAppInfoBySid", method = RequestMethod.POST)
    @ResponseBody
    public AppInfo getAppInfoBySid(AppInfo appInfo){
        logger.info("-------------CcInfoController getAppInfoBySid start-------------");
        //业务类型:01:智能云调度(955xx);02:回拨REST 03：sip
        appInfo.setBusType("01");
        appInfo=appInfoService.getAppInfoByObj(appInfo);
        logger.info("-------------CcInfoController getAppInfoBySid start-------------");

        return appInfo;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadCcInfo")
    public ModelAndView downloadCcInfo(Page page) {
        logger.info("=====================================CcInfoController downloadCcInfo Execute=====================================");
        List<Map<String, Object>> totals = ccInfoService.downloadCcInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> numBlackMap = new HashMap<String, Object>();
            numBlackMap.put("rowNO", map.get("rowNO").toString().replace(".0",""));
            numBlackMap.put("ctime", map.get("ctime").toString().replace(".0",""));
            numBlackMap.put("sid", map.get("sid"));
            numBlackMap.put("name", map.get("name"));
            numBlackMap.put("app_name", map.get("app_name"));
            numBlackMap.put("appid", map.get("appid"));
            numBlackMap.put("ccname", map.get("ccname"));
            numBlackMap.put("subid", map.get("subid"));
            numBlackMap.put("cco_max_num", map.get("cco_max_num"));
            numBlackMap.put("default_cc", map.get("default_cc"));
            numBlackMap.put("relay1", map.get("relay1"));
            list.add(numBlackMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("ID");
        titles.add("创建时间");
        titles.add("account id");
        titles.add("客户名称");
        titles.add("应用名称");
        titles.add("APP ID");
        titles.add("呼叫中心名称");
        titles.add("subID");
        titles.add("话务员数量");
        titles.add("是否缺省");
        titles.add("中继群");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("ctime");
        columns.add("sid");
        columns.add("name");
        columns.add("app_name");
        columns.add("appid");
        columns.add("ccname");
        columns.add("subid");
        columns.add("cco_max_num");
        columns.add("default_cc");
        columns.add("relay1");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "呼叫中心列表");
        map.put("excelName","呼叫中心列表");

       /* Map<String, Object> params = page.getParams();
        LogUtil.log("导出号码黑名单列表", "号码黑名单导出一条记录。导出内容的查询条件为："
                + " id： " + params.get("id") + "，创建时间：" + params.get("createTime")
                + "，号码：" + params.get("number") + "，备注原因："+ params.get("remark"), LogType.UPDATE);*/

        return new ModelAndView(new POIXlsView(), map);
    }

    private List<Map> getRelayWeight(CcInfo ccInfo){
        List<Map> list = new ArrayList<Map>();
        if(Tools.isNotNullStr(ccInfo.getRelay1()))
        {
            Map map = new HashMap<>();
            map.put("relay",ccInfo.getRelay1());
            map.put("weight",ccInfo.getWeight1());
            map.put("name",relayService.getRelayInfoByRelayNum(ccInfo.getRelay1()).getRelayName());
            list.add(map);
        }
        if(Tools.isNotNullStr(ccInfo.getRelay2()))
        {
            Map map = new HashMap<>();
            map.put("relay",ccInfo.getRelay2());
            map.put("weight",ccInfo.getWeight2());
            map.put("name",relayService.getRelayInfoByRelayNum(ccInfo.getRelay2()).getRelayName());
            list.add(map);
        }
        if(Tools.isNotNullStr(ccInfo.getRelay3()))
        {
            Map map = new HashMap<>();
            map.put("relay",ccInfo.getRelay3());
            map.put("weight",ccInfo.getWeight3());
            map.put("name",relayService.getRelayInfoByRelayNum(ccInfo.getRelay3()).getRelayName());
            list.add(map);
        }
        if(Tools.isNotNullStr(ccInfo.getRelay4()))
        {
            Map map = new HashMap<>();
            map.put("relay",ccInfo.getRelay4());
            map.put("weight",ccInfo.getWeight4());
            map.put("name",relayService.getRelayInfoByRelayNum(ccInfo.getRelay4()).getRelayName());
            list.add(map);
        }
        if(Tools.isNotNullStr(ccInfo.getRelay5()))
        {
            Map map = new HashMap<>();
            map.put("relay",ccInfo.getRelay5());
            map.put("weight",ccInfo.getWeight5());
            map.put("name",relayService.getRelayInfoByRelayNum(ccInfo.getRelay5()).getRelayName());
            list.add(map);
        }
        return  list;
    }

    //检查中继状态
    @RequestMapping(value = "checkRelayIsLimit", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage checkRelayIsLimit(String relayNum,String relayId){
        JSonMessage jSonMessage = new JSonMessage();
        SipBasic sipBasic = relayService.getLimitStatusByRelayNum(relayNum);
        if (!"00".equals(sipBasic.getLimitStatus())) {
            return new JSonMessage(R.ERROR, "中继不可用,已被占用"); //  || !"01".equals(sipBasic.getBusType())
        }
        if (!"01".equals(sipBasic.getBusType())) {
            return new JSonMessage(R.ERROR, "非智能云调度中继");
        }
        jSonMessage.setCode("ok");
        return jSonMessage;
    }
    private void setRelayNull(CcInfo ccInfo){
        if(ccInfo.getRelay1()==null){
            ccInfo.setRelay1("");
        }
        if(ccInfo.getRelay2()==null){
            ccInfo.setRelay2("");
        }
        if(ccInfo.getRelay3()==null){
            ccInfo.setRelay3("");
        }
        if(ccInfo.getRelay4()==null){
            ccInfo.setRelay4("");
        }
        if(ccInfo.getRelay5()==null){
            ccInfo.setRelay5("");
        }
    }

}
