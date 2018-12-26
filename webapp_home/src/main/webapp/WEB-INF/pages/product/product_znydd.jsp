<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="智能云调度,在全国范围内构建接入局、落地局，并搭建同意的话务调度平台，构建一张覆盖全国的上午通讯网，实现话务的智能调度">
    <meta name="description" content="提供呼叫中心号码代申请、语音短信落地一站式服务,实现调度网络全国覆盖、无缝接入,话务调度云端处理、高并发,支持跨区域、跨运营商、按时间、按用户类别智能调度,支持移动远程座席、易扩展性、更便携">
    <title>智能云调度_更全面更智慧</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/product_intro.css"/>

    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="zyd_container1">
    <div class="main_box">
        <div class="znydd_main1">
            <div class="zydOne">
                <span class="zyd_com_img_kj zyd_img1"></span>
                <span class="zyd_com_img_kj zyd_img3" onclick="toskip('<c:url value="/product/hbrest"/>')"></span>
                <span class="zyd_com_img_kj zyd_img4" onclick="toskip('<c:url value="/product/designed"/>')"></span>
                <span class="zyd_com_img_kj zyd_img2" onclick="toskip('<c:url value="/product/znydd"/>')"></span>
                <span class="zyd_com_img_kj zyd_img1"></span>
            </div>
            <div class="zydTwo">
                <span class="product_common">智&nbsp;能&nbsp;云&nbsp;调&nbsp;度</span>
                <p>更&nbsp;全&nbsp;面，更&nbsp;智&nbsp;慧</p>
            </div>
            <div class="zydThree zyd_bg">
                <img src="${appConfig.resourcesRoot}/master/img/cover_animation.gif" alt="覆盖通信网图"/>
            </div>
            <div class="zydFour">
                <span class="product_common">覆&nbsp;盖&nbsp;全&nbsp;国&nbsp;的&nbsp;商&nbsp;务&nbsp;通&nbsp;信&nbsp;网</span>
                <p>基于三大运营商网络之上，由玖云平台、接入局和落地局组成，</p>
                <p>自主建设、自主研发、自主运营</p>
            </div>
        </div>
    </div>
</div>

<div class="zyd_container2">
    <div class="main_box">
        <div class="znydd_main2">
            <div class="wisdom_img"><img src="${appConfig.resourcesRoot}/master/img/wisdom-cloud.png" alt="最智慧的云端调度图"/></div>
            <div class="zydFour zydFour_top">
                <span class="product_common">最&nbsp;智&nbsp;慧&nbsp;的&nbsp;云&nbsp;端&nbsp;调&nbsp;度</span>
                <p>玖云平台可根据号码所属区域、客户类型、业务类型、话务峰值,</p>
                <p>将呼入电话调度到指定的营业部、第三方呼叫中心或客户对应的客户经理</p>
            </div>
        </div>
    </div>
</div>

<div class="zyd_container4 clear">
    <div class="main_box">
        <div class="znydd_main4">
            <div class="code_top code_common">
                <span class="code_95">呼叫中心号码代申请及一站式落地</span>
                <p>为客户申请呼叫中心号码，如95、96、400、1010等号码，</p>
                <p>并为这些号码做语音和短信的接入和落地；申请通过率高，工程实施周期短，全国覆盖无盲区</p>
            </div>
            <div class="zyd_code_img">
                <img src="${appConfig.resourcesRoot}/master/img/955scheduling.png" alt="95码号语音短信操作图"/>
            </div>
            <div class="code_common code_common_top">
                <span class="code_95">95号码外呼</span>
                <p>呼叫中心95号码外呼不带区号前缀</p>
                <p>与企业业务系统或手机客户端APP集成，外呼显示95号码</p>
            </div>
            <div  class="zyd_code_img"><img src="${appConfig.resourcesRoot}/master/img/95Call.png" alt="95号码外呼图"/></div>
        </div>
    </div>
</div>

<div class="zyd_container3">
    <div class="main_box">
        <div class="znydd_main3">
            <div class="product_advantage clear">
                <span class="product_common">智&nbsp;能&nbsp;云&nbsp;调&nbsp;度&nbsp;产&nbsp;品&nbsp;优&nbsp;势</span>
            </div>
            <div class="advantage_list">
                <ul>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage ad1"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color">号码快速申请</p>
                            <span class="zyd_common_font">让申请周期由1年缩短至3个月</span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage ad2"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color">省时省力</p>
                            <span class="zyd_common_font">组建专业的团队为企业服务,让企业不需要投入任何<br/>人力和物力,就能轻松实现95号码的使用</span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage ad3"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color">落地周期短</p>
                            <span class="zyd_common_font">在三月内实现95号码在全国各大城市的直接<br/>拨打或接听</span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage ad4"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color">专业服务</p>
                                <span class="zyd_common_font">
                                    提供全国300多个城市固网和移动网的拨测服务;定<br/>
                                    期维护，故障排查响应及时,定位准确,迅速解决
                                </span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage ad5"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color">解决互联互通</p>
                                <span class="zyd_common_font">
                                    在运营商之间搭建互通桥梁,从根本上解决跨网<br/>互联互通问题,
                                    确保用户覆盖全面,正常拨通，<br/>号码显示正确,不出现加0或加区号的现象
                                </span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage ad6"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_font_color">稳定可靠</p>
                            <span class="zyd_common_font">基于运营商PSTN通信网的稳定性,加上主备方案,<br/>比运营商更稳定可靠</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
<script src="${appConfig.resourcesRoot}/master/js/product.js"></script>
</body>
</html>