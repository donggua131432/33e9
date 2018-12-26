package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户管理主账号信息
 *
 * @author Created by Dukai on 2016/2/2.
 */


public class UserAdmin implements Serializable{

    private static final long serialVersionUID = -832470463759995964L;

    private String uid;
    private String sid;
    private String feeid;
    private BigDecimal fee;
    private String callNo;
    private String email;
    private String mobile;

    /** 业务类型 */
    private String utype;
    private String pwd;
    private String salt;
    private String nick;
    private String name;
    private String status;

    /** 认证状态 */
    private String authStatus;

    /** 验签标识 */
    private String token;

    /** 业务类型 */
    private String busType;

    private String busTypes;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loginDate;

    private Integer loginCount;


    /** 短信提醒----阈值余额 */
    private BigDecimal remindFee;

    /** 短信提醒的手机号 */
    private String smsMobile;

    private  String limitFlag;

    private  String smsStatus;

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }
    public String getLimitFlag() {
        return limitFlag;
    }

    public void setLimitFlag(String limitFlag) {
        this.limitFlag = limitFlag;
    }

    public String getSmsMobile() {
        return smsMobile;
    }

    public void setSmsMobile(String smsMobile) {
        this.smsMobile = smsMobile;
    }

    public BigDecimal getRemindFee() {
        return remindFee;
    }

    public void setRemindFee(BigDecimal remindFee) {
        this.remindFee = remindFee;
    }

    private AuthenCompany authenCompany;

    private UserPerson userPerson;

    public UserPerson getUserPerson() {
        return userPerson;
    }

    public void setUserPerson(UserPerson userPerson) {
        this.userPerson = userPerson;
    }

    public AuthenCompany getAuthenCompany() {
        return authenCompany;
    }

    public void setAuthenCompany(AuthenCompany authenCompany) {
        this.authenCompany = authenCompany;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo == null ? null : callNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype == null ? null : utype.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusTypes() {
        return busTypes;
    }

    public void setBusTypes(String busTypes) {
        this.busTypes = busTypes;
    }
}