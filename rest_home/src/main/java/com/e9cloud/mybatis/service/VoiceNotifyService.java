package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.VoiceTemp;
import com.e9cloud.rest.obt.VoiceReq;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public interface VoiceNotifyService extends IBaseService {

    /*
    * 根据RequestID查询
    * */
    VoiceReq findVoice(VoiceReq voiceReq);

    /*
    * 根据RequestID修改同步状态
    * */
    void updateVoice(VoiceReq voiceReq);

    /*
     * 单个插入
     * */
    void insertVoice(VoiceReq voiceReq);

     /*
     *
     * */
     List<VoiceReq> selectVoiceList(String snCode);

     VoiceTemp getVoiceTempById(Integer id);
}
