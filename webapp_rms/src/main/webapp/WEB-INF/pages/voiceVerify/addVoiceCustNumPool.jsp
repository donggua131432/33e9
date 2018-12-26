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
    <title>号码池创建</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var dataTables = "", search_param = {}, checkFormFlag = false;
        $(function(){
            var dataTablesAjax = {
                url: '<c:url value='/voiceCustNum/getVoiceCustNumberList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                { data: 'id'},
                { data: 'number'},
                { data: 'pname'},
                { data: 'cname'},
                { data: 'area_code' },
                { data: 'addtime'},
                { data: ''},
            ];

            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0],
                    "sClass": "text-c",
                    "render": function (data, type, full) {
                        return '<input type="checkbox" class="checkchild" name="checkbox" value="' + full.id + '"/>&nbsp;&nbsp;' + full.rowNO;
                    }
                },
                {
                    "targets": [1,2,3,4,5,6],
                    "orderable":false,
                    "sClass" : "text-c"
                },
                {
                    "targets": [5],
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
                        return '<a title="删除" href="javascript:;" onclick="deleteSingleCustNum(\''+full.id+'\',\''+full.appid+'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
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
                            "areaCode":$("#areaCode").val().trim(),
                            "number":$("#number").val().trim(),
                            "cname":$("#cname").val().trim()
                        }
                    };
                    dataTables.fnDraw();
                }else{
                    layer.msg("Account ID和APP ID不允许为空信息查询失败！");
                }

            });


            $("#axbCustNumberForm").Validform({
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
                        var sid = gets.trim(), matchReg = /^([0-9a-zA-Z]{32})$/,checkStatus = false;
                        if (matchReg.test(sid)) {
                            $.ajax({
                                type: "post",
                                url: "<c:url value='/voiceCustNum/checkAndgetUserAdminWithCompany'/>",
                                dataType: "json",
                                async: false,
                                data: {'sid': sid,'busType':"02",'status':"00"},
                                success: function (data) {
                                    if (data.status == 0) {
                                        $("#uid").val(data.userAdminResult.uid);
                                        $("#name").text(data.userAdminResult.authenCompany.name).removeAttr("style");
                                        checkStatus = true;
                                    }else{
                                        return data.info;
                                    }
                                }
                            });
                        } else {
                            $("#name").text("客户名称自动匹配").css("color", "#cccccc");
                            return "Account ID不合法！";
                        }
                        if (checkStatus == true) {
                            return true;
                        } else{
                            $("#name").text("客户名称自动匹配").css("color", "#cccccc");
                            return "Account ID不存在！";
                        }
                    },
                    "checkedAppId": function (gets, obj, curform, datatype) {
                        var appId = gets.trim(), sid=$("#sid").val().trim(), checkStatus = 0, matchReg = /^([0-9a-zA-Z]{32})$/;
                        if (matchReg.test(appId)) {
                            $.ajax({
                                type: "post",
                                url: "<c:url value='/maskLine/getAppInfoByMap'/>",
                                dataType: "json",
                                async: false,
                                data: {'appId': appId,'busType':"02",'status':"00"},
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

        function addAxbCustNumber(){
            $("#axbCustNumberForm").submit();
            if(checkFormFlag == true){
                openDialog('单个添加','<c:url value="/voiceCustNum/toAddVoiceCustNumber"/>?sid='+$("#sid").val()+'&appid='+$("#appid").val()+'&uid='+$("#uid").val(),'600','350');
            }

        }

        function importAxbCustNumber(){
            $("#axbCustNumberForm").submit();
            if(checkFormFlag == true) {
                openDialog('批量导入', '<c:url value="/voiceCustNum/importVoiceCustNumber"/>?sid='+$("#sid").val()+'&appid='+$("#appid").val()+'&uid='+$("#uid").val(), '600', '260');
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

        // 导出用户号码资源池
        function downloadVoiceCustNum() {
            if(dataTables._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }
            var formData = "";
            $("#appidForImport").val($("#appid").val());
            $('#searchFrom').find('input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
            });
            $('#searchFrom').find('select[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
            });
            formData = formData.substring(1);
            window.open("<c:url value='/voiceCustNum/downloadVoiceCustNumPool'/>?" + formData);
        }



        /** 删除語音号码池 **/
        function deleteSingleCustNum(id,appid){
            layer.confirm('是否确定删除？', {
                btn: ['是','否'] //按钮
            }, function(){
                $.ajax({
                    type: "post",//使用post方法访问后台
                    dataType: "json",//返回json格式的数据
                    url:"<c:url value='/voiceCustNum/deleteVoiceCustNumber'/>",
                    data: {'strId': id},//要发送的数据
                    success: function (data) {
                        if (data.status == '0') {
                            layer.alert(data.info, {icon: 1});
                            dataTables.fnDraw();
                        }
                        else {
                            layer.alert(data.info, {icon: 1});
                            dataTables.fnDraw();
                        }
                    },
                    error: function (data) {
                        alert("系统异常，请稍后重试！");
                    }
                });
            });
        }



        function deleteVoiceCustNumber() {
            var strId = getid();
            if (strId) {
                layer.confirm('是否确认删除？', {
                    btn: ['是','否'] //按钮
                }, function() {
                    $.ajax({
                        type: "post",//使用post方法访问后台
                        dataType: "json",//返回json格式的数据
                        url:"<c:url value='/voiceCustNum/deleteVoiceCustNumber'/>",
                        data: {'strId': strId},//要发送的数据
                        success: function (data) {
                            if (data.status == '0') {
                                layer.alert(data.info, {icon: 1});
                                $('#checkbox_fix').attr("checked", false);
                                dataTables.fnDraw();
                            }
                            else {
                                layer.alert(data.info, {icon: 1});
                                $('#checkbox_fix').attr("checked", false);
                                dataTables.fnDraw();
                            }
                        },
                        error: function (data) {
                            $('#checkbox_fix').attr("checked", false);
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
    <form action="" class="form form-horizontal" id="axbCustNumberForm">
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
                <button type="button" class="btn btn-primary radius" onclick="addAxbCustNumber();"><i class="Hui-iconfont">&#xe600;</i>单个添加</button>
                <button type="button" class="btn btn-primary radius" onclick="importAxbCustNumber();"><i class="Hui-iconfont">&#xe645;</i>批量导入</button>
            </div>
        </div>
    </form>

    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;margin-top: 10px;"/>
    <!-- 分割线 -->

    <div style="margin-top: 15px;">
        <form id="searchFrom" class="form form-horizontal">
            <input type="hidden" name="appid" id="appidForImport"/>
            城市：<input type="text" class="input-text" style="width: 150px; margin-right: 5px;" id="cname" name="cname"/>
            区号：<input type="text" class="input-text" style="width: 150px; margin-right: 5px;" id="areaCode" name="areaCode"/>
            号码：<input type="text" class="input-text" style="width: 150px; margin-right: 10px;" id="number" name="number">
            <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        </form>
    </div>
    <div style="margin-top: 15px;">
        <button type="button" class="btn btn-primary radius" onclick="deleteVoiceCustNumber();"><i class="Hui-iconfont">&#xe645;</i>删除</button>&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary radius" onclick="downloadVoiceCustNum();"><i class="Hui-iconfont">&#xe645;</i>导出</button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="10%"><input type="checkbox" id="checkbox_fix" class="checkall" name="checkbox_fix" value="">序号</th>
                <th>号码</th>
                <th>省份</th>
                <th>城市</th>
                <th>区号</th>
                <th>添加时间</th>
                <th>操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
