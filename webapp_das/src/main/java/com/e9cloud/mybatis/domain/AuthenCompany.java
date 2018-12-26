package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 公司的基本资料
 * Created by Administrator on 2016/8/4.
 */
public class AuthenCompany {

    // id
    private Integer id;

    // 用户uid
    private String uid;

    // 公司名称
    private String name;

    // 法定代表人
    private String legalRepresentative;

    // 公司电话
    private String telno;

    // 公司地址
    private String address;

    // 公司网站
    private String website;

    // 所属行业ID
    private String tradeId;

    // 组织机构号
    private String organizationNo;

    // 组织机构证验证图片名
    private String organizationPic;

    // 税务登记证号
    private String taxRegNo;

    // 税务登记证验证图片名
    private String taxRegPic;

    // 营业执照号
    private String businessLicenseNo;

    // 营业执照验证图片名
    private String businessLicensePic;

    // 证件类型（0：一照一码。1：三证合一。2：三证分离）
    private String cardType;

    // 统一社会信用代码
    private String creditNo;

    // 注册号（三证合一）
    private String registerNo;

    // 0：未认证   1：认证中  2：已认证
    private String status;

    // 创建时间
    private Date createDate;

    // 更新时间
    private Date updateDate;

    private DicData dicData;

    /* ---------------------------以下是 setter 和 getter 方法------------------------------ */

    public DicData getDicData() {
        return dicData;
    }

    public void setDicData(DicData dicData) {
        this.dicData = dicData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno == null ? null : telno.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getOrganizationNo() {
        return organizationNo;
    }

    public void setOrganizationNo(String organizationNo) {
        this.organizationNo = organizationNo == null ? null : organizationNo.trim();
    }

    public String getOrganizationPic() {
        return organizationPic;
    }

    public void setOrganizationPic(String organizationPic) {
        this.organizationPic = organizationPic == null ? null : organizationPic.trim();
    }

    public String getTaxRegNo() {
        return taxRegNo;
    }

    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo == null ? null : taxRegNo.trim();
    }

    public String getTaxRegPic() {
        return taxRegPic;
    }

    public void setTaxRegPic(String taxRegPic) {
        this.taxRegPic = taxRegPic == null ? null : taxRegPic.trim();
    }

    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo == null ? null : businessLicenseNo.trim();
    }

    public String getBusinessLicensePic() {
        return businessLicensePic;
    }

    public void setBusinessLicensePic(String businessLicensePic) {
        this.businessLicensePic = businessLicensePic == null ? null : businessLicensePic.trim();
    }

    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo == null ? null : creditNo.trim();
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo == null ? null : registerNo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }
}
