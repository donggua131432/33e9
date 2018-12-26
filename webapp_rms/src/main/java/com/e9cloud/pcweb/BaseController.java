package com.e9cloud.pcweb;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.common.BaseLogger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BaseController implements BaseLogger {

	public String redirect = "redirect:";

	public String forward = "forward:";

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

	/**
	 * 变量保存到 session 中
	 * @param request HttpServletRequest
	 * @param attrName 属性的名称
	 * @param attrValue 属性的值
     */
	public void addAttributeToSession(HttpServletRequest request, String attrName, Object attrValue) {
		getSession(request).setAttribute(attrName, attrValue);
	}

	/**
	 * 变量保存到 session 中
	 * @param request HttpServletRequest
	 * @param attrName 属性的名称
	 */
	public Object getAttributeFromSession(HttpServletRequest request, String attrName) {
		return getSession(request).getAttribute(attrName);
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
