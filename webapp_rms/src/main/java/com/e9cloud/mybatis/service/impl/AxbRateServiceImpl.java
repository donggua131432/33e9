package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AxbRate;
import com.e9cloud.mybatis.domain.RestRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AxbRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/4/18.
 */
@Service
public class AxbRateServiceImpl extends BaseServiceImpl implements AxbRateService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    @Override
    public AxbRate findAxbRateByFeeId(String feeId) {
        return this.findObjectByPara(Mapper.AxbRate_Mapper.selectAxbRateByFeeId, feeId);
    }


    /**
     *保存费率
     * @param axbRate
     */
    @Override
    public void saveAxbRate(AxbRate axbRate) {
        this.save(Mapper.AxbRate_Mapper.insertAxbRate, axbRate);
    }


    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageAxbRateUnion(Page page) {
        return this.page(Mapper.AxbRate_Mapper.selectPageAxbRateUnion, page);
    }
    /**
     *费率信息联合查询
     * @param userAdmin
     * @return
     */
    @Override
    public List<AxbRate> selectAxbRateList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.AxbRate_Mapper.selectAxbRateUnionInfo, userAdmin);
    }

    @Override
    public  void  updateAxbRate(AxbRate axbRate){
         this.update(Mapper.AxbRate_Mapper.updateAxbRate,axbRate);
    }
}
