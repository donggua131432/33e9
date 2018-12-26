package com.e9cloud.pcweb.ecc.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.service.EccFixPhonePoolService;
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
 * Created by hzd on 2017/2/13.
 */
@Controller
@RequestMapping("/eccFixPhonePool")
public class EccFixPhoneController extends BaseController {
    @Autowired
    private EccFixPhonePoolService eccFixPhonePoolService;
    /**
     * 接听号码-非sip号码查看页面
     * @return
     */
    @RequestMapping("/eccFixPhonePool")
    public String pubNumResPoolMgr(HttpServletRequest request, Model model){

        return "ecc/ecc_fixphone";
    }

    /**
     * 获取ecc 非fixphone列表
     * @param page 分页参数
     * @return 分页信息
     */
    @RequestMapping("pageEccFixPhoneList")
    @ResponseBody
    public PageWrapper pageAppList(Page page) {
        logger.info("=========== EccFixPhoneController pageEccFixPhoneList start ===========");
        return eccFixPhonePoolService.getEccFixPhoneList(page);
    }

    /**
     * 导出SIP号码
     *
     * @return
     */
    @RequestMapping(value = "downloadEccFixPhonePool", method = RequestMethod.GET)
    public ModelAndView downloadSipPhonePool(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = eccFixPhonePoolService.downloadEccFixPhonePool(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("numType", String.valueOf(total.get("numType")).equals("null")?"":String.valueOf(total.get("numType")));
                map.put("number", String.valueOf(total.get("number")).equals("null")?"":String.valueOf(total.get("number")));
                map.put("pname", String.valueOf(total.get("pname")).equals("null")?"":String.valueOf(total.get("pname")));
                map.put("cname", String.valueOf(total.get("cname")).equals("null")?"":String.valueOf(total.get("cname")));
                map.put("companyName", String.valueOf(total.get("companyName")).equals("null")?"":String.valueOf(total.get("companyName")));
                map.put("sid", String.valueOf(total.get("sid")).equals("null")?"":String.valueOf(total.get("sid")));
                map.put("appName", String.valueOf(total.get("appName")).equals("null")?"":String.valueOf(total.get("appName")));
                map.put("appId", String.valueOf(total.get("appId")).equals("null")?"":String.valueOf(total.get("appId")));
                if(String.valueOf(total.get("numType")).equals("null")){
                    map.put("numType", "");
                }else if(String.valueOf(total.get("numType")).equals("02")){
                    map.put("numType", "手机");
                }else if(String.valueOf(total.get("numType")).equals("03")){
                    map.put("numType", "固话");
                }else{
                    map.put("numType", String.valueOf(total.get("numType")));
                }

                if("".equals(total.get("updatetime")) || null == total.get("updatetime")){
                    map.put("updatetime", "");
                } else {
                    map.put("updatetime", String.valueOf(total.get("updatetime").toString().substring(0,19)));
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("接听号码类型");
        titles.add("接听号码");
        titles.add("省份");
        titles.add("城市");
        titles.add("修改时间");
        titles.add("客户名称");
        titles.add("Account ID");
        titles.add("应用名称");
        titles.add("App ID");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("numType");
        columns.add("number");
        columns.add("pname");
        columns.add("cname");
        columns.add("updatetime");
        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appId");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "ECC非SIP号码资源池列表");
        map.put("excelName","ECC非SIP号码资源池列表");
        return new ModelAndView(new POIXlsView(), map);
    }
}
