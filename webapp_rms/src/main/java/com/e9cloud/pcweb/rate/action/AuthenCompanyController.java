package com.e9cloud.pcweb.rate.action;

import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.mybatis.service.UserService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthenCompanyController用于（公司）认证信息的管理
 *
 * Created by DuKai on 2016/1/28.
 */
@Controller
@RequestMapping("/authenCompany")
public class AuthenCompanyController extends BaseController {

    @Autowired
    private AuthenCompanyService authenCompanyService;

    @Autowired
    private UserAdminService userAdminService;

    @RequestMapping("/getAuthenCompany")
    @ResponseBody
    public AuthenCompany getAuthenCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----getAuthenCompany start-------");
        AuthenCompany authenCompany = new AuthenCompany();
        authenCompany.setUid(request.getParameter("uid"));
        return  authenCompanyService.getAuthenCompany(authenCompany);
    }

    @RequestMapping("/getAuthenCompanyBySID")
    @ResponseBody
    public Map<String, Object> getAuthenCompanyBySID(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----getAuthenCompanyBySID start-------");
        Map map = new HashMap<String, Object>();
        UserAdmin userAdmin = userAdminService.findUserAdminSID(request.getParameter("sid"));
        if ("0".equals(userAdmin.getAuthStatus()) || "1".equals(userAdmin.getAuthStatus())){
            map.put("status",2);
            map.put("info","此账号未认证！");
            return map;
        }
        if(userAdmin != null){
            map.put("status", 0);
            map.put("info", "信息查询成功！");
            map.put("userAdmin", userAdmin);
            AuthenCompany authenCompany = new AuthenCompany();
            authenCompany.setUid(userAdmin.getUid());
            map.put("authenCompany", authenCompanyService.getAuthenCompany(authenCompany));
        }
        else {
            map.put("status",1);
            map.put("info","扣款账号不存在！");
        }
        return map;
    }

}
