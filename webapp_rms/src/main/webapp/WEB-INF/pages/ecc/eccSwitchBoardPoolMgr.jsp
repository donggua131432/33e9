<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>外显号码</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var dataTables = "", search_param = {};

        $(function(){
            $.Huitab("#tab-system .tabBar span","#tab-system .tabCon","current","click","0");

            initPCArea("pcode1","ccode1","请选择");
            initPCArea("pcode2","ccode2","请选择");

            var dataTablesAjax = {
                url: '<c:url value='/eccSwitchBoardPool/pageEccSwitchBoardList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                { data: 'id' },
                { data: 'number'},
                { data: 'pname' },
                { data: 'cname' },
                { data: 'status'},
                { data: 'companyName'},
                { data: 'sid' },
                { data: 'appName'},
                { data: 'appId' },

            ];

            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0,1,2,3,4,5,6,7,8,9],
                    "sClass" : "text-c"
                },
                {
                    "targets": [0],
                    "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return '<input type="checkbox" name="checkbox" value="'+full.id+'"/>&nbsp;&nbsp;'+full.rowNO;
                    }
                },
                {
                    "targets": [4],
                    "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.status == '01') {
                            return '<span class="label label-success radius">已分配</span>';
                        }
                        if (full.status == '02') {
                            return '<span class="label label-primary radius">已锁定</span>';
                        }
                        if (full.status == '03') {
                            return '<span class="label label-primary radius">待分配</span>';
                        }
                        return full.status;
                    }
                },

                {
                    "targets": [9],
                    "sClass" : "text-c",
                    "orderable":false,
                    "render": function(data, type, full) {
                        var _ex_in = "";
                        if (full.status == '03') {
                            _ex_in += '<a title="编辑" href="javascript:;" onclick="openDialog(\'编辑\',\'<c:url value="/eccSwitchBoardPool/toEdit?id='+full.id+'"/>\',\'450\',\'300\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i>编辑</a>';
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
            $("#search").on("click", function(){
                var formData = {};
                $("#searchFrom input[name]").each(function(){
                    console.log(1);
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });
                $('#searchFrom').find('select[name]').each(function(){
                    console.log(2);
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });
                search_param = formData;
                console.log(formData);
                dataTables.fnDraw();
            });


            //复选框全选
            $(".checkall").click(function () {
                var check = $(this).prop("checked");
                $(".checkchild").prop("checked", check);
            });
        });

        function addPhone(){
            openDialog('总机号码添加','<c:url value="/eccSwitchBoardPool/toAdd"/>','450','300');
        }

        function importPhone(){
            openDialog('批量导入', '<c:url value="/eccSwitchBoardPool/import"/>', '600', '260');
        }

        // 导出公共号码资源池
        function download() {
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
            window.open("<c:url value='/eccSwitchBoardPool/download'/>?" + formData);
        }

        function deletePhones() {
            var strId = getid();
            if (strId) {
                layer.confirm('是否确认删除？', {
                    btn: ['是','否'] //按钮
                }, function() {
                    $.ajax({
                        type: "post",//使用post方法访问后台
                        dataType: "json",//返回json格式的数据
                        url: "<c:url value='/eccSwitchBoardPool/deleteEccSwitchBoards'/>",

                        data: {'strId': strId},//要发送的数据
                        success: function (data) {
                            if (data.code == '00') {
//                                layer.alert("成功删除号码！",{icon:1},function(){
//                                    dataTables.fnDraw();
//                                    layer_close();
//                                })
                                layer.alert("成功删除号码！", {icon: 1});
                                dataTables.fnDraw();
//                                dataTables2.fnDraw();
                            }else if(data.code == '02'){
                                layer.alert(data.msg, {icon: 1});
                                dataTables.fnDraw();
//                                dataTables2.fnDraw();
                            }
                            else {
                                dataTables.fnDraw();
//                                dataTables2.fnDraw();
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

        /*修改并发数*/
        function openUserDialog(title,url,w,h){
            layer_show(title,url,w,h);
        }
    </script>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="page-container" style="padding: 20px;">
    <%--<form class="form form-horizontal" id="form-article-add">--%>
        <div id="tab-system" class="HuiTab">
            <div ><H2><span>总机号码</span></H2></div>
            <div class="tabCon">
                <form id="searchFrom" class="form form-horizontal">
                    <div class="row cl">
                        总机号码：
                        <input type="text" class="input-text" style="width:186px" placeholder="请输入总机号码" id="number" name="number"/>&nbsp;&nbsp;&nbsp;&nbsp;
                        状态：
                        <select class="input-text cl-1" id="status" name="status" style="width: 200px;">
                            <option value="">全部</option>
                            <option value="01">已分配</option>
                            <option value="02">已锁定</option>
                            <option value="03">待分配</option>
                        </select>&nbsp;&nbsp;&nbsp;&nbsp;

                        省份：
                        <select name="pcode" class="input-text" id="pcode1" style="width:200px;height: 31px">
                        </select>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        城市：
                        <select name="ccode" class="input-text" id="ccode1" style="width:200px;height: 31px">
                        </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    </div>

                    <div class="row cl">
                        Account ID：
                        <input type="text" class="input-text" style="width:186px" placeholder="请输入Account ID" id="sid" name="sid"/>&nbsp;&nbsp;&nbsp;&nbsp;
                        客户名称：
                        <input type="text" class="input-text" style="width:186px" placeholder="请输入客户名称" id="companyName" name="companyName"/>&nbsp;&nbsp;&nbsp;&nbsp;
                        AppId：
                        <input type="text" class="input-text" style="width:186px" placeholder="请输入AppId" id="appId" name="appId"/>&nbsp;&nbsp;&nbsp;&nbsp;
                        应用名称：
                        <input type="text" class="input-text" style="width:186px" placeholder="请输入应用名称" id="appName" name="appName"/>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-success radius" id="search"><i class="Hui-iconfont">&#xe665;</i>查询</button>
                    </div>

                </form>

                <div class="cl pd-5 bg-1 bk-gray mt-20">

                    <button type="button" class="btn btn-primary radius" onclick="addPhone();"><i class="Hui-iconfont">&#xe600;</i>单个添加</button>
                    <button type="button" class="btn btn-primary radius" onclick="importPhone();"><i class="Hui-iconfont">&#xe645;</i>批量添加</button>
                    <button type="button" class="btn btn-primary radius" onclick="deletePhones();"><i class="Hui-iconfont">&#xe609;</i>删除</button>

                    <button style="float:right;" type="button" class="btn btn-primary radius" onclick="download();">导出</button>
                </div>
                <div class="mt-20">
                    <table class="table table-border table-bordered table-hover table-bg table-sort">
                        <thead>
                        <tr class="text-c">
                            <th width="5%"><input type="checkbox" name="" id="checkbox_sip" value="">序号</th>
                            <th width="10%">总机号码</th>
                            <th width="5%">省份</th>
                            <th width="5%">城市</th>
                            <th width="5%">状态</th>
                            <th width="5%">客户名称</th>
                            <th width="10%">Account ID</th>
                            <th width="5%">应用名称</th>
                            <th width="10%">Appid</th>
                            <th width="10%">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
</div>

</body>
</html>
