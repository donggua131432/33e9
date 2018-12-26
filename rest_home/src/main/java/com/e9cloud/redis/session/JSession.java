package com.e9cloud.redis.session;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.redis.base.Order;
import com.e9cloud.redis.util.RedisDBUtils;
import com.e9cloud.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录信息缓存（模拟session）
 */
public class JSession implements Serializable{

    private static final long serialVersionUID = 1L;

    private static final String J_SESSION = "JSession:";

    // 用户信息
    public static final String USER_INFO = "userInfo";

    /**
     * 缓存生存时间
     */
    private static final long expire = 7200;

    private String id;

    private String value;

    public JSession(String id, String value){
        this.id = J_SESSION + id;
        this.value = value;
    }

    public JSession(String id){
        this.id = J_SESSION + id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 创建session
     */
    public void create(){
        RedisDBUtils.set(this.id, this.value, expire);
    }

    /**
     * 刷新session
     */
    public void reExpire() {
        RedisDBUtils.expire(this.id, expire);
    }

    /**
     * 得到session
     */
    public static JSession read(String id) {
        String value = RedisDBUtils.get(J_SESSION + id);
        if (Tools.isNotNullStr(value)) {
            return new JSession(id, value);
        }
        return null;
    }

    /**
     * 存在session
     */
    public static boolean exists(String id) {
        return RedisDBUtils.exists(J_SESSION + id);
    }

    /**
     * 删除session
     */
    public void delete() {
        RedisDBUtils.del(this.id);
    }

}
