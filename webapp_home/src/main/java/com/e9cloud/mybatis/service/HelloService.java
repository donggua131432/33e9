package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Account;

/**
 * Created by Administrator on 2015/12/14.
 */
public interface HelloService extends IBaseService{

    public String getValueByKey(String key);

    public PageWrapper getDataByPage(Page page);

}
