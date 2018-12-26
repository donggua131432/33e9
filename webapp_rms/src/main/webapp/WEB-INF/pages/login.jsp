<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge"/>
    <meta name="renderer" content="webkit"/>
    <title>玖云运营管理平台</title>
    <link rel="stylesheet" type="text/css" href="${appConfig.resourcesRoot}/css/BManagement.css"/>
</head>
<body class="jiuCloudBg2">
<div>
    <div class="headLogo">
        <img src="${appConfig.resourcesRoot}/images/jiuCloudLogo.png" alt="玖云平台运营管理中心logo"/>
    </div>
    <form action="<c:url value='/login'/> " method="post">
        <div class="platformMain operationFont jiuCloud">
            <h2>玖云平台运营管理中心</h2>
            <div class="operatingTop">
                <input type="text" name="username" value="${username}"  class="inputStyle inputW350 jiuCloud_line" placeholder="账号"/>
            </div>
            <div class="operaTop">
                <input type="password" class="inputStyle inputW350 jiuCloud_line" name="password"  placeholder="密码"/>
            </div>
            <div class="platformTop">
                <label>
                    <input type="checkbox" class="RAccount"/>记住密码
                </label>
            <span class="PasswordMj">
                <b class="jiuCloundIco"></b>
                <a href="#" class="whiteFont">忘记密码</a>
            </span>
            </div>
            <button type="submit" class="inputW360 jiuCloudBtn">登录</button>
            <c:if test="${not empty error}">
                <div class="errorPropBox Left60">
                    <b class="triangleTip"></b>
                    <label class="error">${error}</label>
                </div>
            </c:if>

        </div>
    </form>
</div>
</body>
<script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.0.min.js"></script>
<script>
    $(function(){
//        function getWindowHeight(){
//            $(".operatingBg").height($(document).height())
//        }
//        $(window).resize(getWindowHeight);
//        getWindowHeight();

        $(".inputStyle").focus(function(){
            $(this).css("opacity",.8)
        }).blur(function(){
            $(this).css("opacity",1)
        })

    })

</script>
</html>