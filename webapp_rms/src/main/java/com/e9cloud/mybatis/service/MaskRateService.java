package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.MaskRate;
import com.e9cloud.mybatis.domain.UserAdmin;

import java.util.List;

/**
 * 隐私号费率配置
 * Created by dukai on 2016/6/1.
 */
public interface MaskRateService extends IBaseService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    MaskRate findMaskRateByFeeId(String feeId);

    /**
     * 保存费率信息
     * @param maskRate 费率信息
     */
    void saveMaskRate(MaskRate maskRate);

    /**
     * 修改费率信息
     */
    void updateMaskRate(MaskRate maskRate);

    /**
     * 费率信息联合查询
     */
    List<MaskRate> selectMaskRateList(UserAdmin userAdmin);

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    PageWrapper pageMaskRateUnion(Page page);
}
