package com.e9cloud.mybatis.service;


import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;

/**
 * Created by admin on 2016/2/22.
 */
public interface FeeListService extends IBaseService {

    /**
     * 查询用户资费列表信息
     * @param uid
     */
    User feeListRecord(String uid);

    RestRate findRestRateByFeeid(String feeid);

    VoiceVerifyRate findVoiceVerifyRateByFeeid(String feeid);

    CallRate findCallRateByFeeid(String feeid);

    MaskRate findMaskRateByFeeid(String feeid);

    VoiceRate findVoiceRateByFeeid(String feeid);


    SipphonRate findSipphonRateByFeeid(String feeid);

    IvrRate findIvrRateRateByFeeid(String feeid);

    AxbRate findAxbRateByFeeid(String feeid);

    String countCc(String sid);

    String countSipPhone(String sid);

    String countEcc(String sid);

    String countAxb(String sid);
}
