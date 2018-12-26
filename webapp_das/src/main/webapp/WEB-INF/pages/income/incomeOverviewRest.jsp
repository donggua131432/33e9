<%--
  Created by IntelliJ IDEA.
  User:dukai
  Date: 2016/8/22
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>收入总览</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<script type="text/javascript">

    var dataTables = "";
    var search_param = {
        params:{
            "dayRange":"yesterday"
        }
    };
    $(function(){
        //加载月概况信息
        getMonthRestOverviewData($.trim($("#stafDay").val()));

        //加载消费TOP10信息
        var dataTablesAjax = {
            "url": '<c:url value='/incomeOverviewRest/getRestDayRangeRecordTopTen'/>',
            "type": 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({}, _page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            {data: 'rowNO'},
            {data: ''},
            {data: 'sid'},
            {data: 'name'},
            {data: 'total_fee'},
            {data: 'thscsum'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {"targets": [0],"data": "id","sClass" : "text-c",
                "render": function(data, type, full) {
                    return "top"+full.rowNO;
                }
            },
            {"targets": [1],"data": "id","sClass" : "text-c",
                "render": function(data, type, full) {
                    return "专线语音";
                }
            },
            {"targets": [5],"data": "id","sClass" : "text-c",
                "render": function(data, type, full) {
                    if(data){
                        return $.calcEval(data+"/600000").toFixed(2);
                    }
                    return "";
                }
            },
            {"targets": [2,3,4],"data": "id","sClass" : "text-c",
                "render": function(data, type, full) {
                    if (data) {
                        return data;
                    }
                    return "";
                }
            }
        ];

        //初始化表格渲染
        dataTables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "ordering": false,//关闭排序
            "pageLength": 10,//每页显示数据条数
            "dom": 'rt',
            "sErrMode": 'none',
            "columns": columns,
            "ajax": dataTablesAjax,
            "columnDefs":columnDefs
        });

        //加载近期消费数据
        getDayRangeOverviewData();

        $("div.btn-group").find("span").click(function(){
            if(!$(this).hasClass("btn-primary")){
                $(this).addClass("btn-primary");
                $(this).siblings().removeClass("btn-primary");
            }
            getDayRangeOverviewData();
        });
    });

    function clickDtePicker(){
        var stafDay = $dp.cal.getNewDateStr().replace(/[\u4E00-\u9FA5]/g,'-');
        getMonthRestOverviewData(stafDay);
    }

    //获取每月的消费数据
    function getMonthRestOverviewData(stafDay){
        $.ajax({
            type:"post",
            url:"<c:url value='/incomeOverviewRest/getMonthRestOverview'/>",
            dataType:"json",
            async:false,
            data:{'stafDay':stafDay},
            success : function(result) {
                if(result.code == 0){
                    $("#receivableFee").text(result.data.receivableFee);
                    $("#avgDiscountRate").text($.calcEval(result.data.callSumFee+"/"+result.data.timeLengthFee+"/"+result.data.standardFee+"*100").toFixed(2));
                }else{
                    $("#receivableFee").text(0);
                    $("#avgDiscountRate").text(0);
                }
            },
            error: function(){
                console.log("数据请求失败！");
            }
        });
    }

    //获取近期消费数据
    function getDayRangeOverviewData(){
        var dayRange = $.trim($(".btn.btn-primary.radius").attr("spanVal"));
        $.ajax({
            type:"post",
            url:"<c:url value='/incomeOverviewRest/getRestDayRangeRecordInfo'/>",
            dataType:"json",
            async:false,
            data:{'dayRange':dayRange},
            success : function(result) {
                if(result.code == 0){
                    $("#nearReceivableFee").text(result.data.totalFee);
                    $("#nearAvgPrice").text($.calcEval(result.data.fee+"/"+result.data.jfscSum).toFixed(2));
                    $("#nearCallTimeLength").text($.calcEval(result.data.thscSum+"/600000").toFixed(2));
                    $("#nearCallTimeFee").text(result.data.jfscSum);
                }else{
                    $("#nearReceivableFee").text(0);
                    $("#nearAvgPrice").text(0);
                    $("#nearCallTimeLength").text(0);
                    $("#nearCallTimeFee").text(0);
                }
            },
            error: function(){
                console.log("数据请求失败！");
            }
        });

        //刷新消费前TOP10客户
        search_param = {
            params:{
                "dayRange":dayRange
            }
        };
        dataTables.fnDraw();
    }

</script>
<body>
<jsp:include page="../common/nav.jsp"/>
<%--<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 收入数据 <span class="c-gray en">&gt;</span> 收入总览 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>--%>
<div class="pd-20">
    <div class="row cl">
        <div class="col-offset-0">月份</div>
        <div class="col-offset-0 ml-20">
            <input type="hidden" id="maxMonth" value="${maxMonth}"/>
            <input type="hidden" id="minMonth" value="${minMonth}"/>
            <input autocomplete="off" type="text" class="input-text Wdate"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'maxMonth\')}',minDate:'#F{$dp.$D(\'minMonth\')}',
								   readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false,onpicking:clickDtePicker})" id="stafDay" value="${maxMonth}" style="width:186px;height:28px;"/>
        </div>
    </div>

    <div class="row cl mt-20">
        <div class="panel panel-primary col-5-1">
            <div class="panel-header">应收账款</div>
            <div style="height: 100px; padding-top: 30px; text-align: center;">
                <h2 ><span id="receivableFee">话费和录音费</span><span class="ml-10 f-16">元</span></h2>
            </div>
        </div>
        <div class="panel panel-secondary col-5-1 ml-50">
            <div class="panel-header">平均折扣率</div>
            <div style="height: 100px; padding-top: 30px; text-align: center;">
                <h2><span id="avgDiscountRate">应收账款/计费通话时长/标准价</span><span class="ml-10 f-16">%</span></h2>
            </div>
        </div>
        <div class="panel panel-success col-5-1 ml-50">
            <div class="panel-header">毛利</div>
            <div style="height: 100px; padding-top: 30px; text-align: center;">
                <h2><span>0</span><span class="ml-10 f-16">元</span></h2>
            </div>
        </div>
        <div class="panel panel-warning col-5-1 ml-50">
            <div class="panel-header">毛利率</div>
            <div style="height: 100px; padding-top: 30px; text-align: center;">
                <h2><span>0</span><span class="ml-10 f-16">%</span></h2>
            </div>
        </div>
    </div>

    <div class="row cl pt-20">
        <div class="btn-group">
            <span class="btn btn-primary radius" spanVal="yesterday" style="width: 120px;">昨日</span>
            <span class="btn radius" spanVal="7day" style="width: 120px;">近7日</span>
            <span class="btn radius" spanVal="30day" style="width: 120px;">近30日</span>
        </div>
    </div>


    <div class="row cl mt-20">
        <div class="panel panel-primary col-5-1">
            <div class="panel-header">应收账款</div>
            <div style="height: 100px; padding-top: 30px; text-align: center;">
                <h2><span id="nearReceivableFee">0</span><span class="ml-10 f-16">元</span></h2>
            </div>
        </div>
        <div class="panel panel-secondary col-5-1 ml-50">
            <div class="panel-header">平均单价</div>
            <div style="height: 100px; padding-top: 30px; text-align: center;">
                <h2><span id="nearAvgPrice">0</span><span class="ml-10 f-16">元/分钟</span></h2>
            </div>
        </div>
        <div class="panel panel-success col-5-1 ml-50">
            <div class="panel-header">累计通话时长</div>
            <div style="height: 100px; padding-top: 30px; text-align: center;">
                <h2><span id="nearCallTimeLength">0</span><span class="ml-10 f-16">万分钟</span></h2>
            </div>
        </div>
        <div class="panel panel-warning col-5-1 ml-50">
            <div class="panel-header">计费通话时长</div>
            <div style="height: 100px; padding-top: 30px; text-align: center;">
                <h2><span id="nearCallTimeFee">0</span><span class="ml-10 f-16">分钟</span></h2>
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
                    <th width="20%">Account  ID</th>
                    <th width="20%">客户名称</th>
                    <th width="10%">应收账款(元)</th>
                    <th width="20%">累计通话时长(万分钟)</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
