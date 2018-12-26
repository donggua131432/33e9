package com.e9cloud.mybatis.service;

import java.util.List;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.TelnoInfo;

/**
 *
 */
public interface TelnoInfoService extends IBaseService {


    /**
     * 查出所有电话号码
     *
     */
  public  List<TelnoInfo> getAll();




}
