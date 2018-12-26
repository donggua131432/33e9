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
	<link rel="stylesheet" href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
	<!--[if lt IE 9]>
	<script src="js/html5shiv.min.js"></script>
	<![endif]-->
	<link rel="stylesheet" href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>

	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<jsp:include page="../common/loadJs.jsp"/>

	<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
	<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
	<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>

	<script id="czlog" type="text/x-jquery-tmpl">
	<tr class="date_hg">
		<td>\${cname}</td>
		<td>\${areaCode}</td>
		<td>\${number}</td>
	</tr>
	</script>

	<script type="text/javascript">
		var cityInfoArray = {};
		var search_param = {};

		$(function(){
			$("#number").val('');
			$("#areaCode").val('');
			cityInfoArray = getAppInfo("<c:url value='/seNumMgr/getCityInfo'/>","id","city");
			$("#maskNum").page({
				url:"<c:url value='/voiceverifyTemp/pageVoiceverifyNum'/>?appid=${appid}",
				pageSize:10,
				integralTemplate:"#czlog",// tmpl中的模版ID
				integralBody:"#czjllog",// 将模版加入到某个元素的ID
				pagination:"#appListPagination",
				dataRowCallBack:dataRowCallBack
			});

		});





		function search(){
			$("#maskNum").reload({ "params[number]" : $("#number").val(),"params[areaCode]" : $("#areaCode").val()});
		}


		function dataRowCallBack(row) {
			var reg =  /^1\d{10}$/;
			var m95xxx  =/^95\d{3}$/;
			if(reg.test(row.number)){
				row.areaCode="";
			}
			if(m95xxx.test(row.number)){
				row.cname="";
				row.areaCode="";
			}

			return row;
		}

		function closeDiv(divid){
			$('#' + divid ).hide();
			$('#shadeWin').hide();
		}

		// 导出报表
		function downloadReport() {
			var formData = {};
			formData["number"] = $("#number").val();
			formData["areaCode"] = $("#areaCode").val();
			$.fn.downloadReport("<c:url value='/voiceverifyTemp/downloadReport'/>?appid=${appid}",formData);
			<%--table.downloadReport("<c:url value='/voiceverifyTemp/downloadReport'/>?appid=${appid}",formData);--%>
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
		<script type="text/javascript">showSubMenu('kf','appMgr','voiceVerificationList');</script>
		<!-- 左侧菜单 end -->
		<div class="main3">
			<div class="container15">
				<div class="msg">
					<h3>外显号码列表</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<form id="searchForm">
					<div class="app_select">
						<span class="check_font ">区号:</span>
						<input type="text" id="areaCode" name="areaCode" size="8" maxlength="30" class="l_input_style" style="width:180px;"/>
						<span class="check_font pr_left">号码:</span>
						<input type="text" id="number" name="number" size="18" maxlength="30" class="l_input_style" style="width:180px;"/>
						<button type="button" class="md_btn f_right" style="margin-top:-6px;" onclick="search();">查询</button>
					</div>
				</form>

				<!--应用列表-->
				<div class="application">
					<div class="record_down f_right">
						<span  onclick="downloadReport();" class="open_down_billed"><i class="common_icon download_txt"></i>导出</span>
					</div>
					<!--查询弹层-->
					<div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">
						<div class="modal_head">
							<h4>外显号码列表导出</h4>
							<div class="p_right"><a href="javascript:void(0);" onclick="closeDiv('msgDialog');" class="common_icon close_txt"></a></div>
						</div>
						<div class="modal-common_body mdl_pt_body">
							<div class="test_success test_success_left">
								<i class="attention"></i>
								<span>您暂无数据</span>
							</div>
						</div>
					</div>
					<div class="numManagement">
						<table class="table_style" id="maskNum">
							<thead>
							<tr>
								<th style="width:15%">城市</th>
								<th style="width:15%">区号</th>
								<th style="width:20%">号码</th>
							</tr>
							</thead>
							<tbody id="czjllog">

							</tbody>
						</table>
						<div class="turnPage f_right"  id="appListPagination"></div>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>
