package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RestRate;
import com.e9cloud.mybatis.domain.RestStatDayRecord;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RateService;
import com.e9cloud.mybatis.service.RestStatRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dukai on 2016/8/23.
 */
@Service
public class RateServiceImpl extends BaseServiceImpl implements RateService {

    /**
     * 获取Rest标准费用信息
     * @param feeid
     * @return
     */
    @Override
    public RestRate getStandardRestRate(String feeid) {
        return this.findObject(Mapper.Rate_Mapper.selectRestRateByFeeId, feeid);
    }

}
