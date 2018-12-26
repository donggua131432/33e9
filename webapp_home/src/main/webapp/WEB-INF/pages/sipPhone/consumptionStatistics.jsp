<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<title>控制中心页面</title>
	<link rel="stylesheet" href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" >
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
	<!--[if lt IE 9]>
	<script type="text/javascript" src="js/html5shiv.min.js"></script>
	<![endif]-->
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/calceval.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/My97DatePicker/WdatePicker.js"></script>

	<script id="CSTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>日报</td>
			<td>\${statDay}</td>
			<td>\${appName}</td>
			<td>\${fee}</td>
			<td>\${recordingFee}</td>
			<td>\${totalFee}</td>
		</tr>
	</script>

	<script id="CSTemplate_m" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>月报</td>
			<td>\${smonth}</td>
			<td>\${appName}</td>
			<td>\${fee}</td>
			<td>\${recordingFee}</td>
			<td>\${sipNum}</td>
			<td>\${rent}</td>
			<td>\${costMin}</td>
			<td>\${totalFee}</td>
			 <td>
			 <a href="javascript:void(0);" onclick="downloadConsume('\${appid}','\${smonth}')">下载</a>
			 <%--<a href='javascript:void(0);' onclick=downloadConsume('7290fa01293c483fb2709c690f213272','2016-12');>下载</a>--%>
             </td>
		</tr>
	</script>

	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/pagings.js"></script>
	<script type="text/javascript">
		var appInfoArray = {},consumptionStatisticsTable,dataPage,consumptionStatisticsTable_m,dataPage_m;
		var formData = {};

		$(function(){
			$("#reportType").val("1");
			$("#startTime") .val('');
			$("#endTime").val('');
			$("#month").val('');

			//应用列表
			appInfoArray = getAppInfo("<c:url value='/appMgr/getALLSpAppInfo'/>", "appid", "appName");

			dataPage_m = $("#consumptionStatisticsTable_m").page({
				url:"<c:url value='/sipPhoneAppMgr/pageMonthConsumptionStatistics'/>",
				pageSize:30,
				integralBody:"#consumptionStatisticsTbody_m",// 将模版加入到某个元素的ID
				integralTemplate:"#CSTemplate_m",// tmpl中的模版ID
				pagination:"#DSPagination_m", // 分页选项容器
//				param:{"rcdType":"sipprest1"},
				dataRowCallBack:dataRowCallBack_m
			});

			dataPage = $("#consumptionStatisticsTable").page({
				url:"<c:url value='/sipPhoneAppMgr/pageConsumptionStatistics'/>",
				pageSize:30,
				integralBody:"#consumptionStatisticsTbody",// 将模版加入到某个元素的ID
				integralTemplate:"#CSTemplate",// tmpl中的模版ID
				pagination:"#DSPagination", // 分页选项容器
//				param:{"rcdType":"sipprest1"},
				dataRowCallBack:dataRowCallBack
			});

			//报表类型联动时间格式
			$("#reportType").change(function(){
				$("#startTime") .val('');
				$("#endTime").val('');
				$("#month").val('');

				if($(this).val() == 1) {
					$("#date_m").hide();
					$("#date1").show();

					$("#monthReport").hide();
					$("#DSPagination_m").hide();
					$("#dayReport").show();
					$("#DSPagination").show();

				} else {
					$("#date1").hide();
					$("#date_m").show();

					$("#dayReport").hide();
					$("#DSPagination").hide();
					$("#monthReport").show();
					$("#DSPagination_m").show();

				}
				searchCallRecord();
			});
		});

		function dataRowCallBack(row,num) {
			if (row.statDay) {
				if($("#reportType").val()==2) {
					row.statDay = new Date(row.statDay).format("yyyy-MM");
				}else{
					row.statDay = new Date(row.statDay).format("yyyy-MM-dd");
				}
			}

			if(row.recordingFee){
				row.recordingFee = Number(parseFloat(row.recordingFee).toFixed(2));
			}

			if(row.fee){
				row.fee = Number(parseFloat(row.fee).toFixed(2));
				row.totalFee = Number((parseFloat(row.fee)+ parseFloat(row.recordingFee)).toFixed(2));
			}else{
				if(row.recordingFee){
					row.totalFee = Number(parseFloat(row.recordingFee).toFixed(2));
				}else{
					row.totalFee =0;
				}
			}

			if (row.rent) {
				row.rent = Number(parseFloat(row.rent).toFixed(2));
			}
			if (row.costMin) {
				row.costMin = Number(parseFloat(row.costMin).toFixed(2));
			}
			return row;
		}

		function dataRowCallBack_m(row,num) {
			if (row.smonth) {
					row.smonth = new Date(row.smonth).format("yyyy-MM");
			}
			if(row.fee){
				row.fee = Number(parseFloat(row.fee).toFixed(2));

//				var fee_ = 0;
//				var recordingFee_ = 0;
//				var rent_ = 0;
//				var costMin_= 0;
//				if(!(row.fee==""||row.fee==null)){
//					fee_ = row.fee;
//				}
//
//				if(!(row.recordingFee==""||row.recordingFee==null)){
//					recordingFee_ = row.recordingFee;
//				}
//
//				if(!(row.rent==""||row.rent==null)){
//					rent_ = row.rent;
//				}
//
//				if(!(row.costMin==""||row.costMin==null)){
//					costMin_ = row.costMin;
//				}
//
//				row.totalFee = Number((parseFloat(fee_)+ parseFloat(recordingFee_)+ parseFloat(rent_)+ parseFloat(costMin_)).toFixed(2));

			}



			//解决fee为0 recordingFee为0相加后显示空
//			if(row.fee== '0' && row.recordingFee== '0'&& row.rent== '0'&& row.costMin== '0'){
//				row.totalFee =0;
//			}
			if(row.recordingFee){
				row.recordingFee = Number(parseFloat(row.recordingFee).toFixed(2));
			}

			if (row.rent) {
				row.rent = Number(parseFloat(row.rent).toFixed(2));
			}
			if (row.costMin) {
				row.costMin = Number(parseFloat(row.costMin).toFixed(2));
			}

			var fee_ = 0;
			var recordingFee_ = 0;
			var rent_ = 0;
			var costMin_= 0;
			if(row.fee){
				fee_ = row.fee;
			}

			if(row.recordingFee){
				recordingFee_ = row.recordingFee;
			}

			if(row.rent){
				rent_ = row.rent;
			}

			if(row.costMin){
				costMin_ = row.costMin;
			}

			row.totalFee = Number((parseFloat(fee_)+ parseFloat(recordingFee_)+ parseFloat(rent_)+ parseFloat(costMin_)).toFixed(2));
			return row;
		}

		//下载报表
		function exportDataStatistics(){
			var formData = {};

			formData = {params:{"reportType" : $("#reportType").val(),"appId" : $("#appid").val()},startTime:$("#startTime").val(), endTime:$("#endTime").val(),ym:$("#month").val()};
			if($("#reportType").val()==1){
				dataPage.downloadReport("<c:url value='/sipPhoneAppMgr/exportConsumptionStatistics'/>",formData);
			}else if($("#reportType").val()==2){
				dataPage_m.downloadReport("<c:url value='/sipPhoneAppMgr/exportMonthConsumptionStatistics'/>",formData);
			}else{
				return false;
			}
		}

		function downloadConsume(appid,smonth) {
			var formData = {};

			formData = {params:{"smonth" : smonth,"appId" : appid}};

			dataPage_m.downloadReport("<c:url value='/sipPhoneAppMgr/exportMonthConsumptionRecord'/>",formData);

		}

		//查询
		function searchCallRecord(){
			var formData = {};

			formData = {params:{"reportType" : $("#reportType").val(),"appId" : $("#appid").val(),},startTime:$("#startTime").val(), endTime:$("#endTime").val(),ym:$("#month").val()};

			//提示开始时间不能大于结束时间
			if($("#startTime").val() != "" &&$("#endTime").val() != ""){
				var startTime = new Date($("#startTime").val().replace(/\-/g, '\/')).format("yyyy/MM/dd");
				var endTime = new Date($("#endTime").val().replace(/\-/g, '\/')).format("yyyy/MM/dd");
				if(startTime > endTime){
					openMsgDialog("查询时间的开始时间不能大于结束时间！");
					return;
				}
			}
			if($("#reportType").val()==1){
				dataPage.reload(formData);
			}else if($("#reportType").val()==2){
				dataPage_m.reload(formData);
			}

		}

		//关闭消息框
		function closeMsgDialog1(){
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
		<script type="text/javascript">showSubMenu('sphone','yhj','consumptionStatistics');</script>
		<!-- 左侧菜单 end -->
		<div class="main3">

			<div class="container" >
				<div class="msg">
					<h3>消费统计</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>

				<div class="application_msg">
					<form id="searchForm">
					<div class="app_select" >
						<span class="check_font">报表类型：</span>
						<select id="reportType" name="reportType" style="width: 120px;">
							<option value="1">日报</option>
							<option value="2">月报</option>
						</select>

						<span class="" id="date1">
							<span class="check_font">时间：</span>
							<input id="startTime" name="startTime" type="text" class="sm_input_style input-text Wdate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endTime\',{M:-3});}'})"/>
							<span class="check_font">至</span>
							<input id="endTime" name="endTime" type="text" class="sm_input_style input-text Wdate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'startTime\',{M:3});}'})"/>
						</span>

						<span class="check_font call_left" id="date_m" style="display: none;">
							<b class="Bred"></b>
							<span class="check_font">查询月份：</span>
							<input type="hidden" id="maxMonth" value="${currentDate}"/>
							<input type="hidden" id="minMonth" value="${beforeMonth12}"/>
							<%--<input autocomplete="off" type="text" class="sm_input_style" id="month" name="month"
								   onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'maxMonth\')}',minDate:'#F{$dp.$D(\'minMonth\')}',
								   readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false})" style="width:186px;height:28px;"/>--%>
							<input id="month" name="month" type="text" class="input_style input-text Wdate" style="width:168px;" onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\\\'month\\\',{M:-12});}'})"/>
						</span>

						<span class="check_font marLeft50">应用名称：</span>
						<select name="appId" id="appid" style="width:200px;">
							<option value="">全部</option>
						</select>

						<button type="button" class="md_btn f_right" style="margin-top: -5px" onclick="searchCallRecord()">查询</button>

					</div>
					</form>
					<div class="application clear" >
						<div class="record_down f_right">
							<span class="open_down_billed" onclick="exportDataStatistics()"><i class="common_icon download_txt" ></i>导出报表</span>
						</div>

					<!--查询弹层-->
					<div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">
						<div class="modal_head">
							<h4>消费统计导出</h4>
							<div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog1();" class="common_icon close_txt"></a></div>
						</div>
						<div class="modal-common_body mdl_pt_body">
							<div class="test_success test_success_left">
								<i class="attention"></i>
								<span>您暂无数据</span>
							</div>
						</div>
					</div>

					<div class="clear" id="dayReport">
						<table class="table_style" id="consumptionStatisticsTable" >
							<thead>
							<tr>
								<th>报表类型</th>
								<th>时间</th>
								<th>应用名称</th>
								<th>通话总费用(元)</th>
								<th>录音总费用(元)</th>
								<th>合计费用</th>

							</tr>
							</thead>
							<tbody id="consumptionStatisticsTbody">
							</tbody>
						</table>
						<div class="turnPage f_right"  id="DSPagination"></div>
					</div>

						<div id="monthReport" style="display: none">
							<table class="table_style" id="consumptionStatisticsTable_m" >
								<thead>
								<tr>
									<th>报表类型</th>
									<th>时间</th>
									<th>应用名称</th>
									<th>通话总费用(元)</th>
									<th>录音总费用(元)</th>
									<th>号码数量</th>
									<th>号码月租</th>
									<th>低消</th>
									<th>合计费用</th>
									<th style="width:10%">详细消费记录</th>

								</tr>
								</thead>
								<tbody id="consumptionStatisticsTbody_m">
								</tbody>
							</table>

						<div class="turnPage f_right"  id="DSPagination_m" style="display: none"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>
