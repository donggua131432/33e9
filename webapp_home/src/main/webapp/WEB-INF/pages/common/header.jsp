<%@ page language="java" import="java.util.*,com.e9cloud.core.util.RSAUtil" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="header">
	<div class="head_box">
		<span class="img_box f_left"><a href="${appConfig.url}" class="logo"></a></span>
		<span class="f_right">
			<a href="${appConfig.url}" class="nav_title">首页</a>
			<a href="${appConfig.url}dedicated_voice.html" class="nav_title">产品</a>
			<a href="${appConfig.url}financial.html"class="nav_title">解决方案</a>
			<a href="${appConfig.url}UseGuide.html"/>"class="nav_title">API文档</a>
			<a href="${appConfig.url}price.html"class="nav_title">价格</a>
			<!--<a href="#" class="nav_title TEST_DOWNLOAD">体验&下载</a>-->
			<c:choose>
				<c:when test="${not empty sessionScope.userInfo}">
					<a href="<c:url value='/accMgr/index'/>" class="control_center">用户控制中心<span></span></a>
					<a href="<c:url value='/auth/logout'/>" class="back">退出<span></span></a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value='/reg/toSign'/>" class="register">注册<span></span></a>
					<a href="<c:url value='/auth'/>" class="login">登录<span></span></a>
				</c:otherwise>
			</c:choose>
		</span>
	</div>
</div>