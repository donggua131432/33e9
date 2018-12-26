package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayGroup1;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayGroup1Service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 中继群To头域号段
 * Created by Administrator on 2016/9/9.
 */
@Service
public class RelayGroup1ServiceImpl extends BaseServiceImpl implements RelayGroup1Service {

    /**
     * 分页查询 中继群To头域号段
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageGroup1(Page page) {
        return this.page(Mapper.RelayGroup1_Mapper.pageGroup1, page);
    }

    /**
     * 根据ID查找中继群To头域信息
     * @param id
     * @return
     */
    @Override
    public RelayGroup1 findRelayGroup1ById(Integer id) {
        return this.findObject(Mapper.RelayGroup1_Mapper.selectRelayGroup1ById, id);
    }

    /**
     * 根据名称查找中继群To头域信息
     * @param name
     * @return
     */
    @Override
    public RelayGroup1 findRelayGroup1ByName(String name) {
        return this.findObject(Mapper.RelayGroup1_Mapper.selectRelayGroup1ByName, name);
    }

    /**
     * 保存中继群To头域信息
     * @param RelayGroup1 中继群To头域信息
     */
    @Override
    public void saveRelayGroup1(RelayGroup1 RelayGroup1) {
        this.save(Mapper.RelayGroup1_Mapper.saveRelayGroup1, RelayGroup1);
    }

    /**
     * 修改客户信息
     * @param RelayGroup1
     */
    @Override
    public void updateRelayGroup1(RelayGroup1 RelayGroup1) {
        this.update(Mapper.RelayGroup1_Mapper.updateRelayGroup1ById, RelayGroup1);
    }

    /**
     * 删除中继群To头域信息
     * @param id
     */
    @Override
    public void deleteRelayGroup1(Integer id) {
        this.delete(Mapper.RelayGroup1_Mapper.deleteByPrimaryKey, id);
    }

    /**
     * 导出客户信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadRelayGroup1(Page page) {
        return this.download(Mapper.RelayGroup1_Mapper.pageRelayGroup1List, page);
    }
    /**
     * 根据中继群编号查找中继群To头域信息
     * @param tgNum
     * @return
     */
    @Override
    public RelayGroup1 findRelayGroup1BytgNum(String tgNum) {
        return this.findObject(Mapper.RelayGroup1_Mapper.selectRelayGroup1ByName, tgNum);
    }

}
