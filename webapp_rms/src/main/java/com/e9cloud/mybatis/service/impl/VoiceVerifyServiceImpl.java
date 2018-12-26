package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.TempVoice;
import com.e9cloud.mybatis.domain.VoiceVerify;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceVerifyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/5/3.
 */
@Service
public class VoiceVerifyServiceImpl extends BaseServiceImpl implements VoiceVerifyService{

    @Override
    public PageWrapper pageVoiceList(Page page) {
        return this.page(Mapper.VoiceVerify_Mapper.pageVoiceList, page);
    }
    /**
     * 得到一个语音模板
     *
     * @param id 主键
     * @return
     */
    @Override
    public VoiceVerify getTempVoiceByPK(Integer id) {
        return this.findObject(Mapper.VoiceVerify_Mapper.selectByPrimaryKey, id);
    }

    /**
     * 下载 语音通知报表
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadReport(Page page) {
        return this.download(Mapper.VoiceVerify_Mapper.pageVoiceList, page);
    }

    /**
     * 审核语音模板~~~~
     *
     * @param voiceVerify
     */
    @Override
    public void updateAuditStatus(VoiceVerify voiceVerify) {
        this.update(Mapper.VoiceVerify_Mapper.updateByPrimaryKeySelective, voiceVerify);
    }

    /**
     * 得到语音模板的一些信息 如 sid，客户名称等
     *
     * @param tempId
     * @return
     */
    @Override
    public Map<String, Object> getTVInfoByTempId(Integer tempId) {
        return this.findObject(Mapper.VoiceVerify_Mapper.selectTVInfoByTempId, tempId);
    }

    /**
     * 获取未转换为语音文件的文本模版
     *
     * @return
     */
    @Override
    public List<VoiceVerify> getNotTransTempVoice(String id) {
        return this.findObjectListByPara(Mapper.VoiceVerify_Mapper.getNotTransTempVoice,id);
    }
}
