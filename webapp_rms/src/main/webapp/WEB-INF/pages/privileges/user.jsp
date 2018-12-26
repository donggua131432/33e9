<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>用户管理</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c" id="searchFrom">
        用户名称：<input type="text" id="nick" name="nick" class="input-text" placeholder="用户名/登录名" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        所属部门：<input type="text" id="name" name = "name" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <%--<a href="javascript:;" onclick="data_del()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
            <a href="javascript:;" onclick="openUserDialog('添加用户','<c:url value="/user/toAddUser"/>','800','540')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a>
        </span>
    </div>
    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
                <tr class="text-c">
                    <th width="5%"><input name="" type="checkbox" value="">全选</th>
                    <th width="10%">用户名称</th>
                    <th width="15%">登录名称</th>
                    <th width="15%">所属部门</th>
                    <th width="20%">可登陆平台</th>
                    <th width="15%">时间</th>
                    <th width="5%">状态</th>
                    <th width="20%">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">
    var datatables = "";
    var search_param = {},sysTypeArray={}, sysTypeOption="";
    $(document).ready(function() {

        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/user/pageUser'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'id' },
            { data: 'nick' },
            { data: 'username' },
            { data: 'departmentName' },
            { data: 'sysType'},
            { data: 'createTime' },
            { data: 'status'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0],
                "orderable":false,
                "sClass" : "text-c", // 样式（居中）
                "render": function(data, type, full) {
                    return "<td><input name='' type='checkbox' value='" + full.id + "'></td>";
                }
            },
            { "targets": [ 1 ], "render": function(data, type, full) {
                if (typeof data == 'string') {
                    data = data.replace(/</g,"&lt;");
                    data = data.replace(/>/g,"&gt;");
                }
                return data;
            }},
            {
                "targets": [4],
                "data": "id",
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var sysTypeStr = "",sysTypeArr="";
                    if (full.sysType) {
                        sysTypeArr = full.sysType.split(",");
                        $.each(sysTypeArr,function(i,item){
                            if(i == (sysTypeArr.length-1)){
                                sysTypeStr += sysTypeArray[item];
                            }else{
                                sysTypeStr += sysTypeArray[item]+",";
                            }
                        })
                    }
                    return sysTypeStr;
                }
            },
            {
                "targets": [5],
                "data": "id",
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.createTime) {
                        return o.formatDate(full.createTime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createTime;
                }
            },
            {
                "targets": [6],
                "data": "id",
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.status == 0) {
                        return '<span class="label label-success radius">正常</span>';
                    } else {
                        return '<span class="label label-defaunt radius">禁用</span>';
                    }

                }
            },
            {
                "targets": [7],
                "data": "id",
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var _ex_in = "";
                    if (full.status == 0) {
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, '+full.id+', 1,\''+full.sysType+'\')" href="javascript:;" title="禁用"><i class="Hui-iconfont" style="color: red;">&#xe631;</i>禁用</a>';
                    } else {
                        _ex_in += '<a style="text-decoration:none" onClick="adminStatusUpdate(this, '+full.id+', 0,\''+full.sysType+'\')" href="javascript:;" title="解禁"><i class="Hui-iconfont" style="color: green;">&#xe615;</i>解禁</a>';
                    }
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openUserDialog(\'用户编辑\',\'<c:url value="/user/toUpdateUserInfo?id='+full.id+'"/>\',\'800\',\'540\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="color: green;">&#xe6df;</i>编辑</a>';
                    _ex_in += '<a title="分配角色" href="javascript:;" onclick="openUserDialog(\'分配角色\',\'<c:url value="/user/selectRoleToUser?id='+full.id+'"/>\',\'800\',\'340\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="color: green;">&#xe600;</i>分配角色</a>';
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
            "aaSorting": [[ 1, "asc" ]], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
//            search_param = {"fuzzy" : $("#nick").val(),"params[name]" : $("#name").val()};

            var formData = {};
            $('#searchFrom').find('input[name]').each(function(){
                formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
            });
            $('#searchFrom').find('select[name]').each(function(){
                formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
            });
            search_param = formData;
            datatables.fnDraw();
        });

        //平台权限类型
        <c:forEach items="${sysTypeDic}" var="sysType">
        sysTypeArray["${sysType.code}"] = "${sysType.name}";
        sysTypeOption += '<option value="${sysType.code}">${sysType.name}</option>';
        </c:forEach>
        $("#sysType").append(sysTypeOption);

    });

    /*管理员-增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    /* 管理员状态修改 */
    function adminStatusUpdate(obj, id, status,sysType ){
        layer.confirm('是否更改用户使用状态？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/user/updateUserInfo'/>",
                dataType:"json",
                data:{"id":id, "status":status,"sysType":sysType},
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