<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>号码审核</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c">
        accountID：<input type="text" id="accountID" name="accountID" class="input-text" placeholder="accountID" style="width:200px;">

        &nbsp;&nbsp;
        客户名称：<input type="text" id="custom_name" name="custom_name" placeholder="客户名称" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        APP ID：<input type="text" id="appid_" name="appid_" placeholder="APP ID" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        应用名称：<input type="text" id="appid" name="appid" placeholder="应用名称" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        号码：<input type="text" id="number" name="number" placeholder="号码" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>

    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" class="btn btn-primary radius l" onclick="auditAll('号码审核', '<c:url value="/number/auditAll"/>', '550', '550')">批量审核</a>
    </div>
    <div>

        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="40"><input type="checkbox"/>&nbsp;&nbsp;序号</th>
                <th width="80">号码</th>
                <th width="70">accountID</th>
                <th width="100">客户名称</th>
                <th width="40">APP ID</th>
                <th width="90">应用名称</th>
                <th width="90">提交时间</th>
                <th width="40">审核状态</th>
                <th width="90">审核时间</th>
                <th width="90">操作</th>
            </tr>
            </thead>
        </table>

    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/number/list'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'rowNO' },
            { data: 'number' },
            { data: 'sid' },
            { data: 'name' },
            { data: 'appid' },
            { data: 'app_name'},
            { data: 'addtime'},
            { data: 'number_status'},
            { data: 'reviewtime'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0],
                "orderable":false,
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var number_status = full.number_status;
                    if(number_status == "00"){
                        return '<input type="checkbox" name="fullid" value="' + full.id + '_' + full.appid + '"/>' + '&nbsp;&nbsp;&nbsp;&nbsp;' + data;
                    }
                    return '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + data;
                }
            },
            {
                "targets": [1,2,3,4,5],
                "sClass" : "text-c"
            },
            {
                "targets": [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var addtime = full.addtime;
                    if (addtime) {
                        return o.formatDate(addtime,"yyyy-MM-dd hh:mm:ss")
                    }
                    return addtime;
                }
            },
            {
                "targets" : [7],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.number_status == '00') {
                        return "<span class='label label-warning radius'>待审核</span>";
                    }else if(full.number_status == '01'){
                        return "<span class='label label-success radius'>审核通过</span>";
                    }else if(full.number_status == '02'){
                        return "<span class='label label-danger radius'>审核不通过</span>";
                    }else if(full.number_status == '03'){
                        return "<span class='label label-default radius'>已作废</span>";
                    }
                }
            },
            {
                "targets": [8],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var reviewtime = full.reviewtime;
                    if (reviewtime) {
                        return o.formatDate(reviewtime,"yyyy-MM-dd hh:mm:ss")
                    }
                    return reviewtime;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var number_status = full.number_status;
                    if(number_status == "00"){
                        return '<a title="审核" href="javascript:;" ' +
                                'onclick="system_log_show(\'号码审核\',\'<c:url value="/number/toShowCertification?type=edit"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">审核</a>';
                    }else if(number_status == "01"){
                        return '<a title="查看" href="javascript:;" ' +
                                'onclick="system_log_show(\'号码审核\',\'<c:url value="/number/toShowCertification?type=show"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">查看</a>';
                    }
                    else if(number_status == "02"){
                        return '<a title="查看" href="javascript:;" ' +
                                'onclick="system_log_show(\'号码审核\',\'<c:url value="/number/toShowCertification?type=show"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">查看</a>';
                    }
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 7, "asc" ]], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                "params[accountID]" : $("#accountID").val(),
                "params[custom_name]" :　$("#custom_name").val(),
                "params[appid]" : $("#appid").val(),
                "params[appid_]" : $("#appid_").val(),
                "params[number]" : $("#number").val()
            };
            datatables.fnDraw();
        });
    });


    /*
     参数解释：
     title	标题
     url	请求的url
     id		需要操作的数据id
     w		弹出层宽度（缺省调默认值）
     h		弹出层高度（缺省调默认值）
     */
    /*管理员-增加*/
    function system_log_show(title,url,id,w,h){
        url = url + "&id=" + id;
        layer_show(title,url,w,h);
    }

    /**
     * 参数解释：
     * @param title 标题
     * @param url 请求的url
     * @param w 弹出层宽度（缺省调默认值）
     * @param h 弹出层高度（缺省调默认值）
     */
    function auditAll(title,url,w,h){
        var appid = "", ids = "", isR = true;

        $("input[name='fullid']:checked").each(function(i,elem){
            var value = elem.value;
            if (value) {
                var vals = value.split("_");
                ids += "&id=" + vals[0];
                if (i == 0) {
                    appid = vals[1];
                } else {
                    if (appid != vals[1]){
                        isR = false;
                        return false;
                    }
                }
            }
        });

        if (ids == "") {
            layer.alert("请先选择要审核的号码！",{icon:0});
            return;
        }

        if (!isR) {
            layer.alert("号码不在同一个app里！",{icon:0});
            return;
        }

        url = url + "?appid=" + appid + ids;
        layer_show(title,url,w,h);
    }
</script>
</body>
</html>