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
        日期：
        <select id="myYear" name="myYear" class="input-text" style="width:200px;"></select>
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport();" class="btn btn-primary radius r">导出</a>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort" id="J_tab_fam">
            <thead>
            <tr class="text-c">
                <th rowspan="2" width="10%">故障类型</th>
                <th colspan="12" width="90%">日期</th>
            </tr>
            <tr>
                <th width="5%">1月</th>
                <th width="5%">2月</th>
                <th width="5%">3月</th>
                <th width="5%">4月</th>
                <th width="5%">5月</th>
                <th width="5%">6月</th>
                <th width="5%">7月</th>
                <th width="5%">8月</th>
                <th width="5%">9月</th>
                <th width="5%">10月</th>
                <th width="5%">11月</th>
                <th width="5%">12月</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function(){
//设置年份的选择
        var myDate= new Date();
        var startYear=myDate.getFullYear();//起始年份
        var endYear=myDate.getFullYear()+20;//结束年份
        var obj=document.getElementById('myYear');
        for (var i=startYear;i<=endYear;i++)
        {
            obj.options.add(new Option(i,i));
        }
    });

    var search_param = {};
    var datatables;

    $(document).ready(function() {
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/relaylist/pageRelayFaultList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

    // columns 列选项 和表头对应，和后台传回来的数据属性对应
    var columns = [
        { data: 'faultName'},
        { data: 'recordRate1'},
        { data: 'recordRate2'},
        { data: 'recordRate3'},
        { data: 'recordRate4'},
        { data: 'recordRate5'},
        { data: 'recordRate6'},
        { data: 'recordRate7'},
        { data: 'recordRate8'},
        { data: 'recordRate9'},
        { data: 'recordRate10'},
        { data: 'recordRate11'},
        { data: 'recordRate12'}
    ];

    // 对数据进行处理
    var columnDefs = [
        {
            "targets": [0],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                return full.faultName+"(%)";
            }
        },
        {
            "targets": [1],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate1 == '0.00') {
                    return '';
                }
                return full.recordRate1;
            }
        },
        {
            "targets": [2],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate2 == '0.00') {
                    return '';
                }
                return full.recordRate2;
            }
        },
        {
            "targets": [3],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate3 == '0.00') {
                    return '';
                }
                return full.recordRate3;
            }
        },
        {
            "targets": [4],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate4 == '0.00') {
                    return '';
                }
                return full.recordRate4;
            }
        },
        {
            "targets": [5],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate5 == '0.00') {
                    return '';
                }
                return full.recordRate5;
            }
        },
        {
            "targets": [6],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate6 == '0.00') {
                    return '';
                }
                return full.recordRate6;
            }
        },
        {
            "targets": [7],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate7 == '0.00') {
                    return '';
                }
                return full.recordRate7;
            }
        },
        {
            "targets": [8],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate8 == '0.00') {
                    return '';
                }
                return full.recordRate8;
            }
        },
        {
            "targets": [9],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate9 == '0.00') {
                    return '';
                }
                return full.recordRate9;
            }
        },
        {
            "targets": [10],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate10 == '0.00') {
                    return '';
                }
                return full.recordRate10;
            }
        },
        {
            "targets": [11],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate11 == '0.00') {
                    return '';
                }
                return full.recordRate11;
            }
        },
        {
            "targets": [12],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                if (full.recordRate12 == '0.00') {
                    return '';
                }
                return full.recordRate12;
            }
        }

    ];

    datatables = $(".table-sort").dataTable({
        "processing": false, // 是否显示取数据时的那个等待提示
        "serverSide": true,//这个用来指明是通过服务端来取数据
        "bStateSave": true,//状态保存
        "bFilter": false, //隐藏掉自带的搜索功能
        "dom": 'rtilp<"clear">',
        "ordering": false,
        //"aaSorting": [[ 8, "desc" ]], // 默认的排序列（columns）从0开始计数
        //"fnServerData": retrieveData,
        "columns": columns,
        ajax: datatables_ajax,
        "columnDefs":columnDefs
    });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{
                    "myYear" : $("#myYear").val()
                }
            };
            datatables.fnDraw();

        });

    });

    // 导出报表
    function downloadReport() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/relaylist/downloadReport'/>?params[myYear]=" + $("#myYear").val()
        );
    }

</script>
</body>
</html>