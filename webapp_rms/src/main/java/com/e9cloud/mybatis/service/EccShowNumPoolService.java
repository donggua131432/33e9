package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.EccShownum;
import com.e9cloud.mybatis.domain.EccSipphone;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/10.
 */
public interface EccShowNumPoolService extends IBaseService {

    PageWrapper getEccShowNumList(Page page);

    /**
     * 获取号码池信息列表
     * @param number
     * @returnＥｃｃ
     */
    List<EccShownum> getEccShowNumByNumber(String number);


    /**
     * 添加公共号码管理池
     * @param eccShownum
     */
    void addEccShowNum(EccShownum eccShownum);

    /**
     * 根据id查询EccShownum信息
     * @param id
     * @return
     */
    List<EccShownum> getEccShowNumById(String id);

    /**
     * 根据id批量删除
     * @param strId
     */
    void deleteEccShowNumByIds(String[] strId);

    /**
     * 添加公共号码管理池
     * @param eccShownum
     */
    void updateEccShowNum(EccShownum eccShownum);

    List<EccShownum> getEccShowNum(String id);

    /**
     * 公共号码管理池导出
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadEccShowNumList(Page page);

}
