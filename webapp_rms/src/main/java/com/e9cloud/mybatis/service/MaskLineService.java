package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.domain.MaskNumPool;

import java.util.List;
import java.util.Map;

/**
 * 私密专线相关业务类
 * Created by dukai on 2016/6/1.
 */
public interface MaskLineService extends IBaseService{

    /**
     * 获取隐私号码池信息列表
     * @param page
     * @return
     */
    PageWrapper getMaskNumberPoolList(Page page);

    /**
     * 根据ID删除号码池信息
     * @param id
     */
    void deleteMaskNumberPoolById(String id);

    /**
     * 根据条件获取隐私号码池信息
     * @param maskNumPool
     * @return
     */
    MaskNumPool getMaskNumberPoolByObje(MaskNumPool maskNumPool);

    /**
     * 添加隐私号码池
     * @param maskNumPool
     */
    void addMaskNumberPool(MaskNumPool maskNumPool);

    /**
     * 获取隐私号信息列表
     * @param page
     * @return
     */
    PageWrapper getMaskNumberList(Page page);

    /**
     * 根据ID删除隐私号信息
     * @param id
     */
    void deleteMaskNumberById(String id);


    MaskNum getMaskNumberByNumber(String number);
    /**
     * 根据条件获取隐私号信息
     * @param maskNum
     * @return
     */
    MaskNum getMaskNumberByObj(MaskNum maskNum);

    List<MaskNum> getMaskNumList(MaskNum maskNum);

    List<MaskNum> getMaskNumListByStatus(String status);

    /**
     * 添加隐私号码池
     * @param maskNum
     */
    void addMaskNumber(MaskNum maskNum);

    void updateMaskNumber(MaskNum maskNum);

    void updateMaskNumberByAppId(Map map);

    /**
     * 分页查询隐私号
     * @param page
     * @return
     */
    PageWrapper pageMaskNumber(Page page);

    List<Map<String, Object>> downloadMaskNum(Page page);
}
