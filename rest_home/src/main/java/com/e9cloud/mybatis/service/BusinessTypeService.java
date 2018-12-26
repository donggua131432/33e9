package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.BusinessType;

import java.util.Map;

/**
 * @描述: 业务类型
 * @作者: DuKai
 * @创建时间: 2017/4/19 16:13
 * @版本: 1.0
 */
public interface BusinessTypeService extends IBaseService{

    /**
     * 根据参数查找业务类型
     * @param map
     * @return
     */
    BusinessType getBusinessType(Map<String,Object> map);
}
