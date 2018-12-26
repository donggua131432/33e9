package com.e9cloud.rest.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mybatis.domain.CbVoiceCode;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.rest.cb.RestStatisTask;
import com.e9cloud.rest.cb.StatisMsgManager;
import com.e9cloud.util.*;
import com.google.gson.GsonBuilder;
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
import com.e9cloud.redis.util.RedisDBUtils;
import com.e9cloud.rest.cb.CBTcpClient;
import com.e9cloud.rest.cb.Server;
import com.e9cloud.rest.obt.CallBack;
import com.e9cloud.rest.obt.CancelCallBack;
import com.e9cloud.rest.obt.OnlineConcurrent;
import com.e9cloud.rest.service.RestService;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2015/12/16.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class CallBackController {
	private static final Logger logger = LoggerFactory.getLogger(CallBackController.class);

	@Value("#{configProperties['rest.version']}")
	private String version;

	@Autowired
	private SysPropertits sysPropertits;

	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private AccountService accountService;
	@Autowired
	private RestService restService;

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

	@RequestMapping(method = RequestMethod.POST, value = "Calls/callBack", consumes = "application/json")
	public void callBackJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		String subrespCode = null;// 子响应码
		// 生成callId和请求时间
		String callId = ID.randomUUID();
		CallBack callBack = new CallBack();
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
			logger.info("获取AS-callBackJson报文：" + asBody);
			//进行根元素匹配限制，为了兼容以前的jar包，以前提供的jar包统一是已req为空，所以兼容req和callback两种
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("callback")||root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-JSON报文去掉最外层后："+asBody);
				// 将对应字段填充到报文对象

				callBack = new Gson().fromJson(asBody, CallBack.class);
				callBack.setReqDate(new Date());
				// 填充accountSid和callId
				callBack.setAccountSid(accountSid);
				callBack.setCallSid(callId);

				// 进行呼叫回拨业务操作
				Map<String, String> map = callBack(callBack);
				respCode = map.get("respCode");
				respDesc = map.get("respDesc");
				subrespCode = map.get("subrespCode");
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
		callBackReturn(true, respCode, subrespCode, dateCreated, callId, respDesc, response);
		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/callBack", consumes = "application/xml")
	public void callBackXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		String subrespCode = null;// 子响应码
		CallBack callBack = new CallBack();
		callBack.setReqDate(new Date());
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
			logger.info("获取AS-XML报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制，为了兼容以前的jar包，以前提供的jar包xml统一是已func参数为根，所以兼容req和func两种
			String root=mapXML.get("root");
			logger.info("root");
			if(root.equals("callback")||root.equals("req")||root.equals("Calls")){
				// 获取相应报文字段
				String appId = mapXML.get("appId");
				String subId = mapXML.get("subId");
				String from = mapXML.get("from");
				String to = mapXML.get("to");
				String fromSerNum = mapXML.get("fromSerNum");// [0001,0002] 格式
				String toSerNum = mapXML.get("toSerNum");// [0001, 0002] 格式
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

				List fromSerNumList = null;
				List toSerNumList = null;
				if (fromSerNum != null && !"".equals(fromSerNum) && !"[]".equals(fromSerNum)) {
					// 将数组格式字符串进行相关处理，放入list中
					fromSerNumList = new ArrayList();
					fromSerNum = fromSerNum.replace("[", "");
					fromSerNum = fromSerNum.replace("]", "");
					String[] str = fromSerNum.split(",");
					for (String temp : str) {
						fromSerNumList.add(temp);
					}
				}
				if (toSerNum != null && !"".equals(toSerNum) && !"[]".equals(toSerNum)) {
					// 将数组格式字符串进行相关处理，放入list中
					toSerNumList = new ArrayList();
					toSerNum = toSerNum.replace("[", "");
					toSerNum = toSerNum.replace("]", "");
					String[] str = toSerNum.split(",");
					for (String temp : str) {
						toSerNumList.add(temp);
					}
				}
				// //解析对方发来的xml数据，获得EventID节点的值
				// DocumentBuilderFactory dbf =
				// DocumentBuilderFactory.newInstance();
				// DocumentBuilder db = dbf.newDocumentBuilder();
				// Document d = db.parse(request.getInputStream());
				// //获取相应报文字段
				// //对应报文字段为空时，获取会报空指针异常
				// String appId =
				// d.getElementsByTagName("appId").item(0).getFirstChild().getNodeValue();
				// String subId =
				// d.getElementsByTagName("subId").item(0).getFirstChild().getNodeValue();
				// String from =
				// d.getElementsByTagName("from").item(0).getFirstChild().getNodeValue();
				// String to =
				// d.getElementsByTagName("to").item(0).getFirstChild().getNodeValue();
				// String fromSerNum =
				// d.getElementsByTagName("fromSerNum").item(0).getFirstChild().getNodeValue();
				// String toSerNum =
				// d.getElementsByTagName("toSerNum").item(0).getFirstChild().getNodeValue();
				// int maxCallTime =
				// Integer.parseInt(d.getElementsByTagName("maxCallTime").item(0).getFirstChild().getNodeValue());
				// int needRecord =
				// Integer.parseInt(d.getElementsByTagName("needRecord").item(0).getFirstChild().getNodeValue());
				// int countDownTime =
				// Integer.parseInt(d.getElementsByTagName("countDownTime").item(0).getFirstChild().getNodeValue());
				// String userData =
				// d.getElementsByTagName("userData").item(0).getFirstChild().getNodeValue();
				// 将对应字段填充到报文对象

				callBack.setAppId(appId);
				callBack.setSubId(subId);
				callBack.setFrom(from);
				callBack.setTo(to);
				callBack.setFromSerNum(fromSerNumList);
				callBack.setToSerNum(toSerNumList);
				callBack.setMaxCallTime(maxCallTime);
				callBack.setNeedRecord(needRecord);
				callBack.setCountDownTime(countDownTime);
				callBack.setUserData(userData);
				// 填充accountSid和callId
				callBack.setAccountSid(accountSid);
				callBack.setCallSid(callId);

				// 进行呼叫回拨业务操作
				Map<String, String> map = callBack(callBack);
				respCode = map.get("respCode");
				respDesc = map.get("respDesc");
				subrespCode = map.get("subrespCode");
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
		callBackReturn(false, respCode, subrespCode, dateCreated, callId, respDesc, response);
		model.addAttribute("statusCode", 200);
	}

	private void callBackReturn(boolean isJson, String respCode, String subrespCode, String dateCreated, String callId,
			String respDesc, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				// 子错误码不为空时，加上子错误码
				if (Utils.notEmpty(subrespCode)) {
					respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"subStatusCode\":\"" + subrespCode
							+ "\",\"dateCreated\":\"" + dateCreated + "\",\"callId\":\"" + callId
							+ "\",\"represent\":\"" + respDesc + "\"}}";
				} else {
					respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
							+ "\",\"callId\":\"" + callId + "\"}}";
				}

			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				// 子错误码不为空时，加上子错误码
				if (Utils.notEmpty(subrespCode)) {
					respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><subStatusCode>"
							+ subrespCode + "</subStatusCode><dateCreated>" + dateCreated + "</dateCreated><callId>"
							+ callId + "</callId><represent>" + respDesc + "</represent></resp>";
				} else {
					respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
							+ dateCreated + "</dateCreated><callId>" + callId + "</callId></resp>";
				}
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><represent>" + respDesc + "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_callBack报文：" + respBody);
	}

	private Map<String, String> callBack(CallBack callBack) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String subrespCode = null;// 子响应码
		// 生成callId和请求时间
		String callId = callBack.getCallSid();
		String accountSid = callBack.getAccountSid();

		// 必选字段非空验证
		Map map = CallBackValid.baseValid(callBack);
		if (!(boolean) map.get("flag")) {
			respCode = map.get("respCode").toString();
			respDesc = map.get("respDesc").toString();
		} else {
			// 根据appid查找应用信息
			String appid = callBack.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			if(callBack.getFrom().equals(callBack.getTo())){
				respCode = ConstantsEnum.REST_FROM_TO_SAME.getCode();
				respDesc = ConstantsEnum.REST_FROM_TO_SAME.getDesc();
			// 应用信息不正常，返回对应的错误码报文
			}else if (appInfo == null || !"02".equals(appInfo.getBusType())) {
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
						// SubApp subApp=subAppService.findSubAppBySubid(callBack.getSubId());
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
							//主被叫显号处理
							map = restService.callBackXh(callBack,numberBlacks);
							// 主叫为物联网卡时，如被叫显号未是绑定的号码，返回错误码限制呼叫
							if (!(boolean) map.get("flag")) {
								//根据被叫显号字段是否为空，区分两种错误
								if(Utils.notEmpty(callBack.getToSerNum())){
									//被叫显号不能为被叫号码本身
									respCode = ConstantsEnum.REST_TOSERNUM_NOSELF.getCode();
									respDesc = ConstantsEnum.REST_TOSERNUM_NOSELF.getDesc();
								}else{
									//被叫显号属于未绑定号码
									respCode = ConstantsEnum.REST_TOSERNUM_UNBIND.getCode();
									respDesc = ConstantsEnum.REST_TOSERNUM_UNBIND.getDesc();
								}
							} else {
								subrespCode = map.get("subrespCode").toString();
								respDesc = map.get("respDesc").toString();

								respCode = ConstantsEnum.REST_SUCCCODE.getCode();
								// 填充callSid
								callBack.setCallSid(callId);
								// 填充accountSid
								callBack.setAccountSid(accountSid);

								// 添加rn码
								restService.callBackRn(callBack);

								//填充录音标识
								callBack.setRecordFlag(restService.getRecordFlag(accountSid));

								//设置语音编码
								//获取语音编码
								CbVoiceCode cbVoiceCode = voiceCodeService.findVoiceCodeByBusCode("rest");
								if(cbVoiceCode != null) callBack.setCodec(cbVoiceCode.getVoiceCode());

								// 转换成json格式报文
								String body = new Gson().toJson(callBack);
								logger.info("发送CB报文体：" + body);
								// CB报文头4域值
								String v1 = version;// 版本号
								String v2 = String.valueOf(System.currentTimeMillis());// 消息流水号
								String v3 = callId;// 32位标识号
								String v4 = ConstantsEnum.REST_REQUEST_CODE.getStrValue();// 消息类型
								String v5 = sysPropertits.getSnCode();// 设备码
								// 调用CB发送方法
								Server server = InitApp.getInstance().getBestServer();
								boolean status = InitApp.getInstance().sendMsg(CBTcpClient.hbMsg(v1, v2, v3, v4, v5, body),
										server);
								if (!status) {
									// 发送CB失败，返回未知错误
									respCode = ConstantsEnum.REST_UNKNOW.getCode();
									respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
									logger.info(respDesc);
								} else {
									// 缓存callit-->cb
									// 缓存时间原则：
									// 1、默认8小时过期
									// 2、有最大通话时长，按照最大通话时长过期
									int second = 8 * 60 * 60;
									if (callBack.getMaxCallTime() > 0) {
										second = callBack.getMaxCallTime();
									}
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
		map.put("subrespCode", subrespCode);
		return map;
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/QueryOnlineConcurrent", consumes = "application/json")
	public void QueryOnlineConcurrentJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		String concurrentNumber = null;// 瞬时在线并发通话数
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");

			// 获取AS-QueryOnlineConcurrentJson报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-QueryOnlineConcurrentJson报文：" + asBody);
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")) {
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-QueryOnlineConcurrentJson报文去掉最外层后："+asBody);
				// 将对应字段填充到报文对象
				OnlineConcurrent onlineConcurrent = new OnlineConcurrent();
				onlineConcurrent = new Gson().fromJson(asBody, OnlineConcurrent.class);

				// 必选字段非空验证
				if (onlineConcurrent.getAppId() == null || "".equals(onlineConcurrent.getAppId())) {
					respCode = ConstantsEnum.REST_APPID_NULL.getCode();
					respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
					logger.info(respDesc);
				} else if (onlineConcurrent.getQueryTime() == null || "".equals(onlineConcurrent.getQueryTime())) {
					respCode = ConstantsEnum.REST_QUERYTIME_NULL.getCode();
					respDesc = ConstantsEnum.REST_QUERYTIME_NULL.getDesc();
					logger.info(respDesc);
				} else if (onlineConcurrent.getQueryTime().length() != 12
						|| !Utils.isDigit(onlineConcurrent.getQueryTime())) {
					respCode = ConstantsEnum.REST_QUERYTIME_FORMAT.getCode();
					respDesc = ConstantsEnum.REST_QUERYTIME_FORMAT.getDesc();
					logger.info(respDesc);
				} else {
					// 根据appid查找应用信息
					String appid = onlineConcurrent.getAppId();
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
					} else {
						respCode = ConstantsEnum.REST_SUCCCODE.getCode();
						// REST依据查询的时间组装key, 从Redis中获取并发在线数据
						// Key=”c1_appid_yyyymmddhhmm”
						String key = "c1_" + appid + "_" + onlineConcurrent.getQueryTime();
						concurrentNumber = RedisDBUtils.get(key);
						// 如不存在，填充为0
						if (concurrentNumber == null || "".equals(concurrentNumber)) {
							concurrentNumber = "0";
						}
					}
				}
			}else{
				//报文根元素错误
				respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
				respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
			}
		} catch (JsonParseException e){
			//json转对象参数错误
			respCode = ConstantsEnum.REST_PARAM_ERROR.getCode();
			respDesc = ConstantsEnum.REST_PARAM_ERROR.getDesc();
			logger.info(respDesc);
			e.printStackTrace();
		} catch (Exception e) {
			respCode = ConstantsEnum.REST_UNKNOW.getCode();
			respDesc = ConstantsEnum.REST_UNKNOW.getCode();
			logger.info(respDesc);
			e.printStackTrace();
		}
		// 同步返回AS报文，返回对应json报文
		if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
			respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
					+ "\",\"concurrentNumber\":" + concurrentNumber + "}}";
		} else {
			respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated + "\"}}";
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS-QueryOnlineConcurrentJson报文：" + respBody);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/QueryOnlineConcurrent", consumes = "application/xml")
	public void QueryOnlineConcurrentXml(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		String concurrentNumber = null;// 瞬时在线并发通话数
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");

			// 获取AS-QueryOnlineConcurrentXML报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			String asBody = out.toString();
			logger.info("获取AS-QueryOnlineConcurrentXML报文：" + asBody);
			// 解析字符串xml
			Map<String, String> map = ReadXmlUtil.readXML(asBody);
			String root=map.get("root");
			logger.info("root");
			if(root.equals("callback")||root.equals("req")||root.equals("Calls")){
				// 获取相应报文字段
				String appId = map.get("appId");
				String queryTime = map.get("queryTime");

				// 将对应字段填充到报文对象
				OnlineConcurrent onlineConcurrent = new OnlineConcurrent();
				onlineConcurrent.setAppId(appId);
				onlineConcurrent.setQueryTime(queryTime);

				// 必选字段非空验证
				if (onlineConcurrent.getAppId() == null || "".equals(onlineConcurrent.getAppId())) {
					respCode = ConstantsEnum.REST_APPID_NULL.getCode();
					respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
					logger.info(respDesc);
				} else if (onlineConcurrent.getQueryTime() == null || "".equals(onlineConcurrent.getQueryTime())) {
					respCode = ConstantsEnum.REST_QUERYTIME_NULL.getCode();
					respDesc = ConstantsEnum.REST_QUERYTIME_NULL.getDesc();
					logger.info(respDesc);
				} else if (onlineConcurrent.getQueryTime().length() != 12
						|| !Utils.isDigit(onlineConcurrent.getQueryTime())) {
					respCode = ConstantsEnum.REST_QUERYTIME_FORMAT.getCode();
					respDesc = ConstantsEnum.REST_QUERYTIME_FORMAT.getDesc();
					logger.info(respDesc);
				} else {
					// 根据appid查找应用信息
					String appid = onlineConcurrent.getAppId();
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
					} else {
						respCode = ConstantsEnum.REST_SUCCCODE.getCode();
						// REST依据查询的时间组装key, 从Redis中获取并发在线数据
						// Key=”c1_appid_yyyymmddhhmm”
						String key = "cl_" + appid + "_" + onlineConcurrent.getQueryTime();
						concurrentNumber = RedisDBUtils.get(key);
						// 如不存在，填充为0
						if (concurrentNumber == null || "".equals(concurrentNumber)) {
							concurrentNumber = "0";
						}
					}
				}
			}else{
				//报文根元素错误
				respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
				respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
			}
		} catch (Exception e) {
			respCode = ConstantsEnum.REST_UNKNOW.getCode();
			respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
			logger.info(respDesc);
			e.printStackTrace();
		}

		// 同步返回AS报文,返回对应xml报文
		if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
			respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>" + dateCreated
					+ "</dateCreated><concurrentNumber>" + concurrentNumber + "</concurrentNumber></resp>";
		} else {
			respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>" + dateCreated
					+ "</dateCreated></resp>";
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_QueryOnlineConcurrentXML报文：" + respBody);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/CancelCallback", consumes = "application/json")
	public void CancelCallBackJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		String callId = null;
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-CancelCallBackJSON报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}

			String asBody = out.toString();
			logger.info("获取AS-CancelCallBackJSON报文：" + asBody);
			//进行根元素匹配限制
			Map root=new Gson().fromJson(asBody,Map.class);
			if(root.containsKey("req")){
				// 去掉报文最外层的元素，以便实体直接转换
				asBody = asBody.substring(asBody.indexOf("{", 1));
				asBody = asBody.substring(0, asBody.lastIndexOf("}"));
				// logger.info("AS-CancelCallBackJSON报文去掉最外层后："+asBody);
				// 将对应字段填充到报文对象
				CancelCallBack cancelCallBack = new CancelCallBack();
				cancelCallBack = new Gson().fromJson(asBody, CancelCallBack.class);
				cancelCallBack.setAccountSid(accountSid);
				callId = cancelCallBack.getCallId();

				// 进行取消回拨业务操作
				Map<String, String> map = cancelCallBack(cancelCallBack);
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
		cancelCallBackReturn(true, respCode, dateCreated, callId, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/CancelCallback", consumes = "application/xml")
	public void CancelCallBackXml(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String respBody = null;// 响应报文
		// 生成请求时间
		String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
		String callId = null;
		try {
			Map<String, String> params = (Map<String, String>) model.get("params");
			String accountSid = params.get("AccountSid");

			// 获取AS-CancelCallBackXML报文
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = request.getInputStream().read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			String asBody = out.toString();
			logger.info("获取AS-CancelCallBackXML报文：" + asBody);
			// 解析字符串xml
			Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
			//进行根元素匹配限制
			String root=mapXML.get("root");
			if(root.equals("req")||root.equals("Calls")){
				// 获取相应报文字段
				callId = mapXML.get("callId");
				String appId = mapXML.get("appId");

				// 将对应字段填充到报文对象
				CancelCallBack cancelCallBack = new CancelCallBack();
				cancelCallBack.setCallId(callId);
				cancelCallBack.setAppId(appId);
				cancelCallBack.setAccountSid(accountSid);

				// 进行取消回拨业务操作
				Map<String, String> map = cancelCallBack(cancelCallBack);
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
		cancelCallBackReturn(false, respCode, dateCreated, callId, respDesc, response);

		model.addAttribute("statusCode", 200);
	}

	private void cancelCallBackReturn(boolean isJson, String respCode, String dateCreated, String callId,
			String respDesc, HttpServletResponse response) throws Exception {
		String respBody = null;// 响应报文
		// 同步返回AS报文,返回对应json/xml报文
		if (isJson) {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"callId\":\"" + callId + "\"}}";
			} else {
				respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
						+ "\",\"callId\":\"" + callId + "\",\"represent\":\"" + respDesc + "\"}}";
			}
		} else {
			if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><callId>" + callId + "</callId></resp>";
			} else {
				respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
						+ dateCreated + "</dateCreated><callId>" + callId + "</callId><represent>" + respDesc
						+ "</represent></resp>";
			}
		}

		response.setCharacterEncoding("utf-8");// 避免中文乱码
		response.getWriter().print(respBody);
		logger.info("返回AS_cancelCallBack报文：" + respBody);
	}

	private Map<String, String> cancelCallBack(CancelCallBack cancelCallBack) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String subrespCode = null;// 子响应码
		// 生成callId和请求时间
		String callId = cancelCallBack.getCallId();
		String accountSid = cancelCallBack.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段非空验证
		if (!Utils.notEmpty(cancelCallBack.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
			logger.info(respDesc);
		} else if (!Utils.notEmpty(callId)) {
			respCode = ConstantsEnum.REST_CALLID_NULL.getCode();
			respDesc = ConstantsEnum.REST_CALLID_NULL.getDesc();
			logger.info(respDesc);
		} else {
			// 根据appid查找应用信息
			String appid = cancelCallBack.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			// 应用信息不正常，返回对应的错误码报文
			if (appInfo == null || (!"02".equals(appInfo.getBusType()) && !"05".equals(appInfo.getBusType()))) {
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
				} else if (!Utils.notEmpty(jedisClusterUtil.STRINGS.get(callId))) {
					// callId无效
					respCode = ConstantsEnum.REST_CALLID_INVALID.getCode();
					respDesc = ConstantsEnum.REST_CALLID_INVALID.getDesc();
					logger.info(respDesc);
				} else {
					respCode = ConstantsEnum.REST_SUCCCODE.getCode();

					String body = "{\"callSid\":\"" + callId + "\"}";
					logger.info("发送CB报文体：" + body);
					// CB报文头4域值
					String v1 = version;// 版本号
					String v2 = String.valueOf(System.currentTimeMillis());// 消息流水号
					String v3 = callId;// 32位标识号
					String v4 = ConstantsEnum.REST_CANCEL_CODE.getStrValue();// 消息类型
					String v5 = sysPropertits.getSnCode();// 设备码
					// 调用CB发送方法
					String cbip = jedisClusterUtil.STRINGS.get(callId);
					Server server = InitApp.getInstance().getApointCBServer(cbip);
					boolean status = InitApp.getInstance().sendMsg(CBTcpClient.hbMsg(v1, v2, v3, v4, v5, body), server);
					if (!status) {
						// 发送CB失败，返回未知错误
						respCode = ConstantsEnum.REST_UNKNOW.getCode();
						respDesc = ConstantsEnum.REST_UNKNOW.getDesc();
						logger.info(respDesc);
					}
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}

	@RequestMapping(method = RequestMethod.GET, value = "Calls/callBack")
	public String callBack(ModelMap model) throws Exception {
		Map<String, String> params = (Map<String, String>) model.get("params");

		model.addAttribute("statusCode", 200);
		System.out.println("callBack");
		return "callBack-xml";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(ModelMap model) throws Exception {
		Map<String, String> params = (Map<String, String>) model.get("params");

		model.addAttribute("statusCode", 200);
		System.out.println("doPost");
		return "callBack-xml";
	}

	@RequestMapping(method = RequestMethod.POST, value = "Calls/callBack2")
	public String callBack2(ModelMap model) throws Exception {
		Map<String, String> params = (Map<String, String>) model.get("params");
		System.out.println("callBack2");
		model.addAttribute("statusCode", 200);
		RedisDBUtils.set("cl_b6f55ae1e5344782bccf03a8fc526460_201603091528", "8000");
		System.out.println(RedisDBUtils.get("cl_b6f55ae1e5344782bccf03a8fc526460_201603091528"));
		System.out.println(RedisDBUtils.get("pcc"));
		return "callBack-xml";
	}

}
