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
    <%--<div class="text-c">
        用户名称：<input type="text" id="nick" name="nick" class="input-text" placeholder="用户名/登录名" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        所属部门：<input type="text" id="" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>--%>
    <%--<div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            &lt;%&ndash;<a href="javascript:;" onclick="data_del()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>&ndash;%&gt;
            <a href="javascript:;" onclick="openUserDialog('添加用户','<c:url value="/user/toAddUser"/>','800','540')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a>
        </span>
    </div>--%>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="15%">菜单名称</th>
                <th width="15%">路径</th>
                <th width="20%">权限</th>
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
            url: '<c:url value='/action/pageAction'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'name' },
            { data: 'url' },
            { data: 'actions'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0],
                "sClass" : "text-c",
                "orderable":false
            },
            {
                "targets": [2],
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    if (data) {
                        $.each(data, function(i,n) {
                            _ex_in += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="label label-primary radius">' + n.typeName + '</span>';
                        });
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
            "sErrMode": 'none',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {"fuzzy" : $("#nick").val()};
            datatables.fnDraw();
        });

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