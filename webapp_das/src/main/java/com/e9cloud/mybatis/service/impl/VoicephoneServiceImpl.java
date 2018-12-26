package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoicephoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/12/1.
 */
@Service
public class VoicephoneServiceImpl extends BaseServiceImpl implements VoicephoneService {

    @Override
    public List<Map<String, Object>> downloadVoiceReport(Page page) {
        return  this.download(Mapper.VoiceRecord_Mapper.downloadVoiceRecord, page);
    }

    @Override
    public PageWrapper pageVoiceList(Page page) {
        return this.page(Mapper.VoiceRecord_Mapper.pageVoiceRecordList, page);
    }

}
