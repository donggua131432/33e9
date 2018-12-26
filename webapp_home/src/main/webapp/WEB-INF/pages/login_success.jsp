<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

    <title>玖云平台</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/login.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/bootstrap.css">

    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
    <script src="${appConfig.resourcesRoot}/master/js/respond.min.js"></script>
    <style>
        #wrapper{
            height:1050px;
        }
    </style>
    <![endif]-->

</head>
<body>
<div id="wrapper">

    <!-- header start -->
    <jsp:include page="common/header.jsp"/>
    <!-- header end -->

    <div id="section">
        <div class="success_main">
            <div class="container center">
                <div class="row row1">
                        <div>
                            <h1>欢&nbsp;迎&nbsp;回&nbsp;来</h1>
                            <p>通&nbsp;讯&nbsp;无&nbsp;界、云&nbsp;端&nbsp;共&nbsp;享</p>
                        </div>
                </div>
                <div class="row row2">
                    <img src="${appConfig.resourcesRoot}/master/img/lianjia.png" class="img-responsive" alt="图片"/>
                </div>
                <div class="row row3">
                    <a href="${appConfig.url}">返回首页</a>
                    <a href="<c:url value='/accMgr/index'/>" class="top">进入用户控制中心</a>
                </div>
            </div>
        </div>
    </div>

    <!-- footer start -->
    <jsp:include page="common/footer.jsp"/>
    <!-- footer end -->

</div>
<script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/bootstrap.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/login.js"></script>
</body>
</html>