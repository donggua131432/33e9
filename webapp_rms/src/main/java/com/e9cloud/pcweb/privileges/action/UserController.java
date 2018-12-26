package com.e9cloud.pcweb.privileges.action;


import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.page.datatable.DataTablePage;
import com.e9cloud.core.page.datatable.DataTableWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.core.util.Tools;
import com.e9cloud.core.util.UserUtil;
import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.domain.Role;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserRole;
import com.e9cloud.mybatis.service.IDicDataService;
import com.e9cloud.mybatis.service.RoleService;
import com.e9cloud.mybatis.service.UserService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * user用户操作
 * demo 事例
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private IDicDataService dicDataService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        getInfoAppendToModel(model);
        return "privileges/user";
    }

    private Model getInfoAppendToModel(Model model){
        DicData dicData = new DicData();
        List<DicData> sysTypeDicDatas = dicDataService.findDicListByType("sysType");
        model.addAttribute("sysTypeDic", sysTypeDicDatas);
        return model;
    }

    /**
     * 跳转到添加用户界面
     */
    @RequestMapping(value = "toAddUser", method = RequestMethod.GET)
    public String toAddUser () {
        return "privileges/user_add";
    }


    /**
     * 添加用户
     */
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage addUser(User user) throws Exception {
        logger.info("-------------UserController addUser start-------------");
        String sysType = user.getSysType();
        if(user.getUsername() != null && !"".equals(user.getUsername())){
            logger.info("-------------UserController addUser check user only one-------------");
            User checkUser = userService.findAccountByLoginName(user.getUsername());
            if(checkUser != null){
                return new JSonMessage("no", "该用户名已经存在！", null);
            }
        }
        UserUtil.encryption(user);
        user.setSysType("R");
        user.setStatus("1");
        user.setCreateTime(new Date());
        userService.saveUser(user);
        logger.info("-------------UserController addUser success------------");
        return new JSonMessage("ok", "数据添加成功！", null);
    }

    /**
     * 分页查询用户信息
     */
    @RequestMapping(value = "pageUser", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageUser(Page page) {
        return userService.pageUser(page);
    }

    /**
     * 分页查询用户信息
     */
    @RequestMapping(value = "pageUser1", method = RequestMethod.POST)
    @ResponseBody
    public DataTableWrapper pageUser(DataTablePage page) {

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setNick("wzj" + i);
            users.add(user);
        }
        
        DataTableWrapper dataTableWrapper = new DataTableWrapper();

        dataTableWrapper.setAaData(users);
        dataTableWrapper.setRecordsTotal(10l);
        dataTableWrapper.setRecordsFiltered(10l);
        dataTableWrapper.setDraw(1);

        return dataTableWrapper;
    }

    @RequestMapping(value = "getUsersave", method = RequestMethod.POST)
    public String printsave(ModelMap model, User user, String redirect_url) {
        String error = null;
        if (user != null) {
            userService.saveUser(user);
            error = "保存成功";
            model.addAttribute("error", error);
            return "/index";
        }
        error = "保存失败";
        model.addAttribute("error", error);
        return "/index";
    }

    /**
     * 修改用户信息
     */
    @RequestMapping(value = "getUserupdate", method = RequestMethod.POST)
    public String prinUpdate(ModelMap model, User user, String redirect_url) {
        if (user != null) {
            userService.updateUser(user);
            return "/index";
        }
        return "/update";
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "getUserdelete", method = RequestMethod.POST)
    public String prindelete(ModelMap model, User user) {
        String error = null;
        if (user != null) {
            userService.deleteUser(user.getId());
            error = "删除成功";
            model.addAttribute("error", error);
            return "/index";
        }
        error = "删除失败";
        model.addAttribute("error", error);
        return "/delete";
    }

    /**
     * 查询用户信息
     */
    @RequestMapping(value = "getUserselete", method = RequestMethod.POST)
    public String prinselete(User user, HttpServletRequest request) {
        if (user != null) {
            User usr = userService.findAccountByLoginName(user.getUsername());
            request.getSession().setAttribute("user", usr);
            return "/index";
        }
        return "/Slete";
    }

    /**
     * 解禁与禁用
     */
    @RequestMapping(value = "getUserstate", method = RequestMethod.POST)
    public String prinstate(User user) {
        if (user!=null) {
            if (Integer.valueOf(user.getStatus()) != 1 && Integer.valueOf(user.getStatus()) != 0)
            {
            } else  userService.updateUser(user);
        }
        return "/prinstates";
    }


    @RequestMapping("toUpdateUserInfo")
    public String toUpdateUserInfo(User user, Model model){
        logger.info("-------------UserController toUpdateUserInfo start-------------");
        //获取用户信息
        User userInfo = userService.getUserInfo(user);
        model.addAttribute("userInfo", userInfo);
        return "privileges/user_update";
    }


    /**
     * 获取用户信息
     * @param model
     * @return
     */
    @RequestMapping("getUserInfo")
    public String getUserInfo(Model model){
        logger.info("-------------UserController getUserInfo start-------------");
        //获取当前用户信息
        User user = UserUtil.getCurrentUser();
        //获取用户信息（包括角色）
        User userInfo = userService.getUserRoleInfo(user.getId());
        model.addAttribute("userInfo", userInfo);
        return "user/userInfo";
    }

    @RequestMapping("checkUserInfo")
    @ResponseBody
    public Map<String,String> checkUserInfo(HttpServletRequest rquest, User user){
        logger.info("-------------UserController getUserInfo start-------------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");
        //获取当前用户信息
        if(user.getPassword() != null){
            User currentUser = UserUtil.getCurrentUser();
            boolean checkStatus = UserUtil.checkpwd(currentUser,user.getPassword());
            if(checkStatus == true){
                map.put("status","0");
                map.put("info","原始密码正确！");
            }else{
                map.put("info","原始密码错误！");
            }
        }else if (user.getPhone() != null){
            User userInfo = userService.getUserInfo(user);
            if(userInfo != null){
                map.put("info","手机号已经存在！");
            }else{
                map.put("status","0");
                map.put("info","该手机号可以使用！");
            }
        }else if(user.getUsername() != null){
            long l = userService.checkUserInfo(user);
            if(l == 0){
                map.put("status","0");
                map.put("info","该用户名可以使用！");
            }else{
                map.put("info","该用名已经存在！");
            }
        }
        return map;
    }


    /**
     * 修改用户信息
     */
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateUserInfo(User user) throws Exception{
        logger.info("-------------UserController updateUserInfo start-------------");
        Map<String, String> map = new HashMap<String,String>();
        String sysType = user.getSysType();
        map.put("status","1");
        if(user.getId() != null){
            if("".equals(user.getPassword())){
                user.setPassword(null);
            }
            if(user.getPassword() != null){
               UserUtil.encryption(user);
            }

            //判断用户名是否已经存在
            if(Tools.isNotNullStr(user.getUsername())){
                logger.info("-------------UserController updateUserInfo check username only one-------------");
                long l = userService.checkUserInfo(user);
                if(l > 0){
                    map.put("info","该用户名已经存在！");
                    return map;
                }
            }

            //修改用户信息
            userService.updateUser(user);
            User updateUser = userService.getUserInfo(user);
            if (UserUtil.getCurrentUserId() == updateUser.getId()) { // 修改自己的信息时会重新加载信息
                UserUtil.reloadUser(updateUser);
            }

            logger.info("-------------UserController updateUserInfo success-------------");
            map.put("status","0");
            map.put("info","更新数据信息成功！");
            String mobile = user.getPhone();
            //修改手机号码或修改密码   只有两种情况
            if (!"".equals(mobile) && mobile!= null) {
                LogUtil.log("修改个人资料", "手机号码修改成功，修改后手机号为：" + mobile, LogType.UPDATE);
            }else{
                LogUtil.log("修改个人资料", "密码修改成功", LogType.UPDATE);
            }
            return map;
        }else{
            map.put("info","更新数据信息失败！");
            return map;
        }
    }


    @RequestMapping("selectRoleToUser")
    public String selectRoleToUser(User user, Model model){
        logger.info("-------------UserController toUpdateUserInfo start-------------");
        //获取用户信息
        User userInfo = userService.getUserInfo(user);
        List<Role> roles = roleService.findAllRole();
        List<UserRole> userRoleList = userService.findUserRoleByUserId(userInfo.getId());
        model.addAttribute("roles",roles);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("userRoleList",userRoleList);
        return "privileges/role_to_user";
    }
    /**
     * 保存用户和角色关系数据
     */
    @RequestMapping(value = "saveUserRole", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage saveUserRole(String userId ,String[] roleId)  {
        logger.info("-------------UserController saveUserRole start-------------");
        try {
            if(Tools.isNotNullArray(roleId)){
                userService.saveUserRole(userId,roleId);
                return new JSonMessage("0", "分配角色成功！");
            }else {
                return new JSonMessage("1", "请选择角色！");
            }

        } catch (Exception e) {
            return new JSonMessage("1", "分配角色失败！");
        }
    }
}