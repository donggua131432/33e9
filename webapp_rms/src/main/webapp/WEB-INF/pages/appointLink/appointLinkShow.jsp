<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>查看线路特例</title>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal" id="form-member-add">

        <div class="row cl">
            <label class="form-label col-3">显示号码前缀：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appointLink.xhTelno}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">呼叫目的号码前缀：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appointLink.destTelno}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">类型：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appointLink.type}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">RN值：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appointLink.rn}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">备注：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appointLink.remake}"/>
                </span>
            </div>
        </div>

    </form>
</div>
</body>
</html>