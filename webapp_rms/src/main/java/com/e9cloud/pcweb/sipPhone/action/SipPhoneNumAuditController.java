package com.e9cloud.pcweb.sipPhone.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.service.ShowNumApplyService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.sipPhone.biz.ShowNumAuditBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SipPhoneNumAuditController用于外显号码审核
 *
 * Created by hzd on 2016/10/29.
 */
@Controller
@RequestMapping(value = "/sipPhoneNum")
public class SipPhoneNumAuditController extends BaseController {

    @Autowired
    private ShowNumApplyService showNumApplyService;

    @Autowired
    private ShowNumAuditBizService showNumAuditBizService;


    @RequestMapping(value = "audit")
    public String audit() {
        return "SIPPhone/sipPhoneNumAudit";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public PageWrapper list(Page page){
        return showNumApplyService.pageShowNumApplyList(page);
    }

    // 批量审核
    @RequestMapping("auditAll")
    public String auditAll(Model model, String appid, String[] id,String[] audioId) {

        logger.info("=========appid:{}, ids:{}=========", appid, id);

        Long[] str = new Long[id.length];
        for (int i = 0; i < id.length; i++) {
            str[i] = Long.valueOf(id[i]);
        }

        List<SpApplyNum> SpApplyNums = showNumApplyService.getSpApplyNumByIds(str);
        model.addAttribute("ids", Tools.joinArray(id, "-"));
        model.addAttribute("audioIds", Tools.joinArray(audioId, "-"));
        model.addAttribute("appid", appid);
        model.addAttribute("SpApplyNums", SpApplyNums);

        return "SIPPhone/sipPhoneNumToAudit";
    }

    @RequestMapping(value = "editAuditAll")
    @ResponseBody
    public Map<String, String> editAuditAll(HttpServletRequest request, String audioIds,String ids, String appid, String auditCommon, String auditStatus){
        logger.info("=========editAuditAll start=========");
        Map<String, String> map = showNumAuditBizService.auditAllExecute(appid,audioIds, auditCommon, auditStatus);
        return map;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadShowNumApplyInfo")
    public ModelAndView downloadShowNumApplyInfo(Page page) {
        logger.info("=====================================SipPhoneNumAuditController downloadTelnoInfo Execute=====================================");
        List<Map<String, Object>> totals = showNumApplyService.downloadShowNumApplyInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> numBlackMap = new HashMap<String, Object>();
            numBlackMap.put("rowNO", map.get("rowNO").toString().replace(".0",""));
            numBlackMap.put("name", map.get("name"));
            numBlackMap.put("sid", map.get("sid"));
            numBlackMap.put("app_name", map.get("app_name"));
            numBlackMap.put("appid", map.get("appid"));
            if("".equals(map.get("atime")) || null == map.get("atime")){
                numBlackMap.put("atime", "");
            } else {
                numBlackMap.put("atime", String.valueOf(map.get("atime").toString().substring(0,19)));
            }
            numBlackMap.put("sip_phone", map.get("sip_phone"));
            numBlackMap.put("number", map.get("number"));
            numBlackMap.put("show_num", map.get("show_num"));
            if("02".equals(map.get("audit_status"))){
                numBlackMap.put("audit_status","审核不通过");

            }else if("01".equals(map.get("audit_status"))){
                numBlackMap.put("audit_status","审核通过");
            }else{
                numBlackMap.put("audit_status","待审核");
            }
            if("".equals(map.get("audit_common")) ||  map.get("audit_common")==null){
                numBlackMap.put("audit_common", "");
            }
            else {
                numBlackMap.put("audit_common", map.get("audit_common"));
            }
            list.add(numBlackMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("ID");
        titles.add("客户名称");
        titles.add("Account ID");
        titles.add("应用名称");
        titles.add("APP ID");
        titles.add("申请时间");
        titles.add("SIP号码");
        titles.add("固话号码");
        titles.add("外显号码");
        titles.add("状态");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("name");
        columns.add("sid");
        columns.add("app_name");
        columns.add("appid");
        columns.add("atime");
        columns.add("sip_phone");
        columns.add("number");
        columns.add("show_num");
        columns.add("audit_status");
        columns.add("audit_common");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "外显号码审核列表");
        map.put("excelName","外显号码审核列表");

        return new ModelAndView(new POIXlsView(), map);
    }

}
