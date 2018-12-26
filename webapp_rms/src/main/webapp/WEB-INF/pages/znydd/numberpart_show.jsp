<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>查看号段</title>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal" id="form-member-add">

        <div class="row cl">
            <label class="form-label col-3">号段：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${telnoInfo.telno}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">归属地：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${telnoInfo.pname}${telnoInfo.cname}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">运营商：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${telnoInfo.oname}"/>
                </span>
            </div>
        </div>

    </form>
</div>
</body>
</html>