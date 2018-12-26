package com.e9cloud.pcweb.account.biz;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.thirdparty.msg.mail.MailSenderFactory;
import com.e9cloud.thirdparty.msg.mail.SimpleMailSender;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


public class MailUtils {

	/**
	 * 发送重设密码链接的邮件
	 * 
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static void sendResetPasswordEmail(Account account, String vcode) throws MessagingException {

		String href = GenerateLinkUtils.generateResetPwdLink(account, vcode);
		String href2 = getAppConf().getAuthUrl();
		String subject = "玖云平台--找回密码";

		String context = "亲爱的玖云平台 "+account.getEmail()+" 用户,您好："+"<br>"+"<br>"+"您启动了找回密码功能!"
				+ "  请点击以下链接以继续："+"<br>"+"<br>"+"<a href='" + href + "'>"+href+"</a>"+"<br>"
				+"如果以上链接无法点击, 请将上面的地址复制到您的浏览器(如IE)的地址栏进入玖云平台;"+"<br>"
				+"为了确保您的帐号安全,此链接将在电子邮件发出1小时后过期;"+"<br>"
				+"如果您并未提交此请求,可能是其他用户无意中输入了您的电子邮件地址,您的账户仍然安全;"+"<br>"
				+"如果您怀疑有未经授权的人员访问了您的账户,您应前往玖云平台官网：【"+"<a href='" + href2 + "'>"+href2+"</a>"+"】,重设您的密码。"+"<br>"
				+"请勿直接回复该邮件,有关玖云平台的更多帮助信息,请访问：【"+"<a href='" + href2 + "'>"+href2+"</a>"+"】"+"<br>"+"<br>"

				+"此致"+"<br>"+"<br>"
				+"玖云平台"+"<br>"
				;
		SimpleMailSender sms = MailSenderFactory.getSender();
		sms.send(account.getEmail(), subject, context);
	}

	/**
	 * 注册成功后,向用户发送账户激活链接的邮件
	 * 
	 * @param account
	 *            未激活的用户
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static void sendAccountActivateEmail(Account account, String vcode) throws MessagingException {

		String href = GenerateLinkUtils.generateActivateLink(account, vcode);
		String href2 = getAppConf().getAuthUrl();
		String subject = "玖云平台--账户激活";
		String context = "亲爱的玖云平台用户，您好："+"<br>"+"<br>"+"感谢您注册使用玖云平台!"+"<br>"+"您的登录邮箱为："
				+account.getEmail()+"  请点击以下链接激活账号："+"<br>"+"<br>"+"<a href='" + href + "'>"+href+"</a>"+"<br>"
				+"如果以上链接无法点击,请将上面的地址复制到您的浏览器(如IE)的地址栏进入玖云平台（该链接在24小时内有效,24小时后需要重新注册);"+"<br>"
				+"如果这不是您的邮件请忽略，很抱歉打扰您，请原谅。"+"<br>"
				+"请勿直接回复该邮件,有关玖云平台的更多帮助信息,请访问：【"+"<a href='" + href2 + "'>"+href2+"</a>"+"】"+"<br>"+"<br>"
				+"此致"+"<br>"+"<br>"
				+"玖云平台"+"<br>"
				;
		SimpleMailSender sms = MailSenderFactory.getSender();
		sms.send(account.getEmail(), subject, context);
	}

	/**
	 * 发送修改邮箱确认的邮件
	 *
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static void sendUpdateEmail(String href,String email) throws MessagingException {
		String href2 = getAppConf().getAuthUrl();
		String subject = "玖云平台--更换邮箱确认";
		String context = "亲爱的玖云平台用户，您好："+"<br>"+"<br>"+"感谢您注册使用玖云平台!"+"<br>"
				         +"要更换新的邮箱, 请使用以下链接:<br/><a href='" + href + "'>"+href+"</a>"
				         +"如果以上链接无法点击,请将上面的地址复制到您的浏览器(如IE)的地址栏进入玖云平台（该链接在1小时内有效);"+"<br>"
				         +"如果这不是您的邮件请忽略，很抱歉打扰您，请原谅。"+"<br>"
				         +"请勿直接回复该邮件,有关玖云平台的更多帮助信息,请访问：【"+"<a href='" + href2 + "'>"+href2+"</a>"+"】"+"<br>"+"<br>"
				         +"此致"+"<br>"+"<br>"
				         +"玖云平台"+"<br>";

		SimpleMailSender sms = MailSenderFactory.getSender();
		sms.send(email, subject, context);
	}

	/**
	 * 官网修改密码成功后，向用户发送邮件
	 *
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static void sendForResetPasswordSuc(String email) throws MessagingException {

		String href2 = getAppConf().getAuthUrl();
		String subject = "玖云平台--密码设置成功";

		StringBuilder sb = new StringBuilder();
		sb.append("亲爱的玖云平台用户,您好：");
		sb.append("<br><br>");
		sb.append("您的玖云平台账号(").append(email).append(")密码已成功重设;<br>");

		sb.append("如果您没有做过此更改，或者如果您怀疑有未经授权的人员访问了您的账户，");
		sb.append("请立即前往").append(" <a href='" + href2 + "'>" + href2 + "</a> ");
		sb.append("重设您的密码，然后登录玖云平台，查看并更新您的安全设置；<br>");

		sb.append("请勿直接回复该邮件，有关玖云平台的更多帮助信息，请访问：");
		sb.append(" <a href='" + href2 + "'>" + href2 + "</a> <br>");
		sb.append("如果您需要更多帮助，请联系玖云平台客服人员。<br>");

		sb.append("此致<br><br>");
		sb.append("玖云平台"+"<br>");

		SimpleMailSender sms = MailSenderFactory.getSender();
		sms.send(email, subject, sb.toString());
	}

	/**
	 * 官网修改密码成功后，向用户发送邮件
	 *
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static void sendForUpdatepwd(String email) throws MessagingException {

		String href2 = getAppConf().getAuthUrl();
		String subject = "玖云平台--修改密码成功";

		StringBuilder sb = new StringBuilder();
		sb.append("亲爱的玖云平台用户,您好：");
		sb.append("<br><br>");
		sb.append("您的玖云平台账号(").append(email).append(")已成功修改了登录密码，如非您本人操作请及时致电玖云平台客服。<br><br>");
		sb.append("此致<br><br>");
		sb.append("玖云平台"+"<br>");

		SimpleMailSender sms = MailSenderFactory.getSender();
		sms.send(email, subject, sb.toString());
	}

	public static AppConfig getAppConf(){
		return  SpringContextHolder.getBean(AppConfig.class);
	}
}
