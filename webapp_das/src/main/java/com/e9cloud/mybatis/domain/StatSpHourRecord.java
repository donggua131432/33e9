package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

// 云话机 小时统计表 stat_sp_hour_record
public class StatSpHourRecord {

    // 主键 自增
    private Integer id;

    // 统计时间
    private Date stathour;

    // 通话记录类型
    private String rcdtype;

    // AB路
    private String abline;

    // A端号码是否sipphone
    private Boolean sipa;

    // B端号码是否sipphone
    private Boolean sipb;

    // 账户id
    private String sid;

    // 应用id
    private String feeid;

    // 应用id
    private String appid;

    // 子帐号id
    private String subid;

    // 呼叫总数
    private Integer callcnt;

    // 呼叫成功数
    private Integer succcnt;

    // 呼叫应答数
    private Integer answercnt;

    // 总通话时长（秒）
    private Integer thscsum;

    // 总计费时长（分钟）
    private Integer jfscsum;

    // 总录音时长（秒）
    private Integer lyscsum;

    // 计费录音时长
    private Integer jflyscsum;

    // 总话费
    private BigDecimal fee;

    // 总录音费
    private BigDecimal recordingfee;

    //
    private Date ctime;

    ////////////////////////以下是setter和getter方法/////////////////////////////

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

    public String getRcdtype() {
        return rcdtype;
    }

    public void setRcdtype(String rcdtype) {
        this.rcdtype = rcdtype == null ? null : rcdtype.trim();
    }

    public String getAbline() {
        return abline;
    }

    public void setAbline(String abline) {
        this.abline = abline == null ? null : abline.trim();
    }

    public Boolean getSipa() {
        return sipa;
    }

    public void setSipa(Boolean sipa) {
        this.sipa = sipa;
    }

    public Boolean getSipb() {
        return sipb;
    }

    public void setSipb(Boolean sipb) {
        this.sipb = sipb;
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

    public Integer getLyscsum() {
        return lyscsum;
    }

    public void setLyscsum(Integer lyscsum) {
        this.lyscsum = lyscsum;
    }

    public Integer getJflyscsum() {
        return jflyscsum;
    }

    public void setJflyscsum(Integer jflyscsum) {
        this.jflyscsum = jflyscsum;
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