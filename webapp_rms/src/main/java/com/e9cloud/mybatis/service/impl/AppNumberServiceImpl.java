package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppNumber;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppNumberService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
@Service
public class AppNumberServiceImpl extends BaseServiceImpl implements AppNumberService {

    /**
     * 分页查询应用铃声审核列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageAppNumberList(Page page) {
        return this.page(Mapper.AppNumber_Mapper.pageAppNumberList, page);
    }

    @Override
    public void saveAppNumber(AppNumber appNumber) {

    }

    @Override
    public void updateAppNumber(AppNumber appNumber) {
        this.update(Mapper.AppNumber_Mapper.updateByPrimaryKeySelective, appNumber);
    }

    @Override
    public AppNumber getAppNumberById(Long id) {
        return this.findObject(Mapper.AppNumber_Mapper.selectByPrimaryKey, id);
    }

    @Override
    public void updateAppNumberStatus(AppNumber appNumber) {

    }

    @Override
    public AppNumber getNumRemarkByAppid(String appId){
        return this.findObject(Mapper.AppNumber_Mapper.getNumRemarkByAppid, appId);
    }

    /**
     * 根据appid 和 号码id 选择 号码
     *
     * @param ids   id
     * @return List<AppNumber>
     */
    @Override
    public List<AppNumber> getAppNumbersByIds(String[] ids) {
        return this.findObjectList(Mapper.AppNumber_Mapper.getAppNumbersByIds, ids);
    }
}
