package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.FixPhone;
import com.e9cloud.mybatis.domain.SipPhone;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.domain.SipRelayNumPool;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/7/14.
 */
public interface PubNumResPoolService extends IBaseService {

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
    SipRelayNumPool getSubNumberByNumber(String subid, String number);

    /**
     * 获取号码池信息列表
     * @param sipPhone
     * @return
     */
    List<SipPhone> getPubNumResPoolBySipPhone(String sipPhone);

    /**
     * 判断sipphone是否注册
     * @param sipPhone
     * @return
     */
    boolean checkRegisterSipphone(String sipPhone);

    /**
     * 获取号码池信息列表
     * @param sipPhone
     * @return
     */
    List<FixPhone> getPubNumResPoolByNum(String sipPhone);

    /**
     * 添加公共号码管理池
     * @param sipPhone
     */
    void addSipPhone(SipPhone sipPhone);

    void addFixPhone(FixPhone fixPhone);

    PageWrapper getSipPhoneList(Page page);

    PageWrapper getFixPhoneList(Page page);

    List<SipPhone> getSipPhoneById(String id);

    FixPhone getFixPhoneById(String id);


    void updateSubNumber(SipRelayNumPool sipRelayNumPool);

    void deleteSubNumber(SipRelayNumPool sipRelayNumPool);

    void deleteSubNumberBySubid(SipRelayNumPool sipRelayNumPool);

    /**
     * @param sipRelayNumPool
     * @return
     */
    boolean checkSubNumUnique(SipRelayNumPool sipRelayNumPool);

    //公共号码管理池导出
    List<Map<String, Object>> downloadSipPhonePool(Page page);

    //公共号码管理池导出
    List<Map<String, Object>> downloadFixPhonePool(Page page);


    void deleteSipPhoneByIds(String[] strId);

    void deleteFixPhoneByIds(String[] strId);

    /**
     * 为下拉框获取IPPort
     * @return
     */
    List<SipPhone> getIPPortList();
}
