<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
    <script type="text/javascript">
        $(function(){
            ajaxDicData('${appConfig.authUrl}',"appType","appType","${appInfo.appType}","","",true);
            $("#spanAppType").text($("#appType").find("option:selected").text());
            checkedBox('recordingTypes', '${appExtra.recordingType}');
            checkedBox('valueAddeds', '${appExtra.valueAdded}');

        });


    </script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none"></div>
<!--遮罩end-->

<jsp:include page="../common/controlheader.jsp"/>
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('sphone','sipPhoneMgr','appListPhone');</script>
        <!-- 左侧菜单 end -->
        <div class="main3">
            <div class="container15">
                <div class="msg">
                    <h3>创建应用>应用查看</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="create_application">
                    <div class="app_edit_main">
                        <div class="create_app_common">
                            <label class="create_app_title_left " >应用名称:</label>
                            <span>${appInfo.appName}</span>
                        </div>

                        <div class="create_app_common">
                            <label  class="create_app_title_left " >应用类型:</label>
                            <span id="spanAppType"><select id="appType" name="appType"></select></span>
                        </div>

                        <div class="create_app_common">
                            <%--<label class="create_app_title_left" >APP&nbsp;&nbsp;&nbsp;ID:</label>--%>
                            <span class="create_app_title_left">APP&nbsp;&nbsp;&nbsp;ID:<span class="sm_account">${appInfo.appid}</span></span>
                        </div>

                        <div class="create_app_common">
                            <label  class="create_app_title_left" >应用状态:</label>
							<span>
								<c:if test="${appInfo.status == '00'}">正常</c:if>
								<c:if test="${appInfo.status == '01'}">冻结</c:if>
								<c:if test="${appInfo.status == '02'}">已删除</c:if>
							</span>
                        </div>

                        <%--<div class="create_app_common">--%>
                            <%--<label  class="create_app_title_left" >语音编码:</label>--%>
                            <%--<span>${appExtra.voiceCode}</span>--%>
                        <%--</div>--%>

                        <div class="create_app_common">
                            <label class="create_app_title_left " >语音编码:</label>
                            <c:if test="${not empty appExtra.voiceCode}">
                                <span>${appExtra.voiceCode}</span>
                            </c:if>

                            <c:if test="${empty appExtra.voiceCode}">
                                <span>未设置</span>
                            </c:if>
                        </div>


                        <div class="create_app_common Yh_cation">
                            <label class="create_app_title_left " >增值业务:</label>
                            <input type="checkbox"  class="start1" name="valueAddeds" value="0" disabled/>
                            <label for="start1">SIP phone间回拨</label>
                            <input type="checkbox"  class="start1 Yh_left50" name="valueAddeds" value="1" disabled/>
                            <label for="start1">SIP phone间直拨</label>
                        </div>

                        <div class="create_app_common">
                            <label  class="create_app_title_left" >限额消费:</label>
							<span>
								<c:if test="${appInfo.limitFlag == '00'}">未限制</c:if>
								<c:if test="${appInfo.limitFlag == '01'}">
                                    <input type="checkbox" id="start11" class="start1" disabled  checked/>
                                    <label for="start1">开启</label>
                                    <span class="Yh_left50 c8">当前额度为${appInfo.quota}元</span>
                                </c:if>
							</span>
                        </div>

                        <div class="create_app_common Yh_cation">
                            <label class="create_app_title_left" >录音开关:</label>
                            <input type="checkbox"  id="recordingType-2" name="recordingTypes" value="0" disabled class="start1 "/>
                            <label for="start1">回拨录音</label>
                            <input type="checkbox"  id="recordingType-0" name="recordingTypes" value="1" disabled class="start1 Yh_left50" />
                            <label for="start1">直拨录音</label>
                            <input type="checkbox"  id="recordingType-1" name="recordingTypes" value="2" disabled class="start1 Yh_left50"/>
                            <label for="start1">回呼录音</label>

                        </div>

                        <div class="create_app_common Yh_cation ">
                            <label class="create_app_title_left">应用回调:</label>
                            <input type="checkbox" id="start1" name="urlCheck" disabled <c:if test="${appInfo.urlStatus == '00'}">checked</c:if> />
                            <label for="start1">启用</label>
                        </div>

                        
                        <div class="create_app_common">

                            <div class="callback_address_left APP_state" style="margin-left:103px;">
                                <c:if test="${appInfo.urlStatus == '00'}">
                                <div class="callback_address">状态回调地址URL:</div>

                                <span class="top5 c8">${appInfo.callbackUrl}</span>
                                </c:if>
                            </div>
                            <div class="callback_address_left Yh_addtop" style="margin-left:103px;">
                                <c:if test="${appInfo.urlStatus == '00'}">
                                <div class="callback_address">计费回调地址URL:</div>
                                <span class="top5 c8">${appExtra.feeUrl}</span>
                                </c:if>
                            </div>
                        </div>


                    </div>
                    <div class="create_Left">
                        <button type="button" class="sm_btn" onclick="history.go(-1);">返&nbsp;回</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


</body>
</html>
