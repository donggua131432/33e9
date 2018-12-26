package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CbVoiceCode;

/**
 * @描述: 语音编码业务操作类
 * @作者: DuKai
 * @创建时间: 2017/4/8 14:54
 * @版本: 1.0
 */
public interface VoiceCodeService extends IBaseService{

    /**
     * 根据业务类型查找云因编码
     * @param busType
     * @return
     */
    CbVoiceCode findVoiceCodeByBusCode(String busType);

}
