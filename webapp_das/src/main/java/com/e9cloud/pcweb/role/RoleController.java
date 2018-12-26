package com.e9cloud.pcweb.role;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.mybatis.domain.Role;
import com.e9cloud.mybatis.service.ActionService;
import com.e9cloud.mybatis.service.RoleService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理相关
 * Created by wangyu on 2016/9/6.
 */
@Controller
@RequestMapping("/roleMgr")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ActionService actionService;

    /**
     * 跳转到角色管理列表
     * @param model
     * @return
     */
    @RequestMapping("/roleMgrIndex")
    public String roleIndex(Model model){
        logger.info("=====================================RoleController roleIndex Execute=====================================");
        return "role/roleList";
    }

    /**
     * 分页查询角色信息
     * @param page
     * @return
     */
    @RequestMapping("/pageRoleList")
    @ResponseBody
    public PageWrapper pageRoleList(Page page){
        logger.info("=====================================RoleController pageRoleList Execute=====================================");
        return roleService.pageRoleList(page);
    }

    /**
     * 跳转 权限分配页面
     * @param id
     * @return
     */
    @RequestMapping("/toEidtAction")
    public String toEidtAction(Model model, String id){
        logger.info("================= RoleController toEidtAction Execute id:{}=================", id);

        Role role = roleService.findRoleById(id);
        model.addAttribute("role", role);

        return "role/actionEdit";
    }

    /**
     * 跳转到新增角色页面
     * @param model
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole(Model model){
        logger.info("=====================================RoleController addRole Execute=====================================");
        return "role/roleAdd";
    }

    /**
     * 角色修改或查看
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/roleEditView")
    public String roleEditView(HttpServletRequest request, Model model){
        logger.info("=====================================RoleController roleEditView Execute=====================================");
        String id = request.getParameter("id");
        String operationType = request.getParameter("operationType");
        Role role = roleService.findRoleById(id);
        model.addAttribute("operationType",operationType);
        model.addAttribute("role",role);
        return "role/roleEditView";
    }

    /**
     * 修改角色信息
     * @param request
     * @return
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public JSonMessageSubmit updateRole(HttpServletRequest request, Role role){
        logger.info("=====================================RolerController updateRole Execute=====================================");
        Role roleReturn = roleService.findRoleByName(role.getName());
        if(null!=roleReturn){
            if(role.getId().equals(roleReturn.getId())){
                roleService.updateRoleInfo(role);
                return new JSonMessageSubmit("0","信息修改成功！");
            }else{
                return new JSonMessageSubmit("1","角色名称已存在，信息修改失败！");
            }
        }else{
            roleService.updateRoleInfo(role);
            return new JSonMessageSubmit("0","信息修改成功！");
        }
    }

    // 角色分配角色
    @RequestMapping("addAction")
    @ResponseBody
    public JSonMessage addAction(String roleId, String[] aid) {
        roleService.addAction(roleId, aid);
        return new JSonMessage("ok", "");
    }

    /**
     * 删除角色信息
     * @param request
     * @return
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public JSonMessageSubmit deleteRole(HttpServletRequest request){
        logger.info("=====================================RoleController deleteRole Execute=====================================");
        String id = request.getParameter("id");
        Role role = roleService.findRoleById(id);
        if(role != null){
            try{
                roleService.deleteRoleInfo(id);

                return new JSonMessageSubmit("0","信息删除成功！");
            }catch (Exception e){
                return new JSonMessageSubmit("1","信息删除失败！");
            }

        }else{
            return new JSonMessageSubmit("1","该信息已删除！");
        }
    }

    /**
     * 添加角色信息
     * @param request
     * @return
     */
    @RequestMapping("/saveRole")
    @ResponseBody
    public JSonMessageSubmit saveRole(HttpServletRequest request, Role role){
        logger.info("=====================================RoleController saveRole Execute=====================================");
        Role roleReturn = roleService.findRoleByName(role.getName());
        if(null!=roleReturn){
            return new JSonMessageSubmit("1","角色名称已存在，信息保存失败！");
        }
//        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        String uuid = ID.randomUUID();
        role.setId(uuid);
        role.setSysType("D");
        roleService.saveRoleInfo(role);
        return new JSonMessageSubmit("0","信息保存成功！");
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadRoleInfo")
    public ModelAndView downloadRoleInfo(Page page) {
        logger.info("=====================================RoleController downloadRoleInfo Execute=====================================");
        List<Map<String, Object>> totals = roleService.downloadRoleInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> tempMap = new HashMap<String, Object>();
            tempMap.put("rowNO", map.get("rowNO").toString());
//            tempMap.put("rowNO", map.get("rowNO").toString().substring(0,map.get("rowNO").toString().length()-2));
            tempMap.put("name", map.get("name").toString()==null?"":map.get("name").toString());
            if(map.get("type")!=null){
                tempMap.put("type", map.get("type").equals("00")?"系统角色":"部门角色");
            }
            tempMap.put("description", map.get("description")==null?"":map.get("description"));
            list.add(tempMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("角色名称");
        titles.add("角色类型");
        titles.add("角色描述");
        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("name");
        columns.add("type");
        columns.add("description");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "角色信息列表");
        map.put("excelName","角色信息列表");
        return new ModelAndView(new POIXlsView(), map);
    }
}