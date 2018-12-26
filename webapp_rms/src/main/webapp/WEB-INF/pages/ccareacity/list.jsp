<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>区域配置列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        var dataTables = "", search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/ccareacity/pageCcArea'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'rowNO'},
                {data: 'companyName'},
                {data: 'aname'},
                {data: ''}
            ];

            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0,1,2],
                    "orderable":false,
                    "sClass" : "text-c"
                },
                {"targets": [-1], "data": "areaId", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return  '<a title="编辑" href="javascript:void(0);" onclick="openDialog(\'编辑区域信息\',\'<c:url value="/ccareacity/toEditArea?areaId='+full.areaId+'"/>\',\'800\',\'700\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe6df;</i>编辑</a>'
                                + '&nbsp;&nbsp;&nbsp;&nbsp;'
                                + '<a title="查看" href="javascript:void(0);" onclick="openDialog(\'查看区域信息\',\'<c:url value="/ccareacity/toShowArea?areaId='+full.areaId+'"/>\',\'800\',\'700\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe695;</i>查看</a>'
                                + '&nbsp;&nbsp;&nbsp;&nbsp;'
                                + '<a title="删除" href="javascript:void(0);" onclick="delArea(\''+full.areaId+'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
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
                });
                $('#searchFrom').find('select[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });
                search_param = formData;
                dataTables.fnDraw();
            });
        });

        function delArea(areaId) {
            layer.confirm('是否确认删除？', {
                btn: ['是','否'] //按钮
            }, function() {
                $.ajax({
                    type: "post",//使用post方法访问后台
                    dataType: "json",//返回json格式的数据
                    url: "<c:url value='/ccareacity/deleteArea'/>",

                    data: {'areaId': areaId},//要发送的数据
                    success: function (data) {
                        if (data.code == '00') {
                            layer.alert("成功删除区域！", {icon: 1})
                            dataTables.fnDraw();
                        } else if(data.code == '01'){
                            layer.alert(data.msg, {icon: 1});
                            dataTables.fnDraw();
                        }else {
                            dataTables.fnDraw();
                        }
                    },
                    error: function (data) {
                        alert("系统异常，请稍后重试！");
                    }
                });
            });
        }

        /** 导出列表 */
        function downloadCityInfoReport() {
            if(dataTables._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }
            var formData = "";
            $('#searchFrom').find('input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
            });

            formData = formData.substring(1);
            window.open("<c:url value='/ccareacity/downloadareacity'/>?" + formData);
        }
    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            所属企业：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入所属企业" id="companyName" name="companyName"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            区域名称：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入区域名称" id="aname" name="aname"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openDialog('添加区域','<c:url value="/ccareacity/toAddArea"/>','800','700')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 添加区域
            </a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadCityInfoReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <th width="10%">序号</th>
                <th>所属企业</th>
                <th>区域名称</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
