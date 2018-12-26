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
    <div class="" id="searchFrom">
        创建时间：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d\'}'})" id="timemin" name="timemin" class="input-text Wdate" style="width:220px;"/>
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}'})" id="timemax" name="timemax" class="input-text Wdate" style="width:220px;"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        应用状态：
        <select id="status" name="status" style="width: 200px;height: 31px;">
            <option value="">请选择应用状态</option>
            <option value="00">正常</option>
            <%--<option value="01">冻结</option>--%>
            <option value="02">禁用</option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        注册账号：<input type="text" id="email" name="email" class="input-text" placeholder="注册账号" style="width:200px;">

        &nbsp;&nbsp;&nbsp;&nbsp;
        客户名称：<input type="text" id="companyName" name="companyName" class="input-text" placeholder="客户名称" style="width:200px;">
        <br><br>
        Account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="accountID" style="width:360px;">

        &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
        应用名称：<input type="text" id="appName" name="appName" class="input-text" placeholder="应用名称" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;

        APP&nbsp;&nbsp;&nbsp;&nbsp;ID：<input type="text" id="appId" name="appId" class="input-text" placeholder="APP ID" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        应用号码：<input type="text" id="callNo" name="callNo" class="input-text" placeholder="应用号码" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;

        <button  type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>

        <div class="cl pd-5 bg-1 bk-gray mt-20">
            <span class="r">
                <a href="javascript:void(0);" onclick="downloadAppListReport();" class="btn btn-primary radius r">
                    <i class="Hui-iconfont">&#xe600;</i> 导出
                </a>
            </span>
        </div>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <%--<a href="javascript:;" onclick="data_del()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
            <%--<a href="javascript:;" onclick="openUserDialog('添加应用','<c:url value="/znydd/toAddApp"/>','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加应用</a>--%>
        </span>
    </div>
    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
                <tr class="text-c">
                    <th width="5%">序号</th>
                    <th width="10%">创建时间</th>
                    <th width="10%">APP ID</th>
                    <th width="20%">应用名称</th>
                    <th width="10%">Account ID</th>
                    <th width="20%">客户名称</th>
                    <th width="10%">应用号码</th>
                    <th width="5%">应用状态</th>
                    <%--<th width="10%">创建时间</th>--%>
                    <%--<th width="10%">手机号码</th>--%>
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
            url: '<c:url value='/znydd/pageAppList'/>',
            type: 'POST',
            "data": function(d){
                console.log(d);
                var _page = datatables_ajax_data(d);

                console.log(_page);
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
            { data: 'callNo'},
            { data: 'status'},
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
                "targets": [1],
//                "orderable":true,
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.createDate) {
                        return o.formatDate(full.createDate, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createDate;
                }
            },
            {
                "targets": [7],
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
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openUserDialog(\'查看\',\'<c:url value="/znydd/toShowAppInfo?appId='+full.appId+'"/>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">查看</i></a>';
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openUserDialog(\'编辑\',\'<c:url value="/znydd/toEditAppInfo?appId='+full.appId+'"/>\',\'800\',\'400\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i>编辑</a>';
                    if (full.status == '00') {
//                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'01\')" href="javascript:;" title="冻结" class="ml-5"><i class="Hui-iconfont">&#xe631;</i>冻结</a>';
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'02\')" href="javascript:;" title="禁用" class="ml-5"><i class="Hui-iconfont">&#xe60b;</i>禁用</a>';
                    } else if (full.status == '01' || full.status == '02'){
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.appId+'\', \'00\')" href="javascript:;" title="启用" class="ml-5"><i class="Hui-iconfont">&#xe615;</i>启用</a>';
                    }
                    _ex_in += '<a title="App专用路由" href="javascript:;" onclick="openUserDialog(\'App专用路由\',\'<c:url value="/znydd/toEditAppRelayRoute?appId='+full.appId+'"/>\',\'\',\'\')" class="ml-5" style="text-decoration:none;color:blue;">App专用路由</a>';
                    return _ex_in;
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
//            "autoWidth": true,
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 1, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{"companyName" : $("#companyName").val(),
                    "appName" : $("#appName").val(),"status" : $("#status").val(),
                    "appId" : $("#appId").val(),"callNo" : $("#callNo").val(),
                    "sid" : $("#sid").val(),
                    "email" : $("#email").val()
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

    /* 管理员状态修改 */
    function adminStatusUpdate(obj, id, status ){
        layer.confirm('是否更改状态？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/znydd/updateAppStatus'/>",
                dataType:"json",
                data:{"appid":id, "status":status},
                success:function(data){
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("该业务已关闭，不能开启应用", {icon: 2},function(){location.replace(location.href);});
                    }
                }
            });
        });
    }

    /** 导出列表 */
    function downloadAppListReport() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        var formData = "";
        $('#searchFrom').find('input[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
        $('#searchFrom').find('select[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
        formData = formData.substring(1);
        window.open("<c:url value='/znydd/downloadAppListReport'/>?" + formData);
    }
</script>
</body>
</html>