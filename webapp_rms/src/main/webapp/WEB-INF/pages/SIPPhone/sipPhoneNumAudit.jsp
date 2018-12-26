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
    <form id="searchFrom">
        <div class="text-l">
            accountID：<input type="text" id="accountID" name="accountID" class="input-text" placeholder="accountID" style="width:200px;">
            &nbsp;&nbsp;
            客户名称：<input type="text" id="custom_name" name="custom_name" placeholder="客户名称" class="input-text" style="width:200px">
            &nbsp;&nbsp;&nbsp;&nbsp;
            APP ID：<input type="text" id="appid_" name="appid_" placeholder="APP ID" class="input-text" style="width:200px">
            &nbsp;&nbsp;&nbsp;&nbsp;
            应用名称：<input type="text" id="appid" name="appid" placeholder="应用名称" class="input-text" style="width:200px">
            &nbsp;&nbsp;&nbsp;&nbsp;
        </div><br>
        <div class="text-l">
            SIP号码：<input type="text" id="sipphone" name="sipphone" placeholder="SIP号码" class="input-text" style="width:200px">
            &nbsp;&nbsp;&nbsp;&nbsp;
            固话号码：<input type="text" id="fixphone" name="fixphone" placeholder="固话号码" class="input-text" style="width:200px">
            &nbsp;&nbsp;&nbsp;&nbsp;
            外显号码：<input type="text" id="showNum" name="showNum" placeholder="外显号码" class="input-text" style="width:200px">
            &nbsp;&nbsp;&nbsp;&nbsp;
            状态：
            <select id="auditStatus" name="auditStatus" class="select" style="width:200px;height: 31px">
                <option value="" >全部</option>
                <option value="01">审核通过</option>
                <option value="02">审核不通过</option>
                <option value="00" >待审核</option>
            </select>
            <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        </div>
    </form>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <div class="l">
            <a href="javascript:void(0);" class="btn btn-primary radius l" onclick="auditAll('号码审核', '<c:url value="/sipPhoneNum/auditAll"/>', '550', '550')">批量审核</a>
        </div>
        <span class="r">
                <a href="javascript:void(0);" onclick="downloadShowNumApplyReport();" class="btn btn-primary radius r">
                    <i class="Hui-iconfont">&#xe600;</i> 导出
                </a>
        </span>
    </div>
    <div>

        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="50"><input type="checkbox"/>&nbsp;&nbsp;序号</th>
                <th width="80">客户名称</th>
                <th width="70">Account ID</th>
                <th width="100">应用名称</th>
                <th width="40">APP ID</th>
                <th width="100">申请时间</th>
                <th width="90">SIP号码</th>
                <th width="40">固话号码</th>
                <th width="90">外显号码</th>
                <th width="90">状态</th>
                <th width="90">原因</th>
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
            url: '<c:url value='/sipPhoneNum/list'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'rowNO' },
            { data: 'name' },
            { data: 'sid' },
            { data: 'app_name' },
            { data: 'appid' },
            { data: 'atime'},
            { data: 'sip_phone'},
            { data: 'number'},
            { data: 'show_num'},
            { data: 'audit_status'},
            { data: 'audit_common'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var audit_status = full.audit_status;
                    if(audit_status == "00"){
                        return '<input type="checkbox" name="fullid" value="' + full.shownum_id +','+full.id+ '_' + full.appid + '"/>' + '&nbsp;&nbsp;&nbsp;&nbsp;' + data;
                    }
                    return '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + data;
                }
            },
            {
                "targets": [1,2,3,4,6,7,8],
                "sClass" : "text-c"
            },
            {
                "targets": [5],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var atime = full.atime;
                    if (atime) {
                        return o.formatDate(atime,"yyyy-MM-dd hh:mm:ss")
                    }
                    return atime;
                }
            },
            {
                "targets" : [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.audit_status == '00') {
                        return "<span class='label label-warning radius'>待审核</span>";
                    }else if(full.audit_status == '01'){
                        return "<span class='label label-success radius'>审核通过</span>";
                    }else if(full.audit_status == '02'){
                        return "<span class='label label-danger radius'>审核不通过</span>";
                    }
                }
            },
            {
                "targets" : [10],
                "render": function(data, type, full) {
                    if (data) {
                        return "<span style='word-break: break-all'>"+data+"</span>";
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
            "sErrMode": 'none',
            "ordering": false,
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
                "params[sipphone]" : $("#sipphone").val(),
                "params[fixphone]" : $("#fixphone").val(),
                "params[showNum]" : $("#showNum").val(),
                "params[auditStatus]" : $("#auditStatus").val()
            };
            datatables.fnDraw();
        });
    });

    /**
     * 参数解释：
     * @param title 标题
     * @param url 请求的url
     * @param w 弹出层宽度（缺省调默认值）
     * @param h 弹出层高度（缺省调默认值）
     */
    function auditAll(title,url,w,h){
        var appid = "", ids = "",audioIds = "", isR = true;

        $("input[name='fullid']:checked").each(function(i,elem){
            var value = elem.value;
            if (value) {
                var vals = value.split("_");
                var nums = vals[0].split(",");
                ids += "&id=" + nums[0];
                audioIds +="&audioId="+nums[1];
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

        url = url + "?appid=" + appid + ids + audioIds;
        layer_show(title,url,w,h);
    }

    /** 导出列表 */
    function downloadShowNumApplyReport() {
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
        window.open("<c:url value='/sipPhoneNum/downloadShowNumApplyInfo'/>?" + formData);
    }
</script>
</body>
</html>