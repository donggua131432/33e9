package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

import java.util.Map;

/**
 * Created by dukai on 2016/8/15.
 */
public interface SpendFeeService extends IBaseService {

    /**
     * 获取SIP本月消费总和
     * @param map
     * @return
     */
    Map<String,Object> getSipCurrentMonthSumFee(Map map);

    /**
     * 获取SIP昨日消费总和
     * @param map
     * @return
     */
    Map<String,Object> getSipYesterdaySumFee(Map map);

    /**
     * 获取本月消费总和
     * @param map
     * @return
     */
    Map<String,Object> getCurrentMonthSumFee(Map map);

    /**
     * 获取昨日消费总和
     * @param map
     * @return
     */
    Map<String,Object> getYesterdaySumFee(Map map);
}
