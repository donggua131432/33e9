package com.e9cloud.mongodb.domain;

import java.util.Date;
import java.util.Map;

/**
 * Created by dukai on 2016/11/29.
 */
public class RestReqResInfo {

    private String sid; //应用ID
    private String reqType;//接口类型
    private String reqName; //接口名称
    private String accept;//数据响应类型： xml、json
    private String reqUrl;//请求地址
    private String reqInfo; //请求信息

    private String respCode;//响应码
    private Date dateCreated;//数据入库时间
    private Map respInfo; //响应信息

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(String reqInfo) {
        this.reqInfo = reqInfo;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Map getRespInfo() {
        return respInfo;
    }

    public void setRespInfo(Map respInfo) {
        this.respInfo = respInfo;
    }
}
