package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SecretVoice;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface SecretVoiceService extends IBaseService {
    /**
     * 分页查询应用铃声审核列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageSecretVoiceList(Page page);

    /**
     * 修改应用
     * @param secretVoice 应用的基本信息
     */
    void updateSecretVoice(SecretVoice secretVoice);

    /**
     * 根据appid查找一条应用信息
     * @param id
     */
    SecretVoice getSecretVoiceById(Integer id);

    /**
     * 更改用户的状态
     * @param secretVoice
     */
    void updateSecretVoiceStatus(SecretVoice secretVoice);


    Map getmobileByAppid(String appId);

    String getremarkByAppid(String appId);
}
