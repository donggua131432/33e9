package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.CbVoiceCode;

/**
 * Created by dell on 2017/3/23.
 */
public interface CbVoiceCodeService extends IBaseService {


    /**
     * 保存
     * @param cbVoiceCode
     */
    void saveCbVoiceCode(CbVoiceCode cbVoiceCode);

    /**
     * 修改语音编码
     * @param cbVoiceCode
     */
    void updateCbVoiceCode(CbVoiceCode cbVoiceCode);

    public CbVoiceCode findCbVoiceCodeByBusCode(String busCode);

}
