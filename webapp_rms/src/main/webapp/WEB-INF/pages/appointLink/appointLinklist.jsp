<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>特例表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l">
        显示号码：<input type="text" id="xhTelno" name="xhTelno" class="input-text" placeholder="显示号码" style="width:250px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        呼叫目的号码：<input type="text" id="destTelno" name="destTelno" class="input-text" placeholder="呼叫目的号码" style="width:250px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        RN值：<input type="text" id="rn" name="rn" class="input-text" placeholder="RN值" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <%--<div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport();" class="btn btn-primary radius r">导出</a>
    </div>
    --%>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openAppointLinkInfoDialog('新增客户','<c:url value="/appointLink/addAppointLink"/>','800','450')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 新增选线特例
            </a>
        </span>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-primary radius" onclick="importMaskNumber();"><i class="Hui-iconfont">&#xe645;</i>批量导入</button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort" id="J_tab_fam">
            <thead>
            <tr>
                <th scope="col" colspan="9">中继线路调度特例表</th>
            </tr>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="10%">显示号码前缀</th>
                <th width="10%">呼叫目的号码前缀</th>
                <th width="5%">类型</th>
                <th width="10%">RN值</th>
                <th width="10%">备注</th>
                <th width="5%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">
    var search_param = {};
    var datatables;

    $(document).ready(function() {
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/appointLink/pageappointLinkList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

    // columns 列选项 和表头对应，和后台传回来的数据属性对应
    var columns = [
        {data: 'rowNO'},
        { data: 'xhTelno'},
        { data: 'destTelno'},
        { data: 'type'},
        { data: 'rn'},
        { data: 'remake'},
        { data: ''}
    ];

    // 对数据进行处理
    var columnDefs = [
        {
            "targets": [0],
            "sClass" : "text-c"
        },
        {
            "targets": [1],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                return full.xhTelno;
            }
        },
        {
            "targets": [2],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                return full.destTelno;
            }
        },
        {
            "targets": [3],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                return full.type;
            }
        },
        {
            "targets": [4],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                return full.rn;
            }
        },
        {
            "targets": [5],
            "sClass" : "text-c",
            "render": function(data, type, full) {
                return full.remake;
            }
        },
        {
            "targets": [-1],
            "sClass" : "text-c",
            "orderable":false,
            "render": function(data, type, full) {
                var _ex_in = "";
                _ex_in += '<a title="编辑" href="javascript:;" onclick="openAppointLinkInfoDialog(\'编辑\',\'<c:url value="/appointLink/toEdit?id='+full.id+'"></c:url>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none">编辑</a>';
                _ex_in += '<a title="查看" href="javascript:;" onclick="openAppointLinkInfoDialog(\'查看\',\'<c:url value="/appointLink/toShow?id='+full.id+'"></c:url>\',\'600\',\'400\')" class="ml-5" style="text-decoration:none">查看</a>';
                _ex_in += '<a title="删除" href="javascript:;" onclick="delAppointLink(\''+full.id+'\')" class="ml-5" style="text-decoration:none">删除</a>';
                return _ex_in;
            }
        }

    ];

    datatables = $(".table-sort").dataTable({
        "processing": false, // 是否显示取数据时的那个等待提示
        "serverSide": true,//这个用来指明是通过服务端来取数据
        "bStateSave": true,//状态保存
        "bFilter": false, //隐藏掉自带的搜索功能
        "dom": 'rtilp<"clear">',
        "ordering": false,
        //"aaSorting": [[ 8, "desc" ]], // 默认的排序列（columns）从0开始计数
        //"fnServerData": retrieveData,
        "columns": columns,
        ajax: datatables_ajax,
        "columnDefs":columnDefs
    });

    });

        /*新增号段*/
        function openAppointLinkInfoDialog(title,url,w,h){
            layer_show(title,url,w,h);
        }

        /* 删除号段 */
        function delAppointLink(id ){
            layer.confirm('是否删除特例？', {
                btn: ['是','否'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/appointLink/delete'/>",
                    dataType:"json",
                    data:{"id":id},
                    success:function(data){
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                });
            });
        }

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{
                    "xhTelno" : $("#xhTelno").val(),
                    "destTelno" : $("#destTelno").val(),
                    "rn" : $("#rn").val()
                }
            };
            datatables.fnDraw();

        });

    function importMaskNumber(){
        openDialog('批量导入', '<c:url value="/appointLink/importAppointLink"/>?&appid=${appInfo.appid}', '600', '260');
    }

    // 导出报表<%--
    function downloadReport() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/relaylist/downloadReport'/>?params[myYear]=" + $("#myYear").val()
        );
    }--%>

</script>
</body>
</html>