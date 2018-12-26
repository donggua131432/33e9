package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.domain.MaskNumPool;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.MaskLineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 私密专线相关业务实现类
 * Created by dukai on 2016/6/1.
 */
@Service
public class MaskLineServiceImpl extends BaseServiceImpl implements MaskLineService {

    /**
     * 获取隐私号码池信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper getMaskNumberPoolList(Page page) {
        return this.page(Mapper.MaskLine_Mapper.selectMaskNumberPoolList, page);
    }

    /**
     * 根据ID删除隐私号码池信息
     * @param id
     */
    @Override
    public void deleteMaskNumberPoolById(String id) {
        this.delete(Mapper.MaskLine_Mapper.deleteMaskNumberPool, id);
    }

    /**
     * 根据条件获取隐私号码池信息
     * @param maskNumPool
     * @return
     */
    @Override
    public MaskNumPool getMaskNumberPoolByObje(MaskNumPool maskNumPool) {
        return this.findObject(Mapper.MaskLine_Mapper.selectMaskNumberPoolByObj, maskNumPool);
    }

    /**
     * 添加隐私号码池信息
     * @param maskNumPool
     */
    @Override
    public void addMaskNumberPool(MaskNumPool maskNumPool) {
        this.save(Mapper.MaskLine_Mapper.insetMaskNumberPool, maskNumPool);
    }

    /**
     * 获取隐私号列表信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper getMaskNumberList(Page page) {
        return this.page(Mapper.MaskLine_Mapper.selectMaskNumberList, page);
    }

    /**
     * 根据ID删除隐私号信息
     * @param id
     */
    @Override
    public void deleteMaskNumberById(String id) {
        this.delete(Mapper.MaskLine_Mapper.deleteMaskNumber, id);
    }


    @Override
    public MaskNum getMaskNumberByNumber(String number) {
        return this.findObject(Mapper.MaskLine_Mapper.selectMaskNumberByNumber, number);
    }

    /**
     * 根据条件获取隐私号信息
     * @param maskNum
     * @return
     */
    @Override
    public MaskNum getMaskNumberByObj(MaskNum maskNum) {
        return this.findObject(Mapper.MaskLine_Mapper.selectMaskNumberByObj, maskNum);
    }

    @Override
    public List<MaskNum> getMaskNumList(MaskNum maskNum) {
        return this.findObjectList(Mapper.MaskLine_Mapper.selectMaskNumberListUnion, maskNum);
    }

    @Override
    public List<MaskNum> getMaskNumListByStatus(String status) {
        return this.findObjectList(Mapper.MaskLine_Mapper.selectMaskNumberByStatus, status);
    }

    /**
     * 添加隐私号信息
     * @param maskNum
     */
    @Override
    public void addMaskNumber(MaskNum maskNum) {
        this.save(Mapper.MaskLine_Mapper.insetMaskNumber, maskNum);
    }

    /**
     * 修改隐私号信息
     * @param maskNum
     */
    @Override
    public void updateMaskNumber(MaskNum maskNum) {
        this.update(Mapper.MaskLine_Mapper.updateMaskNumber, maskNum);
    }

    @Override
    public void updateMaskNumberByAppId(Map map) {
        this.update(Mapper.MaskLine_Mapper.updateMaskNumberByAppId, map);
    }

    /**
     * 分页查询隐私号
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageMaskNumber(Page page) {
        return this.page(Mapper.MaskNum_Mapper.pageMaskNumber, page);
    }

    @Override
    public List<Map<String, Object>> downloadMaskNum(Page page) {
        return this.download(Mapper.MaskNum_Mapper.pageMaskNumber, page);
    }
}
