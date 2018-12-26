package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.TempVoice;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.MouldVoiceService;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/9/22.
 */
@Service
public class MouldVoiceServiceImpl extends BaseServiceImpl implements MouldVoiceService {
    /**
     * 创建模板语音
     * @param tempVoice
     * @return
     */
    @Override
    public void saveTempVoice(TempVoice tempVoice) {
        this.save(Mapper.TempVoice_Mapper.saveTempVoice, tempVoice);

    }

    /**
     * 分页查询模板语音记录
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageVoiceList(Page page) {
        return this.page(Mapper.TempVoice_Mapper.pageVoiceList, page);
    }

    @Override
    public void delTemp(TempVoice tempVoice) {
        this.update(Mapper.TempVoice_Mapper.delTemp,tempVoice);
    }


    @Override
    public TempVoice getTempVoiceByID(String id) {
        return this.findObjectByPara(Mapper.TempVoice_Mapper.getTempVoiceByID,id);
    }

    /**
     * 修改模板语音
     * @param tempVoice
     * @return
     */
    @Override
    public void updateTempVoice(TempVoice tempVoice) {
        this.update(Mapper.TempVoice_Mapper.updateTempVoice, tempVoice);

    }

    @Override
    public TempVoice getTempVoice(TempVoice voice) {
        return this.findObject(Mapper.TempVoice_Mapper.getTempVoice,voice);
    }

    /**
     * 验证语音模板名称的唯一性
     *
     * @param tempVoice
     * @return
     */
    @Override
    public boolean checkNameUnique(TempVoice tempVoice) {
        long l = this.findObject(Mapper.TempVoice_Mapper.countNameUnique, tempVoice);
        return l == 0;
    }
}
