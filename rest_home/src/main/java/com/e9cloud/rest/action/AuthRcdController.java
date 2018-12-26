package com.e9cloud.rest.action;

import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/03/08.
 */
@Controller
@RequestMapping("/authRcdDownload/{AccountSid}/{appid}/")
public class AuthRcdController
{
    private static final Logger logger = LoggerFactory.getLogger(AuthRcdController.class);

    @Autowired
    private AccountService accountService;

    @Value("#{configProperties['rcd.download.key']}")
    private String rcdKey;

    @ModelAttribute("params")
    public Map<String,String>  gainRestUrlParas( @PathVariable(value="AccountSid") String accountSid,
            @PathVariable(value="appid") String appid, @RequestParam(value = "auth") String auth) {
        Map<String,String>  params = new HashMap<String, String>();

        params.put("appid", appid);
        params.put("AccountSid", accountSid);
        params.put("auth", auth);

        return params;
    }

    @RequestMapping("{fileName}")
    public void doPost(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");
        if(rcdKey.equals(params.get("auth"))){
            logger.info("rcdKey接口验证通过");
            model.addAttribute("statusCode", 200);
            response.setStatus(200);
        }else{
            //获取根据sid用户token
            User user = accountService.getUserBySid(params.get("AccountSid"));
            if(user==null){
                logger.info("SID用户不存在");
                model.addAttribute("statusCode", 403);
                response.setStatus(403);
            }else{
                String authToken = user.getToken();
                EncryptUtil encryptUtil = new EncryptUtil();
                //进行接口验证参数 校验
                String authTemp = encryptUtil.md5Digest(params.get("AccountSid") +":"+ authToken).toUpperCase();
                if(authTemp.equals(params.get("auth"))){
                    logger.info("接口验证通过");
                    model.addAttribute("statusCode", 200);
                    response.setStatus(200);
                }else{
                    logger.info("接口验证参数auth不通过");
                    model.addAttribute("statusCode", 403);
                    response.setStatus(403);
                }
            }
        }


    }

    @RequestMapping("{date}/{fileName}")
    public void doGet(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");
        if(rcdKey.equals(params.get("auth"))){
            logger.info("rcdKey接口验证通过");
            model.addAttribute("statusCode", 200);
            response.setStatus(200);
        }else{
            //获取根据sid用户token
            User user = accountService.getUserBySid(params.get("AccountSid"));
            if(user==null){
                logger.info("SID用户不存在");
                model.addAttribute("statusCode", 403);
                response.setStatus(403);
            }else{
                String authToken = user.getToken();
                EncryptUtil encryptUtil = new EncryptUtil();
                //进行接口验证参数 校验
                String authTemp = encryptUtil.md5Digest(params.get("AccountSid") +":"+ authToken).toUpperCase();
                if(authTemp.equals(params.get("auth"))){
                    logger.info("接口验证通过");
                    model.addAttribute("statusCode", 200);
                    response.setStatus(200);
                }else{
                    logger.info("接口验证参数auth不通过");
                    model.addAttribute("statusCode", 403);
                    response.setStatus(403);
                }
            }
        }


    }

}
