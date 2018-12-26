package com.e9cloud.rest.service;

import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.rest.base.BaseTest;
import com.e9cloud.rest.obt.CallNotifyHttp;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * test for AccountService
 */
public class MongodbCallNotifyAdd extends BaseTest {

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;



    @Test
    public void testAddCallNotifyMongoDB(){
        CallNotifyHttp callNotifyHttp = new CallNotifyHttp();
        callNotifyHttp.setAction("CallInvite");
        callNotifyHttp.setType(1);
        callNotifyHttp.setAppId("d55609ec98214f79aead8ba7e839f0a7");
        callNotifyHttp.setSubId("60000000000008mRrDm254585");
        callNotifyHttp.setCaller("867551001");
        callNotifyHttp.setCalled("867551019");
        callNotifyHttp.setUserFlag(1);
        callNotifyHttp.setSubType(0);
        callNotifyHttp.setCallSid("cbceb1bc692f47859b1668784afcba16");
        callNotifyHttp.setDateCreated("2016-01-26 11:04:10");
        callNotifyHttp.setUserData("TEST");

        callNotifyHttp.setStartTimeA("20160126110410");
        callNotifyHttp.setStartTimeB("20160126110410");
        callNotifyHttp.setEndTime("20160126110437");
        callNotifyHttp.setDuration("27");
        callNotifyHttp.setRecordUrl("");
        callNotifyHttp.setByeType("normal hangup");

        mongoDBDao.save(callNotifyHttp);
    }




}
