package com.e9cloud.mybatis.service;


import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SysAvar;

/**
 * Created by dukai on 2016/8/18.
 */
public interface SysAavrService extends IBaseService {
    /**
     * 获取系统变量
     * @param var
     * @return
     */
    SysAvar getSysAvarByVar(String var);

}
