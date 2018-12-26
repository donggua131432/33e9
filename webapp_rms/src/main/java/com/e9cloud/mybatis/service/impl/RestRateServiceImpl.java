package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RestRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RestRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 */
@Service
public class RestRateServiceImpl extends BaseServiceImpl implements RestRateService{
    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    @Override
    public RestRate findRestRateByFeeId(String feeId) {
        return this.findObjectByPara(Mapper.RestRate_Mapper.selectRestRateByFeeId, feeId);
    }

    /**
     *保存费率
     * @param restRate
     */
    @Override
    public void saveRestRate(RestRate restRate) {
        this.save(Mapper.RestRate_Mapper.insertRestRate, restRate);
    }

    /**
     *修改费率
     * @param restRate
     */
    @Override
    public void updateRestRate(RestRate restRate) {
        this.update(Mapper.RestRate_Mapper.updateRestRate, restRate);
    }

    /**
     *费率信息联合查询
     * @param userAdmin
     * @return
     */
    @Override
    public List<RestRate> selectRestRateList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.RestRate_Mapper.selectRestRateUnionInfo, userAdmin);
    }

    /**
     * 分页联表查询开发接口费率信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageRestRateUnion(Page page) {
        return this.page(Mapper.RestRate_Mapper.selectPageRestRateUnion, page);
    }
}
