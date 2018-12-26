package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.TelephoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/20.
 */
@Service
public class TelephoneServiceImpl extends BaseServiceImpl implements TelephoneService {

    @Override
    public List<Map<String, Object>> downloadTeleReport(Page page) {
        return  this.download(Mapper.RestDayRecord_Mapper.downloadRestDayRecord, page);
    }

    @Override
    public PageWrapper pageTeleList(Page page) {
        return this.page(Mapper.RestDayRecord_Mapper.pageRestDayRecordList, page);
    }

    @Override
    public PageWrapper pageHourTeleList(Page page) {
        return this.page(Mapper.RestHourRecord_Mapper.pageRestHourRecordList, page);
    }

    @Override
    public List<Map<String, Object>> downloadHourTeleReport(Page page) {
        return  this.download(Mapper.RestHourRecord_Mapper.downloadHourTeleReport, page);
    }

}
