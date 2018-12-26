package com.e9cloud.rest.action;

import com.e9cloud.core.support.SysPropertits;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.AppNumberRestService;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.obt.MaskNumber;
import com.e9cloud.rest.service.MaskNumService;
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
 * Created by Administrator on 2016/6/1.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class MaskNumberController {
	private static final Logger logger = LoggerFactory.getLogger(MaskNumberController.class);

	@Value("#{configProperties['rest.version']}")
	private String version;

	@Autowired
	private SysPropertits sysPropertits;
	@Autowired
	private MaskNumService maskNumService;
	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AppNumberRestService appNumberRestService;
	@Autowired
	private JedisClusterUtil jedisClusterUtil;

	@ModelAttribute("params")
	public Map<String, String> gainRestUrlParas(@PathVariable(value = "SoftVersion") String softVersion,
			@PathVariable(value = "AccountSid") String accountSid, @RequestParam(value = "sig") String sig) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("SoftVersion", softVersion);
		params.put("AccountSid", accountSid);
		params.put("Sig", sig);

		return params;
	}

	@RequestMapping(method = RequestMethod.POST, value = "MaskNumber/apply", consumes = "application/json")
	public void applyJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		MaskNumber maskNumber = new MaskNumber();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-applyJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-applyJson报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				maskNumber = new Gson().fromJson(asBody, MaskNumber.class);
				// 填充accountSid
				maskNumber.setAccountSid(accountSid);

				Map<String, String> map = maskNumService.apply(maskNumber);
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
		applyReturn(true, respCode, dateCreated, respDesc, maskNumber, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "MaskNumber/apply", consumes = "application/xml")
	public void applyXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		MaskNumber maskNumber = new MaskNumber();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-applyXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-applyXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")||root.equals("MaskNumber")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String realNumA = mapXML.get("realNumA");
				String realNumB = mapXML.get("realNumB");
				int needRecord = Utils.strToInteger(mapXML.get("needRecord"));
				int validTime = Utils.strToInteger(mapXML.get("validTime"));
				// 针对整形 不为空，格式不符限制转换，避免默认0，而呼叫成功
				if (Utils.notEmpty(mapXML.get("needRecord")) && !Utils.isDirectionDigit(mapXML.get("needRecord"))) {
					needRecord = -5;
				}
				if (Utils.notEmpty(mapXML.get("validTime")) && !Utils.isDirectionDigit(mapXML.get("validTime"))) {
					validTime = -5;
				}
				String areaCode = mapXML.get("areaCode");

				// 将对应字段填充到报文对象
				maskNumber.setAppId(appId);
				maskNumber.setRealNumA(realNumA);
				maskNumber.setRealNumB(realNumB);
				maskNumber.setNeedRecord(needRecord);
				//避免validTime不传，而进行上述业务转型处理 默认0操作，导致redis有效时间插入异常
				if(Utils.notEmpty(mapXML.get("validTime"))){
					maskNumber.setValidTime(validTime);
				}
				maskNumber.setAreaCode(areaCode);
				// 填充accountSid
				maskNumber.setAccountSid(accountSid);

				Map<String, String> map = maskNumService.apply(maskNumber);
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
		applyReturn(false, respCode, dateCreated, respDesc, maskNumber, response);

		model.addAttribute("statusCode", 200);
	}

	private void applyReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
			MaskNumber maskNumber, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\","
						+ maskNumber.toJson() + "}}";
			} else if (ConstantsEnum.REST_MASKNUMREF_EXIST.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\","
						+ "\"represent\":\""+respDesc+"\"," + maskNumber.toJson() + "}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated>" + maskNumber.toXML() + "</resp>";
			} else if (ConstantsEnum.REST_MASKNUMREF_EXIST.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent>" + maskNumber.toXML() + "</resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_apply报文：" + respBody);
	}

	@RequestMapping(method = RequestMethod.POST, value = "MaskNumber/release", consumes = "application/json")
	public void releaseJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		MaskNumber maskNumber = new MaskNumber();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-releaseJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-releaseJson报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				maskNumber = new Gson().fromJson(asBody, MaskNumber.class);
				// 填充accountSid
				maskNumber.setAccountSid(accountSid);

				Map<String, String> map = maskNumService.release(maskNumber);
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
			logger.info(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		releaseReturn(true, respCode, dateCreated, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "MaskNumber/release", consumes = "application/xml")
	public void releaseXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		MaskNumber maskNumber = new MaskNumber();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-releaseXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-releaseXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")||root.equals("MaskNumber")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String realNumA = mapXML.get("realNumA");
				String realNumB = mapXML.get("realNumB");

				// 将对应字段填充到报文对象
				maskNumber.setAppId(appId);
				maskNumber.setRealNumA(realNumA);
				maskNumber.setRealNumB(realNumB);
				// 填充accountSid
				maskNumber.setAccountSid(accountSid);

				Map<String, String> map = maskNumService.release(maskNumber);
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
		releaseReturn(false, respCode, dateCreated, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	private void releaseReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
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
		logger.info("返回AS_release报文：" + respBody);
	}

	@RequestMapping(method = RequestMethod.POST, value = "MaskNumber/query", consumes = "application/json")
	public void queryJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		MaskNumber maskNumber = new MaskNumber();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-queryJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-queryJson报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				maskNumber = new Gson().fromJson(asBody, MaskNumber.class);
				// 填充accountSid
				maskNumber.setAccountSid(accountSid);

				Map<String, String> map = maskNumService.query(maskNumber);
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
			logger.info(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		queryReturn(true, respCode, dateCreated, respDesc, maskNumber, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "MaskNumber/query", consumes = "application/xml")
	public void queryXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		MaskNumber maskNumber = new MaskNumber();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-queryXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-queryXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")||root.equals("MaskNumber")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String realNumA = mapXML.get("realNumA");
				String realNumB = mapXML.get("realNumB");

				// 将对应字段填充到报文对象
				maskNumber.setAppId(appId);
				maskNumber.setRealNumA(realNumA);
				maskNumber.setRealNumB(realNumB);
				// 填充accountSid
				maskNumber.setAccountSid(accountSid);

				Map<String, String> map = maskNumService.query(maskNumber);
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
		queryReturn(false, respCode, dateCreated, respDesc, maskNumber, response);

		model.addAttribute("statusCode", 200);
	}

	private void queryReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
			MaskNumber maskNumber, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\","
						+ maskNumber.toJson() + "}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated>" + maskNumber.toXML() + "</resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_query报文：" + respBody);
	}

	@RequestMapping(method = RequestMethod.POST, value = "MaskNumber/queryAll", consumes = "application/json")
	public void queryAllJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		MaskNumber maskNumber = new MaskNumber();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-queryAllJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-queryAllJson报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				maskNumber = new Gson().fromJson(asBody, MaskNumber.class);
				// 填充accountSid
				maskNumber.setAccountSid(accountSid);

				Map<String, String> map = maskNumService.queryAll(maskNumber);
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
			logger.info(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文
		queryAllReturn(true, respCode, dateCreated, respDesc, maskNumber, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "MaskNumber/queryAll", consumes = "application/xml")
	public void queryAllXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		MaskNumber maskNumber = new MaskNumber();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-queryAllXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-queryAllXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")||root.equals("MaskNumber")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String areaCode = mapXML.get("areaCode");

				// 将对应字段填充到报文对象
				maskNumber.setAppId(appId);
				maskNumber.setAreaCode(areaCode);
				// 填充accountSid
				maskNumber.setAccountSid(accountSid);

				Map<String, String> map = maskNumService.queryAll(maskNumber);
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
		queryAllReturn(false, respCode, dateCreated, respDesc, maskNumber, response);

		model.addAttribute("statusCode", 200);
	}

	private void queryAllReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
			MaskNumber maskNumber, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\""
						+ maskNumber.toListJson() + "}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated>" + maskNumber.toListXML() + "</resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_queryAll报文：" + respBody);
	}
}
