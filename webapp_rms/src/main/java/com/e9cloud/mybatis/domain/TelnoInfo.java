package com.e9cloud.mybatis.domain;

import java.io.Serializable;
/**
 * 号段配置表
 *
 * @author Created by pengchunchen on 2016/8/4.
 */
public class TelnoInfo implements Serializable {

    private Integer id;
    private String telno;//号段
    private String pcode;//省份编码
    private String pname;//省份
    private String ccode;//城市编码
    private String cname;//城市名称
    private String areacode;//区号
    private String operator;//运营商类型
    private String card;//卡类型

    //扩展字段
    private String oname;//运营商名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }
}