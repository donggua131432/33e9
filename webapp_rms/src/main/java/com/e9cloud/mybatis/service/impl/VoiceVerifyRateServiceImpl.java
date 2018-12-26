package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.domain.VoiceVerifyRate;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceVerifyRateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 语音验证码费率配置实现类
 * Created by hzd on 2017/5/2.
 */
@Service
public class VoiceVerifyRateServiceImpl extends BaseServiceImpl implements VoiceVerifyRateService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    @Override
    public VoiceVerifyRate findVoiceVerifyRateByFeeId(String feeId) {
        return this.findObjectByPara(Mapper.VoiceVerifyRate_Mapper.selectVoiceVerifyRateByFeeId, feeId);
    }

    /**
     *保存费率
     * @param voiceVerifyRate
     */
    @Override
    public void saveVoiceVerifyRate(VoiceVerifyRate voiceVerifyRate) {
        this.save(Mapper.VoiceVerifyRate_Mapper.insertVoiceVerifyRate, voiceVerifyRate);
    }

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageVoiceVerifyRateUnion(Page page) {
        return this.page(Mapper.VoiceVerifyRate_Mapper.selectPageVoiceVerifyRateUnion, page);
    }

    /**
     *费率信息联合查询
     * @param userAdmin
     * @return
     */
    @Override
    public List<VoiceVerifyRate> selectVoiceVerifyRateList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.VoiceVerifyRate_Mapper.selectVoiceVerifyRateUnionInfo, userAdmin);
    }

    /**
     *修改费率
     * @param voiceVerifyRate
     */
    @Override
    public void updateVoiceVerifyRate(VoiceVerifyRate voiceVerifyRate) {
        this.update(Mapper.VoiceVerifyRate_Mapper.updateVoiceVerifyRate, voiceVerifyRate);
    }

}
