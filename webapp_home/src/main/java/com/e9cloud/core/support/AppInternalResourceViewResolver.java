package com.e9cloud.core.support;

import com.e9cloud.core.application.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * 类作用：为前端页面引入appConfig配置
 *
 * @author wzj
 */
public class AppInternalResourceViewResolver extends InternalResourceViewResolver {

    @Autowired
    private AppConfig appConfig;

    @Override
    public void setAttributesMap(Map<String, ?> attributes) {
        Map<String, Object> app = new HashMap<String, Object>();

        app.put("appConfig", appConfig);
        app.putAll(attributes);
        super.setAttributesMap(app);
    }

}
