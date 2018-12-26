package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppointLink;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppointLinkService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/1/11.
 */
@Service
public class AppointLinkServiceImpl extends BaseServiceImpl implements AppointLinkService {

    /**
     *
     *
     * @param page
     * @return list
     */
    @Override
    public PageWrapper pageAppointLinkList(Page page) {
        return this.page(Mapper.AppointLink_Mapper.pageAppointLinkList, page);
    }

    /**
     * 保存选线特例
     * @param appointLink 选线特例
     */
    @Override
    public void saveAppointLink(AppointLink appointLink) {
        this.save(Mapper.AppointLink_Mapper.saveAppointLink, appointLink);
    }

    /**
     * 根据id查找一条选线特例信息
     * @param id
     */
    @Override
    public AppointLink queryAppointLinkInfoById(String id){
        return this.findObjectByPara(Mapper.AppointLink_Mapper.queryAppointLinkInfoById, id);
    }

    /**
     * 全条件查找一条选线特例信息
     * @param appointLink
     */
    public AppointLink queryAppointLinkInfo(AppointLink appointLink){
        return this.findObject(Mapper.AppointLink_Mapper.queryAppointLinkInfo, appointLink);
    }

    /**
     * 修改选线特例
     * @param appointLink 选线特例的基本信息
     */
    @Override
    public void updateAppointLinkInfo(AppointLink appointLink) {
        this.update(Mapper.AppointLink_Mapper.updateAppointLinkInfo, appointLink);
    }

    /**
     * 删除选线特例
     * @param appointLink 选线特例的基本信息
     */
    @Override
    public void delAppointLinkInfo(AppointLink appointLink){
        this.delete(Mapper.AppointLink_Mapper.delAppointLinkInfo, appointLink);
    }

}
