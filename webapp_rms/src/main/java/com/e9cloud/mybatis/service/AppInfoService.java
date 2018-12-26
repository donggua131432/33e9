package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.domain.AppNumber;
import com.e9cloud.mybatis.domain.BusinessType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.List;
import java.util.Map;

/**
 * Created by wzj on 2016/3/3.
 */
public interface AppInfoService extends IBaseService {

    /**
     * 分页选取智能云调度应用列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageAppListForZnydd(Page page);

    /**
     * 分页选取云话机应用列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageAppListForSipPhone(Page page);

    /**
     * 分页选取 SIP 应用列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageAppListForSip(Page page);

    List<Map<String, Object>> getpageAppListForSip(Page page);

    /**
     * 保存应用
     * @param appInfo 应用的基本信息
     */
    void saveAppInfo(AppInfo appInfo) throws BadHanyuPinyinOutputFormatCombination;

    /**
     * 修改应用
     * @param appInfo 应用的基本信息
     */
    void updateAppInfo(AppInfo appInfo) throws BadHanyuPinyinOutputFormatCombination;

    /**
     * 修改应用信息和扩展信息
     * @param appInfo 应用的基本信息
     * @param appInfoExtra 扩展信息
     */
    void updateAppInfoAndExtra(AppInfo appInfo, AppInfoExtra appInfoExtra)throws BadHanyuPinyinOutputFormatCombination;

    /**
     * 根据appid查找一条应用信息
     * @param appid
     */
    AppInfo getZnyddAppInfoByAppId(String appid);

    public AppInfo getAppInfoByMap(Map<String, Object> params);


    /**
     * 根据appid查找应用信息(只针对Sip业务)
     * @param appid
     */
    AppInfo getSipAppInfoByAppId(String appid);

    AppInfo getAppInfoByObj(AppInfo appInfo);

    /**
     * 更改用户的状态
     * @param appInfo
     */
    void updateAppStatus(AppInfo appInfo);

    /**
     * 导出应用信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadAppInfo(Page page);

    /**
     * 得到 云话机的appinfo信息
     * @param appid appid
     * @return AppInfo
     */
    AppInfo getSipPhoneAppInfoByAppId(String appid);

    /**
     * 下载云话机应用列表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadAppInfoForSipphone(Page page);

    /**
     * 下载专线语音应用列表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadAppInfoForRest(Page page);

    /**
     * 保存appinfoExtra
     * @param appInfoExtra
     */
    public void saveAppExtra(AppInfoExtra appInfoExtra);


    String countBusinessType(String appid);

}
