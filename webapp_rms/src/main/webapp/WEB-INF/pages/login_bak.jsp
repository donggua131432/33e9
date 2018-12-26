<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>玖云运营平台</title>
    <link rel="stylesheet" type="text/css" href="${appConfig.resourcesRoot}/css/BManagement.css"/>
</head>
<body>
<div class="operaBg">
    <form action="<c:url value='/login'/> " method="post">
        <div class="operaMain">
            <img src="${appConfig.resourcesRoot}/images/operaPlatLogo.png" alt="图片"/>
            <p>玖云运营平台</p>
            <div class="operaTop">
                <input type="text" class="inputStyle" name="username" value="${username}" placeholder="账号"/>
            </div>
            <div class="operaTop">
                <input type="password" class="inputStyle" name="password" placeholder="密码"/>
            </div>
            <div class="rememberPassWord">
                <input type="checkbox" id="RAccount" class="RAccount"/>
                <label for="RAccount">记住账号</label>
                <span class="passwordStyle">
                    <i class="pwd_txt"></i>
                    <a href="javascrip:void(0);">忘记密码</a>
                </span>
            </div>
            <div class="errorPropBox">
                <c:if test="${not empty error}">
                    <label class="error" >
                            ${error}
                    </label>
                </c:if>
            </div>
            <button type="submit" class="operaBtn">登录</button>
        </div>
    </form>
</div>
</body>
<script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.0.min.js"></script>
<script>
    $(function(){
        function getWindowHeight(){
            $(".operaBg").height($(document).height())
        }
        $(window).resize(getWindowHeight);
        getWindowHeight();

        $(".inputStyle").focus(function(){
            $(this).css("opacity",.5)
        }).blur(function(){
            $(this).css("opacity",1)
        })

    })

</script>
</html>