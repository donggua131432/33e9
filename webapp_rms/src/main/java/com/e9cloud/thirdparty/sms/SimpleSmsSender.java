package com.e9cloud.thirdparty.sms;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单短信发送器
 * 
 * @author wzj
 * 
 */
public class SimpleSmsSender {

	private SimpleSms simpleSms;

	private String url;

	public SimpleSmsSender() {
	}

	public SimpleSmsSender(String url, String username, String password, String form, Integer expandPrefix) {
		this.simpleSms = new SimpleSms();

		this.url = url;

		this.simpleSms.setUsername(username);
		this.simpleSms.setPassword(password);
		this.simpleSms.setFrom(form);
		this.simpleSms.setExpandPrefix(expandPrefix);
	}


	/**
	 * 发送短信
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数
	 * @param character
	 *            邮件内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String url, Map<String,Object> params, String character) {
		AdvanceSms.send(url, params, character);
	}

	/**
	 * 发送短信
	 *
	 * @param url
	 *            请求地址
	 * @param simpleSms
	 *            参数
	 * @param character
	 *            邮件内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String url, SimpleSms simpleSms, String character) {

		Map<String,Object> params = new HashMap<>();

		params.put("username",simpleSms.getUsername());
		params.put("password",simpleSms.getPassword());
		params.put("to",simpleSms.getTo());
		params.put("from",simpleSms.getFrom());
		params.put("content",simpleSms.getContent());
		params.put("expandPrefix",simpleSms.getExpandPrefix());
		params.put("presendTime",simpleSms.getPresendTime());

		AdvanceSms.send(url, params, character);
	}

	/**
	 * 发送短信
	 *
	 *  @param to
	 *            短信接收方
	 * @param content
	 *            短信内容
	 * @param presendTime
	 *            发送时间 格式为：YYYY-MM-DD HH24:MI:SS
	 */
	public String send(String to, String content, String presendTime) {

		Map<String,Object> params = new HashMap<>();

		params.put("username",simpleSms.getUsername());
		params.put("password",simpleSms.getPassword());
		params.put("to",to);
		params.put("from",simpleSms.getFrom());
		params.put("content",content);
		params.put("expandPrefix",simpleSms.getExpandPrefix());
		params.put("presendTime",presendTime);

		return AdvanceSms.send(url, params, "GBK");
	}

	/**
	 * 发送短信
	 * @param to
	 *            短信接收方
	 * @param content
	 *            短信内容
	 */
	public String send(String to, String content) {

		return send(to, content, "");
	}

}