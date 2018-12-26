package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.TempVoice;

import java.util.List;
import java.util.Map;

/**
 * 语音通知模板 审核 相关 service
 * Created by Administrator on 2016/9/28.
 */
public interface TempVoiceAuditService extends IBaseService {

    PageWrapper pageVoiceList(Page page);

    /**
     * 得到一个语音模板
     * @param id 主键
     * @return
     */
    TempVoice getTempVoiceByPK(Integer id);

    /**
     * 下载 语音通知报表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadReport(Page page);

    /**
     * 审核语音模板~~~~
     * @param tempVoice
     */
    void updateAuditStatus(TempVoice tempVoice);

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
    List<TempVoice> getNotTransTempVoice(String id);
}
