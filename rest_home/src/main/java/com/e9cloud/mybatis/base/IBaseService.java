package com.e9cloud.mybatis.base;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * 为service提供基本的方法
 */
public interface IBaseService {

    /**
     *根据参数查询一个对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 查询参数
     * @param <T> 泛型
     * @return T
     * @throws DataAccessException
     */
    <T> T findObject(String id, Object obj) throws DataAccessException;

    /**
     *根据参数查询一个对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param para 查询参数
     * @param <T> 泛型
     * @return T
     * @throws DataAccessException
     */
    <T> T findObjectByPara(String id, String para) throws DataAccessException;

    /**
     *根据参数查询一个对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param map 查询参数
     * @param <T> 泛型
     * @return T
     * @throws DataAccessException
     */
    <T> T findObjectByMap(String id, Map<String, Object> map) throws DataAccessException;

    /**
     * 根据参数(object)查询List
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 查询参数
     * @param <E> 泛型
     * @return List<E>
     * @throws DataAccessException
     */
    <E> List<E> findObjectList(String id, Object obj) throws DataAccessException;

    /**
     * 根据参数(para)查询List
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param para 查询参数
     * @param <E> 泛型
     * @return List<E>
     * @throws DataAccessException
     */
    <E> List<E> findObjectListByPara(String id, String para) throws DataAccessException;
    /**
     * 查询List
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param <E> 泛型
     * @return List<E>
     * @throws DataAccessException
     */
    <E> List<E> findList(String id) throws DataAccessException;
    /**
     * 根据参数(map)查询List
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param map 查询参数
     * @param <E> 泛型
     * @return List<E>
     * @throws DataAccessException
     */
    <E> List<E> findObjectListByMap(String id, Map<String, Object> map) throws DataAccessException;

    /**
     * 持久化数据对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 数据对象
     * @throws DataAccessException
     */
    void save(String id, Object obj) throws DataAccessException;

    /**
     * 修改数据对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 数据对象
     * @throws DataAccessException
     */
    void update(String id, Object obj) throws DataAccessException;

    /**
     * 删除数据对象
     *
     * @param id sqlsessionId 如："mapperId.methodId"
     * @param obj 数据参数
     * @throws DataAccessException
     */
    void delete(String id, Object obj) throws DataAccessException;

}
