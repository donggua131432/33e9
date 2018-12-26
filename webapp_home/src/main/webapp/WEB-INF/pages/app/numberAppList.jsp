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

    <script id="appListTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${appName}</td>
            <td>\${appid}</td>
            <td><a href="<c:url value='/numMgr/appNumberManager1?appid=\${appid}&appName=\${appName}'/>">点击管理</a></td>
        </tr>
    </script>
    <script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>

    <script type="text/javascript">
        $(function(){
            $("#appListTable").page({
                url:"<c:url value='/numMgr/getAppList'/>",
                pageSize:5,
                integralBody:"#appListTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#appListTemplate",// tmpl中的模版ID
                pagination:"#appListPagination",
                dataRowCallBack:dataRowCallBack
            });
        })

        function dataRowCallBack(row,num) {
            return row;
        }
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
        <script type="text/javascript">showSubMenu('kf','appMgr','numMgr');</script>
        <!-- 左侧菜单 end -->
        <div class="main3">
            <div class="container">
                <div class="msg">
                    <h3>号码管理</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="numManagement">
                    <table class="table_style" id="appListTable">
                        <thead>
                            <tr>
                                <th>应用名称</th>
                                <th>APP&nbsp;ID</th>
                                <th>号码管理</th>
                            </tr>
                        </thead>
                        <tbody id="appListTbody">

                        </tbody>
                    </table>
                    <div class="turnPage f_right"  id="appListPagination"></div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
