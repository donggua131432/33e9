package com.e9cloud.rest.action;

import com.e9cloud.cache.CacheManager;
import com.e9cloud.core.application.InitApp;
import com.e9cloud.core.common.ID;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.CbVoiceCode;
import com.e9cloud.mybatis.domain.VoiceVerifyTemp;
import com.e9cloud.mybatis.service.NumberBlackService;
import com.e9cloud.mybatis.service.VoiceCodeService;
import com.e9cloud.mybatis.service.VoiceVerifyService;
import com.e9cloud.rest.cb.CBTcpClient;
import com.e9cloud.rest.cb.Server;
import com.e9cloud.rest.obt.VoiceValidate;
import com.e9cloud.rest.service.RestService;
import com.e9cloud.util.Constants;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @描述: 语音验证码请求接口
 * @作者: DuKai
 * @创建时间: 2017/5/3 11:47
 * @版本: 1.0
 */
@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class VoiceValidateController extends BaseController{

    @Autowired
    private RestService restService;

    @Autowired
    private NumberBlackService numberBlackService;

    @Autowired
    private VoiceCodeService voiceCodeService;

    @Autowired
    private VoiceVerifyService voiceVerifyService;

    @RequestMapping(method = RequestMethod.POST, value = "VoiceValidate/requestCall", consumes = {"application/json", "application/xml"})
    public void voiceValidate(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String respCode = null; //响应码
        String respDesc = null; //相应描述
        Map<String, String> paramMap = new HashMap<>(); //通话记录ID
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
                Map<String, Object> voiceValidateMap = (Map<String, Object>) requestParamMap.get("objectMap");
                voiceValidateMap.put("accountSid",requestParamMap.get("accountSid").toString());

                //sipPone呼叫
                Map<String, String> map = voiceValidateCall(voiceValidateMap);
                respCode = map.get("respCode");
                respDesc = map.get("respDesc");
                if(jsonFlag){
                    paramMap.put("jsonParam","\"requestId\":\"" + map.get("callId") + "\",\"callDisplayNumber\":\"" + map.get("callDisplayNumber") + "\"");
                }else{
                    paramMap.put("xmlParam","<requestId>" + map.get("callId") + "</requestId><callDisplayNumber>" + map.get("callDisplayNumber") + "</callDisplayNumber>");
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
        } catch (Exception e) {
            respCode = ConstantsEnum.REST_UNKNOW.getCode();
            respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
            logger.error(respDesc+"{}",e);
        }

        returnAsInfo(jsonFlag, respCode, dateCreated, respDesc, response, paramMap);
    }


    private Map<String, String> voiceValidateCall(Map<String, Object> voiceValidateMap) throws Exception {
        Map<String, String> map = new HashMap<>();
        //校验appid和accountSid的有效性
        String appId = (String) voiceValidateMap.get("appId");
        String accountSid = (String) voiceValidateMap.get("accountSid");
        String voiceRecId = (String) voiceValidateMap.get("voiceRecId");
        String verifyCode = (String) voiceValidateMap.get("verifyCode");
        String to = (String) voiceValidateMap.get("called");
        String displayNum = (String) voiceValidateMap.get("callDisplayNumber");

        VoiceValidate voiceValidate = new VoiceValidate();
        //校验appid和accountSid的有效性
        Map<String, String> checkMap = commonService.checkAppAndAccount(appId,accountSid,"02");
        if(checkMap != null){
            return checkMap;
        }

        //参数校验
        if(Utils.notEmpty(voiceRecId)){
            if(Utils.voiceRecIdValid(voiceRecId)){
                voiceValidate.setVoiceRecId(voiceRecId);
                //根据语音模板ID查找语音文件
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("id",voiceRecId);
                paramMap.put("appid",appId);
                VoiceVerifyTemp voiceVerifyTemp = voiceVerifyService.getVoiceVerifyTempByMap(paramMap);
                if(Utils.notEmpty(voiceVerifyTemp)){
                    if("00".equals(voiceVerifyTemp.getAuditStatus())){
                        map.put("respCode",ConstantsEnum.REST_VOICEVERIFY_AUDIT.getCode());
                        map.put("respDesc",ConstantsEnum.REST_VOICEVERIFY_AUDIT.getDesc());
                        logger.info(ConstantsEnum.REST_VOICEVERIFY_AUDIT.getDesc());
                        return map;
                    }
                    if("02".equals(voiceVerifyTemp.getAuditStatus())) {
                        map.put("respCode", ConstantsEnum.REST_VOICEVERIFY_AUDIT_NO.getCode());
                        map.put("respDesc", ConstantsEnum.REST_VOICEVERIFY_AUDIT_NO.getDesc());
                        logger.info(ConstantsEnum.REST_VOICEVERIFY_AUDIT_NO.getDesc());
                        return map;
                    }
                    if("01".equals(voiceVerifyTemp.getStatus())) {
                        map.put("respCode", ConstantsEnum.REST_VOICEVERIFY_ISNOT.getCode());
                        map.put("respDesc", ConstantsEnum.REST_VOICEVERIFY_ISNOT.getDesc());
                        logger.info(ConstantsEnum.REST_VOICEVERIFY_ISNOT.getDesc());
                        return map;
                    }

                    voiceValidate.setMaxTimes(voiceVerifyTemp.getCalledMax());
                    voiceValidate.setLanguage(voiceVerifyTemp.getLanguage());
                    voiceValidate.setPlayTimes(voiceVerifyTemp.getPlayNum());
                }else{
                    map.put("respCode", ConstantsEnum.REST_VOICEVERIFY_ISNOT.getCode());
                    map.put("respDesc", ConstantsEnum.REST_VOICEVERIFY_ISNOT.getDesc());
                    logger.info(ConstantsEnum.REST_VOICEVERIFY_ISNOT.getDesc());
                    return map;
                }
            }else{
                map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_VOICERECID_FORMAT.getCode());
                map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_VOICERECID_FORMAT.getDesc());
                logger.info(ConstantsEnum.REST_VOICEVALIDATE_VOICERECID_FORMAT.getDesc());
                return map;
            }
        }else{
            voiceValidate.setMaxTimes(20);
            voiceValidate.setLanguage(0);
            voiceValidate.setPlayTimes(2);
        }


        if(!Utils.notEmpty(verifyCode)){
            map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_VERIFYCODE_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_VERIFYCODE_NULL.getDesc());
            logger.info(ConstantsEnum.REST_VOICEVALIDATE_VERIFYCODE_NULL.getDesc());
            return map;
        }

        if(!Utils.verifyCodeValid(verifyCode)){
            map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_VERIFYCODE_FORMAT.getCode());
            map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_VERIFYCODE_FORMAT.getDesc());
            logger.info(ConstantsEnum.REST_VOICEVALIDATE_VERIFYCODE_FORMAT.getDesc());
            return map;
        }

        if(!Utils.notEmpty(to)){
            map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_CALLED_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_CALLED_NULL.getDesc());
            logger.info(ConstantsEnum.REST_VOICEVALIDATE_CALLED_NULL.getDesc());
            return map;
        }

        if(!Utils.phoneAndTelValid(to)){
            map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_CALLED_FORMAT.getCode());
            map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_CALLED_FORMAT.getDesc());
            logger.info(ConstantsEnum.REST_VOICEVALIDATE_CALLED_FORMAT.getDesc());
            return map;
        }

        //获取黑名单号码列表
        List<String> numberBlacks = numberBlackService.findList();
        if (Utils.notEmpty(numberBlacks) && numberBlacks.contains(to)) {
            map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_TO_NUMBERBLACK.getCode());
            map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_TO_NUMBERBLACK.getDesc());
            logger.info(ConstantsEnum.REST_VOICEVALIDATE_TO_NUMBERBLACK.getDesc());
            return map;
        }

        //获取显号池号码 校验显号池是否为空
        List<String> displayNumList = voiceVerifyService.getDisplayNumList(appId);
        if(displayNumList != null && displayNumList.size()>0){
            //校验显号
            if(Utils.notEmpty(displayNum)){
                //显号格式校验
                if(!Utils.phoneTelNfValid(displayNum)){
                    map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_FORMAT.getCode());
                    map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_FORMAT.getDesc());
                    logger.info(ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_FORMAT.getDesc());
                    return map;
                }
                //校验显号黑名单
                if (Utils.notEmpty(numberBlacks) && numberBlacks.contains(displayNum)) {
                    map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_TOSERNUM_NUMBERBLACK.getCode());
                    map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_TOSERNUM_NUMBERBLACK.getDesc());
                    logger.info(ConstantsEnum.REST_VOICEVALIDATE_TOSERNUM_NUMBERBLACK.getDesc());
                    return map;
                }

                //校验此号码是否在显号池中
                if(displayNumList.contains(displayNum)){
                    voiceValidate.setDisplayNum(displayNum);
                }else{
                    map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_NOT.getCode());
                    map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_NOT.getDesc());
                    logger.info(ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_NOT.getDesc());
                    return map;
                }
            }else{
                //直接从显号池中随机读取号码
                String randomNum = displayNumList.get(new Random().nextInt(displayNumList.size()));
                voiceValidate.setDisplayNum(randomNum);
            }
        }else{
            map.put("respCode",ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_NULL.getDesc());
            logger.info(ConstantsEnum.REST_VOICEVALIDATE_DISPLAYNUM_NULL.getDesc());
            return map;
        }


        voiceValidate.setAppId(appId);
        voiceValidate.setCallSid(ID.randomUUID());
        voiceValidate.setVerifyCode(verifyCode);
        voiceValidate.setTo(to);
        setVoiceValidateRn(voiceValidate);
        voiceValidate.setPlayInterval(2);
        voiceValidate.setCallSid(ID.randomUUID());
        //获取语音编码
        CbVoiceCode cbVoiceCode = voiceCodeService.findVoiceCodeByBusCode("voiceValidate");
        if(cbVoiceCode != null) voiceValidate.setCodec(cbVoiceCode.getVoiceCode());
        //voiceValidate.setCodec("PCMA");

        // 转换成json格式报文
        String body = new Gson().toJson(voiceValidate);
        logger.info("发送CB报文体：" + body);
        // CB报文头4域值
        String v1 = version;// 版本号
        String v2 = String.valueOf(System.currentTimeMillis());// 消息流水号
        String v3 = voiceValidate.getCallSid();// 32位标识号
        String v4 = ConstantsEnum.REST_VOICE_VALIDATE_REQUEST_CODE.getStrValue();// 消息类型
        String v5 = sysPropertits.getSnCode();// 设备码
        // 调用CB发送方法
        Server server = InitApp.getInstance().getBestServer();
        boolean status = InitApp.getInstance().sendMsg(CBTcpClient.hbMsg(v1, v2, v3, v4, v5, body),server);
        if (!status) {
            // 发送CB失败，返回未知错误
            map.put("respCode",ConstantsEnum.REST_UNKNOW.getCode());
            map.put("respDesc",ConstantsEnum.REST_UNKNOW.getDesc());
            logger.info(ConstantsEnum.REST_UNKNOW.getDesc());
            return map;
        }

        map.put("respCode",ConstantsEnum.REST_SUCCCODE.getCode());
        map.put("callId",voiceValidate.getCallSid());
        map.put("callDisplayNumber",voiceValidate.getDisplayNum());
        return map;
    }


    /**
     * 设置语音验证码的RN码
     * @param voiceValidate
     */
    private void setVoiceValidateRn(VoiceValidate voiceValidate){
        //获取业务和app路由编码
        AppInfo appInfo = CacheManager.getAppInfo(voiceValidate.getAppId());
        String routeCode = restService.routeCode(appInfo, Constants.BusinessCode.VOICEVALIDATE);
        // 被叫叫显号运营商+区号
        List<String> displayNumList = new ArrayList<>();
        displayNumList.add(voiceValidate.getDisplayNum());
        String fromXh = restService.getXh(displayNumList);
        String rna = restService.getRn(fromXh, voiceValidate.getTo());
        voiceValidate.setRn_a(routeCode + rna);
    }
}
