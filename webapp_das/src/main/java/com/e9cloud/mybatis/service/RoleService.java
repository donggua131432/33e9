package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * Created by wy on 2016/7/8.
 */
public interface RoleService extends IBaseService {

    /**
     * 根据ID查找角色信息
     * @param id
     * @return RoleManager
     */
    Role findRoleById(String id);
    /**
     * 根据名称查找角色信息
     * @param cname
     * @return RoleManager
     */
    Role findRoleByName(String cname);

    /**
     * 保存角色信息
     * @param Role 角色信息
     */
    void saveRoleInfo(Role Role);
    /**
     * 修改角色信息
     */
    void updateRoleInfo(Role Role);

    /**
     * 删除角色信息
     */
    void deleteRoleInfo(String id);

    /**
     * 分页查询角色信息
     * @param page
     * @return
     */
    PageWrapper pageRoleList(Page page);

    /**
     * 导出角色信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadRoleInfo(Page page);

    /**
     * 查找所有角色信息
     * @return RoleManager
     */
    List<Role> findAllRole();

    /**
     * 修改角色权限
     * @param roleId
     * @param aid
     */
    void addAction(String roleId, String[] aid);

    /**
     * 根据roleid清理，角色-权限 关系
     * @param roleId 角色id
     */
    void deleteRoleActionByRoleId(String roleId);

    /**
     * 根据roleid清理，用户-角色 关系
     * @param roleId 角色id
     */
    void deleteUserRoleByRoleId(String roleId);
}
