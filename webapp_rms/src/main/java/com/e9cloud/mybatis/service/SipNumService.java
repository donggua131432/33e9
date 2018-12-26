package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SipAppNumPool;
import com.e9cloud.mybatis.domain.SipNumPool;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/7/14.
 */
public interface SipNumService extends IBaseService {

    /**
     * 获取隐私号信息列表
     * @param number
     * @return
     */
    SipAppNumPool getSipNumberByNumber(String number);

    /**
     * 添加SIP全局号码池
     * @param sipAppNumPool
     */
    void addSIPNumberPool(SipAppNumPool sipAppNumPool);

    PageWrapper getSipNumberList(Page page);

    //全局and子账号 号码池
    PageWrapper getSipNumList(Page page);

    //全局and子账号 号码池--导出
    List<Map<String, Object>> downloadSipNumList(Page page);

    //全局号码导出
    List<Map<String, Object>> getSipNumberListDownload(Page page);

    SipAppNumPool getSipNumInfoById(Integer id);


    void updateSipNumber(SipAppNumPool sipAppNumPool);

    void deleteSipNumber(SipAppNumPool sipAppNumPool);

    /**
     * 根据信息校验公司的唯一性
     * @param sipAppNumPool
     * @return
     */
    boolean checkSipNumUnique(SipAppNumPool sipAppNumPool);

    void deleteStatusBylink(String[] strId);

    List<SipNumPool> getSipNumPoolByNumber(Map map);

}
