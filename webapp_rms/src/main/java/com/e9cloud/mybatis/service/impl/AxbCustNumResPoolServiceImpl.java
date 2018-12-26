package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AxbAppPool;
import com.e9cloud.mybatis.domain.AxbCustNumber;
import com.e9cloud.mybatis.domain.AxbNumRelation;
import com.e9cloud.mybatis.domain.AxbPhone;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AxbCustNumResPoolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 客户号码资源池实现类
 * Created by hzd on 2017/4/19.
 */
@Service
public class AxbCustNumResPoolServiceImpl extends BaseServiceImpl implements AxbCustNumResPoolService{

    /**
     * 获取客户号码池信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper getAxbCustAppPoolList(Page page) {
        return this.page(Mapper.AxbCustNumResPool_Mapper.selectAxbAppPoolList, page);
    }

    /**
     * 添加客户号码池
     * @param axbCustNumber
     */
    @Override
    public void addAxbCustNumberPool(AxbCustNumber axbCustNumber, AxbPhone axbPhone) {
        this.save(Mapper.AxbCustNumResPool_Mapper.insetAxbCustNumberPool, axbCustNumber);
        this.update(Mapper.AxbNumResPool_Mapper.updateAxbPhoneByStatus, axbPhone);
    }

    /**
     * 根据条件获取隐私号码池信息
     * @param axbCustNumber
     * @return
     */
    @Override
    public List<AxbCustNumber> getAxbCustNumberPoolByObj(AxbCustNumber axbCustNumber) {
        return this.findObjectList(Mapper.AxbCustNumResPool_Mapper.selectAxbCustNumberPoolByObj, axbCustNumber);
    }

    /**
     * 获取用户号码池号码信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper getAxbCustNumberList(Page page) {
        return this.page(Mapper.AxbCustNumResPool_Mapper.selectAxbCustNumberList, page);
    }

    /**
     * Axb用户号码池列表信息--导出
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadAxbCustNumPool(Page page) {
        return this.download(Mapper.AxbCustNumResPool_Mapper.selectAxbCustNumberList, page);
    }

    /**
     * 根据条件获取用户号码池信息（app_pool）
     * @param axbAppPool
     * @return
     */
    @Override
    public AxbAppPool getAxbCustAppPoolByObj(AxbAppPool axbAppPool){
        return this.findObject(Mapper.AxbCustNumResPool_Mapper.selectAxbAppPoolByObj, axbAppPool);
    }

    /**
     * 添加用户号码池
     * @param axbAppPool
     */
    @Override
    public void addAxbCustAppPool(AxbAppPool axbAppPool){
        this.save(Mapper.AxbCustNumResPool_Mapper.insetAxbCustAppPool, axbAppPool);
    }

    /**
     * 用于查询app下是否有正常或者已锁定的号码列表
     * @param appid
     */
    @Override
    public boolean checkHasNumber(String appid){
        int count = this.findObjectByPara(Mapper.AxbCustNumResPool_Mapper.checkHasNumber, appid);
        return count == 0;
    }

    /**
     * 根据ID删除隐私号码池信息
     * @param id
     */
    @Override
    public void deleteAxbCustAppPoolById(String id) {
        this.delete(Mapper.AxbCustNumResPool_Mapper.deleteAxbCUstAppPool, id);
    }

    /**
     * 根据number查询虚拟小号是否已绑定
     * @param number
     */
    @Override
    public AxbNumRelation getAxbNumRelationByNum(String number){
        return this.findObject(Mapper.AxbCustNumResPool_Mapper.getAxbNumRelationByNum, number);
    }

    /**
     * 没绑定小号，物理删除
     */
    @Override
    public void deleteAxbCustNum(AxbCustNumber axbCustNumber){
        this.delete(Mapper.AxbCustNumResPool_Mapper.deleteAxbCustNum, axbCustNumber);
    }

    /**
     * 绑定小号，逻辑删除
     */
    @Override
    public void updateAxbCustNumByStatus(AxbCustNumber axbCustNumber){
        this.update(Mapper.AxbCustNumResPool_Mapper.updateAxbCustNumByStatus, axbCustNumber);
    }

    //根据条件修改用户号码关系表
    @Override
    public void updateAxbNumRelationByObj(AxbNumRelation axbNumRelation){
        this.update(Mapper.AxbCustNumResPool_Mapper.updateAxbNumRelationByObj, axbNumRelation);
    }
}
