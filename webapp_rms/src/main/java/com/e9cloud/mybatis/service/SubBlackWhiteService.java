package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.NumberBlack;
import com.e9cloud.mybatis.domain.SipBlackWhite;

import java.util.List;
import java.util.Map;

/**
 * 子帐号黑白名单
 */
public interface SubBlackWhiteService extends IBaseService {

    /**
     * 根据ID查找号码黑白名单信息
     * @param id
     * @return
     */
    SipBlackWhite findNumberById(Integer id);

    /**
     * 根据多个ID查找号码黑名单信息
     * @param map
     * @return
     */
    List<SipBlackWhite> findNumberListByNumbers(Map map);

    /**
     * 保存号码黑名单信息
     * @param list 号码黑名单集合
     */
    void saveNumber(List<SipBlackWhite> list);

    /**
     * 删除号码黑名单信息
     */
    void deleteNumber(Integer id);

    /**
     * 分页查询号码黑名单信息
     * @param page
     * @return
     */
    PageWrapper pageNumber(Page page);

    /**
     * 导出号码黑名单
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadNumber(Page page);
}
