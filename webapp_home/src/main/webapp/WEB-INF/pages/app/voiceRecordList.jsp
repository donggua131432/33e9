<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/9/21
  Time: 16:28
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
    <script id="callListTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${appid}</td>
            <td>\${requestId}</td>
            <td>\${templateId}</td>
            <td>\${bj}</td>
            <td>\${display}</td>
            <td>\${dtmf}</td>
            <td>\${qssj}</td>
		    <td>\${jssj}</td>
            <td>\${connectStatus}</td>
            <td>\${fee}</td>
        </tr>
    </script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>

    <script type="text/javascript">
        var appInfoArray = {};
        //日通话记录下载参数
        //feeid
        var feeid = '${ sessionScope.userInfo.feeid}'
        //业务路径
        var busPath = "/voice/";
        $(function(){
            //通话记录列表
            appInfoArray = getAppInfo("<c:url value='/appMgr/getALLAppInfo'/>","appid","appName");
            $("#callListTable").page({
                url:"<c:url value='/voiceRecord/getCallRecordList'/>",
                pageSize:30,
                integralBody:"#callListTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#callListTemplate",// tmpl中的模版ID
                pagination:"#callListPagination", // 分页选项容器
                dataRowCallBack:dataRowCallBack
            });

            getMonthFile();
        });


        $(function(){
            laydate(callTime);
            laydate(callTime1);
            downloadReport('${currTime}', 'dataModel',busPath+feeid,'<c:url value="/"/>');
        });


        var date = new Date();
        date.setMonth(date.getMonth()-3);
        var beforThreeMonth = date.format("yyyy-MM-dd");

        var callTime = {
            elem: '#callTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            max: laydate.now(), //设定最大日期为当前日期
            min: beforThreeMonth, //最小日期
            istime: true,
            istoday: true,
            choose: function(datas){
                callTime1.min = datas; //开始日选好后，重置结束日的最小日期
                callTime1.start = datas //将结束日的初始值设定为开始日
            }
        };

        var callTime1 = {
            elem: '#callTime1',
            format: 'YYYY-MM-DD hh:mm:ss',
            max: laydate.now(), //设定最大日期为当前日期
            min: beforThreeMonth, //最小日期
            istime: true,
            istoday: true,
            choose: function(datas){
                callTime.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };

        function getMonthFile(){
            $.ajax({
                type:"post",
                url:"<c:url value="/monthFile/getStafMonthFileList"/>",
                dataType:"json",
                async:false,
                data:{"stype":"05"},
                success : function(data) {
                    if(data.length > 0) {
                        var month = monthArray("${currentDate}");
                        $(".down_common").each(function (i) {
                            var elements = "";
                            //var elements = "<span>"+month[i]+"</span><span class='disableCss'>下载</span>";
                            $(data).each(function () {
                                if (month[i] == o.formatDate($(this)[0].sdate, "yyyy年MM月")) {
                                    elements = "<span>" + month[i] + "</span><a href='<c:url value='/monthFile/monthFileDownload'/>?file=" + $(this)[0].fname + "'>下载</a>";
                                }
                            });
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

            if (row.qssj) {
                row.qssj = o.formatDate(row.qssj, "yyyy-MM-dd hh:mm:ss");
            }
            if (row.jssj) {
                row.jssj = o.formatDate(row.jssj, "yyyy-MM-dd hh:mm:ss");
            }

            //接通状态
            if(row.connectStatus == true){
                row.connectStatus = "接通";
            }else if(row.connectStatus == false){
                row.connectStatus = "未接通";
            }

            if (row.thsc) {
                row.thsc = row.thsc+"s";
            }

            return row;
        }


        //查询
        function searchCallRecord(){
            var formData = {};
            $("#searchForm").find(":input[id]").each(function(i){
                formData[$(this).attr("id")] = $(this).val().trim();
            });

            //提示开始时间不能大于结束时间
            if(formData['callTime'] != "" && formData['callTime1'] != ""){
                var callTime = new Date(formData['callTime'].replace(/\-/g, '\/'));
                var callTime1 = new Date(formData['callTime1'].replace(/\-/g, '\/'));
                if(callTime > callTime1){
                    openMsgDialog("查询时间的开始时间不能大于结束时间！");
                    return;
                }
            }else{
                openMsgDialog("请输入查询时间！");
                return;
            }

            if(formData["appid"] == ""){
                openMsgDialog("请选择应用名称！");
                return;
            }

            console.log(formData);

            $("#callListTable").reload(formData);
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
        <script type="text/javascript">showSubMenu('kf','appMgr','speechRecord');</script>
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
                    <div class="OpenRecordNote">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1.通话记录仅提供近3个月的数据查询;</p>
                        <p>2.话单导出仅支持近12个月的数据导出。</p>
                    </div>
                    <form id="searchForm">
                        <div class="record_select">
                            <div class="Open_interface_record re_span_common">
                                <span class="open_width"style="width:400px;">
                                    <b class="Bred">*</b>
                                    <span class="check_font">起止时间：</span>
                                    <input type="text" id="callTime" name="callTime" class="sm_input_style" style="width:140px;"/>
                                    <span class="check_font">至</span>
                                    <input type="text" id="callTime1" name="callTime1" class="sm_input_style" style="width:136px;"/>
                                </span>

                                <span class="check_font call_left"><b class="Bred">*</b>应用名称：</span>
                                <select id="appid" name="appid" style="width: 150px" >
                                    <option value="">全部</option>
                                </select>

                                <span class="check_font call_left">呼叫状态：</span>
                                <select id="connectStatus1" name="connectStatus1" class="On_state" style="width:149px;">
                                    <option value="">全部</option>
                                    <option value="true">接通</option>
                                    <option value="false">未接通</option>
                                </select>

                                <span class="check_font call_left">模板名称：</span>
                                <input type="text" class="input_style" id="name" name="name" style="width:154px;"/>
                            </div>
                            <div class="Open_interface_record re_span_common">

                                <%--<span class="check_font">APP&nbsp;ID：</span>--%>
                                <%--<input type="text" class="input_style" id="appid2" name="appid" style="margin-left:6px;width:305px;"/>--%>
                                <span class="check_font ">&nbsp;&nbsp;被叫号码：</span>
                                <input type="text" class="input_style" id="bj"  name="bj" style="margin-right:5px;width:300px;"/>
                                <span class="check_font call_left"> 外显号码：</span>
                                <input type="text" class="calling_time" id="display" name="display" style="margin-right:6px;width:159px;"/>
                                <span class="check_font call_left">模板&nbsp;ID：</span>
                                <input type="text" id="templateId" name="templateId" class="calling_time" style="margin-left:6px;width:154px;"/>
                            </div>
                        </div>
                    </form>


                    <div class="clear">

                        <!-- 通话记录列表 -->
                        <button type="button" onclick="searchCallRecord();" class="md_btn f_right" style="margin:-10px 0 5px 0;">查询</button>
                        <div class="record_down f_left">
                            <span class="open_down_billed" onclick="showExportCallRecord();"><i class="common_icon download_txt"></i>导出历史话单</span>
                            <span class="open_down_billed marLeft15" onclick="showExportCallCurMonthRecord();">导出本月话单</span>
                        </div>

                        <table class="table_style clear" id="callListTable">
                            <thead>
                                <th style="width: 30px;">序号</th>
                                <th style="width: 200px;">Appid</th>
                                <th style="width: 200px;">requestId</th>
                                <th style="width: 40px;">模板ID</th>
                                <th style="width: 80px;">被叫号码</th>
                                <th style="width: 80px;">外显号码</th>
                                <th style="width: 65px;">DTMF码</th>
                                <th style="width: 90px;">开始时间</th>
                                <th style="width: 90px;">结束时间</th>
                                <th style="width: 40px;">通话状态</th>
                                <th style="width: 40px;">通话费用</th>
                            </thead>
                            <tbody id="callListTbody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="callListPagination"></div>
                    </div>
                </div>
                <!--下载历史话单弹层-->
                <div class="modal-box  phone_t_code" id="re_download_billed" style="display: none;">
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
