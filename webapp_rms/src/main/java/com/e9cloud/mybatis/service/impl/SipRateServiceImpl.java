package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SipRate;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SipRateService;
import org.springframework.stereotype.Service;

/**
 * 中继费率 相关 Service
 * Created by Administrator on 2016/7/19.
 */
@Service
public class SipRateServiceImpl extends BaseServiceImpl implements SipRateService {

    /**
     * 根据子账号获取中继费率
     *
     * @param subid
     * @return
     */
    @Override
    public SipRate getSipRetaBySubid(String subid) {
        return this.findObjectByPara(Mapper.SipRate_Mapper.selectSipRetaBySubid, subid);
    }

    /**
     * 选取标准费率
     *
     * @return
     */
    @Override
    public SipRate getStandardSipReta() {
        return getSipRetaBySubid("0");
    }

    /**
     * 保存中继费率相关信息
     *
     * @param sipRate 中继费率
     */
    @Override
    public void saveSipRate(SipRate sipRate) {
        this.save(Mapper.SipRate_Mapper.insertSelective, sipRate);
    }

    /**
     * 修改中继费率相关信息
     *
     * @param sipRate 中继费率
     */
    @Override
    public void updateSipRate(SipRate sipRate) {
        this.update(Mapper.SipRate_Mapper.updateByPrimaryKeySelective, sipRate);
    }
}
