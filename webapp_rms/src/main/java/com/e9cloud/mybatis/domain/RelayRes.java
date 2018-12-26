package com.e9cloud.mybatis.domain;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * tb_relay_res 供应商线路资源
 */
public class RelayRes {

    // 主键自增
    private Integer id;

    // 供应商id
    private String supId;

    // 资源名称
    private String resName;

    // 中继编号
    private String relayNum;

    // 线路数
    private Integer relayCnt;

    // 总低消
    private BigDecimal relayRent;

    // 城市ccode 对应：tb_telno_city.ccode
    private String cityid;

    // 00:中国移动,01:中国联通,02:中国电信 查看表格：tb_telno_operator.ocode
    private String operator;

    // 签署类型:00：运营商直签，01：第三方资源
    private String signType;

    // 计费周期 60秒默认
    private Integer cycle;

    // 价格
    private BigDecimal per;

    // 00:正常；01:删除
    private String status;

    // 添加时间
    private Date addtime;

    // 最新更新时间
    private Date updatetime;

    // 老的中继编号（编辑时 变更前的中继编号）
    private String oldRelayNum;

    private String pcode; // 省份编号

    private String cname; // 城市名称

    private String pname; // 省份名称

    private SipBasic sipBasic; // 中继信息

    // 详细价格列表
    private List<RelayResPer> relayResPers = Lists.newArrayList();

    //////////////////////// 以下是setter和getter方法 /////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getRelayNum() {
        return relayNum;
    }

    public void setRelayNum(String relayNum) {
        this.relayNum = relayNum == null ? null : relayNum.trim();
    }

    public Integer getRelayCnt() {
        return relayCnt;
    }

    public void setRelayCnt(Integer relayCnt) {
        this.relayCnt = relayCnt;
    }

    public BigDecimal getRelayRent() {
        return relayRent;
    }

    public void setRelayRent(BigDecimal relayRent) {
        this.relayRent = relayRent;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType == null ? null : signType.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOldRelayNum() {
        return oldRelayNum;
    }

    public void setOldRelayNum(String oldRelayNum) {
        this.oldRelayNum = oldRelayNum;
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

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public SipBasic getSipBasic() {
        return sipBasic;
    }

    public void setSipBasic(SipBasic sipBasic) {
        this.sipBasic = sipBasic;
    }

    public List<RelayResPer> getRelayResPers() {
        return relayResPers;
    }

    public void setRelayResPers(List<RelayResPer> relayResPers) {
        this.relayResPers = relayResPers;
    }
}