package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.TempVoice;
import com.e9cloud.mybatis.domain.VoiceVerify;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/5/3.
 */
public interface VoiceVerifyService extends IBaseService{


    PageWrapper pageVoiceList(Page page);

    /**
     * 得到一个语音模板
     * @param id 主键
     * @return
     */
    VoiceVerify getTempVoiceByPK(Integer id);

    /**
     * 下载 语音通知报表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadReport(Page page);

    /**
     * 审核语音模板~~~~
     * @param voiceVerify
     */
    void updateAuditStatus(VoiceVerify voiceVerify);

    /**
     * 得到语音模板的一些信息 如 sid，客户名称等
     * @param tempId
     * @return
     */
    Map<String, Object> getTVInfoByTempId(Integer tempId);

    /**
     * 获取未转换为语音文件的文本模版
     * @return
     */
    List<VoiceVerify> getNotTransTempVoice(String id);
}
