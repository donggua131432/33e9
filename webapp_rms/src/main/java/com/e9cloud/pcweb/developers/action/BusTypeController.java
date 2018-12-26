package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.BusTypeService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
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
 * Created by admin on 2016/8/22.
 */

@Controller
@RequestMapping(value = "/busType")
public class BusTypeController extends BaseController {

    @Autowired
    private BusTypeService busTypeService;
    @Autowired
    private AppInfoService appInfoService;


    @RequestMapping(value ="busTypeConfig", method = RequestMethod.GET)
    public String feeRateConfig(String sid,Model model){
        model.addAttribute("sid",sid);
        return "developersMgr/busTypeConfig";
    }


    /**
     * 开通业务
     * @return
     */
    @RequestMapping(value = "addBusType", method = RequestMethod.GET)
    public String addMaskRate(HttpServletRequest request, Model model,String sid){

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "01");
        BusinessType znyddType = busTypeService.checkBusinessInfo(params);
        model.addAttribute("znyddType",znyddType);
        model.addAttribute("sid",sid);

        Map<String, Object> paramRest = new HashMap<>();
        paramRest.put("sid", sid);
        paramRest.put("busType", "02");
        BusinessType RestType = busTypeService.checkBusinessInfo(paramRest);
        model.addAttribute("RestType",RestType);

        Map<String, Object> paramSid = new HashMap<>();
        paramSid.put("sid", sid);
        paramSid.put("busType", "03");
        BusinessType SipType = busTypeService.checkBusinessInfo(paramSid);
        model.addAttribute("SipType",SipType);

        Map<String, Object> paramPhone = new HashMap<>();
        paramPhone.put("sid", sid);
        paramPhone.put("busType", "05");
        BusinessType PhoneSip = busTypeService.checkBusinessInfo(paramPhone);
        model.addAttribute("PhoneSip",PhoneSip);


        Map<String, Object> paramEcc = new HashMap<>();
        paramEcc.put("sid", sid);
        paramEcc.put("busType", "06");
        BusinessType EccType = busTypeService.checkBusinessInfo(paramEcc);
        model.addAttribute("EccType",EccType);


        Map<String, Object> paramAxb = new HashMap<>();
        paramAxb.put("sid", sid);
        paramAxb.put("busType", "07");
        BusinessType AxbType = busTypeService.checkBusinessInfo(paramAxb);
        model.addAttribute("AxbType",AxbType);


        return "developersMgr/addBusType";
    }


    /**
     * 开通Sip
     * @return
     */
    @RequestMapping("/openSip")
    @ResponseBody
    public Map<String,String> openSip(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "03");
        String countforbidden = busTypeService.countAppForbidden(params);
        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }
            map.put("code","00");
            BusinessType businessType = new BusinessType();
            businessType.setSid(sid);
            businessType.setBusType("03");
            businessType.setStatus("00");
            businessType.setCreateDate(new Date());
            busTypeService.openBusinessType(businessType);

            if(!"0".equals(countforbidden)){
                map.put("code","02");
                return map;
            }else {
                //自动创建应用
                AppInfo appInfo = new AppInfo();
                appInfo.setStatus("00");
                appInfo.setCreateDate(new Date());
                appInfo.setAppid(ID.randomUUID());
                appInfo.setBusType("03");
                appInfo.setAppName("应用001");
                appInfo.setSid(sid);
                appInfoService.saveAppInfo(appInfo);
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }


        return map;
    }
    /**
     * 关闭Sip业务
     * @return
     */
    @RequestMapping("/closeSip")
    @ResponseBody
    public Map<String,String> closeSip(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "03");
        String count = busTypeService.countApp(params);

//        String count = busTypeService.countApp3(sid);

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }

            if(!"0".equals(count)){
                map.put("code","02");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        BusinessType businessType = new BusinessType();
        businessType.setStatus("01");
        businessType.setBusType("03");
        businessType.setSid(sid);
        busTypeService.updateStatus(businessType);
        return map;
    }

    /**
     * 开通智能云调度业务
     * @return
     */
    @RequestMapping("/openZnydd")
    @ResponseBody
    public Map<String,String> openZnydd(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();


        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "01");
        String countforbidden = busTypeService.countAppForbidden(params);

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }
            map.put("code","00");
            BusinessType businessType = new BusinessType();
            businessType.setSid(sid);
            businessType.setBusType("01");
            businessType.setStatus("00");
            businessType.setCreateDate(new Date());
            busTypeService.openBusinessType(businessType);
            if(!"0".equals(countforbidden)){
                map.put("code","02");
                return map;
            }else {
                //自动创建应用
                AppInfo appInfo = new AppInfo();
                appInfo.setStatus("00");
                appInfo.setCreateDate(new Date());
                appInfo.setAppid(ID.randomUUID());
                appInfo.setBusType("01");
                appInfo.setAppName("应用001");
                appInfo.setCallNo("955XX");
                appInfo.setSid(sid);
                appInfoService.saveAppInfo(appInfo);
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }

        return map;
    }
    /**
     * 关闭---智能云调度业务
     * @return
     */
    @RequestMapping("/closeZnydd")
    @ResponseBody
    public Map<String,String> closeZnydd(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "01");
        String count = busTypeService.countApp(params);
        logger.info("-----count--"+count);

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }

            if(!"0".equals(count)){
                map.put("code","02");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        BusinessType businessType = new BusinessType();
        businessType.setStatus("01");
        businessType.setBusType("01");
        businessType.setSid(sid);
        busTypeService.updateStatus(businessType);
        return map;
    }


    /**
     * 开通SipPhone
     * @return
     */
    @RequestMapping("/openPhone")
    @ResponseBody
    public Map<String,String> openPhone(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "05");
        String countforbidden = busTypeService.countAppForbidden(params);

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }
            map.put("code","00");
            BusinessType businessType = new BusinessType();
            businessType.setSid(sid);
            businessType.setBusType("05");
            businessType.setStatus("00");
            businessType.setCreateDate(new Date());
            busTypeService.openBusinessType(businessType);

            if(!"0".equals(countforbidden)){
                map.put("code","02");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        return map;
    }
    /**
     * 关闭openPhone
     * @return
     */
    @RequestMapping("/closePhone")
    @ResponseBody
    public Map<String,String> closePhone(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "05");
        String count = busTypeService.countApp(params);
//        String count = busTypeService.countApp4(sid);

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }

            if(!"0".equals(count)){
                map.put("code","02");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        BusinessType businessType = new BusinessType();
        businessType.setStatus("01");
        businessType.setBusType("05");
        businessType.setSid(sid);
        busTypeService.updateStatus(businessType);
        return map;
    }



    /**
     * 开通SipPhone
     * @return
     */
    @RequestMapping("/openEcc")
    @ResponseBody
    public Map<String,String> openEcc(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "06");
        String countforbidden = busTypeService.countAppForbidden(params);
        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }
            map.put("code","00");
            BusinessType businessType = new BusinessType();
            businessType.setSid(sid);
            businessType.setBusType("06");
            businessType.setStatus("00");
            businessType.setCreateDate(new Date());
            busTypeService.openBusinessType(businessType);

            if(!"0".equals(countforbidden)){
                map.put("code","02");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        return map;
    }
    /**
     * 关闭openEcc
     * @return
     */
    @RequestMapping("/closeEcc")
    @ResponseBody
    public Map<String,String> closeEcc(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();


        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "06");
        String count = busTypeService.countApp(params);
//        String count = busTypeService.countAppEcc(sid);

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }

            if(!"0".equals(count)){
                map.put("code","02");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        BusinessType businessType = new BusinessType();
        businessType.setStatus("01");
        businessType.setBusType("06");
        businessType.setSid(sid);
        busTypeService.updateStatus(businessType);
        return map;
    }

    /**
     * 开通  隐私小号
     * @return
     */
    @RequestMapping("/openAxb")
    @ResponseBody
    public Map<String,String> openAxb(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "07");
        String countforbidden = busTypeService.countAppForbidden(params);
        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }
            map.put("code","00");
            BusinessType businessType = new BusinessType();
            businessType.setSid(sid);
            businessType.setBusType("07");
            businessType.setStatus("00");
            businessType.setCreateDate(new Date());
            busTypeService.openBusinessType(businessType);

            if(!"0".equals(countforbidden)){
                map.put("code","02");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }


        return map;
    }
    /**
     * 关闭  隐私小号
     * @return
     */
    @RequestMapping("/closeAxb")
    @ResponseBody
    public Map<String,String> closeAxb(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", "07");
        String count = busTypeService.countAxb(params);

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }

            if(!"0".equals(count)){
                map.put("code","02");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        BusinessType businessType = new BusinessType();
        businessType.setStatus("01");
        businessType.setBusType("07");
        businessType.setSid(sid);
        busTypeService.updateStatus(businessType);
        return map;
    }

}
