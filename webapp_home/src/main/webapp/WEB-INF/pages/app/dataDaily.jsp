<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/3/09
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
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
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/calceval.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
	<!-- 主叫 被叫 -->
	<script id="callTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>\${num}</td>
			<td>\${stafDay}</td>
			<td>\${appName}</td>
			<td>\${callCnt}</td>
			<td>\${succCnt}</td>
			<td>\${callRate}</td>
			<td>\${thscSum}</td>
			<td>\${callTimeLengthAvg}</td>
			<td>\${jfscSum}</td>
			<td>\${fee}</td>
			<td>\${recordingFee}</td>
		</tr>
	</script>

	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/pagings.js"></script>
	<script type="text/javascript">
		var appInfoArray = {},callingTable,calledTable;
		$(function(){
			//应用列表
			appInfoArray = getAppInfo("<c:url value='/appMgr/getALLAppInfo'/>","appid","appName");

			//主叫
			callingTable = $("#callingTable").page({
				url:"<c:url value='/restStafDayRecord/getCallStatisticsList'/>",
				pageSize:30,
				integralBody:"#callingTbody",// 将模版加入到某个元素的ID
				integralTemplate:"#callTemplate",// tmpl中的模版ID
				pagination:"#callingPagination", // 分页选项容器
				param:{"abLine":"A","stafDay":yesterday},
				dataRowCallBack:dataRowCallBack
			});

			//被叫
			calledTable = $("#calledTable").page({
				url:"<c:url value='/restStafDayRecord/getCallStatisticsList'/>",
				pageSize:30,
				integralBody:"#calledTbody",// 将模版加入到某个元素的ID
				integralTemplate:"#callTemplate",// tmpl中的模版ID
				pagination:"#calledPagination", // 分页选项容器
				param:{abLine:"B","stafDay":yesterday},
				dataRowCallBack:dataRowCallBack
			});
		});

		$(function(){
			laydate(stafDay);
			laydate(stafDay1);
		});
		var yesterday = "", beforThreeMonth="", date = new Date();
		date.setDate(date.getDate()-1);
		yesterday = date.format("yyyy-MM-dd");
		date.setMonth(date.getMonth()-3);
		beforThreeMonth =  date.format("yyyy-MM-dd");
		var stafDay = {
			elem: '#stafDay',
			format: 'YYYY-MM-DD',
			max: laydate.now(), //设定最大日期为当前日期
			min: beforThreeMonth, //最小日期
			istime: false,
			istoday: true,
			choose: function(datas){
				stafDay1.min = datas; //开始日选好后，重置结束日的最小日期
				stafDay1.start = datas //将结束日的初始值设定为开始日
			}
		};

		var stafDay1 = {
			elem: '#stafDay1',
			format: 'YYYY-MM-DD',
			max: laydate.now(), //设定最大日期为当前日期
			min: beforThreeMonth, //最小日期
			istime: false,
			istoday: true,
			choose: function(datas){
				stafDay.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		function dataRowCallBack(row,num) {
			//序号列
			row.num = num;

			/*if (row.stafDay) {
				row.stafDay = o.formatDate(row.stafDay, "yyyy-MM-dd");
			}*/

			if(row.appid){
				row.appName = appInfoArray[row.appid];
			}

			//接通率
			if(row.callCnt!=undefined && row.succCnt!=undefined){
				if(row.callCnt != 0){
					row.callRate = parseFloat($.calcEval("("+row.succCnt+"/"+row.callCnt+")")*100).toFixed(2);
				}else{
					row.callRate =parseFloat(0).toFixed(2);
				}
			}else{
				row.callRate = parseFloat(0).toFixed(2);
			}

			//平均通话时长
			if(row.succCnt!=undefined && row.thscSum!=undefined){
				if(row.succCnt != 0){
					row.callTimeLengthAvg = parseFloat($.calcEval("("+row.thscSum+"/"+row.succCnt+")")).toFixed(0);
				}else{
					row.callTimeLengthAvg = parseFloat(0).toFixed(2);
				}
			}else{
				row.callTimeLengthAvg = parseFloat(0).toFixed(2);
			}
			return row;
		}


		// 切换tab
		function switchTab(sid, hid) {
			//如果表格本身是隐藏的则不清空
//			if($(sid).css("display") == "none"){
//				$("#searchForm").find(":input[id]").each(function(){
//					$(this).val("");
//				});
//			}
			$(sid).show();
			$(hid).hide();
		}



		//下载报表
		function downLoadDataDaily(){
			var formData = {};
			$("#searchForm").find(":input[id]").each(function(){
				formData[$(this).attr("id")] = $(this).val().trim();
			});

			if($("#calling").css("display") == "block"){
				formData["abLine"] = "A";
				callingTable.downloadReport("<c:url value='/restStafDayRecord/downLoadDataDaily'/>",formData)
			}
			if($("#called").css("display") == "block"){
				formData["abLine"] = "B";
				calledTable.downloadReport("<c:url value='/restStafDayRecord/downLoadDataDaily'/>",formData)
			}
		}

		//查询数据日报信息
		function searchDataDaily(){
			var formData = {}, queryStatus = true;
			$("#searchForm").find(":input[id]").each(function(){
				formData[$(this).attr("id")] = $(this).val().trim();
			});


			//提示开始时间不能大于结束时间
			if(formData['stafDay'] != "" && formData['stafDay1'] != ""){
				var stafDay = new Date(formData['stafDay'].replace(/\-/g, '\/'));
				var stafDay1 = new Date(formData['stafDay1'].replace(/\-/g, '\/'));
				if(stafDay > stafDay1){
					queryStatus = false;
					alert("查询时间的开始时间不能大于结束时间！");
				}
			}
			if(queryStatus == true) {
				if ($("#calling").css("display") == "block") {
					callingTable.reload(formData);
				}else if($("#called").css("display") == "block") {
					calledTable.reload(formData);
				}
			}
		}

		//关闭消息框
		function closeMsgDialog(){
			$("#shadeWin").hide();
			$("#msgDialog").hide();
		}
	</script>
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
		<script type="text/javascript">showSubMenu('kf','appMgr','dataDaily');</script>
		<!-- 左侧菜单 end -->
		<div class="main3">
			<div class="container7">
				<div class="msg">
					<h3>数据日报</h3>
					<jsp:include page="../common/message.jsp"/>

				</div>
				<div class="demand Daily">
					<form id="searchForm">
						<span class="c7_common">查询时间：</span>
						<input id="stafDay" name="stafDay" type="text" class="input_style" style="width:168px;"/>至
						<input id="stafDay1" name="stafDay1" type="text" class="input_style" style="width:168px;"/>
						<span class="c7_common demand_left">应用名称：</span>
						<select id="appid" name="appid">
							<option selected value="">全部</option>
						</select>
						<button type="button" class="md_btn f_right" onclick="searchDataDaily();">查询</button>
					</form>
				</div>
				<div class="date">
					<div class="rate_box">
						<span onclick="switchTab('#calling', '#called');" class="call_rate rate_hover">主&nbsp;&nbsp;&nbsp;叫</span>
						<span onclick="switchTab('#called', '#calling');" class="call_rate">被&nbsp;&nbsp;&nbsp;叫</span>
						<span class="open_down_billed f_right" onclick="downLoadDataDaily();"><i class="common_icon download_txt"></i>导出报表</span>
					</div>
					<!--查询弹层-->
					<div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">
						<div class="modal_head">
							<h4>数据日报导出</h4>
							<div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
						</div>
						<div class="modal-common_body mdl_pt_body">
							<div class="test_success test_success_left">
								<i class="attention"></i>
								<span>您暂无数据</span>
							</div>
						</div>
					</div>
					<!--主叫-->
					<div class="data_rate_box" id="calling" >
						<table class="table_style" id="callingTable">
							<thead>
							<tr>
								<th rowspan="2">序号</th>
								<th rowspan="2">日期</th>
								<th rowspan="2">应用名称</th>
								<th colspan="3">通话数量</th>
								<th colspan="3">通话时长</th>
								<th colspan="2">费用(元)</th>
							</tr>
							<tr>
								<th>呼叫总量</th>
								<th>接通量</th>
								<th>接通率(%)</th>
								<th>总通话时长(秒)</th>
								<th>平均通话时长(秒)</th>
								<th>计费通话时长(分钟)</th>
								<th>通话费用</th>
								<th>录音费用</th>
							</tr>
							</thead>
							<tbody id="callingTbody">

							</tbody>
						</table>
						<div class="f_right app_sorter"  id="callingPagination"></div>
					</div>
					<!--被叫-->
					<div class="data_rate_box" id="called" style="display: none;">
						<table class="table_style" id="calledTable">
							<thead>
							<tr>
								<th rowspan="2">序号</th>
								<th rowspan="2">日期</th>
								<th rowspan="2">应用名称</th>
								<th colspan="3">通话数量</th>
								<th colspan="3">通话时长</th>
								<th colspan="2">费用(元)</th>
							</tr>
							<tr>
								<th>呼叫总量</th>
								<th>接通量</th>
								<th>接通率(%)</th>
								<th>总通话时长(秒)</th>
								<th>平均通话时长(秒)</th>
								<th>计费通话时长(分钟)</th>
								<th>通话费用</th>
								<th>录音费用</th>
							</tr>
							</thead>
							<tbody id="calledTbody">

							</tbody>
						</table>
						<div class="f_right app_sorter"  id="calledPagination"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>
