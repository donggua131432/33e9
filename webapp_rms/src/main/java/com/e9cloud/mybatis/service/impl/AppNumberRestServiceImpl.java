package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppNumberRest;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppNumberRestService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
@Service
public class AppNumberRestServiceImpl extends BaseServiceImpl implements AppNumberRestService {

    @Override
    public void saveAppNumber(AppNumberRest appNumberRest) {
        this.save(Mapper.AppNumberRest_Mapper.insert, appNumberRest);
    }

    @Override
    public void saveAppNumberList(List<AppNumberRest> appNumberRestList) {
        this.save(Mapper.AppNumberRest_Mapper.listInsert, appNumberRestList);
    }

    @Override
    public void updateAppNumber(AppNumberRest appNumberRest) {

    }
}
