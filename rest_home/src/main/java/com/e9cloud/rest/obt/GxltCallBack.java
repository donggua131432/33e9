package com.e9cloud.rest.obt;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/21.
 */
public class GxltCallBack {
    private String appId;  //应用ID
    private String requestId;  //业务id
    private String telA; //真实号码
    private String telX; //小号号码
    private String telB; //对端号码
    private String subid; //绑定id
    private String calltype; //呼叫类型
    private String calltime; //发起呼叫时间 = 主叫发起时间
    private String ringingtime; //振铃开始时间
    private String starttime; //通话开始时间 = 被叫接听时间
    private String releasetime; //通话结束时间
    private String callid; //通话标识
    private String releasedir; //释放方向 = 挂机方向
    private String releasecause; //释放原因 = 挂机类型
    private String callrecording; //录音控制
    private String recordUrl; //录音地址
    private String recordMode; //录音模式

    private String userData; //用户自定义数据

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTelA() {
        return telA;
    }

    public void setTelA(String telA) {
        this.telA = telA;
    }

    public String getTelX() {
        return telX;
    }

    public void setTelX(String telX) {
        this.telX = telX;
    }

    public String getTelB() {
        return telB;
    }

    public void setTelB(String telB) {
        this.telB = telB;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }

    public String getCalltime() {
        return calltime;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }

    public String getRingingtime() {
        return ringingtime;
    }

    public void setRingingtime(String ringingtime) {
        this.ringingtime = ringingtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public String getReleasedir() {
        return releasedir;
    }

    public void setReleasedir(String releasedir) {
        this.releasedir = releasedir;
    }

    public String getReleasecause() {
        return releasecause;
    }

    public void setReleasecause(String releasecause) {
        this.releasecause = releasecause;
    }

    public String getCallrecording() {
        return callrecording;
    }

    public void setCallrecording(String callrecording) {
        this.callrecording = callrecording;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getRecordMode() {
        return recordMode;
    }

    public void setRecordMode(String recordMode) {
        this.recordMode = recordMode;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }
}
