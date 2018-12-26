<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>云总机月账单查询</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="http://g.alicdn.com/sj/lib/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/calceval.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/My97DatePicker/WdatePicker.js"></script>
    <script id="monthEccBillTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>\${appName}</td>
			<td>\${ivrFee}</td>
			<td>\${directFee}</td>
			<td>\${localFee}</td>
			<td>\${nonlocalFee}</td>
			<td>\${inRecordingfee}</td>
			<td>\${outRecordingfee}</td>
			<td>\${costFee}</td>
			<td>\${sipnumRent}</td>
			<td>\${minConsume}</td>
			<td>\${totalFee}</td>
			 <td>
			 <a href="javascript:void(0);" onclick="downloadConsume('\${appid}','\${appName}','\${smonth}','\${totalFee}','\${ivrFee}',
			 '\${directFee}','\${localFee}','\${nonlocalFee}','\${costFee}','\${minConsume}')">下载</a>
             </td>
		</tr>
	</script>

    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/pagings.js"></script>
    <script type="text/javascript">
        var appInfoArray = {},dataPage;
        $(function(){
            //应用列表
            appInfoArray = getAppInfo("<c:url value='/eccMonthBill/getALLEccAppInfo'/>", "appid", "appName");

            dataPage = $("#monthEccBillTable").page({
                url:"<c:url value='/eccMonthBill/pageMonthEccBill'/>",
                pageSize:30,
                integralBody:"#monthEccBillTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#monthEccBillTemplate",// tmpl中的模版ID
                pagination:"#monthEccBillPagination", // 分页选项容器
                param:{"time":$("#time").val()},
                dataRowCallBack:dataRowCallBack
            });

            function dataRowCallBack(row,num) {
//                if(row.ivrFee){
                    row.ivrFee = parseFloat(row.ivrFee).toFixed(2);
//                }
//                if(row.directFee){
                    row.directFee = parseFloat(row.directFee).toFixed(2);
//                }
//                if(row.localFee){
                    row.localFee = parseFloat(row.localFee).toFixed(2);
//                }
//                if(row.nonlocalFee){
                    row.nonlocalFee = parseFloat(row.nonlocalFee).toFixed(2);
//                }
//                if(row.inRecordingfee){
                    row.inRecordingfee = parseFloat(row.inRecordingfee).toFixed(2);
//                }
//                if(row.outRecordingfee){
                    row.outRecordingfee = parseFloat(row.outRecordingfee).toFixed(2);
//                }
//                if(row.costFee){
                    row.costFee = parseFloat(row.costFee).toFixed(2);
//                }
//                if(row.sipnumRent){
                    row.sipnumRent = parseFloat(row.sipnumRent).toFixed(2);
//                }
//                if(row.minConsume){
                    row.minConsume = parseFloat(row.minConsume).toFixed(2);
//                }

                var ivrFee_=0;
                var directFee_=0;
                var localFee_=0;
                var nonlocalFee_=0;
                var inRecordingfee_=0;
                var outRecordingfee_=0;
                var costFee_=0;
                var sipnumRent_=0;
                var minConsume_=0;
                if(row.ivrFee){
                    ivrFee_ = row.ivrFee;
                }

                if(row.directFee){
                    directFee_ = row.directFee;
                }

                if(row.localFee){
                    localFee_ = row.localFee;
                }
                if(row.nonlocalFee){
                    nonlocalFee_ = row.nonlocalFee;
                }

                if(row.inRecordingfee){
                    inRecordingfee_ = row.inRecordingfee;
                }

                if(row.outRecordingfee){
                    outRecordingfee_ = row.outRecordingfee;
                }
                if(row.costFee){
                    costFee_ = row.costFee;
                }

                if(row.sipnumRent){
                    sipnumRent_ = row.sipnumRent;
                }

                if(row.minConsume){
                    minConsume_ = row.minConsume;
                }


                row.totalFee = (parseFloat(ivrFee_)+ parseFloat(directFee_)+ parseFloat(localFee_)+ parseFloat(nonlocalFee_)+parseFloat(inRecordingfee_)+
                parseFloat(outRecordingfee_)+parseFloat(costFee_)+parseFloat(sipnumRent_)+parseFloat(minConsume_)).toFixed(2);
                return row;
            }
        });

        //查询
        function search(){
            var formData = {};

            $("#time").val($("#ym").val());

            console.log($("#time").val());

            formData = {params:{"appId" : $("#appid").val(),"time":$("#ym").val()}};
            if($("#ym").val()==""){
                return;
            }else{
                dataPage.reload(formData);
            }

        }
        // 导出报表
        function downloadEccMonthBillReport() {
            window.open("<c:url value='/eccMonthBill/downloadReport'/>?appId=" + $("#appid").val() + "&time=" + $("#ym").val());
        }

        //下载月单详情
        function downloadConsume(appid,appName,smonth,totalFee,ivrFee,directFee,localFee,nonlocalFee,costFee,minConsume) {

            var formData = {};

            var callinFee = (parseFloat(ivrFee) + parseFloat(directFee)).toFixed(2);
            var calloutFee = (parseFloat(localFee) + parseFloat(nonlocalFee)).toFixed(2);

            formData = {params:{"smonth" : smonth,"appId" : appid,"appName" : appName,"totalFee" : totalFee,"callinFee" : callinFee,"calloutFee" : calloutFee,"costFee" : costFee,"minConsume":minConsume}};

            dataPage.downloadReport("<c:url value='/eccMonthBill/exportEccMonthBillRecord'/>",formData);

        }
    </script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none;"></div>
<!--遮罩end-->

<jsp:include page="../common/controlheader.jsp"/>

<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('ecc','yzj','eccMonthBill');</script>
        <!-- 左侧菜单 end -->

        <div class="main3">
            <div class="container7">
                <div class="msg">
                    <h3>月账单查询</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="demand">
                    <div>
                        <input type="hidden" id="time" value="${ym}"/>
                        <input type="hidden" id="maxDate2" value="${ym2}"/>
                        <input type="hidden" id="minDate2" value="${beforcurrentDate}"/>
                        <span class="check_font">应用名称</span>
                        <select name="appId" id="appid" style="width:200px;">
                            <option value="">全部</option>
                        </select>
                        <span class="check_font call_left">查询时间</span>
                        <input type="text" class="Wdate" id="ym" onfocus="WdatePicker({dateFmt:'yyyy年M月',maxDate:'#F{$dp.$D(\'maxDate2\')}',minDate:'#F{$dp.$D(\'minDate2\')}',readOnly:true})" value="${ym}" style="width:184px;height:28px;"/>
                        <button type="button" class="md_btn f_right" style="margin-top:-5px;" onclick="search()">查询</button>
                    </div>
                </div>
                <div class="date clear">
                    <div class="record_down f_right" style="margin-top:8px;">
                        <a href="javascript:void(0);" onclick="downloadEccMonthBillReport();" class="open_down_billed f_right"><i class="common_icon download_txt"></i>导出账单</a>
                    </div>
                    <table class="table_style" id="monthEccBillTable">
                        <thead>
                        <tr>
                            <th rowspan="2">应用名称</th>
                            <th colspan="2">呼入(元)</th>
                            <th colspan="2">呼出(元)</th>
                            <th colspan="2">录音(元)</th>
                            <th colspan="2">号码月租(元)</th>
                            <th>最低消费(元)</th>
                            <th rowspan="2">费用总计(元)</th>
                            <th rowspan="2">账单详情</th>
                        </tr>
                        <tr>
                            <th>呼入总机</th>
                            <th>呼入直拨</th>
                            <th>市话</th>
                            <th>长途</th>
                            <th>呼入录音</th>
                            <th>呼出录音</th>
                            <th>总机月租</th>
                            <th>SIP号码月租</th>
                            <th>补扣最低消费</th>
                        </tr>
                        </thead>
                        <tbody id="monthEccBillTbody">
                        </tbody>
                    </table>
                    <div class="turnPage f_right"  id="monthEccBillPagination"></div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
