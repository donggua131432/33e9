package com.e9cloud.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * http client 请求
 *
 * @author wzj
 *
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String sendAxbHttpPost(String url, Map<String, Object> params) throws Exception{
        String result = null;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //MD5加密
        EncryptUtil encryptUtil = new EncryptUtil();
        //获取时间戳
        String ts = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_SLASH);
        String auth =encryptUtil.md5Digest(params.get("secret")+":"+ts).toUpperCase();

        String body = params.get("body").toString();
        logger.info("发送URL："+url);
        logger.info("发送报文："+body);

        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Accept", "application/json;charset=utf-8");
        httppost.setHeader("Content-Type", "application/json;charset=utf-8");
        httppost.setHeader("ts", ts);
        httppost.setHeader("auth", auth);
        BasicHttpEntity requestBody = new BasicHttpEntity();
        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
        requestBody.setContentLength(body.getBytes("UTF-8").length);
        httppost.setEntity(requestBody);
        System.out.println("请求体长度："+requestBody.getContentLength());
        // 执行客户端请求
        CloseableHttpResponse response = httpclient.execute(httppost);
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) result = EntityUtils.toString(entity, "UTF-8");
        } finally {
            response.close();
        }


        System.out.println("返回结果信息："+result);
        // 关闭连接,释放资源
        try {
            httpclient.close();
        } catch (IOException e) {
            logger.error("(IOException error [{}]",e);
        }

        return result;
    }




    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String post(String url, Map<String, Object> params) {

        String result = null;

        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        UrlEncodedFormEntity uefEntity;

        try {

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                formparams.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
            }
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);

			/*
			 * httppost.setHeader("accept", "application/json");
			 * httppost.setHeader("Content-Type",
			 * "application/json;charset=UTF-8");
			 */

            System.out.println("executing request " + httppost.getURI());
            System.out.println("Entity " + EntityUtils.toString(httppost.getEntity()));

            CloseableHttpResponse response = httpclient.execute(httppost);

            try {

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 发送 get请求
     */
    public static void get(String url, Map<String, Object> params) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {

            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            UrlEncodedFormEntity uefEntity;

            System.out.println("executing request " + httpget.getURI());

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();

            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);

            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();

                System.out.println("--------------------------------------");

                // 打印响应状态
                System.out.println(response.getStatusLine());

                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }

                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送 get请求
     */
    public static String get(String url) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String r = "";

        try {

            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            UrlEncodedFormEntity uefEntity;

            System.out.println("executing request " + httpget.getURI());

            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);

            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();

                System.out.println("--------------------------------------");

                // 打印响应状态
                System.out.println(response.getStatusLine());

                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    r = EntityUtils.toString(entity);
                    System.out.println("Response content: " + r);
                }

                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return r;
    }


}
