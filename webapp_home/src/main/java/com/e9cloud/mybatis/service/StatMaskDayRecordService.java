package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Dukai on 2016/6/21.
 */
public interface StatMaskDayRecordService extends IBaseService {

    /**
     * 获取每天的消费总额
     * @param map
     * @return
     */
    BigDecimal getStatMaskDayConsumeTotal(Map map);


}
