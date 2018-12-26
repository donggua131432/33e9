package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AxbPhone;
import com.e9cloud.mybatis.domain.TelnoCity;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2017/4/18.
 */
public interface AxbNumResPoolService extends IBaseService {
    /**
     * 获取城市下拉列表
     */

    List<TelnoCity> getCitys(Map<String,Object> params);

    /**
     * 获取公共号码池列表
     */
    PageWrapper getAxbNumList(Page page);

    //公共号码管理池导出
    List<Map<String, Object>> downloadAxbNumPool(Page page);

    /**
     * 获取号码池信息列表
     * @param axbPhone
     * @return
     */
    List<AxbPhone> getAxbNumResPoolByAxbPhone(AxbPhone axbPhone);

    /**
     * 单个添加公共号码管理池
     * @param axbPhone
     */
    void addAxbPhone(AxbPhone axbPhone);

    AxbPhone getAxbPhoneById(String id);

    void deleteAxbPhoneByIds(String[] strId);

    //根据id变更公共号码池号码状态
    void updateAxbPhoneByStatus(AxbPhone axbPhone);

    //校验号码+区号是否匹配
    boolean checkAxbNumberMatchAreacode(AxbPhone axbPhone);

}
