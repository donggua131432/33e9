package com.e9cloud.core;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.rest.cb.RestStatisTask;
import com.e9cloud.rest.cb.StatisMsgManager;
import com.e9cloud.rest.obt.CallBack;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.EncryptUtil;
import com.e9cloud.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RestInvokeInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger logger = LoggerFactory.getLogger(RestInvokeInterceptor.class);
    AccountService accountService = SpringContextHolder.getBean(AccountService.class);


    @Value("#{configProperties['rest.version']}")
    private String version;


    /** 进入Controler方法处理前执行，此处可以进行鉴权、日志打印，前传到登录
     * 功能：前向日志统计， 鉴权
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //{SoftVersion}/Accounts/{AccountSid}/Calls/callBack
        HandlerMethod handerMethod = (HandlerMethod)handler;
        logger.info("method name {}",handerMethod.toString());
        //System.out.println(handerMethod.toString());
        //public void com.e9cloud.rest.action.AuthRcdController.doPost(javax.servlet.http.HttpServletRequest,
        //javax.servlet.http.HttpServletResponse,org.springframework.ui.ModelMap) throws java.lang.Exception
        if(handerMethod.toString().contains("CbController")){
            logger.info("停止CB服务调用");
            //停止CBserver
            return true;
        }
        if(handerMethod.toString().contains("ISPController")){
            logger.info("语音通知中转服务调用");
            //内部程序不需要鉴权
            return true;
        }

        if(handerMethod.toString().contains("AxbNotifyController")){
            logger.info("虚拟小号回调不需要鉴权");
            //内部程序不需要鉴权
            return true;
        }

        if(handerMethod.toString().contains("AuthRcdController")){
            logger.info("下载录音鉴权调用");
            //是下载录音鉴权，在controller中验证
            return true;
        }else{
            String respCode=null;//响应码
            String respDesc=null;//相应描述

            EncryptUtil encryptUtil = new EncryptUtil();
            String url = request.getRequestURI();
            String sig =  request.getParameter("sig");
            String accept = request.getHeader("Accept");
            String cType = request.getHeader("Content-Type");
            String auth64 = request.getHeader("Authorization");
            String path = request.getServletPath();
            if(!path.contains(version)){
                //无效版本号
                respCode= ConstantsEnum.REST_NOVERSION.getCode();
                respDesc=ConstantsEnum.REST_NOVERSION.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }

            if(!Utils.notEmpty(sig)){
                respCode= ConstantsEnum.REST_SIG_NULL.getCode();
                respDesc=ConstantsEnum.REST_SIG_NULL.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }
            //base64解密获取 主账户Id + 冒号 + 时间戳
            String auth = null;
            try {
                auth = encryptUtil.base64Decoder(auth64);
            }catch (Exception e){
                respCode=ConstantsEnum.REST_AUTH_EXCEPTION.getCode();
                respDesc=ConstantsEnum.REST_AUTH_EXCEPTION.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }
            //Authorization格式不对（没有或非一个英文冒号）
            if(Utils.occurTimes(auth,":")!=1){
                respCode=ConstantsEnum.REST_AUTH_FORMAT.getCode();
                respDesc=ConstantsEnum.REST_AUTH_FORMAT.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }
            String accountSid = auth.substring(0,auth.indexOf(":"));
            String timestamp = auth.substring(auth.indexOf(":")+1);
            if(!Utils.notEmpty(accountSid)){
                respCode=ConstantsEnum.REST_SID_NULL.getCode();
                respDesc=ConstantsEnum.REST_SID_NULL.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }else if(accountSid.length()!=32){
                respCode=ConstantsEnum.REST_SID_LENGTH.getCode();
                respDesc=ConstantsEnum.REST_SID_LENGTH.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }else if(!Utils.isLetterDigit(accountSid)){
                //accountSid格式不符（英文或数字）
                respCode=ConstantsEnum.REST_SID_FORMAT.getCode();
                respDesc=ConstantsEnum.REST_SID_FORMAT.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }
            if(!Utils.notEmpty(timestamp)){
                respCode=ConstantsEnum.REST_TIMESTAMP_NULL.getCode();
                respDesc=ConstantsEnum.REST_TIMESTAMP_NULL.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }else if(timestamp.length()!=14){
                //格式"yyyyMMddHHmmss"
                respCode=ConstantsEnum.REST_TIMESTAMP_LENGTH.getCode();
                respDesc=ConstantsEnum.REST_TIMESTAMP_LENGTH.getDesc();
                returnBody(response,accept,respCode,respDesc);
                return false;
            }else if(!Utils.isDigit(timestamp)){
                //时间戳格式不符（数字）
                respCode=ConstantsEnum.REST_TIMESTAMP_FORMAT.getCode();
                respDesc=ConstantsEnum.REST_TIMESTAMP_FORMAT.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }
            //验证时间戳是否24小时之内
            long curTime = new Date().getTime();//当前时间毫秒数
            long stampTime = DateUtil.strToDate(timestamp,"yyyyMMddHHmmss").getTime();//时间戳时间毫秒数
            //两个时间间隔毫秒数，转成double，便于转换小时精确保留小数
            double timeNumMS = curTime - stampTime;
            //两个时间间隔小时数
            double timeNum = timeNumMS/1000/60/60;
            logger.info("时间戳判定依据：auth-"+auth+"  timestamp-"+timestamp+
                    "   curTime-"+curTime+"   stampTime-"+stampTime+"   timeNum-"+timeNum+"   date-"+new Date());
            //时间戳有效期为当前北京时间前后的12小时（共24小时），为了适应全球化用户
            if(timeNum>=-12d && timeNum<=12d){
                //获取用户token
                User user = accountService.getUserBySid(accountSid);
                if(user==null){
                    respCode=ConstantsEnum.REST_SIDUSER_NO.getCode();
                    respDesc=ConstantsEnum.REST_SIDUSER_NO.getDesc();
                    logger.info(respDesc);
                    returnBody(response,accept,respCode,respDesc);
                    return false;
                }else{
                    //判断主账号是否可用 既sid是否有效 账户是否被禁用或者冻结
                    if (!"1".equals(user.getStatus())) {
                        respCode = ConstantsEnum.REST_SID_ABNORMAL.getCode();
                        respDesc = ConstantsEnum.REST_SID_ABNORMAL.getDesc();
                        logger.info(respDesc);
                        returnBody(response,accept,respCode,respDesc);
                        return false;
                    }

                    String authToken = user.getToken();
                    //进行接口验证参数 校验
                    String sigTemp = encryptUtil.md5Digest(accountSid + authToken + timestamp).toUpperCase();
                    if(sig.equals(sigTemp)){
                        return true;
                    }else{
                        respCode=ConstantsEnum.REST_SIG_FAIL.getCode();
                        respDesc=ConstantsEnum.REST_SIG_FAIL.getDesc();
                        logger.info(respDesc);
                        returnBody(response,accept,respCode,respDesc);
                        return false;
                    }
                }
            }else{
                respCode=ConstantsEnum.REST_TIMESTAMP_INVALID.getCode();
                respDesc=ConstantsEnum.REST_TIMESTAMP_INVALID.getDesc();
                logger.info(respDesc);
                returnBody(response,accept,respCode,respDesc);
                return false;
            }
        }
    }

    /**
     * Controler方法处理完成，进行View渲染前执行， 此时可以进行向View增加model数据,无法前转（sendRedirect）
     * 功能：增加Model数据
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception{
        //super.postHandle(request, response, handler, modelAndView);
        //强制添加不缓存
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
    }


    /**
     * Rest响应数据生成成功（View渲染完成，返回给也没），此处只能作清理 和 异常日志打印，无法前转（sendRedirect）
     * 功能：后向日志统计, 接口性能分析
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception{
        System.out.println("----------- afterCompletion -----------------");
    }




    public static void returnBody(HttpServletResponse response,String accept,String respCode,String respDesc)
            throws Exception{
        CallBack callBack = new CallBack();
        String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
        //根据报文类型，返回对应格式报文（xml或json）
        response.setCharacterEncoding("utf-8");//避免中文乱码
        if("application/xml".equals(accept)){
            callBack.setAccept(ConstantsEnum.RESTREQTYPE_XML.getStrValue());
            response.getWriter().print("<?xml version='1.0'?><resp><statusCode>"+respCode+"</statusCode>" +
                    "<dateCreated>"+dateCreated+"</dateCreated><represent>"+respDesc+"</represent></resp>");
        }else{
            callBack.setAccept(ConstantsEnum.RESTREQTYPE_JSON.getStrValue());
            response.getWriter().print("{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""
                    +dateCreated+"\",\"represent\":\""+respDesc+"\"}}");
        }
    }
}
