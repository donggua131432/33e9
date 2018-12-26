package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/10.
 */
public interface SipNumResPoolService extends IBaseService {

    PageWrapper getSipPhoneList(Page page);

    /**
     * 获取号码池信息列表
     * @param sipPhone
     * @returnＥｃｃ
     */
    List<EccSipphone> getSipNumResPoolBySipPhone(String sipPhone);


    /**
     * 添加公共号码管理池
     * @param eccSipPhone
     */
    void addSipPhone(EccSipphone eccSipPhone);

    /**
     * 根据id查询EccSipphone信息
     * @param id
     * @return
     */
    List<EccSipphone> getSipPhoneById(String id);

    /**
     * 根据id批量删除
     * @param strId
     */
    void deleteSipPhoneByIds(String[] strId);

    /**
     * 公共号码管理池导出
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadSipPhonePool(Page page);

    /**
     * 为下拉框获取IPPort
     * @return
     */
    List<EccSipphone> getIPPortList();

    void updateEccSipphone(EccSipphone sipphone);
}
