package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 扣款记录实体类
 */
public class PaymentRecords {
    /**
     *主键ID
     */
    private Integer id;

    /**
     * 充值记录状态
     */
    private Integer status;

    /**
     * 充值账号
     */
    private String sid;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 充值金额
     */
    private BigDecimal money;

    /**
     * 执行充值的时间
     */
    private Date paymentTime;

    /**
     * 执行充值操作的当前用户
     */
    private String operator;

    /**
     * 充值过程的备注信息
     */
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

}