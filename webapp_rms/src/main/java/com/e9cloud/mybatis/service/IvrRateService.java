package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.IvrRate;
import com.e9cloud.mybatis.domain.UserAdmin;

import java.util.List;

/**
 * 云总机 相关 Service
 * Created by dukai on 2017/2/13.
 */
public interface IvrRateService extends IBaseService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    IvrRate findIvrRateByFeeId(String feeId);


    /**
     * 保存费率信息
     * @param ivrRate 费率信息
     */
    void saveIvrRate(IvrRate ivrRate);

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    PageWrapper pageIvrRateUnion(Page page);

    /**
     * 修改费率信息
     */
    void updateIvrRate(IvrRate ivrRate);

    /**
     * 费率信息联合查询
     */
    List<IvrRate> selectIvrRateList(UserAdmin userAdmin);

}
