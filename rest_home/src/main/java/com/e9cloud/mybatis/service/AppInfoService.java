package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;

import java.util.List;

/**
 *
 */
public interface AppInfoService extends IBaseService {


    /**
     * 查询应用基本信息
     * @param appid
     */
    AppInfo getAppInfoByAppid(String appid);

    /**
     * 查询显号池
     * @param appid
     * @param isDefined
     */
    List<String> selectRelayNumpoolList(String appid, String isDefined);


    /**
     * 查询所有app数据
     * @return
     */
    List<AppInfo> findAll();

    /**
     * 查询app应用扩展信息
     * @param appid
     * @return
     */
    AppInfoExtra findAppInfoExtraByAppid(String appid);

}
