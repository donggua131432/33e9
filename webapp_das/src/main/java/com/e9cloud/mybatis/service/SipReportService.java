package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by lixin on 2016/12/6.
 */
public interface SipReportService  extends IBaseService{
    /**
     * 分页选取话务报表列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    public PageWrapper pageSipList(Page page);

    /**
     * 导出话务报表信息
     * @param page
     * @return
     */
    public List<Map<String, Object>> downloadSipReport(Page page);
}
