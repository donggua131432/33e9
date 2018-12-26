package com.e9cloud.pcweb.privileges.biz;

import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.mybatis.domain.Menu;
import com.e9cloud.mybatis.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限管理biz层
 * Created by Administrator on 2016/2/18.
 */
@Service
public class PrivilegeService extends BaseLogger {

    @Autowired
    private MenuService menuService;

    /**
     * 编辑菜单时，加载前端相关数据
     * @param id
     */
    public void loadDataForEditMenu(Model model, String id, String sysType) {
        Map<String, String> map = new HashMap<>();
        map.put("parentId", null);
        map.put("sysType", sysType);
        List<Menu> menus = menuService.getMenusByMap(map);

        Menu menu = menuService.getMenuById(id);

        if ("2".equals(menu.getLevel())) {
            model.addAttribute("level1", menu.getParentId());
        }

        if ("3".equals(menu.getLevel())){
            Menu parentMenu = menuService.getMenuById(menu.getParentId());
            model.addAttribute("level1", parentMenu.getParentId());
            model.addAttribute("level2", parentMenu.getId());
            List<Menu> menus2 = menuService.getMenusByParentId(parentMenu.getParentId());
            model.addAttribute("menus2", menus2);
        }

        model.addAttribute("menus", menus);
        model.addAttribute("menu", menu);
    }
}
