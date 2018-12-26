package com.e9cloud.pcweb.maskLine.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.page.Param;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.CityAreaCodeService;
import com.e9cloud.mybatis.service.MaskLineService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.maskLine.MakePoolRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/6/1.
 */
@Controller
@RequestMapping("/maskLine")
public class MaskLineController extends BaseController {
    @Autowired
    private MaskLineService maskLineService;

    @Autowired
    private CityAreaCodeService cityAreaCodeService;

    @Autowired
    private AppInfoService appInfoService;


    /**=================================================================================================================
     * 私密专线列表
     * @return
     */
    @RequestMapping("/maskLineList")
    public String maskLineList(Model model){
        logger.info("----MaskLineController maskNumberConfig start-------");
        List<CityAreaCode> cityList = cityAreaCodeService.getCityAreaCodeList(new CityAreaCode());
        model.addAttribute("cityList", cityList);
        return "maskLine/maskLineList";
    }

    /**
     * 私密专线列表
     * @return
     */
    @RequestMapping("/pageMaskNumber")
    @ResponseBody
    public PageWrapper pageMaskNumber(Page page){
        logger.info("----MaskLineController pageMaskNumber start-------");
        return maskLineService.pageMaskNumber(page);
    }

    @RequestMapping("/deleteMaskNumber")
    @ResponseBody
    public JSonMessage deleteMaskNumber(String id){
        logger.info("----MaskLineController deleteMaskNumber start-------");

        JSonMessage jSonMessage = new JSonMessage();

        MaskNum maskNum = new MaskNum();
        maskNum.setId(id);
        maskNum = maskLineService.getMaskNumberByObj(maskNum);

        // 待分配的删除
        if ("03".equals(maskNum.getStatus())) {
            maskLineService.deleteMaskNumberById(id);
            jSonMessage.setCode("ok");
            jSonMessage.setMsg("删除数据成功!");
        } else {
            jSonMessage.setCode("error");
            jSonMessage.setMsg("该号码已分配或已锁定，不能删除!");
        }

        logger.info("----MaskLineController deleteMaskNumber end-------");

        return jSonMessage;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadMaskNum")
    public ModelAndView download(Page page) {
        List<Map<String, Object>> totals = maskLineService.downloadMaskNum(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("sid", map.get("sid"));
            m.put("companyName", map.get("companyName"));
            m.put("appid", map.get("appid"));
            m.put("appName", map.get("appName"));
            m.put("city", map.get("city"));
            m.put("areaCode", map.get("areaCode"));
            m.put("number", map.get("number"));
            m.put("status", transformTo(map.get("status")));
            list.add(m);
        });

        List<String> titles = new ArrayList<String>();

        titles.add("account ID");
        titles.add("客户名称");
        titles.add("APP ID");
        titles.add("应用名称");
        titles.add("城市");
        titles.add("区号");
        titles.add("号码");
        titles.add("号码状态");

        List<String> columns = new ArrayList<String>();

        columns.add("sid");
        columns.add("companyName");
        columns.add("appid");
        columns.add("appName");
        columns.add("city");
        columns.add("areaCode");
        columns.add("number");
        columns.add("status");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "私密专线列表");
        map.put("excelName","私密专线列表");

        Map<String, Object> params = page.getParams();

        LogUtil.log("导出私密专线列表", "私密专线列表导出一条记录。导出内容的查询条件为：" + " account ID： "
                + params.get("sid") + "，客户名称：" + params.get("companyName") + "，appid：" + params.get("appid") + "，应用名称："
                + params.get("appName") + "，城市：" + params.get("city") + "，区号：" + params.get("areaCode") + "，号码："
                + params.get("number") + "，号码状态：" + transformTo(params.get("status")), LogType.UPDATE);

        return new ModelAndView(new POIXlsView(), map);
    }

    private String transformTo(Object status) {
        if ("01".equals(status)) {
            return "已分配";
        } else if ("02".equals(status)) {
            return "已锁定";
        } else if ("03".equals(status)) {
            return "待分配";
        }
        return "";
    }

    /**=================================================================================================================
     * 隐私号码池配置
     * @return
     */
    @RequestMapping("/maskNumberPoolConfig")
    public String maskNumberConfig(){
        logger.info("----MaskLineController maskNumberConfig start-------");
        return "maskLine/maskNumberPoolConfig";
    }

    /**
     * 获取隐私号码池列表
     * @param page
     * @return
     */
    @RequestMapping("/getMaskNumberPoolList")
    @ResponseBody
    public PageWrapper getMaskNumberPoolList(Page page){
        logger.info("----pageCallRateUnion start-------");
        return maskLineService.getMaskNumberPoolList(page);
    }

    /**
     * 创建号码池
     * @return
     */
    @RequestMapping("/addMaskNumberPool")
    public String addMaskNumberPool(Model model){
        logger.info("----MaskLineController addMaskNumberPool start-------");
        List<CityAreaCode> cityList = cityAreaCodeService.getCityAreaCodeList(new CityAreaCode());
        model.addAttribute("cityList", cityList);
        return "maskLine/addMaskNumberPool";
    }

    /**
     * 编辑号码池
     * @param model
     * @return
     */
    @RequestMapping("/managerMaskNumberPool")
    public String editMaskNumberPool(Model model, String id, String managerType){
        logger.info("----MaskLineController editMaskNumberPool start-------");
        List<CityAreaCode> cityList = cityAreaCodeService.getCityAreaCodeList(new CityAreaCode());
        model.addAttribute("cityList", cityList);

        MaskNumPool maskNumPool = new MaskNumPool();
        maskNumPool.setId(id);
        model.addAttribute("maskNumPool",maskLineService.getMaskNumberPoolByObje(maskNumPool));

        model.addAttribute("managerType", managerType);
        return "maskLine/managerMaskNumberPool";
    }


    /**
     * 删除隐私号码池
     * @param request
     * @return
     */
    @RequestMapping("/deleteMaskNumberPool")
    @ResponseBody
    public JSonMessageSubmit deleteMaskNumberPool(HttpServletRequest request){
        logger.info("----MaskLineController deleteMaskNumberPool start-------");
        //删除号码池的信息
        MaskNumPool maskNumPool = new MaskNumPool();
        maskNumPool.setId(request.getParameter("id"));
        MaskNumPool maskNumPoolResult = maskLineService.getMaskNumberPoolByObje(maskNumPool);
        if(maskNumPoolResult != null){
            maskLineService.deleteMaskNumberPoolById(request.getParameter("id"));

            //修改相关的隐私号状态
            Map<String, String> map = new HashMap<>();
            map.put("status","02");
            map.put("appid",request.getParameter("appid"));
            maskLineService.updateMaskNumberByAppId(map);

            MaskNum maskNum = new MaskNum();
            maskNum.setStatus("02");
            maskNum.setAppid(request.getParameter("appid"));

            //删除redis中的数据
            List<MaskNum> maskNumList = maskLineService.getMaskNumList(maskNum);
            for (MaskNum maskNumResult : maskNumList) {
                MakePoolRedis.getInstance().delMakeNum(maskNumResult.getAppid(),maskNumResult.getCityAreaCode().getAreaCode(),maskNumResult.getNumber());
            }

            return new JSonMessageSubmit("0","删除数据成功！");
        }else{
            return new JSonMessageSubmit("1","删除数据失败！");
        }
    }

    /**
     * 获取隐私号信息列表
     * @param page
     * @return
     */
    @RequestMapping("/getMaskNumberList")
    @ResponseBody
    public PageWrapper getMaskNumberList(Page page){
        logger.info("----MaskLineController getMaskNumberList start-------");
        return maskLineService.getMaskNumberList(page);
    }

    /**
     * 添加隐私号
     * @return
     */
    @RequestMapping("/addMaskNumber")
    public String addMaskNumber(HttpServletRequest request, Model model){
        logger.info("----MaskLineController addMaskNumber start-------");
        List<CityAreaCode> cityList = cityAreaCodeService.getCityAreaCodeList(new CityAreaCode());
        model.addAttribute("cityList", cityList);
        model.addAttribute("sid", request.getParameter("sid"));
        model.addAttribute("appid", request.getParameter("appid"));
        model.addAttribute("uid", request.getParameter("uid"));
        return "maskLine/addMaskNumber";
    }


    /**
     * 导入隐私号
     * @return
     */
    @RequestMapping("/importMaskNumber")
    public String importMaskNumber(HttpServletRequest request, Model model){
        logger.info("----MaskLineController importMaskNumber start-------");
        model.addAttribute("sid", request.getParameter("sid"));
        model.addAttribute("appid", request.getParameter("appid"));
        model.addAttribute("uid", request.getParameter("uid"));
        return "maskLine/importMaskNumber";
    }

    /**
     * 根据条件获取AppInfo信息
     * @param appinfo
     * @return
     */
    @RequestMapping("/getMaskNumberAppInfo")
    @ResponseBody
    public JSonMessage getMaskNumberAppInfo(AppInfo appinfo){
        logger.info("----MaskLineController getMaskNumberAppInfo start-------");
        AppInfo appInfo = appInfoService.getAppInfoByObj(appinfo);
        if(appInfo == null){
            return new JSonMessage("1","该应用信息不存在！");
        }
        return new JSonMessage("0","信息查询成功！",appInfo);
    }

    @RequestMapping("/getAppInfoById")
    @ResponseBody
    public JSonMessage getAppInfoById(String appId){
        logger.info("----MaskLineController getAppInfoById start-------");
        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appId);
        if(appInfo == null){
            return new JSonMessage("1","该应用信息不存在！");
        }
        return new JSonMessage("0","信息查询成功！",appInfo);
    }


    @RequestMapping("/getAppInfoByMap")
    @ResponseBody
    public JSonMessage getAppInfoByMap(String appId,String busType,String status){
        logger.info("----MaskLineController getAppInfoById start-------");
        Map<String, Object> params = new HashMap<>();
        params.put("appId", appId);
        params.put("busType", busType);
        params.put("status", status);
        AppInfo appInfo = appInfoService.getAppInfoByMap(params);
        if(appInfo == null){
            return new JSonMessage("1","该应用信息不存在！");
        }
        return new JSonMessage("0","信息查询成功！",appInfo);
    }
}
