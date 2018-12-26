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
        <%--&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:;" onclick="openAppDialog('创建应用','<c:url value="/sp/toAddApp"/>','800','540')" class="btn btn-success radius"><i class="Hui-iconfont">&#xe665;</i> 创建应用</a>--%>
    </div>

    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="openAppDialog('创建应用','<c:url value="/sp/toAddApp"/>','800','540')" class="btn btn-primary radius"> 创建应用</a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
        </span>

    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="10%">创建时间</th>
                <th width="10%">APP ID</th>
                <th width="10%">应用名称</th>
                <th width="10%">Account ID</th>
                <th width="10%">客户名称</th>
                <th width="8%">应用状态</th>
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
            url: '<c:url value='/sp/pageAppList'/>',
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
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'01\')" href="javascript:;" title="冻结" class="ml-5"><i class="Hui-iconfont">&#xe631;</i>冻结</a>';
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'02\')" href="javascript:;" title="禁用" class="ml-5"><i class="Hui-iconfont">&#xe60b;</i>禁用</a>';
                    } else if (full.status == '01' || full.status == '02'){
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'00\')" href="javascript:;" title="启用" class="ml-5"><i class="Hui-iconfont">&#xe615;</i>启用</a>';
                    }
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openRelayDialog(\'编辑\',\'<c:url value="/sp/toEditAppInfo?appId='+full.appId+'&managerType=edit"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i>编辑</a>';
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openRelayDialog(\'查看\',\'<c:url value="/sp/toShowAppInfo?appId='+full.appId+'&managerType=view"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont"></i>查看</a>';

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
                    "appid" : $("#appid").val()
                },
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
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
        window.open("<c:url value='/sp/downloadReport'/>?params[companyName]=" + $("#companyName").val()
                + "&params[appName]=" + $("#appName").val()
                + "&params[sid]=" + $("#sid").val()
                + "&params[email]=" + $("#email").val()
                + "&params[appid]=" + $("#appid").val()
                + "&timemin=" + $("#timemin").val()
                + "&timemax=" + $("#timemax").val()
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

    /*应用-增加*/
    function openAppDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }
</script>
</body>
</html>