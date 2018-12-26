<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>实时在线通话数查询接口</title>
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
                    <div class="api_m2"><span>实时在线通话数查询接口</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.实时在线通话数接口介绍</span>
                            <p>
                                玖云平台提供基于应用的实时在线通话数查询接口。开发者可通过接口查询实时在线通话数，通话数以一分钟为间隔生成，返回请求时间点（精确到分钟）前一分钟的并发峰值，例如传入201603041122，则返回11:21:00~11:21:59秒的并发峰值数，开发者可轻松、便捷的监控其接口使用情况。<br/>
                                玖云平台实时在线通话数查询REST接口通过HTTPS GET方式提交请求。<br/>
                            </p>
                            <p>
                                <a href="#" class="load"> <b class="icon downTxt"></b>接口示例代码（即Demo）下载</a>
                            </p>
                        </div>
                        <div class="api_m2_box API2">
                            <span class="api_com_font">2.请求地址</span>
                            <p class="blueBg">/{softVersion}/Accounts/{accountSid}/ Calls/QueryOnlineConcurrent</p>
                        </div>
                        <div class="api_m2_box API3">
                            <span class="api_com_font">3.请求包头</span>
                            <p><a href="#" class="Aline" style="margin-left:30px;">请参阅请求统一包头</a></p>
                        </div>
                        <div class="api_m2_box API4">
                            <span class="api_com_font">4.请求包体</span>0
                            <table class="API_style">
                                <tr>
                                    <td>属性</td>
                                    <td>类型</td>
                                    <td>约束</td>
                                    <td>说明</td>
                                </tr>
                                <tr>
                                    <td>appId</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>应用ID</td>
                                </tr>
                                <tr>
                                    <td>queryTime</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>请求查询的时间，精确到分钟，格式"yyyyMMddHHmm"。如：201404161420</td>
                                </tr>
                            </table>
                            <p class="APIsmFont">参数说明：</p>
                            <p>
                                (1)appId：应用ID<br/>
                                (2)queryTime：获取并发量的时间点，精确到分钟
                            </p>
                            <p class="APImdFont">请求示例</p>
                            <span class="STop"><b class="icon guideTxt"></b>JSON请求示例</span>
                            <pre class="blueBg">
   POST
   /2016-01-01/Accounts/798b9ec0eaaa4142852602c402570146/Calls/QueryOnlineConcurrent?sig=
   E36F97B23F1FF9FF05B02BFB34E68BBDHTTP/1.1
   Host:127.0.0.1:9080
   content-length: 79
   Accept: application/json;
   Content-Type: application/json;charset=utf-8;
   Authorization:Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmMAADI1NzAxNDY6MjAxNjAzMjQxNzAwMzE=

   {
       "req": {
           "appId": "feff3cbd52c041aabd5b0492bee56423",
           "queryTime": "201603091528"
       }
   }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
   POST
   /2016-01-01/Accounts/798b9ec0eaaa4142852602c402570146/Calls/QueryOnlineConcurrent?sig=
   E3F434C282C946E839911247F6E7F889HTTP/1.1
   Host:127.0.0.1:9080
   content-length: 131
   Accept: application/xml;
   Content-Type: application/xml;charset=utf-8;
   Authorization:MmM1NjU5OTMyODhmNDA5N2IyMmUzOGY1MDBjOTFaaTI6MjAxNjAzMjQxNzA4MzA=
   &lt;?xml version='1.0' encoding='utf-8'?&gt;
   &lt;req&gt;
   &lt;appId&gt;feff3cbd52c041a1bd5b0aa2bee56423&lt;/appId&gt;
   &lt;queryTime&gt;201603091528&lt;/queryTime&gt;
   &lt;/req&gt;
                            </pre>
                        </div>
                        <div class="api_m2_box API5">
                            <span class="api_com_font">5.响应包体</span>
                            <table class="API_style">
                                <tr>
                                    <td>属性</td>
                                    <td>类型</td>
                                    <td>约束</td>
                                    <td>说明</td>
                                </tr>
                                <tr>
                                    <td>statusCode</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>请求状态码，取值000000（成功），可参考Rest 错误代码。</td>
                                </tr>
                                <tr>
                                    <td>dateCreated</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>响应时间:格式"yyyy/MM/dd HH:mm:ss"。如：2016/01/01 12:00:00</td>
                                </tr>
                                <tr>
                                    <td>concurrentNumber</td>
                                    <td>long</td>
                                    <td>必选</td>
                                    <td>瞬时在线并发通话数。可稍有延时的统计数据</td>
                                </tr>
                            </table>
                            <p class="APImdFont">响应示例</p>
                            <span class="STop"><b class="icon guideTxt"></b>JSON响应示例</span>
                            <pre class="blueBg">
   HTTP/1.1 200 OK 
   Content-Length: 89
   {
       "resp": {
           "statusCode": "000000",
           "dateCreated": "2016/03/24 17:03:00",
           "concurrentNumber": 100
       }
   }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML响应示例</span>
                            <pre class="blueBg">
   HTTP/1.1 200 OK
   Content-Length: 153
   &lt;?xml version='1.0'?&gt;
   &lt;resp&gt;
   &lt;statusCode>'000000'&lt;/statusCode&gt;
   &lt;dateCreated>'2016/03/24 17:14:02'&lt;/dateCreated&gt;
   &lt;concurrentNumber>100&lt;/concurrentNumber&gt;
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
                        <p class="b1"><b class="b"></b>1&nbsp;在线通话数接口介绍</p>
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