package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.MaskRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.MaskRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 隐私号费率配置实现类
 * Created by dukai on 2016/6/1.
 */
@Service
public class MaskRateServiceImpl extends BaseServiceImpl implements MaskRateService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    @Override
    public MaskRate findMaskRateByFeeId(String feeId) {
        return this.findObjectByPara(Mapper.MaskRate_Mapper.selectMaskRateByFeeId, feeId);
    }

    /**
     *保存费率
     * @param maskRate
     */
    @Override
    public void saveMaskRate(MaskRate maskRate) {
        this.save(Mapper.MaskRate_Mapper.insertMaskRate, maskRate);
    }

    /**
     *修改费率
     * @param maskRate
     */
    @Override
    public void updateMaskRate(MaskRate maskRate) {
        this.update(Mapper.MaskRate_Mapper.updateMaskRate, maskRate);
    }

    /**
     *费率信息联合查询
     * @param userAdmin
     * @return
     */
    @Override
    public List<MaskRate> selectMaskRateList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.MaskRate_Mapper.selectMaskRateUnionInfo, userAdmin);
    }

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageMaskRateUnion(Page page) {
        return this.page(Mapper.MaskRate_Mapper.selectPageMaskRateUnion, page);
    }
}
