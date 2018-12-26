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
    <title>应用详情</title>
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
    <script id="sipAppNumberTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${number}</td>
            <td>\${create_time}</td>
        </tr>
    </script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#sipAppNumberTable").page({
                url:"<c:url value='/sipAppDetail/getSipAppNumberPool'/>",
                pageSize:30,
                integralBody:"#sipAppNumberTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#sipAppNumberTemplate",// tmpl中的模版ID
                pagination:"#sipAppNumberPagination", // 分页选项容器
                param:{"appid":"${appInfo.appid}"},
                dataRowCallBack:dataRowCallBack
            });
        });

        function dataRowCallBack(row,num){
            //序号列
            row.num = num;
            if (row.create_time) {
                row.create_time = o.formatDate(row.create_time, "yyyy-MM-dd hh:mm:ss");
            }
            return row;
        }

        //查询
        function searchSipAppNumberPool(){
            var formData = {};
            $("#searchForm").find(":input[id]").each(function(){
                formData["params["+$(this).attr('id')+"]"] = $.trim($(this).val());
            });
            $("#sipAppNumberTable").reload(formData);
        }

        //导出记录
        function downloadSipAppNumPool(){
            var formData = "";
            formData += "&params[appid]=${appInfo.appid}";
            $('#searchForm').find(':input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $.trim($(this).val());
            });
            formData = formData.substring(1);
            window.open("<c:url value='/sipAppDetail/downloadSipAppNumberPool'/>?" + formData);
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
        <script type="text/javascript">showSubMenu('sp','sipMgr','sipAppDetail');</script>
        <!-- 左侧菜单 end -->

        <div class="main3">
            <div class="container" >
                <div class="msg">
                    <h3>应用详情</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="sip_appDetail">
                    <div class="create_app_common">
                        <label class="create_app_title_left" >APP&nbsp;ID：</label>
                        <span  class="ringCommon">${appInfo.appid}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >应用名称:</label>
                        <span  class="ringCommon">${appInfo.appName}</span>
                    </div>
                </div>
                <!--应用列表-->
                <div class="application_msg clear">
                    <div>
                        <p class="application">全局号码池列表</p>
                        <form id="searchForm">
                            <div>
                                <span class="check_font">号码：</span>
                                <input type="text" id="number" name="number" class="input_style"/>
                                <button type="button" class="md_btn call_left" onclick="searchSipAppNumberPool();">查询</button>
                            </div>
                        </form>
                    </div>
                    <div class="record_down f_right">
                        <span class="open_down_billed" onclick="downloadSipAppNumPool();"><i class="common_icon download_txt"></i>导出记录</span>
                    </div>
                    <table class="table_style" id="sipAppNumberTable">
                        <thead>
                            <tr>
                                <th style="width:15%">序号</th>
                                <th>号码</th>
                                <th>创建时间</th>
                            </tr>
                        </thead>
                        <tbody id="sipAppNumberTbody">

                        </tbody>
                    </table>
                    <div class="f_right app_sorter"  id="sipAppNumberPagination"></div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
