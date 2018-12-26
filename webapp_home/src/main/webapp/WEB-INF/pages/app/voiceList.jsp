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

<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->

<section>
    <div id="sec_main">

        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('kf','appMgr','voiceList');</script>
        <!-- 左侧菜单 end -->

        <div class="main3">

            <div class="container1" >
                <div class="msg">
                    <h3>铃声管理</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="ringDroid">
                    <!--应用列表-->
                    <div class="application">
                        <table class="table_style" id="apptable">
                            <thead>
                            <tr>
                                <th style="width:10.6%">应用名称</th>
                                <th style="width:15.6%">APP ID</th>
                                <th style="width:10.6%">提交时间</th>
                                <th style="width:10.6%">铃声大小</th>
                                <th style="width:10.6%">铃声名称</th>
                                <th style="width:10.6%">审核状态</th>
                                <th style="width:15.6%">操作</th>
                                <th style="width:15.6%">审核意见</th>
                            </tr>
                            </thead>
                            <tbody id="integralBody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="pagination"></div>
                    </div>
                </div>
                <input type="hidden"  id="appid" name="appid" value="${appid}"/>
                <!--铃声删除-->
                <div class="modal-box  phone_t_code" id="ring_box" style="display: none;">
                    <div class="modal_head">
                        <h4>应用删除</h4>
                        <div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt"  onclick="closeDiv('ring_box');"></a></div>
                    </div>
                    <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                        <div class="app_delete_main">
                            <p class="app_delete_common"><i class="attention"></i>该铃声一旦删除后，将不能恢复</p>
                            <p class="app_delete_common">确定要删除该铃声？</p>
                        </div>
                    </div>
                    <div class="mdl_foot" style="margin-left:125px;">
                        <button type="button" id="voiceDelButton" onclick="delete1();"  class="true">确&nbsp;&nbsp;认</button>
                        <button type="button" class="false" onclick="closeDiv('ring_box');">取&nbsp;&nbsp;消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script id="integralTemplate" type="text/x-jquery-tmpl">
	<tr class="date_hg">
		<td>\${app_name}</td>
		<td>\${appid}</td>
		<td>\${create_date}</td>
        <td>
		  {{if status==''}}\${voice_size}{{/if}}
		  {{if status!=''}}\${voice_size}M{{/if}}
		</td>
		<td>\${voice_name}</td>
		<td>
		{{if status=='02'}}
		<font color="#FF0000">审核不通过 </font>
		{{/if}}
		{{if status=='00'}}
		审核中...
		{{/if}}
		{{if status=='01'}}
		审核通过
		{{/if}}
		</td>
		<td>
		{{if status=='01'||status=='02'}}
				  <a href="<c:url value='/voiceMgr/updatePage'/>?appid=\${appid}">编辑</a>
				  <a href="<c:url value='/voiceMgr/view'/>?appid=\${appid}">查看</a>
				   <a href="#" onclick="voiceCanel('\${appid}')">删除</a>
				  <%--<button type="button" onclick="voiceCanel('\${appid}') style="font-weight:900;font-family:"Microsoft Yahei"">删除</button>--%>
		{{/if}}
		{{if status=='00'}}
				  <%--<font color="#939393">编辑 </font>--%>
				  <a href="<c:url value='/voiceMgr/view'/>?appid=\${appid}">查看</a>
				  <%--<font color="#939393">删除 </font>--%>
		{{/if}}
		{{if status==''}}
                    <a href="<c:url value='/voiceMgr/savePage'/>?appid=\${appid}">新建</a>
		{{/if}}
		</td>
		<td>\${common}</td>
	</tr>
</script>

<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>

<script>
    var appid;
    function voiceCanel(id){
        $("#shadeWin").show();
        $("#ring_box").show();
        appid = id;
    }

    function closeDiv(divid){
        $('#' + divid ).hide();
        $('#shadeWin').hide();
    }
    function delete1(){
        $.post("<c:url value='/voiceMgr/del'/>",{appid : appid},function(result){
            if(result.status == "00"){
                window.location.reload();
                closeDiv('ring_box');
            }
        });
    }

    // 铃声列表
    $("#apptable").page({
        url:"<c:url value='/voiceMgr/query'/>",
        pageSize:5,
        dataRowCallBack:dataRowCallBack
    });

    function dataRowCallBack(row) {
        // TODO:
        if (row.create_date) {
            row.create_date = o.formatDate(row.create_date, "yyyy-MM-dd ");
        }

        return row;
    }


</script>
</body>
</html>
