package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SPIPMapper;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SPIPMapperService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * sipphone 内网外网映射关系
 * Created by Administrator on 2016/11/7.
 */
@Service
public class SPIPMapperServiceImpl extends BaseServiceImpl implements SPIPMapperService {

    /**
     * 根据外网ip和环境变量获取一条映射关系
     *
     * @param spipMapper
     * @return SPIPMapper
     */
    @Override
    public SPIPMapper getByOuterIpAndEnv(SPIPMapper spipMapper) {
        String envName = System.getenv("envName");
        spipMapper.setEnvName(envName);
        logger.info("envName:{}", envName);
        return this.findObject(Mapper.SPIPMapper_Mapper.selectByOuterIpAndEnv, spipMapper);
    }

    /**
     * 得到要注册的信息列表
     *
     * @param envName
     * @return
     */
    @Override
    public List<Map<String, Object>> selectRegAddInfo(String envName) {
        return this.findObjectListByPara(Mapper.SPIPMapper_Mapper.selectRegAddInfo, envName);
    }

    /**
     * 更改注册状态
     *
     * @param params
     */
    @Override
    public void updateShowNumRegStatus(Map<String, Object> params) {
        this.update(Mapper.SPIPMapper_Mapper.updateShowNumRegStatus, params);
    }

    /**
     * 得到要注册的信息列表
     *
     * @param envName
     * @return
     */
    @Override
    public List<Map<String, Object>> selectRegDelInfo(String envName) {
        return this.findObjectListByPara(Mapper.SPIPMapper_Mapper.selectRegDelInfo, envName);
    }
}
