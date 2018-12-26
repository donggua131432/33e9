package com.e9cloud.rest.action;

import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.obt.CallNotifyHttp;
import com.e9cloud.rest.obt.CallNotifyReq;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/07/05.
 */

@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class RecordsController {
    private static final Logger logger = LoggerFactory.getLogger(RecordsController.class);

    @Value("#{configProperties['rest.version']}")
    private String version;
    @Value("#{configProperties['records.cycle']}")
    private int cycle;
    @Value("#{configProperties['records.times']}")
    private int times;

    @Autowired
    private AccountService accountService;
    @Autowired
    private JedisClusterUtil jedisClusterUtil;

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

    @RequestMapping(method = RequestMethod.POST, value = "Records/query", consumes = "application/json")
    public void queryJson(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        String respCode = null;// 响应码
        String respDesc = null;// 相应描述
        CallNotifyHttp callNotify = new CallNotifyHttp();
        // 生成请求时间
        String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);

        CallNotifyReq callNotifyReq = new CallNotifyReq();
        try {
            Map<String, String> params = (Map<String, String>) model.get("params");
            String accountSid = params.get("AccountSid");

            // 获取AS-queryJson报文
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = request.getInputStream().read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }

            String asBody = out.toString();
            logger.info("获取AS-queryJson报文：" + asBody);
            //进行根元素匹配限制
            String root = asBody.substring(0, asBody.indexOf("{", 1));
            if (root.contains("req")) {
                // 去掉报文最外层的元素，以便实体直接转换
                asBody = asBody.substring(asBody.indexOf("{", 1));
                asBody = asBody.substring(0, asBody.lastIndexOf("}"));
                // logger.info("AS-JSON报文去掉最外层后："+asBody);
                // 将对应字段填充到报文对象
                callNotifyReq = new Gson().fromJson(asBody, CallNotifyReq.class);
                // 填充accountSid和callId
                callNotifyReq.setAccountSid(accountSid);

                // 进行呼叫回拨业务操作
                Map<String, Object> map = query(callNotifyReq);
                respCode = map.get("respCode").toString();
                respDesc = map.get("respDesc").toString();
                callNotify = (CallNotifyHttp) map.get("callNotify");
            } else {
                //报文根元素错误
                respCode = ConstantsEnum.REST_ROOT_ERROR.getCode();
                respDesc = ConstantsEnum.REST_ROOT_ERROR.getDesc();
            }
            logger.info(respDesc);

        } catch (JsonParseException e) {
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
        queryReturn(true, respCode, dateCreated, callNotifyReq.getType(), callNotify, respDesc, response);

        model.addAttribute("statusCode", 200);

    }

    @RequestMapping(method = RequestMethod.POST, value = "Records/query", consumes = "application/xml")
    public void queryXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        String respCode = null;// 响应码
        String respDesc = null;// 相应描述
        CallNotifyHttp callNotify = new CallNotifyHttp();
        // 生成请求时间
        String dateCreated = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME);
        CallNotifyReq callNotifyReq = new CallNotifyReq();
        try {
            Map<String, String> params = (Map<String, String>) model.get("params");
            String accountSid = params.get("AccountSid");

            // 获取AS-queryXML报文
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = request.getInputStream().read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
            String asBody = out.toString();
            logger.info("获取AS-queryXML报文：" + asBody);
            // 解析字符串xml
            Map<String, String> mapXML = ReadXmlUtil.readXML(asBody);
            //进行根元素匹配限制
            String root = mapXML.get("root");
            if (root.contains("req")) {
                // 获取相应报文字段
                String callId = mapXML.get("callId");
                int type = Utils.strToInteger(mapXML.get("type"));

                // 将对应字段填充到报文对象
                callNotifyReq.setCallId(callId);
                callNotifyReq.setType(type);
                // 填充accountSid
                callNotifyReq.setAccountSid(accountSid);

                // 进行呼叫回拨业务操作
                Map<String, Object> map = query(callNotifyReq);
                respCode = map.get("respCode").toString();
                respDesc = map.get("respDesc").toString();
                callNotify = (CallNotifyHttp) map.get("callNotify");
            } else {
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
        queryReturn(false, respCode, dateCreated, callNotifyReq.getType(), callNotify, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    private void queryReturn(boolean isJson, String respCode, String dateCreated, int type, CallNotifyHttp callNotify,
                             String respDesc, HttpServletResponse response) throws Exception {
        String respBody = null;// 响应报文
        // 同步返回AS报文,返回对应json/xml报文
        if (isJson) {
            if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
                respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
                        + "\",\"type\":" + type + ",\"callNotify\":{" + callNotify.toRecordJson(type) + "}}}";
            } else {
                respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"dateCreated\":\"" + dateCreated
                        + "\",\"represent\":\"" + respDesc + "\",\"type\":" + type + "}}";
            }
        } else {
            if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
                respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
                        + dateCreated + "</dateCreated><type>" + type + "</type>" +
                        "<callNotify>" + callNotify.toRecordXML(type) + "</callNotify></resp>";
            } else {
                respBody = "<?xml version='1.0'?><resp><statusCode>" + respCode + "</statusCode><dateCreated>"
                        + dateCreated + "</dateCreated><represent>" + respDesc + "</represent><type>" + type + "</type></resp>";
            }
        }

        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(respBody);
        logger.info("返回AS_Records/query报文：" + respBody);
    }

    private Map<String, Object> query(CallNotifyReq callNotifyReq) throws Exception {
        String respCode = null;// 响应码
        String respDesc = null;// 相应描述
        CallNotifyHttp callNotify = new CallNotifyHttp();// 响应报文

        String callId = callNotifyReq.getCallId();
        int type = callNotifyReq.getType();
        String accountSid = callNotifyReq.getAccountSid();

        Map map = new HashMap<>();
        // 必选字段非空验证
        if (!Utils.notEmpty(callId)) {
            respCode = ConstantsEnum.REST_CALLID_NULL.getCode();
            respDesc = ConstantsEnum.REST_CALLID_NULL.getDesc();
        } else if (type <= 0) {
            respCode = ConstantsEnum.REST_TYPE_NULL.getCode();
            respDesc = ConstantsEnum.REST_TYPE_NULL.getDesc();
        } else if (!timesLimit(accountSid)) {
            respCode = ConstantsEnum.REST_CALLNOTIFY_TIMESLIMIT.getCode();
            respDesc = ConstantsEnum.REST_CALLNOTIFY_TIMESLIMIT.getDesc();
        } else {
            // 获取用户
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
                //根据callId查询通话挂机消息
                Query query = new Query();
                query.addCriteria(Criteria.where("callSid").is(callId));
                //query.addCriteria(Criteria.where("type").is(type));
                //查询两张表 , callNorifyHttp 和 callNotify
                callNotify = mongoDBDao.findOne(query, CallNotifyHttp.class, "callNotifyHttp");
                if (!Utils.notEmpty(callNotify)) {
                    callNotify = mongoDBDao.findOne(query, CallNotifyHttp.class, "callNotify");
                }
                if (!Utils.notEmpty(callNotify)) {
                    //callId不存在
                    respCode = ConstantsEnum.REST_CALLID_INVALID.getCode();
                    respDesc = ConstantsEnum.REST_CALLID_INVALID.getDesc();
                } else {
                    //计算48小时范围
                    long curTime = new Date().getTime();//当前时间毫秒数
                    long stampTime = DateUtil.strToDate(callNotify.getDateCreated(), "yyyyMMddHHmmss").getTime();//挂机消息时间毫秒数
                    //两个时间间隔毫秒数，转成double，便于转换小时精确保留小数
                    double timeNumMS = curTime - stampTime;
                    //两个时间间隔小时数
                    double timeNum = timeNumMS / 1000 / 60 / 60;
                    if (timeNum > 48d) {
                        //通话记录已超过48小时
                        respCode = ConstantsEnum.REST_CALLNOTIFY_OUTOF48.getCode();
                        respDesc = ConstantsEnum.REST_CALLNOTIFY_OUTOF48.getDesc();
                    } else {
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

    /**
     * 同一用户访问次数限制
     * 超限制返回false，反之，计数加1，返回true;
     **/
    private boolean timesLimit(String key) throws Exception {
        int num = 0;
        String value = jedisClusterUtil.STRINGS.get(key);
        if (Utils.notEmpty(value)) {
            num = Integer.parseInt(value);
        }
        if (num >= times) {
            // to do
            // 当前发现一个redis的bug，如果计数器超时时间剩余1的时候,redis的key卡死，不自动清除，删除这个key
            if (jedisClusterUtil.KEYS.ttl(key) == -1) {
                jedisClusterUtil.KEYS.del(key);
            }
            return false;
        } else if (num == 0) {
            //第一次设置过期时间
            Long l = jedisClusterUtil.STRINGS.incr(key);
            jedisClusterUtil.KEYS.expired(key, cycle);
            //System.out.println("第0次incr后："+l);
        } else {
            Long l = jedisClusterUtil.STRINGS.incr(key);
            //每次加1累积次数
            if (jedisClusterUtil.KEYS.ttl(key) == -1) {
                jedisClusterUtil.KEYS.expired(key, cycle);
            }

            //System.out.println("第"+num+"次incr后："+l);
        }
        return true;
    }
}
