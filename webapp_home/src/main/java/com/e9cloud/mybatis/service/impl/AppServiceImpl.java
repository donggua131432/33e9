package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.PinyinUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppService;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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

    @Override
    public int findAppNumCountByAppID(String appid) {
        return (Integer)this.findObject(Mapper.App_Mapper.findAppNumCountByAppID,appid);
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
     * 创建APP子应用
     *
     * @param app
     * @return
     */
    @Override
    public void saveSubApp(SubApp app) {
        this.save(Mapper.App_Mapper.saveSubApp,app);
    }


    @Override
    public void saveAppExtra(AppInfoExtra appInfoExtra) {
        this.save(Mapper.AppInfoExtra_Mapper.saveAppExtra,appInfoExtra);
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
     * APP应用查看(云话机)
     *
     * @param appid
     * @return AppInfoExtra
     */
    @Override
    public AppInfoExtra findAppExtraByAppId(String appid) {
        return this.findObjectByPara(Mapper.AppInfoExtra_Mapper.findAppExtraByAppId,appid);
    }


    /**
     * APP应用查看(云总机)
     *
     * @param appid
     * @return EccAppInfo
     */
    @Override
    public EccAppInfo findAppEccByAppId(String appid) {
        return this.findObjectByPara(Mapper.EccAppInfo_Mapper.findAppEccByAppId,appid);
    }


    @Override
    public Map<String, Object> findCityEccByAppId(String appid) {
        return this.findObject(Mapper.EccAppInfo_Mapper.findCityEccByAppId, appid);
    }


    @Override
    public PageWrapper pageEccNum(Page page) {
        return this.page(Mapper.EccAppInfo_Mapper.pageEccNum, page);
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
     * 只针对sip和智能云调度    一个账户一个appid
     * @param sid sid
     * @param busType 应用类型
     * @return AppInfo
     */
    @Override
    public AppInfo findAppInfoBySIdAndBusType(String sid, String busType){
        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("busType", busType);

        return this.findObjectByMap(Mapper.App_Mapper.findAppInfoBySIdAndBusType, params);
    }

    /**
     * 创建APP应用
     * 默认创建子应用
     * @param app
     * @return
     */
    @Override

    public void saveApp(AppInfo app) throws BadHanyuPinyinOutputFormatCombination {
        app.setPinyin(PinyinUtils.toPinYin(app.getAppName()));
        this.save(Mapper.App_Mapper.saveApp,app);
        String subId = ID.randomUUID();
        String subName = app.getAppName();
        String appid = app.getAppid();
        SubApp subApp = new SubApp();
        subApp.setSubid(subId);
        subApp.setAppid(appid);
        subApp.setSubName(subName);
        subApp.setCreateDate(new Date());
        this.saveSubApp(subApp);
    }

    /**
     * 编辑APP应用
     *
     * @param app
     * @return
     */
    @Override
    public void updateApp(AppInfo app) throws BadHanyuPinyinOutputFormatCombination{
        if(Tools.isNotNullStr(app.getAppName())){
            app.setPinyin(PinyinUtils.toPinYin(app.getAppName()));
        }
        this.update(Mapper.App_Mapper.updateApp,app);
    }

    /**
     * 编辑APP应用-----(云话机)
     * @param appInfoExtra
     * @return
     */
    @Override
    public void updateAppExtra(AppInfoExtra appInfoExtra) {
        this.update(Mapper.AppInfoExtra_Mapper.updateAppExtra,appInfoExtra);
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
     * 查询所有应用信息
     * @param sid
     * @return
     */
    @Override
    public List<AppInfo> findALLAppInfo(String sid) {
        return this.findObjectListByPara(Mapper.App_Mapper.selectAllAppInfo, sid);
    }

    /**
     * 导出所有ECC应用信息
     * @param page 分页信息
     * @return
     */
    @Override
    public List<Map<String, Object>> getAppInfoList(Page page) {
        return this.download(Mapper.EccAppInfo_Mapper.downloadpageEccApp, page);
    }
}
