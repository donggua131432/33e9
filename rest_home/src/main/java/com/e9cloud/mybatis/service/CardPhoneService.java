package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CardPhone;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface CardPhoneService extends IBaseService {


    /**
     * 查询物联网卡绑定号码基本信息
     * @param map
     */
    CardPhone findCardPhoneByMap(Map map);

    /**
     * 绑定物联网卡手机号
     * @param cardPhone
     */
    void bindCardPhone(CardPhone cardPhone);

    /**
     * 解绑物联网卡手机号
     * @param cardPhone
     */
    void unbindCardPhone(CardPhone cardPhone);

    /**
     * 获取物联网卡绑定的号码对象列表集合
     * @param card
     * @return List
     */
    List<CardPhone> findCardPhoneListBycard(Map card);

    /**
     * 获取物联网卡绑定的号码列表集合
     * @param card
     * @return List
     */
    List<String> findPhoneListBycard(Map card);
}
