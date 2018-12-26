package com.e9cloud.rest.action;

import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.core.support.SysPropertits;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.rest.biz.CommonService;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.ReadXmlUtil;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dukai on 2016/10/26.
 * 基础的Controller,所有的Controller可以继承此类
 */
public class BaseController extends BaseLogger{

    @Value("#{configProperties['rest.version']}")
    public String version;

    @Autowired
    public SysPropertits sysPropertits;

    @Autowired
    public AppInfoService appInfoService;

    @Autowired
    public AccountService accountService;

    @Autowired
    public CommonService commonService;


    /**
     * 在执行此controller前先执行该方法解析地址参数
     * @param softVersion
     * @param accountSid
     * @param sig
     * @return
     */
    @ModelAttribute("params")
    public Map<String, String> gainRestUrlParas(@PathVariable(value = "SoftVersion") String softVersion,
            @PathVariable(value = "AccountSid") String accountSid, @RequestParam(value = "sig") String sig){
        Map<String, String> params = new HashMap<String, String>();
        params.put("SoftVersion", softVersion);
        params.put("AccountSid", accountSid);
        params.put("Sig", sig);
        return params;
    }


    public static void returnAsInfo(boolean isJson, String respCode, String dateCreated,
                                      String respDesc, HttpServletResponse response) throws Exception {
        String respBody = null;// 响应报文
        // 同步返回AS报文,返回对应json/xml报文
        if (isJson) {
            if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
                respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\"}}";
            } else {
                respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated  + "\",\"represent\":\"" + respDesc + "\"}}";
            }
        } else {
            if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
                respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>" + dateCreated + "</dateCreated></resp>";
            } else {
                respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
                        + dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
            }
        }
        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(respBody);
        logger.info("返回AS-JSON报文：" + respBody);
    }

    public void returnAsInfo(boolean isJson, String respCode, String dateCreated,
                            String respDesc, HttpServletResponse response, Map<String, String> paramMap) throws Exception {
        String respBody;// 响应报文
        // 同步返回AS报文,返回对应json/xml报文
        if (isJson) {
            if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
                respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\","+paramMap.get("jsonParam")+"}}";
            } else {
                String paramJsonStr = "";
                if(paramMap.containsKey("status")){
                    if("true".equals(paramMap.get("status"))){
                        paramJsonStr += ","+paramMap.get("jsonParam");
                    }
                }
                respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated  + "\",\"represent\":\"" + respDesc + "\""+ paramJsonStr +"}}";
            }
        } else {
            if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
                respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>" + dateCreated + "</dateCreated>"+paramMap.get("xmlParam")+"</resp>";
            } else {
                String paramXmlStr = "";
                if(paramMap.containsKey("status")){
                    if("true".equals(paramMap.get("status"))){
                        paramXmlStr = paramMap.get("xmlParam");
                    }
                }
                respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
                        + dateCreated + "</dateCreated><represent>" + respDesc + "</represent>"+ paramXmlStr +"</resp>";
            }
        }
        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(respBody);
        logger.info("返回AS-JSON报文：" + respBody);
    }


    public static Map<String, Object> analysisRequestParam(HttpServletRequest request, ModelMap model) throws Exception{
        Map<String, Object> map = new HashMap<>();
        //判断所属请求参数类型 json还是xml
        boolean jsonFlag = request.getContentType().contains("application/json") ? true : false;

        Map<String, String> params = (Map<String, String>) model.get("params");
        String accountSid = params.get("AccountSid");

        // 获取AS-语音通知JSON报文
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[1024];
        for (int n; (n = request.getInputStream().read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }

        String asBody = out.toString();
        logger.info("获取请求报文：" + asBody);
        //进行根元素匹配限制
        Map<String, String> objectMap;
        boolean reqFlag;
        if(jsonFlag){
            //解析JSON
            Map<String, Object> jsonMap = new Gson().fromJson(asBody, Map.class);
            reqFlag = jsonMap.containsKey("req");
            objectMap = (Map<String, String>)jsonMap.get("req");
        }else{
            //解析XML
            objectMap = ReadXmlUtil.readXML(asBody);
            reqFlag = "req".equals(objectMap.get("root"));
        }

        map.put("accountSid", accountSid);
        map.put("jsonFlag", jsonFlag);
        map.put("reqFlag", reqFlag);
        map.put("objectMap", objectMap);
        return map;
    }
}
