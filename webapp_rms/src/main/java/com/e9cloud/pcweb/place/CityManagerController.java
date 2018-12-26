package com.e9cloud.pcweb.place;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.PinyinUtils;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
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
 * Created by wangyu on 2016/8/4.
 */
@Controller
@RequestMapping("/cityMgr")
public class CityManagerController extends BaseController{

    @Autowired
    private CityManagerService cityManagerService;

    @Autowired
    private ProvinceManagerService provinceManagerService;

    @Autowired
    private CcAreaCityService areaCityService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private TelnoInfoService telnoInfoService;

    @RequestMapping("/cityMgrIndex")
    public String cityMgrIndex(Model model){
        logger.info("=====================================CityManagerController CityMgrIndex Execute=====================================");
//        getInfoAppendToModel(model);
        return "cityMgr/cityList";
    }

    @RequestMapping("/addCity")
    public String addCity(Model model){
        logger.info("=====================================CityManagerController addCity Execute=====================================");
        getInfoAppendToModel(model);
        return "cityMgr/cityAdd";
    }

    @RequestMapping("/cityEditView")
    public String cityEditView(HttpServletRequest request, Model model){
        logger.info("=====================================CityManagerController cityEditView Execute=====================================");
        String id = request.getParameter("id");
        String operationType = request.getParameter("operationType");
        TelnoCity telnoCity = cityManagerService.findCityById(id);
        model.addAttribute("operationType",operationType);
        model.addAttribute("telnoCity",telnoCity);
        getInfoAppendToModel(model);
        return "cityMgr/cityEditView";
    }

    @RequestMapping("/getInfoAppendToModel")
    @ResponseBody
    private Map getInfoAppendToModel(Model model){
        List<TelnoProvince> province = provinceManagerService.findProvinceByCode(null);
        model.addAttribute("provinceDic", province);
        Map<String,List<TelnoProvince>> map = new HashMap<String,List<TelnoProvince>>();
        map.put("provinceDic", province);
//        userAdminService.getCompanyNameAndSidByPage(null);
        return map;
    }

    /**
     * 分页查询客户信息
     * @param page
     * @return
     */
    @RequestMapping("/pageCityList")
    @ResponseBody
    public PageWrapper pageCityList(Page page){
        logger.info("=====================================CityManagerController pageCityList Execute=====================================");
        return cityManagerService.pageCityList(page);

    }

    /**
     * 删除城市信息
     * @param request
     * @return
     */
    @RequestMapping("/deleteCity")
    @ResponseBody
    public JSonMessageSubmit deleteCity(HttpServletRequest request){
        logger.info("=====================================CityManagerController deleteCity Execute=====================================");
        String id = request.getParameter("id");
        TelnoCity city = cityManagerService.findCityById(id);
        if(city != null){
            if(!city.getCtype()){
                return new JSonMessageSubmit("1","删除失败，公用城市不能删除！");
            }
            CcAreaCity ccAreaCity = areaCityService.findAreaCityByCityCode(city.getCcode());
            if(null!=ccAreaCity){
                CcArea area = areaCityService.getAreaByAreaId(ccAreaCity.getAreaId());
                if(null!=area){
                    return new JSonMessageSubmit("1","删除失败，该城市在【区域管理】中被["+area.getAname()+"]区域占用！");
                }
            }
            TelnoInfo telnoInfo = telnoInfoService.queryTelnoInfoByCcode(city.getCcode());
            if(null!=telnoInfo){
                return new JSonMessageSubmit("1","删除失败，该城市在【号段配置】中被["+telnoInfo.getTelno()+"]号段占用！");
            }
            cityManagerService.deleteCityManager(id);
            return new JSonMessageSubmit("0","信息删除成功！");
        }else{
            return new JSonMessageSubmit("1","该信息已删除！");
        }
    }

    /**
     * 添加城市信息
     * @param request
     * @return
     */
    @RequestMapping("/saveCity")
    @ResponseBody
    public JSonMessageSubmit saveCity(HttpServletRequest request, TelnoCity city)throws BadHanyuPinyinOutputFormatCombination {
        logger.info("=====================================CityManagerController saveCity Execute=====================================");
        TelnoCity cityReturn = cityManagerService.findCityByCode(city.getCcode());
        if(null!=cityReturn){
            return new JSonMessageSubmit("1","城市编号已存在，信息保存失败！");
        }
        TelnoCity telnoCityrResult = cityManagerService.findCityByName(city.getCname());
        if(telnoCityrResult == null){
            int maxId = cityManagerService.getMaxId(null);
            if(maxId<1000){
                city.setId(1000);
            }else {
                city.setId(maxId+1);
            }
            city.setCtype(true);
            city.setPinyin(PinyinUtils.toPinYin(city.getCname()));
            cityManagerService.saveCityManager(city);
            return new JSonMessageSubmit("0","信息保存成功！");
        }else{
            return new JSonMessageSubmit("1","存在该城市，信息保存失败！");
        }
    }

    /**
     * 修改城市信息
     * @param request
     * @return
     */
    @RequestMapping("/updateCity")
    @ResponseBody
    public JSonMessageSubmit updateCity(HttpServletRequest request, TelnoCity city)throws BadHanyuPinyinOutputFormatCombination{
        logger.info("=====================================CityManagerController updateCity Execute=====================================");
        TelnoCity cityReturn = cityManagerService.findCityByCode(city.getCcode());
        TelnoCity cityById = cityManagerService.findCityById(city.getId().toString());
        if(null!=cityReturn && !city.getCcode().equals(cityById.getCcode())){
            return new JSonMessageSubmit("1","城市编号已存在，信息保存失败！");
        }
        String id = request.getParameter("id");
        TelnoCity telnoCityrResult = cityManagerService.findCityById(id);
        if(telnoCityrResult != null){
            city.setPinyin(PinyinUtils.toPinYin(city.getCname()));
            cityManagerService.updateCityManager(city);
            return new JSonMessageSubmit("0","信息保存成功！");
        }else{
            return new JSonMessageSubmit("1","不存在该城市，信息保存失败！");
        }
    }

    /**
     * 将字典转化成MAP格式
     * @param list
     * @return
     */
    public Map<String, String> getDicDataMap(List<DicData> list){
        Map<String, String> map = new HashMap<>();
        for (DicData dicData: list) {
            map.put(dicData.getCode(),dicData.getName());
        }
        return map;
    }

    /**
     * 翻译合作业务类型
     * @param businessType
     * @param map
     * @return
     */
    public String getBusinessTypeName(String businessType, Map<String, String> map){
        String businessTypeStr = "";
        String[] businessTypeStrArr = businessType.split(",");
        for (int i=0; i< businessTypeStrArr.length; i++){
            if(i == (businessTypeStrArr.length-1)){
                businessTypeStr += map.get(businessTypeStrArr[i]);
            }else{
                businessTypeStr += map.get(businessTypeStrArr[i])+",";
            }
        }
        return businessTypeStr;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadCityInfo")
    public ModelAndView downloadCityInfo(Page page) {
        logger.info("=====================================CityController downloadCityInfo Execute=====================================");
        List<Map<String, Object>> totals = cityManagerService.downloadCityInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> cityMap = new HashMap<String, Object>();
            cityMap.put("rowNO", map.get("rowNO").toString());
            cityMap.put("cname", map.get("cname").toString()==null?"":map.get("cname").toString());
            cityMap.put("pname", map.get("pname").toString()==null?"":map.get("pname").toString());
            cityMap.put("areaCode", map.get("areaCode")==null?"":map.get("areaCode"));
            cityMap.put("ccode", map.get("ccode")==null?"":map.get("ccode"));
            cityMap.put("compName", map.get("compName")==null?"":map.get("compName"));
            list.add(cityMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("id");
        titles.add("城市");
        titles.add("所属省份");
        titles.add("城市区号");
        titles.add("城市编号");
        titles.add("所属客户");
        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("cname");
        columns.add("pname");
        columns.add("areaCode");
        columns.add("ccode");
        columns.add("compName");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "城市信息列表");
        map.put("excelName","城市信息列表");
        return new ModelAndView(new POIXlsView(), map);
    }

    // 校验唯一性
    @RequestMapping(value = "checkAreaCodeUnique", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage checkAreaCodeUnique(String areaCode){
        JSonMessage jSonMessage = new JSonMessage();


        TelnoCity city = cityManagerService.findCityByAreaCode(areaCode);
        if (city != null) {
            jSonMessage.setCode("ok");
        }else {
            jSonMessage.setCode("error");
            jSonMessage.setMsg("城市区号不存在!");
        }


        return jSonMessage;
    }

    // 校验唯一性
    @RequestMapping(value = "getCityNameByAreaCode", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage getCityNameByAreaCode(String areacode){
        JSonMessage jSonMessage = new JSonMessage();

        TelnoCity city = cityManagerService.getTelnoCityByAreaCode(areacode);
        if (city != null) {
            jSonMessage.setCode("ok");
            jSonMessage.setData(city);
        }else {
            jSonMessage.setCode("error");
            jSonMessage.setMsg("城市区号不存在!");
        }

        return jSonMessage;
    }
}