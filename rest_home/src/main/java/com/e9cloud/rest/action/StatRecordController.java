package com.e9cloud.rest.action;

import com.e9cloud.mybatis.domain.StatVnDayRecord;
import com.e9cloud.mybatis.service.VnService;
import com.e9cloud.util.ConstantsEnum;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/9/13.
 */
@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class StatRecordController {

    private static final Logger logger = LoggerFactory.getLogger(StatRecordController.class);

    @Autowired
    private VnService vnService;

    @RequestMapping(method = RequestMethod.POST, value = "VnRecord/query", consumes = "application/json")
    public void getVnRecordList(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        String respCode;// 响应码
        String respDesc;// 相应描述
        List<StatVnDayRecord> statVnDayRecordList = null;
        try {
            // 获取AS-queryJson报文
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = request.getInputStream().read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }

            String asBody = out.toString();
            logger.info("获取AS-queryJson报文：" + asBody);
            //进行根元素匹配限制
            String root=asBody.substring(0,asBody.indexOf("{", 1));
            if(root.contains("req")){
                // 去掉报文最外层的元素，以便实体直接转换
                asBody = asBody.substring(asBody.indexOf("{", 1));
                asBody = asBody.substring(0, asBody.lastIndexOf("}"));
                // logger.info("AS-JSON报文去掉最外层后："+asBody);
                // 将对应字段填充到报文对象
                Map<String, Object> paramsMap = new GsonBuilder().create().fromJson(asBody, Map.class);

                statVnDayRecordList = vnService.getStatVnDayRecordByMap(paramsMap);
                respCode = ConstantsEnum.REST_SUCCCODE.getCode();
                respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
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
        queryReturn(respCode, respDesc, statVnDayRecordList,  response);
        model.addAttribute("statusCode", 200);
    }

    private void queryReturn(String respCode, String respDesc, List<StatVnDayRecord> statVnDayRecordList,
                              HttpServletResponse response) throws Exception {
        String respBody;// 响应报文
        // 同步返回AS报文,返回对应json/xml报文
        if (ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)) {
            respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"statVnDayRecordList\":"+new GsonBuilder().create().toJson(statVnDayRecordList)+"}}";
        } else {
            respBody = "{\"resp\":{\"statusCode\":\"" + respCode + "\",\"represent\":\"" + respDesc + "\"}}";
        }
        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(respBody);
        logger.info("返回AS_Records/query报文：" + respBody);
    }
}
