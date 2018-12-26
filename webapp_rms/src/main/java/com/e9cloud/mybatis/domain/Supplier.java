package com.e9cloud.mybatis.domain;

import java.util.Date;

// 中继供应商管理
public class Supplier {

    // 供应商 ID(系统自动生成一串字符，全网唯一)
    private String supId;

    // 供应商名称
    private String supName;

    // 供应商联系人
    private String contacts;

    // 供应商地址
    private String address;

    // 结算方式: 00 预付/ 01月结
    private String payType;

    // 保证金:”1:是、0:否“”两种类型
    private String hasBond;

    // 银行信息:开户名称
    private String bankAccName;

    // 银行信息:收款账号
    private String bankAcdRecipient;

    // 银行信息:银行账号
    private String bankAccNum;

    // 添加时间：默认系统时间
    private Date atime;

    // 联系方式
    private String mobile;

    ////////////////////////////////// setter和getter方法 ///////////////////////////////////

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId == null ? null : supId.trim();
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName == null ? null : supName.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getHasBond() {
        return hasBond;
    }

    public void setHasBond(String hasBond) {
        this.hasBond = hasBond == null ? null : hasBond.trim();
    }

    public String getBankAccName() {
        return bankAccName;
    }

    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName == null ? null : bankAccName.trim();
    }

    public String getBankAcdRecipient() {
        return bankAcdRecipient;
    }

    public void setBankAcdRecipient(String bankAcdRecipient) {
        this.bankAcdRecipient = bankAcdRecipient == null ? null : bankAcdRecipient.trim();
    }

    public String getBankAccNum() {
        return bankAccNum;
    }

    public void setBankAccNum(String bankAccNum) {
        this.bankAccNum = bankAccNum == null ? null : bankAccNum.trim();
    }

    public Date getAtime() {
        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }
}