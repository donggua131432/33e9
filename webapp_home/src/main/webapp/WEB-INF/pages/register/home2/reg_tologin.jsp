<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="keywords" content="玖云平台是提供通话、短信、呼叫中心、流量、专线语音、通话录音、录音、回拨、隐私号等通讯能力与服务的开放平台" />
    <meta name="description" content="玖云平台为企业及个人开发者提供各种通讯能力，包括网络通话、呼叫中心/IVR等，开发者通过嵌入云通讯API能够在应用中轻松实现各种通讯功能，包括语音验证码、语音对讲、群组语聊、点击拨号、云呼叫中心等功能;网络通话,网络电话,云呼叫中心,短信接口,pass平台,流量充值,短信发送平台,通讯云,玖云平台,云通讯,云通信,SDK,API,融合通信,政企通讯,云呼叫中心,呼叫中心建设,呼叫中心能力,通讯线路,虚拟运营商,短信服务,流量分发平台,网络直拨,匿名通话,公费电话,企业服务 " />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit">
    <title>玖云平台-注册</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/base.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/default.css"/>
    <style type="text/css">
        .current{color:#2d8be3 !important;}
    </style>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../../common/baidu.jsp"/>
</head>
<body>
<jsp:include page="../../common/home2/header.jsp"/>
<!-- header end -->

<!--注册-完成注册registered-->
<div class="register">
    <h2 class="f48 text_center">注册账号</h2>
    <div class="registMian">
        <ul class="registBg Bg3">
            <li class="registMr blueFont letter">验证邮箱</li>
            <li class="registMr blueFont letter">填写信息</li>
            <li class="blueFont letter">完成注册</li>
        </ul>
    </div>
    <div class="regist_tit">
        <h3 class="text_center letter zColor">您的账号<span class="blueFont f36 letter">${email}</span>已完成注册<br/>请登录您的账号</h3>
        <ul class="regist_iput inform">
            <li class="regist_rigM"><a href="javascript:toLogin();" class="blueBtn Btn_w400 send">登录</a> </li>
        </ul>
    </div>
</div>
<!--注册-完成注册registered end-->

<!-- footer start -->
<jsp:include page="../../common/home2/footer.jsp"/>
<!-- footer end -->

<script src="${appConfig.resourcesRoot}/master/js/bootstrap.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/stepBar.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/login.js"></script>

<script type="text/javascript">
    var siginForm;

    $(function(){
        stepBar.init("stepBar",{
            step : 3,
            change : true,
            animation : true
        });
    });

    function toLogin(){
        window.open("${appConfig.loginUrl}?email=${email}", "_self");
    }
</script>
</body>
</html>