<%--
  Created by IntelliJ IDEA.
  User: DuKai
  Date: 2016/1/29
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>智能云费率配置</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        var dataTables = "";
        var search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/callRate/pageCallRateUnion'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'sid'},
                {data: 'name'},
                {data: 'fee_mode'},
                {data: 'addtime'},
                {data: 'start_date'},
                {data: 'end_date'},
                {data: 'forever'},
                {data: 'relay_rent'},
                {data: 'callin'},
                {data: 'callin_discount'},
                {data: ''},
                {data: 'callout'},
                {data: 'callout_discount'},
                {data: ''},
                {data: ''}
            ];

            // 对数据进行处理
            var columnDefs = [
                {"targets": [2],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        return full.fee_mode;
                    }
                },
                {"targets": [3],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.addtime) {
                            return o.formatDate(full.addtime, "yyyy-MM-dd hh:mm:ss");
                        }
                        return full.addtime;
                    }
                },
                {"targets": [4], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(full.start_date == undefined){
                            return "";
                        }else{
                            return full.start_date;
                        }
                    }
                },
                {"targets": [5], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(full.end_date == undefined){
                            return "";
                        }else{
                            return full.end_date;
                        }
                    }
                },

                {"targets": [6], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(full.forever == 1){
                            return '<i class="Hui-iconfont" title="是" style="font-size: 20px; color: green;">&#xe6e1;</i>';
                        }else{
                            return '<i class="Hui-iconfont" title="否" style="font-size: 20px; color: red;">&#xe706;</i>';
                        }
                    }
                },
                {"targets": [7], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(full.relay_rent == undefined){
                            return "";
                        }else{
                            return $.calcEval(full.relay_rent+"*"+full.relay_cnt);
                        }
                    }
                },
                {"targets": [8], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return ${callRate.callin};
                    }
                },
                {"targets": [9], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return (full.callin_discount/10);
                    }
                },
                {"targets": [10], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(full.callin_discount != undefined){
                            return $.calcEval("${callRate.callin}*"+full.callin_discount+"/1000").toFixed(4);
                        }else{
                            return "";
                        }

                    }
                },
                {"targets": [11], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return ${callRate.callout};
                    }
                },
                {"targets": [12], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return (full.callout_discount/10);
                    }
                },
                {"targets": [13], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(full.callout_discount != undefined){
                            return $.calcEval("${callRate.callout}*"+full.callout_discount+"/1000").toFixed(4);
                        }else{
                            return "";
                        }

                    }
                },
                {"targets": [14], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return '<a title="查看" href="javascript:;" onclick="callRate_openDialog(\'费率配置详情\',\'<c:url value="/callRate/getCallRateDetail?feeid='+full.feeid+'"/>\',\'800\',\'800\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe695;</i>查看</a>';
                    }
                }
            ];

            //初始化表格渲染
            dataTables = $(".table-sort").dataTable({
                "processing": true, // 是否显示取数据时的那个等待提示
                "serverSide": true,//这个用来指明是通过服务端来取数据
                "bStateSave": true,//状态保存
                "bFilter": false, //隐藏掉自带的搜索功能
                "ordering": false,//关闭排序
                "dom": 'rtilp',
                "sErrMode": 'none',
                "columns": columns,
                ajax: dataTablesAjax,
                "columnDefs":columnDefs
            });

            // 搜索功能
            $("#searchButton").on("click", function(){
                var formData = {};
                $('#searchFrom').find('input[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                })
                search_param = formData;
                dataTables.fnDraw();
            });
        })

        /*费率配置-增加*/
        function callRate_openDialog(title,url,w,h){
            layer_show(title,url,w,h);
        }
    </script>

</head>

<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            Account ID：
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户ID" id="sid" name="sid"/>
            客户名称：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入客户名称" id="name" name="name"/>
            生成时间：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'addtime1\')||\'%y-%M-%d\'}'})" id="addtime" name="addtime" class="input-text Wdate" style="width:186px;"/>-
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'addtime\')}'})" id="addtime1" name="addtime1" class="input-text Wdate" style="width:186px;"/>
            结束时间：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate1\')||\'%y-%M-%d\'}'})" id="endDate" name="endDate" class="input-text Wdate" style="width:186px;"/>-
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endDate\')}'})" id="endDate1" name="endDate1" class="input-text Wdate" style="width:186px;"/>
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <%--<div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="callRate_openDialog('新增费率配置','<c:url value="/callRate/addCallRate"/>','800','820')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 新增费率配置
            </a>
        </span>
    </div>--%>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr>
                    <th scope="col" colspan="15">智能云调度费率列表</th>
                </tr>
                <tr class="text-c">
                    <th rowspan="3" width="150">Account ID</th>
                    <th rowspan="3" width="200">客户名称</th>
                    <th rowspan="3" width="60">计费单位</th>
                    <th rowspan="3" width="120">生成时间</th>
                    <th colspan="3">有效时间</th>
                    <th colspan="7">费用项目</th>
                    <th rowspan="3" width="100">操作</th>
                </tr>
                <tr class="text-c">
                    <th rowspan="2" width="80">生效时间</th>
                    <th rowspan="2" width="80">结束时间</th>
                    <th rowspan="2" width="80">是否永久有效</th>
                    <th rowspan="2">专线月租(元)</th>
                    <th colspan="3">呼入</th>
                    <th colspan="3">呼出</th>
                </tr>
                <tr class="text-c">
                    <th>标准价</th>
                    <th>折扣率</th>
                    <th>折后价</th>
                    <th>标准价</th>
                    <th>折扣率</th>
                    <th>折后价</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
