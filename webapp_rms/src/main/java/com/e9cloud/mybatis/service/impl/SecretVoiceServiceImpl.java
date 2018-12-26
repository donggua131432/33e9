package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.SecretVoice;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SecretVoiceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/2.
 */
@Service
public class SecretVoiceServiceImpl extends BaseServiceImpl implements SecretVoiceService {

    /**
     * 分页查询应用铃声审核列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageSecretVoiceList(Page page) {
        return this.page(Mapper.SecretVoice_Mapper.pageSecretVoiceList, page);
    }

    @Override
    public void updateSecretVoice(SecretVoice secretVoice) {
        this.update(Mapper.SecretVoice_Mapper.updateByPrimaryKeySelective, secretVoice);
    }

    @Override
    public SecretVoice getSecretVoiceById(Integer id) {
        return this.findObject(Mapper.SecretVoice_Mapper.selectByPrimaryKey, id);
    }

    @Override
    public void updateSecretVoiceStatus(SecretVoice secretVoice) {

    }

    @Override
    public Map getmobileByAppid(String appId){
        return this.findObjectByPara(Mapper.SecretVoice_Mapper.getmobileByAppid, appId);
    }

    @Override
    public String getremarkByAppid(String appId){
        return this.findObjectByPara(Mapper.SecretVoice_Mapper.getremarkByAppid, appId);
    }
}
