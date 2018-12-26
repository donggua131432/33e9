package com.e9cloud.rest.action;

import com.e9cloud.core.application.InitApp;
import com.e9cloud.rest.service.VoiceService;
import com.e9cloud.util.Tools;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/03/08.
 */
@Controller
@RequestMapping(method = RequestMethod.GET, value = "/isp/")
public class ISPController {
    private static final Logger logger = LoggerFactory.getLogger(ISPController.class);
    @Autowired
    private VoiceService voiceService;

    @RequestMapping(method = RequestMethod.POST, value = "voiceCanel")
    public void voiceCanel(HttpServletRequest request, HttpServletResponse response, String requestId) throws Exception {
        Boolean flag =  voiceService.removeJob(requestId);
        logger.info("voiceCanel flag {}",flag);
        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(flag?"true":"false");
    }

}
