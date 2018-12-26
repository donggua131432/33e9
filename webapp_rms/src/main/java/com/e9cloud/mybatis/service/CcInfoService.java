package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CcInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by pengchunchen on 2016/8/5.
 */
public interface CcInfoService extends IBaseService {

    /**
     * 保存呼叫中心
     * @param ccInfo 
     */
    void saveCcInfo(CcInfo ccInfo);

    /**
     * 删除呼叫中心
     * @param ccInfo 呼叫中心的基本信息
     */
    void delCcInfo(CcInfo ccInfo);

    /**
     * 修改呼叫中心
     * @param ccInfo 呼叫中心的基本信息
     */
    void updateCcInfo(CcInfo ccInfo);

    /**
     * 修改呼叫中心状态
     * @param ccInfo 呼叫中心的基本信息
     */
    void updateCcInfoStatus(CcInfo ccInfo);

    /**
     * 修改呼叫中心默认（添加或修改选择当前默认时调用，变更之前的默认为否）
     * @param ccInfo 呼叫中心的基本信息
     */
    void updateCcInfodefaultCc(CcInfo ccInfo);

    /**
     * 根据id查找一条呼叫中心信息
     * @param id
     */
    CcInfo queryCcInfoById(String id);

    /**
     * 校验呼叫中心存在性
     * @param ccInfo 呼叫中心的基本信息
     */
    Integer countCcInfo(CcInfo ccInfo);

    /**
     * 分页选取呼叫中心列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageCcInfoList(Page page);

    /**
     * 导出呼叫中心
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadCcInfo(Page page);

    /**
     * 根据sid查找呼叫中心列表
     * @param sid
     */
    List<CcInfo> queryCcInfoBySid(String sid);

}
