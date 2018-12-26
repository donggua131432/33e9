<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>取消通话接口</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/API.css"/>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="API_container">
    <div class="main_box">
        <div class="API_box">
            <div class="API_main1 f_left">
                <div class="api_left api_left_color">
                    <b class="api_txt"></b>
                    <span class="l_api">所有文档</span>
                </div>

                <jsp:include page="../common/api_nav.jsp"/>

            </div>
            <div class="API_main2 f_left" style="clear: both">
                <div id="apima2" style="position: fixed;width: 810px;top: 85px;height:36px;background: #ECECEC;"></div>
                <div id="api_ma2_1">
                    <div class="api_m2"><span>取消通话接口</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.取消通话接口介绍</span>
                            <p>
                                专线语音接口请求成功后，可调用取消专线语音通话接口（下简称：取消通话接口）来结束该次通话。
                            </p>
                        </div>
                        <div class="api_m2_box API2">
                            <span class="api_com_font">2.请求地址</span>
                            <p class="blueBg">/{softVersion}/Accounts/{accountSid}/Calls/CancelCallback</p>
                            <p>REST API是通过HTTPS POST方式请求。</p>
                        </div>
                        <div class="api_m2_box API3">
                            <span class="api_com_font">3.请求包头</span><br/>
                            <p><a href="<c:url value="/api/RESTInterface"/>?target=tybt" class="Aline" style="margin-left:30px;">请参阅请求统一包头</a></p>
                        </div>
                        <div class="api_m2_box API4">
                            <span class="api_com_font">4.请求包体</span>
                            <table class="API_style">
                                <tr>
                                    <td style="width:13%">属性</td>
                                    <td style="width:11%">类型</td>
                                    <td style="width:11%">约束</td>
                                    <td style="width:65%">说明</td>
                                </tr>
                                <tr>
                                    <td>appId</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>应用ID</td>
                                </tr>
                                <tr>
                                    <td>callId</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>回拨通话ID</td>
                                </tr>
                            </table>
                            <p class="APIsmFont">参数说明：</p>
                            <p>
                                (1).appId：应用ID<br/>
                                (2).callId：回拨通话ID<br/>
                            </p>
                            <p class="APImdFont">请求示例</p>
                            <span class="STop"><b class="icon guideTxt"></b>JSON请求示例</span>
                            <pre class="blueBg">
  POST
  /2016-01-01/Accounts/798b9ec0eaa94142852aa2c402570146/Calls/CancelCallback?sig=
  6949C5F977B304EC5BB969FD8838D345HTTP/1.1
  Host:api.33e9cloud.com
  content-length: 272
  Accept: application/json;
  Content-Type: application/json;charset=utf-8;
  Authorization: Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmM0AAI1NzAxNDY6MjAxNjAzMjQxNTE3MDE=

  {
      "req": {
           "appId": "feff3cbd52c041a1baab0492bee56423",
           "callId": "7f04cabc9478460b9661a2b52e98801e"
       }
   }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
  POST
  /2016-01-01/Accounts/798b9ec0eaa94142852aa2c402570146/Calls/CancelCallback?sig=
  230AB7B77C951C9DB5FECD55B3AFB3CFHTTP/1.1
  Host:api.33e9cloud.com
  content-length: 357
  Accept: application/xml;
  Content-Type: application/xml;charset=utf-8;
  Authorization: Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmMAADI1NzAxNDY6MjAxNjAzMjQxNjA2MjI=

      &lt;?xml version='1.0' encoding='utf-8'?&gt;
      &lt;req&gt;
          &lt;appId&gt;feff3cbd52c041a1bd5aa492bee56423&lt;/appId&gt;
          &lt;callId&gt;7f04cabc9478460b9661a2b52e98801e&lt;/callId&gt;
      &lt;/req&gt;
                            </pre>
                        </div>
                        <div class="api_m2_box API5">
                            <span class="api_com_font">5.响应包体</span>
                            <table class="API_style">
                                <tr>
                                    <td style="width: 13%">属性</td>
                                    <td style="width: 11%">类型</td>
                                    <td style="width: 11%">约束</td>
                                    <td style="width: 65%">说明</td>
                                </tr>
                                <tr>
                                    <td>statusCode</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>请求状态码，取值000000（成功），<a href="<c:url value="/api/globalErrorCode"/>" class="Aline">参考Rest 错误代码</a></td>
                                </tr>
                                <tr>
                                    <td>dateCreated</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>响应时间:格式"yyyy/MM/dd HH:mm:ss"；如：2016/01/01 12:00:00</td>
                                </tr>
                                <tr>
                                    <td>callId</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>回拨通话ID</td>
                                </tr>
                                <tr>
                                    <td>represent</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>错误描述，报错时返回，正常时不返回</td>
                                </tr>
                            </table>
                            <p class="APImdFont">响应示例</p>
                            <span class="STop"><b class="icon guideTxt"></b>JSON响应示例</span>
                            <pre class="blueBg">
  POST
  HTTP/1.1 200 OK 
  Content-Length: 126
  {
    "resp": {
      "statusCode": "000000",
      "dateCreated": "2016/03/24 15:36:52",
      "callId": "7f04cabc9478460b9661a2b52e98801e"
    }
  }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML响应示例</span>
                            <pre class="blueBg">
  HTTP/1.1 200 OK 
  Content-Length: 197
  &lt;?xml version='1.0'?&gt;
  &lt;resp&gt;
      &lt;statusCode&gt;000000&lt;/statusCode>
      &lt;dateCreated&gt;2016/03/24 16:12:44&lt;/dateCreated&gt;
      &lt;callId&gt;9d4c26f49d7f45b6917c105374bf4fc6&lt;/callId&gt;
  &lt;/resp&gt;
                            </pre>
                        </div>
                    </div>
                </div>
            </div>
            <div class="API_main3 f_left">
                <div class="stb"> <span class="click_tb spread_tb"></span></div>
                <div class="api_right_box fireHeight">
                    <div class="api_right_center">
                        <p class="b1"><b class="b"></b>1&nbsp;取消通话接口介绍</p>
                        <p class="b2"><b class="b"></b>2&nbsp;请求地址</p>
                        <p class="b3"><b class="b"></b>3&nbsp;请求包头</p>
                        <p class="b4"><b class="b"></b>4&nbsp;请求包体</p>
                        <p class="b5"><b class="b"></b>5&nbsp;响应包体</p>
                    </div>
                </div>
                <div class="xtb"><span class="click_tb close_tb"></span></div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/api.js"></script>
</body>
</html>