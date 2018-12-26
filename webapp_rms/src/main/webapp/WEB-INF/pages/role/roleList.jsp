<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>角色信息管理</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <script type="text/javascript">
        var dataTables = "", search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/roleMgr/pageRoleList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'rowNO'},
                {data: 'name'},
                {data: 'type'},
                {data: 'description'},
                {data: 'menuName'},
                {data: 'operation'}
            ];
            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0,1,2,3,4],
                    "orderable":false,
                    "sClass" : "text-c"
                },
                {
                    "targets": [2],
                    "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.type == '00') {
                            return '<span class="label label-success radius">系统角色</span>';
                        }
                        if (full.type == '01') {
                            return '<span class="label label-primary radius">月结</span>';
                        }
                        return full.type;
                    }
                },
                {"targets": [-1], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        var _ex_in = "";
                        _ex_in += '<a title="修改" href="javascript:void(0);" onclick="openDialog(\'编辑角色信息\',\'<c:url value="/roleMgr/roleEditView?id='+full.id+'&operationType=edit"/>\',\'800\',\'400\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe6df;</i>修改</a>';
                        _ex_in += '<a title="查看" href="javascript:void(0);" onclick="openDialog(\'查看角色信息\',\'<c:url value="/roleMgr/roleEditView?id='+full.id+'&operationType=view"/>\',\'800\',\'300\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe695;</i>查看</a>';
                        _ex_in += '<a title="删除" href="javascript:void(0);" onclick="delRole(\'' + full.id + '\',\'' + full.name + '\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
                        _ex_in += '<a title="分配权限" href="javascript:void(0);" onclick="edtiAction(\'分配权限\',\'<c:url value="/roleMgr/toEidtAction?id='+full.id+'"/>\',\'800\',\'700\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe600;</i>分配权限</a>';
                        return  _ex_in;
                    }
                }
            ];

            //初始化表格渲染
            dataTables = $(".table-sort").dataTable({
                "processing": false, // 是否显示取数据时的那个等待提示
                "serverSide": true,//这个用来指明是通过服务端来取数据
                "bStateSave": true,//状态保存
                "bFilter": false, //隐藏掉自带的搜索功能
                "ordering": false,//关闭排序
//                "autoWidth": true,
                "dom": 'rtilp',
                "sErrMode": 'none',
                "columns": columns,
                ajax: dataTablesAjax,
                "columnDefs":columnDefs
            });

            // 搜索功能
            $("#searchButton").on("click", function(){
                console.info("搜索");
                var formData = {};
                $('#searchFrom').find('input[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });
                $('#searchFrom').find('select[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });
                search_param = formData;
                dataTables.fnDraw();
            });

            $("#type").select2({
                placeholder: "请选择角色类型"});
        });

        /** 删除角色 **/
        function delRole(id,name){
            layer.confirm('记录一旦删除将不可以恢复。</br>确定要删除【'+ name +'】吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/roleMgr/deleteRole'/>",
                    dataType:"json",
                    data:{"id":id},
                    success : function(data) {
                        layer.alert(data.info,{
                            icon: data.status == '0'? 1 : 2,
                            skin: 'layer-ext-moon'
                        },function (){
                            location.replace(location.href);
                        });
                    },
                    error: function(data){
                        layer.alert(data.info,{
                            icon: 2,
                            skin: 'layer-ext-moon'
                        });
                    }
                });
            });
        }

        /** 导出列表 */
        function downloadRoleInfoReport() {
            if(dataTables._fnGetDataMaster().length < 1){
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
            window.open("<c:url value='/roleMgr/downloadRoleInfo'/>?" + formData);
        }

        function edtiAction(title,url,w,h) {
            layer_show(title,url,w,h);
        }
    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            <div class="row cl">
                角色名称：
                <input type="text" class="input-text" style="width:186px" placeholder="请输入角色名称" id="name" name="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
                角色类型：
                <select id="type" name="type" class="input-text cl-1"  style="width:200px;height: 31px">
                    <option value="">请选择</option>
                    <option value=" ">全部</option>
                    <option value="00">系统角色</option>
                </select>&nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-success radius" id="searchButton"><i class="Hui-iconfont">&#xe665;</i>查询</button>
            </div>

        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openDialog('新增角色','<c:url value="/roleMgr/addRole"/>','800','400')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 新增角色
            </a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadRoleInfoReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr>
                    <th scope="col" colspan="6">角色信息</th>
                </tr>
                <tr class="text-c">
                    <th width="5%">序号</th>
                    <th width="10%">角色名称</th>
                    <th width="10%">角色类型</th>
                    <th width="20%">角色描述</th>
                    <th width="40%">权限</th>
                    <th width="15%">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
