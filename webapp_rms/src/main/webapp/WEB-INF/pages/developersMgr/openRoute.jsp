<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/8/22
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>业务专用路由配置</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>

<jsp:include page="../common/nav.jsp"/>

<body>
<div class="pd-20">
    <%--<span class="text-l">业务专用路由配置</span><br>--%>
    <div class="text-c">

        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead  class="text-c">
            <tr >
                <th width="5%">序号</th>
                <th width="5%">业务名称</th>
                <th width="5%">业务编码</th>
                <th width="5%">业务使用专用路由</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    $(document).ready(function() {
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/codeType/pageCodeType'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
            { data: 'rowNO' },
            { data: 'bus_name'},
            { data: 'bus_type'},
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
                    if (full.status == '00') {
                        _ex_in += '<a style="text-decoration:none" onClick="StatusUpdate(this, \''+full.bus_type+'\')"  href="javascript:;" title="开启" class="ml-5"><span class="btn btn-success radius">开启</span></a>';
                    } else if (full.status == '01' ){
                        _ex_in += '<a style="text-decoration:none"  onClick="CloseStatusUpdate(this, \''+full.bus_type+'\')" href="javascript:;" title="关闭" class="ml-5"><span class="btn btn-warning radius">关闭</span></a>';
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
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

    });
    function StatusUpdate(obj, busType){
        layer.confirm('是否确认开启？', {
            btn: ['是','否'] //按钮
        }, function(){
            layer.msg("操作中请稍候......");
            $.ajax({
                type:"post",
                url:"<c:url value='/codeType/openRoute'/>",
                dataType:"json",
                data:{"busType":busType},
                success:function(data){
                    if (data.code == '00') {
                        layer.alert("修改成功", {icon: 1});
                        datatables.fnDraw();
                    } else {
                        layer.alert("修改失败", {icon: 2});
                    }
                }
            });
        });
    }

    function CloseStatusUpdate(obj, busType){
        layer.confirm('是否确认关闭？', {
            btn: ['是','否'] //按钮
        }, function(){
            layer.msg("操作中请稍候......");
            $.ajax({
                type:"post",
                url:"<c:url value='/codeType/closeRoute'/>",
                dataType:"json",
                data:{"busType":busType},
                success:function(data){
                    if (data.code == '00') {
                        layer.alert("修改成功", {icon: 1});
                        datatables.fnDraw();
                    } else {
                        layer.alert("修改失败", {icon: 2});
                    }
                }
            });
        });
    }
</script>
</body>
</html>
