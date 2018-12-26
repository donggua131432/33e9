package com.e9cloud.pcweb.dicdata.action;

import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.IDicDataService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


/**
 * 字典类
 *
 */
@Controller
@RequestMapping("/dicdata")
public class DicDataController extends BaseController{


    @Autowired
    private IDicDataService dicDataService;

    @RequestMapping("/type")
    @ResponseBody
    public Map<String,Object> findDicListByType(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        String typekey = request.getParameter("typekey");
        List<DicData> list = dicDataService.findDicListByType(typekey);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",list);
        return map;
    }

    @RequestMapping("/pid")
    @ResponseBody
    public Map<String,Object>  findDicListByPid(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        String pid = request.getParameter("pid");
        List<DicData> list = dicDataService.findDicListByPid(pid);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",list);
        return map;
    }

    // 城市列表
    @RequestMapping("citys")
    @ResponseBody
    public List<TelnoCity> getCitys(String pcode) {
        return dicDataService.getCitysByPcode(pcode);
    }

    // 省份列表
    @RequestMapping("provinces")
    @ResponseBody
    public List<Province> getProvinces() {
        return dicDataService.getProvinceByCode("");
    }
}