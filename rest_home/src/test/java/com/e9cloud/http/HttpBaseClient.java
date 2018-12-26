package com.e9cloud.http;

import com.e9cloud.util.EncryptUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.net.URI;

public abstract class HttpBaseClient{

	/**
	 * 设置鉴权
	 * @param accountSid
	 * @param authToken
	 * @param timestamp
	 * @param encryptUtil
	 * @return
	 * @throws Exception
	 */
	public static String getSignature(String accountSid, String authToken,String timestamp,EncryptUtil encryptUtil) throws Exception{
		String sig = accountSid + authToken + timestamp;
		String signature = encryptUtil.md5Digest(sig);
		return signature.toUpperCase();
	}

	/**
	 * 需要鉴权
	 * @param cType
	 * @param accountSid
	 * @param authToken
	 * @param timestamp
	 * @param url
	 * @param httpclient
	 * @param encryptUtil
	 * @param body
     * @return
     * @throws Exception
     */
	public static HttpResponse post(String cType, String accountSid, String authToken, String timestamp, String url, DefaultHttpClient httpclient, EncryptUtil encryptUtil, String body) throws Exception{
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Accept", cType);
		httppost.setHeader("Content-Type", cType+";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httppost.setHeader("Authorization", auth);
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		//System.out.println("-----"+requestBody.getContentLength());
		System.out.println("步入httpclient.execute前");
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		//HttpResponse response =callHttps(httpclient,httppost);
		System.out.println("步入httpclient.execute后");
		return response;
	}

	public static HttpResponse get(String cType, String accountSid, String authToken, String timestamp, String url, DefaultHttpClient httpclient, EncryptUtil encryptUtil, String body) throws Exception{
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Accept", cType);
		httpget.setHeader("Content-Type", cType+";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httpget.setHeader("Authorization", auth);
		//设置请求参数
		//String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
		//System.out.println("-----"+body);
		httpget.setURI(new URI(httpget.getURI().toString() + "?" + body));
		System.out.println("步入httpclient.execute前");
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httpget);
		//HttpResponse response =callHttps(httpclient,httpget);
		System.out.println("步入httpclient.execute后");
		return response;
	}


	/**
	 * 不需要鉴权
	 * @param cType
	 * @param url
	 * @param httpclient
	 * @param body
	 * @return
     * @throws Exception
     */
	public static HttpResponse post(String cType,  String url, DefaultHttpClient httpclient, String body) throws Exception{
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Accept", cType);
		httppost.setHeader("Content-Type", cType+";charset=utf-8");
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		//System.out.println("-----"+requestBody.getContentLength());
		System.out.println("步入httpclient.execute前");
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		//HttpResponse response =callHttps(httpclient,httppost);
		System.out.println("步入httpclient.execute后");
		return response;
	}

	public static HttpResponse get(String cType, String url, DefaultHttpClient httpclient, String body) throws Exception{
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Accept", cType);
		httpget.setHeader("Content-Type", cType+";charset=utf-8");

		//System.out.println("-----"+body);
		httpget.setURI(new URI(httpget.getURI().toString() + "?" + body));

		System.out.println("步入httpclient.execute前");
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httpget);
		//HttpResponse response =callHttps(httpclient,httpget);
		System.out.println("步入httpclient.execute后");
		return response;
	}
}
