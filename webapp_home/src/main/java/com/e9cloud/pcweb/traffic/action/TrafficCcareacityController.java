package com.e9cloud.pcweb.traffic.action;

import com.e9cloud.core.common.Tree;
import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.CcArea;
import com.e9cloud.mybatis.domain.CcAreaCity;
import com.e9cloud.mybatis.domain.CcDispatch;
import com.e9cloud.mybatis.service.CcAreaCityService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
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
 * 区域管理
 * Created by hzd on 2016/9/6.
 */
@Controller
@RequestMapping("/smartCloud/traffic/ccareacity")
public class TrafficCcareacityController extends BaseController{

    @Autowired
    private CcAreaCityService ccAreaCityService;

    // 返回调度页面
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request,Model model) {
        logger.info("------------------------------------------------GET TrafficDispatchController index START----------------------------------------------------------------");
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        return "traffic/traffic_ccareacity";
    }

    /**
     * 获取区域分页信息
     * @param page
     * @return
     */
    @RequestMapping("/pageCcArea")
    @ResponseBody
    public PageWrapper getDispatchPageList(HttpServletRequest request, Page page){
        logger.info("------------------------------------------------GET TrafficDispatchController getDispatchPageList START----------------------------------------------------------------");
        Account account = (Account)request.getSession().getAttribute(JSession.USER_INFO);
        page.addParams("sid", account.getSid());
        return ccAreaCityService.pageCcArea(page);
    }

    // 跳转到区域列表页面(新增页面)
    @RequestMapping("toAddArea")
    public String toAddArea(HttpServletRequest request,Model model) {
        Account account = (Account)request.getSession().getAttribute(JSession.USER_INFO);
        model.addAttribute("sid",account.getSid());

        return "traffic/ccareacityAdd";
    }

    // 添加区域
    @RequestMapping("addArea")
    @ResponseBody
    public JSonMessage addArea(HttpServletRequest request,CcArea ccArea, String ccodes) {
        String sid = request.getParameter("sid");
        ccArea.setSid(sid);
        logger.info("addArea ccodes:{}", ccodes);
        ccodes = ccodes.replaceAll("c", "");
        String[] codes = Tools.stringToArray(ccodes, ",");

        List<Tree> citys = ccAreaCityService.addArea(ccArea, codes);

        return citys == null ? new JSonMessage("ok", "") : new JSonMessage("error", "", citys);
    }

    // 跳转到区域列表页面(编辑页面)
    @RequestMapping("toEditArea")
    public String toEditArea(Model model, String areaId) {
        CcArea area = ccAreaCityService.getAreaByAreaId(areaId);
//        UserAdmin userAdmin = new UserAdmin();
//        userAdmin.setSid(area.getSid());
//        userAdmin = userAdminService.getUserAdminWithCompany(userAdmin);
        model.addAttribute("area", area);
//        model.addAttribute("userAdmin", userAdmin);
        return "traffic/ccareacityEdit";
    }

    // 编辑 区域
    @RequestMapping("editArea")
    @ResponseBody
    public JSonMessage editArea(CcArea ccArea, String ccodes) {
        logger.info("editArea ccodes:{}", ccodes);
        ccodes = ccodes.replaceAll("c", "");
        String[] codes = Tools.stringToArray(ccodes, ",");

        List<Tree> citys = ccAreaCityService.editArea(ccArea, codes);

        return citys == null ? new JSonMessage("ok", "") : new JSonMessage("error", "", citys);
    }

    // 删除 区域
    @RequestMapping("/deleteArea")
    @ResponseBody
    public JSonMessage deleteArea(String areaId){
        logger.info("------------------------------------------------GET TrafficCcareacityController deleteArea START----------------------------------------------------------------");

        if (StringUtils.isEmpty(areaId)){
            return new JSonMessage("2","区域删除失败！");
        }
        List<CcDispatch> ccDispatchList = ccAreaCityService.getDispatchByAreaId(areaId);
        StringBuilder dispatchName = new StringBuilder();

        if(ccDispatchList!=null&&ccDispatchList.size()==0){
            ccAreaCityService.deleteCcArea(areaId);
            return new JSonMessage("0","区域删除成功！");
        }else {
            for (CcDispatch ccDispatch:ccDispatchList){
                if(Tools.isNotNullStr(ccDispatch.getDispatchName())) {
                    dispatchName.append(ccDispatch.getDispatchName()).append(",");
                }
            }
            return new JSonMessage("1","区域删除失败！",dispatchName.substring(0,dispatchName.length()-1));
        }

    }

    // 省份 - 城市 树
    @RequestMapping("pctree")
    @ResponseBody
    public List<Tree> pctree(CcAreaCity areaCity) {
        return ccAreaCityService.pctree(areaCity);
    }

    // 省份 - 城市 树
    @RequestMapping("atree")
    @ResponseBody
    public List<Tree> atree(String areaId) {
        return ccAreaCityService.atree(areaId);
    }

    /**
     * aname 区域名称唯一性校验
     * @param ccArea 主账号
     * @return
     */
    @RequestMapping("uniqueAname")
    @ResponseBody
    public JSonMessage uniqueAname(CcArea ccArea) {

        long l = ccAreaCityService.countAreaBySidAndAreaId(ccArea);

        return l > 0 ? new JSonMessage("error", "") : new JSonMessage("ok", "");
    }

    /**
     * 查询某个账户下面已经选择的 城市code
     * @param areaCity
     * @return
     */
    @RequestMapping("getSelectedNodes")
    @ResponseBody
    public List<String> getSelectedNodes(CcAreaCity areaCity) {
        return ccAreaCityService.getSelectedNodes(areaCity);
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadareacity")
    public ModelAndView downloadCityInfo(HttpServletRequest request,Page page) {
        logger.info("=====================================CityController downloadCityInfo Execute=====================================");
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        page.addParams("sid", account.getSid());
        page.setDoDownload(true);
        List<Map<String, Object>> totals = ccAreaCityService.downloadCcArea(page);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> cityMap = new HashMap<String, Object>();
            cityMap.put("companyName", map.get("companyName").toString());
            cityMap.put("aname", map.get("aname").toString());
            if(!"".equals(map.get("remark"))&&map.get("remark")!=null){
                cityMap.put("remark", map.get("remark").toString());
            }else {
                cityMap.put("remark", "");
            }

            list.add(cityMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("所属企业");
        titles.add("区域名称");
        titles.add("区域描述");

        List<String> columns = new ArrayList<String>();
        columns.add("companyName");
        columns.add("aname");
        columns.add("remark");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "区域列表");
        map.put("excelName","区域列表");

        return new ModelAndView(new POIXlsView(), map);
    }
}
