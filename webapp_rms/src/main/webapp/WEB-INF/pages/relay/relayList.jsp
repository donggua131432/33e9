<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>中继配置管理</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="" id="searchFrom">
            日期：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:180px;">
            -
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:180px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            中继名称：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入中继名称" id="relayName" name="relayName"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            中继ID：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入中继ID" id="relayNum" name="relayNum"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            中继方向：
            <select type="text" id="relayType" name="relayType" placeholder="请选择中继方向" class="select" style="width:160px;height: 31px">
                <option value="">请选择中继方向</option>
                <option value="00">闭塞</option>
                <option value="01">入中继</option>
                <option value="10">出中继</option>
                <option value="11">双向中继</option>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;<br><br>
            SIP-URI域名：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入SIP-URI域名" id="sipUrl" name="sipUrl"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            IP:PROT：
            <input type="text" class="input-text" style="width:160px" placeholder="请输入IP:PROT" id="ipport" name="ipport"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success" id="searchButton" ><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="openUserDialog('新增中继','<c:url value="/relay/toAddRelay"/>','1050','800');" class="btn btn-primary radius l">新增中继</a>
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="5%">创建时间</th>
                <th width="10%">中继名称</th>
                <th width="5%">中继ID</th>
                <th width="5%">中继方向</th>
                <th width="5%">并发限制</th>
                <th width="8%">外域对端IP和端口</th>
                <th width="8%">外域本端IP和端口</th>
                <th width="8%">内域对端IP和端口</th>
                <th width="8%">内域本端IP和端口</th>
                <th width="8%">SIP-URI域名</th>
                <th width="5%" style="color: blue;">系统默认强显中继</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
</body>
<script type="text/javascript">
    var dataTables = "";
    var search_param = {};
    $(document).ready(function() {
        //dataTablesAjax 选项
        var dataTablesAjax = {
            url: '<c:url value='/relay/pageRelay'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            {data: 'rowNO'},
            {data: 'createTime'},
            {data: 'relayName'},
            {data: 'relayNum'},
            {data: 'relayType'},
            {data: 'maxConcurrent'},
            {data: 'ipport1'},
            {data: 'ipport2'},
            {data: 'ipport3'},
            {data: 'ipport4'},
            {data: 'sipUrl'},
            {data: 'isForce'},
            {data: ''}
        ];

        // 对数据进行处理 （01:已分配  02:已锁定  03:待分配）
        var columnDefs = [
            {
                "targets": [0, 1,2,3,4, 5, -1],
                "sClass" : "text-c"
            },
            {
                "targets": [-2],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.isForce == '0') {
//                        return '<span class="label label-primary radius">否</span>';
                        return "";
                    }
                    if (full.isForce == '1') {
                        return '<span class="label label-success radius">是</span>';
                    }
                    return full.isForce;
                }
            },
            {
                "targets": [1],
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
                "render" : function(data, type, full) {
                    var relayType = full.relayType;
                    if (full.relayType == '00') {
                        relayType = "闭塞";
                    } else if (full.relayType == '01') {
                        relayType = "入中继";
                    } else if (full.relayType == '10') {
                        relayType = "出中继";
                    }else if (full.relayType == '11') {
                        relayType = "双向中继";
                    }
                    return relayType;
                }
            },
            {
                "targets": [-1],
                "data": "id",
                "render": function(data, type, full) {

                    var _ex_in = "";
                    _ex_in += '<a title="ECC配置" href="javascript:;" onclick="openUserDialog(\'ECC配置\',\'<c:url value="/relay/toEccConfig?id='+full.id+'"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i>ECC配置</a>';
                    if (full.relayType == '10'||full.relayType == '11' ) {
                        _ex_in += '<a title="中继强显号池号码管理" href="javascript:;" onclick="openUserDialog(\'中继强显号池号码管理\',\'<c:url value="/relayNumPool/relayNumPoolMgr?id='+full.id+'"/>\',\'1000\',\'700\')" class="ml-5" style="text-decoration:none;color: blue;">中继强显号池号码管理</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    }
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openUserDialog(\'编辑\',\'<c:url value="/relay/toEditRelayInfo?id='+full.id+'"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i>编辑</a>';
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openUserDialog(\'查看\',\'<c:url value="/relay/toShowRelayInfo?id='+full.id+'"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont"></i>查看</a>';
                    _ex_in += '<a title="删除" href="javascript:;" onclick="deleteRelayInfo(\''+full.id+'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; ">&#xe6e2;</i>删除</a><br/>';

                    return _ex_in;
                }
            }
        ];

        //初始化表格渲染
        dataTables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "ordering": false,//关闭排序
            "dom": 'rtilp',
//            "aaSorting": [ 1, "desc" ], // 默认的排序列（columns）从0开始计数
            "sErrMode": 'none',
            "columns": columns,
            ajax: dataTablesAjax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#searchButton").on("click", function(){
            search_param = {
                params:{
                    "relayName" : $("#relayName").val(),
                    "relayNum" : $("#relayNum").val(),
                    "relayType" : $("#relayType").val(),
                    "sipUrl" : $("#sipUrl").val(),
                    "ipport" : $("#ipport").val()
                },
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            dataTables.fnDraw();
        });
    });

    /*增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    /*增加*/
    function openRelayDialog(title,url,w,h){
        layer_full(title,url,w,h);
    }

    /** 删除中继信息 **/
    function deleteRelayInfo(id,appid){
        layer.confirm('是否确定删除？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/relay/deleteRelay'/>",
                dataType:"json",
                data:{"id" : id},
                success:function(result){
                    if (result.status == 0) {
                        layer.alert("删除成功", {icon: 1});
                        dataTables.fnDraw();
                    } else if(result.status == 1){
                        layer.alert("删除失败", {icon: 2});
                    }else if(result.status == 2){
                        layer.alert("此条中继正在被引用，无法删除", {icon: 2});
                    }
                }
            });
        });
    }

    /** 导出列表 */
    function downloadReport() {
        if(dataTables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/relay/downloadReport'/>?params[relayName]=" + $("#relayName").val()
                + "&params[relayNum]=" + $("#relayNum").val()
                + "&params[relayType]=" + $("#relayType").val()
                + "&params[sipUrl]=" + $("#sipUrl").val()
                + "&params[ipport]=" + $("#ipport").val()
                + "&timemin=" + $("#timemin").val()
                + "&timemax=" + $("#timemax").val()
        );
    }

</script>
</html>
