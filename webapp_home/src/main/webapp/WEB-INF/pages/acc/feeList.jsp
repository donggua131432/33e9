<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/2/18
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心</title>
    <link href="${appConfig.resourcesRoot}/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript">

    </script>
</head>

<body>
<jsp:include page="../common/controlheader.jsp"/>
<section>
    <div id="sec_main">
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('zh','acc_manage','feelist');</script>


        <div class="main3">

            <div class="container8">
                <div class="msg">
                    <h3>资费列表</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="Standard_rates">
                    <div class="app_note">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1.您只有在企业资质认证通过后，才可以看到可变更套餐列表；</p>
                        <p>2.您所有的应用及子账号的消费费用，都统一从Account&nbsp;sid账户余额当中扣除；</p>
                        <p>3.折扣率计算方式为：基础费率x折扣率。</p>
                    </div>

                    <div class="charges_list">
                        <span class="stand_common">当前资费</span>
                    </div>

                    <table class="table_style">
                            <thead>
                                <tr>
                                    <th>业务类型</th>
                                    <th>计费类型</th>
                                    <th>计费方式</th>
                                    <th>标准价</th>
                                    <th>折扣率</th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr>
                                    <td rowspan="3">专线语音</td>
                                    <td rowspan="2">通话费用</td>
                                    <td>A路</td>
                                    <td><fmt:formatNumber value="${restRate.restA}" pattern="0.00"/>元/分钟</td>
                                    <td>${restRate.restADiscount/10}%</td>
                                </tr>
                                <tr>
                                    <td>B路</td>
                                    <td><fmt:formatNumber value="${restRate.rest}" pattern="0.00"/>元/分钟</td>
                                    <td>${restRate.restDiscount/10}%</td>
                                </tr>
                                <tr>
                                    <td>录音费用</td>
                                    <td></td>
                                    <td><fmt:formatNumber value=" ${restRate.recording}" pattern="0.00"/>元/分钟</td>
                                    <td>${restRate.recordingDiscount/10}%</td>
                                </tr>


                                <tr>
                                    <td rowspan="6">专号通</td>
                                    <td rowspan="3">回拨</td>
                                    <td>A路</td>
                                    <td><fmt:formatNumber value="${maskRate.maska}" pattern="0.00"/>元/分钟</td>
                                    <td>${maskRate.maskaDiscount/10}%</td>
                                </tr>
                                <tr>
                                    <td>B路</td>
                                    <td><fmt:formatNumber value="${maskRate.maskb}" pattern="0.00"/>元/分钟</td>
                                    <td>${maskRate.maskbDiscount/10}%</td>
                                </tr>
                                <tr>
                                    <td>录音费用</td>
                                    <td><fmt:formatNumber value="${maskRate.recording}" pattern="0.00"/>元/分钟</td>
                                    <td>${maskRate.recordingDiscount/10}%</td>
                                </tr>
                                <tr>
                                    <td rowspan="2">回呼</td>
                                    <td>A路</td>
                                    <td><fmt:formatNumber value="${maskRate.callback}" pattern="0.00"/>元/分钟</td>
                                    <td>${maskRate.callbackDiscount/10}%</td>
                                </tr>

                                <tr>
                                    <td>录音费用</td>
                                    <td><fmt:formatNumber value="${maskRate.recordingCallback}" pattern="0.00"/>
                                        元/分钟</td>
                                    <td>${maskRate.recordingCallbackDiscount/10}%</td>
                                </tr>
                                <tr>
                                    <td>专号月租费</td>
                                    <td>月租</td>
                                    <td><fmt:formatNumber value="${maskRate.rent}" pattern="0.00"/>元</td>
                                    <td>...</td>
                                </tr>


                                <c:if test="${countAxb != 0}">
                                    <tr>
                                        <td rowspan="2">虚拟小号</td>
                                        <td >通话费用</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value="${axbRate.per60}" pattern="0.00"/>元/分钟</td>
                                        <td>${axbRate.per60Discount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td >月租</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value="${axbRate.rent}" pattern="0.00"/>元</td>
                                        <td>${axbRate.rentDiscount/10}%</td>
                                    </tr>
                                </c:if>

                                <tr>
                                    <td>语音通知</td>
                                    <td>语音费用</td>
                                    <td></td>
                                    <td><fmt:formatNumber value="${voiceRate.per60}" pattern="0.00"/>元/分钟</td>
                                    <td>${voiceRate.per60Discount/10}%</td>
                                </tr>

                                <tr>
                                    <td>语音验证码</td>
                                    <td>通话费用</td>
                                    <c:if test="${voiceVerifyRate.feeMode == '00'}">
                                        <td>按分钟计费</td>
                                        <td><fmt:formatNumber value="${voiceVerifyRate.per60}" pattern="0.00"/>元/分钟</td>
                                        <td>${voiceVerifyRate.per60Discount/10}%</td>
                                    </c:if>
                                    <c:if test="${voiceVerifyRate.feeMode == '01'}">
                                        <td>按条计费</td>
                                        <td><fmt:formatNumber value="${voiceVerifyRate.per}" pattern="0.00"/>元/条</td>
                                        <td>${voiceVerifyRate.perDiscount/10}%</td>
                                    </c:if>
                                </tr>

                                <c:if test="${countCc != 0}">
                                    <tr>
                                        <td rowspan="3">智能云调度</td>
                                        <td>呼入</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value=" ${callRate.callin}" pattern="0.00"/>元/分钟</td>
                                        <td>${callRate.callinDiscount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td>呼出</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value=" ${callRate.callout}" pattern="0.00"/>元/分钟</td>
                                        <td>${callRate.calloutDiscount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td>专线路月租</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value=" ${callRate.relayRent * callRate.relayCnt}" pattern="0.00"/>元</td>
                                        <td>100.0%</td>
                                    </tr>

                                </c:if>

                                <c:if test="${countSipPhone != 0}">

                                    <tr>
                                        <td rowspan="11">云话机</td>
                                        <td rowspan="4">回拨</td>
                                        <td>A路</td>
                                        <td><fmt:formatNumber value="${sipphonRate.restA}" pattern="0.00"/>元/分钟</td>
                                        <td>${sipphonRate.restADiscount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td>B路</td>
                                        <td><fmt:formatNumber value="${sipphonRate.restB}" pattern="0.00"/>元/分钟</td>
                                        <td>${sipphonRate.restBDiscount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td>B路SipPhone</td>
                                        <td><fmt:formatNumber value="${sipphonRate.restBSipphone}" pattern="0.00"/>元/分钟</td>
                                        <td>${sipphonRate.restBSipphoneDiscount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td>录音费用</td>
                                        <td><fmt:formatNumber value="${sipphonRate.restRecording}" pattern="0.00"/>元/分钟</td>
                                        <td>${sipphonRate.restRecordingDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td rowspan="3">直拨</td>
                                        <td>直拨</td>
                                        <td><fmt:formatNumber value="${sipphonRate.sipcall}" pattern="0.00"/>元/分钟</td>
                                        <td>${sipphonRate.sipcallDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td>直拨SipPhone</td>
                                        <td><fmt:formatNumber value="${sipphonRate.sipcallsip}" pattern="0.00"/>
                                            元/分钟</td>
                                        <td>${sipphonRate.sipcallsipDiscount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td>录音费用</td>
                                        <td><fmt:formatNumber value="${sipphonRate.sipcallRecording}" pattern="0.00"/>元/分钟</td>
                                        <td>${sipphonRate.sipcallRecordingDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td rowspan="2">回呼</td>
                                        <td>A路</td>
                                        <td><fmt:formatNumber value="${sipphonRate.backcall}" pattern="0.00"/>元/分钟</td>
                                        <td>${sipphonRate.backcallDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td>录音费用</td>
                                        <td><fmt:formatNumber value="${sipphonRate.backcallRecording}" pattern="0.00"/>
                                            元/分钟</td>
                                        <td>${sipphonRate.backcallRecordingDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td>单号码月租</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value="${sipphonRate.numRent}" pattern="0.00"/>
                                            元</td>
                                        <td>${sipphonRate.numRentDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td>单号码低消</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value="${sipphonRate.numMinCost}" pattern="0.00"/>
                                            元</td>
                                        <td>--</td>
                                    </tr>

                                </c:if>


                                <c:if test="${countEcc != 0}">

                                    <tr>
                                        <td rowspan="10">云总机</td>
                                        <td rowspan="2">呼入总机</td>
                                        <td>SIP</td>
                                        <td><fmt:formatNumber value="${ivrRate.callinSip}" pattern="0.00"/>元/分钟</td>
                                        <td>${ivrRate.callinSipDiscount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td>非SIP</td>
                                        <td><fmt:formatNumber value="${ivrRate.callinNonsip}" pattern="0.00"/>元/分钟</td>
                                        <td>${ivrRate.callinNonsipDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td rowspan="2">呼入</td>
                                        <td>直呼</td>
                                        <td><fmt:formatNumber value="${ivrRate.callinDirect}" pattern="0.00"/>元/分钟</td>
                                        <td>${ivrRate.callinDirectDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td>录音</td>
                                        <td><fmt:formatNumber value="${ivrRate.callinRecording}" pattern="0.00"/>
                                            元/分钟</td>
                                        <td>${ivrRate.callinRecordingDiscount/10}%</td>
                                    </tr>


                                    <tr>
                                        <td rowspan="3">呼出</td>
                                        <td>市话</td>
                                        <td><fmt:formatNumber value="${ivrRate.calloutLocal}" pattern="0.00"/>元/分钟</td>
                                        <td>${ivrRate.calloutLocalDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td>长途</td>
                                        <td><fmt:formatNumber value="${ivrRate.calloutNonlocal}" pattern="0.00"/>
                                            元/分钟</td>
                                        <td>${ivrRate.calloutNonlocalDiscount/10}%</td>
                                    </tr>
                                    <tr>
                                        <td>录音</td>
                                        <td><fmt:formatNumber value="${ivrRate.calloutRecording}" pattern="0.00"/>
                                            元/分钟</td>
                                        <td>${ivrRate.calloutRecordingDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td>总机号码月租</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value="${ivrRate.ivrRent}" pattern="0.00"/>
                                            元</td>
                                        <td>--</td>

                                    </tr>

                                    <tr>
                                        <td>SIP号码月租</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value="${ivrRate.sipnumRent}" pattern="0.00"/>
                                            元</td>
                                        <td>${ivrRate.sipnumRentDiscount/10}%</td>
                                    </tr>

                                    <tr>
                                        <td>SIP号码低消</td>
                                        <td>--</td>
                                        <td><fmt:formatNumber value="${ivrRate.sipnumMinCost}" pattern="0.00"/>
                                            元</td>
                                        <td>--</td>
                                    </tr>

                                </c:if>


                            </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
