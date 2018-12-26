package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CallRate;
import com.e9cloud.mybatis.domain.UserAdmin;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public interface CallRateService extends IBaseService {
    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    CallRate findCallRateByFeeId(String feeId);

    /**
     * 保存费率信息
     * @param callRate 费率信息
     */
    void saveCallRate(CallRate callRate);

    /**
     * 修改费率信息
     */
    void updateCallRate(CallRate callRate);

    /**
     * 费率信息联合查询
     */
    List<CallRate> selectCallRateList(UserAdmin userAdmin);

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    PageWrapper pageCallRateUnion(Page page);

}
