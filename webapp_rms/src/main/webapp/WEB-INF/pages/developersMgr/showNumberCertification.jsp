<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>认证信息</title>
</head>
<body>
<div class="pd-20">

    <form class="form form-horizontal" id="form-edit">
        <div class="row cl" style="margin-top: 0px">
            <label class="form-label col-4">号码：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appNumber.number}
            </div>
        </div>

        <div class="row cl" style="margin-top: 0px">
            <label class="form-label col-4">accountID：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appInfo.sid}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">客户名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appInfo.companyName}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">应用名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appInfo.appName}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">提交时间：</label>
            <div class="formControls col-8" style="line-height: 2">
                <fmt:formatDate value="${appNumber.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">审核：</label>
            <div class="formControls col-8" id="status" style="line-height: 2;width: 150px">
                <c:if test="${appNumber.numberStatus=='01'}">审核通过</c:if>
                <c:if test="${appNumber.numberStatus=='02'}">审核不通过</c:if>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">审核时间：</label>
            <div class="formControls col-8" style="line-height: 2">
                <fmt:formatDate value="${appNumber.reviewtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">备注：</label>
            <div class="formControls col-8" style="line-height: 2;word-break: break-all; word-wrap: break-word;width: 300px;height: 100px">
                <c:out value="${appNumber.remark}"/>
            </div>

        </div>
        <br>
    </form>
</div>
</body>
</html>