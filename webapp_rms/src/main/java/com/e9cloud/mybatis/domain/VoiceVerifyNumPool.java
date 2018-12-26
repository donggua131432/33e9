package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 语音验证码（号码池）
 */
public class VoiceVerifyNumPool {

    // id
    private String id;

    // 城市（tb_telno_city.ccode）
    private String cityid;

    // 号码，支持固话，手机号，95xxx
    private String number;

    // 状态（00:待分配 01:已分配  02：已删除）
    private String status;

    // 添加时间
    private Date addtime;

    // 更新时间
    private Date updatetime;

    //城市区号
    private String areacode;

    // execl 导入时错误信息
    private String importCommon;

    ///////////////////////////////////以下是setter 和 getter 方法///////////////////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getImportCommon() {
        return importCommon;
    }

    public void setImportCommon(String importCommon) {
        this.importCommon = importCommon;
    }
}