package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppInfo;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface AppService extends IBaseService {

    /**
     * 获取用户的应用总数量
     * @param map
     * @return List
     */
    public int findAppListCountByMap(Map map);
    /**
     * 获取用户的应用列表分页集合
     * @param map
     * @return List
     */
    List<AppInfo> findAppListByMap(Map map);

    /**
     * 获取用户的应用列表集合
     * @param map
     * @return List
     */
    List<AppInfo> selectAppInfoListByMap(Map map);

    /**
     * APP应用查看
     * @param appid
     * @return AppInfo
     */
    AppInfo findAppInfoByAppId(String appid);

    /**
     * APP应用查看
     * @param id
     * @return AppInfo
     */
    AppInfo findAppInfoById(int id);

    /**
     * 应用信息和子应用信息联合查询
     * @param sid
     * @return
     */
    List<AppInfo> findAppInfoUnionSubApp(String sid);

    /**
     * 查询所有应用信息
     * @param sid
     * @return
     */
    List<AppInfo> findALLAppInfo(String sid);

    /**
     * 查询所有有限的APP信息
     */
    List<AppInfo> findAllApp();

    /**
     * 查询
     * @param map
     * @return
     */
    List<AppInfo> getAllSpInfo(Map map);

}
