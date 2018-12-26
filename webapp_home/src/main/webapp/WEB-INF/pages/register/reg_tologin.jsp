<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>玖云平台-注册</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/login.css">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/control.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<!-- header start -->
<jsp:include page="../common/header.jsp"/>
<!-- header end -->

<div class="website_container">
    <div class="main_box">
        <div class="register4_row2">
            <div class="register4-img">
                <img src="${appConfig.resourcesRoot}/master/img/logo_zhuce.png" alt="图片"/>
            </div>
            <div class="register2_one">
                <p>您的账号<span>${email}</span>，已完成注册</p>
                <p>请登录您的账号</p>
            </div>
            <div class="btn_center">
                <button type="button" class="login_btn register4_btn" onclick="toLogin();">登&nbsp;&nbsp;录</button>
            </div>
        </div>
    </div>
</div>

<!-- footer start -->
<jsp:include page="../common/footer.jsp"/>
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