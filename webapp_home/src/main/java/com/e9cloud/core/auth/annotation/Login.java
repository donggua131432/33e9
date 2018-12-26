package com.e9cloud.core.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2015/12/23.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {

    // 是否需要鉴权，默认需要
    public boolean auth() default true;

    // 用户角色
    public String[] roles() default {};

    // 设置当没有权限时的跳转路径
    public String url() default "";

    // 设置参数
    public String[] params() default {};

}
