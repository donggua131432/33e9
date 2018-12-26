package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayGroup1;
import com.e9cloud.mybatis.domain. RelayGroup1;

import java.util.List;
import java.util.Map;

/**
 * 中继群To头域号段
 * Created by Administrator on 2016/9/9.
 */
public interface RelayGroup1Service extends IBaseService {

    /**
     * 分页查询 中继群To头域号段
     * @param page
     * @return
     */
    PageWrapper pageGroup1(Page page);

    /**
     * 根据ID查找中继群To头域信息
     * @param id
     * @return  RelayGroup1Manager
     */
     RelayGroup1 findRelayGroup1ById(Integer id);

    /**
     * 根据名称查找中继群To头域信息
     * @param name
     * @return  RelayGroup1Manager
     */
     RelayGroup1 findRelayGroup1ByName(String name);

    /**
     * 根据中继编号查找中继群To头域信息
     * @param tgNum
     * @return  RelayGroup1Manager
     */
    RelayGroup1 findRelayGroup1BytgNum(String tgNum);

    /**
     * 保存中继群To头域信息
     * @param  RelayGroup1 中继群To头域信息
     */
    void saveRelayGroup1( RelayGroup1  RelayGroup1);

    /**
     * 修改中继群To头域信息
     */
    void updateRelayGroup1( RelayGroup1  RelayGroup1);

    /**
     * 删除中继群To头域信息
     */
    void deleteRelayGroup1(Integer id);

    /**
     * 导出中继群To头域信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadRelayGroup1(Page page);

}
