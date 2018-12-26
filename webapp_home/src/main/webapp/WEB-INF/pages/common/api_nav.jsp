<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="spath" value="${pageContext.request.servletPath}"/>
<style type="text/css">
    dd{display: none;}
    .ddShow>dd{display: block;}
</style>
<div class="api_left">
    <dl class="dl <c:if test='${spath.indexOf("UseGuide") >= 0 || spath.indexOf("ManagementGuidelines") >= 0}'>ddShow</c:if>">
        <dt class="sth">
            <span class="dt_api">新手指引</span>
            <span class="f_right APIComTop <c:choose><c:when test='${spath.indexOf("UseGuide") >= 0 || spath.indexOf("ManagementGuidelines") >= 0}'>bouLt2</c:when><c:otherwise>bouLt1</c:otherwise></c:choose>"></span>
        </dt>
        <dd data-url="<c:url value="/api/UseGuide"/>" class="<c:if test='${spath.indexOf("UseGuide") >= 0}'>dd_hover</c:if>">开发者账户使用指南</dd>
        <dd data-url="<c:url value="/api/ManagementGuidelines"/>" class="<c:if test='${spath.indexOf("ManagementGuidelines") >= 0}'>dd_hover</c:if>">开放接口使用指南</dd>
    </dl>
    <dl class="dl <c:if test='${spath.indexOf("PlatformAudit") >= 0 || spath.indexOf("PlatformServices") >= 0}'>ddShow</c:if>">
        <dt>
            <span class="dt_api">平台政策规范</span>
            <span class="f_right APIComTop <c:choose><c:when test='${spath.indexOf("PlatformAudit") >= 0 || spath.indexOf("PlatformServices") >= 0}'>bouLt2</c:when><c:otherwise>bouLt1</c:otherwise></c:choose>"></span>
        </dt>
        <dd data-url="<c:url value="/api/PlatformAudit"/>" class="<c:if test='${spath.indexOf("PlatformAudit") >= 0}'>dd_hover</c:if>">平台审核标准</dd>
        <dd data-url="<c:url value="/api/PlatformServices"/>" class="<c:if test='${spath.indexOf("PlatformServices") >= 0}'>dd_hover</c:if>">平台服务标准</dd>
    </dl>
    <dl class="dl <c:if test='${spath.indexOf("specialNumReleaseInterface") >= 0 || spath.indexOf("specialNumCheckInterface") >= 0 || spath.indexOf("specialNumAppleInterface") >= 0 || spath.indexOf("specialInterface") >= 0 || spath.indexOf("AppMapNumCheckInterface") >= 0 || spath.indexOf("RESTInterface") >= 0 || spath.indexOf("dedicatedVoice") >= 0 || spath.indexOf("CancelCallback") >= 0 || spath.indexOf("recordDownLoad") >= 0 || spath.indexOf("calledRecordCheckRest") >= 0}'>ddShow</c:if>">
        <dt>
            <span class="dt_api">Rest&nbsp;&nbsp;API</span>
            <span class="f_right APIComTop <c:choose><c:when test='${spath.indexOf("specialNumReleaseInterface") >= 0 || spath.indexOf("specialNumCheckInterface") >= 0 || spath.indexOf("specialNumAppleInterface") >= 0 || spath.indexOf("specialInterface") >= 0 || spath.indexOf("AppMapNumCheckInterface") >= 0 || spath.indexOf("RESTInterface") >= 0 || spath.indexOf("dedicatedVoice") >= 0 || spath.indexOf("CancelCallback") >= 0 || spath.indexOf("recordDownLoad") >= 0 || spath.indexOf("calledRecordCheckRest") >= 0}'>bouLt2</c:when><c:otherwise>bouLt1</c:otherwise></c:choose>"></span>
        </dt>
        <dd data-url="<c:url value="/api/RESTInterface"/>" class="<c:if test='${spath.indexOf("RESTInterface") >= 0}'>dd_hover</c:if>">Rest&nbsp;API接入介绍</dd>
        <dd data-url="<c:url value="/api/dedicatedVoice"/>" class="<c:if test='${spath.indexOf("dedicatedVoice") >= 0}'>dd_hover</c:if>">专线语音接口</dd>

        <dd data-url="<c:url value="/api/specialInterface"/>" class="<c:if test='${spath.indexOf("specialInterface") >= 0}'>dd_hover</c:if>">专号通接口</dd>
        <dd data-url="<c:url value="/api/specialNumAppleInterface"/>" class="<c:if test='${spath.indexOf("specialNumAppleInterface") >= 0}'>dd_hover</c:if>">专号通号码申请接口</dd>
        <dd data-url="<c:url value="/api/specialNumCheckInterface"/>" class="<c:if test='${spath.indexOf("specialNumCheckInterface") >= 0}'>dd_hover</c:if>">专号通号码查询接口</dd>
        <dd data-url="<c:url value="/api/specialNumReleaseInterface"/>" class="<c:if test='${spath.indexOf("specialNumReleaseInterface") >= 0}'>dd_hover</c:if>">专号通号码释放接口</dd>
        <dd data-url="<c:url value="/api/AppMapNumCheckInterface"/>" class="<c:if test='${spath.indexOf("AppMapNumCheckInterface") >= 0}'>dd_hover</c:if>">应用映射号码查询接口</dd>

        <dd data-url="<c:url value="/api/CancelCallback"/>" class="<c:if test='${spath.indexOf("CancelCallback") >= 0}'>dd_hover</c:if>">取消通话接口</dd>
        <dd data-url="<c:url value="/api/calledRecordCheckRest"/>" class="<c:if test='${spath.indexOf("calledRecordCheckRest") >= 0}'>dd_hover</c:if>">通话记录查询接口</dd>
        <dd data-url="<c:url value="/api/recordDownLoad"/>" class="<c:if test='${spath.indexOf("recordDownLoad") >= 0}'>dd_hover</c:if>">录音及下载接口</dd>
    </dl>
    <dl class="dl <c:if test='${pageContext.request.servletPath.indexOf("CallbackInterface") >= 0 || spath.indexOf("specialCallbackINoticenterface") >= 0}'>ddShow</c:if>">
        <dt>
            <span class="dt_api">AS回调通知接口</span>
            <span class="f_right APIComTop <c:choose><c:when test='${spath.indexOf("CallbackInterface") >= 0 || spath.indexOf("specialCallbackINoticenterface") >= 0}'>bouLt2</c:when><c:otherwise>bouLt1</c:otherwise></c:choose>"></span>
        </dt>
        <dd data-url="<c:url value="/api/CallbackInterface"/>" class="<c:if test='${spath.indexOf("CallbackInterface") >= 0}'>dd_hover</c:if>">专线语音回调通知接口</dd>
        <dd data-url="<c:url value="/api/specialCallbackINoticenterface"/>" class="<c:if test='${spath.indexOf("specialCallbackINoticenterface") >= 0}'>dd_hover</c:if>">专号通回调通知接口</dd>
    </dl>
    <dl class="dl <c:if test='${spath.indexOf("globalErrorCode") >= 0}'>ddShow</c:if>">
        <dt>
            <span class="dt_api">全局错误码</span>
            <span class="f_right APIComTop <c:if test='${spath.indexOf("globalErrorCode") >= 0}'>bouLt2</c:if>
            <c:if test='${spath.indexOf("globalErrorCode") < 0}'>bouLt1</c:if>"></span>
        </dt>
        <dd data-url="<c:url value="/api/globalErrorCode"/>" class="<c:if test='${spath.indexOf("globalErrorCode") >= 0}'>dd_hover</c:if>">全局错误码表</dd>
    </dl>
    <dl class="dl">
        <dt>
            <span class="dt_api">常见问题库</span>
            <span class="f_right APIComTop bouLt1"></span>
        </dt>
    </dl>
</div>