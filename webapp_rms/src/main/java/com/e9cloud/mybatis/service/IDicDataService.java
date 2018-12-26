package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.DicData;

import java.util.List;

/**
 * 字段表业务类
 */
public interface IDicDataService extends IBaseService {
    /**
     * 根据type类型查询字典信息
     * @param type
     */
    List<DicData> findDicListByType(String type);

    /**
     * 根据父类查询字典信息
     * @param pid
     */
    List<DicData> findDicListByPid(String pid);

    /**
     * 根据对象查询字典信息
     * @param dicData
     */
    DicData findDicByCode(DicData dicData);

    /**
     * 根据对象模糊查询字典信息
     * @param dicData
     */
    List<DicData> findDicLikeCode(DicData dicData);
}
