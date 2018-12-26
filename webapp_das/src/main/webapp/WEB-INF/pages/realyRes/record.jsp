<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>应用列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l">
        资源表报:
        <select id="reportType" name="reportType" class="input-text" style="width:60px;">
            <option value="1">日报</option>
            <option value="2">月报</option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        时间：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="datemin" class="input-text Wdate" style="width:180px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="datemax" class="input-text Wdate" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        供应商名称：
        <select id="resSupid" name="resSupid" class="input-text" style="width:180px;">
            <option value="">请选择</option>
            <c:forEach items="${supplier}" var="sup">
                <option value="${sup.supId}">${sup.supName}</option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        中继名称：
        <select id="relayNum" name="relayNum" class="input-text" style="width:180px;">
            <option value="">请选择</option>
            <c:forEach items="${relays}" var="relay">
                <option value="${relay.relayNum}">${relay.relayName}</option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        线路资源名称：
        <select id="resId" name="resId" class="input-text" style="width:180px">
            <option value="">请选择</option>
            <c:forEach items="${relayRes}" var="res">
                <option value="${res.id}">${res.resName}</option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>

    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReportCommon();" class="btn btn-primary radius r" style="margin-left: 20px;">导出</a>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="8%">时间</th>
                <th width="8%">供应商名称</th>
                <th width="8%">线路资源名称</th>
                <th width="5%">线路资源ID</th>
                <th width="5%">中继名称</th>
                <th width="5%">中继ID</th>
                <th width="5%">呼叫总数</th>
                <th width="5%">接通率</th>
                <th width="5%">应答率</th>
                <th width="5%">累计通话时长(秒)</th>
                <th width="5%">平均通话时长(秒)</th>
                <th width="5%">线路低消(元)</th>
                <th width="4%">总通话费用(元)</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {reportType:'1'};

    $(document).ready(function() {
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/realyRes/pageRecord'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
            { data: 'rowNO'},
            { data: 'statday'},
            { data: 'supName'},
            { data: 'resName'},
            { data: 'resId'},
            { data: 'relayName'},
            { data: 'relayNum'},
            { data: 'callCnt'},
            { data: 'succRate'},
            { data: 'answerRate'},
            { data: 'thscSum'},
            { data: 'thscAvg'},
            { data: 'relayRent', "visible": false },
            { data: 'costFee'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "render": function(data, type, full) {
                    return data;
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": true, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 1, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });


        // 搜索功能
        $("#sarch").on("click", function(){
            var reportType = $("#reportType").val();
            if (reportType == 1) {
                search_param = {
                    params:{
                        "resSupid" : $("#resSupid").val(),
                        "relayNum" : $("#relayNum").val(),
                        "resId": $("#resId").val()
                    },
                    datemax:$("#datemax").val(),
                    datemin:$("#datemin").val(),
                    reportType:reportType
                };
                datatables.fnSetColumnVis( 12, false);
            }
            if (reportType == 2) {
                search_param = {
                    params:{
                        "resSupid" : $("#resSupid").val(),
                        "relayNum" : $("#relayNum").val(),
                        "resId": $("#resId").val()
                    },
                    monthmax:$("#datemax").val(),
                    monthmin:$("#datemin").val(),
                    reportType:reportType
                };
                datatables.fnSetColumnVis( 12, true);
            }

            datatables.fnDraw();
        });

        //报表类型联动时间格式
        $("#reportType").change(function(){
            $("#datemin") .val('');
            $("#datemax").val('');
            if($(this).val()==2){
                $("#datemin") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM'})");
                $("#datemax") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM'})");
            }else if($(this).val()==3){
                $("#datemin") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})");
                $("#datemax") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})");
            } else{
                $("#datemin") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\\'datemax\\')||\\'%y-%M-%d\\'}'})");
                $("#datemax") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\\'datemin\\')}',maxDate:'%y-%M-%d'})");
            }
        });

    });

    /** 导出列表 */
    function downloadReportCommon() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        var reportType = $("#reportType").val();
        if (reportType == 1) {
            search_param = {
                params:{
                    "resSupid" : $("#resSupid").val(),
                    "relayNum" : $("#relayNum").val(),
                    "resId": $("#resId").val()
                },
                datemax:$("#datemax").val(),
                datemin:$("#datemin").val(),
                reportType:reportType
            };
        }
        if (reportType == 2) {
            search_param = {
                params:{
                    "resSupid" : $("#resSupid").val(),
                    "relayNum" : $("#relayNum").val(),
                    "resId": $("#resId").val()
                },
                monthmax:$("#datemax").val(),
                nonthmin:$("#datemin").val(),
                reportType:reportType
            };
        }
        var p = decodeURIComponent($.param(search_param));
        window.open("<c:url value='/realyRes/downloadRecord'/>?" + p);
    }

</script>
</body>
</html>