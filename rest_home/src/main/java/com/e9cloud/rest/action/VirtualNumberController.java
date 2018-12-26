package com.e9cloud.rest.action;

import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.NumberBlackService;
import com.e9cloud.rest.obt.VirtualNumberBody;
import com.e9cloud.rest.obt.VnObj;
import com.e9cloud.rest.vn.VirtualNumService;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/29.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class VirtualNumberController {
	private static final Logger logger = LoggerFactory.getLogger(VirtualNumberController.class);

	@Value("#{configProperties['rest.version']}")
	private String version;

	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private VirtualNumService virtualNumService;
	@Autowired
	private NumberBlackService numberBlackService;

	@ModelAttribute("params")
	public Map<String, String> gainRestUrlParas(@PathVariable(value = "SoftVersion") String softVersion,
			@PathVariable(value = "AccountSid") String accountSid, @RequestParam(value = "sig") String sig) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("SoftVersion", softVersion);
		params.put("AccountSid", accountSid);
		params.put("Sig", sig);

		return params;
	}

	@RequestMapping(method = RequestMethod.POST, value = "VirtualNumber/apply", consumes = "application/json")
	public void applyJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		VirtualNumberBody virtualNumberBody = new VirtualNumberBody();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-xnapplyJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-xnapplyJson报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				virtualNumberBody = new Gson().fromJson(asBody, VirtualNumberBody.class);
				// 填充accountSid
				virtualNumberBody.setAccountSid(accountSid);

				Map<String, String> map = apply(virtualNumberBody);
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
		applyReturn(true, respCode, dateCreated, respDesc, virtualNumberBody, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "VirtualNumber/apply", consumes = "application/xml")
	public void applyXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		VirtualNumberBody virtualNumberBody = new VirtualNumberBody();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-xnapplyXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-xnapplyXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String numberType = mapXML.get("numberType");
				String caller=mapXML.get("caller");
				String called=mapXML.get("called");
				int needRecord = Utils.strToInteger(mapXML.get("needRecord"));
				int validTime = Utils.strToInteger(mapXML.get("validTime"));
				// 针对整形 不为空，格式不符限制转换，避免默认0，而呼叫成功
				if (Utils.notEmpty(mapXML.get("needRecord")) && !Utils.isDirectionDigit(mapXML.get("needRecord"))) {
					needRecord = -5;
				}
				if (Utils.notEmpty(mapXML.get("validTime")) && !Utils.isDirectionDigit(mapXML.get("validTime"))) {
					validTime = -5;
				}

				// 将对应字段填充到报文对象
				virtualNumberBody.setAppId(appId);
				virtualNumberBody.setNumberType(numberType);
				virtualNumberBody.setCaller(caller);
				virtualNumberBody.setCalled(called);
				virtualNumberBody.setNeedRecord(needRecord);
				virtualNumberBody.setValidTime(validTime);

				// 填充accountSid
				virtualNumberBody.setAccountSid(accountSid);

				Map<String, String> map = apply(virtualNumberBody);
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
		applyReturn(false, respCode, dateCreated, respDesc, virtualNumberBody, response);

		model.addAttribute("statusCode", 200);
	}

	/**
	 * 生成虚拟号
	 *
	 * @param virtualNumberBody
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> apply(VirtualNumberBody virtualNumberBody) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = virtualNumberBody.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		Map mapTmp = baseValid(virtualNumberBody);
		if (!(boolean) mapTmp.get("flag")) {
			respCode = mapTmp.get("respCode").toString();
			respDesc = mapTmp.get("respDesc").toString();
		}else{
			// 根据appid查找应用信息
			String appid = virtualNumberBody.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			// 应用信息不正常，返回对应的错误码报文
			if (appInfo == null || !"02".equals(appInfo.getBusType())) {
				respCode = ConstantsEnum.REST_APPID_NO.getCode();
				respDesc = ConstantsEnum.REST_APPID_NO.getDesc();
			} else if (!"00".equals(appInfo.getStatus())) {
				respCode = ConstantsEnum.REST_APPID_ABNORMAL.getCode();
				respDesc = ConstantsEnum.REST_APPID_ABNORMAL.getDesc();
			} else if (!accountSid.equals(appInfo.getSid())) {
				respCode = ConstantsEnum.REST_APPID_NOT_SID.getCode();
				respDesc = ConstantsEnum.REST_APPID_NOT_SID.getDesc();
			} else if ("01".equals(appInfo.getLimitFlag()) && appInfo.getQuota().compareTo(BigDecimal.ZERO)<=0) {
				//应用开启限额，并超额
				respCode = ConstantsEnum.REST_APPID_LIMITDUE.getCode();
				respDesc = ConstantsEnum.REST_APPID_LIMITDUE.getDesc();
			} else {
				// 获取用户token
				User user = accountService.getUserBySid(accountSid);
				if (user == null) {
					respCode = ConstantsEnum.REST_SIDUSER_NO.getCode();
					respDesc = ConstantsEnum.REST_SIDUSER_NO.getDesc();
				} else if (!"1".equals(user.getStatus())) {
					respCode = ConstantsEnum.REST_SID_ABNORMAL.getCode();
					respDesc = ConstantsEnum.REST_SID_ABNORMAL.getDesc();
				} else if (user.getFee().compareTo(BigDecimal.ZERO) <= 0) {
					respCode = ConstantsEnum.REST_SID_OVERDUE.getCode();
					respDesc = ConstantsEnum.REST_SID_OVERDUE.getDesc();
				} else {
					// TODO
					// 根据主叫和被叫生成虚拟号码
					// 1、主叫和被叫已存在虚拟号码，直接返回相关虚拟号码，并在错误描述中提示已存在虚拟号码
					// 2、主叫和被叫不存在虚拟号码，生成6位（100000-999999）随机虚拟号：
					// 2.1 该虚拟号在tb_vn_vnm已存在有效数据，则重新生成虚拟号，直至生成唯一有效的虚拟号
					///////////////////////////////
					VnObj vnObj=new VnObj();
					vnObj.setAppid(virtualNumberBody.getAppId());
					vnObj.setVnType(virtualNumberBody.getNumberType());
					vnObj.setSid(virtualNumberBody.getAccountSid());
					vnObj.setCaller(virtualNumberBody.getCaller());
					vnObj.setCalled(virtualNumberBody.getCalled());
					vnObj.setValidtime(virtualNumberBody.getValidTime());
					vnObj.setNeedrecord(virtualNumberBody.getNeedRecord());

					vnObj=virtualNumService.addVnNum(vnObj);
					if(Utils.notEmpty(vnObj)){
						//不为空，填充虚拟号id和虚拟号码、是否录音、有效时间
						virtualNumberBody.setVnId(vnObj.getVnid());
						virtualNumberBody.setVirtualNumber(vnObj.getVn());
						virtualNumberBody.setNeedRecord(vnObj.getNeedrecord());
						virtualNumberBody.setValidTime(vnObj.getValidtime());
					}

					respCode = ConstantsEnum.REST_SUCCCODE.getCode();
					respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}

	private void applyReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
			VirtualNumberBody virtualNumberBody, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\","
						+ virtualNumberBody.toJson() + "}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated>" + virtualNumberBody.toXML() + "</resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS-xnapply报文：" + respBody);
	}

	/**
	 * 虚拟号列表校验
	 *
	 * @param virtualNumberBody
	 * @return map
	 */
	public Map baseValid(VirtualNumberBody virtualNumberBody) {
		Map map = new HashMap<>();
		String respCode=null;
		String respDesc=null;
		boolean flag=true;
		//必选字段非空验证
		if (!Utils.notEmpty(virtualNumberBody.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
			flag = false;
		}else if (!Utils.notEmpty(virtualNumberBody.getNumberType())) {
			respCode = ConstantsEnum.REST_NUMBERTYPE_NULL.getCode();
			respDesc = ConstantsEnum.REST_NUMBERTYPE_NULL.getDesc();
			flag = false;
		}else if (!"S".equals(virtualNumberBody.getNumberType()) && !"L".equals(virtualNumberBody.getNumberType())) {
			respCode = ConstantsEnum.REST_NUMBERTYPE_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_NUMBERTYPE_FORMAT.getDesc();
			flag = false;
		}else if (!Utils.notEmpty(virtualNumberBody.getCaller())) {
			respCode = ConstantsEnum.REST_FROM_NULL.getCode();
			respDesc = ConstantsEnum.REST_FROM_NULL.getDesc();
			flag = false;
		} else if (!Utils.phonenoValid(virtualNumberBody.getCaller())) {
			respCode = ConstantsEnum.REST_FROM_FORMAT.getCode();
			respDesc = virtualNumberBody.getCaller()+ConstantsEnum.REST_FROM_FORMAT.getDesc();
			flag = false;
		} else if (!Utils.notEmpty(virtualNumberBody.getCalled())) {
			respCode = ConstantsEnum.REST_TO_NULL.getCode();
			respDesc = ConstantsEnum.REST_TO_NULL.getDesc();
			flag = false;
		} else if (!Utils.phonenoValidB(virtualNumberBody.getCalled())) {
			respCode = ConstantsEnum.REST_TO_FORMAT.getCode();
			respDesc = virtualNumberBody.getCalled()+ConstantsEnum.REST_TO_FORMAT.getDesc();
			flag = false;
		}else if (Utils.notEmpty(virtualNumberBody.getNeedRecord()) &&
				virtualNumberBody.getNeedRecord() != 0 && virtualNumberBody.getNeedRecord() != 1) {
			// needRecord格式不符（不为空时，非0和1数字）
			respCode = ConstantsEnum.REST_NEEDRECORD_FORMAT.getCode();
			respDesc = virtualNumberBody.getCalled()+ConstantsEnum.REST_NEEDRECORD_FORMAT.getDesc();
			flag = false;
		} else if (Utils.notEmpty(virtualNumberBody.getValidTime()) &&
				virtualNumberBody.getValidTime() <= 0) {
			// validTime格式不符（不为空时，小于等于0）
			respCode = ConstantsEnum.REST_VALIDTIME_FORMAT.getCode();
			respDesc = virtualNumberBody.getCalled()+ConstantsEnum.REST_VALIDTIME_FORMAT.getDesc();
			flag = false;
		}else{
			// 获取黑名单号码列表
			List<String> numberBlacks=numberBlackService.findList();
			if (Utils.notEmpty(numberBlacks) && numberBlacks.contains(virtualNumberBody.getCaller())) {
				respCode = ConstantsEnum.REST_FROM_NUMBERBLACK.getCode();
				respDesc = virtualNumberBody.getCaller()+ConstantsEnum.REST_FROM_NUMBERBLACK.getDesc();
				flag = false;
			} else if (Utils.notEmpty(numberBlacks) && numberBlacks.contains(virtualNumberBody.getCalled())) {
				respCode = ConstantsEnum.REST_TO_NUMBERBLACK.getCode();
				respDesc = virtualNumberBody.getCalled()+ConstantsEnum.REST_TO_NUMBERBLACK.getDesc();
				flag = false;
			}
		}
		map.put("flag",flag);
		map.put("respCode",respCode);
		map.put("respDesc",respDesc);
		return map;
	}

}
