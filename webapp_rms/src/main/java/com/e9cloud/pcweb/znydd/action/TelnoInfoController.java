package com.e9cloud.pcweb.znydd.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.domain.TelnoInfo;
import com.e9cloud.mybatis.domain.TelnoOperator;
import com.e9cloud.mybatis.domain.TelnoProvince;
import com.e9cloud.mybatis.service.TelnoInfoService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智能云调度-号段配置
 * Created by Administrator on 2016/8/1.
 */
@Controller
@RequestMapping(value = "/numberpart")
public class TelnoInfoController extends BaseController{

    @Autowired
    private TelnoInfoService telnoInfoService;

    // 号段列表
    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String toList() {
        return "znydd/numberpart_list";
    }

    // 创建号段页面
    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public String toAdd() {
        return "znydd/numberpart_add";
    }

    // 显示号段信息
    @RequestMapping(value = "toShow", method = RequestMethod.GET)
    public String toShow(String id, Model model) {
        TelnoInfo telnoInfo = telnoInfoService.queryTelnoInfoById(id);
        model.addAttribute("telnoInfo", telnoInfo);

        return "znydd/numberpart_show";
    }

    // 编辑号段页面
    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    public String toEdit(String id, Model model) {
        TelnoInfo telnoInfo = telnoInfoService.queryTelnoInfoById(id);
        model.addAttribute("telnoInfo", telnoInfo);

        return "znydd/numberpart_edit";
    }

    // 为只能云调度用户添加一个号段
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage add(TelnoInfo telnoInfo) {
        logger.info("-------------TelnoInfoController add start-------------");
        TelnoCity tcity = telnoInfoService.queryCityByCcode(telnoInfo.getCcode());
        telnoInfo.setAreacode(tcity.getAreaCode());
        telnoInfoService.saveTelnoInfo(telnoInfo);
        logger.info("-------------TelnoInfoController add end-------------");

        return new JSonMessage("ok", "新增号段完成");
    }

    // 为只能云调度用户修改一个号段信息
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage edit(TelnoInfo telnoInfo) {
        logger.info("-------------TelnoInfoController edit start-------------");
        telnoInfoService.updateTelnoInfo(telnoInfo);
        logger.info("-------------TelnoInfoController edit end-------------");

        return new JSonMessage("ok", "编辑号段完成");
    }

    // 删除一个号段
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage delete(TelnoInfo telnoInfo) {
        logger.info("-------------TelnoInfoController delete start-------------");
        telnoInfoService.delTelnoInfo(telnoInfo);
        logger.info("-------------TelnoInfoController delete end-------------");

        return new JSonMessage("ok", "删除号段完成");
    }

    // 分页查询号段列表
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageList(Page page) {
//        page.addParams("ubustype","2");
//        page.addParams("abustype","01");
        return telnoInfoService.pageTelnoInfoList(page);
    }

    // 校验唯一性
    @RequestMapping(value = "checkTelnoUnique", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage checkTelnoUnique(TelnoInfo telnoInfo){
        JSonMessage jSonMessage = new JSonMessage();

        logger.info("-------------TelnoInfoController checkTelnoUnique start-------------");

        Integer count = telnoInfoService.countTelnoInfo(telnoInfo);
        if (count == 0) {
            jSonMessage.setCode("ok");
        }

        logger.info("-------------TelnoInfoController checkTelnoUnique start-------------");

        return jSonMessage;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadTelnoInfo")
    public ModelAndView downloadTelnoInfo(Page page) {
        logger.info("=====================================TelnoInfoController downloadTelnoInfo Execute=====================================");
        List<Map<String, Object>> totals = telnoInfoService.downloadTelnoInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> numBlackMap = new HashMap<String, Object>();
            numBlackMap.put("rowNO", map.get("rowNO").toString().replace(".0",""));
            numBlackMap.put("pname", map.get("pname"));
            numBlackMap.put("cname", map.get("cname"));
            numBlackMap.put("telno", map.get("telno"));
            numBlackMap.put("oname", map.get("oname"));
            list.add(numBlackMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("ID");
        titles.add("省份名称");
        titles.add("城市");
        titles.add("号段");
        titles.add("运营商");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("pname");
        columns.add("cname");
        columns.add("telno");
        columns.add("oname");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "号段配置");
        map.put("excelName","号段配置");

       /* Map<String, Object> params = page.getParams();
        LogUtil.log("导出号码黑名单列表", "号码黑名单导出一条记录。导出内容的查询条件为："
                + " id： " + params.get("id") + "，创建时间：" + params.get("createTime")
                + "，号码：" + params.get("number") + "，备注原因："+ params.get("remark"), LogType.UPDATE);*/

        return new ModelAndView(new POIXlsView(), map);
    }

    // 查询所有运营商
    @RequestMapping(value = "queryOperatorList", method = RequestMethod.POST)
    @ResponseBody
    public List<TelnoOperator> queryOperatorList() {
        return telnoInfoService.queryOperatorList();
    }

    // 查询所有省份
    @RequestMapping(value = "queryProvinceList", method = RequestMethod.POST)
    @ResponseBody
    public List<TelnoProvince> queryProvinceList() {
        return telnoInfoService.queryProvinceList();
    }

    // 根据省份编号查询所有城市
    @RequestMapping(value = "queryCityList", method = RequestMethod.POST)
    @ResponseBody
    public List<TelnoCity> queryCityList(String pcode) {
        Map<String, String> map = new HashMap<>();
        map.put("pcode",pcode);
        return telnoInfoService.queryCityList(map);
    }


    // 模糊查询省份
    @RequestMapping(value = "queryProvinceListByPname")
    @ResponseBody
    public List<TelnoProvince> queryProvinceListByPname(Page page) {
        return telnoInfoService.queryProvinceListByPname(page);
    }
}
