<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/2/18
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%><%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心</title>
    <link href="${appConfig.resourcesRoot}/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
    <script src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <script type="text/javascript">
        function show(){
            $("#show_tip").css("display","block");
        }
        function hide(){

            $("#show_tip").css("display","none");
        }

    </script>
</head>
<body>
<div id="toshow"></div>
<jsp:include page="../common/controlheader.jsp"/>
<section>
    <div id="sec_main">
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('zh','acc_manage','rechargelog');</script>
            <div class="main3">
                <div class="msg">
                    <h3>充值记录</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="Prepaid_records">
                    <!-- <div class="record_warm_note" id="show_tip" style="display: none">-->
                    <div class="recordNote">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1.充值记录最大只支持近12个月的数据查询。</p>
                    </div>
                    <div class="record_common re_span_common record_common_top">

                        <span class="check_font">时间：</span>
                        <!-- <i class="common_icon re_question_txt" onmouseover="show();" onMouseOut="hide()"></i>:-->
                        <!-- <i class="common_icon re_question_txt" ></i>:-->
                        <input id="startTime"  class="sm_input_style" />
                        <span class="check_font">至</span>
                            <input id="endTime" class="sm_input_style"/>
                            <span  class="pr_left check_font">支付状态：</span>
                            <select name="Support_state" class="select_style">
                                <option value="1">已支付</option>
                            </select>
                            <span class="pr_left check_font"> 充值类型：</span>
                            <select name="prepaid_type" class="select_style">
                                <option value="1">线下充值</option>
                            </select>
                        </div>
                    <button type="button" onclick="search();" class="md_btn f_right" style="margin-top:25px;">查询</button>
                    <div class="Export_history_billed clear">
                        <a href="javascript:void(0);" onclick="downloadReport('');" class="open_down_billed f_right"><i class="common_icon download_txt"></i>导出记录</a>
                    </div>
                <div class="pre_rates clear">
                    <table class="table_style " id="recharges">
                        <thead>
                        <tr>
                            <th>充值时间</th>
                            <th>充值金额(元)</th>
                            <th>支付状态</th>
                            <th>充值状态</th>
                        </tr>
                        </thead>
                        <tbody id="czjllog">
                        </tbody>
                    </table>
                    <div id="pagination" class="f_right app_sorter"></div>
                </div>
             </div>
     </div>

 </section>

 <!-- 充值记录 -->
<script id="czlog" type="text/x-jquery-tmpl">
	<tr class="date_hg">
		<td>\${rechargeTime}</td>
		<td>\${money}</td>
		<td>\${status}</td>
		<td>\${comment}</td>
	</tr>
</script>

<script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script>

    $(function(){
        laydate(startTime);
        laydate(endTime);
    });

    var startTime = {
        elem: '#startTime',
        format: 'YYYY-MM-DD',
        max: laydate.now(), //设定最大日期为当前日期
        min: laydate.now(-365), //最小日期
        istime: false,
        istoday: false,
        choose: function(datas){
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            endTime.start = datas //将结束日的初始值设定为开始日
        }
    };

    var endTime = {
        elem: '#endTime',
        format: 'YYYY-MM-DD',
        max: laydate.now(), //设定最大日期为当前日期
        min: laydate.now(-365), //最小日期
        istime: false,
        istoday: false,
        choose: function(datas){
            startTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    // 查询充值记录
    $("#recharges").page({
        url:"<c:url value='/rechargeRecord/pageRechargeRecord'/>",
        integralTemplate : "#czlog",
        integralBody : "#czjllog",
        dataRowCallBack : dataRowCallBack
    });

    function search() {
        var startTime = $("#startTime").val(); // TODO:
        var endTime = $("#endTime").val();
        // TODO:
        if(startTime != "" && endTime == "" ){
            alert("结束时间不能为空");
        }
        else if(startTime > endTime){
            alert("开始时间不能大于结束时间！");
        }

        else if(endTime != "" && startTime == "" ){
            alert("开始时间不能为空");
        }

        else{
        $.fn.reload({"startTime" : startTime , "endTime" : endTime});
        }

    }

    function dataRowCallBack(row) {

        // TODO:
        if (row.rechargeTime) {
            row.rechargeTime = o.formatDate(row.rechargeTime, "yyyy-MM-dd hh:mm:ss");
        }
        if (row.status == 0) {
            row.status = "已支付";
        }
        if (row.status == 1) {
            row.status = "已支付";
        }
        if (row.comment !== null) {
            row.comment = "线下充值";
        }

        return row;
    }

    $('#myModal').on('okHide', function(e){console.log('okHide')})
    $('#myModal').on('okHidden', function(e){console.log('okHidden')})
    $('#myModal').on('cancelHide', function(e){console.log('cancelHide')})
    $('#myModal').on('cancelHidden', function(e){console.log('cancelHidden')})

    $('#myModal_phone').on('okHide', function(e){console.log('okHide')})
    $('#myModal_phone').on('okHidden', function(e){console.log('okHidden')})
    $('#myModal_phone').on('cancelHide', function(e){console.log('cancelHide')})
    $('#myModal_phone').on('cancelHidden', function(e){console.log('cancelHidden')})

    // 导出报表
    function downloadReport(type) {

        $.fn.downloadReport("<c:url value='/rechargeRecord/downloadReport'/>");
        //
        //window.open("<c:url value='/rechargeRecord/downloadReport'/>?endTime=" + $("#endTime").val() + "&startTime=" + $("#startTime").val());
    }
</script>
</body>
</html>
