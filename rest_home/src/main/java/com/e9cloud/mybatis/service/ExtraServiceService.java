package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.ExtraService;

import java.util.Map;

/**
 *
 */
public interface ExtraServiceService extends IBaseService {

    /**
     * 查询增值服务基本信息
     * @param map
     */
    ExtraService findExtraServiceByMap(Map map);

}
