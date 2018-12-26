package com.e9cloud.mybatis.domain;

import java.util.Date;

public class CbVoiceCode {

    private Integer id;

    //业务类型 具体查看code
    private String busCode;

    //语音编码
    private String voiceCode;

    private Date addtime;

    private Date updatetime;

    // 类型
    public enum Code {
        rest, mask, cc, voice, sip, sp, ecc,vn,voiceValidate
    }
    /********************************************************* 以下是setter和getter方法 *********************************************************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public void setBusCode(Code code) {
        this.busCode = code.toString();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getVoiceCode() {
        return voiceCode;
    }

    public void setVoiceCode(String voiceCode) {
        this.voiceCode = voiceCode;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}