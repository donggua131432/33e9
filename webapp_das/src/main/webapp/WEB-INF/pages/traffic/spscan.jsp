<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>云话机话务走势</title>
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
        var data = loadData('<c:url value="/traffic/spscan"/>',{stype:stype});
        var xyAxis = parseData(data,stype);

        // 使用刚指定的配置项和数据显示图表。
        thscTotal.setOption(initOption("累计通话时长(秒)", ["回拨A路","回拨B路","SipPhone回拨A路","SipPhone回拨B路","直拨","SipPhone直拨","回呼"], xyAxis['d'], [xyAxis['thscsumRestA'],xyAxis['thscsumRestB'],xyAxis['thscsumSipA'],xyAxis['thscsumSipB'],xyAxis['thscsumRestO'],xyAxis['thscsumSipO'],xyAxis['thscsumC']] ));
        thscAvg.setOption(initOption("平均通话时长(秒)", ["回拨A路","回拨B路","SipPhone回拨A路","SipPhone回拨B路","直拨","SipPhone直拨","回呼"], xyAxis['d'], [xyAxis['thscAvgRestA'],xyAxis['thscAvgRestB'],xyAxis['thscAvgSipA'],xyAxis['thscAvgSipB'],xyAxis['thscAvgRestO'],xyAxis['thscAvgSipO'],xyAxis['thscAvgC']] ));
        jfscTotal.setOption(initOption("计费通话时长(分钟)", ["计费通话时长"], xyAxis['d'], [xyAxis['jfscsum']] ));
        succcnt.setOption(initOption("接通率", ["回拨A路","回拨B路","SipPhone回拨A路","SipPhone回拨B路","直拨","SipPhone直拨","回呼"], xyAxis['d'], [xyAxis['succcntRestA'],xyAxis['succcntRestB'],xyAxis['succcntSipA'],xyAxis['succcntSipB'],xyAxis['succcntRestO'],xyAxis['succcntSipO'],xyAxis['succcntC']] ));
        answercnt.setOption(initOption("应答率", ["回拨A路","回拨B路","SipPhone回拨A路","SipPhone回拨B路","直拨","SipPhone直拨","回呼"], xyAxis['d'], [xyAxis['answercntRestA'],xyAxis['answercntRestB'],xyAxis['answercntSipA'],xyAxis['answercntSipB'],xyAxis['answercntRestO'],xyAxis['answercntSipO'],xyAxis['answercntC']] ));
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
            thscsumRestA:[], thscsumRestB:[], thscsumSipA:[],thscsumSipB:[], thscsumRestO:[], thscsumSipO:[], thscsumC:[],
            thscAvgRestA:[], thscAvgRestB:[], thscAvgSipA:[],thscAvgSipB:[], thscAvgRestO:[], thscAvgSipO:[], thscAvgC:[],
            jfscsum:[],
            succcntRestA:[], succcntRestB:[], succcntSipA:[],succcntSipB:[], succcntRestO:[], succcntSipO:[], succcntC:[],
            answercntRestA:[], answercntRestB:[], answercntSipA:[],answercntSipB:[], answercntRestO:[], answercntSipO:[],answercntC:[],
            callcnt:[], rcdCnt:[]
        };

        for (var i=0; i<pdata.d.length; i++) {
            var hasfill = false;
            if (data) {
                for (var j=0; j<data.length; j++) {
                    if (data[j]['d'] == pdata.d[i]) {
                        pdata.thscsumRestA.push(data[j]['thscsumRestA']);
                        pdata.thscsumRestB.push(data[j]['thscsumRestB']);
                        pdata.thscsumSipA.push(data[j]['thscsumSipA']);
                        pdata.thscsumSipB.push(data[j]['thscsumSipB']);
                        pdata.thscsumRestO.push(data[j]['thscsumRestO']);
                        pdata.thscsumSipO.push(data[j]['thscsumSipO']);
                        pdata.thscsumC.push(data[j]['thscsumC']);

                        pdata.thscAvgRestA.push(data[j]['thscAvgRestA']);
                        pdata.thscAvgRestB.push(data[j]['thscAvgRestB']);
                        pdata.thscAvgSipA.push(data[j]['thscAvgSipA']);
                        pdata.thscAvgSipB.push(data[j]['thscAvgSipB']);
                        pdata.thscAvgRestO.push(data[j]['thscAvgRestO']);
                        pdata.thscAvgSipO.push(data[j]['thscAvgSipO']);
                        pdata.thscAvgC.push(data[j]['thscAvgC']);

                        pdata.jfscsum.push(data[j]['jfscsum']);

                        pdata.succcntRestA.push(data[j]['succcntRestA']);
                        pdata.succcntRestB.push(data[j]['succcntRestB']);
                        pdata.succcntSipA.push(data[j]['succcntSipA']);
                        pdata.succcntSipB.push(data[j]['succcntSipB']);
                        pdata.succcntRestO.push(data[j]['succcntRestO']);
                        pdata.succcntSipO.push(data[j]['succcntSipO']);
                        pdata.succcntC.push(data[j]['succcntC']);

                        pdata.answercntRestA.push(data[j]['answercntRestA']);
                        pdata.answercntRestB.push(data[j]['answercntRestB']);
                        pdata.answercntSipA.push(data[j]['answercntSipA']);
                        pdata.answercntSipB.push(data[j]['answercntSipB']);
                        pdata.answercntRestO.push(data[j]['answercntRestO']);
                        pdata.answercntSipO.push(data[j]['answercntSipO']);
                        pdata.answercntC.push(data[j]['answercntC']);

                        pdata.callcnt.push(data[j]['callcnt']);

                        hasfill = true;
                        break;
                    }
                }
            }
            if(!hasfill) {
                pdata.thscsumRestA.push(0);
                pdata.thscsumRestB.push(0);
                pdata.thscsumSipA.push(0);
                pdata.thscsumSipB.push(0);
                pdata.thscsumRestO.push(0);
                pdata.thscsumSipO.push(0);
                pdata.thscsumC.push(0);

                pdata.thscAvgRestA.push(0);
                pdata.thscAvgRestB.push(0);
                pdata.thscAvgSipA.push(0);
                pdata.thscAvgSipB.push(0);
                pdata.thscAvgRestO.push(0);
                pdata.thscAvgSipO.push(0);
                pdata.thscAvgC.push(0);

                pdata.jfscsum.push(0);

                pdata.succcntRestA.push(1);
                pdata.succcntRestB.push(1);
                pdata.succcntSipA.push(1);
                pdata.succcntSipB.push(1);
                pdata.succcntRestO.push(1);
                pdata.succcntSipO.push(1);
                pdata.succcntC.push(1);

                pdata.answercntRestA.push(1);
                pdata.answercntRestB.push(1);
                pdata.answercntSipA.push(1);
                pdata.answercntSipB.push(1);
                pdata.answercntRestO.push(1);
                pdata.answercntSipO.push(1);
                pdata.answercntC.push(1);

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