<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>认证信息</title>
    <script type="text/javascript">

        function closeEditDialog(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

        function submitToAuth(){
            var status = $("#pass").val();
            var voiceUrl = $("#voiceUrl").val();
            var appId = $("#appId").val();
            var comment = $('#comment').val();
            var id = $("#id").val();
            if(status == "请选择") {
                $(".status").css("display", "block");
            }else if((comment=='' || new RegExp("^[ ]+$").test(comment)) && status == "02"){
                $(".comment").css("display", "block");
            }else if((status == "02" && comment!='' && !new RegExp("^[ ]+$").test(comment)) || status == "01"){
                $(".status").css("display", "none");
                $("#okbutt").attr("disabled","disabled");
                $.post("<c:url value='/ringtone/editAudit'/>",{status:status, comment:comment, id:id, voiceUrl : voiceUrl, appId : appId},function(result){
                    if(result.status==0){
                        /*parent.refresh();*/
                        $('#sarch', parent.document).click();
                        closeEditDialog();
                    }
                });
            }
        }

        function removeSpan(){
            $(".status").css("display", "none");
            $(".comment").css("display", "none");
        }

        function limitNum(){
            var len = $("#comment").val().length;
            if(len > 300){
                $("#comment").val($("#comment").val().substring(0,300));
            }
        }

        function checkComment(){
            var comment = $('#comment').val();
            if(!new RegExp("^[ ]+$").test(comment)){
                $(".comment").css("display", "none");
            }
        }
    </script>
</head>
<body>
<div class="pd-20">

    <form class="form form-horizontal" id="form-edit">
        <input type="hidden" id="id" name="id" value="${appVoice.id}"/>
        <div class="row cl" style="margin-top: 0px">
            <label class="form-label col-4">accountID：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appInfo.sid}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">客户名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appInfo.companyName}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">应用名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appInfo.appName}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">提交时间：</label>
            <div class="formControls col-8" style="line-height: 2">
                <fmt:formatDate value="${appVoice.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">铃声名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appVoice.voiceName}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">铃声：</label>
            <input type="hidden" id="voiceUrl" value="${appVoice.voiceUrl}"/>
            <input type="hidden" id="appId" value="${appVoice.appid}"/>
            <div class="formControls col-8" style="line-height: 2">
                <audio id="voice" src="<c:url value='/voice/anthen/${appVoice.voiceUrl}'/>" controls="controls">
                    您的浏览器不支持 audio 标签。
                </audio>
                <a style="color: blue" href="<c:url value='/ringtone/download?url=${appVoice.voiceUrl}'/>">下载</a>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">大小：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${appVoice.voiceSize}M
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>审核：</label>
            <div class="formControls col-8" id="status" style="line-height: 2;width: 150px">
                <select id="pass" name="status" class="select" onchange="removeSpan();">
                    <option selected>请选择</option>
                    <option value="01">审核通过</option>
                    <option value="02">审核不通过</option>
                </select>
            </div>
            <span class="status" style="line-height: 2;display: none;color: red">请选择</span>
        </div>
        <div class="row cl">
            <label class="form-label col-4">备注：</label>
            <div class="formControls col-8" style="line-height: 2">
                <textarea onkeyup="limitNum();" oninput="checkComment();" id="comment" name="comment" style="width: 300px;height: 100px" placeholder="如果审核不通过，请备注原因"></textarea><br>
                <span class="comment" style="line-height: 2;display: none;color: red">请输入备注</span>
            </div>
        </div>
        <div class="row cl">
            <div style="margin-left: 33%;">
                <input class="btn btn-primary radius" type="button" id="okbutt" name="okbutt" onclick="submitToAuth();" value="&nbsp;&nbsp;确认&nbsp;&nbsp;">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input class="btn btn-default radius" type="reset" onclick="closeEditDialog();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
            </div>
        </div>

    </form>
</div>
</body>
</html>