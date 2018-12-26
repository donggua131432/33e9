<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2016/6/1
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>隐私号配置</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        var dataTables = "";
        var search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/maskLine/getMaskNumberPoolList'/>',
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
                {data: 'sid'},
                {data: 'app_name'},
                {data: 'appid'},
                {data: ''}
            ];

            // 对数据进行处理
            var columnDefs = [
                {"targets": [5], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return '<a title="编辑" href="javascript:;" onclick="openDialog(\'编辑号码池\',\'<c:url value="/maskLine/managerMaskNumberPool?id='+full.id+'&managerType=edit"/>\',\'800\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe6df;</i>编辑</a>'+
                                '<a title="查看" href="javascript:;" onclick="openDialog(\'查看号码池\',\'<c:url value="/maskLine/managerMaskNumberPool?id='+full.id+'&managerType=view"/>\',\'800\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe695;</i>查看</a>'+
                                '<a title="删除" href="javascript:;" onclick="deleteMaskNumPool(\''+full.id+'\',\''+full.appid+'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
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
                "dom": 'rtilp',
                "sErrMode": 'none',
                "columns": columns,
                ajax: dataTablesAjax,
                "columnDefs":columnDefs
            });

            // 搜索功能
            $("#searchButton").on("click", function(){
                var formData = {};
                $('#searchFrom').find('input[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                })
                search_param = formData;
                dataTables.fnDraw();
            });
        })

        /** 删除隐私号码池 **/
        function deleteMaskNumPool(id,appid){
            layer.confirm('是否确定删除？', {
                btn: ['是','否'] //按钮
            }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/maskLine/deleteMaskNumberPool'/>",
                dataType:"json",
                data:{"id":id,"appid":appid},
                success : function(data) {
                    console.log(data);
                    layer.msg(data.info);
                    location.replace(location.href);
                },
                error: function(data){
                    layer.msg(data.info);
                }
            })
            });
        }

    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            客户名称：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入客户名称" id="name" name="name"/>
            Account ID：
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户ID" id="sid" name="sid"/>
            APP ID：
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户ID" id="appid" name="appid"/>
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openDialog('新增号码池','<c:url value="/maskLine/addMaskNumberPool"/>','800','820')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 新增号码池
            </a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="15">隐私号码池列表</th>
            </tr>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="20%">客户名称</th>
                <th width="15%">account ID</th>
                <th width="20%">应用名称</th>
                <th width="15%">APP ID</th>
                <th width="25%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
