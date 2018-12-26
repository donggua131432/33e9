package com.e9cloud.common;

import com.e9cloud.core.util.HttpUtil;
import org.junit.*;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/12/7.
 */
public class UrlTest {
    @Test
    public void testUrl(){
        String url="http://192.168.0.178:8185/tts?filename=2.wav&appid=123456";
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("content","111111111");

//        String str = HttpUtil.postUrl(url, params);
        String content="测试url解析";
        String str = HttpUtil.postUrl(url,content);
        System.out.println(str+"123");

    }
}
