package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/15.
 */
public interface EccDataService extends IBaseService {


    /**
     * 云总机ECC数据统计
     * @param page
     * @return
     */
    public PageWrapper pageDataStatistics(Page page);



    /**
     * 导出云总机ECC数据统计
     * @param page
     * @return
     */
    public List<Map<String, Object>> downloadDataStatisticsReport(Page page);

}
