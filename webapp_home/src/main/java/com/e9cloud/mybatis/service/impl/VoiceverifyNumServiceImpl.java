package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.VoiceverifyNum;
import com.e9cloud.mybatis.domain.VoiceverifyTemp;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceService;
import com.e9cloud.mybatis.service.VoiceverifyNumService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class VoiceverifyNumServiceImpl extends BaseServiceImpl implements VoiceverifyNumService {

    /**
     * 分页查询应用铃声记录
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageVoiceverifyNumList(Page page) {
        return this.page(Mapper.VoiceverifyNum_Mapper.pageVoiceverifyNumList, page);
    }

    @Override
    public List<Map<String, Object>> getpageVoiceverifyNumList(Page page) {
        return this.findObjectList(Mapper.VoiceverifyNum_Mapper.getpageVoiceverifyNumList, page);
    }

    @Override
    public int findListCountByMap(Map map) {
        return (Integer)this.findObjectByMap(Mapper.VoiceverifyNum_Mapper.findListCountByMap,map);
    }

    @Override
    public List<VoiceverifyNum> findListByMap(Map map) {
        return this.findObjectListByMap(Mapper.VoiceverifyNum_Mapper.findListByMap,map);
    }

}
