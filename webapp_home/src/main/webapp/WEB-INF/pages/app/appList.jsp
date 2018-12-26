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
	<link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
	<!--[if lt IE 9]>
	<script src="js/html5shiv.min.js"></script>
	<![endif]-->
	<script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none;"></div>
<!--遮罩end-->

<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->

<section>
	<div id="sec_main">
		<!-- 左侧菜单 start -->
		<jsp:include page="../common/controlleft.jsp"/>
		<script type="text/javascript">showSubMenu('kf','appMgr','appList');</script>
		<!-- 左侧菜单 end -->
		<div class="main3">
			<div class="container" >
				<div class="msg">
					<h3>应用列表</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="application_msg">
					<div class="app_note">
						<span class="WarmPrompt">温馨提醒：</span>
						<p>1.请您按照规则，保持您的应用良好运营，如果出现违规等恶意操作，我们将会对您的账号进行冻结及禁用等处理。</p>
						<p>2.如果您不想使用当前应用，可以选择禁用，受到禁用的应用将就此作废，不可恢复。</p>
						<p>3.如果您的应用出于冻结状态，需要联系我们的客服人员进行解冻，才可以恢复正常使用。</p>
					</div>
					<div class="app_select">
						<span class="check_font">应用类型</span>
						<select name="appType" id="appType">

						</select>
						<span class="check_font">应用名称</span>
						<input type="text" id="appName" name="appName" size="18" maxlength="30" class="l_input_style" style="width:220px;"/>
						<button type="button" class="md_btn f_right" id="appSearch">查询</button>
					</div>

					<!--应用列表-->
					<div class="application">
						<table class="table_style" id="apptable">
							<thead>
							<tr>
								<th style="width:14.6%">应用名称</th>
								<th style="width:13.6%">应用类型</th>
								<th style="width:16.6%">创建时间</th>
								<th style="width:24.6%">APP&nbsp;ID</th>
								<th style="width:8.6%">状态</th>
								<th style="width:10.6%">消费限额(元)</th>
								<th style="width:16.6%">操作</th>
							</tr>
							</thead>
							<tbody id="integralBody">

							</tbody>
						</table>
						<div class="f_right app_sorter"  id="pagination"></div>
					</div>
				</div>
				<!--应用编辑-->
				<%--<div class="modal-box  phone_t_code" id="app_edit_box" style="width: 600px;height: 310px;display: none;" >
					<form id="userForm" name = "userForm" action="" method="post">
					<div class="modal_head">
						<h4>应用编辑</h4>
						<div class="p_right" style="left:573px;"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('app_edit_box');"></a></div>
					</div>
					<div id="app_update_body">

					</div>
					</form>
				</div>--%>
				<!--应用查看-->
				<%--<div class="modal-box  phone_t_code" id="app_view_box" style="width: 600px;height: 310px;display: none;" >
					<div class="modal_head">
						<h4>应用查看</h4>
						<div class="p_right" style="left:573px;" ><a href="javascript:void(0);" class="common_icon close_txt"  onclick="closeDiv('app_view_box');"></a></div>
					</div>
					<div class="modal-common_body mdl_pt_body" id="app_view_body">
						<input type="text" style="margin-left:54px" id="appUpName" name="appUpName" size="28"  />
						<input type="text" style="margin-left:54px" size="38" id="callbackUrl" name="callbackUrl" value=""/>
					</div>
				</div>--%>
				<!--应用删除-->
				<div class="modal-box  phone_t_code" id="app_delete_box" style="display: none">
					<div class="modal_head">
						<h4>应用删除</h4>
						<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('app_delete_box');"></a></div>
					</div>
					<div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
						<div class="app_delete_main">
							<p class="app_delete_common"><i class="attention"></i>该应用一旦删除后，将不能恢复</p>
							<p class="app_delete_common">您确定要删除吗？</p>
						</div>
					</div>
					<div class="mdl_foot f_top">
						<button type="button"  id="appDelButton" class="true">确&nbsp;认</button>
						<button type="button" class="false" onclick="closeDiv('app_delete_box');">取&nbsp;消</button>
					</div>
				</div>
			</div>

		</div>
	</div>
</section>
<!-- 呼入 -->
<script id="integralTemplate" type="text/x-jquery-tmpl">
	<tr class="date_hg">
		<td>\${appName}</td>
		<td>\${appType}</td>
		<td>\${createDate}</td>
		<td>\${appid}</td>
		<td>\${stt}</td>
		<td>\${quota}</td>
		<td>
		{{if status=='00'}}
			  <a href="<c:url value='/appMgr/appInfoEdit'/>?appid=\${appid}">编辑</a>
			  <a href="<c:url value='/appMgr/appInfoView'/>?appid=\${appid}">查看</a>
			  <a href="javascript:void(0);" onclick="appCanel('\${id}');">删除</a>
		{{/if}}
		{{if status=='01'}}
			  <a href="<c:url value='/appMgr/appInfoView'/>?appid=\${appid}">查看</a>
		{{/if}}
		</td>
		<%--<td>\${limit_flag}</td>--%>
	</tr>
</script>

<%--script id="integralAppViewTmpl" type="text/x-jquery-tmpl">
	<div class="app_checked_main">
		<div class="check_common">
			<span class="app_financial">应&nbsp;用&nbsp;名&nbsp;称：</span>
			<span>\${appName}</span>
		</div>
		<div class="check_common">
			<span class="app_financial">应&nbsp;用&nbsp;类&nbsp;型：</span>
			<span>\${dicData.name}</span>
			<span class="app_financial state_type">状&nbsp;态：</span>
			<span>
			{{if status=='00'}}
				正常
			{{/if}}
			{{if status=='01'}}
				冻结
			{{/if}}
			{{if status=='02'}}
				已删除
			{{/if}}
			</span>
		</div>
		<div class="check_common">
			<span class="app_financial">应&nbsp;用&nbsp;回&nbsp;调：</span>
			<span>
			{{if urlStatus=='00'}}
				已启用
			{{/if}}
			{{if urlStatus=='01'}}
				未启用
			{{/if}}
			</span>
			<div class="check_callback_address">
				<div style="margin-left:54px";class="callback_address">回调地址URL:</div>
				<span style="margin-left:54px ;word-break: break-all; word-wrap: break-word;";class="ca_address">\${callbackUrl}</span>
			</div>
		</div>
		<div class="check_common">
			<span class="app_financial">详&nbsp;情：</span>
			 <span>
				 <span class="APP_ID">APP&nbsp;ID：</span>
				 <span class="APP_ID">\${appid}</span>
			 </span>
		</div>
	</div>
</script>

<script-- id="integralAppUpdateTmpl" type="text/x-jquery-tmpl">
	<div class="modal-common_body mdl_pt_body" >
		<div class="app_edit_main">
			<div class="edit_common">
				<label for="app_name" class="edit_common_font edit_left">应用名称</label>
				<input type="text" id="appUpName" name="appUpName" size="28" maxlength="40" value="\${appName}"/>
				<label class="app_name_note">不超过20个汉字</label>
			</div>
			<div class="edit_common">
				<label for="app_name" class="edit_common_font edit_left">应用类型</label>
				<select name="appUpType" id="appUpType" class="app_type">

				</select>
				<label class="edit_common_font edit_left state_left">状态：</label>
				<span class="state_normal">
					{{if status=='00'}}
						正常
					{{/if}}
					{{if status=='01'}}
						冻结
					{{/if}}
					{{if status=='02'}}
						已删除
					{{/if}}
				</span>
			</div>
			<div class="edit_common">
				<label class="edit_common_font edit_left">应用回调</label>
					<input type="hidden" id="urlStatus" name="urlStatus" value="\${urlStatus}"/>
					{{if urlStatus=='00'}}
						<input type="checkbox" id="urlCheck" name="urlCheck" onclick="checkUrlStt();" checked/>
					{{/if}}
					{{if urlStatus=='01'}}
						<input type="checkbox" id="urlCheck" name="urlCheck" onclick="checkUrlStt();" />
					{{/if}}
				<label for="urlStatus">
						启用
				</label>
			</div>
			<div class="edit_common">
				<div class="callback_address_left">
					<div class="callback_address">回调地址URL:</div>
					{{if urlStatus=='00'}}
						<input type="text"  size="38" id="callbackUrl" name="callbackUrl" value="\${callbackUrl}"  maxlength="100"/>
					{{/if}}
					{{if urlStatus=='01'}}
						<input type="text"  size="38" id="callbackUrl" name="callbackUrl"  disabled="disabled" value="\${callbackUrl}" maxlength="100"/>
					{{/if}}

				</div>
			</div>
			<div class="edit_common">
				<label class="edit_common_font edit_left">详情：</label>
				<span>
					<span class="APP_ID">APP&nbsp;ID：</span>
					<span class="APP_ID">\${appid}</span>
				</span>
			</div>
		</div>
	</div>
	<div class="mdl_foot f_top">
		<button type="button" class="true"  onclick="appUpdate('\${id}');">提&nbsp;&nbsp;交</button>
		<button type="button" class="false" onclick="closeDiv('app_edit_box');">取&nbsp;&nbsp;消</button>
	</div>
</script--%>
<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>

<script>
	var validator = $("#userForm").validate({
		rules: {
			appUpName: {
				required: true,
				appName:true
			},
			callbackUrl: {
				required: true,
				appUrl: true
			}
		},messages: {
			appUpName: {
				required: '请输入应用名称！'
			},callbackUrl: {
				required:'请输入URL！',
				appUrl:"请输入合法的URL！"
			}
		}
	});
	$(function(){
		$('#appSearch').click(function() {
			var tpyeValue = $("#appType").val();
			var nameValue = $("#appName").val();
			$("#apptable").reload(
					{'appType':tpyeValue,"appName":nameValue}
			)
		});
		$('#appDelButton').click(function() {
			var id = appid;
			$.ajax({
				type: "post",//使用post方法访问后台
				dataType: "json",//返回json格式的数据
				url: "<c:url value='/appMgr/del'/>",
				data:{'id':id},//要发送的数据
				success: function(data){//data为返回的数据，在这里做数据绑定
					if(data){
						$('#shadeWin').hide();
						$('#app_delete_box').hide();
						$('#appSearch').click();
					}else{
						alert("出现异常");
					}

				},
				error  : function(data){
					alert("出现异常");
				}
			});
		});
	})

	ajaxDicData('${appConfig.authUrl}',"appType","appType");
	<%--function appView(appid){
		$("#shadeWin").show();
		$("#app_view_body").empty();
		$.ajax({
			type: "post",//使用post方法访问后台
			dataType: "json",//返回json格式的数据
			url: "<c:url value='/appMgr/view'/>",
			data:{'appid':appid},//要发送的数据
			success: function(data){//data为返回的数据，在这里做数据绑定
				$( "#integralAppViewTmpl" ).tmpl( data ).appendTo( $("#app_view_body") );
				$('#shadeWin').show();
				$('#app_view_box').show();
			},
			error  : function(data){
				alert("出现异常");
			}
		});
	}--%>

	var appid;
	function appCanel(id){
		$("#shadeWin").show();
		$("#app_delete_box").show();
		appid = id;
	}


	<%--function appUpdateView(appid){
		$("#app_update_body").empty();
		$.ajax({
			type: "post",//使用post方法访问后台
			dataType: "json",//返回json格式的数据
			url: "<c:url value='/appMgr/view'/>",
			data:{'appid':appid},//要发送的数据
			success: function(data){//data为返回的数据，在这里做数据绑定
				$( "#integralAppUpdateTmpl" ).tmpl( data ).appendTo( $("#app_update_body") );
				ajaxDicData('${appConfig.authUrl}',"appType","appUpType",data.appType);
				$('#shadeWin').show();
				$('#app_edit_box').show();
			},
			error  : function(data){
				alert("出现异常");
			}
		});
	}--%>

	function closeDiv(divid){
		$('#' + divid ).hide();
		$('#shadeWin').hide();
	}
	// 应用列表
	$("#apptable").page({
		url:"<c:url value='/appMgr/list'/>",
		pageSize:5,
		dataRowCallBack:dataRowCallBack
	});

	function dataRowCallBack(row) {
		// TODO:
		if (row.createDate) {
			row.createDate = o.formatDate(row.createDate, "yyyy-MM-dd hh:mm:ss");
		}
		if (row.status == '00') {
			row.stt = "正常";
		}
		if (row.status == '01') {
			row.stt = "冻结";
		}
		if (row.status == '02') {
			row.stt = "已删除";
		}
		if (row.limitFlag == '00') {
			row.quota = "无限制";
		}

		return row;
	}

	<%--function checkUrlStt(){
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
	}

	function appUpdate(id){
		var valid = validator.form();
		if (!valid){
			return false;
		}
		var appName = $('#appUpName').val();
		var urlStatus = $('#urlStatus').val();
		var callbackUrl = $('#callbackUrl').val();
		var appType =  $('#appUpType').val();
		$.ajax({
			type: "post",//使用post方法访问后台
			dataType: "json",//返回json格式的数据
			url: "<c:url value='/appMgr/update'/>",
			data:{'id':id,'appName':appName,'urlStatus':urlStatus,'appType':appType,'callbackUrl':callbackUrl},//要发送的数据
			success: function(data){//data为返回的数据，在这里做数据绑定
				if(data){
					$('#shadeWin').hide();
					$('#app_edit_box').hide();
					$('#appSearch').click();
				}else{
					alert("出现异常");
				}

			},
			error  : function(data){
				alert("出现异常");
			}
		});

	}--%>
</script>
</body>
</html>
