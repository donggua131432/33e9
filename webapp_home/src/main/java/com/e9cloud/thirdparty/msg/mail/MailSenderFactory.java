package com.e9cloud.thirdparty.msg.mail;


/**
 * 发件箱工厂
 * 
 * @author wzj
 * 
 */
public class MailSenderFactory {

	/**
	 * 服务邮箱
	 */
	private static SimpleMailSender serviceSms = null;

	/**
	 * 获取邮箱
	 *
	 *            邮箱类型
	 * @return 符合类型的邮箱
	 */
	public static SimpleMailSender getSender() {
		if (serviceSms == null) {
			serviceSms = new SimpleMailSender("system@33e9.com", "33E9.com");
		}
		return serviceSms;
	}

}