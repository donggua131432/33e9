package com.e9cloud.mongodb.base;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * mongodb 的基类
 *
 * Created by Administrator on 2015/12/17.
 */
public interface MongoDBDao  {

    /**
     * 根据主键查询一个对象
     * @param id 主键
     * @param entityClass 对象的类型
     * @return 对象实例
     */
    <T> T findById(String id, Class<T> entityClass);

    /**
     * 根据主键查询一个对象
     * @param query 查询条件
     * @param entityClass 对象的类型
     * @return 对象实例
     */
    <T> T findOne(Query query, Class<T> entityClass);

    /**
     * 根据主键查询一个对象
     * @param query 查询条件
     * @param entityClass 对象的类型
     * @return 对象实例
     */
    <T> T findOne(Query query, Class<T> entityClass ,String collection);

      /**
     * 通过条件查询实体(集合)
     * @param query 查询条件
     * @param entityClass 对象的类型
     */
    <T> List<T> find(Query query, Class<T> entityClass) ;

    /**
     * 通过条件查询实体(集合)
     * @param query 查询条件
     * @param entityClass 对象的类型
     */
    <T> List<T> find(Query query, Class<T> entityClass, String collection) ;

    /**
     * 通过条件查询实体(集合)的所有元素
     *
     * @param entityClass 对象的类型
     */
    <T> List<T> findAll(Class<T> entityClass) ;

    /**
     * 通过条件查询更新数据
     *
     * @param query
     * @param update
     * @return
     */
    <T> void update(Query query, Update update, Class<T> entityClass) ;

    /**
     * 保存一个对象到mongodb
     *
     * @param entity
     * @return
     */
    <T> void save(T entity) ;

    /**
     * 求数据总和
     * @param query
     * @return
     */
    public <T> long count(Query query, Class<T> entityClass);
    public <T> long count(Query query, Class<T> entityClass, String collection);

    public <T> String sum(String sumName, Query query, String collection, String groupName);


    /**
     * 保存一个指定对象到mongodb
     *
     * @param entity
     * @return
     */
    <T> void saveTable(T entity,String collectionName) ;
}
