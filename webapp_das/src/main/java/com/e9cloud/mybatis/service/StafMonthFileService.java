package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.StafMonthFile;

import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/3/12.
 */
public interface StafMonthFileService extends IBaseService {

    /**
     * 根据条件下载信息
     * @param map
     * @return
     */
    List<StafMonthFile> getStafMonthFileList(Map map);

}
