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
            <div class="container15">
                <div class="msg">
                    <h3>铃声管理&gt;铃声修改</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <form id="voiceForm" name = "voiceForm" action="<c:url value='/voiceMgr/update'/>" method="post" enctype="multipart/form-data">
                <div class="ringEdit">
                    <div class="create_app_common">
                        <label class="create_app_title_left" >应用名称:</label>
                        <input type="hidden"  id="appid" name="appid" value="${voice.appid}"/>
                        <input type="hidden"  id="appname" name="appname" value="${appname}"/>
                        <span class="create_app_title_left">${appname}</span>
                    </div>
                    <div class="create_app_common">
                        <label  class="create_app_title_left" >铃声上传:</label>
                        <input type="text" id="voiceUrl1" name="voiceFile1" class="l_input_style" value="${voice.voiceUrl}" readonly= "true" style="width:400px;"/>
                        <%--<input type='button' class='browse' value='浏览' onclick="document.getElementById('voiceUrl').click()" />--%>
                        <button type="button" class="browse" style="">浏览</button>
                        <input type="file" name="voiceFile" id="voiceUrl" class="childPosition" style="left:870px;"  onchange="document.getElementById('voiceUrl1').value=this.value" />
                        <%--<input type="file" name="voiceFile" id="voiceUrl" class="file" style="display: none"  size="28" onchange="document.getElementById('voiceUrl1').value=this.value" />--%>

                        <div><i class="create_ring_name_note ringTip">*铃声只支持原生WAV格式，最大支持2M。</i></div>
                        <div id="errormsg" class="Error_box" style="margin: 15px 0 -25px 103px;width: 400px;">
                            <c:if test="${not empty error}">
                                <c:if test="${error eq 'error'}"><label class="error">上传失败，请上传WAV格式铃声！ </label> </c:if>
                                <c:if test="${error eq 'size'}"><label class="error">上传失败，您上传的文件大于2M！ </label> </c:if>
                                <c:if test="${error eq 'null'}"><label class="error">文件为空，请选择铃声上传 </label></c:if>
                                <c:if test="${error eq 'chong'}"><label class="error">提交失败，该应用存在待审核的铃声 </label></c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left">铃声名称:</label>
                        <span class="create_app_title_left"><input type="text" onkeyup="checkVoiceName(this);" id="voiceName" name="voiceName" maxlength="10" class="l_input_style" style="width:400px;" value="${voice.voiceName}"/></span>
                        <div id="errorVoiceNameMsg" class="Error_box" style="margin: 15px 0 -25px 103px;width: 400px;"></div>
                    </div>
                    <div class="create_button">
                        <button type="button" id="voiceButton" name="voiceButton" class="sm_btn">提&nbsp;交</button>
                        <button type="button" id="restButton" name="restButton" class="cancel_btn">取&nbsp;消</button>
                    </div>
                </div>
                </form>
            </div>
        </div>
        <img id="tempimg" dynsrc="" src="" style="display:none" />

</section>
<script type="text/javascript">
    // wav音乐大小
    function getFileSize(element) {
        var fileSize = 0;
        if(element.files){ // element.files 是chrome，firefox等浏览器的对象，如果是ie的话他的值就是false

            var thisFile = element.files[0]; // 获取文件的对像
            fileSize = thisFile.size;//获取当前上传的文件的大小
        } else { // 如果是ie
            try {
                var obj_img = document.getElementById('tempimg');
                obj_img.dynsrc = element.value;
                fileSize = obj_img.fileSize;
            } catch (e){
                fileSize = 0;
            }
        }

        return fileSize;
    }

    $(function(){

        $('#voiceButton').click(function() {
            var url = $("#voiceUrl").val();
            $("#errormsg").html('');
            // 内容为空
            /*if (!url){
                $("#errormsg").html('<label class="error">文件为空，请选择铃声上传! </label>');
                return;
            }*/

            if(url){
                // 格式是否正确
                if (".WAV" != url.substring(url.lastIndexOf(".")).toLocaleUpperCase()) {
                    $("#errormsg").html('<label class="error">文件格式不正确! </label>');
                    return;
                }

                // 文件过大
                if (getFileSize(document.getElementById('voiceUrl')) > 2097152) {
                    $("#errormsg").html('<label class = "error">上传失败，您上传的文件大于2M! </label>');
                    return;
                }
            }

            var reg = /^([\u4e00-\u9fa5_a-zA-Z0-9]{0,10})$/;
            if(!reg.test($("#voiceName").val().trim())){
                $("#errorVoiceNameMsg").html('<label class="error">格式错误，请重新输入!</label>');
                return;
            }

            $(this).attr("disabled","disabled");
            $('#voiceForm').submit();
        });

        $('#restButton').click(function() {
            $("#voiceUrl1").val("");
            $("#voiceName").val("");
        })
    });

    function checkVoiceName(obj){
        //匹配只能输入数字，字母，汉字
        var reg = /^([\u4e00-\u9fa5_a-zA-Z0-9]{0,10})$/;
        if(reg.test($(obj).val().trim())){
            $("#errorVoiceNameMsg").html('');
        }else{
            $("#errorVoiceNameMsg").html('<label class="error">格式错误，请重新输入!</label>');
        }
    }



//    $(function (){
//        $('#voiceButton').click(function() {
//
//            $('#voiceForm').submit();
//
//        });
//
//
//        $('#restButton').click(function() {
//
//            $("#voiceUrl1").val("");
//        })
//    })
</script>
<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
</body>
</html>
