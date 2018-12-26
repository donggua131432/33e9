<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>专线语音接口</title>
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
                    <div class="api_m2"><span>专线语音接口</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.专线语音介绍</span>
                            <p>
                                专线语音，也称点击呼叫、网页回拨（Click to call），是通过传统电信专线外呼双方电话号码，双方接通后建立通话。基于玖云平台的专线语音接口，可用最少的代码，最快的时间让应用具有通信能力。<br/>
                            </p>
                            <p>玖云平台专线语音REST接口通过HTTPS POST方式提交请求。</p>
                        </div>
                        <div class="api_m2_box API2">
                            <span class="api_com_font">2.请求地址</span>
                            <p class="blueBg">/{softVersion}/Accounts/{accountSid}/Calls/callBack</p>
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
                                    <td>from</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>主叫电话号码。目前支持手机及固定电话</td>
                                </tr>
                                <tr>
                                    <td>to</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>被叫电话号码。目前支持手机及固定电话</td>
                                </tr>
                                <tr>
                                    <td>fromSerNum</td>
                                    <td>List(String)</td>
                                    <td>可选</td>
                                    <td>主叫侧显示号码列表。列表最多包含三个元素，第一个元素为优先显号号码，如无线路可以显该号，则显示备选号码中的一个。若为空则默认随机显号。所有显号号码必须通过平台审核后，才可使用，否则不显号</td>
                                </tr>
                                <tr>
                                    <td>toSerNum</td>
                                    <td>List(String)</td>
                                    <td>可选</td>
                                    <td>被叫侧显示号码列表。列表最多包含三个元素，第一个元素为优先显号号码，如无线路可以显该号，则显示备选号码中的一个。若为空则默认随机显号。若主叫侧为手机号码，被叫显示主叫真实手机号码时该号码无需审核，其余所有显号号码必须通过平台审核后，才可使用，否则不显号</td>
                                </tr>
                                <tr>
                                    <td>needRecord</td>
                                    <td>int</td>
                                    <td>可选</td>
                                    <td>是否录音：0表示不录音；1表示录音；默认值0</td>
                                </tr>
                                <tr>
                                    <td>userData</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>用户自定义数据，最长可支持128字节。如用户配置了回调接口，则在回调接口中将这一数据原样返回</td>
                                </tr>
                                <tr>
                                    <td>maxCallTime</td>
                                    <td>int</td>
                                    <td>可选</td>
                                    <td>通话的最大时长,单位为秒。当通话时长到达最大时长则挂断通话。默认值空。开发者设置的最大通话时长最长为8小时，超过8小时返回错误码。计时以主被叫建立通话为起始</td>
                                </tr>
                            </table>
                            <p class="APIsmFont">参数说明：</p>
                            <p>
                                (1).appId：应用ID<br/>
                                (2).from：主叫号码，需要进行号码检查，如号码不合法返回错误码<br/>
                                (3).to：被叫号码，需要进行号码检查，如号码不合法返回错误码<br/>
                                (4).fromSerNum：主叫侧显号，需要对用户传入显号号码进行权限检查（是否审核通过），如号码不合法则默认不显号；所有显号号码必须通过平台审核后，才可使用，否则不显号<br/>
                                (5).toSerNum：被叫侧显号，需要对用户传入显号号码进行权限检查（是否审核通过），如号码不合法则默认不显号；若主叫侧为手机号码，被叫显示主叫真实手机号码时该号码无需审核，其余所有显号号码必须通过平台审核后，才可使用，否则不显号<br/>
                                (6).needRecord：录音标志参数，录音文件下载地址在呼叫挂机计费通知回调(Hangup)中获取，非实时可以在官网通话记录界面进行下载。注：因录音文件需要时间同步到下载服务器，建议在获取到录音下载地址10分钟后再进行下载<br/>
                                (7).userData：用户自定义数据，最长可传入128字节，用户配置回调接口的情况下，将数据返回回调接口<br/>
                                (8).maxCallTime：最大通话时长，用于在请求通话时定义本次通话的最大通话时长
                            </p>
                            <p class="APImdFont">请求示例</p>
                            <span class="STop"><b class="icon guideTxt"></b>JSON请求示例</span>
                            <pre class="blueBg">
  POST
  /2016-01-01/Accounts/798b9ec0eaa94142852aa2c402570146/Calls/callBack?sig=
  6949C5F977B304EC5BB969FD8838D345HTTP/1.1
  Host:127.0.0.1:9080
  content-length: 272
  Accept: application/json;
  Content-Type: application/json;charset=utf-8;
  Authorization: Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmM0AAI1NzAxNDY6MjAxNjAzMjQxNTE3MDE=

  {
      "callback": {
           "appId": "feff3cbd52c041a1baab0492bee56423",
           "from": "13912345678",
           "to": "13812345678",
           "fromSerNum": [
               "13812345678"
           ],
           "toSerNum": [
               "13912345678"
           ],
           "needRecord": "1",
           "userData": "abcdef",
           "maxCallTime": "120"
       }
   }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
  POST
  /2016-01-01/Accounts/798b9ec0eaa94142852aa2c402570146/Calls/callBack?sig=
  230AB7B77C951C9DB5FECD55B3AFB3CFHTTP/1.1
  Host:127.0.0.1:9080
  content-length: 357
  Accept: application/xml;
  Content-Type: application/xml;charset=utf-8;
  Authorization: Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmMAADI1NzAxNDY6MjAxNjAzMjQxNjA2MjI=

      &lt;?xml version='1.0' encoding='utf-8'?&gt;
      &lt;callback&gt;
          &lt;appId&gt;feff3cbd52c041a1bd5aa492bee56423&lt;/appId&gt;
          &lt;from&gt;13912345678&lt;/from&gt;
          &lt;to&gt;13812345678&lt;/to&gt;
          &lt;fromSerNum&gt;[13812345678]&lt;/fromSerNum&gt;
          &lt;toSerNum&gt;[13912345678]&lt;/toSerNum&gt;
          &lt;needRecord&gt;1&lt;/needRecord&gt;
          &lt;userData&gt;abcdef&lt;/userData&gt;
          &lt;maxCallTime&gt;120&lt;/maxCallTime&gt;
      &lt;/callback&gt;
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
                                    <td>subStatusCode</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>当请求状态码为000000（成功）时，该值可能不为空，用来描述不影响呼叫的错误，例如：显号参数错误等</td>
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
                                    <td>可选</td>
                                    <td>一个由32个字符组成的电话唯一标识符，请求成功返回，请求失败不返回</td>
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
      "callId": "ca7b3057e11743afb43f8dd25ab60d30",
      "dateCreated": "2016/03/24 15:36:52",
      "subStatusCode": ""
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
      &lt;callId&gt;9d4c26f49d7f45b6917c105374bf4fc6&lt;/callId&gt;
      &lt;dateCreated&gt;2016/03/24 16:12:44&lt;/dateCreated&gt;
      &lt;subStatusCode&gt;&lt;/subStatusCode&gt;
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
                        <p class="b1"><b class="b"></b>1&nbsp;专线语音介绍</p>
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