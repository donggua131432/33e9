package com.e9cloud.mongodb.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wzj on 2017/4/19.
 */
public class MaskBRecord implements Serializable {

    private static final long serialVersionUID = 9097246183310032343L;

    private String appid ; //应用ID
    private String feeid ; //费率ID
    private String sid ; //Account ID

    private String telA ; // A路
    private String telB ; // B路
    private String telX ; // 虚拟小号
    private String callid ; //通话记录ID
    private String calltype ; //AXB 业务：（针对被保护号码A 来分）10：通话主叫: 11：通话被叫 20：呼叫不允许
    private String releasecause; //第三方错误码
    private String releasedir ; //释放方向1 表示主叫，2 表示被叫，0 表示平台释放

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date calltime ; // 发起呼叫时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ringingtime ; // 振铃开始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime; // 通话开始时间(被叫接听时间)

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releasetime; // 通话结束时

    private Integer connectSucc; //接通成功状态 0未接通，1接通

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qssj = new Date(); //起始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jssj = new Date(); //结束时间

    private Integer thsc ; //通话时长
    private Integer jfsc; //计费时长
    private Double fee; //通话费用

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date calltime1;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //////////////////////////////////////////////////////////


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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTelA() {
        return telA;
    }

    public void setTelA(String telA) {
        this.telA = telA;
    }

    public String getTelB() {
        return telB;
    }

    public void setTelB(String telB) {
        this.telB = telB;
    }

    public String getTelX() {
        return telX;
    }

    public void setTelX(String telX) {
        this.telX = telX;
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }

    public Date getCalltime() {
        return calltime;
    }

    public void setCalltime(Date calltime) {
        this.calltime = calltime;
    }

    public Date getRingingtime() {
        return ringingtime;
    }

    public void setRingingtime(Date ringingtime) {
        this.ringingtime = ringingtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(Date releasetime) {
        this.releasetime = releasetime;
    }

    public Integer getConnectSucc() {return connectSucc;}

    public void setConnectSucc(Integer connectSucc) {this.connectSucc = connectSucc;}

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

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Date getCalltime1() {
        return calltime1;
    }

    public void setCalltime1(Date calltime1) {
        this.calltime1 = calltime1;
    }

    public String getReleasecause() {return releasecause;}

    public void setReleasecause(String releasecause) {this.releasecause = releasecause;}

    public String getReleasedir() {
        return releasedir;
    }

    public void setReleasedir(String releasedir) {
        this.releasedir = releasedir;
    }
}
