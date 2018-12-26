package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.VoiceTemp;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceNotifyService;
import com.e9cloud.rest.obt.VoiceReq;
import com.e9cloud.rest.service.VoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
@Service
public class VoiceNotifyServiceImpl extends BaseServiceImpl implements VoiceNotifyService {

    @Override
    public VoiceReq findVoice(VoiceReq voiceReq) {
        return (VoiceReq)this.findObject(Mapper.VOICE_Mapper.queryVoice,voiceReq);
    }

    @Override
    public List<VoiceReq> selectVoiceList(String snCode) {
        return (List)this.findObjectListByPara(Mapper.VOICE_Mapper.selectVoiceList,snCode);
    }

    @Override
    public void insertVoice(VoiceReq voiceReq) {
        this.save(Mapper.VOICE_Mapper.insertVoice,voiceReq);
    }

    @Override
    public void updateVoice(VoiceReq voiceReq) {
        this.update(Mapper.VOICE_Mapper.updateVoice, voiceReq);
    }

    @Override
    public VoiceTemp getVoiceTempById(Integer id) {
        return this.findObject(Mapper.VOICE_Mapper.selectVoiceTempById, id);
    }
}
