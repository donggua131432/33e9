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
	<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
	<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
	<script>
		var table;
		$(function(){
			$(".msg_state").delegate(".btn_style", "click", function(){
				$(this).addClass("hover_state_btn");
				$(this).siblings().removeClass("hover_state_btn");
				var status = $(this).attr("data-status");
				if (status == '1') {
					$("#hadread").hide();
				} else {
					$("#hadread").show();
				}
				table.reload({params : { status : status }});
			});

			// 消息列表
			table = $("#msgtable").page({
				url : "<c:url value='/msgMgr/pageMsg'/>",
				pageSize : 10,
				dataRowCallBack : dataRowCallBack
			});

			function dataRowCallBack(row) {
				// TODO:
				if (row.sendTime) {
					row.sendTime = o.formatDate(row.sendTime, "yyyy-MM-dd hh:mm:ss");
				}
				if (row.msgCode == 0){
					row.msgCode = "系统通知";
				}
				if (row.msgCode == 1){
					row.msgCode = "产品升级";
				}
				if (row.msgCode == 2){
					row.msgCode = "运营活动";
				}
				if (row.msgCode == 3){
					row.msgCode = "营销推广";
				}
				if (row.msgCode == 4){
					row.msgCode = "公告通知";
				}
				var className = 'checkMsgColor';
				if (row.status == '0') {
					className = 'NoCheckMsgColor';
				}
				row.className = className;

				var checkbox = "";
				if (!checkbox) {
					checkbox = document.getElementById("checkbox");
				}
				if (checkbox.checked) {
					checkbox.checked = false;
				}

				return row;
			}

			// 初始化消息
			initMessage();

			$('#hadread').click(function() {
				var strId = getid();
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/msgMgr/updateStatus'/>",
					data:{'strId':strId},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							$('#shadeWin').show();
							$('#hadRead_message').show();
							initMessage();
						} else {
							$('#shadeWin').show();
							$('#choose_message').show();
						}
					},
					error: function (data) {
						alert("系统异常，请稍后重试！");
					}
				});
			});

			$('#delete').click(function() {
				var strId = getid();
				$.ajax({
					type: "post",//使用post方法访问后台
					dataType: "json",//返回json格式的数据
					url: "<c:url value='/msgMgr/deleteMessage'/>",
					data:{'strId':strId},//要发送的数据
					success: function (data) {
						if(data.code=='00') {
							$('#shadeWin').hide();
							$('#delete_message_suc').hide();
							initMessage();
							table.reload({},true);
						} else {
							$('#shadeWin').show();
							$('#choose_message').show();
						}
					},
					error: function (data) {
						alert("系统异常，请稍后重试！");
					}
				});
			});

			// 全选框
			$("#checkbox").on("change", function() {
				console.log(this.checked);
				if (this.checked) {
					$("#integralBody input[type='checkbox']").each(function(i) {
						this.checked = true;
					});
				} else {
					$("#integralBody input[type='checkbox']").each(function(i) {
						this.checked = false;
					});
				}
			});

		});

		function initMessage() {
			$.ajax({
				type: "post",//使用post方法访问后台
				dataType: "json",//返回json格式的数据
				url: "<c:url value='/msgMgr/getcount'/>",
				success: function (data) {
					$('#countAll').text("全部消息(" + data.countAll+")");
					$('#countA').text("已读消息(" + data.countA+")");
					$('#countB').text("未读消息(" + data.countB+")");
					$('#messageA').text(data.countB + "个未读消息");
				}
			});
		}

		function getid(){
			//获得checkbox的id值
			var strId = "";
			$("#integralBody input[type='checkbox']:checked").each(function(i){
				console.log(i);
				if(i < $("#integralBody input[type='checkbox']:checked").length-1){
					strId += $(this).val() + ",";
				}else{
					strId += $(this).val();
				}
			});
			return strId;
		}

		// 开启消息框
		function showmsg(obj) {
			var content = $(obj).attr("data-content");
			var id = $(obj).attr("data-id");

			$("#msgtype").html("系统消息：");
			$("#shadeWin").show();
			$("#msgCon").show();

			$.ajax({
				type: "post",//使用post方法访问后台
				dataType: "json",//返回json格式的数据
				url: "<c:url value='/msgMgr/readMessage'/>",
				data:{'strId':id},//要发送的数据
				success: function (data) {
					initMessage();
					$(".notice_content").html(data.msg.content);
				},
				error: function (data) {
					alert("系统异常，请稍后重试！");
				}
			});

		}

		// 关闭消息框
		function closemsg() {
			$("#shadeWin").hide();
			$("#msgCon").hide();
			$(".notice_content").html("");
			$("#msgtype").html("");
			table.reload({},true);
		}

		function closeDiv(divid){
			$('#' + divid ).hide();
			$('#shadeWin').hide();
			table.reload({},true);
		}

		function showDiv(divid){
			$('#' + divid ).hide();
			$('#shadeWin').hide();
		}

		function flagdelete(){
			var strId = getid();
			if(strId.length > 0){
				$("#shadeWin").show();
				$("#delete_message_suc").show();
			} else {
				$("#shadeWin").show();
				$("#choose_message").show();
			}
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
		<script type="text/javascript">showSubMenu('zh','acc_manage','message');</script>
		<div class="main3">
			<div class="container3">
				<div class="msg">
					<h3>消息管理</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="msg_management">
					<div class="msg_state">
						<span id="countAll" class="btn_style hover_state_btn">全部消息(0)</span>
						<span id="countA" class="btn_style" data-status="1">已读消息(0)</span>
						<span id="countB" class="btn_style" data-status="0">未读消息(0)</span>
					</div>
					<div class="msg_content">
						<table class="msg_table" id="msgtable">
							<thead>
								<tr>
									<th style="width:15%">类&nbsp;型</th>
									<th style="width:65%">标&nbsp;题&nbsp;内&nbsp;容</th>
									<th style="width:20%">通&nbsp;知&nbsp;时&nbsp;间</th>
								</tr>
							</thead>
							<tbody id="integralBody">

							</tbody>
						</table>
						<div class="table_foot">
                            <span class="table_f1">
                                 <input type="checkbox" id="checkbox"/>
                                 <button type="button" id="hadread" name="hadread">标&nbsp;为&nbsp;已&nbsp;读</button>
                                 <button type="button" id="flagdelete" name="flagdelete" onclick="flagdelete();">删&nbsp;&nbsp;除</button>
								</span>
						</div>
						<div class="f_right app_sorter" id="pagination"></div>

					</div>
				</div>

				<!--系统通知设置成功-->
				<div class="modal-box phone_t_code" id="msgCon" style="display: none;">
					<div class="modal_head">
						<h4 id="msgtype">系统通知：</h4>
						<div class="p_right"><a href="javascript:;" onclick="closemsg();" class="common_icon close_txt"></a></div>
					</div>
					<div class="modal-common_body mdl_pt_body" style="height:300px;overflow-y:auto">
						<div class="notice_content">
						</div>
					</div>
				</div>

				<!--删除消息成功-->
				<div class="modal-box  phone_t_code" style="display:none;" id="delete_message_suc">
					<div class="modal_head">
						<h4>消息删除</h4>
						<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('delete_message_suc');"></a></div>
					</div>
					<div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
						<div class="app_delete_main">
							<p class="app_delete_common"><i class="attention"></i>消息一旦删除后，将不能恢复</p>
							<p class="app_delete_common">您确定要删除吗？</p>
						</div>
					</div>
					<div class="mdl_foot f_top">
						<button type="button" id="delete" class="true">确&nbsp;认</button>
						<button type="button" class="false" onclick="closeDiv('delete_message_suc');">取&nbsp;消</button>
					</div>
				</div>

				<div class="modal-box  phone_t_code" style="display:none;" id="choose_message">
					<div class="modal_head">
						<h4>提示信息</h4>
						<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="showDiv('choose_message')"></a></div>
					</div>
					<div class="modal-common_body mdl_pt_body set_paw_left">
						<div class="test_success test_success_left">
							<i class="attention"></i>
							<span>您没有进行选择</span>
						</div>
					</div>
				</div>

				<div class="modal-box phone_t_code" style="display:none;" id="hadRead_message">
					<div class="modal_head">
						<h4>提示信息</h4>
						<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('hadRead_message')"></a></div>
					</div>
					<div class="modal-common_body mdl_pt_body set_paw_left">
						<div class="test_success test_success_left">
							<i class="lg_common_icon success_txt"></i>
							<span>消息已标为已读</span>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</section>

<%--<div id="shadeWin" class="shade_on_win"></div>--%>

<script id="integralTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td>
			<input type="checkbox" name="checkbox" value="\${id}"/>
			<label class="\${className}">\${msgCode}</label>
		</td>
		<td>
			<span>
				<a href="javascript:;" onclick="showmsg(this);" data-id="\${id}" data-status="\${status}" class="\${className}">\${title}</a>
			</span>
		</td>
		<td><span class="\${className}">\${sendTime}</span></td>
	</tr>
</script>

</body>
</html>
