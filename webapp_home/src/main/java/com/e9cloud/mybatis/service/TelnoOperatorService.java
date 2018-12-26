package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.TelnoOperator;

import java.util.List;

/**
 * Created by Administrator on 2016/08/10.
 */
public interface TelnoOperatorService extends IBaseService{

    /**
     * 获取运营商表信息列表
     * @param telnoOperator
     * @return
     */
    List<TelnoOperator> getTelnoOperatorList(TelnoOperator telnoOperator);
}
