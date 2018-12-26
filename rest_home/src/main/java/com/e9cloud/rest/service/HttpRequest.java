package com.e9cloud.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

	public static String get(boolean isJson, String url, String appId) throws ClientProtocolException, IOException {
		String cType = isJson ? "application/json" : "application/xml";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("appid", appId));

		// 要传递的参数.
		url = url + "?" + URLEncodedUtils.format(params, HTTP.UTF_8);

		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Accept", cType);
		httpget.setHeader("Content-Type", cType + ";charset=utf-8");

		log.info("发送通知参数URI：" + httpget.getURI());
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5 * 1000).build();
		httpget.setConfig(requestConfig);
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String result = "";
		if (entity != null) {
			result = EntityUtils.toString(entity, "UTF-8");
		}
		log.info("soufun响应报文：" + result);
		return result;
	}
}
