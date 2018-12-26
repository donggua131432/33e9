package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 智能云调度：区域配置
 */
public class CcAreaCity {

    // 主键：自增
    private Integer id;

    // account id
    private String sid;

    // 区域id
    private String areaId;

    // 区域编号
    private String cityCode;

    // 创建时间
    private Date ctime;

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
        this.sid = sid == null ? null : sid.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}