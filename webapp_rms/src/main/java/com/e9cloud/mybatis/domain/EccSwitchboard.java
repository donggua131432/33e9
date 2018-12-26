package com.e9cloud.mybatis.domain;

import java.util.Date;

// ecc 总机 tb_ecc_switchboard
public class EccSwitchboard {

    // 主键uuid
    private String id;

    // appid
    private String appid;

    //
    private String eccid;

    // 城市ccode
    private String cityid;

    // 号码
    private String number;

    // 状态（01:已分配  02:已锁定  03:待分配）
    private String status;

    //
    private Date addtime;

    //
    private Date updatetime;

    private String pcode;

    private String ccode;

    // 城市区号
    private String areaCode;

    // execl 导入时错误信息
    private String importCommon;

    ///////////////////////////// 以下是setter和getter方法 /////////////////////////////////////

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

    public String getEccid() {
        return eccid;
    }

    public void setEccid(String eccid) {
        this.eccid = eccid == null ? null : eccid.trim();
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

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
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