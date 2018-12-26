package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.IvrDayRecordService;
import com.e9cloud.mybatis.service.SpphoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/12/1.
 */
@Service
public class IvrDayRecordServiceImpl extends BaseServiceImpl implements IvrDayRecordService {

    @Override
    public List<Map<String, Object>> downloadIvrReport(Page page) {
        return  this.download(Mapper.StatIvrDayRecord_Mapper.downloadIvrRecord, page);
    }

    @Override
    public PageWrapper pageIvrList(Page page) {
        return this.page(Mapper.StatIvrDayRecord_Mapper.pageIvrRecordList, page);
    }

}
