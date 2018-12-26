package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CodeType;

import java.util.List;

/**
 * Created by shenliang on 2016/9/30.
 */
public interface CodeTypeService  extends IBaseService {

    List<CodeType> findAll();
}
