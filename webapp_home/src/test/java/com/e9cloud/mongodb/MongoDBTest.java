package com.e9cloud.mongodb;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.common.ID;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mongodb.domain.RestRecord;
import com.e9cloud.mongodb.domain.StafRecord;
import com.e9cloud.mybatis.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/1.
 */
public class MongoDBTest extends BaseTest{

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    // 基本插入
    @Test
    @Rollback(false)
    public void testSave() {
        U u = new U();
        u.setId(ID.randomUUID());
        u.setName("wzj11111111111");

        mongoDBDao.save(u);
    }

    // 查询
    @Test
    public void testQuery() {

        //RestRecord restRecord = new RestRecord();

        //Query query = new Query();
        //query.addCriteria(Criteria.where("fee").is(0));
        //List<StafRecord> u = mongoDBDao.find(query, StafRecord.class);

        List<RestRecord> u = mongoDBDao.findAll(RestRecord.class);

        System.out.println("---------------------------------------------");
        System.out.println(u.size());
        /*u.forEach((v)->{
            System.out.println(v.getDay() + " : " + v.getFee());
        });*/

        System.out.println("---------------------------------------------");
    }
}
