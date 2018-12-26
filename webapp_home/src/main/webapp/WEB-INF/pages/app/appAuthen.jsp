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
    <link href="${appConfig.resourcesRoot}/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
    <script type="text/javascript">
        function authEnter(){
            window.location.href="<c:url value='/authen/authstatus'/>";
        }
    </script>

</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none"></div>
<!--遮罩end-->

<jsp:include page="../common/controlheader.jsp"/>
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('kf','appMgr','appSave');</script>
        <!-- 左侧菜单 end -->
        <div class="main3">
            <div class="container15">
                <div class="msg">
                    <h3>创建应用</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <p class="app_delete_common appNoTestLeft"><i class="attention"></i>您的企业信息未认证，不能创建应用.<button type="button" class="md_btn" onclick="authEnter();" style="margin-left:70px;">立即认证</button></p>
                <div class="create_application disableCsss">
                    <form action="">
                        <div class="app_edit_main">
                            <div class="create_app_common">
                                <b class="appColor">*</b>
                                <label for="app_name1" class="create_app_title_left appColor">应用名称:</label>
                                <input type="text" id="app_name1" class="l_input_style appBg" style="width:600px;"/>
                                <div><i class="create_app_name_note appColor">不超过50个字符</i></div>
                            </div>
                            <div class="create_app_common">
                                <label  class="create_app_title_left APP_state appColor">应用类型:</label>
                                <select name="app_state" id="APP_state" class="appBg">
                                    <option value="1"></option>
                                </select>
                            </div>
                            <div class="create_app_common create_app_callback">
                                <label class="create_app_title_left appColor">A S&nbsp;回调:</label>
                                <input type="checkbox" id="start1" class="start1"/>
                                <label for="start1">启用</label>
                            </div>
                            <div class="create_app_common">
                                <div class="callback_address_left">
                                    <div class="callback_address appColor">回调地址URL:</div>
                                    <textarea name="callback_address" id="create_callback_address" class="appBg" style="padding:5px 5px;font-size: 15px;" cols="73" rows="4"></textarea>
                                    <p class="URL_sample">
                                        <i class="appColor">示例：</i><br/>
                                        <i class="appColor">http://sgdhdjhuyhjtdyjytjy5454654ghkhglhii;oi;po;jhk,hjjhukhuuiloilioluyiolyuouiuhjlji<br/>loigjhvnghkjhgghlgiklvhkbmbhnvgg.com</i>
                                    </p>
                                </div>
                            </div>
                        </div>

                    </form>
                    <!--未验证弹层-->
                    <div class="modal-box  phone_t_code" id="ring_box" style="display: none;">
                        <div class="modal_head">
                            <h4>信息未认证</h4>
                            <div class="p_right"><a href="#" class="common_icon close_txt"></a></div>
                        </div>
                        <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                            <div class="app_delete_main">
                                <p class="app_delete_common"><i class="attention"></i>您的企业信息未认证，不能创建应用，</p>
                                <p class="app_delete_common">是否立即进行认证？</p>
                            </div>
                        </div>
                        <div class="mdl_foot" style="margin-left:230px;">
                            <button type="button" class="true">立即认证</button>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</section>


</body>
</html>
