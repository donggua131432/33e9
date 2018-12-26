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
	<link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
	<link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
	<!--[if lt IE 9]-->
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
	<!--[endif]-->
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/sui.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/echarts/echarts.min.js"></script>
	<script type="text/javascript" src="${appConfig.resourcesRoot}/js/echarts/china.js"></script>
	<script type="text/javascript">
		var areaDataAll = [], pnameDataAll = [], pnameDataTop10 = [], callcntDataAll = [], callcntDataTop10 = [];
		var mapEcharts;
		$(function(){
			//话务总览效果
			$(".dataRange").click(function(){
				if(!$(this).hasClass("dataRangeHover")){
					$(this).addClass("dataRangeHover");
					$(this).siblings().removeClass("dataRangeHover");
				}
			});
			searchTrafficScan();
		});

		function searchTrafficScan(){
			areaDataAll =[
				{name:'北京',value:0},{name:'天津',value:0},{name:'上海',value:0}, {name:'重庆',value:0},
				{name:'河北',value:0},{name:'河南',value:0},{name:'云南',value:0}, {name:'辽宁',value:0},
				{name:'黑龙江',value:0},{name:'湖南',value:0},{name:'安徽',value:0}, {name:'山东',value:0},
				{name:'新疆',value:0},{name:'江苏',value:0}, {name:'浙江',value:0}, {name:'江西',value:0},
				{name:'湖北',value:0},{name:'广西',value:0},{name:'甘肃',value:0}, {name:'山西',value:0},
				{name:'内蒙古',value:0},{name:'陕西',value:0},{name:'吉林',value:0}, {name:'福建',value:0},
				{name:'贵州',value:0},{name:'广东',value:0},{name:'青海',value:0},{name:'西藏',value:0},
				{name:'四川',value:0},{name:'宁夏',value:0},{name:'海南',value:0},{name:'台湾',value:0},
				{name:'香港',value:0},{name:'南海诸岛',value:0}
			];
			pnameDataAll = [
				'北京','天津','上海', '重庆','河北','河南','云南','辽宁','黑龙江','湖南','安徽','山东','新疆','江苏',
				'浙江','江西', '湖北','广西','甘肃','山西','内蒙古','陕西','吉林','福建','贵州','广东','青海','西藏',
				'四川','宁夏','海南','台湾','香港','南海诸岛'
			];
			callcntDataAll = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
			var timeRange = $.trim($(".dataRange.dataRangeHover").attr("spanVal")), subid = $("#subid").val();
			//获取呼入地区分布图数据
			$.ajax({
				type:"post",
				url:"<c:url value="/smartCloud/traffic/scan/getCcCallInRecordList"/>",
				dataType:"json",
				async:false,
				data:{timeRange:timeRange,subid:subid,abline:"I"},
				success : function(data) {
					var areaDataArray = [];
					if(data != null && data != ""){
						$(data).each(function(){
							var areaData = {};
							areaData["name"] = $(this)[0]["pname"];
							areaData["value"] = $(this)[0]["callcnt"];
							areaDataArray.push(areaData);
						});
						//合并json数据
						$(areaDataArray).each(function(j){
							for(var i=0; i<areaDataAll.length; i++){
								if(areaDataArray[j]["name"] == areaDataAll[i]["name"]){
									areaDataAll[i] = $.extend(areaDataAll[i],areaDataArray[j]);
								}
							}
						});
					}else{
						console.log("呼入地区数据不存在！");
					}
				},
				error:function(){
					console.log("呼入地区数据请求失败！");
				}
			});

			//获取呼叫总量数据
			$.ajax({
				type:"post",
				url:"<c:url value="/smartCloud/traffic/scan/getCcCallInRecordList"/>",
				dataType:"json",
				async:false,
				data:{timeRange:timeRange,subid:subid},
				success : function(data) {
					var pnameData = [], callcntData = [];
					pnameDataTop10=[];
					callcntDataTop10=[];
					if(data != null && data != ""){
						$.each(data,function(i,n){
							if (n["pname"]) { // 过滤掉为空的情况
								pnameData.push(ifnull(n["pname"],"未知"));
								callcntData.push(ifnull(n["callcnt"],"0"));
							}
						});
						//合并数组
						pnameData=$.unique($.merge(pnameData,pnameDataAll));
						for(var i=0; i<callcntData.length; i++){
							//删除数组中的最后一个元素
							callcntDataAll.pop();
						}
						callcntData=$.merge(callcntData,callcntDataAll);
						//添加top10数据
						for(var i=9; i>=0; i--){
							pnameDataTop10.push(pnameData[i]);
							callcntDataTop10.push(callcntData[i]);
						}
					}else{
						for(var i=9; i>=0; i--){
							pnameDataTop10.push(pnameDataAll[i]);
							callcntDataTop10.push(callcntDataAll[i]);
						}
						console.log("呼叫总量数据不存在！");
					}
				},
				error:function(){
					console.log("呼叫总量数据请求失败！");
				}
			});
			initCallInMap();
			initcallCenterSum();
		}
		//加载呼入地区分布图数据
		function initCallInMap(){
			if (!mapEcharts) {
				mapEcharts = echarts.init(document.getElementById("callInMap"));
			}
			var mapOption = {
				title : {text: '呼入地区分布图',subtext: '颜色深浅表示呼叫量高低'},
				grid:{top:'-100',bottom:'30'},
				tooltip : {trigger: 'item'},
				visualMap: {
					type:'piecewise',
					pieces: [
						{min: 10000, label: '10000以上'},
						{min: 8000, max: 10000},
						{min: 5000, max: 8000},
						{max: 5000, label: '5000以下'}
					],
					top:'65',
					color: ["#326dbd", "#9ac5ff"]
				},
				series : [
					{
						name: '呼入量',
						type: 'map',
						mapType: 'china',
						top:10,
						bottom:10,
						itemStyle:{
							normal:{label:{show:true}},
							emphasis:{
								label:{show:true},
								areaColor:"#66cb99" //鼠标悬停时区域颜色
							}
						},
						data:areaDataAll
					}
				]
			};
			mapEcharts.setOption(mapOption);
		}
		//加载呼叫总量数据
		function initcallCenterSum(){
			var callCenterCharts = echarts.init(document.getElementById("callCenterSum"));
			var callCenterOption = {
				title:{text: '呼叫总量',subtext: 'TOP10'},
				tooltip:{show:false,formatter: '{b}：{c}'},
				color:["#5791e1"],
				grid:{top:'60',left:'70',right:'80',bottom:'30'},
				xAxis : [
					{
						type:'value',
						axisLine:{show:false},
						splitLine:{show:false},
						axisLabel:{show:false},
						axisTick:{show:false}
					}
				],
				yAxis : [
					{
						type: 'category',
						axisLine:{show:false},
						splitLine:{show:false},
						axisTick:{show:false},
						axisLabel:{
							margin:12,
							textStyle:{
								fontSize: '14'
							}
						},
						data:pnameDataTop10
					}
				],
				series : [
					{
						name:'呼叫总量',
						type:'bar',
						barWidth: 16,
						label:{
							normal:{
								show:true,
								position:['1040', '-1'],
								textStyle:{
									fontSize:'14',
									color:'#000000',
									fontFamily: '微软雅黑'
								}
							}
						},
						data:callcntDataTop10
					}
				]
			};
			callCenterCharts.setOption(callCenterOption);
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
		<script type="text/javascript">showSubMenu('zn','smart_cloud','scan');</script>
		<!-- 左侧菜单 end -->

		<div class="main3">
			<div class="container7">
				<div class="msg">
					<h3>话务总览</h3>
					<jsp:include page="../common/message.jsp"/>
				</div>
				<div class="demand">
					<span class="c7_common">数据范围</span>
					<span class="dataRange dataRangeHover" spanVal="yesterday">昨天</span>
					<span class="dataRange noneBL" spanVal="7day">近7天</span>
					<span class="dataRange noneBL" spanVal="30day">近30天</span>
					<span class="c7_common demand_left">呼叫中心</span>
					<select id="subid" name="subid">
						<option value="">全部</option>
						<c:forEach items="${ccInfoList}" var="ccInfo">
							<option value="${ccInfo.subid}">${ccInfo.ccname}</option>
						</c:forEach>
					</select>
					<button type="button" class="md_btn f_right" onclick="searchTrafficScan();" style="margin-top:-5px;">查询</button>
				</div>
				<div class="date clear">
					<div class="on_state clear">
						<div id="callInMap" style="width: 100%; height:600px;"></div>
					</div>
					<div class="inbound_breatheOut">
						<div id="callCenterSum" style="width: 100%;height:600px;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>
