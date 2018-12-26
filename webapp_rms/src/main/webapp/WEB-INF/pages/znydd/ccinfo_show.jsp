<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>查看呼叫中心</title>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal" id="form-member-add">

        <div class="row cl">
            <label class="form-label col-3">subID：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${ccInfo.subid}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">客户名称：</label>
            <div class="formControls col-5">
                <span class="mt-3">
                    <c:out value="${ccInfo.name}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">Account ID：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${ccInfo.sid}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">应用名称：</label>
            <div class="formControls col-5">
                <span class="mt-3">
                    <c:out value="${ccInfo.appName}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">APP ID：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${ccInfo.appid}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <br>
        <HR style="border:1 dashed #987cb9" width="100%" color=#987cb9 SIZE=1>

        <div class="row cl">
            <label class="form-label col-3">呼叫中心名称：</label>
            <div class="formControls col-5">
                <span class="mt-3">
                    <c:out value="${ccInfo.ccname}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">最大话务员数量：</label>
            <div class="formControls col-5">
                <span class="mt-3">
                    <c:out value="${ccInfo.ccoMaxNum}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">是否开启缺省：</label>
            <div class="formControls col-5">
                <c:if test="${ccInfo.defaultCc == 1}">
                    <span class="mt-3">
                        是
                    </span>
                </c:if>
                <c:if test="${ccInfo.defaultCc != 1}">
                    <span class="mt-3">
                        否
                    </span>
                </c:if>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <c:if test="${not empty ccInfo.relayName1}">
            <div class="row cl">
                <label class="form-label col-2">中继群：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.relayName1}"/>
                </span>
                </div>
                <label class="form-label col-2">权重：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.weight1}"/>
                </span>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty ccInfo.relayName2}">
            <div class="row cl">
                <label class="form-label col-2">中继群：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.relayName2}"/>
                </span>
                </div>
                <label class="form-label col-2">权重：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.weight2}"/>
                </span>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty ccInfo.relayName3}">
            <div class="row cl">
                <label class="form-label col-2">中继群：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.relayName3}"/>
                </span>
                </div>
                <label class="form-label col-2">权重：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.weight3}"/>
                </span>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty ccInfo.relayName4}">
            <div class="row cl">
                <label class="form-label col-2">中继群：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.relayName4}"/>
                </span>
                </div>
                <label class="form-label col-2">权重：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.weight4}"/>
                </span>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty ccInfo.relayName5}">
            <div class="row cl">
                <label class="form-label col-2">中继群：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.relayName5}"/>
                </span>
                </div>
                <label class="form-label col-2">权重：</label>
                <div class="formControls col-4">
                <span class="mt-3">
                    <c:out value="${ccInfo.weight5}"/>
                </span>
                </div>
            </div>
        </c:if>

        <div class="row cl">
            <label class="form-label col-3">逃生号码：</label>
            <div class="formControls col-5">
                <span class="mt-3">
                    <c:out value="${ccInfo.lifeRelay}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">DTMF收号：</label>
            <div class="formControls col-5">
                <span class="mt-3">
                    <c:out value="${ccInfo.dtmfNum}"/>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">备注：</label>
            <div class="formControls col-7">
                <span class="mt-3">
                    <c:out value="${ccInfo.remark}"/>
                </span>
            </div>
            <div class="col-2"></div>
        </div>

    </form>
</div>
</body>
</html>