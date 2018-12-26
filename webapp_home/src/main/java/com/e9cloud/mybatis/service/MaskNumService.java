package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.CityCode;
import com.e9cloud.mybatis.domain.RechargeRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/1.
 */

public  interface MaskNumService extends IBaseService {

    /**
     * 分页查询充值记录
     * @param page 分页信息
     * @return
     */
    PageWrapper pageMaskNum(Page page);

    List<CityCode> findcityALL();

    /**
     * 查询导出记录
     * @param page 查询信息
     * @return
     */
    List<Map<String, Object>> getpageMaskNumList(Page page);
}


