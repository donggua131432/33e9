<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>号码审核</title>
    <script type="text/javascript">

        function closeEditDialog(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

        function submitToAuth(){
            var appid = $("#appid").val();
            var ids = $("#id").val();
            var audioIds = $("#audioId").val();
            var auditCommon = $('#auditCommon').val();
            var auditStatus = $("#auditStatus").val();
            if(auditStatus == "请选择") {
                $(".status").css("display", "block");
            }else if((auditCommon=='' || new RegExp("^[ ]+$").test(auditCommon)) && auditStatus == "02"){
                $(".comment").css("display", "block");
            }else if((auditStatus == "02" && auditCommon!='' && !new RegExp("^[ ]+$").test(auditCommon)) || auditStatus == "01"){
                $(".status").css("display", "none");
                $("#sure").attr("disabled", "disabled");
                $.post("<c:url value='/sipPhoneNum/editAuditAll'/>",{appid:appid,audioIds:audioIds,auditStatus:auditStatus, auditCommon:auditCommon, ids:ids},function(result){
                    if(result.status==0){
                        layer.msg("批量审核成功!", {icon: 1});
                        $('#sarch', parent.document).click();
                        closeEditDialog();
                    }else if(result.status==2){
                        $("#sure").attr("disabled", false);
                        layer.msg("存在最新编辑的外显号项，请重新选择审批!", {icon: 1});
                    }else{
                        $("#sure").attr("disabled", false);
                        layer.msg("批量审核失败!", {icon: 1});
                    }
                });
            }
        }

        function removeSpan(){
            $(".status").css("display", "none");
            $(".auditCommon").css("display", "none");
        }

        function limitNum(){
            var len = $("#auditCommon").val().length;
            if(len > 300){
                $("#auditCommon").val($("#auditCommon").val().substring(0,300));
            }
        }

        function checkComment(){
            var auditCommon = $('#auditCommon').val();
            if(!new RegExp("^[ ]+$").test(auditCommon)){
                $(".auditCommon").css("display", "none");
            }
        }
    </script>
</head>
<body>
<div class="pd-20">

    <form class="form form-horizontal" id="form-edit">
        <input type="hidden" id="id" name="id" value="${ids}"/>
        <input type="hidden" id="audioId" name="audioId" value="${audioIds}"/>

        <input type="hidden" id="appid" name="appid" value="${appid}"/>

        <div class="mt-20">
            <table class="table table-border table-bordered table-hover table-bg">
                <thead>
                <tr class="text-c">
                    <th width="25%">sip号码</th>
                    <th width="25%">固话号码</th>
                    <th width="25%">外显号码</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${SpApplyNums}" var="SpApplyNums">
                    <tr>
                        <td>${SpApplyNums.sipphone}</td>
                        <td>${SpApplyNums.fixphone}</td>
                        <td>${SpApplyNums.showNum}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>审核：</label>
            <div class="formControls col-8" id="status" style="line-height: 2;width: 150px">
                <select id="auditStatus" name="auditStatus" class="select" onchange="removeSpan();">
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
                <textarea onkeyup="limitNum();" oninput="checkComment();" id="auditCommon" name="auditCommon" style="width: 300px;height: 100px" placeholder="如果审核不通过，请备注原因" maxlength="500"></textarea><br>
                <span class="comment" style="line-height: 2;display: none;color: red">请输入备注</span>
            </div>
        </div>

        <div class="row cl">
            <div style="margin-left: 33%;">
                <input class="btn btn-primary radius" id="sure" type="button" onclick="submitToAuth();" value="&nbsp;&nbsp;确认&nbsp;&nbsp;">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input class="btn btn-default radius" type="reset" onclick="closeEditDialog();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
            </div>
        </div>

    </form>
</div>
</body>
</html>