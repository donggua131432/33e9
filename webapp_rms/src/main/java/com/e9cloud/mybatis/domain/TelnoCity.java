package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * Created by dell on 2016/8/4.
 */
public class TelnoCity implements Serializable {

    private Integer id; //主键
    private String pcode;//所属省份
    private String ccode;//城市代码
    private String cname;//城市名称
    private String areaCode;//城市区号
    private String sid;//所属客户sid
    private Boolean ctype;//城市属性  0公用1专属
    private String ctime;//创建时间
    private String pinyin;//城市名称拼音
    private  TelnoProvince province;//省份

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPcode() {
        return pcode;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public TelnoProvince getProvince() {
        return province;
    }

    public void setProvince(TelnoProvince province) {
        this.province = province;
    }

    public Boolean getCtype() {
        return ctype;
    }

    public void setCtype(Boolean ctype) {
        this.ctype = ctype;
    }

    public String getPinyin() { return pinyin;}

    public void setPinyin(String pinyin) {this.pinyin = pinyin;}
}
