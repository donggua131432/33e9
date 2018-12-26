package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.CityCode;
import com.e9cloud.mybatis.domain.RechargeRecord;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.MaskNumService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/1.
 */
@Service
public class MaskNumServiceImpl extends BaseServiceImpl implements MaskNumService {

    /**
     * 分页查询充值记录
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageMaskNum(Page page) {
        return this.page(Mapper.MaskNum_Mapper.pageMaskNum, page);
    }

    /**
     * 查询所有城市信息
     * @return
     */
    @Override
    public List<CityCode> findcityALL() {
        return this.findObjectListByPara(Mapper.CityCode_Mapper.findcityALL,"");
    }

    /**
     * 查询所有记录
     * @param page 分页信息
     * @return
     */
    @Override
    public List<Map<String, Object>> getpageMaskNumList(Page page) {
        return this.findObjectList(Mapper.MaskNum_Mapper.getpageMaskNumList, page);
    }
}
