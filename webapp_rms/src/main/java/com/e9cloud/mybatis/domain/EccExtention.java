package com.e9cloud.mybatis.domain;

import java.util.Date;

// ecc 分机 对应表格tb_ecc_extention
public class EccExtention {

    // 主键UUID
    private String id;

    // appid
    private String appid;

    // eccid tb_app_info_ecc.id
    private String eccid;

    // 分机号
    private String subNum;

    // 接听号码类型：00:sipphone,01：手机号，02：固话
    private String numType;

    private String numTypeStr;

    // ecc SIP phone id
    private String sipphoneId;

    // ecc 固话、手机号 id
    private String fixphoneId;

    private String phone;

    // ecc 外显号 id
    private String shownumId;

    private String shownum;

    // 长途权限：00 开启，01 关闭
    private String longDistanceFlag;

    // 号码禁用开关：00 可用，01禁用
    private String callSwitchFlag;

    private String cityid;

    // 状态：00正常,01已删除
    private String status;

    //
    private Date addtime;

    //
    private Date updatetime;

    private String importCommon;

    ////////////////////////// 以下是setter和getter方法 /////////////////////////////////

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

    public String getSubNum() {
        return subNum;
    }

    public void setSubNum(String subNum) {
        this.subNum = subNum == null ? null : subNum.trim();
    }

    public String getNumType() {
        return numType;
    }

    public void setNumType(String numType) {
        this.numType = numType == null ? null : numType.trim();
    }

    public String getNumTypeStr() {
        return numTypeStr;
    }

    public void setNumTypeStr(String numTypeStr) {
        this.numTypeStr = numTypeStr;
    }

    public String getSipphoneId() {
        return sipphoneId;
    }

    public void setSipphoneId(String sipphoneId) {
        this.sipphoneId = sipphoneId == null ? null : sipphoneId.trim();
    }

    public String getFixphoneId() {
        return fixphoneId;
    }

    public void setFixphoneId(String fixphoneId) {
        this.fixphoneId = fixphoneId == null ? null : fixphoneId.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getShownumId() {
        return shownumId;
    }

    public void setShownumId(String shownumId) {
        this.shownumId = shownumId == null ? null : shownumId.trim();
    }

    public String getLongDistanceFlag() {
        return longDistanceFlag;
    }

    public void setLongDistanceFlag(String longDistanceFlag) {
        this.longDistanceFlag = longDistanceFlag == null ? null : longDistanceFlag.trim();
    }

    public String getCallSwitchFlag() {
        return callSwitchFlag;
    }

    public void setCallSwitchFlag(String callSwitchFlag) {
        this.callSwitchFlag = callSwitchFlag == null ? null : callSwitchFlag.trim();
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

    public String getImportCommon() {
        return importCommon;
    }

    public void setImportCommon(String importCommon) {
        this.importCommon = importCommon;
    }

    public String getShownum() {
        return shownum;
    }

    public void setShownum(String shownum) {
        this.shownum = shownum;
    }
}