package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.IvrRate;
import com.e9cloud.mybatis.domain.SipphonRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.IvrRateService;
import com.e9cloud.mybatis.service.PhoneRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dukai on 2016/10/28.
 */
@Service
public class IvrRateServiceImpl extends BaseServiceImpl implements IvrRateService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    @Override
    public IvrRate findIvrRateByFeeId(String feeId) {
        return this.findObjectByPara(Mapper.IvrRate_Mapper.selectIvrRateByFeeId, feeId);
    }

    /**
     *保存费率
     * @param ivrRate
     */
    @Override
    public void saveIvrRate(IvrRate ivrRate) {
        this.save(Mapper.IvrRate_Mapper.insertIvrRate, ivrRate);
    }

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageIvrRateUnion(Page page) {
        return this.page(Mapper.IvrRate_Mapper.selectPageIvrRateUnion, page);
    }

    /**
     *修改费率
     * @param ivrRate
     */
    @Override
    public void updateIvrRate(IvrRate ivrRate) {
        this.update(Mapper.IvrRate_Mapper.updateIvrRate, ivrRate);
    }

    /**
     *费率信息联合查询
     * @param userAdmin
     * @return
     */
    @Override
    public List<IvrRate> selectIvrRateList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.IvrRate_Mapper.selectIvrRateUnionInfo, userAdmin);
    }
}
