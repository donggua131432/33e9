package com.e9cloud.pcweb.developers.action;

import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.domain.ExtraType;
import com.e9cloud.mybatis.service.ExtraTypeService;
import com.e9cloud.pcweb.BaseController;
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
 * Created by admin on 2016/8/23.
 */
@Controller
@RequestMapping(value = "/extraType")
public class ExtraTypeController extends BaseController {

    @Autowired
    public ExtraTypeService extraTypeService;
    /**
     * 开通增值服务
     * @return
     */
    @RequestMapping(value = "addExtraType", method = RequestMethod.GET)
    public String addValueSer(HttpServletRequest request, Model model, String sid){

        ExtraType extraType = extraTypeService.checkExtraInfo(sid);
        ExtraType extraType3 = extraTypeService.checkExtraBphone(sid);
        model.addAttribute("extraType",extraType);
        model.addAttribute("extraType3",extraType3);
        model.addAttribute("sid",sid);
        return "developersMgr/addExtraType";
    }


    /**
     * 开通B路计费增值服务
     * @return
     */
    @RequestMapping("/openBtapes")
    @ResponseBody
    public Map<String,String> openBtapes(HttpServletRequest request, HttpServletResponse response, String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }
        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        ExtraType extraType = new ExtraType();
        extraType.setSid(sid);
        extraType.setExtraType("01");
        extraType.setStatus("00");
        extraType.setCreateDate(new Date());
        extraTypeService.openExtraType(extraType);
        return map;
    }
    /**
     * 关闭---B路计费增值服务
     * @return
     */
    @RequestMapping("/closeBtapes")
    @ResponseBody
    public Map<String,String> closeBtapes(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        ExtraType extraType = new ExtraType();
        extraType.setStatus("01");
        extraType.setExtraType("01");
        extraType.setSid(sid);
        extraTypeService.updateStatus(extraType);
        return map;
    }

    /**
     * 开通B路手机显号免审
     * @return
     */
    @RequestMapping("/openBphone")
    @ResponseBody
    public Map<String,String> openBphone(HttpServletRequest request, HttpServletResponse response, String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }
        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        ExtraType extraType = new ExtraType();
        extraType.setSid(sid);
        extraType.setExtraType("03");
        extraType.setStatus("00");
        extraType.setCreateDate(new Date());
        extraTypeService.openExtraType(extraType);
        return map;
    }

    /**
     * 关闭---B路手机显号免审
     * @return
     */
    @RequestMapping("/closeBphone")
    @ResponseBody
    public Map<String,String> closeBphone(HttpServletRequest request, HttpServletResponse response,String sid) throws Exception {
        Map<String,String> map = new HashMap<String, String>();

        try{
            if(StringUtils.isEmpty(sid)){
                map.put("code","01");
                return map;
            }

        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        ExtraType extraType = new ExtraType();
        extraType.setStatus("01");
        extraType.setExtraType("03");
        extraType.setSid(sid);
        extraTypeService.updateStatus(extraType);
        return map;
    }
}
