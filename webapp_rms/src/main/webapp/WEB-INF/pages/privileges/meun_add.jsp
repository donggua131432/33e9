<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>

    <link href="${appConfig.resourcesRoot}/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${appConfig.resourcesRoot}/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
    <link href="${appConfig.resourcesRoot}/js/plugins/icheck/icheck.css" rel="stylesheet" type="text/css" />
    <link href="${appConfig.resourcesRoot}/js/plugins/Hui-iconfont/1.0.6/iconfont.css" rel="stylesheet"/>

    <title>添加菜单</title>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/menu/addMenu'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${sysType}" name="sysType">
        <div class="row cl">
            <label class="form-label col-3">一级菜单：</label>
            <div class="formControls col-5">
                <span class="select-box">
                    <select class="select" size="1" name="level1" id="level1">
                        <option value="" selected>请选择一级菜单</option>
                        <c:forEach items="${menus}" var="menu">
                            <option value="${menu.id}">${menu.name}</option>
                        </c:forEach>
                    </select>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">二级菜单：</label>
            <div class="formControls col-5">
                <span class="select-box">
                    <select class="select" size="1" name="level2" id="level2">
                        <option value="" selected>请选择二级菜单</option>
                    </select>
                </span>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>名称：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入菜单名称" id="name" name="name" nullmsg="名称不能为空">
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">路径：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="/xxx/xxx" id="url" name="url">
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">图标：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="" id="icon" name="icon">
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>排序：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="1" placeholder="请填写顺序" id="orderBy" name="orderBy" datatype="n" nullmsg="请填写顺序" errormsg="必须为数字">
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">状态：</label>
            <div class="formControls col-5 skin-minimal">
                <div class="radio-box">
                    <input type="radio" id="state-1" name="state" value="0" checked>
                    <label for="state-1">可用</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="state-2" name="state" value="1">
                    <label for="state-2">禁用</label>
                </div>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>

    </form>
</div>

<script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/icheck/jquery.icheck.min.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/Validform/5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/layer/2.1/layer.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/H-ui.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/H-ui.admin.js"></script>
<script type="text/javascript">
    $(function(){
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-member-add").Validform({
            tiptype:2,
            ajaxPost:true,
            callback:function(msg){
                if (msg.code == "ok") {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.refresh();
                    parent.layer.close(index);
                }
            }
        });

        $(function(){
            $("#level1").on("change", function(){
                $("#level2").html('<option value="">请选择二级菜单</option>');
                if (this.value) {
                    $.ajax({
                        type: "POST",
                        data: {parentId: this.value},
                        url: "<c:url value='/menu/getMenusByParentId'/>",
                        dataType: 'json',
                        success: function (arrys) {
                            if (arrys) {
                                $.each(arrys, function (i, arr) {
                                    $("#level2").append("<option value=" + arr.id + ">" + arr.name + "</option>");
                                });

                            }
                        }
                    });
                }
            });
        });
    });
</script>
</body>
</html>