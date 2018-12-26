<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>话务总览</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<script type="text/javascript">
    $(function(){
        $(".btn-group span").on("click", function(){
            $(this).siblings().removeClass("btn-primary").addClass("btn-default");
            $(this).removeClass("btn-default").addClass("btn-primary");
        });
        //初始加载昨日数据
        showData(-1);
    });

    // 根据日期数静态切换展示数据
    function showData(dateNum) {
        //加载相应数据
        var data=loadData(dateNum);
        //alert(data.dateNum);
        //展示总收入、累计通话时长、计费通话时长、消费客户数
        $("#fee").text(data.fee+"元");
        $("#thscsum").text(data.thscsum+"秒");
        $("#jfscsum").text(data.jfscsum+"分");
        $("#countUser").text(data.countUser+"个");
        //应用占比饼图展示
        var legend=[];
        var series=[];

        $.each(data.typeList,function(i,v){
            legend.push(v.typeName);
            series.push({value: ifnull(v.fee,0), name:v.typeName});
        });
//        legend.push('直接访问1');
//        legend.push('邮件营销');
//        legend.push('联盟广告');
//        legend.push('视频广告');
//        legend.push('搜索引擎');
//        series.push({value:335, name:'直接访问1'});
//        series.push({value:310, name:'邮件营销'});
//        series.push({value:234, name:'联盟广告'});
//        series.push({value:135, name:'视频广告'});
//        series.push({value:1548, name:'搜索引擎'});
        initOption("应收占比",legend,series);

        //展示消费top 10
        var tbody="";
        $.each(data.topList,function(i,v){
            tbody +='<tr>';
            tbody +='<td>'+ v.rowno+'</td>';
            tbody +='<td>'+ v.typeName+'</td>';
            tbody +='<td>'+ v.sid+'</td>';
            tbody +='<td>'+v.name+'</td>';
            tbody +='<td>'+v.jfscsum/10000+'</td>';
            tbody +='<td>'+v.fee+'</td>';
            tbody +='</tr>';
        });
        if(!tbody){
            tbody +='<tr>';
            tbody +='<td colspan="6">无数据</td>';
            tbody +='</tr>';
        }

        $("#top").html(tbody);
    }

    // 加载数据
    function loadData(dateNum) {
        var data;
        $.ajax({
            async: false,
            type: "POST",
            url: "<c:url value='/overviews/tel'/>",
            data: {"dateNum":dateNum},
            success: function(msg){
                data = msg;
            }
        });
        return data;
    }

    // 初始化配置项
    function initOption(title,legend,series) {
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

        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('thscTotal'));

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }

</script>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="row cl pt-20">
        <div class="col-offset-0 btn-group">
            <span class="btn btn-primary radius" style="width: 120px;" onclick="showData(-1);">昨日</span>
            <span class="btn btn-default radius" style="width: 120px;" onclick="showData(-7);">近7日</span>
            <span class="btn btn-default radius" style="width: 120px;" onclick="showData(-30);">近30日</span>
        </div>
    </div>

    <div class="row cl mt-20">
        <div class="panel panel-primary col-5-1">
            <div class="panel-header">总收入</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="fee">0元</h4>
            </div>
        </div>
        <div class="panel panel-secondary col-5-1 ml-50">
            <div class="panel-header">累计通话时长</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="thscsum">0秒</h4>
            </div>
        </div>
        <div class="panel panel-success col-5-1 ml-50">
            <div class="panel-header">计费通话时长</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="jfscsum">0分</h4>
            </div>
        </div>
        <div class="panel panel-warning col-5-1 ml-50">
            <div class="panel-header">消费用户数</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="countUser">0个</h4>
            </div>
        </div>
    </div>

    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;" class="mt-20"/>
    <!-- 分割线 -->

    <div class="row cl mt-20">
        <div class="panel panel-success">
            <%--<div class="panel-header">应收占比</div>--%>
            <div  id="thscTotal" style="height: 300px; text-align: center;">

            </div>
        </div>
    </div>

    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;" class="mt-20"/>
    <!-- 分割线 -->

    <h4>消费前TOP10客户</h4>
    <div class="mt-15">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="10%">排名</th>
                <th width="20%">消费产品</th>
                <th width="20%">account ID</th>
                <th width="20%">客户名称</th>
                <th width="20%">计费通话时长(万分钟)</th>
                <th width="10%">应收账款(元)</th>
            </tr>
            </thead>
            <tbody id="top">

            </tbody>
        </table>
    </div>
</div>
</body>
</html>
