package com.e9cloud.mongodb.base;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/17.
 */
@Repository("mongoDBDao")
public class MongoDBDaoImpl implements MongoDBDao {

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
        return mongoTemplate.findById(id,entityClass);
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
        return mongoTemplate.findOne(query,entityClass);
    }

    /**
     * 根据主键查询一个对象
     *
     * @param query       查询条件
     * @param entityClass 对象的类型
     * @param collection
     * @return 对象实例
     */
    @Override
    public <T> T findOne(Query query, Class<T> entityClass, String collection) {
        return mongoTemplate.findOne(query,entityClass,collection);
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
     * @param query
     */
    @Override
    public <T> List<T> find(Query query, Class<T> entityClass, String collection) {
        return this.mongoTemplate.find(query, entityClass, collection);
    }

    /**
     * 通过条件查询实体(集合)的所有元素
     *
     * @param entityClass 对象的类型
     */
    public <T> List<T> findAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    /**
     * 通过条件查询更新数据
     *
     * @param query
     * @param update
     * @param entityClass 对象的类型
     */
    @Override
    public <T>  void update(Query query, Update update,Class<T> entityClass) {
        this.mongoTemplate.updateFirst(query,update,entityClass);
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

    @Override
    public <T> long count(Query query,Class<T> entityClass, String collection) {
        return this.mongoTemplate.count(query,entityClass,collection);
    }


    /**
     * 2016-06-22 add by DK
     * @param sumName 需要求和的字段
     * @param query 查询条件
     * @param collection 查询的表
     * @param groupName 分组的字段
     * @param <T>
     * @return
     */
    @Override
    public <T> String sum(String sumName, Query query, String collection, String groupName) {
        double total = 0;
        String reduce = "function(doc, agr){" +
                "            agr.total += doc."+sumName+";" +
                "        }";

        DBObject result = mongoTemplate.getCollection(collection).group(new BasicDBObject(groupName, 1),
                query.getQueryObject(),
                new BasicDBObject("total", total),
                reduce);

        Map<String,BasicDBObject> map = result.toMap();
        if(map.size() > 0){
            BasicDBObject bDbo = map.get("0");
            if(bDbo != null && bDbo.get("total") != null) {
                total = bDbo.getDouble("total");
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(total);
    }
    /**
     * 保存一个指定对象到mongodb
     *
     * @param entity
     * @return
     */
    @Override
    public <T> void saveTable(T entity,String collectionName) {
        this.mongoTemplate.save(entity,collectionName);
    }
}
