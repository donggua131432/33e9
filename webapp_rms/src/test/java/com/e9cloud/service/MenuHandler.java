package com.e9cloud.service;

import com.e9cloud.mybatis.service.MenuService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 菜单管理类,动态生成 MenuCode
 * Created by Administrator on 2016/5/24.
 */
public class MenuHandler extends BaseController{

    @Autowired
    private MenuService menuService;

    @Autowired
    public void generatedMenu() {

    }
}
