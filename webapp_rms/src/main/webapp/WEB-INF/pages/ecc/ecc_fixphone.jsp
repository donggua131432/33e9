<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>接听号码-非SIP号码</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <form id="searchFrom" class="form form-horizontal">
        <div class="text-l">
            接听号码：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入非SIP号码" id="number" name="number"/>&nbsp;&nbsp;&nbsp;&nbsp;
            省份：
            <select name="pcode" class="input-text" id="pcode" style="width:200px;height: 31px">
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            城市：
            <select name="ccode" class="input-text" id="ccode" style="width:200px;height: 31px">
            </select>&nbsp;&nbsp;&nbsp;&nbsp;
            Account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="accountID" style="width:280px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
        </div><br>
        <div class="text-l">
            客户名称：<input type="text" id="companyName" name="companyName" class="input-text" placeholder="客户名称" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            APP ID：<input type="text" id="appid" name="appid" class="input-text" placeholder="appid" style="width:280px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            应用名称：<input type="text" id="appName" name="appName" class="input-text" placeholder="应用名称" style="width:200px">
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        </div>
    </form>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
        </span>

    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="5%">接听号码类型</th>
                <th width="10%">接听号码</th>
                <th width="5%">省份</th>
                <th width="5%">城市</th>
                <th width="10%">修改时间</th>
                <th width="10%">客户名称</th>
                <th width="10%">Account ID</th>
                <th width="10%">应用名称</th>
                <th width="10%">APP ID</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {
        initPCArea("pcode","ccode","请选择");
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/eccFixPhonePool/pageEccFixPhoneList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
            { data: 'rowNO' },
            { data: 'numType'},
            { data: 'number'},
            { data: 'pname'},
            { data: 'cname'},
            { data: 'updatetime'},
            { data: 'companyName'},
            { data: 'sid' },
            { data: 'appName'},
            { data: 'appId'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.numType == '02') {
                        return '手机  ';
                    }
                    if (full.numType == '03') {
                        return '固话  ';
                    }
                    return full.numType;
                }
            },
            {
                "targets": [5],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.updatetime) {
                        return o.formatDate(full.updatetime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createDate;
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 6, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
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
            datatables.fnDraw();
        });
    });

    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
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
        window.open("<c:url value='/eccFixPhonePool/downloadEccFixPhonePool'/>?" + formData);
    }
</script>
</body>
</html>