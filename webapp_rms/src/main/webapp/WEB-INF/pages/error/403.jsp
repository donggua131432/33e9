<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/H-ui.css"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/js/plugins/Hui-iconfont/1.0.6/iconfont.css"/>

    <title>403</title>

    <style type="text/css">
        html {overflow-y:auto}
        .Hui-nav-toggle { display: none!important }
        #page-404 .Hui-wraper{padding:20px; width:auto}
        @media (max-width: 767px) {
            #page-websafecolor .Hui-wraper{ padding:15px}
        }
    </style>
</head>
<body id="page-404">
<section class="Hui-container">
    <div class="Hui-wraper cl">
        <article class="page-404 minWP text-c">
            <p class="error-title"><i class="Hui-iconfont va-m"></i><span class="va-m"> 403</span></p>
            <p class="error-description">不好意思，您没有访问该页面的权限~</p>
            <p class="error-info">您可以：<a href="javascript:;" onclick="history.go(-1)" class="c-primary">&lt; 返回上一页</a><span class="ml-20">|</span><a href="/" class="c-primary ml-20">去首页 &gt;</a></p>
        </article>
    </div>
</section>

</body>
</html>
