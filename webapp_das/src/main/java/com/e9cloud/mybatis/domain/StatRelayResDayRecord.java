package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

// 中继资源话务统计
public class StatRelayResDayRecord {

    //
    private Integer id;

    // 日期
    private Date statday;

    // accountSid
    private String sid;

    // 业务表名称
    private String collection;

    // 分路
    private String abline;

    // 中继编号
    private String relayNum;

    // 资源id
    private Integer resId;

    // 资源供应商id
    private String resSupid;

    // 被叫运营商
    private String operator;

    // 呼叫类型:00本地,01长途
    private String callType;

    // 资源号码归属地
    private String areaCode;

    // 被叫号码类型:00手机,01固话,02400电话,03955电话
    private String numType;

    // 价格
    private BigDecimal price;

    // 计费周期
    private Integer cycle;

    //
    private Integer callCnt;

    //
    private Integer succCnt;

    //
    private Integer answerCnt;

    // 计费总时长(分)
    private Integer jfscSum;

    // 通话总时长(秒)
    private Integer thscSum;

    // 成本计费次数
    private Integer cycleTimes;

    // 成本费用
    private BigDecimal costFee;

    //
    private Date ctime;

    //////////////////////////////////////// 以下是setter和getter方法 //////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStatday() {
        return statday;
    }

    public void setStatday(Date statday) {
        this.statday = statday;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection == null ? null : collection.trim();
    }

    public String getAbline() {
        return abline;
    }

    public void setAbline(String abline) {
        this.abline = abline == null ? null : abline.trim();
    }

    public String getRelayNum() {
        return relayNum;
    }

    public void setRelayNum(String relayNum) {
        this.relayNum = relayNum == null ? null : relayNum.trim();
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getResSupid() {
        return resSupid;
    }

    public void setResSupid(String resSupid) {
        this.resSupid = resSupid == null ? null : resSupid.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType == null ? null : callType.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getNumType() {
        return numType;
    }

    public void setNumType(String numType) {
        this.numType = numType == null ? null : numType.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(Integer callCnt) {
        this.callCnt = callCnt;
    }

    public Integer getSuccCnt() {
        return succCnt;
    }

    public void setSuccCnt(Integer succCnt) {
        this.succCnt = succCnt;
    }

    public Integer getAnswerCnt() {
        return answerCnt;
    }

    public void setAnswerCnt(Integer answerCnt) {
        this.answerCnt = answerCnt;
    }

    public Integer getJfscSum() {
        return jfscSum;
    }

    public void setJfscSum(Integer jfscSum) {
        this.jfscSum = jfscSum;
    }

    public Integer getThscSum() {
        return thscSum;
    }

    public void setThscSum(Integer thscSum) {
        this.thscSum = thscSum;
    }

    public Integer getCycleTimes() {
        return cycleTimes;
    }

    public void setCycleTimes(Integer cycleTimes) {
        this.cycleTimes = cycleTimes;
    }

    public BigDecimal getCostFee() {
        return costFee;
    }

    public void setCostFee(BigDecimal costFee) {
        this.costFee = costFee;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}