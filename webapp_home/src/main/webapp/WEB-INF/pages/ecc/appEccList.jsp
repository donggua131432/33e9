<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <script type="text/javascript">showSubMenu('ecc','yzj','appListEcc');</script>
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
                        <p class="app_delete_common"><i class="attention"></i><span id="alertMsg">此应用有号码正在使用，无法删除！</span></p>
                    </div>
                </div>
                <div class="mdl_foot" style="margin-left:220px;">
                    <button type="button" onclick="closeMsgDialog();" class="true">确&nbsp;&nbsp;定</button>
                </div>
            </div>

            <div class="container" >
                <div class="msg">
                    <h3>应用列表</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="application_msg">
                    <div class="app_note">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1.请您按照规则，保持您的应用良好运营，如果出现违规等恶意操作，我们将会对您的账号进行冻结及禁用等处理。</p>
                        <p>2.如果您不想使用当前应用，可以选择禁用，受到禁用的应用将就此作废，不可恢复。</p>
                        <p>3.如果您的应用出于冻结状态，需要联系我们的客服人员进行解冻，才可以恢复正常使用。</p>
                    </div>
                    <div class="app_select">
                        <span class="check_font">应用类型</span>
                        <select name="appType" id="appType">

                        </select>
                        <span class="check_font">应用名称</span>
                        <input type="text" id="appName" name="appName" size="18" maxlength="30" class="l_input_style" style="width:220px;"/>

                        <p class="f_right">
                            <button type="button" class="md_btn yzj_Mgr" id="appSearch">查询</button>

                            <span class="open_down_billed" onclick="downloadReport();"><i class="common_icon download_txt"></i>导出应用</span>

                        </p>
                    </div>

                    <!--应用列表-->
                    <div class="application">
                        <table class="table_style" id="apptable">
                            <thead>
                            <tr>
                                <th style="width:14.6%">应用名称</th>
                                <th style="width:13.6%">应用类型</th>
                                <th style="width:16.6%">创建时间</th>
                                <th style="width:24.6%">APP&nbsp;ID</th>
                                <th style="width:8.6%">状态</th>
                                <%--<th style="width:10.6%">消费限额(元)</th>--%>
                                <th style="width:16.6%">操作</th>
                            </tr>
                            </thead>
                            <tbody id="integralBody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="pagination"></div>
                    </div>
                </div>
                <!--查询弹层-->
                <div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">
                    <div class="modal_head">
                        <h4>ECC应用导出</h4>
                        <div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
                    </div>
                    <div class="modal-common_body mdl_pt_body">
                        <div class="test_success test_success_left">
                            <i class="attention"></i>
                            <span>您暂无数据</span>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>
<!-- 呼入 -->
<script id="integralTemplate" type="text/x-jquery-tmpl">
	<tr class="date_hg">
		<td>\${appName}</td>
		<td>\${appType}</td>
		<td>\${createDate}</td>
		<td>\${appid}</td>
		<td>\${stt}</td>
		<%--<td>\${quota}</td>--%>
		<td>
		{{if status=='00'}}
			  <%--<a href="<c:url value='/phoneApp/appInfoEdit'/>?appid=\${appid}">编辑</a>--%>
			  <a href="<c:url value='/eccApp/appInfoView'/>?appid=\${appid}">查看</a>
			  <%--<a href="javascript:void(0);" onclick="appCanel('\${id}','\${appid}');">删除</a>--%>
		{{/if}}
		{{if status=='01'}}
			  <a href="<c:url value='/appMgr/appInfoView'/>?appid=\${appid}">查看</a>
		{{/if}}
		</td>
		<%--<td>\${limit_flag}</td>--%>
	</tr>
</script>

<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>

<script>
    var appid;
    var appid1;
    function appCanel(id,a){
        $("#shadeWin").show();
        $("#app_delete_box").show();
        appid = id;
        appid1 = a;
    }


    var validator = $("#userForm").validate({
        rules: {
            appUpName: {
                required: true,
                appName:true
            },
            callbackUrl: {
                required: true,
                appUrl: true
            }
        },messages: {
            appUpName: {
                required: '请输入应用名称！'
            },callbackUrl: {
                required:'请输入URL！',
                appUrl:"请输入合法的URL！"
            }
        }
    });

    $(function(){
        $('#appSearch').click(function() {
            var tpyeValue = $("#appType").val();
            var nameValue = $("#appName").val();
            $("#apptable").reload(
                    {'appType':tpyeValue,"appName":nameValue}
            )
        });
    })

    ajaxDicData('${appConfig.authUrl}',"appType","appType");




    function closeDiv(divid){
        $('#' + divid ).hide();
        $('#shadeWin').hide();
    }
    // 应用列表
    $("#apptable").page({
        url:"<c:url value='/eccApp/list'/>",
        pageSize:5,
        dataRowCallBack:dataRowCallBack
    });

    function dataRowCallBack(row) {
        // TODO:
        if (row.createDate) {
            row.createDate = o.formatDate(row.createDate, "yyyy-MM-dd hh:mm:ss");
        }
        if (row.status == '00') {
            row.stt = "正常";
        }
        if (row.status == '01') {
            row.stt = "冻结";
        }
        if (row.status == '02') {
            row.stt = "已删除";
        }
        if (row.limitFlag == '00') {
            row.quota = "无限制";
        }

        return row;
    }


    // 导出报表
    function downloadReport() {
        var tpyeValue = $("#appType").val();
        var nameValue = $("#appName").val();
        $("#apptable").downloadReport("<c:url value='/eccApp/downloadReport'/>",{'appType':tpyeValue,"appName":nameValue});
    }


    //关闭消息框
    function closeMsgDialog(){
        $("#shadeWin").hide();
        $("#msgDialog").hide();
    }

</script>
</body>
</html>
