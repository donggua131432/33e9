package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppNumber;
import com.e9cloud.mybatis.domain.AppNumberRest;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.NumberManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/3/29.
 */
@Service
public class NumberManagerServiceImpl extends BaseServiceImpl implements NumberManagerService {
    /** 添加号码 **/
    @Override
    public void addAppNumbers(List<AppNumber> list) {
        this.save(Mapper.AppNumber_Mapper.insertAppNumbers,list);
    }
    /** 删除号码 **/
    @Override
    public void deleteAppNumbers(Long[] ids) {
        this.update(Mapper.AppNumber_Mapper.deleteAppNumbers,ids);
    }
    /** 清空号码 **/
    @Override
    public void clearAppNumberByAppId(String appid) {
        this.update(Mapper.AppNumber_Mapper.clearAppNumberByAppId,appid);
    }
    /** 根据id修改号码 **/
    @Override
    public void updateAppNumberById(AppNumber appNumber) {
        this.update(Mapper.AppNumber_Mapper.updateAppNumberById, appNumber);
    }
    /** 查询号码 **/
    @Override
    public List<AppNumber> findAppNumberList(AppNumber appNumber) {
        return this.findObjectList(Mapper.AppNumber_Mapper.findAppNumberList, appNumber);
    }

    /** 添加rest号码 **/
    @Override
    public void addAppNumberRest(List<AppNumberRest> list) {
        this.save(Mapper.AppNumberRest_Mapper.insertAppNumberRest,list);
    }
    /** 删除rest号码 **/
    @Override
    public void deleteAppNumberRest(Long[] ids) {
        this.delete(Mapper.AppNumberRest_Mapper.deleteNumberRestByNumberIds,ids);
    }
    /** 根据AppId清空rest号码 **/
    @Override
    public void clearAppNumberRestByAppId(String appid) {
        this.delete(Mapper.AppNumberRest_Mapper.clearAppNumberRestByAppId,appid);
    }
    /** 根据APPID及号码码查询rest号码 **/
    @Override
    public List<AppNumberRest> selectAppNumberRestByNumbers(Map map) {
        return this.findObjectListByMap(Mapper.AppNumberRest_Mapper.selectAppNumberRestByNumbers, map);
    }

    /** 根据对象查询rest号码 **/
    @Override
    public List<AppNumberRest> selectAppNumberRest(AppNumberRest appNumberRest) {
        return this.findObjectList(Mapper.AppNumberRest_Mapper.selectAppNumberRest, appNumberRest);
    }

    /**
     * 分页查询 号码列表列表
     * @param page 分页参数
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageNumberManager(Page page) {
        return this.page(Mapper.AppNumber_Mapper.pageNumber, page);
    }

    /**
     * 统计number的个数
     *
     * @param appNumber
     * @return
     */
    @Override
    public AppNumber countNumByNumber(AppNumber appNumber) {
        return this.findObject(Mapper.AppNumber_Mapper.countNumByNumber, appNumber);
    }

    /**
     * 添加SIP全局号码池
     * @param appNumber
     */
    @Override
    public void addAppNumber(AppNumber appNumber){
        this.save(Mapper.AppNumber_Mapper.insertAppNumber, appNumber);
    }

    @Override
    public AppNumber findNumByAppidAndNumber(AppNumber appNumber) {
        return this.findObject(Mapper.AppNumber_Mapper.findNumByAppidAndNumber, appNumber);
    }

    /** 单个删除号码 **/
    @Override
    public void deleteAppNumber(Long id) {
        this.update(Mapper.AppNumber_Mapper.deleteAppNumber,id);
        this.delete(Mapper.AppNumberRest_Mapper.deleteAppNumberRest,id);
    }

    /** 根据id查询号码 **/
    public AppNumber getNumberInfo(Long id){
        return this.findObject(Mapper.AppNumber_Mapper.getNumberInfo,id);
    }

    /** 单个删除号码 **/
    @Override
    public void reCommitAppNumber(AppNumber appNumber) {
        this.update(Mapper.AppNumber_Mapper.reCommitAppNumber,appNumber);
    }
    /**
     * 导出报表 号码列表
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadAppNumReport(Page page) {
        return this.download(Mapper.AppNumber_Mapper.downloadAppNumReport, page);
    }
}
