package com.e9cloud.mybatis.base;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Created by wangzhongjun on 2015/12/10.
 */
public interface IBaseDao {

    <T> T findObject(String id, Object obj) throws DataAccessException;

    <T> T findObjectByPara(String id, String para) throws DataAccessException;

    <T> T findObjectByMap(String id, Map<String, Object> map) throws DataAccessException;

    <E> List<E> findObjectList(String id, Object obj) throws DataAccessException;

    <E> List<E> findObjectListByPara(String id, String para) throws DataAccessException;

    <E> List<E> findObjectListByMap(String id, Map<String, Object> map) throws DataAccessException;

    void save(String id, Object obj) throws DataAccessException;

    void update(String id, Object obj) throws DataAccessException;

    void delete(String id, Object obj) throws DataAccessException;

     <E> List<E> findList(String id) throws DataAccessException;
}
