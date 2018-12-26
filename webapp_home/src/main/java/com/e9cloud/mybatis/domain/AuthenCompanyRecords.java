package com.e9cloud.mybatis.domain;

import java.util.Date;

public class AuthenCompanyRecords {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.uid
     *
     * @mbggenerated
     */
    private String uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.legal_representative
     *
     * @mbggenerated
     */
    private String legalRepresentative;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.telno
     *
     * @mbggenerated
     */
    private String telno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.address
     *
     * @mbggenerated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.website
     *
     * @mbggenerated
     */
    private String website;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.trade_id
     *
     * @mbggenerated
     */
    private String tradeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.organization_no
     *
     * @mbggenerated
     */
    private String organizationNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.organization_pic
     *
     * @mbggenerated
     */
    private String organizationPic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.tax_reg_no
     *
     * @mbggenerated
     */
    private String taxRegNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.tax_reg_pic
     *
     * @mbggenerated
     */
    private String taxRegPic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.business_license_no
     *
     * @mbggenerated
     */
    private String businessLicenseNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.business_license_pic
     *
     * @mbggenerated
     */
    private String businessLicensePic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.card_type
     *
     * @mbggenerated
     */
    private String cardType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.credit_no
     *
     * @mbggenerated
     */
    private String creditNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.register_no
     *
     * @mbggenerated
     */
    private String registerNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.create_date
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_authen_company_records.update_date
     *
     * @mbggenerated
     */
    private Date updateDate;
    private String comment;
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    private DicData dicData;

    public DicData getDicData() {
        return dicData;
    }

    public void setDicData(DicData dicData) {
        this.dicData = dicData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.id
     *
     * @return the value of tb_authen_company_records.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.id
     *
     * @param id the value for tb_authen_company_records.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.uid
     *
     * @return the value of tb_authen_company_records.uid
     *
     * @mbggenerated
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.uid
     *
     * @param uid the value for tb_authen_company_records.uid
     *
     * @mbggenerated
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.name
     *
     * @return the value of tb_authen_company_records.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.name
     *
     * @param name the value for tb_authen_company_records.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.legal_representative
     *
     * @return the value of tb_authen_company_records.legal_representative
     *
     * @mbggenerated
     */
    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.legal_representative
     *
     * @param legalRepresentative the value for tb_authen_company_records.legal_representative
     *
     * @mbggenerated
     */
    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.telno
     *
     * @return the value of tb_authen_company_records.telno
     *
     * @mbggenerated
     */
    public String getTelno() {
        return telno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.telno
     *
     * @param telno the value for tb_authen_company_records.telno
     *
     * @mbggenerated
     */
    public void setTelno(String telno) {
        this.telno = telno == null ? null : telno.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.address
     *
     * @return the value of tb_authen_company_records.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.address
     *
     * @param address the value for tb_authen_company_records.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.website
     *
     * @return the value of tb_authen_company_records.website
     *
     * @mbggenerated
     */
    public String getWebsite() {
        return website;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.website
     *
     * @param website the value for tb_authen_company_records.website
     *
     * @mbggenerated
     */
    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.trade_id
     *
     * @return the value of tb_authen_company_records.trade_id
     *
     * @mbggenerated
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.trade_id
     *
     * @param tradeId the value for tb_authen_company_records.trade_id
     *
     * @mbggenerated
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.organization_no
     *
     * @return the value of tb_authen_company_records.organization_no
     *
     * @mbggenerated
     */
    public String getOrganizationNo() {
        return organizationNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.organization_no
     *
     * @param organizationNo the value for tb_authen_company_records.organization_no
     *
     * @mbggenerated
     */
    public void setOrganizationNo(String organizationNo) {
        this.organizationNo = organizationNo == null ? null : organizationNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.organization_pic
     *
     * @return the value of tb_authen_company_records.organization_pic
     *
     * @mbggenerated
     */
    public String getOrganizationPic() {
        return organizationPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.organization_pic
     *
     * @param organizationPic the value for tb_authen_company_records.organization_pic
     *
     * @mbggenerated
     */
    public void setOrganizationPic(String organizationPic) {
        this.organizationPic = organizationPic == null ? null : organizationPic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.tax_reg_no
     *
     * @return the value of tb_authen_company_records.tax_reg_no
     *
     * @mbggenerated
     */
    public String getTaxRegNo() {
        return taxRegNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.tax_reg_no
     *
     * @param taxRegNo the value for tb_authen_company_records.tax_reg_no
     *
     * @mbggenerated
     */
    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo == null ? null : taxRegNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.tax_reg_pic
     *
     * @return the value of tb_authen_company_records.tax_reg_pic
     *
     * @mbggenerated
     */
    public String getTaxRegPic() {
        return taxRegPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.tax_reg_pic
     *
     * @param taxRegPic the value for tb_authen_company_records.tax_reg_pic
     *
     * @mbggenerated
     */
    public void setTaxRegPic(String taxRegPic) {
        this.taxRegPic = taxRegPic == null ? null : taxRegPic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.business_license_no
     *
     * @return the value of tb_authen_company_records.business_license_no
     *
     * @mbggenerated
     */
    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.business_license_no
     *
     * @param businessLicenseNo the value for tb_authen_company_records.business_license_no
     *
     * @mbggenerated
     */
    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo == null ? null : businessLicenseNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.business_license_pic
     *
     * @return the value of tb_authen_company_records.business_license_pic
     *
     * @mbggenerated
     */
    public String getBusinessLicensePic() {
        return businessLicensePic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.business_license_pic
     *
     * @param businessLicensePic the value for tb_authen_company_records.business_license_pic
     *
     * @mbggenerated
     */
    public void setBusinessLicensePic(String businessLicensePic) {
        this.businessLicensePic = businessLicensePic == null ? null : businessLicensePic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.card_type
     *
     * @return the value of tb_authen_company_records.card_type
     *
     * @mbggenerated
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.card_type
     *
     * @param cardType the value for tb_authen_company_records.card_type
     *
     * @mbggenerated
     */
    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.credit_no
     *
     * @return the value of tb_authen_company_records.credit_no
     *
     * @mbggenerated
     */
    public String getCreditNo() {
        return creditNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.credit_no
     *
     * @param creditNo the value for tb_authen_company_records.credit_no
     *
     * @mbggenerated
     */
    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo == null ? null : creditNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.register_no
     *
     * @return the value of tb_authen_company_records.register_no
     *
     * @mbggenerated
     */
    public String getRegisterNo() {
        return registerNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.register_no
     *
     * @param registerNo the value for tb_authen_company_records.register_no
     *
     * @mbggenerated
     */
    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo == null ? null : registerNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.status
     *
     * @return the value of tb_authen_company_records.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.status
     *
     * @param status the value for tb_authen_company_records.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.create_date
     *
     * @return the value of tb_authen_company_records.create_date
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.create_date
     *
     * @param createDate the value for tb_authen_company_records.create_date
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_authen_company_records.update_date
     *
     * @return the value of tb_authen_company_records.update_date
     *
     * @mbggenerated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_authen_company_records.update_date
     *
     * @param updateDate the value for tb_authen_company_records.update_date
     *
     * @mbggenerated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}