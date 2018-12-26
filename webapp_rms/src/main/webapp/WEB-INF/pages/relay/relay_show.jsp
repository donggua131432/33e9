<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <title>新增中继</title>
</head>
<script type="text/javascript">
    $(function(){
        $("input[name='sipBus']").val([${bus}]);

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        fillCityName($("#areacode").text());
    });

    function fillCityName(gets) {
        if (gets) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/cityMgr/getCityNameByAreaCode'/>",
                data: {"areacode" : gets},
                success: function(msg){
                    if (msg.code == 'ok') {
                        $("#cityname1").text(msg.data.cname);
                        exits = true;
                    } else {
                        exits = msg.msg;
                    }
                }
            });
        }
    }
</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/relay/updateRelayInfo'/>" method="post" class="form form-horizontal" id="form-relay-add">
        <h4 class="page_title">中继信息</h4>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>中继名称：</label>
            <div class="formControls col-6">
                ${sipBasic.relayName}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>中继群编号：</label>
            <div class="formControls col-6">
                ${sipBasic.relayNum}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">类型：</label>
            <div class="formControls col-2">
                <c:choose>
                    <c:when test="${sipBasic.useType eq '00'}">客户</c:when>
                    <c:when test="${sipBasic.useType eq '01'}">供应商</c:when>
                    <c:when test="${sipBasic.useType eq '02'}">平台内部</c:when>
                </c:choose>
            </div>
            <label class="form-label col-2"><c:if test="${sipBasic.useType eq '00'}">业务类型：</c:if></label>
            <div class="formControls col-2">
                <c:if test="${sipBasic.useType eq '00'}">
                    <c:choose>
                        <c:when test="${sipBasic.busType eq '03'}">SIP</c:when>
                        <c:when test="${sipBasic.busType eq '05'}">云话机</c:when>
                        <c:when test="${sipBasic.busType eq '01'}">智能云调度</c:when>
                        <c:when test="${sipBasic.busType eq '91'}">呼叫中心代答中继</c:when>
                    </c:choose>
                </c:if>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>中继方向：</label>
            <div class="formControls col-6">
                <c:if test="${sipBasic.relayType == '00'}">闭塞</c:if>
                <c:if test="${sipBasic.relayType == '01'}">入中继</c:if>
                <c:if test="${sipBasic.relayType == '10'}">出中继</c:if>
                <c:if test="${sipBasic.relayType == '11'}">双向中继</c:if>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>外域对端区号：</label>
            <div class="formControls col-6">
                <span id="areacode">${sipBasic.areacode}</span>
                <span id="cityname1"></span>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>外域对端IP和端口：</label>
            <div class="formControls col-2">
                ${ip1}
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-1">
                ${port1}
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>外域本端IP和端口：</label>
            <div class="formControls col-2">
                ${ip2}
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-1">
                ${port2}
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>内域对端IP和端口：</label>
            <div class="formControls col-2">
                ${ip3}
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-1">
                ${port3}
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>内域本端IP和端口：</label>
            <div class="formControls  col-2">
                ${ip4}
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls  col-1">
                ${port4}
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>SIP-URI域名：</label>
            <div class="formControls col-6">
                ${sipBasic.sipUrl}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>出局R-URI：</label>
            <div class="formControls col-6">
                ${sipBasic.sipOutnoPr}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>并发呼叫限制：</label>
            <div class="formControls col-6">
                ${sipBasic.maxConcurrent}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>中继业务处理：</label>
            <div class="formControls col-10">
                <div class="skin-minimal">
                <c:forEach items="${relayBus}" var="bus">
                    <div class="check-box" style="width: 260px;">
                        <input type="checkbox" id="checkbox-${bus.code}" name="sipBus" value="${bus.code}" disabled>
                        <label for="checkbox-${bus.code}">${bus.name}</label>
                    </div>
                </c:forEach>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>