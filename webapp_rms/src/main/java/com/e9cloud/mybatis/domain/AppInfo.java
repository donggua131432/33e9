package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AppInfo implements Serializable {

    // 主键 自增
    private Integer id;

    // 应用id
    private String appid;

    // 应用主账号
    private String sid;

    // 业务类型:01:智能云调度(955xx);02:回拨REST 03：sip, 05 ：云话机
    private String busType;

    // 应用名称
    private String appName;

    // 应用类型 金融、医疗 等
    private String appType;

    // 呼叫号码:bus_type为955xx时，此字段不能为空
    private String callNo;

    // 应用回调URL
    private String callbackUrl;

    // 应用关联的配置类型
    private String associateType;

    // 回调url是否启用 00：启用 01：不启用
    private String urlStatus;

    // app 状态 00:正常 01:冻结  02:禁用
    private String status;

    // 创建时间
    private Date createDate;

    // 更新时间
    private Date updateDate;

    // 字典信息 如 行业等
    private DicData dicData;

    /** 客户名称 */
    private String companyName;

    /** app最大并发数 */
    private Integer maxConcurrent;

    /** 应用限额 */
    private String limitFlag;

    /** 额度 */
    private BigDecimal quota;

    /** 系统默认或自定义中继(0:系统默认,1:自定义) */
    private String isDefined;

   /** 业务是否使用专用路由：00:关闭 01:开启*/
    private String isRoute;

    /** 路由编码：四位数字（首位可为0），如不够四位，前面补0*/
    private String routeCode;

    // 呼叫中心代答中继
    private String answerTrunk;

    /** 区域名称拼音*/
    private String pinyin;

    /** appInfo 的扩展信息 */
    private AppInfoExtra appInfoExtra;

    ////////////////////////////////////// 以下 是 setter 和 getter 方法 ///////////////////////////////////////

    public String getIsDefined() {
        return isDefined;
    }

    public void setIsDefined(String isDefined) {
        this.isDefined = isDefined;
    }

    public Integer getMaxConcurrent() {
        return maxConcurrent;
    }

    public void setMaxConcurrent(Integer maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

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

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType == null ? null : busType.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo == null ? null : callNo.trim();
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl == null ? null : callbackUrl.trim();
    }

    public String getAssociateType() {
        return associateType;
    }

    public void setAssociateType(String associateType) {
        this.associateType = associateType == null ? null : associateType.trim();
    }

    public String getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(String urlStatus) {
        this.urlStatus = urlStatus == null ? null : urlStatus.trim();
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getIsRoute() {
        return isRoute;
    }

    public void setIsRoute(String isRoute) {
        this.isRoute = isRoute;
    }

    public String getAnswerTrunk() {
        return answerTrunk;
    }

    public void setAnswerTrunk(String answerTrunk) {
        this.answerTrunk = answerTrunk;
    }

    public AppInfoExtra getAppInfoExtra() {
        return appInfoExtra;
    }

    public void setAppInfoExtra(AppInfoExtra appInfoExtra) {
        this.appInfoExtra = appInfoExtra;
    }

    public String getLimitFlag() {
        return limitFlag;
    }

    public void setLimitFlag(String limitFlag) {
        this.limitFlag = limitFlag;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public String getPinyin() {return pinyin;}

    public void setPinyin(String pinyin) {this.pinyin = pinyin;}
}