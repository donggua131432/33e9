package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.PinyinUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppInfoService;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/3.
 */
@Service
public class AppInfoServiceImpl extends BaseServiceImpl implements AppInfoService {
    /**
     * 分页选取智能云调度应用列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageAppListForZnydd(Page page) {
        return this.page(Mapper.AppInfo_Mapper.pageAppListForZnydd, page);
    }

    /**
     * 分页选取SIP应用列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageAppListForSip(Page page) {
        return this.page(Mapper.AppInfo_Mapper.pageAppListForSip, page);
    }

    /**
     * 分页选取云话机应用列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageAppListForSipPhone(Page page) {
        return this.page(Mapper.AppInfo_Mapper.pageAppListForSipPhone, page);
    }

    @Override
    public List<Map<String, Object>> getpageAppListForSip(Page page) {
        return this.findObjectList(Mapper.AppInfo_Mapper.getpageAppListForSip, page);
    }
    /**
     * 保存应用
     *
     * @param appInfo 应用的基本信息
     */
    @Override
    public void saveAppInfo(AppInfo appInfo) throws BadHanyuPinyinOutputFormatCombination {
        appInfo.setPinyin(PinyinUtils.toPinYin(appInfo.getAppName()));
        this.save(Mapper.AppInfo_Mapper.insertSelective, appInfo);
    }

    /**
     * 修改应用
     *
     * @param appInfo 应用的基本信息
     */
    @Override
    public void updateAppInfo(AppInfo appInfo) throws BadHanyuPinyinOutputFormatCombination{
        if(Tools.isNotNullStr(appInfo.getAppName())){
            appInfo.setPinyin(PinyinUtils.toPinYin(appInfo.getAppName()));
        }
        this.update(Mapper.AppInfo_Mapper.updateByPrimaryKeySelective, appInfo);
    }

    /**
     * 修改应用信息和扩展信息
     *
     * @param appInfo      应用的基本信息
     * @param appInfoExtra 扩展信息
     */
    @Override
    public void updateAppInfoAndExtra(AppInfo appInfo, AppInfoExtra appInfoExtra)throws BadHanyuPinyinOutputFormatCombination {
        if(Tools.isNotNullStr(appInfo.getAppName())){
            appInfo.setPinyin(PinyinUtils.toPinYin(appInfo.getAppName()));
        }
        this.update(Mapper.AppInfo_Mapper.updateByPrimaryKeySelective, appInfo);
        this.update(Mapper.AppInfoExtra_Mapper.updateByAppidSelective, appInfoExtra);
    }

    /**
     * 根据appid查找一条应用信息
     *
     * @param appid
     */
    @Override
    public AppInfo getZnyddAppInfoByAppId(String appid) {
        return this.findObjectByPara(Mapper.AppInfo_Mapper.findZnyddAppInfoByAppId, appid);
    }


    @Override
    public AppInfo getAppInfoByMap(Map<String, Object> params) {
        return this.findObjectByMap(Mapper.AppInfo_Mapper.getAppInfoByMap, params);
    }

    /**
     * 根据appid查找   SIP应用
     *
     * @param appid
     */
    @Override
    public AppInfo getSipAppInfoByAppId(String appid) {
        return this.findObjectByPara(Mapper.AppInfo_Mapper.findSipAppInfoByAppId, appid);
    }

    /**
     * 根据条件查找一条应用信息
     * @param appInfo
     * @return
     */
    @Override
    public AppInfo getAppInfoByObj(AppInfo appInfo) {
        return this.findObject(Mapper.AppInfo_Mapper.findAppInfoByObj, appInfo);
    }

    /**
     * 更改用户的状态
     *
     * @param appInfo
     */
    @Override
    public void updateAppStatus(AppInfo appInfo) {
        this.update(Mapper.AppInfo_Mapper.updateAppStatus ,appInfo);
    }

    /**
     * 导出应用列表信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadAppInfo(Page page) {
        return this.download(Mapper.AppInfo_Mapper.pageAppListForZnydd, page);
    }

    /**
     * 得到 云话机的appinfo信息
     *
     * @param appid appid
     * @return AppInfo
     */
    @Override
    public AppInfo getSipPhoneAppInfoByAppId(String appid) {
        return this.findObjectByPara(Mapper.AppInfo_Mapper.selectSipPhoneAppInfoByAppId, appid);
    }

    /**
     * 导出应用列表信息 (云话机应用列表)
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadAppInfoForSipphone(Page page) {
        return this.download(Mapper.AppInfo_Mapper.pageAppListForSipPhone, page);
    }

    /**
     * 导出应用列表信息 (专线语音应用列表)
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadAppInfoForRest(Page page) {
        return this.download(Mapper.AppInfo_Mapper.pageAppListForRest, page);
    }

    @Override
    public void saveAppExtra(AppInfoExtra appInfoExtra) {
        this.save(Mapper.AppInfoExtra_Mapper.saveAppExtra,appInfoExtra);
    }

    @Override
    public String countBusinessType(String appid) {
        return this.findObjectByPara(Mapper.AppInfoExtra_Mapper.countBusinessType, appid);
    }
}
