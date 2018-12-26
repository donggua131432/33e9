package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 中继群运营商被叫前缀
 */
public class RelayGroup2 extends RelayGroup implements Serializable {

    // 主键 自增
    private Integer id;

    // 中继群编号
    private String tgNum;

    // 中继群名
    private String tgName;

    // 运营商编号
    private String operatorCode;

    // 运营商名称
    private String operatorName;

    // 被叫前缀
    private String calledPre;

    public RelayGroup2 () {
        this.setG("2");
    }

    //////////////////////////////////////////////////////

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

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode == null ? null : operatorCode.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getCalledPre() {
        return calledPre;
    }

    public void setCalledPre(String calledPre) {
        this.calledPre = calledPre == null ? null : calledPre.trim();
    }
}