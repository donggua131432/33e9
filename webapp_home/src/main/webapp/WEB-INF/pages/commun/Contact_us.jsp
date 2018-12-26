<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">-->
    <title>玖云平台-联系我们</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/login.css">
    <script src="http://api.map.baidu.com/api?v=2.0&ak=8vggsUWcL38cpMGEozcmTr89"></script>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<!-- header start -->
<jsp:include page="../common/header.jsp"/>
<!-- header end -->

<div class="website_container contact_us" style="height:1248px;">
    <div class="contact_head">
        <div class="main_box contact_row1">
            <p>联系我们</p>
            <span class="con_us">CONTACT&nbsp;US</span>
            <span class="Customer_phone"><b>客服电话：</b>0755-33548988</span>
        </div>
    </div>
    <div class="main_box contact_row2" >
        <div class="contact_img" id="allmap"></div>
        <div class="contact_foot" >
            <span class="aboutUs">联系方式:</span>
            <p>公司名称：深圳市大众通信技术有限公司</p>
            <p>公司地址：深圳市南山区高新中一道2号长园新材料港10栋3层</p>
            <p>合作邮箱：support@33e9cloud.com</p>
            <p>客服电话：0755-33548988</p>
            <p>在线咨询<a href="javascript:;" id="onlines"><img src="${appConfig.resourcesRoot}/master/img/online-server.png" alt="QQ咨询"/></a></p>
        </div>
    </div>
</div>

<!-- footer start -->
<jsp:include page="../common/footer.jsp"/>
<!-- footer end -->

<!--map-->
<script type="text/javascript">
    var map = new BMap.Map("allmap");
    map.centerAndZoom("深圳市南山区高新中一道2号长园新材料港",17);
    map.disableScrollWheelZoom();
    var geo = new BMap.Geocoder();
    geo.getPoint("深圳市南山区高新中一道2号长园新材料港",function(point){
        var marker = new BMap.Marker(point);
        map.centerAndZoom(point,50);
        map.addOverlay(marker);
        var opts = {
            width :300,
            height: 100,
            title : "深圳市大众通信技术有限公司"
        };
        // 创建信息窗口对象
        var info = new BMap.InfoWindow("地址:深圳市南山区高新中一道2号长园新材料港10栋3楼",opts);
        // 为标注绑定click事件
        marker.addEventListener("click",function(){
            // 打开信息窗口
            map.openInfoWindow(info,point);
        });
        map.addOverlay(marker);               // 将标注添加到地图中
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    },"深圳市");

    //单个
    BizQQWPA.addCustom({
        aty: '0', //指定工号类型,0-自动分流，1-指定工号，2-指定分组
        a:'0',
        nameAccount: '800164600', //营销 QQ 号码
        selector: 'onlines' //WPA 被放置的元素
    });
</script>
</body>
</html>