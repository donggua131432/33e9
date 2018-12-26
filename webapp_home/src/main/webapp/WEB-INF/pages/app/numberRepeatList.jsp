<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/3/28
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>号码管理</title>
    <link rel="stylesheet" href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>

    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
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
        <script type="text/javascript">showSubMenu('kf','appMgr','numMgr');</script>
        <!-- 左侧菜单 end -->
        <div class="main3">
            <div class="container">
                <div class="msg">
                    <h3>号码管理</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="questionNum">
                    <p class="app_delete_common"><i class="attention"></i>发现${numRepeatList.size()}个问题号码</p>
                    <p class="localSubLeft">以上号码，可用显号池中已存在，不可以重复提交，请修改后重新提交，谢谢！</p>
                    <table class="table_style">
                        <c:forEach var="number" items="${numRepeatList}" step="3" varStatus="status">
                            <tr>
                                <td>${number.number}</td>
                                <td>${numRepeatList[status.index+1].number}</td>
                                <td>${numRepeatList[status.index+2].number}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="questionReason clear">
                    </div>
                    <div class="submitBtn">
                        <button type="button" onclick="javascript:history.go(-1);" class="md_btn">返&nbsp;回</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
