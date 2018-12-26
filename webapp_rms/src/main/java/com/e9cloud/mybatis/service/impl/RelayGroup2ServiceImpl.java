package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayGroup2;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayGroup2Service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 中继群运营商被叫前缀
 * Created by Administrator on 2016/9/9.
 */
@Service
public class RelayGroup2ServiceImpl extends BaseServiceImpl implements RelayGroup2Service {

    /**
     * 分页查询 中继群运营商被叫前缀
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageGroup2(Page page) {
        return this.page(Mapper.RelayGroup2_Mapper.pageGroup2, page);
    }




    /*** 根据ID查找中继群To头域信息
     *
    * @param id
    * @return
            */
    @Override
    public RelayGroup2 findRelayGroup2ById(Integer id) {
        return this.findObject(Mapper.RelayGroup2_Mapper.selectRelayGroup2ById, id);
    }
    /**
     * 根据名称查找中继群To头域信息
     * @param name
     * @return
     */
    @Override
    public RelayGroup2 findRelayGroup2ByName(String name) {
        return this.findObject(Mapper.RelayGroup2_Mapper.selectRelayGroup2ByName, name);
    }

    /**
     * 保存中继群To头域信息
     * @param RelayGroup2 中继群To头域信息
     */
    @Override
    public void saveRelayGroup2(RelayGroup2 RelayGroup2) {
        this.save(Mapper.RelayGroup2_Mapper.saveRelayGroup2, RelayGroup2);
    }

    /**
     * 修改客户信息
     * @param RelayGroup2
     */
    @Override
    public void updateRelayGroup2(RelayGroup2 RelayGroup2) {
        this.update(Mapper.RelayGroup2_Mapper.updateRelayGroup2ById, RelayGroup2);
    }

    /**
     * 删除中继群To头域信息
     * @param id
     */
    @Override
    public void deleteRelayGroup2(Integer id) {
        this.delete(Mapper.RelayGroup2_Mapper.deleteByPrimaryKey, id);
    }


    /**
     * 导出客户信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadRelayGroup2(Page page) {
        return this.download(Mapper.RelayGroup2_Mapper.pageGroup2, page);
    }
    /**
     * 根据中继群编号查找中继群To头域信息
     * @param tgNum
     * @return
     */
    @Override
    public RelayGroup2 findRelayGroup2BytgNum(String tgNum) {
        return this.findObject(Mapper.RelayGroup2_Mapper.selectRelayGroup2ByName, tgNum);
    }


}
