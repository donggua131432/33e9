<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="专号通">
    <meta name="description" content="专号通是为了保护用户真实联系方式，通过玖云平台提供的专号建立联系的呼叫业务用户在指定时间内可通过平台指派的专号联系同时隐藏用户之间的真实联系方式从而避免用户被恶意来电骚扰、以及信息泄露等风险">

    <title>专号通</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/product_intro.css"/>

    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>
<!--header-->
<jsp:include page="../common/header.jsp"/>
<!--header end-->

<!--DesigBg1-->
<div class="zyd_container1 DesigBg">
    <div class="main_box">
        <div class="znydd_main1">
            <div class="zydOne">
                <span class="zyd_com_img_kj zyd_img1"></span>
                <span class="zyd_com_img_kj zyd_img3" onclick="toskip('<c:url value="/product/hbrest"/>')"></span>
                <span class="zyd_com_img_kj zyd_img4b" onclick="toskip('<c:url value="/product/designed"/>')"></span>
                <span class="zyd_com_img_kj zyd_img2b" onclick="toskip('<c:url value="/product/znydd"/>')"></span>
                <span class="zyd_com_img_kj zyd_img1"></span>
            </div>
            <div class="zydTwo">
                <span class="product_common">专&nbsp;号&nbsp;通</span>
                <p class="DesigColor">更&nbsp;私&nbsp;密, 更&nbsp;便&nbsp;捷</p>
            </div>
            <div class="zydThree DesigTop">
                <img src="${appConfig.resourcesRoot}/master/img/Designed_pic5.png" alt="覆盖通信网图"/>
            </div>
            <div class="zydFour whiteColor">
                <p>专号通是为了保护用户真实联系方式，通过玖云平台提供的专号建立联系的呼叫业务</p>
                <p>用户在指定时间内可通过平台指派的专号联系</p>
                <p>同时隐藏用户之间的真实联系方式从而</p>
                <p>避免用户被恶意来电骚扰、以及信息泄露等风险</p>
            </div>
        </div>
    </div>
</div>
<!--DesigBg1 end-->
<!--DesigBg2-->
<div class="DesigBg2">
    <div class="main_box">
        <div class="znydd_main2">
            <h2 class="DesigedH2">应&nbsp;用&nbsp;场&nbsp;景</h2>
            <ul class="DesignUl">
                <li>
                    <img src="${appConfig.resourcesRoot}/master/img/Designed_pic6.png" alt="约车软件" title="约车软件">
                    <p>在约车软件行业，司机骚扰乘客的事件频频发生，为该行业带来了负面影响；<br/>专号通保护司机及乘客的真实联系方式，降低了骚扰事件发生的可能性</p>
                </li>
                <li>
                    <img src="${appConfig.resourcesRoot}/master/img/Designed_pic7.png" alt="=房产中介" title="房产中介">
                    <p>经纪人私单交易是房产中介行业最大的困惑；<br/>专号通为经纪人手机匹配一个专号，用户通过专号与经纪人保持联系，<br/>专号号码长期有效，换人不换号，防止用户流失</p>
                </li>
                <li class="RightNo">
                    <img src="${appConfig.resourcesRoot}/master/img/Designed_pic8.png" alt="移动医疗" title="移动医疗">
                    <p>移动医疗的服务建立在医患电话沟通的基础上，为防止医患纠纷等问题发生，专号通可以让患者与医生建立通话，通话结束后，患者将无法通过该专号联系到医生，当患者和医生在沟通过程中发生纠纷时，客户可调取录音，将事件回溯，提升其服务质量</p>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--DesigBg2 end-->
<!--DesigBg3-->
<div class="zyd_container3 DesigBg DesigBott">
    <div class="main_box">
        <div class="znydd_main3">
            <div class="product_advantage clear"><span class="product_common">专&nbsp;号&nbsp;通&nbsp;产&nbsp;品&nbsp;优&nbsp;势</span></div>
            <div class="DesigList">
                <ul>
                    <li>
                        <div class="f_left DesigIco">
                            <i class="ico1"></i>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color DesigColor">轻松集成</p>
                            <span class="zyd_common_font whiteColor">少量代码即可集成到客户的业务流程中</span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left DesigIco">
                            <i class="ico2"></i>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color DesigColor">专号通话</p>
                            <span class="zyd_common_font whiteColor">在指定时间内，用户可直接拨打专号呼叫双方，当<br/>超过指定时间，专号失效，播放号码失效提示音</span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left DesigIco">
                            <i class="ico3"></i>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color DesigColor">信息保护</p>
                            <span class="zyd_common_font whiteColor">隐藏用户之间的真实联系方式，避免用户被恶意<br/>来电骚扰、信息泄露等风险</span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left DesigIco">
                            <i class="ico4"></i>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color DesigColor">自助专号池</p>
                                <span class="zyd_common_font whiteColor">提多元化专号类型，包括固话、95等号码类别可供客<br/>户选用，客户可自定义号码用途及有效期
                                </span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left DesigIco">
                            <i class="ico5"></i>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color DesigColor">智能调配</p>
                            <span class="zyd_common_font whiteColor">提供不同地区的电话号码作为专号，而且可根据当地<br/>业务繁忙程度做出智能调配</span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left DesigIco">
                            <i class="ico6"></i>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color DesigColor">通话录音</p>
                            <span class="zyd_common_font whiteColor">用户通话实时录音，当遇到纠纷时，可将事件回溯，<br/>提升客服质量</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--DesigBg3 end-->

<!--footer-->
<jsp:include page="../common/footer.jsp"/>
<!--footer end-->
<script src="${appConfig.resourcesRoot}/master/js/product.js"></script>
</body>
</html>