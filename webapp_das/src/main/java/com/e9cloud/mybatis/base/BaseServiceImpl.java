package com.e9cloud.mybatis.base;

import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangzhongjun on 2015/12/10.
 */
@Service("baseService")
public class BaseServiceImpl extends BaseLogger implements IBaseService{

    @Autowired
    @Qualifier("baseDao")
    private IBaseDao baseDao;

    /**
     *根据参数查询一个对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 查询参数
     * @param <T> 泛型
     * @return T
     * @throws DataAccessException
     */
    @Override
    public <T> T findObject(String id, Object obj) throws DataAccessException {
        return baseDao.findObject(id, obj);
    }

    /**
     *根据参数查询一个对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param para 查询参数
     * @param <T> 泛型
     * @return T
     * @throws DataAccessException
     */
    @Override
    public <T> T findObjectByPara(String id, String para) throws DataAccessException {
        return baseDao.findObjectByPara(id, para);
    }

    /**
     *根据参数查询一个对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param map 查询参数
     * @param <T> 泛型
     * @return T
     * @throws DataAccessException
     */
    @Override
    public <T> T findObjectByMap(String id, Map<String, Object> map) throws DataAccessException {
        return baseDao.findObjectByMap(id, map);
    }

    /**
     * 根据参数(object)查询List
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 查询参数
     * @param <E> 泛型
     * @return List<E>
     * @throws DataAccessException
     */
    @Override
    public <E> List<E> findObjectList(String id, Object obj) throws DataAccessException {
        return baseDao.findObjectList(id, obj);
    }

    /**
     * 根据参数(para)查询List
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param para 查询参数
     * @param <E> 泛型
     * @return List<E>
     * @throws DataAccessException
     */
    @Override
    public <E> List<E> findObjectListByPara(String id, String para) throws DataAccessException {
        return baseDao.findObjectListByPara(id, para);
    }

    /**
     * 根据参数(map)查询List
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param map 查询参数
     * @param <E> 泛型
     * @return List<E>
     * @throws DataAccessException
     */
    @Override
    public <E> List<E> findObjectListByMap(String id, Map<String, Object> map) throws DataAccessException {
        return baseDao.findObjectListByMap(id, map);
    }

    /**
     * 持久化数据对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 数据对象
     * @throws DataAccessException
     */
    @Override
    public void save(String id, Object obj) throws DataAccessException {
        baseDao.save(id, obj);
    }

    /**
     * 修改数据对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 数据对象
     * @throws DataAccessException
     */
    @Override
    public void update(String id, Object obj) throws DataAccessException {
        baseDao.update(id, obj);
    }

    /**
     * 删除数据对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 数据参数
     * @throws DataAccessException
     */
    @Override
    public void delete(String id, Object obj) throws DataAccessException {
        baseDao.delete(id, obj);
    }

    /**
     * 分页查询
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param page 分页参数
     * @return pageWrapper
     * @throws DataAccessException
     */
    @Override
    public PageWrapper page(String id, Page page) throws DataAccessException {
        Map<String, Long> map = baseDao.findObject(id, page);
        page.setTotal(map.get("counts"));
        page.setDoAount(false);
        return new PageWrapper(page.getDraw(), page.getPage(), page.getPageSize(), page.getTotal(), baseDao.findObjectList(id, page));
    }

    /**
     * 分页查询
     *
     * @param countid 统计数量 sqlsessionId 如："mapperId.methodId"
     * @param listid 数量查询 sqlsessionId 如："mapperId.methodId"
     * @param page 分页参数
     * @return pageWrapper
     * @throws DataAccessException
     */
    @Override
    public PageWrapper page(String countid, String listid, Page page) throws DataAccessException{

        long total = baseDao.findObject(countid, page);
        page.setTotal(total);
        page.setDoAount(false);
        return new PageWrapper(page.getDraw(), page.getPage(), page.getPageSize(), page.getTotal(), baseDao.findObjectList(listid, page));
    }

    /**
     * 导出报表
     *
     * @param listid 数量查询 sqlsessionId 如："mapperId.methodId"
     * @param page 分页参数
     * @return pageWrapper
     * @throws DataAccessException
     */
    @Override
    public <E> List<E> download(String listid, Page page) throws DataAccessException{
        page.setDoDownload(true);
        return baseDao.findObjectList(listid, page);
    }
}
