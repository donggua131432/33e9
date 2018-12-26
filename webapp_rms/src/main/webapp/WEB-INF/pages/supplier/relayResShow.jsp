<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加线路资源</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/supplier/saveSupplier"/>" method="post" class="form form-horizontal" id="addSupplier">

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>资源名称：</label>
            <div class="formControls col-7">
                <c:out value="${res.resName}"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>中继名称：</label>
            <div class="formControls col-7">
                <c:out value="${res.sipBasic.relayName}"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">中继ID：</label>
            <div class="formControls col-7">
                <c:out value="${res.relayNum}"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                外域对端IP和端口：<c:out value="${res.sipBasic.ipport1}"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                外域本端IP和端口：<c:out value="${res.sipBasic.ipport2}"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                内域对端IP和端口：<c:out value="${res.sipBasic.ipport3}"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                内域本端IP和端口：<c:out value="${res.sipBasic.ipport4}"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">线路数：</label>
            <div class="formControls col-7">
                <c:out value="${res.relayCnt}"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">总低消：</label>
            <div class="formControls col-7">
                <c:out value="${res.relayRent}"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">所属运营商：</label>
            <div class="formControls col-7">
                <c:choose>
                    <c:when test="${res.operator eq '00'}">中国移动</c:when>
                    <c:when test="${res.operator eq '01'}">中国联通</c:when>
                    <c:when test="${res.operator eq '02'}">中国电信</c:when>
                    <c:when test="${res.operator eq '06'}">其他</c:when>
                </c:choose>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">归属地：</label>
            <div class="formControls col-7">
                <c:out value="${res.cname}"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">签署类型：</label>
            <div class="formControls col-7">
                <c:choose>
                    <c:when test="${res.signType eq '00'}">运营商直签</c:when>
                    <c:when test="${res.signType eq '01'}">第三方资源</c:when>
                </c:choose>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">价格：</label>
            <div class="formControls col-7">
                <c:out value="${res.per}"/>
                元&nbsp;/&nbsp;
                <c:choose>
                    <c:when test="${res.cycle eq '6'}">6秒</c:when>
                    <c:when test="${res.cycle eq '60'}">分</c:when>
                </c:choose>
            </div>
            <div class="col-2"></div>
        </div>

        <h4 class="page_title"></h4>

        <div class="row cl">
            <label class="form-label col-1"></label>
            <div class="col-2">运营商</div>
            <div class="col-2">类型</div>
            <div class="col-2">落地号码</div>
            <div class="col-4">价格</div>
            <div class="col-1"></div>
        </div>

    <c:if test="${not empty res.relayResPers}">
        <c:forEach items="${res.relayResPers}" var="per">
        <div class="row cl">
            <label class="form-label col-1"></label>
            <div class="col-2">
                <c:choose>
                    <c:when test="${per.operator eq '00'}">移动</c:when>
                    <c:when test="${per.operator eq '01'}">联通</c:when>
                    <c:when test="${per.operator eq '02'}">电信</c:when>
                </c:choose>
            </div>
            <div class="col-2">
                <c:choose>
                    <c:when test="${per.callType eq '00'}">市话</c:when>
                    <c:when test="${per.callType eq '01'}">长途</c:when>
                </c:choose>
            </div>
            <div class="col-2">
                <c:choose>
                    <c:when test="${per.numType eq '00'}">手机</c:when>
                    <c:when test="${per.numType eq '01'}">固话</c:when>
                    <c:when test="${per.numType eq '04'}">其他</c:when>
                </c:choose>
            </div>
            <div class="col-4">
                <c:out value="${per.per}"/>
                元&nbsp;/&nbsp;
                <c:choose>
                    <c:when test="${per.cycle eq '6'}">6秒</c:when>
                    <c:when test="${per.cycle eq '60'}">分</c:when>
                </c:choose>
            </div>
            <div class="col-1"></div>
        </div>
        </c:forEach>
    </c:if>

    </form>
</div>
</body>
</html>
