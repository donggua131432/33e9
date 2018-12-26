<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<title>话务监控</title>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
	<!--[if lt IE 9]>
	<script type="text/javascript" src="js/html5shiv.min.js"></script>
	<![endif]-->
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/sui.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/echarts/echarts.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<script type="text/javascript" >
		var initCallInDom,initCallOutDom;
		$(function(){
			initCallInDom = echarts.init(document.getElementById("callInChart"));
			initCallOutDom = echarts.init(document.getElementById("callIOutChart"));
			//每隔5分钟生成时间
			/*var date = new Date("2016-12-13 15:25");
			for(var i=1; i<=36; i++){
				date.setMinutes(date.getMinutes()-5);
				console.log(date.format("yyyy-MM-dd hh:mm"));
			}*/

			loadCharts();
			setInterval(loadCharts,60000);
		});

		function loadCharts(){
			var data = loadAjaxData('<c:url value="/smartCloud/traffic/monitor/getCcMonitorData"/>',{subid:$("#subid").val()}),
					chartsData = parseData(data['ccMonitor'],data['currentDate']);

			var initCallInSeries = [], initCallOutSeries = [];
			initCallInSeries.push(initSeries('呼入总量',chartsData.callcntI,0));
			initCallInSeries.push(initSeries('接通率',chartsData.succcntI,1));

			initCallOutSeries.push(initSeries('呼出总量',chartsData.callcntO,0));
			initCallOutSeries.push(initSeries('接通率',chartsData.succcntO,1));

			initCallInDom.setOption(initOption("呼入",["呼入总量","接通率"],chartsData.xAxisData, initCallInSeries));
			initCallOutDom.setOption(initOption("呼出",["呼出总量","接通率"],chartsData.xAxisData, initCallOutSeries));
		}

		function parseData(data, dateTime){
			var xAxisData = [],
				subDate = dateTime.substring(dateTime.length-2,dateTime.length),
				currentDate =  dateTime.substring(0,dateTime.length-2)+parseInt(subDate-(subDate%5)),
				chartsData = {
					xAxisData: xAxisData,
					callcntI:[],
					succcntI:[],
					callcntO:[],
					succcntO:[]
				};

			var arrayCount=3*60/${sysAvar.ivalue}, date = new Date(currentDate.replace(/-/g,"/"));
			date.setHours(date.getHours()-3);
			for(var m=0; m<arrayCount; m++) {
				date.setMinutes(date.getMinutes() + 5);
				xAxisData.push(date.format("hh:mm"));
			}

			var i, j,flag;
			for(i=0; i<xAxisData.length; i++){
				if(data){
					flag = false;
					for (j=0; j<data.length; j++) {
						if(xAxisData[i] == data[j]['d']){
							//呼入
							chartsData.callcntI.push(data[j]['callcntI']!=undefined?data[j]['callcntI']:0);
							chartsData.succcntI.push(data[j]['succcntI']!=undefined?parseInt(data[j]['succcntI']*100):0);
							//呼出
							chartsData.callcntO.push(data[j]['callcntO']!=undefined?data[j]['callcntO']:0);
							chartsData.succcntO.push(data[j]['succcntO']!=undefined?parseInt(data[j]['succcntO']*100):0);
							flag = true;
						}
					}
					if(flag == false){
						//呼入
						chartsData.callcntI.push(0);
						chartsData.succcntI.push(0);
						//呼出
						chartsData.callcntO.push(0);
						chartsData.succcntO.push(0);
					}
				}else{
					//呼入
					chartsData.callcntI.push(0);
					chartsData.succcntI.push(0);
					//呼出
					chartsData.callcntO.push(0);
					chartsData.succcntO.push(0);
				}
			}
			return chartsData;
		}


		function loadAjaxData(url,params) {
			var data;
			$.ajax({
				async: false,
				type: "POST",
				url: url,
				data: params,
				success: function(msg){
					data = msg;
				}
			});
			return data;
		}


		function initSeries(title,data,yAxisIndex){
			var optionSeries = {
				name:title,
				type:'line',
				yAxisIndex: yAxisIndex,
				symbol:'circle',
				symbolSize:6,
				showAllSymbol: false,
				itemStyle: {
					normal: {
						areaStyle: {
							color:'#e8e8f4',
							type: 'default'
						}
					}
				},
				data:data
			};
			return optionSeries;
		}


		function initOption(title,legendData,xAxis,series){
			var optionMonitor = {
				title : {text:title},
				color:['#6766cb','#66cb99'],
				tooltip : {
					trigger: 'axis',
					formatter: function (params) {
						var res = params[0].name;
						for (var i = 0; i < params.length; i++) {
							res += '<br/>' + params[i].seriesName;
							if(params[i].seriesName == "接通率"){
								res += ' : ' + params[i].value + "%";
							}else{
								res += ' : ' + params[i].value;
							}
						}
						return res;
					}
				},
				grid:{top:'60',left:'60',right:'50',bottom:'30'},
				legend: {
					left:'53',
					top:'7',
					data:legendData
				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						boundaryGap : false,
						data : xAxis
					}
				],
				yAxis : [
					{
						type : 'value',
						splitLine:{lineStyle:{type:'dashed'}}
					},
					{
						type : 'value',
						min:0,
						max:100,
						splitLine:{lineStyle:{type:'dashed'}},
						axisLabel: {
							formatter: function (v) {
								return v + '%'
							}
						}
					}
				],
				series : series
			};
			return optionMonitor;
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
		<script type="text/javascript">showSubMenu('zn','smart_cloud','monitor');</script>
		<!-- 左侧菜单 end -->
		<div class="main3">
			<div class="container7">
				<div class="msg">
					<h3>话务监控</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="demand">
					<span class="c7_common">呼叫中心</span>
					<select id="subid" name="subid" onchange="loadCharts();">
						<c:forEach items="${ccInfoList}" var="ccInfo">
							<option value="${ccInfo.subid}">${ccInfo.ccname}</option>
						</c:forEach>
					</select>
					<!--<button type="button" class="md_btn hw_btn">查询</button>-->
				</div>

				<div class="date clear">
					<%--呼入--%>
					<div class="on_state clear">
						<div id="callInChart" style="width: 1167px;height:348px;"></div>
					</div>
					<%--呼出--%>
					<div class="inbound_breatheOut">
						<div id="callIOutChart" style="width: 1167px;height:348px;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>