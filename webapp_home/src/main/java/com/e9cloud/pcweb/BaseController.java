package com.e9cloud.pcweb;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.redis.session.JSession;
import com.sun.javafx.binding.ObjectConstant;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 基础的Controller,所有的Controller可以继承此类
 */
// @Controller
public class BaseController extends BaseLogger {

	public String redirect = "redirect:";

	public String forward = "forward:";

	public String has_read = "hasRead";

	@Autowired
	public AppConfig appConfig;

	/**
	 * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
	 */

	// @ModelAttribute("")
	public void init(Model model) {
		// model.addAttribute("now", new Date());
	}

	// 得到session
	public HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	// 公共方法获得user信息
	public void addAttributeToSession(HttpServletRequest request, String key, Object value){
		HttpSession session = getSession(request);
		session.setAttribute(key, value);
	}

	// 公共方法获得user信息
	public Object getAttributeFromSession(HttpServletRequest request, String key){
		HttpSession session = getSession(request);
		return session.getAttribute(key);
	}

	// 公共方法获得user信息
	public void removeAttributeFromSession(HttpServletRequest request, String key){
		HttpSession session = getSession(request);
		session.removeAttribute(key);
	}

	// 公共方法获得user信息
	public Account getCurrUser(HttpServletRequest request){
		Object o = getSession(request).getAttribute(JSession.USER_INFO);
		if (o != null) {
			return (Account)o;
		}
		return null;
	}

	// 公共方法获得user信息
	public String getCurrUserId(HttpServletRequest request){
		Object o = getSession(request).getAttribute(JSession.USER_INFO);
		if (o != null) {
			return ((Account)o).getUid();
		}
		return "";
	}

	// 公共方法获得user信息
	public String getCurrUserSid(HttpServletRequest request){
		Object o = getSession(request).getAttribute(JSession.USER_INFO);
		if (o != null) {
			return ((Account)o).getSid();
		}
		return "";
	}

	// 公共方法获得user信息
	public void updateCurrUser(HttpServletRequest request, Account o){
		getSession(request).setAttribute(JSession.USER_INFO, o);
	}

	/**
	 * 公共下载方法
	 * 
	 * @param response
	 * @param file
	 *            下载的文件
	 * @param fileName
	 *            下载时显示的文件名
	 * @return
	 * @throws Exception
	 */
	public HttpServletResponse downFile(HttpServletResponse response, File file, String fileName,
			boolean delFile) throws Exception {
		response.setContentType("application/x-download");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		OutputStream out = null;
		InputStream in = null;
		// 下面一步不可少
		fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		response.addHeader("Content-disposition", "attachment;filename=" + fileName);// 设定输出文件头

		try {
			out = response.getOutputStream();
			in = new FileInputStream(file);
			int len = in.available();
			byte[] b = new byte[len];
			in.read(b);
			out.write(b);
			out.flush();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("下载失败!");
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (delFile) {
				file.delete();
			}
		}
		return response;
	}
}
