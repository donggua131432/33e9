package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.StatVnDayRecord;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VnService;
import com.e9cloud.rest.obt.VnObj;
import com.e9cloud.rest.obt.VnWhite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/26.
 */
@Service
public class VnSerivceImpl  extends BaseServiceImpl implements VnService {
    /**
     * 保存白名单
     *
     * @param obj
     */
    @Override
    public void saveWhiteNum(VnWhite obj) {
        this.save(Mapper.VN_Mapper.saveWhiteNum,obj);
    }

    /**
     * 保存虚拟号
     *
     * @param obj
     */
    @Override
    public void saveVn(VnObj obj) {
         this.save(Mapper.VN_Mapper.saveVn,obj);
    }

    /**
     * 查询Vn是否存在
     *
     * @param obj
     */
    @Override
    public VnObj findVnByObj(VnObj obj) {
        return (VnObj)this.findObject(Mapper.VN_Mapper.existsVn,obj);
    }


    /**
     * 获取统计虚拟号话单统计信息
     * @param map
     * @return
     */
    @Override
    public List<StatVnDayRecord> getStatVnDayRecordByMap(Map map) {
        return this.findObjectListByMap(Mapper.VN_Mapper.selectStatVnDayRecordByMap, map);
    }


}
