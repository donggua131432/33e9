<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/2/1
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="common/loadMeta.jsp"%>
    <LINK rel="Bookmark" href="${appConfig.resourcesRoot}/images/favicon.ico" />
    <LINK rel="Shortcut Icon" href="${appConfig.resourcesRoot}/images/favicon.ico" />
    <title>玖云运营管理平台</title>
    <%@include file="common/loadCss.jsp"%>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/skin/default/skin.css" id="skin" configPath="${appConfig.resourcesRoot}"/>
    <%@include file="common/loadJs.jsp"%>
</head>
<body>
<%@include file="common/header.jsp"%>
<%@include file="common/left.jsp"%>

<section class="Hui-article-box">
    <div id="Hui-tabNav" class="Hui-tabNav">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
                <li class="active"><span title="我的桌面" data-href="<c:url value='/main'/>">我的桌面</span><em></em></li>
            </ul>
        </div>
        <%--<div class="Hui-tabNav-more btn-group">
            <a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a>
            <a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
        </div>--%>
    </div>
    <div id="iframe_box" class="Hui-article">
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            <iframe scrolling="yes" frameborder="0" src="<c:url value='/main'/>"></iframe>
        </div>
    </div>
</section>
</body>
</html>