package com.e9cloud.rest.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.e9cloud.cache.CacheManager;

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/reset")
public class ResetTenloController {
	public void reset(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CacheManager.reload();
	}

}
