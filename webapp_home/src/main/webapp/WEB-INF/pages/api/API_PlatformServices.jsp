<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>平台服务承诺</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/API.css"/>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="API_container">
    <div class="main_box">
        <div class="main_box">
            <div class="API_box">
                <div class="API_main1 f_left">
                    <div class="api_left api_left_color">
                        <b class="api_txt"></b>
                        <span class="l_api">所有文档</span>
                    </div>

                    <jsp:include page="../common/api_nav.jsp"/>

                </div>
                <div class="API_main2 f_left" style="clear: both">
                    <div style="position:fixed;width:810px;top:85px;height:36px;background:#ECECEC;"></div>
                    <div id="api_ma2_1">
                        <div class="api_m2"><span>平台服务承诺</span></div>
                        <div>
                            <div class="api_m2_box API1">
                                <span class="api_com_font">1.服务方式</span>
                                <p>
                                    玖云平台提供的标准技术服务包含以下两种服务方式<br/>
                                    (1).官网在线客服
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/WebsiteService.png" alt="授权书模板图片"/></p>
                                <p>
                                    (2).官方客户服务电话—0755-33548988
                                </p>
                            </div>
                            <div class="api_m2_box API2">
                                <span class="api_com_font">2.服务时间</span>
                                <p>技术服务标准服务时间表如下：</p>
                                <table class="API_style">
                                    <tr>
                                        <td>属性</td>
                                        <td>时间</td>
                                        <td>支持范围</td>
                                        <td>备注</td>
                                    </tr>
                                    <tr>
                                        <td rowspan="2">工作日</td>
                                        <td>09:00—17:30</td>
                                        <td>全部服务范围</td>
                                        <td>午休时间为12:00—13:30</td>
                                    </tr>
                                    <tr>
                                        <td>17:30—21:00</td>
                                        <td>紧急故障处理</td>
                                        <td class="redFont">通过紧急热线获取服务</td>
                                    </tr>
                                    <tr>
                                        <td>非工作日</td>
                                        <td>09:00—21:00</td>
                                        <td>紧急故障处理</td>
                                        <td class="redFont">通过紧急热线获取服务</td>
                                    </tr>
                                </table>
                            </div>
                            <div class="api_m2_box API3">
                                <span class="api_com_font">3.服务范围</span>
                                <p>
                                    为了让用户更快速有效的接入平台的产品，我们将提供多种技术服务。<br/>
                                    具体包括以下几个方面：<br/>
                                    (1).售前咨询<br/>
                                    咨询范围包括产品特性、价格、性能、服务标准等<br/>
                                    (2).技术对接咨询<br/>
                                    包括用户对接平台产品过程中遇到的与玖平台相关的所有问题<br/>
                                    (3).产品新需求的搜集<br/>
                                    用户在平台产品使用过程中，会对产品提出新的建议与需求，技术服务会及时将问题反馈给相关产品部门<br/>
                                    (4).售后维护<br/>
                                    用户对接完成正式使用过程中，对产品的友好性，可靠性及稳定性等会有一些反馈，技术服务将及时确认问题，并协助处理，最后达到用户满意<br/>
                                    (5).投诉处理<br/>
                                    接收处理用户投诉，责任到人或具体部门，最终将处理结果反馈给客户，达到用户满意<br/>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="API_main3 f_left">
                    <div class="stb"> <span class="click_tb spread_tb"></span></div>
                    <div class="api_right_box THeight">
                        <div class="api_right_center">
                            <p class="b1"><b class="b"></b>1&nbsp;服务方式</p>
                            <p class="b2"><b class="b"></b>2&nbsp;服务时间</p>
                            <p class="b3"><b class="b"></b>3&nbsp;服务范围</p>
                        </div>
                    </div>
                    <div class="xtb"><span class="click_tb close_tb"></span></div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/api.js"></script>

</body>
</html>