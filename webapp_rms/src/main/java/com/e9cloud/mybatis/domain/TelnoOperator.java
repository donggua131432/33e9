package com.e9cloud.mybatis.domain;

import java.io.Serializable;
/**
 * 运营商表
 *
 * @author Created by pengchunchen on 2016/8/4.
 */
public class TelnoOperator implements Serializable {

    private Integer id;
    private String ocode;//运营商编码
    private String oname;//运营商名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }
}