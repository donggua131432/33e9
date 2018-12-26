package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 云话机：sip 号码
 */
public class SipPhone {

    // 主键
    private String id;

    //  sip号码
    private String sipPhone;

    // 认证id
    private String impi;

    // 城市区号ID
    private String cityid;

    // 城市区号
    private String areaCode;

    // execl 导入时错误信息
    private String importCommon;
    // 认证密码
    private String pwd;
    //  IP 端口
    private String ipPort;
    //  SIP REALM
    private String sipRealm;
    //  状态（01:已分配  02:已锁定  03:待分配）
    private String status;
    //  创建时间
    private Date addtime;
    //最后的修改时间
    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSipPhone() {
        return sipPhone;
    }

    public void setSipPhone(String sipPhone) {
        this.sipPhone = sipPhone == null ? null : sipPhone.trim();
    }

    public String getImpi() {
        return impi;
    }

    public void setImpi(String impi) {
        this.impi = impi == null ? null : impi.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort == null ? null : ipPort.trim();
    }

    public String getSipRealm() {
        return sipRealm;
    }

    public void setSipRealm(String sipRealm) {
        this.sipRealm = sipRealm == null ? null : sipRealm.trim();
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