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
    <div class="text-c">
        日期范围：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:195px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:195px;">
        &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
        accountID：<input type="text" id="accountID" name="accountID" class="input-text" placeholder="accountID" style="width:185px;">

        &nbsp;&nbsp;
        客户账号：<input type="text" id="companyEmail" name="companyEmail" class="input-text" placeholder="客户账号" style="width:185px;">

        &nbsp;&nbsp;
        客户名称：<input type="text" id="companyName" name="companyName" class="input-text" placeholder="客户名称" style="width:185px;">

        &nbsp;&nbsp;
        所属行业：<select id="tradeId" name="tradeId" style="width: 185px;height: 31px;">
        <option value="">请选择所属行业</option>
        <c:forEach items="${dicDatas}" var="dd">
            <option value="${dd.code}">${dd.name}</option>
        </c:forEach>
        </select>

        &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<br>
    </div>
    <div class="text-l">
        认证状态：<select id="authStatus" name="authStatus" style="width: 185px;height: 31px;">
        <option value="">请选择认证状态</option>
        <option value="0">未认证</option>
        <option value="1">认证中</option>
        <option value="2">已认证</option>
        <option value="3">认证不通过</option>
    </select>

        &nbsp;&nbsp;

        账户状态：<select id="accountStatus" name="accountStatus" style="width: 185px;height: 31px;">
        <option value="">请选择账户状态</option>
        <option value="1">正常</option>
        <option value="2">冻结</option>
        <option value="3">禁用</option>
    </select>
        &nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <%-- <div class="cl pd-5 bg-1 bk-gray mt-20">
       <span class="l">
            <a href="javascript:;" onclick="data_del()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
            <a href="javascript:;" onclick="openUserDialog('添加应用','<c:url value="/znydd/toAddApp"/>','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加应用</a>

        </span>
    </div>--%>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="10%">accountID</th>
                <th width="">客户账号</th>
                <th width="">客户名称</th>
                <th width="10%">所属行业</th>
                <th width="">认证状态</th>
                <th class="text-c" width="10%">账户余额（元）</th>
                <th width="10%">注册时间</th>
                <th width="10%">账户状态</th>
                <th width="14%">操作</th>
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
            url: '<c:url value='/userAdminMgr/pageUserAdmin'/>',
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
            { data: 'sid'},
            { data: 'email'},
            { data: 'name'},
            { data: 'tradeId'},
            { data: 'authStatus'},
            { data: 'fee'},
            { data: 'createDate' },
            { data: 'status', name : 'a.status'},
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
                "targets": [3],
                "orderable":false,
                "render": function(data, type, full) {
                    console.log(full.name);
                    if(full.name){
                        return full.name;
                    }else{
                        return " ";
                    }
                }
            },
            {
                "targets": [4],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.tradeId) {
                        $.each(jsdds, function(i,v){
                            if (v.code == full.tradeId) {
                                full.tradeId = v.name;
                                return false;
                            }
                        });
                        return full.tradeId;
                    }else{
                        return " ";
                    }

                }
            },
            {
                "targets": [5],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.authStatus == '0') {
                        return '<span class="label label-defaunt radius">未认证</span>';
                    }
                    if (full.authStatus == '1') {
                        return '<span class="label label-warning radius">认证中</span>';
                    }
                    if (full.authStatus == '2') {
                        return '<span class="label label-success radius">已认证</span>';
                    }
                    if (full.authStatus == '3') {
                        return '<span class="label label-danger radius">认证不通过</span>';
                    }
                    return full.authStatus;
                }
            },
            {
                "targets": [6],
                "sClass" : "text-r"
            },
            {
                "targets": [7],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.createDate) {
                        return o.formatDate(full.createDate, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createDate;
                }
            },
            {
                "targets": [-2],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.status == '1') {
                        return '<span class="label label-success radius">正常</span>';
                    }
                    if (full.status == '2') {
                        return '<span class="label label-warning radius">冻结</span>';
                    }
                    if (full.status == '3') {
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
                    if (full.status == '1') {
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.uid+'\', \'2\', \''+full.name+'\')" href="javascript:;" title="冻结" class="ml-5"><i class="Hui-iconfont">冻结</i></a>';
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.uid+'\', \'3\', \''+full.name+'\')" href="javascript:;" title="禁用" class="ml-5"><i class="Hui-iconfont">禁用</i></a>';
                    } else if (full.status == '2' ){
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.uid+'\', \'1\', \''+full.name+'\')" href="javascript:;" title="解冻" class="ml-5"><i class="Hui-iconfont">解冻</i></a>';
                        /*_ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.uid+'\', \'3\', \''+full.name+'\')" href="javascript:;" title="禁用" class="ml-5"><i class="Hui-iconfont">禁用</i></a>';*/
                    } else if (full.status == '3'){
                        /*_ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.uid+'\', \'2\', \''+full.name+'\')" href="javascript:;" title="冻结" class="ml-5"><i class="Hui-iconfont">冻结</i></a>';*/
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, \''+full.uid+'\', \'1\', \''+full.name+'\')" href="javascript:;" title="解禁" class="ml-5"><i class="Hui-iconfont">解禁</i></a>';
                    }

                    _ex_in += '<a title="查看" href="javascript:;" onclick="openUserDialog(\'开发者资料\',\'<c:url value="/userAdminMgr/showUserAdmin?uid='+full.uid+'"></c:url>\',\'1000\',\'530\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">查看</i></a>';

                    _ex_in += '<a title="开通" href="javascript:;" onclick="openUserDialog(\'客户信息配置——开通\',\'<c:url value="/busType/busTypeConfig?sid='+full.sid+'"></c:url>\',\'800\',\'530\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">开通</i></a>';
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
                    "name" : $("#companyName").val(),
                    "email" : $("#companyEmail").val(),
                    "tradeId" : $("#tradeId").val(),
                    "authStatus" : $("#authStatus").val(),
                    "accountStatus" : $("#accountStatus").val(),
                    "accountID" : $("#accountID").val()
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
    function adminStatusUpdate(obj, id, status, name){
        var tips = "";
        if(status=="2"){
            tips="您确定要冻结该账户？";
        }else if(status=="3"){
            tips="您确定要禁用该账户？";
        }else if(status=="1"){
            tips="您确定要启用该账户？";
        }
        layer.confirm(tips, {
            title: "客户名称:"+name,
            btn: ['是','否'] //按钮
        }, function(){
            layer.msg("操作中请稍候......");
            $.ajax({
                type:"post",
                url:"<c:url value='/userAdminMgr/updateUserAdminStatus'/>",
                dataType:"json",
                data:{"uid":id, "status":status,"name":name},
                success:function(data){
                    if (data.code == 'ok') {
                        layer.alert("修改成功", {icon: 1});
                        datatables.fnDraw();
                    } else {
                        layer.alert("修改失败", {icon: 2});
                    }
                }
            });
        });
    }

    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/userAdminMgr/downloadReport'/>?params[name]=" + $("#companyName").val()
                + "&params[accountID]=" + $("#accountID").val()
                + "&params[email]=" + $("#companyEmail").val()
                + "&params[tradeId]=" + $("#tradeId").val()
                + "&params[authStatus]=" + $("#authStatus").val()
                + "&params[accountStatus]=" + $("#accountStatus").val()
                + "&timemin=" + $("#timemin").val()
                + "&timemax=" + $("#timemax").val()
        );
    }
</script>
</body>
</html>
