<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>中继群主叫强显号段表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            中继群编号：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入中继群编号" id="tgNum" name="tgNum"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            中继群名：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入中继群名" id="tgName" name="tgName"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success" id="searchButton" ><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="openUserDialog('新增中继','<c:url value="/relaygroup3/toAddRelayGroup3"/>','950','700');" class="btn btn-primary radius">新增中继</a>
        <a href="javascript:void(0);" onclick="importNumber();" class="btn btn-primary radius"><i class="Hui-iconfont"></i>批量导入</a>

        <%--<a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>--%>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="15%">中继群编号</th>
                <th width="15%">中继群名</th>
                <th width="15%">号段起始号码</th>
                <th width="15%">号段结束号码</th>
                <th width="15%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
<script type="text/javascript">
    var dataTables = "";
    var search_param = {};
    $(document).ready(function() {
        //dataTablesAjax 选项
        var dataTablesAjax = {
            url: '<c:url value="/relaygroup3/pageGroup3"/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // id, tg_num tgNum, tg_name tgName, code_start codeStart, code_end codeEnd
        var columns = [
            {data: 'rowNO'},
            {data: 'tgNum'},
            {data: 'tgName'},
            {data: 'codeStart'},
            {data: 'codeEnd'},
            {data: ''}
        ];

        var columnDefs = [
            {
                "targets": [0,1,2,3,4,-1],
                "sClass" : "text-c"
            },
            {
                "targets": [-1],
                "data": "id",
                "render": function(data, type, full) {

                    var _ex_in = "";

                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openUserDialog(\'编辑\',\'<c:url value="/relaygroup3/RelayGroup3EditView?id='+full.id+'&operationType=edit"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i>编辑</a>';
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openUserDialog(\'查看\',\'<c:url value="/relaygroup3/RelayGroup3EditView?id='+full.id+'&operationType=view"/>\',\'700\',\'600\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont"></i>查看</a>';
                    _ex_in += '<a title="删除" href="javascript:;" onclick="deleteRelayInfo(\''+full.id+'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; ">&#xe6e2;</i>删除</a><br/>';

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
//            "aaSorting": [ 1, "desc" ], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            "columns": columns,
            ajax: dataTablesAjax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#searchButton").on("click", function(){
            search_param = {
                params:{
                    "tgNum" : $("#tgNum").val(),
                    "tgName" : $("#tgName").val()
                }
            };
            dataTables.fnDraw();
        });
    });

    /*增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    // 批量导入
    function importNumber(){
        openDialog('批量导入', '<c:url value="/relaygroup3/toImportGroup3"/>?g=3', '600', '260');
    }

    /*增加*/
    function openRelayDialog(title,url,w,h){
        layer_full(title,url,w,h);
    }

    /** 删除中继信息 **/
    function deleteRelayInfo(id,appid){
        layer.confirm('是否确定删除？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/relaygroup3/deleteRelayGroup3'/>",
                dataType:"json",
                data:{"id" : id},
                success:function(result){
                    if (result.status == 0) {
                        layer.alert("删除成功", {icon: 1});
                        dataTables.fnDraw();
                    } else if(result.status == 1){
                        layer.alert("删除失败", {icon: 2});
                    }else if(result.status == 2){
                        layer.alert("此条中继正在被引用，无法删除", {icon: 2});
                    }
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
       /* window.open("<c:url value='/relay/downloadReport'/>?params[relayName]=" + $("#relayName").val()
                + "&params[relayNum]=" + $("#relayNum").val()
                + "&params[relayType]=" + $("#relayType").val()
                + "&timemin=" + $("#timemin").val()
                + "&timemax=" + $("#timemax").val()
        );*/

        var formData = "";
        $('#searchFrom').find('input[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
//            $('#searchFrom').find('select[name]').each(function(){
//                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
//            });
        formData = formData.substring(1);
        window.open("<c:url value='/relaygroup3/downloadRelayGroup3'/>?" + formData);
    }

</script>
</html>
