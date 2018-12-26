package com.e9cloud.mybatis.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import com.e9cloud.core.support.DisableException;
import com.e9cloud.core.support.FreezeException;
import com.e9cloud.core.support.SystemPermissionException;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.Encodes;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.Action;
import com.e9cloud.mybatis.domain.Role;
import com.e9cloud.mybatis.domain.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Objects;

/**
 * 实现登陆功能的realm
 * 
 * @author wzj
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {

	private static final int HASH_INTERATIONS = 1024;

	private static final String HASH_ALGORITHM = "SHA-1";

	@Autowired
	protected AccountService accountService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = null;
		try {
			user = accountService.findAccountByLoginName(token.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			if (Tools.isNullStr(user.getSysType()) || !user.getSysType().contains(Constants.RMS_USER_SYS_TYPE_R)) { // 用户系统权限
				throw new SystemPermissionException("no permission to login this system");
			}
			if(Constants.RMS_USER_STATUS_DISABLED.equals(user.getStatus())){
				throw new DisableException("this user is disabled");
			}
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(user, new String(token.getPassword())),
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser)principals.fromRealm(getName()).iterator().next();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User user = shiroUser.getUser();
		/*for (Role role : user.getRoles()) {
			info.addRole(role.getId());
			for (Action action : role.getActions()) {
				info.addStringPermission(action.getUrl());
			}
		}*/

		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;

		private User user;
		private String password;

		public ShiroUser(User user, String password) {
			this.user = user;
			this.password = password;
		}

		public User getUser() {
			return user;
		}

		public String getName() {
			return user.getNick();
		}

		public String getPassword() {
			return password;
		}

		public void reload(User user, String password){
			this.user = user;
			this.password = password;

		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return user.getUsername() == null ? "" : user.getUsername();
		}

		/**
		 * 重载hashCode,只计算id;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(user.getId());
		}

		/**
		 * 重载equals,只计算id;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (user == null) {
				if (other.user != null) {
					return false;
				}
			} else if (!user.getId().equals(other.user.getId())) {
				return false;
			}
			return true;
		}
	}
}
