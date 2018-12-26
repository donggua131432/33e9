package com.e9cloud.pcweb.place;

import com.e9cloud.core.common.Tree;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.CcArea;
import com.e9cloud.mybatis.domain.CcAreaCity;
import com.e9cloud.mybatis.domain.CcDispatch;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.CcAreaCityService;
import com.e9cloud.mybatis.service.CcDispatchService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.pcweb.BaseController;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
 * 智能云调度：区域配置
 * Created by Administrator on 2016/8/11.
 */
@Controller
@RequestMapping("/ccareacity")
public class CcAreaCityController extends BaseController {

    @Autowired
    private CcAreaCityService areaCityService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private CcDispatchService ccDispatchService;

    // 跳转到区域列表页面
    @RequestMapping("toList")
    public String toList() {
        return "ccareacity/list";
    }

    // 分页查询区域列表
    @RequestMapping(value = "pageCcArea", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageAreaCity(Page page) {
        return areaCityService.pageCcArea(page);
    }

    // 跳转到区域列表页面(新增页面)
    @RequestMapping("toAddArea")
    public String toAddArea() {
        return "ccareacity/areaAdd";
    }

    // 跳转到区域列表页面(编辑页面)
    @RequestMapping("toEditArea")
    public String toEditArea(Model model, String areaId) {
        CcArea area = areaCityService.getAreaByAreaId(areaId);
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(area.getSid());
        userAdmin = userAdminService.getUserAdminWithCompany(userAdmin);
        model.addAttribute("area", area);
        model.addAttribute("userAdmin", userAdmin);
        return "ccareacity/areaEdit";
    }

    // 跳转到区域列表页面(显示页面)
    @RequestMapping("toShowArea")
    public String toShowArea(Model model, String areaId) {
        CcArea area = areaCityService.getAreaByAreaId(areaId);
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(area.getSid());
        userAdmin = userAdminService.getUserAdminWithCompany(userAdmin);
        model.addAttribute("area", area);
        model.addAttribute("userAdmin", userAdmin);
        return "ccareacity/areaShow";
    }

    // 添加区域
    @RequestMapping("addArea")
    @ResponseBody
    public JSonMessage addArea(CcArea ccArea, String ccodes)throws BadHanyuPinyinOutputFormatCombination {
        logger.info("addArea ccodes:{}", ccodes);
        ccodes = ccodes.replaceAll("c", "");
        String[] codes = Tools.stringToArray(ccodes, ",");

        List<Tree> citys = areaCityService.addArea(ccArea, codes);

        return citys == null ? new JSonMessage(R.OK, "") : new JSonMessage(R.ERROR, "", citys);
    }

    // 编辑 区域
    @RequestMapping("editArea")
    @ResponseBody
    public JSonMessage editArea(CcArea ccArea, String ccodes)throws BadHanyuPinyinOutputFormatCombination {
        logger.info("editArea ccodes:{}", ccodes);
        ccodes = ccodes.replaceAll("c", "");
        String[] codes = Tools.stringToArray(ccodes, ",");

        List<Tree> citys = areaCityService.editArea(ccArea, codes);

        return citys == null ? new JSonMessage(R.OK, "") : new JSonMessage(R.ERROR, "", citys);
    }

    //删除区域
    @RequestMapping("deleteArea")
    @ResponseBody
    public Map<String, String> deleteNum (HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        String areaId = request.getParameter("areaId");
        if (StringUtils.isEmpty(areaId)){
            map.put("code","99");
        }
        List<CcDispatch> ccDispatchList = ccDispatchService.getDispatchByAreaId(areaId);
        StringBuilder dispatchName = new StringBuilder();

        if(ccDispatchList!=null&&ccDispatchList.size()==0){
            areaCityService.deleteCcArea(areaId);
            map.put("code","00");
        }else {
            for (CcDispatch ccDispatch:ccDispatchList){
                if(Tools.isNotNullStr(ccDispatch.getDispatchName())){
                    dispatchName.append(ccDispatch.getDispatchName()).append(",");
                }

            }
            map.put("code","01");
            map.put("msg","区域已被:("+dispatchName.substring(0,dispatchName.length()-1)+")调度使用，不能删除！");
        }
        return map;
    }

    // 省份 - 城市 树
    @RequestMapping("pctree")
    @ResponseBody
    public List<Tree> pctree(CcAreaCity areaCity) {
        return areaCityService.pctree(areaCity);
    }

    // 省份 - 城市 树
    @RequestMapping("atree")
    @ResponseBody
    public List<Tree> atree(String areaId) {
        return areaCityService.atree(areaId);
    }

    /**
     * aname 区域名称唯一性校验
     * @param ccArea 主账号
     * @return
     */
    @RequestMapping("uniqueAname")
    @ResponseBody
    public JSonMessage uniqueAname(CcArea ccArea) {

        long l = areaCityService.countAreaBySidAndAreaId(ccArea);

        return l > 0 ? new JSonMessage(R.ERROR, "") : new JSonMessage(R.OK, "");
    }

    /**
     * 查询某个账户下面已经选择的 城市code
     * @param areaCity
     * @return
     */
    @RequestMapping("getSelectedNodes")
    @ResponseBody
    public List<String> getSelectedNodes(CcAreaCity areaCity) {
        return areaCityService.getSelectedNodes(areaCity);
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadareacity")
    public ModelAndView downloadCityInfo(Page page) {
        logger.info("=====================================CityController downloadCityInfo Execute=====================================");
        page.setDoDownload(true);
        List<Map<String, Object>> totals = areaCityService.downloadCcArea(page);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> cityMap = new HashMap<String, Object>();
            cityMap.put("rowNO", Double.valueOf(map.get("rowNO").toString()).intValue());
            cityMap.put("companyName", map.get("companyName").toString());
            cityMap.put("aname", map.get("aname").toString());
            list.add(cityMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("所属企业");
        titles.add("区域名称");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("companyName");
        columns.add("aname");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "区域列表");
        map.put("excelName","区域列表");

        return new ModelAndView(new POIXlsView(), map);
    }

}
