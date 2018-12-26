package com.e9cloud.mybatis.domain;


/**
 * 全局、子账号号码池 by hzd/20160921
 */
public class SipNumPool {
    private String number;
    private String companyName;
    private String accountId;
    private String type;
    private String subName;
    private String createTime;
    private String appid;
    private String subid;

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getSubid() {

        return subid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppid() {

        return appid;
    }

    public String getNumber() {
        return number;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public String getSubName() {
        return subName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}