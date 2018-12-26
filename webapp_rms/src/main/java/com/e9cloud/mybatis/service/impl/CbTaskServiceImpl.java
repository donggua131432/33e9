package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.CbTaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/3.
 */
@Service
public class CbTaskServiceImpl extends BaseServiceImpl implements CbTaskService {

    /**
     * 保存应用
     *
     * @param cbTask 应用的基本信息
     */
    @Override
    public void saveCbTask(CbTask cbTask) {
        this.save(Mapper.CbTask_Mapper.insertSelective, cbTask);
    }


}
