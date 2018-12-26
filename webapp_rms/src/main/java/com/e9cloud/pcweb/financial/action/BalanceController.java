package com.e9cloud.pcweb.financial.action;

import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BalanceController用于获取账户余额
 *
 * Created by wujiang on 2016/2/22.
 */
@Controller
@RequestMapping("/balance")
public class BalanceController extends BaseController {

    @Autowired
    private UserAdminService userAdminService;

    @RequestMapping("/getBalance")
    @ResponseBody
    public UserAdmin getAuthenCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----getBalance start-------");
        return userAdminService.findUserAdminSID(request.getParameter("uid"));
    }

}
