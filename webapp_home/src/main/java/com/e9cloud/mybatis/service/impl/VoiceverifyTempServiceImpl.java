package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.VoiceverifyTemp;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceverifyTempService;
import org.springframework.stereotype.Service;


@Service
public class VoiceverifyTempServiceImpl extends BaseServiceImpl implements VoiceverifyTempService {


    @Override
    public PageWrapper pageVoiceverifyList(Page page) {
        return this.page(Mapper.VoiceverifyTemp_Mapper.pageVoiceverifyList, page);
    }

    @Override
    public void saveVoiceverifyTemp(VoiceverifyTemp voiceverifyTemp) {
        this.save(Mapper.VoiceverifyTemp_Mapper.insertSelective, voiceverifyTemp);

    }

    /**
     * 验证语音模板名称的唯一性
     *
     * @param voiceverifyTemp
     * @return
     */
    @Override
    public boolean checkNameUnique(VoiceverifyTemp voiceverifyTemp) {
        long l = this.findObject(Mapper.VoiceverifyTemp_Mapper.countNameUnique, voiceverifyTemp);
        return l == 0;
    }

    @Override
    public void updateByPrimaryKeySelective(VoiceverifyTemp voiceverifyTemp) {
        this.update(Mapper.VoiceverifyTemp_Mapper.updateByPrimaryKeySelective,voiceverifyTemp);
    }

    @Override
    public VoiceverifyTemp getVoiceverifyTempByID(String id) {
        return this.findObjectByPara(Mapper.VoiceverifyTemp_Mapper.getVoiceverifyTempByID,id);
    }
}
