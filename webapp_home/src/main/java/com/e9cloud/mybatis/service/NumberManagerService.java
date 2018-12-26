package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppNumber;
import com.e9cloud.mybatis.domain.AppNumberRest;

import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/3/29.
 */
public interface NumberManagerService extends IBaseService{
    /** 添加号码 **/
    void addAppNumbers(List<AppNumber> list);

    /** 删除号码 **/
    void deleteAppNumbers(Long[] ids);

    /** 根据AppId清空审核通过的号码 **/
    void clearAppNumberByAppId(String appid);

    /** 根据id修改号码 **/
    void updateAppNumberById(AppNumber appNumber);

    /** 查询号码 **/
    List<AppNumber> findAppNumberList(AppNumber appNumber);

    /** 添加rest号码 **/
    void addAppNumberRest(List<AppNumberRest> list);

    /** 删除rest号码 **/
    void deleteAppNumberRest(Long[] ids);

    /** 根据AppId清空rest号码 **/
    void clearAppNumberRestByAppId(String appid);

    /** 根据APPID及号码码查询rest号码 **/
    List<AppNumberRest> selectAppNumberRestByNumbers(Map map);

    /** 根据对象查询rest号码 **/
    List<AppNumberRest> selectAppNumberRest(AppNumberRest appNumberRest);

    /**
     * 分页查询 号码列表
     * @param page 分页参数
     * @return pageWrapper
     */
    PageWrapper pageNumberManager(Page page);

    /**
     * 统计number的个数
     * @param appNumber
     * @return
     */
    AppNumber countNumByNumber(AppNumber appNumber);

    /** 添加号码 **/
    void addAppNumber(AppNumber appNumber);

    /**
     * 根据appid,number查询号码
     * @param appNumber
     * @return
     */
    AppNumber findNumByAppidAndNumber(AppNumber appNumber);
    /** 单个删除号码 **/
    void deleteAppNumber(Long id);

    /**
     * 根据id查询号码
     * @param id
     * @return
     */
    AppNumber getNumberInfo(Long id);

    /** 重新提交号码 **/
    void reCommitAppNumber(AppNumber appNumber);

    /**
     * 导出报表 号码列表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadAppNumReport(Page page);
}
