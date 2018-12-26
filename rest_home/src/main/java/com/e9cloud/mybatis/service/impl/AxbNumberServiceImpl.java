package com.e9cloud.mybatis.service.impl;

import com.e9cloud.cache.LocalCacheMap;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AxbNumRelation;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AxbNumberService;
import com.e9cloud.rest.obt.AxbNumber;
import com.e9cloud.util.InitValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @描述: 虚拟小号Rest接口实现类
 * @作者: DuKai
 * @创建时间: 2017/4/18 17:31
 * @版本: 1.0
 */
@Service
public class AxbNumberServiceImpl extends BaseServiceImpl implements AxbNumberService {
    /**
     * 获取虚拟号映射关系集合
     * @param appid
     * @return
     */
    @Override
    public List<AxbNumRelation> getAxbNumberList(String appid) {
        return this.findObjectListByPara(Mapper.AxbNumber_Mapper.selectNumXByAppId, appid);
    }

    public AxbNumRelation getAxbNumberRelation(Map<String, Object> map){
        return this.findObjectByMap(Mapper.AxbNumber_Mapper.selectNumXByParam, map);
    }

    /**
     * 获取虚拟号码集合
     * @param map
     * @return
     */
    @Override
    public List<String> getNumXList(Map<String, Object> map) {
        String cacheKey = map.get("appid")+"abxNumberCache";
        //先从缓存容器中加载，不存在或超时再查数据库
        LocalCacheMap<List<String>> voiceVerifyNumberCache = InitValue.abxNumberCache;
        if(voiceVerifyNumberCache == null){
            List<String> list = this.findObjectListByMap(Mapper.AxbNumber_Mapper.selectNumXPool, map);
            if(list!=null && list.size()>0){
                //设置缓存的key
                InitValue.setAbxNumberCacheKey(cacheKey);
                //放入缓存容器，一分钟有效
                InitValue.abxNumberCache.put(cacheKey,list,60);
            }
            return list;
        }else{
            List<String> xNumberList= InitValue.abxNumberCache.get(cacheKey);
            if(xNumberList == null){
                List<String> list = this.findObjectListByMap(Mapper.AxbNumber_Mapper.selectNumXPool, map);
                if(list!=null && list.size()>0){
                    //设置缓存的key
                    InitValue.setAbxNumberCacheKey(cacheKey);
                    //放入缓存容器，一分钟有效
                    InitValue.abxNumberCache.put(cacheKey,list,60);
                    return list;
                }
            }
            return xNumberList;
        }

    }

    /**
     * 获取可用的X号码集合
     * @param map
     * @return
     */
    @Override
    public List<AxbNumber> getTelXList(Map<String, Object> map) {
        return this.findObjectListByMap(Mapper.AxbNumber_Mapper.selectNumXByUinJoin, map);
    }
}
