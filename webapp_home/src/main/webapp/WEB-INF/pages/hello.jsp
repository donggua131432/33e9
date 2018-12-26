<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="e9" uri="http://www.e9cloud.com/e9tag"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>index</title>
</head>
<body>
	<h1>${message}</h1>
	<div id="userinfo"></div>
	<div>${appConfig.url}</div>
	<e9:printCRNContext primaryKey="123"/>
	<div id="main" style="width: 600px;height:400px;"></div>
	<div id="main1" style="width: 600px;height:400px;"></div>
	<div id="map" style="width: 1059px;height:528px;"></div>
</body>
<script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.3.min.js"></script>
<script src="${appConfig.resourcesRoot}/js/echarts/echarts.min.js"></script>
<script src="${appConfig.resourcesRoot}/js/echarts/china.js"></script>

<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('main'));
	var option = {
		title: {
			text: 'ECharts 入门示例'
		},
		tooltip: {},
		legend: {
			data:['销量']
		},
		xAxis: {
			data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
		},
		yAxis: {},
		series: [{
			name: '销量',
			type: 'bar',
			data: [5, 20, 36, 10, 10, 20]
		}]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);

	var myChart1 = echarts.init(document.getElementById('main1'));
	var option1 = {
		title: {
			text: '堆叠区域图'
		},
		tooltip : {
			trigger: 'axis'
		},
		legend: {
			data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		xAxis : [
			{
				type : 'category',
				boundaryGap : false,
				data : ['周一','周二','周三','周四','周五','周六','周日']
			}
		],
		yAxis : [
			{
				type : 'value'
			}
		],
		series : [
			{
				name:'邮件营销',
				type:'line',
				stack: '总量',
				areaStyle: {normal: {}},
				data:[120, 132, 101, 134, 90, 230, 210]
			},
			{
				name:'联盟广告',
				type:'line',
				stack: '总量',
				areaStyle: {normal: {}},
				data:[220, 182, 191, 234, 290, 330, 310]
			},
			{
				name:'视频广告',
				type:'line',
				stack: '总量',
				areaStyle: {normal: {}},
				data:[150, 232, 201, 154, 190, 330, 410]
			},
			{
				name:'直接访问',
				type:'line',
				stack: '总量',
				areaStyle: {normal: {}},
				data:[320, 332, 301, 334, 390, 330, 320]
			},
			{
				name:'搜索引擎',
				type:'line',
				stack: '总量',
				label: {
					normal: {
						show: true,
						position: 'top'
					}
				},
				areaStyle: {normal: {}},
				data:[820, 932, 901, 934, 1290, 1330, 1320]
			}
		]
	};

	myChart1.setOption(option1);


	option2 = {
		title : {
			text: 'iphone销量',
			subtext: '纯属虚构',
			left: 'center'
		},
		tooltip : {
			trigger: 'item'
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data:['iphone3','iphone4','iphone5']
		},
		visualMap: {
			min: 0,
			max: 2500,
			left: 'left',
			top: 'bottom',
			text:['高','低'],           // 文本，默认为数值文本
			calculable : true
		},
		toolbox: {
			show: true,
			orient : 'vertical',
			left: 'right',
			top: 'center',
			feature : {
				mark : {show: true},
				dataView : {show: true, readOnly: false},
				restore : {show: true},
				saveAsImage : {show: true}
			}
		},
		series : [
			{
				name: 'iphone3',
				type: 'map',
				mapType: 'china',
				roam: false,
				itemStyle:{
					normal:{label:{show:true}},
					emphasis:{label:{show:true}}
				},
				data:[
					{name: '北京',value: Math.round(Math.random()*1000)},
					{name: '天津',value: Math.round(Math.random()*1000)},
					{name: '上海',value: Math.round(Math.random()*1000)},
					{name: '重庆',value: Math.round(Math.random()*1000)},
					{name: '河北',value: Math.round(Math.random()*1000)},
					{name: '河南',value: Math.round(Math.random()*1000)},
					{name: '云南',value: Math.round(Math.random()*1000)},
					{name: '辽宁',value: Math.round(Math.random()*1000)},
					{name: '黑龙江',value: Math.round(Math.random()*1000)},
					{name: '湖南',value: Math.round(Math.random()*1000)},
					{name: '安徽',value: Math.round(Math.random()*1000)},
					{name: '山东',value: Math.round(Math.random()*1000)},
					{name: '新疆',value: Math.round(Math.random()*1000)},
					{name: '江苏',value: Math.round(Math.random()*1000)},
					{name: '浙江',value: Math.round(Math.random()*1000)},
					{name: '江西',value: Math.round(Math.random()*1000)},
					{name: '湖北',value: Math.round(Math.random()*1000)},
					{name: '广西',value: Math.round(Math.random()*1000)},
					{name: '甘肃',value: Math.round(Math.random()*1000)},
					{name: '山西',value: Math.round(Math.random()*1000)},
					{name: '内蒙古',value: Math.round(Math.random()*1000)},
					{name: '陕西',value: Math.round(Math.random()*1000)},
					{name: '吉林',value: Math.round(Math.random()*1000)},
					{name: '福建',value: Math.round(Math.random()*1000)},
					{name: '贵州',value: Math.round(Math.random()*1000)},
					{name: '广东',value: Math.round(Math.random()*1000)},
					{name: '青海',value: Math.round(Math.random()*1000)},
					{name: '西藏',value: Math.round(Math.random()*1000)},
					{name: '四川',value: Math.round(Math.random()*1000)},
					{name: '宁夏',value: Math.round(Math.random()*1000)},
					{name: '海南',value: Math.round(Math.random()*1000)},
					{name: '台湾',value: Math.round(Math.random()*1000)},
					{name: '香港',value: Math.round(Math.random()*1000)},
					{name: '澳门',value: Math.round(Math.random()*1000)}
				]
			},
			{
				name: 'iphone4',
				type: 'map',
				mapType: 'china',
				itemStyle:{
					normal:{label:{show:true}},
					emphasis:{label:{show:true}}
				},
				data:[
					{name: '北京',value: Math.round(Math.random()*1000)},
					{name: '天津',value: Math.round(Math.random()*1000)},
					{name: '上海',value: Math.round(Math.random()*1000)},
					{name: '重庆',value: Math.round(Math.random()*1000)},
					{name: '河北',value: Math.round(Math.random()*1000)},
					{name: '安徽',value: Math.round(Math.random()*1000)},
					{name: '新疆',value: Math.round(Math.random()*1000)},
					{name: '浙江',value: Math.round(Math.random()*1000)},
					{name: '江西',value: Math.round(Math.random()*1000)},
					{name: '山西',value: Math.round(Math.random()*1000)},
					{name: '内蒙古',value: Math.round(Math.random()*1000)},
					{name: '吉林',value: Math.round(Math.random()*1000)},
					{name: '福建',value: Math.round(Math.random()*1000)},
					{name: '广东',value: Math.round(Math.random()*1000)},
					{name: '西藏',value: Math.round(Math.random()*1000)},
					{name: '四川',value: Math.round(Math.random()*1000)},
					{name: '宁夏',value: Math.round(Math.random()*1000)},
					{name: '香港',value: Math.round(Math.random()*1000)},
					{name: '澳门',value: Math.round(Math.random()*1000)}
				]
			},
			{
				name: 'iphone5',
				type: 'map',
				mapType: 'china',
				itemStyle:{
					normal:{label:{show:true}},
					emphasis:{label:{show:true}}
				},
				data:[
					{name: '北京',value: Math.round(Math.random()*1000)},
					{name: '天津',value: Math.round(Math.random()*1000)},
					{name: '上海',value: Math.round(Math.random()*1000)},
					{name: '广东',value: Math.round(Math.random()*1000)},
					{name: '台湾',value: Math.round(Math.random()*1000)},
					{name: '香港',value: Math.round(Math.random()*1000)},
					{name: '澳门',value: Math.round(Math.random()*1000)}
				]
			}
		]
	};

	var echarts2 = echarts.init(document.getElementById("map"));

	echarts2.setOption(option2);

</script>
</html>