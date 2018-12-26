package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.domain.ExtraType;

/**
 * Created by admin on 2016/8/23.
 */
public interface ExtraTypeService extends IBaseService {

    public ExtraType checkExtraInfo(String sid);

    public ExtraType checkExtraBphone(String sid);

    void openExtraType(ExtraType extraType);

    void updateStatus(ExtraType extraType);

    String count1(String uid);
}
