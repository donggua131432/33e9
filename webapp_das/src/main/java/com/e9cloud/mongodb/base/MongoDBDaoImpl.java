package com.e9cloud.mongodb.base;

import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
@Repository
public class MongoDBDaoImpl extends BaseLogger implements MongoDBDao {

    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;


    /**
     * 根据主键查询一个对象
     *
     * @param id 主键（唯一id）
     * @param entityClass 对象的类型
     * @return T 对象实例
     */
    @Override
    public <T> T findById(String id, Class<T> entityClass) {
        return mongoTemplate.findById(id, entityClass);
    }

    /**
     * 根据主键查询一个对象
     *
     * @param query       查询条件
     * @param entityClass 对象的类型
     * @return 对象实例
     */
    @Override
    public <T> T findOne(Query query, Class<T> entityClass) {
        return mongoTemplate.findById(query, entityClass);
    }

    /**
     * 通过条件查询实体(集合)
     *
     * @param query
     */
    @Override
    public <T> List<T> find(Query query,Class<T> entityClass) {
        return this.mongoTemplate.find(query, entityClass);
    }

    /**
     * 通过条件查询实体(集合)
     *
     * @param query 查询条件
     * @param entityClass 对象类型
     * @param collection 集合名称
     */
    @Override
    public <T> List<T> find(Query query, Class<T> entityClass, String collection) {
        return this.mongoTemplate.find(query, entityClass, collection);
    }

    /**
     * 通过条件查询更新数据
     *
     * @param query
     * @param update
     * @param entityClass 对象的类型
     */
    @Override
    public <T> void update(Query query, Update update, Class<T> entityClass) {
        this.mongoTemplate.updateFirst(query, update, entityClass);
    }

    /**
     * 保存一个对象到mongodb
     *
     * @param entity
     */
    @Override
    public <T> void save(T entity) {
        this.mongoTemplate.save(entity);
    }

    /**
     * 求数据总和
     *
     * @param query
     * @param entityClass 对象的类型
     * @return
     */
    @Override
    public <T> long count(Query query,Class<T> entityClass) {
        return this.mongoTemplate.count(query,entityClass);
    }

    /**
     * 求数据总和
     *
     * @param query
     * @param collection 集合名称
     * @return
     */
    @Override
    public <T> long count(Query query, String collection) {
        return this.mongoTemplate.count(query, collection);
    }

    /**
     * 分页查询mongodb 数据
     *
     * @param page        分页参数
     * @param entityClass 对象的类型
     * @return
     */
    @Override
    public <T> PageWrapper page(Page page, Class<T> entityClass, String collection) {

        //获取通话记录总数量
        Query queryList = new Query();

        // 添加查询
        page.getConds().forEach((cond) -> {
            queryList.addCriteria(cond.toCriteria());
        });

        long total = (count(queryList, collection));
        page.setTotal(total);

        //查询显示记录条数
        queryList.limit(page.getPageSize()).skip(page.getStart());

        //排序
        if (Tools.isNotNullStr(page.getSidx())) {
            Sort.Direction sord = "desc".equals(page.getSord()) ? Sort.Direction.DESC : Sort.Direction.ASC;
            queryList.with(new Sort(new Sort.Order(sord, page.getSidx())));
        }

        logger.info(queryList.toString());

        return new PageWrapper(0, page.getPage(), page.getPageSize(), total, find(queryList, entityClass, collection));
    }

    /**
     * 查询 导出 mongodb 数据
     *
     * @param page        分页参数
     * @param entityClass 对象的类型
     * @param collection  集合名称
     * @return
     */
    @Override
    public <T> List<T> download(Page page, Class<T> entityClass, String collection) {
        //获取通话记录总数量
        Query queryList = new Query();

        // 添加查询
        page.getConds().forEach((cond) -> {
            queryList.addCriteria(cond.toCriteria());
        });

        logger.info(queryList.getQueryObject().toString());

        //排序
        if (Tools.isNotNullStr(page.getSidx())) {
            Sort.Direction sord = "desc".equals(page.getSord()) ? Sort.Direction.DESC : Sort.Direction.ASC;
            queryList.with(new Sort(new Sort.Order(sord, page.getSidx())));
        }

        return find(queryList, entityClass, collection);
    }

    /**
     * 根据命令执行
     *
     * @param command
     * @return
     */
    @Override
    public CommandResult executeCommand(String command) {
        return mongoTemplate.executeCommand(command);
    }

    /**
     * 去除重复值
     *
     * @param collection
     * @param key
     * @param query
     * @return
     */
    @Override
    public List distinct(String collection, String key, DBObject query) {
        return mongoTemplate.getCollection(collection).distinct(key, query);
    }

}
