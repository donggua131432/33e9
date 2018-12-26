<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2016/12/2
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>话务监控-专线语音</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<script type="text/javascript">

    var callCntDom,callTimeLengthDom,successCntDom,answerCntDom, initDataInterval;

    $(function(){
        //渲染监控图
        callCntDom = echarts.init(document.getElementById("callCnt"));
        callTimeLengthDom = echarts.init(document.getElementById("callTimeLength"));
        successCntDom = echarts.init(document.getElementById("successCnt"));
        answerCntDom = echarts.init(document.getElementById("answerCnt"));

        $("div.btn-group").find("span").click(function() {
            if (!$(this).hasClass("btn-primary")) {
                $(this).addClass("btn-primary");
                $(this).siblings().removeClass("btn-primary");
            }
            $(this).attr("spanType") == "now" ?  initData("now") : initData("history");
        });

        initData("now");
    });


    function initData(dataType){
        if(dataType == "now"){
            $("#timeDiv").hide();
            loadCharts("now");
            initDataInterval = setInterval(function(){
                loadCharts("now");
            },60000);
        }else{
            $("#timeDiv").show();
            loadCharts("history");
            window.clearInterval(initDataInterval);
        }
    }


    function loadCharts(sType){
        var data = loadAjaxData('<c:url value="/monitor/restMonitorData"/>',{sType:sType,day:$("#historyDateTime").val()}),
             chartsData = parseData(data['monitor'],data['currentDate'],sType);

        var callCntSeries = [], thscsumSeries = [], succcntSeries = [], answercntSeries = [];
        callCntSeries.push(initSeries('话务总量','#00FF00','#F0FFF0',chartsData.callCnt));
        thscsumSeries.push(initSeries('A路','#00FF00','#F0FFF0',chartsData.thscsumA));
        thscsumSeries.push(initSeries('B路','#FF0000','#FFF0F0',chartsData.thscsumB));
        succcntSeries.push(initSeries('A路','#00FF00','#F0FFF0',chartsData.succcntA));
        succcntSeries.push(initSeries('B路','#FF0000','#FFF0F0',chartsData.succcntB));
        answercntSeries.push(initSeries('A路','#00FF00','#F0FFF0',chartsData.answercntA));
        answercntSeries.push(initSeries('B路','#FF0000','#FFF0F0',chartsData.answercntB));

        $("#spanLineA").text(chartsData.callTimeLengthA);
        $("#spanLineB").text(chartsData.callTimeLengthB);
        callCntDom.setOption(initOption("话务总量",["话务总量"],chartsData.xAxisData, callCntSeries));
        callTimeLengthDom.setOption(initOption("累计通话时长（秒）",["A路","B路"],chartsData.xAxisData, thscsumSeries));
        successCntDom.setOption(initOption("接通率",["A路","B路"],chartsData.xAxisData, succcntSeries));
        answerCntDom.setOption(initOption("应答率",["A路","B路"],chartsData.xAxisData, answercntSeries));
    }

    function  initSeries(title,color,color1,data){
         var seriesOption = {
            name:title,
            type:'line',
            symbol:'circle',
            symbolSize:6,
            itemStyle : {
                normal : {
                    color:color,
                    lineStyle:{color:color},
                    areaStyle:{color:color1,type: 'default'}
                }
            },
            data:data
         };
        return seriesOption;
    }

    function parseData(data,dateTime,sType){
        var xAxisData = [],
            subDate = dateTime.substring(dateTime.length-2,dateTime.length),
            strDate =  dateTime.substring(0,dateTime.length-2)+parseInt(subDate-(subDate%5)),
            currentDate = strDate.substring(strDate.length-5, strDate.length),
            chartsData = {
                xAxisData: xAxisData,
                callCnt:[],
                thscsumA:[],
                thscsumB:[],
                succcntA:[],
                succcntB:[],
                answercntA:[],
                answercntB:[],
                callTimeLengthA:0,
                callTimeLengthB:0
            };

        var arrayCount=24*60/${sysAvar.ivalue}, date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setMinutes(date.getMinutes() - 5);
        date.setSeconds(0);
        date.setMilliseconds(0);
        for(var m=1; m<=arrayCount; m++) {
            date.setMinutes(date.getMinutes() + 5);
            xAxisData.push(date.format("hh:mm"));
        }

        var i, j,flag;
        for(i=0; i<xAxisData.length; i++){
            if(data){
                if(!compareDate(currentDate,xAxisData[i]) && sType=="now"){
                    chartsData.callCnt.push("");
                    chartsData.thscsumA.push("");
                    chartsData.thscsumB.push("");
                    //接通率
                    chartsData.succcntA.push("");
                    chartsData.succcntB.push("");
                    //应答率
                    chartsData.answercntA.push("");
                    chartsData.answercntB.push("");
                }

                flag = false;
                for (j=0; j<data.length; j++) {
                    if(xAxisData[i] == data[j]['d']){
                        //话务总量
                        chartsData.callCnt.push(data[j]['callcnt']);
                        //累计通话时长
                        if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                            if(i!=0){
                                chartsData.thscsumA.push(parseInt(chartsData.thscsumA[i-1]+data[j]['thscsumA']));
                                chartsData.thscsumB.push(parseInt(chartsData.thscsumB[i-1]+data[j]['thscsumB']));
                            }else{
                                chartsData.thscsumA.push(data[j]['thscsumA']);
                                chartsData.thscsumB.push(data[j]['thscsumB']);
                            }
                        }
                        //当前累计通话时长
                        if(data[j]['thscsumA']) chartsData.callTimeLengthA += parseInt(data[j]['thscsumA']);
                        if(data[j]['thscsumB']) chartsData.callTimeLengthB += parseInt(data[j]['thscsumB']);

                        //接通率
                        chartsData.succcntA.push(data[j]['succcntA']!=undefined?data[j]['succcntA']:0);
                        chartsData.succcntB.push(data[j]['succcntB']!=undefined?data[j]['succcntB']:0);
                        //应答率
                        chartsData.answercntA.push(data[j]['answercntA']!=undefined?data[j]['answercntA']:0);
                        chartsData.answercntB.push(data[j]['answercntB']!=undefined?data[j]['answercntB']:0);
                        flag = true;
                    }
                }
                if(flag == false){
                    if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                        chartsData.callCnt.push(0);
                        if(i!=0){
                            chartsData.thscsumA.push(chartsData.thscsumA[i-1]);
                            chartsData.thscsumB.push(chartsData.thscsumB[i-1]);
                        }else{
                            chartsData.thscsumA.push(0);
                            chartsData.thscsumB.push(0);
                        }
                        //接通率
                        chartsData.succcntA.push(1);
                        chartsData.succcntB.push(1);
                        //应答率
                        chartsData.answercntA.push(1);
                        chartsData.answercntB.push(1);
                    }
                }
            }else{
                if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                    chartsData.callCnt.push(0);
                    chartsData.thscsumA.push(0);
                    chartsData.thscsumB.push(0);
                    //接通率
                    chartsData.succcntA.push(1);
                    chartsData.succcntB.push(1);
                    //应答率
                    chartsData.answercntA.push(1);
                    chartsData.answercntB.push(1);
                }
            }
        }
        return chartsData;
    }

    //判断d1是否大于d2
    function compareDate(d1,d2){
        var date = new Date(), a = d1.split(":"), b = d2.split(":");
        return date.setHours(a[0],a[1]) >= date.setHours(b[0],b[1]);
    }

    function initOption(title,legendData,xAxisData,seriesData){
        var option = {
            title : {text:title},
            tooltip : {
                trigger: 'axis',
                formatter: function (params) {
                    var res = "时间："+params[0].name;
                    for (var i = 0; i < params.length; i++) {
                        res += '<br/>' + params[i].seriesName+ '：' + params[i].value;
                    }
                    return res;
                }
            },
            grid:{top:'60',left:'60',right:'50',bottom:'30'},
            legend : {
                data:legendData
            },
            calculable : true,
            xAxis : [
                {
                    type:'category',
                    boundaryGap:false,
                    splitLine:{lineStyle:{type:'dashed'}},  //设置坐标线为虚线
                    data:xAxisData
                }
            ],
            yAxis : [
                {
                    type:'value',
                    splitLine:{lineStyle:{type:'dashed'}} //设置坐标线为虚线
                }
            ],
            series:seriesData
        };
        return option;
    }
</script>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="row cl">
        <div class="btn-group col-2" style="width: 200px;">
            <span spanType="now" class="btn btn-primary radius">实时监控</span>
            <span spanType="history" class="btn radius">历史数据</span>
        </div>
        <label id="timeDiv" style="display: none;" >
            时间：<input type="text" id="historyDateTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'${yesterday}',onpicked:loadCharts('history'),isShowClear:false,isShowOK:false,isShowToday:false})" class="input-text Wdate" value="${yesterday}" style="width:186px;"/>
        </label>
    </div>

    <div class="mt-20">
        <!-- 1.	话务总量 -->
        <div id="callCnt" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>

        <!-- 2.	累计通话时长 -->
        <div style="position: relative; float: left;">
            <div style="position: absolute; margin-top: 30px; margin-left: 500px; font-size: 12px;">当前累计通话时长[A路:<span id="spanLineA">0</span>&nbsp;&nbsp;&nbsp;B路:<span id="spanLineB">0</span>]</div>
            <div id="callTimeLength" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;"></div>
        </div>

        <!-- 3.	接通率 -->
        <div id="successCnt" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>

        <!-- 4.	应答率 -->
        <div id="answerCnt" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>
    </div>
</div>
</body>
</html>
