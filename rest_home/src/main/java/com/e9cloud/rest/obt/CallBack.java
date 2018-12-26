package com.e9cloud.rest.obt;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CallBack
{
    private  String appId ;//应用ID
    private  String subId;//子账号ID
    private  String from;//主叫电话号码
    private  String to ;//被叫电话号码
    private List<String> fromSerNum;//主叫侧显示号码列表 [0001, 0002]
    private  List<String> toSerNum;//被叫侧显示号码列表 [0001, 0002]
    private  int maxCallTime ;//通话的最大时长,单位为秒
    private  int needRecord;//是否录音：0表示不录音；1表示录音；默认值0
    private  int countDownTime;//设置倒计时时间，单位秒，只能选择5秒、30秒或60秒，默认值为空
    private  String userData ;//用户自定义数据
    //CB需要 扩增字段
    private String callSid;
    private String accountSid;
    private String recordFlag;//录音标识 A:A路接听开始录音   B:B路接听开始录音
    private String codec; //语音编码

    //rest调度字段
    private String rn_a;
    //rest调度字段
    private String rn_b;



    private String respCode;//信息
    private Date dateCreated;//数据入库时间
    private Date reqDate;
    private String accept;//数据响应类型： xml、json
    private String reqType;//接口类型

    //将对象字段转换成XML字符串
    public String toXML() {
        return "<appId>"+appId+"</appId>" +
                "<subId>"+subId+"</subId>" +
                "<from>"+from+"</from>" +
                "<to>"+to+"</to>" +
                "<fromSerNum>"+fromSerNum+"</fromSerNum>" +
                "<toSerNum>"+toSerNum+"</toSerNum>" +
                "<maxCallTime>"+maxCallTime+"</maxCallTime>" +
                "<needRecord>"+needRecord+"</needRecord>" +
                "<countDownTime>"+countDownTime+"</countDownTime>" +
                "<userData>"+userData+"</userData>" +
                "<callSid>"+callSid+"</callSid>"+
                "<accountSid>"+accountSid+"</accountSid>"+
                "<rnA>"+rn_a+"</rnA>"+
                "<rnB>"+rn_b+"</rnB>"
                ;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<String> getFromSerNum() {
        return fromSerNum;
    }

    public void setFromSerNum(List<String> fromSerNum) {
        this.fromSerNum = fromSerNum;
    }

    public List<String> getToSerNum() {
        return toSerNum;
    }

    public void setToSerNum(List<String> toSerNum) {
        this.toSerNum = toSerNum;
    }

    public int getMaxCallTime() {
        return maxCallTime;
    }

    public void setMaxCallTime(int maxCallTime) {
        this.maxCallTime = maxCallTime;
    }

    public int getNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(int needRecord) {
        this.needRecord = needRecord;
    }

    public int getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(int countDownTime) {
        this.countDownTime = countDownTime;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getRn_a() {
        return rn_a;
    }

    public String getRn_b() {
        return rn_b;
    }

    public void setRn_a(String rn_a) {
        this.rn_a = rn_a;
    }

    public void setRn_b(String rn_b) {
        this.rn_b = rn_b;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
}
