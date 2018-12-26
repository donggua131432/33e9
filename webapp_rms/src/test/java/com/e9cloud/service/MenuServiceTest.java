package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.domain.Menu;
import com.e9cloud.mybatis.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 菜单管理相关测试
 * Created by Administrator on 2016/2/16.
 */
public class MenuServiceTest extends BaseTest{

    @Autowired
    private MenuService menuService;

    /** 测试分页查询菜单列表 */
    @Test
    public void testPageMenu(){

        Page page = new Page();
        PageWrapper wrapper = menuService.pageMenu("R");

        List<?> rows =  wrapper.getRows();
        rows.forEach((v)->{
            logger.info("-------------" + v);
        });

    }

    /** 测试分页查询菜单列表 */
    @Test
    public void testListAll(){

        List<Menu> rows = menuService.listAll("R");

        rows.forEach((v)->{
            logger.info("-------------" + v);
        });

    }

}
