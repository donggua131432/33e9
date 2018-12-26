package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AxbAppPool;
import com.e9cloud.mybatis.domain.AxbCustNumber;
import com.e9cloud.mybatis.domain.AxbNumRelation;
import com.e9cloud.mybatis.domain.AxbPhone;

import java.util.List;
import java.util.Map;

/**
 * 客户号码资源池业务类
 * Created by hzd on 2017/4/19.
 */
public interface AxbCustNumResPoolService extends IBaseService {

    /**
     * 获取客户号码池信息列表
     * @param page
     * @return
     */
    PageWrapper getAxbCustAppPoolList(Page page);

    /**
     * 添加客户号码
     * @param axbCustNumber
     */
    void addAxbCustNumberPool(AxbCustNumber axbCustNumber, AxbPhone axbPhone);

    /**
     * 根据条件获取客户号码信息
     * @param axbCustNumber
     * @return
     */
    List<AxbCustNumber> getAxbCustNumberPoolByObj(AxbCustNumber axbCustNumber);

    /**
     * 获取用户号码信息列表
     * @param page
     * @return
     */
    PageWrapper getAxbCustNumberList(Page page);

    //用户号码管理池导出
    List<Map<String, Object>> downloadAxbCustNumPool(Page page);

    /**
     * 根据条件获取用户号码池信息（app_pool）
     * @param axbAppPool
     * @return
     */
    AxbAppPool getAxbCustAppPoolByObj(AxbAppPool axbAppPool);

    /**
     * 添加用户号码池
     * @param axbAppPool
     */
    void addAxbCustAppPool(AxbAppPool axbAppPool);

    /**
     * 用于查询app下是否有正常或者已锁定的号码列表
     * @param appid
     */
    boolean checkHasNumber(String appid);

    /**
     * 根据ID删除号码池信息
     * @param id
     */
    void deleteAxbCustAppPoolById(String id);

    /**
     * 根据number查询虚拟小号是否已绑定
     * @param number
     */
    AxbNumRelation getAxbNumRelationByNum(String number);

    //没绑定小号，直接物理删除
    void deleteAxbCustNum(AxbCustNumber axbCustNumber);

    //绑定小号，直接逻辑删除
    void updateAxbCustNumByStatus(AxbCustNumber axbCustNumber);

    //根据条件修改用户号码关系表
    void updateAxbNumRelationByObj(AxbNumRelation axbNumRelation);

}
