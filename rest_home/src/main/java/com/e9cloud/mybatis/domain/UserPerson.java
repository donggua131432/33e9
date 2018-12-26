package com.e9cloud.mybatis.domain;

import java.util.Date;

public class UserPerson {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_admin_authen_personal.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_admin_authen_personal.uid
     *
     * @mbggenerated
     */
    private String uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_admin_authen_personal.real_name
     *
     * @mbggenerated
     */
    private String realName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_admin_authen_personal.idcard_type
     *
     * @mbggenerated
     */
    private Integer idcardType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_admin_authen_personal.idcard_no
     *
     * @mbggenerated
     */
    private String idcardNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_admin_authen_personal.idcard_pic
     *
     * @mbggenerated
     */
    private String idcardPic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_admin_authen_personal.create_date
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user_admin_authen_personal.update_date
     *
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_admin_authen_personal.id
     *
     * @return the value of tb_user_admin_authen_personal.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_admin_authen_personal.id
     *
     * @param id the value for tb_user_admin_authen_personal.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_admin_authen_personal.uid
     *
     * @return the value of tb_user_admin_authen_personal.uid
     *
     * @mbggenerated
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_admin_authen_personal.uid
     *
     * @param uid the value for tb_user_admin_authen_personal.uid
     *
     * @mbggenerated
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_admin_authen_personal.real_name
     *
     * @return the value of tb_user_admin_authen_personal.real_name
     *
     * @mbggenerated
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_admin_authen_personal.real_name
     *
     * @param realName the value for tb_user_admin_authen_personal.real_name
     *
     * @mbggenerated
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_admin_authen_personal.idcard_type
     *
     * @return the value of tb_user_admin_authen_personal.idcard_type
     *
     * @mbggenerated
     */
    public Integer getIdcardType() {
        return idcardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_admin_authen_personal.idcard_type
     *
     * @param idcardType the value for tb_user_admin_authen_personal.idcard_type
     *
     * @mbggenerated
     */
    public void setIdcardType(Integer idcardType) {
        this.idcardType = idcardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_admin_authen_personal.idcard_no
     *
     * @return the value of tb_user_admin_authen_personal.idcard_no
     *
     * @mbggenerated
     */
    public String getIdcardNo() {
        return idcardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_admin_authen_personal.idcard_no
     *
     * @param idcardNo the value for tb_user_admin_authen_personal.idcard_no
     *
     * @mbggenerated
     */
    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo == null ? null : idcardNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_admin_authen_personal.idcard_pic
     *
     * @return the value of tb_user_admin_authen_personal.idcard_pic
     *
     * @mbggenerated
     */
    public String getIdcardPic() {
        return idcardPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_admin_authen_personal.idcard_pic
     *
     * @param idcardPic the value for tb_user_admin_authen_personal.idcard_pic
     *
     * @mbggenerated
     */
    public void setIdcardPic(String idcardPic) {
        this.idcardPic = idcardPic == null ? null : idcardPic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_admin_authen_personal.create_date
     *
     * @return the value of tb_user_admin_authen_personal.create_date
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_admin_authen_personal.create_date
     *
     * @param createDate the value for tb_user_admin_authen_personal.create_date
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_admin_authen_personal.update_date
     *
     * @return the value of tb_user_admin_authen_personal.update_date
     *
     * @mbggenerated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_admin_authen_personal.update_date
     *
     * @param updateDate the value for tb_user_admin_authen_personal.update_date
     *
     * @mbggenerated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}