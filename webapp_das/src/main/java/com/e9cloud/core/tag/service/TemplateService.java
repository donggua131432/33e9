package com.e9cloud.core.tag.service;

import com.e9cloud.core.freemarker.DataModelWrapper;
import com.e9cloud.mybatis.base.IBaseService;

/**
 *  用于处理模板和数据模型
 *
 * Created by Administrator on 2015/12/17.
 */
public interface TemplateService extends IBaseService {

    /**
     * 根据主键查询模板和数据模型
     * @param id 主键
     * @return dataModelWrapper
     */
    DataModelWrapper getDataModelWrapperByPK(String id);
}
