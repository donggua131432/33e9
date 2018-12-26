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
    <title>号码池管理</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var dataTables = "",
             search_param = {
                 params:{
                    "appid":"${maskNumPool.appid}"
                 }
             };
        $(function(){
            var dataTablesAjax = {
                url: '<c:url value='/maskLine/getMaskNumberList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                <c:if test="${managerType == 'edit'}">
                { data: 'id'},
                </c:if>
                { data: 'city'},
                { data: 'area_code' },
                { data: 'number'},
                { data: 'status'}
            ];

            // 对数据进行处理
            var columnDefs = [
                <c:if test="${managerType == 'edit'}">
                {
                    "targets": [0, 1, 2, 3, 4],
                    "sClass": "text-c"
                },
                {
                    "targets": [0],
                    "sClass": "text-c",
                    "render": function (data, type, full) {
                        return '<input type="checkbox" class="checkchild" name="checkbox" value="' + full.id + '"/>&nbsp;&nbsp;' + full.rowNO;
                    }
                },
                {
                    "targets": [4],
                    "sClass" : "text-c",
                    "orderable":false,
                    "render": function(data, type, full) {
                        if(full.status == "01"){
                            return "已分配";
                        }else if(full.status == "02"){
                            return "已锁定";
                        }else if(full.status == "03"){
                            return "待分配";
                        }
                    }
                }
                </c:if>
                <c:if test="${managerType == 'view'}">
                {
                    "targets": [0, 1, 2, 3],
                    "sClass": "text-c"
                },
                {
                    "targets": [3],
                    "sClass" : "text-c",
                    "orderable":false,
                    "render": function(data, type, full) {
                        if(full.status == "01"){
                            return "已分配";
                        }else if(full.status == "02"){
                            return "已锁定";
                        }else if(full.status == "03"){
                            return "待分配";
                        }
                    }
                }
                </c:if>

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
                "ajax": dataTablesAjax,
                "columnDefs":columnDefs
            });

            // 搜索功能
            $("#search").on("click", function(){
                search_param = {
                    params:{
                        "appid":"${maskNumPool.appid}",
                        "cityid":$("#cityid").val().trim(),
                        "areaCode":$("#areaCode").val().trim(),
                        "number":$("#number").val().trim()
                    }
                };
                dataTables.fnDraw();
            });

        });

        function addMaskNumber(){
            openDialog('单个添加','<c:url value="/maskLine/addMaskNumber"/>?sid=${maskNumPool.sid}&appid=${maskNumPool.appid}','600','350');


        }

        function importMaskNumber(){
            openDialog('批量导入', '<c:url value="/maskLine/importMaskNumber"/>?sid=${maskNumPool.sid}&appid=${maskNumPool.appid}', '600', '260');
        }


        function maskNumCancel(id){
            $.ajax({
                type:"post",
                url:"<c:url value='/maskNum/deleteMaskNumber'/>",
                dataType:"json",
                data:{"id" : id},
                success:function(result){
                    if (result.status == 0) {
                        layer.alert("删除成功", {icon: 1});
                        dataTables.fnDraw();
                    } else {
                        layer.alert("删除失败", {icon: 2});
                    }
                }
            });
        }

        function deleteMaskNumber() {
            var strId = getid();
            if (strId) {
                layer.confirm('是否确认删除？', {
                    btn: ['是','否'] //按钮
                }, function() {
                    $.ajax({
                        type: "post",//使用post方法访问后台
                        dataType: "json",//返回json格式的数据
                        url:"<c:url value='/maskNum/deleteMaskNumber'/>",
                        data: {'strId': strId},//要发送的数据
                        success: function (data) {
                            if (data.status == '0') {
//                                layer.alert("成功删除号码！",{icon:1},function(){
//                                    dataTables.fnDraw();
//                                    layer_close();
//                                })
                                layer.alert("成功删除号码！", {icon: 1});
                                dataTables.fnDraw();
                                dataTables2.fnDraw();
                            }
                            else {
                                dataTables.fnDraw();
                                dataTables2.fnDraw();
                            }
                        },
                        error: function (data) {
                            alert("系统异常，请稍后重试！");
                        }
                    });
                });
            }
            else {
                layer.alert("请选择要删除的号码！",{icon:0})
            }

        }

        function getid(){
            //获得checkbox的id值
            var strId = "";
            $("input[name='checkbox']:checked").each(function(i){
                console.log(i);
                if ($(this).val()){
                    if(i < $("input[name='checkbox']:checked").length-1){
                        strId += $(this).val() + ",";
                    }else{
                        strId += $(this).val();
                    }
                }

            });
            return strId;
        }
    </script>
</head>
<body>
<div class="pd-20">
    <div class="form form-horizontal">
        <div class="row cl">
            <label class="form-label col-4">Account ID：</label>
            <div class="formControls col-8">
                <span>${maskNumPool.sid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">客户名称：</label>
            <div class="formControls col-8">
                <span>${maskNumPool.name}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">APP ID：</label>
            <div class="formControls col-8">
                <span>${maskNumPool.appid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">应用名称：</label>
            <div class="formControls col-8">
                <span>${maskNumPool.appName}</span>
            </div>
        </div>
        <c:if test="${managerType == 'edit'}">
            <div class="row cl">
                <label class="form-label col-4">号码添加：</label>
                <div class="formControls col-8">
                    <button type="button" class="btn btn-primary radius" onclick="addMaskNumber();"><i class="Hui-iconfont">&#xe600;</i>单个添加</button>
                    <button type="button" class="btn btn-primary radius" onclick="importMaskNumber();"><i class="Hui-iconfont">&#xe645;</i>批量导入</button>
                    <button type="button" class="btn btn-primary radius" onclick="deleteMaskNumber();"><i class="Hui-iconfont">&#xe645;</i>批量删除</button>
                </div>
            </div>
        </c:if>
    </div>
    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;margin-top: 10px;"/>
    <!-- 分割线 -->

    <div style="margin-top: 15px;">
        城市：<span class="select-box" style="width:200px;margin-right: 5px;">
            <select id="cityid" name="cityid"  class="select">
                <option value="">请选择城市</option>
                <c:forEach items="${cityList}" var="city">
                    <option value="${city.id}">${city.city}</option>
                </c:forEach>
            </select>
        </span>
        区号：<input type="text" class="input-text" style="width: 150px; margin-right: 5px;" id="areaCode" name="areaCode"/>
        号码：<input type="text" class="input-text" style="width: 150px; margin-right: 10px;" id="number" name="number">
        <button type="button" class="btn btn-success radius" id="search" name="search"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <c:if test="${managerType == 'edit'}">
                    <th width="10%"><input type="checkbox" id="checkbox_fix" class="checkall" name="" value="">序号</th>
                    <th width="20%">城市</th>
                    <th width="20%">区号</th>
                    <th width="20%">号码</th>
                    <th width="15%">状态</th>
                </c:if>
                <c:if test="${managerType == 'view'}">
                    <th width="25%">城市</th>
                    <th width="25%">区号</th>
                    <th width="25%">号码</th>
                    <th width="25%">状态</th>
                </c:if>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
