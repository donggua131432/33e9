<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>智能云调度话务走势</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>

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
        客户名称：<select id="sid" class="input-text" style="width: 300px">
                </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        呼叫中心名称：<select id="ccInfo" class="input-text" style="width: 300px">
                        <option value="">全部</option>
                    </select>
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

        <!-- 7.	呼入运营商占比 -->
        <div id="callcntI" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;float:left;">
        </div>

        <!-- 8.	呼出运营商占比 -->
        <div id="callcntO" class="maskWraper" style="width:800px; height:400px; border:solid 1px #ddd;margin:0 30px 30px 0;">
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
    var callcntI = echarts.init(document.getElementById('callcntI'));
    var callcntO = echarts.init(document.getElementById('callcntO'));

    $(function(){
        $(".btn-group span").on("click", function(){
            $(this).siblings().removeClass("btn-primary").addClass("btn-default");
            $(this).removeClass("btn-default").addClass("btn-primary");
            var stype = $(this).attr("data-stype");
            initcharts(stype);
        });

        initcharts('1');

        $("#sid").select2({
            placeholder: "全部",
            ajax: {
                url: "<c:url value='/userAdmin/getCompanyNameAndSidForSelect2'/>",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        params: {name:params.term,busType:'02', stype: function() {return $(".btn-group span.btn-primary").attr("data-stype");}},
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

            //escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            //minimumInputLength: 1,
            //templateResult: formatRepo, // omitted for brevity, see the source of this page
            //templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
            //data:data
        });

        $("#sid").change(function(){
            var data = loadAjaxData('<c:url value="/traffic/getCcInfo"/>',{sid:$(this).val(),stype:$(".btn-group span.btn-primary").attr("data-stype")});
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
            initcharts($(".btn-group span.btn-primary").attr("data-stype"));
        });

        $("#ccInfo").on("change",function(){
            initcharts($(".btn-group span.btn-primary").attr("data-stype"));
        });
    });

    function initcharts(stype){
        var data = loadData('<c:url value="/traffic/ccscan"/>',{stype:stype, sid:$("#sid").val(), subid:$("#ccInfo").val()});
        var xyAxis = parseData(data,stype);

        // 使用刚指定的配置项和数据显示图表。
        thscTotal.setOption(initOption("累计通话时长(秒)", ["呼入","呼出"], xyAxis['d'], [xyAxis['thscsumI'],xyAxis['thscsumO']] ));
        thscAvg.setOption(initOption("平均通话时长(秒)", ["呼入","呼出"], xyAxis['d'], [xyAxis['thscAvgI'],xyAxis['thscAvgO']] ));
        jfscTotal.setOption(initOption("计费通话时长(分钟)", ["计费通话时长"], xyAxis['d'], [xyAxis['jfscsum']] ));
        succcnt.setOption(initOption("接通率", ["呼入","呼出"], xyAxis['d'], [xyAxis['succcntI'],xyAxis['succcntO']] ));
        answercnt.setOption(initOption("应答率", ["呼入","呼出"], xyAxis['d'], [xyAxis['answercntI'],xyAxis['answercntO']] ));
        callcnt.setOption(initOption("话务总量", ["话务总量"], xyAxis['d'], [xyAxis['callcnt']] ));

        var piedata = loadData('<c:url value="/traffic/ccOperator"/>',{stype:stype, sid:$("#sid").val(), subid:$("#ccInfo").val()});
        callcntI.setOption(initPieOption("呼入运营商占比", ["移动","联通","电信","其他"], [piedata['callcntI00'] || 0,piedata['callcntI01'] || 0,piedata['callcntI02'] || 0,piedata['callcntI06'] || 0] ));
        callcntO.setOption(initPieOption("呼出运营商占比", ["移动","联通","电信","其他"], [piedata['callcntO00'] || 0,piedata['callcntO01'] || 0,piedata['callcntO02'] || 0,piedata['callcntO06'] || 0] ));
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
            thscsumI:[],thscsumO:[],
            thscAvgI:[],thscAvgO:[],
            jfscsum:[],
            succcntI:[],succcntO:[],
            answercntI:[],answercntO:[],
            callcntI00:[],callcntI01:[],callcntI02:[],callcntI06:[],
            callcntO00:[],callcntO01:[],callcntO02:[],callcntO06:[],
            callcnt:[], rcdCnt:[]
        };

        for (var i=0; i<pdata.d.length; i++) {
            var hasfill = false;
            if (data) {
                for (var j=0; j<data.length; j++) {
                    if (data[j]['d'] == pdata.d[i]) {
                        pdata.thscsumI.push(data[j]['thscsumI']);
                        pdata.thscsumO.push(data[j]['thscsumO']);

                        pdata.thscAvgI.push(data[j]['thscAvgI']);
                        pdata.thscAvgO.push(data[j]['thscAvgO']);

                        pdata.jfscsum.push(data[j]['jfscsum']);

                        pdata.succcntI.push(data[j]['succcntI']);
                        pdata.succcntO.push(data[j]['succcntO']);

                        pdata.answercntI.push(data[j]['answercntI']);
                        pdata.answercntO.push(data[j]['answercntO']);

                        /*pdata.callcntI00.push(data[j]['callcntI00']);
                        pdata.callcntI01.push(data[j]['callcntI01']);
                        pdata.callcntI02.push(data[j]['callcntI02']);
                        pdata.callcntI06.push(data[j]['callcntI06']);

                        pdata.callcntO00.push(data[j]['callcntO00']);
                        pdata.callcntO01.push(data[j]['callcntO01']);
                        pdata.callcntO02.push(data[j]['callcntO02']);
                        pdata.callcntO06.push(data[j]['callcntO06']);
*/
                        pdata.callcnt.push(data[j]['callcnt']);

                        hasfill = true;
                        break;
                    }
                }
            }
            if(!hasfill) {
                pdata.thscsumI.push(0);
                pdata.thscsumO.push(0);

                pdata.thscAvgI.push(0);
                pdata.thscAvgO.push(0);

                pdata.jfscsum.push(0);

                pdata.succcntI.push(1);
                pdata.succcntO.push(1);

                pdata.answercntI.push(1);
                pdata.answercntO.push(1);

               /* pdata.callcntI00.push(0);
                pdata.callcntI01.push(0);
                pdata.callcntI02.push(0);
                pdata.callcntI06.push(0);

                pdata.callcntO00.push(0);
                pdata.callcntO01.push(0);
                pdata.callcntO02.push(0);
                pdata.callcntO06.push(0);*/

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

    // 初始化配置项
    function initPieOption(title,legend,values) {
        var series = [];
        for (var i=0; i<values.length; i++) {
            series.push({
                name: legend[i], //'A路',
                //type:'line',
                //stack: '总量',
                value:values[i]
            });
        }

        var option = {
            title : {
                text: title,//'应用占比',
//            subtext: '纯属虚构',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: legend//['直接访问1','邮件营销','联盟广告','视频广告','搜索引擎']
            },
            series : [
                {
                    name: title,
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data: series,
//                            [
//                                {value:335, name:'直接访问'},
//                                {value:310, name:'邮件营销'},
//                                {value:234, name:'联盟广告'},
//                                {value:135, name:'视频广告'},
//                                {value:1548, name:'搜索引擎'}
//                            ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        return option;
    }
</script>

</body>
</html>