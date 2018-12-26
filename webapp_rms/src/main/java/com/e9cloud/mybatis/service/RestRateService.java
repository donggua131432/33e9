package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CallRate;
import com.e9cloud.mybatis.domain.RestRate;
import com.e9cloud.mybatis.domain.UserAdmin;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public interface RestRateService extends IBaseService {
    /**
     * 根据费率ID查找开发接口费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    RestRate findRestRateByFeeId(String feeId);

    /**
     * 保存开发接口费率信息
     * @param restRate 费率信息
     */
    void saveRestRate(RestRate restRate);

    /**
     * 修改开发接口费率信息
     */
    void updateRestRate(RestRate restRate);

    /**
     * 开发接口费率信息联合查询
     */
    List<RestRate> selectRestRateList(UserAdmin userAdmin);

    /**
     * 分页联表查询开发接口费率信息
     * @param page
     * @return
     */
    PageWrapper pageRestRateUnion(Page page);

}
