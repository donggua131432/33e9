package com.e9cloud.core.support;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户被冻结
 * Created by Administrator on 2016/6/8.
 */
public class SystemPermissionException extends AuthenticationException {

    private static final long serialVersionUID = -495801598885958459L;

    public SystemPermissionException() {
        super();
    }

    public SystemPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemPermissionException(String message) {
        super(message);
    }

    public SystemPermissionException(Throwable cause) {
        super(cause);
    }

}
