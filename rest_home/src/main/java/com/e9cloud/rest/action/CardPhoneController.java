package com.e9cloud.rest.action;

import com.e9cloud.mybatis.domain.CardPhone;
import com.e9cloud.rest.service.CardPhoneCTLService;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.ReadXmlUtil;
import com.e9cloud.util.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class CardPhoneController {
	private static final Logger logger = LoggerFactory.getLogger(CardPhoneController.class);

	@Value("#{configProperties['rest.version']}")
	private String version;

	@Autowired
	private CardPhoneCTLService cardPhoneCTLService;

	@ModelAttribute("params")
	public Map<String, String> gainRestUrlParas(@PathVariable(value = "SoftVersion") String softVersion,
			@PathVariable(value = "AccountSid") String accountSid, @RequestParam(value = "sig") String sig) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("SoftVersion", softVersion);
		params.put("AccountSid", accountSid);
		params.put("Sig", sig);

		return params;
	}

	@RequestMapping(method = RequestMethod.POST, value = "Numbers/bind", consumes = "application/json")
	public void bindJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		CardPhone cardPhone = new CardPhone();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-bindJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-bindJson报文：" + asBody);
			//进行根元素匹配限制
			String root=asBody.substring(0,asBody.indexOf("{", 1));
			if(root.contains("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				cardPhone = new Gson().fromJson(asBody, CardPhone.class);
				// 填充accountSid
				cardPhone.setAccountSid(accountSid);

				Map<String, String> map = cardPhoneCTLService.bind(cardPhone);
				respCode = map.get("respCode");
				respDesc = map.get("respDesc");
			}else{
				//报文根元素错误
				respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
				respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
			}
			logger.info(respDesc);

		} catch (JsonParseException e){
			//json转对象参数错误
			respCode = ConstantsEnum.REST_PARAM_ERROR.getCode();
			respDesc = ConstantsEnum.REST_PARAM_ERROR.getDesc();
			logger.info(respDesc);
			e.printStackTrace();
		} catch (Exception e) {
			respCode = ConstantsEnum.REST_UNKNOW.getCode();
			respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
			logger.error(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		bindReturn(true, respCode, dateCreated, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Numbers/bind", consumes = "application/xml")
	public void bindXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		CardPhone cardPhone = new CardPhone();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-bindXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-bindXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.contains("req")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String card = mapXML.get("card");
				String phone = mapXML.get("phone");

				// 将对应字段填充到报文对象
				cardPhone.setAppId(appId);
				cardPhone.setCard(card);
				cardPhone.setPhone(phone);

				cardPhone.setAccountSid(accountSid);

				Map<String, String> map = cardPhoneCTLService.bind(cardPhone);
				respCode = map.get("respCode");
				respDesc = map.get("respDesc");
			}else{
				//报文根元素错误
				respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
				respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
			}
			logger.info(respDesc);

		} catch (Exception e) {
			respCode = ConstantsEnum.REST_UNKNOW.getCode();
			respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
			logger.info(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		bindReturn(false, respCode, dateCreated, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	private void bindReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
			HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\"}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated></resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_bind报文：" + respBody);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Numbers/unBind", consumes = "application/json")
	public void unBindJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		CardPhone cardPhone = new CardPhone();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-bindJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-unBindJson报文：" + asBody);
			//进行根元素匹配限制
			String root=asBody.substring(0,asBody.indexOf("{", 1));
			if(root.contains("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				cardPhone = new Gson().fromJson(asBody, CardPhone.class);
				// 填充accountSid
				cardPhone.setAccountSid(accountSid);

				Map<String, String> map = cardPhoneCTLService.unBind(cardPhone);
				respCode = map.get("respCode");
				respDesc = map.get("respDesc");
			}else{
				//报文根元素错误
				respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
				respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
			}
			logger.info(respDesc);

		} catch (JsonParseException e){
			//json转对象参数错误
			respCode = ConstantsEnum.REST_PARAM_ERROR.getCode();
			respDesc = ConstantsEnum.REST_PARAM_ERROR.getDesc();
			logger.info(respDesc);
			e.printStackTrace();
		} catch (Exception e) {
			respCode = ConstantsEnum.REST_UNKNOW.getCode();
			respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
			logger.error(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		unBindReturn(true, respCode, dateCreated, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Numbers/unBind", consumes = "application/xml")
	public void unBindXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		CardPhone cardPhone = new CardPhone();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-unBindXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-unBindXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.contains("req")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String card = mapXML.get("card");
				String phone = mapXML.get("phone");

				// 将对应字段填充到报文对象
				cardPhone.setAppId(appId);
				cardPhone.setCard(card);
				cardPhone.setPhone(phone);

				cardPhone.setAccountSid(accountSid);

				Map<String, String> map = cardPhoneCTLService.unBind(cardPhone);
				respCode = map.get("respCode");
				respDesc = map.get("respDesc");
			}else{
				//报文根元素错误
				respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
				respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
			}
			logger.info(respDesc);

		} catch (Exception e) {
			respCode = ConstantsEnum.REST_UNKNOW.getCode();
			respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
			logger.info(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		unBindReturn(false, respCode, dateCreated, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	private void unBindReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
							HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\"}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated></resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_unBind报文：" + respBody);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Numbers/queryBindInfo", consumes = "application/json")
	public void queryBindInfoJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		CardPhone cardPhone = new CardPhone();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-queryBindInfoJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-queryBindInfoJson报文：" + asBody);
			//进行根元素匹配限制
			String root=asBody.substring(0,asBody.indexOf("{", 1));
			if(root.contains("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				cardPhone = new Gson().fromJson(asBody, CardPhone.class);
				// 填充accountSid
				cardPhone.setAccountSid(accountSid);

				Map<String, String> map = cardPhoneCTLService.queryBindInfo(cardPhone);
				respCode = map.get("respCode");
				respDesc = map.get("respDesc");
			}else{
				//报文根元素错误
				respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
				respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
			}
			logger.info(respDesc);

		} catch (JsonParseException e){
			//json转对象参数错误
			respCode = ConstantsEnum.REST_PARAM_ERROR.getCode();
			respDesc = ConstantsEnum.REST_PARAM_ERROR.getDesc();
			logger.info(respDesc);
			e.printStackTrace();
		} catch (Exception e) {
			respCode = ConstantsEnum.REST_UNKNOW.getCode();
			respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
			logger.error(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		queryBindInfoReturn(true, respCode, dateCreated, respDesc, cardPhone, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Numbers/queryBindInfo", consumes = "application/xml")
	public void queryBindInfoXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		CardPhone cardPhone = new CardPhone();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-unBindXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-unBindXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.contains("req")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String card = mapXML.get("card");
				String phone = mapXML.get("phone");

				// 将对应字段填充到报文对象
				cardPhone.setAppId(appId);
				cardPhone.setCard(card);
				cardPhone.setPhone(phone);

				cardPhone.setAccountSid(accountSid);

				Map<String, String> map = cardPhoneCTLService.queryBindInfo(cardPhone);
				respCode = map.get("respCode");
				respDesc = map.get("respDesc");
			}else{
				//报文根元素错误
				respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
				respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
			}
			logger.info(respDesc);

		} catch (Exception e) {
			respCode = ConstantsEnum.REST_UNKNOW.getCode();
			respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
			logger.info(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		queryBindInfoReturn(false, respCode, dateCreated, respDesc, cardPhone, response);

		model.addAttribute("statusCode", 200);
	}

	private void queryBindInfoReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
							  CardPhone cardPhone, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				//根据手机号集合组装
				if(Utils.notEmpty(cardPhone.getCardPhones())){
					String str="";
					for (CardPhone cardPhoneTmp:cardPhone.getCardPhones()){
						str+="\""+cardPhoneTmp.getPhone()+"\",";
					}
					//去掉最后一个逗号
					str=str.substring(0,str.length()-1);
					respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\"" +
							",\"cardPhones\":["+str+"]}}";
				}else{
					respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\"" +
							",\"cardPhones\":[]}}";
				}
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				//根据手机号集合组装
				if(Utils.notEmpty(cardPhone.getCardPhones())){
					String str="";
					for (CardPhone cardPhoneTmp:cardPhone.getCardPhones()){
						str+=cardPhoneTmp.getPhone()+",";
					}
					//去掉最后一个逗号
					str=str.substring(0,str.length()-1);
					respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
							+ dateCreated + "</dateCreated><cardPhones>"+str+"</cardPhones></resp>";
				}else{
					respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
							+ dateCreated + "</dateCreated><cardPhones></cardPhones></resp>";
				}
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_queryBindInfo报文：" + respBody);
	}

}
