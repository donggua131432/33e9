package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CallRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CallRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
@Service
public class CallRateServiceImpl extends BaseServiceImpl implements CallRateService{
    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    @Override
    public CallRate findCallRateByFeeId(String feeId) {
        return this.findObjectByPara(Mapper.CallRate_Mapper.selectCallRateByFeeId, feeId);
    }

    /**
     *保存费率
     * @param callRate
     */
    @Override
    public void saveCallRate(CallRate callRate) {
        this.save(Mapper.CallRate_Mapper.insertCallRate, callRate);
    }

    /**
     *修改费率
     * @param callRate
     */
    @Override
    public void updateCallRate(CallRate callRate) {
        this.update(Mapper.CallRate_Mapper.updateCallRate, callRate);
    }

    /**
     *费率信息联合查询
     * @param userAdmin
     * @return
     */
    @Override
    public List<CallRate> selectCallRateList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.CallRate_Mapper.selectCallRateUnionInfo, userAdmin);
    }

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCallRateUnion(Page page) {
        return this.page(Mapper.CallRate_Mapper.selectPageCallRateUnion, page);
    }
}
