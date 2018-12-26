package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.domain.VoiceRate;

import java.util.List;

/**
 * 隐私号费率配置
 * Created by dukai on 2016/6/1.
 */
public interface VoiceRateService extends IBaseService {

    /**
     * 根据费率ID查找费率信息
     * @param feeId 费率ID
     * @return FeeRate
     */
    VoiceRate findVoiceRateByFeeId(String feeId);

    /**
     * 保存费率信息
     * @param voiceRate 费率信息
     */
    void saveVoiceRate(VoiceRate voiceRate);

    /**
     * 修改费率信息
     */
    void updateVoiceRate(VoiceRate voiceRate);

    /**
     * 费率信息联合查询
     */
    List<VoiceRate> selectVoiceRateList(UserAdmin userAdmin);

    /**
     * 分页联表查询费率信息
     * @param page
     * @return
     */
    PageWrapper pageVoiceRateUnion(Page page);
}
