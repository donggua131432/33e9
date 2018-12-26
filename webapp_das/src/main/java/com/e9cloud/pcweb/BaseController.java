package com.e9cloud.pcweb;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.common.BaseLogger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 基础的Controller,所有的Controller可以继承此类
 */
// @Controller
public class BaseController extends BaseLogger {

	public String redirect = "redirect:";

	public String forward = "forward:";

	@Autowired
	public AppConfig appConfig;

	// 得到session
	public HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

}
