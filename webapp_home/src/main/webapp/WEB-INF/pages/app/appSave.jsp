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
		ajaxDicData('${appConfig.authUrl}',"appType","appType",'');
		$(function(){
			var siginForm = $("#userForm").validate({
				rules: {
					appName: {
						required: true,
						appName:true
					},
					callbackUrl: {
						required: true,
						appUrl:true
					}
					,
					quota: {
						required: true,
						quota:true
					}

				},
				messages: {
					appName: {
						required: '请输入应用名称！'
					},callbackUrl: {
						required:'请输入URL！',
						appUrl:"请输入合法的URL！"
					},
					quota: {
						required:'请输入消费限额！',
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
				if(siginForm.form()) {
					$('#userForm').submit();
				}
			});
			$('#urlCheck').click(function() {
				if($('#urlCheck').is(':checked')) {
					$('#urlStatus').val("00");
					$('#callbackUrl').removeAttr('disabled');

				}
				else{
					$('#callbackUrl').attr("disabled","true");
					$('#callbackUrl').removeClass().empty();
					$('#callbackUrl-error').removeClass().empty();
					$('#urlStatus').val("01");
				}
			});
		});
		function closeDiv(divid){
			$('#' + divid ).hide();
			$('#shadeWin').hide();
			$('#modal_box_limit').hide();
		};
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
		<script type="text/javascript">showSubMenu('kf','appMgr','appSave');</script>
		<!-- 左侧菜单 end -->
		<div class="main3">
			<div class="container15">
				<div class="msg">
					<h3>创建应用</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<form id="userForm" name = "userForm" action="<c:url value='/appMgr/save'/>" method="post">
				<div class="create_application">
					<div class="app_edit_main">
						<div class="create_app_common">
							<b>*</b>
							<label for="appName" class="create_app_title_left" >应用名称:</label>
							<input type="text" id="appName" name="appName" size="50" maxlength="20" class="l_input_style" style="width:600px;"/>
							<div><i class="create_app_name_note">不超过20个汉字</i></div>
						</div>
						<div class="create_app_common">
							<label class="create_app_title_left APP_state" >应用类型:</label>
							<select name="appType" id="appType">

							</select>
						</div>
						<div class="create_app_common create_app_callback">
							<label class="create_app_title_left" >限额消费:</label>
							<input type="radio" id="openFlag" name="limitFlag" value="01" class="certificate"/><span class="organ_msg_common">开启</span>
							<input type="radio" id="closeFlag" name="limitFlag" value="00" class="certificate" checked="checked" /><span class="organ_msg_common">关闭</span>
						</div>

						<div class=" create_app_name_note createTop" id="limit" style="display: none;">
						<span class="organ_msg_common">当前用户无限额，现设置限额</span><input type="text" id="quota" name="quota" size="50" maxlength="9" class="l_input_style inputK"/> <span> 元 </span>
						</div>
						<div class="create_app_common create_app_callback" id="closelimit" style="display: none;">
						</div>

						<div class="create_app_common create_app_callback">
							<label class="create_app_title_left" >应用回调:</label>
							<input type="hidden" id="urlStatus" name="urlStatus" value="00"/>
							<input type="checkbox" id="urlCheck" name="urlCheck"  checked/>
							<label for="urlStatus">启用</label>
						</div>
						<div class="create_app_common">
							<div class="callback_address_left">
								<div class="callback_address">回调地址URL:</div>
								<textarea name="callbackUrl" id="callbackUrl"  maxlength="100" style="padding:5px 5px;font-size: 15px;" cols="73" rows="4"></textarea>
								<%--<input type="text" size="50" id="callbackUrl" name="callbackUrl" maxlength="100"/>--%>
								<p class="URL_sample">
									<i>示例：</i><br/>
									<i>http://www.33e9ecloud.com/notifyServer</i>
								</p>
							</div>
						</div>
						<div class="create_Left">
							<button type="button" name="appButton" id="appButton" class="sm_btn" >保存</button>
							<%--<button type="button" class="cancel_btn">取消</button>--%>
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
