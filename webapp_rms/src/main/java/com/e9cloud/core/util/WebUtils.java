package com.e9cloud.core.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {

	public static boolean isAjax(HttpServletRequest httpRequest) {
		return "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With"));
	}

	public static void sendJson(HttpServletResponse httpResponse, String jsonString) throws IOException {
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/json");
		PrintWriter out = httpResponse.getWriter();
		out.println(jsonString);
		out.flush();
		out.close();
	}

	public static void issueRedirect(ServletRequest request, ServletResponse response, String unauthorizedUrl)
			throws IOException {
		org.apache.shiro.web.util.WebUtils.issueRedirect(request, response, unauthorizedUrl);
	}

	public static HttpServletResponse toHttp(ServletResponse response) {
		return org.apache.shiro.web.util.WebUtils.toHttp(response);
	}

}
