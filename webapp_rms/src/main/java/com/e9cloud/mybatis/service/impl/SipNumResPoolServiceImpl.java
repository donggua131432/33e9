package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.PubNumResPoolService;
import com.e9cloud.mybatis.service.SipNumResPoolService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/10
 */
@Service
public class SipNumResPoolServiceImpl extends BaseServiceImpl implements SipNumResPoolService {
    @Override
    public PageWrapper getSipPhoneList(Page page) {
        return  this.page(Mapper.EccSipphone_Mapper.pageEccSipPhoneList, page);
    }
    /**
     * 获取号码池信息列表
     *
     * @param sipPhone
     * @return
     */
    @Override
    public List<EccSipphone> getSipNumResPoolBySipPhone(String sipPhone) {
        return this.findObjectListByPara(Mapper.EccSipphone_Mapper.getSipPhoneBySipPhone, sipPhone);
    }


    /**
     * 添加公共号码管理池
     *
     * @param eccSipPhone
     */
    @Override
    public void addSipPhone(EccSipphone eccSipPhone){
        this.save(Mapper.EccSipphone_Mapper.insertSipPhone,eccSipPhone);
    }

    @Override
    public List<EccSipphone> getSipPhoneById(String id) {
        return this.findObjectListByPara(Mapper.EccSipphone_Mapper.getSipPhoneById, id);
    }

    @Override
    public void deleteSipPhoneByIds(String[] strId) {
        this.delete(Mapper.EccSipphone_Mapper.deleteSipPhoneByIds, strId);
    }

    @Override
    public List<Map<String, Object>> downloadSipPhonePool(Page page) {
        return this.download(Mapper.EccSipphone_Mapper.downloadSipPhoneList, page);
    }

    /**
     * 为下拉框获取IPPort
     *
     * @return
     */
    @Override
    public List<EccSipphone> getIPPortList() {
        return this.findObjectList(Mapper.EccSipphone_Mapper.selectIPPortList, null);
    }

    @Override
    public void updateEccSipphone(EccSipphone sipphone) {
        this.update(Mapper.EccSipphone_Mapper.updateByPrimaryKeySelective, sipphone);
    }
}
