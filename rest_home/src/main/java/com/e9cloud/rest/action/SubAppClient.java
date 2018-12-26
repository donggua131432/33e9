package com.e9cloud.rest.action;

import com.e9cloud.util.DateUtil;
import com.e9cloud.util.EncryptUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.util.Date;

public class SubAppClient
{

	private static String serverUri = "http://127.0.0.1:8080/rest_home";


	public static void main(String[] args)
	{

		createJson();
//		createXML();
//		modifyJson();
//		modifyXML();
//		deleteJson();
//		deleteXML();
//		queryJson();
//		queryXML();
	}

	public static HttpResponse post(String cType,String accountSid,String authToken,String timestamp,String url,DefaultHttpClient httpclient,EncryptUtil encryptUtil,String body) throws Exception{
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
		System.out.println("-----"+requestBody.getContentLength());
		System.out.println("步入httpclient.execute前");
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		//HttpResponse response =callHttps(httpclient,httppost);
		System.out.println("步入httpclient.execute后");
		return response;
	}

	public static String getSignature(String accountSid, String authToken,String timestamp,EncryptUtil encryptUtil) throws Exception{
		String sig = accountSid + authToken + timestamp;
		String signature = encryptUtil.md5Digest(sig);
		return signature.toUpperCase();
	}
	
	private static void createJson()
	{
		System.out.println("createJson");
		String accountSid="2c565993288f4097b22e38f500c91f92";
		String authToken="4b5270819b024b39ae07bdcd984846ef";
		String url=serverUri + "/SoftVersion/Accounts/AccountSid/Subid/create";

		String body="{'req':{'appId':'b6f55ae1e5344782bccf03a8fc526460','subName':'c彭春臣'}}";
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;

			System.out.println(body);
			HttpResponse response=post("application/json",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		System.out.println("读取返回结果result:"+result);
	}
	
	private static void createXML()
	{
		System.out.println("createXML");
		String accountSid="2c565993288f4097b22e38f500c91f92";
		String authToken="4b5270819b024b39ae07bdcd984846ef";
		String url=serverUri + "/SoftVersion/Accounts/AccountSid/Subid/create";
		String body="<?xml version='1.0' encoding='UTF-8'?><req><appId>b6f55ae1e5344782bccf03a8fc526460</appId><subName>pcc</subName></req>";
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
//			String timestamp = "20160223000000";// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;

			System.out.println(body);
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		System.out.println(result);
	}

	private static void modifyJson()
	{
		System.out.println("modifyJson");
		String accountSid="2c565993288f4097b22e38f500c91f92";
		String authToken="4b5270819b024b39ae07bdcd984846ef";
		String url=serverUri + "/SoftVersion/Accounts/AccountSid/Subid/modify";

		String body="{'req':{'appId':'b6f55ae1e5344782bccf03a8fc526460','newSubName':'pcc','subId':'44caa226f13f4a6cb383c33d677dbe21'}}";
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;

			System.out.println(body);
			HttpResponse response=post("application/json",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println("读取返回结果result:"+result);
	}

	private static void modifyXML()
	{
		System.out.println("modifyXML");
		String accountSid="2c565993288f4097b22e38f500c91f92";
		String authToken="4b5270819b024b39ae07bdcd984846ef";
		String url=serverUri + "/SoftVersion/Accounts/AccountSid/Subid/modify";
		String body="<?xml version='1.0' encoding='utf-8'?>" +
				"<req><appId>b6f55ae1e5344782bccf03a8fc526460</appId><newSubName>pcc</newSubName><subId>44caa226f13f4a6cb383c33d677dbe21</subId></req>";
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
//			String timestamp = "20160223000000";// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;

			System.out.println(body);
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println(result);
	}

	private static void deleteJson()
	{
		System.out.println("deleteJson");
		String accountSid="2c565993288f4097b22e38f500c91f92";
		String authToken="4b5270819b024b39ae07bdcd984846ef";
		String url=serverUri + "/SoftVersion/Accounts/AccountSid/Subid/delete";

		String body="{'req':{'appId':'b6f55ae1e5344782bccf03a8fc526460','subId':'b18f0644ed3f4bd7a911d91e1263e59b'}}";
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;

			System.out.println(body);
			HttpResponse response=post("application/json",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println("读取返回结果result:"+result);
	}

	private static void deleteXML()
	{
		System.out.println("deleteXML");
		String accountSid="2c565993288f4097b22e38f500c91f92";
		String authToken="4b5270819b024b39ae07bdcd984846ef";
		String url=serverUri + "/SoftVersion/Accounts/AccountSid/Subid/delete";
		String body="<?xml version='1.0' encoding='utf-8'?>" +
				"<req><appId>b6f55ae1e5344782bccf03a8fc526460</appId><subId>b18f0644ed3f4bd7a911d91e1263e59b</subId></req>";
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
//			String timestamp = "20160223000000";// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;

			System.out.println(body);
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println(result);
	}

	private static void queryJson()
	{
		System.out.println("queryJson");
		String accountSid="2c565993288f4097b22e38f500c91f92";
		String authToken="4b5270819b024b39ae07bdcd984846ef";
		String url=serverUri + "/SoftVersion/Accounts/AccountSid/Subid/query";

		String body="{'req':{'appId':'b6f55ae1e5344782bccf03a8fc526460'}}";
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;

			System.out.println(body);
			HttpResponse response=post("application/json",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println("读取返回结果result:"+result);
	}

	private static void queryXML()
	{
		System.out.println("queryXML");
		String accountSid="2c565993288f4097b22e38f500c91f92";
		String authToken="4b5270819b024b39ae07bdcd984846ef";
		String url=serverUri + "/SoftVersion/Accounts/AccountSid/Subid/query";
		String body="<?xml version='1.0' encoding='utf-8'?>" +
				"<req><appId>b6f55ae1e5344782bccf03a8fc526460</appId></req>";
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME_NO_SLASH);// 时间戳
//			String timestamp = "20160223000000";// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;

			System.out.println(body);
			HttpResponse response=post("application/xml",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println(result);
	}
}
