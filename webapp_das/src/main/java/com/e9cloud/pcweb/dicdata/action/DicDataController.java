package com.e9cloud.pcweb.dicdata.action;

import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.service.IDicDataService;
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

    @Autowired
    private IDicDataService dicDataService;

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
}