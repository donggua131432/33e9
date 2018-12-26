package com.e9cloud.mybatis.service.impl;


import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.FeeListService;
import org.springframework.stereotype.Service;
/**
 * Created by admin on 2016/2/22.
 */
@Service
public class FeeListServiceImpl extends BaseServiceImpl implements FeeListService {

    /**
     * 查询用户资费信息
     *
     * @param uid
     */
    @Override
    public User feeListRecord(String uid) {
        return this.findObjectByPara(Mapper.RestRate_Mapper.selectRestRateForFeeid, uid);
    }

    @Override
    public RestRate findRestRateByFeeid(String feeid) {
        return this.findObjectByPara(Mapper.RestRate_Mapper.selectRestRateForFeeid, feeid);
    }
    @Override
    public CallRate findCallRateByFeeid(String feeid) {
        return this.findObjectByPara(Mapper.CallRate_Mapper.selectCallRateForFeeid, feeid);
    }
    @Override
    public MaskRate findMaskRateByFeeid(String feeid) {
        return this.findObjectByPara(Mapper.MaskRate_Mapper.selectMaskRateForFeeid, feeid);
    }

    @Override
    public SipphonRate findSipphonRateByFeeid(String feeid) {
        return this.findObjectByPara(Mapper.MaskRate_Mapper.selectSipphonRateForFeeid, feeid);
    }

    @Override
    public VoiceRate findVoiceRateByFeeid(String feeid) {
        return this.findObjectByPara(Mapper.MaskRate_Mapper.findVoiceRateByFeeid, feeid);
    }

    @Override
    public VoiceVerifyRate findVoiceVerifyRateByFeeid(String feeid) {
        return this.findObjectByPara(Mapper.VoiceverifyRate_Mapper.findVoiceVerifyRateByFeeid, feeid);
    }

    @Override
    public IvrRate findIvrRateRateByFeeid(String feeid) {
        return this.findObjectByPara(Mapper.IvrRate_Mapper.findIvrRateRateByFeeid, feeid);
    }


    @Override
    public AxbRate findAxbRateByFeeid(String feeid) {
        return this.findObjectByPara(Mapper.AxbRate_Mapper.findAxbRateByFeeid, feeid);
    }

    @Override
    public String countCc(String sid) {
        return this.findObjectByPara(Mapper.MaskRate_Mapper.countCc, sid);
    }

    @Override
    public String countSipPhone(String sid) {
        return this.findObjectByPara(Mapper.MaskRate_Mapper.countSipPhone, sid);
    }

    @Override
    public String countEcc(String sid) {
        return this.findObjectByPara(Mapper.MaskRate_Mapper.countEcc, sid);
    }

    @Override
    public String countAxb(String sid) {
        return this.findObjectByPara(Mapper.MaskRate_Mapper.countAxb, sid);
    }
}
