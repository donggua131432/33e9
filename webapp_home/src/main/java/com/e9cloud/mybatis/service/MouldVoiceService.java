package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.SecretVoice;
import com.e9cloud.mybatis.domain.TempVoice;
import com.e9cloud.mybatis.domain.UserCompany;

import java.util.List;

/**
 * Created by admin on 2016/9/22.
 */
public interface MouldVoiceService extends IBaseService {
    /**
     * 创建模板语音
     * @param tempVoice
     * @return
     */
    public void saveTempVoice(TempVoice tempVoice);

    /**
     * 分页查询语音模板信息
     * @param page 分页信息
     * @return
     */
    PageWrapper pageVoiceList(Page page);

    /**
     * delete模板语音
     * @param tempVoice
     * @return
     */

    public void delTemp(TempVoice tempVoice);

    TempVoice getTempVoiceByID(String id);

    TempVoice  getTempVoice(TempVoice voice);

    public void updateTempVoice(TempVoice tempVoice);

    //验证语音模板名称的唯一性
    boolean checkNameUnique(TempVoice tempVoice);

}
