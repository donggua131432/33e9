package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CityCode;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AxbNumService;
import com.e9cloud.mybatis.service.MaskNumService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/1.
 */
@Service
public class AxbNumServiceImpl extends BaseServiceImpl implements AxbNumService {

    /**

     * 分页查询充值记录
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageAxbNum(Page page) {
        return this.page(Mapper.AxbNum_Mapper.pageAxbNum, page);
    }

    /**
     * 查询所有城市信息
     * @return
     */
    @Override
    public List<CityCode> findcityALL() {
        return this.findObjectListByPara(Mapper.CityManager_Mapper.findcityALL,"");
    }

    /**
     * 查询所有记录
     * @param page 分页信息
     * @return
     */
    @Override
    public List<Map<String, Object>> getpageAxbNumList(Page page) {
        return this.findObjectList(Mapper.AxbNum_Mapper.getpageAxbNumList, page);
    }
}
