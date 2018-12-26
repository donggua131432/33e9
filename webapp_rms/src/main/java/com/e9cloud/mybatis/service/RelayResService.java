package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayRes;
import com.e9cloud.mybatis.domain.RelayResPer;

import java.util.List;
import java.util.Map;

/**
 * 供应商线路资源相关Service
 * Created by Administrator on 2016/11/23.
 */
public interface RelayResService extends IBaseService {

    /**
     * 分页查询资源信息
     * @param page 分页信息
     * @return 分页列表
     */
    PageWrapper pageRes(Page page);

    /**
     * 资源id
     * @param id
     */
    void delRes(Integer id);

    /**
     * 下载列表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadRes(Page page);

    /**
     * 通过主键（id）得到资源信息(价格详细，中继，城市)
     * @return RelayRes
     */
    RelayRes getResWithPersAndCityById(Integer id);

    /**
     * 保存资源信息，并保存价格详细信息
     * @param relayRes 资源信息
     * @param resPers 价格详细信息
     */
    JSonMessage addResAndPers(RelayRes relayRes, List<RelayResPer> resPers);

    /**
     * 保存资源信息，并保存价格详细信息
     * @param relayRes 资源信息
     * @param resPers 价格详细信息
     */
    JSonMessage updateResAndPers(RelayRes relayRes, List<RelayResPer> resPers);

}
