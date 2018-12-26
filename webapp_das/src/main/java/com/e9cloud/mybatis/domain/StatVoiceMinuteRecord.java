package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

// 语音通知 分钟统计表  stat_voice_minute_record
public class StatVoiceMinuteRecord {

    // 主键 自增
    private Integer id;

    // 统计时间
    private Date statmin;

    // 计费ID
    private String feeid;

    // 应用ID
    private String appid;

    // 呼叫总数
    private Integer callcnt;

    // 有效呼叫数
    private Integer validcnt;

    // 接通数
    private Integer succcnt;

    // 应答数
    private Integer answercnt;

    // 试呼次数
    private Integer trysum;

    // 通话时长
    private Integer thscsum;

    // 计费时长
    private Integer jfscsum;

    // 通话费
    private BigDecimal fee;

    // 录音费
    private BigDecimal recordingfee;

    //
    private Date ctime;

    /////////////////////以下是setter和getter方法////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStatmin() {
        return statmin;
    }

    public void setStatmin(Date statmin) {
        this.statmin = statmin;
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

    public Integer getCallcnt() {
        return callcnt;
    }

    public void setCallcnt(Integer callcnt) {
        this.callcnt = callcnt;
    }

    public Integer getValidcnt() {
        return validcnt;
    }

    public void setValidcnt(Integer validcnt) {
        this.validcnt = validcnt;
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

    public Integer getTrysum() {
        return trysum;
    }

    public void setTrysum(Integer trysum) {
        this.trysum = trysum;
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

    public BigDecimal getRecordingfee() {
        return recordingfee;
    }

    public void setRecordingfee(BigDecimal recordingfee) {
        this.recordingfee = recordingfee;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}