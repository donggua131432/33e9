<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/7/14
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>SIP全局号码池管理</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var dataTables = "",
                search_param = {
                    params:{
                        "appid":"${appid}",
                        "managerType":"${managerType}"
                    }
                };
        $(function(){
            var dataTablesAjax = {
                url: '<c:url value='/sipNumPool/getSipNumberList'/>',
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
                { data: 'create_time' },
                { data: 'max_concurrent'}
                <c:if test="${managerType == 'edit'}">
                ,{ data: ''}
                </c:if>
            ];

            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0,1,2,3,4],
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
                <c:if test="${managerType == 'edit'}">
                ,{
                    "targets": [-1],
                    "sClass" : "text-c",
                    "orderable":false,
                    "render": function(data, type, full) {
                        var _ex_in = "";
                            _ex_in += '<a title="修改并发数" href="javascript:;" onclick="openUserDialog(\'修改并发数\',\'<c:url value="/sipNumPool/toUpdateMax?id='+full.id+'"></c:url>\',\'500\',\'350\')" class="ml-5" style="text-decoration:none;color:blue;">修改</a>';
                            _ex_in += '<a title="删除号码" href="javascript:void(0);" onclick="SipNumCancel(\''+full.id+'\');" class="ml-5" style="text-decoration:none;color:blue;">删除</a>';

                        return _ex_in;
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
                        "appid":"${appid}",
                        "managerType":"${managerType}",
                        "number":$("#number").val().trim()
                    }
                };
                dataTables.fnDraw();
            });

        });


        function SipNumCancel(id){
            layer.confirm('是否删除号码？', {
                btn: ['是','否'] //按钮
            }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/sipNumPool/deleteSipNumber'/>",
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

        // 导出全局号码池
        function downloadMaskNumber() {
            if(dataTables._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }
            //
            window.open("<c:url value='/sipNumPool/downloadMaskNumber'/>?params[appid]=" + '${appid}'
            );
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
                        url: "<c:url value='/sipNumPool/deleteNum'/>",

                        data: {'strId': strId},//要发送的数据
                        success: function (data) {
                            if (data.code == '00') {
//                                layer.alert("成功删除号码！",{icon:1},function(){
//                                    dataTables.fnDraw();
//                                    layer_close();
//                                })
                                layer.alert("成功删除号码！", {icon: 1})
                                dataTables.fnDraw();
                            } else {
                                dataTables.fnDraw();
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

        /*管理员-增加*/
        function openUserDialog(title,url,w,h){
            layer_show(title,url,w,h);
        }
    </script>
</head>
<body>
<div class="pd-20">
    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;margin-top: 10px;"/>
    <!-- 分割线 -->

    <div style="margin-top: 15px;">
        号码：<input type="text" class="input-text" style="width: 150px; margin-right: 10px;" id="number" name="number">
        <button type="button" class="btn btn-success radius" id="search" name="search"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div style="margin-top: 16px;width: 742px;">
        <button type="button" class="btn btn-primary radius" onclick="deleteMaskNumber();">批量删除</button>
        <button style="float:right;" type="button" class="btn btn-primary radius" onclick="downloadMaskNumber();">导出</button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <c:if test="${managerType eq 'edit'}">
                    <th width="10%"><input type="checkbox" name="" value="">序号</th>
                    <th width="20%">号码</th>
                    <th width="20%">创建时间</th>
                    <th width="15%">最大并发数</th>
                    <th width="25%">操作</th>
                </c:if>
                <c:if test="${managerType == 'view'}">
                    <th width="20%">序号</th>
                    <th width="20%">号码</th>
                    <th width="20%">创建时间</th>
                    <th width="15%">最大并发数</th>
                </c:if>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
