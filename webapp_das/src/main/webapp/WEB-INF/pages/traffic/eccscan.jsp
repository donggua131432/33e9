<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>云总机话务走势</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <style>
        .wordbreak{word-break:break-all;}
    </style>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="btn-group">
        <span data-stype="1" class="btn btn-primary radius">昨天</span>
        <span data-stype="7" class="btn btn-default radius">近7日</span>
        <span data-stype="30" class="btn btn-default radius">近30日</span>
    </div>

    <div class="mt-20">
        <!-- 1.	累计通话时长 -->
        <div id="thscTotal" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>

        <!-- 2.	计费通话时长 -->
        <div id="jfscTotal" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
        </div>

        <!-- 3.	平均通话时长 -->
        <div id="thscAvg" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>

        <!-- 4.	话务总量 -->
        <div id="callcnt" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
        </div>

        <!-- 5.	接通率 -->
        <div id="succcnt" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>

        <!-- 6.	应答率 -->
        <div id="answercnt" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
        </div>

    </div>
</div>
<script type="text/javascript">

    // 基于准备好的dom，初始化echarts实例
    var thscTotal = echarts.init(document.getElementById('thscTotal'));
    var thscAvg = echarts.init(document.getElementById('thscAvg'));
    var jfscTotal = echarts.init(document.getElementById('jfscTotal'));
    var succcnt = echarts.init(document.getElementById('succcnt'));
    var answercnt = echarts.init(document.getElementById('answercnt'));
    var callcnt = echarts.init(document.getElementById('callcnt'));

    $(function(){
        $(".btn-group span").on("click", function(){
            $(this).siblings().removeClass("btn-primary").addClass("btn-default");
            $(this).removeClass("btn-default").addClass("btn-primary");
            var stype = $(this).attr("data-stype");
            initcharts(stype);
        });

        initcharts('1');
    });

    function initcharts(stype){
        var data = loadData('<c:url value="/traffic/eccscan"/>',{stype:stype});
        var xyAxis = parseData(data,stype);

        // 使用刚指定的配置项和数据显示图表。
        thscTotal.setOption(initOption("累计通话时长(秒)", ["呼入总机-SIP","呼入总机-非SIP","呼入直呼","呼出市话","呼出长途"], xyAxis['d'], [xyAxis['thscsumS'],xyAxis['thscsumNS'],xyAxis['thscsumD'],xyAxis['thscsumL'],xyAxis['thscsumNL']] ));
        thscAvg.setOption(initOption("平均通话时长(秒)", ["呼入总机-SIP","呼入总机-非SIP","呼入直呼","呼出市话","呼出长途"], xyAxis['d'], [xyAxis['thscAvgS'],xyAxis['thscAvgNS'],xyAxis['thscAvgD'],xyAxis['thscAvgL'],xyAxis['thscAvgNL']] ));
        jfscTotal.setOption(initOption("计费通话时长(分钟)", ["计费通话时长"], xyAxis['d'], [xyAxis['jfscsum']] ));
        succcnt.setOption(initOption("接通率", ["呼入总机-SIP","呼入总机-非SIP","呼入直呼","呼出市话","呼出长途"], xyAxis['d'], [xyAxis['succcntS'],xyAxis['succcntNS'],xyAxis['succcntD'],xyAxis['succcntL'],xyAxis['succcntNL']] ));
        answercnt.setOption(initOption("应答率", ["呼入总机-SIP","呼入总机-非SIP","呼入直呼","呼出市话","呼出长途"], xyAxis['d'], [xyAxis['answercntS'],xyAxis['answercntNS'],xyAxis['answercntD'],xyAxis['answercntL'],xyAxis['answercntNL']] ));
        callcnt.setOption(initOption("话务总量", ["话务总量"], xyAxis['d'], [xyAxis['callcnt']] ));
    }

    function parseData(data,stype){
        var bdays = '${bdays}';
        var sdays = '${sdays}';
        var d = [];

        if (stype == '7') {
            $.each(sdays.split(','), function(i,day){
                $.each(o.hours_for_days, function(j,h){
                    d.push(day + " " + h);
                });
            });
        } else if (stype == '30') {
            d = bdays.split(',');
        } else {
            d = o.min_5;
        }

        console.log(d);

        var pdata = {
            d: d,
            thscsumS:[], thscsumNS:[], thscsumD:[],thscsumL:[], thscsumNL:[],
            thscAvgS:[], thscAvgNS:[], thscAvgD:[],thscAvgL:[], thscAvgNL:[],
            jfscsum:[],
            succcntS:[], succcntNS:[], succcntD:[],succcntL:[], succcntNL:[],
            answercntS:[], answercntNS:[], answercntD:[],answercntL:[], answercntNL:[],
            callcnt:[], rcdCnt:[]
        };

        for (var i=0; i<pdata.d.length; i++) {
            var hasfill = false;
            if (data) {
                for (var j=0; j<data.length; j++) {
                    if (data[j]['d'] == pdata.d[i]) {
                        pdata.thscsumS.push(data[j]['thscsumS']);
                        pdata.thscsumNS.push(data[j]['thscsumNS']);
                        pdata.thscsumD.push(data[j]['thscsumD']);
                        pdata.thscsumL.push(data[j]['thscsumL']);
                        pdata.thscsumNL.push(data[j]['thscsumNL']);

                        pdata.thscAvgS.push(data[j]['thscAvgS']);
                        pdata.thscAvgNS.push(data[j]['thscAvgNS']);
                        pdata.thscAvgD.push(data[j]['thscAvgD']);
                        pdata.thscAvgL.push(data[j]['thscAvgL']);
                        pdata.thscAvgNL.push(data[j]['thscAvgNL']);

                        pdata.jfscsum.push(data[j]['jfscsum']);

                        pdata.succcntS.push(data[j]['succcntS']);
                        pdata.succcntNS.push(data[j]['succcntNS']);
                        pdata.succcntD.push(data[j]['succcntD']);
                        pdata.succcntL.push(data[j]['succcntL']);
                        pdata.succcntNL.push(data[j]['succcntNL']);

                        pdata.answercntS.push(data[j]['answercntS']);
                        pdata.answercntNS.push(data[j]['answercntNS']);
                        pdata.answercntD.push(data[j]['answercntD']);
                        pdata.answercntL.push(data[j]['answercntL']);
                        pdata.answercntNL.push(data[j]['answercntNL']);

                        pdata.callcnt.push(data[j]['callcnt']);

                        hasfill = true;
                        break;
                    }
                }
            }
            if(!hasfill) {
                pdata.thscsumS.push(0);
                pdata.thscsumNS.push(0);
                pdata.thscsumD.push(0);
                pdata.thscsumL.push(0);
                pdata.thscsumNL.push(0);

                pdata.thscAvgS.push(0);
                pdata.thscAvgNS.push(0);
                pdata.thscAvgD.push(0);
                pdata.thscAvgL.push(0);
                pdata.thscAvgNL.push(0);

                pdata.jfscsum.push(0);

                pdata.succcntS.push(1);
                pdata.succcntNS.push(1);
                pdata.succcntD.push(1);
                pdata.succcntL.push(1);
                pdata.succcntNL.push(1);

                pdata.answercntS.push(1);
                pdata.answercntNS.push(1);
                pdata.answercntD.push(1);
                pdata.answercntL.push(1);
                pdata.answercntNL.push(1);

                pdata.callcnt.push(0);
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
                top:'25',
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
                //splitNumber:10
                //'category',
                boundaryGap: false,
                data: xAxis
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
</script>

</body>
</html>