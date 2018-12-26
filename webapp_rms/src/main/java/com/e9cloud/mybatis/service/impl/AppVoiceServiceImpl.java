package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppVoiceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2016/3/3.
 */
@Service
public class AppVoiceServiceImpl extends BaseServiceImpl implements AppVoiceService {

    /**
     * 分页查询应用铃声审核列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageAppVoiceList(Page page) {
        return this.page(Mapper.AppVoice_Mapper.pageAppVoiceList, page);
    }

    @Override
    public void saveAppVoice(AppVoice appVoice) {

    }

    @Override
    public void updateAppVoice(AppVoice appVoice) {
        this.update(Mapper.AppVoice_Mapper.updateByPrimaryKeySelective, appVoice);
    }

    @Override
    public AppVoice getAppVoiceById(Integer id) {
        return this.findObject(Mapper.AppVoice_Mapper.selectByPrimaryKey, id);
    }

    @Override
    public void updateAppVoiceStatus(AppVoice AppVoice) {

    }

    @Override
    public Map getmobileByAppid(String appId){
        return this.findObjectByPara(Mapper.AppVoice_Mapper.getmobileByAppid, appId);
    }

    @Override
    public String getremarkByAppid(String appId){
        return this.findObjectByPara(Mapper.AppVoice_Mapper.getremarkByAppid, appId);
    }


}
