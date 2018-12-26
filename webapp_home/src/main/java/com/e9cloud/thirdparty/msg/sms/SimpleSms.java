package com.e9cloud.thirdparty.msg.sms;

import java.util.ArrayList;
import java.util.List;

/**
 * 下行（MT）短信
 * 当ISP系统通过短信网关向用户手机发短信（MT服务），ISP系统采用类似的方式向短信网关的CGI发短信
 * http://<host>:<port>/GsmsHttp?username=user&password=pass&from=001&to=130xxxxxxxxx&content=hell,world&presendTime=2003-02-03 12:12:03&expandPrefix=113
 * 参数的传输格式为：x-www-form-urlencoded
 */
public class SimpleSms {

	// 接收短信的手机号 非空 支持多个(<=15)手机号码，中间以“,”分割
	private String to;

	// 发短信的端口扩展号
	private String from;

	// ISP登陆名(公司分配给ISP) 非空，如果为机构版，则应以“机构ID:登陆名”方式赋值
	private String username;

	// ISP登陆密码(公司分配给ISP)非空
	private String password;

	// 定时发送时间（格式：YYYY-MM-DD HH24:MI:SS）
	private String presendTime;

	// 短信内容 不能为空(只支持GBK编码,若使用其它编码需要转换一下)
	private String content;

	// 用户下行短信自扩展端口，可为空，只接受数字。其该端口的使用需要相关机构的指定配置。
	// （该字段单独最大长度为14位，但下行短信实际发送后会根据SP号进行相关的截取操作）
	private Integer expandPrefix;

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

	public String getPresendTime() {
		return presendTime;
	}

	public void setPresendTime(String presendTime) {
		this.presendTime = presendTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getExpandPrefix() {
		return expandPrefix;
	}

	public void setExpandPrefix(Integer expandPrefix) {
		this.expandPrefix = expandPrefix;
	}
}
