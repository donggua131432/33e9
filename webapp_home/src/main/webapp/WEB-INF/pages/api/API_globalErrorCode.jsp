<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>全局错误码表</title>
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
                    <div class="api_m2"><span>全局错误码表</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <span class="api_com_font">1.错误码表</span><br/>
                            <span class="STop">
                                <b class="icon guideTxt"></b>内部错误
                            </span>
                            <table class="API_style">
                                <tr>
                                    <td>代码</td>
                                    <td>含义</td>
                                </tr>
                                <tr>
                                    <td>900000</td>
                                    <td>未知错误</td>
                                </tr>
                                <tr>
                                    <td>900001-900097</td>
                                    <td>内部错误（预留）</td>
                                </tr>
                                <tr>
                                    <td>900098</td>
                                    <td>参数错误</td>
                                </tr>
                                <tr>
                                    <td>900099</td>
                                    <td>无效版本号</td>
                                </tr>
                            </table>
                            <span class="STop">
                                <b class="icon guideTxt"></b>账号
                            </span>
                            <table class="API_style">
                                <tr>
                                    <td style="width:15%">代码</td>
                                    <td>含义</td>
                                </tr>
                                <tr>
                                    <td>900100</td>
                                    <td>sig参数为空</td>
                                </tr>
                                <tr>
                                    <td>900101</td>
                                    <td>Authorization解密异常</td>
                                </tr>
                                <tr>
                                    <td>900102</td>
                                    <td>Authorization格式不对（没有或非一个英文冒号）</td>
                                </tr>
                                <tr>
                                    <td>900103</td>
                                    <td>accountSid为空</td>
                                </tr>
                                <tr>
                                    <td>900104</td>
                                    <td>accountSid长度不符（32）</td>
                                </tr>
                                <tr>
                                    <td>900105</td>
                                    <td>accountSid格式不符（英文或数字）</td>
                                </tr>
                                <tr>
                                    <td>900106</td>
                                    <td>时间戳为空</td>
                                </tr>
                                <tr>
                                    <td>900107</td>
                                    <td>时间戳长度不符</td>
                                </tr>
                                <tr>
                                    <td>900108</td>
                                    <td>时间戳格式不符（数字）</td>
                                </tr>
                                <tr>
                                    <td>900109</td>
                                    <td>时间戳无效</td>
                                </tr>
                                <tr>
                                    <td>900110</td>
                                    <td>SID用户不存在</td>
                                </tr>
                                <tr>
                                    <td>900111</td>
                                    <td>验证参数sig不通过</td>
                                </tr>
                                <tr>
                                    <td>900112</td>
                                    <td>appId为空</td>
                                </tr>
                                <tr>
                                    <td>900113</td>
                                    <td>appId不存在</td>
                                </tr>
                                <tr>
                                    <td>900114</td>
                                    <td>appId不正常</td>
                                </tr>
                                <tr>
                                    <td>900115</td>
                                    <td>appId回调url不启用</td>
                                </tr>
                                <tr>
                                    <td>900116</td>
                                    <td>appId不属于主账号</td>
                                </tr>
                                <tr>
                                    <td>900117</td>
                                    <td>主账号欠费</td>
                                </tr>
                                <tr>
                                    <td>900118</td>
                                    <td>subId为空</td>
                                </tr>
                                <tr>
                                    <td>900119</td>
                                    <td>subId不存在（不为空时）</td>
                                </tr>
                                <tr>
                                    <td>900120</td>
                                    <td>subId不属于应用</td>
                                </tr>
                                <tr>
                                    <td>900121</td>
                                    <td>subId不正常</td>
                                </tr>
                                <tr>
                                    <td>900122</td>
                                    <td>主账号不正常</td>
                                </tr>
                                <tr>
                                    <td>900123</td>
                                    <td>appId已超出限额</td>
                                </tr>
                            </table>
                            <span class="STop">
                                <b class="icon guideTxt"></b>专线语音
                            </span>
                            <table class="API_style">
                                <tr>
                                    <td style="width:15%">代码</td>
                                    <td>含义</td>
                                </tr>
                                <tr>
                                    <td>900200</td>
                                    <td>from为空</td>
                                </tr>
                                <tr>
                                    <td>900201</td>
                                    <td>from格式不符（手机-11位数字，以1开头、固话-带区号11/12位，以0开头）</td>
                                </tr>
                                <tr>
                                    <td>900202</td>
                                    <td>to为空</td>
                                </tr>
                                <tr>
                                    <td>900203</td>
                                    <td>to格式不符（手机、固话、400号码、9XXXX及扩展号-最小5位，最大11位，包括9本身）</td>
                                </tr>
                                <tr>
                                    <td>900204</td>
                                    <td>主被叫显号未审核</td>
                                </tr>
                                <tr>
                                    <td>900205</td>
                                    <td>主叫显号不能为主叫号码本身</td>
                                </tr>
                                <tr>
                                    <td>900206</td>
                                    <td>主叫显号未审核</td>
                                </tr>
                                <tr>
                                    <td>900207</td>
                                    <td>被叫显号不能为被叫号码本身</td>
                                </tr>
                                <tr>
                                    <td>900208</td>
                                    <td>被叫显号未审核</td>
                                </tr>
                                <tr>
                                    <td>900209</td>
                                    <td>maxCallTime格式不符（不为空时，小于0或超过8小时）</td>
                                </tr>
                                <tr>
                                    <td>900210</td>
                                    <td>needRecord格式不符（不为空时，非0和1数字）</td>
                                </tr>
                                <tr>
                                    <td>900211</td>
                                    <td>countDownTime格式不符（不为空时，非5/30/50数字）</td>
                                </tr>
                                <tr>
                                    <td>900212</td>
                                    <td>countDownTime无效（maxCallTime为空或maxCallTime小于countDownTime）</td>
                                </tr>
                                <tr>
                                    <td>900213</td>
                                    <td>callId为空</td>
                                </tr>
                                <tr>
                                    <td>900214</td>
                                    <td>queryTime为空</td>
                                </tr>
                                <tr>
                                    <td>900215</td>
                                    <td>queryTime格式不符（长度和数字）</td>
                                </tr>
                                <tr>
                                    <td>900216</td>
                                    <td>userData格式不符（不为空时，最长128字节）</td>
                                </tr>
                                <tr>
                                    <td>900217</td>
                                    <td>callId无效</td>
                                </tr>
                                <tr>
                                    <td>900218</td>
                                    <td>type为空</td>
                                </tr>
                                <tr>
                                    <td>900219</td>
                                    <td>通过记录已超过48小时</td>
                                </tr>
                                <tr>
                                    <td>900220</td>
                                    <td>from黑名单号码限制</td>
                                </tr>
                                <tr>
                                    <td>900221</td>
                                    <td>to黑名单号码限制</td>
                                </tr>
                                <tr>
                                    <td>900222</td>
                                    <td>主叫显号黑名单号码限制</td>
                                </tr>
                                <tr>
                                    <td>900223</td>
                                    <td>被叫显号黑名单号码限制</td>
                                </tr>
                                <tr>
                                    <td>900224</td>
                                    <td>查询次数超限</td>
                                </tr>
                            </table>
                            <span class="STop">
                                <b class="icon guideTxt"></b>子账号
                            </span>
                            <table class="API_style">
                                <tr>
                                    <td style="width:15%">代码</td>
                                    <td>含义</td>
                                </tr>
                                <tr>
                                    <td>900300</td>
                                    <td>subName为空</td>
                                </tr>
                                <tr>
                                    <td>900301</td>
                                    <td>subName长度不符</td>
                                </tr>
                                <tr>
                                    <td>900302</td>
                                    <td>subName已存在</td>
                                </tr>
                                <tr>
                                    <td>900303</td>
                                    <td>newSubName为空</td>
                                </tr>
                                <tr>
                                    <td>900304</td>
                                    <td>newSubName长度不符</td>
                                </tr>
                                <tr>
                                    <td>900305</td>
                                    <td>newSubName已存在</td>
                                </tr>
                                <tr>
                                    <td>900306</td>
                                    <td>subId长度不符</td>
                                </tr>
                                <tr>
                                    <td>900307</td>
                                    <td>subId格式不符（英文或数字）</td>
                                </tr>
                            </table>
                            <span class="STop">
                                <b class="icon guideTxt"></b>专号通
                            </span>
                            <table class="API_style">
                                <tr>
                                    <td style="width:15%">代码</td>
                                    <td>含义</td>
                                </tr>
                                <tr>
                                    <td>900400</td>
                                    <td>不存在隐私号码映射</td>
                                </tr>
                                <tr>
                                    <td>900401</td>
                                    <td>realNumA为空</td>
                                </tr>
                                <tr>
                                    <td>900402</td>
                                    <td>realNumA格式不符（手机-11位数字，以1开头、固话-带区号11/12位，以0开头）</td>
                                </tr>
                                <tr>
                                    <td>900403</td>
                                    <td>realNumB为空</td>
                                </tr>
                                <tr>
                                    <td>900404</td>
                                    <td>realNumB格式不符（手机-11位数字，以1开头、固话-带区号11/12位，以0开头）</td>
                                </tr>
                                <tr>
                                    <td>900405</td>
                                    <td>validTime格式不符（不为空时，小于等于0）</td>
                                </tr>
                                <tr>
                                    <td>900406</td>
                                    <td>不存在有效隐私号码</td>
                                </tr>
                                <tr>
                                    <td>900407</td>
                                    <td>realNumA和realNumB相同</td>
                                </tr>
                                <tr>
                                    <td>900408</td>
                                    <td>areaCode格式不符（以0开头，三或者四位、全数字）</td>
                                </tr>
                                <tr>
                                    <td>900409</td>
                                    <td>隐私映射已存在</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="API_main3 f_left">
                <div class="stb"> <span class="click_tb spread_tb"></span></div>
                <div class="api_right_box OHeight">
                    <div class="api_right_center">
                        <p class="b1"><b class="b"></b>1&nbsp;错误码表</p>
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