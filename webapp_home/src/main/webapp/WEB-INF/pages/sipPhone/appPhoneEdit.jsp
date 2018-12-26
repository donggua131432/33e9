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
            ajaxDicData('${appConfig.authUrl}',"appType","appType","${appInfo.appType}");

            checkedBox('recordingTypes', '${appExtra.recordingType}');
            checkedBox('valueAddeds1', '${appExtra.valueAdded}');

            var siginForm = $("#userForm").validate({
                rules: {
                    appName: {
                        required: true,
                        appName:true
                    },
                    appType: {
                        required: true
                    },
                    callbackUrl: {
                        appUrl:true
                    },
                    feeUrl: {
                        appUrl:true
                    },
                    quota1: {
//                        required: true,
                        quota:true
                    },
                    quota_: {
//                        required: true,
                        quota:true
                    }
                },
                messages: {
                    appName: {
                        required: '请输入应用名称！'
                    },
                    appType: {
                        required: '请选择应用类型！'
                    },
                    callbackUrl: {
                        appUrl:"请输入合法的URL！"
                    },
                    feeUrl: {
                        appUrl:"请输入合法的URL！"
                    },
                    quota1: {
//                        required:'请输入消费限额！',
                        quota:"请输入大于0的合法消费限额！"
                    },
                    quota_: {
//                        required:'请输入消费限额！',
                        quota:"请输入大于0的合法消费限额！"
                    }
                },
                errorPlacement: function(error, element) {
                    if (element.next().is('span')) {
                        error.insertAfter(element.next());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            $('#openFlag').click(function() {
                $('#limit').show();
            });
            $('#closeFlag').click(function() {
                $('#closelimit').show();
                $('#limit').hide();
            });

            $('#appButton').click(function() {
                var $feeUrl = $('#feeUrl').val();
                var $callbackUrl = $('#callbackUrl').val();

                if ($('#urlCheck').is(':checked') &&  $feeUrl ==""  && $callbackUrl ==""){
                    $("#shadeWin").show();
                    $("#showMsgDialog").show();
                }

                else if(siginForm.form()) {
                    $('#userForm').submit();
                }
            });




            $('#urlCheck').click(function() {
                if($('#urlCheck').is(':checked')) {
                    $('#urlStatus').val("00");
                    $('#callbackUrl').removeAttr('disabled');
                    $('#feeUrl').removeAttr('disabled');

                }
                else{
                    $('#callbackUrl').attr("disabled","true");
                    $('#callbackUrl').removeClass().empty();
                    $('#callbackUrl-error').removeClass().empty();
                    $('#urlStatus').val("01");

                    $('#feeUrl').attr("disabled","true");
                    $('#feeUrl').removeClass().empty();
                    $('#feeUrl-error').removeClass().empty();
                }
            });

            <%--$("input[name='limitCheck']:checked").val('${appInfo.limitFlag}');--%>

            $('#limitCheck').click(function() {
                if($('#limitCheck').is(':checked')) {
                    $('#limit').show();
                    $('#limitFlag').val("01");
                }
                else{
                    $('#limit').hide();
                    $('#limitFlag').val("00");
                }
            });
        });

        //信息提示框关闭
        function closeMsgDialog(){
            $("#shadeWin").hide();
            $("#showMsgDialog").hide();
        }
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

            <!--消息显示-->
            <div class="modal-box  phone_t_code" id="showMsgDialog" style="display: none;">
                <div class="modal_head">
                    <h4>消息提示</h4>
                    <div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                    <div class="app_delete_main" style="height: 50px; padding: 20px;">
                        <p class="app_delete_common"><i class="attention"></i><span id="alertMsg">请至少填写一种回调地址URL！</span></p>
                    </div>
                </div>
                <div class="mdl_foot" style="margin-left:220px;">
                    <button type="button" onclick="closeMsgDialog();" class="true">确&nbsp;&nbsp;定</button>
                </div>
            </div>



            <div class="container15">
                <div class="msg">
                    <h3>创建应用>应用编辑</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <form id="userForm" name = "userForm" action="<c:url value='/phoneApp/update'/>" method="post">
                    <input type="hidden" name="id" value="${appInfo.id}" />
                    <input type="hidden" name="appid" value="${appInfo.appid}" />
                    <input type="hidden" name="quota" value="${appInfo.quota}" />
                    <div class="create_application">
                        <div class="app_edit_main">

                            <div class="create_app_common">
                                <b>*</b>
                                <label for="appName" class="create_app_title_left" >应用名称:</label>
                                <input type="text" id="appName" name="appName" size="50" maxlength="20" value="${appInfo.appName}" class="l_input_style" style="width:600px;"/>
                                <div><i class="create_app_name_note">不超过20个汉字</i></div>
                            </div>

                            <div class="create_app_common">
                                <label class="create_app_title_left APP_state" >应用类型:</label>
                                <select name="appType" id="appType">

                                </select>
                            </div>


                            <div class="create_app_common">
                                <span class="application_ID">APP&nbsp;ID:<span class="sm_account">${appInfo.appid}</span></span>
                            </div>

                            <div class="create_app_common">
                                <label  class="create_app_title_left APP_state" >应用状态:</label>
								<span>
									<c:if test="${appInfo.status == '00'}">正常</c:if>
									<c:if test="${appInfo.status == '01'}">冻结</c:if>
									<c:if test="${appInfo.status == '02'}">已删除</c:if>
								</span>
                            </div>

                            <div class="create_app_common">
                                <label class="create_app_title_left APP_state" >语音编码:</label>
                                 <c:if test="${not empty appExtra.voiceCode}">
                                    <span>${appExtra.voiceCode}</span>
                                 </c:if>

                                <c:if test="${empty appExtra.voiceCode}">
                                    <span>未设置</span>
                                </c:if>
                            </div>
                            <div class="create_app_common Yh_cation">
                                <label class="create_app_title_left APP_state" >增值业务:</label>
                                <input type="checkbox"  class="start1" id="valueAdded-0" name="valueAddeds1" value="0" disabled />
                                <label for="start1">SIP phone间回拨</label>
                                <input type="checkbox"  class="start1 Yh_left50" id="valueAdded-1" name="valueAddeds1" value="1" disabled/>
                                <label for="start1">SIP phone间直拨</label>
                            </div>


                            <div class="create_app_common create_app_callback Yh_cation" style="padding-bottom:25px;">
                                <div style="float:left;">
                                    <label class="create_app_title_left" >消费限额:</label>
                                    <input type="hidden" id="limitFlag" name="limitFlag" value="${appInfo.limitFlag}"/>
                                    <c:if test="${appInfo.limitFlag == '01'}">
                                        <input type="checkbox" id="limitCheck" name="limitCheck" value="01" class="certificate" checked="checked" />
                                        <span class="organ_msg_common">开启</span>
                                    </c:if>
                                    <c:if test="${appInfo.limitFlag == '00'}">
                                        <input type="checkbox" id="limitCheck" name="limitCheck" value="00" class="certificate" />
                                        <span class="organ_msg_common">开启</span>
                                    </c:if>
                                </div>

                                <div id="limit" style="<c:if test="${appInfo.limitFlag eq '00'}">display: none;</c:if> float:left;margin-top:-3px;">
                                    <c:if test="${not empty appInfo.quota }">
                                        <span class="organ_msg_common">当前用户限额消费 ${appInfo.quota} 元，现修改至</span>
                                        <select name="limitStatus" id="limitStatus" >
                                            <option value="00">额度提升</option>
                                            <option value="01">额度下调</option>
                                        </select>
                                        <input type="text" id="quota1" name="quota1" size="50" maxlength="9"  class="l_input_style inputK"/><span>元 </span>
                                    </c:if>

                                    <c:if test="${appInfo.quota == null }">
                                        <span class="organ_msg_common">当前应用消费无限额，现设置为</span>
                                        <select name="limitStatus" id="limitStatus" >
                                            <option value="00">额度提升</option>
                                        </select>
                                        <input type="text" id="quota_" name="quota_" size="50" maxlength="9"  class="l_input_style inputK"/><span>元 </span>
                                    </c:if>

                                </div>

                            </div>



                            <%--<div class="create_app_common create_app_callback" id="closelimit" style="display: none;">--%>
                            <%--</div>--%>

                            <div class="create_app_common create_app_callback Yh_cation">

                                <%--@declare id="start1"--%><label class="create_app_title_left" >录音开关:</label>
                                <input type="checkbox" id="recordingType-2" name="recordingTypes" value="0" class="start1 "/>
                                <label for="start1">回拨录音</label>
                                <input type="checkbox" id="recordingType-0" name="recordingTypes" value="1" class="start1 Yh_left50"/>
                                <label for="start1">直拨录音</label>
                                <input type="checkbox" id="recordingType-1" name="recordingTypes" value="2" class="start1 Yh_left50"/>
                                <label for="start1">回呼录音</label>

                            </div>

                            <div class="create_app_common create_app_callback">
                                <label class="create_app_title_left" >应用回调:</label>
                                <input type="hidden" id="urlStatus" name="urlStatus" value="${appInfo.urlStatus}"/>
                                <c:if test="${appInfo.urlStatus == '00'}">
                                    <input type="checkbox" id="urlCheck" name="urlCheck" checked/>
                                </c:if>
                                <c:if test="${appInfo.urlStatus == '01'}">
                                    <input type="checkbox" id="urlCheck" name="urlCheck"/>
                                </c:if>
                                <label for="urlStatus">启用</label>
                            </div>
                            <div class="create_app_common">
                                <div class="callback_address_left">
                                    <c:if test="${appInfo.urlStatus == '00'}">
                                        <div class="callback_address">回调地址URL:<span class="Yh_Examples">(示例：http://www.33e9ecloud.com/notifyServer)</span></div>
                                        <textarea name="callbackUrl" id="callbackUrl" maxlength="100" style="padding:5px 5px;font-size: 15px;" cols="73" rows="4">${appInfo.callbackUrl}</textarea>
                                        <br>
                                        <div class="callback_address Yh_addtop">计费回调地址URL:<span class="Yh_Examples">(示例：http://www.33e9ecloud.com/notifyServer)</span></div>
                                        <textarea name="feeUrl" id="feeUrl" maxlength="100" style="padding:5px 5px;font-size: 15px;" cols="73" rows="4">${appExtra.feeUrl}</textarea>
                                    </c:if>
                                    <c:if test="${appInfo.urlStatus == '01'}">
                                        <div class="callback_address">回调地址URL:<span class="Yh_Examples">(示例：http://www.33e9ecloud.com/notifyServer)</span></div>
                                        <textarea name="callbackUrl" id="callbackUrl" maxlength="100" style="padding:5px 5px;font-size: 15px;" disabled cols="73" rows="4"></textarea>
                                        <br>
                                        <div class="callback_address Yh_addtop">计费回调地址URL:<span class="Yh_Examples">(示例：http://www.33e9ecloud.com/notifyServer)</span></div>
                                        <textarea name="feeUrl" id="feeUrl" maxlength="100" style="padding:5px 5px;font-size: 15px;" disabled cols="73" rows="4"></textarea>
                                    </c:if>
                                </div>
                            </div>
                            <div class="create_button">
                                <button type="button" name="appButton" id="appButton" class="sm_btn">保存</button>
                                <button type="button" class="cancel_btn" onclick="history.go(-1);">取消</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>
