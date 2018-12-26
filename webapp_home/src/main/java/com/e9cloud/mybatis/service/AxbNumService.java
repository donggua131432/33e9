package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CityCode;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/4/18.
 */

public  interface AxbNumService extends IBaseService {

    /**
     * 分页查询记录
     * @param page 分页信息
     * @return
     */
    PageWrapper pageAxbNum(Page page);

    /**
     * 查询所有城市信息
     * @return
     */
    List<CityCode> findcityALL();

    /**
     * 查询导出记录
     * @param page 查询信息
     * @return
     */
    List<Map<String, Object>> getpageAxbNumList(Page page);
}


