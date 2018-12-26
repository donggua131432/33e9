<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<title>控制中心</title>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui.min.css" />
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
	<!--[if lt IE 9]-->
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
	<!--[endif]-->
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/sui.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/My97DatePicker/WdatePicker.js"></script>
	<%--<td {{if del }}style="text-decoration:line-through"{{/if}}>\${ccname}</td>--%>
	<script id="trafficStatTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>\${day}</td>
			<td>\${pname}</td>
			<td>\${ccname}</td>
			<td>\${operator}</td>
			<td>\${abline}</td>
			<td>\${succcnt}</td>
			<td>\${thscsum}</td>
			<td>\${jfscsum}</td>
		</tr>
	</script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
	<script type="text/javascript" >
		//声明日期控件
		var date = new Date();
		date.setMonth(date.getMonth()-12);
		var beforeMonth12 = date.format("yyyy-MM-dd");
		var ccInfoArray ={}, operatorArray={}, stathour = {
			elem: '#stathour',
			format: 'YYYY-MM-DD hh:mm:ss',
			min: beforeMonth12,
			max: laydate.now(), //设定最大日期为当前日期
			istime: true,
			istoday: true,
			choose: function(datas){
				stathour1.min = datas; //开始日选好后，重置结束日的最小日期
				stathour1.start = datas; //将结束日的初始值设定为开始日
			}
		},
		stathour1 = {
			elem: '#stathour1',
			format: 'YYYY-MM-DD hh:mm:ss',
			min: beforeMonth12,
			max: laydate.now(), //设定最大日期为当前日期
			istime: true,
			istoday: true,
			choose: function(datas){
				stathour.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};

		$(function(){
			//渲染日期控件
			laydate(stathour);laydate(stathour1);
			getCcInfoArray();
			ccInfoArray = getDatas("<c:url value='/znydd/bill/recentCall/getAllCallCenterInfoWithDelete'/>");
			operatorArray = getOperatorArray();
			$("#trafficStatTable").page({
				url:"<c:url value='/smartCloud/traffic/stat/pageStatCcRecordList'/>",
				pageSize:30,
				integralBody:"#trafficStatTbody",// 将模版加入到某个元素的ID
				integralTemplate:"#trafficStatTemplate",// tmpl中的模版ID
				pagination:"#trafficStatPagination", // 分页选项容器
				dataRowCallBack:dataRowCallBack
			});
		});

		function dataRowCallBack(row,num) {
			//序号列
			row.num = num;
			if(row.area_id){
				row.areaName = ccAreaArray[row.area_id];
			}

			if(row.abline){
				if(row.abline=="I"){
					row.abline = "呼入";
				} else if(row.abline=="O"){
					row.abline = "呼出";
				}
			}

			if(row.subid){
				$.each(ccInfoArray, function(i,n){
					if (n.subid == row.subid){
						row.ccname = n.ccname;
						row.del = n.status != '00'

					}
				});
			}

			if(row.operator){
				row.operator = operatorArray[row.operator];
			} else {
				row.operator = "";
			}

			if($("#reportType").val() == "1" && row.stathour){
				row.day = o.formatDate(row.stathour, "yyyy-MM-dd hh:mm:ss");
			}

			if($("#reportType").val() == "2" && row.statday){
				row.day = row.statday;
			}

			if($("#reportType").val() == "3" && row.statday){
				row.day = row.statday;
			}
			return row;
		}

		function selectReportType(obj){
			var reportType = $(obj).val();
			if(reportType == "3"){
				$("#stathourSpan").hide();
				$("#statdaySpan").show();
			}else{
				$("#stathourSpan").show();
				$("#statdaySpan").hide();
			}
			$("#stathour,#stathour1,#statday").val("");
			var formData = {};
			$("#searchForm").find(":input[name]").each(function(){
				formData["params["+$(this).attr("name")+"]"] = $.trim($(this).val());
			});
			$("#trafficStatTable").reload(formData);
		}

		//获取呼叫中心键值对数组
		function getCcInfoArray(){
			var ccInfoArray = {};
			<c:forEach items="${ccInfoList}" var="ccInfo">
				$("#subid").append('<option value="${ccInfo.subid}">${ccInfo.ccname}</option>');
			    ccInfoArray["${ccInfo.subid}"] = "${ccInfo.ccname}";
			</c:forEach>
			return ccInfoArray;
		}

		//获取统计方式键值对数组
		function getOperatorArray(){
			var operatorArray = {};
			<c:forEach items="${telnoOperatorList}" var="telnoOperator">
				$("#operator").append('<option value="${telnoOperator.ocode}">${telnoOperator.oname}</option>');
				operatorArray["${telnoOperator.ocode}"] = "${telnoOperator.oname}";
			</c:forEach>
			return operatorArray;
		}

		//查询
		function searchTrafficStat(){
			var formData = {}, reportType =  $("#reportType").val();
			$("#searchForm").find(":input[name]").each(function(){
				formData["params["+$(this).attr("name")+"]"] = $.trim($(this).val());
			});

			//提示开始时间不能大于结束时间
			if(reportType == 1 || reportType == 2){
				if(formData["params[stathour]"] != "" && formData["params[stathour1]"] != ""){
					var stathour = new Date(formData["params[stathour]"].replace(/\-/g, '\/')),
						stathour1 = new Date(formData["params[stathour1]"].replace(/\-/g, '\/')),
						subTime = stathour1.getTime()-stathour.getTime();
					if(stathour > stathour1){
						openMsgDialog("查询时间的开始时间不能大于结束时间！");
						return;
					}else{
						if(reportType == 1){
							var hours=Math.floor(subTime/(3600*1000)); //计算小时数
							if(hours > 24){
								openMsgDialog("小时查询不能超过24小时！");
								return;
							}
						}

						if(reportType == 2){
							var days=Math.floor(subTime/(24*3600*1000)); //计算天数
							if(days > 30){
								openMsgDialog("日查询不能超过30天！");
								return;
							}
						}
					}
				}else{
					openMsgDialog("请输入起止时间进行查询！");
					return;
				}
			}

			if(reportType == 3 && formData["params[statday]"] == ""){
				openMsgDialog("请输入月份进行查询！");
				return;
			}

			if(reportType == 2){
				formData["params[statday]"] = formData["params[stathour]"];
				formData["params[statday1]"] = formData["params[stathour1]"];
			}
			$("#trafficStatTable").reload(formData);
		}

		/** 导出记录 **/
		function downloadTrafficStat() {
			var formData = "", reportType = $("#reportType").val();
			$('#searchForm').find(':input[name]').each(function(){
				if($(this).attr('name') != "statday"){
					formData += "&params["+$(this).attr('name')+"]=" + $.trim($(this).val());
				}
				//formData += "&params["+$(this).attr('name')+"]=" + $.trim($(this).val());
			});
			if(reportType == 2){
				formData += "&params[statday]=" + $.trim($("#stathour").val());
				formData += "&params[statday1]=" + $.trim($("#stathour1").val());
			}
			if(reportType == 3){
				formData += "&params[statday]=" + $.trim($("#statday").val());
			}
			formData = formData.substring(1);
			window.open("<c:url value='/smartCloud/traffic/stat/downloadCcRecordReport'/>?"+formData);
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
		<script type="text/javascript">showSubMenu('zn','smart_cloud','stat');</script>
		<!-- 左侧菜单 end -->
		<div class="main3">
			<!--消息显示-->
			<div class="modal-box  phone_t_code" id="showMsgDialog" style="display: none;">
				<div class="modal_head">
					<h4>消息提示</h4>
					<div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
				</div>
				<div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
					<div class="app_delete_main" style="height: 50px; padding: 20px;">
						<p class="app_delete_common"><i class="attention"></i><span id="alertMsg"></span></p>
					</div>
				</div>
				<div class="mdl_foot" style="margin-left:220px;">
					<button type="button" onclick="closeMsgDialog();" class="true">确&nbsp;&nbsp;定</button>
				</div>
			</div>

			<div class="container7">
				<div class="msg">
					<h3>话务统计报表</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<form id="searchForm">
					<div class="demand">
						<span class="c7_common">报表类型</span>
						<select id="reportType" name="reportType" onchange="selectReportType(this);">
							<option value="1">小时</option>
							<option value="2">日</option>
							<option value="3">月</option>
						</select>
						<span class="zyd_open_width call_left" id="stathourSpan">
							<span class="c7_common">起止时间</span>
							<input type="text" id="stathour" name="stathour" readonly class="sm_input_style" style="width: 205px;"/>
							<span class="check_font">至</span>
							<input type="text" id="stathour1" name="stathour1" readonly class="sm_input_style" style="width: 205px;"/>
						</span>
						<span class="zyd_open_width call_left" id="statdaySpan" style="display: none">
							<span class="c7_common">查询月份</span>
							<input type="hidden" id="maxMonth" value="${currentDate}"/>
							<input type="hidden" id="minMonth" value="${beforeMonth12}"/>
							<input autocomplete="off" type="text" class="sm_input_style" id="statday" name="statday"
								   onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'maxMonth\')}',minDate:'#F{$dp.$D(\'minMonth\')}',
								   readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false})" style="width:186px;height:28px;"/>
						</span>
						<button type="button" class="md_btn f_right" onclick="searchTrafficStat();" style="margin-top:-5px;">查询</button>
					</div>
					<div style="margin:-0.5em 0em 0em 1.2em">
						<span class=c7_common>呼叫类型</span>
						<select id="abline" name="abline">
							<option value="">全部</option>
							<option value="I">呼入</option>
							<option value="O">呼出</option>
						</select>
						<span class="c7_common call_left">呼叫中心</span>
						<select id="subid" name="subid">
							<option value="">全部</option>
						</select>
						<span class="c7_common call_left">运营商</span>
						<select id="operator" name="operator">
							<option value="">全部</option>
						</select>
						<div class="record_down f_right" style="margin-top:8px;">
							<span class="open_down_billed" onclick="downloadTrafficStat();"><i class="common_icon download_txt"></i>导出报表</span>
						</div>
					</div>
				</form>
				<div class="date clear">
					<table class="table_style  record_table re_a_color clear" id="trafficStatTable">
						<thead>
							<tr>
								<th rowspan="2" width="15%">日期</th>
								<th rowspan="2" width="8%">省份</th>
								<th rowspan="2" width="20%">呼叫中心</th>
								<th rowspan="2" width="12%">运营商</th>
								<th rowspan="2" width="10%">呼叫类型</th>
								<th colspan="3" width="35%">通话明细</th>
							</tr>
							<tr>
								<th width="8%">接通数</th>
								<th width="12%">总通话时长(秒)</th>
								<th width="20%">计费通话时长(分钟)</th>
							</tr>
						</thead>
						<tbody id="trafficStatTbody">

						</tbody>
					</table>
					<div class="f_right app_sorter"  id="trafficStatPagination"></div>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>
