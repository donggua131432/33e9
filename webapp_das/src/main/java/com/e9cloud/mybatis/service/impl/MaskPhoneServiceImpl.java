package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.MaskPhoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/6.
 */
@Service
public class MaskPhoneServiceImpl extends BaseServiceImpl implements MaskPhoneService{

    @Override
    public PageWrapper pageMaskList(Page page) {
        return this.page(Mapper.StatMaskDayRecord_Mapper.pageMaskReportList, page);
    }

    @Override
    public List<Map<String, Object>> downloadMaskReport(Page page) {
        return  this.download(Mapper.StatMaskDayRecord_Mapper.downloadMaskReport, page);
    }

}
