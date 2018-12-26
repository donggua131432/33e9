package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SipDayRecord;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SpendFeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/8/15.
 */
@Service
public class SpendFeeServiceImpl extends BaseServiceImpl implements SpendFeeService {

    /**
     * 获取Sip本月消费总和
     * @param map
     * @return
     */
    @Override
    public Map<String,Object> getSipCurrentMonthSumFee(Map map) {
        return this.findObject(Mapper.SipDayRecord_Mapper.selectSipCurrentMonthSumFee, map);
    }

    /**
     * 获取Sip昨日消费总和
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> getSipYesterdaySumFee(Map map) {
        return this.findObject(Mapper.SipDayRecord_Mapper.selectSipYesterdaySumFee, map);
    }

    /**
     * 获取本月消费总和
     * @param map
     * @return
     */
    @Override
    public Map<String,Object> getCurrentMonthSumFee(Map map) {
        return this.findObject(Mapper.SipDayRecord_Mapper.selectCurrentMonthSumFee, map);
    }

    /**
     * 获取昨日消费总和
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> getYesterdaySumFee(Map map) {
        return this.findObject(Mapper.SipDayRecord_Mapper.selectYesterdaySumFee, map);
    }
}
