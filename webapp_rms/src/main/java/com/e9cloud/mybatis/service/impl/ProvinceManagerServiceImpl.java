package com.e9cloud.mybatis.service.impl;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.TelnoProvince;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.ProvinceManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/7/6.
 */
@Service
public class ProvinceManagerServiceImpl extends BaseServiceImpl implements ProvinceManagerService {
    /**
     * 根据ID查找省份信息
     * @param id
     * @return
     *
     */
    @Override
    public TelnoProvince findProvinceById(String id) {
        return this.findObject(Mapper.ProvinceManager_Mapper.selectProvinceManagerById, id);
    }


    /**
     * 根据名称查找省份字典信息
     * @param pcode
     * @return
     */
    @Override
    public List<TelnoProvince> findProvinceByCode(String pcode) {
        return this.findObjectList(Mapper.ProvinceManager_Mapper.selectProvinceManagerByCode, pcode);
    }


    /**
     * 保存省份信息
     * @param Province 省份信息
     */
    @Override
    public void saveProvinceManager(TelnoProvince Province) {
        this.save(Mapper.ProvinceManager_Mapper.saveProvinceManager, Province);
    }

    /**
     * 修改客户信息
     * @param Province
     */
    @Override
    public void updateProvinceManager(TelnoProvince Province) {
        this.update(Mapper.ProvinceManager_Mapper.updateProvinceManager, Province);
    }

    /**
     * 删除省份信息
     * @param id
     */
    @Override
    public void deleteProvinceManager(String id) {
        this.delete(Mapper.ProvinceManager_Mapper.deleteProvinceManager, id);
    }

    /**
     * 分页查询省份信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageProvinceList(Page page) {
        return this.page(Mapper.ProvinceManager_Mapper.pageProvinceManagerList, page);
    }

    /**
     * 导出客户信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadProvinceInfo(Page page) {
        return this.download(Mapper.ProvinceManager_Mapper.pageProvinceManagerList, page);
    }
}
