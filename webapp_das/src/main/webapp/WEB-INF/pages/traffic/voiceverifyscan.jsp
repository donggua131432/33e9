<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>语音验证码话务走势</title>
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

        <!-- 3.	平均通话时长 -->
        <div id="thscAvg" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
        </div>

        <!-- 5.	接通率 -->
        <div id="succcnt" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>

        <!-- 6.	应答率 -->
        <div id="answercnt" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
        </div>

        <!-- 2.	计费通话时长 -->
        <div id="jfscTotal" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>

        <!-- 4.	计费条数 -->
        <div id="chargCount" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
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
    var chargCount = echarts.init(document.getElementById('chargCount'));

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
        var data = loadData('<c:url value="/traffic/voiceverifyscan"/>',{stype:stype});
        var xyAxis = parseData(data,stype);

        // 使用刚指定的配置项和数据显示图表。
        thscTotal.setOption(initOption("累计通话时长(秒)", ["累计通话时长"], xyAxis['d'], [xyAxis['thscsum']] ));
        thscAvg.setOption(initOption("平均通话时长(秒)", ["平均通话时长"], xyAxis['d'], [xyAxis['thscAvg']] ));
        succcnt.setOption(initOption("接通率", ["接通率"], xyAxis['d'], [xyAxis['succcntRate']] ));
        answercnt.setOption(initOption("应答率", ["应答率"], xyAxis['d'], [xyAxis['answercntRate']] ));
        jfscTotal.setOption(initOption("计费通话时长(分钟)", ["计费通话时长"], xyAxis['d'], [xyAxis['jfscsum']] ));
        chargCount.setOption(initOption("计费条数", ["计费条数"], xyAxis['d'], [xyAxis['chargCount']] ));
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
            thscsum:[],
            thscAvg:[],
            jfscsum:[],
            succcntRate:[],
            answercntRate:[],
            chargCount:[], rcdCnt:[]
        };

        for (var i=0; i<pdata.d.length; i++) {
            var hasfill = false;
            if (data) {
                for (var j=0; j<data.length; j++) {
                    if (data[j]['d'] == pdata.d[i]) {
                        pdata.thscsum.push(data[j]['thscsum']);
                        pdata.thscAvg.push(data[j]['thscAvg']);
                        pdata.jfscsum.push(data[j]['jfscsum']);
                        pdata.succcntRate.push(data[j]['succcntRate']);
                        pdata.answercntRate.push(data[j]['answercntRate']);
                        pdata.chargCount.push(data[j]['chargCount']);

                        hasfill = true;
                        break;
                    }
                }
            }
            if(!hasfill) {
                pdata.thscsum.push(0);
                pdata.thscAvg.push(0);
                pdata.jfscsum.push(0);
                pdata.succcntRate.push(1);
                pdata.answercntRate.push(1);
                pdata.chargCount.push(0);
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