package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoicephoneService;
import com.e9cloud.mybatis.service.VoiceverifyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/12/1.
 */
@Service
public class VoiceverifyServiceImpl extends BaseServiceImpl implements VoiceverifyService {

    @Override
    public List<Map<String, Object>> downloadVoiceverifyReport(Page page) {
        return  this.download(Mapper.VoiceverifyRecord_Mapper.downloadVoiceverifyRecord, page);
    }

    @Override
    public PageWrapper pageVoiceverifyList(Page page) {
        return this.page(Mapper.VoiceverifyRecord_Mapper.pageVoiceverifyRecordList, page);
    }

}
