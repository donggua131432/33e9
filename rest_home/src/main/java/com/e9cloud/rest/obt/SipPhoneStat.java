package com.e9cloud.rest.obt;

/**
 * Created by dukai on 2016/12/23.
 */
public class SipPhoneStat {

    private String appId; //应用ID
    private String sipPhoneNumber; //sipPhone号码
    private String longDistanceFlag; //长途禁止开关
    private String callSwitchFlag; //呼叫禁止开关

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSipPhoneNumber() {
        return sipPhoneNumber;
    }

    public void setSipPhoneNumber(String sipPhoneNumber) {
        this.sipPhoneNumber = sipPhoneNumber;
    }

    public String getLongDistanceFlag() {
        return longDistanceFlag;
    }

    public void setLongDistanceFlag(String longDistanceFlag) {
        this.longDistanceFlag = longDistanceFlag;
    }

    public String getCallSwitchFlag() {
        return callSwitchFlag;
    }

    public void setCallSwitchFlag(String callSwitchFlag) {
        this.callSwitchFlag = callSwitchFlag;
    }
}
