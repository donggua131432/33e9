<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
        <div class="login_common_row1">
            <div class="row1">
                <div class="fw">密码找回</div>
                <div id="stepBar" class="ui-stepBar-wrap col-md-10">
                    <div class="ui-stepBar">
                        <div class="ui-stepProcess" ></div>
                    </div>
                    <div class="ui-stepInfo-wrap">
                        <table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">1</a>
                                    <p class="ui-stepName">填写邮箱</p>
                                </td>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">2</a>
                                    <p class="ui-stepName">发送完成</p>
                                </td>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">3</a>
                                    <p class="ui-stepName">密码重置</p>
                                </td>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">4</a>
                                    <p class="ui-stepName">完成重置</p>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="login2_row2">
            <div class="login2_one register2_one">
                <p class="mail_send">您的邮箱<span>${email}</span>验证邮件已发送</p>
                <span class="mail_note">玖云平台已将重置密码邮件发送至您的邮箱内,请查收您的邮件并完成重置密码操作</span>
            </div>
            <div class="login2_two">
                <button type="button" class="login_btn login2_btn" onclick="tomail();">登&nbsp;录&nbsp;邮&nbsp;箱</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/stepBar.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/login.js"></script>
<script type="text/javascript">
    $(function(){
        stepBar.init("stepBar",{
            step : 2,
            change : true,
            animation : true
        });
    });

    function tomail(){
        window.open("${fum}", "_blank");
    }
</script>
</body>
</html>