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
        创建时间：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:200px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:200px;">

        &nbsp;&nbsp;&nbsp;&nbsp;
        应用状态：
        <select id="status" name="status" style="width: 200px;height: 31px;">
            <option value="">请选择应用状态</option>
            <option value="00">正常</option>
            <option value="01">冻结</option>
            <option value="02">禁用</option>
        </select>
        &nbsp;&nbsp;
        注册账号：<input type="text" id="email" name="email" class="input-text" placeholder="注册账号" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        客户名称：<input type="text" id="companyName" name="companyName" class="input-text" placeholder="客户名称" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;

    </div><br>
    <div class="text-l">
        Account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="accountID" style="width:280px;">
        &nbsp;&nbsp;&nbsp;&nbsp;

        应用名称：<input type="text" id="appName" name="appName" class="input-text" placeholder="应用名称" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;

        APP ID：<input type="text" id="appid" name="appid" class="input-text" placeholder="appid" style="width:280px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>
    <%-- <div class="cl pd-5 bg-1 bk-gray mt-20">
       <span class="l">
            <a href="javascript:;" onclick="data_del()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
            <a href="javascript:;" onclick="openUserDialog('添加应用','<c:url value="/znydd/toAddApp"/>','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加应用</a>

        </span>
    </div>--%>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
                <tr class="text-c">
                    <th width="5%">序号</th>
                    <th width="10%">创建时间</th>
                    <th width="10%">APP ID</th>
                    <th width="">应用名称</th>
                    <th width="10%">Account ID</th>
                    <th width="">客户名称</th>
                    <th width="6%">应用状态</th>
                    <th width="15%">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    var dds = '${dds}';
    $(document).ready(function() {

        var jsdds = $.parseJSON(dds);

        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/openi/pageAppList'/>',
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
            { data: 'createDate'},
            { data: 'appId'},
            { data: 'appName'},
            { data: 'sid' },
            { data: 'companyName'},
            { data: 'status'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.createDate) {
                        return o.formatDate(full.createDate, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createDate;
                }
            },
            {
                "targets": [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.status == '00') {
                        return '<span class="label label-success radius">正常</span>';
                    }
                    if (full.status == '01') {
                        return '<span class="label label-warning radius">冻结</span>';
                    }
                    if (full.status == '02') {
                        return '<span class="label label-defaunt radius">禁用</span>';
                    }
                    return full.status;
                }
            },

            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    if (full.status == '00') {
                        _ex_in += '<a style="text-decoration:none;color:blue;" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'01\',\''+full.companyName+'\',\''+full.sid+'\',\''+full.appName+'\')" href="javascript:;" title="冻结" class="ml-5">冻结</a>';
                        _ex_in += '<a style="text-decoration:none;color:blue;" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'02\',\''+full.companyName+'\',\''+full.sid+'\',\''+full.appName+'\')" href="javascript:;" title="禁用" class="ml-5">禁用</a>';
                    } else if (full.status == '01'){
                        _ex_in += '<a style="text-decoration:none;color:blue;" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'00\',\''+full.companyName+'\',\''+full.sid+'\',\''+full.appName+'\')" href="javascript:;" title="解冻" class="ml-5">解冻</a>';
                        _ex_in += '<a style="text-decoration:none;color:blue;" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'02\',\''+full.companyName+'\',\''+full.sid+'\',\''+full.appName+'\')" href="javascript:;" title="禁用" class="ml-5">禁用</a>';
                    } else if (full.status == '02') {
                        _ex_in += '<a style="text-decoration:none;" href="javascript:;" title="冻结" class="ml-5">冻结</a>';
                        _ex_in += '<a style="text-decoration:none;color:blue;" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'00\',\''+full.companyName+'\',\''+full.sid+'\',\''+full.appName+'\')" href="javascript:;" title="解禁" class="ml-5">解禁</a>';
                    }

                    _ex_in += '<a title="查看" href="javascript:;" onclick="openUserDialog(\'查看\',\'<c:url value="/openi/toShowAppInfo?appId='+full.appId+'"></c:url>\',\'600\',\'400\')" class="ml-5" style="text-decoration:none;color:blue;">查看</a>';
                    _ex_in += '<a title="中继强显号码池关联" href="javascript:;" onclick="openRelayDialog(\'中继强显号码池关联\',\'<c:url value="/openi/toEditAppRelay?appId='+full.appId+'"></c:url>\',\'600\',\'400\')" class="ml-5" style="text-decoration:none;color:blue;">中继强显号码池关联</a>';
                    _ex_in += '<a title="App专用路由" href="javascript:;" onclick="openRouteDialog(\'App专用路由\',\'<c:url value="/openi/toEditAppRelayRoute?appId='+full.appId+'"></c:url>\',\'800\',\'600\')" class="ml-5" style="text-decoration:none;color:blue;">App专用路由</a>';
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
            "aaSorting": [[ 7, "desc" ]], // 默认的排序列（columns）从0开始计数
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
                    "email" : $("#email").val(),
                    "status" : $("#status").val(),
                    "appId" : $("#appid").val()
                },
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            datatables.fnDraw();
        });
    });

    /*管理员-增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    function openRelayDialog(title,url,w,h){
        layer_full(title,url,w,h);
    }

    function openRouteDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }
    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/openi/downloadReport'/>?params[companyName]=" + $("#companyName").val()
                + "&params[appName]=" + $("#appName").val()
                + "&params[sid]=" + $("#sid").val()
                + "&params[email]=" + $("#email").val()
                + "&params[appid]=" + $("#appid").val()
                + "&params[status]=" + $("#status").val()
                + "&timemin=" + $("#timemin").val()
                + "&timemax=" + $("#timemax").val()
        );
        //datatables_download_report($(), columns, search_param, columnDefs);
    }


    /* 管理员状态修改 */
    function adminStatusUpdate(obj, id, status ,companyName,sid,appName){
        var msg = $(obj).attr("title");
        layer.confirm('是否 ' + msg + ' 该用户？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/openi/updateAppStatus'/>",
                dataType:"json",
                data:{"appid":id, "status":status,"companyName":companyName,"sid":sid,"appName":appName},
                success:function(data){
                    if (data.code == '00') {
                        layer.alert("修改成功", {icon: 1});
                        datatables.fnDraw();
                    } else {
                        layer.alert("修改失败", {icon: 2});
                    }

                }
            });
        });
    }
</script>
</body>
</html>