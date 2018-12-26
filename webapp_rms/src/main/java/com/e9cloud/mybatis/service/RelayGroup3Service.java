package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayGroup3;

import java.util.List;
import java.util.Map;

/**
 * 中继群主叫强显号段表
 * Created by Administrator on 2016/9/9.
 */
public interface RelayGroup3Service extends IBaseService {

    /**
     * 分页查询 中继群主叫强显号段表
     * @param page
     * @return
     */
    PageWrapper pageGroup3(Page page);


    /**
     * 根据ID查找中继群To头域信息
     * @param id
     * @return  RelayGroup3Manager
     */
    RelayGroup3 findRelayGroup3ById(Integer id);
    /**
     * 根据名称查找中继群To头域信息
     * @param name
     * @return  RelayGroup3Manager
     */
    RelayGroup3 findRelayGroup3ByName(String name);

    /**
     * 根据中继编号查找中继群To头域信息
     * @param tgNum
     * @return  RelayGroup3Manager
     */
    RelayGroup3 findRelayGroup3BytgNum(String tgNum);

    /**
     * 保存中继群To头域信息
     * @param  RelayGroup3 中继群To头域信息
     */
    void saveRelayGroup3( RelayGroup3  RelayGroup3);
    /**
     * 修改中继群To头域信息
     */
    void updateRelayGroup3( RelayGroup3  RelayGroup3);

    /**
     * 删除中继群To头域信息
     */
    void deleteRelayGroup3(Integer id);

    /**
     * 导出中继群To头域信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadRelayGroup3(Page page);
}
