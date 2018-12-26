package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/20.
 */
@Service
public class AppServiceImpl extends BaseServiceImpl implements AppService {

    /**
     * 获取用户的应用总数量
     *
     * @param map
     * @return List
     */
    @Override
    public int findAppListCountByMap(Map map) {
        return (Integer)this.findObjectByMap(Mapper.App_Mapper.findAppListCountByMap,map);
    }

    /**
     * 获取用户的应用列表集合
     *
     * @param map
     * @return List
     */
    @Override
    public List<AppInfo> findAppListByMap(Map map) {
        return this.findObjectListByMap(Mapper.App_Mapper.findAppListByMap,map);
    }

    /**
     * 获取用户的应用列表集合
     * @param map
     * @return
     */
    @Override
    public List<AppInfo> selectAppInfoListByMap(Map map) {
        return this.findObjectListByMap(Mapper.App_Mapper.selectAppInfoListByMap, map);
    }

    /**
     * APP应用查看
     *
     * @param appid
     * @return AppInfo
     */
    @Override
    public AppInfo findAppInfoByAppId(String appid) {
        return this.findObjectByPara(Mapper.App_Mapper.findAppInfoByAppId,appid);
    }

    /**
     * APP应用查看
     *
     * @param id
     * @return AppInfo
     */
    @Override
    public AppInfo findAppInfoById(int id) {
        return this.findObject(Mapper.App_Mapper.findAppInfoById,id);
    }

    /**
     * 应用信息和子应用信息联合查询
     * @param sid
     * @return
     */
    @Override
    public List<AppInfo> findAppInfoUnionSubApp(String sid) {
        return this.findObjectListByPara(Mapper.App_Mapper.selectAppInfoUnionSubApp, sid);
    }

    /**
     * 查询所有有限的APP信息
     */
    @Override
    public List<AppInfo> findAllApp() {
        return this.findObjectList(Mapper.App_Mapper.findAllApp,null);
    }

    /**
     * 查询所有应用信息
     * @param sid
     * @return
     */
    @Override
    public List<AppInfo> findALLAppInfo(String sid) {
        return this.findObjectListByPara(Mapper.App_Mapper.selectAllAppInfo, sid);
    }

    /**
     * 根据sid、busType查询app下拉列表
     *
     * @param map
     * @return
     */
    @Override
    public List<AppInfo> getAllSpInfo(Map map) {
        return this.findObjectListByMap(Mapper.App_Mapper.getAllSpInfo, map);
    }
}
