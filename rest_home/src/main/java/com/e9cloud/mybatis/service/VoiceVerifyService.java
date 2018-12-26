package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.VoiceVerifyTemp;

import java.util.List;
import java.util.Map;

/**
 * @描述: 语音验证码服务
 * @作者: DuKai
 * @创建时间: 2017/5/5 14:18
 * @版本: 1.0
 */
public interface VoiceVerifyService extends IBaseService{

    /**
     * 获取语音模板
     * @param map
     * @return
     */
    VoiceVerifyTemp getVoiceVerifyTempByMap(Map map);

    /**
     * 获取语音验证码显号池
     * @param appid
     * @return
     */
    List<String> getDisplayNumList(String appid);
}
