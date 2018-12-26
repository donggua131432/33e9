package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.MaskRate;
import com.e9cloud.mybatis.domain.SipphonRate;
import com.e9cloud.mybatis.domain.UserAdmin;

import java.util.List;

/**
 * Created by admin on 2016/10/28.
 */
public interface PhoneRateService extends IBaseService {
    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    SipphonRate findPhoneRateByFeeId(String feeId);


    /**
     * 保存费率信息
     * @param sipphonRate 费率信息
     */
    void savePhoneRate(SipphonRate sipphonRate);

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    PageWrapper pagePhoneRateUnion(Page page);

    /**
     * 修改费率信息
     */
    void updatePhoneRate(SipphonRate sipphonRate);

    /**
     * 费率信息联合查询
     */
    List<SipphonRate> selectPhoneRateList(UserAdmin userAdmin);
}
