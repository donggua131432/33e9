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
    <title>中继强显号号码池管理</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var dataTables = "", search_param = {
            params:{
                "relayNum":"${sipBasic.relayNum}"
            }
        };
        $(function(){
            var dataTablesAjax = {
                url: '<c:url value='/relayNumPool/getRelayNumList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };
            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                { data: 'rowNO' },
                { data: 'number'},
                { data: 'create_time' },
                { data: 'max_concurrent'}
                <c:if test="${managerType == 'edit'}">
                ,{ data: ''}
                </c:if>
            ];
            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0,1,2,3],
                    "sClass" : "text-c"
                },
                {
                    "targets": [2],
                    "sClass" : "text-c",
                    "render": function(data, type, full) {
                        var create_time = full.create_time;
                        if (create_time) {
                            return o.formatDate(create_time,"yyyy-MM-dd hh:mm:ss")
                        }
                        return create_time;
                    }
                }
                ,{
                    "targets": [4],
                    "sClass" : "text-c",
                    "orderable":false,
                    "render": function(data, type, full) {
                        var _ex_in = "";
                        _ex_in += '<a title="修改并发数" href="javascript:;" onclick="openUserDialog(\'修改并发数\',\'<c:url value="/relayNumPool/toUpdateMax?id='+full.id+'"></c:url>\',\'500\',\'350\')" class="ml-5" style="text-decoration:none;color:blue;">修改</a>';
                        _ex_in += '<a title="删除号码" href="javascript:void(0);" onclick="RelayNumCancel(\''+full.id+'\');" class="ml-5" style="text-decoration:none;color:blue;">删除</a>';

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
                search_param = {
                    params:{
                        "relayNum":"${sipBasic.relayNum}",
                        "number":$("#number").val().trim()
                    }
                };
                dataTables.fnDraw();
            });


            //设置复选框的值
            $(".skin-minimal input").each(function(){
                if(${sipBasic.isForce==1}){
                    $(this).iCheck('check');
                }
            });


       /*    $('.skin-minimal input').on('ifChecked', function(event){
                //如果是选中，点击后则为不选中
//               $(this).val("1");
                console.info($(this).val());
//                isForce("设置系统默认强显中继?",'1');

            });
            $('.skin-minimal input').on('ifUnchecked', function(event){
                //如果不选中，点击后则为选中
//                $(this).val("0");
                console.info($(this).val());
//                isForce("取消系统默认强显中继?",'0');

            });*/

            $('.skin-minimal input').iCheck({
                checkboxClass: 'icheckbox-blue',
                increaseArea: '20%'
            });


            // 表单校验
            $("#updateIsForce").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    $.Showmsg(data.info);
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.status == "0"){
                            //刷新父级页面
//                            parent.location.replace(parent.location.href);
                            //关闭当前窗口
//                            layer_close();
                            parent.dataTables.fnDraw();
                        }
                    },2000);
                }
            });

        });

        var  isForce = function(title,isForce){
            $.ajax({
                type:"post",
                url:'<c:url value="/relayNumPool/updateIsForce"/>',
                dataType:"json",
                data:{"isForce" : isForce,"id":${sipBasic.id}},
                success:function(result){
                    if (result.status == 0) {
                        if($("#isForce").prop("checked")==true){
                            layer.msg("设置系统默认强显中继",{icon: 1});
                        }else{
                            layer.msg("取消系统默认强显中继",{icon: 2});
                        }

                        parent.dataTables.fnDraw();
                    } else {
                        layer.msg("修改失败", {icon: 2});
                    }
                }
            });
        };


        function RelayNumCancel(id){
            layer.confirm('是否删除号码？', {
                btn: ['是','否'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/relayNumPool/deleteRelayNumber'/>",
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
            });
        }

        function addMaskNumber(){
            openDialog('单个添加','<c:url value="/relayNumPool/addRelayNumber"/>?relayNum=${sipBasic.relayNum}','600','350');
        }

        function importMaskNumber(){
            openDialog('批量导入', '<c:url value="/relayNumPool/importRelayNumber"/>?relayNum=${sipBasic.relayNum}', '600', '260');
        }
        /*修改并发数*/
        function openUserDialog(title,url,w,h){
            layer_show(title,url,w,h);
        }
        $(window).resize(function(){
            $(".table-sort").width("100%");
        });
    </script>
</head>
<body>
<div class="pd-20">
    <div class="form form-horizontal">
        <div class="row cl">
            <label class="form-label col-4">中继名称：</label>
            <div class="formControls col-8">
                <span>${sipBasic.relayName}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">中继群编号：</label>
            <div class="formControls col-8">
                <span>${sipBasic.relayNum}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">中继方向：</label>
            <div class="formControls col-8">
                <c:if test="${sipBasic.relayType=='10'}">
                    <span>出中继</span>
                </c:if>
                <c:if test="${sipBasic.relayType=='11'}">
                    <span>双向中继</span>
                </c:if>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">号码添加：</label>
            <div class="formControls col-8">
                <button type="button" class="btn btn-primary radius" onclick="addMaskNumber();"><i class="Hui-iconfont">&#xe600;</i>单个添加</button>
                <button type="button" class="btn btn-primary radius" onclick="importMaskNumber();"><i class="Hui-iconfont">&#xe645;</i>批量导入</button>
            </div>
        </div>
    </div>

    <form action="<c:url value="/relayNumPool/updateIsForce" />" method="post" class="form form-horizontal" id="updateIsForce">
        <input type="hidden" name = "id" id="id" value="${sipBasic.id}"/>
        <div class="row cl">
            <div class="form col-3"></div>
            <div class="formControls col-3">
                <div class="skin-minimal">
                    <div class="check-box">
                        <%--<input type="checkbox" name = "isForce" id="isForce" value="1"/>--%>
                        <input type="checkbox" name = "isForce" id="isForce" value="1" onclick="javascript:this.value=this.checked?1:0"/>
                        <label id="isForceLable" for="isForce" style="color: red;font-weight:bold">系统默认强显中继</label>
                    </div>
                </div>
            </div>
            <div class="col-1">
                <input class="btn btn-secondary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>

    </form>
    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;margin-top: 10px;"/>
    <!-- 分割线 -->

    <div style="margin-top: 15px;">
        号码：<input type="text" class="input-text" style="width: 150px; margin-right: 10px;" id="number" name="number">
        <button type="button" class="btn btn-success radius" id="search" name="search"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="20%">序号</th>
                <th width="20%">号码</th>
                <th width="20%">创建时间</th>
                <th width="15%">最大并发数</th>
                <th width="25%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
