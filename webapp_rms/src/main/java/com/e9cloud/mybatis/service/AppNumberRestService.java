package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppNumberRest;

import java.util.List;

/**
 * Created by wj on 2016/3/3.
 */
public interface AppNumberRestService extends IBaseService {
    /**
     * 保存
     * @param appNumberRest
     */
    void saveAppNumber(AppNumberRest appNumberRest);


    /**
     * 保存
     * @param appNumberRestList
     */
    void saveAppNumberList(List<AppNumberRest> appNumberRestList);

    /**
     * 修改
     * @param appNumberRest
     */
    void updateAppNumber(AppNumberRest appNumberRest);

}
