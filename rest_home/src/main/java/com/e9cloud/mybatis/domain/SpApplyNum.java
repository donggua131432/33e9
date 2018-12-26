package com.e9cloud.mybatis.domain;


public class SpApplyNum {

    private Long id; //主键
    private String accountSid; //账户ID
    private String appid; //应用ID
    private String sipPhone; //sipPhone号码
    private String showNum; //外显号
    private String number; //关联落地固话
    private String longDistanceFlag; //长途禁止开关
    private String callSwitchFlag; //呼叫禁止开关

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSipPhone() {
        return sipPhone;
    }

    public void setSipPhone(String sipPhone) {
        this.sipPhone = sipPhone;
    }

    public String getShowNum() {
        return showNum;
    }

    public void setShowNum(String showNum) {
        this.showNum = showNum;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpApplyNum that = (SpApplyNum) o;

        return sipPhone.equals(that.sipPhone);

    }

    @Override
    public int hashCode() {
        return sipPhone.hashCode();
    }
}