package com.e9cloud.rest.service;


import com.e9cloud.rest.base.BaseTest;
import com.e9cloud.rest.obt.CallNotifyHttp;
import com.mongodb.BasicDBObject;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by dukai on 2016/4/19.
 */
public class mongodbInit extends BaseTest{

    @Test
    public void mongodbInitCollection(){
        MongoTemplate mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");

        mongoTemplate.createCollection(CallNotifyHttp.class);
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("appId", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("subId", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("caller", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("called", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("callSid", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("dateCreated", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("startTimeA", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("startTimeB", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("endTime", -1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("duration", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("maskNumber", 1));
        mongoTemplate.getCollection(mongoTemplate.getCollectionName(CallNotifyHttp.class)).createIndex(new BasicDBObject("startTime", 1));

        System.out.println("Over");
    }
}
