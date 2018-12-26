<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>供应商资源信息</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        var dataTables = "", search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value="/relayRes/pageRes"/>?supId=${supplier.supId}',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'rowNO'},
                {data: 'resName'},
                {data: 'id'},
                {data: 'relayName'},
                {data: 'relayNum'},
                {data: 'operator'},
                {data: ''}
            ];
            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0,1,2,3,4,5],
                    "orderable":false,
                    "sClass" : "text-c"
                },
                {
                    "targets": [5],
                    "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.operator == '00') {
                            return '中国移动';
                        }
                        if (full.operator == '01') {
                            return '中国联通';
                        }
                        if (full.operator == '02') {
                            return '中国电信';
                        }
                        if (full.operator == '06') {
                            return '其他';
                        }
                        return full.operator;
                    }
                },
                {"targets": [-1], "sClass" : "text-c",
                    "render": function(data, type, full) {
                        var _ex_in = "";
                        _ex_in += '<a title="查看" href="javascript:void(0);" onclick="openDialog(\'查看资源信息\',\'<c:url value="/relayRes/toShowRes?id='+full.id+'&operationType=view"/>\',\'900\',\'800\')" class="ml-5" style="text-decoration:none">查看</a>';
                        _ex_in += '<a title="编辑" href="javascript:void(0);" onclick="openDialog(\'编辑资源信息\',\'<c:url value="/relayRes/toEditRes?id='+full.id+'&operationType=edit"/>\',\'900\',\'800\')" class="ml-5" style="text-decoration:none">编辑</a>';
                        _ex_in += '<a title="删除" href="javascript:void(0);" onclick="delRes(\''+full.id+'\',\''+full.resName+'\');" class="ml-5" style="text-decoration:none">删除</a>';
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

            $(window).resize(function(){
                $(".table-sort").width("100%");
            });

        });

        /*管理员-增加*/
        function openFullDialog(title,url,w,h){
            layer_full(title,url,w,h);
        }

        /** 删除供应商 **/
        function delRes(id,name){
            layer.confirm('记录一旦删除将不可以恢复。</br>确定要删除【'+ name +'】吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/relayRes/delRes'/>",
                    dataType:"json",
                    data:{"id":id},
                    success : function(data) {
                        layer.alert(data.msg,{
                            icon: data.code == 'ok'? 1 : 2,
                            skin: 'layer-ext-moon'
                        },function (){
                            location.replace(location.href);
                        });
                    },
                    error: function(data){
                        layer.alert(data,{
                            icon: 2,
                            skin: 'layer-ext-moon'
                        });
                    }
                });
            });
        }

        /** 导出列表 */
        function downloadReport() {
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
            window.open("<c:url value='/relayRes/downloadReport'/>?supId=${supplier.supId}&" + formData);
        }
    </script>
</head>
<body>

<div class="pd-20">
    <div class="text-c">
        供应商名称：<c:out value="${supplier.supName}"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        供应商ID：<c:out value="${supplier.supId}"/>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openDialog('新增资源','<c:url value="/relayRes/toAddRes"/>?supId=${supplier.supId}','900','800')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 新增资源
            </a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th width="5%">序号</th>
                    <th width="15%">线路资源名称</th>
                    <th width="20%">线路资源ID</th>
                    <th width="15%">中继名称</th>
                    <th width="15%">中继ID</th>
                    <th width="15%">归属运营商</th>
                    <th width="15%">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
