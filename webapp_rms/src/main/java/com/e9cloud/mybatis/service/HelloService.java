package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

/**
 * Created by Administrator on 2015/12/14.
 */
public interface HelloService extends IBaseService{

    public String getValueByKey(String key);
}
