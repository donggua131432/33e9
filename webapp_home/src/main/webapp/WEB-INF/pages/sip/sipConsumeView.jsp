<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/7/12
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>SIP消费总览</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/My97DatePicker/WdatePicker.js"></script>
    <!-- 消费报表 -->
    <script id="reportTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>\${num}</td>
			<td>\${statday}</td>
			<td>\${subName}</td>
			<td style="padding-left: 8px; padding-right: 8px;">\${subid}</td>
			<td>\${fee}</td>
		</tr>
	</script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/pagings.js"></script>
    <script type="text/javascript">
        var subInfoArray = {},dateReportTable,monthReportTable;
        var date = new Date();
        date.setMonth(date.getMonth()-3);
        var beforeMonth = date.format("yyyy-MM-dd");
        var statday = {
            elem: '#statday',
            format: 'YYYY-MM-DD',
            max: laydate.now(), //设定最大日期为当前日期
            min: beforeMonth,
            istime: true,
            istoday: false,
            isclear:false,
            choose: function(data){
                //日期限制跨度30天
                var date = new Date(data);
                date.setDate(date.getDate()+30);
                var strAfterDay = date.format("yyyy-MM-dd");
                if(new Date(strAfterDay) >= new Date(laydate.now("yyyy-MM-dd"))){
                    statday1.max = laydate.now("yyyy-MM-dd");
                }else{
                    statday1.max = strAfterDay;
                }
                statday1.min = data; //开始日选好后，重置结束日的最小日期
                statday1.start = data //将结束日的初始值设定为开始日
            }
        };
        var statday1 = {
            elem: '#statday1',
            format: 'YYYY-MM-DD',
            max: laydate.now(), //设定最大日期为当前日期
            min: beforeMonth,
            istime: true,
            istoday: false,
            isclear:false,
            choose: function(data){
                statday.max = data; //结束日选好后，重置开始日的最大日期
                statday.start = data;
                //日期限制跨度30天
                var date = new Date(data);
                date.setDate(date.getDate()-30);
                var strBeforeDay = date.format("yyyy-MM-dd");
                if(new Date(strBeforeDay) <= new Date(beforeMonth)){
                    statday.min = beforeMonth;
                }else{
                    statday.min = strBeforeDay;
                }
            }
        };

        $(function(){
            //应用列表
            subInfoArray = getAppInfo("<c:url value='/sipTrafficStatistics/getSipRelayInfo'/>","subid","subName");
            //日期组件初始化渲染
            laydate(statday);
            laydate(statday1);
            //日报
            dateReportTable = $("#dateReportTable").page({
                url:"<c:url value='/sipConsumeView/getSipDayReportList'/>",
                pageSize:30,
                integralBody:"#dateReportTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#reportTemplate",// tmpl中的模版ID
                pagination:"#dateReportPagination", // 分页选项容器
                dataRowCallBack:dataRowCallBack
            });

            //月报
            monthReportTable = $("#monthReportTable").page({
                url:"<c:url value='/sipConsumeView/getSipMonthReportList'/>",
                pageSize:30,
                integralBody:"#monthReportTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#reportTemplate",// tmpl中的模版ID
                pagination:"#monthReportPagination", // 分页选项容器
                dataRowCallBack:dataRowCallBack
            });
        });


        function dataRowCallBack(row,num) {
            //序号列
            row.num = num;
           /* if (row.statday) {
             row.statday = o.formatDate(row.statday, "yyyy-MM-dd");
             }*/
            if(row.subid){
                row.subName = subInfoArray[row.subid];
            }
            return row;
        }

        function changeReportType(obj){
            var reportType = $(obj).val();
            if(reportType == "dateType"){
                $("#dateSpan").show();
                $("#dateReportDiv").show();
                $("#monthSpan").hide();
                $("#monthReportDiv").hide();
            }else if(reportType == "monthType"){
                $("#dateSpan").hide();
                $("#dateReportDiv").hide();
                $("#monthSpan").show();
                $("#monthReportDiv").show();
            }
        }

        //查询
        function searchDateMonthReport(){
            var formData = {}, reportType = $("#reportType").val();
            formData["params[reportType]"] = reportType;
            formData["params[subid]"] = $("#subid").val();
            if(reportType == "dateType"){
                //查询日报=============================================================================
                formData["params[statday]"] = $("#statday").val();
                formData["params[statday1]"] = $("#statday1").val();
                //提示开始时间不能大于结束时间
                if(formData["params[statday]"] != "" && formData["params[statday1]"] != ""){
                    var statday = new Date(formData["params[statday]"].replace(/\-/g, '\/'));
                    var statday1 = new Date(formData["params[statday1]"].replace(/\-/g, '\/'));
                    if(statday > statday1){
                        openMsgDialog("查询时间的开始时间不能大于结束时间！");
                        return;
                    }
                }
                dateReportTable.reload(formData);
            }else if(reportType == "monthType"){
                //查询月报=============================================================================
                formData["params[statday]"] = $("#monthStatDay").val();
                monthReportTable.reload(formData);
            }
        }

        //导出记录
        function downloadDateMonthReport() {
            var formData = "",  reportType = $("#reportType").val();
            formData += "&params[reportType]=" + reportType;
            formData += "&params[subid]=" + $.trim($("#subid").val());
            if(reportType == "dateType"){
                //导出日报记录=============================================================================
                formData += "&params[statday]=" + $.trim($("#statday").val());
                formData += "&params[statday1]=" + $.trim($("#statday1").val());
                formData = formData.substring(1);
                window.open("<c:url value='/sipConsumeView/downloadSipDayReportList'/>?"+formData);
            }else if(reportType == "monthType") {
                //导出月报记录=============================================================================
                formData += "&params[statday]=" + $.trim($("#monthStatDay").val());
                formData = formData.substring(1);
                window.open("<c:url value='/sipConsumeView/downloadSipMonthReportList'/>?"+formData);
            }
        }
    </script>
</head>
<body>
<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('sp','sipMgr','sipConsumeView');</script>
        <!-- 左侧菜单 end -->


        <div class="main3">
            <div class="container" >
                <div class="msg">
                    <h3>消费总览</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="sip_consumption">
                    <div class="sipConsumptionBox f_left">
                        <div class="sip_consumptionComFont"><i class="common_icon y_txt"></i>昨日消费</div>
                        <div class="ac_je ac_yc" style="margin-top:20px;"><span>${sipYesterdayFee}</span>元</div>
                    </div>

                    <div class="sipConsumptionBox f_right">
                        <div class="sip_consumptionComFont"><i class="common_icon m_txt"></i>本月消费</div>
                        <div class="ac_je ac_tm" style="margin-top:20px;"><span>${sipMonthFee}</span>元</div>
                    </div>
                </div>
                <div class="msg clear">
                    <h3>消费报表</h3>
                </div>
                <!--查询条件-->
                <div id="dateDiary" class="application_msg">
                    <div class="app_select" >
                        <span class="check_font">报表类型：</span>
                        <select autocomplete="off" id="reportType" onchange="changeReportType(this);" class="sip_select">
                            <option value="dateType">日报</option>
                            <option value="monthType">月报</option>
                        </select>

                        <span class="check_font " style="margin-left:-28px;">子账号名称：</span>
                        <select autocomplete="off" id="subid" name="subid" style="width:180px;">
                            <option selected value="">全部</option>
                        </select>

                        <span style="margin-left:-28px;" id="dateSpan">
                                <b class="Bred">*</b>
                                <span class="check_font">时间：</span>
                                <input autocomplete="off" type="text" readonly id="statday" class="sm_input_style"/>
                                <span class="check_font">至</span>
                                <input autocomplete="off" type="text" readonly id="statday1" class="sm_input_style"/>
                        </span>

                        <span style="margin-left:-28px; display:none;" id="monthSpan">
                            <b class="Bred">*</b>
                            <span class="check_font">时间：</span>
                            <input type="hidden" id="maxMonth" value="${currentDate}"/>
                            <input type="hidden" id="minMonth" value="${beforeMonth}"/>
                            <input autocomplete="off" type="text" class="Wdate" id="monthStatDay"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'maxMonth\')}',minDate:'#F{$dp.$D(\'minMonth\')}',
                                   readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false})" style="width:184px;height:28px;"/>
                        </span>
                        <button type="button" class="md_btn f_right" style="margin-top:-5px;" onclick="searchDateMonthReport();">查询</button>
                    </div>

                    <!--日报表单-->
                    <div class="application clear" id="dateReportDiv">
                        <div class="record_down f_right">
                            <span class="open_down_billed" onclick="downloadDateMonthReport();"><i class="common_icon download_txt"></i>导出记录</span>
                        </div>
                        <table class="table_style" id="dateReportTable">
                            <thead>
                                <tr>
                                    <th style="width:10%">序号</th>
                                    <th style="width:10%">日期</th>
                                    <th style="width:30%">子账号名称</th>
                                    <th style="width:30%">子账号 ID</th>
                                    <th style="width:20%">消费金额(元)</th>
                                </tr>
                            </thead>
                            <tbody id="dateReportTbody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="dateReportPagination"></div>
                    </div>

                    <!--月报表单-->
                    <div class="application clear" id="monthReportDiv" style="display: none;">
                        <div class="record_down f_right">
                            <span class="open_down_billed" onclick="downloadDateMonthReport();"><i class="common_icon download_txt"></i>导出记录</span>
                        </div>
                        <table class="table_style" id="monthReportTable">
                            <thead>
                            <tr>
                                <th style="width:10%">序号</th>
                                <th style="width:10%">日期</th>
                                <th style="width:30%">子账号名称</th>
                                <th style="width:30%">子账号 ID</th>
                                <th style="width:20%">消费金额(元)</th>
                            </tr>
                            </thead>
                            <tbody id="monthReportTbody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter" id="monthReportPagination"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
