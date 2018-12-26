package com.e9cloud.mybatis.service.impl;

import com.e9cloud.cache.LocalCacheMap;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.domain.VoiceVerifyTemp;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceVerifyService;
import com.e9cloud.util.InitValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @描述: 语音验证码服务
 * @作者: DuKai
 * @创建时间: 2017/5/5 14:24
 * @版本: 1.0
 */
@Service
public class VoiceVerifyServiceImpl extends BaseServiceImpl implements VoiceVerifyService{

    /**
     * 语音验证码模板获取
     * @param map
     * @return
     */
    @Override
    public VoiceVerifyTemp getVoiceVerifyTempByMap(Map map) {
        return (VoiceVerifyTemp) this.findObjectByMap(Mapper.VoiceVerifyTemp_Mapper.selectVoiceVerifyTemp, map);
    }

    /**
     * 语音验证码显号池获取
     * @param appid
     * @return
     */
    @Override
    public List<String> getDisplayNumList(String appid) {
        String cacheKey = appid+"voiceVerifyNumberCache";
        LocalCacheMap<List<String>> voiceVerifyNumberCache = InitValue.voiceVerifyNumberCache;
        //先从缓存容器中加载，不存在或超时再查数据库
        if(voiceVerifyNumberCache==null){
            List<String> list = this.findObjectList(Mapper.VoiceVerifyTemp_Mapper.selectDisplayNumPool, appid);
            if(list!=null && list.size()>0){
                //放入缓存容器，一分钟有效
                InitValue.setVoiceVerifyNumberCache(cacheKey);
                InitValue.voiceVerifyNumberCache.put(cacheKey,list,60);
            }
            return list;
        }else{
            List<String> displayNumList= InitValue.voiceVerifyNumberCache.get(cacheKey);
            if(displayNumList == null){
                List<String> list = this.findObjectList(Mapper.VoiceVerifyTemp_Mapper.selectDisplayNumPool, appid);
                if(list!=null && list.size()>0){
                    //放入缓存容器，一分钟有效
                    InitValue.voiceVerifyNumberCache.put(cacheKey,list,60);
                    return list;
                }
            }
            return displayNumList;
        }
    }
}
