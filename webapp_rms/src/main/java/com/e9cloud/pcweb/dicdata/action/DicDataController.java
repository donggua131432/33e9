package com.e9cloud.pcweb.dicdata.action;

import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.domain.TelnoProvince;
import com.e9cloud.mybatis.service.CityManagerService;
import com.e9cloud.mybatis.service.IDicDataService;
import com.e9cloud.mybatis.service.ProvinceManagerService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典类
 *
 */
@Controller
@RequestMapping("/dicdata")
public class DicDataController extends BaseController {

    // 字典信息
    @Autowired
    private IDicDataService dicDataService;

    // 城市信息
    @Autowired
    private CityManagerService cityManagerService;

    // 省份信息
    @Autowired
    private ProvinceManagerService provinceManagerService;

    // 根据字典表类型查询字典信息
    @RequestMapping("/type")
    @ResponseBody
    public Map<String,Object> findDicListByType(String typekey) throws Exception {
        List<DicData> list = dicDataService.findDicListByType(typekey);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",list);
        return map;
    }

    // 根据父类ID查询字典信息
    @RequestMapping("/pid")
    @ResponseBody
    public Map<String,Object>  findDicListByPid(String pid) throws Exception {
        List<DicData> list = dicDataService.findDicListByPid(pid);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("data",list);
        return map;
    }

    // 城市列表
    @RequestMapping("citys")
    @ResponseBody
    public List<TelnoCity> getCitys(String pcode, boolean diy) {
        Map<String, Object> params = new HashMap<>();
        params.put("pcode", pcode);
        params.put("diy", diy);
        return cityManagerService.getCitysByPcode(params);
    }

    // 省份列表
    @RequestMapping("provinces")
    @ResponseBody
    public List<TelnoProvince> getProvinces() {
        return provinceManagerService.findProvinceByCode("");
    }
}