package com.e9cloud.rest.action;

import com.e9cloud.cache.CacheManager;
import com.e9cloud.core.application.InitApp;
import com.e9cloud.core.common.ID;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.service.NumberBlackService;
import com.e9cloud.mybatis.service.SipPhoneService;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.cb.CBTcpClient;
import com.e9cloud.rest.cb.Server;
import com.e9cloud.rest.obt.SipPhone;
import com.e9cloud.rest.obt.SipPhoneStat;
import com.e9cloud.rest.obt.TelnoObj;
import com.e9cloud.rest.service.RestService;
import com.e9cloud.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Created by dukai on 2016/10/26.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class SipPhoneController extends BaseController{

    @Autowired
    private SipPhoneService sipPhoneService;

    @Autowired
    private RestService restService;

    @Autowired
    private JedisClusterUtil jedisClusterUtil;

    @Autowired
    private NumberBlackService numberBlackService;


    /**
     * 云话机回拨REST接口
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "SipPhone/callback", consumes = {"application/json", "application/xml"})
    public void sipPhoneCallback(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        String respCode = null; //响应码
        String respDesc = null; //相应描述
        //String subRespCode = null; //子响应码
        //String subRespDesc = null; //子相应描述
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
                Map<String, Object> sipPhoneMap = (Map<String, Object>) requestParamMap.get("objectMap");
                String jsonStr = new Gson().toJson(sipPhoneMap);
                SipPhone sipPhone = new Gson().fromJson(jsonStr, SipPhone.class);
                sipPhone.setAccountSid(requestParamMap.get("accountSid").toString());
                sipPhone.setCallSid(ID.randomUUID());

                //sipPone呼叫
                Map<String, String> map = sipPhoneCallback(sipPhone);
                respCode = map.get("respCode");
                respDesc = map.get("respDesc");
                //subRespCode = map.get("subRespCode");
                //subRespDesc = map.get("subRespDesc");
                if(jsonFlag){
                    paramMap.put("jsonParam","\"callId\":\"" + map.get("callId") + "\"");
                }else{
                    paramMap.put("xmlParam","<callId>" + map.get("callId") + "</callId>");
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
        } catch (Exception e) {
            respCode = ConstantsEnum.REST_UNKNOW.getCode();
            respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
            logger.error(respDesc+"{}",e);
        }

        returnAsInfo(jsonFlag, respCode, dateCreated, respDesc, response, paramMap);
    }

    private Map<String, String> sipPhoneCallback(SipPhone sipPhone) throws Exception{
        Map<String, String> map = new HashMap<>();
        //校验appid和accountSid的有效性
        Map<String, String> checkMap = commonService.checkAppAndAccount(sipPhone.getAppId(),sipPhone.getAccountSid(),"05");
        if(checkMap != null){
            return checkMap;
        }

        //必选字段格式验证
        if(!Utils.notEmpty(sipPhone.getFrom())){
            map.put("respCode",ConstantsEnum.REST_FROM_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_FROM_NULL.getDesc());
            logger.info(ConstantsEnum.REST_FROM_NULL.getDesc());
            return map;
        }

        //校验主叫号码是否为sipPhone号码  取数据库sipPhone号码进行校验
        List<SpApplyNum> spApplyNumList;
        SpApplyNum spApplyNumFrom = null;
        if(Utils.sipPhoneValid(sipPhone.getFrom())){
            spApplyNumFrom = new SpApplyNum();
            spApplyNumFrom.setSipPhone(sipPhone.getFrom());
            spApplyNumList =  sipPhoneService.getSpApplyNumList(sipPhone.getAppId());
            if(Utils.notEmpty(spApplyNumList) && spApplyNumList.size()>0){
                if(!spApplyNumList.contains(spApplyNumFrom)){
                    //主叫号码未审核
                    map.put("respCode",ConstantsEnum.REST_SIPPHONE_FROM_NOTAUDIT.getCode());
                    map.put("respDesc",ConstantsEnum.REST_SIPPHONE_FROM_NOTAUDIT.getDesc());
                    logger.info(ConstantsEnum.REST_SIPPHONE_FROM_NOTAUDIT.getDesc());
                    return map;
                }
            }else{
                //主叫号码未审核
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_FROM_NOTAUDIT.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_FROM_NOTAUDIT.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_FROM_NOTAUDIT.getDesc());
                return map;
            }



            spApplyNumFrom = spApplyNumList.get(spApplyNumList.indexOf(spApplyNumFrom));
            if("01".equals(spApplyNumFrom.getCallSwitchFlag())){
                //主叫SIP号码已禁用
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_FROM_DISABLE.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_FROM_DISABLE.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_FROM_DISABLE.getDesc());
                return map;
            }
        }else{
            //主叫号码格式不符
            map.put("respCode",ConstantsEnum.REST_FROM_FORMAT.getCode());
            map.put("respDesc",ConstantsEnum.REST_FROM_FORMAT.getDesc());
            logger.info(ConstantsEnum.REST_FROM_FORMAT.getDesc());
            return map;
        }


        if(!Utils.notEmpty(sipPhone.getTo())){
            map.put("respCode",ConstantsEnum.REST_TO_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_TO_NULL.getDesc());
            logger.info(ConstantsEnum.REST_TO_NULL.getDesc());
            return map;
        }

        //获取AppInfo的扩展信息
        AppInfoExtra appInfoExtra = appInfoService.findAppInfoExtraByAppid(sipPhone.getAppId());
        if(!Utils.notEmpty(appInfoExtra)){
            map.put("respCode",ConstantsEnum.REST_SIPPHONE_APP_EXTRA.getCode());
            map.put("respDesc",ConstantsEnum.REST_SIPPHONE_APP_EXTRA.getDesc());
            logger.info(ConstantsEnum.REST_SIPPHONE_APP_EXTRA.getDesc());
            return map;
        }


        if(Utils.sipPhoneValid(sipPhone.getTo())){
            //校验被叫号码是否为sipPhone号码  取数据库sipPhone号码进行校验
           // SpApplyNum spApplyNumTo = getSpApplyNum(spApplyNumList, sipPhone.getTo());
            SpApplyNum spApplyNumTo = new SpApplyNum();
            spApplyNumTo.setSipPhone(sipPhone.getTo());
            if(!spApplyNumList.contains(spApplyNumTo)){
                //被叫号码未审核
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_TO_NOTAUDIT.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_TO_NOTAUDIT.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_TO_NOTAUDIT.getDesc());
                return map;
            }else{
                //如果都是sipPhone号码的话 就需要检验是否有开通sipPhone间的回拨业务
                //校验是否开通增值业务：bit0:SIP phone间回拨、 bit1:SIP phone间直拨
                Integer valueAdded = appInfoExtra.getValueAdded();
                if(!Utils.notEmpty(valueAdded) || (valueAdded&1) != 1){
                    //未开通增值业务不能进行sipPhone间的回拨
                    map.put("respCode",ConstantsEnum.REST_SIPPHONE_CALLBACK_NOTBUS.getCode());
                    map.put("respDesc",ConstantsEnum.REST_SIPPHONE_CALLBACK_NOTBUS.getDesc());
                    logger.info(ConstantsEnum.REST_SIPPHONE_CALLBACK_NOTBUS.getDesc());
                    return map;
                }
            }

            spApplyNumTo = spApplyNumList.get(spApplyNumList.indexOf(spApplyNumTo));
            if("01".equals(spApplyNumTo.getCallSwitchFlag())){
                //被叫SIP号码已禁用
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_TO_DISABLE.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_TO_DISABLE.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_TO_DISABLE.getDesc());
                return map;
            }
        }else{
            if(!Utils.phoneAndTelValid(sipPhone.getTo())) {
                map.put("respCode",ConstantsEnum.REST_TO_FORMAT.getCode());
                map.put("respDesc",ConstantsEnum.REST_TO_FORMAT.getDesc());
                logger.info(ConstantsEnum.REST_TO_FORMAT.getDesc());
                return map;
            }else{
                //获取黑名单号码列表
                List<String> numberBlacks=numberBlackService.findList();
               if(numberBlacks.contains(sipPhone.getTo())) {
                   //被叫显号黑名单限制
                   map.put("respCode",ConstantsEnum.REST_TO_NUMBERBLACK.getCode());
                   map.put("respDesc",ConstantsEnum.REST_TO_NUMBERBLACK.getDesc());
                   logger.info(ConstantsEnum.REST_TO_NUMBERBLACK.getDesc());
                   return map;
               }
            }
        }

        //获取主被叫长途区号
        String areaCodeFrom = getAreaCode(sipPhone.getFrom());
        String areaCodeTo = getAreaCode(sipPhone.getTo());
        if( !areaCodeFrom.equals(areaCodeTo) && "01".equals(spApplyNumFrom.getLongDistanceFlag()) && !Utils.sipPhoneValid(sipPhone.getTo())){
            //SIP号码无长途权限
            map.put("respCode",ConstantsEnum.REST_SIPPHONE_NOLONG.getCode());
            map.put("respDesc",ConstantsEnum.REST_SIPPHONE_NOLONG.getDesc());
            logger.info(ConstantsEnum.REST_SIPPHONE_NOLONG.getDesc());
            return map;
        }


        //被叫显号不能为被叫号码本身
        if(sipPhone.getTo().equals(spApplyNumFrom.getShowNum())){
            map.put("respCode",ConstantsEnum.REST_TOSERNUM_NOSELF.getCode());
            map.put("respDesc",ConstantsEnum.REST_TOSERNUM_NOSELF.getDesc());
            logger.info(ConstantsEnum.REST_TOSERNUM_NOSELF.getDesc());
            return map;
        }

        if(Utils.notEmpty(sipPhone.getMaxCallTime()) && (sipPhone.getMaxCallTime()<0 || sipPhone.getMaxCallTime()>28800)) {
            //maxCallTime格式不符（不为空时，小于0 或 超过8小时）
            map.put("respCode",ConstantsEnum.REST_MAXCALLTIME_FORMAT.getCode());
            map.put("respDesc",ConstantsEnum.REST_MAXCALLTIME_FORMAT.getDesc());
            logger.info(ConstantsEnum.REST_MAXCALLTIME_FORMAT.getDesc());
            return map;
        }

        if(Utils.notEmpty(sipPhone.getUserData()) && sipPhone.getUserData().length()>128){
            //userData格式不符（不为空时，最长128字节）
            map.put("respCode",ConstantsEnum.REST_USERDATA_FORMAT.getCode());
            map.put("respDesc",ConstantsEnum.REST_USERDATA_FORMAT.getDesc());
            logger.info(ConstantsEnum.REST_USERDATA_FORMAT.getDesc());
            return map;
        }


        //设置主被叫外显号
        List<String> fromToSerNum = new ArrayList<>();
        fromToSerNum.add(spApplyNumFrom.getShowNum());
        sipPhone.setFromSerNum(fromToSerNum);
        sipPhone.setToSerNum(fromToSerNum);

        // 添加rn码
        setSipPhoneRn(sipPhone);

        Integer recordingType = appInfoExtra.getRecordingType();
        //填充录音标识
        if(Utils.notEmpty(recordingType) && (recordingType&1) == 1){
            sipPhone.setNeedRecord(1);
        }else{
            sipPhone.setNeedRecord(0);
        }

        //主叫(A路)必须是sipPhone号码
        if(Utils.sipPhoneValid(sipPhone.getFrom()) && Utils.sipPhoneValid(sipPhone.getTo())){
            sipPhone.setSipphoneFlag(3);
        }else if(Utils.sipPhoneValid(sipPhone.getFrom()) && !Utils.sipPhoneValid(sipPhone.getTo())){
            sipPhone.setSipphoneFlag(1);
        }else if(!Utils.sipPhoneValid(sipPhone.getFrom()) && Utils.sipPhoneValid(sipPhone.getTo())){
            sipPhone.setSipphoneFlag(2);
        }

        //语音编码
        sipPhone.setCodec(appInfoExtra.getVoiceCode());

        //填充录音标识
        sipPhone.setRecordFlag(restService.getRecordFlag(sipPhone.getAccountSid()));

        // 转换成json格式报文
        String body = new Gson().toJson(sipPhone);
        logger.info("发送CB报文体：" + body);
        // CB报文头4域值
        String v1 = version;// 版本号
        String v2 = String.valueOf(System.currentTimeMillis());// 消息流水号
        String v3 = sipPhone.getCallSid();// 32位标识号
        String v4 = ConstantsEnum.REST_SIPPHONE_REQUEST_CODE.getStrValue();// 消息类型
        String v5 = sysPropertits.getSnCode();// 设备码
        // 调用CB发送方法
        Server server = InitApp.getInstance().getBestServer();
        boolean status = InitApp.getInstance().sendMsg(CBTcpClient.hbMsg(v1, v2, v3, v4, v5, body),
                server);
        jedisClusterUtil.STRINGS.incr("cbmonitor:req:"+DateUtil.dateToStr(new Date(),DateUtil.NO_SLASH)+":"+v5);
        jedisClusterUtil.STRINGS.incr("cbmonitor:req:"+DateUtil.dateToStr(new Date(),DateUtil.NO_SLASH)+":"+v5+":"+server.getIp());
        if (!status) {
            // 发送CB失败，返回未知错误
            map.put("respCode",ConstantsEnum.REST_UNKNOW.getCode());
            map.put("respDesc",ConstantsEnum.REST_UNKNOW.getDesc());
            logger.info(ConstantsEnum.REST_UNKNOW.getDesc());
            return map;
        } else {
            // 缓存callit-->cb
            // 缓存时间原则：
            // 1、默认8小时过期
            // 2、有最大通话时长，按照最大通话时长过期
            int second = 8 * 60 * 60;
            if (sipPhone.getMaxCallTime() > 0) {
                second = sipPhone.getMaxCallTime();
            }
            jedisClusterUtil.STRINGS.setEx(sipPhone.getCallSid(),  1 * 60 * 60, server.getIp());
        }

        map.put("respCode",ConstantsEnum.REST_SUCCCODE.getCode());
        map.put("callId",sipPhone.getCallSid());
        return map;
    }


    /**
     * 设置sipPhone的RN码
     * @param sipPhone
     */
    private void setSipPhoneRn(SipPhone sipPhone){
        //获取业务和app路由编码
        AppInfo appInfo = CacheManager.getAppInfo(sipPhone.getAppId());
        String routeCode = restService.routeCode(appInfo, Constants.BusinessCode.CLOUDPHONE);
        // 主叫显号运营商+区号
        String fromXh = restService.getXh(sipPhone.getFromSerNum());
        String rna = restService.getRn(fromXh, sipPhone.getFrom());
        sipPhone.setRn_a(routeCode + rna);

        // 被叫显号运营商+区号
        String toXh = restService.getXh(sipPhone.getToSerNum());
        String rnb = restService.getRn(toXh, sipPhone.getTo());
        sipPhone.setRn_b(routeCode + rnb);
    }

    /**
     * 获取号码所对应的区号
     * @param phone
     * @return
     */
    public String getAreaCode(String phone){
        // 获得号码前几位
        int bit = phone.length() < 7 ? phone.length() : 7;
        TelnoObj telnoObj = null;
        while (telnoObj == null && bit >= 3) {
            phone = phone.substring(0, bit);
            telnoObj = CacheManager.telnoInfoMap.get(phone);
            bit--;
        }
        return Utils.notEmpty(telnoObj) ? telnoObj.getAreacode() : "0000";
    }

    /**
     * SipPhone号码权限设置
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "SipPhone/setCallSwitch", consumes = {"application/json", "application/xml"})
    public void setCallSwitch(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception{
        handleSipPhoneStat(request,response,model,"switch","update");
    }

    /**
     * SipPhone号码权限查询
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "SipPhone/queryCallSwitch", consumes = {"application/json", "application/xml"})
    public void queryCallSwitch(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception{
        handleSipPhoneStat(request,response,model,"switch","select");
    }

    /**
     * SipPhone长途权限设置
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "SipPhone/setLongDistanceCall", consumes = {"application/json", "application/xml"})
    public void setLongDistanceCall(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception{
        handleSipPhoneStat(request,response,model,"longDistance","update");
    }

    /**
     * SipPhone长途权限查询
     * @param request
     * @param response
     * @param model
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "SipPhone/queryLongDistanceCall", consumes = {"application/json", "application/xml"})
    public void queryLongDistanceCall(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception{
        handleSipPhoneStat(request,response,model,"longDistance","select");
    }

    private void handleSipPhoneStat(HttpServletRequest request, HttpServletResponse response, ModelMap model, String handleType, String operType) throws Exception{
        String respCode; //响应码
        String respDesc; //相应描述
        Map<String, String> paramMap = new HashMap<>(); //响应as端参数设置
        // 生成请求时间
        String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);

        boolean jsonFlag = true;
        try{
            Map<String, Object> requestParamMap = analysisRequestParam(request, model);

            jsonFlag = (boolean) requestParamMap.get("jsonFlag");
            boolean reqFlag = (boolean) requestParamMap.get("reqFlag");
            if(reqFlag){
                Map<String, String> reqMap = (Map<String, String>) requestParamMap.get("objectMap");
                String jsonStr = new Gson().toJson(reqMap);
                SipPhoneStat sipPhoneStat = new Gson().fromJson(jsonStr, SipPhoneStat.class);

                //设置参数信息
                SpApplyNum spApplyNum = new SpApplyNum();
                spApplyNum.setAppid(sipPhoneStat.getAppId());
                spApplyNum.setSipPhone(sipPhoneStat.getSipPhoneNumber());
                spApplyNum.setCallSwitchFlag(sipPhoneStat.getCallSwitchFlag());
                spApplyNum.setLongDistanceFlag(sipPhoneStat.getLongDistanceFlag());
                spApplyNum.setAccountSid(requestParamMap.get("accountSid").toString());

                //取消通话
                Map<String, String> map = operSipPhone(spApplyNum,handleType,operType);
                respCode = map.get("respCode");
                respDesc = map.get("respDesc");
                if("select".equals(operType)) {
                    if (jsonFlag) {
                        paramMap.put("jsonParam", map.get("jsonParam"));
                    } else {
                        paramMap.put("xmlParam", map.get("xmlParam"));
                    }
                    if("000000".equals(respCode)){
                        paramMap.put("status", "true");
                    }
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
        } catch (Exception e) {
            respCode = ConstantsEnum.REST_UNKNOW.getCode();
            respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
            logger.error(respDesc+"{}",e);
        }

        if("select".equals(operType)){
            returnAsInfo(jsonFlag, respCode, dateCreated, respDesc, response, paramMap);
        }else{
            returnAsInfo(jsonFlag, respCode, dateCreated, respDesc, response);
        }
    }

    /**
     * 校验信息
     * @param spApplyNum
     * @param reqType
     * @param operType
     * @return
     * @throws Exception
     */
    private Map<String, String>  operSipPhone(SpApplyNum spApplyNum, String reqType, String operType) throws Exception{
        Map<String, String> map = new HashMap<>();

        //校验appid和accountSid的有效性
        Map<String, String> checkMap = commonService.checkAppAndAccount(spApplyNum.getAppid(), spApplyNum.getAccountSid(),"05");
        if(checkMap != null){
            return checkMap;
        }

        //必选字段格式验证
        if(!Utils.notEmpty(spApplyNum.getSipPhone())){
            map.put("respCode",ConstantsEnum.REST_SIPPHONE_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_SIPPHONE_NULL.getDesc());
            logger.info(ConstantsEnum.REST_SIPPHONE_NULL.getDesc());
            return map;
        }

        //校验SipPhone号码的格式
        SpApplyNum spApplyNumFrom;
        if(Utils.sipPhoneValid(spApplyNum.getSipPhone())){
            spApplyNumFrom =  sipPhoneService.getSipPhoneStat(spApplyNum);
            if(!Utils.notEmpty(spApplyNumFrom)){
                //SIP号码未审核
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_NOTAUDIT.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_NOTAUDIT.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_NOTAUDIT.getDesc());
                return map;
            }
        }else{
            //SIP号码格式不符
            map.put("respCode",ConstantsEnum.REST_SIPPHONE_NOMATCH.getCode());
            map.put("respDesc",ConstantsEnum.REST_SIPPHONE_NOMATCH.getDesc());
            logger.info(ConstantsEnum.REST_SIPPHONE_NOMATCH.getDesc());
            return map;
        }

        if("update".equals(operType)){
            if("switch".equals(reqType) && !Utils.notEmpty(spApplyNum.getCallSwitchFlag())){
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_SWITCH_NULL.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_SWITCH_NULL.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_SWITCH_NULL.getDesc());
                return map;
            }

            if("longDistance".equals(reqType) && !Utils.notEmpty(spApplyNum.getLongDistanceFlag())){
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_LONG_NULL.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_LONG_NULL.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_LONG_NULL.getDesc());
                return map;
            }

            if(Utils.notEmpty(spApplyNum.getLongDistanceFlag()) && !"00".equals(spApplyNum.getLongDistanceFlag()) &&
                    !"01".equals(spApplyNum.getLongDistanceFlag())){
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_LONG_NOMAT.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_LONG_NOMAT.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_LONG_NOMAT.getDesc());
                return map;
            }

            if(Utils.notEmpty(spApplyNum.getCallSwitchFlag()) &&!"00".equals(spApplyNum.getCallSwitchFlag()) &&
                    !"01".equals(spApplyNum.getCallSwitchFlag())){
                map.put("respCode",ConstantsEnum.REST_SIPPHONE_SWITCH_NOMAT.getCode());
                map.put("respDesc",ConstantsEnum.REST_SIPPHONE_SWITCH_NOMAT.getDesc());
                logger.info(ConstantsEnum.REST_SIPPHONE_SWITCH_NOMAT.getDesc());
                return map;
            }

            //设置状态信息
            sipPhoneService.updateSipPhoneStat(spApplyNum);
        }

        if("select".equals(operType)){
            if("switch".equals(reqType)){
                map.put("jsonParam", "\"sipPhoneNumber\":\"" + spApplyNumFrom.getSipPhone() + "\",\"callSwitchFlag\":\"" + spApplyNumFrom.getCallSwitchFlag() + "\"");
                map.put("xmlParam", "<sipPhoneNumber>" + spApplyNumFrom.getSipPhone() + "</sipPhoneNumber><callSwitchFlag>"+ spApplyNumFrom.getCallSwitchFlag() + "</callSwitchFlag>");
            }

            if("longDistance".equals(reqType)){
                map.put("jsonParam", "\"sipPhoneNumber\":\"" + spApplyNumFrom.getSipPhone() + "\",\"longDistanceCallFlag\":\"" + spApplyNumFrom.getLongDistanceFlag() + "\"");
                map.put("xmlParam", "<sipPhoneNumber>" + spApplyNumFrom.getSipPhone() + "</sipPhoneNumber><longDistanceCallFlag>"+ spApplyNumFrom.getLongDistanceFlag() + "</longDistanceCallFlag>");
            }
        }

        map.put("respCode", ConstantsEnum.REST_SUCCCODE.getCode());
        return map;
    }

}
