package com.e9cloud.rest.action;

import com.e9cloud.rest.cb.AppIdTreadManager;
import com.e9cloud.rest.obt.CallNotifyHttp;
import com.e9cloud.rest.obt.GxltCallBack;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @描述: 回调控制器
 * @作者: DuKai
 * @创建时间: 2017/4/22 17:03
 * @版本: 1.0
 */
@Controller
@RequestMapping(method = RequestMethod.GET, value = "/axbNotify/")
public class AxbNotifyController{
    private static final Logger logger = LoggerFactory.getLogger(AxbNotifyController.class);
    /**
     * 虚拟小号回调接口
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "hangup")
    public void hangup(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        logger.info("-----------------AxbNumber/hangup-------------------");
        String respCode = null; //响应码
        String respDesc = null; //相应描述

        // 生成请求时间
        String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
        try{
            // 获取AS-语音通知JSON报文
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[1024];
            for (int n; (n = request.getInputStream().read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }

            String asBody = out.toString();
            logger.info("获取请求报文：" + asBody);
            GxltCallBack gxltCallBack = new Gson().fromJson(asBody, GxltCallBack.class);

            CallNotifyHttp callNotify = new CallNotifyHttp();
            callNotify.setAction("Hangup");
            callNotify.setType(Integer.parseInt(gxltCallBack.getCalltype()));
            callNotify.setAppId(gxltCallBack.getAppId());
            callNotify.setCaller(gxltCallBack.getTelA());
            callNotify.setCalled(gxltCallBack.getTelB());
            callNotify.setTelX(gxltCallBack.getTelX());
            callNotify.setStartTimeA(gxltCallBack.getCalltime());
            callNotify.setStartTimeB(gxltCallBack.getStarttime());
            callNotify.setEndTime(gxltCallBack.getReleasetime());
            callNotify.setDuration(DateUtil.getSecond(gxltCallBack.getStarttime(), gxltCallBack.getReleasetime()));
            callNotify.setCallSid(gxltCallBack.getCallid());
            callNotify.setUserFlag(Integer.parseInt(gxltCallBack.getReleasedir()));
            callNotify.setByeType(gxltCallBack.getReleasecause());
            callNotify.setDateCreated(dateCreated);
            callNotify.setUserData(gxltCallBack.getUserData());
            callNotify.setRecordUrl(gxltCallBack.getRecordUrl());

            //添加到发送任务
            AppIdTreadManager.getInstance().addParseMsg(callNotify);

            respCode = ConstantsEnum.REST_SUCCCODE.getCode();

            logger.info(respDesc);
        } catch (JsonParseException e){
            //json转对象参数错误
            respCode = ConstantsEnum.REST_PARAM_ERROR.getCode();
            respDesc = ConstantsEnum.REST_PARAM_ERROR.getDesc();
            logger.error(respDesc+"{}",e);
        } catch (Exception e) {
            respCode = ConstantsEnum.REST_UNKNOW.getCode();
            respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
            logger.error(respDesc+"{}",e);
        }

        BaseController.returnAsInfo(true, respCode, dateCreated, respDesc, response);
    }
}
