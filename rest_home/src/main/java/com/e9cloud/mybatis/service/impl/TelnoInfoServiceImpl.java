package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.TelnoInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.TelnoInfoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TelnoInfoServiceImpl extends BaseServiceImpl implements TelnoInfoService {


    @Override
    public List<TelnoInfo> getAll(){
        return this.findList(Mapper.Telno_Mapper.findAllTelno) ;
    }
}