package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.CbVoiceCode;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.mybatis.service.CbVoiceCodeService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/3/3.
 */
@Service
public class CbVoiceCodeServiceImpl extends BaseServiceImpl implements CbVoiceCodeService {

    /**
     * 保存语音编解码
     * @param cbVoiceCode
     */
    @Override
    public void saveCbVoiceCode(CbVoiceCode cbVoiceCode) {
        this.save(Mapper.CbVoiceCode_Mapper.insertSelective, cbVoiceCode);
    }

    @Override
    public void updateCbVoiceCode(CbVoiceCode cbVoiceCode) {
        this.update(Mapper.CbVoiceCode_Mapper.updateByPrimaryKeySelective, cbVoiceCode);
    }

    @Override
    public CbVoiceCode findCbVoiceCodeByBusCode(String busCode) {
        return this.findObjectByPara(Mapper.CbVoiceCode_Mapper.findCbVoiceCodeByBusCode, busCode);
    }

}
