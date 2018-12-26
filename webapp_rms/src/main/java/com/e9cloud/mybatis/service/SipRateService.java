package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SipRate;

/**
 * 中继费率 相关 Service
 * Created by Administrator on 2016/7/19.
 */
public interface SipRateService extends IBaseService {

    /**
     * 根据子账号获取中继费率
     * @return
     */
    SipRate getSipRetaBySubid(String subid);

    /**
     * 选取标准费率
     * @return
     */
    SipRate getStandardSipReta();

    /**
     * 保存中继费率相关信息
     * @param sipRate 中继费率
     */
    void saveSipRate(SipRate sipRate);

    /**
     * 修改中继费率相关信息
     * @param sipRate 中继费率
     */
    void updateSipRate(SipRate sipRate);

}
