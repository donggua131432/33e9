<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!--<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">-->
	<title>玖云平台-登录</title>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/login.css">
	<!--[if lt IE 9]>
	<script src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
	<![endif]-->
	<script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
	<jsp:include page="common/baidu.jsp"/>
</head>
<body style="overflow-x:hidden">

<jsp:include page="common/header.jsp"/>


<div class="website_container login_page">
	<div class="main_box">
		<div class="login_row1">
			<img src="${appConfig.resourcesRoot}/master/img/logo_zhuce.png" alt="登录logo"/>
			<p>登&nbsp;录&nbsp;玖&nbsp;云&nbsp;平&nbsp;台</p>
		</div>
		<div class="login_row2">
			<form id="signinForm" class="form-horizontal" action="${appConfig.loginUrl}" method="post">
				<input type="hidden" name="redirect_url" value="${redirect_url}"/>
				<input type="hidden" id="password" name="password" value=""/>

				<div class="login_account">
					<label for="user">账号</label>
					<input type="text" id="user" name="username" value="${username}" placeholder="请输入您的账号"/>
				</div>
				<div class="login_password">
					<label for="paw">密码</label>
					<input type="password" id="paw" name="pwd" placeholder="请输入您的密码"/>
				</div>
				<div class="F_password">
					<input type="checkbox" id="R_account" class="rr_account"/><label for="R_account">记住账号</label>
                    <span>
                        <i class="mark_txt"></i>
                        <a href="<c:url value='/retrieve/index'/>" target="_blank">忘记密码？</a>
                    </span>
				</div>

				<div id="errmsg" class="login_tip">
					<c:if test="${not empty msg}">
						<label class="error" style="float: none">${msg}</label>
					</c:if>
				</div>

				<div class="btn_center">
					<button onclick="rsalogin()" type="button" class="login_btn loginO_btn" style="cursor: pointer;">登&nbsp;&nbsp;录</button>
				</div>
			</form>
		</div>
	</div>
</div>

<jsp:include page="common/footer.jsp"/>

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

	function bodyRSA()	{
		setMaxDigits(130);
		key = new RSAKeyPair("${appConfig.rsaExponent}", "", "${appConfig.rsaModulus}");
	}
	//rsa end

</script>
</body>
</html>