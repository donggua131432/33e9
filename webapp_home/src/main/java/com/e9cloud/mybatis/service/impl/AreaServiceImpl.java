package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.Area;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/3/11.
 */
@Service
public class AreaServiceImpl extends BaseServiceImpl implements AreaService{
    /**
     * 查询所有的地区
     *
     * @param area
     * @return
     */
    @Override
    public List<Area> getAllArea(Area area) {
        return this.findObjectList(Mapper.Area_Mapper.selectAll, area);
    }
}
