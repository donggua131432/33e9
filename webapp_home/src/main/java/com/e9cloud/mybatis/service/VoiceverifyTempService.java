package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.VoiceverifyTemp;


public interface VoiceverifyTempService extends IBaseService  {

    /**
     * 分页查询语音验证码模板信息
     * @param page 分页信息
     * @return
     */
    PageWrapper pageVoiceverifyList(Page page);

    /**
     * 保存语音验证码模板
     * @param voiceverifyTemp
     */
    public void saveVoiceverifyTemp(VoiceverifyTemp voiceverifyTemp);

    boolean checkNameUnique(VoiceverifyTemp voiceverifyTemp) ;


    /**
     * 修改
     * @param voiceverifyTemp
     */
    public void updateByPrimaryKeySelective(VoiceverifyTemp voiceverifyTemp);

    VoiceverifyTemp getVoiceverifyTempByID(String id);

}
