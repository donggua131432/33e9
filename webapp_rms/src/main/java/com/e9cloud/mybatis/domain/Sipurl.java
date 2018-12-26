package com.e9cloud.mybatis.domain;

import java.util.Date;

public class Sipurl {

    //
    private String id;

    //
    private String sipurl;

    // 号码
    private String num;

    // 新增时间
    private Date addtime;

    // 修改时间
    private Date updatetime;

    private String importCommon;

    //////////////////////////////////setter和getter方法////////////////////////////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSipurl() {
        return sipurl;
    }

    public void setSipurl(String sipurl) {
        this.sipurl = sipurl == null ? null : sipurl.trim();
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
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

    public String getImportCommon() {
        return importCommon;
    }

    public void setImportCommon(String importCommon) {
        this.importCommon = importCommon;
    }
}