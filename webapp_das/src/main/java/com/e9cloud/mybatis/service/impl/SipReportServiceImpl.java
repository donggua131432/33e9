package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SipReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lixin  on 2016/12/6.
 */
@Service
public class SipReportServiceImpl extends BaseServiceImpl implements SipReportService {

    @Override
    public PageWrapper pageSipList(Page page) {
        return this.page(Mapper.StatSipDayRecord_Mapper.pageSipReportList, page);
    }

    @Override
    public List<Map<String, Object>> downloadSipReport(Page page) {
        return  this.download(Mapper.StatSipDayRecord_Mapper.downloadSipReport, page);
    }
}
