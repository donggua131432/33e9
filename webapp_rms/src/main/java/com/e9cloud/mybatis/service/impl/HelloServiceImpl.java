package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/12/14.demo
 */
@Service
public class HelloServiceImpl extends BaseServiceImpl implements HelloService{
    @Override
    public String getValueByKey(String key) {
        return this.findObjectByPara(Mapper.Hello_Mapper.selectValueByKey,key).toString();
    }
}
