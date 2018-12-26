package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.VoiceverifyNum;
import com.e9cloud.mybatis.domain.VoiceverifyTemp;
import com.e9cloud.mybatis.mapper.Mapper;

import java.util.List;
import java.util.Map;


public interface VoiceverifyNumService extends IBaseService  {

    /**
     * 分页查询用户号码池（语音验证码）信息
     * @param page 分页信息
     * @return
     */
    PageWrapper pageVoiceverifyNumList(Page page);

    /**
     * 查询导出记录
     * @param page 查询信息
     * @return
     */
    List<Map<String, Object>> getpageVoiceverifyNumList(Page page);

     int findListCountByMap(Map map);

     List<VoiceverifyNum> findListByMap(Map map);

}
