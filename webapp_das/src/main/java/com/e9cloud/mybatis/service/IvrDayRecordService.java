package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface IvrDayRecordService extends IBaseService {
    /**
     * 分页选取话务报表列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    public PageWrapper pageIvrList(Page page);

    /**
     * 导出话务报表信息
     * @param page
     * @return
     */
    public List<Map<String, Object>> downloadIvrReport(Page page);

}
