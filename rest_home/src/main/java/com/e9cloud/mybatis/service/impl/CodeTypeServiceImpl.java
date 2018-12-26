package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CodeType;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CodeTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shenliang on 2016/9/30.
 */
@Service
public class CodeTypeServiceImpl extends BaseServiceImpl implements CodeTypeService {


    @Override
    public List<CodeType> findAll() {
        return super.findList(Mapper.Code_Type_Mapper.findAll);
    }
}
