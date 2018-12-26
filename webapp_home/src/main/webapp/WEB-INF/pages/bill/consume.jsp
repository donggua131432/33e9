<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<title>消费总览</title>
	<link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
	<!--[if lt IE 9]>
	<script src="js/html5shiv.min.js"></script>
	<![endif]-->

	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
	<script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
	<script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>

	<script src="${appConfig.resourcesRoot}/js/echarts/echarts.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/My97DatePicker/WdatePicker.js"></script>

	<script type="text/javascript">
		$(function(){
			//初始化echarts实例,并设置值
			showViewByDate('${currentDate}');
			$("#month").on("change",function(){
				if(this.value == 'data') {
					$("#dateMonth").hide();
					$("#dateDay").show();
				}
				if(this.value == 'month') {
					$("#dateMonth").show();
					$("#dateDay").hide();
				}
			});
		});

		//获取每月天数的数组
		function getMonthDaysArray(year,month,day){
			var days=new Date(year, month, day).getDate();  dayArray = [];
			for (var i=1; i<=days; i++){
				if(i.toString().length<=1){
					dayArray.push("0"+i);
				}else{
					dayArray.push(i.toString());
				}
			}
			return dayArray;
		}

		//实例化近期消费走势Echarts
		function  initConsumeTrendEcharts(consumeTrendList,currentDate){
			var dayTitle = "近期消费走势（截止到"+currentDate+"）", dayValueGroupData=[], checkPushStatus="0",
					dayGroupData = getMonthDaysArray(currentDate.substring(0,4), currentDate.substring(5,6), 0);

			$.each(dayGroupData, function(i,v){
				checkPushStatus = "0";

				$.each(consumeTrendList, function(n,v){
					if(dayGroupData[i] == v.d){
						if (v.fee){}
						checkPushStatus = v.fee.replace(',','');
					}
				});

				dayValueGroupData.push(checkPushStatus);
			});

			//加载echarts
			echarts.init(document.getElementById("consumeTrend")).setOption({
				title:{text:dayTitle,left:'left',top:'20',padding: 0,textStyle:{color:'#5b5b5b',fontWeight:'normal',fontSize:'18'}},
				tooltip:{show:'true',trigger: 'axis',formatter: '{b0}日：{c0}万元',backgroundColor: '#5791E1'},
				grid:{top:'80',left:'60',right:'50',bottom:'40'},
				xAxis:{
					boundaryGap:false,
					name:'日期',
					data:dayGroupData
				},
				yAxis:{
					type:'value',
					name:'单位（万元）',
					min:0,
					boundaryGap:[0.2, 0.2]
				},
				series:[{
					type:'line',
					symbol:'circle',
					symbolSize:5,
					showAllSymbol: false,
					itemStyle:{
						normal:{
							color:'#5791E1',
							shadowColor: 'rgba(0, 0, 0, 0.5)',
							shadowBlur:10
						}
					},
					lineStyle:{
						normal:{
							shadowColor: 'rgba(0, 0, 0, 0.5)',
							shadowBlur:10
						}
					},
					data:dayValueGroupData
				}]
			});
		}

		//实例化近期消费走势Echarts
		function  initConsumeTrendEcharts2(consumeTrendList,currentDate){
			var dayTitle = "近期消费走势（截止到"+currentDate+"）", dayValueGroupData=[], checkPushStatus="0",days=[];

			for (var i = 0; i < 24; i++) {
				checkPushStatus = "0";
				days.push(i);
				$.each(consumeTrendList, function(n,v){
					if(i == v.d){
						if (v.fee){
							checkPushStatus = v.fee.replace(',','');
						}
					}
				});

				dayValueGroupData.push(checkPushStatus);
			}

			//加载echarts
			echarts.init(document.getElementById("consumeTrend")).setOption({
				title:{text:dayTitle,left:'left',top:'20',padding: 0,textStyle:{color:'#5b5b5b',fontWeight:'normal',fontSize:'18'}},
				tooltip:{show:'true',trigger: 'axis',formatter: '{b0}时：{c0}万元',backgroundColor: '#5791E1'},
				grid:{top:'80',left:'60',right:'50',bottom:'40'},
				xAxis:{
					boundaryGap:false,
					name:'时',
					data:days
				},
				yAxis:{
					type:'value',
					name:'单位（万元）',
					min:0,
					boundaryGap:[0.2, 0.2]
				},
				series:[{
					type:'line',
					symbol:'circle',
					symbolSize:5,
					showAllSymbol: false,
					itemStyle:{
						normal:{
							color:'#5791E1',
							shadowColor: 'rgba(0, 0, 0, 0.5)',
							shadowBlur:10
						}
					},
					lineStyle:{
						normal:{
							shadowColor: 'rgba(0, 0, 0, 0.5)',
							shadowBlur:10
						}
					},
					data:dayValueGroupData
				}]
			});
		}

		//实例化应用消费概况Echarts
		function  initConsumeSurveyEcharts(consumeSurveyList){

			if(consumeSurveyList) {

				var appTitle = "每个呼叫中心的消费概况", appGroupData = [], appValueGroupData = [];
				if(consumeSurveyList.length == 0){
					appGroupData.push("无");
					appValueGroupData.push(0);
				}
				$.each(consumeSurveyList, function(i,v){
					appGroupData.push(v.callCenter);
					if (v.fee) {
						appValueGroupData.push(v.fee.replace(',',''));
					} else {

						appValueGroupData.push(0);
					}


				});

				//加载echarts
				echarts.init(document.getElementById("appConsumeSurvey")).setOption({
					title: {text: appTitle, left: 'left', padding: 0,textStyle:{color:'#5b5b5b',fontWeight:'normal',fontSize:'18'}},
					grid: {left: '40', right: '70'},
					tooltip: {show: true, formatter: '{b0}：{c0}万元', backgroundColor: '#5791E1'},
					xAxis: {
						name: '呼叫中心',
						data: appGroupData,
						axisLabel:{rotate:0,margin:8,interval:0,formatter: function (value) {
							var i=0; str="", varArray = value.split("");
							for(i;i<varArray.length;i++){
								if((i+1)%6 == 0){
									str += varArray[i]+"\n";
								}else{
									str += varArray[i];
								}
							}
							return str;
						}},
					},
					yAxis: {
						type: 'value',
						scale: true,
						name: '单位（万元）',
						min: 0,
						boundaryGap: [0.2, 0.2]
					},
					series: [{
						type: 'bar',
						barWidth: 25,
						barMinHeight: 3,
						label: {
							normal: {
								show: true,
								position: 'top',
								formatter: '{c}万元'
							}
						},
						itemStyle: {
							normal: {
								color: '#5791E1'
							}
						},
						data: appValueGroupData
					}]
				})
			}
		}

		//实例化应用消费概况Echarts
		function  initConsumeSurveyEcharts2(consumeSurveyList){

			if(consumeSurveyList) {

				var appTitle = "每个呼叫中心的消费概况", appGroupData = [], appValueGroupData = [];
				if(consumeSurveyList.length == 0){
					appGroupData.push("无");
					appValueGroupData.push(0);
				}
				$.each(consumeSurveyList, function(i,v){
					appGroupData.push(v.callCenter);
					if (v.fee) {
						appValueGroupData.push(v.fee.replace(',',''));
					} else {

						appValueGroupData.push(0);
					}


				});

				//加载echarts
				echarts.init(document.getElementById("appConsumeSurvey")).setOption({
					title: {text: appTitle, left: 'left', padding: 0,textStyle:{color:'#5b5b5b',fontWeight:'normal',fontSize:'18'}},
					grid: {left: '40', right: '70'},
					tooltip: {show: true, formatter: '{b0}：{c0}万元', backgroundColor: '#5791E1'},
					xAxis: {
						name: '呼叫中心',
						data: appGroupData,
						axisLabel:{rotate:0,margin:8,interval:0,formatter: function (value) {
							var i=0; str="", varArray = value.split("");
							for(i;i<varArray.length;i++){
								if((i+1)%6 == 0){
									str += varArray[i]+"\n";
								}else{
									str += varArray[i];
								}
							}
							return str;
						}},
					},
					yAxis: {
						type: 'value',
						scale: true,
						name: '单位（万元）',
						min: 0,
						boundaryGap: [0.2, 0.2]
					},
					series: [{
						type: 'bar',
						barWidth: 25,
						barMinHeight: 3,
						label: {
							normal: {
								show: true,
								position: 'top',
								formatter: '{c}万元'
							}
						},
						itemStyle: {
							normal: {
								color: '#5791E1'
							}
						},
						data: appValueGroupData
					}]
				})
			}
		}


		function showViewByDate(){

			var month = $("#month").val();
			var ym = "";
			var url ="";
			if (month == "month") {
				ym = $("#dateMonth").val();
				url = "<c:url value='/znydd/bill/consume/getConsumeAlways'/>"
				$.ajax( {
					type:"post",
					url:url,
					dataType:"json",
					data:{'ym':ym},
					success : function(data) {
						if(data.status == 0){
							$("#consumeTotalFee").text(data.consumeTotal || '0.000');
							$("#times").text(ym+"   消费总计:");
							initConsumeTrendEcharts(data.consumeTrendList, data.ym);
							initConsumeSurveyEcharts(data.consumeSurveyList)
						}else{
							alert(data.info);
						}

					},
					error: function(){
						alert("数据请求失败！");
					}
				});
			} else {
				ym = $("#dateDay").val();
				url = "<c:url value='/znydd/bill/consume/getHourConsumeAlways'/>"
				$.ajax( {
					type:"post",
					url:url,
					dataType:"json",
					data:{'ym':ym},
					success : function(data) {
						if(data.status == 0){
							$("#consumeTotalFee").text(data.consumeTotal || '0.000');
							$("#times").text(ym+"   消费总计:");

							initConsumeTrendEcharts2(data.consumeTrendList, data.ym);
							initConsumeSurveyEcharts2(data.consumeSurveyList)
						}else{
							alert(data.info);
						}

					},
					error: function(){
						alert("数据请求失败！");
					}
				});
			}

			$("span[name='spanConsumeDate']").text(ym);


		}

	</script>
</head>
<body>
<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->
<section>
	<div id="sec_main">
		<!-- 左侧菜单 start -->
		<jsp:include page="../common/controlleft.jsp"/>
		<script type="text/javascript">showSubMenu('zn','smart_cloud','consume');</script>
		<!-- 左侧菜单 end -->


		<div class="main3">
			<div class="container16" >
				<div class="msg width">
					<h3>消费总览</h3>
				</div>
				<div class="consume_msg">
					<div class="consume_msg_select width">
						<label>按&nbsp;账&nbsp;期:</label>
						<select name="month" id="month" style="width:120px;" >
							<option value="month">月</option>
							<option value="data">日</option>
						</select>
						<input type="hidden" id="maxDate" value="${currentDate}"/>
						<input type="hidden" id="minDate" value="${beforcurrentDate}"/>

						<input type="hidden" id="maxDate2" value="${currentDate2}"/>
						<input type="hidden" id="minDate2" value="${beforcurrentDate2}"/>


						<input type="text" class="Wdate" id="dateMonth" onfocus="WdatePicker({dateFmt:'yyyy年M月',maxDate:'#F{$dp.$D(\'maxDate\')}',minDate:'#F{$dp.$D(\'minDate\')}',readOnly:true})" value="${currentDate}" style="margin-left:10px;margin-bottom:5px;width:184px;height:30px;"/>

						<input type="text" class="Wdate" id="dateDay" onfocus="WdatePicker({dateFmt:'yyyy年M月dd日',maxDate:'#F{$dp.$D(\'maxDate2\')}',minDate:'#F{$dp.$D(\'minDate2\')}',readOnly:true})" value="${currentDate2}" style="margin-left:10px;margin-bottom:5px;width:184px;height:30px; display: none"/>


						<button type="button" onclick="showViewByDate();" class="md_btn f_right" style="margin-top:-5px;">查询</button>
					</div>
					<div class="consume_msg_table clear">
						<div class="table_first">
							<div class="consume_gl f_left">
								<div class="consume_total">
									<span class="consume_common_font" id="times">${currentDate} </span>
									<%--<span class="consume_common_font">消费总计：</span>--%>
									<span>(&yen;)</span>
								</div>
								<div class="img_common_top consume_overview_width">
									<span id="consumeTotalFee">${consumeTotal}</span>
									<p>(万)元</p>
								</div>
							</div>
							<div class="consume_zs f_left">
								<div id="consumeTrend" class="consume_tendency_width" style="height:480px;"></div>
							</div>
						</div>
						<div class="table_second clear">
							<div id="appConsumeSurvey" class="ev_call_center_zs" style="height:580px;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>
