<%--
  Created by IntelliJ IDEA.
  User: DuKai
  Date: 2016/1/29
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>智能云费率配置</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        var dataTables = "";
        var search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/voiceRate/pageVoiceRateUnion'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'sid'},
                {data: 'name'},
                {data: 'addtime'},
                {data: 'per60'},
                {data: 'per60_Discount'},
                {data: ''},
                {data: ''}
            ];

            // 对数据进行处理
            var columnDefs = [
                {"targets": [2],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.addtime) {
                            return o.formatDate(full.addtime, "yyyy-MM-dd hh:mm:ss");
                        }
                        return full.addtime;
                    }
                },
                {"targets": [3], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return ${voiceRate.per60};
                    }
                },
                {"targets": [4], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return (full.per60_discount/10);
                    }
                },
                {"targets": [5], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return $.calcEval("${voiceRate.per60}*"+full.per60_discount+"/1000").toFixed(4);
                    }
                },
                {"targets": [6], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return '<a title="查看" href="javascript:;" onclick="voiceRate_openDialog(\'语音通知费率配置详情\',\'<c:url value="/voiceRate/getVoiceRateDetail?feeid='+full.feeid+'"/>\',\'800\',\'700\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe695;</i>查看</a>';
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


        /*管理员-增加*/
        function voiceRate_openDialog(title,url,w,h){
            layer_show(title,url,w,h);
        }
    </script>

</head>

<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            Account ID：
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户ID" id="sid" name="sid"/>
            客户名称：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入客户名称" id="name" name="name"/>
            生成时间：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'addtime1\')||\'%y-%M-%d\'}'})" id="addtime" name="addtime" class="input-text Wdate" style="width:186px;"/>-
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'addtime\')}'})" id="addtime1" name="addtime1" class="input-text Wdate" style="width:186px;"/>
            是否永久有效：
            <select type="text" id="forever" name="forever" style="width: 160px;height: 31px;">
                <option value="">请选择</option>
                <option value="0">否</option>
                <option value="1">是</option>
            </select>
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="7">语音通知费率列表</th>
            </tr>
            <tr class="text-c">
                <th width="150">Account ID</th>
                <th width="200">客户名称</th>
                <th width="120">创建时间</th>
                <th width="120">标准价</th>
                <th width="120">折扣率</th>
                <th width="120">折扣价</th>
                <th width="100">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
