package com.e9cloud.mybatis.service.impl;


import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.FeeListService;
import com.e9cloud.mybatis.service.SysAavrService;
import org.springframework.stereotype.Service;

/**
 * Created by dukai on 2016/8/18.
 */
@Service
public class SysAvarServiceImpl extends BaseServiceImpl implements SysAavrService {

    /**
     * 获取系统变量
     * @param var
     * @return
     */
    @Override
    public SysAvar getSysAvarByVar(String var) {
        return this.findObject(Mapper.SysAvar_Mapper.selectSysAvarByVar, var);
    }
}
