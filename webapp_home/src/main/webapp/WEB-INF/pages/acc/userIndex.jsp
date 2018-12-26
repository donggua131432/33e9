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
	<script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
	<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
	<script src="${appConfig.resourcesRoot}/js/encrypt/RSA.js"></script>
	<script src="${appConfig.resourcesRoot}/js/encrypt/BigInt.js"></script>
	<script src="${appConfig.resourcesRoot}/js/encrypt/Barrett.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<script type="text/javascript">
		$(function(){

			$('#checkMobileAuth').click(function() {
				$("#mobileError").hide();
				$("#vcodeError").hide();
				$('#shadeWin').show();
				$('#modal_box_mobile').show();
			});
			$('#updateMobileAuth').click(function() {
				$('#shadeWin').show();
				$('#update_box_mobile').show();
			});

			$('#resetButton').click(function() {
				$('#resetForm').submit();
			});


			function checkMobile(){
				var mobileV = $("#mobile").val();
				if(mobileV){
					var reg =  /^1\d{10}$/;
					if(!reg.test(mobileV)){
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
//				if(!checkMobile()){
//					return;
//				};
				$('#vcodeError').hide();
//				$("#mobileError").hide();
//				var mobile = $('#mobile').val();
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/checkMobile'/>",
//					data:{'mobile':mobile},//要发送的数据
					success: function (data) {
						if(data.status=='00') {
							wait = 120;
							get_code_time();
						}else if(data.status=='01'){
							wait = data.second;
							get_code_time();
						}else if(data.status=='02'){
							$('#mobileError').html('请输入您绑定的手机号!');
							$('#mobileError').show();
						}
						else{
							$('#mobileError').html('获取短信验证码失败!');
							$('#mobileError').show();
						}
					},
					error: function (data) {
						$('#mobileError').html('获取短信验证码失败，系统异常!');
						$('#mobileError').show();
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
				var vcode = $('#vcode').val();
				if(!vcode){
					$('#vcodeError').html("请输入手机验证码!");
					$('#vcodeError').show();
					return "";
				}
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/upToken'/>",
					data:{'vcode':vcode},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							$('#authToken').html(data.token);
							$('#modal_box_mobile' ).hide();
							$('#shadeWin').hide();
							$('#checkMobileAuth').hide();
							window.location.reload();
						}else if(data.code=='01'){
							$('#vcodeError').show();
							$('#vcodeError').html('验证码不正确！');
						}else if(data.code=='02'){
							$('#vcodeError').show();
							$('#vcodeError').html('验证码不正确！');
						}else{
							$('#vcodeError').show();
							$('#vcodeError').html('查看失败！');
						}
					},
					error: function (data) {
						$('#vcodeError').show();
						$('#vcodeError').html('查看失败，系统异常！');
					}
				});
			});


			//账户主页--余额短信提醒


			$('#open_msg').click(function(){
				$('#tip_content').show();
			});
			$('#close_msg').click(function(){
				$('#tip_content').hide();
				$('#amend_money_before').show();
				$('#amend_money_after').hide();
				$('#amend_tel_before').show();
				$('#amend_tel_after').hide()
			});

			$('#amend_balance_btn').click(function(){
				$('#amend_money_before').hide();
				var value=$(".money_num").text();
				$(".money_input_val").val(value);
				$('#amend_money_after').show();

			});
			$('#amend_tel_btn').click(function(){
				$('#amend_tel_before').hide();
				var value=$(".tel_input").text();
				$(".tel_input_val").val(value);
				$('#amend_tel_after').show()
			});


			function checkLimitMobile(){
				var mobileV = $("#smsMobile").val();
				if(mobileV){
					var reg =  /^1\d{10}$/;
					if(!reg.test(mobileV)){
						$("#mobileMsgError").show();
						return false;
					}
				}else{
					$("#mobileMsgError").html("请输入手机号码!");
					$("#mobileMsgError").show();
					return false;
				}
				return true;
			};


			$('#gettestcode').click(function() {
				if(!checkLimitMobile()){
					return;
				};
				$('#vcodeMsgError').hide();
				$("#mobileMsgError").hide();
				var mobile = $('#smsMobile').val();
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/sendMobileCodeSms'/>",
					data:{'mobile':mobile},//要发送的数据
					success: function (data) {
						if(data.status=='00') {
							wait = 120;
							get_code_time1();
						}else if(data.status=='01'){
							wait = data.second;
							get_code_time1();
						}
						else{
							$('#mobileMsgError').show();
							$('#mobileMsgError').html('获取短信验证码失败!');
						}
					},
					error: function (data) {
						$('#mobileMsgError').show();
						$('#mobileMsgError').html('获取短信验证码失败，系统异常!');
					}
				});
			});
			var wait =  120;
			get_code_time1 = function () {
				if (wait == 0) {
					$('#gettestcode').show()
					$('#mobileSucAfter').hide()
					wait =120;
				} else {
					$('#gettestcode').hide()
					$('#mobileSucAfter').html('已发送&nbsp;'+wait+'s');
					$('#mobileSucAfter').show();
					wait--;
					setTimeout(function() {
						get_code_time1();
					}, 1000);
				}
			};


			$('#telSubmit').click(function() {
				$('#vcodeMsgError').hide();
				$('#mobileMsgError').hide();
				var mobile = $('#smsMobile').val();
				var vcode = $('#num').val();
				if(!checkLimitMobile()){
					return;
				};
				if(!vcode){
					$('#vcodeMsgError').html("请输入手机验证码!");
					$('#vcodeMsgError').show();
					return "";
				}
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/upSmsMoble'/>",
					data:{'mobile':mobile,'vcode':vcode},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							$('#shadeWin').show();
							$('#modal_box_smsMobile_suc').show();
						}else if(data.code=='01'){
							$('#vcodeMsgError').show();
							$('#vcodeMsgError').html('验证码不正确!');
						}else if(data.code=='02'){
							$('#vcodeMsgError').show();
							$('#vcodeMsgError').html('验证码不正确!');
						}else{
							$('#mobileMsgError').show();
							$('#mobileMsgError').html('手机号修改失败!');
						}
					},
					error: function (data) {
						$('#mobileMsgError').show();
						$('#mobileMsgError').html('手机号修改失败，系统异常!');
					}
				});
			});


			function checkLimitFee(){
				var remindFee = $("#remindFee").val();
				if(remindFee){
					var reg = /^\+?[0-9]\d{0,5}$/;
					if(!reg.test(remindFee)){
						$("#remindFeeError").show();
						return false;
					}
				}else{
					$("#remindFeeError").html("请输入整数!");
					$("#remindFeeError").show();
					return false;
				}
				return true;
			};
			$('#feeSubmit').click(function() {
				$('#remindFeeError').hide();
				var remindFee = $('#remindFee').val();
				if(!checkLimitFee()){
					return;
				};
				if(!remindFee){
					$('#remindFeeError').html("请输入余额提醒的金额!");
					$('#remindFeeError').show();
					return "";
				}
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/upremindFee'/>",
					data:{'remindFee':remindFee},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							$('#shadeWin').show();
							$('#modal_box_smsFee_suc').show();
						}else{
							$('#remindFeeError').show();
							$('#remindFeeError').html('修改失败!');
						}
					},
					error: function (data) {
						$('#remindFeeError').show();
						$('#remindFeeError').html('修改失败，系统异常!');
					}
				});
			});

			$("input[name='limitFlag']:checked").click();


			$('#close_msg').click(function() {
				var remindFee = $('#remindFee').val();
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/upFlag'/>",
					data:{'remindFee':remindFee},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							window.location.reload();
						}else{
							$('#remindFeeError').show();
							$('#remindFeeError').html('修改失败!');
						}
					},
					error: function (data) {
						$('#remindFeeError').show();
						$('#remindFeeError').html('修改失败，系统异常!');
					}
				});
			});

			$('#open_msg').click(function() {
				var remindFee = $('#remindFee').val();
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/accMgr/openFlag'/>",
					data:{'remindFee':remindFee},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							window.location.reload();
						}else{
							$('#remindFeeError').show();
							$('#remindFeeError').html('修改失败!');
						}
					},
					error: function (data) {
						$('#remindFeeError').show();
						$('#remindFeeError').html('修改失败，系统异常!');
					}
				});
			});

		});



		function closeDiv(divid){
			$('#' + divid ).hide();
			$('#shadeWin').hide();
			$('#vcodeError').hide();
			$('#mobileError').hide();

			$('#vcodeMsgError').hide();
			$('#mobileMsgError').hide();
			window.location.reload();
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
		<jsp:include page="../common/controlleft.jsp"/>
		<script type="text/javascript">showSubMenu('zh','acc_manage','accIndex');</script>
		<div class="main3">
			<div class="container4">
				<div class="coupons">
					<img src="${appConfig.resourcesRoot}/img/recharge_banner%20(2).png" alt="广告"/>
				</div>
				<div class="coupons_main">
					<div class="msg">
						<h3>账户信息</h3>
						<jsp:include page="../common/message.jsp"/>
						<c:if test="${ (empty sFlag)||sFlag=='0'}">
							<span class="zh_test">您还未进行账户认证,请先认证 <a href="<c:url value='/authen/addper'/>">马&nbsp;上&nbsp;认&nbsp;证&nbsp;&gt;&nbsp;&gt;</a></span>
						</c:if>
						<c:if test="${sFlag=='1'}">
							<span class="zh_test">账户状态：认证中</span>
						</c:if>
						<c:if test="${sFlag=='2'}">
							<span class="zh_test">账户状态：已认证</span>
						</c:if>
						<c:if test="${sFlag=='3'}">
							<span class="zh_test">账户状态：认证未通过</span>
						</c:if>
					</div>

					<div class="Ac_information">
						<div class="Ac_balance left clear_f">
							<div class="common_size">
								<i class="common_icon pig"></i>账户余额
							</div>
							<div class="ac_je ac_zhye"><span>${account.fee}</span>元</div>
						</div>

						<div class="Ac_balance left clear_f">
							<div class="common_size">
								<i class="common_icon y_txt"></i>昨日消费
							</div>
							<div class="ac_je ac_yc"><span>${yesterdayFee}</span>元</div>
						</div>

						<div class="Ac_balance left">
							<div class="common_size">
								<i class="common_icon m_txt"></i>本月消费
							</div>
							<div class="ac_je ac_tm"><span>${currentMonthFee}</span>元</div>
						</div>
					</div>


					<!--余额短信提醒-->

					<div class="msg_alert_box clear">
						<form action="">
							<span class="msg_alert"><i class="Balance_ico"></i>余额短信提醒</span>
                        <span>
							<c:if test="${account.limitFlag == '01'}">
								<input type="radio" name="limitFlag" id="open_msg" value="01" class="open_msg_input" checked="checked" /><label for="open_msg" class="open_msg">开启</label>
								<input type="radio" name="limitFlag" id="close_msg" value="00" class="close_msg_input" /><label for="close_msg" class="close_msg">关闭</label>
							</c:if>
							<c:if test="${account.limitFlag == '00'}">
								<input type="radio" name="limitFlag" id="open_msg" value="01" class="open_msg_input"  /><label for="open_msg" class="open_msg">开启</label>
								<input type="radio" name="limitFlag" id="close_msg" value="00" class="close_msg_input" checked="checked"/><label for="close_msg" class="close_msg">关闭</label>
							</c:if>

                        </span>
							<div class="marLeft200" id="tip_content" style="display: none;">
								<div class="marTop20">
									<span>账户余额不足(元)：</span>
                                <span id="amend_money_before">
                                     <span class="money_num">${account.remindFee}</span>
                                     <button type="button" class="amend_btn amend_balance_btn" id="amend_balance_btn">修改余额</button>
                                </span>
                                <span id="amend_money_after" style="display: none">
                                    <input type="text" id="remindFee" name="remindFee" maxlength="6" class="amend_input money_input_val"/>
                                    <button type="button" class="amend_btn" id="feeSubmit">提交</button>
									<label class="error" id="remindFeeError" style="display: none">请输入整数</label>
                                </span>
								</div>
								<div class="marTop20">
									<span>发送短信至手机：</span>
                                <span id="amend_tel_before">
                                    <span class="telephone_num tel_input">
                                   <c:if test="${empty account.smsMobile }">
                                    ${account.mobile}
								   </c:if>
									<c:if test="${not  empty account.smsMobile }">
										${account.smsMobile}
									</c:if>
									</span>
                                    <button type="button" class="amend_btn amend_tel_btn" id="amend_tel_btn">修改号码</button>
                                </span>
                                <span id="amend_tel_after" style="display: none">
                                    <input type="text"  id="smsMobile" name="smsMobile" maxlength="11" placeholder="请输入新手机号" class="telephone_num amend_input"/>

                                    <button type="button" class="amend_btn" id="gettestcode">获取验证码</button>
									<span><button type="button" class="amend_btn" id="mobileSucAfter" style="display: none"></button></span>
                                    <input type="text" id="num" name="num" maxlength="6" placeholder="请输入验证码" class="amend_input"/>
                                    <button type="button" class="amend_btn" id="telSubmit" style="margin-right:10px;">提交</button>

									<label class="error" id="mobileMsgError" style="display: none">手机号输入有误，请重新输入</label>
									<label class="error" id="vcodeMsgError" style="display: none">验证码输入有误，请重新输入</label>
                                </span>

								</div>
							</div>
						</form>
					</div>
					<!--更huan电话号码成功-->
					<div class="modal-box  phone_t_code" STYLE="display: none" id="modal_box_smsMobile_suc">
						<div class="modal_head">
							<h4>设置短信提醒</h4>
							<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('modal_box_smsMobile_suc')"></a></div>
						</div>
						<div class="modal-common_body mdl_pt_body">
							<div class="test_success test_success_left">
								<i class="lg_common_icon success_txt"></i>
								<span>恭喜您,手机号修改成功!</span>
							</div>
						</div>
					</div>


					<!--更huan电话号码成功-->
					<div class="modal-box  phone_t_code" STYLE="display: none" id="modal_box_smsFee_suc">
						<div class="modal_head">
							<h4>设置短信提醒</h4>
							<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('modal_box_smsFee_suc')"></a></div>
						</div>
						<div class="modal-common_body mdl_pt_body">
							<div class="test_success test_success_left">
								<i class="lg_common_icon success_txt"></i>
								<span>短信提醒的金额设置成功!</span>
							</div>
						</div>
					</div>


					<div class="de_account clear" style="margin-top:0px;">
						<span class="de_m_account"><i class="deve_ico"></i>开发者主账号</span>
						<p class="de_com_c">Account Sid：<br/>
							<span class="de_com_top">${account.sid}</span>
						</p>
						<c:if test="${not empty authFlag}">
							<p class="de_com_c de_border"> Auth Token：<br/>
								<span class="de_com_top">${account.token}</span>
						        <button type="button" class="ph_test_btn" id="updateMobileAuth">重置</button>
						    </p>
						</c:if>
						<c:if test="${empty authFlag}">
							<p class="de_com_c de_border"> Auth Token：<br/>
								<span class="de_com_top" id="authToken">**********************</span>
							    <button type="button" class="ph_test_btn" id="checkMobileAuth">查看</button>
							</p>
						</c:if>

							<p class="de_com_c">Rest URL：<br/>
							<span class="de_com_top"> https://api.33e9cloud.com</span>
							</p>
					</div>

					<div class="modal-box  phone_t_code" id="modal_box_mobile" style="display: none">
						<div class="modal_head">
							<h4>手机验证</h4>
							<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('modal_box_mobile')"></a></div>
						</div>
						<div class="modal-common_body mdl_pt_body">
							<div class="Error_box">
								<label for="inp_phone" class="error" style="display: none;">请输入正确的手机号码</label>
							</div>
							<label for="inp_phone" class="IpCode">&nbsp;&nbsp;&nbsp;&nbsp;请输入手机号码</label>
							<input type="text" id="inp_phone" class="modal_input_style" disabled="true"　readOnly="true" value="<c:out value='${account.mobile}'/>" style="width:250px;"/>
							<div class="Error_box" >
								<label class="error" id="mobileError" style="display: none">验证码输入有误，请重新输入</label>
							</div>

							<div class="phone_btn">
								<span><button type="button" class="get_test_code" id="upMobleB">获取短信验证码</button></span>
								<span><button type="button" class="send_test_code" id="mobileSuc" style="display: none"></button></span>
							</div>
								<label for="vcode" class="TeCode">&nbsp;请输入手机验证码</label>
								<input type="text" id="vcode" name="vcode" size="12" maxlength="6" class="modal_input_style" style="width:250px;"/><br/>
								<%--<label class="error changePhoneLeft" id="vcodeError" style="display: none">验证码输入有误，请重新输入</label>--%>
						    </div>
							<div class="Error_box" >
								<label class="error" id="vcodeError" style="display: none">验证码输入有误，请重新输入</label>
							</div>
							<div class="mdl_foot">
								<button type="button" class="true" id="saveMobileB">确&nbsp;&nbsp;认</button>
								<button type="button" class="false" onclick="closeDiv('modal_box_mobile')">取&nbsp;&nbsp;消</button>
							</div>
					</div>

					<div class="modal-box  phone_t_code"  style="display: none" id="modal_box_mobile_suc">
						<div class="modal_head">
							<h4>手机验证</h4>
							<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('modal_box_mobile_suc')"></a></div>
						</div>
						<div class="modal-common_body mdl_pt_body">
							<div class="test_success">
								<i class="common_icon success_txt"></i>
								<span>手机验证成功</span>
							</div>
						</div>

					</div>


					<!--弹层——重置页面-->
					<div class="modal-box  phone_t_code" id="update_box_mobile" style="display: none;">
						<div class="modal_head">
							<h4>重置Token</h4>
							<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt"  onclick="closeDiv('update_box_mobile')"></a></div>
						</div>
						<div class="modal-common_body mdl_pt_body">
							<div class="T_rest">
								<form id="resetForm" name = "resetForm" action="<c:url value='/accMgr/reset'/>" method="post">
									<p>重置Auth&nbsp;token&nbsp;是很危险的动作，</p>
									<p>建议在应用使用人数少的情况下中断服务进行充值，</p>
									<p>重置后原token将不可用，请谨慎操作。</p>
								</form>
							</div>
						</div>
						<div class="mdl_foot" style="margin:100px 0 0 150px;">
							<button type="button" class="true" name="resetButton" id="resetButton" >确&nbsp;&nbsp;认</button>
							<button type="button" class="false" onclick="closeDiv('update_box_mobile')">取&nbsp;&nbsp;消</button>
						</div>
					</div>


				</div>
			</div>
		</div>

	</div>
</section>


</body>
</html>
