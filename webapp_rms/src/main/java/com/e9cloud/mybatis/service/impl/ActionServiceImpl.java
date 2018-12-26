package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.Tree;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.Action;
import com.e9cloud.mybatis.domain.Menu;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.ActionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限管理相关
 * Created by Administrator on 2016/9/6.
 */
@Service
public class ActionServiceImpl extends BaseServiceImpl implements ActionService {

    /**
     * 分页查询action
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageAction(Page page) {
        return this.page(Mapper.Menu_Mapper.countMenuWithAction, Mapper.Menu_Mapper.pageMenuWithAction, page);
    }

    /**
     * 得到权限树
     *
     * @return
     */
    @Override
    public List<Tree> getATree(String roleId) {
        return this.findObjectList(Mapper.Action_Mapper.selectATree, roleId);
    }

    /**
     * 保存action
     *
     * @param action
     */
    @Override
    public void saveAction(Action action) {
        this.save(Mapper.Action_Mapper.insertSelective, action);
    }

    /**
     * 删除根据menuID 删除 action
     *
     * @param menuId
     */
    @Override
    public void deleteActionByMenuId(String menuId) {
        this.delete(Mapper.Action_Mapper.deleteActionByMenuId, menuId);
    }

    /**
     * 根据menuId 选择菜单
     *
     * @param mid
     * @return
     */
    @Override
    public List<Action> getActionstByMId(String mid) {
        return this.findObjectList(Mapper.Action_Mapper.selectByMId, mid);
    }
}
