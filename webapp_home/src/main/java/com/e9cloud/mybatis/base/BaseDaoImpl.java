package com.e9cloud.mybatis.base;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wangzhongjun on 2015/12/11.
 */
@Repository("baseDao")
public class BaseDaoImpl extends SqlSessionDaoSupport implements IBaseDao{

    @Override
    public <E> List<E> findObjectList(String id, Object obj) throws DataAccessException {
        return getSqlSession().selectList(id, obj);
    }

    @Override
    public <E> List<E> findObjectListByPara(String id, String para) throws DataAccessException {
        return getSqlSession().selectList(id, para);
    }

    @Override
    public <E> List<E> findObjectListByMap(String id, Map<String, Object> map) throws DataAccessException {
        return getSqlSession().selectList(id, map);
    }

    @Override
    public <T> T findObject(String id, Object obj) throws DataAccessException {
        return getSqlSession().selectOne(id, obj);
    }

    @Override
    public <T> T findObjectByPara(String id, String para) throws DataAccessException {
        return getSqlSession().selectOne(id, para);
    }

    @Override
    public <T> T findObjectByMap(String id, Map<String, Object> map) throws DataAccessException {
        return getSqlSession().selectOne(id, map);
    }

    @Override
    public void save(String id, Object obj) throws DataAccessException {
        getSqlSession().insert(id, obj);
    }

    @Override
    public void update(String id, Object obj) throws DataAccessException {
        getSqlSession().update(id, obj);
    }

    @Override
    public void delete(String id, Object obj) throws DataAccessException {
        getSqlSession().delete(id, obj);
    }
}
