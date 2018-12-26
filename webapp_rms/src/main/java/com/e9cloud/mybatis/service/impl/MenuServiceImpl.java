package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.Action;
import com.e9cloud.mybatis.domain.Menu;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.ActionService;
import com.e9cloud.mybatis.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单相关service
 * Created by Administrator on 2016/2/16.
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl implements MenuService{

    @Autowired
    private ActionService actionService;

    /**
     * 分页查询菜单列表
     * @return PageWrapper
     */
    public PageWrapper pageMenu(String sysType){
        List<Menu> rows = new ArrayList<>();

        List<Menu> menus = listAll(sysType);

        menus.forEach((menu)->{
            rows.add(menu);

            List<Menu> children = menu.getChildren();
            int size = children.size();

            for (int i = 0; i < size; i ++) {
                Menu m = children.get(i);
                m.setLast(i == (size -1));
                rows.add(m);

                List<Menu> c = m.getChildren();
                int s = c.size();
                for (int j = 0; j < s; j ++) {
                    Menu mr = c.get(j);
                    mr.setParentLast(i == (size -1));
                    mr.setLast(j == (s -1));
                    rows.add(mr);
                }

            }
            menu.setChildren(children);
        });

        return new PageWrapper(1, 1, rows.size(), rows.size(), rows);
    }

    /**
     * 查询所有的菜单列表
     * @return PageWrapper
     */
    public List<Menu> listAll(String sysType) {
        Map<String, String> map = new HashMap<>();
        map.put("parentId",null);
        map.put("sysType",sysType);

        List<Menu> menus = getMenusByMap(map);

        // 只有三级目录
        menus.forEach((menu)->{
            List<Menu> children = getMenusByParentId(menu.getId());
            children.forEach((m)->{
                m.setChildren(getMenusByParentId(m.getId()));
            });
            menu.setChildren(children);
        });

        return menus;
    }

    /**
     * 根据父菜单id查询菜单列表
     * @return PageWrapper
     */
    public List<Menu> getMenusByParentId(String parentId) {
        return this.findObjectListByPara(Mapper.Menu_Mapper.selectMenusByParentId, parentId);
    }

    /**
     * 根据id删除一个菜单
     */
    public void deleteMenuById(String id) {
        this.delete(Mapper.Menu_Mapper.deleteByPrimaryKey, id);
        actionService.deleteActionByMenuId(id);
    }

    /**
     * 保存菜单
     * @param menu
     */
    public void saveMenu(Menu menu) {
        this.save(Mapper.Menu_Mapper.insertSelective, menu);
    }

    /**
     * 保存菜单和Action
     *
     * @param menu
     */
    @Override
    public void saveMenuAndAction(Menu menu) {

        saveMenu(menu);

        if (Tools.isNotNullStr(menu.getUrl())) {
            Action action = new Action();
            action.setId(ID.randomUUID());
            action.setMenuId(menu.getId());
            action.setType("ALL");
            action.setState("0");
            actionService.saveAction(action);
        }

    }

    /**
     * 修改菜单
     * @param menu
     */
    public void updateMenu(Menu menu) {
        this.update(Mapper.Menu_Mapper.updateByPrimaryKeySelective, menu);

        List<Action> actions = actionService.getActionstByMId(menu.getId());
        if (Tools.isNotNullStr(menu.getUrl()) && (actions == null || actions.size() == 0)) {
            Action action = new Action();
            action.setId(ID.randomUUID());
            action.setMenuId(menu.getId());
            action.setType("ALL");
            action.setState("0");
            actionService.saveAction(action);
        }
    }

    /**
     * 根据主键选择菜单
     * @param id
     * @return
     */
    public Menu getMenuById(String id) {
        return this.findObjectByPara(Mapper.Menu_Mapper.selectByPrimaryKey, id);
    }


    /**
     * 根据父菜单id及sysType查询菜单列表
     * @return PageWrapper
     */
    public List<Menu> getMenusByMap(Map map) {
        return this.findObjectListByMap(Mapper.Menu_Mapper.selectMenusByMap, map);
    }

    /**
     * 加载用户的所属菜单
     *
     * @param sysType
     * @param userId
     * @return
     */
    @Override
    public List<Menu> getMenusByUserID(String sysType, int userId) {
        List<Menu> menulist = new ArrayList<>();

        List<Menu> mLvevl1 = new ArrayList<>();
        List<Menu> mLvevl2 = new ArrayList<>();
        List<Menu> mLvevl3 = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("sysType", sysType);

        List<Menu> menus = this.findObjectListByMap(Mapper.Menu_Mapper.selectMenusByUserID, map);

        // 加载父类菜单 // 只有三级目录
        for (Menu menu : menus) {
            if (Tools.isNotNullStr(menu.getParentId())) {
                Menu m = this.getMenuById(menu.getParentId());
                if (Tools.isNotNullStr(m.getParentId())) {
                    Menu mp = this.getMenuById(m.getParentId());
                    if (!menulist.contains(mp)) {
                        menulist.add(mp);
                    }
                }
                if (!menulist.contains(m)) {
                    menulist.add(m);
                }
            }
            if (!menulist.contains(menu)) {
                menulist.add(menu);
            }
        }

        for (Menu m : menulist) {
            if ("1".equals(m.getLevel())) {
                mLvevl1.add(m);
            }
            if ("2".equals(m.getLevel())) {
                mLvevl2.add(m);
            }
            if ("3".equals(m.getLevel())) {
                mLvevl3.add(m);
            }
        }

        // 排序
        mLvevl1 = mLvevl1.stream().sorted((o1, o2) -> o1.getOrderBy() - o2.getOrderBy()).collect(Collectors.toList());
        mLvevl2 = mLvevl2.stream().sorted((o1, o2) -> o1.getOrderBy() - o2.getOrderBy()).collect(Collectors.toList());


        if (mLvevl3.size() > 0) {
            mLvevl3 = mLvevl3.stream().sorted((o1, o2) -> o1.getOrderBy() - o2.getOrderBy()).collect(Collectors.toList());
            for (Menu m2 : mLvevl2) {
                for (Menu m3 : mLvevl3) {
                    if (m2.getId().equals(m3.getParentId())) {
                        m2.getChildren().add(m3);
                    }
                }
            }
        }

        for (Menu m1 : mLvevl1) {
            for (Menu m2 : mLvevl2) {
                if (m1.getId().equals(m2.getParentId())) {
                    m1.getChildren().add(m2);
                }
            }
        }

        return mLvevl1;
    }
}
