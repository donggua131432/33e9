package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserRole;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.e9cloud.mybatis.mapper.Mapper.*;

/**
 * Created by Administrator on 2016/1/11.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{

    /**
     * 根据登录名查找用户
     *
     * @param loginName 登录名
     * @return user
     */
    @Override
    public User findAccountByLoginName(String loginName) {
        return this.findObjectByPara(User_Mapper.selectByLoginName, loginName);
    }


    /**
     * 添加用户
     * @param user 用户
     */
    @Override
    public void saveUser(User user) {
        this.save(User_Mapper.insertSelective, user);
    }

    /**
     *删除用户
     * @param id 用户id
     */
    @Override
    public void deleteUser(Integer id) {
        this.delete(User_Mapper.deleteByPrimaryKey, id);
    }

    /**
     * 修改用户
     * @param user 用户
     */
    @Override
    public void updateUser(User user) {
        this.update(User_Mapper.updateByPrimaryKeySelective, user);
    }


    @Override
    public List<User> findlist() {
        return   this.findObjectList(User_Mapper.selectByLoginList, null);
    }

    /**
     * 分页查询用户信息
     *
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageUser(Page page) {
        return this.page(Mapper.User_Mapper.pageUser, page);
    }

    /**
     * 获取用户信息
     * @param user
     * @return
     */
    @Override
    public User getUserInfo(User user) {
        return this.findObject(User_Mapper.getUserInfo, user);
    }

    /**
     * 获取用户信息（包括角色）
     * @param userId
     * @return
     */
    @Override
    public User getUserRoleInfo(Integer userId) {
        return this.findObject(User_Mapper.getUserRoleInfo, userId);
    }

    @Override
    public List<UserRole> findUserRoleByUserId(Integer userId) {
        return   this.findObjectList(User_Mapper.selectUserRoleByUserId, userId);
    }

    /**
     * 校验用户是否存在
     * @param user
     * @return
     */
    @Override
    public long checkUserInfo(User user) {
        return this.findObject(User_Mapper.checkUserInfo, user);
    }

    /**
     * 添加用户角色信息
     * @param userId
     * @param roleId
     */
    @Override
    public void saveUserRole(String userId ,String[] roleId)throws Exception {
        this.deleteUserRole(userId);
        UserRole userRole  = new UserRole();
        userRole.setUserId(Integer.parseInt(userId));
        for(String roleID :roleId){
//            userRole.setId(Integer.parseInt(ID.randomUUID()));
            userRole.setRoleId(roleID);
            this.save(User_Mapper.insertUserRole, userRole);
        }
    }

    /**
     *删除用户角色关联数据
     * @param userId 用户id
     */
    @Override
    public void deleteUserRole(String userId) {
        this.delete(User_Mapper.deleteUserRole, userId);
    }
}
