<%--
  Created by IntelliJ IDEA.
  User:dukai
  Date: 2016/8/22
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>收入报表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<script type="text/javascript">
    var dataTables = "",
         search_param = {
             params:{
                 "reportType":"1"
             }
         };
    $(function(){
        getDayRangeOverviewData();

        var dataTablesAjax = {
            url: '<c:url value='/incomeReportRest/getRestDayMonthReportList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'stafday'},
            { data: '' },
            { data: 'sid'},
            { data: 'name'},
            { data: 'total_fee' },
            { data: 'account_fee'},
            { data: ''},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {"targets": [0],"data": "id","sClass" : "text-c",
                "render": function(data, type, full) {
                    if (data) {
                        var reportType = $("#reportType").val();
                        if(reportType == "1"){
                            return o.formatDate(data, "yyyy-MM-dd");
                        }else{
                            return o.formatDate(data, "yyyy-MM");
                        }
                    }
                    return "";
                }
            },
            {"targets": [1],"data": "id","sClass" : "text-c",
                "render": function(data, type, full) {
                    return "语音专线";
                }
            },
            {"targets": [2,3,4,5,6,7],"data": "id","sClass" : "text-c",
                "render": function(data, type, full) {
                    if (data) {
                        return data;
                    }
                    return "";
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
            /*"pageLength": 10,//每页显示数据条数
            "aLengthMenu": [
                [10, 25, 50, "-1"],
                [10, 25, 50, "所有"]
            ],//定义每页显示数据数量*/
            "dom": 'rtilp',
            "sErrMode": 'none',
            "columns": columns,
            "ajax": dataTablesAjax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#searchButton").on("click", function(){
            search_param = getFormData();
            dataTables.fnDraw();
            getDayRangeOverviewData();
        });
    });

    //转换报表类型
    function selectReportType(){
        var reportType = $("#reportType").val();
        if(reportType == "1"){
            $("#spanDayReport").show();
            $("#spanMonthReport").hide();
        }else{
            $("#spanDayReport").hide();
            $("#spanMonthReport").show();
        }
        $("#stafDay,#stafDay1,#monthStafDay,#sid,#name").val("");
    }

    //获取form表单数据
    function getFormData(){
        var formData = {},reportType = $("#reportType").val();
        $("#searchFrom").find(":input[name]").each(function(){
            formData["params["+$(this).attr("name")+"]"] = $.trim($(this).val());
        });
        if(reportType == "1"){
            formData["params[stafDay]"]= $.trim($("#stafDay").val());
            formData["params[stafDay1]"]= $.trim($("#stafDay1").val());
        }else{
            formData["params[stafDay]"]= $.trim($("#monthStafDay").val());
        }
        formData["params[reportType]"]= $.trim($("#reportType").val());
        return formData;
    }

    //根据条件获取应付款总额
    function getDayRangeOverviewData(){
        var formData = getFormData();
        $.ajax({
            type:"post",
            url:"<c:url value='/incomeReportRest/getRestDayMonthTotalFee'/>",
            dataType:"json",
            async:false,
            data:formData,
            success : function(result) {
                if(result.data != null){
                    $("#totalReceivableFee").text(result.data.total_fee);
                }else{
                    $("#totalReceivableFee").text("0");
                }
            },
            error: function(){
                console.log("数据请求失败！");
            }
        });
    }

    /** 导出报表数据 **/
    function downLoadRestReport(){
        if(dataTables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        var formData = "",reportType = $("#reportType").val();
        $("#searchFrom").find(":input[name]").each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $.trim($(this).val());
        });
        if(reportType == "1"){
            formData += "&params[stafDay]=" + $.trim($("#stafDay").val());
            formData += "&params[stafDay1]=" + $.trim($("#stafDay1").val());
        }else{
            formData += "&params[stafDay]=" + $.trim($("#monthStafDay").val());
        }
        formData = formData.substring(1);
        window.open("<c:url value='/incomeReportRest/downloadRestReport'/>?" + formData);
    }

</script>
<jsp:include page="../common/nav.jsp"/>
<%--<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 收入数据 <span class="c-gray en">&gt;</span> 收入报表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>--%>

<div class="pd-20">
    <div class="text-l">
        <form id="searchFrom">
            报表类型：
            <select type="text" id="reportType" name="reportType" placeholder="报表类型" class="select" onchange="selectReportType(this);" style="width:160px;height: 31px">
                <option value="1" selected>日报</option>
                <option value="2">月报</option>
            </select>
            <span id="spanDayReport">
                日期：
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'stafDay1\')||\'%y-%M-%d\'}'})" id="stafDay" class="input-text Wdate" style="width:186px;"/>-
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'stafDay\')}'})" id="stafDay1" class="input-text Wdate" style="width:186px;"/>
            </span>
            <span id="spanMonthReport" style="display: none;">
                月份：
                <input type="hidden" id="maxMonth" value="${maxMonth}"/>
				<input type="hidden" id="minMonth" value="${minMonth}"/>
                <input autocomplete="off" type="text" class="input-text Wdate" id="monthStafDay"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'maxMonth\')}',minDate:'#F{$dp.$D(\'minMonth\')}',
                               readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false})" style="width:186px;"/>
            </span>
            Account ID：
            <input type="text" class="input-text" style="width:280px" maxlength="32" placeholder="请输入客户ID" id="sid" name="sid"/>
            客户名称
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户名称" id="name" name="name"/>
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
       <span class="l">
           <a href="javascript:void(0);" onclick="downLoadRestReport();" class="btn btn-primary radius">
               <i class="Hui-iconfont">&#xe644;</i> 导出
           </a>
       </span>
        <span class="r">
            应收账款(总计)：<span id="totalReceivableFee">0</span>元
        </span>
   </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr>
                    <th scope="col" colspan="8">收入报表</th>
                </tr>
                <tr class="text-c">
                    <th rowspan="2" width="200">日期</th>
                    <th rowspan="2" width="200">业务名称</th>
                    <th rowspan="2" width="200">Account ID</th>
                    <th rowspan="2" width="200">客户名称</th>
                    <th colspan="4" width="200">收入明细</th>
                </tr>
                <tr class="text-c">
                    <th>应收账款(元)</th>
                    <th>账户余额(元)</th>
                    <th>毛利(元)</th>
                    <th>毛利率(%)</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
