package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 中继线路故障统计
 *
 * @author Created by hzd on 2016/8/18.
 */

public class RelayFault implements Serializable {


    private static final long serialVersionUID = 1816538011484425935L;

    //故障类型
    private String faultName;
    //错误码（...,4xx,...）
    private String codeRange;

    private Integer myYear;

    private String recordRate;


    //故障次数占比%（1-12月）
    private String recordRate1;

    private String recordRate2;

    private String recordRate3;

    private String recordRate4;

    private String recordRate5;

    private String recordRate6;

    private String recordRate7;

    private String recordRate8;

    private String recordRate9;

    private String recordRate10;

    private String recordRate11;

    private String recordRate12;

    public String getFaultName() {
        return faultName;
    }

    public String getCodeRange() {
        return codeRange;
    }

    public String getRecordRate() { return recordRate; }

    public String getRecordRate1() {
        return recordRate1;
    }

    public String getRecordRate2() {
        return recordRate2;
    }

    public String getRecordRate3() {
        return recordRate3;
    }

    public String getRecordRate4() {
        return recordRate4;
    }

    public String getRecordRate5() {
        return recordRate5;
    }

    public String getRecordRate6() {
        return recordRate6;
    }

    public String getRecordRate7() {
        return recordRate7;
    }

    public String getRecordRate8() {
        return recordRate8;
    }

    public String getRecordRate9() {
        return recordRate9;
    }

    public String getRecordRate10() {
        return recordRate10;
    }

    public String getRecordRate11() {
        return recordRate11;
    }

    public String getRecordRate12() {
        return recordRate12;
    }

    public Integer getMyYear() {
        return myYear;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public void setCodeRange(String codeRange) {
        this.codeRange = codeRange;
    }

    public void setRecordRate(String recordRate) { this.recordRate = recordRate; }

    public void setRecordRate1(String recordRate1) {
        this.recordRate1 = recordRate1;
    }

    public void setRecordRate2(String recordRate2) {
        this.recordRate2 = recordRate2;
    }

    public void setRecordRate3(String recordRate3) {
        this.recordRate3 = recordRate3;
    }

    public void setRecordRate4(String recordRate4) {
        this.recordRate4 = recordRate4;
    }

    public void setRecordRate5(String recordRate5) {
        this.recordRate5 = recordRate5;
    }

    public void setRecordRate6(String recordRate6) {
        this.recordRate6 = recordRate6;
    }

    public void setRecordRate7(String recordRate7) {
        this.recordRate7 = recordRate7;
    }

    public void setRecordRate8(String recordRate8) {
        this.recordRate8 = recordRate8;
    }

    public void setRecordRate9(String recordRate9) {
        this.recordRate9 = recordRate9;
    }

    public void setRecordRate10(String recordRate10) {
        this.recordRate10 = recordRate10;
    }

    public void setRecordRate11(String recordRate11) {
        this.recordRate11 = recordRate11;
    }

    public void setRecordRate12(String recordRate12) {
        this.recordRate12 = recordRate12;
    }

    public void setMyYear(Integer myYear) {
        this.myYear = myYear;
    }
}