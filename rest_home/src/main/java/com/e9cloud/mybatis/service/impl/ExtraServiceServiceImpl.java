package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.ExtraService;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.ExtraServiceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/26.
 */
@Service
public class ExtraServiceServiceImpl extends BaseServiceImpl implements ExtraServiceService {

    /**
     * 查询增值服务基本信息
     * @param map
     */
    @Override
    public ExtraService findExtraServiceByMap(Map map){
        return (ExtraService) this.findObjectByMap(Mapper.ExtraService_Mapper.findExtraServiceByMap, map);
    }

}
