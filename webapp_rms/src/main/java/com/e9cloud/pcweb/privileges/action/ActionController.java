package com.e9cloud.pcweb.privileges.action;

import com.e9cloud.core.common.Tree;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.service.ActionService;
import com.e9cloud.mybatis.service.MenuService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 菜单权限管理
 * Created by Administrator on 2016/9/6.
 */
@Controller
@RequestMapping("/action")
public class ActionController extends BaseController {

    @Autowired
    private ActionService actionService;

    // 菜单权限页面
    @RequestMapping("index")
    public String index() {
        return "privileges/action";
    }

    // 分页查询action
    @RequestMapping("pageAction")
    @ResponseBody
    public PageWrapper pageAction(Page page){
        return actionService.pageAction(page);
    }

    // 菜单权限--新增页面
    @RequestMapping("toAddAction")
    public String toAddAction() {
        return "privileges/action_add";
    }

    // 菜单权限--编辑页面
    @RequestMapping("toEditAction")
    public String toEditAction() {
        return "privileges/action_edit";
    }

    // 菜单权限--树 tree
    @RequestMapping("atree")
    @ResponseBody
    public List<Tree> atree(String roleId) {
        return actionService.getATree(roleId);
    }

}
