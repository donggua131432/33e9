package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.domain.CodeType;
import com.e9cloud.mybatis.service.CodeTypeService;
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
 * Created by admin on 2016/9/20.
 */
@Controller
@RequestMapping("/codeType")
public class CodeTypeController extends BaseController {
    @Autowired
     private CodeTypeService codeTypeService;

    /**
     * 开通业务
     * @return
     */
    @RequestMapping(value = "goRoute", method = RequestMethod.GET)
    public String addMaskRate(HttpServletRequest request, Model model, String sid){

        return "developersMgr/openRoute";
    }

    /**
     * 分页查询开发者列表
     * @param page 分页信息
     * @return
     */
    @RequestMapping(value = "pageCodeType",method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageUserAdminList(Page page){

        return codeTypeService.pageCodeType(page);
    }


    /**
     * 开通专用路由
     * @return
     */
    @RequestMapping("/openRoute")
    @ResponseBody
    public Map<String,String> openRoute(HttpServletRequest request, HttpServletResponse response, String busType) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
            try{
                if(StringUtils.isEmpty(busType)){
                    map.put("code","01");
                    return map;
                }
            }catch (Exception e){
                map.put("code","99");
                return  map;
            }
            map.put("code","00");
            CodeType codeType = new CodeType();
            codeType.setBusType(busType);
            codeType.setStatus("01");
            codeType.setCreateDate(new Date());
            codeTypeService.updateStatus(codeType);
            return map;
    }

    /**
     * 关闭专用路由
     * @return
     */
    @RequestMapping("/closeRoute")
    @ResponseBody
    public Map<String,String> closeRoute(HttpServletRequest request, HttpServletResponse response, String busType) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        try{
            if(StringUtils.isEmpty(busType)){
                map.put("code","01");
                return map;
            }
        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        CodeType codeType = new CodeType();
        codeType.setBusType(busType);
        codeType.setStatus("00");
        codeType.setCreateDate(new Date());
        codeTypeService.updateStatus(codeType);
        return map;
    }

}
