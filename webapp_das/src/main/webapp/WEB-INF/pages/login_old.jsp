<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge"/>
    <meta name="renderer" content="webkit"/>
    <title>海川数据中心</title>
    <link rel="stylesheet" type="text/css" href="${appConfig.resourcesRoot}/css/BManagement.css"/>
</head>
<body>
<div class="dataCenterBg">
    <div class="headLogo">
        <img src="${appConfig.resourcesRoot}/images/dataCenterLogo.png" alt="海川数据中心"/>
    </div>
    <form action="<c:url value='/login'/> " method="post">
    <div class="platformMain commonHeadFont">
            <p>海纳百川，有容乃大！</p>
            <div class="operatingTop">
                <input type="text"  name="username" value="${username}" class="inputStyle dataCenterBorder" placeholder="账号"/>
            </div>
            <div class="operaTop">
                <input type="password" class="inputStyle dataCenterBorder" name="password"  placeholder="密码"/>
            </div>
            <div class="platformTop">
                <label>
                    <input type="checkbox" class="RAccount"/>记住密码
                </label>
                <span class="passwordStyle">
                    <b class="dataCenter_txt"></b>
                    <a href="javascrip:void(0);" class="whiteFont">忘记密码</a>
                </span>
            </div>
            <button type="submit" class="dataCenterBtn">登录</button>
            <c:if test="${not empty error}">
                <div class="errorPropBox">
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