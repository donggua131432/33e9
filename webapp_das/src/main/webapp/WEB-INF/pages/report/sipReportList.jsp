<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/12/6
  Time: 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>Sip业务话务报表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l" id="searchFrom">
        <div class="text-l">
            &nbsp;&nbsp;&nbsp;&nbsp;
            报表类型：
            <select id="reportType" name="reportType" class="input-text" style="width:200px;" nullmsg="时间不能为空">
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
            account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="account ID" style="width:250px">
            &nbsp;&nbsp;&nbsp;&nbsp;
            客户名称：<input type="text" id="companyName" name="companyName" class="input-text" placeholder="客户名称" style="width:200px;">
        </div>
         <br>
        <div class="text-l">
            &nbsp;&nbsp;&nbsp;&nbsp;
            Sub &nbsp;&nbsp;  ID：<input type="text" id="subid" name="sid" class="input-text" placeholder="Sub ID" style="width:280px">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            子账号名称：<input type="text" id="subName" name="subName" class="input-text" placeholder="子账号名称" style="width:280px;">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span >
                <a href="javascript:void(0);"  id="search" name="search" class="btn btn-success radius">
                    <i class="Hui-iconfont">&#xe665;</i> 查询
                </a>
            </span>
        </div>
    </div>

    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadTeleReport();" class="btn btn-primary radius r">导  出</a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <%--<th>序号</th>--%>
                <th>日期</th>
                <th>accountId</th>
                <th>客户名称</th>
                <th>Sub ID</th>
                <th>子账号名称</th>
                <%--<th>中继名称</th>--%>
                <th>呼叫总数</th>
                <th>接通率(%)</th>
                <th>应答率(%)</th>
                <th>累计通话时长(秒)</th>
                <th>计费通话时长(分)</th>
                <th>平均通话时长(秒)</th>
                <th>通话费用(元)</th>
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
            url: '<c:url value='/toSip/pageSipList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };
        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
//            { data: 'rowNo'},
            { data: 'statday'},
            { data: 'sid'},
            { data: 'companyName'},
            { data: 'subid'},
            { data: 'sub_name'},
            /*{ data: 'relay_name'},*/
            { data: 'callcnt'},
            { data: 'callCompletingRate'},
            { data: 'answerRate'},
            { data: 'thscsum'},
            { data: 'jfscsum'},
            { data: 'pjsc'},
            { data: 'fee'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10,11],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [0],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.statday) {
                        if($("#reportType").val()==1) {
                            return full.statday;
                        }else if ($("#reportType").val()==2){
                            return full.statday.substring(0,7);
                        }
                        else{
                            return full.statday;
                        }
                    }
                    return full.statday;
                }
            }
        ];
        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 7, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "ordering": false,//关闭排序
            "columnDefs":columnDefs
//            "createdRow": function ( row, data, index ) {
//                $('td', row).eq(0).text(index + 1);
//            }
        });

        // 搜索功能
        $("#search").on("click", function(){
            if(check()){
                search_param = {
                    params:{"companyName" : $("#companyName").val(),"subid" : $("#subid").val(),"subName" : $("#subName").val(),"sid" : $("#sid").val(),"recordType" : $("#recordType").val(),"busType" : $("#busType").val(),"reportType": $("#reportType").val()},
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
    function downloadTeleReport() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        window.open("<c:url value='/toSip/downloadSipReport'/>?params[companyName]=" + $("#companyName").val()
                + "&params[reportType]=" + $("#reportType").val()
                + "&params[sid]=" + $("#sid").val()
                + "&params[subid]=" + $("#subid").val()
                + "&params[subName]=" + $("#subName").val()
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