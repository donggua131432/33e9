<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/12/22
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>数据同步</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="openTelnoInfoDialog('添加任务','<c:url value="/openTask/toAdd"/>','1200','800')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加任务</a>
        </span>
    </div>
    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="15%">序号</th>
                <th width="15%">任务名称</th>
                <th width="25%">任务描述</th>
                <th width="25%">操作</th>
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
            url: '<c:url value='/openTask/pageTask'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // id,pname,cname,telno,oname
        var columns = [
            { data: 'rowNO' },
            { data: 'name'},
            { data: 'remark'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="执行列表" href="javascript:;" onclick="openTelnoInfoDialog(\'执行列表\',\'<c:url value="/openTask/toShow?taskID='+full.task_id+'"></c:url>\',\'1200\',\'800\')" class="ml-5" style="text-decoration:none">执行列表</a>';
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openTelnoInfoDialog(\'编辑\',\'<c:url value="/openTask/toEdit?taskID='+full.task_id+'"></c:url>\',\'1200\',\'800\')" class="ml-5" style="text-decoration:none">编辑</a>';
                    _ex_in += '<a title="删除" href="javascript:;" onclick="delTaskPart(\''+full.id+'\')" class="ml-5" style="text-decoration:none">删除</a>';
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
            "aaSorting": [[ 1, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "ordering": false,//关闭排序
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

    });


    /*新增任务*/
    function openTelnoInfoDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    /* 删除任务*/
    function delTaskPart(id){
        layer.confirm('是否删除该任务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/openTask/delete'/>",
                dataType:"json",
                data:{"id":id},
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
