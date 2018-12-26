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
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<script type="text/javascript">
		$(function(){

			//加载联系方式1
			//ajaxDicData('${appConfig.authUrl}',"contactway","attenType1", '${user.userExternInfo.attenType1}');
			//加载联系方式2
			//ajaxDicData('${appConfig.authUrl}',"contactway","attenType2", '${user.userExternInfo.attenType2}');
			//加载联系方式3
			//ajaxDicData('${appConfig.authUrl}',"contactway","attenType3", '${user.userExternInfo.attenType3}');
			var siginForm = $("#userForm").validate({
				rules: {
					qq: {
						qq:true
					},
					attenWay1: {
						mobile:true
					},
					attenWay2: {
						mobile:true
					},
					attenWay3: {
						mobile:true
					}
				},
				messages: {
					qq: {
						qq:"请输入正确的qq号！"
					}
				}
			});


			$('#userButton').click(function() {
				if(siginForm.form()) {
					$('#userForm').submit();
				}
			});
			$('#tooltip_btn_email').click(function() {
				$('#emailError').hide();
				$('#shadeWin').show();
				$('#modal_box_email').show();
			});
			$('#tooltip_btn_mobile').click(function() {
				$("#mobileError").hide();
				$("#vcodeError").hide();
				$('#shadeWin').show();
				$('#modal_box_mobile').show();
			});


			function checkEmail(){
				var oldemail  = '${user.email}'
				var emailV = $("#email").val();
				if(emailV){
					if(emailV==oldemail){
						$("#emailError").html("邮箱与原邮箱一致！");
						$("#emailError").show();
						return false;
					}
					var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
					if(!reg.test(emailV)){
						$("#emailError").html("请输入有效的邮箱地址！");
						$("#emailError").show();
						return false;
					}
				}else{
					$("#emailError").html("请输入电子邮件!");
					$("#emailError").show();
					return false;
				}
				return true;
			}
			$('#upEmailB').click(function() {
				if(!checkEmail()){
					return;
				}
				$("#emailError").hide();
				var email = $('#email').val();
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/sendEmail'/>",
					data:{'email':email},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							$('#modal_box_email' ).hide();
							$('#modal_box_email_suc').show();
						}else if(data.code=='01') {
							$("#emailError").show();
							$("#emailError").html("该邮箱已被注册!");
						} else {
							$("#emailError").show();
							$("#emailError").html("邮件发送失败!");
						}
					},
					error: function (data) {
						$("#emailError").show();
						$("#emailError").html("邮件发送失败!");
					}
				});
			});

			function checkMobile(){
				$("#mobileError").html('');
				var oldemobile  = '${user.mobile}';
				var mobileV = $("#mobile").val();
				if(mobileV){
					if(mobileV==oldemobile){
						$("#mobileError").html("手机号码与原手机号码一致！");
						$("#mobileError").show();
						return false;
					}
					var reg =  /^1\d{10}$/;
					if(!reg.test(mobileV)){
						$("#mobileError").html('请输入正确的手机号！');
						$("#mobileError").show();
						return false;
					}
				}else{
					$("#mobileError").html("请输入手机号码!");
					$("#mobileError").show();
					return false;
				}
				return true;
			}
			$('#upMobleB').click(function() {
				if(!checkMobile()){
					return;
				};
				$('#vcodeError').hide();
				$("#mobileError").hide();
				var mobile = $('#mobile').val();
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/sendMobileCode'/>",
					data:{'mobile':mobile},//要发送的数据
					success: function (data) {
						if(data.status=='00') {
							wait = 120;
							get_code_time();
						}else if(data.status=='01'){
							wait = data.second;
							get_code_time();
						}else if(data.status=='02'){
							$('#mobileError').show();
							$('#mobileError').html('该手机号已被占用!');
						}
						else{
							$('#mobileError').show();
							$('#mobileError').html('获取短信验证码失败!');
						}
					},
					error: function (data) {
						$('#mobileErr').show();
						$('#mobileErr').html('获取短信验证码失败，系统异常!');
					}
				});
			});
			var wait =  120;
			get_code_time = function () {
				if (wait == 0) {
					$('#upMobleB').show()
					$('#mobileSuc').hide()
					wait =120;
				} else {
					$('#upMobleB').hide()
					$('#mobileSuc').html('验证码已发送&nbsp;'+wait+'s');
					$('#mobileSuc').show();
					wait--;
					setTimeout(function() {
						get_code_time();
					}, 1000);
				}
			};
			$('#saveMobileB').click(function() {
				$('#vcodeError').hide();
				$('#mobileError').hide();
				var mobile = $('#mobile').val();
				var vcode = $('#vcode').val();
				if(!checkMobile()){
					return;
				};
				if(!vcode){
					$('#vcodeError').html("请输入手机验证码!");
					$('#vcodeError').show();
					return "";
				}
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/upMoble'/>",
					data:{'mobile':mobile,'vcode':vcode},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							$('#modal_box_mobile' ).hide();
							$('#modal_box_mobile_suc').show();
							$('#mobieShow').html(mobile);
						}else if(data.code=='01'){
							$('#vcodeError').show();
							$('#vcodeError').html('验证码不正确!');
						}else if(data.code=='02'){
							$('#vcodeError').show();
							$('#vcodeError').html('验证码不正确!');
						}else{
							$('#mobileError').show();
							$('#mobileError').html('手机号修改失败!');
						}
					},
					error: function (data) {
						$('#mobileError').show();
						$('#mobileErr').html('手机号修改失败，系统异常!');
					}
				});
			});
		});

	function closeDiv(divid){
		$('#' + divid ).hide();
		$('#shadeWin').hide();
		$('#vcodeError').hide();
		$('#emailError').hide();
		$('#mobileError').hide();
		window.location.reload();
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
		<script type="text/javascript">showSubMenu('zh','acc_manage','accInfo');</script>
		<div class="main3">
			<div class="container1">
				<div class="msg">
					<h3>基础信息</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="base_main box">
					<p class="common_msg"><i class="common_icon base_txt"></i>基础信息</p>
					<form id="userForm" name = "userForm" action="<c:url value='/accMgr/save'/>" method="post">
						<div>
							<span class="common">注册邮箱：</span><span class="common_main fixed">${user.email}</span>
							<button type="button" class="tooltip_btn" id="tooltip_btn_email">更换邮箱</button>
							<div class="modal-box  phone_t_code" style="display: none;" id="modal_box_email">
								<div class="modal_head">
									<h4>更换邮箱</h4>
									<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('modal_box_email')"></a></div>
								</div>
								<div class="modal-common_body mdl_pt_body">
									<p class="center">新邮箱认证通过后才可启用</p>
									<div class="mdl_center">
										<div class="Error_box errorLeft">
											<label class="error" id="emailError" style="display: none">请输入有效的电子邮件!</label>
										</div>

										<%--<label class="error Error_left" id="emailError" style="display:none">请输入有效的电子邮件!</label><br/>--%>
										<label for="email" class="la_color">新邮箱</label>
										<input type="text" id="email" class="modal_input_style" name="email" size="50" style="width:280px;" maxlength="50"/><br/>
										<%--<p class="modal_left yzm_left">请前往您的邮箱获取验证链接<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;并点击链接进行验证</p>--%>
										<p class="modal_left yzm_left">请前往您的邮箱获取验证链接，并点击链接进行<br/>验证</p>
									</div>
								</div>
								<div class="mdl_foot f_top">
									<button type="button" class="true" name="upEmailB" id="upEmailB" >确认发送</button>
									<button type="button" class="false" onclick="closeDiv('modal_box_email');">取&nbsp;&nbsp;消</button>
								</div>
							</div>
							<!--更换邮箱成功-->
							<div class="modal-box  phone_t_code" style="display: none;" id="modal_box_email_suc">
								<div class="modal_head">
									<h4>提示</h4>
									<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('modal_box_email_suc')"></a></div>
								</div>
								<div class="modal-common_body mdl_pt_body">
									<div class="test_success test_success_left">
										<i class="lg_common_icon success_txt"></i>
										<span>确认邮件已发送成功!</span>
									</div>
								</div>
							</div>

						</div>

						<div>
							<span class="common">注册手机：</span><span class="common_main fixed" id="mobieShow">${user.mobile}</span>
							<button class=" tooltip_btn change_phone_btn" type="button" id="tooltip_btn_mobile">更换手机号</button>
							<div class="modal-box  phone_t_code" style="display: none;" id="modal_box_mobile">
								<div class="modal_head">
									<h4>更换手机号码</h4>
									<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('modal_box_mobile')"></a></div>
								</div>
								<div class="modal-common_body mdl_pt_body" >
									<p class="PhoneCenter">新的手机号码验证通过后才可启用</p>
									<div class="Error_box">
										<label class="error" id="mobileError" style="display: none">请输入正确的手机号码</label>
									</div>
									<%--<label class="error changePhoneLeft" id="mobileError" style="display: none">请输入正确的电话号码！</label><br/>--%>
									<label for="mobile" class="ph_left la_color">新手机号码</label>
									<input type="text" id="mobile" name="mobile" class="modal_input_style" maxlength="11" size="22"/>
									<div class="phone_btn">
										<span><button type="button" name="upMobleB" id="upMobleB"  class="get_test_code get_t_b">获取短信验证码</button></span>
										<span><button type="button" class="send_test_code get_t_b"  id="mobileSuc" style="display: none"></button></span>
									</div>
									<label for="vcode" class="ph_left la_color">短信验证码</label>
									<input type="text" id="vcode" name="vcode" class="modal_input_style" maxlength="6" size="22"/><br/>
									<%--<label class="error changePhoneLeft" id="vcodeError" style="display: none">验证码输入有误，请重新输入</label>--%>
									<div class="Error_box">
										<label class="error" id="vcodeError" style="display: none">验证码输入有误，请重新输入</label>
									</div>
								</div>
								<div class="mdl_foot f_top">
									<button type="button" class="true" type="button" id="saveMobileB">确&nbsp;&nbsp;认</button>
									<button type="button" class="false" type="button" onclick="closeDiv('modal_box_mobile');">取&nbsp;&nbsp;消</button>
								</div>
							</div>

							<!--更换电话号码成功-->
							<div class="modal-box  phone_t_code" STYLE="display: none" id="modal_box_mobile_suc">
								<div class="modal_head">
									<h4>手机验证</h4>
									<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('modal_box_mobile_suc')"></a></div>
								</div>
								<div class="modal-common_body mdl_pt_body">
									<div class="test_success test_success_left">
										<i class="lg_common_icon success_txt"></i>
										<span>更改手机号码成功</span>
									</div>
								</div>
							</div>
						</div>


						<div><label for="qq" class="common">QQ：</label><input type="text" id="qq" name="qq"  class="l_input_style" size="25" maxlength="12" value="${user.userExternInfo.qq}"/></div>

						<div>
							<label for="address" class="common" style="margin-right:14px;">联系地址：</label>
							<textarea name="address" id="address" maxlength="30" cols="39" rows="4"><c:out value='${user.userExternInfo.address}'/></textarea>
						</div>
						<div>
							<p class="common_msg"><i class="common_icon other_txt"></i>其他信息<span class="linkman">（如遇紧急情况无法联系到初始注册绑定号码，我们会与您设定的应急联系人联系）</span></p>
							<div>
								<span class="common">其他人联系电话：</span>

								<input id="attenWay1" name="attenWay1"  class="l_input_style" type="text" maxlength="11" size="26"   value="${user.userExternInfo.attenWay1}"/>
							</div>
							<div>
								<span class="common">其他人联系电话：</span>

								<input id="attenWay2" name="attenWay2"  class="l_input_style" type="text" maxlength="11" size="26" value="${user.userExternInfo.attenWay2}"/>
							</div>
							<div>
								<span class="common">其他人联系电话：</span>

								<input id="attenWay3" name="attenWay3"  class="l_input_style" type="text" maxlength="11" size="26" value="${user.userExternInfo.attenWay3}"/>
							</div>
						</div>
						<div><button type="button" name="userButton" id="userButton" class="md_btn base_btn_left" >保存</button></div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>


</body>
</html>
