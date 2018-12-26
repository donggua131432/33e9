package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AxbNumRelation;
import com.e9cloud.rest.obt.AxbNumber;


import java.util.List;
import java.util.Map;

/**
 * @描述: 虚拟小Rest接口服务
 * @作者: DuKai
 * @创建时间: 2017/4/18 17:26
 * @版本: 1.0
 */
public interface AxbNumberService extends IBaseService{

    /**
     * 获取虚拟号映射关系集合
     * @param appid
     * @return
     */
    List<AxbNumRelation> getAxbNumberList(String appid);

    AxbNumRelation getAxbNumberRelation(Map<String, Object> map);

    /**
     * 获取虚拟号码集合
     * @param map
     * @return
     */
    List<String> getNumXList(Map<String,Object> map);


    /**
     * 获取可用的X号码集合
     * @param map
     * @return
     */
    List<AxbNumber> getTelXList(Map<String,Object> map);

}
