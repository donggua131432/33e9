package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.SubApp;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SubAppService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/7.
 */
@Service
public class SubAppServiceImpl extends BaseServiceImpl implements SubAppService {

    /**
     * 查询子账号基本信息
     * @param subid
     */
    @Override
    public SubApp findSubAppBySubid(String subid){
        return this.findObjectByPara(Mapper.SubApp_Mapper.findSubAppBySubid, subid);
    }

    /**
     * 查询子账号基本信息
     * @param map
     */
    @Override
    public SubApp findSubAppByMap(Map map){
        return (SubApp) this.findObjectByMap(Mapper.SubApp_Mapper.findSubAppByMap, map);
    }

    /**
     * 修改子账号基本信息
     * @param subApp
     */
    @Override
    public void updateSubApp(SubApp subApp){
        this.update(Mapper.SubApp_Mapper.updateSubApp, subApp);
    }

    /**
     * 创建子账号基本信息
     * @param subApp
     */
    @Override
    public void saveSubApp(SubApp subApp){
        this.save(Mapper.SubApp_Mapper.saveSubApp, subApp);
    }

    /**
     * 创建子账号基本信息
     * @param subApp
     */
    @Override
    public void deleteSubApp(SubApp subApp){
        this.delete(Mapper.SubApp_Mapper.deleteSubApp, subApp);
    }

    /**
     * 获取应用的子账号列表集合
     * @param appid
     * @return List
     */
    @Override
    public List<SubApp> findSubAppListByAppid(String appid){
        return this.findObjectListByPara(Mapper.SubApp_Mapper.findSubAppListByAppid, appid);
    }
}
