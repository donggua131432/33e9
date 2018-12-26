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
		<script type="text/javascript">showSubMenu('zh','acc_manage','accInfo');</script>
		<div class="main3">
			<div class="container1" >
				<div class="msg">
					<h3>基础信息</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="base_main box">
					<p class="common_msg">
						<i class="common_icon base_txt"></i>基础信息
						<a href="<c:url value='/accMgr/update'/>" class="alert_base_msg purple"><i class="common_icon alter_txt"></i>修改信息</a>
					</p>
					<form action="">
						<div>
							<span class="common">注册邮箱：</span><span class="common_main">${user.email}</span>
						</div>

						<div>
							<span class="common">注册手机：</span><span class="common_main">${user.mobile}</span>
						</div>

						<div><label class="common">QQ：</label><span class="common_main qq_left">${user.userExternInfo.qq}</span></div>

						<div>
							<label class="common">联系地址：</label>
							<span class="common_main"><c:out value="${user.userExternInfo.address} "/></span>
						</div>

						<div>
							<p class="common_msg"><i class="common_icon other_txt"></i>其他信息<span class="linkman">（如遇紧急情况无法联系到初始注册绑定号码，我们会与您设定的应急联系人联系）</span></p>
							<div>
								<span class="common">其他人联系电话：</span>
								<span class="common_main c_right">${user.userExternInfo.attenWay1}</span>
							</div>
							<div>
								<span class="common">其他人联系电话：</span>
								<span class="common_main c_right">${user.userExternInfo.attenWay2}</span>
							</div>
							<div>
								<span class="common">其他人联系电话：</span>
								<span class="common_main c_right">${user.userExternInfo.attenWay3}</span>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>


</body>
</html>
