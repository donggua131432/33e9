package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.UserAdmin;

import java.util.Map;

/**
 * Created by wj on 2016/3/3.
 */
public interface AppVoiceService extends IBaseService {

    /**
     * 分页查询应用铃声审核列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageAppVoiceList(Page page);

    /**
     * 保存应用
     * @param appVoice 应用的基本信息
     */
    void saveAppVoice(AppVoice appVoice);

    /**
     * 修改应用
     * @param appVoice 应用的基本信息
     */
    void updateAppVoice(AppVoice appVoice);

    /**
     * 根据appid查找一条应用信息
     * @param id
     */
    AppVoice getAppVoiceById(Integer id);

    /**
     * 更改用户的状态
     * @param AppVoice
     */
    void updateAppVoiceStatus(AppVoice AppVoice);


    Map getmobileByAppid(String appId);

    String getremarkByAppid(String appId);


}
