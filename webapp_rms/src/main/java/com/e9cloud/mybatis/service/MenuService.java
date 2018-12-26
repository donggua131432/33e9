package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Menu;

import java.util.*;

/**
 * 菜单相关service
 * Created by Administrator on 2016/2/16.
 */
public interface MenuService extends IBaseService{

    /**
     * 分页查询菜单列表
     * @return PageWrapper
     */
    PageWrapper pageMenu(String sysType);

    /**
     * 查询所有的菜单列表
     * @return List<Menu>
     */
    List<Menu> listAll(String sysType);

    /**
     * 加载用户的所属菜单
     * @param sysType
     * @return
     */
    List<Menu> getMenusByUserID(String sysType, int userId);

    /**
     * 根据id删除一个菜单
     */
    void deleteMenuById(String id);

    /**
     * 根据parentId 查询 子菜单
     */
    List<Menu> getMenusByParentId(String parentId);

    /**
     * 保存菜单
     * @param menu
     */
    void saveMenu(Menu menu);

    /**
     * 保存菜单和Action
     * @param menu
     */
    void saveMenuAndAction(Menu menu);

    /**
     * 修改菜单
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 根据主键选择菜单
     * @param id
     * @return
     */
    Menu getMenuById(String id);

    /**
     * 根据map(parentId,sysType) 查询 子菜单
     */
    List<Menu> getMenusByMap(Map map);

}
