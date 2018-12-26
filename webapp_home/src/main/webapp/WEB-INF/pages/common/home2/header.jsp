<%@ page language="java" import="java.util.*,com.e9cloud.core.util.RSAUtil" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--右边浮动层-->
<div class="right_Sidebar">
    <%--<p class="serviceImg" id="onlineService" ><a href="javascript:;" class="iphone fr"></a></p>--%>
    <p class="serviceImg" ><a href="javascript:;" class="Service fr"></a></p>
</div>
<!--右边浮动层 edn-->
<!--头部-->
<div class="header">
    <div class="headerHd">
        <div class="logo fl"><a href="${appConfig.url}" onclick="beforeGotoIndex();"><img src="${appConfig.url}images/logo.png"></a></div>
        <div class="nav fr">
            <ul class="navText fl">
                <li><a href="${appConfig.url}" onclick="beforeGotoIndex();">首页</a></li>
                <li><a href="${appConfig.url}dedicated_voice.html">产品</a></li>
                <li><a href="${appConfig.url}financial.html">解决方案</a></li>
                <li><a href="${appConfig.url}price.html">价格</a></li>
                <li><a href="${appConfig.url}api/UseGuide.html">API文档</a></li>
            </ul>
            <div class="loginbtn fl loginpd" id="unlogin">
                <c:set var="spath" value="${pageContext.request.servletPath}"/>
                <a href="<c:url value='/reg/toSign'/>" <c:if test='${spath.indexOf("reg") > -1}'>class="cur"</c:if>>注册</a>
                <a href="<c:url value='/auth'/>" <c:if test='${spath.indexOf("login") > -1}'>class="cur"</c:if>>登录</a>
            </div>
            <!--登录后状态-->
            <div class="userCter fl loginpd" style="display:none;" id="login">
                <a href="<c:url value='/accMgr/index'/>" class="userd">用户控制中心</a>
                <a href="<c:url value='/auth/logout'/>">退出</a>
            </div>
            <!--登录后状态 enf-->
        </div>
    </div>
</div>
<!--头部 end-->
<%--<script charset="utf-8" src="http://wpa.b.qq.com/cgi/wpa.php"></script>--%>
<script src="${appConfig.resourcesRoot}/master/js/cookie.js"></script>
<script type="text/javascript">

    var login = 'isLogin';
    $(function(){
        var un = getCookie(login);
        if (un) {
            $("#unlogin").hide();
            $("#login").show();
        }
    });

    /*BizQQWPA.addCustom({
        aty: '0', //指定工号类型,0-自动分流，1-指定工号，2-指定分组
        a:'0',
        nameAccount: '800164600', //营销 QQ 号码
        selector: 'onlineService' //WPA 被放置的元素
    });
    BizQQWPA.addCustom({
        aty: '0', //指定工号类型,0-自动分流，1-指定工号，2-指定分组
        a:'0',
        nameAccount: '800164600', //营销 QQ 号码
        selector: 'contactUs' //WPA 被放置的元素
    });*/
</script>
