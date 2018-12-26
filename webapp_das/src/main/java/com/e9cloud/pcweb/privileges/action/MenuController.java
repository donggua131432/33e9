package com.e9cloud.pcweb.privileges.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Menu;
import com.e9cloud.mybatis.service.MenuService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.privileges.biz.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * Created by Administrator on 2016/2/15.
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 返回菜单管理页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model){
        logger.info("-------------to menu index-------------");
        model.addAttribute("sysType",appConfig.getSysType());
        return "privileges/meun";
    }

    /**
     * 分页查询菜单列表
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper page(String sysType){
        logger.info("-------------to menu index-------------");
        return menuService.pageMenu(sysType);
    }

    /**
     * 返回菜单管理页面
     */
    @RequestMapping(value = "toAddMenu", method = RequestMethod.GET)
    public String toAddMenu(Model model, String sysType){
        logger.info("-------------toAddMenu-------------");
        Map<String, String> map = new HashMap<>();
        map.put("parentId",null);
        map.put("sysType",sysType);
        List<Menu> menus = menuService.getMenusByMap(map);
        model.addAttribute("menus", menus);
        model.addAttribute("sysType",sysType);
        return "privileges/meun_add";
    }

    /**
     * 添加菜单
     */
    @RequestMapping(value = "addMenu", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage addMenu(Menu menu, String level1, String level2, String sysType) {

        logger.info("----------{}----------", "addMenu");

        menu.setId(ID.randomUUID());
        menu.setCreateDate(new Date());
        menu.setUpdateDate(new Date());
        menu.setSysType(sysType);

        if (Tools.isNotNullStr(level2)) { // 二级菜单不为空时，添加的为三级菜单
            menu.setLevel("3");
            menu.setParentId(level2);
        } else if (Tools.isNotNullStr(level1)) { // 一级菜单不为空时，添加的为二级菜单
            menu.setLevel("2");
            menu.setParentId(level1);
        } else { // 一级和二级都为空是，添加的为三级菜单
            menu.setLevel("1");
        }

        menuService.saveMenuAndAction(menu);

        return new JSonMessage("ok", "");
    }

    /**
     * 返回菜单管理页面
     */
    @RequestMapping(value = "toEditMenu", method = RequestMethod.GET)
    public String toEditMenu(Model model, String id, String sysType){
        logger.info("-------------toEditMenu start-------------");

        privilegeService.loadDataForEditMenu(model, id, sysType);

        logger.info("-------------toEditMenu end-------------");

        return "privileges/meun_edit";
    }

    /**
     * 添加菜单
     */
    @RequestMapping(value = "editMenu", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage editMenu(Menu menu, String level1, String level2) {

        logger.info("----------{}----------", "addMenu");

        menu.setUpdateDate(new Date());

        if (Tools.isNotNullStr(level2)) { // 二级菜单不为空时，添加的为三级菜单
            menu.setLevel("3");
            menu.setParentId(level2);
        } else if (Tools.isNotNullStr(level1)) { // 一级菜单不为空时，添加的为二级菜单
            menu.setLevel("2");
            menu.setParentId(level1);
        } else { // 一级和二级都为空是，添加的为三级菜单
            menu.setLevel("1");
        }

        menuService.updateMenu(menu);

        return new JSonMessage("ok", "");
    }

    /**
     * 修改菜单状态
     */
    @RequestMapping(value = "updateMenuState", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateMenuState(Menu menu) {

        menuService.updateMenu(menu);

        return new JSonMessage("ok", "");
    }

    /**
     * 根据parentId查询子菜单
     */
    @RequestMapping(value = "getMenusByParentId", method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> getMenusByParentId(String parentId){
        logger.info("-------------getMenusByParentId-------------");

        return menuService.getMenusByParentId(parentId);
    }

    /** 删除一个菜单 */
    @RequestMapping(value = "deleteMenu", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage deleteMenu(String id) {
        List<Menu> menus = menuService.getMenusByParentId(id);
        if (menus.size() > 0) {
            return new JSonMessage("hasChildren", "");
        }
        menuService.deleteMenuById(id);

        return new JSonMessage("ok", "");
    }

    //////////////////////////////////////////// dasMenu start //////////////////////////////////////////////
    /**
     * 返回菜单管理页面
     */
    @RequestMapping(value = "das",method = RequestMethod.GET)
    public String dasIndex(Model model){
        logger.info("-------------to menu index-------------");
        model.addAttribute("sysType","D");
        return "privileges/meun";
    }

    //////////////////////////////////////////// dasMenu end //////////////////////////////////////////////
}
