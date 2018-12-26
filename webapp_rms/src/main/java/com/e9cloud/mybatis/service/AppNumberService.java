package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppNumber;

import java.util.List;

/**
 * Created by wj on 2016/3/3.
 */
public interface AppNumberService extends IBaseService {

    /**
     * 分页查询应用号码审核列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageAppNumberList(Page page);

    /**
     * 保存应用
     * @param appNumber 应用的基本信息
     */
    void saveAppNumber(AppNumber appNumber);

    /**
     * 修改应用
     * @param appNumber 应用的基本信息
     */
    void updateAppNumber(AppNumber appNumber);

    /**
     * 根据appid查找一条应用信息
     * @param id
     */
    AppNumber getAppNumberById(Long id);

    /**
     * 更改用户的状态
     * @param appNumber
     */
    void updateAppNumberStatus(AppNumber appNumber);

    AppNumber getNumRemarkByAppid(String appId);

    /**
     * 根据appid 和 号码id 选择 号码
     * @param ids id
     * @return List<AppNumber>
     */
    List<AppNumber> getAppNumbersByIds(String[] ids);
}
