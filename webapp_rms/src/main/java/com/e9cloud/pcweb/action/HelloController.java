package com.e9cloud.pcweb.action;

import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.UserUtil;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.MenuService;
import com.e9cloud.pcweb.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * demo 事例
 */
@Controller
@RequestMapping("/")
public class HelloController extends BaseController{

    @Autowired
    private AccountService accountService ;

	@Autowired
	private MenuService menuService;


    @RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		HttpServletRequest request = (HttpServletRequest) ((WebSubject) SecurityUtils.getSubject()).getServletRequest();

		User user = UserUtil.getCurrentUser();
		if (user != null && "admin".equals(user.getUsername())) {
			request.getSession().setAttribute("menus", menuService.listAll(appConfig.getSysType()));
		} else {
			request.getSession().setAttribute("menus", menuService.getMenusByUserID(appConfig.getSysType(), UserUtil.getCurrentUserId()));
		}

		return "index";
	}

	@RequestMapping("main")
	public String forMain(){
		return "main";
	}

	@RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public JSonMessage getUserInfo(String username) {
		logger.info(username);
		return new JSonMessage("001","正确");
	}
}