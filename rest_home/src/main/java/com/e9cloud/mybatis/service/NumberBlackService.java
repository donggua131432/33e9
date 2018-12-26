package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;

/**
 *
 */
public interface NumberBlackService extends IBaseService {

    /**
     * 获取黑名单号码列表集合
     * @return List
     */
    public List<String> findList();
}
