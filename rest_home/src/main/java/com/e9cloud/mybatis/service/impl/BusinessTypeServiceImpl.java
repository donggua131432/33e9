package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.BusinessTypeService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @描述: 业务类型
 * @作者: DuKai
 * @创建时间: 2017/4/19 16:16
 * @版本: 1.0
 */
@Service
public class BusinessTypeServiceImpl extends BaseServiceImpl implements BusinessTypeService {


    /**
     * 根据参数查找业务类型
     * @param map
     * @return
     */
    @Override
    public BusinessType getBusinessType(Map<String, Object> map) {
        return this.findObjectByMap(Mapper.BusinessType_Mapper.selectBusinessTypeByParam, map);
    }
}
