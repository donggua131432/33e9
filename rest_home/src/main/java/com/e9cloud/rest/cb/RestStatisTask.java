package com.e9cloud.rest.cb;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.RestReqResInfo;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.Tools;
import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/23.
 */
public class RestStatisTask  implements Runnable {
    private RestReqResInfo restReqResInfo;

    MongoDBDao mongoDBDao = SpringContextHolder.getBean(MongoDBDao.class);
    MongoTemplate mongoTemplate = SpringContextHolder.getBean(MongoTemplate.class);

    public RestStatisTask(RestReqResInfo restReqResInfo) {
        this.restReqResInfo = restReqResInfo;
    }

    @Override
    public void run() {
        // 解析RAS报文体
        restReqResInfo.setDateCreated(new Date());
        Date dateTime = restReqResInfo.getDateCreated();
        //获取表名
        String collection = Tools.genMogonDbTableName(ConstantsEnum.RESTREQ_NAME.getStrValue(),dateTime);
        //判断改表是否存在 不存在就创建并建立索引
        if(!mongoTemplate.collectionExists(collection)){
            mongoTemplate.createCollection(collection);
            mongoTemplate.getCollection(collection).createIndex(new BasicDBObject("dateCreated", 1));
        }
        mongoDBDao.saveTable(restReqResInfo, collection);
    }

    public RestReqResInfo getRestReqResInfo() {
        return restReqResInfo;
    }
}
