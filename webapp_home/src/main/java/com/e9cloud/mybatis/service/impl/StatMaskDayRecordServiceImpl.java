package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.StatMaskDayRecordService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Dukai on 2016/6/21.
 */
@Service
public class StatMaskDayRecordServiceImpl extends BaseServiceImpl implements StatMaskDayRecordService {

    /**
     * 获取每天的消费总额
     * @param map
     * @return
     */
    @Override
    public BigDecimal getStatMaskDayConsumeTotal(Map map) {
        return (BigDecimal) this.findObjectByMap(Mapper.StatMaskDayRecord_Mapper.selectMaskDayConsumeTotal, map);
    }


}
