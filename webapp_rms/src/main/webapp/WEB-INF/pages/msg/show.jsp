<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>查看消息</title>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal" id="form-member-add">

        <div class="row cl">
            <label class="form-label col-3">消息类型：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:forEach items="${dicDatas}" var="dd">
                        <c:if test="${dd.code eq msg.msgCode}">
                            <c:out value="${dd.name}"/>
                        </c:if>
                    </c:forEach>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">标题：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${msg.title}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">创建时间：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:if test="${not empty msg.createTime}">
                        <fmt:formatDate value="${msg.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </c:if>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">发布时间：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:if test="${not empty msg.sendTime}">
                        <fmt:formatDate value="${msg.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </c:if>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">客户类型：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:if test="${not empty msg.busType}">
                        <c:if test="${msg.busType.contains('02')}">专线语音&nbsp;&nbsp;</c:if>
                        <c:if test="${msg.busType.contains('03')}">SIP接口&nbsp;&nbsp;</c:if>
                        <c:if test="${msg.busType.contains('01')}">智能云调度&nbsp;&nbsp;</c:if>
                    </c:if>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">状态：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:if test="${not empty msg.status}">
                        <c:if test="${msg.status eq '3'}">未发送&nbsp;&nbsp;</c:if>
                        <c:if test="${msg.status eq '4'}">已发送&nbsp;&nbsp;</c:if>
                    </c:if>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">消息内容：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    ${msg.content}
                </span>
            </div>
        </div>

    </form>
</div>
</body>
</html>