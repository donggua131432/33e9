package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.MaskRate;
import com.e9cloud.mybatis.domain.SipphonRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.PhoneRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/10/28.
 */
@Service
public class PhoneRateServiceImpl extends BaseServiceImpl implements PhoneRateService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    @Override
    public SipphonRate findPhoneRateByFeeId(String feeId) {
        return this.findObjectByPara(Mapper.SipphonRate_Mapper.selectPhoneRateByFeeId, feeId);
    }

    /**
     *保存费率
     * @param sipphonRate
     */
    @Override
    public void savePhoneRate(SipphonRate sipphonRate) {
        this.save(Mapper.SipphonRate_Mapper.insertPhoneRate, sipphonRate);
    }

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pagePhoneRateUnion(Page page) {
        return this.page(Mapper.SipphonRate_Mapper.selectPagePhoneRateUnion, page);
    }

    /**
     *修改费率
     * @param sipphonRate
     */
    @Override
    public void updatePhoneRate(SipphonRate sipphonRate) {
        this.update(Mapper.SipphonRate_Mapper.updatePhoneRate, sipphonRate);
    }

    /**
     *费率信息联合查询
     * @param userAdmin
     * @return
     */
    @Override
    public List<SipphonRate> selectPhoneRateList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.SipphonRate_Mapper.selectPhoneRateUnionInfo, userAdmin);
    }
}
