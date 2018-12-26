<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心页面</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui.min.css" />
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/sui.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript">
        $(function(){

            $('#appid').val('${appInfo.appid}');
            initPCArea('pcode','ccode','全部','<c:url value="/"/>');
            var siginForm = $("#applyNumForm").validate({
                rules: {
                    pcode: {
                        required: true
                    },
                    ccode: {
                        required: true
                    }
                    ,
                    amount: {
                        required: true,
                        amount:true
                    }
                    ,
                    rate: {
                        required: true,
                        rate:true
                    }

                },
                messages: {
                    pcode: {
                        required: '请选择省份！'
                    },ccode: {
                        required: '请选择城市！'
                    },amount: {
                        required: '请输入SIP号码数量！',
                        amount:"请输入大于0的合法SIP号码数量！"
                    },
                    rate: {
                        required: '请输入绑定固话比例！',
                        rate:"请输入大于0的合法绑定固话比例！"
                    }
                },
                errorPlacement: function(error, element) {
                    if (element.next().is('span')) {
                        error.insertAfter(element.next());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            $('#applyNumButton').click(function() {
                if(siginForm.form()) {
                    $('#applyNumForm').submit();
                }
            });
        });
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
        <script type="text/javascript">showSubMenu('sphone','sipPhoneMgr','sipPhoneNumMgr');</script>
        <!-- 左侧菜单 end -->
        <!--右边内容-->
        <div class="main3">
            <div class="container" >
                <div class="msg">
                    <h3>号码管理>申请记录>申请号码</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <form id="applyNumForm" name = "applyNumForm" action="<c:url value='/sipPhoneAppMgr/applyPhoneSave'/>" method="post">
                    <input type="hidden" id="appid" name="appid" />
                    <div class="ringEdit">
                        <div class="create_app_common">
                            <label class="create_app_title_left" >应用名称:</label>
                            <span  class="ringCommon">${appInfo.appName}</span>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left" >APP&nbsp;&nbsp;&nbsp;ID:</label>
                            <span  class="ringCommon">${appInfo.appid}</span>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left" >省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份:</label>
                            <select name="pcode" id="pcode" style="margin-right:10px;">
                            </select>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left" >城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市:</label>
                            <select id="ccode" name="ccode" style="margin-right:10px;">
                                <option selected value="">全部</option>
                            </select>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left">SIP号码数量 :</label>
                            <input type="text" id="amount" name="amount" maxlength="11" class="input_W140"/>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left">绑定固话比例:</label>
                            <input type="text" id="rate" name="rate" maxlength="11" class="input_W140"/><span style="margin-right:10px;">：1</span>
                            <em class="NumberText">例如：若填入2，则表示SIP号码与绑定固话比例为2:1，<br/>即两个SIP号码绑定一个固话号码</em>
                        </div>
                        <button type="submit"  name="applyNumButton" id="applyNumButton" class="sm_btn SmTop NumberBtn">确&nbsp;定</button>
                        <a href="<c:url value='/sipPhoneAppMgr/applySipPhoneRecord'/>?appid=${appInfo.appid}"><button type="button" class="cancel_btn SmTop">返&nbsp;回</button></a>
                    </div>
                </form>
                </div>
               </div>
        <!--右边内容 end-->
 </div>
</section>
</body>
</html>
