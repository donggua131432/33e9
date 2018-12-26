package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SipRate;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.domain.SipRelayNumPool;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayInfoService;
import com.e9cloud.mybatis.service.RelayService;
import com.e9cloud.mybatis.service.SipRateService;
import com.e9cloud.mybatis.service.SubNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 中继子账号相关Service
 * Created by Administrator on 2016/7/19.
 */
@Service
public class RelayInfoServiceImpl extends BaseServiceImpl implements RelayInfoService {

    @Autowired
    private SipRateService sipRateService;

    @Autowired
    private RelayService relayService;

    @Autowired
    private SubNumService subNumService;

    /**
     * 分页查询子账号列表
     *
     * @param page 分页信息
     * @return PageWrapper
     */
    @Override
    public PageWrapper pageRelayInfo(Page page) {
        return this.page(Mapper.SipRelayInfo_Mapper.pageRelayInfo, page);
    }

    /**
     * 保存中继-子账号信息和对应的费率信息
     *
     * @param relayInfo 中继-子账号信息
     * @param sipRate   sip费率
     */
    @Override
    public void saveRelayInfoAndRate(SipRelayInfo relayInfo, SipRate sipRate) {
        this.saveRelayInfo(relayInfo);
        sipRateService.saveSipRate(sipRate);
    }

    /**
     * 修改中继-子账号信息和对应的费率信息
     *
     * @param relayInfo 中继-子账号信息
     * @param sipRate   sip费率
     */
    @Override
    public void updateRelayInfoAndRate(SipRelayInfo relayInfo, SipRate sipRate) {
        // freezeRelay(relayInfo.getSubid(), relayInfo.getRelayNum());
        this.updateRelayInfo(relayInfo);
        sipRateService.updateSipRate(sipRate);
    }

    /**
     * 保存中继-子账号信息
     *
     * @param relayInfo 中继-子账号信息
     */
    @Override
    public void saveRelayInfo(SipRelayInfo relayInfo){
        this.save(Mapper.SipRelayInfo_Mapper.insertSelective, relayInfo);
    }

    /**
     * 根据subid查询中继-子账号信息
     *
     * @param subid
     * @return
     */
    @Override
    public SipRelayInfo getRelayInfoBySubid(String subid) {
        return this.findObjectByPara(Mapper.SipRelayInfo_Mapper.selectRelayInfoBySubid, subid);
    }

    /**
     * 修改中继-子账号信息
     *
     * @param relayInfo 中继-子账号信息
     */
    @Override
    public void updateRelayInfo(SipRelayInfo relayInfo) {
        this.update(Mapper.SipRelayInfo_Mapper.updateBySubidSelective, relayInfo);
    }

    /**
     * 删除子账号 并 清除relayNum
     *
     * @param subid 子账号id
     */
    @Override
    public void deleteSubBySubid(String subid) {
        // freezeRelay(subid, "");
        SipRelayInfo relayInfo = new SipRelayInfo();
        relayInfo.setSubid(subid);
        relayInfo.setStatus("01");
        relayInfo.setRelayNum("");
        this.updateRelayInfo(relayInfo);
        SipRelayNumPool sipRelayNumPool = new SipRelayNumPool();
        sipRelayNumPool.setSubid(subid);
        subNumService.deleteSubNumberBySubid(sipRelayNumPool);

    }

    /**
     * 冻结中继
     * @param subid 子账号id
     * @param newRelayNum 中继编号
     */
    private void freezeRelay(String subid, String newRelayNum) {
        SipRelayInfo oldRelayInfo = this.getRelayInfoBySubid(subid);

        // 更换中继时，冻结原来的中继
        if (Tools.isNotNullStr(oldRelayInfo.getRelayNum()) && !oldRelayInfo.getRelayNum().equals(newRelayNum)) {
            relayService.updateRelayStatusByRelayNum(oldRelayInfo.getRelayNum(), "02");
        }
    }

    /**
     * 下载子账号报表
     *
     * @param page 分页信息
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSubReport(Page page) {
        return this.download(Mapper.SipRelayInfo_Mapper.pageRelayInfo, page);
    }
}
