package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserRole;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public interface UserService extends IBaseService{

    /**
     * 根据登录名查找用户
     * @param loginName 登录名
     * @return user
     */
    User findAccountByLoginName(String loginName);


    /**
     * 保存用户信息
     * @param user 登录名
     */
    void saveUser(User user);

    /**
     * 删减用户
     */
     void deleteUser(Integer id);


    /**
     * 修改用户信息
     */
    void updateUser(User user);

    /**
     * 查询所有
     */
    List<User> findlist();

    /**
     * 分页查询用户信息
     * @param page 分页信息
     * @return
     */
    PageWrapper pageUser(Page page);

    /**
     * 获取用户信息
     * @param user
     * @return
     */
    User getUserInfo(User user);

    /**
     * 获取用户信息（包括角色）
     * @param userId
     * @return
     */
    User getUserRoleInfo(Integer userId);

    /**
     * 校验用户是否存在
     * @param user
     * @return
     */
    long checkUserInfo(User user);

    /**
     * 保存用户角色信息
     * @param id
     * @param roleId
     */
    void saveUserRole(String id ,String[] roleId) throws Exception;

    /**
     *删除用户角色关联数据
     * @param userId 用户id
     */
     void deleteUserRole(String userId);
    /**
     *根据用户ID查找用户角色关联数据
     * @param userId 用户id
     */
    List<UserRole> findUserRoleByUserId(Integer userId);
}
