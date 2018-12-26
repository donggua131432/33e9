package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StatCcHourRecord implements Serializable{

    private static final long serialVersionUID = -7438121192401479237L;

    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stathour;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stathour1;

    private String feeid;

    private String appid;

    private String subid;

    private String abline;

    private String operator;

    private String pname;

    private String cname;

    private Integer callcnt;

    private Integer succcnt;

    private Integer answercnt;

    private Integer thscsum;

    private Integer jfscsum;

    private BigDecimal fee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStathour() {
        return stathour;
    }

    public void setStathour(Date stathour) {
        this.stathour = stathour;
    }

    public Date getStathour1() {
        return stathour1;
    }

    public void setStathour1(Date stathour1) {
        this.stathour1 = stathour1;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid == null ? null : subid.trim();
    }

    public String getAbline() {
        return abline;
    }

    public void setAbline(String abline) {
        this.abline = abline == null ? null : abline.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public Integer getCallcnt() {
        return callcnt;
    }

    public void setCallcnt(Integer callcnt) {
        this.callcnt = callcnt;
    }

    public Integer getSucccnt() {
        return succcnt;
    }

    public void setSucccnt(Integer succcnt) {
        this.succcnt = succcnt;
    }

    public Integer getAnswercnt() {
        return answercnt;
    }

    public void setAnswercnt(Integer answercnt) {
        this.answercnt = answercnt;
    }

    public Integer getThscsum() {
        return thscsum;
    }

    public void setThscsum(Integer thscsum) {
        this.thscsum = thscsum;
    }

    public Integer getJfscsum() {
        return jfscsum;
    }

    public void setJfscsum(Integer jfscsum) {
        this.jfscsum = jfscsum;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}