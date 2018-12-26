<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2017/5/1
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>客户号码资源池配置</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        var dataTables = "";
        var search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/voiceCustNum/getPageList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'rowNO'},
                {data: 'addtime'},
                {data: 'name'},
                {data: 'sid'},
                {data: 'app_name'},
                {data: 'appid'},
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
                    "targets": [1],
                    "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.addtime) {
                            return o.formatDate(full.addtime, "yyyy-MM-dd hh:mm:ss");
                        }
                        return full.addtime;
                    }
                },
                {"targets": [6], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return '<a title="编辑" href="javascript:;" onclick="openDialog(\'编辑号码池\',\'<c:url value="/voiceCustNum/managerVoiceCustNumberPool?appid='+full.appid+'&managerType=edit"/>\',\'800\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe6df;</i>编辑</a>'+
                                '<a title="删除" href="javascript:;" onclick="deleteVoiceAppPool(\''+full.id+'\',\''+full.appid+'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
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

        /** 删除語音号码池 **/
        function deleteVoiceAppPool(id,appid){
            layer.confirm('是否确定删除？', {
                btn: ['是','否'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/voiceCustNum/deleteVoiceAppPool'/>",
                    dataType:"json",
                    data:{"id":id,"appid":appid},
                    success : function(data) {
                        layer.alert(data.info, {icon: 1});
                        dataTables.fnDraw();
                    },
                    error: function(data){
                        layer.alert(data.info, {icon: 1});
                    }
                })
            });
        }



        // 导出账号应用信息
        function downloadVoiceAppInfo() {
            if(dataTables._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }

            var formData = {};
            $('#searchFrom').find('input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
            });
            window.open("<c:url value='/voiceCustNum/downloadVoiceAppInfo'/>?" + formData);
        }

    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            <div class="text-c">
            创建时间：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" name="timemin" datatype="*" class="input-text Wdate" style="width:195px;">
            -
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" name="timemax" class="input-text Wdate" style="width:195px;">
            &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
            Account ID：
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户ID" id="sid" name="sid"/>&nbsp;&nbsp;&nbsp;&nbsp;
            客户名称：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入客户名称" id="name" name="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <br>
            <div class="text-c">
            APP ID：
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户ID" id="appid" name="appid"/>&nbsp;&nbsp;&nbsp;&nbsp;
            应用名称：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入应用名称" id="appName" name="appName"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
            </div>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openDialog('新增号码池','<c:url value="/voiceCustNum/addVoiceCustNumberPool"/>','800','820')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 新增号码池
            </a>
        </span>
        <span class="r">
            <button type="button" class="btn btn-primary radius" onclick="downloadVoiceAppInfo();"><i class="Hui-iconfont">&#xe645;</i>导出</button>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="15">用户号码资源池列表</th>
            </tr>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="15%">创建时间</th>
                <th width="18%">客户名称</th>
                <th width="15%">account ID</th>
                <th width="18%">应用名称</th>
                <th width="15%">APP ID</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
