package com.e9cloud.core.tag.service.impl;

import com.e9cloud.core.freemarker.DataModelWrapper;
import com.e9cloud.core.tag.service.TemplateService;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理模板和数据模型
 *
 * Created by Administrator on 2015/12/17.
 */
@Service
public class TemplateServiceImpl extends BaseServiceImpl implements TemplateService {

    /**
     * 根据主键查询模板和数据模型
     * @param id 主键
     * @return dataModelWrapper
     */
    @Override
    public DataModelWrapper getDataModelWrapperByPK(String id) {
        // TODO:this is a demo
        String stringTemplate = "<h1>this is a demo for template, th PK is ${pk}</h1>";

        /* 创建数据模型 */
        Map<String, Object> dataModel = new HashMap<String, Object>();
        dataModel.put("pk", id);

        return new DataModelWrapper(stringTemplate,dataModel);
    }
}
