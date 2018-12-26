package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.EccFixphone;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2017/2/13.
 */
public interface EccFixPhonePoolService extends IBaseService {

    PageWrapper getEccFixPhoneList(Page page);

    //ecc 非fixphone列表导出
    List<Map<String, Object>> downloadEccFixPhonePool(Page page);

    List<EccFixphone> getEccFixphoneByNumber(String fixphone);

    void saveFixPhone(EccFixphone eccFixphone);

    void deleteByPK(String id);
}
