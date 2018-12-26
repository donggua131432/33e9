<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="专线语音、智能云调度、录音、电话录音">
    <meta name="description" content="为企业和开发者提供语音功能的API接口，将通信能力应用于各种CRM、ERP、OA等软件系统,调用云通信平台的开放接口发起双向呼叫，业务系统能快速集成通信功能，常用于电销、客服、通知等场景，零设备投入、零维护，按需所取、按量付费,在全国范围内构建接入局、落地局，并搭建同意的话务调度平台，构建一张覆盖全国的上午通讯网，实现话务的智能调度,提供呼叫中心号码代申请、语音短信落地一站式服务实现调度网络全国覆盖、无缝接入话务调度云端处理、高并发支持跨区域、跨运营商、按时间、按用户类别智能调度支持移动远程座席、易扩展性、更便携">
    <title>专线语音_智能云调度_录音_电话录音</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/price.css"/>

    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

    <jsp:include page="../common/header.jsp"/>

    <div class="container CHeight">
        <div class="main_box">
            <div class="price_series">
                <span class="preferential">少量投入&nbsp;更多回报</span>
            </div>
            <div class="price_box">
                <div class="price_main f_left p2">
                    <span class="price_common_title rest_pic"></span>
                    <p class="pri_common">专线语音</p>
                    <div class="price_state"><b>&yen;</b><span class="price">0.2</span><b style="font-weight:normal" class="DesigFont">元/分钟</b></div>
                    <%--<p class="price_bottom re_bottom">所有按照分钟计算</p>--%>
                </div>
                <!--专通号-->
                <div class="price_main f_left bd">
                    <span class="price_common_title Designed_pic4"></span>
                    <p class="pri_common">专号通</p>
                    <p class="DesigFont">专号月租：<span class="DesPrice"><a href="javascript:void(0);" class="DesingBtn">面议</a></span></p>
                    <p class="DesigFont">专号通话：<span class="DesPrice"><b>0.20</b> 元/分钟</span></p>
                    <p class="DesigFont">录音计费：<span class="DesPrice"><b>0.01</b> 元/分钟</span></p>
                </div>
                <!--专通号 end-->
                <div class="price_main f_left p1">
                    <span class="price_common_title znydd_pic"></span>
                    <p class="pri_common">智能云调度</p>
                    <p class="face_talk">面议</p>
                    <%--<p class="price_bottom">所有按照分钟计算</p>--%>
                </div>
                <div class="price_main f_left p3  RightNo">
                    <span class="price_common_title record_pic"></span>
                    <p class="pri_common">录音</p>
                    <div class="price_state"><b>&yen;</b>&nbsp;&nbsp;&nbsp;<span class="price">0.01</span><b>&nbsp;&nbsp;元/分钟</b></div>
                    <%--<p class="price_bottom re_bottom">所有按照分钟计算</p>--%>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../common/footer.jsp"/>

</body>
</html>