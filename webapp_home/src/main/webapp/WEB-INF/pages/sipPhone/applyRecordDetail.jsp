<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心页面</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui.min.css" />
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/sui.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none;"></div>
<!--遮罩end-->
<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('sphone','sipPhoneMgr','sipPhoneNumMgr');</script>
        <!-- 左侧菜单 end -->

        <!--右边内容-->
        <div class="main3">
            <div class="container" >
                <div class="msg">
                    <h3>号码管理>申请记录>查看</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="ringEdit">
                    <div class="create_app_common">
                        <label class="create_app_title_left" >应用名称:</label>
                        <span  class="ringCommon">${appInfo.appName}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >APP&nbsp;&nbsp;&nbsp;ID:</label>
                        <span  class="ringCommon">${appInfo.appid}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份:</label>
                        <span>${sipPhoneApply.pname}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市:</label>
                        <span>${sipPhoneApply.cname}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left">SIP号码数量 :</label>
                        <span>${sipPhoneApply.amount}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left">绑定固话比例:</label>
                        <span>${sipPhoneApply.rate}:1</span>
                    </div>
                    <div class="create_app_common" style="padding-top:30px;">
                        <label class="create_app_title_left">状态:</label>
                        <c:if test="${sipPhoneApply.auditStatus =='00'}">
                            <span>审核中</span>
                        </c:if>
                        <c:if test="${sipPhoneApply.auditStatus =='01'}">
                            <span>通过</span>
                        </c:if>
                        <c:if test="${sipPhoneApply.auditStatus =='02'}">
                            <span>不通过</span>
                        </c:if>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left">原因:</label>
                        <span>${sipPhoneApply.auditCommon}</span>
                    </div>
                    <a href="<c:url value='/sipPhoneAppMgr/applySipPhoneRecord'/>?appid=${appInfo.appid}"><button type="submit" class="sm_btn SmTop NumberBtn">返&nbsp;回</button></a>
                   </div>
                </div>
               </div>
        <!--右边内容 end-->
 </div>
</section>
</body>
</html>
