package com.e9cloud.mybatis.domain;

import java.util.Date;

public class EccAppInfo {

    //
    private String id;

    //
    private String appid;

    // 总机号id
    private String switchboardId;

    // 总机号
    private String switchboard;

    //
    private String cityid;

    // 区号
    private String areaCode;

    private String pname; // 省份名称

    private String cname; // 城市名称

    private String pcode; // 省份编号

    // 转接开关
    private Boolean transfer;

    // 转接号码
    private String transferNum;

    // 是否需要提示音
    private Boolean ivrVoiceNeed;

    // 提示音的名称
    private String ivrVoiceName;

    //
    private String ivrVoiceUrl;

    //
    private Date addtime;

    //
    private Date updatetime;

    ///////////////////// 以下是setter和getter方法 /////////////////////////


    public String getSwitchboard() {
        return switchboard;
    }

    public void setSwitchboard(String switchboard) {
        this.switchboard = switchboard;
    }

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

    public String getSwitchboardId() {
        return switchboardId;
    }

    public void setSwitchboardId(String switchboardId) {
        this.switchboardId = switchboardId == null ? null : switchboardId.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public String getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(String transferNum) {
        this.transferNum = transferNum == null ? null : transferNum.trim();
    }

    public Boolean getIvrVoiceNeed() {
        return ivrVoiceNeed;
    }

    public void setIvrVoiceNeed(Boolean ivrVoiceNeed) {
        this.ivrVoiceNeed = ivrVoiceNeed;
    }

    public String getIvrVoiceName() {
        return ivrVoiceName;
    }

    public void setIvrVoiceName(String ivrVoiceName) {
        this.ivrVoiceName = ivrVoiceName == null ? null : ivrVoiceName.trim();
    }

    public String getIvrVoiceUrl() {
        return ivrVoiceUrl;
    }

    public void setIvrVoiceUrl(String ivrVoiceUrl) {
        this.ivrVoiceUrl = ivrVoiceUrl == null ? null : ivrVoiceUrl.trim();
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
}