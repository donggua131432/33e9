package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.FixPhone;
import com.e9cloud.mybatis.domain.SipPhone;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.domain.SipRelayNumPool;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.PubNumResPoolService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 */
@Service
public class PubNumResPoolServiceImpl extends BaseServiceImpl implements PubNumResPoolService {

    /**
     * 获取子帐号信息
     *
     * @param subid
     * @return
     */
    @Override
    public SipRelayInfo getSubApp(String subid) {
        return (SipRelayInfo) this.findObjectByPara(Mapper.SubApp_Mapper.selectSubAppBySubid, subid);
    }

    /**
     * 获取号码池信息列表
     *
     * @param number
     * @return
     */
    @Override
    public SipRelayNumPool getSubNumberByNumber(String subid,String number) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("subid",subid);
        map.put("number",number);
        return this.findObject(Mapper.SubPool_Mapper.selectSubNumberByNumber,map);
    }

    /**
     * 获取号码池信息列表
     *
     * @param sipPhone
     * @return
     */
    @Override
    public List<SipPhone> getPubNumResPoolBySipPhone(String sipPhone) {
        return this.findObjectListByPara(Mapper.SipPhone_Mapper.getSipPhoneBySipPhone,sipPhone);
    }

    /**
     * 判断sipphone是否注册
     * @param sipPhone
     * @return
     */
    @Override
    public boolean checkRegisterSipphone(String sipPhone) {
        long l =  this.findObject(Mapper.SipPhone_Mapper.checkRegisterSipphone,sipPhone);
        return l == 0;
    }

    /**
     * 获取号码池信息列表
     *
     * @param num
     * @return
     */
    @Override
    public List<FixPhone> getPubNumResPoolByNum(String num) {
        return this.findObjectList(Mapper.FixPhone_Mapper.getFixPhoneByNum,num);
    }

    /**
     * 添加公共号码管理池
     *
     * @param sipPhone
     */
    @Override
    public void addSipPhone(SipPhone sipPhone){
        this.save(Mapper.SipPhone_Mapper.insertSipPhone,sipPhone);
    }

    @Override
    public void addFixPhone(FixPhone fixPhone){
        this.save(Mapper.FixPhone_Mapper.insertFixPhone,fixPhone);
    }

    @Override
    public PageWrapper getSipPhoneList(Page page) {
        return  this.page(Mapper.SipPhone_Mapper.pageSipPhoneList, page);
    }

    @Override
    public PageWrapper getFixPhoneList(Page page) {
        return  this.page(Mapper.FixPhone_Mapper.pageFixPhoneList, page);
    }

    /**
     * 获取Sip子账号号码池列表信息--导出
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSipPhonePool(Page page) {
        return this.download(Mapper.SipPhone_Mapper.downloadSipPhoneList, page);
    }

    /**
     * 获取Sip子账号号码池列表信息--导出
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadFixPhonePool(Page page) {
        return this.download(Mapper.FixPhone_Mapper.downloadFixPhoneList, page);
    }

    @Override
    public List<SipPhone> getSipPhoneById(String id) {
        return this.findObjectListByPara(Mapper.SipPhone_Mapper.getSipPhoneById,id);
    }

    @Override
    public FixPhone getFixPhoneById(String id) {
        return this.findObjectByPara(Mapper.FixPhone_Mapper.getFixPhoneById,id);
    }

    @Override
    public void updateSubNumber(SipRelayNumPool sipRelayNumPool) {
        this.update(Mapper.SubPool_Mapper.updateSubNumber,sipRelayNumPool);
    }

    @Override
    public void deleteSubNumber(SipRelayNumPool sipRelayNumPool) {
        this.delete(Mapper.SubPool_Mapper.deleteSubNumber,sipRelayNumPool);
    }

    @Override
    public void deleteSubNumberBySubid(SipRelayNumPool sipRelayNumPool) {
        this.delete(Mapper.SubPool_Mapper.deleteSubNumberBySubid,sipRelayNumPool);
    }

    /**
     * @param sipRelayNumPool
     * @return
     */
    @Override
    public boolean checkSubNumUnique(SipRelayNumPool sipRelayNumPool) {
        long l =  this.findObject(Mapper.SubPool_Mapper.checkSubNumUnique,sipRelayNumPool);
        return l == 0;
    }

    @Override
    public void deleteSipPhoneByIds(String[] strId) {
        this.delete(Mapper.SipPhone_Mapper.deleteSipPhoneByIds, strId);
    }

    @Override
    public void deleteFixPhoneByIds(String[] strId) {
        this.delete(Mapper.FixPhone_Mapper.deleteFixPhoneByIds, strId);
    }

    /**
     * 为下拉框获取IPPort
     *
     * @return
     */
    @Override
    public List<SipPhone> getIPPortList() {
        return this.findObjectList(Mapper.SipPhone_Mapper.selectIPPortList, null);
    }
}
