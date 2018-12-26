package com.e9cloud.pcweb.znydd.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.CcDispatch;
import com.e9cloud.mybatis.domain.CcDispatchInfo;
import com.e9cloud.mybatis.domain.CcInfo;
import com.e9cloud.mybatis.service.CcDispatchInfoService;
import com.e9cloud.mybatis.service.CcDispatchService;
import com.e9cloud.mybatis.service.CcInfoService;
import com.e9cloud.pcweb.BaseController;
import com.google.gson.Gson;
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
 * 话务调度
 * Created by pengchunchen on 2016/8/8.
 */
@Controller
@RequestMapping(value = "/ccdispatch")
public class CcDispatchController extends BaseController{

    @Autowired
    private CcDispatchService ccDispatchService;
    @Autowired
    private CcInfoService ccInfoService;
    @Autowired
    private CcDispatchInfoService ccDispatchInfoService;

    // 话务调度列表
    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String toList() {
        return "znydd/ccdispatch_list";
    }

    // 创建话务调度页面
    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public String toAdd() {
        return "znydd/ccdispatch_add";
    }

    /**
     * 获取配置的呼叫中心
     * @param sid
     * @return
     */
    @RequestMapping("/getCcDispatchInfoList")
    @ResponseBody
    public List<CcInfo> getCcDispatchInfoList(String sid){
        logger.info("------------------------------------------------GET TrafficDispatchController getCcDispatchInfoList START----------------------------------------------------------------");
        return ccInfoService.queryCcInfoBySid(sid);
    }

    // 显示话务调度信息
    @RequestMapping(value = "toShow", method = RequestMethod.GET)
    public String toShow(String id, Model model) {
        CcDispatch ccDispatch = ccDispatchService.queryCcDispatchById(id);
        model.addAttribute("ccDispatch", ccDispatch);

        return "znydd/ccdispatch_show";
    }

    // 编辑话务调度页面
    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    public String toEdit(String id,String dispatchId,String sid, Model model) {
        CcDispatch ccDispatch = ccDispatchService.queryCcDispatchById(id);
        ccDispatch.setTimeStartShow(strFormat(ccDispatch.getTimeStart().toString()));
        ccDispatch.setTimeEndShow(strFormat(ccDispatch.getTimeEnd().toString()));
        model.addAttribute("ccDispatch", ccDispatch);
        model.addAttribute("weekdays", Tools.decimalToBitStr(ccDispatch.getWeekday()));

        List<CcDispatchInfo> ccDispatchInfoList = ccDispatchInfoService.queryListByDId(dispatchId);
        List<CcInfo> ccInfoList = ccInfoService.queryCcInfoBySid(sid);
        model.addAttribute("ccDispatchInfoList", ccDispatchInfoList);
        model.addAttribute("ccInfoList", ccInfoList);
        model.addAttribute("dispatchId", dispatchId);

        return "znydd/ccdispatch_edit";
    }

    // 为只能云调度用户添加一个话务调度
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage add(String dispatchListStr,String ccStr) {
        logger.info("-------------CcDispatchController add start-------------");
        List<CcDispatchInfo> ccDispatchInfoList = JSonUtils.readValue(dispatchListStr, List.class,CcDispatchInfo.class);
        CcDispatch ccDispatch = new Gson().fromJson(ccStr,CcDispatch.class);
        long l = ccDispatchService.countDispatchBySidAndDispatchName(ccDispatch);
        if(l > 0){
            return new JSonMessage(R.ERROR, "该调度名称已存在!");
        }else{
            //自动生成dispatchId
            String dispatchId = ID.randomUUID();
            ccDispatch.setDispatchId(dispatchId);
            ccDispatch.setStatus("00");
            for (CcDispatchInfo ccDispatchInfo:ccDispatchInfoList){
                ccDispatchInfo.setDispatchId(dispatchId);
            }
            boolean submitFlag = false;
            CcInfo ccInfo = null;
            for(CcDispatchInfo ccDispatchInfo:ccDispatchInfoList){
                ccInfo = new CcInfo();
                ccInfo.setSubid(ccDispatchInfo.getSubid());
                Integer countcc = ccInfoService.countCcInfo(ccInfo);
                if(countcc > 0){
                    submitFlag = true;
                }else{
                    submitFlag = false;
                    break;
                }
            }

            if (submitFlag == true){
                ccDispatchService.saveCcDispatch(ccDispatch,ccDispatchInfoList);
                return new JSonMessage("ok", "新增话务调度完成!");
            }else {
                List<CcInfo> ccInfoList = ccInfoService.queryCcInfoBySid(ccDispatch.getSid());
                return new JSonMessage("recommit", "提交了已删除的呼叫中心，请重新添加！",ccInfoList);
            }

        }

    }

    // 为只能云调度用户修改一个话务调度信息
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage edit(CcDispatch ccDispatch,String dispatchListStr,String ccStr,String dispatchId) {
        logger.info("-------------CcDispatchController edit start-------------");
        CcDispatch ccDispatch1 = new Gson().fromJson(ccStr,CcDispatch.class);
        long l = ccDispatchService.countDispatchBySidAndDispatchName(ccDispatch1);
        if(l > 0){
            return new JSonMessage(R.ERROR, "该调度名称已存在!");
        }else {
            //删除配置的呼叫中心
            CcDispatchInfo ccDispatchInfo = new CcDispatchInfo();
            ccDispatchInfo.setDispatchId(dispatchId);
            //配置呼叫中心
            List<CcDispatchInfo> ccDispatchInfoList = JSonUtils.readValue(dispatchListStr, List.class,CcDispatchInfo.class);
            //添加调度


            boolean submitFlag = false;
            CcInfo ccInfo = null;
            for(CcDispatchInfo ccDispatchInfo1:ccDispatchInfoList){
                ccInfo = new CcInfo();
                ccInfo.setSubid(ccDispatchInfo1.getSubid());
                Integer countcc = ccInfoService.countCcInfo(ccInfo);
                if(countcc > 0){
                    submitFlag = true;
                }else{
                    submitFlag = false;
                    break;
                }
            }

            if (submitFlag == true){
                ccDispatchService.updateCcDispatch(ccDispatch1,ccDispatchInfo,ccDispatchInfoList);
                return new JSonMessage("ok", "编辑话务调度完成!");
            }else {
                List<CcInfo> ccInfoList = ccInfoService.queryCcInfoBySid(ccDispatch1.getSid());
                return new JSonMessage("recommit", "提交了已删除的呼叫中心，请重新添加！",ccInfoList);
            }
        }
    }

    // 分页查询话务调度列表
    @RequestMapping(value = "pageList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageList(Page page) {
//        page.addParams("ubustype","2");
        page.addParams("abustype","01");
        PageWrapper pageWrapper=ccDispatchService.pageCcDispatchList(page);
        return pageWrapper;
    }

    //批量删除全局号码
    @RequestMapping("deleteDispatch")
    @ResponseBody
    public Map<String, String> deleteNum (HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        String dispatchId = request.getParameter("dispatchId");
        if (StringUtils.isEmpty(id)){
            map.put("code","99");
        }
        CcDispatch ccDispatch = new CcDispatch();
        ccDispatch.setId(Integer.parseInt(id));
        ccDispatch.setStatus(status);
        ccDispatch.setDispatchId(dispatchId);
        ccDispatchService.deleteCcDispatch(ccDispatch);
        map.put("code","00");
        return map;
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadCcDispatch")
    public ModelAndView downloadCcDispatch(Page page) {
        logger.info("=====================================CcDispatchController downloadCcDispatch Execute=====================================");
        List<Map<String, Object>> totals = ccDispatchService.downloadCcDispatch(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> numBlackMap = new HashMap<String, Object>();
            numBlackMap.put("rowNO", map.get("rowNO").toString().replace(".0",""));
            numBlackMap.put("ctime", map.get("ctime").toString().replace(".0",""));
            numBlackMap.put("name", map.get("name"));
            numBlackMap.put("sid", map.get("sid"));
            numBlackMap.put("app_name", map.get("app_name"));
            numBlackMap.put("appid", map.get("appid"));
            numBlackMap.put("aname", map.get("aname"));
            numBlackMap.put("time_start", strFormat(map.get("time_start").toString()));
            numBlackMap.put("time_end", strFormat(map.get("time_end").toString()));
            numBlackMap.put("valid_date", map.get("valid_date").toString().replace(".0",""));
            if(!"".equals(map.get("ccname"))&&map.get("ccname")!=null){
                numBlackMap.put("ccname", map.get("ccname").toString());
            }else {
                numBlackMap.put("ccname", "");
            }
            list.add(numBlackMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("rowNO");
        titles.add("创建时间");
        titles.add("客户名称");
        titles.add("account id");
        titles.add("应用名称");
        titles.add("APP ID");
        titles.add("调度区域");
        titles.add("开始时间");
        titles.add("结束时间");
        titles.add("生效时间");
        titles.add("呼叫中心");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("ctime");
        columns.add("name");
        columns.add("sid");
        columns.add("app_name");
        columns.add("appid");
        columns.add("aname");
        columns.add("time_start");
        columns.add("time_end");
        columns.add("valid_date");
        columns.add("ccname");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "话务调度列表");
        map.put("excelName","话务调度列表");

       /* Map<String, Object> params = page.getParams();
        LogUtil.log("导出号码黑名单列表", "号码黑名单导出一条记录。导出内容的查询条件为："
                + " id： " + params.get("id") + "，创建时间：" + params.get("createTime")
                + "，号码：" + params.get("number") + "，备注原因："+ params.get("remark"), LogType.UPDATE);*/

        return new ModelAndView(new POIXlsView(), map);
    }

    // 查询所有调度区域
    @RequestMapping("queryCcAreaList1")
    @ResponseBody
    public PageWrapper queryCcAreaList1(Page page) {
        return ccDispatchService.queryCcAreaList1(page);
    }

    // 根据sid查询相应的调度区域
    @RequestMapping(value = "queryCcAreaList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper queryCcAreaList(Page page) {
        return ccDispatchService.queryCcAreaList(page);
    }

    public String strFormat(String str) {
        String strFormat="";
        if(str != null && !"".equals(str)){
            if(str.length()==1){
                strFormat="00:0"+str;
            }else if(str.length()==2){
                strFormat="00:"+str;
            }else if(str.length()==3){
                strFormat="0"+str.substring(0,1)+":"+str.substring(1);
            }else if(str.length()==4){
                strFormat=str.substring(0,2)+":"+str.substring(2);
            }
        }
        return strFormat;
    }

    // 查找智能云调度客户
    @RequestMapping("getCompanyNameAndSidByPage")
    @ResponseBody
    public PageWrapper getCompanyNameAndSidByPage(Page page){
        logger.info("-------------CcDispatchController getCompanyNameAndSidByPage start-------------");

        return ccDispatchService.getCompanyNameAndSidByPage(page);
    }

    /**
     * aname 调度名称唯一性校验
     * @param ccDispatch 主账号
     * @return
     */
    @RequestMapping("uniqueAname")
    @ResponseBody
    public JSonMessage uniqueAname(CcDispatch ccDispatch) {

        long l = ccDispatchService.countDispatchBySidAndDispatchName(ccDispatch);

        return l > 0 ? new JSonMessage(R.ERROR, "") : new JSonMessage(R.OK, "");
    }

}
