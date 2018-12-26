package com.e9cloud.mybatis.domain;

import java.util.Date;

// Rest接口请求失败原因
public class StatRestResponse {

    //
    private Integer id;

    // 统计时间,精确到分钟
    private Date statTime;

    // 响应码
    private String respCode;

    // 响应次数
    private Integer respCnt;

    // 生成时间
    private Date ctime;

    ////////////////////// 以下是setter和getter方法 /////////////////////////

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