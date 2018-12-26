package com.e9cloud.core.support;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户被禁用
 * Created by Administrator on 2016/6/8.
 */
public class DisableException extends AuthenticationException {

    private static final long serialVersionUID = -495801598885958459L;

    public DisableException() {
        super();
    }

    public DisableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisableException(String message) {
        super(message);
    }

    public DisableException(Throwable cause) {
        super(cause);
    }

}
