<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>角色添加</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <script type="text/javascript">
        $(function() {
            $('.skin-minimal input').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });

            $("#addRole").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.status == 0){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "zh1-10":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,12}$/,
                    "empty": /^\s*$/
                }
            });

            $("#type").select2({
                placeholder: "请选择角色类型"});
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/roleMgr/saveRole" />" method="post" class="form form-horizontal" id="addRole">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>角色名称：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入角色名称" id="name" name="name" datatype="zh1-30|n1-30|s1-30" maxlength="15" errormsg="角色名称不合法！" nullmsg="角色名称不能为空"/>
            </div>
            <div class="col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>角色类型：</label>
            <div class="formControls col-7">
                <select class="input-text" id="type" name="type" datatype="*" nullmsg="角色类型不能为空">
                    <option  value="">请选择</option>
                    <option   value=" ">全部</option>
                    <option  value="00">系统角色</option>
                </select>
            </div>
            <div class="col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>角色描述：</label>
            <div class="formControls col-7">
                <textarea  class="input-text" value="" placeholder="请输入角色描述,最大不超过200个汉字" id="description" name="description" datatype="*" maxlength="200" errormsg="角色描述不合法！" nullmsg="角色描述不能为空" style="height: 100px;"></textarea>
            </div>
            <div class="col-3"></div>
        </div>
        <div class="row cl">
            <div class="col-offset-5">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
