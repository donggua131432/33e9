<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>REST API接入介绍</title>
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
                    <div class="api_m2"><span>REST&nbsp;API接入介绍</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.概述</span>
                            <p>REST即表述性状态传递（英文：Representational State Transfer，简称REST）是一套新兴的WEB通讯协议，访问方式和普通的HTTP类似，平台接口分GET和POST两种请求方式。</p>
                            <p>玖云平台REST接口为第三方应用提供了简单易用的API调用服务，第三方开发者可以快速、高效、低成本的集成玖云平台API，轻松体验其强大的通信能力。</p>
                            <p>玖云平台的REST接口支持JSON及XML两种请求格式,采用MD5加密算法，通过URL形式发送参数。</p>
                            <p>
                                <a href="${appConfig.resourcesRoot}/master/file/rest_api.zip" download="rest_api.zip" class="load"><b class="icon downTxt"></b>REST接口示例代码（即Demo）下载</a>
                            </p>
                        </div>
                        <div class="api_m2_box API2">
                            <span class="api_com_font">2.名词解释</span>
                            <p>
                                (1).accountSid：用户注册后，为每一个开发者分配的唯一ID；32位字符，全局唯一<br/>
                                (2).authToken：用户注册后，为每一个开发者分配的与accountSid对应的唯一秘钥32位字符，全局唯一<br/>
                                (3).appId：应用ID，创建应用时，平台分配的唯一ID；32位字符，全局唯一<br/>
                                (4).AS：应用服务器<br/>
                                (5).A路：主叫侧通话<br/>
                                (6).B路：被叫侧通话
                            </p>
                        </div>
                        <div class="api_m2_box API3">
                            <span class="api_com_font">3.接入流程</span>
                            <p>玖云平台接口对接流程如下:</p>
                            <p class="picWidth">
                                <img src="${appConfig.resourcesRoot}/master/img/processGuidance.jpg" alt="流程图"/>
                            </p>
                            <p>
                                (1).注册玖云平台账号<br/>
                                (2).进行开发者资质认证<br/>
                                开发者提交资质认证后，需要平台工作人员进行信息正确性及合法性审核，审核通过后可使用平台全部功能<br/><a href="<c:url value="/api/UseGuide"/>?target=zzrz" class="Aline">查看资质认证方法</a>，<a href="<c:url value="/api/PlatformAudit"/>?target=zzshbz" class="Aline">查看资质认证审核标准。</a><br/>
                                (3).注册成功后，登录“用户控制中心”，进行应用创建，并配置相关参数<br/>
                                (4).以上准备完成后，正式开始对接玖云平台的REST接口进行测试。REST接口配合回调通知接口同时使用可以实时获取接口使用状态。<br/><a href="<c:url value="/api/CallbackInterface"/>" class="Aline">查看回调接口说明</a><br/>
                                (5).玖云平台提供多种个性化定制功能，用户可通过“用户控制中心”设置相关参数，例如铃音、号码等。该步骤也可在接口对接之前进行；号码及铃音配置后，需要平台工作人员进行信息正确性及合法性审核，审核通过可进行正常使用<br/>
                                <a href="<c:url value="/api/ManagementGuidelines"/>?target=lsgl" class="Aline">查看铃音设置方法</a>，<a href="<c:url value="/api/PlatformAudit"/>?target=lsshbz" class="Aline">查看铃音审核标准</a>，<a href="<c:url value="/api/ManagementGuidelines"/>?target=hmgl" class="Aline">查看号码设置方法</a>，<a href="<c:url value="/api/PlatformAudit"/>?target=hmshbz" class="Aline">查看号码审核标准</a><br/>
                                (6).对接完成开始正式使用，使用过程中玖云平台将持续为您提供售后服务
                            </p>
                        </div>
                        <div class="api_m2_box API4" id="tybt">
                            <span class="api_com_font">4.请求统一包头</span>
                            <p>所有REST接口采用统一的请求包头。具体定义如下：</p>
                            <p class="APImdFont">Base URL</p>
                            <p>
                                文档中所有被引用的地址都有如下Base URL：<br/>
                                https://api.33e9cloud.com<br/>
                                注意： 为了确保数据隐私，平台的REST API是通过HTTPS方式请求。<br/>
                            </p>
                            <p class="APImdFont">请求统一包头</p>
                            <span class="STop">业务URL格式：</span>
                            <p>Base URL与业务URL相拼接为完整请求URL</p>
                            <span class="STop">主账号鉴权：</span>
                            <p class="blueBg">&nbsp;&nbsp;/{softVersion}/Accounts/{accountSid}/{Func}/{funcdes}?sig={sigParameter}</p>
                            <span class="STop">HTTP标准包头字段（必填）：</span>
                            <p class="blueBg">
                                &nbsp;&nbsp;Accept:application/xml;<br/>
                                &nbsp;&nbsp;Content-Type:application/xml;charset=utf-8;<br/> 
                                Content-Length:256;<br/> 
                                Authorization:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                            </p>
                            <span class="STop">请求包头定义</span>
                            <table class="API_style">
                                <tr>
                                    <td>属性</td>
                                    <td>类型</td>
                                    <td>约束</td>
                                    <td>说明</td>
                                </tr>
                                <tr>
                                    <td>softVersion</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>当前版本号，使用默认值2016-01-01（暂定）</td>
                                </tr>
                                <tr>
                                    <td>accountSid</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>主账户Id。由32个英文字母和阿拉伯数字组成的主账户唯一标识符</td>
                                </tr>
                                <tr>
                                    <td>sigParameter</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>鉴权参数，请求URL必须带有此参数，生成规则详见下方说明</td>
                                </tr>
                                <tr>
                                    <td>Accept</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>客户端响应接收数据格式：application/xml、application/json</td>
                                </tr>
                                <tr>
                                    <td>Content-Type</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>类型application/xml;charset=utf-8 或者 application/json;charset=utf-8</td>
                                </tr>
                                <tr>
                                    <td>Authorization</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>验证信息，生成规则详见下方说明</td>
                                </tr>
                                <tr>
                                    <td>Func</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>业务功能，根据业务的需要添加</td>
                                </tr>
                                <tr>
                                    <td>funcdes</td>
                                    <td>String</td>
                                    <td>可选</td>
                                    <td>业务操作，业务功能的各类具体操作分支</td>
                                </tr>
                            </table>
                            <p class="APIsmFont">参数说明：</p>
                            <p>
                                (1).sigParameter为REST API 鉴权参数<br/>
                                • URL后必须带有sig参数，例如：sig=AAABBBCCCDDDEEEFFFGGG<br/>
                                • 使用MD5加密（主账号Id + 主账号授权令牌 +时间戳）；其中主账号Id和主账号授权令牌分别对应“用户控制中心”中accountSid和authToken<br/>
                                • 时间戳是当前系统时间，格式"yyyyMMddHHmmss"；时间戳有效时间为24小时，如：20160316142030<br/>
                                • sigParameter参数需要大写<br/>
                                (2).Authorization是包头验证信息<br/>
                                • 使用Base64编码（主账户Id + 冒号 + 时间戳）<br/>
                                • 冒号为英文冒号<br/>
                                • 时间戳是当前系统时间，格式"yyyyMMddHHmmss"，需与sigParameter中时间戳相同<br/>
                                (3).Func描述业务功能，funcdes描述业务功能的具体操作<br/>
                                例如：专线语音为/Calls/callback<br/>
                                (4).支持json及xml两种格式<br/>
                                (5).时间戳有效期为当前北京时间前后的12小时（共24小时），为了适应全球化用户
                            </p>
                            <p class="APImdFont">数据报文格式</p>
                            <p>
                                玖云平台REST接口支持两种主流的报文格式：XML和JSON。通过请求包头的字段Content-Type及Accept，即可决定请求包体和响应包体的格式。<br/>
                                下方示例，表示请求类型格式是XML，要求服务器响应的包体类型也是XML：<br/>
                                <span  class="blueBg">Content-Type:application/xml;charset=utf-8;Accept:application/xml;</span><br/>
                                <span >下方示例，表示请求类型格式是JSON，要求服务器响应类型也是JSON：</span><br/>
                                <span class="blueBg">Content-Type:application/json;charset=utf-8;Accept:application/json;</span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        <div class="API_main3 f_left ">
            <div class="stb"> <span class="click_tb spread_tb"></span></div>
            <div class="api_right_box FHeight ">
                <div class="api_right_center">
                    <p class="b1"><b class="b"></b>1&nbsp;概述</p>
                    <p class="b2"><b class="b"></b>2&nbsp;名词解释</p>
                    <p class="b3"><b class="b"></b>3&nbsp;接入流程</p>
                    <p class="b4"><b class="b"></b>4&nbsp;请求统一包头</p>
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