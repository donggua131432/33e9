package com.e9cloud.mybatis.service.impl;

import com.e9cloud.cache.LocalCacheMap;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SipPhoneService;
import com.e9cloud.util.InitValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dukai on 2016/10/29.
 */
@Service
public class SipPhoneServiceImpl extends BaseServiceImpl implements SipPhoneService {
    /**
     * 获取和App相关联的sipPhone号码列表
     * @param appid
     * @return
     */
    @Override
    public List<SpApplyNum> getSpApplyNumList(String appid) {
        String cacheKey = appid+"sipPhoneCache";
        LocalCacheMap<List<SpApplyNum>> sipPhponeCache =InitValue.sipPhponeCache;
        if(sipPhponeCache==null){
            List<SpApplyNum> spApplyNumList = this.findObjectList(Mapper.SpApplyNum_Mapper.selectSipPhoneByAppId, appid);
            if(spApplyNumList!=null && spApplyNumList.size()>0){
                //设置缓存容量的大小
                InitValue.setSipPhponeCache(cacheKey);
                //放入缓存容器，10秒钟有效
                InitValue.sipPhponeCache.put(cacheKey,spApplyNumList,60);
            }
            return spApplyNumList;
        }else {
            List<SpApplyNum> spApplyNumList = InitValue.sipPhponeCache.get(cacheKey);
            if(spApplyNumList == null){
                List<SpApplyNum> list = this.findObjectList(Mapper.SpApplyNum_Mapper.selectSipPhoneByAppId, appid);
                if(list!=null && list.size()>0){
                    //放入缓存容器，10秒钟有效
                    InitValue.sipPhponeCache.put(cacheKey,list,60);
                    return list;
                }
            }
            return spApplyNumList;
        }
    }

    /**
     * 获取sipPhone状态信息
     * @param spApplyNum
     * @return
     */
    @Override
    public SpApplyNum getSipPhoneStat(SpApplyNum spApplyNum) {
        return this.findObject(Mapper.SpApplyNum_Mapper.selectSipPhoneStat,spApplyNum);
    }

    /**
     * 更改sipPhone状态信息
     * @param spApplyNum
     */
    @Override
    public void updateSipPhoneStat(SpApplyNum spApplyNum) {
        this.update(Mapper.SpApplyNum_Mapper.updateSipPhoneStat,spApplyNum);
    }
}
