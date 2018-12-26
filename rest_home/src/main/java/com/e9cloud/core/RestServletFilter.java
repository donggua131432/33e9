package com.e9cloud.core;

import com.e9cloud.core.application.InitApp;
import com.e9cloud.core.common.ResponseWrapper;
import com.e9cloud.mongodb.domain.RestReqResInfo;
import com.e9cloud.rest.cb.RestStatisTask;
import com.e9cloud.rest.cb.StatisMsgManager;
import com.e9cloud.util.Constants;
import com.e9cloud.util.ReadXmlUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dukai on 2016/11/25.
 */
public class RestServletFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RestServletFilter.class);

    private static String version = InitApp.getInstance().getValue("rest.version", "");
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestUrl = httpRequest.getServletPath();
        logger.info("requestUrl {}",requestUrl);
        if(requestUrl.contains("/"+version+"/Accounts")){
            if(!requestUrl.contains("/VnRecord/query") && !requestUrl.contains("/Subid/")
                    && !requestUrl.contains("/Calls/sfForwardUpdate")){

                RestReqResInfo restReqResInfo =  new RestReqResInfo();

                String[] urlArray = requestUrl.split("/");
                restReqResInfo.setSid(urlArray[3]);
                String funStr = urlArray[4]+"_"+urlArray[5];

                Map<String, String> map = Constants.getLogTypeAndName(funStr);
                restReqResInfo.setReqType(map.get("strType"));
                restReqResInfo.setReqName(map.get("strName"));

                String accept = httpRequest.getHeader("Accept").split("/")[1];
                restReqResInfo.setAccept(accept);
                restReqResInfo.setReqUrl(requestUrl);

                //使用自定义的响应包装器来包装原始的ServletResponse
                ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);
                chain.doFilter(request,responseWrapper);
                //获取截获的结果
                String result = responseWrapper.getResult();
                logger.info("拦截的响应信息result{}",result);
                try {
                    if("json".equals(accept)){
                        Map jsonMap = new Gson().fromJson(result, Map.class);
                        restReqResInfo.setRespInfo(jsonMap);
                        Map<String, String> resp = null;
                        if(jsonMap.containsKey("resp")){
                            resp = (Map<String, String>) jsonMap.get("resp");
                        }
                        if(resp.containsKey("statusCode")){
                            restReqResInfo.setRespCode(resp.get("statusCode"));
                        }
                    }else{
                        Map xmlMap = ReadXmlUtil.readXML(result);
                        restReqResInfo.setRespInfo(xmlMap);
                        if(xmlMap.containsKey("statusCode")){
                            restReqResInfo.setRespCode((String) xmlMap.get("statusCode"));
                        }
                    }
                } catch (Exception e) {
                    logger.info("拦截的响应信息转换数据异常{}",e);
                    logger.error("拦截的响应信息转换数据异常{}",e);
                }
                //将信息记入mongodb
                //StatisMsgManager.getInstance().addParseMsg(new RestStatisTask(restReqResInfo));
                StatisMsgManager.getInstance().addParseMsg(restReqResInfo);
                //输出结果
                PrintWriter out = response.getWriter();
                out.print(result);
                out.flush();
                out.close();
            }else{
                chain.doFilter(request,response);
            }
        }else{
            logger.info("-----------------非rest业务接口-----------------");
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
