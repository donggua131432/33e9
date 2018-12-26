<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">-->
    <title>玖云平台-密码找回</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/login.css">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/control.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="website_container">
    <div class="main_box">
        <div class="login4_row2">
            <div class="login4-img">
                <img src="${appConfig.resourcesRoot}/master/img/logo_zhuce.png" alt="图片"/>
            </div>
            <div class="register2_one">
                <p>您的账号<span>${email}</span>，已完成密码重置</p>
                <p>请使用新密码登录</p>
            </div>
            <div class="btn_center">
                <button type="button" class="login_btn login4_btn" onclick="toLogin();">登&nbsp;&nbsp;录</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/stepBar.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/login.js"></script>
<script type="text/javascript">
    var regForm;

    function toLogin(){
        window.open("${appConfig.loginUrl}?email=${email}");
    }

    $(function(){
        stepBar.init("stepBar",{
            step : 4,
            change : false,
            animation : false
        });
    });
    regForm = $("#regForm").validate({
        rules: {
            username:{
                required: true,
                email:true,
                remote: {
                    url: "<c:url value='/retrieve/validate'/>",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        email: function() {
                            return $("#email").val();
                        }
                    }
                }
            },
            password: {
                required: true,
                password:true
            }
        },
        messages: {
            username:{
                required: "请输入Email地址",
                email: "请输入正确的email地址",
                remote:"此账号不存在"
            },
            password: {
                required: "密码不能为空"
            }
        }
    });

    function sumc(obj) {
        if(regForm.form())
        {
            obj.disabled=true;
            bodyRSA();
            var password = encryptedString(key, $("#password").val());
            $("#password").val(password);
            $("#regForm").submit();
        }
    }


    function bodyRSA()	{
        setMaxDigits(130);
        key = new RSAKeyPair("${appConfig.rsaExponent}", "", "${appConfig.rsaModulus}");
    }
</script>
</body>
</html>