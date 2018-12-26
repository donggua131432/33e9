package com.e9cloud.mybatis.domain;

import java.util.Date;

public class StatApiResponse {
    private Integer id;

    private Date statTime;

    private String reqType;

    private String reqName;

    private String respCode;

    private Integer respCnt;

    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStatTime() {
        return statTime;
    }

    public void setStatTime(Date statTime) {
        this.statTime = statTime;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType == null ? null : reqType.trim();
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName == null ? null : reqName.trim();
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode == null ? null : respCode.trim();
    }

    public Integer getRespCnt() {
        return respCnt;
    }

    public void setRespCnt(Integer respCnt) {
        this.respCnt = respCnt;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}