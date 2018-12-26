package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.domain.VoiceVerifyRate;

import java.util.List;

/**
 * 语音验证码费率配置
 * Created by hzd on 2017/5/2.
 */
public interface VoiceVerifyRateService extends IBaseService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    VoiceVerifyRate findVoiceVerifyRateByFeeId(String feeId);

    /**
     * 保存费率信息
     * @param voiceVerifyRate 费率信息
     */
    void saveVoiceVerifyRate(VoiceVerifyRate voiceVerifyRate);

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    PageWrapper pageVoiceVerifyRateUnion(Page page);

    /**
     * 费率信息联合查询
     */
    List<VoiceVerifyRate> selectVoiceVerifyRateList(UserAdmin userAdmin);

    /**
     * 修改费率信息
     */
    void updateVoiceVerifyRate(VoiceVerifyRate voiceVerifyRate);

}
