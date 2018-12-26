package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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


    public int findAppNumCountByAppID(String appid);
    /**
     * 获取用户的应用列表分页集合
     * @param map
     * @return List
     */
    public List<AppInfo> findAppListByMap(Map map);

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
    public AppInfo findAppInfoByAppId(String appid);


    /**
     * APP应用查看(云话机)
     * @param appid
     * @return AppInfoExtra
     */
    public AppInfoExtra findAppExtraByAppId(String appid);




    /**
     * APP应用查看(----云总机)
     * @param appid
     * @return AppInfoEcc
     */
    public EccAppInfo findAppEccByAppId(String appid);
    /**
     * APP应用查看
     * @param id
     * @return AppInfo
     */
    public AppInfo findAppInfoById(int id);

    /**
     * 只针对sip和智能云调度    一个账户一个appid
     * @param sid sid
     * @param busType 应用类型
     * @return AppInfo
     */
    public AppInfo findAppInfoBySIdAndBusType(String sid, String busType);

    /**
     * 创建APP应用
     * @param app
     * @return
     */
    public void saveApp(AppInfo app) throws BadHanyuPinyinOutputFormatCombination;



    /**
     * 编辑APP应用
     * @param app
     * @return
     */
    public void updateApp(AppInfo app) throws BadHanyuPinyinOutputFormatCombination;



    /**
     * 编辑APP应用(云话机)
     * @param appInfoExtra
     * @return
     */
    public void updateAppExtra(AppInfoExtra appInfoExtra);

    /**
     * 创建APP子应用
     * @param app
     * @return
     */
    public void saveSubApp(SubApp app);

    public void saveAppExtra(AppInfoExtra appInfoExtra);

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

    Map<String, Object> findCityEccByAppId(String appid);

    /**
     * 分页查询分机信息记录
     * @param page 分页信息
     * @return
     */
    PageWrapper pageEccNum(Page page);


    /**
     * 查询导出应用信息
     * @param page 查询信息
     * @return
     */
    List<Map<String, Object>> getAppInfoList(Page page);
}
