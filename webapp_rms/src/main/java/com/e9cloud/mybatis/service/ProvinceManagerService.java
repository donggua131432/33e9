package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.TelnoProvince;

import java.util.List;
import java.util.Map;

/**
 * 省份管理
 * Created by wy on 2016/7/8.
 */
public interface ProvinceManagerService extends IBaseService {

    /**
     * 根据ID查找省份信息
     * @param id
     * @return ProvinceManager
     */
    TelnoProvince findProvinceById(String id);
    /**
     * 根据代码查找省份信息
     * @param pcode
     * @return ProvinceManager
     */
    List<TelnoProvince> findProvinceByCode(String pcode);

    /**
     * 保存省份信息
     * @param Province 省份信息
     */
    void saveProvinceManager(TelnoProvince Province);
    /**
     * 修改省份信息
     */
    void updateProvinceManager(TelnoProvince Province);

    /**
     * 删除省份信息
     */
    void deleteProvinceManager(String id);

    /**
     * 分页查询省份信息
     * @param page
     * @return
     */
    PageWrapper pageProvinceList(Page page);

    /**
     * 导出省份信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadProvinceInfo(Page page);
}
