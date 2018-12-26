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
    <title>话务统计</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/calceval.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <script id="sipTrafficTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
			<td>\${statday}</td>
			<td>\${subName}</td>
			<td>\${subid}</td>
			<td>\${callcnt}</td>
			<td>\${callTimeAvg}</td>
			<td>\${connectRate}</td>
			<td>\${answerRate}</td>
			<td>\${fee}</td>
        </tr>
    </script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script type="text/javascript">
        var subInfoArray = {},
         statday = {
            elem: '#statday',
            format: 'YYYY-MM-DD',
            max: laydate.now(), //设定最大日期为当前日期
            istime: true,
            istoday: false,
            isclear: true,  //是否显示清空按钮
            choose: function(datas){
                statday1.min = datas; //开始日选好后，重置结束日的最小日期
                statday1.start = datas //将结束日的初始值设定为开始日
            }
        },
        statday1 = {
            elem: '#statday1',
            format: 'YYYY-MM-DD',
            max: laydate.now(), //设定最大日期为当前日期
            istime: true,
            istoday: true,
            isclear: true,  //是否显示清空按钮
            choose: function(datas){
                statday.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };


        $(function(){
            //sip应用列表
            subInfoArray = getAppInfo("<c:url value='/sipTrafficStatistics/getSipRelayInfo'/>","subid","subName");
            //日期组件初始化渲染
            laydate(statday);
            laydate(statday1);
            $("#statday").val(laydate.now(-1));
            $("#statday1").val(laydate.now(-1));
            //话务统计列表
            $("#sipTrafficTable").page({
                url:"<c:url value='/sipTrafficStatistics/getSipTrafficListPage'/>",
                pageSize:30,
                integralBody:"#sipTrafficTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#sipTrafficTemplate",// tmpl中的模版ID
                pagination:"#sipTrafficPagination", // 分页选项容器
                param:{"statday":laydate.now(-1),"statday1":laydate.now(-1)},
                dataRowCallBack:dataRowCallBack
            });
        });

        function dataRowCallBack(row,num) {
            //序号列
            row.num = num;
            if (row.statday) {
                row.statday = o.formatDate(row.statday, "yyyy-MM-dd");
            }

            if(row.subid){
                row.subName = subInfoArray[row.subid];
            }

            //平均通话时长(总通话时长/呼叫成功次数)
            if(row.thscsum!=undefined && row.succcnt!=undefined){
                if(row.succcnt != 0){
                    row.callTimeAvg = parseFloat($.calcEval("("+row.thscsum+"/"+row.succcnt+")")).toFixed(2);
                }else{
                    row.callTimeAvg = parseFloat(0).toFixed(2);
                }
            }else{
                row.callTimeAvg = parseFloat(0).toFixed(2);
            }

            //接通率(呼叫成功次数/呼叫总数)
            if(row.succcnt!=undefined && row.callcnt!=undefined){
                if(row.callcnt != 0){
                    row.connectRate = parseFloat($.calcEval("("+row.succcnt+"/"+row.callcnt+")")*100).toFixed(2);
                }else{
                    row.connectRate = parseFloat(0).toFixed(2);
                }
            }else{
                row.connectRate = parseFloat(0).toFixed(2);
            }

            //应答率(应答数/呼叫成功次数)
            if(row.answercnt!=undefined && row.succcnt!=undefined){
                if(row.succcnt != 0){
                    row.answerRate = parseFloat($.calcEval("("+row.answercnt+"/"+row.succcnt+")")*100).toFixed(2);
                }else{
                    row.answerRate = parseFloat(0).toFixed(2);
                }
            }else{
                row.answerRate = parseFloat(0).toFixed(2);
            }
            return row;
        }


        //查询
        function searchSipTraffic(){
            var formData = {}, params = {};
            $("#searchForm").find(":input[id]").each(function(){
                params[$(this).attr('id')] = $.trim($(this).val());
            });
            formData["params"] = params;
            //提示开始时间不能大于结束时间
            if(formData.params.statday != "" && formData.params.statday1 != ""){
                var statday = new Date(formData.params.statday.replace(/\-/g, '\/'));
                var statday1 = new Date(formData.params.statday1.replace(/\-/g, '\/'));
                if(statday > statday1){
                    openMsgDialog("查询时间的开始时间不能大于结束时间！");
                    return;
                }
            }

            if(formData.params.statday == ""){
                openMsgDialog("查询时间的开始时间不能为空！");
                return;
            }

            if(formData.params.statday1 == ""){
                openMsgDialog("查询时间的结束时间不能为空！");
                return;
            }

            $("#sipTrafficTable").reload(formData);
        }

        /** 导出记录 **/
        function downloadSipTraffic() {
            var formData = "";
            $('#searchForm').find(':input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $.trim($(this).val());
            });
            formData = formData.substring(1);
            window.open("<c:url value='/sipTrafficStatistics/downloadSipTrafficList'/>?"+formData);
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
        <script type="text/javascript">showSubMenu('sp','sipMgr','sipTrafficStatistics');</script>
        <!-- 左侧菜单 end -->


        <div class="main3">
            <!--消息显示-->
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
            <div class="container" >
                <div class="msg">
                    <h3>话务统计</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="application_msg">
                    <form id="searchForm">
                        <div class="app_select">
                            <span class="open_width">
                                <b class="Bred">*</b>
                                <span class="check_font">时间：</span>
                                <input type="text" readonly id="statday" name="statday" class="sm_input_style"/>
                                <span class="check_font">至</span>
                                <input type="text" readonly id="statday1" name="statday1" class="sm_input_style"/>
                            </span>
                            <span class="check_font call_left">子账号名称：</span>
                            <select id="subid" name="subid">
                                <option selected value="">全部</option>
                            </select>
                            <button type="button" class="md_btn f_right" onclick="searchSipTraffic();">查询</button>
                        </div>
                    </form>
                    <!--应用列表-->
                    <div class="application clear">
                        <div class="record_down f_right">
                            <span class="open_down_billed" onclick="downloadSipTraffic();"><i class="common_icon download_txt"></i>导出记录</span>
                        </div>
                        <table class="table_style record_table" id="sipTrafficTable">
                            <thead>
                            <tr>
                                <th style="width:5%">序号</th>
                                <th style="width:9%">日期</th>
                                <th style="width:15%">子账号名称</th>
                                <th style="width:27%">子账号&nbsp;ID</th>
                                <th style="width:7%">呼叫总量</th>
                                <th style="width:10%">平均通话时长</th>
                                <th style="width:9%">接通率(%)</th>
                                <th style="width:9%">应答率(%)</th>
                                <th style="width:9%">通话费用(元)</th>
                            </tr>
                            </thead>
                            <tbody id="sipTrafficTbody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="sipTrafficPagination"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
