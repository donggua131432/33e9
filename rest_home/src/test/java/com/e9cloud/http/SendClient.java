package com.e9cloud.http;

import com.e9cloud.util.DateUtil;
import com.e9cloud.util.EncryptUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by dukai on 2016/9/27.
 * 公共客户端发送请求
 */

public class SendClient extends HttpBaseClient {

	private static final Logger logger = LoggerFactory.getLogger(SendClient.class);

	public static String serverUri = "http://127.0.0.1:8085/rest_home";
	//public static String serverUri = "http://10.0.33.34:8083";
	//public static String serverUri = "http://10.0.7.46:8080";

	/**
	 *  * 需要鉴权
	 * @param sendType 请求类型（post或者get)
	 * @param accountSid
	 * @param authToken
	 * @param url  请求地址
	 * @param typeBody 请求/响应参数序列化格式
     * @param body 请求参数内容
     * @return
     */
	public static String sendHttpAuth(String sendType,String accountSid,String authToken,String url,String typeBody,String body){
		logger.info("sendHttps");
		String result = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		//设置超时时间
		httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 180000);
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			url = url +"?sig="+signature;
			logger.info("发送URL："+url);
			logger.info("发送报文："+body);
			HttpResponse response;
			if("post".equals(sendType)){
				response=post("application/"+typeBody, accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			}else{
				response=get("application/"+typeBody, accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			}
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
		logger.info("返回报文:"+result);
		return result;
	}


	/**
	 * 不需要鉴权
	 * @param sendType 请求类型（post或者get)
	 * @param url  请求地址
	 * @param typeBody 请求/响应参数序列化格式
	 * @param body 请求参数内容
     * @return
     */
	public static String sendHttp(String sendType, String url, String typeBody, String body)
	{
		logger.info("sendHttp");
		String result = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		//设置超时时间
		httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 180000);
		try {
			logger.info("发送URL："+url);
			logger.info("发送报文："+body);
			HttpResponse response;
			if("post".equals(sendType)){
				response=post("application/"+typeBody, url, httpclient, body);
			}else{
				response=get("application/"+typeBody, url, httpclient, body);
			}

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
		logger.info("返回报文:"+result);
		return result;
	}

}
