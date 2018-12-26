package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RestRate;
import com.e9cloud.mybatis.domain.RestStatDayRecord;

import java.util.Date;

/**
 * Created by dukai on 2016/8/23.
 */
public interface RateService extends IBaseService{
    /** 获取rest标准费用信息 **/
    RestRate getStandardRestRate(String feeid);
}
