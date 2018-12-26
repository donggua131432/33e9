package com.e9cloud.mongodb.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dukai on 2016/5/31.
 */
public class MaskRecord implements Serializable{

    private static final long serialVersionUID = 9097246183310032343L;

    /**
     * 如果是回拨REST话单，下列属性要赋值
     */

    private String abLine; //呼入呼出(A路B路)
    private String appid; //应用ID
    private String feeid; //费率ID
    private String rcdLine;
    private String subid; //子账号ID
    private String sid; //Account ID
    private String display; //显示号码
    private String aTelno; //A路主叫号码(REST主叫 9)
    private String bTelno; //B路被叫号码(REST被叫 17)
    private String url; //录音文件存放地址
    private Integer cdrRecordStart; //CDR文件解析起始位置
    private String callid; //通话记录ID
    private String callSid; //通话记录CALLSID(AB路一致)
    private Integer callType; //呼叫类型
    private Integer status;  //接通状态
    private Double fee; //通话费用
    private Double recordingFee; //录音费用
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qssj; //起始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jssj; //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jssj1;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closureTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closureTime1;
    /*记录时间,用来做各种查询统计的时间条件
    * 由于话单有可能既没有开始时间也没有结束时间，需要一个时间属性做统一处理
    * 计划采用系统处理话单的时间,若采用回话发起时间则有可能出现跨天的情况，若超出了系统做日统计或小时统计的时间，则会出现遗漏
    * 故记录时间暂用话单采集的时间，话单时间点归类到这个时间
    * 待IMS到位后，分析下是否所有话单都有recordClosureTime，有的话就采用这个时间
    * */

    private Integer thsc; //通话时长
    private Integer thsc1;
    private Integer jfsc; //计费时长
    private Boolean connectStatus; //接通状态
    private Integer connectSucc; //接通成功状态
    private Integer connectFalse; //接通失败状态

    private String pname; //省
    private String cname; //市

    private String zjCname; //主叫城市
    private String zjPname; //主叫省份
    private String zjOperator; //主叫运营商
    private String bjCname; //被叫城市
    private String bjPname; //被叫省份
    private String bjOperator; //被叫运营商
    private String displayCname; //显号城市
    private String displayPname;  //显号省份
    private String displayOperator; //显号运营商

    private String displayPoolCity; //显号池城市

    public String getRcdLine() {
        return rcdLine;
    }

    public void setRcdLine(String rcdLine) {
        this.rcdLine = rcdLine;
    }


    public String getAbLine() {
        return abLine;
    }

    public void setAbLine(String abLine) {
        this.abLine = abLine;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getaTelno() {
        return aTelno;
    }

    public void setaTelno(String aTelno) {
        this.aTelno = aTelno;
    }

    public String getbTelno() {
        return bTelno;
    }

    public void setbTelno(String bTelno) {
        this.bTelno = bTelno;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCdrRecordStart() {
        return cdrRecordStart;
    }

    public void setCdrRecordStart(Integer cdrRecordStart) {
        this.cdrRecordStart = cdrRecordStart;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(Double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public Date getQssj() {
        return qssj;
    }

    public void setQssj(Date qssj) {
        this.qssj = qssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public Integer getThsc() {
        return thsc;
    }

    public void setThsc(Integer thsc) {
        this.thsc = thsc;
    }

    public Integer getJfsc() {
        return jfsc;
    }

    public void setJfsc(Integer jfsc) {
        this.jfsc = jfsc;
    }

    public Boolean getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(Boolean connectStatus) {
        this.connectStatus = connectStatus;
    }

    public Integer getConnectSucc() {
        return connectSucc;
    }

    public void setConnectSucc(Integer connectSucc) {
        this.connectSucc = connectSucc;
    }

    public Integer getConnectFalse() {
        return connectFalse;
    }

    public void setConnectFalse(Integer connectFalse) {
        this.connectFalse = connectFalse;
    }

    public Date getJssj1() {
        return jssj1;
    }

    public void setJssj1(Date jssj1) {
        this.jssj1 = jssj1;
    }

    public Integer getThsc1() {
        return thsc1;
    }

    public void setThsc1(Integer thsc1) {
        this.thsc1 = thsc1;
    }

    public Date getClosureTime() {
        return closureTime;
    }

    public void setClosureTime(Date closureTime) {
        this.closureTime = closureTime;
    }

    public Date getClosureTime1() {
        return closureTime1;
    }

    public void setClosureTime1(Date closureTime1) {
        this.closureTime1 = closureTime1;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getZjCname() {
        return zjCname;
    }

    public void setZjCname(String zjCname) {
        this.zjCname = zjCname;
    }

    public String getZjPname() {
        return zjPname;
    }

    public void setZjPname(String zjPname) {
        this.zjPname = zjPname;
    }

    public String getZjOperator() {
        return zjOperator;
    }

    public void setZjOperator(String zjOperator) {
        this.zjOperator = zjOperator;
    }

    public String getBjCname() {
        return bjCname;
    }

    public void setBjCname(String bjCname) {
        this.bjCname = bjCname;
    }

    public String getBjPname() {
        return bjPname;
    }

    public void setBjPname(String bjPname) {
        this.bjPname = bjPname;
    }

    public String getBjOperator() {
        return bjOperator;
    }

    public void setBjOperator(String bjOperator) {
        this.bjOperator = bjOperator;
    }

    public String getDisplayCname() {
        return displayCname;
    }

    public void setDisplayCname(String displayCname) {
        this.displayCname = displayCname;
    }

    public String getDisplayPname() {
        return displayPname;
    }

    public void setDisplayPname(String displayPname) {
        this.displayPname = displayPname;
    }

    public String getDisplayOperator() {
        return displayOperator;
    }

    public void setDisplayOperator(String displayOperator) {
        this.displayOperator = displayOperator;
    }

    public String getDisplayPoolCity() {
        return displayPoolCity;
    }

    public void setDisplayPoolCity(String displayPoolCity) {
        this.displayPoolCity = displayPoolCity;
    }
}
