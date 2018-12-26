package com.e9cloud.pcweb.bill.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.CallCenterService;
import com.e9cloud.mybatis.service.FeeListService;
import com.e9cloud.mybatis.service.RechargeRecordService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  呼叫中心列表
 * Created by lixin on 2016/8/4.
 */
@Controller
@RequestMapping("/callCenter")
public class CallCenterController extends BaseController {

    @Autowired
    private AppService appService;
    @Autowired
    private CallCenterService callCenterService;

    /**
     * 展示呼叫中心页面
     */
    @RequestMapping("/query")
    public String queryCall(HttpServletRequest request, Model model) throws Exception {

        Account account = this.getCurrUser(request);
        model.addAttribute("callCenter", callCenterService.getAllCallCenterInfo(account.getSid()));
        model.addAttribute("appInfo", appService.findAppInfoBySIdAndBusType(account.getSid(), Constants.APP_BUS_955));

        model.addAttribute("flag", callCenterService.checkDefaultCc(account.getSid()));

        model.addAttribute("Default", callCenterService.getCcInfoByDefault(account.getSid()));

        return "app/callCenter";
    }

    /**
     * 分页呼叫中心列表
     */
    @RequestMapping(value = "pageCallList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageCallList(HttpServletRequest request,Page page) throws Exception {
        Account account = this.getCurrUser(request);
        page.getParams().put("sid", account.getSid());
        return callCenterService.pageCallList(page);
    }


    /**
     * 修改缺省调度
     */
    @RequestMapping("/defaultCc")
    @ResponseBody
    public Map<String,String> defaultCc(HttpServletRequest request) throws Exception {

        Map<String,String> map = new HashMap<String, String>();

        String ccid = request.getParameter("ccid");
        String checked = request.getParameter("checked");

        Account account =  (Account) request.getSession().getAttribute("userInfo");
        if("".equals(ccid)){
            map.put("code","01");
            return map;
        }
        try{
            CcInfo ccInfo = new CcInfo();
            ccInfo.setSid(account.getSid());
            ccInfo.setSubid(ccid);
            logger.info(ccid);
            if ("false".equals(checked)){
                callCenterService.updateDefault(ccInfo);
                callCenterService.updateDefault2(ccInfo);
            }else {
                callCenterService.setDefault(ccInfo);
            }
        }catch (Exception e){
            logger.info("----Update defaultCc error-------",e);
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        return map;
    }

    @RequestMapping("/fucdefaultCc")
    @ResponseBody
    public Map<String,String> fucdefaultCc(HttpServletRequest request) throws Exception {

        Map<String,String> map = new HashMap<String, String>();

        String ccid = request.getParameter("ccid");
        String checked = request.getParameter("checked");

        Account account =  (Account) request.getSession().getAttribute("userInfo");
        if("".equals(ccid)){
            map.put("code","01");
            return map;
        }
        try{
            CcInfo ccInfo = new CcInfo();
            ccInfo.setSid(account.getSid());
            ccInfo.setSubid(ccid);
            if ("true".equals(checked)){
                callCenterService.updateDefault(ccInfo);
                callCenterService.updateDefault2(ccInfo);
            }else {
                callCenterService.setDefault(ccInfo);
            }

        }catch (Exception e){
            logger.info("----Update defaultCc error-------",e);
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        return map;
    }

    @RequestMapping("/resetdefaultCc")
    @ResponseBody
    public Map<String,String> resetdefaultCc(HttpServletRequest request) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        try{
            CcInfo ccInfo = new CcInfo();
            ccInfo.setSid(account.getSid());
            callCenterService.setDefault(ccInfo);

        }catch (Exception e){
            logger.info("----Update defaultCc error-------",e);
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        return map;
    }
}
