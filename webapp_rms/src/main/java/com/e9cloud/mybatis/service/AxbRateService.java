package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AxbRate;
import com.e9cloud.mybatis.domain.RestRate;
import com.e9cloud.mybatis.domain.UserAdmin;

import java.util.List;

/**
 * Created by admin on 2017/4/18.
 */
public interface AxbRateService extends IBaseService {

    /**
     * 根据费率ID查找开发接口费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    AxbRate findAxbRateByFeeId(String feeId);


    void saveAxbRate(AxbRate axbRate);


    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    PageWrapper pageAxbRateUnion(Page page);


    /**
     * 费率信息联合查询
     */
    List<AxbRate> selectAxbRateList(UserAdmin userAdmin);



    /**
     * 修改费率信息
     */
    void updateAxbRate(AxbRate axbRate);

}
