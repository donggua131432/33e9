<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>号码池列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l">
        Account ID：<input type="text" id="accountId" name="accountId" class="input-text" placeholder="accountId" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        所属客户：<input type="text" id="companyName" name="companyName" class="input-text" placeholder="所属客户" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        子账号名称：<input type="text" id="subName" name="subName" class="input-text" placeholder="subName" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        号码类型：<select id="type" name="type" style="width: 160px;height: 31px;">
        <option value="">请选择号码类型</option>
            <option value="0">全局</option>
            <option value="1">子账号</option>
    </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
    </div><br>
    <div class="text-l">
        号码：<input type="text" id="number" name="number" class="input-text" placeholder="号码" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="10%">号码</th>
                <th width="10%">所属客户</th>
                <th width="10%">Account ID</th>
                <th width="10%">号码类型</th>
                <th width="10%">子账号名称</th>
                <th width="10%">创建时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/sipNumPool/pageSipNumList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
            { data: 'rowNO' },
            { data: 'number'},
            { data: 'companyName'},
            { data: 'accountId' },
            { data: 'type'},
            { data: 'subName'},
            { data: 'createTime'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [4],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.type == '0') {
                        return '全局';
                    }
                    if (full.type == '1') {
                        return '子账号';
                    }
                    return full.type;
                }
            },
            {
                "targets": [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.createTime) {
                        return o.formatDate(full.createTime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createTime;
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
//            "aaSorting": [[ 7, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{
                    "accountId" : $("#accountId").val(),
                    "companyName" : $("#companyName").val(),
                    "subName" : $("#subName").val(),
                    "type" : $("#type").val(),
                    "number" : $("#number").val()
                },
            };
            datatables.fnDraw();
        });
    });

    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/sipNumPool/downloadSipNumList'/>?params[accountId]=" + $("#accountId").val()
                + "&params[companyName]=" + $("#companyName").val()
                + "&params[subName]=" + $("#subName").val()
                + "&params[type]=" + $("#type").val()
                + "&params[number]=" + $("#number").val()
        );
        //datatables_download_report($(), columns, search_param, columnDefs);
    }

</script>
</body>
</html>