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
 * Created by admin on 2016/5/31.
 */
@Service
public class SecretVoiceServiceImpl extends BaseServiceImpl implements SecretVoiceService {

    /**
     * 分页查询应用铃声记录
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageVoiceList(Page page) {
        return this.page(Mapper.SecretVoice_Mapper.voiceListBySid, page);
    }

    /**
     * 创建铃声
     * @param voice
     * @return
     */
    @Override
    public void saveVoice(SecretVoice voice) {
        this.save(Mapper.SecretVoice_Mapper.saveVoice, voice);

    }
    /**
     * 修改铃声
     * @param voice
     * @return
     */
    @Override
    public void updateVoice(SecretVoice voice) {
        this.update(Mapper.SecretVoice_Mapper.updateVoice, voice);

    }

    @Override
    public void updateVoiceName(Map map) {

        this.update(Mapper.SecretVoice_Mapper.updateVoiceName, map);
    }

    /**
     * 删除铃声
     * @param appid
     * @return
     */
    @Override
    public void delVoice(String appid) {

        this.delete(Mapper.SecretVoice_Mapper.delVoice, appid);
    }

    @Override
    public String countVoiceByAppid(String appid) {
        return this.findObjectByPara(Mapper.SecretVoice_Mapper.countVoiceByAppid, appid);
    }

    @Override
    public String updateVoiceByAppid(String appid) {
        return this.findObjectByPara(Mapper.SecretVoice_Mapper.updateVoiceByAppid, appid);
    }

    /**
     * 根据ID值查询APPname的值
     * @param id
     * @return
     */
    @Override
    public String findAppNameByID(String id) {
        return this.findObjectByPara(Mapper.SecretVoice_Mapper.findAppNameByID, id);
    }

    @Override
    public String findAppNameByAPPid(String appid) {
        return this.findObjectByPara(Mapper.SecretVoice_Mapper.findAppNameByAPPid, appid);
    }

    @Override
    public String getAppvoiceURLByAPPid(String appid) {
        return this.findObjectByPara(Mapper.SecretVoice_Mapper.getAppvoiceURLByAPPid, appid);
    }

    @Override
    public SecretVoice findVoiceListByAppid(String appid) {
        return this.findObjectByPara(Mapper.SecretVoice_Mapper.findVoiceListByAppid, appid);
    }


}
