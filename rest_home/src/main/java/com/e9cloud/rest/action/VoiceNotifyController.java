package com.e9cloud.rest.action;

import com.e9cloud.core.support.SysPropertits;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.core.common.ID;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.VoiceTemp;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.VoiceNotifyService;
import com.e9cloud.rest.obt.CallNotifyHttp;
import com.e9cloud.rest.biz.CommonService;
import com.e9cloud.rest.obt.VoiceNotify;
import com.e9cloud.rest.obt.VoiceReq;
import com.e9cloud.rest.service.VoiceService;
import com.e9cloud.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/9/26.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class VoiceNotifyController {
	private static final Logger logger = LoggerFactory.getLogger(VoiceNotifyController.class);

	@Value("#{configProperties['rest.version']}")
	private String version;


	@Autowired
	private SysPropertits sysPropertits;

	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private VoiceNotifyService voiceNotifyService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private VoiceService voiceService;

	@Autowired
	private MongoDBDao mongoDBDao;

	@ModelAttribute("params")
	public Map<String, String> gainRestUrlParas(@PathVariable(value = "SoftVersion") String softVersion,
			@PathVariable(value = "AccountSid") String accountSid, @RequestParam(value = "sig") String sig) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("SoftVersion", softVersion);
		params.put("AccountSid", accountSid);
		params.put("Sig", sig);
		return params;
	}

	@RequestMapping(method = RequestMethod.POST, value = "VoiceNotify/request", consumes = "application/json")
	public void requestJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码\
		String subRespCode = null;//子响应码
		String respDesc = null;// 相应描述
		String subRespDesc = null;// 子相应描述
		String requestId = null;// 请求ID
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		try{
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-语音通知JSON报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[1024];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-语音通知JSON报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				//去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				//将对应字段填充到报文对象
				VoiceReq voiceReq = new Gson().fromJson(asBody, VoiceReq.class);
				//去除被叫号码列表的重复值
				HashSet<String> hashSet = new HashSet<>();
				hashSet.addAll(voiceReq.getToList());
				voiceReq.setToList(new ArrayList<>(hashSet));

				voiceReq.setAccountSid(accountSid);
				voiceReq.setRequestId(ID.randomUUID());

				//语音通知及预约语音通知
				Map<String, String> map = bespeakVoiceNotify(voiceReq);
				respCode = map.get("respCode");
				subRespCode = map.get("subRespCode");
				respDesc = map.get("respDesc");
				subRespDesc = map.get("subRespDesc");
				requestId = map.get("requestId");
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
			logger.error("requestJson error",e);
		}
		requestReturn(true, respCode, subRespCode, requestId, dateCreated, respDesc, subRespDesc, response);
		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "VoiceNotify/request", consumes = "application/xml")
	public void requestXml(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String subRespCode = null;//子响应码
		String respDesc = null;// 相应描述
		String subRespDesc = null;// 子相应描述
		String requestId = null;// 请求ID
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		try{
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-语音通知JSON报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[1024];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-CancelXML报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXml = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXml.get("root");
			if(root.equals("req")){
				//将对应字段填充到报文对象
				VoiceReq voiceReq = new VoiceReq();
				voiceReq.setAppId(mapXml.get("appId"));
				voiceReq.setRequestId(ID.randomUUID());
				voiceReq.setVoiceRecId(mapXml.get("voiceRecId"));
				voiceReq.setAccountSid(accountSid);
				voiceReq.setDtmfFlag(Integer.parseInt(mapXml.get("dtmfFlag")));
				voiceReq.setOrderFlag(Integer.parseInt(mapXml.get("orderFlag")));
				voiceReq.setOrderTime(mapXml.get("orderTime"));
				voiceReq.setModuleParams(mapXml.get("moduleParams"));
				voiceReq.setToList(commonService.getStrToList(mapXml.get("toList")));
				voiceReq.setDisplayNum(mapXml.get("displayNum"));

				//语音通知及预约语音通知
				Map<String, String> map = bespeakVoiceNotify(voiceReq);
				respCode = map.get("respCode");
				subRespCode = map.get("subRespCode");
				respDesc = map.get("respDesc");
				subRespDesc = map.get("subRespDesc");
				requestId = map.get("requestId");
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
		requestReturn(false, respCode, subRespCode, requestId, dateCreated, respDesc, subRespDesc, response);
		model.addAttribute("statusCode", 200);
	}

	private Map<String, String> bespeakVoiceNotify(VoiceReq voiceReq) throws Exception{
		//校验appid和accountSid的有效性
		Map<String, String> map = new HashMap<>();
		Map<String, String> checkMap = commonService.checkAppAndAccount(voiceReq.getAppId(),voiceReq.getAccountSid(),"02");
		if(checkMap != null){
			return checkMap;
		}

		//语音通知模板ID不允许为空
		if (!Utils.notEmpty(voiceReq.getVoiceRecId())) {
			map.put("respCode",ConstantsEnum.REST_VOICE_RECID_NULL.getCode());
			map.put("respDesc",ConstantsEnum.REST_VOICE_RECID_NULL.getDesc());
			logger.info(ConstantsEnum.REST_VOICE_RECID_NULL.getDesc());
			return map;
		}else{
			if(!Utils.isDigit(voiceReq.getVoiceRecId()) || voiceReq.getVoiceRecId().length() > 9){
				map.put("respCode",ConstantsEnum.REST_VOICE_RECID_NUM.getCode());
				map.put("respDesc",ConstantsEnum.REST_VOICE_RECID_NUM.getDesc());
				logger.info(ConstantsEnum.REST_VOICE_RECID_NUM.getDesc());
				return map;
			}else{
				VoiceTemp voiceTemp =  voiceNotifyService.getVoiceTempById(Integer.parseInt(voiceReq.getVoiceRecId()));
				if(voiceTemp == null){
					map.put("respCode",ConstantsEnum.REST_VOICE_RECID.getCode());
					map.put("respDesc",ConstantsEnum.REST_VOICE_RECID.getDesc());
					logger.info(ConstantsEnum.REST_VOICE_RECID.getDesc());
					return map;
				}
			}
		}


		//被叫号码列表不允许为空
		if (!Utils.notEmpty(voiceReq.getToList())) {
			map.put("respCode",ConstantsEnum.REST_TO_NULL.getCode());
			map.put("respDesc",ConstantsEnum.REST_TO_NULL.getDesc());
			logger.info(ConstantsEnum.REST_TO_NULL.getDesc());
			return map;
		}

		//反馈接收开关格式校验
		if(Utils.notEmpty(voiceReq.getDtmfFlag()) && voiceReq.getDtmfFlag() != 0 && voiceReq.getDtmfFlag() != 1){
			map.put("respCode",ConstantsEnum.REST_VOICE_DTMF_FLAG.getCode());
			map.put("respDesc",ConstantsEnum.REST_VOICE_DTMF_FLAG.getDesc());
			logger.info(ConstantsEnum.REST_VOICE_DTMF_FLAG.getDesc());
			return map;
		}

		//预约下发开关格式校验
		if(Utils.notEmpty(voiceReq.getOrderFlag()) && voiceReq.getOrderFlag() != 0 && voiceReq.getOrderFlag() != 1){
			map.put("respCode",ConstantsEnum.REST_VOICE_ORDER_FLAG.getCode());
			map.put("respDesc",ConstantsEnum.REST_VOICE_ORDER_FLAG.getDesc());
			logger.info(ConstantsEnum.REST_VOICE_ORDER_FLAG.getDesc());
			return map;
		}

		//预约下发时间格式校验
		if(Utils.notEmpty(voiceReq.getOrderTime()) && Utils.notEmpty(voiceReq.getOrderFlag()) && voiceReq.getOrderFlag() == 1){
			if(Utils.dateTimeValid(voiceReq.getOrderTime(), "yyyy/MM/dd HH:mm:ss")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date1 = sdf.parse(voiceReq.getOrderTime());

				Date date2 = new Date();
				if(date1.getTime() < date2.getTime()){
					map.put("respCode",ConstantsEnum.REST_VOICE_TIME_COMPARE.getCode());
					map.put("respDesc",ConstantsEnum.REST_VOICE_TIME_COMPARE.getDesc());
					logger.info(ConstantsEnum.REST_VOICE_TIME_COMPARE.getDesc());
					return map;
				}else{
					long diffHour = date1.getTime()-date2.getTime()-(24*60*60*1000);
					if(diffHour >  0){
						map.put("respCode",ConstantsEnum.REST_VOICE_TIME_MAX.getCode());
						map.put("respDesc",ConstantsEnum.REST_VOICE_TIME_MAX.getDesc());
						logger.info(ConstantsEnum.REST_VOICE_TIME_MAX.getDesc());
						return map;
					}
				}
			}else{
				map.put("respCode",ConstantsEnum.REST_VOICE_ORDER_TIME.getCode());
				map.put("respDesc",ConstantsEnum.REST_VOICE_ORDER_TIME.getDesc());
				logger.info(ConstantsEnum.REST_VOICE_ORDER_TIME.getDesc());
				return map;
			}
		}else{
			voiceReq.setOrderTime(DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME));
		}

		//模板参数格式校验
		if(Utils.notEmpty(voiceReq.getModuleParams()) && !Utils.isLetterDigit(voiceReq.getModuleParams())){
			map.put("respCode",ConstantsEnum.REST_VOICE_MODULE_PARAMS.getCode());
			map.put("respDesc",ConstantsEnum.REST_VOICE_MODULE_PARAMS.getDesc());
			logger.info(ConstantsEnum.REST_VOICE_MODULE_PARAMS.getDesc());
			return map;
		}

		//被叫号码列表上限不能超过100
		if (voiceReq.getToList().size() > 100) {
			map.put("respCode",ConstantsEnum.REST_VOICE_TO_LIST_MAX.getCode());
			map.put("respDesc",ConstantsEnum.REST_VOICE_TO_LIST_MAX.getDesc());
			logger.info(ConstantsEnum.REST_VOICE_TO_LIST_MAX.getDesc());
			return map;
		}

		//验证被叫号码列表中是否有不符合格式的号码
		for (String toPhone: voiceReq.getToList()) {
			if(!Utils.phoneAndTelValid(toPhone)){
				map.put("respCode",ConstantsEnum.REST_TO_FORMAT.getCode());
				map.put("respDesc",ConstantsEnum.REST_TO_FORMAT.getDesc()+"："+toPhone);
				logger.info(ConstantsEnum.REST_TO_FORMAT.getDesc());
				return map;
			}
		}


		//设置被叫显号列表参数
		List<String> beCalledDisplayNumList = new ArrayList<>();
		if(Utils.notEmpty(voiceReq.getDisplayNum())){
			beCalledDisplayNumList.add(voiceReq.getDisplayNum());
		}
		//被叫显号校验 校验外显号码是否为审核通过的号码 或者不在黑名单中的号码
		map = commonService.checkShowNumber(voiceReq.getAppId(),voiceReq.getAccountSid(),beCalledDisplayNumList, voiceReq.getToList(),"");
		if(ConstantsEnum.REST_SUCCCODE.getCode().equals(map.get("respCode").toString())){
			voiceReq.setDisplayNum(map.get("beCalledDisplayNum").toString());
		}else if(ConstantsEnum.REST_TOSERNUM_NOT_AUDIT.getCode().equals(map.get("respCode").toString())){
			//如果外显号错误 也可以呼叫成功 返回子错误码
			map.put("subRespCode",map.get("respCode").toString());
			map.put("subRespDesc",map.get("respDesc").toString());
			voiceReq.setDisplayNum(map.get("beCalledDisplayNum").toString());
		}else{
			return map;
		}

		map.putAll(voiceService.saveVoiceNotify(voiceReq));
		return map;
	}


	private void requestReturn(boolean isJson, String respCode, String subRespCode,  String requestId, String dateCreated,
							   String respDesc, String subRespDesc, HttpServletResponse response) throws Exception {
		String respBody;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
			// 子错误码不为空时，拼接上子错误码
			String subStr = "";
			if (Utils.notEmpty(subRespCode)) {
				if(isJson){
					subStr = "\"statusSubCode\":\"" + subRespCode + "\",\"represent\":\"" + subRespDesc + "\",";
				}else{
					subStr = "<statusSubCode>"+ subRespCode +"</statusSubCode><represent>"+ subRespDesc +"</represent>";
				}
			}

			if(isJson){
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\","+subStr +"\"dateCreated\":\"" + dateCreated + "\",\"requestId\":\"" + requestId + "\"}}";
			}else{
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode>" + subStr +
						"<dateCreated>"+ dateCreated + "</dateCreated><requestId>"+ requestId + "</requestId></resp>";
			}
		} else {
			if(isJson){
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\",\"represent\":\"" + respDesc + "\"}}";
			}else{
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}
		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_request报文：" + respBody);
	}


	@RequestMapping(method = RequestMethod.POST, value = "VoiceNotify/cancel", consumes = "application/json")
	public void cancelJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-CancelJSON报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-CancelJSON报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-CancelJSON报文去掉最外层后："+asBody);
				// 将对应字段填充到报文对象
				VoiceNotify voiceNotify = new VoiceNotify();
				voiceNotify = new Gson().fromJson(asBody, VoiceNotify.class);
				voiceNotify.setAccountSid(accountSid);

				// 进行取消回拨业务操作
				Map<String, String> map = cancel(voiceNotify);
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
		cancelReturn(true, respCode, dateCreated, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "VoiceNotify/cancel", consumes = "application/xml")
	public void cancelXml(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		String requestId = null;
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-CancelXML报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			String asBody = out.toString();
			logger.info("获取AS-CancelXML报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")){
				// 获取相应报文字段
				requestId = mapXML.get("requestId");
				String appId = mapXML.get("appId");

				// 将对应字段填充到报文对象
				VoiceNotify voiceNotify = new VoiceNotify();
				voiceNotify.setRequestId(requestId);
				voiceNotify.setAppId(appId);
				voiceNotify.setAccountSid(accountSid);

				// 进行取消预约语音通知操作
				Map<String, String> map = cancel(voiceNotify);
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
		cancelReturn(false, respCode, dateCreated, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	private void cancelReturn(boolean isJson, String respCode, String dateCreated,
			String respDesc, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\"}}";
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
						+ dateCreated + "</dateCreated><represent>" + respDesc
						+ "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_cancelCallBack报文：" + respBody);
	}

	private Map<String, String> cancel(VoiceNotify voiceNotify) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成requestId和请求时间
		String requestId = voiceNotify.getRequestId();
		String accountSid = voiceNotify.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段非空验证
		if (!Utils.notEmpty(voiceNotify.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
			logger.info(respDesc);
		} else if (!Utils.notEmpty(requestId)) {
			respCode = ConstantsEnum.REST_REQUESTID_NULL.getCode();
			respDesc = ConstantsEnum.REST_REQUESTID_NULL.getDesc();
			logger.info(respDesc);
		} else {
			// 根据appid查找应用信息
			String appid = voiceNotify.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			// 应用信息不正常，返回对应的错误码报文
			if (appInfo == null || !"02".equals(appInfo.getBusType())) {
				respCode = ConstantsEnum.REST_APPID_NO.getCode();
				respDesc = ConstantsEnum.REST_APPID_NO.getDesc();
				logger.info(respDesc);
			} else if (!"00".equals(appInfo.getStatus())) {
				respCode = ConstantsEnum.REST_APPID_ABNORMAL.getCode();
				respDesc = ConstantsEnum.REST_APPID_ABNORMAL.getDesc();
				logger.info(respDesc);
			} else if (!accountSid.equals(appInfo.getSid())) {
				respCode = ConstantsEnum.REST_APPID_NOT_SID.getCode();
				respDesc = ConstantsEnum.REST_APPID_NOT_SID.getDesc();
				logger.info(respDesc);
			} else {
				// 获取用户token
				User user = accountService.getUserBySid(accountSid);
				if (user == null) {
					respCode = ConstantsEnum.REST_SIDUSER_NO.getCode();
					respDesc = ConstantsEnum.REST_SIDUSER_NO.getDesc();
					logger.info(respDesc);
				} else if (!"1".equals(user.getStatus())) {
					respCode = ConstantsEnum.REST_SID_ABNORMAL.getCode();
					respDesc = ConstantsEnum.REST_SID_ABNORMAL.getDesc();
					logger.info(respDesc);
				} else if (user.getFee().compareTo(BigDecimal.ZERO) <= 0) {
					respCode = ConstantsEnum.REST_SID_OVERDUE.getCode();
					respDesc = ConstantsEnum.REST_SID_OVERDUE.getDesc();
					logger.info(respDesc);
				} else {
					respCode = ConstantsEnum.REST_SUCCCODE.getCode();

					//requestId存在且未到预约时间，可以取消
					//requestId不存在、或已下发，则直接返回错误
					VoiceReq voiceReqTmp=new VoiceReq();
					voiceReqTmp.setRequestId(requestId);
					VoiceReq voiceReq=voiceNotifyService.findVoice(voiceReqTmp);
					if(!Utils.notEmpty(voiceReq)){
						respCode = ConstantsEnum.REST_REQUESTID_NOTEXIST.getCode();
						respDesc = ConstantsEnum.REST_REQUESTID_NOTEXIST.getDesc();
						logger.info(respDesc);
					}else if(voiceReq.getSyncFlag()==1){
						respCode = ConstantsEnum.REST_REQUESTID_PUSH.getCode();
						respDesc = ConstantsEnum.REST_REQUESTID_PUSH.getDesc();
						logger.info(respDesc);
					}else if(voiceReq.getSyncFlag()==2){
						respCode = ConstantsEnum.REST_REQUESTID_CANCEL.getCode();
						respDesc = ConstantsEnum.REST_REQUESTID_CANCEL.getDesc();
						logger.info(respDesc);
					}else{
						//取消预约语音通知
						voiceReqTmp.setId(voiceReq.getId());
						voiceReqTmp.setSyncFlag(2);
						voiceNotifyService.updateVoice(voiceReqTmp);
						Map param = new HashMap<>();
						param.put("requestId",requestId);
						String url = "http://"+voiceReq.getIpPort()+"/isp/voiceCanel";
						String result= HttpUtil.post(url,param);
						if(StringUtils.isNoneBlank(result)&&"true".equals(result))
						{
							respCode = ConstantsEnum.REST_SUCCCODE.getCode();
							respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
						}else{
							respCode = ConstantsEnum.REST_REQUESTID_CANCEL.getCode();
							respDesc = ConstantsEnum.REST_REQUESTID_CANCEL.getDesc();
							}
					}
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}

	@RequestMapping(method = RequestMethod.POST, value = "VoiceNotify/queryRecord", consumes = "application/json")
	public void queryRecordJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		CallNotifyHttp callNotify=new CallNotifyHttp();
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-queryRecordJSON报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-queryRecordJSON报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-CancelJSON报文去掉最外层后："+asBody);
				// 将对应字段填充到报文对象
				VoiceNotify voiceNotify = new VoiceNotify();
				voiceNotify = new Gson().fromJson(asBody, VoiceNotify.class);
				voiceNotify.setAccountSid(accountSid);

				// 进行查询语音通知状态回调操作
				Map<String, Object> map = queryRecord(voiceNotify);
				respCode = map.get("respCode").toString();
				respDesc = map.get("respDesc").toString();
				callNotify = (CallNotifyHttp) map.get("callNotify");
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
		queryRecordReturn(true, respCode, dateCreated, callNotify, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "VoiceNotify/queryRecord", consumes = "application/xml")
	public void queryRecordXml(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		CallNotifyHttp callNotify=new CallNotifyHttp();
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		String requestId = null;
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-queryRecordXML报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			String asBody = out.toString();
			logger.info("获取AS-queryRecordXML报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")){
				// 获取相应报文字段
				requestId = mapXML.get("requestId");
				String appId = mapXML.get("appId");
				String to = mapXML.get("to");

				// 将对应字段填充到报文对象
				VoiceNotify voiceNotify = new VoiceNotify();
				voiceNotify.setRequestId(requestId);
				voiceNotify.setAppId(appId);
				voiceNotify.setTo(to);
				voiceNotify.setAccountSid(accountSid);

				// 进行查询语音通知状态回调操作
				Map<String, Object> map = queryRecord(voiceNotify);
				respCode = map.get("respCode").toString();
				respDesc = map.get("respDesc").toString();
				callNotify = (CallNotifyHttp) map.get("callNotify");
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
		queryRecordReturn(false, respCode, dateCreated, callNotify, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	private void queryRecordReturn(boolean isJson, String respCode, String dateCreated, CallNotifyHttp callNotify,
							  String respDesc, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"voiceNotify\":{"+callNotify.toVoiceRecord(isJson)+"}}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated>" +
						"<voiceNotify>" + callNotify.toVoiceRecord(isJson) + "</voiceNotify></resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_queryRecord报文：" + respBody);
	}

	private Map<String, Object> queryRecord(VoiceNotify voiceNotify) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		CallNotifyHttp callNotify=new CallNotifyHttp();// 响应报文
		// 生成requestId和请求时间
		String requestId = voiceNotify.getRequestId();
		String accountSid = voiceNotify.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段非空验证
		if (!Utils.notEmpty(voiceNotify.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
			logger.info(respDesc);
		} else if (!Utils.notEmpty(requestId)) {
			respCode=ConstantsEnum.REST_REQUESTID_NULL.getCode();
			respDesc=ConstantsEnum.REST_REQUESTID_NULL.getDesc();
			logger.info(respDesc);
		}else if(!Utils.notEmpty(voiceNotify.getTo())){
			respCode=ConstantsEnum.REST_TO_NULL.getCode();
			respDesc=ConstantsEnum.REST_TO_NULL.getDesc();
			logger.info(respDesc);
		}else if(!Utils.phoneAndTelValid(voiceNotify.getTo())){
			respCode=ConstantsEnum.REST_TO_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_TO_FORMAT.getDesc();
			logger.info(respDesc);
		} else {
			// 根据appid查找应用信息
			String appid = voiceNotify.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			// 应用信息不正常，返回对应的错误码报文
			if (appInfo == null || !"02".equals(appInfo.getBusType())) {
				respCode = ConstantsEnum.REST_APPID_NO.getCode();
				respDesc = ConstantsEnum.REST_APPID_NO.getDesc();
				logger.info(respDesc);
			} else if (!"00".equals(appInfo.getStatus())) {
				respCode = ConstantsEnum.REST_APPID_ABNORMAL.getCode();
				respDesc = ConstantsEnum.REST_APPID_ABNORMAL.getDesc();
				logger.info(respDesc);
			} else if (!accountSid.equals(appInfo.getSid())) {
				respCode = ConstantsEnum.REST_APPID_NOT_SID.getCode();
				respDesc = ConstantsEnum.REST_APPID_NOT_SID.getDesc();
				logger.info(respDesc);
			} else {
				// 获取用户token
				User user = accountService.getUserBySid(accountSid);
				if (user == null) {
					respCode = ConstantsEnum.REST_SIDUSER_NO.getCode();
					respDesc = ConstantsEnum.REST_SIDUSER_NO.getDesc();
					logger.info(respDesc);
				} else if (!"1".equals(user.getStatus())) {
					respCode = ConstantsEnum.REST_SID_ABNORMAL.getCode();
					respDesc = ConstantsEnum.REST_SID_ABNORMAL.getDesc();
					logger.info(respDesc);
				} else if (user.getFee().compareTo(BigDecimal.ZERO) <= 0) {
					respCode = ConstantsEnum.REST_SID_OVERDUE.getCode();
					respDesc = ConstantsEnum.REST_SID_OVERDUE.getDesc();
					logger.info(respDesc);
				} else {
					//根据requestId和to查询语音通知挂机消息
//					（1）若requestId存在则返回对应语音通知回调的结果
//					（2）若查询不到，返回错误码
					Query query = new Query();
					//语音通知的callSid就是requestId
					query.addCriteria(Criteria.where("callSid").is(requestId));
					query.addCriteria(Criteria.where("to").is(voiceNotify.getTo()));
					callNotify=mongoDBDao.findOne(query,CallNotifyHttp.class);
					if(!Utils.notEmpty(callNotify)){
						//不存在
						respCode = ConstantsEnum.REST_VOICE_NOTEXIST.getCode();
						respDesc = ConstantsEnum.REST_VOICE_NOTEXIST.getDesc();
					}else{
						respCode = ConstantsEnum.REST_SUCCCODE.getCode();
						respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
					}
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		map.put("callNotify", callNotify);
		return map;
	}

}
