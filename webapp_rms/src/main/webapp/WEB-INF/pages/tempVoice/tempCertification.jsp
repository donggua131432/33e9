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
</head>
<body>
<div class="pd-20">

    <form class="form form-horizontal" id="form-edit">
        <div class="row cl">
            <label class="form-label col-4">模板类型：</label>
            <div class="formControls col-8" style="line-height: 2">
                <c:if test="${tempVoice.type eq '00'}">语音</c:if>
                <c:if test="${tempVoice.type eq '01'}">文本</c:if>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">模板名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${tempVoice.name}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">模板ID：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${tempVoice.id}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">客户名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${tvInfo.companyName}
            </div>
        </div>

        <c:if test="${tempVoice.type eq '01'}">
            <div class="row cl">
                <label class="form-label col-4">模板内容：</label>
                <div class="formControls col-8" style="line-height: 2">
                    ${tempVoice.tContent}
                </div>
            </div>
        </c:if>

        <!--- 已审核过的 --->
        <c:if test="${tempVoice.auditStatus ne '00'}">
            <div class="row cl">
                <label class="form-label col-4">审核：</label>
                <div class="formControls col-8" style="line-height: 2;">
                    <c:if test="${tempVoice.auditStatus eq '01'}">审核通过</c:if>
                    <c:if test="${tempVoice.auditStatus eq '02'}">审核不通过</c:if>
                </div>
            </div>
        </c:if>
        <c:if test="${tempVoice.auditStatus ne '01'}">
            <div class="row cl">
                <label class="form-label col-4">审核原因：</label>
                <div class="formControls col-8" style="line-height: 2;word-break: break-all; word-wrap: break-word;width: 300px;">
                    <c:out value="${tempVoice.auditCommon}"/>
                </div>
            </div>
        </c:if>
        <c:if test="${tempVoice.auditStatus eq '01' and tempVoice.type eq '01'}">
            <div class="row cl">
                <label class="form-label col-4">生成状态：</label>
                <div class="formControls col-8" style="line-height: 2;word-break: break-all; word-wrap: break-word;width: 300px;">
                    <c:if test="${empty tempVoice.vUrl}">
                        生成中
                    </c:if>
                    <c:if test="${not empty tempVoice.vUrl}">
                        已完成
                    </c:if>
                </div>
            </div>
        </c:if>
        <c:if test="${tempVoice.type eq '00'}">
            <div class="row cl">
                <label class="form-label col-8">
                    <audio id="voice" src="<c:url value="/voice/temp/${tempVoice.vUrl}"/>" controls="controls">
                        您的浏览器不支持 audio 标签。
                    </audio>
                </label>
                <div class="formControls col-4" style="line-height: 2">
                    <a style="color: blue" href="<c:url value="/tempVoiceAudit/download?url=${tempVoice.vUrl}&vType=${tempVoice.type}"/>" download="${tempVoice.vUrl.split("/")[1]}">语音模板下载</a>
                </div>
            </div>
        </c:if>
        <c:if test="${tempVoice.type eq '01'}">
         <c:if test="${ not empty tempVoice.vUrl}">
            <div class="row cl">
                <label class="form-label col-4">

                </label>
                <div class="formControls col-4" style="line-height: 2">
                    <a style="color: blue" href="<c:url value="/tempVoiceAudit/download?url=${tempVoice.vUrl}&vType=${tempVoice.type}"/>" download="${tempVoice.vUrl.split("/")[1]}">语音模板下载</a>
                </div>
            </div>
         </c:if>
        </c:if>
        <!--- 待审核的 --->
        <c:if test="${tempVoice.auditStatus eq '00'}">
            <div class="row cl">
                <label class="form-label col-4">审核：</label>
                <div class="formControls col-8" id="status" style="line-height: 2">
                    <select id="auditStatus" class="select" style="width: 300px;">
                        <option value="01">审核通过</option>
                        <option value="02">审核不通过</option>
                    </select>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">审核原因：</label>
                <div class="formControls col-8" style="line-height: 2;word-break: break-all; word-wrap: break-word;width: 300px;">
                    <textarea id="auditCommon" class="textarea" style="width: 300px;" maxlength="150"></textarea>
                </div>
            </div>

            <div class="row cl">
                <div style="margin-left: 33%;">
                    <input class="btn btn-primary radius" id="sure" type="button" onclick="submitToAuth();" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
                </div>
            </div>
        </c:if>
        <br>
    </form>
</div>
</body>
<script type="text/javascript">
    function submitToAuth() {

        var id = '${tempVoice.id}';
        var auditStatus = $("#auditStatus").val();
        var auditCommon = $("#auditCommon").val();

        if (auditStatus == '02' && !$.trim(auditCommon)) {
            layer.alert("审核不通过时,请输入审核原因", {icon:6});
            return;
        }

        $.ajax({
            type: "POST",
            url: "<c:url value="/tempVoiceAudit/audit"/>",
            data: {id:id, auditStatus:auditStatus, auditCommon:auditCommon,type:'${tempVoice.type}'},
            success: function(msg){
                console.log(msg);
                if (msg.code == 'ok') {
                    //刷新父级页面
                    parent.location.replace(parent.location.href);
                    //关闭当前窗口
                    layer_close();
                } else {
                    layer.alert("提交失败请重新提交", {icon:5});
                }
            }
        });
    }
</script>
</html>