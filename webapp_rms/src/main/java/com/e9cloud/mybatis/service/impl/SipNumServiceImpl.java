package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SipAppNumPool;
import com.e9cloud.mybatis.domain.SipNumPool;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SipNumService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/7/14.
 */
@Service
public class SipNumServiceImpl   extends BaseServiceImpl implements SipNumService {

    /**
     * 获取隐私号信息列表
     * @param number
     * @return
     */
    @Override
    public SipAppNumPool getSipNumberByNumber(String number) {
        return this.findObject(Mapper.SipAppNumPool_Mapper.selectSipNumberByNumber, number);
    }

    /**
     * 添加SIP全局号码池
     * @param sipAppNumPool
     */
    @Override
    public void addSIPNumberPool(SipAppNumPool sipAppNumPool){
        this.save(Mapper.SipAppNumPool_Mapper.insetSIPNumberPool, sipAppNumPool);
    }

    /**
     * 获取Sip全局号码池列表信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper getSipNumberList(Page page) {
        return this.page(Mapper.SipAppNumPool_Mapper.selectSipNumberList, page);
    }

    /**
     * sip全局、子账号 号码池列表信息
     *
     * @return
     */
    @Override
    public PageWrapper getSipNumList(Page page) {
        return this.page(Mapper.SipAppNumPool_Mapper.getSipNumList,page);
    }

    /**
     * sip全局、子账号 号码池列表信息--导出
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSipNumList(Page page) {
        return this.download(Mapper.SipAppNumPool_Mapper.getSipNumList,page);
    }

    /**
     * 获取Sip全局号码池列表信息--导出
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> getSipNumberListDownload(Page page) {
        return this.download(Mapper.SipAppNumPool_Mapper.selectSipNumberListDownload, page);
    }

    /**
     * 获取Sip全局and子账号号码池列表信息
     * @param map
     * @return
     */
    public List<SipNumPool> getSipNumPoolByNumber(Map map){
        return this.findObjectListByMap(Mapper.SipAppNumPool_Mapper.getSipNumPoolByNumber,map);
    }

    @Override
    public SipAppNumPool getSipNumInfoById(Integer id) {
        return this.findObject(Mapper.SipAppNumPool_Mapper.getSipNumInfoById, id);
    }

    @Override
    public void updateSipNumber(SipAppNumPool sipAppNumPool) {
        this.update(Mapper.SipAppNumPool_Mapper.updateSipNumber, sipAppNumPool);
    }
    @Override
    public void deleteSipNumber(SipAppNumPool sipAppNumPool) {
        this.delete(Mapper.SipAppNumPool_Mapper.deleteSipNumber, sipAppNumPool);
    }
    @Override
    public boolean checkSipNumUnique(SipAppNumPool sipAppNumPool) {
        long l = this.findObject(Mapper.SipAppNumPool_Mapper.checkSipNumUnique, sipAppNumPool);
        return l == 0;
    }

    @Override
    public void deleteStatusBylink(String[] strId) {
        this.update(Mapper.SipAppNumPool_Mapper.deleteStatusBylink, strId);
    }

}
