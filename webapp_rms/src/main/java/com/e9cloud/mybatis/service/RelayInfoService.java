package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SipRate;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 中继子账号相关 Service
 * Created by Administrator on 2016/7/19.
 */
public interface RelayInfoService extends IBaseService {

    /**
     * 分页查询子账号列表
     *
     * @param page 分页信息
     * @return PageWrapper
     */
    PageWrapper pageRelayInfo(Page page);

    /**
     * 保存中继-子账号信息和对应的费率信息
     * @param relayInfo 中继-子账号信息
     * @param sipRate sip费率
     */
    void saveRelayInfoAndRate(SipRelayInfo relayInfo, SipRate sipRate);

    /**
     * 修改中继-子账号信息和对应的费率信息
     * @param relayInfo 中继-子账号信息
     * @param sipRate sip费率
     */
    void updateRelayInfoAndRate(SipRelayInfo relayInfo, SipRate sipRate);

    /**
     * 保存中继-子账号信息
     *
     * @param relayInfo 中继-子账号信息
     */
    void saveRelayInfo(SipRelayInfo relayInfo);

    /**
     * 修改中继-子账号信息
     *
     * @param relayInfo 中继-子账号信息
     */
    void updateRelayInfo(SipRelayInfo relayInfo);

    /**
     * 根据subid查询中继-子账号信息
     * @param subid
     * @return
     */
    SipRelayInfo getRelayInfoBySubid(String subid);

    /**
     * 删除子账号 并 清除relayNum
     * @param subid 子账号id
     */
    void deleteSubBySubid(String subid);

    /**
     * 下载子账号报表
     * @param page 分页信息
     * @return
     */
    List<Map<String, Object>> downloadSubReport(Page page);
}
