package com.e9cloud.mybatis.domain;

import java.util.Date;

public class AxbNum {

    private String id;

    private String appid;

    private String axbNumId;

    //状态（00:正常 01:已锁定 02：已删除 ）
    private String status;

    private Date addtime;

    private Date updatetime;

    //冻结的时候，保存冻结时间
    private Date freezetime;

    //删除时间
    private Date deltime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAxbNumId() {
        return axbNumId;
    }

    public void setAxbNumId(String axbNumId) {
        this.axbNumId = axbNumId;
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

    public Date getFreezetime() {
        return freezetime;
    }

    public void setFreezetime(Date freezetime) {
        this.freezetime = freezetime;
    }

    public Date getDeltime() {
        return deltime;
    }

    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }
}