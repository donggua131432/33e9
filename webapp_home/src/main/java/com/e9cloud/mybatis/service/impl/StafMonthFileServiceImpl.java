package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.StafMonthFile;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.StafMonthFileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/3/12.
 */
@Service
public class StafMonthFileServiceImpl extends BaseServiceImpl implements StafMonthFileService {

    /**
     * 根据条件下载信息
     * @param map
     * @return
     */
    @Override
    public List<StafMonthFile> getStafMonthFileList(Map map) {
        return this.findObjectListByMap(Mapper.StafMonthFile_Mapper.findStafMonthFileList, map);
    }
}
