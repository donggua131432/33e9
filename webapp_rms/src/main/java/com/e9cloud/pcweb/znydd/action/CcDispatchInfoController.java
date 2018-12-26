package com.e9cloud.pcweb.znydd.action;

import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.mybatis.domain.CcDispatchInfo;
import com.e9cloud.mybatis.domain.CcInfo;
import com.e9cloud.mybatis.service.CcDispatchInfoService;
import com.e9cloud.mybatis.service.CcInfoService;
import com.e9cloud.pcweb.BaseController;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 话务调度呼叫中心
 * Created by pengchunchen on 2016/8/10.
 */
@Controller
@RequestMapping(value = "/ccdispatchinfo")
public class CcDispatchInfoController extends BaseController{

    @Autowired
    private CcDispatchInfoService ccDispatchInfoService;
    @Autowired
    private CcInfoService ccInfoService;

    // 编辑话务调度呼叫中心页面
    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    public String toEdit(String dispatchId,String sid, Model model) {
        List<CcDispatchInfo> ccDispatchInfoList = ccDispatchInfoService.queryListByDId(dispatchId);
        List<CcInfo> ccInfoList = ccInfoService.queryCcInfoBySid(sid);
        model.addAttribute("ccDispatchInfoList", ccDispatchInfoList);
        model.addAttribute("ccInfoList", ccInfoList);
        model.addAttribute("dispatchId", dispatchId);

        return "znydd/ccdispatchinfo_edit";
    }

    // 为只能云调度用户修改一个话务调度呼叫中心信息
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage edit(String dispatchListStr,String dispatchId) {
        logger.info("-------------CcDispatchInfoController edit start-------------");
        //删除配置的呼叫中心
        CcDispatchInfo ccDispatchInfo = new CcDispatchInfo();
        ccDispatchInfo.setDispatchId(dispatchId);
        ccDispatchInfoService.delCcDispatchInfo(ccDispatchInfo);
        //添加配置呼叫中心
        List<CcDispatchInfo> ccDispatchInfoList =  new Gson().fromJson(dispatchListStr,List.class);

        ccDispatchInfoService.addCcDispatchInfoList(ccDispatchInfoList);
        logger.info("-------------CcDispatchInfoController edit end-------------");

        return new JSonMessage("ok", "编辑话务调度呼叫中心完成");
    }

}
