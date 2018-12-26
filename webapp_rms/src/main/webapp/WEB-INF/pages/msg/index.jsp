<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>应用列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c">
        发布时间：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:180px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}'})" id="timemax" class="input-text Wdate" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;

        消息类型：
        <select id="msgCode" name="msgCode" style="width: 180px;height: 31px;">
            <option value="">请选择消息类型</option>
            <c:forEach items="${dicDatas}" var="dd">
                <option value="${dd.code}">${dd.name}</option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
       <span class="l">
            <a href="javascript:;" onclick="openUserDialog('新建消息','<c:url value="/msgMgr/toAddMsg"/>','1000','800')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新建消息</a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="15%">消息类型</th>
                <th width="">标题</th>
                <th width="15%">创建时间</th>
                <th width="15%">发布时间</th>
                <th width="10%">操作</th>
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
            url: '<c:url value='/msgMgr/pageMsg'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
            { data: 'rowNO'},
            { data: 'msgCode'},
            { data: 'title'},
            { data: 'createTime'},
            { data: 'sendTime'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,3,4,5],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.msgCode) {
                        $.each(jsdds, function(i,v){
                            if (v.code == full.msgCode) {
                                full.msgCode = v.name;
                                return false;
                            }
                        });
                    }
                    return full.msgCode;
                }
            },
            {
                "targets": [2],
                "orderable":false,
                "render": function(data, type, full) {
                    var title = full.title;
                    if (title) {
                        title = title.replace(/>/g, '&gt;');
                        title = title.replace(/</g, '&lt;');
                    }
                    return title;
                }
            },
            {
                "targets": [3],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.createTime) {
                        return o.formatDate(full.createTime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createTime;
                }
            },
            {
                "targets": [4],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.sendTime) {
                        return o.formatDate(full.sendTime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.sendTime;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openUserDialog(\'查看\',\'<c:url value="/msgMgr/toShowMsg?id='+full.id+'"></c:url>\',\'800\',\'600\')" class="ml-5" style="text-decoration:none;color:blue;">查看</a>';
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
            "aaSorting": [[ 4, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{
                    "msgCode" : $("#msgCode").val()
                },
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            datatables.fnDraw();
        });
    });

    /*管理员-增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

</script>
</body>
</html>