package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 中继群To头域号段
 */
public class RelayGroup1 extends RelayGroup implements Serializable {

    // id 自增
    private Integer id;

    // 中继群编号
    private String tgNum;

    // 中继群名
    private String tgName;

    // 城市
    private String cityName;

    // 电话区号
    private String areaCode;

    // 号段起始号码
    private String codeStart;

    // 号段结束号码
    private String codeEnd;

    public RelayGroup1 () {
        this.setG("1");
    }

    ///////////////////////////// setter 和 getter /////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTgNum() {
        return tgNum;
    }

    public void setTgNum(String tgNum) {
        this.tgNum = tgNum == null ? null : tgNum.trim();
    }

    public String getTgName() {
        return tgName;
    }

    public void setTgName(String tgName) {
        this.tgName = tgName == null ? null : tgName.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getCodeStart() {
        return codeStart;
    }

    public void setCodeStart(String codeStart) {
        this.codeStart = codeStart == null ? null : codeStart.trim();
    }

    public String getCodeEnd() {
        return codeEnd;
    }

    public void setCodeEnd(String codeEnd) {
        this.codeEnd = codeEnd == null ? null : codeEnd.trim();
    }
}