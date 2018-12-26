package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SPIPMapper;
import com.e9cloud.pcweb.maskLine.MakePoolRedis;

import java.util.List;
import java.util.Map;

/**
 * sipphone 内网外网映射关系
 * Created by Administrator on 2016/11/7.
 */
public interface SPIPMapperService extends IBaseService {

    /**
     * 根据外网ip和环境变量获取一条映射关系
     * @param spipMapper
     * @return SPIPMapper
     */
    SPIPMapper getByOuterIpAndEnv(SPIPMapper spipMapper);

    /**
     * 得到要注册的信息列表
     * @param envName
     * @return
     */
    List<Map<String, Object>> selectRegAddInfo(String envName);

    /**
     * 更改注册状态
     * @param params
     */
    void updateShowNumRegStatus(Map<String, Object> params);

    /**
     * 得到要注册的信息列表
     * @param envName
     * @return
     */
    List<Map<String, Object>> selectRegDelInfo(String envName);

}
