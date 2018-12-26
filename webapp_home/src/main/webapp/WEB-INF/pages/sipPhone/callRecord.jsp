<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<title>通话记录</title>
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
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/pagings.js"></script>

	<script id="huiboTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${appid}</td>
            <td style="word-break:break-all;">\${callSid}</td>
            <td>\${rcdType}</td>
            <td>\${abLine}</td>
            <td>\${zj}</td>
            <td>\${bj}</td>
			<td>\${display}</td>
            <td>\${qssj}</td>
            <td>\${jssj}</td>
            <td>\${connectSucc}</td>
            <td>\${thsc}</td>
            <td>\${fee}</td>
            <td>\${recordingFee}</td>
            <td>
                {{if urlStatus=='0'}}
                     <a href="\${url}" download>点击下载</a>
                {{/if}}
                {{if urlStatus=='1'}}
                     <span class="disableCss">文件已过期</span>
                {{/if}}
                {{if urlStatus=='2'}}
                     <span class="disableCss">无录音文件</span>
                {{/if}}
            </td>
        </tr>
    </script>
	<script id="zhiboTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${appid}</td>
            <td style="word-break:break-all;">\${callSid}</td>
            <td>\${rcdType}</td>
            <td>\${zj}</td>
            <td>\${bj}</td>
			<td>\${display}</td>
            <td>\${qssj}</td>
            <td>\${jssj}</td>
            <td>\${connectSucc}</td>
            <td>\${thsc}</td>
            <td>\${fee}</td>
            <td>\${recordingFee}</td>
            <td>
                {{if urlStatus=='0'}}
                     <a href="\${url}" download>点击下载</a>
                {{/if}}
                {{if urlStatus=='1'}}
                     <span class="disableCss">文件已过期</span>
                {{/if}}
                {{if urlStatus=='2'}}
                     <span class="disableCss">无录音文件</span>
                {{/if}}
            </td>
        </tr>
    </script>
	<script id="huihuTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${appid}</td>
            <td style="word-break:break-all;">\${callSid}</td>
            <td>\${rcdType}</td>
            <td>\${zj}</td>
            <td>\${bj}</td>
			<td>\${display}</td>
            <td>\${qssj}</td>
            <td>\${jssj}</td>
            <td>\${connectSucc}</td>
            <td>\${thsc}</td>
            <td>\${fee}</td>
            <td>\${recordingFee}</td>
            <td>
                {{if urlStatus=='0'}}
                     <a href="\${url}" download>点击下载</a>
                {{/if}}
                {{if urlStatus=='1'}}
                     <span class="disableCss">文件已过期</span>
                {{/if}}
                {{if urlStatus=='2'}}
                     <span class="disableCss">无录音文件</span>
                {{/if}}
            </td>
        </tr>
    </script>

	<script type="text/javascript">
		var appInfoArray = {}/*, appSubInfoArray={}*/;
		var huiboPage ={},zhiboPage ={},huihuPage ={};
		//日通话记录下载参数
		//feeid
		var feeid = '${ sessionScope.userInfo.feeid}';
		//业务路径
		var busPath = "sipphone/sipprest/";
		var type="sipprest";


		$(function(){
			//通话记录列表
			appInfoArray = getAppInfo("<c:url value='/appMgr/getALLSpAppInfo'/>","appid","appName");
			/*appSubInfoArray = getAppInfo("<c:url value='/appMgr/getAppInfoUnionSubApp'/>","subid","subName");*/


			huiboPage = $("#huiboTable").page({
				url:"<c:url value='/sipPhoneAppMgr/getCallRecordList'/>",
				pageSize:30,
				integralBody:"#huiboTbody",// 将模版加入到某个元素的ID
				integralTemplate:"#huiboTemplate",// tmpl中的模版ID
				pagination:"#huiboPagination", // 分页选项容器
				param:{"rcdType":"sipprest"},//rcdType;呼叫类型(回拨 : SIPPREST, 直拨 : SIPPOUT, 回呼 : SIPPIN)
				dataRowCallBack:dataRowCallBack
			});

			zhiboPage = $("#zhiboTable").page({
				url:"<c:url value='/sipPhoneAppMgr/getCallRecordList'/>",
				pageSize:30,
				integralBody:"#zhiboTbody",// 将模版加入到某个元素的ID
				integralTemplate:"#zhiboTemplate",// tmpl中的模版ID
				pagination:"#zhiboPagination", // 分页选项容器
				param:{"rcdType":"sippout"},//rcdType;呼叫类型(回拨 : SIPPREST, 直拨 : SIPPOUT, 回呼 : SIPPIN)
				dataRowCallBack:dataRowCallBack
			});

			huihuPage = $("#huihuTable").page({
				url:"<c:url value='/sipPhoneAppMgr/getCallRecordList'/>",
				pageSize:30,
				integralBody:"#huihuTbody",// 将模版加入到某个元素的ID
				integralTemplate:"#huihuTemplate",// tmpl中的模版ID
				pagination:"#huihuPagination", // 分页选项容器
				param:{"rcdType":"sippin"},//rcdType;呼叫类型(回拨 : SIPPREST, 直拨 : SIPPOUT, 回呼 : SIPPIN)
				dataRowCallBack:dataRowCallBack
			});


			getMonthFile();

			switchRefress();

			laydate(closureTime);
			laydate(closureTime1);

			downloadReport('${currTime}', 'dataModel', busPath+feeid, '<c:url value="/"/>');
		});

		var date = new Date();
		date.setMonth(date.getMonth()-3);
		var beforThreeMonth = date.format("yyyy-MM-dd");

		var closureTime = {
			elem: '#closureTime',
			format: 'YYYY-MM-DD hh:mm:ss',
			max: laydate.now(), //设定最大日期为当前日期
			min: beforThreeMonth, //最小日期
			istime: true,
			istoday: true,
			choose: function(datas){
				closureTime1.min = datas; //开始日选好后，重置结束日的最小日期
				closureTime1.start = datas; //将结束日的初始值设定为开始日
			}
		};

		var closureTime1 = {
			elem: '#closureTime1',
			format: 'YYYY-MM-DD hh:mm:ss',
			max: laydate.now(), //设定最大日期为当前日期
			min: beforThreeMonth, //最小日期
			istime: true,
			istoday: true,
			choose: function(datas){
				closureTime.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};

		//导出数据提示框
		function showExportCallRecord1() {
			$("#shadeWin").show();
			if("sipprest"==type){
				$("#re_download_billed").show();
				$("#re_download_billed1").hide();
				$("#re_download_billed2").hide();
			}
			if("sippout"==type){
				$("#re_download_billed").hide();
				$("#re_download_billed1").show();
				$("#re_download_billed2").hide();
			}
			if("sippin"==type){
				$("#re_download_billed").hide();
				$("#re_download_billed1").hide();
				$("#re_download_billed2").show();
			}
		}

		function closeExportCallRecord1() {
			$("#shadeWin").hide();
			$("#re_download_billed").hide();
			$("#re_download_billed1").hide();
			$("#re_download_billed2").hide();
		}

		function getMonthFile(){
			$.ajax({
				type:"post",
				url:"<c:url value="/monthFile/getStafMonthFileList"/>",
				dataType:"json",
				async:false,
				data:{"stype":"06"},
				success : function(data) {
					var elementsStr = "";
					var elementsStr1 = "";
					var elementsStr2 = "";
					if(data.length > 0) {
						var month = monthArray(new Date().format("yyyy年MM月"));

						$(".down_common").each(function (i) {
							var elements = "";

							$(data).each(function () {
								if($(this)[0].fname.indexOf("sipprest") >= 0){
									if (month[i] == o.formatDate($(this)[0].sdate, "yyyy年MM月")) {
										elements = "<span>" + month[i] + "</span><a href='<c:url value='/monthFile/monthFileDownload'/>?file=" + $(this)[0].fname + "'>下载</a>";
										elementsStr += month[i];
									}
								}
							});
							$(this).html(elements);
						})
						if(elementsStr.length == 0){
							$(".download_billed").html("<div class='download_msg'><i class='attention'></i><span>您暂无历史话单<span></div>");
						}

						$(".down_common1").each(function (i) {
							var elements1 = "";

							$(data).each(function () {
								if($(this)[0].fname.indexOf("sippout") >= 0){
									if (month[i] == o.formatDate($(this)[0].sdate, "yyyy年MM月")) {
										elements1 = "<span>" + month[i] + "</span><a href='<c:url value='/monthFile/monthFileDownload'/>?file=" + $(this)[0].fname + "'>下载</a>";
										elementsStr1 += month[i];
									}
								}
							});
							$(this).html(elements1);
						})
						if(elementsStr1.length == 0){
							$(".download_billed1").html("<div class='download_msg'><i class='attention'></i><span>您暂无历史话单<span></div>");
						}

						$(".down_common2").each(function (i) {
							var elements2 = "";

							$(data).each(function () {
//
								if($(this)[0].fname.indexOf("sippin") >= 0){
									if (month[i] == o.formatDate($(this)[0].sdate, "yyyy年MM月")) {
										elements2 = "<span>" + month[i] + "</span><a href='<c:url value='/monthFile/monthFileDownload'/>?file=" + $(this)[0].fname + "'>下载</a>";
										elementsStr2 += month[i];
									}
								}
							});
							$(this).html(elements2);
						})
						if(elementsStr2.length == 0){
							$(".download_billed2").html("<div class='download_msg'><i class='attention'></i><span>您暂无历史话单<span></div>");
						}
					}else{
						$(".download_billed").html("<div class='download_msg'><i class='attention'></i><span>您暂无历史话单<span></div>");
					}
				},
				error:function(){
					console.log("数据请求失败！");
				}
			});
		}

		function dataRowCallBack(row,num) {
			//序号列
			row.num = num;
			if(row.appid){
				row.appName = appInfoArray[row.appid];
//				alert(row.appid+"____________"+row.appName);
			}

			/*if(row.subid){
			 row.subName = appSubInfoArray[row.subid];
			 }*/

			if (row.qssj) {
				row.qssj = o.formatDate(row.qssj, "yyyy-MM-dd hh:mm:ss");
			}
			if (row.jssj) {
				row.jssj = o.formatDate(row.jssj, "yyyy-MM-dd hh:mm:ss");
			}
			//接通状态
			if(row.connectSucc == 1){
				row.connectSucc = "接通";
			}else if(row.connectSucc == 0){
				row.connectSucc = "未接通";
			}
			if(row.url){
				var currentDate = new Date().format("yyyy-MM-dd"), days=0;
				//获取时间差
				if(row.jssj){
					days = getDateDiff(row.jssj.substr(0,10),currentDate);
					if(days <= 30){

						if(row.thsc > 0 &&(( row.abLine == "A" && row.rcdLine == 'A') ||
								(row.abLine == "B" &&  row.rcdLine == 'B')||
								row.abLine == "C" && row.rcdLine != '0')){
							row.url = "${appConfig.nasDownLoadPath}"+row.url+"?auth=${auth}";
							row.urlStatus=0;
						}
						else{
							row.urlStatus=2;
						}
					}else{
						row.urlStatus=1;
					}
				}else{
					//days = 31;
					row.urlStatus=2;
				}
			}else{
				row.urlStatus=2;
			}
			// 呼叫类型(回拨 : SIPPREST, 直拨 : SIPPOUT, 回呼 : SIPPIN)
			if(row.rcdType == "sipprest"){
				row.rcdType = "回拨";
			}else if(row.rcdType == "sippout"){
				row.rcdType = "直拨";
			}else if(row.rcdType == "sippin"){
				row.rcdType = "回呼";
			}

			if(row.abLine == "A"){
				row.abLine = "A路";
			}else if(row.abLine == "B"){
				row.abLine = "B路";
			}else if(row.abLine == "C"){
				row.abLine = "回呼";
			}
			if (row.thsc) {
				row.thsc = row.thsc+"s";
			}
			return row;
		}


		//查询
		function searchCallRecord() {
			var formData = {};
			//提示开始时间不能大于结束时间
			if($("#closureTime").val() != "" &&$("#closureTime1").val() != ""){
				var closureTime = new Date($("#closureTime").val().replace(/\-/g, '\/'));
				var closureTime1 = new Date($("#closureTime1").val().replace(/\-/g, '\/'));
				if(closureTime > closureTime1){
					openMsgDialog("查询时间的开始时间不能大于结束时间！");
					return;
				}
			}else{
				openMsgDialog("请输入查询时间！");
				return;
			}
			if($("#appid").val() == ""){
				openMsgDialog("请选择应用名称！");
				return;
			}


			/*if(params['thsc'] != "" && params['thsc1'] != ""){
			 if(params['thsc'] > params['thsc1']) {
			 openMsgDialog("通话时长的最小通话时长不能大</br></br>于最大通话时长！");
			 return;
			 }
			 }*/

			// 呼叫类型(回拨 : SIPPREST, 直拨 : SIPPOUT, 回呼 : SIPPIN)
			if ($(".tabListBg").text() == "回拨") {
				formData = {params:{"connectSucc" : $("#connectSucc").val().trim(),"appid" : $("#appid").val().trim(),"callSid" : $("#callSid").val().trim(),"zj" : $("#zj").val().trim(),"bj" : $("#bj").val().trim(),"rcdType" : "sipprest"},
					sTime:$("#closureTime").val(), eTime:$("#closureTime1").val()};
				huiboPage.reload(formData);
			} else if($(".tabListBg").text() == "直拨"){
				formData ={params:{"connectSucc" : $("#connectSucc").val().trim(),"appid" : $("#appid").val().trim(),"callSid" : $("#callSid").val().trim(),"zj" : $("#zj").val().trim(),"bj" : $("#bj").val().trim(),"rcdType" : "sippout"},
					sTime:$("#closureTime").val(), eTime:$("#closureTime1").val()};
				zhiboPage.reload(formData);
			} else if($(".tabListBg").text()== "回呼"){
				formData = {params:{"connectSucc" : $("#connectSucc").val().trim(),"appid" : $("#appid").val().trim(),"callSid" : $("#callSid").val().trim(),"zj" : $("#zj").val().trim(),"bj" : $("#bj").val().trim(),"display" : $("#display").val().trim(),"rcdType" : "sippin"},
					sTime:$("#closureTime").val(), eTime:$("#closureTime1").val()};
				huihuPage.reload(formData);
			}
		}

		//下载报表
		function exportCallRecord(){
			var formData = {};

			// 呼叫类型(回拨 : SIPPREST, 直拨 : SIPPOUT, 回呼 : SIPPIN)
			if ($(".tabListBg").text() == "回拨") {
				formData = {params:{"connectSucc" : $("#connectSucc").val().trim(),"appid" : $("#appid").val().trim(),"callSid" : $("#callSid").val().trim(),"zj" : $("#zj").val().trim(),"bj" : $("#bj").val().trim(),"rcdType" : "sipprest"},
					sTime:$("#closureTime").val(), eTime:$("#closureTime1").val()};
				huiboPage.downloadReport("<c:url value='/sipPhoneAppMgr/exportCallRecord'/>",formData);
			} else if($(".tabListBg").text() == "直拨"){
				formData ={params:{"connectSucc" : $("#connectSucc").val().trim(),"appid" : $("#appid").val().trim(),"callSid" : $("#callSid").val().trim(),"zj" : $("#zj").val().trim(),"bj" : $("#bj").val().trim(),"rcdType" : "sippout"},
					sTime:$("#closureTime").val(), eTime:$("#closureTime1").val()};
				zhiboPage.downloadReport("<c:url value='/sipPhoneAppMgr/exportCallRecord'/>",formData);
			} else if($(".tabListBg").text()== "回呼"){
				formData = {params:{"connectSucc" : $("#connectSucc").val().trim(),"appid" : $("#appid").val().trim(),"callSid" : $("#callSid").val().trim(),"zj" : $("#zj").val().trim(),"bj" : $("#bj").val().trim(),"display" : $("#display").val().trim(),"rcdType" : "sippin"},
					sTime:$("#closureTime").val(), eTime:$("#closureTime1").val()};
				huihuPage.downloadReport("<c:url value='/sipPhoneAppMgr/exportCallRecord'/>",formData);
			}

		}

		var switchRefress = function(){
			var formData = {};
			$(".tabNav>li").click(function(){
				$(this).removeClass("tabListInitBg").addClass("tabListBg");
				$(this).siblings("li").removeClass("tabListBg").addClass("tabListInitBg");

				//清除查询条件
				$(".record_select  input,select").each(function(){
					$(this).val("");
				});

				// 呼叫类型(回拨 : SIPPREST, 直拨 : SIPPOUT, 回呼 : SIPPIN)
				if($(".tabListBg").text()=="回拨"){
					$("#bj").nextAll().remove();
					busPath = "sipphone/sipprest/";

					downloadReport('${currTime}', 'dataModel', busPath+feeid, '<c:url value="/"/>');
//				formData[params["rcdType"]] == "SIPPREST"
					formData = {params:{"rcdType":"sipprest"}};
					$("#huibo").show();
					$("#zhibo").hide();
					$("#huihu").hide();
					huiboPage.reload(formData);
					type="sipprest";
				}else if($(".tabListBg").text()=="直拨"){
					$("#bj").nextAll().remove();
					busPath = "sipphone/sippout/";
					downloadReport('${currTime}', 'dataModel', busPath+feeid, '<c:url value="/"/>');
//				formData[params["rcdType"]] == "SIPPOUT"
					formData = {params:{"rcdType":"sippout"}};

					$("#huibo").hide();
					$("#zhibo").show();
					$("#huihu").hide();
					zhiboPage.reload(formData);
					type="sippout";
				}else if($(".tabListBg").text()=="回呼"){
					$("#bj").after("<span class='check_font call_left'>外显号码：</span> <input type='text' id='display' name='display' class='input_style' style='width: 150px;'/>");
					busPath = "sipphone/sippin/";
					downloadReport('${currTime}', 'dataModel', busPath+feeid, '<c:url value="/"/>');
					formData = {params:{"rcdType":"sippin"}};
					$("#huibo").hide();
					$("#zhibo").hide();
					$("#huihu").show();
					huihuPage.reload(formData);
					type="sippin";
				}else{
					return false;
				}
			});
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
		<script type="text/javascript">showSubMenu('sphone','yhj','callRecord');</script>
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

			<div class="container17" >
				<div class="msg">
					<h3>通话记录</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="records_msg">
					<div class="recordNote">
						<span class="WarmPrompt">温馨提醒：</span>
						<p>1.通话记录仅提供近3个月的数据查询；2.话单导出仅支持近12个月的数据导出。</p>
					</div>


					<div class="tab">
						<ul class="tabNav">
							<li class="tabListBg borderRN">回拨</li>
							<li class="tabListInitBg borderRN">直拨</li>
							<li class="tabListInitBg">回呼</li>
						</ul>
					</div>
					<div class="huibo blueLine clear" style="display: block">
						<form id="searchForm">
							<div class="record_select " >
								<div class="Open_interface_record re_span_common">
                            <span class="open_width">
                                <!--<b class="Bred">*</b>-->
                                <span class="check_font">起止时间：</span>
                                <input id="closureTime" name="closureTime" type="text" class="sm_input_style"/>
                                    <span class="check_font">至</span>
                                    <input id="closureTime1" name="closureTime1" type="text" class="sm_input_style"/>
                            </span>
									<span class="check_font call_left">应&nbsp;&nbsp;用：</span>
									<select id="appid" name="appid" style="width:178px;">
										<option value="">全部</option>
										<%--<c:forEach items="${appInfos}" var="appInfo">
                           				 <option  id="${appInfo.id}" value="${appInfo.appid}">${appInfo.appName}</option>
										</c:forEach>--%>
									</select>
									<span class="check_font call_left">接通状态：</span>
									<select id="connectSucc" name="connectSucc" style="width:178px;">
										<option value="">全部</option>
										<option value="0">未接通</option>
										<option value="1">接通</option>
									</select>
								</div>
								<div class="Open_interface_record re_span_common">
									<span class="check_font">Call&nbsp;&nbsp;ID：</span>
									<input id="callSid" name="callSid" type="text" class="input_style" style="width: 150px;"/>
									<span class="check_font call_left">主叫号码：</span>
									<input type="text" id="zj" name="zj" class="input_style"  style="width: 150px;"/>
									<span class="check_font call_left">被叫号码：</span>
									<input type="text" id="bj" name="bj" class="input_style" style="width: 150px;"/>
								</div>
								<button type="button" class="md_btn f_right" style="margin-top: -5px" onclick="searchCallRecord()">查询</button>
							</div>
						</form>
						<div class="record_down clear" style="margin-top: 50px;margin-right:0px;">
							<div class=" f_right">
								<span class="open_down_billed" onclick="showExportCallRecord1();"><i class="common_icon download_txt"></i>导出历史话单</span>
								<span class="open_down_billed marLeft15" onclick="showExportCallCurMonthRecord();">导出本月话单</span>
							</div>
						</div>
						<div class="clear" id="huibo">
							<table class="table_style" id="huiboTable" style="width:99.8%">
								<thead>
								<tr>
									<th style="width: 34px;">序号</th>
									<th style="width: 200px;">Appid</th>
									<th style="width: 200px;">callid</th>
									<th style="width: 40px;">呼叫</br>类型</th>
									<th style="width: 40px;">类型</th>
									<th style="width: 80px;">主叫号码</th>
									<th style="width: 80px;">被叫号码</th>
									<th style="width: 80px;">外显号码</th>
									<th style="width: 70px;">开始时间</th>
									<th style="width: 70px;">结束时间</th>
									<th style="width: 50px;">通话</br>状态</th>
									<th style="width: 50px;">通话</br>时长</th>
									<th style="width: 50px;">通话</br>费用</th>
									<th style="width: 50px;">录音</br>费用</th>
									<th style="width: 70px;">录音</br>下载</th>
								</tr>
								</thead>
								<tbody id="huiboTbody">
								</tbody>
							</table>
							<div class="f_right app_sorter" id="huiboPagination"></div>
						</div>
						<div class="clear" id="zhibo" style="display: none;">
							<table class="table_style" id="zhiboTable" style="width:99.8%">
								<thead>
								<tr>
									<th style="width: 34px;">序号</th>
									<th style="width: 200px;">Appid</th>
									<th style="width: 200px;">callid</th>
									<th style="width: 80px;">呼叫类型</th>
									<th style="width: 80px;">主叫号码</th>
									<th style="width: 80px;">被叫号码</th>
									<th style="width: 80px;">外显号码</th>
									<th style="width: 70px;">开始时间</th>
									<th style="width: 70px;">结束时间</th>
									<th style="width: 50px;">通话</br>状态</th>
									<th style="width: 50px;">通话</br>时长</th>
									<th style="width: 50px;">通话</br>费用</th>
									<th style="width: 50px;">录音</br>费用</th>
									<th style="width: 70px;">录音</br>下载</th>
								</tr>
								</thead>
								<tbody id="zhiboTbody">
								</tbody>
							</table>
							<div class="f_right app_sorter"  id="zhiboPagination"></div>
						</div>
						<div class="clear" id="huihu" style="display: none;">
							<table class="table_style" id="huihuTable" style="width:99.8%">
								<thead>
								<tr>
									<th style="width: 34px;">序号</th>
									<th style="width: 200px;">Appid</th>
									<th style="width: 200px;">callid</th>
									<th style="width: 80px;">呼叫类型</th>
									<th style="width: 80px;">主叫号码</th>
									<th style="width: 80px;">被叫号码</th>
									<th style="width: 80px;">外显号码</th>
									<th style="width: 70px;">开始时间</th>
									<th style="width: 70px;">结束时间</th>
									<th style="width: 50px;">通话</br>状态</th>
									<th style="width: 50px;">通话</br>时长</th>
									<th style="width: 50px;">通话</br>费用</th>
									<th style="width: 50px;">录音</br>费用</th>
									<th style="width: 70px;">录音</br>下载</th>
								</tr>
								</thead>
								<tbody id="huihuTbody">
								</tbody>
							</table>
							<div class="f_right app_sorter" id="huihuPagination"></div>
						</div>
					</div>
				</div>

				<!--下载历史话单弹层-->
				<div class="modal-box  phone_t_code" id="re_download_billed" style="display: none;">
					<div class="modal_head">
						<h4>下载历史话单</h4>
						<div class="p_right">
							<a href="javascript:void(0);" class="common_icon close_txt" onclick="closeExportCallRecord1();"></a>
						</div>
					</div>
					<div class="modal-common_body mdl_pt_body">
						<div class="download_billed">
							<div class="download_time_left f_left">
								<div class="down_common"><span>2015年01月</span></div>
								<div class="down_common"><span>2015年02月</span></div>
								<div class="down_common"><span>2015年03月</span></div>
								<div class="down_common"><span>2015年04月</span></div>
								<div class="down_common"><span>2015年05月</span></div>
								<div class="down_common"><span>2015年06月</span></div>
							</div>
							<div class="download_time_right f_right">
								<div class="down_common"><span>2015年07月</span></div>
								<div class="down_common"><span>2015年08月</span></div>
								<div class="down_common"><span>2015年09月</span></div>
								<div class="down_common"><span>2015年10月</span></div>
								<div class="down_common"><span>2015年11月</span></div>
								<div class="down_common"><span>2015年12月</span></div>
							</div>
						</div>
					</div>
				</div>

				<!--下载历史话单弹层-->
				<div class="modal-box  phone_t_code" id="re_download_billed1" style="display: none;">
					<div class="modal_head">
						<h4>下载历史话单</h4>
						<div class="p_right">
							<a href="javascript:void(0);" class="common_icon close_txt" onclick="closeExportCallRecord1();"></a>
						</div>
					</div>
					<div class="modal-common_body mdl_pt_body">
						<div class="download_billed1">
							<div class="download_time_left f_left">
								<div class="down_common1"><span>2015年01月</span></div>
								<div class="down_common1"><span>2015年02月</span></div>
								<div class="down_common1"><span>2015年03月</span></div>
								<div class="down_common1"><span>2015年04月</span></div>
								<div class="down_common1"><span>2015年05月</span></div>
								<div class="down_common1"><span>2015年06月</span></div>
							</div>
							<div class="download_time_right f_right">
								<div class="down_common1"><span>2015年07月</span></div>
								<div class="down_common1"><span>2015年08月</span></div>
								<div class="down_common1"><span>2015年09月</span></div>
								<div class="down_common1"><span>2015年10月</span></div>
								<div class="down_common1"><span>2015年11月</span></div>
								<div class="down_common1"><span>2015年12月</span></div>
							</div>
						</div>
					</div>
				</div>

				<!--下载历史话单弹层-->
				<div class="modal-box  phone_t_code" id="re_download_billed2" style="display: none;">
					<div class="modal_head">
						<h4>下载历史话单</h4>
						<div class="p_right">
							<a href="javascript:void(0);" class="common_icon close_txt" onclick="closeExportCallRecord1();"></a>
						</div>
					</div>
					<div class="modal-common_body mdl_pt_body">
						<div class="download_billed2">
							<div class="download_time_left f_left">
								<div class="down_common2"><span>2015年01月</span></div>
								<div class="down_common2"><span>2015年02月</span></div>
								<div class="down_common2"><span>2015年03月</span></div>
								<div class="down_common2"><span>2015年04月</span></div>
								<div class="down_common2"><span>2015年05月</span></div>
								<div class="down_common2"><span>2015年06月</span></div>
							</div>
							<div class="download_time_right f_right">
								<div class="down_common2"><span>2015年07月</span></div>
								<div class="down_common2"><span>2015年08月</span></div>
								<div class="down_common2"><span>2015年09月</span></div>
								<div class="down_common2"><span>2015年10月</span></div>
								<div class="down_common2"><span>2015年11月</span></div>
								<div class="down_common2"><span>2015年12月</span></div>
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
</body>
</html>
