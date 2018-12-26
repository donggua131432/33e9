package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 呼叫中心信息表
 *
 * @author Created by pengchunchen on 2016/8/5.
 */
public class CcInfo implements Serializable {

    private Integer id;
    private String sid;//用户的accountid
    private String appid;//appid
    private String subid;//子帐号id
    private String ccname;//呼叫中心名称
    private String relay1;//中继1
    private String relay2;//中继2
    private String lifeRelay;//逃生中继
    private Integer ccoMaxNum;//最大话务员数量
    private String dtmfNum;//DTMF收号
    private Integer defaultCc;//是否缺省调度 1 是 0 否
    private String status;//状态 00:正常 01:禁用
    private String remark;//备注
    private Date ctime;

    private String relay3;//中继3

    private String relay4;//中继4

    private String relay5;//中继5

    private Integer weight1;//权重1

    private Integer weight2;//权重2

    private Integer weight3;//权重3

    private Integer weight4;//权重4

    private Integer weight5;//权重5

    //扩展字段
    private String name;//客户名称
    private String appName;//应用名称
    private String relayName1;//中继群名称1
    private String relayName2;//中继群名称2
    private String relayName3;//中继群名称3
    private String relayName4;//中继群名称4
    private String relayName5;//中继群名称5

    public String getRelayName3() {
        return relayName3;
    }

    public void setRelayName3(String relayName3) {
        this.relayName3 = relayName3;
    }

    public String getRelayName4() {
        return relayName4;
    }

    public void setRelayName4(String relayName4) {
        this.relayName4 = relayName4;
    }

    public String getRelayName5() {
        return relayName5;
    }

    public void setRelayName5(String relayName5) {
        this.relayName5 = relayName5;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getCcname() {
        return ccname;
    }

    public void setCcname(String ccname) {
        this.ccname = ccname;
    }

    public String getRelay1() {
        return relay1;
    }

    public void setRelay1(String relay1) {
        this.relay1 = relay1;
    }

    public String getRelay2() {
        return relay2;
    }

    public void setRelay2(String relay2) {
        this.relay2 = relay2;
    }

    public String getLifeRelay() {
        return lifeRelay;
    }

    public void setLifeRelay(String lifeRelay) {
        this.lifeRelay = lifeRelay;
    }

    public Integer getCcoMaxNum() {
        return ccoMaxNum;
    }

    public void setCcoMaxNum(Integer ccoMaxNum) {
        this.ccoMaxNum = ccoMaxNum;
    }

    public String getDtmfNum() {
        return dtmfNum;
    }

    public void setDtmfNum(String dtmfNum) {
        this.dtmfNum = dtmfNum;
    }

    public Integer getDefaultCc() {
        return defaultCc;
    }

    public void setDefaultCc(Integer defaultCc) {
        this.defaultCc = defaultCc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRelayName1() {
        return relayName1;
    }

    public void setRelayName1(String relayName1) {
        this.relayName1 = relayName1;
    }

    public String getRelayName2() {
        return relayName2;
    }

    public void setRelayName2(String relayName2) {
        this.relayName2 = relayName2;
    }

    public String getRelay3() {
        return relay3;
    }

    public void setRelay3(String relay3) {
        this.relay3 = relay3;
    }

    public String getRelay4() {
        return relay4;
    }

    public void setRelay4(String relay4) {
        this.relay4 = relay4;
    }

    public String getRelay5() {
        return relay5;
    }

    public void setRelay5(String relay5) {
        this.relay5 = relay5;
    }

    public Integer getWeight1() {
        return weight1;
    }

    public void setWeight1(Integer weight1) {
        this.weight1 = weight1;
    }

    public Integer getWeight2() {
        return weight2;
    }

    public void setWeight2(Integer weight2) {
        this.weight2 = weight2;
    }

    public Integer getWeight3() {
        return weight3;
    }

    public void setWeight3(Integer weight3) {
        this.weight3 = weight3;
    }

    public Integer getWeight4() {
        return weight4;
    }

    public void setWeight4(Integer weight4) {
        this.weight4 = weight4;
    }

    public Integer getWeight5() {
        return weight5;
    }

    public void setWeight5(Integer weight5) {
        this.weight5 = weight5;
    }
}