package com.e9cloud.mybatis.domain;

import java.io.Serializable;

public class SysAvar implements Serializable{
    
    private static final long serialVersionUID = 2357529210053991856L;

    private String var;

    private String varname;

    private String valtype;

    private Integer ivalue;

    private String cvalue;

    private Float fvalue;

    private String remark;

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var == null ? null : var.trim();
    }

    public String getVarname() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname = varname == null ? null : varname.trim();
    }

    public String getValtype() {
        return valtype;
    }

    public void setValtype(String valtype) {
        this.valtype = valtype == null ? null : valtype.trim();
    }

    public Integer getIvalue() {
        return ivalue;
    }

    public void setIvalue(Integer ivalue) {
        this.ivalue = ivalue;
    }

    public String getCvalue() {
        return cvalue;
    }

    public void setCvalue(String cvalue) {
        this.cvalue = cvalue == null ? null : cvalue.trim();
    }

    public Float getFvalue() {
        return fvalue;
    }

    public void setFvalue(Float fvalue) {
        this.fvalue = fvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}