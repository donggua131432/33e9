<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>营收报表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var datatables_days = "", search_param_days = {datemin:'${ymd}', datemax:'${ymd}'};
        var datatables_month = "", search_param_month = {ym:'${ym}'};

        $(function(){
            $.Huitab("#tab-system .btn-group span","#tab-system .tabCon","btn-primary","click","0");

            var dataTablesAjax_days = {
                url: '<c:url value='/revenue/report/pageDays'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param_days);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns_days = [
                { data: 'statday'},
                { data: 'sid'},
                { data: 'companyName'},
                { data: 'fee'},
                { data: 'balance'},
                { data: 'tfee'},
                { data: 'yw'}
            ];

            // 对数据进行处理
            var columnDefs_days = [
                {
                    "targets": [0,1,2,3,4,5,6],
                    "orderable":false,
                    "sClass" : "text-c"
                },
                {
                    "targets": [0],
                    "render": function(data, type, full) {
                        /*if (data) {
                            return o.formatDate(data, "yyyy-MM-dd");
                        }*/
                        return data;
                    }
                },
                {
                    "targets": [6],
                    "sClass" : "text-c",
                    "render": function(data, type, full) {
                        var yw = "";
                        if (data) {
                            yw = data;
                            yw = yw.replace("rest","专线语音");
                            yw = yw.replace("mask","专号通");
                            yw = yw.replace("ecc","云总机");
                            yw = yw.replace("cc","智能云调度");
                            yw = yw.replace("voiceverify","语音验证码");
                            yw = yw.replace("voice","语音通知");
                            yw = yw.replace("sp","云话机");
                            yw = yw.replace("sip","SIP业务");
                            yw = yw.replace("vn","虚拟号");

                        }
                        return yw;
                    }
                }
            ];

            //初始化表格渲染
            datatables_days = $("#tableDays").dataTable({
                "processing": false, // 是否显示取数据时的那个等待提示
                "serverSide": true,//这个用来指明是通过服务端来取数据
                "bStateSave": true,//状态保存
                "bFilter": false, //隐藏掉自带的搜索功能
                "ordering": false,//关闭排序
                "dom": 'rtilp',
                "sErrMode": 'none',
                "columns": columns_days,
                "ajax": dataTablesAjax_days,
                "columnDefs":columnDefs_days
            });

            // 搜索功能
            $("#search_days").on("click", function(){
                search_param_days = {
                    datemin:$("#timemin").val(),
                    datemax:$("#timemax").val(),
                    params:{
                        sid:$("#searchFrom_days input[name='sid']").val(),
                        companyName:$("#searchFrom_days input[name='companyName']").val()
                    }
                };
                datatables_days.fnDraw();
            });

            /*********************************** datatable2 of start******************************************************************************* */

            var dataTablesAjax_month = {
                url: '<c:url value='/revenue/report/pageMonth'/>',
                type: 'POST',
                "data": function (d) {
                    var _page = datatables_ajax_data(d);
                    return $.extend({}, _page, search_param_month);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns_month = [
                {data: 'ym'},
                {data: 'sid'},
                {data: 'companyName'},
                {data: 'fee'},
                {data: 'pbalance'},
                {data: 'bbalance'},
                {data: 'tfee'},
                {data: ''}
            ];

            // 对数据进行处理
            var columnDefs_month = [
                {
                    "targets": [0, 1, 2, 3, 4, 5, 6, 7],
                    "sClass": "text-c"
                },
                {
                    "targets": [7],
                    "sClass": "text-c",
                    "render": function (data, type, full) {
                        return '<a title="查看" href="javascript:;" onclick="openDialog(\'查看\',\'<c:url value="/revenue/report/monthDetails?feeid='+full.feeid+'&ym='+full.ym+'&sid='+full.sid+'"/>\',\'1000\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont"></i>查看</a>';
                    }
                }
            ];

            //初始化表格渲染
            datatables_month = $("#tableMonth").dataTable({
                "processing": false, // 是否显示取数据时的那个等待提示
                "serverSide": true,//这个用来指明是通过服务端来取数据
                "bStateSave": true,//状态保存
                "bFilter": false, //隐藏掉自带的搜索功能
                "ordering": false,//关闭排序
                "dom": 'rtilp',
                "sErrMode": 'none',
                "columns": columns_month,
                "ajax": dataTablesAjax_month,
                "columnDefs": columnDefs_month
            });

            /*********************************** datatable2 of end ******************************************************************************* */

                // 搜索功能
            $("#search_month").on("click", function(){
                search_param_month = {
                    ym:$("#ym").val(),
                    params:{
                        sid:$("#searchFrom_month input[name='sid']").val(),
                        companyName:$("#searchFrom_month input[name='companyName']").val()
                    }
                };
                datatables_month.fnDraw();
            });

            //复选框全选
            $(".checkall").click(function () {
                var check = $(this).prop("checked");
                $(".checkchild").prop("checked", check);
            });
        });

        // 导出公共号码资源池
        function downloadDays() {
            if(datatables_days._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }
            var params = jQuery.param(search_param_days);
            window.open("<c:url value='/revenue/report/downloadDays'/>?" + params);
        }

        // 导出公共号码资源池
        function downloadMonth() {
            if(datatables_month._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }
            var params = jQuery.param(search_param_month);

            window.open("<c:url value='/revenue/report/downloadMonth'/>?" + params);
        }

        /*修改并发数*/
        function openDialog(title,url,w,h){
            layer_show(title,url,w,h);
        }
    </script>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="page-container" style="padding: 20px;">
    <%--<form class="form form-horizontal" id="form-article-add">--%>
    <div id="tab-system" class="HuiTab">
        <%--<div class="tabBar cl">--%>
            <div class="btn-group">
                <span data-stype="1" class="btn btn-default radius">日报</span>
                <span data-stype="7" class="btn btn-default radius">月报</span>
            </div>
        <%--</div>--%>

        <div class="tabCon mt-20">
            <form id="searchFrom_days" class="form form-horizontal">
                <div class="text-l">
                    时间：
                    <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d\'}'})" id="timemin" value="${ymd}" class="input-text Wdate" style="width:200px;">
                    -
                    <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'${ymd}'})" id="timemax" value="${ymd}" class="input-text Wdate" style="width:200px;">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="account ID" style="width:200px;">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    客户名称：<input id="companyName" name="companyName" class="input-text" style="width:200px;" placeholder="请输入客户名称"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-success radius" id="search_days"><i class="Hui-iconfont">&#xe665;</i>查询</button>
                </div>
            </form>

            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <button style="float:right;" type="button" class="btn btn-primary radius" onclick="downloadDays();">导出</button>
            </div>

            <div class="mt-20">
                <table id="tableDays" class="table table-border table-bordered table-hover table-bg table-sort">
                    <thead>
                    <tr class="text-c">
                        <th width="15%">时间</th>
                        <th width="20%">Account ID</th>
                        <th width="15%">客户名称</th>
                        <th width="10%">应收账款（元）</th>
                        <th width="10%">账户余额（元）</th>
                        <th width="10%">累计消费总额（元）</th>
                        <th width="20%">使用产品</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>

        <%-------------------------------------------------------------------------------月报----------------------------------------------------------------------------%>

        <div class="tabCon mt-20">

            <form id="searchFrom_month" class="form form-horizontal">
                <div class="row cl">
                    时间：
                    <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'${ym}'})" value="${ym}" id="ym" class="input-text Wdate" style="width:200px;">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    account ID：<input type="text" name="sid" class="input-text" placeholder="account ID" style="width:200px;">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    客户名称：<input name="companyName" class="input-text" style="width:200px;" placeholder="请输入客户名称"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" class="btn btn-success radius" id="search_month"><i class="Hui-iconfont">&#xe665;</i>查询</button>
                </div>
            </form>

            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <button style="float:right;" type="button" class="btn btn-primary radius" onclick="downloadMonth();">导出</button>
            </div>

            <div class="mt-20">
                <table id="tableMonth" class="table table-border table-bordered table-hover table-bg table-sort">
                    <thead>
                    <tr class="text-c">
                        <th width="10%">时间</th>
                        <th width="20%">Account ID</th>
                        <th width="20%">客户名称</th>
                        <th width="10%">应收账款（元）</th>
                        <th width="10%">期初余额（元）</th>
                        <th width="10%">账户余额（元）</th>
                        <th width="10%">累计消费总额（元）</th>
                        <th width="10%">消费账单</th>
                    </tr>
                    </thead>
                </table>
            </div>

        </div>
    </div>
    <%--</form>--%>
</div>

</body>
</html>
