package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SubApp;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface SubAppService extends IBaseService {


    /**
     * 查询子账号基本信息
     * @param subid
     */
    SubApp findSubAppBySubid(String subid);

    /**
     * 查询子账号基本信息
     * @param map
     */
    SubApp findSubAppByMap(Map map);

    /**
     * 修改子账号基本信息
     * @param subApp
     */
    void updateSubApp(SubApp subApp);

    /**
     * 创建子账号基本信息
     * @param subApp
     */
    void saveSubApp(SubApp subApp);

    /**
     * 创建子账号基本信息
     * @param subApp
     */
    void deleteSubApp(SubApp subApp);

    /**
     * 获取应用的子账号列表集合
     * @param appid
     * @return List
     */
    public List<SubApp> findSubAppListByAppid(String appid);
}
