<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/7/14
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>查看sip应用详情</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        function goNumlistNumber(){
            openDialog('全局号码池展示','<c:url value="/sipNumPool/goNumlist"/>?&appid=${appInfo.appid}&managerType=${managerType}','800','450');
        }
    </script>
</head>
<body>
<div class="pd-20">
    <div class="form form-horizontal">
        <div class="row cl">
            <label class="form-label col-4">Account ID：</label>
            <div class="formControls col-8">
                <span>${appInfo.sid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">客户名称：</label>
            <div class="formControls col-8">
                <span>${appInfo.companyName}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">APP ID：</label>
            <div class="formControls col-8">
                <span>${appInfo.appid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">应用名称：</label>
            <div class="formControls col-8">
                <span>${appInfo.appName}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">最大并发数：</label>
            <div class="formControls col-8">
                <span>${appInfo.maxConcurrent}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">全局号码池列表：</label>
            <div class="formControls col-4">
                <a class="btn btn-primary radius" onclick="goNumlistNumber();"><i class="Hui-iconfont">&#xe645;</i>查看号码&nbsp;&gt;&gt;</a>

            </div>
            <div class="col-4"> </div>
        </div>
    </div>

    <h4 class="page_title"></h4>

    <div class="text-l">
        日期范围：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:200px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:200px;">
        &nbsp;&nbsp;
        子账号名称：<input type="text" id="subName" name="subName" class="input-text" placeholder="子账号名称" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        子账号ID：<input type="text" id="subid" name="subid" class="input-text" placeholder="子账号ID" style="width:200px;">

        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>

    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th rowspan="2" width="5%">序号</th>
                <th rowspan="2" width="10%">创建时间</th>
                <th rowspan="2" width="10%">子账号名称</th>
                <th rowspan="2" width="10%">子账号ID</th>
                <th rowspan="2" width="10%">中继名称</th>
                <th rowspan="2" width="10%">中继编号</th>
                <th rowspan="2" width="10%">随机选号</th>
                <th colspan="3" width="15%">费率</th>
                <th rowspan="2" width="5%">计费单位</th>
                <th rowspan="2" width="15%">操作</th>
            </tr>
            <tr class="text-c">
                <th>标准价</th>
                <th>折扣率</th>
                <th>折后价</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/sip/pageSubList?params[appid]=${appInfo.appid}'/>',
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
            { data: 'createTime'},
            { data: 'subName'},
            { data: 'subid' },
            { data: 'relayName'},
            { data: 'relayNum'},
            { data: 'numFlag'},
            { data: 'createDate'},
            { data: 'perDiscount'},
            { data: 'createDate'},
            { data: 'cycle'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "render": function(data, type, full) {
                    if (full.createTime) {
                        return o.formatDate(full.createTime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createTime;
                }
            },
            {
                "targets": [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.numFlag == '01') {
                        return '<span class="label label-success radius">打开</span>';
                    }
                    if (full.numFlag == '00') {
                        return '<span class="label label-default radius">关闭</span>';
                    }
                    return full.numFlag;
                }
            },
            {
                "targets": [7],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cycle == '6') {
                        return '${sipRate.per6}';
                    }
                    if (full.cycle == '60') {
                        return '${sipRate.per60}';
                    }
                    return '';
                }
            },
            {
                "targets": [8],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cycle == '6') {
                        return full.per6Discount/10;
                    }
                    if (full.cycle == '60') {
                        return full.per60Discount/10;
                    }
                    return '';
                }
            },
            {
                "targets": [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cycle == '6' && full.per6Discount) {
                        return Number((full.per6Discount * '${sipRate.per6}' /1000)).toFixed(4);
                    }
                    if (full.cycle == '60' && full.per60Discount) {
                        return Number((full.per60Discount * '${sipRate.per6}' /1000)).toFixed(4);
                    }
                    return '';
                }
            },
            {
                "targets": [-2],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cycle == '6') {
                        return '6秒';
                    }
                    if (full.cycle == '60') {
                        return '分钟';
                    }
                    return full.cycle;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openUserDialog(\'查看\',\'<c:url value="/sip/toShowSub?appid=${appInfo.appid}&subid=' + full.subid + '"/>\',\'800\',\'600\');" class="ml-5" style="text-decoration:none">查看</a>';
                    _ex_in += '<a title="号码池管理" href="javascript:;" onclick="openRelayDialog(\'号码池管理\',\'<c:url value="/subNumPool/subNumPoolMgr?subid='+full.subid+'"></c:url>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none">号码池管理</a>';
                    _ex_in += '<a title="黑白名单" href="javascript:;" onclick="openRelayDialog(\'黑白名单\',\'<c:url value="/subBlackWhite/subBlackWhtieMgr?subid='+full.subid+'"></c:url>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none">黑白名单</a>';
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
                params:{
                    "subName" : $("#subName").val(),
                    "subid" : $("#subid").val()
                },
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            datatables.fnDraw();
        });

        $(window).resize(function(){
            $(".table-sort").width("100%");
        });
    });

    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/sip/downloadSubReport'/>?params[relayName]=" + $("#relayName").val()
                + "&params[relayNum]=" + $("#relayNum").val()
                + "&params[appid]=" + '${appInfo.appid}'
                + "&timemax=" + $("#timemax").val()
                + "&timemin=" + $("#timemin").val()
        );
    }

    /*增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    function openRelayDialog(title,url,w,h) {
        layer_full(title,url,w,h);
    }

    /* 管理员状态修改 */
    function adminStatusUpdate(obj, id, status ){
        layer.confirm('是否更改状态？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/sip/updateAppStatus'/>",
                dataType:"json",
                data:{"appid":id, "status":status},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
            });
        });
    }
</script>
</html>
