<%--
  Created by IntelliJ IDEA.
  User: DuKai
  Date: 2016/07/05
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>号码黑名单列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        var dataTables = "";
        var search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/numberBlack/numberBlackList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'rowNO'},
                {data: 'create_time'},
                {data: 'number'},
                {data: 'remark'},
                {data: ''}
            ];

            // 对数据进行处理
            var columnDefs = [
                {"targets": [1],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.create_time) {
                            return o.formatDate(full.create_time, "yyyy-MM-dd hh:mm:ss");
                        }
                        return full.create_time;
                    }
                },
                {"targets": [4], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return '<a title="删除" href="javascript:void(0);" onclick="delNumberBlack(\''+full.id+'\',\''+full.number+'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
                    }
                }
            ];

            //初始化表格渲染
            dataTables = $(".table-sort").dataTable({
                "processing": true, // 是否显示取数据时的那个等待提示
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

        /** 删除号码黑名单 **/
        function delNumberBlack(id,number){
            layer.confirm('记录一旦删除，记录下面对应的号码将恢复正常。</br>确定要删除号码【'+ number +'】吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/numberBlack/deleteNubmerBlack'/>",
                    dataType:"json",
                    data:{"id":id},
                    success : function(data) {
                        layer.alert(data.info,{
                            icon: data.status == '0'? 1 : 2,
                            skin: 'layer-ext-moon'
                        },function (){
                            location.replace(location.href);
                        });
                    },
                    error: function(data){
                        layer.alert(data.info,{
                            icon: 2,
                            skin: 'layer-ext-moon'
                        });
                    }
                });
            });
        }

        /** 导出列表 */
        function downloadNumBlackReport() {
            if(dataTables._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }
            var formData = "";
            $('#searchFrom').find('input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
            });
            formData = formData.substring(1);
            window.open("<c:url value='/numberBlack/downloadNumberBlack'/>?" + formData);
        }

    </script>

</head>

<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            创建时间：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createTime1\')||\'%y-%M-%d\'}'})" id="createTime" name="createTime" class="input-text Wdate" style="width:186px;"/>-
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'createTime\')}'})" id="createTime1" name="createTime1" class="input-text Wdate" style="width:186px;"/>
            号码查询：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入号码" id="number" name="number"/>
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openDialog('号码黑名单添加','<c:url value="/numberBlack/addNumberBlack"/>','700','400')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 新增号码黑名单
            </a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadNumBlackReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr>
                    <th scope="col" colspan="5">号码黑名单列表</th>
                </tr>
                <tr class="text-c">
                    <th width="5%">序号</th>
                    <th width="20%">创建时间</th>
                    <th width="20%">号码</th>
                    <th width="40%">备注原因</th>
                    <th width="15%">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
