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
    <div class="text-l" id="searchFrom">
        &nbsp;&nbsp;&nbsp;&nbsp;
        报表类型：
        <select id="reportType" name="reportType" class="input-text" style="width:200px;">
            <option value="0">请选择</option>
            <option value="1">日报</option>
            <option value="2">月报</option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        时间：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}'})" id="datemin" name="datemin" datatype="*" class="input-text Wdate" style="width:200px;" onblur="check();">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'datemin\')}'})" id="datemax" name="datemax" class="input-text Wdate" style="width:200px;" onblur="check();">
        &nbsp;&nbsp;&nbsp;&nbsp;
        呼叫类型：
        <select id="rcdtype" name="rcdtype" class="input-text" style="width:200px;">
            <option value="">全部</option>
            <option value="CallInSip">呼入总机-SIP</option>
            <option value="CallInNonSip">呼入总机-非SIP</option>
            <option value="CallInDirect">呼入-直呼</option>
            <option value="CallOutLocal">呼出市话</option>
            <option value="CallOutNonLocal">呼出长途</option>

        </select>
    </div><br>
    <div class="text-l">
        &nbsp;&nbsp;&nbsp;&nbsp;
        Account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="Account ID" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        客户名称：<input type="text" id="companyName" name="companyName" class="input-text" placeholder="客户名称" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <span>
            <a href="javascript:void(0);"  id="search" name="search" class="btn btn-success radius">
                <i class="Hui-iconfont">&#xe665;</i> 查询
            </a>
        </span>
    </div>

    <div class="row cl pd-5 bg-1 bk-gray mt-20">
        <span class="r">
            <button  onclick="downloadSpReport();" class="btn btn-primary radius r" id="export">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </button>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <th>时间</th>
                <th>Account ID</th>
                <th>客户名称</th>
                <th>呼叫类型</th>
                <th>呼叫总数</th>
                <th>接通率</th>
                <th>应答率</th>
                <th>累计通话时长(秒)</th>
                <th>计费通话时长(分)</th>
                <th>平均通话时长(秒)</th>
                <th>录音时长(秒)</th>
                <th>计费录音时长(分)</th>
                <th>通话费用(元)</th>
                <th>录音费用(元)</th>
            </tr>
            <thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    var feeid = "";
    var companyName = "";
    $(document).ready(function() {

         //datatables ajax 选项
            var datatables_ajax = {
                url: '<c:url value='/ivr/pageIvrList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };
        var columns = [
            { data: 'statday'},
            { data: 'sid'},
            { data: 'companyName'},
            { data: 'rcdtype'},
            { data: 'callcnt'},
            { data: 'callRate'},
            { data: 'callAnswerRate'},
            { data: 'thscsum'},
            { data: 'jfscsum'},
            { data: 'pjsc'},
            { data: 'lyscsum'},
            { data: 'jflyscsum'},
            { data: 'fee'},
            { data: 'recordingfee'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10,11,12],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [0],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.statday) {
                        if($("#reportType").val()==2) {
                            return full.statday.toString().substr(0,full.statday.toString().lastIndexOf("-"));
                        }else{
                            return full.statday;
                        }

                    }
                    return full.statday;
                }
            },
            {
                "targets": [3],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.rcdtype == 'CallInSip') {
                        return '呼入总机-SIP';
                    }
                    if (full.rcdtype == 'CallInNonSip') {
                        return '呼入总机-非SIP';
                    }
                    if (full.rcdtype == 'CallInDirect') {
                        return '呼入-直呼';
                    }
                    if (full.rcdtype == 'CallOutLocal') {
                        return '呼出市话';
                    }
                    if (full.rcdtype == 'CallOutNonLocal') {
                        return '呼出长途';
                    }
                    return full.rcdtype;
                }
            },
            {
                "targets": [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.callAnswerRate==undefined) {
                        return "0.00%"
                    }else {
                        return full.callAnswerRate;
                    }
                }
            },
            {
                "targets": [7],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return full.thscsum;
                }
            },
            {
                "targets": [8],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return full.jfscsum;
                }
            },
            {
                "targets": [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return full.pjsc;
                }
            },
            {
                "targets": [10],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return full.lyscsum;
                }
            },
            {
                "targets": [11],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return full.jflyscsum;
                }
            }
        ];
        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "ordering": false,//关闭排序
            "dom": 'rtilp<"clear">',
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#search").on("click", function(){
           if(check()){
               search_param = {
                   params:{"companyName" : $("#companyName").val(),"sid" : $("#sid").val(),"reportType": $("#reportType").val(),"rcdtype":$("#rcdtype").val()},
                   datemax:$("#datemax").val(),
                   datemin:$("#datemin").val()
               };
               console.info($("#datemax").val());
               datatables.fnDraw();
           }


        });


        //报表类型联动时间格式
       $("#reportType").change(function(){
           $("#datemin") .val('');
           $("#datemax").val('');
           if($(this).val()==2){
               $("#datemin") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM'})");
               $("#datemax") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM'})");
           }else if($(this).val()==3){
               $("#datemin") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})");
               $("#datemax") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})");
           } else{
               $("#datemin") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd'})");
               $("#datemax") .attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd'})");
           }
       });

    });

    /** 导出列表 */
    function downloadSpReport() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }

        window.open("<c:url value='/ivr/downloadIvrReport'/>?params[companyName]=" + $("#companyName").val()
                + "&params[reportType]=" + $("#reportType").val()
                + "&params[sid]=" + $("#sid").val()
                + "&params[rcdtype]=" + $("#rcdtype").val()
                + "&datemin=" + $("#datemin").val()
                + "&datemax=" + $("#datemax").val()
        );
    }

    var check= function(){
        var flag =true;
        if(($("#datemin").val()==""&&$("#datemax").val()!="")){
            if($("#reportType").val()==0){
                layer.tips('请选择报表类型', '#reportType');
                flag = false;
            }
            layer.tips("请输入开始时间","#datemin");
            flag = false;
        }
        if(($("#datemin").val()!=""&&$("#datemax").val()=="")){
            if($("#reportType").val()==0){
                layer.tips('请选择报表类型', '#reportType');
                flag = false;
            }
            layer.tips("请输入结束时间","#datemax");
            flag = false;
        }
        if(($("#datemin").val()!=""&&$("#datemax").val()!="")&&$("#reportType").val()==0){
            layer.tips('请选择报表类型', '#reportType');
            flag = false;
        }
        return flag;
    }
</script>
</body>
</html>