package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.NumberBlack;

import java.util.List;
import java.util.Map;

/**
 * 号码黑名单
 * Created by dukai on 2016/7/6.
 */
public interface NumberBlackService extends IBaseService {

    /**
     * 根据ID查找号码黑名单信息
     * @param id 费率ID
     * @return FeeRate
     */
    NumberBlack findNumberBlackById(Integer id);

    /**
     * 根据多个ID查找号码黑名单信息
     * @param numbers
     * @return
     */
    List<NumberBlack> findNumberBlackListByNumbers(String[] numbers);

    /**
     * 保存号码黑名单信息
     * @param list 号码黑名单集合
     */
    void saveNumberBlack(List<NumberBlack> list);

    /**
     * 删除号码黑名单信息
     */
    void deleteNumberBlack(Integer id);

    /**
     * 分页查询号码黑名单信息
     * @param page
     * @return
     */
    PageWrapper pageNumberBlack(Page page);

    /**
     * 导出号码黑名单
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadBlackNumber(Page page);
}
