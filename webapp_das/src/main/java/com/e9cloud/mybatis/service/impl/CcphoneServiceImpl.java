package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CcphoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/12/1.
 */
@Service
public class CcphoneServiceImpl extends BaseServiceImpl implements CcphoneService {

    @Override
    public List<Map<String, Object>> downloadCcReport(Page page) {
        return  this.download(Mapper.CcRecord_Mapper.pageCcRecordList, page);
    }

    @Override
    public PageWrapper pageCcList(Page page) {
        return this.page(Mapper.CcRecord_Mapper.countCcRecordList, Mapper.CcRecord_Mapper.pageCcRecordList, page);
    }

}
