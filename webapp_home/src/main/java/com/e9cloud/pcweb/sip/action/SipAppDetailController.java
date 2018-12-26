package com.e9cloud.pcweb.sip.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.service.SipManagerService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.sip.biz.SipCommonService;
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
@RequestMapping("/sipAppDetail")
public class SipAppDetailController extends BaseController{

    @Autowired
    private SipManagerService sipManagerService;

    @Autowired
    private SipCommonService sipCommonService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model){
        logger.info("------------------------------------------------GET SipAppDetailController index START------------------------------------------------");
        model.addAttribute("appInfo", sipCommonService.getSipAppInfo(request));
        return "sip/sipAppDetail";
    }


    /**
     * 获取全局号码池
     * @param page
     * @return
     */
    @RequestMapping("/getSipAppNumberPool")
    @ResponseBody
    public PageWrapper getSipAppNumberPool(Page page){
        logger.info("------------------------------------------------GET SipAppDetailController getSipAppNumberPool START------------------------------------------------");
        return sipManagerService.getSipAppNumberPool(page);
    }

    /**
     * 导出全局号码池报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadSipAppNumberPool")
    public ModelAndView downloadSipAppNumberPool(Page page) {
        logger.info("------------------------------------------------GET SipAppDetailController downloadSipAppNumberPool START------------------------------------------------");
        List<Map<String, Object>> totals = sipManagerService.downloadSipAppNumberPool(page);
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
        map.put("title", "全局号码池列表");
        map.put("excelName","全局号码池列表");

        return new ModelAndView(new POIXlsView(), map);
    }

}
