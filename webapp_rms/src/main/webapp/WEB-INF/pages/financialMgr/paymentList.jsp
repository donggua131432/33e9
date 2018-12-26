<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>扣款记录</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>

<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c" style="text-align: left">
        日期时间范围：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})" id="datemin" class="input-text Wdate" style="width:186px;"/>-
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'datemin\')}'})" id="datemax" class="input-text Wdate" style="width:186px;"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        操作人：
        <input type="text" class="input-text" style="width:200px"  id="operator" name="operator"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        accountID：
        <input type="text" class="input-text" style="width:200px" placeholder="请输入客户ID" id="sid" name="sid"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        客户名称：
        <input type="text" class="input-text" style="width:200px" placeholder="请输入客户名称" id="customer_name" name="customer_name"/>
        <button type="submit" class="btn btn-success" id="sarch" name="sarch"/>
            <i class="Hui-iconfont">&#xe665;</i>查询
        </button>
    </div>

    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>
    <table class="table table-border table-bordered table-hover table-bg table-sort" style="table-layout:fixed; word-break: break-all; word-wrap: break-word;">
        <thead>
        <tr class="text-c">
            <th width="20"><input type="checkbox" name="" value=""></th>
            <th width="80">时间</th>
            <th width="100">accountID</th>
            <th width="50">客户名称</th>
            <th width="50">扣款金额(元)</th>
            <th width="50">操作人</th>
            <th width="150">备注</th>
        </tr>
        </thead>

    </table>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {

        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/financial/selectPaymentRecordsPage'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'id' },
            { data: 'payment_time' },
            { data: 'sid' },
            { data: 'customer_name' },
            { data: 'money' },
            { data: 'operator'},
            { data: 'comment'}
        ];
        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0],
                "data": "id",
                "orderable":false,
                "sClass" : "text-c", // 样式（居中）
                "render": function(data, type, full) {
                    return "<td><input name='' type='checkbox' value='" + full.id + "'></td>";
                }
            },
            {
                "targets": [1],
                "data": "id",
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.payment_time) {
                        return o.formatDate(full.payment_time, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.payment_time;
                }
            },
            {
                "targets": [6],
                "data": "id",
                "render": function(data, type, full) {
                    if (full.comment) {
                        return full.comment.replace(/\<\/?/g,"").replace(/\>/g,"");
                    }
                    return full.comment;
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 1, "desc" ]], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {"params[sid]" : $("#sid").val(), "params[customer_name]" : $("#customer_name").val(),"fuzzy" : $("#operator").val(), "datemin" : $("#datemin").val(), "datemax" : $("#datemax").val()};
            datatables.fnDraw();
        });
    });


    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        window.open("<c:url value='/financial/downloadReport'/>?type=paymentList&fuzzy=" + $("#operator").val()
            + "&datemin=" + $("#datemin").val() + "&datemax=" + $("#datemax").val()
        );
    }

</script>

</body>
</html>
