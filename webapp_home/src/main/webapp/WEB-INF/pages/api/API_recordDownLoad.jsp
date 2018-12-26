<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>录音及下载接口</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/API.css"/>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="API_container">
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
                <div id="apima2" style="position: fixed;width: 810px;top: 85px;height:36px;background: #ECECEC;"></div>
                <div id="api_ma2_1">
                    <div class="api_m2"><span>录音及下载接口</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.录音介绍</span>
                            <p>
                                玖云平台录音功能不提供单独接口，而是作为参数在专线语音接口中进行设置，如果设置录音参数则该次通话将会被录音，录音开始时间为A路接听时间，结束时间为通话结束时间。开发者可通过两种方式获取录音文件，分别为，呼叫挂机计费通知接口中返回的录音下载地址下载、官网用户控制中心通话记录界面点击下载。所有录音文件免费保留30天，到期自动删除。
                            </p>
                            <p>
                                通过下载地址下载录音文件时，必须添加鉴权参数（生成规则详见下方说明），鉴权通过后可进行文件下载。
                            </p>
                            <p>
                                玖云平台录音下载通过HTTP GET方式提交请求。
                            </p>
                        </div>
                        <div class="api_m2_box API2">
                            <span class="api_com_font">2.录音及下载流程</span>
                            <p class="picWidth">
                                <img src="${appConfig.resourcesRoot}/master/img/APIrecord.jpg" alt="录音下载流程图"/>
                            </p>
                            <p>
                                (1).录音功能作为参数在&nbsp;<a href="<c:url value="/api/dedicatedVoice"/>" class="Aline">专线语音REST接口</a>&nbsp;中定义，请求时需要将needRecord参数值设置为1<br/>
                                (2).录音开始时间为A路接听时间，结束时间为通话结束时间<br/>
                                (3).录音会在通话结束后10分钟左右生成<br/>
                                (4).如果应用配置了有效的回调地址，则可在呼叫挂机计费通知回调中获取下载地址<br/>
                                (5).官网用户控制中心的通话记录界面也可以下载已录音通话对应的录音文件<br/>
                            </p>
                        </div>
                        <div class="api_m2_box API3">
                            <span class="api_com_font">3.请求参数</span>
                            <table class="API_style">
                                <tr>
                                    <td>属性</td>
                                    <td>类型</td>
                                    <td>约束</td>
                                    <td>说明</td>
                                </tr>
                                <tr>
                                    <td>downUrl</td>
                                    <td>url</td>
                                    <td>必选</td>
                                    <td>呼叫挂机计费通知中返回的该次通话的录音下载地址</td>
                                </tr>
                                <tr>
                                    <td>authParameter</td>
                                    <td>String</td>
                                    <td>必选</td>
                                    <td>录音下载鉴权参数</td>
                                </tr>
                            </table>
                            <p class="APIsmFont">参数说明：</p>
                            <p>
                                (1).auth：录音下载鉴权参数，生成规则如下<br/>
                                •  URL后必须带有auth参数，例如：auth =AAABBBCCCDDDEEEFFFGGG<br/>
                                •  使用MD5加密（主账号Id + : + 主账号授权令牌）；其中主账号Id和主账号授权令牌分别对应管理控制台中accountSid和authToken<br/>
                                •  authParameter参数需要大写<br/>
                                (2).downUrl：呼叫挂机计费通知中返回的通话录音下载地址
                            </p>
                        </div>
                        <div class="api_m2_box API4">
                            <span class="api_com_font">4.请求示例</span>
                            <p class="blueBg">GET {downUrl}?auth={authParameter}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="API_main3 f_left">
                <div class="stb"> <span class="click_tb spread_tb"></span></div>
                <div class="api_right_box FHeight">
                    <div class="api_right_center">
                        <p class="b1"><b class="b"></b>1&nbsp;录音介绍</p>
                        <p class="b2"><b class="b"></b>2&nbsp;录音及下载流程</p>
                        <p class="b3"><b class="b"></b>3&nbsp;请求参数</p>
                        <p class="b4"><b class="b"></b>4&nbsp;请求示例</p>
                    </div>
                </div>
                <div class="xtb"><span class="click_tb close_tb"></span></div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/api.js"></script>
</body>
</html>