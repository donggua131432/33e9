package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.BusinessType;

import java.util.Map;

/**
 * Created by admin on 2016/8/22.
 */
public interface BusTypeService extends IBaseService {

   public BusinessType checkBusinessInfo(Map<String, Object> params);


    String countApp(Map<String, Object> params);

    String countAxb(Map<String, Object> params);

    String count1(String uid);

    String count3(String uid);


    String countAppForbidden(Map<String, Object> params);


    void openBusinessType(BusinessType businessType);

    void updateStatus(BusinessType businessType);

    //根据条件获取busTypeINfo
    BusinessType getBusinessTypeInfo(BusinessType businessType);

}
