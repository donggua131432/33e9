package com.e9cloud.pcweb.account.action;

import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.DigestsUtils;
import com.e9cloud.core.util.Encodes;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.RetrieveService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.account.biz.RegAndFindPwdService;
import com.e9cloud.redis.util.RedisDBUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/13.
 */
@Controller
@RequestMapping("/error")
public class ErrorActionController extends BaseController {
    /**
     * 403错误页面
     */
    @RequestMapping(value ="403" ,method = RequestMethod.GET)
    public String error403() {
        return  "/common/403";
    }
}
