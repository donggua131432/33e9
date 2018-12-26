<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta name="keywords" content="玖云平台是提供通话、短信、呼叫中心、流量、专线语音、通话录音、录音、回拨、隐私号等通讯能力与服务的开放平台" />
	<meta name="description" content="玖云平台为企业及个人开发者提供各种通讯能力，包括网络通话、呼叫中心/IVR等，开发者通过嵌入云通讯API能够在应用中轻松实现各种通讯功能，包括语音验证码、语音对讲、群组语聊、点击拨号、云呼叫中心等功能;网络通话,网络电话,云呼叫中心,短信接口,pass平台,流量充值,短信发送平台,通讯云,玖云平台,云通讯,云通信,SDK,API,融合通信,政企通讯,云呼叫中心,呼叫中心建设,呼叫中心能力,通讯线路,虚拟运营商,短信服务,流量分发平台,网络直拨,匿名通话,公费电话,企业服务 " />
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="renderer" content="webkit">
	<title>玖云平台-通讯能力与服务的开放平台</title>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/base.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/default.css"/>
	<style type="text/css">
		.current{color:#2d8be3 !important;}
	</style>
	<script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
	<jsp:include page="common/baidu.jsp"/>
</head>

<jsp:include page="common/home2/header.jsp"/>

<!--登录login-->
<div class="register">
	<h2 class="f48 text_center">登录玖云平台</h2>
	<div class="regist_tit loginTop">
		<form id="signinForm" class="form-horizontal" action="${appConfig.loginUrl}" method="post">
			<input type="hidden" name="redirect_url" value="${redirect_url}"/>
			<input type="hidden" id="password" name="password" value=""/>
			<ul class="regist_iput loginLeft">
				<li>
					<i class="loginIco1"></i>
					<input type="text" class="inputBk input_w400 va-m acnun-left"   id="user" name="username" value="${username}" placeholder="请输入您的账号" >
				</li>
				<li>
					<i class="loginIco2"></i>
					<input type="password" class="inputBk input_w400 va-m acnun-left"id="paw" name="pwd" placeholder="请输入您的密码">
				</li>
				<li class="loginPass"><span class="c9"><input  id="R_account" type="checkbox" class="remat">记住账号</span><a href="<c:url value='/retrieve/index'/>" class="c9"><em class="passIco va-m"></em>忘记密码</a></li>
				<li class="errHeight">
					<div id="errmsg">
						<c:if test="${not empty msg}">
							<label class="error" style="float: none">${msg}</label>
						</c:if>
					</div>
				</li>
				<li><a href="javascript:rsalogin();" class="blueBtn Btn_w400 send">登录</a> </li>
			</ul>
		</form>
	</div>
</div>
<!--登录login end-->
<jsp:include page="common/home2/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/bootstrap.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/login.js"></script>

<script src="${appConfig.resourcesRoot}/master/js/validation/jquery.validate.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/messages_zh.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/validate-extend.js"></script>

<script src="${appConfig.resourcesRoot}/master/js/encrypt/RSA.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/encrypt/BigInt.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/encrypt/Barrett.js"></script>

<script src="${appConfig.resourcesRoot}/master/js/cookie.js"></script>

<script type="text/javascript">

	var siginForm;
	var jyptun = "jyptun";

	$(function(){

		var un = getCookie(jyptun);
		if (un && !$("#user").val()) {
			$("#user").val(un);
		}

		siginForm = $("#signinForm").validate({
			rules: {
				username: {
					required:true,
					username:true
				},
				pwd: {
					required:true,
					password:false
				}
			},
			messages: {
				username: {
					required:"请输入账号",
					username:"格式不正确"
				},
				pwd: {
					required: "请输入密码"
				}
			},
			errorPlacement: function(error, element) {
				$("#errmsg").html("");
				error.css({float:"none"}).appendTo($("#errmsg"));
			}
		});

		//回车提交事件
		$("body").keydown(function(event) {
			if (event.keyCode == "13") {//keyCode=13是回车键
				rsalogin();
			}
		});
	});

	//rsa begin
	var key ;
	function rsalogin() {
		if(siginForm.element("#user") && siginForm.element("#paw")){
			bodyRSA();
			var password = encryptedString(key, $("#paw").val());
			//console.info(password);
			//alert(password);
			$("#password").val(password);

			if ($("#R_account").is(':checked')){
				setCookie(jyptun, $("#user").val(), "d30");
			} else {
				delCookie(jyptun);
			}

			$("#signinForm").submit();
		}
	}

	function bodyRSA() {
		setMaxDigits(130);
		key = new RSAKeyPair("${appConfig.rsaExponent}", "", "${appConfig.rsaModulus}");
	}
	//rsa end

</script>
</body>
</html>