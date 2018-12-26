<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>发布流程</title>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/workflow/deploy"/>" method="post" class="form form-horizontal" id="form-member-add" enctype="multipart/form-data">
        <div class="row cl">
            <label class="form-label col-3">zip文件：</label>
            <div class="col-7">
                <span class="btn-upload form-group">
                    <input class="input-text upload-url radius" type="text" name="uploadfile-1" id="uploadfile-1" readonly placeholder="只支持zip和bar格式的文件">
                    <a href="javascript:void(0);" class="btn btn-primary radius"><i class="iconfont">&#xf0020;</i> 浏览文件</a>
                    <input type="file" multiple name="file" id="file" class="input-file">
                </span>
            </div>
            <div class="col-2">
                <span id="buttonPlaceholder"></span>
                <span id="file_error" class="Validform_checktip Validform_wrong" style="display: none;"></span>
            </div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <div class="progress"><div class="progress-bar"><span class="sr-only" style="width:25%"></span></div></div>
            </div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="button" onclick="subf();" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="closeFeeRateDialog();"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
<script>

    // 表单校验
    function subf(){
        var submitF = true;
        $("input:file").each( function() {//遍历页面里所有的input:file
            var id = this.id;
            var ignore = $("#" + id).attr("ignore");
            if(!ignore){
                var value = this.value;
                if(!value){
                    $('#' + this.id + "_error").html("请选择上传的文件").show();
                    submitF = false;
                    return false;
                } else {
                    var suf = value.substring(value.lastIndexOf(".") + 1);
                    console.log(suf);
                    if (!/^zip|bar$/.test(suf)) {
                        $('#' + this.id + "_error").html("文件类型不正确").show();
                        submitF = false;
                        return false;
                    } else {
                        $('#' + this.id + "_error").hide();
                    }
                }
            }
        });

        if(submitF){
            $("#form-member-add").submit();
        }
    }
</script>
</body>
</html>