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
	<script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
	<![endif]-->
	<script type="text/javascript" src="http://g.alicdn.com/sj/lib/jquery/dist/jquery.min.js"></script>

</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display:none;"></div>
<!--遮罩end-->

<jsp:include page="../common/controlheader.jsp"/>

<section>
	<div id="sec_main">

		<!-- 左侧菜单 start -->
		<jsp:include page="../common/controlleft.jsp"/>
		<script type="text/javascript">showSubMenu('zn','smart_cloud','recentCall');</script>
		<!-- 左侧菜单 end -->

		<div class="main3">

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


			<div class="container11">
				<div class="msg">
					<h3>通话记录</h3>
				</div>
				<div class="records_msg">
					<div class="recordNote">
						<span class="WarmPrompt">温馨提醒：</span>
						<p>1.通话记录仅提供当月数据查询下载，如需要更多数据，请联系客服人员。谢谢~</p>
					</div>
					<form id="searchForm">
						<div class="record_select">
							<div class="record_common re_span_common">

								<span class="zyd_open_width">
									<span class="check_font">起止时间：</span>
									<input type="text" id="closureTime" name="closureTime" class="sm_input_style"/>
									<span class="check_font">至</span>
									<input type="text" id="closureTime1" name="closureTime1" class="sm_input_style"/>
								</span>

								<span class="check_font">呼叫中心：</span>
								<select id="subid" name="subid" class="zyd_open_width">
									<option value="">全部</option>
									<c:forEach items="${callCenter}" var="cc">
										<option value="${cc.subid}">${cc.ccname}</option>
									</c:forEach>
								</select>

								<%--<select id="cc" name="subid" style="width:182px;">--%>
									<%--<option value="">请选择</option>--%>
									<%--<c:forEach items="${cc}" var="cc">--%>
										<%--<option value="${cc.subid}">${cc.ccname}</option>--%>
									<%--</c:forEach>--%>
								<%--</select>--%>

								<span class="check_font">省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：</span>
								<select name="pname" id="pname">
									<option value="">请选择</option>
									<c:forEach items="${provice}" var="provice">
											<option value="${provice.pname}">${provice.pname}</option>
									</c:forEach>
								</select>

							</div>
							<button type="button" onclick="searchZNYCallRecord()" class="md_btn f_right" style="margin-top:-6px;">查询</button>
							<div class="record_common re_span_common">
								<span class="check_font">运营商：</span>
								<select name="operator" id="operator"  style="width:344px;">
									<option value="">请选择</option>
									<option value="00">移动</option>
									<option value="01">联通</option>
									<option value="02">电信</option>
									<option value="06">其他</option>

								</select>

								<span class="check_font">&nbsp;&nbsp;&nbsp;&nbsp;接通状态：</span>
								<select name="connectSucc" id="connectSucc">
									<option value="">请选择</option>
									<option value="1">接通</option>
									<option value="0">未接通</option>
								</select>

								<span class="check_font call_left">&nbsp;&nbsp;呼叫类型：</span>
								<select name="abLine" id="abLine">
									<option value="">请选择</option>
									<option value="I">呼入</option>
									<option value="O">呼出</option>
								</select>
								<div class="marTop30">
									<span class="check_font">主叫号码:</span>
									<input type="text" class="input_style" id="zj" name="zj" style="width:366px;"/>
									<span class="check_font call_left">&nbsp;&nbsp;被叫号码：</span>
									<input type="text" class="input_style" id="bj"  name="bj"  style="width:185px;"/>
								</div>
							</div>
						</div>
					</form>

					<div class="zyd_record_common clear">
						<div class="record_down f_right">
							<span class="open_down_billed" onclick="openAlert('#shadeWin,#app_edit_box');"><i class="common_icon download_txt"></i>导出历史话单</span>
							<span class="open_down_billed marLeft15" onclick="showExportCallCurMonthRecord();">导出本月话单</span>

						</div>
						<table class="table_style" id="znyddCallListTable">
							<thead>
							<%--<tr>--%>
								<%--<th style="width:8%">省份</th>--%>
								<%--<th style="width:8%">城市</th>--%>
								<%--<th style="width:13%">呼叫中心</th>--%>
								<%--<th style="width:10%">主叫号码</th>--%>
								<%--<th style="width:10%">被叫号码</th>--%>
								<%--<th style="width:6%">呼叫类型</th>--%>
								<%--<th style="width:8%">所属运营商</th>--%>
								<%--<th style="width:16%">开始时间</th>--%>
								<%--<th style="width:16%">结束时间</th>--%>
								<%--<th style="width:16%">通话时长</th>--%>
								<%--<th style="width:10%">接通状态</th>--%>
								<%--<th style="width:11%">费用</th>--%>
							<%--</tr>--%>
							<tr>
								<th style="width: 34px;">序号</th>
								<th>省份</th>
								<th>城市</th>
								<th>呼叫中心名称</th>
								<th>呼叫类型</th>
								<th>运营商</th>
								<th>主叫号码</th>
								<th>被叫号码</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>通话状态</th>
								<th>通话费用</th>
							</tr>
							</thead>
							<tbody id="znyddIntegralBody">
							</tbody>
						</table>
						<div class="f_right app_sorter"  id="znyddpagination" ></div>
					</div>
				</div>

				<!--下载历史话单弹层-->
				<div class="modal-box  phone_t_code" id="app_edit_box" style="display:none;">
					<div class="modal_head">
						<h4>下载历史话单</h4>
						<div class="p_right"><a href="javascript:void(0);" onclick="closeAlert('#shadeWin,#app_edit_box');" class="common_icon close_txt"></a></div>
					</div>
					<div class="modal-common_body mdl_pt_body">
						<div class="download_billed">
							<div class="download_time_left f_left">
								<c:forEach items="${months}" var="m" varStatus="status">
									<c:if test="${m[1] < 10}">
										<div class="down_common"><span>${m[0]}年&nbsp;&nbsp;${m[1]}月</span>下载</div>
									</c:if>
									<c:if test="${m[1] >= 10}">
										<div class="down_common"><span>${m[0]}年${m[1]}月</span>下载</div>
									</c:if>
									<c:if test="${status.index == 5}">
										</div><div class="download_time_right f_right">
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-box  phone_t_code" id="dataModel" style="height:360px;display: none" >

				</div>
			</div>

		</div>
	</div>
</section>

<script id="znyydIntegralTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td>\${num}</td>
		<td>\${pname}</td>
		<td>\${cname}</td>
		<td>\${ccname}</td>
		<td>\${abLine}</td>
		<td>\${operator}</td>
		<td>\${zj}</td>
		<td>\${bj}</td>
		<td>\${qssj}</td>
		<td>\${jssj}</td>
		<td>\${connectSucc}</td>
		<td>\${fee}</td>
	</tr>
</script>

<script type="text/javascript" src="http://g.alicdn.com/sj/dpl/1.5.1/js/sui.min.js"></script>
<script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
<script src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/pagings.js"></script>


<script>
	//日通话记录下载参数
	//feeid
	var feeid = '${ sessionScope.userInfo.feeid}'
	//业务路径
	var busPath = "/ccenter/";
	var date = new Date(),subInfoArray = {},subInfoArray2 = {};
	date.setMonth(date.getMonth()-3);
	var beforeMonth = date.format("yyyy-MM-dd");
	var closureTime = {
		elem: '#closureTime',
		format: 'YYYY-MM-DD hh:mm:ss',
		max: laydate.now(), //设定最大日期为当前日期
		min: beforeMonth, //最小日期
		istime: true,
		istoday: true,
		choose: function(data){
			//日期限制跨度30天
			var date = new Date(data);
			date.setDate(date.getDate()+30);
			var strAfterDay = date.format("yyyy-MM-dd");
			if(new Date(strAfterDay) >= new Date(laydate.now("yyyy-MM-dd"))){
				closureTime1.max = laydate.now("yyyy-MM-dd");
			}else{
				closureTime1.max = strAfterDay;
			}
			closureTime1.min = data; //开始日选好后，重置结束日的最小日期
			closureTime1.start = data //将结束日的初始值设定为开始日
		}
	};

	var closureTime1 = {
		elem: '#closureTime1',
		format: 'YYYY-MM-DD hh:mm:ss',
		max: laydate.now(), //设定最大日期为当前日期
		min: beforeMonth, //最小日期
		istime: true,
		istoday: true,
		choose: function(data){
			closureTime.max = data; //结束日选好后，重置开始日的最大日期
			closureTime.start = data;
			//日期限制跨度30天
			var date = new Date(data);
			date.setDate(date.getDate()-30);
			var strBeforeDay = date.format("yyyy-MM-dd");
			if(new Date(strBeforeDay) <= new Date(beforeMonth)){
				closureTime.min = beforeMonth;
			}else{
				closureTime.min = strBeforeDay;
			}
		}
	};

	var table = "";

	$(function(){
		laydate(closureTime);
		laydate(closureTime1);
		<%--subInfoArray = getAppInfo("<c:url value='/znydd/bill/recentCall/getAllCcInfo'/>","cc","ccname");--%>

		subInfoArray2 = getDataList("<c:url value='/znydd/bill/recentCall/getAllCcInfo1'/>","subid","ccname");
		table = $("#znyddCallListTable").page({
			url:"<c:url value='/znydd/bill/recentCall/getZnyCallRecordList'/>",
			pageSize:30,
			integralBody:"#znyddIntegralBody",// 将模版加入到某个元素的ID
			integralTemplate:"#znyydIntegralTemplate",// tmpl中的模版ID
			pagination:"#znyddpagination", // 分页选项容器
			dataRowCallBack:dataRowCallBack
		});
		getMonthFile();
		downloadReport('${currTime}', 'dataModel',busPath+feeid,'<c:url value="/"/>');
	});


//	function search(){
//		table.reload({
//			params : {
//				conn : $("#conn").val(),
//				operator : $("#operator").val(),
//				callType : $("#callType").val(),
//				area : $("#area").val(),
//				callCenter : $("#callCenter").val()
//			},
//			sTime : $("#sTime").val(),
//			eTime : $("#eTime").val()
//		});
//	}

	//查询
	function searchZNYCallRecord(){
		var formData = {};
		$("#searchForm").find(":input[id]").each(function(i){
			formData[$(this).attr("id")] = $.trim($(this).val());
		});
		//提示开始时间不能大于结束时间
		if(formData['closureTime'] != "" && formData['closureTime1'] != ""){
			var closureTime = new Date(formData['closureTime'].replace(/\-/g, '\/'));
			var closureTime1 = new Date(formData['closureTime1'].replace(/\-/g, '\/'));
			if(closureTime > closureTime1){
				openMsgDialog("查询时间的开始时间不能大于结束时间！");
				return;
			}
		}else{
			openMsgDialog("请输入查询时间！");
			return;
		}
		table.reload(formData);
	}


	function dataRowCallBack(row,num){
		//序号列
		row.num = num;
		if(row.subid){
			row.ccname = subInfoArray2[row.subid];
		}
		if (row.operator == "02") {
			row.operator = "电信"
		} else if (row.operator == "01") {
			row.operator = "联通"
		} else if (row.operator == "00"){
			row.operator = "移动"
		}else if (row.operator == "06"){
			row.operator = "其他"
		}else{
            row.operator = "其他"
        }

		if (row.abLine == "I") {
			row.abLine = "呼入";
		} else if (row.abLine == "O") {
			row.abLine = "呼出";
		}

		if(row.closureTime){
			row.closureTime =  o.formatDate(row.closureTime, "yyyy-MM-dd");
		}
		if (row.qssj) {
			row.qssj = o.formatDate(row.qssj, "yyyy-MM-dd hh:mm:ss");
		}
		if (row.jssj) {
			row.jssj = o.formatDate(row.jssj, "yyyy-MM-dd hh:mm:ss");
		}
		if (row.thsc) {
			row.thsc = row.thsc+"s";
		}
		//接通状态
		if(row.connectSucc == 1){
			row.connectSucc = "接通";
		}else if(row.connectSucc == 0){
			row.connectSucc = "未接通";
		}
		return row;
	}


	function getMonthFile(){
		$.ajax({
			type:"post",
			url:"<c:url value="/monthFile/getStafMonthFileList"/>",
			dataType:"json",
			async:false,
			data:{"stype":"04"},
			success : function(data) {
				if(data.length > 0) {
					var month = monthArray("${currentDate}");
					$(".down_common").each(function (i) {
						var elements = "";
						$(data).each(function () {
							if (month[i] == o.formatDate($(this)[0].sdate, "yyyy年MM月")) {
								elements = "<span>" + month[i] + "</span><a href='<c:url value='/monthFile/monthFileDownload'/>?file=" + $(this)[0].fname + "'>下载</a>";
							}
						})
						$(this).html(elements);
					})
				}else{
					$(".download_billed").html("<div class='download_msg'><i class='attention'></i><span>您暂无历史话单<span></div>");
				}
			},
			error:function(){
				console.log("数据请求失败！");
			}
		});
	}

	function closeAlert(fid) {
		$(fid).hide();
	}

	function openAlert(fid) {
		$(fid).show();
	}

</script>
</body>
</html>

