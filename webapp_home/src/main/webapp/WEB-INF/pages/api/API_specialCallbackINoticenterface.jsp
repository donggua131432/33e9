<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
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
                        <div class="api_m2"><span>专号通回调通知接口</span></div>
                        <div>
                            <div class="api_m2_box API1">
                                <span class="api_com_font">1.专号通回调通知AS接口介绍</span>
                                <p>
                                    玖云平台针对专线语音业务提供了四种不同的回调接口通知，分别为A路及B路呼叫发起通知、呼叫建立通知、呼叫挂机计费通知。用户可通过这些通知实时获取通话状态，并在呼叫结束后获取本次通话的话单详情。
                                </p>
                                <p>
                                    专号通回调通知接口为用户开发的服务器接口，玖云平台在通话的不同阶段通过POST方式请求用户的AS服务器，向用户发起通知。
                                </p>
                                <p>
                                    <a href="${appConfig.resourcesRoot}/master/file/masknotify.zip" download="masknotify.zip" class="load"> <b class="icon downTxt"></b>专号通回调通知接口示例代码（即Demo）下载</a>
                                </p>
                            </div>
                            <div class="api_m2_box API2">
                                <span class="api_com_font">2.专号通回调通知AS接口接入及使用流程</span>
                                <p class="APImdFont">接入流程</p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/callBackinface.jpg" alt="回调接入流程图"/></p>
                                <p>
                                    (1).创建应用并配置用户的AS服务器地址即回调地址<br/>
                                    (2).用户AS服务器端开发相应接口<br/>
                                    (3).开始使用
                                </p>
                                <p class="APImdFont">使用流程</p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/callBackGuide.jpg" alt="回调使用流程图"/></p>
                                <p>
                                    (1).用户发起专线语音呼叫请求，并请求成功<br/>
                                    (2).平台发起A路呼叫<br/>
                                    (3).平台请求用户配置的回调地址，通知A路呼叫发起<br/>
                                    (4).A路接听，平台发起B路呼叫<br/>
                                    (5).平台请求用户配置的回调地址，通知B路呼叫发起<br/>
                                    (6).B路接听，呼叫正常建立<br/>
                                    (7).平台请求用户配置的回调地址，通知呼叫建立<br/>
                                    (8).A路或B路挂机，通话结束<br/>
                                    (9).平台请求用户配置的回调地址，通知呼叫结束，并返回详细话单
                                    REST接口请求成功后，呼叫过程中出现任意异常，平台都会请求用户配置的回调地址，通知呼叫结束，并返回详细话单<br/>
                                    (10).REST接口请求成功后，呼叫过程中出现任意异常，平台都会请求用户配置的回调地址，通知呼叫结束，并返回详细话单
                                </p>
                                <p class="APImdFont">请求地址配置</p>
                                <p>
                                    请求地址为开发者服务器地址，具体配置方式如下：
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/appManage.png" alt="配置图标"/></p>
                                <p>
                            </div>
                            <div class="api_m2_box API3">
                                <span class="api_com_font">3.专号通回调通知AS接口详述</span>
                                <p class="APIlgFont">专号通呼叫发起通知接口</p>
                                <p>此接口用于呼叫发起的时，平台通知用户AS服务器。平台呼叫A路及B路是会分别发出通知。</p>
                                <p class="APImdFont">请求地址</p>
                                <p>用户创建应用时自行配置URL地址。通过POST方式请求通知用户，只提供XML格式。</p>
                                <p class="APImdFont">请求包头</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>说明</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Type</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>类型application/xml</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Length</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>消息体的长度</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">请求包体</p>
                                <table class="API_style">
                                    <tr>
                                        <td style="width:10%">属性</td>
                                        <td style="width:10%">类型</td>
                                        <td style="width:10%">约束</td>
                                        <td style="width:20%">描述</td>
                                        <td style="width:50%">取值</td>
                                    </tr>
                                    <tr>
                                        <td>action</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>请求类型</td>
                                        <td>CallInvite</td>
                                    </tr>
                                    <tr>
                                        <td>type</td>
                                        <td>int</td>
                                        <td>必选</td>
                                        <td>呼叫类型</td>
                                        <td>1:专线语音 2:专号通</td>
                                    </tr>
                                    <tr>
                                        <td>appId</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>应用ID</td>
                                        <td>应用ID</td>
                                    </tr>
                                    <tr>
                                        <td>caller</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>主叫号码</td>
                                        <td>type取值为1时，主叫号码为电话号码</td>
                                    </tr>
                                    <tr>
                                        <td>called</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>被叫号码</td>
                                        <td>type取值为1时，被叫号码为电话号码</td>
                                    </tr>
                                    <tr>
                                        <td>maskNumber</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>平台分配的隐私号码</td>
                                        <td>隐私号码</td>
                                    </tr>
                                    <tr>
                                        <td>userFlag</td>
                                        <td>int</td>
                                        <td>必选</td>
                                        <td>A，B路标识</td>
                                        <td>0标识A路（主叫），1标识B路（被叫）</td>
                                    </tr>
                                    <tr>
                                        <td>callId</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>呼叫的唯一标示</td>
                                        <td>32位字符串</td>
                                    </tr>
                                    <tr>
                                        <td>dateCreated</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>请求时间</td>
                                        <td>回调用户的时间</td>
                                    </tr>
                                    <tr>
                                        <td>userData</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>用户数据</td>
                                        <td>用户自定义数据，最长可支持128字节。如用户在回拨REST请求接口中传入，则回调会将数据原样回调给客户，否则为空</td>
                                    </tr>
                                </table>
                                <p class="APIsmFont">参数说明：</p>
                                <p>
                                    (1).action：回调通知的类型<br/>
                                    (2).maskNumber：与REST请求的maskNumber一致<br/>
                                    (3).callId：与REST请求返回的callId一致<br/>
                                    (4).userFlag：区别是主叫产生的回调，还是被叫产生的回调<br/>
                                    (5).userData：用户回调REST接口中传入的数据
                                </p>
                                <p class="APImdFont">用户AS响应包头</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>描述</td>
                                        <td>举例</td>
                                    </tr>
                                    <tr>
                                        <td>Status-Code</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>应答状态码，全部为200</td>
                                        <td>HTTP/1.1 200 OK</td>
                                    </tr>
                                    <tr>
                                        <td>Date</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>服务器时间</td>
                                        <td>Wed Nov 9 16:08:57 2011</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Length</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>消息体的长度</td>
                                        <td>140</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">用户AS响应包体</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>描述</td>
                                        <td>取值</td>
                                    </tr>
                                    <tr>
                                        <td>StatusCode</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>响应结果</td>
                                        <td>用户返回错误码 0000表示成功，目前平台不对此字段进行解析</td>
                                    </tr>
                                    <tr>
                                        <td>statusMsg</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>响应结果描述</td>
                                        <td>用户自定义描述，目前平台不对此字段进行解析</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">回调请求示例</p>
                                <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
   &lt;?xml version='1.0'?&gt;
   &lt;req&gt;
        &lt;action&gt;CallInvite&lt;/action&gt;
        &lt;type&gt;2&lt;/type&gt;
        &lt;appId&gt;feff3cbd52c041a1bd5b0492bee56423&lt;/appId&gt;
        &lt;caller&gt;13912345678&lt;/caller&gt;
        &lt;called&gt;13812345678&lt;/called&gt;
        &lt;maskNumber&gt;07557280761&lt;/maskNumber&gt;
        &lt;userFlag&gt;0&lt;/userFlag&gt;
        &lt;callId&gt;420d9ec4557f4eeabfb003983d5b2049&lt;/callId&gt;
        &lt;dateCreated&gt;20160324175622&lt;/dateCreated&gt;
        &lt;userData&gt;abcdef&lt;/userData&gt;
   &lt;/req&gt;
                            </pre>
                                <p class="APImdFont">用户响应示例</p>
                                <span class="STop"><b class="icon guideTxt"></b>XML响应示例</span>
                            <pre class="blueBg">
   &lt;?xml version='1.0' encoding='utf-8'?&gt;
   &lt;Response&gt;
       &lt;statusCode&gt;0000&lt;/statusCode&gt;
       &lt;statusMsg&gt;Success&lt;/statusMsg&gt;
   &lt;/Response&gt;
                            </pre>
                                <p class="APIlgFont marginTop">专号通呼叫建立通知接口</p>
                                <p>此接口用于B路（被叫）接听后，通话建立成功的时。平台通知用户AS服务器呼叫已经建立。</p>
                                <p class="APImdFont">请求地址</p>
                                <p>用户创建应用时自行配置URL地址。通过POST方式请求通知用户，只提供XML格式。</p>
                                <p class="APImdFont">请求包头</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>说明</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Type</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>类型application/xml</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Length</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>消息体的长度</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">请求包体</p>
                                <table class="API_style">
                                    <tr>
                                        <td style="width:10%">属性</td>
                                        <td style="width:10%">类型</td>
                                        <td style="width:10%">约束</td>
                                        <td style="width:15%">描述</td>
                                        <td style="width:55%">取值</td>
                                    </tr>
                                    <tr>
                                        <td>action</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>请求类型</td>
                                        <td>CallEstablish</td>
                                    </tr>
                                    <tr>
                                        <td>type</td>
                                        <td>int</td>
                                        <td>必选</td>
                                        <td>呼叫类型</td>
                                        <td>1:专线语音 2:专号通</td>
                                    </tr>
                                    <tr>
                                        <td>appId</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>应用ID</td>
                                        <td>应用ID</td>
                                    </tr>
                                    <tr>
                                        <td>caller</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>主叫号码</td>
                                        <td>type取值为1时，主叫号码为电话号码</td>
                                    </tr>
                                    <tr>
                                        <td>called</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>被叫号码</td>
                                        <td>type取值为1时，被叫号码为电话号码</td>
                                    </tr>
                                    <tr>
                                        <td>maskNumber</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>平台分配的隐私号码</td>
                                        <td>隐私号码</td>
                                    </tr>
                                    <tr>
                                        <td>callId</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>呼叫的唯一标示</td>
                                        <td>32位字符串</td>
                                    </tr>
                                    <tr>
                                        <td>dateCreated</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>请求时间</td>
                                        <td>回调用户的时间</td>
                                    </tr>
                                    <tr>
                                        <td>userData</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>用户数据</td>
                                        <td>用户自定义数据，最长可支持128字节。如用户在回拨REST请求接口中传入，则回调会将数据原样回调给客户</td>
                                    </tr>
                                </table>
                                <p class="APIsmFont">参数说明：</p>
                                <p>
                                    (1)action：回调类型标记<br/>
                                    (2)maskNumber：与REST请求返回的maskNumber一致<br/>
                                    (3)callId：与REST请求返回的callId一致
                                </p>
                                <p class="APImdFont">用户AS响应包头</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>描述</td>
                                        <td>举例</td>
                                    </tr>
                                    <tr>
                                        <td>Status-Code</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>应答状态码，全部为200</td>
                                        <td>HTTP/1.1 200 OK</td>
                                    </tr>
                                    <tr>
                                        <td>Date</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>服务器时间</td>
                                        <td>Wed Nov 9 16:08:57 2011</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Length</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>消息体的长度</td>
                                        <td>140</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">用户AS响应包体</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>描述</td>
                                        <td>取值</td>
                                    </tr>
                                    <tr>
                                        <td>StatusCode</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>认证授权结果</td>
                                        <td>用户返回错误码0000表示成功，目前平台不对此字段进行解析</td>
                                    </tr>
                                    <tr>
                                        <td>statusMsg</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>认证结果描述</td>
                                        <td>用户自定义描述，目前平台不对此字段进行解析</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">回调请求示例</p>
                                <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
   &lt;?xml version='1.0'?&gt;
   &lt;req&gt;
        &lt;action&gt;CallEstablish&lt;/action&gt;
        &lt;type&gt;2&lt;/type&gt;
        &lt;appId&gt;feff3cbd52c041a1bd5b0492bee56423&lt;/appId&gt;
        &lt;caller&gt;13912345678&lt;/caller&gt;
        &lt;called&gt;13812345678&lt;/called&gt;
        &lt;maskNumber&gt;07557280761&lt;/maskNumber&gt;
        &lt;callId&gt;420d9ec4557f4eeabfb003983d5b2049&lt;/callId&gt;
        &lt;dateCreated&gt;20160324175652&lt;/dateCreated&gt;
        &lt;userData&gt;{"test":"test"}&lt;/userData&gt;
   &lt;/req&gt;
                            </pre>
                                <p class="APImdFont">用户响应示例</p>
                                <span class="STop"><b class="icon guideTxt"></b>XML响应示例</span>
                            <pre class="blueBg">
   &lt;?xml version='1.0' encoding='utf-8'?&gt;
   &lt;Response&gt;
       &lt;statusCode&gt;0000&lt;/statusCode&gt;
       &lt;statusMsg&gt;Success&lt;/statusMsg&gt;
   &lt;/Response&gt;
                            </pre>
                                <p class="APIlgFont marginTop" id="hdtz">专号通呼叫挂机计费通知接口</p>
                                <p>此接口用于呼叫断开的时候，平台通知用户AS服务器。包括正常断开以及异常断开情况，通话详细话单也在此回调接口中一起返回。</p>
                                <p class="APImdFont">请求地址</p>
                                <p>用户创建应用时自行配置URL地址。通过POST方式请求通知用户，只提供XML格式。</p>
                                <p class="APImdFont">请求包头</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>说明</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Type</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>类型application/xml</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Length</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>消息体的长度</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">请求包体</p>
                                <table class="API_style">
                                    <tr>
                                        <td style="width:10%">属性</td>
                                        <td style="width:10%">类型</td>
                                        <td style="width:10%">约束</td>
                                        <td style="width:35%">描述</td>
                                        <td style="width:35%">取值</td>
                                    </tr>
                                    <tr>
                                        <td>action</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>请求类型</td>
                                        <td>Hangup</td>
                                    </tr>
                                    <tr>
                                        <td>type</td>
                                        <td>int</td>
                                        <td>必选</td>
                                        <td>呼叫类型</td>
                                        <td>1:专线语音 2:专号通</td>
                                    </tr>
                                    <tr>
                                        <td>appId</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>应用ID</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>caller</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>主叫号码</td>
                                        <td>type取值为1时，主叫号码为电话号码</td>
                                    </tr>
                                    <tr>
                                        <td>called</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>被叫号码</td>
                                        <td>type取值为1时，被叫号码为电话号码</td>
                                    </tr>
                                    <tr>
                                        <td>maskNumber</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>平台分配的隐私号码</td>
                                        <td>隐私号码</td>
                                    </tr>
                                    <tr>
                                        <td>startTimeA</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>回拨时，为主叫接听时间；</td>
                                        <td>YYYYMMDDHH24MISS20130212000256</td>
                                    </tr>
                                    <tr>
                                        <td>startTimeB</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>回拨时，为被叫接听时间；</td>
                                        <td>YYYYMMDDHH24MISS20130212000256</td>
                                    </tr>
                                    <tr>
                                        <td>endTime</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>通话结束时间</td>
                                        <td>YYYYMMDDHH24MISS</td>
                                    </tr>
                                    <tr>
                                        <td>duration</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>通话时长。回拨时，如被叫接听，通话建立，则为B路接听到结束的时间。如被叫未接听，则为0</td>
                                        <td>单位：秒</td>
                                    </tr>
                                    <tr>
                                        <td>callId</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>呼叫的唯一标示</td>
                                        <td>32位字符串</td>
                                    </tr>
                                    <tr>
                                        <td>recordUrl</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>回拨时，通话录音完整下载地址。url需要以.mp3为后缀</td>
                                        <td>若未开启录音则默认空</td>
                                    </tr>
                                    <tr>
                                        <td>byeType</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>通话挂机类型</td>
                                        <td>
                                            0：正常挂断<br/>
                                            1：A无法接通<br/>
                                            2：B无法接通<br/>
                                            3：A目标忙<br/>
                                            4：B目标忙<br/>
                                            5：通话达到最大时长<br/>
                                            6：服务器错误<br/>
                                            7：网络错误<br/>
                                            255：其他错误
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>dateCreated</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>请求时间</td>
                                        <td>回调用户的时间</td>
                                    </tr>
                                    <tr>
                                        <td>userData</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>用户数据</td>
                                        <td>用户自定义数据，最长可支持128字节。如用户在回拨REST请求接口中传入，则回调会将数据原样回调给客户</td>
                                    </tr>
                                </table>
                                <p class="APIsmFont">参数说明：</p>
                                <p>
                                    (1).maskNumber：与REST请求返回的maskNumber一致<br/>
                                    (2).callId：与REST请求返回的callId一致<br/>
                                    (3).recordUrl：如开启录音业务，则此处会返回录音下载地址<br/>
                                    (4).byeType：挂机原因，包含正常挂机及异常挂机多种情况
                                </p>
                                <p class="APImdFont">用户AS响应包头</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>描述</td>
                                        <td>举例</td>
                                    </tr>
                                    <tr>
                                        <td>Status-Code</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>应答状态码，全部为200</td>
                                        <td>HTTP/1.1 200 OK</td>
                                    </tr>
                                    <tr>
                                        <td>Date</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>服务器时间</td>
                                        <td>Wed Nov 9 16:08:57 2011</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Length</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>消息体的长度</td>
                                        <td>140</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">用户AS响应包体</p>
                                <table class="API_style">
                                    <tr>
                                        <td style="width:10%">属性</td>
                                        <td style="width:10%">类型</td>
                                        <td style="width:10%">约束</td>
                                        <td style="width:15%">描述</td>
                                        <td style="width:55%">取值</td>
                                    </tr>
                                    <tr>
                                        <td>StatusCode</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>认证授权结果</td>
                                        <td>用户返回错误码0000表示成功，目前平台不对此字段进行解析</td>
                                    </tr>
                                    <tr>
                                        <td>statusMsg</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>认证结果描述</td>
                                        <td>用户自定义描述，目前平台不对此字段进行解析</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">回调请求示例</p>
                                <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
   &lt;?xml version='1.0'?&gt;
   &lt;req&gt;
        &lt;action&gt;Hangup&lt;/action&gt;
        &lt;type&gt;2&lt;/type&gt;
        &lt;appId&gt;feff3cbd52c041a1bd5b0492bee56423&lt;/appId&gt;
        &lt;caller&gt;13912345678&lt;/caller&gt;
        &lt;called&gt;13812345678&lt;/called&gt;
        &lt;maskNumber&gt;07557280761&lt;/maskNumber&gt;
        &lt;callId&gt;feff3cbd52c041a1bd5b0492bee56423&lt;/callId&gt;
        &lt;dateCreated&gt;20160324180031&lt;/dateCreated&gt;
        &lt;userData&gt;abcdef&lt;/userData&gt;
        &lt;startTimeA&gt;20160324180016&lt;/startTimeA&gt;
        &lt;startTimeB&gt;20160324180022&lt;/startTimeB&gt;
        &lt;endTime&gt;20160324180031&lt;/endTime&gt;
        &lt;duration&gt;9&lt;/duration&gt;
        &lt;recordUrl&gt;&lt;/recordUrl&gt;
        &lt;byeType&gt;0&lt;/byeType&gt;
   &lt;/req&gt;
                            </pre>
                                <p class="APImdFont">用户响应示例</p>
                                <span class="STop"><b class="icon guideTxt"></b>XML响应示例</span>
                            <pre class="blueBg">
   &lt;?xml version='1.0' encoding='utf-8'?&gt;
   &lt;Response&gt;
       &lt;statusCode&gt;0000&lt;/statusCode&gt;
       &lt;statusMsg&gt;Success&lt;/statusMsg&gt;
   &lt;/Response&gt;
                            </pre>
                                <p class="APIlgFont marginTop" style="margin-top:10px;">回呼挂机计费通知接口</p>
                                <p>此接口用于回呼隐私号通话断开的时候，平台通知第三方应用服务器。包括正常断开以及异常断开情况，通话详细话单也在此回调接口中一起返回。</p>
                                <p class="APImdFont">请求地址</p>
                                <p>用户创建应用时自行配置URL地址。通过POST方式请求通知用户，只提供XML格式。</p>
                                <p class="APImdFont">请求包头</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>说明</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Type</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>类型application/xml</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Length</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>消息体的长度</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">请求包体</p>
                                <table class="API_style">
                                    <tr>
                                        <td style="width:10%">属性</td>
                                        <td style="width:10%">类型</td>
                                        <td style="width:10%">约束</td>
                                        <td style="width:35%">描述</td>
                                        <td style="width:35%">取值</td>
                                    </tr>
                                    <tr>
                                        <td>action</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>请求类型</td>
                                        <td>IncomeHangup</td>
                                    </tr>
                                    <tr>
                                        <td>type</td>
                                        <td>int</td>
                                        <td>必选</td>
                                        <td>呼叫类型</td>
                                        <td>1:专线语音 2:专号通</td>
                                    </tr>
                                    <tr>
                                        <td>appId</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>应用ID</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>caller</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>主叫号码</td>
                                        <td>type取值为1时，主叫号码为电话号码</td>
                                    </tr>
                                    <tr>
                                        <td>called</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>被叫号码</td>
                                        <td>type取值为1时，被叫号码为电话号码</td>
                                    </tr>
                                    <tr>
                                        <td>maskNumber</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>平台分配的隐私号码</td>
                                        <td>隐私号码</td>
                                    </tr>
                                    <tr>
                                        <td>startTime</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>被叫接听时间</td>
                                        <td>YYYYMMDDHH24MISS20130212000256</td>
                                    </tr>
                                    <tr>
                                        <td>endTime</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>通话结束时间</td>
                                        <td>YYYYMMDDHH24MISS</td>
                                    </tr>
                                    <tr>
                                        <td>duration</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>通话时长。回拨时，如被叫接听，通话建立，则为B路接听到结束的时间。如被叫未接听，则为0</td>
                                        <td>单位：秒</td>
                                    </tr>
                                    <tr>
                                        <td>recordUrl</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>回拨时，通话录音完整下载地址。url需要以.mp3为后缀</td>
                                        <td>若未开启录音则默认空。若被叫未接听，则不进行录音</td>
                                    </tr>
                                    <tr>
                                        <td>byeType</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>通话挂机类型</td>
                                        <td>
                                            0：正常挂断<br/>
                                            1：A无法接通<br/>
                                            2：B无法接通<br/>
                                            3：A目标忙<br/>
                                            4：B目标忙<br/>
                                            5：通话达到最大时长<br/>
                                            6：服务器错误<br/>
                                            7：网络错误<br/>
                                            255：其他错误
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>dateCreated</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>请求时间</td>
                                        <td>回调用户的时间</td>
                                    </tr>
                                </table>
                                <p class="APIsmFont">参数说明：</p>
                                <p>
                                    (1).maskNumber：与REST请求返回的maskNumber一致<br/>
                                    (2).recordUrl：如开启录音业务，则此处会返回录音下载地址<br/>
                                    (3).byeType：挂机原因，包含正常挂机及异常挂机多种情况
                                </p>
                                <p class="APImdFont">用户AS响应包头</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>类型</td>
                                        <td>约束</td>
                                        <td>描述</td>
                                        <td>举例</td>
                                    </tr>
                                    <tr>
                                        <td>Status-Code</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>应答状态码，全部为200</td>
                                        <td>HTTP/1.1 200 OK</td>
                                    </tr>
                                    <tr>
                                        <td>Date</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>服务器时间</td>
                                        <td>Wed Nov 9 16:08:57 2011</td>
                                    </tr>
                                    <tr>
                                        <td>Content-Length</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>消息体的长度</td>
                                        <td>140</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">用户AS响应包体</p>
                                <table class="API_style">
                                    <tr>
                                        <td style="width:10%">属性</td>
                                        <td style="width:10%">类型</td>
                                        <td style="width:10%">约束</td>
                                        <td style="width:15%">描述</td>
                                        <td style="width:55%">取值</td>
                                    </tr>
                                    <tr>
                                        <td>StatusCode</td>
                                        <td>String</td>
                                        <td>必选</td>
                                        <td>认证授权结果</td>
                                        <td>用户返回错误码0000表示成功，目前平台不对此字段进行解析</td>
                                    </tr>
                                    <tr>
                                        <td>statusMsg</td>
                                        <td>String</td>
                                        <td>可选</td>
                                        <td>认证结果描述</td>
                                        <td>用户自定义描述，目前平台不对此字段进行解析</td>
                                    </tr>
                                </table>
                                <p class="APImdFont">回调请求示例</p>
                                <span class="STop"><b class="icon guideTxt"></b>XML请求示例</span>
                            <pre class="blueBg">
   &lt;?xml version='1.0'?&gt;
   &lt;req&gt;
        &lt;action&gt;IncomeHangup&lt;/action&gt;
        &lt;type&gt;2&lt;/type&gt;
        &lt;appId&gt;feff3cbd52c041a1bd5b0492bee56423&lt;/appId&gt;
        &lt;caller&gt;13912345678&lt;/caller&gt;
        &lt;called&gt;13812345678&lt;/called&gt;
        &lt;maskNumber&gt;07557280761&lt;/maskNumber&gt;
        &lt;dateCreated&gt;20160324180031&lt;/dateCreated&gt;
        &lt;startTime&gt;20160324180022&lt;/startTime&gt;
        &lt;endTime&gt;20160324180031&lt;/endTime&gt;
        &lt;duration&gt;9&lt;/duration&gt;
        &lt;recordUrl&gt;&lt;/recordUrl&gt;
        &lt;byeType&gt;0&lt;/byeType&gt;
   &lt;/req&gt;
                            </pre>
                                <p class="APImdFont">用户响应示例</p>
                                <span class="STop"><b class="icon guideTxt"></b>XML响应示例</span>
                            <pre class="blueBg">
   &lt;?xml version='1.0' encoding='utf-8'?&gt;
   &lt;Response&gt;
       &lt;statusCode&gt;0000&lt;/statusCode&gt;
       &lt;statusMsg&gt;Success&lt;/statusMsg&gt;
   &lt;/Response&gt;
                            </pre>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="API_main3 f_left">
                    <div class="stb"> <span class="click_tb spread_tb"></span></div>
                    <div class="api_right_box THeight">
                        <div class="api_right_center">
                            <p class="b1"><b class="b"></b>1&nbsp;回调接口介绍</p>
                            <p class="b2"><b class="b"></b>2&nbsp;回调AS使用流程</p>
                            <p class="b3"><b class="b"></b>3&nbsp;回调AS接口详述</p>
                        </div>
                    </div>
                    <div class="xtb"><span class="click_tb close_tb"></span></div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
<script src="${appConfig.resourcesRoot}/master/js/api.js"></script>

</body>
</html>
