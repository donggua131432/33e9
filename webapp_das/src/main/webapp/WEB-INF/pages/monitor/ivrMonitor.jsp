<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>话务监控-云总机</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
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
        var data = loadAjaxData('<c:url value="/monitor/ivrMonitorData"/>',{sType:sType,day:$("#historyDateTime").val()}),
                chartsData = parseData(data['monitor'],data['currentDate'],sType);

        var callCntSeries = [], thscsumSeries = [], succcntSeries = [], answercntSeries = [];
        callCntSeries.push(initSeries('话务总量','#00FF00','#F0FFF0',chartsData.callCnt));
        //累计通话时长
        thscsumSeries.push(initSeries('呼入总机-SIP','#00FF00','#F0FFF0',chartsData.thscsumS));
        thscsumSeries.push(initSeries('呼入总机-非SIP','#FF0000','#FFF0F0',chartsData.thscsumNS));
        thscsumSeries.push(initSeries('呼入-直呼','#7D00FF','#E6D2FF',chartsData.thscsumD));
        thscsumSeries.push(initSeries('呼出-市话','#FF9C00','#FFF0C8',chartsData.thscsumL));
        thscsumSeries.push(initSeries('呼出-长途','#FFFF00','#FFFFF0',chartsData.thscsumNL));

        succcntSeries.push(initSeries('呼入总机-SIP','#00FF00','#F0FFF0',chartsData.succcntS));
        succcntSeries.push(initSeries('呼入总机-非SIP','#FF0000','#FFF0F0',chartsData.succcntNS));
        succcntSeries.push(initSeries('呼入-直呼','#7D00FF','#E6D2FF',chartsData.succcntD));
        succcntSeries.push(initSeries('呼出-市话','#FF9C00','#FFF0C8',chartsData.succcntL));
        succcntSeries.push(initSeries('呼出-长途','#FFFF00','#FFFFF0',chartsData.succcntNL));

        answercntSeries.push(initSeries('呼入总机-SIP','#00FF00','#F0FFF0',chartsData.answercntS));
        answercntSeries.push(initSeries('呼入总机-非SIP','#FF0000','#FFF0F0',chartsData.answercntNS));
        answercntSeries.push(initSeries('呼入-直呼','#7D00FF','#E6D2FF',chartsData.answercntD));
        answercntSeries.push(initSeries('呼出-市话','#FF9C00','#FFF0C8',chartsData.answercntL));
        answercntSeries.push(initSeries('呼出-长途','#FFFF00','#FFFFF0',chartsData.answercntNL));

        $("#spanLineS").text(chartsData.currThscsumS);
        $("#spanLineNS").text(chartsData.currThscsumNS);
        $("#spanLineD").text(chartsData.currThscsumD);
        $("#spanLineL").text(chartsData.currThscsumL);
        $("#spanLineNL").text(chartsData.currThscsumNL);


        callCntDom.setOption(initOption("话务总量",["话务总量"],chartsData.xAxisData, callCntSeries));
        callTimeLengthDom.setOption(initOption("累计通话时长(秒)",["呼入总机-SIP","呼入总机-非SIP","呼入-直呼","呼出-市话","呼出-长途"],chartsData.xAxisData, thscsumSeries));
        successCntDom.setOption(initOption("接通率",["呼入总机-SIP","呼入总机-非SIP","呼入-直呼","呼出-市话","呼出-长途"],chartsData.xAxisData, succcntSeries));
        answerCntDom.setOption(initOption("应答率",["呼入总机-SIP","呼入总机-非SIP","呼入-直呼","呼出-市话","呼出-长途"],chartsData.xAxisData, answercntSeries));
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
                    thscsumS:[],
                    thscsumNS:[],
                    thscsumD:[],
                    thscsumL:[],
                    thscsumNL:[],

                    //接通率
                    succcntS:[],
                    succcntNS:[],
                    succcntD:[],
                    succcntL:[],
                    succcntNL:[],
                    //应答率
                    answercntS:[],
                    answercntNS:[],
                    answercntD:[],
                    answercntL:[],
                    answercntNL:[],

                    //当前累计通话时长
                    currThscsumS:0,
                    currThscsumNS:0,
                    currThscsumD:0,
                    currThscsumL:0,
                    currThscsumNL:0,

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
                    chartsData.thscsumS.push("");
                    chartsData.thscsumNS.push("");
                    chartsData.thscsumD.push("");
                    chartsData.thscsumL.push("");
                    chartsData.thscsumNL.push("");

                    //接通率
                    chartsData.succcntS.push("");
                    chartsData.succcntNS.push("");
                    chartsData.succcntD.push("");
                    chartsData.succcntL.push("");
                    chartsData.succcntNL.push("");

                    //应答率
                    chartsData.answercntS.push("");
                    chartsData.answercntNS.push("");
                    chartsData.answercntD.push("");
                    chartsData.answercntL.push("");
                    chartsData.answercntNL.push("");

                }

                flag = false;
                for (j=0; j<data.length; j++) {
                    if(xAxisData[i] == data[j]['d']){
                        //话务总量
                        chartsData.callCnt.push(data[j]['callcnt']);
                        //累计通话时长
                        if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                            if(i!=0){
                                chartsData.thscsumS.push(parseInt(chartsData.thscsumS[i-1]+data[j]['thscsumS']));
                                chartsData.thscsumNS.push(parseInt(chartsData.thscsumNS[i-1]+data[j]['thscsumNS']));
                                chartsData.thscsumD.push(parseInt(chartsData.thscsumD[i-1]+data[j]['thscsumD']));
                                chartsData.thscsumL.push(parseInt(chartsData.thscsumL[i-1]+data[j]['thscsumL']));
                                chartsData.thscsumNL.push(parseInt(chartsData.thscsumNL[i-1]+data[j]['thscsumNL']));

                            }else{
                                chartsData.thscsumS.push(data[j]['thscsumS']);
                                chartsData.thscsumNS.push(data[j]['thscsumNS']);
                                chartsData.thscsumD.push(data[j]['thscsumD']);
                                chartsData.thscsumL.push(data[j]['thscsumL']);
                                chartsData.thscsumNL.push(data[j]['thscsumNL']);

                            }
                        }

                        //当前累计通话时长
                        if(data[j]['thscsumS']) chartsData.currThscsumS+=parseInt(data[j]['thscsumS']);
                        if(data[j]['thscsumNS']) chartsData.currThscsumNS+=parseInt(data[j]['thscsumNS']);
                        if(data[j]['thscsumD']) chartsData.currThscsumD+=parseInt(data[j]['thscsumD']);
                        if(data[j]['thscsumL']) chartsData.currThscsumL+=parseInt(data[j]['thscsumL']);
                        if(data[j]['thscsumNL']) chartsData.currThscsumNL+=parseInt(data[j]['thscsumNL']);


                        //接通率
                        chartsData.succcntS.push(data[j]['succcntS']!=undefined?data[j]['succcntS']:0);
                        chartsData.succcntNS.push(data[j]['succcntNS']!=undefined?data[j]['succcntNS']:0);
                        chartsData.succcntD.push(data[j]['succcntD']!=undefined?data[j]['succcntD']:0);
                        chartsData.succcntL.push(data[j]['succcntL']!=undefined?data[j]['succcntL']:0);
                        chartsData.succcntNL.push(data[j]['succcntNL']!=undefined?data[j]['succcntNL']:0);

                        //应答率
                        chartsData.answercntS.push(data[j]['answercntS']!=undefined?data[j]['answercntS']:0);
                        chartsData.answercntNS.push(data[j]['answercntNS']!=undefined?data[j]['answercntNS']:0);
                        chartsData.answercntD.push(data[j]['answercntD']!=undefined?data[j]['answercntD']:0);
                        chartsData.answercntL.push(data[j]['answercntL']!=undefined?data[j]['answercntL']:0);
                        chartsData.answercntNL.push(data[j]['answercntNL']!=undefined?data[j]['answercntNL']:0);

                        flag = true;
                    }
                }
                if(flag == false){
                    if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                        chartsData.callCnt.push(0);
                        if(i!=0){

                            chartsData.thscsumS.push(chartsData.thscsumS[i-1]);
                            chartsData.thscsumNS.push(chartsData.thscsumNS[i-1]);
                            chartsData.thscsumD.push(chartsData.thscsumD[i-1]);
                            chartsData.thscsumL.push(chartsData.thscsumL[i-1]);
                            chartsData.thscsumNL.push(chartsData.thscsumNL[i-1]);
                        }else{
                            chartsData.thscsumS.push(0);
                            chartsData.thscsumNS.push(0);
                            chartsData.thscsumD.push(0);
                            chartsData.thscsumL.push(0);
                            chartsData.thscsumNL.push(0);

                        }
                        //接通率
                        chartsData.succcntS.push(1);
                        chartsData.succcntNS.push(1);
                        chartsData.succcntD.push(1);
                        chartsData.succcntL.push(1);
                        chartsData.succcntNL.push(1);

                        //应答率
                        chartsData.answercntS.push(1);
                        chartsData.answercntNS.push(1);
                        chartsData.answercntD.push(1);
                        chartsData.answercntL.push(1);
                        chartsData.answercntNL.push(1);

                    }
                }
            }else{
                if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                    chartsData.callCnt.push(0);
                    chartsData.thscsumS.push(0);
                    chartsData.thscsumNS.push(0);
                    chartsData.thscsumD.push(0);
                    chartsData.thscsumL.push(0);
                    chartsData.thscsumNL.push(0);

                    //接通率
                    chartsData.succcntS.push(1);
                    chartsData.succcntNS.push(1);
                    chartsData.succcntD.push(1);
                    chartsData.succcntL.push(1);
                    chartsData.succcntNL.push(1);

                    //应答率
                    chartsData.answercntS.push(1);
                    chartsData.answercntNS.push(1);
                    chartsData.answercntD.push(1);
                    chartsData.answercntL.push(1);
                    chartsData.answercntNL.push(1);

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
        <label id="timeDiv" style="display: none;">
            时间：<input type="text" id="historyDateTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'${yesterday}',onpicked:loadCharts('history'),isShowClear:false,isShowOK:false,isShowToday:false})" class="input-text Wdate" value="${yesterday}" style="width:186px;"/>
        </label>
    </div>

    <div class="mt-20">
        <!-- 1.	话务总量 -->
        <div id="callCnt" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>

        <!-- 2.	累计通话时长 -->
        <div style="position: relative; float: left;">
            <div style="position: absolute; margin-top: 1px; margin-left: 160px; font-size: 12px;">
                当前累计通话时长[呼入总机-SIP:<span id="spanLineS">0</span>&nbsp;&nbsp;&nbsp;呼入总机-非SIP:<span id="spanLineNS">0</span>
                &nbsp;&nbsp;&nbsp;呼入-直呼:<span id="spanLineD">0</span>&nbsp;&nbsp;&nbsp;呼出-市话:<span id="spanLineL">0</span>&nbsp;&nbsp;&nbsp;呼出-长途:<span id="spanLineNL">0</span>]

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
