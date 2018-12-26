<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/7/12
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>通话记录</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <script id="sipCallListTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${subid}</td>
            <td>\${aTelno}</td>
            <td>\${bTelno}</td>
            <td>\${qssj}</td>
            <td>\${jssj}</td>
            <td>\${connectSucc}</td>
            <td>\${fee}</td>
        </tr>
    </script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>

    <script type="text/javascript">
        //日通话记录下载参数
        //feeid
        var feeid = '${ sessionScope.userInfo.feeid}'
        //业务路径
        var busPath = "/sip/";
        var date = new Date(),subInfoArray = {};
        date.setMonth(date.getMonth()-3);
        var beforeMonth = date.format("yyyy-MM-dd");
        var closureTime = {
            elem: '#closureTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            max: laydate.now(), //设定最大日期为当前日期
            min: beforeMonth, //最小日期
            istime: true,
            istoday: true,
            choose: function(data){
                //日期限制跨度30天
                var date = new Date(data);
                date.setDate(date.getDate()+30);
                var strAfterDay = date.format("yyyy-MM-dd");
                if(new Date(strAfterDay) >= new Date(laydate.now("yyyy-MM-dd"))){
                    closureTime1.max = laydate.now("yyyy-MM-dd");
                }else{
                    closureTime1.max = strAfterDay;
                }
                closureTime1.min = data; //开始日选好后，重置结束日的最小日期
                closureTime1.start = data; //将结束日的初始值设定为开始日
            }
        };

        var closureTime1 = {
            elem: '#closureTime1',
            format: 'YYYY-MM-DD hh:mm:ss',
            max: laydate.now(), //设定最大日期为当前日期
            min: beforeMonth, //最小日期
            istime: true,
            istoday: true,
            choose: function(data){
                closureTime.max = data; //结束日选好后，重置开始日的最大日期
                closureTime.start = data;
                //日期限制跨度30天
                var date = new Date(data);
                date.setDate(date.getDate()-30);
                var strBeforeDay = date.format("yyyy-MM-dd");
                if(new Date(strBeforeDay) <= new Date(beforeMonth)){
                    closureTime.min = beforeMonth;
                }else{
                    closureTime.min = strBeforeDay;
                }
            }
        };

        $(function(){
            laydate(closureTime);
            laydate(closureTime1);
            //sip应用列表
            subInfoArray = getAppInfo("<c:url value='/sipTrafficStatistics/getSipRelayInfo'/>","subid","subName");
            $("#sipCallListTable").page({
                url:"<c:url value='/sipCallRecord/getSipCallRecordList'/>",
                pageSize:30,
                integralBody:"#sipCallListTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#sipCallListTemplate",// tmpl中的模版ID
                pagination:"#sipCallListPagination", // 分页选项容器
                dataRowCallBack:dataRowCallBack
            });
            getMonthFile();
            downloadReport('${currTime}', 'dataModel',busPath+feeid,'<c:url value="/"/>');
        });

        function getMonthFile(){
            $.ajax({
                type:"post",
                url:"<c:url value="/monthFile/getStafMonthFileList"/>",
                dataType:"json",
                async:false,
                data:{"stype":"03"},
                success : function(data) {
                    if(data.length > 0) {
                        var month = monthArray(new Date().format("yyyy年MM月"));
                        $(".down_common").each(function (i) {
                            var elements = "";
                            $(data).each(function () {
                                if (month[i] == o.formatDate($(this)[0].sdate, "yyyy年MM月")) {
                                    elements = "<span>" + month[i] + "</span><a href='<c:url value='/monthFile/monthFileDownload'/>?file=" + $(this)[0].fname + "'>下载</a>";
                                }
                            })
                            $(this).html(elements);
                        })
                    }else{
                        $(".download_billed").html("<div class='download_msg'><i class='attention'></i><span>您暂无历史话单<span></div>");
                    }
                },
                error:function(){
                    console.log("数据请求失败！");
                }
            });
        }

        function dataRowCallBack(row,num) {
            //序号列
            row.num = num;
            if(row.closureTime){
                row.closureTime =  o.formatDate(row.closureTime, "yyyy-MM-dd");
            }
            if(row.subid){
                row.subName = subInfoArray[row.subid];
            }
            if (row.qssj) {
                row.qssj = o.formatDate(row.qssj, "yyyy-MM-dd hh:mm:ss");
            }
            if (row.jssj) {
                row.jssj = o.formatDate(row.jssj, "yyyy-MM-dd hh:mm:ss");
            }
            if (row.thsc) {
                row.thsc = row.thsc+"s";
            }
            //接通状态
            if(row.connectSucc == 1){
                row.connectSucc = "接通";
            }else if(row.connectSucc == 0){
                row.connectSucc = "未接通";
            }
            return row;
        }


        //查询
        function searchSipCallRecord(){
            var formData = {};
            $("#searchForm").find(":input[id]").each(function(i){
                formData[$(this).attr("id")] = $.trim($(this).val());
            });
            //提示开始时间不能大于结束时间
            if(formData['closureTime'] != "" && formData['closureTime1'] != ""){
                var closureTime = new Date(formData['closureTime'].replace(/\-/g, '\/'));
                var closureTime1 = new Date(formData['closureTime1'].replace(/\-/g, '\/'));
                if(closureTime > closureTime1){
                    openMsgDialog("查询时间的开始时间不能大于结束时间！");
                    return;
                }
            }else{
                openMsgDialog("请输入查询时间！");
                return;
            }
           /* if(formData["subid"] == ""){
                openMsgDialog("请选择应用名称！");
                return;
            }*/
            $("#sipCallListTable").reload(formData);
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
        <script type="text/javascript">showSubMenu('sp','sipMgr','sipCallRecord');</script>
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

            <div class="container17" >
                <div class="msg">
                    <h3>通话记录</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="records_msg">
                    <div class="recordNote">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1.时间区间跨度最大支持30天，保留12个月的历史数据。</p>
                    </div>
                    <form id="searchForm">
                        <div class="record_select">
                            <div class="Open_interface_record re_span_common">
                                <span class="open_width">
                                    <b class="Bred">*</b>
                                    <span class="check_font">起止时间：</span>
                                    <input id="closureTime" name="closureTime" type="text" class="sm_input_style"/>
                                    <span class="check_font">至</span>
                                    <input id="closureTime1" name="closureTime1" type="text" class="sm_input_style"/>
                                </span>
                                <span class="check_font call_left">主叫号码：</span>
                                <input type="text" id="aTelno" name="aTelno" class="input_style"/>
                                <span class="check_font call_left">被叫号码：</span>
                                <input type="text" id="bTelno" name="bTelno" class="input_style "/>
                            </div>

                            <div class="Open_interface_record re_span_common">
                                <span class="open_width"></span>
                                <span class="check_font call_left" style="margin-left: 2px;">子账号名称：</span>
                                <select id="subid" name="subid" style="width:182px;">
                                    <option selected value="">全部</option>
                                </select>
                                <span class="check_font call_left" style="margin-left: 13px;">接通状态：</span>
                                <select id="connectSucc" name="connectSucc" style="width:182px;">
                                    <option value="">全部</option>
                                    <option value="0">未接通</option>
                                    <option value="1">接通</option>
                                </select>

                            </div>

                            <button type="button" class="md_btn f_right" onclick="searchSipCallRecord();" style="margin-top: -5px">查询</button>
                        </div>
                    </form>
                    <div class="record_down f_right" style="margin-top: 13px">
                        <span class="open_down_billed" onclick="showExportCallRecord();"><i class="common_icon download_txt"></i>导出历史话单</span>
                        <span class="open_down_billed marLeft15" onclick="showExportCallCurMonthRecord();">导出本月话单</span>
                    </div>
                    <div class="clear">
                        <table class="table_style record_table re_a_color clear" id="sipCallListTable">
                            <thead>
                            <tr>
                                <th style="width: 30px;">序号</th>
                                <th style="width: 150px;">subId</th>
                                <th style="width: 80px;">主叫号码</th>
                                <th style="width: 80px;">被叫号码</th>
                                <th style="width: 50px;">开始时间</th>
                                <th style="width: 50px;">结束时间</th>
                                <th style="width: 60px;">通话状态</th>
                                <th style="width: 70px;">通话费用</th>
                            </tr>
                            </thead>
                            <tbody id="sipCallListTbody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="sipCallListPagination"></div>
                    </div>
                </div>
                <!--下载历史话单弹层-->
                <div class="modal-box  phone_t_code" id="re_download_billed" style="display: none">
                    <div class="modal_head">
                        <h4>下载历史话单</h4>
                        <div class="p_right">
                            <a href="javascript:void(0);" class="common_icon close_txt" onclick="closeExportCallRecord();"></a>
                        </div>
                    </div>
                    <div class="modal-common_body mdl_pt_body">
                        <div class="download_billed">
                            <div class="download_time_left f_left">
                                <div class="down_common"><span>2015年01月</span></div>
                                <div class="down_common"><span>2015年02月</span></div>
                                <div class="down_common"><span>2015年03月</span></div>
                                <div class="down_common"><span>2015年04月</span></div>
                                <div class="down_common"><span>2015年05月</span></div>
                                <div class="down_common"><span>2015年06月</span></div>
                            </div>
                            <div class="download_time_right f_right">
                                <div class="down_common"><span>2015年07月</span></div>
                                <div class="down_common"><span>2015年08月</span></div>
                                <div class="down_common"><span>2015年09月</span></div>
                                <div class="down_common"><span>2015年10月</span></div>
                                <div class="down_common"><span>2015年11月</span></div>
                                <div class="down_common"><span>2015年12月</span></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-box  phone_t_code" id="dataModel" style="height:360px;display: none" >

                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
