<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>应用列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <style>
        .wordbreak{word-break:break-all;}
    </style>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="btn-group" style="float: none">
        <span data-stype="n" class="btn btn-primary radius">实时</span>
        <span data-stype="h" class="btn btn-default radius">查看历史数据</span>
    </div>

    <div id="timediv" style="margin-left: 200px;display: none;">
        时间：<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="datemin" datatype="*" class="input-text Wdate" value="${yestoday}" style="width:200px;" onchange="reinitcharts();">
    </div>

    <div class="mt-20">
        <!-- 1.	累计通话时长 -->
        <%--<div id="thscTotal" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>--%>

        <!-- 2.	平均通话时长 -->
        <%--<div id="thscAvg" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
        </div>--%>

        <!-- 3.	计费通话时长 -->
        <%--<div id="jfscTotal" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>--%>

        <!-- 4.	接通率 -->
        <div id="succcnt" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>

        <!-- 5.	应答率 -->
        <div id="answercnt" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
        </div>

        <!-- 6.	话务总量 -->
        <div id="callcnt" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>

        <!-- 7.	录音比 -->
        <%--<div id="recording" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>--%>

        <!-- 8.	话务总量 -->
        <div id="response" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
        </div>
    </div>
</div>
<script type="text/javascript">

    // 基于准备好的dom，初始化echarts实例
    //var thscTotal = echarts.init(document.getElementById('thscTotal'));
    //var thscAvg = echarts.init(document.getElementById('thscAvg'));
    //var jfscTotal = echarts.init(document.getElementById('jfscTotal'));
    var succcnt = echarts.init(document.getElementById('succcnt'));
    var answercnt = echarts.init(document.getElementById('answercnt'));
    var callcnt = echarts.init(document.getElementById('callcnt'));
    //var recording = echarts.init(document.getElementById('recording'));
    var response = echarts.init(document.getElementById('response'));

    $(function(){
        $(".btn-group span").on("click", function(){
            $(this).siblings().removeClass("btn-primary").addClass("btn-default");
            $(this).removeClass("btn-default").addClass("btn-primary");
            var stype = $(this).attr("data-stype");

            if (stype == 'n') {
                $(".btn-group").css({"float":"none"});
                $("#timediv").hide();
            }
            if (stype == 'h') {
                $(".btn-group").css({"float":"left"});
                $("#timediv").show();
            }

            initcharts(stype, '${yestoday}');

        });

        initcharts('n');
    });

    // 从新加载报表平台
    function reinitcharts() {
        var date = $("#datemin").val();
        if (date) {
            initcharts('h', date);
        }
    }

    function initcharts(stype,day){
        var data = loadData('<c:url value="/monitor/restscan"/>',{stype:stype,day:day});
        var xyAxis = parseData(data.monitor,stype);

        var responseData = data.response;

        // 使用刚指定的配置项和数据显示图表。
        //thscTotal.setOption(initOption("累计通话时长", ["A路","B路"], xyAxis['d'], [xyAxis['thscsumA'],xyAxis['thscsumB']] ));
        //thscAvg.setOption(initOption("平均通话时长", ["A路","B路"], xyAxis['d'], [xyAxis['thscAvgA'],xyAxis['thscAvgB']] ));
        //jfscTotal.setOption(initOption("计费通话时长", ["计费通话时长"], xyAxis['d'], [xyAxis['jfscsum']] ));
        succcnt.setOption(initOption("接通率", ["A路","B路"], xyAxis['d'], [xyAxis['succcntA'],xyAxis['succcntB']] ));
        answercnt.setOption(initOption("应答率", ["A路","B路"], xyAxis['d'], [xyAxis['answercntA'],xyAxis['answercntB']] ));
        callcnt.setOption(initOption("话务总量", ["话务总量"], xyAxis['d'], [xyAxis['callcnt']] ));
        //recording.setOption(initOption("录音比", ["录音比"], xyAxis['d'], [xyAxis['rcdCnt']]));
        response.setOption(initPieOption("接口请求失败原因", responseData));
    }

    function parseData(data,stype){
        var d = [];

        var pdata = {
            d: d, thscsumA:[], thscsumB:[],
            thscAvgA:[], thscAvgB:[], jfscsum:[],
            succcntA:[], succcntB:[], answercntA:[],
            answercntB:[], callcnt:[], rcdCnt:[]
        };

        if (data) {
            for (var j=0; j<data.length; j++) {
                pdata.d.push(data[j]['d']);

                pdata.thscsumA.push(data[j]['thscsumA']);
                pdata.thscsumB.push(data[j]['thscsumB']);
                pdata.thscAvgA.push(data[j]['thscAvgA']);
                pdata.thscAvgB.push(data[j]['thscAvgB']);
                pdata.jfscsum.push(data[j]['jfscsum']);
                pdata.succcntA.push(data[j]['succcntA']);
                pdata.succcntB.push(data[j]['succcntB']);
                pdata.answercntA.push(data[j]['answercntA']);
                pdata.answercntB.push(data[j]['answercntB']);
                pdata.callcnt.push(data[j]['callcnt']);
                pdata.rcdCnt.push(ifnull(data[j]['rcdCnt'],0));
            }
        }

        return pdata;
    }

    // 加载数据
    function loadData(url,params) {
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

    // 初始化配置项
    function initOption(title,legend,xAxis,yAxis) {
        var series = [];
        for (var i=0; i<yAxis.length; i++) {
            series.push({
                name: legend[i], //'A路',
                type:'line',
                //stack: '总量',
                data:yAxis[i]
            });
        }

        var option = {
            title: {
                text: title //'累计通话时长'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:legend //['A路','B路']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: xAxis //['周一','周二','周三','周四','周五','周六','周日','周一','周二','周三','周四','周五','周六','周日','周一','周二','周三','周四','周五','周六','周日']
            },
            yAxis: {
                type: 'value'
            },
            series: series /**[
                {
                    name: legend[0], //'A路',
                    type:'line',
                    stack: '总量',
                    data:yAxis //[150, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190, 330, 410]
                },
                {
                    name:legend[1],
                    type:'line',
                    stack: '总量',
                    data:yAxis //[320, 332, 301, 334, 390, 330, 320,320, 332, 301, 334, 390, 330, 320,320, 332, 301, 334, 390, 330, 320]
                }
            ]
            **/
        };

        return option;
    }

    // 初始化饼状图参数
    function initPieOption(appTitle,response){
        var appGroupData = [];
        var appValueGroupData = [];
        if (response) {
            $.each(response,function(n,v){
                appGroupData.push(v.resp_code);
                appValueGroupData.push(v.resp_cnt);
            });
        }

        if (appGroupData.length == 0) {
            appGroupData.push('0');
            appValueGroupData.push(0);
        }

        var option = {
            title: {text: appTitle},
            tooltip: {show: true, formatter: '{b0}：{c0}', backgroundColor: '#5791E1'},
            xAxis: {
                name: '错误码',
                data: appGroupData
            },
            yAxis: {
                type: 'value',
                        scale: true,
                        name: '次数',
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
                        formatter: '{c}'
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#5791E1'
                    }
                },
                data: appValueGroupData
            }]
        };

        return option;
    }
</script>

</body>
</html>