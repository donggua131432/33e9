package com.e9cloud.core.util;

import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.ShiroDbRealm;
import com.e9cloud.mybatis.service.ShiroDbRealm.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;


/**
 * 类作用：用户的基本信息
 * 
 * @author wzj
 */
public class UserUtil {

	private static final int SALT_SIZE = 8;
	private static final int HASH_INTERATIONS = 1024;

	/** 获取当前登录用户的对象 */
	public static User getCurrentUser() {
		// 使用Shiro获取当前登录用户的信息
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		if (shiroUser == null) {
			return null;
		}
		return shiroUser.getUser();
	}

	/** 获取当前登录用户的对象 */
	public static int getCurrentUserId() {
		// 使用Shiro获取当前登录用户的信息
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		if (shiroUser == null || shiroUser.getUser() == null) {
			return -1;
		}
		return shiroUser.getUser().getId();
	}

	/** 返回当前登录用户的明文密码 */
	public static String getCurrentUserPassword() {
		// 使用Shiro获取当前登录用户的信息
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();

		return shiroUser.getPassword();
	}

	/** 重新加载shiro中的用户信息 */
	public static void reloadUser(User user) {
		// 使用Shiro获取当前登录用户的信息
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		shiroUser.reload(user, user.getPassword());
	}

	/** 判断当前用户是否登录 */
	public static boolean isLogin() {

		return getCurrentUser() != null;
	}

	/** 退出 */
	public static void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	/** 登录 */
	public static boolean login(User user) {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 密码加密。
	 * 
	 * @param user
	 *            要加密的用户。
	 * @return 返回加密后的用户对象。
	 */
	public static void encryption(User user) throws Exception {

		/* 对用户密码进行加密 */
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));
		byte[] shaPassword = Digests.sha1(user.getPassword(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(shaPassword));
	}

	/**
	 * 密码加密。
	 * 
	 * @param user
	 *            要加密的用户。
	 * @return 返回加密后的用户对象。
	 */
	public static boolean checkpwd(User user, String pwd) {

		/* 对用户密码进行加密 */
		byte[] shaPassword = Digests.sha1(pwd, Encodes.decodeHex(user.getSalt()), HASH_INTERATIONS);
		return user.getPassword().equals((Encodes.encodeHex(shaPassword)));
	}
}
