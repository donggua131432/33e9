<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>API文档</title>
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
                <div style="position:fixed;width:810px;top:85px;height:36px;background:#ECECEC;"></div>
                <div id="api_ma2_1">
                    <div class="api_m2"><span>专号通接口</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.专号通接口介绍</span>
                            <p>
                                专号通为玖云平台提供的用于保护主叫号码隐私的呼叫业务。<br/>
                                玖云平台专号通REST接口通过HTTPS&nbsp;POST方式提交请求。
                            </p>
                        </div>
                        <div class="api_m2_box API2">
                            <span class="api_com_font">2.请求地址</span>
                            <p class="blueBg">&nbsp;&nbsp;/{softVersion}/Accounts/{accountSid}/Calls/maskCallback</p>
                            <p>REST&nbsp;API是通过HTTPS&nbsp;POST方式请求。</p>
                        </div>
                        <div class="api_m2_box API3">
                            <span class="api_com_font">3.请求包头</span>
                            <p><a href="<c:url value="/api/RESTInterface"/>?target=tybt" class="Aline" style="margin-left:30px;">请参阅请求统一包头</a></p>
                        </div>
                        <div class="api_m2_box API4">
                            <span class="api_com_font">4.请求包体</span>
                            <table class="API_style">
                                <tr>
                                    <td>属性</td>
                                    <td>类型</td>
                                    <td style="width:35px;">约束</td>
                                    <td>说明</td>
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
                                    <td>maskNumber</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>隐私号码为主被叫号码对应的平台动态分配的号码，用于显号。若映射不存在或不正确不能发起呼叫</td>
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
                                (2).form：主叫号码，需要进行号码检查，如号码不合法返回错误码<br/>
                                (3).to：被叫号码，需要进行号码检查，如号码不合法返回错误码<br/>
                                (4).maskNumber：隐私号码为主被叫号码对应的平台动态分配的号码，用于显号。若号码未申请，或号码已过期则请求失败<br/>
                                (5).needRecord：录音标志参数，录音文件下载地址在呼叫挂机计费通知回调(Hangup)中获取，非实时可以在官网通话记录界面进行下载。注：因录音文件需要时间同步到下载服务器，建议在获取到录音下载地址10分钟后再进行下载<br/>
                                (6).userData：用户自定义数据，最长可传入128字节，用户配置回调接口的情况下，将数据返回回调接口<br/>
                                (7).maxCallTime：最大通话时长，用于在请求通话时定义本次通话的最大通话时长
                            </p>
                            <p class="APImdFont">请求示例</p>
                            <span class="STop"><b class="icon guideTxt"></b>JSON请求示例</span>
                            <pre class="blueBg">
    POST
    /2016-01-01/Accounts/798b9ec0eaa94142852aa2c402570146/Calls/maskCallback?sig=<br/>&nbsp;&nbsp;6949C5F977B304EC5BB969FD8838D345HTTP/1.1
    Host:127.0.0.1:9080
    content-length: 272
    Accept: application/json;
    Content-Type: application/json;charset=utf-8;
    Authorization: Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmM0AAI1NzAxNDY6MjAxNjAzMjQxNTE3MDE=

    {
        "maskCallback": {
            "appId": "feff3cbd52c041a1baab0492bee56423",
            "from": "13912345678",
            "to": "13812345678",
            "maskNumber": "075512345678",
            "needRecord": 1,
            "userData": "abcdef",
            "maxCallTime": 120
        }
    }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
    POST
    /2016-01-01/Accounts/798b9ec0eaa94142852aa2c402570146/Calls/maskCallback?sig= <br/>&nbsp;&nbsp;230AB7B77C951C9DB5FECD55B3AFB3CFHTTP/1.1
    Host:127.0.0.1:9080
    content-length: 357
    Accept: application/xml;
    Content-Type: application/xml;charset=utf-8;
    Authorization: Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmMAADI1NzAxNDY6MjAxNjAzMjQxNjA2MjI=

        &lt;?xml version='1.0' encoding='utf-8'?&gt;
        &lt;maskCallback&gt;
            &lt;appId&gt;feff3cbd52c041a1bd5aa492bee56423&lt;/appId&gt;
            &lt;from&gt;13912345678&lt;/from&gt;
            &lt;to&gt;13812345678&lt;/to&gt;
            &lt;maskNumber&gt;075512345678&lt;/maskNumber&gt;
            &lt;needRecord&gt;1&lt;/needRecord&gt;
            &lt;userData&gt;abcdef&lt;/userData&gt;
            &lt;maxCallTime&gt;120&lt;/maxCallTime&gt;
        &lt;/maskCallback&gt;
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
                                    <td>请求状态码，取值000000（成功），可参考<a href="<c:url value="/api/globalErrorCode"/>" class="Aline">&nbsp;Rest 错误代码</a></td>
                                </tr>
                                <tr>
                                    <td>dateCreated</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>响应时间:格式"yyyy/MM/dd HH:mm:ss"。如：2016/01/01 12:00:00</td>
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
    HTTP/1.1 200 OK 
    Content-Length: 126
    {
        "resp": {
            "statusCode": "000000",
            "dateCreated": "2016/03/24 15:36:52",
            "callId": "ca7b3057e11743afb43f8dd25ab60d30"
        }
    }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML响应示例</span>
                            <pre class="blueBg">
    HTTP/1.1 200 OK 
    Content-Length: 197
    &lt;?xml version='1.0'?&gt;
    &lt;resp&gt;
        &lt;statusCode&gt;000000&lt;/statusCode&gt;
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
                        <p class="b1"><b class="b"></b>1&nbsp;专号通接口介绍</p>
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
