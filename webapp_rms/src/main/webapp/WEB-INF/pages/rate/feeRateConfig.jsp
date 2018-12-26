<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2016/6/1
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>专号通费率配置</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
    $(function(){
        $.HuitabHref("#feeRateTab .tabBar span","#feeRateTab .tabCon","current","click","0");
    })

    </script>
</head>
<body>
    <jsp:include page="../common/nav.jsp"/>

    <div id="feeRateTab" class="HuiTab">
        <div class="tabBar cl">
            <span href="<c:url value='/maskRate/addMaskRate'/>">专号通费率配置</span>
            <span href="<c:url value='/restRate/addRestRate'/>">专线语音费率配置</span>
            <span href="<c:url value='/callRate/addCallRate'/>">智能云调度费率配置</span>
            <span href="<c:url value='/voiceRate/addVoiceRate'/>">语音通知费率配置</span>
            <span href="<c:url value='/spRate/addPhoneRate'/>">云话机费率配置</span>
            <span href="<c:url value='/ivrRate/addIvrRate'/>">云总机费率配置</span>
            <span href="<c:url value='/axbRate/addAxbRate'/>">虚拟小号费率配置</span>
            <span href="<c:url value='/voiceVerifyRate/addVoiceCodeRate'/>">语音验证码费率配置</span>
        </div>
        <div class="tabCon">
            <iframe frameborder="0" width="100%" height="91%" src="<c:url value='/maskRate/addMaskRate'/>"></iframe>
        </div>
    </div>
</body>
</html>
