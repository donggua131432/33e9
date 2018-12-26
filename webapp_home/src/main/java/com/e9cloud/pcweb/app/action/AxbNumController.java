package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.CityCode;
import com.e9cloud.mybatis.service.AxbNumService;
import com.e9cloud.mybatis.service.MaskNumService;
import com.e9cloud.pcweb.BaseController;
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
 * Created by admin on 2017/4/18.
 */

@Controller
@RequestMapping("/axbNumMgr")
public class AxbNumController extends BaseController {


    @Autowired
    private AxbNumService axbNumService;

    /**
     * 查询隐私号码列表
     */

    @RequestMapping("/index")
    public String secretNumList() throws Exception {
        return "app/axbList";
    }

    /**
     * 分页查询隐私号码信息
     */
    @RequestMapping("/pageAxbNum")
    @ResponseBody
    public PageWrapper pageRechargeRecord(HttpServletRequest request, Page page) throws Exception {
        Account account = this.getCurrUser(request);
        page.getParams().put("sid", account.getSid());
        return axbNumService.pageAxbNum(page);
    }
    /**
     * 查询出城市信息
     */
    @RequestMapping(value = "/getCityInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<CityCode> getCityInfo(HttpServletRequest request){
        //获取当前用户信息
        List<CityCode> cityList = axbNumService.findcityALL();
        return cityList;
    }


    /**
     * 下载报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request,String type, Model model, Page page) {
        Account account = this.getCurrUser(request);
        page.getParams().put("sid", account.getSid());

        List<Map<String, Object>> totals = axbNumService.getpageAxbNumList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("app_name", String.valueOf(total.get("app_name")));
                map.put("appid", String.valueOf(total.get("appid")));
                map.put("cname", String.valueOf(total.get("cname")));
                map.put("area_code", String.valueOf(total.get("area_code")));
                map.put("number", String.valueOf(total.get("number")));
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("应用名称");
        titles.add("APP ID");
        titles.add("城市");
        titles.add("区号");
        titles.add("号码");

        List<String> columns = new ArrayList<String>();

        columns.add("app_name");
        columns.add("appid");
        columns.add("city");
        columns.add("area_code");
        columns.add("number");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "虚拟小号信息列表");
        map.put("excelName","虚拟小号信息报表");
        return new ModelAndView(new POIXlsView(), map);
    }
}
