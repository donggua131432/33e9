<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="keywords" content="玖云平台是提供通话、短信、呼叫中心、流量、专线语音、通话录音、录音、回拨、隐私号等通讯能力与服务的开放平台" />
    <meta name="description" content="玖云平台为企业及个人开发者提供各种通讯能力，包括网络通话、呼叫中心/IVR等，开发者通过嵌入云通讯API能够在应用中轻松实现各种通讯功能，包括语音验证码、语音对讲、群组语聊、点击拨号、云呼叫中心等功能;网络通话,网络电话,云呼叫中心,短信接口,pass平台,流量充值,短信发送平台,通讯云,玖云平台,云通讯,云通信,SDK,API,融合通信,政企通讯,云呼叫中心,呼叫中心建设,呼叫中心能力,通讯线路,虚拟运营商,短信服务,流量分发平台,网络直拨,匿名通话,公费电话,企业服务 " />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit">
    <title>玖云平台-密码找回</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/base.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/default.css"/>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../../common/home2/header.jsp"/>

<div class="register">
    <h2 class="f48 text_center">密码找回</h2>
    <div class="registMian">
        <ul class="registBg passBg4 passBtu">
            <li class="passMr blueFont letter">填写邮箱</li>
            <li class="passMr blueFont  letter">发送完成</li>
            <li class="passMr blueFont lette">重置密码</li>
            <li class="lette blueFont">完成重置</li>
        </ul>
    </div>
    <div class="regist_tit">
        <h3 class="text_center letter zColor">您的账号<span class="blueFont f36 letter">${email}</span>已完成密码重置<br/>请使用新密码登录</h3>
        <ul class="regist_iput">
            <li class="regist_rigM"><a href="javascript:toLogin();" class="blueBtn Btn_w400 send">登录</a> </li>
        </ul>
    </div>
</div>
<jsp:include page="../../common/home2/footer.jsp"/>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/login.js"></script>
<script type="text/javascript">

    function toLogin(){
        window.open("${appConfig.loginUrl}?email=${email}");
    }
</script>
</body>
</html>