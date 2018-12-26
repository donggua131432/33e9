<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>呼叫中心列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript"  src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>

</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l">
        <form id="searchFrom">
            创建时间：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:180px;">
            -
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:180px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            客户名称：<input type="text" id="name" name="name" class="input-text" placeholder="客户名称" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            subID：<input type="text" id="subid" name="subid" class="input-text" placeholder="subID" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            呼叫中心名称：<input type="text" id="ccname" name="ccname" class="input-text" placeholder="呼叫中心名称" style="width:200px;">
            <br><br>
            应用名称：<input type="text" id="appName" name="appName" class="input-text" placeholder="应用名称" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            是否缺省：<select id="defaultCc" name="defaultCc" class="input-text" style="width: 200px;height: 31px;">
                        <option value="">请选择是否缺省</option>
                        <option value="0">否</option>
                        <option value="1">是</option>
                      </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openCcInfoDialog('创建呼叫中心','<c:url value="/ccinfo/toAdd"/>','800','600')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 创建呼叫中心</a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadCcInfoReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
                <tr class="text-c">
                    <th width="5%">序号</th>
                    <th width="">创建时间</th>
                    <th width="">account id</th>
                    <th width="">客户名称</th>
                    <th width="">应用名称</th>
                    <th width="">APP ID</th>
                    <th width="">呼叫中心名称</th>
                    <th width="">subID</th>
                    <th width="">话务员数量</th>
                    <th width="">是否缺省</th>
                    <th width="">中继群</th>
                    <th width="8%">操作</th>
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
            url: '<c:url value='/ccinfo/pageList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // tc.id,tc.ctime,tc.sid,tuc.name,ta.app_name,tc.appid,tc.ccname,
        // tc.subid,tc.cco_max_num,tc.default_cc,tc.relay1
        var columns = [
            { data: 'rowNO'},
            { data: 'ctime'},
            { data: 'sid' },
            { data: 'name'},
            { data: 'app_name'},
            { data: 'appid'},
            { data: 'ccname'},
            { data: 'subid'},
            { data: 'cco_max_num'},
            { data: 'default_cc'},
            { data: 'relay1'},
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
                "targets": [1],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.ctime) {
                        return o.formatDate(full.ctime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.ctime;
                }
            },
            {
                "targets": [8],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cco_max_num) {
                        return full.cco_max_num;
                    }
                    return "";
                }
            },
            {
                "targets": [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.default_cc == 0) {
                        return '<span class="label label-success radius">否</span>';
                    }
                    if (full.default_cc == 1) {
                        return '<span class="label label-warning radius">是</span>';
                    }
                    return full.default_cc;
                }
            },
            {
                "targets": [10],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.relay1) {
                        return full.relay1;
                    }
                    return "";
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openCcInfoDialog(\'编辑\',\'<c:url value="/ccinfo/toEdit?id='+full.id+'"/>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none">编辑</a>';
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openCcInfoDialog(\'查看\',\'<c:url value="/ccinfo/toShow?id='+full.id+'"/>\',\'600\',\'400\')" class="ml-5" style="text-decoration:none">查看</a>';
                    if (full.status == '00') {
                        _ex_in += '<a title="删除" href="javascript:;" onclick="ccInfoStatusUpdate(\''+full.subid+'\', \'01\')" class="ml-5" style="text-decoration:none">删除</a>';
                    }

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
            "aaSorting": [[ 1, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{"name" : $("#name").val(),"subid" : $("#subid").val(),"ccname" : $("#ccname").val(),
                    "appName" : $("#appName").val(),"defaultCc" : $("#defaultCc").val()},
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            datatables.fnDraw();
        });
    });

    /*创建呼叫中心*/
    function openCcInfoDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    /* 呼叫中心状态修改 */
    function ccInfoStatusUpdate(subid, status ){
        layer.confirm('是否删除呼叫中心？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/ccinfo/updateStatus'/>",
                dataType:"json",
                data:{"subid":subid, "status":status},
                success:function(data){
                    if(data.code=="no"){
                        layer.alert(data.msg, {icon: 5});
                    }else{
                        datatables.fnDraw();
                        layer.alert(data.msg, {icon: 1});
                    }
                }
            });
        });
    }

    /** 导出列表 */
    function downloadCcInfoReport() {
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
        window.open("<c:url value='/ccinfo/downloadCcInfo'/>?" + formData);
    }
</script>
</body>
</html>