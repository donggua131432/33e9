<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>操作日志</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c">
        日期范围：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:175px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:175px;">
        &nbsp;&nbsp;
        客户账号：<input type="text" id="custom_email" name="custom_email" placeholder="客户账号" class="input-text" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        客户名称：<input type="text" id="custom_name" name="custom_name" placeholder="客户名称" class="input-text" style="width:200px">
        &nbsp;&nbsp;
        行业：<select id="tradeId" name="tradeId" class="select" style="width:200px;height: 31px">
                <option value="">请选择所属行业</option>
                <c:forEach items="${dicDatas}" var="dd">
                     <option value="${dd.code}">${dd.name}</option>
                </c:forEach>
             </select>
        &nbsp;&nbsp;
        审核状态：
        <select id="status" name="status" class="select" style="width:180px;height: 31px">
            <option value="">请选择审核状态</option>
            <option value="0" >待审核</option>
            <option value="1">审核通过</option>
            <option value="2">审核不通过</option>
        </select>
        &nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>
    <div class="mt-20">

        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="40">序号</th>
                <th width="80">accountID</th>
                <th width="100">注册时间</th>
                <th width="100">客户名称</th>
                <th width="100">客户账号</th>
                <th width="90">所属行业</th>
                <th width="50">审核状态</th>
                <th width="40">账户状态</th>
                <th width="90">账户余额(元)</th>
                <th width="90">操作</th>
            </tr>
            </thead>
        </table>

    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    var dds = '${dds}';
    $(document).ready(function() {
        var jsdds = $.parseJSON(dds);
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/developers/auditDetail'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'rowNO' },
            { data: 'sid' },
            { data: 'create_date' },
            { data: 'c_name' },
            { data: 'email' },
            { data: 'trade_id'},
            { data: 'status'},
            { data: 'auth_status'},
            { data: 'fee'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1,2,5],
                "sClass" : "text-c"
            },
            {
                "targets": [2],
                "render": function(data, type, full) {
                    var create_date = full.create_date;
                    if (create_date) {
                        return o.formatDate(create_date,"yyyy-MM-dd hh:mm:ss")
                    }
                    return create_date;
                }
            },
            {
                "targets": [5],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.trade_id) {
                        $.each(jsdds, function(i,v){
                            if (v.code == full.trade_id) {
                                full.trade_id = v.name;
                                return false;
                            }
                        });
                    }
                    return full.trade_id;
                }
            },
            {
                "targets" : [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.status == '0') {
                        return "<span class='label label-warning radius'>待审核</span>";
                    }else if(full.status == '1'){
                        return "<span class='label label-success radius'>审核通过</span>";
                    }else if(full.status == '2'){
                        return "<span class='label label-danger radius'>审核不通过</span>";
                    }
                }
            },
            {
                "targets" : [7],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.auth_status == '0') {
                        return "<span class='label label-defaunt radius'>未认证<span>";
                    }else if(full.auth_status == '1'){
                        return "<span class='label label-warning radius'>认证中</span>";
                    }else if(full.auth_status == '2'){
                        return "<span class='label label-success radius'>已认证</span>";
                    }else if(full.auth_status == '3'){
                        return "<span class='label label-danger radius'>认证未通过</span>";
                    }
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var status = full.status;
                    if(status == 0){
                        return '<a title="审核" href="javascript:;" ' +
                                'onclick="system_log_show(\'认证信息\',\'<c:url value="/developers/toShowCertification?type=edit"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">审核</a>';
                    }else if(status == 1){
                        var _ex_in = "";
                        _ex_in +=  '<a title="查看" href="javascript:;" ' +
                                'onclick="system_log_show(\'认证信息\',\'<c:url value="/developers/toShowCertification?type=authshow"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">查看</a>';
                        _ex_in +=  '<a title="修改" href="javascript:;" ' +
                                'onclick="system_log_show(\'认证信息\',\'<c:url value="/developers/toShowCertification?type=update"/>\',' + full.id + ',\'900\',\'650\')" class="ml-5" style="text-decoration:none; color:blue">修改</a>';
                        return _ex_in;
                    }
                    else if(status == 2){
                        return '<a title="查看" href="javascript:;" ' +
                                'onclick="system_log_show(\'认证信息\',\'<c:url value="/developers/toShowCertification?type=show"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">查看</a>';
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
            "aaSorting": [[ 6, "asc" ]], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                "fuzzy" : $("#custom_name").val(),
                "params[tradeId]" : $("#tradeId option:selected").val(),
                "params[status]" : $("#status option:selected").val(),
                "params[custom_email]" : $("#custom_email").val(),
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            datatables.fnDraw();
        });
        // 加载所属行业列表
        getTradeOption();
    });

    // 加载所属行业列表
    function getTradeOption() {
        $.ajax({
            type: "POST",
            url: "<c:url value='/dicdata/type'/>",
            data: {"typekey" : "tradeType"},
            success: function(msg){
                if (msg.data) {
                    $("#tradeId").html("");
                    var options = "<option value=''>请选择所属行业</option>";
                    $.each(msg.data, function(n,v) {
                        options += "<option value='" + v.code + "'>" + v.name + "</option>";
                    });
                    $("#tradeId").append(options);
                }
            }
        });
    }

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
    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/developers/downloadReport'/>?fuzzy=" + $("#custom_name").val()
                + "&params[tradeId]=" + $("#tradeId option:selected").val()
                + "&params[status]=" + $("#status option:selected").val()
                + "&params[custom_email]=" + $("#custom_email").val()
                + "&timemin=" + $("#timemin").val()
                + "&timemax=" + $("#timemax").val()
        );
    }

</script>
</body>
</html>