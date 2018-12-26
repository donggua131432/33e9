<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
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
        <script type="text/javascript">showSubMenu('sphone','sipPhoneMgr','voiceListPhone');</script>
        <!-- 左侧菜单 end -->

        <div class="main3">

            <div class="container">
                <div class="msg">
                    <h3>铃声管理&gt;铃声查看</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="ringEdit">
                    <div class="create_app_common">
                        <label class="create_app_title_left" >应用名称:</label>
                        <span  class="ringCommon"><c:out value="${appname}"/></span>
                        <input type="hidden"  id="appid" name="appid" value="${appid}"/>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >铃声名称:</label>
                        <span  class="ringCommon"><c:out value="${voice.voiceName}"/></span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >铃声大小:</label>
                        <span  class="ringCommon"><c:out value="${voice.voiceSize}"/>M</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >铃&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;声:</label>
                        <span class="ringDownL_icon"></span><a href="<c:url value='/voicePhone/download'/>?url=${voice.voiceUrl}" class="ringDownLoad">下载</a>
                    </div>

                    <div class="create_app_common">
                        <label class="create_app_title_left" >审核状态:</label>
                        <c:if test="${voice.status =='02'}">
                            <span  class="ringCommon">审核不通过</span>
                        </c:if>
                        <c:if test="${voice.status =='00'}">
                            <span  class="ringCommon">审核中</span>
                        </c:if>
                        <c:if test="${voice.status =='01'}">
                            <span  class="ringCommon">审核通过</span>
                        </c:if>

                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
                        <span  class="ringCommon"><c:out value="${voice.common}"/></span>
                    </div>
                    <button type="button" id="goList" name="goList" class="sm_btn SmTop">返&nbsp;回</button>
                </div>


            </div>
        </div>
</section>


<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
<script type="text/javascript">
    $("#goList").click(function(){
        window.location="<c:url value='/voicePhone/index'/>";
    })
</script>

</body>
</html>
