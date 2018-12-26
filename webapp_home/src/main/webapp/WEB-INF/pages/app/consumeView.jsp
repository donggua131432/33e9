<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/2/29
  Time: 16:27
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
    <title>消费总览</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>

    <script src="${appConfig.resourcesRoot}/js/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/My97DatePicker/WdatePicker.js"></script>

    <script type="text/javascript">
        $(function(){
            //初始化echarts实例,并设置值
            initConsumeTrendEcharts(${consumeTrendList},"${currentDate}");
            initConsumeSurveyEcharts(${consumeSurveyList});
        });

        //实例化近期消费走势Echarts
        function  initConsumeTrendEcharts(consumeTrendList,currentDate){
            var dayTitle="近期消费走势（"+currentDate+"）",dayValueGroupData=[],checkPushStatus="0",
                 dayGroupData=getMonthDaysArray(currentDate.substr(0,4), currentDate.substr(5,2), 0);
            for(var i=0; i<dayGroupData.length; i++){
                checkPushStatus="0";
                for (var j = 0; j < consumeTrendList.length; j++) {
                    if(dayGroupData[i] == consumeTrendList[j]["stafDay"].substr(8)){
                        checkPushStatus=consumeTrendList[j]["totalFee"];
                    }
                }
                dayValueGroupData.push(checkPushStatus);
            }
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


        //实例化应用消费概况Echarts
        function  initConsumeSurveyEcharts(consumeSurveyList){
            var appTitle = "应用消费概况", appGroupData = [], appValueGroupData = [];
            if(consumeSurveyList != null && consumeSurveyList != '' && consumeSurveyList != undefined) {
                for (var i = 0; i < consumeSurveyList.length; i++) {
                    appGroupData.push(consumeSurveyList[i]["appName"]);
                    appValueGroupData.push(consumeSurveyList[i]["totalFee"]);
                }
            }else{
                appGroupData = ["暂无消费"];
                appValueGroupData = [0];
            }

            //加载echarts
            echarts.init(document.getElementById("appConsumeSurvey")).setOption({
                title: {text: appTitle, left: 'left', padding: 0,textStyle:{color:'#5b5b5b',fontWeight:'normal',fontSize:'18'}},
                grid: {left: '60', right: '40'},
                tooltip: {show: true, formatter: '{b0}：{c0}万元', backgroundColor: '#5791E1'},
                xAxis: {
                    name: '应用',
                    data: appGroupData,
                    axisLabel:{rotate:0,margin:8,interval:0,formatter: function (value) {
                        var i=0; str="", varArray = value.split("");
                        for(i;i<varArray.length;i++){
                            if((i+1)%5 == 0){
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


        function showViewByDate(){
            $("span[name='spanConsumeDate']").text($dp.cal.getNewDateStr());
            var formatStr = $dp.cal.getNewDateStr().replace(/[\u4E00-\u9FA5]/g,'-'),
            /*stafday=formatStr.substr(0,formatStr.length-1)*/ stafDay=formatStr+"1";
            $.ajax( {
                type:"post",
                url:"<c:url value='/monthlySta/getConsumeAlways'/>",
                dataType:"json",
                data:{'stafDay':stafDay},
                success : function(data) {
                    if(data.status == 0){
                        //近期消费走势
                        initConsumeTrendEcharts(JSON.parse(data.consumeTrendList),data.currentDate);
                        //应用消费概况
                        initConsumeSurveyEcharts(JSON.parse(data.consumeSurveyList));
                        $("#consumeTotalFee").text(data.consumeTotal.totalFee);
                    }else{
                        alert(data.info);
                    }
                },
                error: function(){
                    alert("数据请求失败！");
                }
            });
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
        <script type="text/javascript">showSubMenu('kf','appMgr','consumeView');</script>
        <!-- 左侧菜单 end -->


        <div class="main3">
            <div class="container16" >
                <div class="msg width">
                    <h3>消费总览</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="consume_msg">
                    <div class="consume_msg_select width">
                        <label>按账期:</label>
                        <input type="hidden" id="maxDate" value="${currentDate}"/>
                        <input type="hidden" id="minDate" value="${beforThreeMonth}"/>
                        <input type="text" class="Wdate" id="dateMonth" onfocus="WdatePicker({dateFmt:'yyyy年M月',maxDate:'#F{$dp.$D(\'maxDate\')}',minDate:'#F{$dp.$D(\'minDate\')}',readOnly:true,onpicking:showViewByDate,isShowClear:false,isShowOK:false,isShowToday:false})" value="${currentDate}" style="margin-left:10px;margin-bottom:5px;width:184px;height:25px;"/>
                    </div>
                    <div class="consume_msg_table clear">
                        <div class="table_first">
                            <div class="consume_gl f_left">
                                <span class="consume_common_font"><span name="spanConsumeDate">${currentDate}</span>消费概览</span>
                                <div class="consume_total"><span name="spanConsumeDate">${currentDate}</span>消费总计：<span>(&yen;)</span></div>
                                <div class="img_common_top consume_overview_width">
                                    <span id="consumeTotalFee">${consumeTotal.totalFee}</span>
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
