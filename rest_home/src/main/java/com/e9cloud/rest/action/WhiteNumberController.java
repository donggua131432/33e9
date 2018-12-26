package com.e9cloud.rest.action;

import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.NumberBlackService;
import com.e9cloud.rest.obt.VirtualNumberBody;
import com.e9cloud.rest.obt.VnObj;
import com.e9cloud.rest.obt.WhiteNumberBody;
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
 * Created by Administrator on 2016/9/12.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class WhiteNumberController {
	private static final Logger logger = LoggerFactory.getLogger(WhiteNumberController.class);

	@Value("#{configProperties['rest.version']}")
	private String version;

	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private VirtualNumService virtualNumService;

	@ModelAttribute("params")
	public Map<String, String> gainRestUrlParas(@PathVariable(value = "SoftVersion") String softVersion,
			@PathVariable(value = "AccountSid") String accountSid, @RequestParam(value = "sig") String sig) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("SoftVersion", softVersion);
		params.put("AccountSid", accountSid);
		params.put("Sig", sig);

		return params;
	}

	@RequestMapping(method = RequestMethod.POST, value = "White/operate", consumes = "application/json")
	public void operateJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		WhiteNumberBody whiteNumberBody = new WhiteNumberBody();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-operateJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-operateJson报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				whiteNumberBody = new Gson().fromJson(asBody, WhiteNumberBody.class);
				// 填充accountSid
				whiteNumberBody.setAccountSid(accountSid);

				Map<String, String> map = operate(whiteNumberBody);
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
		operateReturn(true, respCode, dateCreated, respDesc, whiteNumberBody, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "White/operate", consumes = "application/xml")
	public void operateXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		// 将对应字段填充到报文对象
		WhiteNumberBody whiteNumberBody = new WhiteNumberBody();
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-operateXml报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-operateXml报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String action = mapXML.get("action");
				String number=mapXML.get("number");
				String type=mapXML.get("type");
				int validTime = Utils.strToInteger(mapXML.get("validTime"));
				// 针对整形 不为空，格式不符限制转换，避免默认0，而呼叫成功
				if (Utils.notEmpty(mapXML.get("validTime")) && !Utils.isDirectionDigit(mapXML.get("validTime"))) {
					validTime = -5;
				}

				// 将对应字段填充到报文对象
				whiteNumberBody.setAppId(appId);
				whiteNumberBody.setAction(action);
				whiteNumberBody.setNumber(number);
				whiteNumberBody.setType(type);
				whiteNumberBody.setValidTime(validTime);

				// 填充accountSid
				whiteNumberBody.setAccountSid(accountSid);

				Map<String, String> map = operate(whiteNumberBody);
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
		operateReturn(false, respCode, dateCreated, respDesc, whiteNumberBody, response);

		model.addAttribute("statusCode", 200);
	}

	/**
	 * 白名单维护
	 *
	 * @param whiteNumberBody
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> operate(WhiteNumberBody whiteNumberBody) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = whiteNumberBody.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		Map mapTmp = baseValid(whiteNumberBody);
		if (!(boolean) mapTmp.get("flag")) {
			respCode = mapTmp.get("respCode").toString();
			respDesc = mapTmp.get("respDesc").toString();
		}else{
			// 根据appid查找应用信息
			String appid = whiteNumberBody.getAppId();
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
					//根据action操作add或del
					///////////////////////////////
					if("add".equals(whiteNumberBody.getAction())){
						if("L".equals(whiteNumberBody.getType())){
							//永久有效存储redis
							virtualNumService.addWhite(whiteNumberBody.getAccountSid(),whiteNumberBody.getNumber(),0);
						}else{
							//固定有效期存储redis
							virtualNumService.addWhite(whiteNumberBody.getAccountSid(),whiteNumberBody.getNumber(),whiteNumberBody.getValidTime());
						}
					}else if("del".equals(whiteNumberBody.getAction())){
						//删除对应白名单redis存储
						virtualNumService.delWhite(whiteNumberBody.getAccountSid(),whiteNumberBody.getNumber());
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

	private void operateReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
			WhiteNumberBody whiteNumberBody, HttpServletResponse response) throws Exception {
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
		logger.info("返回AS-operate报文：" + respBody);
	}

	/**
	 * 白名单维护校验
	 *
	 * @param whiteNumberBody
	 * @return map
	 */
	public Map baseValid(WhiteNumberBody whiteNumberBody) {
		Map map = new HashMap<>();
		String respCode=null;
		String respDesc=null;
		boolean flag=true;
		//必选字段非空验证
		if (!Utils.notEmpty(whiteNumberBody.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
			flag = false;
		}else if (!Utils.notEmpty(whiteNumberBody.getAction())) {
			respCode = ConstantsEnum.REST_ACTION_NULL.getCode();
			respDesc = ConstantsEnum.REST_ACTION_NULL.getDesc();
			flag = false;
		}else if (!"add".equals(whiteNumberBody.getAction()) && !"del".equals(whiteNumberBody.getAction())) {
			respCode = ConstantsEnum.REST_ACTION_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_ACTION_FORMAT.getDesc();
			flag = false;
		}else if (!Utils.notEmpty(whiteNumberBody.getNumber())) {
			respCode = ConstantsEnum.REST_NUMBER_NULL.getCode();
			respDesc = ConstantsEnum.REST_NUMBER_NULL.getDesc();
			flag = false;
		}else if (!Utils.notEmpty(whiteNumberBody.getType())) {
			respCode = ConstantsEnum.REST_TYPE_NULL.getCode();
			respDesc = ConstantsEnum.REST_TYPE_NULL.getDesc();
			flag = false;
		}else if (!"S".equals(whiteNumberBody.getType()) && !"L".equals(whiteNumberBody.getType())) {
			respCode = ConstantsEnum.REST_TYPE_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_TYPE_FORMAT.getDesc();
			flag = false;
		}else if (Utils.notEmpty(whiteNumberBody.getValidTime()) && whiteNumberBody.getValidTime() <= 0) {
			// validTime格式不符（不为空时，小于等于0）
			respCode = ConstantsEnum.REST_VALIDTIME_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_VALIDTIME_FORMAT.getDesc();
			flag = false;
		}
		map.put("flag",flag);
		map.put("respCode",respCode);
		map.put("respDesc",respDesc);
		return map;
	}

}
