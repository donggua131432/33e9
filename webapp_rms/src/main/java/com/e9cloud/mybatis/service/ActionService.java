package com.e9cloud.mybatis.service;

import com.e9cloud.core.common.Tree;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Action;
import com.e9cloud.mybatis.domain.Menu;

import java.util.List;

/**
 * 权限管理相关
 * Created by Administrator on 2016/9/6.
 */
public interface ActionService extends IBaseService {

    /**
     * 分页查询action
     * @param page
     * @return
     */
    PageWrapper pageAction(Page page);

    /**
     * 得到权限树
     * @return
     */
    List<Tree> getATree(String roleId);

    /**
     * 保存action
     * @param action
     */
    void saveAction(Action action);

    /**
     * 删除根据menuID 删除 action
     * @param menuId
     */
    void deleteActionByMenuId(String menuId);

    /**
     * 根据menuId 选择菜单
     * @param mid
     * @return
     */
    List<Action> getActionstByMId(String mid);
}
