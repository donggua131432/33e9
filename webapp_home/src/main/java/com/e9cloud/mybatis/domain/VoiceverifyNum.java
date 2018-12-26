package com.e9cloud.mybatis.domain;

import java.util.Date;

public class VoiceverifyNum {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_voiceverify_num.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_voiceverify_num.appid
     *
     * @mbggenerated
     */
    private String appid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_voiceverify_num.num_id
     *
     * @mbggenerated
     */
    private String numId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_voiceverify_num.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_voiceverify_num.addtime
     *
     * @mbggenerated
     */
    private Date addtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_voiceverify_num.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_voiceverify_num.deltime
     *
     * @mbggenerated
     */
    private Date deltime;

    private String cname;
    private String areaCode;
    private String number;


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_voiceverify_num.id
     *
     * @return the value of tb_voiceverify_num.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_voiceverify_num.id
     *
     * @param id the value for tb_voiceverify_num.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_voiceverify_num.appid
     *
     * @return the value of tb_voiceverify_num.appid
     *
     * @mbggenerated
     */
    public String getAppid() {
        return appid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_voiceverify_num.appid
     *
     * @param appid the value for tb_voiceverify_num.appid
     *
     * @mbggenerated
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_voiceverify_num.num_id
     *
     * @return the value of tb_voiceverify_num.num_id
     *
     * @mbggenerated
     */
    public String getNumId() {
        return numId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_voiceverify_num.num_id
     *
     * @param numId the value for tb_voiceverify_num.num_id
     *
     * @mbggenerated
     */
    public void setNumId(String numId) {
        this.numId = numId == null ? null : numId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_voiceverify_num.status
     *
     * @return the value of tb_voiceverify_num.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_voiceverify_num.status
     *
     * @param status the value for tb_voiceverify_num.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_voiceverify_num.addtime
     *
     * @return the value of tb_voiceverify_num.addtime
     *
     * @mbggenerated
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_voiceverify_num.addtime
     *
     * @param addtime the value for tb_voiceverify_num.addtime
     *
     * @mbggenerated
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_voiceverify_num.updatetime
     *
     * @return the value of tb_voiceverify_num.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_voiceverify_num.updatetime
     *
     * @param updatetime the value for tb_voiceverify_num.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_voiceverify_num.deltime
     *
     * @return the value of tb_voiceverify_num.deltime
     *
     * @mbggenerated
     */
    public Date getDeltime() {
        return deltime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_voiceverify_num.deltime
     *
     * @param deltime the value for tb_voiceverify_num.deltime
     *
     * @mbggenerated
     */
    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}