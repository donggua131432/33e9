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
    <title>号码池创建</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var dataTables = "", search_param = {}, checkFormFlag = false;
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
                { data: 'city'},
                { data: 'area_code' },
                { data: 'number'},
                { data: 'status'},
                { data: ''}
            ];

            // 对数据进行处理
            var columnDefs = [
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
                },
                {
                    "targets": [4],
                    "sClass" : "text-c",
                    "orderable":false,
                    "render": function(data, type, full) {
                        var _ex_in = "";
                        if(full.status == "01"){
                            _ex_in += '<a title="取消分配" href="javascript:void(0);" onclick="maskNumCancel(\''+full.id+'\');" class="ml-5" style="text-decoration:none;color:blue;">删除</a>';
                        }else if(full.status == "02"){
                            _ex_in += '<span style="color:#ccc">删除<span>';
                        }
                        return _ex_in;
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
                "ajax": dataTablesAjax,
                "columnDefs":columnDefs
            });

            // 搜索功能
            $("#sarch").on("click", function(){
                if($("#appid").val() != "" && $("#sid").val() != ""){
                    search_param = {
                        params:{
                            "appid":$("#appid").val().trim(),
                            "cityid":$("#cityid").val().trim(),
                            "areaCode":$("#areaCode").val().trim(),
                            "number":$("#number").val().trim()
                        }
                    };
                    dataTables.fnDraw();
                }else{
                    layer.msg("Account ID和APP ID不允许为空信息查询失败！");
                }

            });


            $("#maskNumberForm").Validform({
                tiptype: 2,
                ajaxPost: true,
                beforeCheck:function(){
                    //在表单提交执行验证之前执行的函数，curform参数是当前表单对象。
                    //这里明确return false的话将不会继续执行验证操作;
                    checkFormFlag = false;
                },
                beforeSubmit:function(){
                    //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                    //这里明确return false的话表单将不会提交;
                    checkFormFlag = true;
                    return false;
                },
                datatype: {
                    "checkedSid": function (gets, obj, curform, datatype) {
                        var sid = gets.trim(), checkStatus = false, matchReg = /^([0-9a-zA-Z]{32})$/;
                        if (matchReg.test(sid)) {
                            $.ajax({
                                type: "post",
                                url: "<c:url value='/userAdmin/getUserAdminWithCompany'/>",
                                dataType: "json",
                                async: false,
                                data: {'sid': sid},
                                success: function (data) {
                                    if (data.status == 0) {
                                        $("#uid").val(data.userAdminResult.uid);
                                        $("#name").text(data.userAdminResult.authenCompany.name).removeAttr("style");
                                        checkStatus = true;
                                    }
                                }
                            });
                        } else {
                            $("#name").text("客户名称自动匹配").css("color", "#cccccc");
                            return "Account ID不合法！";
                        }
                        if (checkStatus == true) {
                            return true;
                        } else {
                            $("#name").text("客户名称自动匹配").css("color", "#cccccc");
                            return "Account ID不存在！";
                        }
                    },
                    "checkedAppId": function (gets, obj, curform, datatype) {
                        var appId = gets.trim(), sid=$("#sid").val().trim(), checkStatus = 0, matchReg = /^([0-9a-zA-Z]{32})$/;
                        if (matchReg.test(appId)) {
                            $.ajax({
                                type: "post",
                                url: "<c:url value='/maskLine/getAppInfoById'/>",
                                dataType: "json",
                                async: false,
                                data: {'appId': appId},
                                success: function (result) {
                                    if (result.code == 0) {
                                        if(result.data.sid == sid){
                                             $("#appName").text(result.data.appName).removeAttr("style");
                                             checkStatus = 0;
                                        }else{
                                            $("#appName").text(result.data.appName).removeAttr("style");
                                            checkStatus = 2;
                                        }
                                    }else{
                                        checkStatus = 1;
                                    }
                                }
                            });
                        } else {
                            $("#appName").text("应用名称自动匹配").css("color", "#cccccc");
                            return "APP ID不合法！";
                        }

                        if (checkStatus == 0) {
                            return true;
                        } else if(checkStatus == 1) {
                            $("#appName").text("应用名称自动匹配").css("color", "#cccccc");
                            return "APP ID不存在！";
                        } else if(checkStatus == 2) {
                            return "APP ID与Account ID不匹配！";
                        }
                    }
                }
            })
        });

        function addMaskNumber(){
            $("#maskNumberForm").submit();
            if(checkFormFlag == true){
                openDialog('单个添加','<c:url value="/maskLine/addMaskNumber"/>?sid='+$("#sid").val()+'&appid='+$("#appid").val()+'&uid='+$("#uid").val(),'600','350');
            }

        }

        function importMaskNumber(){
            $("#maskNumberForm").submit();
            if(checkFormFlag == true) {
                openDialog('批量导入', '<c:url value="/maskLine/importMaskNumber"/>?sid='+$("#sid").val()+'&appid='+$("#appid").val()+'&uid='+$("#uid").val(), '600', '260');
            }
        }


        function maskNumCancel(id){
            $.ajax({
                type:"post",
                url:"<c:url value='/maskNum/deleteMaskNumber'/>",
                dataType:"json",
                data:{"id" : id},
                success:function(result){
                    if (result.status == 0) {
                        layer.alert(result.info, {icon: 1});
                        dataTables.fnDraw();
                    } else {
                        layer.alert(result.info, {icon: 2});
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="" class="form form-horizontal" id="maskNumberForm">
        <div class="row cl">
            <label class="form-label col-3">Account ID：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text radius" style="width:300px;" maxlength="32"  datatype="checkedSid" placeholder="请输入account ID" id="sid" name="sid" errormsg="Account ID不合法" nullmsg="Account ID不能为空"/>
                <input type="hidden" name="uid" id="uid" />
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">客户名称：</label>
            <div class="formControls col-9">
                <span id="name"style="color: #cccccc">客户名称自动匹配</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">APP ID：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text radius" style="width:300px;" maxlength="32" datatype="checkedAppId" placeholder="请输入APP ID" id="appid" name="appid" errormsg="APP ID不合法" nullmsg="APP ID不能为空"/>
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">应用名称：</label>
            <div class="formControls col-9">
                <span id="appName"style="color: #cccccc">应用名称自动匹配</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">号码添加：</label>
            <div class="formControls col-9">
                <%--<a href="javascript:void(0);" onclick="openDialog('单个添加','<c:url value="/maskLine/addMaskNumber"/>','600','350')" class="btn btn-primary radius">
                    <i class="Hui-iconfont">&#xe600;</i>单个添加
                </a>
                <a href="javascript:void(0);" onclick="openDialog('批量导入','<c:url value="/maskLine/importMaskNumber"/>','600','260')" class="btn btn-primary radius">
                    <i class="Hui-iconfont">&#xe645;</i>批量导入
                </a>--%>
                <button type="button" class="btn btn-primary radius" onclick="addMaskNumber();"><i class="Hui-iconfont">&#xe600;</i>单个添加</button>
                <button type="button" class="btn btn-primary radius" onclick="importMaskNumber();"><i class="Hui-iconfont">&#xe645;</i>批量导入</button>
            </div>
        </div>
    </form>

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
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="20%">城市</th>
                <th width="20%">区号</th>
                <th width="20%">号码</th>
                <th width="15%">状态</th>
                <th width="25%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
