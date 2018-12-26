package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.TelnoOperator;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.TelnoOperatorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/08/10
 */
@Service
public class TelnoOperatorServiceImpl extends BaseServiceImpl implements TelnoOperatorService{

    /**
     *获取运营商表信息列表
     * @param telnoOperator
     * @return
     */
    @Override
    public List<TelnoOperator> getTelnoOperatorList(TelnoOperator telnoOperator) {
        return this.findObjectList(Mapper.TelnoOperator_Mapper.selectTelnoOperatorByObj, telnoOperator);
    }
}
