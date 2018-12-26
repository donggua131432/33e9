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
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/echarts/china.js"></script>
    <script type="text/javascript">

        var callCntDom,callTimeLengthDom,successCntDom,answerCntDom, callInMapDom, initDataInterval;

        $(function(){
            $("#sid").select2({
                placeholder: "全部",
                ajax: {
                    url: "<c:url value='/monitor/getCompanyNameAndSidForSelect2'/>",
                    dataType: 'json',
                    delay: 250,
                    data: function (params) {
                        return {
                            params: {
                                name:params.term,//输入框的内容
                                busType:'01', //用户的业务类型
                                sType:function(){
                                    return $(".btn-group span.btn-primary").attr("spanType");
                                },
                                day:function(){
                                    return $("#historyDateTime").val();
                                }
                            },
                            page: params.page
                        };
                    },
                    processResults: function (data, params) {
                        params.page = params.page || 1;
                        var items = [];
                        if (params.page == 1) {
                            items.push({id:"-1", text:"全部"});
                        }

                        $.each(data.rows,function(i,v){
                            items.push({id:v.sid, text:v.name});
                        });

                        return {
                            results: items,
                            pagination: {
                                more: data.toIndex < data.total
                            }
                        };
                    },
                    cache: true
                }
            });

            $("#sid").change(function(){
                var data = loadAjaxData('<c:url value="/monitor/getCcInfo"/>',{sid:$(this).val(),sType:$(".btn-group span.btn-primary").attr("spanType"),day:$("#historyDateTime").val()});
                $("#ccInfo").html('<option value="">全部</option>');
                if(data["ccInfoList"].length>0){
                    $(data["ccInfoList"]).each(function(){
                        if($(this)[0]["subid"]){
                            $("#ccInfo").append('<option value="'+$(this)[0]["subid"]+'">'+$(this)[0]["ccname"]+'</option>');
                        }
                    });
                    $("#appid").val(data["ccInfoList"][0]["appid"]);
                }else{
                    $("#appid").val("");
                }
                loadCharts($(".btn-group span.btn-primary").attr("spanType"));
            });

            $("#ccInfo").on("change",function(){
                loadCharts($(".btn-group span.btn-primary").attr("spanType"));
            });

            //渲染监控图
            callCntDom = echarts.init(document.getElementById("callCnt"));
            callTimeLengthDom = echarts.init(document.getElementById("callTimeLength"));
            successCntDom = echarts.init(document.getElementById("successCnt"));
            answerCntDom = echarts.init(document.getElementById("answerCnt"));
            callInMapDom = echarts.init(document.getElementById("callInMap"));

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
            var data = loadAjaxData('<c:url value="/monitor/cloudMonitorData"/>',{appid:$("#appid").val(),subid:$("#ccInfo").val(),sType:sType,day:$("#historyDateTime").val()}),
                    chartsData = parseData(data['monitor'],data['monitorProv'],data['currentDate'],sType);

            var callCntSeries = [], thscsumSeries = [], succcntSeries = [], answercntSeries = [];
            callCntSeries.push(initSeries('话务总量','#00FF00','#F0FFF0',chartsData.callCnt));
            thscsumSeries.push(initSeries('呼入','#00FF00','#F0FFF0',chartsData.thscsumI));
            thscsumSeries.push(initSeries('呼出','#FF0000','#FFF0F0',chartsData.thscsumO));
            succcntSeries.push(initSeries('呼入','#00FF00','#F0FFF0',chartsData.succcntI));
            succcntSeries.push(initSeries('呼出','#FF0000','#FFF0F0',chartsData.succcntO));
            answercntSeries.push(initSeries('呼入','#00FF00','#F0FFF0',chartsData.answercntI));
            answercntSeries.push(initSeries('呼出','#FF0000','#FFF0F0',chartsData.answercntO));

            $("#spanLineI").text(chartsData.callTimeLengthI);
            $("#spanLineO").text(chartsData.callTimeLengthO);
            callCntDom.setOption(initOption("话务总量",["话务总量"],chartsData.xAxisData, callCntSeries));
            callTimeLengthDom.setOption(initOption("累计通话时长(秒)",["呼入","呼出"],chartsData.xAxisData, thscsumSeries));
            successCntDom.setOption(initOption("接通率",["呼入","呼出"],chartsData.xAxisData, succcntSeries));
            answerCntDom.setOption(initOption("应答率",["呼入","呼出"],chartsData.xAxisData, answercntSeries));

            callInMapDom.setOption(initOptionMap(chartsData.areaDataAll));
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

        function parseData(data,provDate,dateTime,sType){
            var xAxisData = [],
                    subDate = dateTime.substring(dateTime.length-2,dateTime.length),
                    strDate =  dateTime.substring(0,dateTime.length-2)+parseInt(subDate-(subDate%5)),
                    currentDate = strDate.substring(strDate.length-5, strDate.length),
                    chartsData = {
                        xAxisData: xAxisData,
                        callCnt:[],
                        thscsumI:[],
                        thscsumO:[],
                        succcntI:[],
                        succcntO:[],
                        answercntI:[],
                        answercntO:[],
                        callTimeLengthI:0,
                        callTimeLengthO:0,
                        areaDataAll:[
                            {name:'北京市',value:0},{name:'天津市',value:0},{name:'上海市',value:0}, {name:'重庆市',value:0},
                            {name:'河北省',value:0},{name:'河南省',value:0},{name:'云南省',value:0}, {name:'辽宁省',value:0},
                            {name:'黑龙江省',value:0},{name:'湖南省',value:0},{name:'安徽省',value:0}, {name:'山东省',value:0},
                            {name:'新疆',value:0},{name:'江苏省',value:0}, {name:'浙江省',value:0}, {name:'江西省',value:0},
                            {name:'湖北省',value:0},{name:'广西省',value:0},{name:'甘肃省',value:0}, {name:'山西省',value:0},
                            {name:'内蒙古',value:0},{name:'陕西省',value:0},{name:'吉林省',value:0}, {name:'福建省',value:0},
                            {name:'贵州省',value:0},{name:'广东省',value:0},{name:'青海省',value:0},{name:'西藏',value:0},
                            {name:'四川省',value:0},{name:'宁夏',value:0},{name:'海南省',value:0},{name:'台湾省',value:0},
                            {name:'香港',value:0},{name:'南海诸岛',value:0}
                        ]
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

            var i, j, n,flag;
            for(i=0; i<xAxisData.length; i++){
                if(data){
                    if(!compareDate(currentDate,xAxisData[i]) && sType=="now"){
                        chartsData.callCnt.push("");
                        chartsData.thscsumI.push("");
                        chartsData.thscsumO.push("");
                        //接通率
                        chartsData.succcntI.push("");
                        chartsData.succcntO.push("");
                        //应答率
                        chartsData.answercntI.push("");
                        chartsData.answercntO.push("");
                    }

                    flag = false;
                    for (j=0; j<data.length; j++) {
                        if(xAxisData[i] == data[j]['d']){
                            //话务总量
                            chartsData.callCnt.push(data[j]['callcnt']);
                            //累计通话时长
                            if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                                if(i!=0){
                                    chartsData.thscsumI.push(parseInt(chartsData.thscsumI[i-1]+data[j]['thscsumI']));
                                    chartsData.thscsumO.push(parseInt(chartsData.thscsumO[i-1]+data[j]['thscsumO']));
                                }else{
                                    chartsData.thscsumI.push(data[j]['thscsumI']);
                                    chartsData.thscsumO.push(data[j]['thscsumO']);
                                }
                            }
                            //当前累计通话时长
                            if(data[j]['thscsumI']) chartsData.callTimeLengthI += parseInt(data[j]['thscsumI']);
                            if(data[j]['thscsumO']) chartsData.callTimeLengthO += parseInt(data[j]['thscsumO']);

                            //接通率
                            chartsData.succcntI.push(data[j]['succcntI']!=undefined?data[j]['succcntI']:0);
                            chartsData.succcntO.push(data[j]['succcntO']!=undefined?data[j]['succcntO']:0);
                            //应答率
                            chartsData.answercntI.push(data[j]['answercntI']!=undefined?data[j]['answercntI']:0);
                            chartsData.answercntO.push(data[j]['answercntO']!=undefined?data[j]['answercntO']:0);
                            flag = true;
                        }
                    }
                    if(flag == false){
                        if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                            chartsData.callCnt.push(0);
                            if(i!=0){
                                chartsData.thscsumI.push(chartsData.thscsumI[i-1]);
                                chartsData.thscsumO.push(chartsData.thscsumO[i-1]);
                            }else{
                                chartsData.thscsumI.push(0);
                                chartsData.thscsumO.push(0);
                            }
                            //接通率
                            chartsData.succcntI.push(1);
                            chartsData.succcntO.push(1);
                            //应答率
                            chartsData.answercntI.push(1);
                            chartsData.answercntO.push(1);
                        }
                    }
                }else{
                    if((sType=="now" && compareDate(currentDate,xAxisData[i])) || sType=="history"){
                        chartsData.callCnt.push(0);
                        chartsData.thscsumI.push(0);
                        chartsData.thscsumO.push(0);
                        //接通率
                        chartsData.succcntI.push(1);
                        chartsData.succcntO.push(1);
                        //应答率
                        chartsData.answercntI.push(1);
                        chartsData.answercntO.push(1);
                    }
                }
            }

            //设置呼入地区分布图数据
            $(provDate).each(function(m){
                for(n=0; n<chartsData.areaDataAll.length; n++){
                    if(provDate[m]["name"] == chartsData.areaDataAll[n]["name"]){
                        chartsData.areaDataAll[n] = $.extend(chartsData.areaDataAll[n],provDate[m]);
                    }
                }
            });
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


        function initOptionMap(areaDataAll){
            var mapOption = {
                title : {text:'呼入地区分布图',subtext: '颜色深浅表示呼叫量高低'},
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
            return mapOption;
        }
    </script>
</head>

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
        <input type="hidden" id="appid" value=""/>
        客户名称：<select id="sid" class="input-text" style="width: 300px"></select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        呼叫中心名称：<select id="ccInfo" class="input-text radius" style="width: 300px;"></select>
    </div>

    <div class="mt-20">
        <!-- 1.	话务总量 -->
        <div id="callCnt" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>

        <!-- 2.	累计通话时长 -->
        <div style="position: relative; float: left;">
            <div style="position: absolute; margin-top: 30px; margin-left: 500px; font-size: 12px;">
                当前累计通话时长[呼入:<span id="spanLineI">0</span>&nbsp;&nbsp;&nbsp;呼出:<span id="spanLineO">0</span>]
            </div>
            <div id="callTimeLength" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;"></div>
        </div>

        <!-- 3.	接通率 -->
        <div id="successCnt" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>

        <!-- 4.	应答率 -->
        <div id="answerCnt" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>

        <!-- 呼入地区分布图 -->
        <div id="callInMap" style="width:1632px; height:800px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;"></div>
    </div>
</div>
</body>
</html>
