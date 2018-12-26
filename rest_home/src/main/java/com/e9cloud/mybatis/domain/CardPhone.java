package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CardPhone implements Serializable {

    private int id;//主键id
    private String card;//物联网卡号
    private String phone;//手机号
    private Date createTime;//创建时间

    // 扩增字段
    private String accountSid;
    private String appId;
    private List<CardPhone> cardPhones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<CardPhone> getCardPhones() {
        return cardPhones;
    }

    public void setCardPhones(List<CardPhone> cardPhones) {
        this.cardPhones = cardPhones;
    }
}