package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.domain.UserAdminHistory;
import com.e9cloud.mybatis.domain.UserExternInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.UserAdminService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.e9cloud.mybatis.mapper.Mapper.UserAdmin_Mapper;

/**
 * Created by Administrator on 2016/1/11.
 */
@Service
public class UserAdminServiceImpl extends BaseServiceImpl implements UserAdminService {

    @Override
    public UserAdmin findUserAdminSID(String SID) {
        return this.findObjectByPara(UserAdmin_Mapper.selectBySID, SID);
    }

    @Override
    public UserAdmin findUserAdminByUid(String uid){
        return this.findObjectByPara(UserAdmin_Mapper.selectByPrimaryKey, uid);
    }

    @Override
    public void updateUserAdminWithFee(UserAdmin userAdmin) {
        this.update(UserAdmin_Mapper.updateByPrimaryKeySelective, userAdmin);
    }

    @Override
    public PageWrapper selectBalanceListPage(Page page) {
        return this.page(UserAdmin_Mapper.selectBalanceListPage, page);
    }

    @Override
    public List<UserAdmin> selectBalanceLisDownload(Page page) {
        return this.findObjectList(UserAdmin_Mapper.selectBalanceListDownload, page);
    }

    /**
     * 根据参数查询用户管理主账号信息
     * @param userAdmin
     * @return
     */
    @Override
    public UserAdmin getUserAdmin(UserAdmin userAdmin) {
        return this.findObject(Mapper.UserAdmin_Mapper.selectUserAdminByObject, userAdmin);
    }

    @Override
    public UserAdmin getUserAdminWithCompany(UserAdmin userAdmin) {
        return this.findObject(Mapper.UserAdmin_Mapper.selectUserAdminUnionCompanyInfo, userAdmin);
    }

    /**
     * 保存用户信息
     *
     * @param userAdmin
     */
    @Override
    public void saveUserAdmin(UserAdmin userAdmin) {
        this.save(Mapper.UserAdmin_Mapper.insertSelective, userAdmin);
    }

    /**
     * 根据邮箱或者手机号统计用户数量
     *
     * @param userAdmin 只能有手机号或邮箱
     * @return
     */
    @Override
    public long countUserAdminByEmailOrMobile(UserAdmin userAdmin) {
        return this.findObject(Mapper.UserAdmin_Mapper.countUserAdminByEmailOrMobile, userAdmin);
    }

    /**
     * 得到公司名和sid下拉列表
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper getCompanyNameAndSidByPage(Page page) {
        return this.page(Mapper.UserAdmin_Mapper.findCompanyNameAndSidByPage, page);
    }

    /**
     * 得到公司名和sid下拉列表(Select2)
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper getCompanyNameAndSidForSelect2(Page page) {
        return this.page(Mapper.UserAdmin_Mapper.findCompanyNameAndSidForSelect2, page);
    }

    @Override
    public void updateAuthStatus(UserAdmin userAdmin) {
        this.update(Mapper.UserAdmin_Mapper.updateByPrimaryKeySelective, userAdmin);
    }

    /**
     * 分页查询开发者列表
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageUserAdminList(Page page) {
        return this.page(Mapper.UserAdmin_Mapper.pageUserAdminList, page);
    }

    @Override
    public List<Map<String, Object>> getPageUserAdminList(Page page) {
        return this.findObjectList(Mapper.UserAdmin_Mapper.getPageUserAdminList, page);
    }
    /**
     * 修改用户的状态
     *
     * @param userAdmin
     */
    @Override
    public void updateUserAdminStatusByUid(UserAdmin userAdmin) {
        userAdmin.setUpdateDate(new Date());
        this.update(Mapper.UserAdmin_Mapper.updateUserAdminStatusByUid, userAdmin);
    }

    /**
     * 查询所有的用户
     *
     * @return
     */
    @Override
    public List<UserAdmin> findAllUserAdmin() {
        return this.findObjectList(Mapper.UserAdmin_Mapper.selectAll, null);
    }

    @Override
    public List<UserAdmin> getUserAdminWithCompanyList(UserAdmin userAdmin) {
        return this.findObjectList(Mapper.UserAdmin_Mapper.selectUserAdminUnionCompanyInfo, userAdmin);
    }
}
