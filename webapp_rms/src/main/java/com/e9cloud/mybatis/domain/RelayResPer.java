package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * tb_relay_res_per 供应商资源--价格详细
 */
public class RelayResPer {

    // 主键，自增
    private Integer id;

    // 资源id tb_relay_res.id
    private Integer resId;

    // 00:中国移动,01:中国联通,02:中国电信 查看表格：tb_telno_operator.ocode
    private String operator;

    // 00 市话，01 长途
    private String callType;

    // 落地号码： 00:手机;01:固话;02:400号码;03:955号码;04:其他
    private String numType;

    // 计费周期 60秒默认
    private Integer cycle;

    // // 价格
    private BigDecimal per;

    // 添加时间
    private Date addtime;

    // 最新更新时间
    private Date updatetime;

    ////////////////////////// 以下是setter和getter ////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType == null ? null : callType.trim();
    }

    public String getNumType() {
        return numType;
    }

    public void setNumType(String numType) {
        this.numType = numType == null ? null : numType.trim();
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public BigDecimal getPer() {
        return per;
    }

    public void setPer(BigDecimal per) {
        this.per = per;
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
}