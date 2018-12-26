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
                    <div class="api_m2"><span>专号通号码申请接口</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.专号通号码申请接口介绍</span>
                            <p>
                                开发者调用专号通号码申请接口前，需要先建立号码池（具体方法可联系工作人员）。号码池建立后，通过本接口动态申请一个可用号码，该号码可用于映射真实号码A,B之间的通话。
                            </p>
                        </div>
                        <div class="api_m2_box API2">
                            <span class="api_com_font">2.请求地址</span>
                            <p class="blueBg">&nbsp;&nbsp;/{softVersion}/Accounts/{accountSid}/MaskNumber/apply</p>
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
                                    <td>realNumA</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>申请隐私号码的真实号码1，1、2两个号码的顺序可互换</td>
                                </tr>
                                <tr>
                                    <td>realNumB</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>申请隐私号码的真实号码2，1、2两个号码的顺序可互换</td>
                                </tr>
                                <tr>
                                    <td>needRecord</td>
                                    <td>int</td>
                                    <td>可选</td>
                                    <td>0表示不录音；1表示录音；不传默认值0。该录音标志位标记这一个映射关系的回拨及回呼是否录音</td>
                                </tr>
                                <tr>
                                    <td>validTime</td>
                                    <td>int</td>
                                    <td>可选</td>
                                    <td>隐私号码有效时间，秒为单位；例如3600秒-有效时间为一个小时，不传默认24小时有效</td>
                                </tr>
                                <tr>
                                    <td>areaCode</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>传入区号，根据区号选择对应地区的可用隐私号码，不传则隐私号码池全局选择隐私号码</td>
                                </tr>
                            </table>
                            <p>
                                <a href="${appConfig.resourcesRoot}/master/file/area_code.xlsx" class="load" download="area_code.xlsx"> <b class="icon downTxt"></b>区号列表下载</a>
                            </p>
                            <p class="APIsmFont">参数说明：</p>
                            <p>
                                (1).appId：应用ID<br/>
                                (2).realNumA：申请隐私号码的真实号码1，1、2两个号码的顺序可互换<br/>
                                (3).realNumB：申请隐私号码的真实号码2，1、2两个号码的顺序可互换<br/>
                                (4).needRecord：0表示不录音；1表示录音；不传默认值0。该录音标志位标记这一个映射关系的回拨及回呼是否录音<br/>
                                (5).validTime：隐私号码有效时间，秒为单位；例如3600秒-有效时间为一个小时，不传默认24小时有效<br/>
                                (6).areaCode：传入区号，根据区号选择对应地区的可用隐私号码，不传则隐私号码池全局选择隐私号码
                            </p>
                            <p class="APImdFont">请求示例</p>
                            <span class="STop"><b class="icon guideTxt"></b>JSON请求示例</span>
                            <pre class="blueBg">
    POST
    /2016-01-01/Accounts/798b9ec0eaa94142852aa2c402570146/MaskNumber/apply?sig=<br/>&nbsp;&nbsp;6949C5F977B304EC5BB969FD8838D345HTTP/1.1
    Host:127.0.0.1:9080
    content-length: 272
    Accept: application/json;
    Content-Type: application/json;charset=utf-8;
    Authorization: Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmM0AAI1NzAxNDY6MjAxNjAzMjQxNTE3MDE=

    {
        "req": {
            "appId": "feff3cbd52c041a1baab0492bee56423",
            "realNumA": "12345678",
            "realNumB": "87654321",
            "needRecord": 1,
            "validTime": 3600,
            "areaCode": "0755"
        }
    }
                            </pre>
                            <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
    POST
    /2016-01-01/Accounts/798b9ec0eaa94142852aa2c402570146/MaskNumber/apply?sig= <br/>&nbsp;&nbsp;230AB7B77C951C9DB5FECD55B3AFB3CFHTTP/1.1
    Host:127.0.0.1:9080
    content-length: 357
    Accept: application/xml;
    Content-Type: application/xml;charset=utf-8;
    Authorization: Nzk4YjllYzBlYWE5NDE0Mjg1MjYwMmMAADI1NzAxNDY6MjAxNjAzMjQxNjA2MjI=

        &lt;?xml version='1.0' encoding='utf-8'?&gt;
        &lt;req&gt;
            &lt;appId&gt;feff3cbd52c041a1bd5aa492bee56423&lt;/appId&gt;
            &lt;realNumA&gt;13512341234&lt;/realNumA&gt;
            &lt;realNumB&gt;13412341234&lt;/realNumB&gt;
            &lt;needRecord&gt;1&lt;/needRecord&gt;
            &lt;validTime&gt;3600&lt;/validTime&gt;
            &lt;areaCode&gt;0755&lt;/areaCode&gt;
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
                                    <td>请求状态码，取值000000（成功），可参考<a href="<c:url value="/api/globalErrorCode"/>" class="Aline">&nbsp;Rest 错误代码</a></td>
                                </tr>
                                <tr>
                                    <td>dateCreated</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>响应时间:格式"yyyy/MM/dd HH:mm:ss"。如：2016/01/01 12:00:00</td>
                                </tr>
                                <tr>
                                    <td>represent</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>错误描述，报错时返回，正常时不返回</td>
                                </tr>
                                <tr>
                                    <td>realNumA</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>申请隐私号码的真实号码1 ，正常时返回</td>
                                </tr>
                                <tr>
                                    <td>realNumB</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>申请隐私号码的真实号码2 ，正常时返回</td>
                                </tr>
                                <tr>
                                    <td>maskNumber</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>分配的隐私号码 ，正常时返回</td>
                                </tr>
                                <tr>
                                    <td>areaCode</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>若用户确认申请号码区域则返回区号，否则返回0 ，正常时返回</td>
                                </tr>
                                <tr>
                                    <td>validTime</td>
                                    <td>int</td>
                                    <td>可选</td>
                                    <td>号码有效时间 ，正常时返回</td>
                                </tr>
                                <tr>
                                    <td>needRecord</td>
                                    <td>int</td>
                                    <td>可选</td>
                                    <td>是否进行录音 ，正常时返回</td>
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
            "realNumA": "13512341234",
            "realNumB": "13412341234",
            "maskNumber": "07557280761",
            "needRecord": 1,
            "validTime": 3600,
            "areaCode": "0755"
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
        &lt;realNumA&gt;13512341234&lt;/realNumA&gt;
        &lt;realNumB&gt;13412341234&lt;/realNumB&gt;
        &lt;maskNumber&gt;07557280761&lt;/maskNumber&gt;
        &lt;needRecord&gt;1&lt;/needRecord&gt;
        &lt;validTime&gt;3600&lt;/validTime&gt;
        &lt;areaCode&gt;0755&lt;/areaCode&gt;
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
                        <p class="b1"><b class="b"></b>1&nbsp;专号通号码申请介绍</p>
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
