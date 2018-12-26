package com.e9cloud.pcweb.user.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.UserAdminService;
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
 *UserAdminController用户西信息模块相关的业务控制，
 *
 * Created by DuKai on 2016/2/19.
 *
 */
@Controller
@RequestMapping(value = "/userAdmin")
public class UserAdminController extends BaseController {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;
    /**
     * 联表查询费率信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getUserAdminWithCompany")
    @ResponseBody
    public Map<String, Object> getUserAdminWithCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----getUserAdminWithCompany start-------");
        Map map = new HashMap<String, Object>();
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(request.getParameter("sid"));

        UserAdmin userAdminResult = userAdminService.getUserAdminWithCompany(userAdmin);
        if(userAdminResult != null){
            map.put("status",0);
            map.put("info","信息查询成功！");
            map.put("userAdminResult",userAdminResult);
        }else{
            map.put("status",1);
            map.put("info","开发者ID不存在！");
        }

        return map;
    }

    // 校验唯一性
    @RequestMapping("checkUnique")
    @ResponseBody
    public JSonMessage checkUnique(UserAdmin user){
        JSonMessage jSonMessage = new JSonMessage();

        logger.info("-------------UserController checkUnique start-------------");

        long l = userAdminService.countUserAdminByEmailOrMobile(user);
        if (l == 0) {
            jSonMessage.setCode("ok");
        }

        logger.info("-------------UserController checkUnique start-------------");

        return jSonMessage;
    }

    // 校验唯一性
    @RequestMapping("checkUniqueCompany")
    @ResponseBody
    public JSonMessage checkUniqueCompany(HttpServletRequest request, HttpServletResponse response, AuthenCompany company){
        JSonMessage jSonMessage = new JSonMessage();
        String companyName = request.getParameter("companyName");
        if(Tools.isNotNullStr(companyName)){
            company.setName(companyName);
        }
        logger.info("-------------UserController checkUnique start-------------");

        long l = authenCompanyService.countCompanyByInfo(company);
        if (l == 0) {
            jSonMessage.setCode("ok");
        }

        logger.info("-------------UserController checkUnique start-------------");

        return jSonMessage;
    }

    // 认证通过之后，修改认证信息时  校验唯一性
    @RequestMapping("authcheckUniqueCompany")
    @ResponseBody
    public JSonMessage authcheckUniqueCompany(HttpServletRequest request, HttpServletResponse response,AuthenCompany company){
        JSonMessage jSonMessage = new JSonMessage();
        String companyName = request.getParameter("companyName");
        String uid = request.getParameter("uid");
        if(Tools.isNotNullStr(companyName)){
            company.setName(companyName);
        }
        company.setUid(uid);
        logger.info("-------------UserController checkUnique start-------------");
        long l = authenCompanyService.countCompany(company);
        if (l == 0) {
            jSonMessage.setCode("ok");
        }

        logger.info("-------------UserController checkUnique start-------------");

        return jSonMessage;
    }

    // 校验唯一性
    @RequestMapping("getCompanyNameAndSidByPage")
    @ResponseBody
    public PageWrapper getCompanyNameAndSidByPage(Page page){

        logger.info("-------------UserController getCompanyNameAndSidByPage start-------------");

        return userAdminService.getCompanyNameAndSidByPage(page);
    }
}
