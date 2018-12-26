package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * Created by dell on 2016/8/4.
 */
public class TelnoProvince implements Serializable {
    private Integer id;
    private String pcode;//省份代码
    private String pname;//省份名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
