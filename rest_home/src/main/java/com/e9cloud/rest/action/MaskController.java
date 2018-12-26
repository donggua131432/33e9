package com.e9cloud.rest.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.e9cloud.mybatis.domain.CbVoiceCode;
import com.e9cloud.mybatis.service.*;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.e9cloud.core.application.InitApp;
import com.e9cloud.core.common.ID;
import com.e9cloud.core.support.SysPropertits;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.cb.CBTcpClient;
import com.e9cloud.rest.cb.Server;
import com.e9cloud.rest.mask.MakeService;
import com.e9cloud.rest.obt.MaskCallBack;
import com.e9cloud.rest.service.RestService;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.ReadXmlUtil;
import com.e9cloud.util.Utils;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/6/1.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class MaskController {
	private static final Logger logger = LoggerFactory.getLogger(MaskController.class);

	@Value("#{configProperties['rest.version']}")
	private String version;

	@Autowired
	private SysPropertits sysPropertits;

	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SubAppService subAppService;
	@Autowired
	private RestService restService;
	@Autowired
	private AppNumberRestService appNumberRestService;
	@Autowired
	private NumberBlackService numberBlackService;
	@Autowired
	private JedisClusterUtil jedisClusterUtil;

	@Autowired
	private VoiceCodeService voiceCodeService;

	@ModelAttribute("params")
	public Map<String, String> gainRestUrlParas(@PathVariable(value = "SoftVersion") String softVersion,
			@PathVariable(value = "AccountSid") String accountSid, @RequestParam(value = "sig") String sig) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("SoftVersion", softVersion);
		params.put("AccountSid", accountSid);
		params.put("Sig", sig);

		return params;
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/sfForwardUpdate", consumes = "application/json")
	public void sfMaskUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		try {
			// 获取AS-callBackJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			String[] body = asBody.split("[,]");
			if (body.length == 2) {
				String key = body[0];
				String value = body[1];
				jedisClusterUtil.STRINGS.set(key, value);
			} else if (body.length == 3) {
				String key = body[0];
				String value = body[1];
				String seconds = body[2];
				int second = Integer.parseInt(seconds);
				jedisClusterUtil.STRINGS.setEx(key, second, value);
			}
			logger.info("获取AS-sfMaskUpdate报文：" + asBody);
			// 将对应字段填充到报文对象
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/maskCallback", consumes = "application/json")
	public void maskCallbackJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成callId和请求时间
		String callId = ID.randomUUID();
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-callBackJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-maskCallbackJson报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("maskCallback")||root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				// 将对应字段填充到报文对象
				MaskCallBack maskCallBack = new MaskCallBack();
				maskCallBack = new Gson().fromJson(asBody, MaskCallBack.class);
				// 填充callSid
				maskCallBack.setCallSid(callId);
				// 填充accountSid
				maskCallBack.setAccountSid(accountSid);

				Map<String, String> map = maskCallback(maskCallBack);
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
		maskCallbackReturn(true, respCode, dateCreated, callId, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/maskCallback", consumes = "application/xml")
	public void maskCallbackXml(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成callId和请求时间
		String callId = ID.randomUUID();
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-XML报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			String asBody = out.toString();
			logger.info("获取AS_maskCallbackXML报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.contains("maskCallback")||root.contains("req")||root.contains("Calls")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String subId = mapXML.get("subId");
				String from = mapXML.get("from");
				String to = mapXML.get("to");
				String maskNumber = mapXML.get("maskNumber");
				int maxCallTime = Utils.strToInteger(mapXML.get("maxCallTime"));
				int needRecord = Utils.strToInteger(mapXML.get("needRecord"));
				int countDownTime = Utils.strToInteger(mapXML.get("countDownTime"));
				// 针对整形 不为空，格式不符限制转换，避免默认0，而呼叫成功
				if (Utils.notEmpty(mapXML.get("maxCallTime")) && !Utils.isDirectionDigit(mapXML.get("maxCallTime"))) {
					maxCallTime = -5;
				}
				if (Utils.notEmpty(mapXML.get("needRecord")) && !Utils.isDirectionDigit(mapXML.get("needRecord"))) {
					needRecord = -5;
				}
				if (Utils.notEmpty(mapXML.get("countDownTime")) && !Utils.isDirectionDigit(mapXML.get("countDownTime"))) {
					countDownTime = -5;
				}
				String userData = mapXML.get("userData");

				// 将对应字段填充到报文对象
				MaskCallBack maskCallBack = new MaskCallBack();
				maskCallBack.setAppId(appId);
				maskCallBack.setSubId(subId);
				maskCallBack.setFrom(from);
				maskCallBack.setTo(to);
				maskCallBack.setMaskNumber(maskNumber);
				maskCallBack.setMaxCallTime(maxCallTime);
				maskCallBack.setNeedRecord(needRecord);
				maskCallBack.setCountDownTime(countDownTime);
				maskCallBack.setUserData(userData);
				// 填充callSid
				maskCallBack.setCallSid(callId);
				// 填充accountSid
				maskCallBack.setAccountSid(accountSid);

				// 进行私密回拨业务操作
				Map<String, String> map = maskCallback(maskCallBack);
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
		maskCallbackReturn(false, respCode, dateCreated, callId, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	private void maskCallbackReturn(boolean isJson, String respCode, String dateCreated, String callId, String respDesc,
			HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"callId\":\"" + callId + "\"}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><callId>" + callId + "</callId></resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_maskCallback报文：" + respBody);
	}

	private Map<String, String> maskCallback(MaskCallBack callBack) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		// 生成callId和请求时间
		String callId = callBack.getCallSid();
		String accountSid = callBack.getAccountSid();

		// 必选字段格式验证
		Map map = CallBackValid.maskBaseValid(callBack);
		if (!(boolean) map.get("flag")) {
			respCode = map.get("respCode").toString();
			respDesc = map.get("respDesc").toString();
		} else {
			// 根据appid查找应用信息
			String appid = callBack.getAppId();
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
					// 获取黑名单号码列表
					List<String> numberBlacks=numberBlackService.findList();
					if (Utils.notEmpty(numberBlacks) && numberBlacks.contains(callBack.getFrom())) {
						respCode = ConstantsEnum.REST_FROM_NUMBERBLACK.getCode();
						respDesc = ConstantsEnum.REST_FROM_NUMBERBLACK.getDesc();
					} else if (Utils.notEmpty(numberBlacks) && numberBlacks.contains(callBack.getTo())) {
						respCode = ConstantsEnum.REST_TO_NUMBERBLACK.getCode();
						respDesc = ConstantsEnum.REST_TO_NUMBERBLACK.getDesc();
					} else {
						boolean flag = true;
						// 一期不上，暂时屏蔽
						// if(Utils.notEmpty(callBack.getSubId())){
						// SubApp
						// subApp=subAppService.findSubAppBySubid(callBack.getSubId());
						// if(subApp==null){
						// respCode=ConstantsEnum.REST_SUBID_NO;
						// respDesc=ConstantsEnum.REST_SUBID_NO;
						// flag=false;
						// }else if(!appid.equals(subApp.getAppid())){
						// respCode=ConstantsEnum.REST_SUBID_NOT_APPID;
						// respDesc=ConstantsEnum.REST_SUBID_NOT_APPID;
						// flag=false;
						// }else if(!"00".equals(subApp.getStatus())){
						// respCode=ConstantsEnum.REST_SUBID_ABNORMAL;
						// respDesc=ConstantsEnum.REST_SUBID_ABNORMAL;
						// flag=false;
						// }
						// }
						if (flag) {
							// TODO OK
							// 隐私号码为主被叫号码对应的平台动态分配的号码，用于显号。
							// 若映射不存在或不正确不能发起呼叫
							///////////////////////////////
							boolean result = MakeService.getInstance().existRelation(callBack.getAppId(),
									callBack.getFrom(), callBack.getTo(), callBack.getMaskNumber());
							if (result == false) {
								// 映射不存在或不正确不能发起呼叫
								respCode = ConstantsEnum.REST_MASKNUMREF_NOTEXIST.getCode();
								respDesc = ConstantsEnum.REST_MASKNUMREF_NOTEXIST.getDesc();
							} else {
								respCode = ConstantsEnum.REST_SUCCCODE.getCode();
								respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();

								//获取语音编码
								CbVoiceCode cbVoiceCode = voiceCodeService.findVoiceCodeByBusCode("mask");
								//设置语音编码
								if(cbVoiceCode != null) callBack.setCodec(cbVoiceCode.getVoiceCode());

								// TODO OK
								// 拼接RN字段
								restService.callBackRn(callBack);

								//填充录音标识
								callBack.setRecordFlag(restService.getRecordFlag(accountSid));

								// 转换成json格式报文
								String body = new Gson().toJson(callBack);
								logger.info("发送CB报文体：" + body);
								// CB报文头4域值
								String v1 = version;// 版本号
								String v2 = String.valueOf(System.currentTimeMillis());// 消息流水号
								String v3 = callId;// 32位标识号
								String v4 = ConstantsEnum.REST_MASK_REQUEST_CODE.getStrValue();// 消息类型
								String v5 = sysPropertits.getSnCode();// 设备码
								// 调用CB发送方法

								Server server = InitApp.getInstance().getBestServer();
								boolean status = InitApp.getInstance().sendMsg(CBTcpClient.hbMsg(v1, v2, v3, v4, v5, body),
										server);
								if (!status) {
									// 发送CB失败，返回未知错误
									respCode = ConstantsEnum.REST_UNKNOW.getCode();
									respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
								} else {
									// 缓存callid-->cb
									// 缓存时间原则：
									// 1、默认8小时过期
									// 2、有最大通话时长，按照最大通话时长过期
									int second = 8 * 60 * 60;
									if (callBack.getMaxCallTime() > 0) {
										second = callBack.getMaxCallTime();
									}
									logger.info("隐私回拨发起呼叫存储callID:{}",callId);
									jedisClusterUtil.STRINGS.setEx(callId,  1 * 60 * 60, server.getIp());
								}
							}

						}
					}
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}

}
