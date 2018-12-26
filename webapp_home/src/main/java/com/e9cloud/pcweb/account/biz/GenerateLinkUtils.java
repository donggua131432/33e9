package com.e9cloud.pcweb.account.biz;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.util.Encodes;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 生成帐户激活、重新设置密码的链接
 */
public class GenerateLinkUtils {

	private static final String V_CODE = "vcode";

	/**
	 * 生成帐户激活链接
	 */
	public static String generateActivateLink(Account account, String vcode) {

		AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);

		return appConfig.getAuthUrl() + "reg/actAccount?uid=" + account.getUid() + "&" + V_CODE + "=" + vcode;
	}

	/**
	 * 生成重设密码的链接
	 */
	public static String generateResetPwdLink(Account account, String vcode) {
		AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
		return appConfig.getAuthUrl() + "retrieve/updatepwds?uid=" + account.getUid() + "&" + V_CODE + "=" + vcode;
	}

	/**
	 * 生成更改邮箱确认的链接
	 */
	public static String generateUpdateEmailLink(String uid, String vcode) {
		AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
		return appConfig.getAuthUrl() + "retrieve/upEmail?uid=" +uid + "&" + V_CODE + "=" + vcode;
	}
	/**
	 * 生成验证帐户的MD5校验码
	 * 
	 * @param user
	 *            要激活的帐户
	 * @return 将用户名和密码组合后，通过md5加密后的16进制格式的字符串
	 */
	public static String generateCheckcode(User user) {
		String userName = user.getUid();
		String randomCode = "";
		return md5(userName + ":" + randomCode);
	}

	/**
	 * 验证校验码是否和注册时发送的验证码一致
	 * 
	 * @param user
	 *            要激活的帐户
	 * @param checkCode
	 *            注册时发送的校验码
	 * @return 如果一致返回true，否则返回false
	 */
	public static boolean verifyCheckcode(User user, String checkCode) {
		return generateCheckcode(user).equals(checkCode);
	}

	private static String md5(String string) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
			md.update(string.getBytes());
			byte[] md5Bytes = md.digest();
			return bytes2Hex(md5Bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String bytes2Hex(byte[] byteArray) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (byteArray[i] >= 0 && byteArray[i] < 16) {
				strBuf.append("0");
			}
			strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
		}
		return strBuf.toString();
	}

}
