<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/8/22
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>客户信息配置——开通</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        $(function(){
            $.HuitabHref("#feeRateTab .tabBar span","#feeRateTab .tabCon","current","click","0");
        })

    </script>
</head>
<body>
<%--<jsp:include page="../common/nav.jsp"/>--%>

<div id="feeRateTab" class="HuiTab">

    <input type="hidden" id="sid" name="sid" value="${sid}" >
    <div class="tabBar cl">
        <span href="<c:url value='/busType/addBusType?sid=${sid}'/>">开通业务</span>
        <span href="<c:url value='/extraType/addExtraType?sid=${sid}'/>">开通增值服务</span>
    </div>
    <div class="tabCon">
        <iframe frameborder="0" width="100%" height="91%" src="<c:url value='/busType/addBusType?sid=${sid}'/>"></iframe>
    </div>
</div>
</body>
</html>
