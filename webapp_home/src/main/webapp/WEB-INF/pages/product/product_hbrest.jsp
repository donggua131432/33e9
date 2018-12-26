<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="专线语音,为企业和开发者提供语音功能的API接口，将通信能力应用于各种CRM、ERP、OA等软件系统">
    <meta name="description" content="调用云通信平台的开放接口发起双向呼叫，业务系统能快速集成通信功能，常用于电销、客服、通知等场景，零设备投入、零维护，按需所取、按量付费">
    <title>专线语音_让通信更简单</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/product_intro.css"/>
    <style>

    </style>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>
<div class="hbr_container1">
    <div class="main_box">
        <div class="hbr_main1">
            <div class="zydOne">
                <span class="zyd_com_img_kj zyd_img1"></span>
                <span class="zyd_com_img_kj hbr_img3" onclick="toskip('<c:url value="/product/hbrest"/>')"></span>
                <span class="zyd_com_img_kj zyd_img4" onclick="toskip('<c:url value="/product/designed"/>')"></span>
                <span class="zyd_com_img_kj hbr_img2" onclick="toskip('<c:url value="/product/znydd"/>')"></span>
                <span class="zyd_com_img_kj zyd_img1"></span>
            </div>
            <div class="zydTwo">
                <span class="product_common">专&nbsp;线&nbsp;语&nbsp;音</span>
                <p>让&nbsp;通&nbsp;信&nbsp;更&nbsp;简&nbsp;单</p>
            </div>
            <div class="zydThree"><img src="${appConfig.resourcesRoot}/master/img/huiBoRest_PeiTu.png" alt="API接口图"/></div>
            <div class="zydFour">
                <span class="product_common">易&nbsp;用&nbsp;的&nbsp;通&nbsp;信&nbsp;接&nbsp;口</span>
                <p>玖云平台为开发者提供Restful&nbsp;API接口和SDK开发工具包</p>
            </div>
        </div>
    </div>
</div>
<div class="hbr_container2">
    <div class="main_box">
        <div class="hbr_main2">
            <div class="wisdom_img"><img src="${appConfig.resourcesRoot}/master/img/huiBoRest_yingYong.png" alt="语音功能图"/></div>
            <div class="zydFour hbrFour_top">
                <span class="product_common">丰&nbsp;富&nbsp;的&nbsp;语&nbsp;音&nbsp;功&nbsp;能</span>
                <p>实现PC客户端、网页、APP等软件里的点击呼叫、网页回呼等语音功能</p>
                <p>常见的应用包括电销、企业客户批量通知、业务咨询、客服、企业内部通信等</p>
            </div>
        </div>
    </div>
</div>
<div class="hbr_container4 clear">
    <div class="hbr_container4Two">
        <div class="main_box" style="position: relative">
            <div class="hbr_main4">
                <div class="APPCode_top code_common">
                    <span class="product_common">广&nbsp;泛&nbsp;的&nbsp;应&nbsp;用&nbsp;场&nbsp;景</span>
                </div>
                <div>
                    <div class="hbr_dw hbr_wz1">
                        <span class="mul_use mul1"></span>
                        <p>网&nbsp;页&nbsp;回&nbsp;呼</p>
                    </div>
                    <div class="hbr_dw hbr_wz2">
                        <span class="mul_use mul2"></span>
                        <p>点&nbsp;击&nbsp;呼&nbsp;叫</p>
                    </div>
                    <div class="hbr_dw hbr_wz3">
                        <span class="mul_use mul3"></span>
                        <p>批&nbsp;量&nbsp;去&nbsp;电</p>
                    </div>
                    <div class="hbr_dw hbr_wz4">
                        <span class="mul_use mul4"></span>
                        <p>数&nbsp;据&nbsp;分&nbsp;析</p>
                    </div>
                    <div class="hbr_dw hbr_wz5">
                        <span class="mul_use mul5"></span>
                        <p>通&nbsp;话&nbsp;记&nbsp;录</p>
                    </div>
                    <div class="hbr_dw hbr_wz6">
                        <span class="mul_use mul6"></span>
                        <p>隐&nbsp;藏&nbsp;号&nbsp;码</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="hbr_container3">
    <div class="main_box">
        <div class="hbr_main3">
            <div class="product_advantage clear">
                <span class="product_common">专&nbsp;线&nbsp;语&nbsp;音&nbsp;产&nbsp;品&nbsp;优&nbsp;势</span>
            </div>
            <div class="advantage_list">
                <ul>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage hbr_ad1"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_blue_color">快速集成</p>
                                <span class="zyd_common_font">
                                    少量代码即可集成到企业ERP、CRM、OA系统中<br/>
                                    支持主流编程语言JS、PHP、C++等和主流操<br/>
                                    作系统iOS、Android、Windows
                                </span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage hbr_ad2"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_blue_color">零购买设备</p>
                                <span class="zyd_common_font">
                                    企业不再购买部署设备，按需取用、按需付费和集<br/>中管理
                                </span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage hbr_ad3"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_blue_color">零维护成本</p>
                                <span class="zyd_common_font">
                                    云端部署，企业无需投入人力维护
                                </span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage hbr_ad4"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_blue_color">语音质量</p>
                                <span class="zyd_common_font">
                                    非网络电话，等同于运营商网络的电话质量
                                </span>
                        </div>
                    </li>
                    <li>
                        <div class="f_left">
                            <span class="zyd_advantage hbr_ad5"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="common_blue_color">功能全面</p>
                                <span class="zyd_common_font">
                                    隐藏号码，灵活去电，全程录音，通话数据分析
                                </span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
</body>

<script src="${appConfig.resourcesRoot}/master/js/product.js"></script>
</html>