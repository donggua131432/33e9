package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wujiang on 2016/2/18.
 */
public interface UserAdminService extends IBaseService{

    /**
     * 根据充值账号（sid）查询用户
     * @param SID 充值账号
     * @return userAdmin
     */
    public UserAdmin findUserAdminSID(String SID);

    UserAdmin findUserAdminByUid(String uid);

    /**
     * 根据充值账号（sid）查询用户,更新fee
     * @param userAdmin
     */
    public void updateUserAdminWithFee(UserAdmin userAdmin);

    /**
     * 分页查询用户余额列表
     * @param page
     * @return
     */
    PageWrapper selectBalanceListPage(Page page);


    /**
     * 根据参数查询用户管理主账号信息
     * @param userAdmin
     * @return
     */
    public UserAdmin getUserAdmin(UserAdmin userAdmin);

    public UserAdmin getUserAdminWithCompany(UserAdmin userAdmin);

    /**
     * 下载用户的余额列表信息
     * @param page 分页信息
     * @return
     */
    public List<UserAdmin> selectBalanceLisDownload(Page page);

    /**
     * 保存用户信息
     * @param userAdmin
     */
    public void saveUserAdmin(UserAdmin userAdmin);

    /**
     * 根据邮箱或者手机号统计用户数量
     * @param userAdmin 只能有手机号或邮箱
     * @return
     */
    long countUserAdminByEmailOrMobile(UserAdmin userAdmin);

    /**
     * 保存用户扩展
     * @param userExternInfo
     */
    public void saveUserExtern(UserExternInfo userExternInfo);

    /**
     * 根据uid查询扩展信息
     * @param uid
     * @return
     */
    UserExternInfo getUserExternInfoByUid(String uid);

    /**
     * 得到公司名和sid下拉列表
     * @param page
     * @return
     */
    PageWrapper getCompanyNameAndSidByPage(Page page);

    /**
     * 更改用户的认证状态
     * @param userAdmin
     * @return
     */
    public void updateAuthStatus(UserAdmin userAdmin);

    /**
     * 分页查询开发者列表
     * @return
     */
    PageWrapper pageUserAdminList(Page page);

    List<Map<String, Object>> getPageUserAdminList(Page page);

    /**
     * 修改用户的状态
     * @param userAdmin
     */
    void updateUserAdminStatusByUid(UserAdmin userAdmin);

    /**
     * 分页查询用户历史余额列表
     * @param page
     * @return
     */
    PageWrapper selectBalanceHistoryListPage(Page page);

    /**
     * 查询用户历史余额列表
     * @param page
     * @return
     */
    List<UserAdminHistory> selectBalanceHistoryLisDownload(Page page);

    /**
     * 查询所有的用户
     * @return
     */
    List<UserAdmin> findAllUserAdmin();

    /**
     * 查询所有的用户 和 用户类型
     * @return
     */
    List<UserAdmin> findAllUserAdminAndBusTypes();
}
