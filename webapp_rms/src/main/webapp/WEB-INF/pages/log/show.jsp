<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>

    <link href="${appConfig.resourcesRoot}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${appConfig.resourcesRoot}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
    <link href="${appConfig.resourcesRoot}/js/plugins/icheck/icheck.css" rel="stylesheet" type="text/css" />
    <link href="${appConfig.resourcesRoot}/js/plugins/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet"/>

    <title>日志详细</title>
</head>
<body>
<div class="pd-20">

    <form class="form form-horizontal">

        <div class="row cl">
            <label class="form-label col-4">日志ID：</label>
            <div class="formControls col-8">
                ${log.id}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">日志名称：</label>
            <div class="formControls col-8">
                ${log.logName}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">操作类型：</label>
            <div class="formControls col-8">
                <c:choose>
                    <c:when test="${log.logType == '01'}">
                        登录
                    </c:when>
                    <c:when test="${log.logType == '02'}">
                        退出
                    </c:when>
                    <c:when test="${log.logType == '03'}">
                        新增
                    </c:when>
                    <c:when test="${log.logType == '04'}">
                        删除
                    </c:when>
                    <c:when test="${log.logType == '05'}">
                        修改
                    </c:when>
                </c:choose>

            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">操作详情：</label>
            <div class="formControls col-8">
                <span style="word-break:break-all; ">
                    ${log.logContent}
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">操作人：</label>
            <div class="formControls col-8">
                ${log.nick}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">角色：</label>
            <div class="formControls col-8">
                ${log.roleName}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">操作时间：</label>
            <div class="formControls col-8">
                <fmt:formatDate value="${log.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/layer/2.1/layer.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/H-ui.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/H-ui.admin.js"></script>
</body>
</html>