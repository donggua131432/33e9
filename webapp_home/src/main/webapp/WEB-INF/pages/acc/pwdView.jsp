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
	<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
	<script src="${appConfig.resourcesRoot}/js/encrypt/RSA.js"></script>
	<script src="${appConfig.resourcesRoot}/js/encrypt/BigInt.js"></script>
	<script src="${appConfig.resourcesRoot}/js/encrypt/Barrett.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<script type="text/javascript">
		var siginForm;
		$(function(){
			siginForm = $("#pwdForm").validate({
				rules: {
					oldpassword: {
						required:true,
						password:true
					},
					newpassword: {
						required:true,
						password:true
					},

					oldpwd: {
							required:true,
							password:true
						},
					repassword: {
						required:true,
						password:true,
						equalTo: "#newpassword"
					}
				},
				messages: {
					oldpassword: {
						required:"请输入当前密码"
					},
					newpassword: {
						required:"请输入新密码"

					},
					oldpwd: {
						required:"原来的密码错误"

					},
					repassword: {
						required:"请输入确认密码",
						equalTo: "两次输入密码不一致"
					}
				}
			});
		});

		//rsa begin
		var key ;

		function bodyRSA()	{
			setMaxDigits(130);
			key = new RSAKeyPair("${appConfig.rsaExponent}", "", "${appConfig.rsaModulus}");
		}
		//rsa end
		function submitForm(){

		}
		function updpwd(){
			if(siginForm.form()) {
				bodyRSA();
				var oldpassword = encryptedString(key, $("#oldpassword").val());
				$("#oldpwd").val(oldpassword);
				var newpassword = encryptedString(key, $("#newpassword").val());
				$("#newpwd").val(newpassword);

			$.ajax({
				type: "post",//使用post方法访问后台
				dataType: "json",//返回json格式的数据
				url: "<c:url value='/accMgr/upPwd'/>",
				data:{'oldpwd':oldpassword,'newpwd':newpassword},//要发送的数据
				success: function (data) {
					if(data.code=='00') {
						$('#shadeWin').show();
						$('#update_password_suc').show();
					}
				     else if(data.code=='01') {
						$("#oldpassword-error").show();
						$("#oldpassword-error").html("原密码输入有误");
					} else {
						$("#oldpassword-error").show();
						$("#oldpassword-error").html("修改密码失败!");
					}
				},
				error: function (data) {
					$("#oldpassword-error").show();
					$("#oldpassword-error").html("修改失败！");
				}
			});
			}

		}
		function closeDiv(divid){
			$('#' + divid ).hide();
			$('#shadeWin').hide();
			window.location.href="<c:url value='/accMgr/index'/>";

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
		<jsp:include page="../common/controlleft.jsp"/>
		<script type="text/javascript">showSubMenu('zh','acc_manage','accPwd');</script>
		<div class="main3">
			<div class="container3">
				<div class="msg">
					<h3>密码设置</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="reset_paw ">
					<form name="pwdForm" id="pwdForm">
						<input type="hidden" id="oldpwd" name="oldpwd">
						<input type="hidden" id="newpwd" name="newpwd">
						<div class="paw_bottom">
							<label >原&nbsp;密&nbsp;码：</label>
							<input type="password" id="oldpassword" maxlength="16" name="oldpassword" size="33" class="old_paw l_input_style" placeholder="请输入现在使用的密码"/><label  id="oldpassword-error" for="oldpassword" style="display: none" class="error"></label>
						</div>

						<div class="paw_bottom">
							<label >新&nbsp;密&nbsp;码：</label>
							<input type="password" id="newpassword" maxlength="16" name="newpassword" size="33" class="new_paw l_input_style"  placeholder="如放弃修改请为空"/>
						</div>

						<div class="paw_bottom">
							<label>再次输入密码：</label>
							<input type="password" id="repassword" maxlength="16" name="repassword" size="33" class="re_enter_paw l_input_style" placeholder="请再次输入新密码"/>
						</div>
						<div><button type="button" id="pwdButton" name="pwdButton" onclick="updpwd();" class="md_btn paw_btn">确认</button></div>
					</form>
				</div>
				<!--密码设置成功-->
				<div class="modal-box  phone_t_code" style="display:none;" id="update_password_suc">
					<div class="modal_head">
						<h4>密码设置</h4>
						<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('update_password_suc')"></a></div>
					</div>
					<div class="modal-common_body mdl_pt_body set_paw_left">
						<div class="test_success test_success_left">
							<i class="lg_common_icon success_txt"></i>
							<span>密码设置成功</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>


</body>
</html>
