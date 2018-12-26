package com.e9cloud.rest.action;

import com.e9cloud.core.application.InitApp;
import com.e9cloud.mybatis.domain.AxbNumRelation;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.service.AxbNumberService;
import com.e9cloud.mybatis.service.BusinessTypeService;
import com.e9cloud.rest.obt.AxbNumber;
import com.e9cloud.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @描述: 虚拟小号Rest接口
 * @作者: DuKai
 * @创建时间: 2017/4/18 11:01
 * @版本: 1.0
 */
@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class AxbNumberController extends BaseController{

    @Autowired
    private AxbNumberService axbNumberService;

    @Autowired
    private BusinessTypeService businessTypeService;



    /**
     * 小号绑定
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "AxbNumber/bind", consumes = {"application/json", "application/xml"})
    public void axbNumberBind(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        String respCode = null; //响应码
        String respDesc = null; //相应描述
        Map<String, String> paramMap = new HashMap<>(); //返回参数信息
        // 生成请求时间
        String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);

        //判断所属请求参数类型 json还是xml
        boolean jsonFlag = true;
        try{
            Map<String, Object> requestParamMap = analysisRequestParam(request, model);

            jsonFlag = (boolean) requestParamMap.get("jsonFlag");
            boolean reqFlag = (boolean) requestParamMap.get("reqFlag");
            if(reqFlag){
                //将对应字段填充到报文对象
                Map<String, Object> axbNumMap = (Map<String, Object>) requestParamMap.get("objectMap");
                String jsonStr = new Gson().toJson(axbNumMap);
                AxbNumber axbNumber = new Gson().fromJson(jsonStr, AxbNumber.class);
                axbNumber.setAccountSid((String) requestParamMap.get("accountSid"));

                //绑定隐私号
                Map<String, String> map = bind(axbNumber);
                respCode = map.get("respCode");
                respDesc = map.get("respDesc");
                if(jsonFlag){
                    paramMap.put("jsonParam","\"subid\":\"" + map.get("subid") + "\", \"telX\":\"" + map.get("telX") + "\"");
                }else{
                    paramMap.put("xmlParam","<subid>" + map.get("subid") + "</subid><telX>" + map.get("telX") + "</telX>");
                }
            }else{
                //报文根元素错误
                respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
                respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
            }
            logger.info(respDesc);
        } catch (JsonParseException e){
            //json转对象参数错误
            respCode = ConstantsEnum.REST_PARAM_ERROR.getCode();
            respDesc = ConstantsEnum.REST_PARAM_ERROR.getDesc();
            logger.error(respDesc+"{}",e);
        } catch (HttpHostConnectException e) {
            respCode = ConstantsEnum.REST_AXB_HTTP_TIME_OUT.getCode();
            respDesc = ConstantsEnum.REST_AXB_HTTP_TIME_OUT.getDesc();
            logger.error(respDesc+"{}",e);
        } catch (IllegalArgumentException e) {
            respCode = ConstantsEnum.REST_AXB_HTTP_FAIL.getCode();
            respDesc = ConstantsEnum.REST_AXB_HTTP_FAIL.getDesc();
            logger.error(respDesc+"{}",e);
        } catch (Exception e) {
            respCode = ConstantsEnum.REST_UNKNOW.getCode();
            respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
            logger.error(respDesc+"{}",e);
        }

        returnAsInfo(jsonFlag, respCode, dateCreated, respDesc, response, paramMap);

    }

    private Map<String, String> bind(AxbNumber axbNumber) throws Exception{
        Map<String, String> map = new HashMap<>();
        //校验appid和accountSid的有效性
        Map<String, String> checkMap = commonService.checkAppAndAccount(axbNumber.getAppId(),axbNumber.getAccountSid(),"02");
        if(checkMap != null){
            return checkMap;
        }

        //判断小号业务类型是否开通
        Map<String, String> checkBusinessMap = checkBusinessType(axbNumber.getAccountSid());
        if(checkBusinessMap != null){
            return checkBusinessMap;
        }

        //必选字段格式验证
        if(!Utils.notEmpty(axbNumber.getTelA())){
            map.put("respCode",ConstantsEnum.REST_AXB_TELA_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_TELA_NULL.getDesc());
            logger.info(ConstantsEnum.REST_AXB_TELA_NULL.getDesc());
            return map;
        }

        if (!Utils.phoneValid(axbNumber.getTelA())) {
            map.put("respCode",ConstantsEnum.REST_AXB_TELA_FORMAT.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_TELA_FORMAT.getDesc());
            logger.info(ConstantsEnum.REST_AXB_TELA_FORMAT.getDesc());
            return map;
        }

        if(!Utils.notEmpty(axbNumber.getTelB())){
            map.put("respCode",ConstantsEnum.REST_AXB_TELB_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_TELB_NULL.getDesc());
            logger.info(ConstantsEnum.REST_AXB_TELB_NULL.getDesc());
            return map;
        }

        if (!Utils.phoneValid(axbNumber.getTelB())) {
            map.put("respCode",ConstantsEnum.REST_AXB_TELB_FORMAT.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_TELB_FORMAT.getDesc());
            logger.info(ConstantsEnum.REST_AXB_TELB_FORMAT.getDesc());
            return map;
        }

        if (axbNumber.getTelA().equals(axbNumber.getTelB())) {
            map.put("respCode",ConstantsEnum.REST_AXB_TEL_EQUALS.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_TEL_EQUALS.getDesc());
            logger.info(ConstantsEnum.REST_AXB_TEL_EQUALS.getDesc());
            return map;
        }

        if(!Utils.notEmpty(axbNumber.getAreacode())){
            map.put("respCode",ConstantsEnum.REST_AXB_AREACODE_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_AREACODE_NULL.getDesc());
            logger.info(ConstantsEnum.REST_AXB_AREACODE_NULL.getDesc());
            return map;
        }

        if (Utils.notEmpty(axbNumber.getAreacode()) && !Utils.areaCodeValid(axbNumber.getAreacode())) {
            map.put("respCode",ConstantsEnum.REST_AXB_AREACODE_FORMAT.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_AREACODE_FORMAT.getDesc());
            logger.info(ConstantsEnum.REST_AXB_AREACODE_FORMAT.getDesc());
            return map;
        }

        if (Utils.notEmpty(axbNumber.getExpiration())) {
            if(axbNumber.getExpiration() <= 0){
                map.put("respCode",ConstantsEnum.REST_AXB_EXPIRATION_FORMAT.getCode());
                map.put("respDesc",ConstantsEnum.REST_AXB_EXPIRATION_FORMAT.getDesc());
                logger.info(ConstantsEnum.REST_AXB_EXPIRATION_FORMAT.getDesc());
                return map;
            }
        }else{
            axbNumber.setExpiration(24*3600);
        }

        if (Utils.notEmpty(axbNumber.getUserData()) && axbNumber.getUserData().length() > 128) {
            map.put("respCode",ConstantsEnum.REST_AXB_USERDATA_OUT.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_USERDATA_OUT.getDesc());
            logger.info(ConstantsEnum.REST_AXB_USERDATA_OUT.getDesc());
            return map;
        }


        if(Utils.notEmpty(axbNumber.getCallrecording())){
            if(!"0".equals(axbNumber.getCallrecording()) && !"1".equals(axbNumber.getCallrecording())){
                map.put("respCode",ConstantsEnum.REST_AXB_CALLRECORDING_FORMAT.getCode());
                map.put("respDesc",ConstantsEnum.REST_AXB_CALLRECORDING_FORMAT.getDesc());
                logger.info(ConstantsEnum.REST_AXB_CALLRECORDING_FORMAT.getDesc());
                return map;
            }
        }else{
            axbNumber.setCallrecording("0");
        }


        Map<String, String> getTelXMap = getTelX(axbNumber);
        String telXFlag = getTelXMap.get("telXFlag");
        String send = getTelXMap.get("send");
        if("nullTelX".equals(telXFlag)){
            map.put("respCode",ConstantsEnum.REST_AXB_TELX_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_TELX_NULL.getDesc());
            logger.info(ConstantsEnum.REST_AXB_TELX_NULL.getDesc());
            return map;
        }else if("noTelX".equals(telXFlag)){
            map.put("respCode",ConstantsEnum.REST_AXB_TELX_NOT.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_TELX_NOT.getDesc());
            logger.info(ConstantsEnum.REST_AXB_TELX_NOT.getDesc());
            return map;
        }
        //设置X号码
        axbNumber.setTelX(getTelXMap.get("telX"));

        Map<String, String> resultMap = new HashMap<>();
        if("true".equals(send)){
            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("secret", InitApp.getInstance().getValue("abx.secret", ""));
            sendMap.put("body", new Gson().toJson(axbNumber));
            //String url =  InitApp.getInstance().getHttpUrl()+"";
            String url = InitApp.getInstance().getValue("abx.url.httpAddress", "")+"/gxlt/bind";
            String result = HttpUtil.sendAxbHttpPost(url, sendMap);
            //如返回结果不为空 进行错误码转换
            resultMap = switchErrorCode(result);
        }else{
            resultMap.putAll(getTelXMap);
        }
        map.putAll(resultMap);
        return map;
    }

    //如返回结果不为空 进行错误码转换
    public Map<String, String> switchErrorCode(String result){
        Map<String, String> map = new HashMap<>();
        if(!"".equals(result)){
            Map<String, Object> resultMap = JSonUtils.readValue(result,Map.class);
            String code = String.valueOf(resultMap.get("code"));
            String message = String.valueOf(resultMap.get("message"));
            if("0".equals(code)){
                map.put("respCode",ConstantsEnum.REST_SUCCCODE.getCode());
                map.put("respDesc",ConstantsEnum.REST_SUCCCODE.getDesc());
                Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("data");
                if(Utils.notEmpty(responseMap)){
                    map.put("subid", String.valueOf(responseMap.get("subid")));
                    map.put("telX", String.valueOf(responseMap.get("telX")));
                }
            }else{
                map.put("respCode","901"+code);
                map.put("respDesc",message);
            }
        }else{
            map.put("respCode",ConstantsEnum.REST_AXB_HTTP_FAIL.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_HTTP_FAIL.getDesc());
        }

        return map;
    }


    public Map<String, String> getTelX(AxbNumber axbNumber){
        Map<String, String> map = new HashMap<>();
        String send = "true";

        Map<String, Object> poolParam = new HashMap<>();
        poolParam.put("appid", axbNumber.getAppId());
        poolParam.put("areaCode", axbNumber.getAreacode());
        poolParam.put("telA",axbNumber.getTelA());
        poolParam.put("telB",axbNumber.getTelB());
        //获取A、B号码映射关系
        AxbNumRelation axbNumRelationResult = axbNumberService.getAxbNumberRelation(poolParam);
        if(axbNumRelationResult != null){
            send = "false";
            map.put("subid",axbNumRelationResult.getSubid());
            map.put("telX",axbNumRelationResult.getNumX());
            map.put("respCode",ConstantsEnum.REST_SUCCCODE.getCode());
        }else{
            //获取X号码池
            List<String> poolList = axbNumberService.getNumXList(poolParam);
            if(poolList != null){
                //获取可用的X号码
                List<AxbNumber> telXList = axbNumberService.getTelXList(poolParam);
                if(telXList != null && telXList.size()>0){
                    map.put("telX",telXList.get(0).getTelX());
                }else{
                    send = "false";
                    map.put("telXFlag","noTelX");
                }
            }else{
                send = "false";
                map.put("telXFlag","nullTelX");
            }
        }
        map.put("send",send);
        return map;
    }

    private Map<String, String> checkBusinessType(String accountSid){
        Map<String, String> map = new HashMap<>();
        //判断小号业务类型是否开通
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("sid",accountSid);
        mapParam.put("busType","07");
        mapParam.put("status","00");
        BusinessType businessType = businessTypeService.getBusinessType(mapParam);
        if(businessType == null){
            map.put("respCode",ConstantsEnum.REST_AXB_BUSINESS_TYPE_OPEN.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_BUSINESS_TYPE_OPEN.getDesc());
            logger.info(ConstantsEnum.REST_AXB_BUSINESS_TYPE_OPEN.getDesc());
            return map;
        }
        return null;
    }

    /**
     * 小号解绑
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "AxbNumber/unbind", consumes = {"application/json", "application/xml"})
    public void axbNumberUnbind(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        String respCode = null; //响应码
        String respDesc = null; //相应描述
        // 生成请求时间
        String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
        //判断所属请求参数类型 json还是xml
        boolean jsonFlag = true;
        try{
            Map<String, Object> requestParamMap = analysisRequestParam(request, model);

            jsonFlag = (boolean) requestParamMap.get("jsonFlag");
            boolean reqFlag = (boolean) requestParamMap.get("reqFlag");
            if(reqFlag){
                //将对应字段填充到报文对象
                Map<String, Object> bodyMap = (Map<String, Object>) requestParamMap.get("objectMap");
                bodyMap.put("accountSid", requestParamMap.get("accountSid"));

                //绑定隐私号
                Map<String, String> map = unbind(bodyMap);
                respCode = map.get("respCode");
                if(!"000000".equals(respCode)){
                    respDesc = map.get("respDesc");
                    logger.info(respDesc);
                }
            }else{
                //报文根元素错误
                respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
                respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
            }
        } catch (JsonParseException e){
            //json转对象参数错误
            respCode = ConstantsEnum.REST_PARAM_ERROR.getCode();
            respDesc = ConstantsEnum.REST_PARAM_ERROR.getDesc();
            logger.error(respDesc+"{}",e);
        } catch (HttpHostConnectException e) {
            respCode = ConstantsEnum.REST_AXB_HTTP_TIME_OUT.getCode();
            respDesc = ConstantsEnum.REST_AXB_HTTP_TIME_OUT.getDesc();
            logger.error(respDesc+"{}",e);
        } catch (IllegalArgumentException e) {
            respCode = ConstantsEnum.REST_AXB_HTTP_FAIL.getCode();
            respDesc = ConstantsEnum.REST_AXB_HTTP_FAIL.getDesc();
            logger.error(respDesc+"{}",e);
        } catch (Exception e) {
            respCode = ConstantsEnum.REST_UNKNOW.getCode();
            respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
            logger.error(respDesc+"{}",e);
        }

        returnAsInfo(jsonFlag, respCode, dateCreated, respDesc, response);
    }

    private Map<String, String> unbind(Map<String, Object> bodyMap) throws Exception{
        Map<String, String> map = new HashMap<>();
        //校验appid和accountSid的有效性
        String appId = (String) bodyMap.get("appId");
        String accountSid = (String) bodyMap.get("accountSid");
        String subid = (String) bodyMap.get("subid");
        Map<String, String> checkMap = commonService.checkAppAndAccount(appId, accountSid, "02");
        if(checkMap != null){
            return checkMap;
        }

        //判断小号业务类型是否开通
        Map<String, String> checkBusinessMap = checkBusinessType(accountSid);
        if(checkBusinessMap != null){
            return checkBusinessMap;
        }

        //必选字段格式验证
        if(!Utils.notEmpty(subid)){
            map.put("respCode",ConstantsEnum.REST_AXB_BIND_ID_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_AXB_BIND_ID_NULL.getDesc());
            logger.info(ConstantsEnum.REST_AXB_BIND_ID_NULL.getDesc());
            return map;
        }

        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("secret", InitApp.getInstance().getValue("abx.secret", ""));
        sendMap.put("body", new Gson().toJson(bodyMap));
        //String url = InitApp.getInstance().getHttpUrl()+"";
        String url = InitApp.getInstance().getValue("abx.url.httpAddress", "")+"/gxlt/unBind";
        String result = HttpUtil.sendAxbHttpPost(url, sendMap);
        //如返回结果不为空 进行错误码转换
        Map<String, String> resultMap = switchErrorCode(result);
        map.putAll(resultMap);
        return map;
    }
}
