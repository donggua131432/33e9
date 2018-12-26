package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Area;

import java.util.List;

/**
 * Created by Administrator on 2016/3/11.
 */
public interface AreaService extends IBaseService {

    /**
     * 查询所有的地区
     *
     * @param area
     * @return
     */
    List<Area> getAllArea(Area area);

}
