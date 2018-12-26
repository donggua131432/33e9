<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none;"></div>
<!--遮罩end-->
<div id="toshow"></div>
<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->

<section>
    <div id="sec_main">

        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('kf','appMgr','numList');</script>
        <!-- 左侧菜单 end -->

        <div class="main3">

            <div class="container1" >
                <div class="msg">
                    <h3>号码展示</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="application_msg">
                    <div class="app_select" id="searchForm">
                        <span class="check_font">应用名称 : </span>
                        <select id="appid" name="appid">
                            <option selected value="">全部</option>
                        </select>
                        <span class="check_font pr_left">城市 :</span>
                            <select id="id" name="id">
                                <option selected value="">全部</option>
                            </select>
                        <span class="check_font pr_left">号码:</span>
                        <input type="text" id="number" name="number" size="18" maxlength="30" class="l_input_style" style="width:220px;"/>
                        <button type="button" class="md_btn f_right" style="margin-top:-6px;" onclick="search();">查询</button>
                    </div>

                    <!--应用列表-->
                    <div class="application">
                        <div class="Export_history_billed">
                            <span  onclick="downloadReport('');" class="open_down_billed f_right"><i class="common_icon download_txt"></i>导出列表</span>
                        </div>
                        <table class="table_style" id="maskNum">
                            <thead>
                            <tr>
                                <th style="width:20%">应用名称</th>
                                <th style="width:30%">APP ID</th>
                                <th style="width:15%">城市</th>
                                <th style="width:15%">区号</th>
                                <th style="width:20%">号码</th>
                            </tr>
                            </thead>

                            <tbody id="czjllog">

                            </tbody>

                        </table>
                        <div id="pagination" class="f_right app_sorter"></div>
                    </div>
                </div>
                <%--<input type="hidden"  id="appid" name="appid" value="${appid}"/>--%>

            </div>
        </div>
    </div>
</section>
<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
<!-- 充值记录 -->
<script id="czlog" type="text/x-jquery-tmpl">
	<tr class="date_hg">
		<td>\${app_name}</td>
		<td>\${appid}</td>
		<td>\${city}</td>
		<td>\${area_code}</td>
		<td>\${number}</td>
	</tr>
</script>

<script type="text/javascript">
    var appInfoArray = {};
    var cityInfoArray = {};
    var search_param = {};
    $(function(){
    appInfoArray = getAppInfo("<c:url value='/appMgr/getALLAppInfo'/>","appid","appName");

    cityInfoArray = getAppInfo("<c:url value='/seNumMgr/getCityInfo'/>","id","city");
   });

    //查询号码记录
    var table =
    $("#maskNum").page({
        url:"<c:url value='/seNumMgr/pageMaskNum'/>",
        integralTemplate : "#czlog",
        integralBody : "#czjllog",
        dataRowCallBack : dataRowCallBack
    });
    function search() {
        var formData = {};
        $("#searchForm").find(":input[id]").each(function(i){
            formData[$(this).attr("id")] = $(this).val().trim();
        });
        table.reload({ "params[number]" : $("#number").val(),"params[appid]" : $("#appid").val(),"params[id]" : $("#id").val()});
    }

    function dataRowCallBack(row) {
        return row;
    }


    // 导出报表
    function downloadReport(type) {

        $.fn.downloadReport("<c:url value='/seNumMgr/downloadReport'/>");
    }

</script>

</body>
</html>
