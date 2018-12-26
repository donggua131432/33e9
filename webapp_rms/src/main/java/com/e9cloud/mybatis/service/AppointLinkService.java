package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppointLink;

/**
 * Created by hzd on 2016/8/18.
 */
public interface AppointLinkService extends IBaseService {

    /**
     * 特例表
     * @param page
     * @return list
     */
    PageWrapper pageAppointLinkList(Page page);

    /**
     * 保存选线特例
     * @param appointLink 选线特例
     */
    void saveAppointLink(AppointLink appointLink);

    /**
     * 根据id查找一条选线特例信息
     * @param id
     */
    AppointLink queryAppointLinkInfoById(String id);

    /**
     * 全条件查找一条选线特例信息
     * @param appointLink
     */
    AppointLink queryAppointLinkInfo(AppointLink appointLink);

    /**
     * 修改选线特例
     * @param appointLink 选线特例的基本信息
     */
    void updateAppointLinkInfo(AppointLink appointLink);

    /**
     * 删除选线特例
     * @param appointLink 选线特例的基本信息
     */
    void delAppointLinkInfo(AppointLink appointLink);

}
