package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppNumberRest;

import java.util.List;

/**
 * Created by wj on 2016/3/3.
 */
public interface AppNumberRestService extends IBaseService {

    /**
     * 根据appid查询所有的显号集合
     * @param appid
     * @return List
     */
    List<String> findAppNumberRestListByAppid(String appid);
}
