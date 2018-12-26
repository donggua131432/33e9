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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatRecordClient {

	private static String serverUri = "http://127.0.0.1:8085/rest_home";

	public static void main(String[] args) {
		 query();
	}

	public static HttpResponse post(String cType, String accountSid, String authToken, String timestamp, String url,
			DefaultHttpClient httpclient, EncryptUtil encryptUtil, String body) throws Exception {
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Accept", cType);
		httppost.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httppost.setHeader("Authorization", auth);
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		System.out.println("-----" + requestBody.getContentLength());
		System.out.println("步入httpclient.execute前");
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		// HttpResponse response =callHttps(httpclient,httppost);
		System.out.println("步入httpclient.execute后");
		return response;
	}

	public static String getSignature(String accountSid, String authToken, String timestamp, EncryptUtil encryptUtil)
			throws Exception {
		String sig = accountSid + authToken + timestamp;
		String signature = encryptUtil.md5Digest(sig);
		return signature.toUpperCase();
	}

	private static void query() {
		System.out.println("vnRecordsQuery");
		String accountSid = "2c565993288f4097b22e38f500c91f92";
		String authToken = "4b5270819b024b39ae07bdcd984846ef";
		String url = serverUri + "/2016-01-01/Accounts/" + accountSid + "/VnRecord/query";
		Calendar cal = Calendar.getInstance();
		cal.set(2016,8,05);
		String body = "{'req':{'feeid':'502aaf1191254c46890802a5cccaefe4','statday':'"+new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+"'}}";
		String result = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature = getSignature(accountSid, authToken, timestamp, encryptUtil);
			url = url + "?sig=" + signature;

			System.out.println(body);
			HttpResponse response = post("application/json", accountSid, authToken, timestamp, url, httpclient,
					encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		System.out.println("读取返回结果result:" + result);
	}

}
