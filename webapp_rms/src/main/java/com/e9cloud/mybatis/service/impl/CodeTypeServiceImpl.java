package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.domain.CodeType;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CodeTypeService;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/9/20.
 */
@Service
public class CodeTypeServiceImpl extends BaseServiceImpl implements CodeTypeService {


    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCodeType(Page page) {
        return this.page(Mapper.CodeType_Mapper.pageCodeType, page);
    }

    @Override
    public void updateStatus(CodeType codeType) {
        this.update(Mapper.CodeType_Mapper.updateStatus, codeType);
    }
}
