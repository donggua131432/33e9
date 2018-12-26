package com.e9cloud.pcweb.sipPhone;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.SPApplyService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.sipPhone.biz.SPAllotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云话机--云话机号码分配
 * Created by Administrator on 2016/10/28.
 */
@Controller
@RequestMapping("/spApply")
public class SPNumApplyController extends BaseController {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private SPApplyService spApplyService;

    @Autowired
    private SPAllotService spAllotService;

    // 云话机号码申请记录页面
    @RequestMapping("index")
    public String index(){
        return "SIPPhone/apply_index";
    }

    /**
     * 获取云话机应用列表
     * @param page 分页参数
     * @return 分页信息
     */
    @RequestMapping("pageApplyList")
    @ResponseBody
    public PageWrapper pageAppList(Page page) {
        logger.info("=========== SPNumApplyController pageAppList start ===========");
        return spApplyService.pageApplyList(page);
    }

    // 云话机外显号号码页面
    @RequestMapping("numList")
    public String numList(Model model, String id){

        SipPhoneApply spApply = spApplyService.getApplyWithCityById(id);
        model.addAttribute("spApply", spApply);

        return "SIPPhone/apply_num_list";
    }

    // 云话机外显号号码分配页面
    @RequestMapping("numAllot")
    public String numAllot(Model model, String id){

        SipPhoneApply spApply = spApplyService.getApplyWithCityById(id);
        model.addAttribute("spApply", spApply);

        return "SIPPhone/apply_num_allot";
    }

    // 云话机外显号号码分配页面(选取号码)
    @RequestMapping("allotPage")
    @ResponseBody
    public JSonMessage allotPage(String id){
        return spAllotService.allotPage(id);
    }

    // 云话机外显号号码分配号码
    @RequestMapping("allotNum")
    @ResponseBody
    public JSonMessage allotNum(SipPhoneApply apApply, String sipFix){
        return spAllotService.saveAllotNumsAndAudit(apApply, sipFix);
    }

    /**
     * 查看应用信息
     * @return 查看信息的页面
     */
    @RequestMapping(value = "pageNumList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageNumList(Page page) {
        logger.info("pageNumList for id : {}", page.getParams());
        return spApplyService.pageNumList(page);
    }

    /**
     * 删除外显号（可批量）
     * @return JSonMessage
     */
    @RequestMapping(value = "deleteShowNums")
    @ResponseBody
    public JSonMessage deleteShowNums(String[] id) {
        spApplyService.deleteShowNums(id);
        return new JSonMessage(R.OK, "");
    }

    // 开启/关闭长途，开启/禁用号码
    @RequestMapping("updateSipstatus")
    @ResponseBody
    public JSonMessage updateSipstatus(SpApplyNum applyNum) {
        logger.info("applyNum:{}", JSonUtils.toJSon(applyNum));
        spApplyService.updateSipstatus(applyNum);
        return new JSonMessage(R.OK, "操作成功");
    }

    /**
     * 外显号单个添加
     */
    @RequestMapping("toAddShowNum")
    public String toAddShowNum(Model model, String appid) {
        model.addAttribute("appid", appid);
        return "SIPPhone/add_show_num";
    }

    /**
     * 外显号单个添加
     */
    @RequestMapping("addShowNum")
    @ResponseBody
    public JSonMessage addShowNum(Model model, SpApplyNum applyNum) {
        if (Tools.isNullStr(applyNum.getShowNum())) { // 外显号码为空时，将外显号 赋值为 固话号码
            applyNum.setShowNum(applyNum.getFixphone());
        }
        spApplyService.addShowNum(applyNum);
        return new JSonMessage(R.OK, "提交成功");
    }

    /**
     * 外显号单个添加
     */
    @RequestMapping("updateShowNum")
    @ResponseBody
    public JSonMessage updateShowNum(Model model, SpApplyNum applyNum) {
        if (Tools.isNullStr(applyNum.getShowNum())) { // 外显号码为空时，将外显号 赋值为 固话号码
            applyNum.setShowNum(applyNum.getFixphone());
        }
        spApplyService.updateShowNum(applyNum);
        return new JSonMessage(R.OK, "提交成功");
    }

    /**
     * 外显号批量分配
     */
    @RequestMapping("toAddApply")
    public String toAddApply(Model model, String appid) {
        model.addAttribute("appid", appid);
        return "SIPPhone/add_apply";
    }

    /**
     * 外显号批量分配
     */
    @RequestMapping("addApply")
    @ResponseBody
    public JSonMessage addApply(Model model, SipPhoneApply apply) {

        apply.setId(ID.randomUUID());
        apply.setAuditStatus("00"); // 待分配
        apply.setOperator("01"); // 操作人员：运营人员
        spApplyService.addApply(apply);

        return new JSonMessage(R.OK, "提交成功");
    }

    /**
     * 外显号批量分配
     */
    @RequestMapping("toEditShowNum")
    public String toEditShowNum(Model model, long id) {
        SpApplyNum applyNum = spApplyService.getShowNumWithSipFixPhoneByPK(id);
        model.addAttribute("applyNum", applyNum);
        return "SIPPhone/edit_show_num";
    }

    // 校验sip号码
    @RequestMapping("checkSipphone")
    @ResponseBody
    public JSonMessage checkSipphone(String sipphone, String cityid) {
        return spAllotService.checkSipphone(sipphone, cityid);
    }

    // 校验固话号码
    @RequestMapping("checkFixphone")
    @ResponseBody
    public JSonMessage checkFixphone(String fixphone, String cityid, String appid) {
        return spAllotService.checkFixphone(fixphone, cityid, appid);
    }

    // 校验固话号码
    @RequestMapping("checkShowNum")
    @ResponseBody
    public JSonMessage checkShowNum(String showNum, String appid) {
        Map<String, Object> params = new HashMap<>();
        params.put("showNum", showNum);
        params.put("appid", appid);

        Long count = spApplyService.checkShowNumByMap(params);

        if (count > 0) {
            return new JSonMessage(R.ERROR, "其他客户已经占用");
        }

        return new JSonMessage(R.OK, "");
    }

    /**
     * 导出申请报表
     *
     * @return
     */
    @RequestMapping(value = "downloadApplyReport", method = RequestMethod.GET)
    public ModelAndView downloadApplyReport(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = spApplyService.downloadApplyList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();

                map.put("companyName",String.valueOf(total.get("companyName")));
                map.put("sid", String.valueOf(total.get("sid")));
                map.put("appName", String.valueOf(total.get("appName")));
                map.put("appid",String.valueOf(total.get("appid")));
                map.put("atime", Tools.formatDate(total.get("atime")));

                if ("00".equals(map.get("operator"))) {
                    map.put("operator", "客户");
                } else {
                    map.put("operator", "运营人员");
                }

                if("00".equals(total.get("auditStatus"))){
                    map.put("auditStatus", "待分配");
                } else if("02".equals(total.get("auditStatus"))){
                    map.put("auditStatus", "审核不通过");
                } else {
                    map.put("auditStatus", "已分配");
                }

                list.add(map);
            }
        }

        List<String> titles = new ArrayList<String>();

        titles.add("客户名称");
        titles.add("Account ID");
        titles.add("应用名称");
        titles.add("APP ID");
        titles.add("申请时间");
        titles.add("操作人");
        titles.add("状态");

        List<String> columns = new ArrayList<String>();

        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appid");
        columns.add("atime");
        columns.add("operator");
        columns.add("auditStatus");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "云话机申请列表");
        map.put("excelName","云话机申请列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("云话机申请列表", "云话机申请列表导出。导出内容的查询条件为：" + " ，appid：" + params.get("appid") + "，应用名称："
                + params.get("appName"), LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals =  spApplyService.downloadShowNumList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sipphone",Tools.toStr(total.get("sipphone")));
                map.put("fixphone", Tools.toStr(total.get("fixphone")));

                if (map.get("aShowNum") != null && "".equals(map.get("aShowNum"))) {
                    map.put("showNum", Tools.toStr(total.get("aShowNum")));
                } else {
                    map.put("showNum", Tools.toStr(total.get("showNum")));
                }

                map.put("auditStatus", Tools.decode(total.get("auditStatus"),"00","待审核","02","审核不通过","正常"));
                map.put("pname", Tools.toStr(total.get("pname")));
                map.put("cname", Tools.toStr(total.get("cname")));
                map.put("pname", Tools.toStr(total.get("pname")));
                map.put("pwd", Tools.toStr(total.get("pwd")));
                map.put("sipRealm", Tools.toStr(total.get("sipRealm")));
                map.put("ipPort", Tools.toStr(total.get("ipPort")));
                map.put("atime", Tools.toStr(total.get("atime")));
                map.put("longDistanceFlag", Tools.decode(total.get("longDistanceFlag"),"00","开启","01","关闭"));
                map.put("callSwitchFlag", Tools.decode(total.get("callSwitchFlag"),"00","开启","01","禁用"));

                list.add(map);
            }
        }

        List<String> titles = new ArrayList<String>();

        titles.add("SIP号码");
        titles.add("固话号码");
        titles.add("外显号码");
        titles.add("外显号码状态");
        titles.add("省份");
        titles.add("城市");
        titles.add("认证密码");
        titles.add("SIP REALM");
        titles.add("IP：PORT");
        titles.add("创建时间");
        titles.add("长途状态");
        titles.add("号码状态");

        List<String> columns = new ArrayList<String>();

        columns.add("sipphone");
        columns.add("fixphone");
        columns.add("showNum");
        columns.add("auditStatus");
        columns.add("pname");
        columns.add("cname");
        columns.add("pwd");
        columns.add("sipRealm");
        columns.add("ipPort");
        columns.add("atime");
        columns.add("longDistanceFlag");
        columns.add("callSwitchFlag");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "云话机外显号码列表");
        map.put("excelName","云话机外显号码列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("云话机外显号码列表", "云话机外显号码列表导出。导出内容的查询条件为：" + " ，appid：" + params.get("appid") + "，应用名称："
                + params.get("appName"), LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }

}
