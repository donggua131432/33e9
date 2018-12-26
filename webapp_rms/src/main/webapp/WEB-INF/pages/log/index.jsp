<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>操作日志</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c">
        日期范围：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="datemin" class="input-text Wdate" style="width:220px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="datemax" class="input-text Wdate" style="width:220px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        管理员账户：<input type="text" id="username" name="username" placeholder="管理员账号" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        操作类型：

        <select type="text" id="logType" name="logType" placeholder="操作类型" class="select" style="width:200px;height: 31px">
            <option value="">请选择操作类型</option>
            <option value="03">新增</option>
            <option value="05">修改</option>
            <option value="04">删除</option>
            <option value="01">登录</option>
            <option value="02">退出</option>
        </select>

        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>

    <div class="mt-20">

        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="40">序号</th>
                <th width="80">日志ID</th>
                <th width="120">日志名称</th>
                <th width="60">管理员账号</th>
                <th width="60">操作人</th>
                <th width="90">角色</th>
                <th width="40">IP</th>
                <th width="90">日志类型</th>
                <th width="90">操作时间</th>
                <th width="90">操作</th>
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
            url: '<c:url value='/log/pageLog'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'rowNO' },
            { data: 'id' },
            { data: 'logName', name : "log_name" },
            { data: 'username' },
            { data: 'nick' },
            { data: 'roleName', name : "role_name"},
            { data: 'ip'},
            { data: 'logType', name : "log_type"},
            { data: 'createTime', name : "create_time"},
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
                "targets": [1,7,8],
                "sClass" : "text-c"
            },
            {
                "targets": [7],
                "render": function(data, type, full) {
                    var logType = full.logType;
                    if (logType == '01') {
                        logType = "登录";
                    } else if (logType == '02') {
                        logType = "退出";
                    } else if (logType == '03') {
                        logType = "新增";
                    } else if (logType == "04") {
                        logType = "删除";
                    } else if (logType == "05") {
                        logType = "修改";
                    }
                    return logType;
                }
            },
            {
                "targets": [8],
                "render": function(data, type, full) {
                    var createTime = full.createTime;
                    if (createTime) {
                        return o.formatDate(createTime,"yyyy-MM-dd hh:mm:ss")
                    }
                    return createTime;
                }
            },
            {
                "targets": [-1],
                "data": "id",
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return '<a title="详情" href="javascript:;" onclick="system_log_show(\'日志详情\',\'<c:url value="/log/toShowLog"></c:url>\',' + full.id + ',\'600\',\'400\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">查看</i></a>';
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            // "order": [[ 1, "asc" ]，[ 2, "asc" ]], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                "fuzzy" : $("#username").val(),
                "timemin" : $("#datemin").val(),
                "timemax" : $("#datemax").val(),
                params : {
                    "logType" : $("#logType").val()
                }
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
        url = url + "?id=" + id;
        layer_show(title,url,w,h);
    }

    /*编辑用户信息*/
    function data_edit(title,url,id){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }

    /*图片-审核*/
    function picture_shenhe(obj,id){

        layer.confirm('审核文章？', {
                    btn: ['通过','不通过'],
                    shade: false
                },
                function(){
                    $(obj).parents("tr").find(".td-manage").prepend('<a class="c-primary" onClick="picture_start(this,id)" href="javascript:;" title="申请上线">申请上线</a>');
                    $(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已发布</span>');
                    $(obj).remove();
                    layer.msg('已发布', {icon:6,time:1000});
                },
                function(){
                    $(obj).parents("tr").find(".td-manage").prepend('<a class="c-primary" onClick="picture_shenqing(this,id)" href="javascript:;" title="申请上线">申请上线</a>');
                    $(obj).parents("tr").find(".td-status").html('<span class="label label-danger radius">未通过</span>');
                    $(obj).remove();
                    layer.msg('未通过', {icon:5,time:1000});
                });
    }

    /*图片-下架*/
    function picture_stop(obj,id){
        layer.confirm('确认要下架吗？',function(index){
            $(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="picture_start(this,id)" href="javascript:;" title="发布"><i class="Hui-iconfont">&#xe603;</i></a>');
            $(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已下架</span>');
            $(obj).remove();
            layer.msg('已下架!',{icon: 5,time:1000});
        });
    }

    /*图片-发布*/
    function picture_start(obj,id){
        layer.confirm('确认要发布吗？',function(index){
            $(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="picture_stop(this,id)" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>');
            $(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已发布</span>');
            $(obj).remove();
            layer.msg('已发布!',{icon: 6,time:1000});
        });
    }
    /*图片-申请上线*/
    function picture_shenqing(obj,id){
        $(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">待审核</span>');
        $(obj).parents("tr").find(".td-manage").html("");
        layer.msg('已提交申请，耐心等待审核!', {icon: 1,time:2000});
    }

    /*图片-删除*/
    function picture_del(obj,id){
        layer.confirm('确认要删除吗？',function(index){
            $(obj).parents("tr").remove();
            layer.msg('已删除!',{icon:1,time:1000});
        });
    }
</script>
</body>
</html>