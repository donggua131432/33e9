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
    <title>私密专线列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            客户名称：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入客户名称" id="companyName" name="companyName"/>
            Account ID：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入Account ID" id="sid" name="sid"/>
            APP ID：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入APP ID" id="appid" name="appid"/>
            城市：
            <select type="text" id="cityid" name="cityid" placeholder="城市" class="select" style="width:160px;height: 31px">
                <option value="">请选择城市</option>
                <c:forEach items="${cityList}" var="city">
                    <option value="${city.id}">${city.city}</option>
                </c:forEach>
            </select>
            区号：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入区号" id="areaCode" name="areaCode"/>
            号码状态：
            <select type="text" id="status" name="status" placeholder="请选择号码状态" class="select" style="width:160px;height: 31px">
                <option value="">请选择号码状态</option>
                <option value="01">已分配</option>
                <option value="02">已锁定</option>
                <option value="03">待分配</option>
            </select>
            号码：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入号码" id="number" name="number"/>
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <%--<tr>
                <th scope="col" colspan="10">私密专线列表</th>
            </tr>--%>
            <tr class="text-c">
                <th width="3%">序号</th>
                <th width="15%">account ID</th>
                <th width="17%">客户名称</th>
                <th width="15%">APP ID</th>
                <th width="18%">应用名称</th>
                <th width="5%">城市</th>
                <th width="5%">区号</th>
                <th width="10%">号码</th>
                <th width="5%">号码状态</th>
                <th width="7%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
<script type="text/javascript">
    var dataTables = "";
    var search_param = {};
    $(function(){
        //dataTablesAjax 选项
        var dataTablesAjax = {
            url: '<c:url value='/maskLine/pageMaskNumber'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            {data: 'rowNO'},
            {data: 'sid'},
            {data: 'companyName'},
            {data: 'appid'},
            {data: 'appName'},
            {data: 'city'},
            {data: 'areaCode'},
            {data: 'number'},
            {data: 'status'},
            {data: ''}
        ];

        // 对数据进行处理 （01:已分配  02:已锁定  03:待分配）
        var columnDefs = [
            {
                "targets": [0, 1, 3, 5, 6, 8, -1],
                "sClass" : "text-c"
            },
            {
                "targets": [8],
                "render" : function(data, type, full) {
                    var status = full.status;
                    if (full.status == '01') {
                        status = "已分配";
                    } else if (full.status == '02') {
                        status = "已锁定";
                    } else if (full.status == '03') {
                        status = "待分配";
                    }
                    return status;
                }
            },
            {
                "targets": [-1],
                "data": "id",
                "render": function(data, type, full) {
                    if (full.status == '03') {
                        return '<a title="删除" href="javascript:;" onclick="deleteMaskNum(\''+full.id+'\',\''+ full.number +'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
                    }
                    return "";
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

    /** 删除隐私号码 **/
    function deleteMaskNum(id,num){
        layer.confirm('确定要删除号码：'+ num +'？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/maskLine/deleteMaskNumber'/>",
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
                    layer.alert("删除失败！",{
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
        //
        var formData = "";
        $('#searchFrom').find('input[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
        $('#searchFrom').find('select[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
        formData = formData.substring(1);
        window.open("<c:url value='/maskLine/downloadMaskNum'/>?" + formData);
    }

</script>
</html>
