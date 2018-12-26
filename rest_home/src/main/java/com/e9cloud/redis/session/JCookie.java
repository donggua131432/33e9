package com.e9cloud.redis.session;

import com.e9cloud.util.Tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie 工具类
 */
public class JCookie {

    private HttpServletRequest request;

    private HttpServletResponse response;

    public final static String AccessToken = "AccessToken";

    public final static long expiry = 7200;

    public JCookie(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    /**
     * 创建 一个 cookie
     *
     * @param name cookieName
     * @param value cookie值
     */
    public void createCookie(String name, String value){
        createCookie(name, value ,null, null);
    }

    /**
     * 删除 一个 cookie
     *
     * @param name cookieName
     */
    public void removeCookie(String name){

        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/"); //设置成跟写入cookies一样的
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    /**
     * 得到 一个 cookie
     *
     * @param name cookieName
     */
    public Cookie getCookie(String name){

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (AccessToken.equals(cookie.getName())) {
                return cookie;
            }
        }

       return null;
    }

    /**
     * 创建 一个 cookie
     *
     * @param name cookieName
     * @param value cookie值
     * @param path 路径
     * @param domain webdomain
     */
    public void createCookie(String name, String value, String path, String domain){

        Cookie cookie = new Cookie(name, value);

        if (Tools.isNotNullStr(path)) {
            cookie.setPath(path);
        }

        if (Tools.isNotNullStr(domain)) {
            cookie.setDomain(domain);
        }

        this.response.addCookie(cookie);
    }

}
