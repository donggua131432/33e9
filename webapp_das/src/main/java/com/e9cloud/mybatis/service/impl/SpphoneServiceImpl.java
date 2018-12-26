package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SpphoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/12/1.
 */
@Service
public class SpphoneServiceImpl extends BaseServiceImpl implements SpphoneService {

    @Override
    public List<Map<String, Object>> downloadSpReport(Page page) {
        return  this.download(Mapper.SpRecord_Mapper.downloadSpRecord, page);
    }

    @Override
    public PageWrapper pageSpList(Page page) {
        return this.page(Mapper.SpRecord_Mapper.pageSpRecordList, page);
    }

}
