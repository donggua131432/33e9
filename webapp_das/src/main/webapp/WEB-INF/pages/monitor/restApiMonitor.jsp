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

    var reqSumDom,reqTypeDom,errorCodeDom, initDataInterval;

    $(function(){
        //渲染监控图
        reqSumDom = echarts.init(document.getElementById("reqSumMonitor"));
        reqTypeDom = echarts.init(document.getElementById("reqTypeMonitor"));
        errorCodeDom = echarts.init(document.getElementById("errorCodeStat"));

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
        var data = loadAjaxData('<c:url value="/monitor/restApiMonitorData"/>',{sType:sType,day:$("#historyDateTime").val()}),
             chartsData = parseData(data['monitor'],data['monitorErrorCode'],data['currentDate'],sType);

        var reqSumSeries = [], reqTypeSeries = [], errorCodeSeries = [];
        reqSumSeries.push(initSeries('请求总数','#00FF00','#F0FFF0',chartsData.respCntSum));
        reqSumSeries.push(initSeries('请求成功数','#FF0000','#FFF0F0',chartsData.respSuccCntsum));

        reqTypeSeries.push(initSeries('通用接口','#00FF00','#F0FFF0',chartsData.currency));
        reqTypeSeries.push(initSeries('专线语音','#FF0000','#FFF0F0',chartsData.rest));
        reqTypeSeries.push(initSeries('专号通','#7D00FF','#E6D2FF',chartsData.mask));
        reqTypeSeries.push(initSeries('物联网卡','#FF9C00','#FFF0C8',chartsData.card));
        reqTypeSeries.push(initSeries('虚拟号','#FFFF00','#FFFFF0',chartsData.vn));
        reqTypeSeries.push(initSeries('语音通知','#0000FF','#DCDCFF',chartsData.voice));
        reqTypeSeries.push(initSeries('SipPhone','#00FFFF','#E6FFFF',chartsData.sipPhone));

        errorCodeSeries.push(initBarSeries("错误码数",chartsData.respCodeSum));

        reqSumDom.setOption(initOption("接口请求总览",["请求总数","请求成功数"],chartsData.xAxisData, reqSumSeries));
        reqTypeDom.setOption(initOption("接口请求监控",["通用接口","专线语音","专号通","物联网卡","虚拟号","语音通知","SipPhone"],chartsData.xAxisData, reqTypeSeries));
        errorCodeDom.setOption(initOption("错误码统计",["错误码数"],chartsData.respCode, errorCodeSeries));
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

    function initBarSeries(title,data){
        var barSeriesOption ={
            name:title,
            type:'bar',
            barMaxWidth:30,
            data:data
        }
        return barSeriesOption;
    }

    function parseData(data,codeDate,dateTime,sType){
        var xAxisData = [],
            subDate = dateTime.substring(dateTime.length-2,dateTime.length),
            strDate =  dateTime.substring(0,dateTime.length-2)+parseInt(subDate-(subDate%5)),
            currentDate = strDate.substring(strDate.length-5, strDate.length),
            chartsData = {
                xAxisData: xAxisData,
                respCntSum:[],
                respSuccCntsum:[],
                currency:[],
                rest:[],
                mask:[],
                card:[],
                vn:[],
                voice:[],
                sipPhone:[],
                respCode:[],
                respCodeSum:[]
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

        var i, j,m,flag;
        for(i=0; i<xAxisData.length; i++){
            if(data){
                if(!compareDate(currentDate,xAxisData[i]) && sType=="now"){
                    chartsData.respCntSum.push("");
                    chartsData.respSuccCntsum.push("");
                    chartsData.currency.push("");
                    chartsData.rest.push("");
                    chartsData.mask.push("");
                    chartsData.card.push("");
                    chartsData.vn.push("");
                    chartsData.voice.push("");
                    chartsData.sipPhone.push("");
                }

                flag = false;
                for (j=0; j<data.length; j++) {
                    if(xAxisData[i] == data[j]['d']){
                        chartsData.respCntSum.push(data[j]['respCntSum']!=undefined?data[j]['respCntSum']:0);
                        chartsData.respSuccCntsum.push(data[j]['respSuccCntsum']!=undefined?data[j]['respSuccCntsum']:0);

                        chartsData.currency.push(data[j]['currency']!=undefined?data[j]['currency']:0);
                        chartsData.rest.push(data[j]['rest']!=undefined?data[j]['rest']:0);
                        chartsData.mask.push(data[j]['mask']!=undefined?data[j]['mask']:0);
                        chartsData.card.push(data[j]['card']!=undefined?data[j]['card']:0);
                        chartsData.vn.push(data[j]['vn']!=undefined?data[j]['vn']:0);
                        chartsData.voice.push(data[j]['voice']!=undefined?data[j]['voice']:0);
                        chartsData.sipPhone.push(data[j]['sipPhone']!=undefined?data[j]['sipPhone']:0);
                        flag = true;
                    }
                }
                if(flag == false){
                    if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                        chartsData.respCntSum.push(0);
                        chartsData.respSuccCntsum.push(0);
                        chartsData.currency.push(0);
                        chartsData.rest.push(0);
                        chartsData.mask.push(0);
                        chartsData.card.push(0);
                        chartsData.vn.push(0);
                        chartsData.voice.push(0);
                        chartsData.sipPhone.push(0);
                    }
                }
            }else{
                if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                    chartsData.respCntSum.push(0);
                    chartsData.respSuccCntsum.push(0);
                    chartsData.currency.push(0);
                    chartsData.rest.push(0);
                    chartsData.mask.push(0);
                    chartsData.card.push(0);
                    chartsData.vn.push(0);
                    chartsData.voice.push(0);
                    chartsData.sipPhone.push(0);
                }
            }
        }


        for(m=0; m<codeDate.length; m++){
            chartsData.respCode.push(codeDate[m]['resp_code']);
            chartsData.respCodeSum.push(codeDate[m]['respCodeSum']);
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
                    var res = params[0].seriesName == "错误码数" ? "错误码："+params[0].name : "时间："+params[0].name;
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
                    //boundaryGap:false,
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
        <!-- 1.	请求接口总量监控 -->
        <div id="reqSumMonitor" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>

        <!-- 2.	请求接口类型监控 -->
        <div id="reqTypeMonitor" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>

        <!-- 3.	错误码统计 -->
        <div id="errorCodeStat" style="width:1632px; height:500px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>
    </div>
</div>
</body>
</html>
