package com.e9cloud.exception;

/**
 * Created by Administrator on 2016/4/19.
 */
public class HttpException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public HttpException() {
		super();
	}

	public HttpException(String msg) {
		super(msg);
	}

	public HttpException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public HttpException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
