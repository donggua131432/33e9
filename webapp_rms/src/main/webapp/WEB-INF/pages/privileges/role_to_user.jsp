<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/2/26
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/css/H-ui.min.css"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>添加用户</title>
</head>
<script type="text/javascript">
    $(function(){

        /*$('.skin-minimal input').on('ifChecked', function(event){
            //如果是选中，点击后则为不选中
            $("#roleIds").val($("#roleIds").val()+$(this).val()+",");
        });
        $('.skin-minimal input').on('ifUnchecked', function(event){
            //如果不选中，点击后则为选中
            $("#roleIds").val($("#roleIds").val().replace($(this).val()+",",""));
        });*/
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            increaseArea: '20%'
        });
        $("#form-member-assign").Validform({
            tiptype:2,
            ajaxPost:true,
            callback:function(data){
                $.Showmsg(data.msg);
                setTimeout(function(){
                    $.Hidemsg();
                    if(data.code == 0){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        //关闭当前窗口
                        layer_close();
                    }
                },2000);
            }
        });

        //设置复选框的值
        $(".skin-minimal input").each(function(){
                <c:forEach items="${userRoleList}" var="userRole">
                                <%--alert(${userRole.roleId}+"***"+$(this).val());--%>
                                if("${userRole.roleId}".indexOf($(this).val()) >= 0){
                                    $(this).iCheck('check');
                                }
                </c:forEach>
        });
    })
</script>
<body>
<div class="pd-20">
    <form method="post" action="<c:url value='/user/saveUserRole'/>" class="form form-horizontal" id="form-member-assign">
        <input type="hidden" name="userId" value="${userInfo.id}"/>
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>系统角色：</label>
            <div class="formControls col-9">
                <div class="skin-minimal">
                    <c:forEach items="${roles}" var="role" varStatus="status">
                        <div class="check-box">
                            <input type="checkbox" id="${status.index}" value="${role.id}" name="roleId"/>
                            <label for="${status.index}">${role.name}</label>
                        </div>
                    </c:forEach>
                </div>
                <%--<input type="hidden" id="roleIds" name="roleIds"/>--%>
            </div>
            <div class="col-0"></div>
        </div>
        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
