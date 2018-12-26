<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/6
  Time: 15:23
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
        var data = loadAjaxData('<c:url value="/monitor/sipPhoneMonitorData"/>',{sType:sType,day:$("#historyDateTime").val()}),
                chartsData = parseData(data['monitor'],data['currentDate'],sType);

        var callCntSeries = [], thscsumSeries = [], succcntSeries = [], answercntSeries = [];
        callCntSeries.push(initSeries('话务总量','#00FF00','#F0FFF0',chartsData.callCnt));
        //累计通话时长
        thscsumSeries.push(initSeries('回拨A路','#00FF00','#F0FFF0',chartsData.thscsumRestA));
        thscsumSeries.push(initSeries('回拨B路','#FF0000','#FFF0F0',chartsData.thscsumRestB));
        thscsumSeries.push(initSeries('SipPhone回拨A路','#7D00FF','#E6D2FF',chartsData.thscsumSipA));
        thscsumSeries.push(initSeries('SipPhone回拨B路','#FF9C00','#FFF0C8',chartsData.thscsumSipB));
        thscsumSeries.push(initSeries('直拨','#FFFF00','#FFFFF0',chartsData.thscsumRestO));
        thscsumSeries.push(initSeries('SipPhone直拨','#0000FF','#DCDCFF',chartsData.thscsumSipO));
        thscsumSeries.push(initSeries('回呼','#00FFFF','#E6FFFF',chartsData.thscsumC));
        //接通率
        succcntSeries.push(initSeries('回拨A路','#00FF00','#F0FFF0',chartsData.succcntRestA));
        succcntSeries.push(initSeries('回拨B路','#FF0000','#FFF0F0',chartsData.succcntRestB));
        succcntSeries.push(initSeries('SipPhone回拨A路','#7D00FF','#E6D2FF',chartsData.succcntSipA));
        succcntSeries.push(initSeries('SipPhone回拨B路','#FF9C00','#FFF0C8',chartsData.succcntSipB));
        succcntSeries.push(initSeries('直拨','#FFFF00','#FFFFF0',chartsData.succcntRestO));
        succcntSeries.push(initSeries('SipPhone直拨','#0000FF','#DCDCFF',chartsData.succcntSipO));
        succcntSeries.push(initSeries('回呼','#00FFFF','#E6FFFF',chartsData.succcntC));
        //应答率
        answercntSeries.push(initSeries('回拨A路','#00FF00','#F0FFF0',chartsData.answercntRestA));
        answercntSeries.push(initSeries('回拨B路','#FF0000','#FFF0F0',chartsData.answercntRestB));
        answercntSeries.push(initSeries('SipPhone回拨A路','#7D00FF','#E6D2FF',chartsData.answercntSipA));
        answercntSeries.push(initSeries('SipPhone回拨B路','#FF9C00','#FFF0C8',chartsData.answercntSipB));
        answercntSeries.push(initSeries('直拨','#FFFF00','#FFFFF0',chartsData.answercntRestO));
        answercntSeries.push(initSeries('SipPhone直拨','#0000FF','#DCDCFF',chartsData.answercntSipO));
        answercntSeries.push(initSeries('回呼','#00FFFF','#E6FFFF',chartsData.answercntC));


        $("#spanLineRestA").text(chartsData.currThscsumRestA);
        $("#spanLineRestB").text(chartsData.currThscsumRestB);
        $("#spanLineSipA").text(chartsData.currThscsumSipA);
        $("#spanLineSipB").text(chartsData.currThscsumSipB);
        $("#spanLineRestO").text(chartsData.currThscsumRestO);
        $("#spanLineSipO").text(chartsData.currThscsumSipO);
        $("#spanLineC").text(chartsData.currThscsumC);
        callCntDom.setOption(initOption("话务总量",["话务总量"],chartsData.xAxisData, callCntSeries));
        callTimeLengthDom.setOption(initOption("累计通话时长(秒)",["回拨A路","回拨B路","SipPhone回拨A路","SipPhone回拨B路","直拨","SipPhone直拨","回呼"],chartsData.xAxisData, thscsumSeries));
        successCntDom.setOption(initOption("接通率",["回拨A路","回拨B路","SipPhone回拨A路","SipPhone回拨B路","直拨","SipPhone直拨","回呼"],chartsData.xAxisData, succcntSeries));
        answerCntDom.setOption(initOption("应答率",["回拨A路","回拨B路","SipPhone回拨A路","SipPhone回拨B路","直拨","SipPhone直拨","回呼"],chartsData.xAxisData, answercntSeries));
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
                    //累计通话时长
                    thscsumRestA:[],
                    thscsumRestB:[],
                    thscsumSipA:[],
                    thscsumSipB:[],
                    thscsumRestO:[],
                    thscsumSipO:[],
                    thscsumC:[],
                    //接通率
                    succcntRestA:[],
                    succcntRestB:[],
                    succcntSipA:[],
                    succcntSipB:[],
                    succcntRestO:[],
                    succcntSipO:[],
                    succcntC:[],
                    //应答率
                    answercntRestA:[],
                    answercntRestB:[],
                    answercntSipA:[],
                    answercntSipB:[],
                    answercntRestO:[],
                    answercntSipO:[],
                    answercntC:[],
                    //当前累计通话时长
                    currThscsumRestA:0,
                    currThscsumRestB:0,
                    currThscsumSipA:0,
                    currThscsumSipB:0,
                    currThscsumRestO:0,
                    currThscsumSipO:0,
                    currThscsumC:0
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
                    //话务统计
                    chartsData.callCnt.push("");
                    //累计通话时长
                    chartsData.thscsumRestA.push("");
                    chartsData.thscsumRestB.push("");
                    chartsData.thscsumSipA.push("");
                    chartsData.thscsumSipB.push("");
                    chartsData.thscsumRestO.push("");
                    chartsData.thscsumSipO.push("");
                    chartsData.thscsumC.push("");
                    //接通率
                    chartsData.succcntRestA.push("");
                    chartsData.succcntRestB.push("");
                    chartsData.succcntSipA.push("");
                    chartsData.succcntSipB.push("");
                    chartsData.succcntRestO.push("");
                    chartsData.succcntSipO.push("");
                    chartsData.succcntC.push("");
                    //应答率
                    chartsData.answercntRestA.push("");
                    chartsData.answercntRestB.push("");
                    chartsData.answercntSipA.push("");
                    chartsData.answercntSipB.push("");
                    chartsData.answercntRestO.push("");
                    chartsData.answercntSipO.push("");
                    chartsData.answercntC.push("");
                }

                flag = false;
                for (j=0; j<data.length; j++) {
                    if(xAxisData[i] == data[j]['d']){
                        //话务总量
                        chartsData.callCnt.push(data[j]['callcnt']);
                        //累计通话时长
                        if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                            if(i!=0){
                                chartsData.thscsumRestA.push(parseInt(chartsData.thscsumRestA[i-1]+data[j]['thscsumRestA']));
                                chartsData.thscsumRestB.push(parseInt(chartsData.thscsumRestB[i-1]+data[j]['thscsumRestB']));
                                chartsData.thscsumSipA.push(parseInt(chartsData.thscsumSipA[i-1]+data[j]['thscsumSipA']));
                                chartsData.thscsumSipB.push(parseInt(chartsData.thscsumSipB[i-1]+data[j]['thscsumSipB']));
                                chartsData.thscsumRestO.push(parseInt(chartsData.thscsumRestO[i-1]+data[j]['thscsumRestO']));
                                chartsData.thscsumSipO.push(parseInt(chartsData.thscsumSipO[i-1]+data[j]['thscsumSipO']));
                                chartsData.thscsumC.push(parseInt(chartsData.thscsumC[i-1]+data[j]['thscsumC']));
                            }else{
                                chartsData.thscsumRestA.push(data[j]['thscsumRestA']);
                                chartsData.thscsumRestB.push(data[j]['thscsumRestB']);
                                chartsData.thscsumSipA.push(data[j]['thscsumSipA']);
                                chartsData.thscsumSipB.push(data[j]['thscsumSipB']);
                                chartsData.thscsumRestO.push(data[j]['thscsumRestO']);
                                chartsData.thscsumSipO.push(data[j]['thscsumSipO']);
                                chartsData.thscsumC.push(data[j]['thscsumC']);
                            }
                        }
                        //当前累计通话时长
                        if(data[j]['thscsumRestA']) chartsData.currThscsumRestA+=parseInt(data[j]['thscsumRestA']);
                        if(data[j]['thscsumRestB']) chartsData.currThscsumRestB+=parseInt(data[j]['thscsumRestB']);
                        if(data[j]['thscsumSipA']) chartsData.currThscsumSipA+=parseInt(data[j]['thscsumSipA']);
                        if(data[j]['thscsumSipB']) chartsData.currThscsumSipB+=parseInt(data[j]['thscsumSipB']);
                        if(data[j]['thscsumRestO']) chartsData.currThscsumRestO+=parseInt(data[j]['thscsumRestO']);
                        if(data[j]['thscsumSipO']) chartsData.currThscsumSipO+=parseInt(data[j]['thscsumSipO']);
                        if(data[j]['thscsumC']) chartsData.currThscsumC+=parseInt(data[j]['thscsumC']);

                        //接通率
                        chartsData.succcntRestA.push(data[j]['succcntRestA']!=undefined?data[j]['succcntRestA']:0);
                        chartsData.succcntRestB.push(data[j]['succcntRestB']!=undefined?data[j]['succcntRestB']:0);
                        chartsData.succcntSipA.push(data[j]['succcntSipA']!=undefined?data[j]['succcntSipA']:0);
                        chartsData.succcntSipB.push(data[j]['succcntSipB']!=undefined?data[j]['succcntSipB']:0);
                        chartsData.succcntRestO.push(data[j]['succcntRestO']!=undefined?data[j]['succcntRestO']:0);
                        chartsData.succcntSipO.push(data[j]['succcntSipO']!=undefined?data[j]['succcntSipO']:0);
                        chartsData.succcntC.push(data[j]['succcntC']!=undefined?data[j]['succcntC']:0);

                        //应答率
                        chartsData.answercntRestA.push(data[j]['answercntRestA']!=undefined?data[j]['answercntRestA']:0);
                        chartsData.answercntRestB.push(data[j]['answercntRestB']!=undefined?data[j]['answercntRestB']:0);
                        chartsData.answercntSipA.push(data[j]['answercntSipA']!=undefined?data[j]['answercntSipA']:0);
                        chartsData.answercntSipB.push(data[j]['answercntSipB']!=undefined?data[j]['answercntSipB']:0);
                        chartsData.answercntRestO.push(data[j]['answercntRestO']!=undefined?data[j]['answercntRestO']:0);
                        chartsData.answercntSipO.push(data[j]['answercntSipO']!=undefined?data[j]['answercntSipO']:0);
                        chartsData.answercntC.push(data[j]['answercntC']!=undefined?data[j]['answercntC']:0);

                        flag = true;
                    }
                }
                if(flag == false){
                    if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                        chartsData.callCnt.push(0);
                        if(i!=0){
                            chartsData.thscsumRestA.push(chartsData.thscsumRestA[i-1]);
                            chartsData.thscsumRestB.push(chartsData.thscsumRestB[i-1]);
                            chartsData.thscsumSipA.push(chartsData.thscsumSipA[i-1]);
                            chartsData.thscsumSipB.push(chartsData.thscsumSipB[i-1]);
                            chartsData.thscsumRestO.push(chartsData.thscsumRestO[i-1]);
                            chartsData.thscsumSipO.push(chartsData.thscsumSipO[i-1]);
                            chartsData.thscsumC.push(chartsData.thscsumC[i-1]);
                        }else{
                            chartsData.thscsumRestA.push(0);
                            chartsData.thscsumRestB.push(0);
                            chartsData.thscsumSipA.push(0);
                            chartsData.thscsumSipB.push(0);
                            chartsData.thscsumRestO.push(0);
                            chartsData.thscsumSipO.push(0);
                            chartsData.thscsumC.push(0);
                        }
                        //接通率
                        chartsData.succcntRestA.push(1);
                        chartsData.succcntRestB.push(1);
                        chartsData.succcntSipA.push(1);
                        chartsData.succcntSipB.push(1);
                        chartsData.succcntRestO.push(1);
                        chartsData.succcntSipO.push(1);
                        chartsData.succcntC.push(1);
                        //应答率
                        chartsData.answercntRestA.push(1);
                        chartsData.answercntRestB.push(1);
                        chartsData.answercntSipA.push(1);
                        chartsData.answercntSipB.push(1);
                        chartsData.answercntRestO.push(1);
                        chartsData.answercntSipO.push(1);
                        chartsData.answercntC.push(1);
                    }
                }
            }else{
                if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                    chartsData.callCnt.push(0);
                    chartsData.thscsumRestA.push(0);
                    chartsData.thscsumRestB.push(0);
                    chartsData.thscsumSipA.push(0);
                    chartsData.thscsumSipB.push(0);
                    chartsData.thscsumRestO.push(0);
                    chartsData.thscsumSipO.push(0);
                    chartsData.thscsumC.push(0);
                    //接通率
                    chartsData.succcntRestA.push(1);
                    chartsData.succcntRestB.push(1);
                    chartsData.succcntSipA.push(1);
                    chartsData.succcntSipB.push(1);
                    chartsData.succcntRestO.push(1);
                    chartsData.succcntSipO.push(1);
                    chartsData.succcntC.push(1);
                    //应答率
                    chartsData.answercntRestA.push(1);
                    chartsData.answercntRestB.push(1);
                    chartsData.answercntSipA.push(1);
                    chartsData.answercntSipB.push(1);
                    chartsData.answercntRestO.push(1);
                    chartsData.answercntSipO.push(1);
                    chartsData.answercntC.push(1);
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
                top:'30',
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

        <!-- 2.	累计通话时长   "回拨A路","回拨B路","SipPhone回拨A路","SipPhone回拨B路","直拨","SipPhone直拨","回呼"-->
        <div style="position: relative; float: left;">
            <div style="position: absolute; margin-top: 1px; margin-left: 260px; font-size: 12px;">
                当前累计通话时长[回拨A路:<span id="spanLineRestA">0</span>&nbsp;&nbsp;&nbsp;回拨B路:<span id="spanLineRestB">0</span>
                &nbsp;&nbsp;&nbsp;SipPhone回拨A路:<span id="spanLineSipA">0</span>&nbsp;&nbsp;&nbsp;SipPhone回拨B路:<span id="spanLineSipB">0</span>
            </div>
            <div style="position: absolute; margin-top: 16px; margin-left: 360px; font-size: 12px;">
                直拨:<span id="spanLineRestO">0</span>&nbsp;&nbsp;&nbsp;SipPhone直拨:<span id="spanLineSipO">0</span>
                &nbsp;&nbsp;&nbsp;回呼:<span id="spanLineC">0</span>]
            </div>
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
