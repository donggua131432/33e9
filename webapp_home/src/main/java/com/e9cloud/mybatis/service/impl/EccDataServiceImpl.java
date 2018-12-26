package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.EccDataService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/15.
 */
@Service
public class EccDataServiceImpl extends BaseServiceImpl implements EccDataService {

    /**
     * 云总机ECC数据统计
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageDataStatistics(Page page) {
        return this.page(Mapper.IvrDayRecord_Mapper.pageIvrDayRecordList, page);
    }

    /**
     * 导出云总机ECC数据统计
     * @param page
     * @return
     */

    @Override
    public List<Map<String, Object>> downloadDataStatisticsReport(Page page) {
        return  this.download(Mapper.IvrDayRecord_Mapper.downloadIvrDayRecordList, page);
    }

}
