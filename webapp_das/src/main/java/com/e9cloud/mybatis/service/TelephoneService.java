package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface TelephoneService extends IBaseService {
    /**
     * 分页选取话务报表列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    public PageWrapper pageTeleList(Page page);

    /**
     * 导出话务报表信息
     * @param page
     * @return
     */
    public List<Map<String, Object>> downloadTeleReport(Page page);

    /**
     * 分页选取话务报表列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    public PageWrapper pageHourTeleList(Page page);

    public List<Map<String, Object>> downloadHourTeleReport(Page page);
}
