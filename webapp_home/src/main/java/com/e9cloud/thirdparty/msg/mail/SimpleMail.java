package com.e9cloud.thirdparty.msg.mail;

import java.util.ArrayList;
import java.util.List;

public class SimpleMail {

	// 收件人邮箱地址
	private String to;

	// 发件人邮箱地址
	private String from;

	// SMTP服务器地址
	private String smtpServer;

	// 登录SMTP服务器的用户名
	private String username;

	// 登录SMTP服务器的密码
	private String password;

	// 邮件主题
	private String subject;

	// 邮件正文
	private String content;

	// 记录所有附件文件的集合
	List<String> attachments = new ArrayList<String>();

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

}
