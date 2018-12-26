package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AxbPhone;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AxbNumResPoolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2017/4/18.
 */
@Service
public class AxbNumResPoolServiceImpl extends BaseServiceImpl implements AxbNumResPoolService{
    /**
     * 获取AXB城市下拉列表
     */
    @Override
    public List<TelnoCity> getCitys(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.AxbNumResPool_Mapper.selectCitys, params);
    }

    /**
     * 获取AXB公共号码池列表
     */
    @Override
    public PageWrapper getAxbNumList(Page page){
        return  this.page(Mapper.AxbNumResPool_Mapper.pageAxbNumList, page);
    }

    /**
     * Axb公共号码池列表信息--导出
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadAxbNumPool(Page page) {
        return this.download(Mapper.AxbNumResPool_Mapper.pageAxbNumList, page);
    }

    /**
     * 获取号码池信息列表
     *
     * @param axbPhone
     * @return
     */
    @Override
    public List<AxbPhone> getAxbNumResPoolByAxbPhone(AxbPhone axbPhone) {
        return this.findObjectList(Mapper.AxbNumResPool_Mapper.getAxbPhoneByAxbPhone,axbPhone);
    }

    /**
     * 添加公共号码管理池
     *
     * @param axbPhone
     */
    @Override
    public void addAxbPhone(AxbPhone axbPhone){
        this.save(Mapper.AxbNumResPool_Mapper.insertAxbPhone,axbPhone);
    }

    @Override
    public AxbPhone getAxbPhoneById(String id) {
        return this.findObjectByPara(Mapper.AxbNumResPool_Mapper.getAxbPhoneById,id);
    }

    @Override
    public void deleteAxbPhoneByIds(String[] strId) {
        this.update(Mapper.AxbNumResPool_Mapper.deleteAxbPhoneByIds, strId);
    }

    /**
     * 根据id变更公共号码池号码状态
     */
    @Override
    public void updateAxbPhoneByStatus(AxbPhone axbPhone){
        this.update(Mapper.AxbNumResPool_Mapper.updateAxbPhoneByStatus, axbPhone);
    }

    //校验号码+区号是否匹配
    @Override
    public boolean checkAxbNumberMatchAreacode(AxbPhone axbPhone){
        long l = this.findObject(Mapper.AxbNumResPool_Mapper.checkAxbNumberMatchAreacode, axbPhone);
        return l == 0;
    }
}
