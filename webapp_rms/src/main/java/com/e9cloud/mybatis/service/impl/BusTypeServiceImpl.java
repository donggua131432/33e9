package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.BusTypeService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by admin on 2016/8/22.
 */
@Service
public class BusTypeServiceImpl extends BaseServiceImpl implements BusTypeService {

    @Override
    public BusinessType checkBusinessInfo(Map<String, Object> params) {
        return this.findObjectByMap(Mapper.BusinessType_Mapper.checkBusinessInfo, params);
    }


    public void openBusinessType(BusinessType businessType) {
        this.save(Mapper.BusinessType_Mapper.insert, businessType);
    }

    public void updateStatus(BusinessType businessType) {
        this.update(Mapper.BusinessType_Mapper.updateStatus, businessType);
    }

    @Override
    public String countApp(Map<String, Object> params) {
        return this.findObjectByMap(Mapper.BusinessType_Mapper.countApp, params);
    }

    @Override
    public String countAxb(Map<String, Object> params) {
        return this.findObjectByMap(Mapper.BusinessType_Mapper.countAxb, params);
    }


    @Override
    public String count1(String uid) {
        return this.findObjectByPara(Mapper.BusinessType_Mapper.count1, uid);
    }

    @Override
    public String count3(String uid) {
        return this.findObjectByPara(Mapper.BusinessType_Mapper.count3, uid);
    }


    @Override
    public String countAppForbidden(Map<String, Object> params) {
        return this.findObjectByMap(Mapper.BusinessType_Mapper.countAppForbidden, params);
    }

    @Override
    public BusinessType getBusinessTypeInfo(BusinessType businessType) {
        return this.findObject(Mapper.BusinessType_Mapper.getBusinessTypeInfo, businessType);
    }

}
