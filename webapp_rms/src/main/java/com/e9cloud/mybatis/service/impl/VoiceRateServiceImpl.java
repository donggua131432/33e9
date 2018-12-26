package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.domain.VoiceRate;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 隐私号费率配置实现类
 * Created by dukai on 2016/6/1.
 */
@Service
public class VoiceRateServiceImpl extends BaseServiceImpl implements VoiceRateService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    @Override
    public VoiceRate findVoiceRateByFeeId(String feeId) {
        return this.findObjectByPara(Mapper.VoiceRate_Mapper.selectVoiceRateByFeeId, feeId);
    }

    /**
     *保存费率
     * @param voiceRate
     */
    @Override
    public void saveVoiceRate(VoiceRate voiceRate) {
        this.save(Mapper.VoiceRate_Mapper.insertVoiceRate, voiceRate);
    }

    /**
     *修改费率
     * @param voiceRate
     */
    @Override
    public void updateVoiceRate(VoiceRate voiceRate) {
        this.update(Mapper.VoiceRate_Mapper.updateVoiceRate, voiceRate);
    }

    /**
     *费率信息联合查询
     * @param userAdmin
     * @return
     */
    @Override
    public List<VoiceRate> selectVoiceRateList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.VoiceRate_Mapper.selectVoiceRateUnionInfo, userAdmin);
    }

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageVoiceRateUnion(Page page) {
        return this.page(Mapper.VoiceRate_Mapper.selectPageVoiceRateUnion, page);
    }
}
