<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<title>话务调度</title>
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
	<script id="dispatchTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>\${num}</td>
			<td>\${dispatch_name}</td>
			<td>\${areaName}</td>
			<td>\${time_start}</td>
			<td>\${time_end}</td>
			<td>\${weekday}</td>
			<td>\${valid_date}</td>
			<td>\${ccname}</td>
			<td>
				<button type="button" onclick="openAddDispatchDialog('edit','\${dispatch_id}');" class="editDemand">编辑调度</button>
				<button type="button" onclick="todeleteDispatch('\${dispatch_id}','\${dispatch_name}');">删除调度</button>
			</td>
		</tr>
	</script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
	<script type="text/javascript" >

		//声明日期控件
		var ccAreaArray = {},
		validDate = {
			elem: '#validDate',
			format: 'YYYY-MM-DD hh:mm:ss',
			//max: laydate.now(), //设定最大日期为当前日期
			istime: true,
			istoday: true,
			choose: function(datas){
				validDate1.min = datas; //开始日选好后，重置结束日的最小日期
				validDate1.start = datas //将结束日的初始值设定为开始日
			}
		},
		validDate1 = {
			elem: '#validDate1',
			format: 'YYYY-MM-DD hh:mm:ss',
			//max: laydate.now(), //设定最大日期为当前日期
			istime: true,
			istoday: true,
			choose: function(datas){
				validDate.max = datas; //结束日选好后，重置开始日的最大日期
			}
		},
		addValidDate = {
			elem: '#addValidDate',
			format: 'YYYY-MM-DD hh:mm:ss',
			min: laydate.now(), //设定最大日期为当前日期
			istime: true,
			istoday: true
		};

		$(function(){
			//渲染日期控件
			laydate(validDate);laydate(validDate1);laydate(addValidDate);
			ccAreaArray = getCcAreaInfo();

			$("#dispatchTable").page({
				url:"<c:url value='/smartCloud/traffic/dispatch/getDispatchPageList'/>",
				pageSize:30,
				integralBody:"#dispatchTbody",// 将模版加入到某个元素的ID
				integralTemplate:"#dispatchTemplate",// tmpl中的模版ID
				pagination:"#dispatchPagination", // 分页选项容器
				dataRowCallBack:dataRowCallBack
			});

			//智能云调度编辑话务调度全选效果

			$("#checkAll").click(function(){
				if($(this).get(0).checked){
					checkAll();
				}else{
					uncheckAll();
				}
			});
		});
		function dataRowCallBack(row,num) {
			//序号列
			row.num = num;
			if(row.area_id){
				row.areaName = ccAreaArray[row.area_id];
			}
			if(row.weekday){
				var weekdayArray = parseInt(row.weekday).toString(2).split(""),weekdayStr="";
				for(var i=0; i<weekdayArray.length; i++){
					if(weekdayArray[weekdayArray.length-1-i]==1){
						if(i == 0){
							weekdayStr += "周一,";
						}
						if(i == 1){
							weekdayStr += "周二,";
						}
						if(i == 2){
							weekdayStr += "周三,";
						}
						if(i == 3){
							weekdayStr += "周四,";
						}
						if(i == 4){
							weekdayStr += "周五,";
						}
						if(i == 5){
							weekdayStr += "周六,";
						}
						if(i == 6){
							weekdayStr += "周日,";
						}
					}
				}
				row.weekday = weekdayStr.substring(0,weekdayStr.length-1);
			}

			if (row.time_start.toString()) {
				var timeStartPad = padLeft(row.time_start,4);
				row.time_start = timeStartPad.substring(0,2)+"："+timeStartPad.substring(2,4);
			}

			if (row.time_end.toString()) {
				var timeEndPad = padLeft(row.time_end,4);
				row.time_end = timeEndPad.substring(0,2)+"："+timeEndPad.substring(2,4);
			}

			if (row.valid_date) {
				row.valid_date = o.formatDate(row.valid_date, "yyyy-MM-dd hh:mm:ss");
			}
			return row;
		}

		var ccInfoOptionsHtml = "";
		var ccDispatchInfoArray = "";

		//新增或编辑话务调度的对话框
		function openAddDispatchDialog(dialogType,dispatchId){
			if(dialogType == "edit"){
				$("#addEditDispatchDialog").find("h4").text("编辑话务调度");
				$("#editCc").find("input[name='dispatchId']").val(dispatchId);
				$.ajax({
					type:"post",
					url:"<c:url value="/smartCloud/traffic/dispatch/getDispatchInfo"/>",
					dataType:"json",
					async:false,
					data:{dispatchId:dispatchId},
					success : function(data) {
						var timeStartPad = padLeft(data.timeStart,4), timeEndPad = padLeft(data.timeEnd,4);
						data["timeStartHour"] = timeStartPad.substring(0,2);
						data["timeStartMinute"] = timeStartPad.substring(2,4);
						data["timeEndHour"] = timeEndPad.substring(0,2);
						data["timeEndMinute"] = timeEndPad.substring(2,4);
						//console.log(data);
						if(data != null){
							$("#dispatchForm").find(":input[name]").each(function(){
								if($(this).attr("name") != "weekday"){
									$(this).val(data[$(this).attr("name")]);
								}
							});
							var weekdayArray = parseInt(data["weekday"]).toString(2).split("");
							for(var i=0; i<weekdayArray.length; i++){
								if(weekdayArray[weekdayArray.length-1-i]==1){
									$("#dispatchForm").find(":input[name='weekday']").eq(i).prop("checked",true);
								}
							}
						}
					},
					error:function(){
						dispatchTipMsg("showTipMsg","请求失败！");
					}
				});

				$.ajax({
					type:"post",
					url:"<c:url value="/smartCloud/traffic/dispatch/getCcDispatchInfoList"/>",
					dataType:"json",
					async:false,
					data:{dispatchId:dispatchId},
					success : function(result) {
						if(result.code == 0){
							$("#editCallCenterDiv").find(".marTop10").remove();
							$(result.data.ccDispatchInfoList).each(function(i,ccDispatchInfo){
								var optionsHtml = "<option value=''>请选择呼叫中心</option>", buttonHtml = "";
								ccInfoOptionsHtml = "";
								ccDispatchInfoArray = result.data.ccInfoList;
								$(result.data.ccInfoList).each(function(j,ccInfo){
									if(ccDispatchInfo.subid == ccInfo.subid){
										ccInfoOptionsHtml += '<option selected value="'+ ccInfo.subid +'">' + ccInfo.ccname + '</option>';
									}else{
										ccInfoOptionsHtml += '<option value="'+ ccInfo.subid +'">' + ccInfo.ccname + '</option>';
									}
								});

								if(i == parseInt(result.data.ccDispatchInfoList.length-1)){
									buttonHtml = '<span onclick="delCallCenterItem(this);" class="cursorPointer marLeft30"><span class="bRadius delete">X</span><span>删除</span></span>' +
											'<span onclick="addCallCenterItem(this);" class="cursorPointer marLeft15"><span class="bRadius add">+</span><span>添加</span></span>';
								}else{
									buttonHtml = '<span onclick="delCallCenterItem(this);" class="cursorPointer marLeft30"><span class="bRadius delete">X</span><span>删除</span></span>';
								}
								$("#editCallCenterDiv").append('<div class="marTop10">' +
										'呼叫中心<select name="subid" class="marRight20">'+ optionsHtml + ccInfoOptionsHtml +'</select>' +
										'优先级<input type="text" name="pri" value="'+$(this)[0].pri+'" readonly class="sm_input_style" style="width:80px;"/>'+ buttonHtml +'</div>');
							});
						}else{
							ccInfoOptionsHtml = "";
							ccDispatchInfoArray = result.data.ccInfoList;
							$(result.data.ccInfoList).each(function(j,ccInfo){
								ccInfoOptionsHtml += '<option value="'+ ccInfo.subid +'">' + ccInfo.ccname + '</option>';
							});
							$("#editCallCenterDiv").find(".marTop10").remove();
							$("#editCallCenterDiv").append('<div class="marTop10">'
									+ '呼叫中心'
									+ '<select name="subid" class="marRight20">'
									+ ccInfoOptionsHtml
									+ '</select>'
									+ '优先级<input type="text" name="pri" value="1" readonly class="sm_input_style" style="width:80px;"/>'
									+ '<span onclick="delCallCenterItem(this);" class="cursorPointer marLeft30"><span class="bRadius delete">X</span><span>删除</span></span>'
									+ '<span onclick="addCallCenterItem(this);" class="cursorPointer marLeft15"><span class="bRadius add">+</span><span>添加</span></span>'
									+ '</div>');
						}
					},
					error:function(){
						dispatchTipMsg("showCallCenterTipMsg","数据请求失败！");
					}
				});
			}else{
				$("#addEditDispatchDialog").find("h4").text("新增话务调度");

				var startData ={};
				startData["timeStartHour"] = '00';
				startData["timeStartMinute"] = '00';
				startData["timeEndHour"] = '23';
				startData["timeEndMinute"] = '59';
				validDate;
				startData["validDate"] = new Date().format("yyyy-MM-dd hh:mm:ss");
				$("#dispatchForm").find(":input[name='weekday']").prop("checked",true);
				if(startData != null){
					$("#dispatchForm").find(":input[name]").each(function(){
						if($(this).attr("name") != "weekday"){
							$(this).val(startData[$(this).attr("name")]);
						}
					});
				}

				$.ajax({
					type:"post",
					url:"<c:url value="/smartCloud/traffic/dispatch/getCcDispatchInfoList"/>",
					dataType:"json",
					async:false,
					data:{dispatchId:dispatchId},
					success : function(result) {

						ccInfoOptionsHtml = "";
						ccDispatchInfoArray = result.data.ccInfoList;
						$(result.data.ccInfoList).each(function(j,ccInfo){
							ccInfoOptionsHtml += '<option value="'+ ccInfo.subid +'">' + ccInfo.ccname + '</option>';
						});
						$("#editCallCenterDiv").find(".marTop10").remove();
						$("#editCallCenterDiv").append('<div class="marTop10">'
								+ '呼叫中心'
								+ '<select name="subid" class="marRight20">'
								+ ccInfoOptionsHtml
								+ '</select>'
								+ '优先级<input type="text" name="pri" value="1" readonly class="sm_input_style" style="width:80px;"/>'
								+ '<span onclick="delCallCenterItem(this);" class="cursorPointer marLeft30"><span class="bRadius delete">X</span><span>删除</span></span>'
								+ '<span onclick="addCallCenterItem(this);" class="cursorPointer marLeft15"><span class="bRadius add">+</span><span>添加</span></span>'
								+ '</div>');

					},
					error:function(){
						dispatchTipMsg("showCallCenterTipMsg","数据请求失败！");
					}
				});
			}
			$("#shadeWin").show();
			$("#addEditDispatchDialog").show();
		}

		//关闭对话框
		function closeAddDispatchDialog(dialogId){
			$("#shadeWin").hide();
			$("#"+dialogId).find("form :input").each(function(){
				if($(this).attr("type") == "checkbox"){
					$(this).removeAttr("checked");
				}else{
					$(this).val("");
				}
			});
			$("#"+dialogId).hide();
		}

		//提交话务调度
		function submitDispatchForm(){
			var formData = {}, submitFlag = true;

			$("#dispatchForm").find(":input[name]").each(function(){
				if($(this).attr("name") != "weekday"){
					if( $.trim($(this).val())){
						formData[$(this).attr('name')] = $.trim($(this).val());
					}else{
						submitFlag = false;
					}
				}
			});

			var matchDispatchName = /^[\u4E00-\u9FA5-a-zA-Z0-9\(\)]{1,20}$/;
			if(formData.dispatchName == undefined){
				dispatchTipMsg("showTipMsg","调度名称不能为空");
				return;
			}

			if(!matchDispatchName.test(formData.dispatchName)&&formData.dispatchName!=""){
				dispatchTipMsg("showTipMsg","调度名称不合法");
				return;
			}

			if(formData.areaId == undefined){
				dispatchTipMsg("showTipMsg","调度区域不能为空");
				return;
			}

			//日期类型转化
			var weekday = "", weekdayArr = $("#dispatchForm").find(":input[name='weekday']"), weekLength = weekdayArr.length;
			weekdayArr.each(function(i){
				var weekdayElement = weekdayArr.eq(weekLength-i-1);
				if(weekdayElement.is(":checked")){
					weekday += weekdayElement.val();
				}else{
					weekday += "0";
				}
			});

			if(parseInt(weekday,2) != 0){
				formData["weekday"] = parseInt(weekday,2);
			}else{
				dispatchTipMsg("showTipMsg","请选择日期类型！");
				return;
			}

			var matchHour = /^[0-1]\d|2[0-3]$/, matchMinute=/^[0-5]\d|2[0-9]$/;
			if(!matchHour.test(formData.timeStartHour) || !matchMinute.test(formData.timeStartMinute)){
				dispatchTipMsg("showTipMsg","开始时间格式错误！时(00-23) 分(00-59)");
				return;
			}
			formData["timeStart"]=parseInt(formData.timeStartHour+formData.timeStartMinute);

			if(!matchHour.test(formData.timeEndHour) || !matchMinute.test(formData.timeEndMinute)){
				dispatchTipMsg("showTipMsg","结束时间格式错误！时(00-23) 分(00-59)");
				return;
			}
			formData["timeEnd"]=parseInt(formData.timeEndHour+formData.timeEndMinute);

			if(parseInt(formData.timeStart) > parseInt(formData.timeEnd)){
				dispatchTipMsg("showTipMsg","开始时间不能大于结束时间！");
				return;
			}
			if(parseInt(formData.timeStart) == parseInt(formData.timeEnd)){
				dispatchTipMsg("showTipMsg","开始时间不能等于结束时间！");
				return;
			}

			//呼叫中心start
			var dispatchId="", subid="", subidArray=[], pri="", priArray=[], formData1=[], submitFlag1=true;
			//获取元素值
			$("#editCallCenterDiv").find(":input[name]").each(function(){
				if($(this).attr("name") == "subid"){
					subid += $(this).val()+",";
				}else if($(this).attr("name") == "pri"){
					pri += $(this).val()+",";
				}
			});

			if(subid == "" || subid ==","){
				dispatchTipMsg("showTipMsg","呼叫中心不能为空，请先添加呼叫中心！");
				submitFlag1 = false;
				return;
			}

			//截掉字符串最后一个逗号
			subidArray = subid.substring(0,subid.length-1).split(",");
			priArray = pri.substring(0,pri.length-1).split(",");
			//判断重复项以及赋值
			for(var i=0; i<subidArray.length; i++){
				var dispatchInfo={};
				if(subid.replace(subidArray[i]+",","").indexOf(subidArray[i]+",") > -1) {
					dispatchTipMsg("showTipMsg",getCcDispatchInfoArray()[subidArray[i]]+"重复，不能配置重复的呼叫中心！");
					submitFlag1 = false;
					return;
				}
				dispatchInfo["subid"] = subidArray[i];
				dispatchInfo["pri"] = priArray[i];
				formData1.push(dispatchInfo);
			}
			//呼叫中心end

			var submitUrl = "";
			if(formData.dispatchId != "" && formData.dispatchId != undefined){
				formData["dispatchId"] = $("#dispatchId").val();
				submitUrl = "<c:url value="/smartCloud/traffic/dispatch/updateDispatchInfo"/>";
			}else{
				submitFlag = true;
				submitUrl = "<c:url value="/smartCloud/traffic/dispatch/addDispatchInfo"/>";
			}
			//提交调度信息
			if(submitFlag == true){
				$.ajax({
					type:"post",
					url:submitUrl,
					dataType:"json",
					async:false,
					data:{ccStr:JSON.stringify(formData),dispatchListStr: JSON.stringify(formData1)},
					success : function(result) {
						if(result.code == 0){
							dispatchTipMsg("showTipMsg",result.msg);
							closeDiv("addEditDispatchDialog");
							$("#dispatchTable").reload();
						}else if(result.code == 1){
							dispatchTipMsg("showTipMsg",result.msg);
						}else if(result.code == 2){
							dispatchTipMsg("showTipMsg",result.msg);
							var optionsHtml = "";
							$("select[name='subid']").children().remove();
							$.each(result.data,function(i,item){
								optionsHtml += '<option value='+item.subid+'>'+item.ccname+'</option>';
							});
							$("select[name='subid']:eq(0)").html(optionsHtml);
						}else{
							dispatchTipMsg("showTipMsg",result.msg);
						}
					},
					error:function(){
						dispatchTipMsg("showTipMsg","存在错误的数据格式，请求失败！");
					}
				});
			}else{
				dispatchTipMsg("showTipMsg","信息填写不完整，提交失败！");
			}
		}

		function dispatchTipMsg(id,obj){
			$("#"+id).text(obj).show();
			setTimeout(function(){
				$("#"+id).hide();
			},2000);
		}

		//渲染所有下拉框以及返回CcArea键值对数组的信息
		function getCcAreaInfo(){
			var ccAreaArray = {};
			<c:forEach items="${ccAreaList}" var="ccAreaList">
				ccAreaArray["${ccAreaList.areaId}"] = "${ccAreaList.aname}";
				$("#searchAreaId").append("<option value='${ccAreaList.areaId}'>${ccAreaList.aname}</option>");
				$("#addAreaId").append("<option value='${ccAreaList.areaId}'>${ccAreaList.aname}</option>");
			</c:forEach>
			return ccAreaArray;
		}

		//配置添加呼叫中心
		function addCallCenterItem(obj){
			var itemCount = $("#editCallCenterDiv").find(".marTop10").length;
			if(itemCount <= 4){
				var itemHtml = '<div class="marTop10">' +
									'呼叫中心<select name="subid" class="marRight20">'
									+ ccInfoOptionsHtml
									+ '</select>' +
									'优先级<input type="text" name="pri" value="'+ parseInt(itemCount+1) +'" readonly class="sm_input_style" style="width:80px;"/>'+
									'<span onclick="delCallCenterItem(this);" class="cursorPointer marLeft30"><span class="bRadius delete">X</span><span>删除</span></span>' +
									'<span onclick="addCallCenterItem(this);" class="cursorPointer marLeft15"><span class="bRadius add">+</span><span>添加</span></span></div>';
				$("#editCallCenterDiv").append(itemHtml);
				$(obj).remove();
			}else{
				dispatchTipMsg("showTipMsg","只能配置5个呼叫中心！");
			}
		}

		//删除呼叫中心配置项
		function delCallCenterItem(obj){
			var itemCount = $("#editCallCenterDiv").find(".marTop10").length;
			if(itemCount > 1){
				$(obj).parent().remove();
				$("#editCallCenterDiv").find("span[class='cursorPointer marLeft15']").remove();
				$("#editCallCenterDiv").find(".marTop10").last().append('<span onclick="addCallCenterItem(this);" class="cursorPointer marLeft15"><span class="bRadius add">+</span><span>添加</span></span>');
				//重新给优先级赋值
				$("#editCallCenterDiv").find(":input[name='pri']").each(function(i){
					$(this).val(parseInt(i+1));
				});
			}else{
				dispatchTipMsg("showTipMsg","不能删除，至少需要配置一个呼叫中心！");
			}
		}

		var numberid;
		function todeleteDispatch(id,dispatchName){
			$(".delDispatchName").find("span").html(dispatchName);
			$("#shadeWin").show();
			$("#delTemplate").show();
			numberid=id;
		}
		//删除呼叫中心
		function deleteDispatch(){
			var dispatchId = numberid;
			$.ajax({
				type:"post",
				url:"<c:url value="/smartCloud/traffic/dispatch/deleteDispatch"/>",
				dataType:"json",
				async:false,
				data:{dispatchId:dispatchId},
				success : function(result) {
					if(result.code == 0){
						$("#dispatchTable").reload();
						closeAddDispatchDialog('delTemplate');
						openMsgDialog(result.msg);
					}else{
						openMsgDialog("信息删除失败！");
					}
				},
				error:function(){
					openMsgDialog("数据请求失败！");
				}
			});
		}


		//获取呼叫中心键值对数组
		function getCcDispatchInfoArray(){
			var ccDispatchInfoArrays = {};
			$.each(ccDispatchInfoArray, function(i,ccinfo) {
				ccDispatchInfoArrays[ccinfo.subid] = ccinfo.ccname;
			});
			return ccDispatchInfoArrays;
		}

		//查询
		function searchCcDispatch(){
			var formData = {};
			$("#searchForm").find(":input[name]").each(function(){
				formData["params["+$(this).attr("name")+"]"] = $.trim($(this).val());
			});
			//提示开始时间不能大于结束时间
			if(formData["params[validDate]"] != "" && formData["params[validDate1]"] != ""){
				var validDate = new Date(formData["params[validDate]"].replace(/\-/g, '\/'));
				var validDate1 = new Date(formData["params[validDate1]"].replace(/\-/g, '\/'));
				if(validDate > validDate1){
					openMsgDialog("查询时间的开始时间不能大于结束时间！");
					return;
				}
			}
			$("#dispatchTable").reload(formData);
		}

		/** 导出记录 **/
		function downloadCcDispatch() {
			var formData = "";
			$('#searchForm').find(':input[name]').each(function(){
				formData += "&params["+$(this).attr('name')+"]=" + $.trim($(this).val());
			});
			formData = formData.substring(1);
			window.open("<c:url value='/smartCloud/traffic/dispatch/downloadCcDispatch'/>?"+formData);
		}
		function closeDiv(divid){
			$('#' + divid ).hide();
			$('#shadeWin').hide();
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
		<script type="text/javascript">showSubMenu('zn','smart_cloud','dispatch');</script>
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
					<h3>话务调度</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="demand">
					<form id="searchForm">
						<span class="c7_common">调度区域</span>
						<select id="searchAreaId" name="areaId">
							<option value="">全部</option>
						</select>
						<span class="c7_common demand_left">生效时间</span>
						<input type="text" id="validDate" name="validDate" readonly class="sm_input_style"/>
						<span class="check_font">至</span>
						<input type="text" id="validDate1" name="validDate1" readonly class="sm_input_style"/>
						<button type="button" class="md_btn f_right" onclick="searchCcDispatch();" style="margin-top:-5px;">查询</button>
					</form>
				</div>

				<div class="date clear">
					<button type="button" class="addDemand" onclick="openAddDispatchDialog('add','');">新增话务调度</button>
					<div class="record_down f_right" style="margin-top:8px;">
						<span class="open_down_billed" onclick="downloadCcDispatch();"><i class="common_icon download_txt"></i>导出报表</span>
					</div>
					<table class="table_style" id="dispatchTable">
						<thead>
							<tr>
								<th>序号</th>
								<th>调度名称</th>
								<th>调度区域</th>
								<th>开始时间</th>
								<th>结束时间</th>
								<th>日期类型</th>
								<th>生效时间</th>
								<th>呼叫中心</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="dispatchTbody">

						</tbody>
					</table>
					<div class="f_right app_sorter"  id="dispatchPagination"></div>
				</div>
			</div>

			<!--新增编辑话务调度-->
			<div id="addEditDispatchDialog" class="modal-box  phone_t_code" style="display: none;top:40%">
				<div class="modal_head">
					<h4>新增话务调度</h4>
					<div class="p_right"><a href="javascript:void(0);" onclick="closeAddDispatchDialog('addEditDispatchDialog');" class="common_icon close_txt"></a></div>
				</div>
				<div class="modal-common_body mdl_pt_body marLeft28" id="editCc">
					<form id="dispatchForm">
						<input type="hidden" id="dispatchId" name="dispatchId"/>
						<input type="hidden" name="sid" value="${sid}"/>
						<div class="marTop30">
							<div>
								<span class="c7_common">调度名称</span>
								<input type="text" id="dispatchName" name="dispatchName" class="input_style" maxlength="20"/>
								<span class="c7_common call_left">调度区域</span>
								<select id="addAreaId" name="areaId" style="width:180px;">
									<option value="">请选择</option>
								</select>
							</div>

							<div class="dateStatus sch_date marTop30">
								<span class="marRight8" style="letter-spacing:1px;">日期类型</span>
								<input type="checkbox" class="checkAll" id="checkAll"/>
								<label for="checkAll" class="marRight16">全选</label>
								<input type="checkbox" name="weekday" id="Monday" value="1"/><label for="Monday">周一</label>
								<input type="checkbox" name="weekday" id="Tuesday" value="1"/><label for="Tuesday">周二</label>
								<input type="checkbox" name="weekday" id="Wednesday" value="1"/><label for="Wednesday">周三</label>
								<input type="checkbox" name="weekday" id="Thursday" value="1"/><label for="Thursday">周四</label>
								<input type="checkbox" name="weekday" id="Friday" value="1"/><label for="Friday">周五</label>
								<input type="checkbox" name="weekday" id="Saturday" value="1"/><label for="Saturday">周六</label>
								<input type="checkbox" name="weekday" id="Sunday" value="1"/><label for="Sunday">周日</label>
							</div>
							<div class="marTop30">
								<span class="c7_common">开始时间</span>
								<input type="text" class="input_style" style="width: 65px;" id="timeStartHour" name="timeStartHour" value="" maxlength="2"/>
								<span class="c7_common">时</span>
								<input type="text" class="input_style" style="width: 65px;" id="timeStartMinute" name="timeStartMinute" value="" maxlength="2"/>
									<span class="c7_common">分</span>
								<span class="c7_common marLeft15">结束时间</span>
								<input type="text" class="input_style" style="width: 65px;" id="timeEndHour" name="timeEndHour" value="" maxlength="2"/>
										<span class="c7_common">时</span>
								<input type="text" class="input_style" style="width: 65px;" id="timeEndMinute" name="timeEndMinute" value="" maxlength="2"/>
											<span class="c7_common">分</span>
							</div>
							<div class="marTop30">
								<span class="c7_common">生效时间</span>
								<input type="text" id="addValidDate" name="validDate" readonly class="input_style"/>

							</div>
							<p class="marTop20" style="margin-right:30px;border:1px dashed #CECECE;"></p>
							<div id="editCallCenterDiv">
								<p>呼叫中心管理：</p>
								<div class="marTop10">
									<span class="c7_common">呼叫中心</span>
									<span class="c7_common call_left">优先级</span>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div  style="position:relative;z-index:10;margin-left:40px;height: 10px; width: 500px; text-align: center;"><span id="showTipMsg" style="color: red;display: none;"></span></div>
				<div class="mdl_foot" style="margin:30px 0 0 240px;">
					<button type="button" onclick="submitDispatchForm();" class="true">提&nbsp;&nbsp;交</button>
				</div>
			</div>

			<!--删除话务调度-->
			<div class="modal-box  phone_t_code" id="delTemplate" style="display: none;">
				<div class="modal_head">
					<h4>删除话务调度</h4>
					<div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('delTemplate');"></a></div>
				</div>
				<div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
					<div class="app_delete_main">
						<p class="app_delete_common"><i class="attention"></i>该话务调度一旦删除后，将不能恢复</p>
						<p class="app_delete_common">确定要删除该话务调度？</p>
						<p class="delDispatchName">调度名称：<span></span></p>
					</div>
				</div>
				<div class="mdl_foot" style="margin:50px 0 0 150px;">
					<button type="button" class="true" onclick="deleteDispatch()">确&nbsp;&nbsp;认</button>
					<button type="button" class="false" onclick="closeDiv('delTemplate');">取&nbsp;&nbsp;消</button>
				</div>
			</div>
		</div>
	</div>
</section>

</body>
</html>
