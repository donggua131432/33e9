package com.e9cloud.pcweb.revenue.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.DateTools;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.RevenueReportService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.revenue.biz.RevenueReportBizService;
import com.e9cloud.pcweb.revenue.tools.RevenuePOIXlsView;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/11/29.
 */
@Controller
@RequestMapping("revenue/report")
public class RevenueReportController extends BaseController {

    @Autowired
    private RevenueReportService revenueReportService;

    @Autowired
    private RevenueReportBizService reportBizService;

    @RequestMapping(method = RequestMethod.GET)
    public String report(Model model) {
        model.addAttribute("ymd", Tools.formatDate(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd"));
        model.addAttribute("ym", Tools.formatDate(DateUtils.addMonths(new Date(), -1), "yyyy-MM"));
        return  "revenue/revenue_report";
    }

    @RequestMapping("pageDays")
    @ResponseBody
    public PageWrapper pageDays(Page page){
        return revenueReportService.pageDays(page);
    }

    @RequestMapping("pageMonth")
    @ResponseBody
    public PageWrapper pageMonth(Page page, String ym){

        if (Tools.isNotNullStr(ym)) {
            Date date = Tools.parseDate(ym, "yyyy-MM");
            page.setDatemax(DateTools.getLastDayOfMonth(date));
            page.setDatemin(DateUtils.addDays(date, -1));
        }

        return revenueReportService.pageMonth(page);
    }

    @RequestMapping("monthDetails")
    public String monthDetails(Model model, String ym, String feeid, String sid) {

        logger.info("---------------monthDetails ym:{},feeid:{}---------------", ym, feeid);

        Map<String, Object> details = reportBizService.monthDetails(ym, feeid, sid);

        model.addAttribute("ym", ym); // 月份
        model.addAttribute("feeid", feeid); // 月份
        model.addAttribute("sid", sid); // 月份
        model.addAttribute("month", details.get("month")); // 客户信息

        model.addAttribute("rests", details.get("rest")); // rest 专线语音
        model.addAttribute("masks", details.get("mask")); // mask 专号通
        model.addAttribute("sips", details.get("sip")); // sip sip接口
        model.addAttribute("ccs", details.get("cc")); // cc 智能云调度
        model.addAttribute("sps", details.get("sp")); // sp 云话机
        model.addAttribute("eccs", details.get("ecc")); // ecc 云总机
        model.addAttribute("voiceverify", details.get("voiceverify")); // ecc 云总机

        return "revenue/month_details";
    }

    @RequestMapping("downloadMonthDetails")
    public ModelAndView downloadMonthDetails(HttpServletResponse response, String ym, String feeid, String sid) {
        Map<String, Object> details = reportBizService.monthDetails(ym, feeid, sid);

        details.put("excelName", "营收表报-"+ ((Map)details.get("month")).get("companyName"));

        return new ModelAndView(new RevenuePOIXlsView(), details);

    }

    @RequestMapping("downloadDays")
    public ModelAndView downloadDays(Page page) {
        List<Map<String, Object>> totals = revenueReportService.downloadDays(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();

                map.put("statday", Tools.toStr(total.get("statday")));
                map.put("sid", Tools.toStr(total.get("sid")));
                map.put("companyName", Tools.toStr(total.get("companyName")));
                map.put("fee", Tools.toStr(total.get("fee")));
                map.put("balance", Tools.toStr(total.get("balance")));
                map.put("tfee", Tools.toStr(total.get("tfee")));
                map.put("yw", Tools.toYW(Tools.toStr(total.get("yw"))));

                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("时间");
        titles.add("Account ID");
        titles.add("客户名称");
        titles.add("应收账款（元）");
        titles.add("账户余额（元）");
        titles.add("累计消费总额（元）");
        titles.add("使用产品");

        List<String> columns = new ArrayList<String>();

        columns.add("statday");
        columns.add("sid");
        columns.add("companyName");
        columns.add("fee");
        columns.add("balance");
        columns.add("tfee");
        columns.add("yw");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "营收表报（日报）");
        map.put("excelName","营收表报（日报）");

        return new ModelAndView(new POIXlsView(), map);
    }

    @RequestMapping("downloadMonth")
    public ModelAndView downloadMonth(Page page, String ym) {

        if (Tools.isNotNullStr(ym)) {
            Date date = Tools.parseDate(ym, "yyyy-MM");
            page.setDatemax(DateTools.getLastDayOfMonth(date));
            page.setDatemin(DateUtils.addDays(date, -1));
        }

        List<Map<String, Object>> totals = revenueReportService.downloadMonth(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();

                map.put("ym", Tools.toStr(total.get("ym")));
                map.put("sid", Tools.toStr(total.get("sid")));
                map.put("companyName", Tools.toStr(total.get("companyName")));
                map.put("fee", Tools.toStr(total.get("fee")));
                map.put("pbalance", Tools.toStr(total.get("pbalance")));
                map.put("bbalance", Tools.toStr(total.get("bbalance")));
                map.put("tfee", Tools.toStr(total.get("tfee")));

                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("时间");
        titles.add("Account ID");
        titles.add("客户名称");
        titles.add("应收账款（元）");
        titles.add("期初余额（元）");
        titles.add("账户余额（元）");
        titles.add("累计消费总额（元）");

        List<String> columns = new ArrayList<String>();

        columns.add("ym");
        columns.add("sid");
        columns.add("companyName");
        columns.add("fee");
        columns.add("pbalance");
        columns.add("bbalance");
        columns.add("tfee");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "营收表报（月报）");
        map.put("excelName","营收表报（月报）");

        return new ModelAndView(new POIXlsView(), map);
    }
}
