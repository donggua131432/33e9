<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>新增子账号</title>
</head>

<body>
<div class="pd-20">
    <form action="<c:url value='/sip/addSub'/>" method="post" class="form form-horizontal" id="form-member-add">

        <input type="hidden" id="appid" name="appid" value="${appid}"/>
        <input type="hidden" id="sid" name="sid" value="${sid}"/>

        <div class="row cl">
            <label class="form-label col-3">子账号名称：</label>
            <div class="formControls col-6">
                <c:out value="${relayInfo.subName}"/>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">中继名称：</label>
            <div class="formControls col-6">
                <c:forEach items="${relays}" var="relay">
                    <c:if test="${relay.relayNum eq relayInfo.relayNum}">
                        <c:out value="${relay.relayName}"/>
                    </c:if>
                </c:forEach>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">选择开关：</label>
            <div class="formControls col-6">
                <c:if test="${relayInfo.numFlag eq '00'}">
                    关闭
                </c:if>
                <c:if test="${relayInfo.numFlag eq '01'}">
                    打开
                </c:if>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">被叫号码类型：</label>
            <div class="formControls col-6">
                <c:forEach items="${calledTypes}" var="calledType">
                    <c:if test="${calledType eq 0}">电信&nbsp;&nbsp;</c:if>
                    <c:if test="${calledType eq 1}">移动&nbsp;&nbsp;</c:if>
                    <c:if test="${calledType eq 2}">联通&nbsp;&nbsp;</c:if>
                    <c:if test="${calledType eq 3}">其他&nbsp;&nbsp;</c:if>
                </c:forEach>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">被叫号码限制：</label>
            <div class="formControls col-6">
                <c:forEach items="${calledLimits}" var="calledLimit">
                    <c:if test="${calledLimit eq 0}">手机&nbsp;&nbsp;</c:if>
                    <c:if test="${calledLimit eq 1}">固话&nbsp;&nbsp;</c:if>
                    <c:if test="${calledLimit eq 2}">400&nbsp;&nbsp;</c:if>
                    <c:if test="${calledLimit eq 3}">95XX&nbsp;&nbsp;</c:if>
                    <c:if test="${calledLimit eq 4}">其他&nbsp;&nbsp;</c:if>
                </c:forEach>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">最大并发数：</label>
            <div class="formControls col-6">
                <c:out value="${relayInfo.maxConcurrent}"/>
            </div>
            <div class="col-3"></div>
        </div>


        <h4 class="page_title">中继信息:</h4>

        <div class="row cl">
            <label class="form-label col-3">外域对端IP：</label>
            <div class="formControls col-2">
                    <label>${fn:substringBefore(sipBasic.ipport1, ":")} </label>
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-2">
                <label>${fn:substringAfter(sipBasic.ipport1, ":")} </label>
            </div>
            <div class="formControls col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">外域本端IP：</label>
            <div class="formControls col-2">
                <label>${fn:substringBefore(sipBasic.ipport2, ":")} </label>
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-2">
                <label>${fn:substringAfter(sipBasic.ipport2, ":")} </label>
            </div>
            <div class="formControls col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">内域对端IP：</label>
            <div class="formControls col-2">
                <label>${fn:substringBefore(sipBasic.ipport3, ":")} </label>
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-2">
                <label>${fn:substringAfter(sipBasic.ipport3, ":")} </label>
            </div>
            <div class="formControls col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">内域本端IP：</label>
            <div class="formControls col-2">
                <label>${fn:substringBefore(sipBasic.ipport4, ":")} </label>
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-2">
                <label>${fn:substringAfter(sipBasic.ipport4, ":")} </label>
            </div>
            <div class="formControls col-3"></div>
        </div>


        <h4 class="page_title">费率配置</h4>

        <div class="row cl">
            <label class="form-label col-3">计费单位：</label>
            <div class="formControls col-6">
                <c:if test="${rate.cycle eq 6}">
                    <label>6秒</label>
                </c:if>
                <c:if test="${rate.cycle eq 60}">
                    <label>分钟</label>
                </c:if>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">标准价：</label>
            <div class="formControls col-6">
                <c:if test="${rate.cycle eq 6}">
                    <label>${sipRate.per6}元</label>
                </c:if>
                <c:if test="${rate.cycle eq 60}">
                    <label>${sipRate.per60}元</label>
                </c:if>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">折扣率：</label>
            <div class="formControls col-6">
                <c:if test="${rate.cycle eq 6}">
                    <c:out value="${rate.per6Discount/10}"/>%
                </c:if>
                <c:if test="${rate.cycle eq 60}">
                    <c:out value="${rate.per60Discount/10}"/>%
                </c:if>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">折后价：</label>
            <div class="formControls col-6">
                <c:if test="${rate.cycle eq 6}">
                    <label> ${rate.per6Discount * sipRate.per6 / 1000 }元</label>
                </c:if>
                <c:if test="${rate.cycle eq 60}">
                    <label>${rate.per60Discount * sipRate.per60 / 1000 }元</label>
                </c:if>
            </div>
            <div class="formControls col-3"></div>
        </div>

    </form>
</div>
</body>
</html>