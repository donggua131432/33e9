package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayGroup3;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayGroup3Service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 中继群主叫强显号段表
 * Created by Administrator on 2016/9/9.
 */
@Service
public class RelayGroup3ServiceImpl extends BaseServiceImpl implements RelayGroup3Service {

    /**
     * 分页查询 中继群主叫强显号段表
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageGroup3(Page page) {
        return this.page(Mapper.RelayGroup3_Mapper.pageGroup3, page);
    }



    /*** 根据ID查找中继群To头域信息
     *
     * @param id
     * @return
     */
    @Override
    public RelayGroup3 findRelayGroup3ById(Integer id) {
        return this.findObject(Mapper.RelayGroup3_Mapper.selectRelayGroup3ById, id);
    }
    /**
     * 根据名称查找中继群To头域信息
     * @param name
     * @return
     */
    @Override
    public RelayGroup3 findRelayGroup3ByName(String name) {
        return this.findObject(Mapper.RelayGroup3_Mapper.selectRelayGroup3ByName, name);
    }

    /**
     * 保存中继群To头域信息
     * @param RelayGroup3 中继群To头域信息
     */
    @Override
    public void saveRelayGroup3(RelayGroup3 RelayGroup3) {
        this.save(Mapper.RelayGroup3_Mapper.saveRelayGroup3, RelayGroup3);
    }

    /**
     * 修改客户信息
     * @param RelayGroup3
     */
    @Override
    public void updateRelayGroup3(RelayGroup3 RelayGroup3) {
        this.update(Mapper.RelayGroup3_Mapper.updateRelayGroup3ById, RelayGroup3);
    }

    /**
     * 删除中继群To头域信息
     * @param id
     */
    @Override
    public void deleteRelayGroup3(Integer id) {
        this.delete(Mapper.RelayGroup3_Mapper.deleteByPrimaryKey, id);
    }


    /**
     * 导出客户信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadRelayGroup3(Page page) {
        return this.download(Mapper.RelayGroup3_Mapper.pageGroup3, page);
    }
    /**
     * 根据中继群编号查找中继群To头域信息
     * @param tgNum
     * @return
     */
    @Override
    public RelayGroup3 findRelayGroup3BytgNum(String tgNum) {
        return this.findObject(Mapper.RelayGroup3_Mapper.selectRelayGroup3ByName, tgNum);
    }


}
