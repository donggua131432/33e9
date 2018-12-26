<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>话务统计</title>
    <link rel="stylesheet" href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" >
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/calceval.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/My97DatePicker/WdatePicker.js"></script>
    <script id="DSTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>\${abLine}</td>
			<td>\${statDay}</td>
			<td>\${appName}</td>
			<td>\${rcdType}</td>

			<td>\${callCnt}</td>
			<td>\${callRate}</td>

			<td>\${thscSum}</td>
			<td>\${jfscSum}</td>
			<td>\${pjthsc}</td>
			<td>\${lyscsum}</td>

			<td>\${fee}</td>
			<td>\${recordingFee}</td>
			<td>\${feesum}</td>
		</tr>
	</script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <%--<script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>--%>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script type="text/javascript">
        var appInfoArray = {},dataStatisticsTable,dataPage;
        var formData = {};

        $(function(){
            //应用列表
            appInfoArray = getAppInfo("<c:url value='/eccApp/getALLAppInfo'/>", "appid", "appName");
            //主叫
            dataPage = $("#dataStatisticsTable").page({
                url:"<c:url value='/eccApp/pageDataStatistics'/>",
                pageSize:30,
                integralBody:"#dataStatisticsTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#DSTemplate",// tmpl中的模版ID
                pagination:"#DSPagination", // 分页选项容器
//                param:{"rcdType":"sipprest1"},
                dataRowCallBack:dataRowCallBack
            });

            //报表类型联动时间格式
            $("#reportType").change(function(){
                $("#startTime") .val('');
                $("#endTime").val('');
                if($(this).val() == 1) {
                    $("#date_m").hide();
                    $("#date1").show();
                } else {
                    $("#date1").hide();
                    $("#date_m").show();
                }
            });
        });

        function dataRowCallBack(row,num) {
            //序号列
            row.num = num;
//
            // 呼叫类型(回拨 : sipprest, 直拨 : sippout, 回呼 : sippin)
            if(row.rcdType == "CallInSip"){
                row.rcdType = "呼入总机SIP";
            }else if(row.rcdType == "CallInNonSip"){
                row.rcdType = "呼入总机非SIP";
            }else if(row.rcdType == "CallInDirect"){
                row.rcdType = "呼入总机直呼";
            }else if(row.rcdType == "CallOutLocal"){
                row.rcdType = "呼出市话";
            }else if(row.rcdType == "CallOutNonLocal"){
                row.rcdType = "呼出长途";
            }
            if (row.statDay) {
                if($("#reportType").val()==2) {
                    row.statDay = new Date(row.statDay).format("yyyy-MM");
                    row.abLine = "月报";
                }else{
                    row.statDay = new Date(row.statDay).format("yyyy-MM-dd");
                    row.abLine = "日报";
                }
            }
            if(row.fee){
                row.fee = Number(parseFloat(row.fee).toFixed(2));
            }
            if(row.recordingFee){
                row.recordingFee = Number(parseFloat(row.recordingFee).toFixed(2));
            }
            return row;
        }

        //下载报表
        function exportDataStatistics(){
            var formData = {};
                formData = {params:{"reportType" : $("#reportType").val(),"appId" : $("#appid").val()},startTime:$("#startTime").val(), endTime:$("#endTime").val(),ym:$("#month").val()};
            dataPage.downloadReport("<c:url value='/eccApp/exportDataStatistics'/>",formData);
        }
        //查询
        function searchCallRecord(){
            var formData = {};
                formData = {params:{"reportType" : $("#reportType").val(),"appId" : $("#appid").val()},startTime:$("#startTime").val(), endTime:$("#endTime").val(),ym:$("#month").val()};

            //提示开始时间不能大于结束时间
            if($("#startTime").val() != "" &&$("#endTime").val() != ""){
                var startTime = new Date($("#startTime").val().replace(/\-/g, '\/')).format("yyyy/MM/dd");
                var endTime = new Date($("#endTime").val().replace(/\-/g, '\/')).format("yyyy/MM/dd");
                if(startTime > endTime){
                    openMsgDialog("查询时间的开始时间不能大于结束时间！");
                    return;
                }
            }
            dataPage.reload(formData);
        }

        //关闭消息框
        function closeMsgDialog1(){
            $("#shadeWin").hide();
            $("#msgDialog").hide();
        }
    </script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none;"></div>
<!--遮罩end-->
<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('ecc','yzj','dataStatisticsEcc');</script>
        <!-- 左侧菜单 end -->
        <div class="main3">
            <div class="modal-box  phone_t_code" id="showMsgDialog" style="display: none;">
                <div class="modal_head">
                    <h4>消息提示</h4>
                    <div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                    <div class="app_delete_main" style="height: 50px; padding: 20px;">
                        <p class="app_delete_common"><i class="attention"></i><span id="alertMsg"></span></p>
                    </div>
                </div>
                <div class="mdl_foot" style="margin-left:220px;">
                    <button type="button" onclick="closeMsgDialog();" class="true">确&nbsp;&nbsp;定</button>
                </div>
            </div>

            <div class="container17" >
                <div class="msg">
                    <h3>数据统计</h3>
                    <jsp:include page="../common/message.jsp"/>

                </div>
                <div class="records_msg">

                    <div>
                        <form id="searchForm">
                            <div class="record_select" >
                                <div class="Open_interface_record re_span_common">

                                    <span class="check_font">报表类型：</span>
                                    <select id="reportType" name="reportType" style="width:120px;">
                                        <option value="1">日报</option>
                                        <option value="2">月报</option>
                                    </select>

								<span class="check_font call_left" id="date1">
                                	<b class="Bred"></b>
									<span class="check_font">起止时间：</span>

                                     <input type="hidden" id="currentDay" value="${currentDay}"/>
                                    <input type="hidden" id="beforeDay3" value="${beforeDay3}"/>

                           			<input id="startTime" name="startTime" type="text" class="input_style input-text Wdate" style="width:168px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'currentDay\')}',minDate:'#F{$dp.$D(\'currentDay\',{M:-3});}'})"/>
									<span class="check_font">至</span>
									<%--<input id="endTime" name="endTime" type="text" class="input_style input-text Wdate" style="width:168px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'#F{$dp.$D(\'endTime\'}'})"/>--%>

                                    <input id="endTime" name="endTime" type="text" class="input_style input-text Wdate" style="width:168px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'startTime\',{M:3});}'})"/>


                                </span>

								<span class="check_font call_left" id="date_m" style="display: none;">
                                	<b class="Bred"></b>
									<span class="check_font">查询月份：</span>
                                    <input type="hidden" id="maxDate" value="${currentMonth}"/>
                                    <input type="hidden" id="minDate" value="${beforeMonth12}"/>
                           			<input id="month" name="month" type="text" class="input_style input-text Wdate" style="width:168px;" onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'maxDate\')}', minDate:'#F{$dp.$D(\'minDate\')}'})"/>
								   <%--<input id="month" name="month" type="text" class="input_style input-text Wdate" style="width:168px;" onfocus="WdatePicker({dateFmt:'yyyy-MM', maxDate:'#F{$dp.$D(\\\'maxDate\\\',{M:-12});}'})"/>--%>


                                </span>

                                    <span class="check_font call_left">应用名称：</span>
                                    <select name="appId" id="appid" style="width:160px;">
                                        <option value="">全部</option>
                                    </select>
                                </div>
                                <div class="">
                                    <button type="button" class="md_btn f_right" style="margin-top: -5px" onclick="searchCallRecord()">查询</button>
                                </div>
                            </div>
                        </form>
                        <div class="record_down clear" style="margin-top: 0px">
                            <span class="open_down_billed	f_right" onclick="exportDataStatistics()"><i class="common_icon download_txt"></i>导出报表</span>
                        </div>
                        <!--查询弹层-->
                        <div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">
                            <div class="modal_head">
                                <h4>数据统计导出</h4>
                                <div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog1();" class="common_icon close_txt"></a></div>
                            </div>
                            <div class="modal-common_body mdl_pt_body">
                                <div class="test_success test_success_left">
                                    <i class="attention"></i>
                                    <span>您暂无数据</span>
                                </div>
                            </div>
                        </div>

                        <div class="clear">
                            <table class="table_style" id="dataStatisticsTable" style="width:99.8%">
                                <thead>
                                <tr>
                                    <th>报表类型</th>
                                    <th>时间</th>
                                    <th>应用名称</th>
                                    <th>通话类型</th>
                                    <th>通话总数</th>
                                    <th>接通率</th>
                                    <th>通话总时长(s)</th>
                                    <th>计费通话时长(min)</th>
                                    <th>平均通话时长(s)</th>
                                    <th>录音总时长(s)</th>
                                    <th>通话总费用(元)</th>
                                    <th>录音总费用(元)</th>
                                    <th>合计费用(元)</th>
                                </tr>
                                </thead>
                                <tbody id="dataStatisticsTbody">
                                </tbody>
                            </table>
                            <div class="f_right app_sorter"  id="DSPagination"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
