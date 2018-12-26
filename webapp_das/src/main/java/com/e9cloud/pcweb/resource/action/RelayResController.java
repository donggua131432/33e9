package com.e9cloud.pcweb.resource.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.RelayResService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中继资源管理
 * Created by Administrator on 2017/3/30.
 */
@Controller
@RequestMapping("/realyRes")
public class RelayResController extends BaseController {

    @Autowired
    private RelayResService relayResService;

    // 页面跳转
    @RequestMapping("record")
    public String record (Model model) {

        model.addAttribute("relayRes", relayResService.getAllRelayRes());
        model.addAttribute("supplier", relayResService.getAllSupplier());
        model.addAttribute("relays", relayResService.getAllRelayByUseType(Constants.RELAY_USE_TYPE_SUPPLY));

        return "realyRes/record";
    }

    // 分页
    @RequestMapping("pageRecord")
    @ResponseBody
    public PageWrapper pageRecord (Page page, String reportType) {
        page.addParams("reportType", reportType);
        return relayResService.pageRecord(page);
    }

    @RequestMapping("downloadRecord")
    public ModelAndView downloadRecord(Page page, String reportType) {
        page.addParams("reportType", reportType);
        List<Map<String, Object>> totals = relayResService.downloadRecord(page);
        logger.info("=====================================RelayResController downloadRecord Execute=====================================");
        boolean isMonthRecord = "2".equals(reportType);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> teleReportMap = new HashMap<String, Object>();
            teleReportMap.put("rowNO", map.get("rowNO"));
            teleReportMap.put("statday", map.get("statday"));
            teleReportMap.put("supName", Tools.toStr(map.get("supName")));
            teleReportMap.put("resName", Tools.toStr(map.get("resName")));
            teleReportMap.put("resId", Tools.toStr(map.get("resId")));
            teleReportMap.put("relayName", Tools.toStr(map.get("relayName")));
            teleReportMap.put("relayNum", Tools.toStr(map.get("relayNum")));
            teleReportMap.put("callCnt", Tools.toStr(map.get("callCnt")));
            teleReportMap.put("succRate", Tools.toStr(map.get("succRate")));
            teleReportMap.put("answerRate", Tools.toStr(map.get("answerRate")));
            teleReportMap.put("thscSum", Tools.toStr(map.get("thscSum")));
            teleReportMap.put("thscAvg", Tools.toStr(map.get("thscAvg")));

            if (isMonthRecord) {
                teleReportMap.put("relayRent", Tools.toStr(map.get("relayRent")));
            }

            teleReportMap.put("costFee", Tools.toStr(map.get("costFee")));
            list.add(teleReportMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("时间");
        titles.add("供应商名称");
        titles.add("线路资源名称");
        titles.add("线路资源ID");
        titles.add("中继名称");
        titles.add("中继ID");
        titles.add("呼叫总数");
        titles.add("接通率");
        titles.add("应答率");
        titles.add("累计通话时长(秒)");
        titles.add("平均通话时长(秒)");

        if (isMonthRecord) {
            titles.add("线路低消(元)");
        }

        titles.add("总通话费用(元)");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("statday");
        columns.add("supName");
        columns.add("resName");
        columns.add("resId");
        columns.add("relayName");
        columns.add("relayNum");
        columns.add("callCnt");
        columns.add("succRate");
        columns.add("answerRate");
        columns.add("thscSum");
        columns.add("thscAvg");
        if (isMonthRecord) {
            columns.add("relayRent");
        }
        columns.add("costFee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "资源话务统计报表信息");
        map.put("excelName","资源话务统计报表信息");

        return new ModelAndView(new POIXlsView(), map);
    }
}
