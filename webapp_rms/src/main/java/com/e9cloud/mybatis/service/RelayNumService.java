package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayNumPool;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.domain.SipRelayNumPool;

import java.util.List;

/**
 * Created by admin on 2016/7/14.
 */
public interface RelayNumService extends IBaseService {

    /**
     * 获取强显号号码信息
     * @param subid
     * @return
     */
    SipRelayInfo getSubApp(String subid);
    /**
     * 通过中继号,号码获取号码池信息列表
     * @param number
     ** @param num
     * @return
     */
    List<RelayNumPool> getRelayNumberByNumber(String num, String number);

    /**
     * 添加强显号码池
     * @param relayNumPool
     */
    void addRelayNumberPool(RelayNumPool relayNumPool);
    /**
     * 获取中继强显号列表
     * @param page
     */
    PageWrapper getRelayNumList(Page page);

    RelayNumPool getRelayNumInfoById(Integer id);


    void updateRelayNumber(RelayNumPool relayNumPool);

    void deleteRelayNumber(RelayNumPool relayNumPool);

    /**
     * @param relayNumPool
     * @return
     */
    boolean checkRelayNumUnique(RelayNumPool relayNumPool);
}
