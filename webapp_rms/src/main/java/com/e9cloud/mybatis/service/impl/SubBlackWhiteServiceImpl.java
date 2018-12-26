package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SipBlackWhite;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SubBlackWhiteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/22.
 */
@Service
public class SubBlackWhiteServiceImpl  extends BaseServiceImpl implements SubBlackWhiteService {
    /**
     * 根据ID查找号码黑白名单信息
     *
     * @param id
     * @return
     */
    @Override
    public SipBlackWhite findNumberById(Integer id) {
        return this.findObject(Mapper.SubWhiteBlack_Mapper.selectNumberById,id);
    }

    /**
     * 根据多个ID查找号码黑名单信息
     *
     * @param map
     * @return
     */
    @Override
    public List<SipBlackWhite> findNumberListByNumbers(Map map) {
        return this.findObjectList(Mapper.SubWhiteBlack_Mapper.selectNumberListByNumbers,map);
    }

    /**
     * 保存号码黑名单信息
     *
     * @param list 号码黑名单集合
     */
    @Override
    public void saveNumber(List<SipBlackWhite> list) {
            this.save(Mapper.SubWhiteBlack_Mapper.saveNumber,list);
    }

    /**
     * 删除号码黑名单信息
     *
     * @param id
     */
    @Override
    public void deleteNumber(Integer id) {
        this.delete(Mapper.SubWhiteBlack_Mapper.deleteNumber,id);
    }

    /**
     * 分页查询号码黑名单信息
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageNumber(Page page) {
         return this.page(Mapper.SubWhiteBlack_Mapper.pageNumber, page);
    }

    /**
     * 导出号码黑名单
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadNumber(Page page) {
        return null;
    }
}
