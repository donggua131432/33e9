package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.IDicDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15.
 */
@Service
public class DicDataServiceImpl extends BaseServiceImpl implements IDicDataService {

    /**
     * 根据type类型查询字典信息
     *
     * @param type
     */
    @Override
    public List<DicData> findDicListByType(String type) {
        return this.findObjectList(Mapper.DicData_Mapper.findDicListByType,type);
    }

    /**
     * 根据父类查询字典信息
     *
     * @param pid
     */
    @Override
    public List<DicData> findDicListByPid(String pid) {
        return this.findObjectList(Mapper.DicData_Mapper.findDicListByPid,pid);
    }

    /**
     * 根据对象查询字典信息
     * @param dicData
     * @return
     */
    @Override
    public DicData findDicByCode(DicData dicData) {
        return this.findObject(Mapper.DicData_Mapper.findDicByCode, dicData);
    }

    /**
     * 根据对象模糊查询字典信息
     * @param dicData
     * @return
     */
    @Override
    public  List<DicData> findDicLikeCode(DicData dicData) {
        return this.findObjectList(Mapper.DicData_Mapper.findDicLikeCode, dicData);
    }
}
