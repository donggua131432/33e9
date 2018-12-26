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
    <title>话单下载</title>
</head>
<script type="text/javascript">
    $(function(){
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-record-add").Validform({
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
            },
            datatype: {
                "fileName": function (gets, obj, curform, datatype) {
                    if(!gets){
                        return "文件名不能为空！"
                    }
                    var result;
                    $.ajax({
                        async:false,
                        type: "post",
                        url: "<c:url value="/recordlist/checkFileName"/>",
                        dataType: "json",
                        data: {'fileName' : gets},
                        success: function (data) {
                            if (data.code == "ok") {
                                result = true;
                            }
                            if (data.code == "error") {
                                result = "文件名称已存在";
                            }
                        },
                        error: function () {
                            result = "数据请求异常";
                        }

                    });
                    return result;
                }

            }
        });

    });



</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/recordlist/download'/>" method="post" class="form form-horizontal" id="form-record-add">
        <textarea id="conds" name="conds"  style="display: none">${conds}</textarea>
        <input id="feeid" name="feeid" type="hidden" value="${feeid}">
        <input id="source" name="source" type="hidden" value="${code}">
        <input id="pageCount" name="pageCount" type="hidden" value="${pageCount}">
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>文件名：</label>

            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入文件名" id="fileName" name="fileName" datatype="/^[\u4e00-\u9fa5-a-zA-Z0-9\(\)\-\（\）\,\，\——\；\。\！\.]+$/"  errormsg="文件名不能出现特殊字符"   nullmsg="文件名不能为空！" maxlength="30"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red"></span>数据长度：</label>
            <div class="formControls col-5">
                ${pageCount}条
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