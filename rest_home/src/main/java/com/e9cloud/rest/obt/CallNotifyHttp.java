package com.e9cloud.rest.obt;

import com.e9cloud.util.Utils;

import java.util.Date;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CallNotifyHttp
{
    private String feeUrl; // 计费回调地址

    private  String action ;//请求类型  CallInvite(呼叫发起)  CallEstablish(呼叫建立) Hangup(呼叫挂机)  IncomeHangup(专号通回呼挂机)
    private  int type;//呼叫类型 1:专线回拨、 2:专号回拨(隐私,搜房)、 3:保留、 4:保留、 5:保留(中原ivr)、  6:语音通知、 7:sipPhone回拨、 8:sipPhone直拨、 9:sipPhone回呼、10:虚拟小号回拨、11:语音验证码
    private  String appId;//应用ID
    private  String subId ;//子账号id
    private  String caller;//主叫号码
    private  String called;//被叫号码
    /**CallInvite-呼叫挂机 独有属性 start**/
    private  int userFlag ;//主被叫标示	0标示主叫，1标示被叫
    /**CallInvite-呼叫挂机 独有属性 end**/
    private  int subType;//外呼显号标示	0:不显号 1:显号
    private  String callSid;//呼叫的唯一标示	32位字符串
    private  String dateCreated;//请求时间	  回调用户的时间
    private  String userData ;//用户自定义数据

    /**Hangup-呼叫挂机 独有属性**/
    private  String startTimeA ;//主叫接听时间 YYYYMMDDHH24MISSCallNotify
    private  String startTimeB ;//被叫接听时间 YYYYMMDDHH24MISS
    private  String endTime;//通话结束时间 YYYYMMDDHH24MISS
    private  String duration;//主叫或被叫通话时长 单位：秒
    private  String recordUrl;//通话录音完整下载地址
    private  String byeType ;//通话挂机类型

    /**私密专线 独有属性**/
    private  String maskNumber ;//隐私号码

    /**虚拟小号**/
    private String telX;  //虚拟号码

    /**虚拟号码 独有属性**/
    private  String dtmfNumber ;//虚拟号码

    /**(IncomeHangup 回呼挂机), (SipPhoneHangup 直拨挂机、回呼挂机) 独有属性**/
    private  String startTime ;//被叫接听时间 YYYYMMDDHH24MISS

    /**VoiceNotify-语音通知挂机回调 独有属性**/
    private  int dtmfFlag ;//0表示关闭，不接受反馈；1表示开启，接受反馈码；默认值是0。
    private  String dtmfNum ;//接收到的客户响应编码,若反馈开关开启，则接收客户DTMF码。
    private  int orderFlag ;//是否开启定时下发。
    private  String orderTime ;//定时下发开关开启有效。传入格式YYYYMMDDhhmmss，精确到秒即可
    private  String callTime ;//呼叫时间，最后一次呼叫时间为准。
    private  String to ;//被叫电话号码，目前支持手机及固定电话（固话需带区号）
    private  String displayNum ;//被叫的外显号码，rest端必须传入。
    private  String voiceRecId;//语音模板ID

    private Date createDateTime;//入库时间

    /**语音验证码**/
    private String verifyCode; //语音验证码


    //将对象呼叫建立或呼叫接通字段转换成XML字符串
    public String toXML(String rootElement) {

        if (type == 10) { // 虚拟小号
            return toAxbXML(rootElement);
        }

        StringBuffer str=new StringBuffer();
        if("VoiceNotify".equals(action)) {
            //语音通知挂机回调
            str.append( "<?xml version='1.0'?>");
            str.append( "<" +rootElement+ ">");
            str.append( "<action>"+action+"</action>" +
                    "<appId>"+appId+"</appId>" +
                    "<dtmfFlag>"+dtmfFlag+"</dtmfFlag>" +
                    "<dtmfNum>"+dtmfNum+"</dtmfNum>" +
                    "<orderFlag>"+orderFlag+"</orderFlag>" +
                    "<orderTime>"+orderTime+"</orderTime>" +
                    "<callTime>"+callTime+"</callTime>" +
                    "<requestId>"+callSid+"</requestId>" +
                    "<to>"+to+"</to>" +
                    "<byeType>"+byeType+"</byeType>" +
                    "<displayNum>"+displayNum+"</displayNum>");
            str.append( "</" +rootElement+ ">");
        }else if("VoiceCodeNotify".equals(action)){
            //语音验证码通知挂机回调
            str.append( "<?xml version='1.0'?>");
            str.append( "<" +rootElement+ ">");
            str.append( "<action>"+action+"</action>" +
                    "<appId>"+appId+"</appId>" +
                    "<callSid>"+callSid+"</callSid>" +
                    "<voiceRecId>"+voiceRecId+"</voiceRecId>" +
                    "<verifyCode>"+verifyCode+"</verifyCode>" +
                    "<to>"+to+"</to>" +
                    "<displayNum>"+displayNum+"</displayNum>" +
                    "<callTime>"+callTime+"</callTime>" +
                    "<byeType>"+byeType+"</byeType>");
            str.append( "</" +rootElement+ ">");
        }else if(Utils.notEmpty(dtmfNumber)){
            //根据dtmfNumber区分是虚拟号码通知
            //虚拟通知
            str.append( "<?xml version='1.0'?>");
            str.append( "<" +rootElement+ ">");
            str.append( "<action>"+action+"</action>" +
                    "<type>"+type+"</type>" +
                    "<caller>"+caller+"</caller>" +
                    "<called>"+called+"</called>" +
                    "<dtmfNumber>"+dtmfNumber+"</dtmfNumber>" +
                    "<dateCreated>"+dateCreated+"</dateCreated>" +
                    "<startTime>"+startTime+"</startTime>" +
                    "<endTime>"+endTime+"</endTime>" +
                    "<duration>"+duration+"</duration>" +
                    "<callId>"+callSid+"</callId>" +
                    "<recordUrl>"+recordUrl+"</recordUrl>");
            str.append( "</" +rootElement+ ">");
        }else if(Utils.notEmpty(maskNumber)){
            //根据maskNumber区分是正常回拨通知，还是隐私通知
            //隐私通知
            str.append( "<?xml version='1.0'?>");
            str.append( "<" +rootElement+ ">");
            str.append( "<action>"+action+"</action>" +
                    "<type>"+type+"</type>" +
                    "<appId>"+appId+"</appId>" +
                    "<caller>"+caller+"</caller>" +
                    "<called>"+called+"</called>" +
                    "<maskNumber>"+maskNumber+"</maskNumber>");
            //如果是呼叫发起通知，则组装发起专有属性
            if("CallInvite".equals(action)) {
                str.append("<userFlag>" + userFlag + "</userFlag>");
            }
            //如果是回呼挂机通知，则组装回呼挂机专有属性
            if("IncomeHangup".equals(action)){
                str.append( "<dateCreated>"+dateCreated+"</dateCreated>" +
                        "<startTime>"+startTime+"</startTime>" +
                        "<endTime>"+endTime+"</endTime>" +
                        "<duration>"+duration+"</duration>" +
                        "<recordUrl>"+recordUrl+"</recordUrl>" +
                        "<userFlag>" + userFlag + "</userFlag>"+
                        "<byeType>"+byeType+"</byeType>");
            }else{
                str.append( "<callId>"+callSid+"</callId>" +
                        "<dateCreated>"+dateCreated+"</dateCreated>" +
                        "<userData>"+userData+"</userData>");
                //如果是呼叫挂机通知，则组装挂机专有属性
                if("Hangup".equals(action)){
                    str.append( "<startTimeA>"+startTimeA+"</startTimeA>" +
                            "<startTimeB>"+startTimeB+"</startTimeB>" +
                            "<endTime>"+endTime+"</endTime>" +
                            "<duration>"+duration+"</duration>" +
                            "<recordUrl>"+recordUrl+"</recordUrl>" +
                            "<userFlag>" + userFlag + "</userFlag>"+
                            "<byeType>"+byeType+"</byeType>");
                }
            }

            str.append( "</" +rootElement+ ">");
        }else{
            //正常回拨通知
            str.append( "<?xml version='1.0'?>");
            str.append( "<" +rootElement+ ">");
            str.append( "<action>"+action+"</action>" +
                    "<type>"+type+"</type>" +
                    "<appId>"+appId+"</appId>" +
                    //"<subId>"+subId+"</subId>" +
                    "<caller>"+caller+"</caller>" +
                    "<called>"+called+"</called>" +
                    "<subType>"+subType+"</subType>" +
                    "<callId>"+callSid+"</callId>" +
                    "<dateCreated>"+dateCreated+"</dateCreated>");


            //如果是呼叫发起通知或者是挂机 则加上主被叫标示
            if("CallInvite".equals(action) || "Hangup".equals(action)) {
                str.append("<userFlag>" + userFlag + "</userFlag>");
            }

            //如果是呼叫挂机通知，则组装挂机专有属性
            if("Hangup".equals(action)){
                if(type == 8 || type == 9){
                    str.append("<startTime>"+startTime+"</startTime>");
                }else{
                    str.append("<startTimeA>"+startTimeA+"</startTimeA>" +
                            "<startTimeB>"+startTimeB+"</startTimeB>"+
                            "<userData>"+userData+"</userData>");
                }
                str.append("<endTime>"+endTime+"</endTime>" +
                        "<duration>"+duration+"</duration>" +
                        "<recordUrl>"+recordUrl+"</recordUrl>" +
                        "<byeType>"+byeType+"</byeType>");
            }else{
                str.append("<userData>"+userData+"</userData>");
            }
            str.append( "</" +rootElement+ ">");
        }
        return str.toString();
    }

    private String toAxbXML(String rootElement){
        //虚拟小号挂机回调
        String str = "<?xml version='1.0'?>"+
                "<" +rootElement+ ">"+
                "<action>"+action+"</action>" +
                "<type>"+type+"</type>" +
                "<appId>"+appId+"</appId>" +
                "<caller>"+caller+"</caller>" +
                "<called>"+called+"</called>" +
                "<telX>"+telX+"</telX>" +
                "<startTimeA>"+startTimeA+"</startTimeA>" +
                "<startTimeB>"+startTimeB+"</startTimeB>" +
                "<endTime>"+endTime+"</endTime>" +
                "<duration>"+duration+"</duration>" +
                "<callId>"+callSid+"</callId>"+
                "<userFlag>"+userFlag+"</userFlag>" +
                "<byeType>"+byeType+"</byeType>" +
                "<dateCreated>"+dateCreated+"</dateCreated>"+
                "<userData>"+userData+"</userData>"+
                "<recordUrl>"+recordUrl+"</recordUrl>"+
                "</" +rootElement+ ">";
        return str;
    }

    // 将对象字段转换成Json字符串
    public String toRecordJson(int type) {
        StringBuffer str=new StringBuffer();
        //根据type区分是正常回拨通知，还是隐私通知
        if(type==2){
            //隐私通知
            str.append( "\"action\":\""+action+"\"," +
                    "\"type\":"+type+"," +
                    "\"appId\":\""+appId+"\"," +
                    "\"caller\":\""+caller+"\"," +
                    "\"called\":\""+called+"\"," +
                    "\"maskNumber\":\""+maskNumber+"\",");

            //如果是回呼挂机通知，则组装回呼挂机专有属性
            if("IncomeHangup".equals(action)){
                str.append( "\"dateCreated\":\""+dateCreated+"\"," +
                        "\"startTime\":\""+startTime+"\"," +
                        "\"endTime\":\""+endTime+"\"," +
                        "\"duration\":\""+duration+"\"," +
                        "\"recordUrl\":\""+recordUrl+"\"," +
                        "\"byeType\":\""+byeType+"\",");
            }else{
                str.append( "\"callId\":\""+callSid+"\"," +
                        "\"dateCreated\":\""+dateCreated+"\"," +
                        "\"userData\":\""+userData+"\",");
                //如果是呼叫挂机通知，则组装挂机专有属性
                if("Hangup".equals(action)){
                    str.append( "\"startTimeA\":\""+startTimeA+"\"," +
                            "\"startTimeB\":\""+startTimeB+"\"," +
                            "\"endTime\":\""+endTime+"\"," +
                            "\"duration\":\""+duration+"\"," +
                            "\"recordUrl\":\""+recordUrl+"\"," +
                            "\"byeType\":\""+byeType+"\"");
                }
            }

        }else{
            //正常回拨通知
            str.append( "\"action\":\""+action+"\"," +
                    "\"type\":"+type+"," +
                    "\"appId\":\""+appId+"\"," +
                    //"\"subId\":\""+subId+"\"," +
                    "\"caller\":\""+caller+"\"," +
                    "\"called\":\""+called+"\"," +
                    "\"subType\":"+subType+"," +
                    "\"callId\":\""+callSid+"\"," +
                    "\"dateCreated\":\""+dateCreated+"\"," +
                    "\"userData\":\""+userData+"\",");
            //如果是呼叫挂机通知，则组装挂机专有属性
            if("Hangup".equals(action)){
                str.append( "\"startTimeA\":\""+startTimeA+"\"," +
                        "\"startTimeB\":\""+startTimeB+"\"," +
                        "\"endTime\":\""+endTime+"\"," +
                        "\"duration\":\""+duration+"\"," +
                        "\"recordUrl\":\""+recordUrl+"\"," +
                        "\"byeType\":\""+byeType+"\"");
            }
        }

        return str.toString();
    }

    // 将对象字段转换成XML字符串
    public String toRecordXML(int type) {
        StringBuffer str=new StringBuffer();
        //根据type区分是正常回拨通知，还是隐私通知
        if(type==2){
            //隐私通知
            str.append( "<action>"+action+"</action>" +
                    "<type>"+type+"</type>" +
                    "<appId>"+appId+"</appId>" +
                    "<caller>"+caller+"</caller>" +
                    "<called>"+called+"</called>" +
                    "<maskNumber>"+maskNumber+"</maskNumber>");

            //如果是回呼挂机通知，则组装回呼挂机专有属性
            if("IncomeHangup".equals(action)){
                str.append( "<dateCreated>"+dateCreated+"</dateCreated>" +
                        "<startTime>"+startTime+"</startTime>" +
                        "<endTime>"+endTime+"</endTime>" +
                        "<duration>"+duration+"</duration>" +
                        "<recordUrl>"+recordUrl+"</recordUrl>" +
                        "<byeType>"+byeType+"</byeType>");
            }else{
                str.append( "<callId>"+callSid+"</callId>" +
                        "<dateCreated>"+dateCreated+"</dateCreated>" +
                        "<userData>"+userData+"</userData>");
                //如果是呼叫挂机通知，则组装挂机专有属性
                if("Hangup".equals(action)){
                    str.append( "<startTimeA>"+startTimeA+"</startTimeA>" +
                            "<startTimeB>"+startTimeB+"</startTimeB>" +
                            "<endTime>"+endTime+"</endTime>" +
                            "<duration>"+duration+"</duration>" +
                            "<recordUrl>"+recordUrl+"</recordUrl>" +
                            "<byeType>"+byeType+"</byeType>");
                }
            }

        }else{
            //正常回拨通知
            str.append( "<action>"+action+"</action>" +
                    "<type>"+type+"</type>" +
                    "<appId>"+appId+"</appId>" +
                    //"<subId>"+subId+"</subId>" +
                    "<caller>"+caller+"</caller>" +
                    "<called>"+called+"</called>");
            str.append( "<subType>"+subType+"</subType>" +
                    "<callId>"+callSid+"</callId>" +
                    "<dateCreated>"+dateCreated+"</dateCreated>" +
                    "<userData>"+userData+"</userData>");
            //如果是呼叫挂机通知，则组装挂机专有属性
            if("Hangup".equals(action)){
                str.append( "<startTimeA>"+startTimeA+"</startTimeA>" +
                        "<startTimeB>"+startTimeB+"</startTimeB>" +
                        "<endTime>"+endTime+"</endTime>" +
                        "<duration>"+duration+"</duration>" +
                        "<recordUrl>"+recordUrl+"</recordUrl>" +
                        "<byeType>"+byeType+"</byeType>");
            }
        }

        return str.toString();
    }

    // 将对象字段转换成Json/Xml字符串
    public String toVoiceRecord(boolean isJson) {
        StringBuffer str=new StringBuffer();
        //根据type区分是正常回拨通知，还是隐私通知
        if (isJson) {
            //隐私通知
            str.append( "\"action\":\""+action+"\"," +
                    "\"dtmfFlag\":"+dtmfFlag+"," +
                    "\"dtmfNum\":\""+dtmfNum+"\"," +
                    "\"orderFlag\":"+orderFlag+"," +
                    "\"orderTime\":\""+orderTime+"\"," +
                    "\"callTime\":\""+callTime+"\"," +
                    "\"requestId\":\""+callSid+"\"," +
                    "\"to\":\""+to+"\"," +
                    "\"byeType\":\""+byeType+"\"," +
                    "\"displayNum\":\""+displayNum+"\"");
        }else{
            str.append( "<action>"+action+"</action>" +
                    "<dtmfFlag>"+dtmfFlag+"</dtmfFlag>" +
                    "<dtmfNum>"+dtmfNum+"</dtmfNum>" +
                    "<orderFlag>"+orderFlag+"</orderFlag>" +
                    "<orderTime>"+orderTime+"</orderTime>" +
                    "<callTime>"+callTime+"</callTime>" +
                    "<requestId>"+callSid+"</requestId>" +
                    "<to>"+to+"</to>" +
                    "<byeType>"+byeType+"</byeType>" +
                    "<displayNum>"+displayNum+"</displayNum>");

        }

        return str.toString();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public int getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(int userFlag) {
        this.userFlag = userFlag;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getStartTimeA() {
        return startTimeA;
    }

    public void setStartTimeA(String startTimeA) {
        this.startTimeA = startTimeA;
    }

    public String getStartTimeB() {
        return startTimeB;
    }

    public void setStartTimeB(String startTimeB) {
        this.startTimeB = startTimeB;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getByeType() {
        return byeType;
    }

    public void setByeType(String byeType) {
        this.byeType = byeType;
    }

    public String getMaskNumber() {
        return maskNumber;
    }

    public void setMaskNumber(String maskNumber) {
        this.maskNumber = maskNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDtmfNumber() {
        return dtmfNumber;
    }

    public void setDtmfNumber(String dtmfNumber) {
        this.dtmfNumber = dtmfNumber;
    }

    public int getDtmfFlag() {
        return dtmfFlag;
    }

    public void setDtmfFlag(int dtmfFlag) {
        this.dtmfFlag = dtmfFlag;
    }

    public String getDtmfNum() {
        return dtmfNum;
    }

    public void setDtmfNum(String dtmfNum) {
        this.dtmfNum = dtmfNum;
    }

    public int getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(int orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(String displayNum) {
        this.displayNum = displayNum;
    }

    public String getVoiceRecId() {
        return voiceRecId;
    }

    public void setVoiceRecId(String voiceRecId) {
        this.voiceRecId = voiceRecId;
    }

    public Date getCreateDateTime() {
        if(createDateTime == null){
            createDateTime = new Date();
        }
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getTelX() {
        return telX;
    }

    public void setTelX(String telX) {
        this.telX = telX;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getFeeUrl() {
        return feeUrl;
    }

    public void setFeeUrl(String feeUrl) {
        this.feeUrl = feeUrl;
    }
}
