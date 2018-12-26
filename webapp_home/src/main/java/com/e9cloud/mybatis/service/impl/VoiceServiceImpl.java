package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.SubApp;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceService;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


/**
 * Created by admin on 2016/3/24.
 */
@Service
public class VoiceServiceImpl extends BaseServiceImpl implements VoiceService {

    /**
     * 分页查询应用铃声记录
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageVoiceList(Page page) {
        return this.page(Mapper.AppVoice_Mapper.voiceListBySid, page);
    }


    /**
     * 分页查询应用铃声记录----(云话机)
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper voicePhoneListBySid(Page page) {
        return this.page(Mapper.AppVoice_Mapper.voicePhoneListBySid, page);
    }

    /**
     * 创建铃声
     * @param voice
     * @return
     */
    @Override
    public void saveVoice(AppVoice voice) {
        this.save(Mapper.AppVoice_Mapper.saveVoice, voice);

    }
    /**
     * 修改铃声
     * @param voice
     * @return
     */
    @Override
    public void updateVoice(AppVoice voice) {
        this.update(Mapper.AppVoice_Mapper.updateVoice, voice);

    }

    @Override
    public void updateVoiceName(Map map) {
        this.update(Mapper.AppVoice_Mapper.updateVoiceName, map);
    }

    /**
     * 删除铃声
     * @param appid
     * @return
     */
    @Override
    public void delVoice(String appid) {
        this.delete(Mapper.AppVoice_Mapper.delVoice, appid);
    }

    @Override
    public String countVoiceByAppid(String appid) {
        return this.findObjectByPara(Mapper.AppVoice_Mapper.countVoiceByAppid, appid);
    }

    @Override
    public String updateVoiceByAppid(String appid) {
        return this.findObjectByPara(Mapper.AppVoice_Mapper.updateVoiceByAppid, appid);
    }

    /**
     * 根据ID值查询APPname的值
     * @param id
     * @return
     */
    @Override
    public String findAppNameByID(String id) {
        return this.findObjectByPara(Mapper.AppVoice_Mapper.findAppNameByID, id);
    }

    @Override
    public String findAppNameByAPPid(String appid) {
        return this.findObjectByPara(Mapper.AppVoice_Mapper.findAppNameByAPPid, appid);
    }

    @Override
    public String getAppvoiceURLByAPPid(String appid) {
        return this.findObjectByPara(Mapper.AppVoice_Mapper.getAppvoiceURLByAPPid, appid);
    }

    @Override
    public AppVoice findVoiceListByAppid(String appid) {
        return this.findObjectByPara(Mapper.AppVoice_Mapper.findVoiceListByAppid, appid);
    }



}
