<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>玖云平台-链接失效</title>
    <style>
        *{margin:0;padding:0;}
        .f_left{float:left;}
        body{font-family:"方正兰亭黑体",'方正兰亭细黑', "Microsoft Yahei","黑体";overflow: hidden;}
        .page_bgc{border-top:1px solid #3F4857;width:100%;background:#3F4857;}
        .error_top{margin:200px auto;width:800px;height:300px;}
        .LINK_Failure_img{margin-right:50px;}
        .LINK_Failure_note{margin-top:20px;}
        .LINK_Failure_note>p{margin-top:10px;letter-spacing:8px;font-size:18px;color:#FFFFFF;}
        .LINK_Failure_note>button{margin-top:110px;border:none;border-radius:3px;width:180px;height:40px;letter-spacing:8px;font-size:20px;background:#548BEF;color:#FFFFFF;}
    </style>
</head>
<body>
<div class="page_bgc">
    <div class="error_top">
        <div class="LINK_Failure_img f_left">
            <img src="${appConfig.resourcesRoot}/master/img/logo_3D.png" alt="链接失效"/>
        </div>
        <div class="LINK_Failure_note f_left">
            <p>很抱歉，链接已失效</p>
            <p>请重新注册并接收新的邮件...</p>
            <button type="button" onclick="goHome();">返回首页</button>
        </div>
    </div>
</div>
</body>
<script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
<script>
    var wH=$(window).height();
    $(".page_bgc").height(wH);

    function goHome(){
        window.location.href = '${appConfig.authUrl}';
    }
</script>
</html>
