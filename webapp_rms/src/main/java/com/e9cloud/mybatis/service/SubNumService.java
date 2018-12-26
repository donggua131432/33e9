package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.domain.SipRelayNumPool;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/7/14.
 */
public interface SubNumService extends IBaseService {

    /**
     * 获取子帐号信息
     * @param subid
     * @return
     */
    SipRelayInfo getSubApp(String subid);
    /**
     * 获取号码池信息列表
     * @param number
     * @return
     */
    SipRelayNumPool getSubNumberByNumber(String subid,String number);

    /**
     * 获取号码池信息列表
     * @param number
     * @return
     */
    List<SipRelayNumPool> getSubNumberByNumber(String number);

    /**
     * 添加子帐号号码池
     * @param sipRelayNumPool
     */
    void addSubNumberPool(SipRelayNumPool sipRelayNumPool);

    PageWrapper getSubNumberList(Page page);

    SipRelayNumPool getSubNumInfoById(Integer id);


    void updateSubNumber(SipRelayNumPool sipRelayNumPool);

    void deleteSubNumber(SipRelayNumPool sipRelayNumPool);

    void deleteSubNumberBySubid(SipRelayNumPool sipRelayNumPool);

    /**
     * @param sipRelayNumPool
     * @return
     */
    boolean checkSubNumUnique(SipRelayNumPool sipRelayNumPool);

    //子账号号码导出
    List<Map<String, Object>> getSubNumberListDownload(Page page);

    void deleteStatusBylink(String[] strId);
}
