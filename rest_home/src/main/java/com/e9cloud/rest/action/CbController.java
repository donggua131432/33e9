package com.e9cloud.rest.action;

import com.e9cloud.core.application.InitApp;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.util.EncryptUtil;
import com.e9cloud.util.Tools;
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
@RequestMapping(method = RequestMethod.GET, value = "/cb/")
public class CbController {
    private static final Logger logger = LoggerFactory.getLogger(CbController.class);

    @Value("#{configProperties['cb.controller.ip']}")
    private String clientIp;


    @Value("#{configProperties['cb.tcp.socketAddress']}")
    private String cbIps;

    @RequestMapping(method = RequestMethod.GET, value = "start")
    public void start(HttpServletRequest request, HttpServletResponse response, String cbIp, ModelMap model) throws Exception {
        if(Tools.isNullStr(cbIp)){
            logger.info("cbIp {} is null", cbIp);
            model.addAttribute("status", 404);
            response.setStatus(404);
        }
        String ip = request.getRemoteAddr();
        if (ip.startsWith(clientIp)) {
            if (cbIps.contains(cbIp)) {
                InitApp.getInstance().startCBServer(cbIp);
                logger.info("ip {} start cbIp {}  success", cbIp);
                model.addAttribute("status", 200);
                response.setStatus(200);
            } else {
                logger.info("cbIp {} not in cbIps{}", cbIp,cbIps);
                model.addAttribute("status", 200);
                response.setStatus(404);
            }
        } else {
            logger.info("ip {}  start cbIp {} not auth", ip, cbIp);
            model.addAttribute("status", 403);
            response.setStatus(403);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "startAll")
    public void startAll(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        InitApp.getInstance().startAllCBServer();
        logger.info(" startAll cb success ");
        model.addAttribute("status", 200);
        response.setStatus(200);


    }

    @RequestMapping(method = RequestMethod.GET, value = "stop")
    public void stop(HttpServletRequest request, HttpServletResponse response, String cbIp, ModelMap model) throws Exception {
        if(Tools.isNullStr(cbIp)){
            logger.info("cbIp {} is null", cbIp);
            model.addAttribute("status", 404);
            response.setStatus(404);
        }
        String ip = request.getRemoteAddr();
        if (ip.startsWith(clientIp)) {
            if (cbIps.contains(cbIp)) {
                InitApp.getInstance().stopCBServer(cbIp);
                logger.info("stop cbIp {} stop success", cbIp);
                model.addAttribute("status", 200);
                response.setStatus(200);
            } else {
                logger.info("cbIp {} not in cbIps{}", cbIp,cbIps);
                model.addAttribute("status", 404);
                response.setStatus(404);
            }
        } else {
            logger.info("ip {}  stop cbIp {} not auth", ip, cbIp);
            model.addAttribute("status", 403);
            response.setStatus(403);
        }
    }



}
