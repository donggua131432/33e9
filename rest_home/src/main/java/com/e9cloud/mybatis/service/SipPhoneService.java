package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SpApplyNum;
import java.util.List;

/**
 * Created by dukai on 2016/10/29.
 */
public interface SipPhoneService extends IBaseService {
    /**
     * 获取和App相关联的sipPhone号码列表
     * @param appid
     * @return
     */
    List<SpApplyNum> getSpApplyNumList(String appid);

    /**
     * 获取sipPhone状态信息
     * @param spApplyNum
     * @return
     */
    SpApplyNum getSipPhoneStat(SpApplyNum spApplyNum);

    /**
     * 更改sipPhone状态信息
     * @param spApplyNum
     * @return
     */
    void updateSipPhoneStat(SpApplyNum spApplyNum);

}
