package com.e9cloud.mongodb.base;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

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
    <T> T findById(String id,Class<T> entityClass);

    /**
     * 根据主键查询一个对象
     * @param query 查询条件
     * @param entityClass 对象的类型
     * @return 对象实例
     */
    <T> T findOne(Query query,Class<T> entityClass);

      /**
     * 通过条件查询实体(集合)
     * @param query 查询条件
     * @param entityClass 对象的类型
     */
    <T> List<T> find(Query query,Class<T> entityClass);

    /**
     * 通过条件查询实体(集合)
     *
     * @param query 查询条件
     * @param entityClass 对象类型
     * @param collection 集合名称
     */
    <T> List<T> find(Query query, Class<T> entityClass, String collection);

    /**
     * 通过条件查询更新数据
     *
     * @param query 查询条件
     * @param update
     * @return
     */
    <T> void update(Query query, Update update,Class<T> entityClass) ;

    /**
     * 保存一个对象到mongodb
     *
     * @param entity
     * @return
     */
    <T> void save(T entity) ;

    /**
     * 求数据总和
     * @param query 查询条件
     * @return
     */
    <T> long count(Query query,Class<T> entityClass);

    /**
     * 求数据总和
     *
     * @param query 查询条件
     * @param collection 集合名称
     * @return
     */
    <T> long count(Query query, String collection);

    /**
     *  分页查询mongodb 数据
     * @param page 分页参数
     * @param entityClass 对象的类型
     * @param collection 集合名称
     * @return
     */
    <T> PageWrapper page(Page page, Class<T> entityClass, String collection);

    /**
     *  查询 导出 mongodb 数据
     * @param page 分页参数
     * @param entityClass 对象的类型
     * @param collection 集合名称
     * @return
     */
    <T> List<T> download(Page page, Class<T> entityClass, String collection);

    /**
     * 根据命令执行
     * @param command
     * @return
     */
    CommandResult executeCommand(String command);

    /**
     * 去除重复值
     * @param key
     * @return
     */
    List distinct(String collection, String key, DBObject query);

}
