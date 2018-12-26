package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.domain.CodeType;

/**
 * Created by admin on 2016/9/20.
 */
public interface CodeTypeService extends IBaseService {

    /**
     * 分页查询
     * @return
     */
    PageWrapper pageCodeType(Page page);

    void updateStatus(CodeType codeType);

}
