<%@ page language="java" import="java.util.*,com.e9cloud.core.util.RSAUtil" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="baidu.jsp"/>
<script type="text/javascript">
    // 如果是从导航栏过来的，那么直接显示第二屏
    function beforeGotoIndex() {
        try {
            localStorage.fromNav = true;
        } catch (e) {
        }
    }
</script>
<div class="header">
	<div class="headBox">
		<span class="img_box f_left"><a href="${appConfig.url}" onclick="beforeGotoIndex();" class="logo"><img src="${appConfig.resourcesRoot}/img/logo.png" alt="LOGO"/></a></span>
        <span class="fontBox f_right">
            <a href="${appConfig.url}" onclick="beforeGotoIndex();" class="nav_title">首页</a>
            <a href="${appConfig.url}dedicated_voice.html" class="nav_title">产品</a>
            <a href="${appConfig.url}financial.html" class="nav_title">解决方案</a>
            <a href="${appConfig.url}price.html" class="nav_title">价格</a>
            <a href="${appConfig.url}api/UseGuide.html" class="nav_title">API文档</a>
			<!--<a href="#" class="pic pic_back"><img src="img/ac-infrom.png" alt="图片"/></a>-->
            <a href="javascript:void(0);" onclick="logout();" class="pic"><img src="${appConfig.resourcesRoot}/img/back.png" alt="图片"/></a>
        </span>
	</div>
</div>