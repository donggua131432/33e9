package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.StatVnDayRecord;
import com.e9cloud.rest.obt.VnObj;
import com.e9cloud.rest.obt.VnWhite;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface VnService extends IBaseService {


    /**
     * 查询Vn是否存在
     */
    VnObj findVnByObj(VnObj obj);

    /**
     * 保存虚拟号
     */
    void saveVn(VnObj obj);
    /**
     * 保存白名单
     */
    void saveWhiteNum(VnWhite obj);

    /**
     * 获取统计虚拟号话单统计信息
     * @param map
     * @return
     */
    List<StatVnDayRecord> getStatVnDayRecordByMap(Map map);



}
