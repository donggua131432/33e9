<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>通话记录查询接口</title>
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
                    <div class="api_m2"><span>通话记录查询接口</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.通话记录查询接口介绍</span>
                            <p>
                                开发者可通过通话记录查询接口，查询通话详单。通话详单可在挂机后查询，但会存在几秒钟到几分钟不等的时差。<br/>
                                本接口目前支持专线语音及专号通通话记录查询。用户可查询48小时内的通话记录。接口主要用做挂机回调通知的补充查询，限制同一账户每分钟查询次数小于10次。
                            </p>
                            <p>玖云平台实时在线通话数查询REST接口通过HTTPS POST方式提交请求。</p>
                        </div>
                        <div class="api_m2_box API2">
                            <span class="api_com_font">2.请求地址</span>
                            <p class="blueBg">&nbsp;&nbsp;/{softVersion}/Accounts/{accountSid}/Records/query</p>
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
                                    <td>callId</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>REST请求生成通话的唯一标示</td>
                                </tr>
                                <tr>
                                    <td>type</td>
                                    <td>int</td>
                                    <td>必选</td>
                                    <td>1：专线语音，2：专号通，目前支持这两种类型，后续可能会增加，不同类型返回的响应值不同</td>
                                </tr>
                            </table>
                            <p class="APIsmFont">参数说明：</p>
                            <p>
                                (1).callId：呼叫ID<br/>
                                (2).type：业务类型
                            </p>
                            <p class="APImdFont">请求示例</p>
                            <span class="STop"><b class="icon guideTxt"></b>JSON请求示例</span>
                            <pre class="blueBg">
    POST
    /2016-01-01/Accounts/798b9ec0eaaa4142852602c402570146/Records/query?sig=
    E36F97B23F1FF9FF05B02BFB34E68BBDHTTP/1.1
    Host:127.0.0.1:9080
    content-length: 79
    Accept: application/json;
    Content-Type: application/json;charset=utf-8;
    Authorization:Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmMAADI1NzAxNDY6MjAxNjAzMjQxNzAwMzE=
    {
        "req": {
            "callId": "303e5d6dbca849eda7ba446aa2fe1ab5",
            "type": 1
        }
    }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
    POST
    /2016-01-01/Accounts/798b9ec0eaaa4142852602c402570146/Records/query?sig=
    E3F434C282C946E839911247F6E7F889 HTTP/1.1
    Host:127.0.0.1:9080
    content-length: 131
    Accept: application/xml;
    Content-Type: application/xml;charset=utf-8;
    Authorization:MmM1NjU5OTMyODhmNDA5N2IyMmUzOGY1MDBjOTFaaTI6MjAxNjAzMjQxNzA4MzA=
    &lt;?xml version='1.0' encoding='utf-8'?&gt;
    &lt;req&gt;
        &lt;callId>303e5d6dbca849eda7ba446aa2fe1ab5&lt;/callId&gt;
        &lt;type>1&lt;/type&gt;
    &lt;/req&gt;
                            </pre>
                        </div>
                        <div class="api_m2_box API5">
                            <span class="api_com_font">5.响应包体</span>
                            <table class="API_style">
                                <tr>
                                    <td colspan="2">属性</td>
                                    <td>类型</td>
                                    <td>约束</td>
                                    <td>说明</td>
                                </tr>
                                <tr>
                                    <td colspan="2">statusCode</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>请求状态码，取值000000（成功），可参考<a href="<c:url value="/api/globalErrorCode"/>" class="Aline">&nbsp;Rest 错误代码</a></td>
                                </tr>
                                <tr>
                                    <td colspan="2">dateCreated</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>响应时间:格式"yyyy/MM/dd HH:mm:ss"。如：2016/01/01 12:00:00</td>
                                </tr>
                                <tr>
                                    <td colspan="2">represent</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>错误描述，报错时返回，正常时不返回</td>
                                </tr>
                                <tr>
                                    <td colspan="2">type</td>
                                    <td>int</td>
                                    <td>必选</td>
                                    <td>业务类型，1：专线语音，2：专号通</td>
                                </tr>
                                <tr>
                                    <td rowspan="6">callNotify</td>
                                    <td>action</td>
                                    <td>String</td>
                                    <td rowspan="6">可选</td>
                                    <td>请求类型</td>
                                </tr>
                                <tr>
                                    <td>type</td>
                                    <td>int</td>
                                    <td>呼叫类型</td>
                                </tr>
                                <tr>
                                    <td>appId</td>
                                    <td>String</td>
                                    <td>应用ID</td>
                                </tr>
                                <tr>
                                    <td>caller</td>
                                    <td>String</td>
                                    <td>主叫号码</td>
                                </tr>
                                <tr>
                                    <td>called</td>
                                    <td>String</td>
                                    <td>被叫号码</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>
                                        剩余字段(不同类型通话记录不同，参照对应通话类型的回调知报文)<br/>
                                        <a href="<c:url value="/api/CallbackInterface"/>?target=hdtz" class="Aline">专线语音挂机回调通知接口</a><br/>
                                        <a href="<c:url value="/api/specialCallbackINoticenterface"/>?target=hdtz" class="Aline">专号通挂机回调通知接口</a>
                                    </td>
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
            "type": 1,
            "callNotify":{
                "action": "Hangup",
                "type": 1,
                "appId": "feff3cbd52c041a1bd5b0492bee56423",
                "caller": "13912345678",
                "called": "13812345678",
                ...
            }
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
        &lt;type&gt;1&lt;/type&gt;
        &lt;callNotify&gt;
            &lt;action&gt;Hangup&lt;/action&gt;
            &lt;type&gt;1&lt;/type&gt;
            &lt;appId&gt;feff3cbd52c041a1bd5b0492bee56423&lt;/appId&gt;
            &lt;caller&gt;13912345678&lt;/caller&gt;
            &lt;called&gt;13812345678&lt;/called&gt;
            ...
        &lt;/callNotify&gt;
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
                        <p class="b1"><b class="b"></b>1&nbsp;通话记录查询介绍</p>
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
