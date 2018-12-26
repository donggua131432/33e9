package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayGroup2;

import java.util.List;
import java.util.Map;

/**
 * 中继群运营商被叫前缀
 * Created by Administrator on 2016/9/9.
 */
public interface RelayGroup2Service extends IBaseService {

    /**
     * 分页查询 中继群运营商被叫前缀
     * @param page
     * @return
     */
    PageWrapper pageGroup2(Page page);






    /**
     * 根据ID查找中继群To头域信息
     * @param id
     * @return  RelayGroup2Manager
     */
    RelayGroup2 findRelayGroup2ById(Integer id);
    /**
     * 根据名称查找中继群To头域信息
     * @param name
     * @return  RelayGroup2Manager
     */
    RelayGroup2 findRelayGroup2ByName(String name);

    /**
     * 根据中继编号查找中继群To头域信息
     * @param tgNum
     * @return  RelayGroup2Manager
     */
    RelayGroup2 findRelayGroup2BytgNum(String tgNum);

    /**
     * 保存中继群To头域信息
     * @param  RelayGroup2 中继群To头域信息
     */
    void saveRelayGroup2( RelayGroup2  RelayGroup2);
    /**
     * 修改中继群To头域信息
     */
    void updateRelayGroup2( RelayGroup2  RelayGroup2);

    /**
     * 删除中继群To头域信息
     */
    void deleteRelayGroup2(Integer id);

    /**
     * 导出中继群To头域信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadRelayGroup2(Page page);
}
