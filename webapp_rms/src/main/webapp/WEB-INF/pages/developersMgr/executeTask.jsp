<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/12/26
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>任务执行</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
</head>
<body>

<input type="hidden" id="taskID" name="taskID" value="${taskID}" >
<div class="pd-20">
    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="10%">序号</th>
                <th width="20%">URL</th>
                <th width="25%">上次执行时间</th>
                <th width="25%">执行结果</th>
                <th width="20%">操作</th>
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
            url: '<c:url value='/openTask/pageURLList?taskID=${taskID}'/>',
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
            { data: 'url'},
            { data: 'updatetime'},
            { data: 'result'},
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
                "targets": [2],
                "data": "id",
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.updatetime) {
                        return o.formatDate(full.updatetime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.updatetime;
                }
            },
            {
                "targets": [3],
                "data": "id",
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.result=='00') {
                        return "成功";
                    }else if (full.result == null){
                        return "";
                    }else {
                        return "失败";
                    }
                    return full.result;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a style="text-decoration:none" onClick="StatusUpdate(this, \''+full.id+'\',\''+full.url+'\',\''+full.task_id+'\')"  href="javascript:;" title="开启" class="ml-5"><span class="btn btn-success radius">执行</span></a>';
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

    /* 执行任务*/
    function StatusUpdate(obj,id,url,task_id){
        layer.confirm('是否确认执行该任务？', {
            btn: ['是','否'] //按钮
        }, function(){
            layer.msg("操作中请稍候......");
            $.ajax({
                type:"post",
                url:"<c:url value='/openTask/executeTask'/>",
                dataType:"json",
                data:{"id":id,"task_id":task_id,"url":url},
                success:function(data){
                    if (data.code == '88') {
                        layer.alert("请稍后，每条任务执行的最小间隔时间是5分钟", {icon: 2});
                    }else if (data.code == '00') {
                        layer.alert("执行成功", {icon: 1});
                        datatables.fnDraw();
                    } else {
                        layer.alert("执行失败", {icon: 2});
                    }
                }
            });
        });
    }

</script>
</body>
</html>
