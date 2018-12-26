<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <title>新增号段</title>
</head>
<script type="text/javascript">
    $(function(){

        $("#form-member-add").Validform({
            tiptype:2,
            ajaxPost:true,
            callback:function(data){
                $.Showmsg(data.msg);
                setTimeout(function(){
                    $.Hidemsg();
                    if(data.code == "ok"){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        //关闭当前窗口
                        layer_close();
                    }
                },2000);
            }
        });
    })

</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/appointLink/saveAppointLink'/>" method="post" class="form form-horizontal" id="form-member-add">
        <div class="row cl">
            <label class="form-label col-3">显示号码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="显示和呼叫目的号码不能都为空" id="xhTelno" name="xhTelno" datatype="/^[0-9]*$/|/^[*]?$/" maxlength="20" errormsg="显示号码前缀应为数字字符串或‘*’！"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">呼叫目的号码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="显示和呼叫目的号码不能都为空" id="destTelno" name="destTelno" datatype="/^[0-9]*$/|/^[*]?$/" maxlength="20" errormsg="呼叫目的号码前缀应为数字字符串或‘*’！"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">类型：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入类型" id="type" name="type" datatype="/^[0-9]*$/" maxlength="9"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>rn值：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入rn值" id="rn" name="rn" datatype="/^[0-9]{1,20}$/" nullmsg="RN值不能为空！" maxlength="20" errormsg="RN值应为数字字符串!"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">备注：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入备注" id="remake" name="remake" maxlength="20"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();;"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>