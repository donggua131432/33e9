package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.domain.ExtraType;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.ExtraTypeService;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/8/23.
 */
@Service
public class ExtraTypeServiceImpl extends BaseServiceImpl implements ExtraTypeService {

    @Override
    public ExtraType checkExtraInfo(String sid) {
        return this.findObject(Mapper.ExtraType_Mapper.checkExtraInfo, sid);
    }

    @Override
    public ExtraType checkExtraBphone(String sid) {
        return this.findObject(Mapper.ExtraType_Mapper.checkExtraBphone, sid);
    }

    public void openExtraType(ExtraType extraType) {
        this.save(Mapper.ExtraType_Mapper.insert, extraType);
    }

    public void updateStatus(ExtraType extraType) {
        this.update(Mapper.ExtraType_Mapper.updateStatus, extraType);
    }

    @Override
    public String count1(String uid) {
        return this.findObjectByPara(Mapper.ExtraType_Mapper.count1, uid);
    }


}
