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
        Account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="accountID" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        客户名称：<input type="text" id="companyName" name="companyName" class="input-text" placeholder="客户名称" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        APP ID：<input type="text" id="appid" name="appid" class="input-text" placeholder="appid" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        应用名称：<input type="text" id="appName" name="appName" class="input-text" placeholder="应用名称" style="width:180px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        状态：<select type="text" id="auditStatus" name="auditStatus" class="input-text" style="width:180px;">
            <option value="">全部</option>
            <option value="00">待分配</option>
            <option value="01">已分配</option>
            <option value="02">审核不通过</option>
        </select>
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
                <th width="15%">客户名称</th>
                <th width="10%">Account ID</th>
                <th width="10%">应用名称</th>
                <th width="10%">APP ID</th>
                <th width="10%">申请时间</th>
                <th width="10%">操作人</th>
                <th width="10%">状态</th>
                <th width="10%">操作</th>
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
            url: '<c:url value='/spApply/pageApplyList'/>',
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
            { data: 'companyName'},
            { data: 'sid'},
            { data: 'appName' },
            { data: 'appid'},
            { data: 'atime'},
            { data: 'operator'},
            { data: 'auditStatus'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [5],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (data) {
                        return o.formatDate(data, "yyyy-MM-dd hh:mm:ss");
                    }
                    return data;
                }
            },
            {
                "targets": [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (data == '00') {
                        return '<span class="label label-success radius">客户</span>';
                    }
                    if (data == '01') {
                        return '<span class="label label-defaunt radius">运营人员</span>';
                    }
                    return data;
                }
            },
            {
                "targets": [7],
                "sClass" : "text-c",
                "render": function(data, type, full) { // 00审核中   01:通过:  02:不通过
                    if (data == '00') {
                        return '<span class="label label-success radius">待分配</span>';
                    }
                    if (data == '01') {
                        return '<span class="label label-defaunt radius">已分配</span>';
                    }
                    if (data == '02') {
                        return '<span class="label label-defaunt radius">审核不通过</span>';
                    }
                    return data;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openRelayDialog(\'查看\',\'<c:url value="/spApply/numList?id='+full.id+'&managerType=view"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont"></i>查看</a>';
                    if (full.auditStatus == '00') {
                        _ex_in += '<a title="编辑" href="javascript:;" onclick="openRelayDialog(\'分配\',\'<c:url value="/spApply/numAllot?id='+full.id+'&managerType=edit"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i>分配</a>';
                    }

                    return _ex_in;
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 5, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{
                    "companyName" : $("#companyName").val(),
                    "appName" : $("#appName").val(),
                    "sid" : $("#sid").val(),
                    "auditStatus" : $("#auditStatus").val(),
                    "appid" : $("#appid").val()
                }
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
        window.open("<c:url value='/spApply/downloadApplyReport'/>?params[companyName]=" + $("#companyName").val()
                + "&params[appName]=" + $("#appName").val()
                + "&params[sid]=" + $("#sid").val()
                + "&params[auditStatus]=" + $("#auditStatus").val()
                + "&params[appid]=" + $("#appid").val()
        );
        //datatables_download_report($(), columns, search_param, columnDefs);
    }

    /*管理员-增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    /*管理员-增加*/
    function openRelayDialog(title,url,w,h){
        layer_full(title,url,w,h);
    }

    /* 管理员状态修改 */
    function adminStatusUpdate(obj, id, status ){
        layer.confirm('是否更改状态？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/sip/updateAppStatus'/>",
                dataType:"json",
                data:{"appid":id, "status":status},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
            });
        });
    }
</script>
</body>
</html>