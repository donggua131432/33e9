package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CbTask;

/**
 * Created by dell on 2017/3/23.
 */
public interface CbTaskService extends IBaseService {


    /**
     * 保存
     * @param cbTask
     */
    void saveCbTask(CbTask cbTask);

}
