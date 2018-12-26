package com.e9cloud.rest.cb;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mongodb.domain.RestReqResInfo;
import com.e9cloud.rest.obt.CallNotifyHttp;
import com.e9cloud.rest.obt.CallNotifyStatus;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.Tools;
import com.mongodb.BasicDBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2016/3/18.
 */
public class StatisMsgManager {

    private static final Logger logger = LoggerFactory.getLogger(StatisMsgManager.class);
    private static final MongoTemplate mongoTemplate = SpringContextHolder.getBean(MongoTemplate.class);

    private static StatisMsgManager restTreadManager = new StatisMsgManager();

    private StatisMsgManager() {

    }

    public static StatisMsgManager getInstance() {
        return restTreadManager;
    }

    public void init() {
        start();
    }

    /**
     * 解析消息队列
     */
    private final Queue<Object> paresMsgTaskQueue = new ConcurrentLinkedQueue<>();

    /**
     * 添加解析消息
     *
     * @param object
     */
    public void addParseMsg(Object object) {
        logger.info("接收RAS统计消息");
        paresMsgTaskQueue.offer(object);
    }

    /***
     * 开始解析消息
     */
    public void start() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object object = null;
                        int times = 0;
                        List<Object> list = new ArrayList<>();
                        long start = System.currentTimeMillis();
                        while ((object = paresMsgTaskQueue.poll()) != null && times < 500) {
                            if (object instanceof RestReqResInfo) {
                                RestReqResInfo restReqResInfo = (RestReqResInfo) object;
                                restReqResInfo.setDateCreated(new Date());
                                list.add(restReqResInfo);
                            } else {
                                list.add(object);
                            }
                            times++;
                        }

                        if (!list.isEmpty()) {
                            StatisMsgManager.getInstance().addRestReqResInfoList(list);
                        }

                        long diff = System.currentTimeMillis() - start;
                        if (diff < 100) {
                            Thread.sleep(100 - diff);
                        }
                        times = 0;
                    } catch (Exception e) {
                        logger.error("StatisMsgManager==>向mongoDB中添加信息超时{}", e);
                    }
                }
            }
        };
        Thread thread = new Thread(runnable, "接收消息线程");
        thread.start();
    }

    /**
     * 向mongoDB中添加请求信息
     *
     * @param list
     */
    public void addRestReqResInfoList(List<Object> list) {
        //获取表名
        String tableName = Tools.genMogonDbTableName(ConstantsEnum.RESTREQ_NAME.getStrValue(), new Date());
        //判断改表是否存在 不存在就创建并建立索引
        if (!mongoTemplate.collectionExists(tableName)) {
            mongoTemplate.createCollection(tableName);
            mongoTemplate.getCollection(tableName).createIndex(new BasicDBObject("dateCreated", 1));
        }
        //RestReqResInfo对象做分表存储，其它做泛型入库
        List<RestReqResInfo> restReqResInfos = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        for (Object obj : list) {
            if (obj instanceof RestReqResInfo) {
                restReqResInfos.add((RestReqResInfo) obj);
            } else {
                objects.add(obj);
            }
        }
        if (!restReqResInfos.isEmpty()) {
            mongoTemplate.insert(list, tableName);
        }
        if (!objects.isEmpty()) {
            mongoTemplate.insertAll(objects);
        }

    }
}
