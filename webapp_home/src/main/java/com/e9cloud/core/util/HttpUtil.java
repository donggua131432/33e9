package com.e9cloud.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * http client 请求
 *
 * @author wzj
 *
 */
public class HttpUtil {

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static JsonNode post(String url, Map<String, Object> params) {

        JsonNode jsonNode = null;

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

                HttpEntity entityres = response.getEntity();

                if (entityres != null) {
                    jsonNode = JSonUtils.readerValue(entityres.getContent());
                    System.out.println("--------------------------------------");
                    System.out.println("Response content: " + jsonNode.toString());
                    System.out.println("--------------------------------------");
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

        return jsonNode;
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
