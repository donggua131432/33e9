package com.e9cloud.redis;

import com.e9cloud.rest.base.BaseTest;
import com.e9cloud.redis.base.Order;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.query.SortQueryBuilder;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public class SpringRedisTest extends BaseTest {

    protected Order order;

    //@Autowired
    protected RedisTemplate redisTemplate;

    @Before
    public void before() throws Exception {

        // redisTemplate = SpringContextHolder.getBean("redisTemplate", RedisTemplate.class);

        order = new Order();
        order.setId("10001");
        order.setOrderNo("AO001");
        order.setPrice(199.6);
        order.setCreateDate(new Date());
    }
	
	@Test
	public void valueOperationTest(){  
		ValueOperations<String, Order> valueOper = (ValueOperations<String, Order>)redisTemplate.opsForValue();
        Order order = new Order("10001","OA1110",11.0,new Date());
        valueOper.set("order:" + order.getId(),order);  
        System.out.println(valueOper.get("order:" + order.getId()).getOrderNo());  
    }  
	@Test
    public void listOperationTest(){  
    	Order order = new Order("10000","OA1110",11.0,new Date());
        ListOperations<String, Order> listOper = (ListOperations<String, Order> )redisTemplate.opsForList();
        listOper.leftPush("user:list", order);//lpush,head  
        listOper.rightPush("user:list", order);//rpush,tail  
    }  
      
    public void boundValueOperationTest(){  
    	Order order = new Order("10000","OA1110",11.0,new Date());
        BoundValueOperations<String, Order> bvo = (BoundValueOperations<String, Order>)redisTemplate.boundValueOps("order:" + order.getId());
        bvo.set(order);  
        bvo.expire(60, TimeUnit.MINUTES);  
    }  
      
    /** 
     * 非连接池环境下，事务操作；对于spring data redis而言，每次操作(例如，get，set)都有会从pool中获取connection； 
     * 因此在连接池环境下，使用事务需要注意。 
     */  
    @Test
    public void txUnusedPoolTest(){  
    	Order order = new Order("10000","OA1110",11.0,new Date());
        redisTemplate.watch("order:" + order.getId());  
        redisTemplate.multi();  
        ValueOperations<String, Order> tvo = redisTemplate.opsForValue();
        tvo.set("user:" + order.getId(), order);  
        redisTemplate.exec();  
    }  
      
    /** 
     * 在连接池环境中，需要借助sessionCallback来绑定connection 
     */  
    @Test
    public void txUsedPoolTest(){
        SessionCallback<Order> sessionCallback = new SessionCallback<Order>() {
            @Override  
            public Order execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws DataAccessException {
                operations.multi();  
                Order order = new Order("10000","OA1110",11.0,new Date());
                String key = "order:" + order.getId();  
                BoundValueOperations<String, Order> oper = operations.boundValueOps(key);
                oper.set(order);  
                oper.expire(60, TimeUnit.MINUTES);  
                operations.exec();  
                return order;  
            }  
        };  
        redisTemplate.execute(sessionCallback);  
    }  
      
    /** 
     * pipeline : 1，正确使用方式 
     */  
    @Test
    public void pipelineTest(){  
        final byte[] rawKey = redisTemplate.getKeySerializer().serialize("user_total");  
        //pipeline  
        RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {  
            @Override  
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {  
                connection.openPipeline();  
                connection.incr(rawKey);  
                connection.incr(rawKey);  
                return connection.closePipeline();  
            }  
              
        };  
          
        List<Object> results = (List<Object>)redisTemplate.execute(pipelineCallback);  
        for(Object item : results){  
            System.out.println(item.toString());  
        }  
    }  
    
    @Test
    //pipeline:备用方式  
    public void pipelineXTest(){  
        byte[] rawKey = redisTemplate.getKeySerializer().serialize("user_total");  
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();  
        RedisConnection redisConnection = RedisConnectionUtils.getConnection(factory);  
        List<Object> results;  
        try{  
            redisConnection.openPipeline();  
            redisConnection.incr(rawKey);  
            results = redisConnection.closePipeline();  
        }finally{  
            RedisConnectionUtils.releaseConnection(redisConnection, factory);  
        }  
        if(results == null){  
            return;  
        }  
        for(Object item : results){  
            System.out.println(item.toString());  
        }  
  
    }  
    /** 
     * list/set排序，注意排序是根据value值的“字符”特性排序，如果value是java-object字节流，将不会有效 
     */  
    @Test
    public void sortTest(){  
		ListOperations<String, String> listOper = redisTemplate.opsForList();  
        String listKey = "user:list";  
        listOper.leftPush(listKey, "zhangsan");  
        listOper.leftPush(listKey, "lisi");  
        listOper.leftPush(listKey, "wanger");  
        SortQueryBuilder<String> builder = SortQueryBuilder.sort(listKey);  
        builder.alphabetical(true);//对字符串使用“字典顺序”  
        builder.order(org.springframework.data.redis.connection.SortParameters.Order.DESC);//倒序  
        builder.limit(0, 2);  
        //builder.limit(new Range(0, 2));  
        List<String> results = redisTemplate.sort(builder.build());  
        for(String item : results){  
            System.out.println(item);  
        }  
    }  
}
