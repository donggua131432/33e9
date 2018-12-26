package com.e9cloud.mybatis.service.impl;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.Role;
import com.e9cloud.mybatis.domain.RoleAction;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
    /**
     * 根据ID查找角色信息
     * @param id
     * @return
     */
    @Override
    public Role findRoleById(String id) {
        return this.findObject(Mapper.Role_Mapper.selectRoleById, id);
    }
    /**
     * 根据名称查找角色信息
     * @param name
     * @return
     */
    @Override
    public Role findRoleByName(String name) {
        return this.findObject(Mapper.Role_Mapper.selectRoleByName, name);
    }

    /**
     * 保存角色信息
     * @param Role 角色信息
     */
    @Override
    public void saveRoleInfo(Role Role) {
        this.save(Mapper.Role_Mapper.saveRole, Role);
    }

    /**
     * 修改角色信息
     * @param Role
     */
    @Override
    public void updateRoleInfo(Role Role) {
        this.update(Mapper.Role_Mapper.updateRoleById, Role);
    }

    /**
     * 删除角色信息
     * @param id
     */
    @Override
    public void deleteRoleInfo(String id) {
        this.deleteRoleActionByRoleId(id);
        this.deleteUserRoleByRoleId(id);
        this.delete(Mapper.Role_Mapper.deleteRoleById, id);
    }

    /**
     * 分页查询角色信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageRoleList(Page page) {
        return this.page(Mapper.Role_Mapper.pageRoleList, page);
    }

    /**
     * 导出角色信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadRoleInfo(Page page) {
        return this.download(Mapper.Role_Mapper.pageRoleList, page);
    }
    /**
     * 查找所有角色信息
     * @return
     */
    @Override
    public List<Role> findAllRole() {
        return this.findObjectList(Mapper.Role_Mapper.allRoleList, null);
    }

    /**
     * 修改角色权限
     *
     * @param roleId
     * @param aids
     */
    @Override
    public void addAction(String roleId, String[] aids) {

        // 清理以前的角色-权限关系
        deleteRoleActionByRoleId(roleId);

        // 插入新的角色-权限关系
        RoleAction roleAction = new RoleAction();
        for (String aid : aids) {
            roleAction.setActionId(aid);
            roleAction.setRoleId(roleId);
            this.save(Mapper.RoleAction_Mapper.insertSelective, roleAction);
        }
    }

    /**
     * 根据roleid清理，角色-权限 关系
     * @param roleId 角色id
     */
    @Override
    public void deleteRoleActionByRoleId(String roleId) {
        this.delete(Mapper.RoleAction_Mapper.deleteByRoleId, roleId);
    }


    /**
     * 根据roleid清理，用户-角色 关系
     * @param roleId 角色id
     */
    @Override
    public void deleteUserRoleByRoleId(String roleId) {
        this.delete(Mapper.UserRole_Mapper.deleteByRoleId, roleId);
    }
}
