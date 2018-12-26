<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="专线语音、智能云调度">
    <meta name="description" content="在全国范围内构建接入局、落地局，并搭建同意的话务调度平台，构建一张覆盖全国的上午通讯网，实现话务的智能调度,提供呼叫中心号码代申请、语音短信落地一站式服务,实现调度网络全国覆盖、无缝接入话务调度云端处理、高并发,支持跨区域、跨运营商、按时间、按用户类别智能调度,支持移动远程座席、易扩展性、更便携">
    <title>专线语音_智能云调度_玖云平台产品系列</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/product.css"/>
    <style>
    </style>

    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="container CHeight">
    <div class="main_box">
        <div class="product_series">
            <h1>玖云平台产品系列</h1>
            <!--<p class="communication_simple">让通信更简单</p>-->
        </div>
        <div class="product_box">

            <div id="hbrest" class="product_main f_left Product_hover1" data-url="${appConfig.resourcesRoot}/master/">
                <span class="common_title common_title1 rest_pic1"></span>
                <p class="pro_common pro_common1">专线语音</p>
                <p class="pro_intro">为企业和开发者提供语音功能的API接口，</p>
                <!--<p class="pro_intro"></p>-->
                <p class="pro_intro">将通信能力应用于</p>
                <p class="pro_intro proIntroB">各种CRM、ERP、OA等软件系统</p>
                <a href="<c:url value='/product/hbrest'/>">了解更多&gt;</a>
                <div class="bottom">
                    <img src="${appConfig.resourcesRoot}/master/img/rest_pic1.png" class="img1" alt="回拨示意图"/>
                </div>
            </div>

            <!--专通号-->
            <div id="designed" class="product_main f_left Product_hover2" data-url="${appConfig.resourcesRoot}/master/">
                <span class="common_title common_title2 Designed_pic1"></span>
                <p class="pro_common pro_common2">专号通</p>
                <p class="pro_intro">用户在指定时间内可通过平台指派的</p>
                <p class="pro_intro">专号联系，同时隐藏用户之间的</p>
                <p class="pro_intro">真实联系方式从而避免用户</p>
                <p class="pro_intro">被恶意来电骚扰、以及信息泄露等风险</p>
                <a href="<c:url value='/product/designed'/>">了解更多&gt;</a>
                <div class="bottom DPor">
                    <img src="${appConfig.resourcesRoot}/master/img/Designed_pic2.png" class="img2" alt="专通号"/>
                </div>
            </div>
            <!--专通号 end-->

            <div id="znydd" class="product_main f_left RightNo Product_hover3" data-url="${appConfig.resourcesRoot}/master/">
                <span class="common_title common_title3 ydd_pic1"></span>
                <p class="pro_common pro_common3">智能云调度</p>
                <p class="pro_intro">在全国范围内建设接入局、落地局，</p>
                <p class="pro_intro">并搭建统一的话务调度平台，</p>
                <p class="pro_intro">构建一张覆盖全国的商务通信网，</p>
                <p class="pro_intro">实现话务的智能调度</p>
                <a href="<c:url value='/product/znydd'/>">了解更多&gt;</a>
                <div class="bottom">
                    <img src="${appConfig.resourcesRoot}/master/img/Traffic_scheduling1.png" class="img3" alt="智能云调度示意图"/>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/product.js"></script>

</body>
</html>