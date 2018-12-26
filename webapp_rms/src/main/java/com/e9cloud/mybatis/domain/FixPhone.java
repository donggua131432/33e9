package com.e9cloud.mybatis.domain;

public class FixPhone {
    //  主键uuid
    private String id;
    //应用appid
    private String appid;
    //城市code
    private String cityid;

    // 城市区号
    private String areaCode;

    // execl 导入时错误信息
    private String importCommon;
    //固话号码
    private String number;
    //状态（01:已分配  02:已锁定  03:待分配）
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getImportCommon() {
        return importCommon;
    }

    public void setImportCommon(String importCommon) {
        this.importCommon = importCommon;
    }
}