<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>添加用户</title>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal" id="form-member-add">

        <div class="row cl">
            <label class="form-label col-3">客户名称：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appInfo.companyName}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">Account ID：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appInfo.sid}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">应用名称：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appInfo.appName}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">APP ID：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appInfo.appid}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">应用状态：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:choose>
                        <c:when test="${appInfo.status eq '00'}">
                            <span class="label label-success radius">正常</span>
                        </c:when>
                        <c:when test="${appInfo.status eq '01'}">
                            <span class="label label-warning radius">冻结</span>
                        </c:when>
                        <c:when test="${appInfo.status eq '02'}">
                            <span class="label label-defaunt radius">禁用</span>
                        </c:when>
                    </c:choose>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">创建时间：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <fmt:formatDate value="${appInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </span>
            </div>
        </div>

    </form>
</div>
</body>
</html>