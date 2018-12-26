package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Audio2Wav;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.TempVoice;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.TempVoiceAuditService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 语音通知模板 审核 相关 service
 * Created by Administrator on 2016/9/28.
 */
@Service
public class TempVoiceAuditServiceImpl extends BaseServiceImpl implements TempVoiceAuditService {

    @Override
    public PageWrapper pageVoiceList(Page page) {
        return this.page(Mapper.TempVoice_Mapper.pageVoiceList, page);
    }

    /**
     * 得到一个语音模板
     *
     * @param id 主键
     * @return
     */
    @Override
    public TempVoice getTempVoiceByPK(Integer id) {
        return this.findObject(Mapper.TempVoice_Mapper.selectByPrimaryKey, id);
    }

    /**
     * 下载 语音通知报表
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadReport(Page page) {
        return this.download(Mapper.TempVoice_Mapper.pageVoiceList, page);
    }

    /**
     * 审核语音模板~~~~
     *
     * @param tempVoice
     */
    @Override
    public void updateAuditStatus(TempVoice tempVoice) {
        this.update(Mapper.TempVoice_Mapper.updateByPrimaryKeySelective, tempVoice);
    }

    /**
     * 得到语音模板的一些信息 如 sid，客户名称等
     *
     * @param tempId
     * @return
     */
    @Override
    public Map<String, Object> getTVInfoByTempId(Integer tempId) {
        return this.findObject(Mapper.TempVoice_Mapper.selectTVInfoByTempId, tempId);
    }

    /**
     * 获取未转换为语音文件的文本模版
     *
     * @return
     */
    @Override
    public List<TempVoice> getNotTransTempVoice(String id) {
        return this.findObjectListByPara(Mapper.TempVoice_Mapper.getNotTransTempVoice,id);
    }
}
