<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>语音通知模板审核</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l">
        <form id="searchFrom">
            模板 ID：<input type="text" id="id" name="id" class="input-text" placeholder="模板ID" style="width:200px;">
            &nbsp;&nbsp;
            模板名称：<input type="text" id="name" name="name" placeholder="模板名称" class="input-text" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            客户名称：<input type="text" id="companyName" name="companyName" placeholder="客户名称" class="input-text" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            account Sid：<input type="text" id="sid" name="sid" placeholder="account Sid" class="input-text" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            appid：<input type="text" id="appid" name="appid" placeholder="appid" class="input-text" style="width:200px;">

            <br><br>

            审核时间：<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="auditTime" name="auditTime" class="input-text Wdate" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            模板类型：
            <select id="type" name="type" class="input-text" style="width:200px;">
                <option value="">请选择模板类型</option>
                <option value="00">语音</option>
                <option value="01">文本</option>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            审核状态：
            <select id="auditStatus" name="auditStatus" class="input-text" style="width:200px;">
                <option value="">请选择审核状态</option>
                <option value="00">待审核</option>
                <option value="01">审核通过</option>
                <option value="02">审核不通过</option>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        </form>
    </div>

    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" class="btn btn-primary radius r" onclick="downloadReport()">导出</a>
    </div>

    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="5%">模板ID</th>
                <th width="10%">模板名称</th>
                <th width="10%">客户名称</th>
                <th width="10%">account Sid</th>
                <th width="5%">应用名称</th>
                <th width="10%">APP ID</th>
                <th width="5%">模板类型</th>
                <th width="10%">创建时间</th>
                <th width="10%">审核时间</th>
                <th width="10%">审核状态</th>
                <th width="10%">操作</th>
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
            url: '<c:url value='/tempVoiceAudit/pageVoiceList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'rowNO' },
            { data: 'id' },
            { data: 'name' },
            { data: 'companyName' },
            { data: 'sid' },
            { data: 'appName'},
            { data: 'appid'},
            { data: 'type'},
            { data: 'atime'},
            { data: 'auditTime'},
            { data: 'auditStatus'},
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
                "targets": [1,2,3,4,5,6],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets" : [7],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    // 00 语音，01 文本。
                    if (data == '00') {
                        return "<span class='label label-warning radius'>语音</span>";
                    }else if(data == '01'){
                        return "<span class='label label-success radius'>文本</span>";
                    }else{
                        return data;
                    }
                }
            },
            {
                "targets": [8,9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var addtime = data;
                    if (addtime) {
                        return o.formatDate(addtime,"yyyy-MM-dd hh:mm:ss")
                    }
                    return addtime;
                }
            },
            {
                "targets" : [10],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (data == '00') {
                        return "<span class='label label-warning radius'>待审核</span>";
                    }else if(data == '01'){
                        return "<span class='label label-success radius'>审核通过</span>";
                    }else if(data == '02'){
                        return "<span class='label label-danger radius'>审核不通过</span>";
                    }else if(data == '03'){
                        return "<span class='label label-default radius'>已作废</span>";
                    }
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var auditStatus = full.auditStatus;
                    if(auditStatus == "00"){
                        return '<a title="审核" href="javascript:;" ' +
                                'onclick="system_log_show(\'语音模板审核\',\'<c:url value="/tempVoiceAudit/toTempCertification?type=edit"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">审核</a>';
                    }else if(auditStatus == "01"){
                        return '<a title="查看" href="javascript:;" ' +
                                'onclick="system_log_show(\'语音模板审核\',\'<c:url value="/tempVoiceAudit/toTempCertification?type=show"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">查看</a>';
                    }
                    else if(auditStatus == "02"){
                        return '<a title="查看" href="javascript:;" ' +
                                'onclick="system_log_show(\'语音模板审核\',\'<c:url value="/tempVoiceAudit/toTempCertification?type=show"/>\',' + full.id + ',\'550\',\'550\')" class="ml-5" style="text-decoration:none; color:blue">查看</a>';
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
            "aaSorting": [[ 10, "asc" ]], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params : {
                    "id" : $("#id").val(),
                    "name" :　$("#name").val(),
                    "companyName" : $("#companyName").val(),
                    "sid" : $("#sid").val(),
                    "appid" : $("#appid").val(),
                    "type" : $("#type").val(),
                    "auditStatus" : $("#auditStatus").val(),
                    "auditTime" : $("#auditTime").val()
                }
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

    /** 导出列表 */
    function downloadReport() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        var formData = "";
        $('#searchFrom').find('input[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
        $('#searchFrom').find('select[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
        formData = formData.substring(1);
        window.open("<c:url value='/tempVoiceAudit/downloadReport'/>?" + formData);
    }

</script>
</body>
</html>