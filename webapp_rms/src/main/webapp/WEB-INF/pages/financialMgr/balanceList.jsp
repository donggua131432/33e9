<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>用户余额列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>

<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c" style="text-align: left">
        余额区间：
        <input type="text" class="input-text" style="width:100px;" id="price_min" name="price_min" onblur="checkMin();"/>-
        <input type="text" class="input-text" style="width:100px;" id="price_max" name="price_max" onblur="checkMax();"/>
        <span style="color:red"></span>
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
    <table class="table table-border table-bordered table-hover table-bg table-sort">
        <thead>
        <tr class="text-c">
            <th width="40"><input type="checkbox" name="" value=""></th>
            <th width="150">accountID</th>
            <th width="150">客户名称</th>
            <th width="150">可用余额</th>
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
            url: '<c:url value='/financial/selectBalanceListPage'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'id' },
            { data: 'sid' },
            { data: 'name' },
            { data: 'fee' }
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
            if(checkMin() && checkMax()){
                search_param = {"params[sid]" : $("#sid").val(), "params[customer_name]" : $("#customer_name").val(),
                    "params[price_min]" : $("#price_min").val(), "params[price_max]" : $("#price_max").val()};
                datatables.fnDraw();
            }
        });
    });

    function checkMin(){
        var min = $("#price_min").val();
        var el = /^\-?[1-9][0-9]{0,4}$/;
        if(!(el.test(min) || min==='0' || min=='')){
            $("#price_max").next().html("格式不正确");
            return false;
        }else{
            $("#price_max").next().html("");
            return true;
        }
    }

    function checkMax(){
        var min = $("#price_min").val();
        var max = $("#price_max").val();
        var el = /^\-?[1-9][0-9]{0,4}$/;
        if(!(el.test(max) || max==='0' || max=='')){
            $("#price_max").next().html("格式不正确");
            return false;
        }
        if(Number(max)<Number(min)){
            $("#price_max").next().html("必须大于前面的数");
            return false;
        }else{
            $("#price_max").next().html("");
            return true;
        }
    }
    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/financial/downloadReport'/>?type=balanceList"
                + "&params[sid]=" + $("#sid").val() + "&params[customer_name]="
                + $("#customer_name").val() + "&params[price_min]=" + $("#price_min").val()
                + "&params[price_max]=" + $("#price_max").val()

        );
    }
</script>

</body>
</html>
