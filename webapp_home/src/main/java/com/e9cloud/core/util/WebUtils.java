package com.e9cloud.core.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/4/12.
 */
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

}
