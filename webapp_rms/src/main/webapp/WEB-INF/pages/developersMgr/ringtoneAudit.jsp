<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>铃声审核</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c">
        accountID：<input type="text" id="accountID" name="accountID" class="input-text" placeholder="accountID" style="width:200px;">

        &nbsp;&nbsp;
        客户名称：<input type="text" id="company_name" name="company_name" placeholder="客户名称" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        审核状态：
        <select id="status" name="status" class="select" style="width:200px;height: 31px">
            <option value="" >全部</option>
            <option value="01">审核通过</option>
            <option value="02">审核不通过</option>
            <option value="00" >待审核</option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        APP ID：<input type="text" id="appid" name="appid" placeholder="APP ID" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        应用名称：<input type="text" id="app_name" name="app_name" placeholder="应用名称" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>

    <div class="mt-20">

        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="40">序号</th>
                <th width="80">accountID</th>
                <th width="100">提交时间</th>
                <th width="100">客户名称</th>
                <th width="90">铃声大小</th>
                <th width="90">铃声名称</th>
                <th width="80">APP ID</th>
                <th width="100">应用名称</th>
                <th width="100">审核时间</th>
                <th width="90">审核状态</th>
                <th width="50">操作</th>
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
            url: '<c:url value='/ringtone/list'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'rowNO' },
            { data: 'sid' },
            { data: 'create_date' },
            { data: 'name' },
            { data: 'voice_size'},
            { data: 'voice_name'},
            { data: 'appid'},
            { data: 'app_name'},
            { data: 'update_date'},
            { data: 'status'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1,2,6,7,8],
                "sClass" : "text-c"
            },
            {
                "targets": [2],
                "render": function(data, type, full) {
                    var create_date = full.create_date;
                    if (create_date) {
                        return o.formatDate(create_date,"yyyy-MM-dd hh:mm:ss")
                    }
                    return create_date;
                }
            },
            {
                "targets": [4],
                "render": function(data, type, full) {
                    var voice_size = full.voice_size;
                    if (voice_size) {
                        return voice_size+"M"
                    }
                    return voice_size;
                }
            },
            {
                "targets": [8],
                "render": function(data, type, full) {
                    var update_date = full.update_date;
                    if (update_date) {
                        return o.formatDate(update_date,"yyyy-MM-dd hh:mm:ss")
                    }
                    return update_date;
                }
            },
            {
                "targets" : [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.status == '00') {
                        return "<span class='label label-warning radius'>待审核</span>";
                    }else if(full.status == '01'){
                        return "<span class='label label-success radius'>审核通过</span>";
                    }else if(full.status == '02'){
                        return "<span class='label label-danger radius'>审核不通过</span>";
                    }
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var status = full.status;
                    if(status == '00'){
                        return '<a title="审核" href="javascript:;" ' +
                                'onclick="system_log_show(\'铃声审核\',\'<c:url value="/ringtone/toShowCertification?type=edit"></c:url>\',' + full.id + ',\'600\',\'600\')" class="ml-5" style="text-decoration:none; color:blue">审核</a>';
                    }else if(status == '01'){
                        return '<a title="查看" href="javascript:;" ' +
                                'onclick="system_log_show(\'铃声审核\',\'<c:url value="/ringtone/toShowCertification?type=show"></c:url>\',' + full.id + ',\'600\',\'600\')" class="ml-5" style="text-decoration:none; color:blue">查看</a>';
                    }
                    else if(status == '02'){
                        return '<a title="查看" href="javascript:;" ' +
                                'onclick="system_log_show(\'铃声审核\',\'<c:url value="/ringtone/toShowCertification?type=show"></c:url>\',' + full.id + ',\'600\',\'600\')" class="ml-5" style="text-decoration:none; color:blue">查看</a>';
                    }
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 9, "asc" ]], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                "params[accountID]" : $("#accountID").val(),
                "params[companyName]" : $("#company_name").val(),
                "params[appName]" : $("#app_name").val(),
                "params[appid]" : $("#appid").val(),
                "params[status]" : $("#status option:selected").val()
            };
            datatables.fnDraw();
        });
    });


    /*
     参数解释：
     title	标题
     url	请求的url
     id		需要操作的数据id
     w		弹出层宽度（缺省调默认值）
     h		弹出层高度（缺省调默认值）
     */
    /*管理员-增加*/
    function system_log_show(title,url,id,w,h){
        url = url + "&id=" + id;
        layer_show(title,url,w,h);
    }


</script>
</body>
</html>