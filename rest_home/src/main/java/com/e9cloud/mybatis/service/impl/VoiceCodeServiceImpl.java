package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CbVoiceCode;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceCodeService;
import org.springframework.stereotype.Service;

/**
 * @描述: 语音编码实现类
 * @作者: DuKai
 * @创建时间: 2017/4/8 14:57
 * @版本: 1.0
 */
@Service
public class VoiceCodeServiceImpl extends BaseServiceImpl implements VoiceCodeService {

    /**
     * 根据业务类型查找云因编码
     * @param busType
     * @return
     */
    @Override
    public CbVoiceCode findVoiceCodeByBusCode(String busType) {
        return this.findObjectByPara(Mapper.CbVoiceCode_Mapper.selectVoiceCodeByBusCode, busType);
    }
}
