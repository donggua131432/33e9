package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.common.Tree;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CcArea;
import com.e9cloud.mybatis.domain.CcAreaCity;
import com.e9cloud.mybatis.domain.CcDispatch;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CcAreaCityService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智能云调度：区域配置 ServiceImpl
 * Created by Administrator on 2016/8/11.
 */
@Service
public class CcAreaCityServiceImpl extends BaseServiceImpl implements CcAreaCityService {

    /**
     * 分页查询 区域列表
     * @param page 分页参数
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageCcArea(Page page) {
        return this.page(Mapper.CcArea_Mapper.pageCcArea, page);
    }

    /**
     * 下载区域列表
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadCcArea(Page page) {
        return this.download(Mapper.CcArea_Mapper.pageCcArea, page);
    }

    /**
     * 省份 - 城市 树
     *
     * @return
     */
    @Override
    public List<Tree> pctree(CcAreaCity areaCity) {
        return this.findObjectList(Mapper.CcAreaCity_Mapper.pctree, areaCity);
    }

    /**
     * 增加区域
     *
     * @param ccArea 区域
     * @param codes  城市编号
     */
    @Override
    public List<Tree> addArea(CcArea ccArea, String[] codes) {
        List<Tree> citys = filteAreaCity(ccArea, codes);

        if (citys == null || citys.size() == 0) {
            String eareId = ID.randomUUID();
            ccArea.setAreaId(eareId);
            ccArea.setStatus("00");

            this.save(Mapper.CcArea_Mapper.insertSelective ,ccArea);
            CcAreaCity areaCity;
            for (String code : codes) {
                areaCity = new CcAreaCity();
                areaCity.setAreaId(eareId);
                areaCity.setCityCode(code);
                areaCity.setSid(ccArea.getSid());

                this.save(Mapper.CcAreaCity_Mapper.insertSelective, areaCity);
            }
            return null;
        }

        return citys;
    }

    /**
     * 修改区域
     *
     * @param ccArea 区域
     * @param codes  城市编号
     */
    @Override
    public List<Tree> editArea(CcArea ccArea, String[] codes) {
        List<Tree> citys = filteAreaCity(ccArea, codes);

        if (citys == null || citys.size() == 0) {
            this.update(Mapper.CcArea_Mapper.updateByAreaId ,ccArea);

            // 先清除 区域 内的城市
            this.delete(Mapper.CcAreaCity_Mapper.deleteByAreaId, ccArea.getAreaId());

            CcAreaCity areaCity;
            for (String code : codes) {
                areaCity = new CcAreaCity();
                areaCity.setAreaId(ccArea.getAreaId());
                areaCity.setCityCode(code);
                areaCity.setSid(ccArea.getSid());

                this.save(Mapper.CcAreaCity_Mapper.insertSelective, areaCity);
            }
            return null;
        }

        return citys;
    }

    // areaCity 是否已经存在
    public boolean exited (CcAreaCity areaCity) {
        long l = this.findObject(Mapper.CcAreaCity_Mapper.countByAreaCity, areaCity);
        return l > 0;
    }

    /**
     * 过滤城市信息 返回重复的城市信息
     * @return
     */
    private List<Tree> filteAreaCity(CcArea ccArea, String[] codes){
        Map<String, Object> params = new HashMap<>();
        params.put("areaId", ccArea.getAreaId());
        params.put("sid", ccArea.getSid());
        params.put("codes", codes);
        return this.findObjectListByMap(Mapper.CcAreaCity_Mapper.filteAreaCity, params);
    }

    /**
     * 根据areaId 查询 区域 信息
     *
     * @param areaId 区域id
     * @return CcArea
     */
    @Override
    public CcArea getAreaByAreaId(String areaId) {
        return this.findObjectByPara(Mapper.CcArea_Mapper.selectByAreaId, areaId);
    }

    /**
     * 某个区域 的 城市 树
     *
     * @param areaId 区域Id
     * @return
     */
    @Override
    public List<Tree> atree(String areaId) {
        return this.findObjectListByPara(Mapper.CcAreaCity_Mapper.atree, areaId);
    }

    /**
     * 统计sid下某个名称的的个数
     *
     * @param ccArea
     * @return
     */
    @Override
    public long countAreaBySidAndAreaId(CcArea ccArea) {
        return this.findObject(Mapper.CcArea_Mapper.countAreaBySidAndAreaId, ccArea);
    }

    /**
     * 查询某个账户下面已经选择的 城市code
     *
     * @param areaCity
     * @return
     */
    @Override
    public List<String> getSelectedNodes(CcAreaCity areaCity) {
        return this.findObjectList(Mapper.CcAreaCity_Mapper.selectSelectedNodes, areaCity);
    }

    @Override
    public CcAreaCity findAreaCityByCityCode(String cityCode) {
        return this.findObject(Mapper.CcAreaCity_Mapper.selectByCityCode, cityCode);
    }

    /**
     * 统计areaid下的话务调度
     *
     * @return list
     */
    @Override
    public List<CcDispatch> getDispatchByAreaId(String areaId) {
        return this.findObjectListByPara(Mapper.CcAreaCity_Mapper.getDispatchByAreaId, areaId);
    }

    /**
     * 删除区域
     * @param areaId
     */
    @Override
    public void deleteCcArea(String areaId) {
        this.update(Mapper.CcArea_Mapper.deleteCcArea, areaId);
        this.delete(Mapper.CcAreaCity_Mapper.deleteByAreaId, areaId);
    }
}
