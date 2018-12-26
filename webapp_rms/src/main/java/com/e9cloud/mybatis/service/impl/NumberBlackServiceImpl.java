package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.NumberBlack;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.NumberBlackService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/7/6.
 */
@Service
public class NumberBlackServiceImpl extends BaseServiceImpl implements NumberBlackService {

    /**
     * 根据ID查找号码黑名单信息
     * @param id 费率ID
     * @return FeeRate
     */
    @Override
    public NumberBlack findNumberBlackById(Integer id) {
        return this.findObject(Mapper.NumberBlack_Mapper.selectNumberBlackById, id);
    }

    /**
     * 根据多个ID查找号码黑名单信息
     * @param numbers
     * @return
     */
    @Override
    public List<NumberBlack> findNumberBlackListByNumbers(String[] numbers) {
        return this.findObjectList(Mapper.NumberBlack_Mapper.selectNumberBlackListByNumbers, numbers);
    }

    /**
     * 保存号码黑名单信息
     * @param list 号码黑名单集合
     */
    @Override
    public void saveNumberBlack(List<NumberBlack> list) {
        this.save(Mapper.NumberBlack_Mapper.saveNumberBlack, list);
    }

    /**
     * 删除号码黑名单信息
     */
    @Override
    public void deleteNumberBlack(Integer id) {
        this.delete(Mapper.NumberBlack_Mapper.deleteNumberBlack, id);
    }

    /**
     * 分页查询号码黑名单信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageNumberBlack(Page page) {
        return this.page(Mapper.NumberBlack_Mapper.pageNumberBlack, page);
    }

    /**
     * 导出号码黑名单
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadBlackNumber(Page page) {
        return this.download(Mapper.NumberBlack_Mapper.pageNumberBlack, page);
    }
}
