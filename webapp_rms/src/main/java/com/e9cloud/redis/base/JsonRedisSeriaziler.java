package com.e9cloud.redis.base;

import java.nio.charset.Charset;

//import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 使用Jackson自定义json序列化工具
 */
public class JsonRedisSeriaziler {
	public static final String EMPTY_JSON = "{}";  
    
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");  
      
    protected ObjectMapper objectMapper = new ObjectMapper();  
    public JsonRedisSeriaziler(){}  
      
    /** 
     * java-object as json-string 
     * @param object 
     * @return 
     */  
    public String seriazileAsString(Object object){  
        if (object== null) {  
            return EMPTY_JSON;  
        }  
        try {  
            return this.objectMapper.writeValueAsString(object);  
        } catch (Exception ex) {  
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);  
        }  
    }  
      
    /** 
     * json-string to java-object 
     * @param str 
     * @return 
     */  
    public <T> T deserializeAsObject(String str,Class<T> clazz){  
        if(str == null || clazz == null){  
            return null;  
        }  
        try{  
            return this.objectMapper.readValue(str, clazz);  
        }catch (Exception ex) {  
            throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);  
        }  
    }  
}
