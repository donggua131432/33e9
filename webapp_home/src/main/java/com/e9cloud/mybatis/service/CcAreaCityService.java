package com.e9cloud.mybatis.service;

import com.e9cloud.core.common.Tree;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CcArea;
import com.e9cloud.mybatis.domain.CcAreaCity;
import com.e9cloud.mybatis.domain.CcDispatch;

import java.util.List;
import java.util.Map;

/**
 * 智能云调度：区域配置 Service
 * Created by Administrator on 2016/8/11.
 */
public interface CcAreaCityService extends IBaseService {

    /**
     * 分页查询 区域列表
     * @param page 分页参数
     * @return pageWrapper
     */
    PageWrapper pageCcArea(Page page);

    /**
     * 下载区域列表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadCcArea(Page page);

    /**
     * 省份 - 城市 树
     * @return
     */
    List<Tree> pctree(CcAreaCity areaCity);

    /**
     * 某个区域 的 城市 树
     * @param areaId 区域Id
     * @return
     */
    List<Tree> atree(String areaId);

    /**
     * 增加区域
     * @param ccArea 区域
     * @param codes 城市编号
     */
    List<Tree> addArea(CcArea ccArea, String[] codes);

    /**
     * 编辑区域
     * @param ccArea 区域
     * @param codes 城市编号
     */
    List<Tree> editArea(CcArea ccArea, String[] codes);

    /**
     * 根据areaId 查询 区域 信息
     * @param areaId 区域id
     * @return CcArea
     */
    CcArea getAreaByAreaId(String areaId);

    /**
     * 统计sid下某个名称的的个数
     * @param ccArea
     * @return
     */
    long countAreaBySidAndAreaId(CcArea ccArea);
    /**
     * areaCity 是否已经存在
     * @param areaCity
     * @return
     */
     boolean  exited (CcAreaCity areaCity);
    /**
     * 查询某个账户下面已经选择的 城市code
     * @param areaCity
     * @return
     */
    List<String> getSelectedNodes(CcAreaCity areaCity);

    /**
     * 根据城市编号查找区域城市关系
     * @param cityCode
     * @return
     */
    CcAreaCity findAreaCityByCityCode(String cityCode);

    /**
     * 统计areaid下的话务调度
     * @param areaId
     * @return
     */
    List<CcDispatch> getDispatchByAreaId(String areaId);

    /**
     * 删除区域
     * @param areaId
     */
    void deleteCcArea(String areaId);

}
